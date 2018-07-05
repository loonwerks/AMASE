package edu.umn.cs.crisys.safety.analysis.handlers;

import com.rockwellcollins.atc.agree.analysis.AgreeException;
import com.rockwellcollins.atc.agree.analysis.AgreeRenaming;
import com.rockwellcollins.atc.agree.analysis.views.AgreeResultsLinker;

import edu.umn.cs.crisys.safety.analysis.soteria.CompContractViolation;
import edu.umn.cs.crisys.safety.analysis.soteria.CompFaultActivation;
import edu.umn.cs.crisys.safety.analysis.soteria.SoteriaComp;
import edu.umn.cs.crisys.safety.analysis.soteria.SoteriaCompLib;
import edu.umn.cs.crisys.safety.analysis.soteria.SoteriaFormula;
import edu.umn.cs.crisys.safety.analysis.soteria.SoteriaFormulaSubgroup;
import edu.umn.cs.crisys.safety.analysis.soteria.SoteriaModel;
import jkind.api.results.AnalysisResult;
import jkind.api.results.CompositeAnalysisResult;
import jkind.api.results.JKindResult;
import jkind.api.results.PropertyResult;
import jkind.api.results.Renaming;
import jkind.results.ValidProperty;

public class IvcToSoteriaGenerator {
	SoteriaCompLib soteriaCompLib = new SoteriaCompLib();

	public SoteriaModel generateModel(AnalysisResult result, AgreeResultsLinker linker) {
		// get current verification result
		AnalysisResult curResult = ((CompositeAnalysisResult) result).getChildren().get(0);
		// get component name
		String compName = curResult.getName().replaceFirst("Verification for ", "").replaceFirst(".impl", "");
		// build Soteria component for the current component
		// get current component name
		SoteriaComp curComp = new SoteriaComp(compName);
		walkthroughResults(curResult, curComp, linker);
		soteriaCompLib.addComp(curComp);
		return new SoteriaModel(soteriaCompLib);
	}

	private void walkthroughResults(AnalysisResult result, SoteriaComp comp, AgreeResultsLinker linker) {

		// if one layer, the curResult is JKindResult for the current component verified
		if (result instanceof JKindResult) {
			// for each propertyResult
			for (PropertyResult propertyResult : ((JKindResult) result).getPropertyResults()) {
				String propertyName = propertyResult.getName();
				System.out.println("property name: "+propertyName);
				//if it's not an assumption, then it's a guarantee
				if(!propertyName.contains("assume") &&  !propertyName.contains("Assumptions")) {
					//if it's a valid guarantee
					if (propertyResult.getStatus().equals(jkind.api.results.Status.VALID)) {
						//add property as an output to the soteria map
						comp.addOutput(propertyName);
						ValidProperty property = (ValidProperty) propertyResult.getProperty();
						Renaming renaming = linker.getRenaming(result);
						if (renaming instanceof AgreeRenaming) {
							// TODO: when mivc is in place, update to handle a list of ivc sets
							// and update the formula to handle conjunction of different ivc sets
							SoteriaFormulaSubgroup formulaSubgroup = new SoteriaFormulaSubgroup(propertyName);
							for (String ivcElem : property.getIvc()) {
								System.out.println("ivcElem: " + ivcElem);
								// add each ivc element to component inputs (sans duplicate)
								String refStr = ((AgreeRenaming) renaming).getSupportRefString(ivcElem);
								comp.addInput(refStr);
								// add each ivc element to formulaSubgroup
								if (refStr.startsWith("__fault")) {
									// TODO: get the fault name for that fault activation variable in ivcElement
									CompFaultActivation faultActivation = new CompFaultActivation(refStr);
									formulaSubgroup.addFormulaElem(faultActivation);
								} else {
									// TODO: get the component guarantee name for the contract name in ivcElement
									CompContractViolation contractViolation = new CompContractViolation(refStr);
									formulaSubgroup.addFormulaElem(contractViolation);
								}
							}
							SoteriaFormula formula = new SoteriaFormula(propertyName);
							formula.addFormulaSubgroup(formulaSubgroup);
							comp.addFormula(propertyName, formula);
						} else {
							throw new AgreeException("Not AGREE Renaming");
						}
					}
				}
			}
		}
		else if (result instanceof CompositeAnalysisResult) {
			for (AnalysisResult curResult : ((CompositeAnalysisResult) result).getChildren()) {
				// recursively call walkthroughResults
				walkthroughResults(curResult, comp, linker);
			}

		} else {
			throw new AgreeException("Not JKindResult or CompositeAnalysisResult");
		}
	}

}
