package edu.umn.cs.crisys.safety.analysis.handlers;

import java.util.HashMap;

import javax.swing.JOptionPane;

import com.rockwellcollins.atc.agree.analysis.AgreeException;
import com.rockwellcollins.atc.agree.analysis.AgreeRenaming;
import com.rockwellcollins.atc.agree.analysis.views.AgreeResultsLinker;

import edu.umn.cs.crisys.safety.analysis.SafetyException;
import edu.umn.cs.crisys.safety.analysis.soteria.CompContractViolation;
import edu.umn.cs.crisys.safety.analysis.soteria.CompFaultActivation;
import edu.umn.cs.crisys.safety.analysis.soteria.SoteriaComp;
import edu.umn.cs.crisys.safety.analysis.soteria.SoteriaCompLib;
import edu.umn.cs.crisys.safety.analysis.soteria.SoteriaFault;
import edu.umn.cs.crisys.safety.analysis.soteria.SoteriaFormula;
import edu.umn.cs.crisys.safety.analysis.soteria.SoteriaFormulaSubgroup;
import edu.umn.cs.crisys.safety.analysis.soteria.SoteriaModel;
import edu.umn.cs.crisys.safety.safety.FaultSubcomponent;
import edu.umn.cs.crisys.safety.safety.impl.FaultStatementImpl;
import edu.umn.cs.crisys.safety.safety.impl.ProbabilityStatementImpl;
import jkind.api.results.AnalysisResult;
import jkind.api.results.CompositeAnalysisResult;
import jkind.api.results.JKindResult;
import jkind.api.results.PropertyResult;
import jkind.api.results.Renaming;
import jkind.results.ValidProperty;

public class IvcToSoteriaGenerator {
	SoteriaCompLib compLib = new SoteriaCompLib();
	SoteriaModel model = new SoteriaModel();
	boolean isLowerLevel = false;
	public HashMap<UniqueID, UniqueID> idMap = new HashMap<>();

	public SoteriaModel generateModel(AnalysisResult result, AgreeResultsLinker linker) {
		// get current verification result
		AnalysisResult curResult = ((CompositeAnalysisResult) result).getChildren().get(0);
		walkthroughResults(curResult, null, linker);
		model.addCompLib(compLib);
		// create component instances for the base model
		model.createCompInst();
		// create component connections for the base model
		model.createConnections();
		return model;
	}

