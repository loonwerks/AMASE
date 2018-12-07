//package edu.umn.cs.crisys.safety.analysis.handlers;
//
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//
//import com.rockwellcollins.atc.agree.analysis.AgreeException;
//import com.rockwellcollins.atc.agree.analysis.AgreeRenaming;
//import com.rockwellcollins.atc.agree.analysis.views.AgreeResultsLinker;
//
//import edu.umn.cs.crisys.safety.analysis.SafetyException;
//import edu.umn.cs.crisys.safety.analysis.soteria.CompContractViolation;
//import edu.umn.cs.crisys.safety.analysis.soteria.CompFaultActivation;
//import edu.umn.cs.crisys.safety.analysis.soteria.SoteriaComp;
//import edu.umn.cs.crisys.safety.analysis.soteria.SoteriaCompLib;
//import edu.umn.cs.crisys.safety.analysis.soteria.SoteriaFault;
//import edu.umn.cs.crisys.safety.analysis.soteria.SoteriaFormula;
//import edu.umn.cs.crisys.safety.analysis.soteria.SoteriaFormulaSubgroup;
//import edu.umn.cs.crisys.safety.analysis.soteria.SoteriaModel;
//import edu.umn.cs.crisys.safety.safety.FaultSubcomponent;
//import edu.umn.cs.crisys.safety.safety.impl.FaultStatementImpl;
//import edu.umn.cs.crisys.safety.safety.impl.ProbabilityStatementImpl;
//import jkind.api.results.AnalysisResult;
//import jkind.api.results.CompositeAnalysisResult;
//import jkind.api.results.JKindResult;
//import jkind.api.results.PropertyResult;
//import jkind.api.results.Renaming;
//import jkind.results.ValidProperty;
//
//public class IvcToSoteriaGenerator {
//	SoteriaCompLib compLib = new SoteriaCompLib();
//	SoteriaModel model = new SoteriaModel();
//	boolean isLowerLevel = false;
//	public HashMap<UniqueID, UniqueID> elemIdMap = new HashMap<>();
////	public HashMap<UniqueID, UniqueID> compIdMap = new HashMap<>();
//	public HashSet<String> compNameSet = new HashSet<>();
//
//	public SoteriaModel generateModel(AnalysisResult result, AgreeResultsLinker linker) {
//		// get current verification result
//		AnalysisResult curResult = ((CompositeAnalysisResult) result).getChildren().get(0);
//		walkthroughResults(curResult, null, linker);
//		model.addCompLib(compLib);
//		// create component instances for the base model
//		model.createCompInst();
//		// create component connections for the base model
//		model.createConnections();
//		return model;
//	}
//
//	private void walkthroughResults(AnalysisResult result, SoteriaComp comp, AgreeResultsLinker linker) {
//		// if one layer, the curResult is JKindResult for the current component verified
//		if (result instanceof JKindResult) {
//			Renaming renaming = linker.getRenaming(result);
//			if (renaming instanceof AgreeRenaming) {
//				// for each propertyResult
//				for (PropertyResult propertyResult : ((JKindResult) result).getPropertyResults()) {
//					extractPropertyResult(comp, (AgreeRenaming) renaming, propertyResult);
//				}
//			} else {
//				throw new AgreeException("Not AGREE Renaming");
//			}
//		} else if (result instanceof CompositeAnalysisResult) {
//			// get component name
//			String compName = result.getName().replaceFirst("Verification for ", "");
//			if (compName.contains(".")) {
//				compName = compName.replaceAll("\\.", "_");
//				// compName = compName.substring(0, compName.lastIndexOf('.'));
//			}
//			if (!compNameSet.contains(compName)) {
//				compNameSet.add(compName);
//				// build Soteria model for the current component
//				// get current component name
//				SoteriaComp curComp = new SoteriaComp(compName);
//				for (AnalysisResult curResult : ((CompositeAnalysisResult) result).getChildren()) {
//					// recursively call walkthroughResults
//					walkthroughResults(curResult, curComp, linker);
//					// only the first result contains the top level properties
//					if (!isLowerLevel) {
//						isLowerLevel = true;
//					}
//				}
//				compLib.addComp(curComp);
//			}
//
//		} else {
//			throw new AgreeException("Not JKindResult or CompositeAnalysisResult");
//		}
//	}
//
//	private void extractPropertyResult(SoteriaComp comp, AgreeRenaming renaming, PropertyResult propertyResult) {
//		// get original property name
//		String origPropertyName = propertyResult.getName();
//		String lustreName = renaming.getLustreNameFromAgreeVar(origPropertyName);
//		String propertyName = updateElemName(comp.componentName + "_" + lustreName);
//		// if it is a guarantee
//		if (lustreName.startsWith("__GUARANTEE")) {
//			// if it's a valid guarantee
//			if (propertyResult.getStatus().equals(jkind.api.results.Status.VALID)) {
//				// add property as an output to the soteria map
//				comp.addOutput(propertyName);
//				// add property violation as a top level fault to the model
//				if (!isLowerLevel) {
//					CompContractViolation contractViolation = new CompContractViolation(comp.componentName,
//							propertyName);
//					model.addTopLevelFault(contractViolation);
//				}
//				ValidProperty property = (ValidProperty) propertyResult.getProperty();
//				SoteriaFormula formula = new SoteriaFormula(propertyName);
//				// handle multiple ivc sets
//				for (List<String> ivcSet : property.getIvcSets()) {
//					SoteriaFormulaSubgroup formulaSubgroup = new SoteriaFormulaSubgroup(propertyName);
//					extractIvcSets(comp, renaming, formulaSubgroup, ivcSet);
//					if (!formulaSubgroup.elmeList.isEmpty()) {
//						formula.addFormulaSubgroup(formulaSubgroup);
//					}
//				}
//				if (!formula.formulaBody.isEmpty()) {
//					comp.addFormula(propertyName, formula);
//				}
//			} else if (propertyResult.getStatus().equals(jkind.api.results.Status.CANCELED)) {
//
////				JOptionPane.showMessageDialog(null,
////						"One of the properties was canceled in the process of model checking."
////								+ " Rerun this analysis to proceed.",
////						"Safety Analysis Error", JOptionPane.ERROR_MESSAGE);
//
//				throw new SafetyException("One of the properties was canceled in the process of model checking."
//						+ " Rerun this analysis to proceed.");
//			} else if (propertyResult.getStatus().equals(jkind.api.results.Status.INVALID)) {
////				JOptionPane.showMessageDialog(null,
////						"One of the properties is invalid. The model must be valid using AGREE Verify All Layers."
////								+ " The invalid property is shown in the AGREE console.",
////						"Safety Analysis Error",
////						JOptionPane.ERROR_MESSAGE);
//
//				throw new SafetyException(
//						"One of the properties is invalid. The model must be valid using AGREE Verify All Layers.");
//			}
//		}
//	}
//
//	private void extractIvcSets(SoteriaComp comp, AgreeRenaming renaming, SoteriaFormulaSubgroup formulaSubgroup,
//			List<String> ivcSet) {
//		for (String ivcElem : ivcSet) {
//			extractIvcElem(comp, renaming, formulaSubgroup, ivcElem);
//		}
//	}
//
//	private void extractIvcElem(SoteriaComp comp, AgreeRenaming renaming, SoteriaFormulaSubgroup formulaSubgroup,
//			String ivcElem) {
//		String ivcElemName = updateElemName(ivcElem);
//		// add each ivc element to formulaSubgroup
//		if (ivcElem.startsWith("__fault")) {
//			String refStr = renaming.getSupportRefString(ivcElem);
//			extractFaultIvcElem(comp, renaming, formulaSubgroup, ivcElemName, refStr);
//		} else {
//			extractContractIvcElem(comp, formulaSubgroup, ivcElemName);
//		}
//	}
//
//	private void extractContractIvcElem(SoteriaComp comp, SoteriaFormulaSubgroup formulaSubgroup, String propertyName) {
//		// add each ivc element that are verified contracts from subsequent layer to component inputs (sans duplicate)
//		comp.addInput(propertyName);
//		CompContractViolation contractViolation = new CompContractViolation(comp.componentName, propertyName);
//		formulaSubgroup.addFormulaElem(contractViolation);
//	}
//
//	private void extractFaultIvcElem(SoteriaComp comp, AgreeRenaming renaming, SoteriaFormulaSubgroup formulaSubgroup,
//			String faultName, String faultRefName) {
//		// get the fault name for that fault activation variable in ivcElement
//		CompFaultActivation faultActivation = new CompFaultActivation(comp.componentName, faultName);
//		formulaSubgroup.addFormulaElem(faultActivation);
//		// if ivcElem is not yet in basicEvents
//		if (!comp.basicEvents.containsKey(faultName)) {
//			FaultStatementImpl faultStmtImpl = (FaultStatementImpl) renaming.getRefMap().get(faultRefName);
//			for (FaultSubcomponent faultSub : faultStmtImpl.getFaultDefinitions()) {
//				if (faultSub instanceof ProbabilityStatementImpl) {
//					String probStr = ((ProbabilityStatementImpl) faultSub).getProbability();
//					float failureProb = Float.parseFloat(probStr);
//					// TODO: need to have component specify failure rate and exposure time in the future
//					// currently treat exposure time as (float) 1.0
//					// and treat the failure probability from the fault statement as the failure rate
//					SoteriaFault basicEvent = new SoteriaFault(updateElemName(faultName), failureProb, (float) 1.0);
//					comp.addBasicEvent(faultName, basicEvent);
//				}
//			}
//		}
//	}
//
//	public String updateElemName(String name) {
//		String updatedName = null;
//		UniqueID originalNameId = new UniqueID(name);
//		// first check if the original name is already in the keys of the map
//		// if yes, retrieve the updated name from its value
//		if (elemIdMap.containsKey(originalNameId)) {
//			updatedName = elemIdMap.get(originalNameId).id;
//		}
//		// if not, update the name
//		else {
//			// replace all non-alphanumeric characters with "_"
//			// remove leading _
//			updatedName = name.replaceAll("\\P{Alnum}", "_").replaceAll("^_+", "");
//			// add a new entry into the map
//			elemIdMap.put(originalNameId, new UniqueID(updatedName));
//		}
//
//		return updatedName;
//	}
//
////	public boolean checkCompName(String name) {
////		UniqueID originalNameId = new UniqueID(name);
////		// first check if the original name is already in the keys of the map
////		// if yes, need to create a new name
////		if (compNameSet.contains(originalNameId)) {
////			return true;
////		} else {
////			compIdMap.put(originalNameId, new UniqueID(updatedName));
////			return false;
////		}
////	}
////
////	public String updateCompName(String name) {
////		String updatedName = name;
////		String nameToCheck = updatedName;
////		int varIndex = 0;
////		UniqueID originalNameId = new UniqueID(name);
////		// first check if the original name is already in the keys of the map
////		// if yes, need to create a new name
////		if (compIdMap.containsKey(originalNameId)) {
////			// check if the updated name is in the map values
////			// if yes, update the name further so it's unique from existing values
////			while (compIdMap.containsValue(new UniqueID(nameToCheck))) {
////				varIndex++;
////				nameToCheck = updatedName + "_" + varIndex;
////			}
////			updatedName = nameToCheck;
////			// add a new entry into the map
////			compIdMap.put(originalNameId, new UniqueID(updatedName));
////		}
////
////		return updatedName;
////	}
//
//}
