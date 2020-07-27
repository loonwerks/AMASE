package edu.umn.cs.crisys.safety.analysis.generators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.osate.aadl2.ComponentImplementation;

import com.rockwellcollins.atc.agree.analysis.AgreeException;
import com.rockwellcollins.atc.agree.analysis.AgreeRenaming;
import com.rockwellcollins.atc.agree.analysis.views.AgreeResultsLinker;

import edu.umn.cs.crisys.safety.analysis.MHSUtils;
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
	private LinkedHashMap<String, Set<List<String>>> mapPropertyToMCSs = new LinkedHashMap<String, Set<List<String>>>();
	private String componentName = "";

	public SoteriaFaultTree generateSoteriaFT(AnalysisResult result, AgreeResultsLinker linker) {
		// initialize
		MHSUtils.clearLocals();
		// get current verification result
		AnalysisResult curResult = ((CompositeAnalysisResult) result).getChildren().get(0);
		walkthroughResults(curResult, null, linker);
		// some node's child nodes were added before those child nodes obtained their own child nodes
		// fix those discrepancies in the traverse
		soteriaFT.updateChildNodes();
		// sort through intermediate nodes to declare before use
		soteriaFT.sortIntermediateNodes();
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
			if (curCompName.equals("agree") || curCompName.equals("safety")) {
				ComponentImplementation comp = linker.getComponent(result);
				componentName = comp.getName();
			} else {
				componentName = curCompName;
			}
			// ---------------------------------------------------------------
			// TODO: The following two lines of code are commented out because
			// they cause the resolve visitor to not complete its job.
			// Need to figure out why the actual component name causes a problem
			// for resolve visitor.
			// Currently at the top level of analysis, the component name
			// is saved as the annex name (agree or safety).
			// This is incorrect.
//			ComponentImplementation comp = linker.getComponent(result);
//			String curCompName = comp.getName();
			// ----------------------------------------------------------------

			// if (!compNameSet.contains(curCompName)) {
			// compNameSet.add(curCompName);
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
			// }

		} else {
			throw new AgreeException("Not JKindResult or CompositeAnalysisResult");
		}
	}

	private void extractPropertyResult(String compName, AgreeRenaming renaming, PropertyResult propertyResult) {
		// get original property name
		String origPropertyName = propertyResult.getName();
		String lustreName = renaming.getLustreNameFromAgreeVar(origPropertyName);
		String propertyName = MHSUtils.updateElemName(compName + "_" + lustreName);
		// if it is a guarantee
		if (lustreName.startsWith("__GUARANTEE")) {
			// if it's a valid guarantee
			if (propertyResult.getStatus().equals(jkind.api.results.Status.VALID)) {
				// TODO: get user specified property name if exists
				String propertyDescription = propertyResult.getName();
				ValidProperty property = (ValidProperty) propertyResult.getProperty();
				// check if there is timeout in MIVC analysis
				if (property.getMivcTimedOut()) {
					new SafetyException("MIVC ANALYSIS TIMEOUT FOR " + lustreName + ": " + origPropertyName);
				}

				// Create mapPropToMCS for hierarchical ft printout
				String propName = propertyName + ": " + propertyDescription;
				Set<List<String>> mcsList = new HashSet<List<String>>();

				// turn MIVC sets to MCS sets
				// no limit on mhs set size
				Set<List<String>> mcsSets = MHSUtils.computeMHS(property.getIvcSets(), 0, false);
				// Create list for prop->MCS mapping
				mcsList = createMCSList(mcsSets, renaming, compName);
				mapPropertyToMCSs.put(componentName + "__" + lustreName, mcsList);

				// create node when mcsSets is not empty
				if (!mcsSets.isEmpty()) {
					SoteriaFTNonLeafNode propertyNode;
					boolean isNewNode = true;
					boolean createOrNode = (mcsSets.size() > 1);
					if (!soteriaFT.intermediateNodes.containsKey(propertyName)) {
						if (createOrNode) {
							propertyNode = new SoteriaFTOrNode(propertyName, propertyDescription);
						} else {
							propertyNode = new SoteriaFTAndNode(propertyName, propertyDescription);
						}
					} else {
						propertyNode = soteriaFT.intermediateNodes.get(propertyName);
						// if the no child node has been populated for this node yet
						if (!(propertyNode instanceof SoteriaFTOrNode) && !(propertyNode instanceof SoteriaFTAndNode)) {
							if (createOrNode) {
								propertyNode = new SoteriaFTOrNode(propertyName, propertyNode.propertyDescription);
							} else {
								propertyNode = new SoteriaFTAndNode(propertyName, propertyNode.propertyDescription);
							}
						} else {
							isNewNode = false;
						}
					}

					if (isNewNode) {
						int index = 0;
						for (List<String> mcsSet : mcsSets) {
							String mcsSetNodeName = propertyName + "_" + Integer.toString(index);
							SoteriaFTAndNode mcsSetNode = new SoteriaFTAndNode(mcsSetNodeName, propertyDescription);
							extractMCSSets(compName, renaming, mcsSetNode, mcsSet);
							propertyNode.addChildNode(mcsSetNodeName, mcsSetNode);
							// mcsSetNode.addParentNode(propertyNode);
							soteriaFT.addIntermediateNode(mcsSetNodeName, mcsSetNode);
							// update intermediate node
							soteriaFT.addIntermediateNode(propertyNode.nodeName, propertyNode);
							index++;
						}
					}
					if (!isLowerLevel) {
						soteriaFT.addRootNode(propertyName, propertyNode);
						propertyNode.setRoot();
					}
					soteriaFT.addIntermediateNode(propertyName, propertyNode);
				} else {
					SoteriaFTNonLeafNode propertyNode = new SoteriaFTNonLeafNode(propertyName, propertyDescription);
					if (soteriaFT.intermediateNodes.containsKey(propertyName)) {
						propertyNode = soteriaFT.intermediateNodes.get(propertyName);
					}
					propertyNode.resolved = true;
					propertyNode.nodeValue = false;
					if (!isLowerLevel) {
						soteriaFT.addRootNode(propertyName, propertyNode);
						propertyNode.setRoot();
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
		} else if (propertyResult.getStatus().equals(jkind.api.results.Status.UNKNOWN)) {
			// TODO: throw exception in the GUI
			throw new SafetyException(
					"One of the properties is unknown. All properties must be known and valid to generate minimal cut sets"
							+ " Tip: Set solver to Z3 and set analysis properties PDR = 4.");
		}

	}

	/**
	 * Creates a set of lists corresponding to each mcs set for a property.
	 * Used for hierarchical ft printout.
	 * @param mcsSets Set of list of strings (all mcss for a property)
	 * @param renaming AgreeRenaming map
	 * @param compName component name
	 * @return Returns new set of lists with new descriptive strings
	 */
	private Set<List<String>> createMCSList(Set<List<String>> mcsSets, AgreeRenaming renaming, String compName) {
		HashSet<List<String>> mcsFullSet = new HashSet<List<String>>();
		if (!mcsSets.isEmpty()) {
			for (List<String> mcsSet : mcsSets) {
				List<String> mcsList = new ArrayList<String>();
				for (String mcsElem : mcsSet) {
					mcsList.add(getMCSInfo(mcsElem, renaming, compName));
				}
				mcsFullSet.add(mcsList);
			}
		}
		return mcsFullSet;
	}

	/**
	 * Gathers details about MCS element and returns descriptive string
	 * including component name, fault/contract name and description.
	 * @param mcsElem String of mcs element
	 * @param renaming AgreeRenaming map
	 * @param compName component name where this fault/contract can be found
	 * @return descriptive string for hierarchical ft textual representation
	 */
	private String getMCSInfo(String mcsElem, AgreeRenaming renaming, String compName) {
		String refStr = renaming.getSupportRefString(mcsElem);
		if (mcsElem.startsWith("__fault")) {
			FaultStatementImpl faultStmtImpl = (FaultStatementImpl) renaming.getRefMap().get(refStr);
			String faultUserName = faultStmtImpl.getName();
			String faultUserExplanation = faultStmtImpl.getStr();
			return "Contributing fault found in component " + stripOutInstanceName(mcsElem) + ": " + faultUserName
					+ ": " + faultUserExplanation + " (" + mcsElem + ")";

		} else {
			return "Supporting contract in component " + componentName + ": " + refStr;

		}
	}

	private void extractMCSSets(String compName, AgreeRenaming renaming, SoteriaFTAndNode mcsSetNode,
			List<String> mcsSet) {
		for (String mcsElem : mcsSet) {
			extractMCSElem(compName, renaming, mcsSetNode, mcsElem);
		}
	}

	private void extractMCSElem(String compName, AgreeRenaming renaming, SoteriaFTAndNode mcsSetNode, String mcsElem) {
		String mcsElemName = MHSUtils.updateElemName(mcsElem);
		// add each mcs element to formulaSubgroup
		if (mcsElem.startsWith("__fault")) {
			String refStr = renaming.getSupportRefString(mcsElem);
			extractFaultMCSElem(compName, renaming, mcsSetNode, mcsElemName, refStr, mcsElem);
		} else {
			extractContractMCSElem(compName, mcsSetNode, mcsElemName);
		}
	}

	private void extractContractMCSElem(String compName, SoteriaFTAndNode mcsSetNode, String propertyName) {
		SoteriaFTNonLeafNode nonLeafNode;
		if (!soteriaFT.intermediateNodes.containsKey(propertyName)) {
			nonLeafNode = new SoteriaFTNonLeafNode(propertyName, "");
			soteriaFT.addIntermediateNode(propertyName, nonLeafNode);
		} else {
			nonLeafNode = soteriaFT.intermediateNodes.get(propertyName);
		}
		// note: a SoteriaFTNonLeafNode added here as a child node could be updated later with its own child node
		// will walk through all nodes again to fix the discrepancy
		mcsSetNode.addChildNode(propertyName, nonLeafNode);
		// update the node in intermediate nodes
		soteriaFT.addIntermediateNode(mcsSetNode.nodeName, mcsSetNode);
		// nonLeafNode.addParentNode(mcsSetNode);
	}

	private void extractFaultMCSElem(String compName, AgreeRenaming renaming, SoteriaFTAndNode mcsSetNode,
			String faultName, String faultRefName, String originalFaultName) {
		// differentiate same fault definitions activated in subcomponents of different parent components
		String updatedFaultName = MHSUtils.updateElemName(compName + "_" + faultName);
		// if mcsElem is not yet in leaf nodes
		if (!soteriaFT.leafNodes.containsKey(updatedFaultName)) {
			FaultStatementImpl faultStmtImpl = (FaultStatementImpl) renaming.getRefMap().get(faultRefName);
			// original fault name specified by the user
			String faultUserName = faultStmtImpl.getName();
			// original fault explanation specified by the user
			String faultUserExplanation = faultStmtImpl.getStr();
			// probability string
			String probStr = "";
			// failure probability
			float failureProb = (float) 0.0;
			for (FaultSubcomponent faultSub : faultStmtImpl.getFaultDefinitions()) {
				if (faultSub instanceof ProbabilityStatementImpl) {
					probStr = ((ProbabilityStatementImpl) faultSub).getProbability();
					failureProb = Float.parseFloat(probStr);
				}
			}
			// TODO: need to have component specify failure rate and exposure time in the future
			// currently treat exposure time and failure rate as (float) 1.0
			// and set the failure probability from the fault statement as the failure rate
			SoteriaFTLeafNode ftLeafNode = new SoteriaFTLeafNode(compName, updatedFaultName, (float) 1.0, (float) 1.0,
					failureProb, originalFaultName, faultUserName, faultUserExplanation);
			soteriaFT.addLeafNode(updatedFaultName, ftLeafNode);
			mcsSetNode.addChildNode(updatedFaultName, ftLeafNode);
			// update intermediate node
			soteriaFT.addIntermediateNode(mcsSetNode.nodeName, mcsSetNode);
			// ftLeafNode.addParentNode(mcsSetNode);
		} else {
			SoteriaFTLeafNode leafNode = soteriaFT.leafNodes.get(updatedFaultName);
			mcsSetNode.addChildNode(updatedFaultName, leafNode);
			soteriaFT.addIntermediateNode(mcsSetNode.nodeName, mcsSetNode);
			// leafNode.addParentNode(mcsSetNode);
		}

	}

	/**
	 * Pulls component instance name from lustre fault name.
	 * @param lustreName __fault__independently__active__instanceName__instanceName__fault_n
	 * @return instanceName
	 */
	public String stripOutInstanceName(String lustreName) {
		String instanceName = "";
		String intermed = lustreName.replace("__fault__independently__active__", "");
		String[] arrOfStr = intermed.split("__", 2);
		if (arrOfStr.length != 0) {
			instanceName = arrOfStr[0];
		}
		return instanceName;
	}

	/**
	 * Public getter for map of property string to its mcs strings.
	 * @return map of descriptive strings prop -> {MCSs}
	 */
	public LinkedHashMap<String, Set<List<String>>> getMapPropertyToMCSs() {
		return mapPropertyToMCSs;
	}

}
