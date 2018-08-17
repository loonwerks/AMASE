package edu.umn.cs.crisys.safety.analysis.handlers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.rockwellcollins.atc.agree.analysis.AgreeException;
import com.rockwellcollins.atc.agree.analysis.AgreeRenaming;
import com.rockwellcollins.atc.agree.analysis.views.AgreeResultsLinker;

import edu.umn.cs.crisys.safety.analysis.SafetyException;
import edu.umn.cs.crisys.safety.analysis.soteria.faultTree.SoteriaFTAndNode;
import edu.umn.cs.crisys.safety.analysis.soteria.faultTree.SoteriaFTLeafNode;
import edu.umn.cs.crisys.safety.analysis.soteria.faultTree.SoteriaFTNonLeafNode;
import edu.umn.cs.crisys.safety.analysis.soteria.faultTree.SoteriaFTOrNode;
import edu.umn.cs.crisys.safety.analysis.soteria.faultTree.SoteriaFaultTree;
import edu.umn.cs.crisys.safety.safety.FaultSubcomponent;
import edu.umn.cs.crisys.safety.safety.impl.FaultStatementImpl;
import edu.umn.cs.crisys.safety.safety.impl.ProbabilityStatementImpl;
import jkind.api.results.AnalysisResult;
import jkind.api.results.CompositeAnalysisResult;
import jkind.api.results.JKindResult;
import jkind.api.results.PropertyResult;
import jkind.api.results.Renaming;
import jkind.results.ValidProperty;

public class IvcToSoteriaFTGenerator {
	SoteriaFaultTree soteriaFT = new SoteriaFaultTree();
	boolean isLowerLevel = false;
	public HashMap<UniqueID, UniqueID> elemIdMap = new HashMap<>();
	public HashSet<String> compNameSet = new HashSet<>();

	public SoteriaFaultTree generateSoteriaFT(AnalysisResult result, AgreeResultsLinker linker) {
		// get current verification result
		AnalysisResult curResult = ((CompositeAnalysisResult) result).getChildren().get(0);
		walkthroughResults(curResult, null, linker);
		return soteriaFT;
	}

	private void walkthroughResults(AnalysisResult result, String compName, AgreeResultsLinker linker) {
		// if one layer, the curResult is JKindResult for the current component verified
		if (result instanceof JKindResult) {
			Renaming renaming = linker.getRenaming(result);
			if (renaming instanceof AgreeRenaming) {
				// for each propertyResult
				for (PropertyResult propertyResult : ((JKindResult) result).getPropertyResults()) {
					extractPropertyResult(compName, (AgreeRenaming) renaming, propertyResult);
				}
			} else {
				throw new AgreeException("Not AGREE Renaming");
			}
		} else if (result instanceof CompositeAnalysisResult) {
			// get component name
			String curCompName = result.getName().replaceFirst("Verification for ", "");
			if (curCompName.contains(".")) {
				curCompName = curCompName.replaceAll("\\.", "_");
			}
			if (!compNameSet.contains(curCompName)) {
				compNameSet.add(curCompName);
				// build Soteria model for the current component
				// get current component name
				for (AnalysisResult curResult : ((CompositeAnalysisResult) result).getChildren()) {
					// recursively call walkthroughResults
					walkthroughResults(curResult, curCompName, linker);
					// only the first result contains the top level properties
					if (!isLowerLevel) {
						isLowerLevel = true;
					}
				}
			}

		} else {
			throw new AgreeException("Not JKindResult or CompositeAnalysisResult");
		}
	}