	private void walkthroughResults(AnalysisResult result, SoteriaComp comp, AgreeResultsLinker linker) {
		// if one layer, the curResult is JKindResult for the current component verified
		if (result instanceof JKindResult) {
			Renaming renaming = linker.getRenaming(result);
			if (renaming instanceof AgreeRenaming) {
				// for each propertyResult
				for (PropertyResult propertyResult : ((JKindResult) result).getPropertyResults()) {
					// get property name
					String propertyName = updateName(propertyResult.getName());
					// if it's not an assumption, then it's a guarantee
					if (!propertyName.contains("assume") && !propertyName.contains("Assumptions")) {
						// if it's a valid guarantee
						if (propertyResult.getStatus().equals(jkind.api.results.Status.VALID)) {
							// add property as an output to the soteria map
							comp.addOutput(propertyName);
							// add property violation as a top level fault to the model
							if (!isLowerLevel) {
								CompContractViolation contractViolation = new CompContractViolation(comp.componentName,
										propertyName);
								model.addTopLevelFault(contractViolation);
							}
							ValidProperty property = (ValidProperty) propertyResult.getProperty();
							SoteriaFormula formula = new SoteriaFormula(propertyName);

							// TODO: when mivc is in place, update to handle a list of ivc sets
							// and update the formula to handle conjunction of different ivc sets
							SoteriaFormulaSubgroup formulaSubgroup = new SoteriaFormulaSubgroup(propertyName);
							for (String ivcElem : property.getIvc()) {
								String refStr = ((AgreeRenaming) renaming).getSupportRefString(ivcElem);
								// add each ivc element to formulaSubgroup
								if (refStr.startsWith("fault: ")) {
									// TODO: get the fault name for that fault activation variable in ivcElement
									String faultName = refStr.replaceFirst("fault: ", "");
									CompFaultActivation faultActivation = new CompFaultActivation(comp.componentName,
											updateName(faultName));
									formulaSubgroup.addFormulaElem(faultActivation);
									// if ivcElem is not yet in basicEvents
									if (!comp.basicEvents.containsKey(ivcElem)) {
										FaultStatementImpl faultStmtImpl = (FaultStatementImpl) ((AgreeRenaming) renaming)
												.getRefMap().get(faultName);
										for (FaultSubcomponent faultSub : faultStmtImpl.getFaultDefinitions()) {
											if (faultSub instanceof ProbabilityStatementImpl) {
												String probStr = ((ProbabilityStatementImpl) faultSub).getProbability();
												float failureProb = Float.parseFloat(probStr);
												// TODO: need to have component specify failure rate and exposure time in the future
												// currently treat exposure time as (float) 1.0
												// and treat the failure probability from the fault statement as the failure rate
												SoteriaFault basicEvent = new SoteriaFault(updateName(faultName),
														failureProb,
														(float) 1.0);
												comp.addBasicEvent(ivcElem, basicEvent);
											}
										}
									}

								} else {
									// add each ivc element that are verified contracts from subsequent layer to component inputs (sans duplicate)
									comp.addInput(updateName(refStr));
									CompContractViolation contractViolation = new CompContractViolation(
											comp.componentName, updateName(refStr));
									formulaSubgroup.addFormulaElem(contractViolation);
								}
							}
							if (!formulaSubgroup.elmeList.isEmpty()) {
								formula.addFormulaSubgroup(formulaSubgroup);
								comp.addFormula(propertyName, formula);
							}
						} else {

							JOptionPane.showMessageDialog(null,
									"One of the properties is invalid. The nominal model must have valid properties."
											+ " The invalid property is shown in the AGREE results pane.",
									"Safety Analysis Error",
									JOptionPane.ERROR_MESSAGE);

							throw new SafetyException("One of the properties is invalid. The nominal model"
									+ " must have valid properties.");
						}
					}
				}
			} else {
				throw new AgreeException("Not AGREE Renaming");
			}
		} else if (result instanceof CompositeAnalysisResult) {
			// get component name
			String compName = result.getName().replaceFirst("Verification for ", "").replaceFirst(".impl", "");
			// build Soteria model for the current component
			// get current component name
			SoteriaComp curComp = new SoteriaComp(compName);
			for (AnalysisResult curResult : ((CompositeAnalysisResult) result).getChildren()) {
				// recursively call walkthroughResults
				walkthroughResults(curResult, curComp, linker);
				// only the first result contains the top level properties
				if (!isLowerLevel) {
					isLowerLevel = true;
				}
			}
			compLib.addComp(curComp);

		} else {
			throw new AgreeException("Not JKindResult or CompositeAnalysisResult");
		}
	}

	public String updateName(String name) {
		String updatedName = null;
		String nameToCheck = null;
		int varIndex = 0;
		UniqueID originalNameId = new UniqueID(name);
		// first check if the original name and recordId tuple is already in the keys of the map
		// if yes, retrieve the updated name from its value
		if (idMap.containsKey(originalNameId)) {
			updatedName = idMap.get(originalNameId).id;
		}
		// if not, update the name
		else {
			// replace all non-alphanumeric characters with empty strings
			// remove leading _
			// replace sequence of underscore with one underscore
			updatedName = name.replaceAll("\\P{Alnum}", "_").replaceAll("^_+", "").replaceAll("\\_+", "_");
			// check if the name is longer than 48 characters
			// if yes, truncate it to 48 characters
			if (updatedName.length() > 48) {
				updatedName = updatedName.substring(0, 48);
			}
			nameToCheck = updatedName;
			// check if the updated name and recordId tuple is in the map values
			// if yes, update the name further so it's unique from existing values
			while (idMap.containsValue(new UniqueID(nameToCheck))) {
				varIndex++;
				// make sure the updated name is not longer than 48 characters
				int indexLength = String.valueOf(varIndex).length();
				if ((updatedName.length() + indexLength) > 48) {
					updatedName = updatedName.substring(0, (48 - indexLength));
				}
				nameToCheck = updatedName + "_" + varIndex;
			}
			updatedName = nameToCheck;
			// add a new entry into the map
			idMap.put(originalNameId, new UniqueID(updatedName));
		}

		return updatedName;
	}

}