	private void extractPropertyResult(String compName, AgreeRenaming renaming, PropertyResult propertyResult) {
		// get original property name
		String origPropertyName = propertyResult.getName();
		String lustreName = renaming.getLustreNameFromAgreeVar(origPropertyName);
		String propertyName = updateElemName(compName + "_" + lustreName);
		// if it is a guarantee
		if (lustreName.startsWith("__GUARANTEE")) {
			// if it's a valid guarantee
			if (propertyResult.getStatus().equals(jkind.api.results.Status.VALID)) {

				ValidProperty property = (ValidProperty) propertyResult.getProperty();
				SoteriaFTNonLeafNode propertyNode;
				boolean isNewNode = true;
				boolean createAndNode = (property.getIvcSets().size() > 1);
				if (!soteriaFT.intermediateNodes.containsKey(propertyName)) {
					if (createAndNode) {
						propertyNode = new SoteriaFTAndNode(propertyName);
					} else {
						propertyNode = new SoteriaFTOrNode(propertyName);
					}
				} else {
					propertyNode = soteriaFT.intermediateNodes.get(propertyName);
					// if the no child node has been populated for this node yet
					if (!(propertyNode instanceof SoteriaFTOrNode) && !(propertyNode instanceof SoteriaFTAndNode)) {
						if (createAndNode) {
							propertyNode = new SoteriaFTAndNode(propertyName);
						} else {
							propertyNode = new SoteriaFTOrNode(propertyName);
						}
					} else {
						isNewNode = false;
					}
				}

				if (isNewNode) {
					for (List<String> ivcSet : property.getIvcSets()) {
						int index = 0;
						String ivcSetNodeName = propertyName + "_" + Integer.toString(index);
						SoteriaFTOrNode ivcSetNode = new SoteriaFTOrNode(ivcSetNodeName);
						extractIvcSets(compName, renaming, ivcSetNode, ivcSet);
						propertyNode.addChildNode(ivcSetNodeName, ivcSetNode);
					}
					if (!isLowerLevel) {
						soteriaFT.addRootNode(propertyName, propertyNode);
					}
					soteriaFT.addIntermediateNode(propertyName, propertyNode);
				}
			}
		} else if (propertyResult.getStatus().equals(jkind.api.results.Status.CANCELED)) {
			// TODO: throw exception in the GUI
			throw new SafetyException("One of the properties was canceled in the process of model checking."
					+ " Rerun this analysis to proceed.");
		} else if (propertyResult.getStatus().equals(jkind.api.results.Status.INVALID)) {
			// TODO: throw exception in the GUI
			throw new SafetyException(
					"One of the properties is invalid. The model must be valid using AGREE Verify All Layers.");
		}

	}

	private void extractIvcSets(String compName, AgreeRenaming renaming, SoteriaFTOrNode ivcSetNode,
			List<String> ivcSet) {
		for (String ivcElem : ivcSet) {
			extractIvcElem(compName, renaming, ivcSetNode, ivcElem);
		}
	}

	private void extractIvcElem(String compName, AgreeRenaming renaming, SoteriaFTOrNode ivcSetNode, String ivcElem) {
		String ivcElemName = updateElemName(ivcElem);
		// add each ivc element to formulaSubgroup
		if (ivcElem.startsWith("__fault")) {
			String refStr = renaming.getSupportRefString(ivcElem);
			extractFaultIvcElem(compName, renaming, ivcSetNode, ivcElemName, refStr);
		} else {
			extractContractIvcElem(compName, ivcSetNode, ivcElemName);
		}
	}

	private void extractContractIvcElem(String compName, SoteriaFTOrNode ivcSetNode, String propertyName) {
		if (!soteriaFT.intermediateNodes.containsKey(propertyName)) {
			SoteriaFTNonLeafNode nonLeafNode = new SoteriaFTNonLeafNode(propertyName);
		} else {
			SoteriaFTNonLeafNode nonLeafNode = soteriaFT.intermediateNodes.get(propertyName);
			ivcSetNode.addChildNode(propertyName, nonLeafNode);
		}
	}

	private void extractFaultIvcElem(String compName, AgreeRenaming renaming, SoteriaFTOrNode ivcSetNode,
			String faultName, String faultRefName) {
		// if ivcElem is not yet in leaf nodes
		if (!soteriaFT.leafNodes.containsKey(faultName)) {
			FaultStatementImpl faultStmtImpl = (FaultStatementImpl) renaming.getRefMap().get(faultRefName);
			for (FaultSubcomponent faultSub : faultStmtImpl.getFaultDefinitions()) {
				if (faultSub instanceof ProbabilityStatementImpl) {
					String probStr = ((ProbabilityStatementImpl) faultSub).getProbability();
					float failureProb = Float.parseFloat(probStr);
					// TODO: need to have component specify failure rate and exposure time in the future
					// currently treat exposure time as (float) 1.0
					// and treat the failure probability from the fault statement as the failure rate
					String updatedFaultName = updateElemName(faultName);
					SoteriaFTLeafNode ftLeafNode = new SoteriaFTLeafNode(compName, updatedFaultName, failureProb,
							(float) 1.0);
					soteriaFT.addLeafNode(faultName, ftLeafNode);
					ivcSetNode.addChildNode(updatedFaultName, ftLeafNode);
				}
			}
		} else {
			SoteriaFTLeafNode leafNode = soteriaFT.leafNodes.get(faultName);
			ivcSetNode.addChildNode(faultName, leafNode);
		}

	}

	public String updateElemName(String name) {
		String updatedName = null;
		UniqueID originalNameId = new UniqueID(name);
		// first check if the original name is already in the keys of the map
		// if yes, retrieve the updated name from its value
		if (elemIdMap.containsKey(originalNameId)) {
			updatedName = elemIdMap.get(originalNameId).id;
		}
		// if not, update the name
		else {
			// replace all non-alphanumeric characters with "_"
			// remove leading _
			updatedName = name.replaceAll("\\P{Alnum}", "_").replaceAll("^_+", "");
			// add a new entry into the map
			elemIdMap.put(originalNameId, new UniqueID(updatedName));
		}

		return updatedName;
	}

}
