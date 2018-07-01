package edu.umn.cs.crisys.safety.analysis.soteria;

import java.util.ArrayList;
import java.util.List;

import jkind.Assert;

public class SoteriaComp {

	public final String componentName;
	public final String faultString = "contract violation";
	public List<String> inputFlows = new ArrayList<>();
	public List<String> outputFlows = new ArrayList<>();
	public List<String> faults = new ArrayList<>();
	public List<SoteriaFault> basicEvents = new ArrayList<SoteriaFault>();
	public List<SoteriaFormula> formulas = new ArrayList<SoteriaFormula>();

	public List<String> ports = new ArrayList<>();

	public SoteriaComp(String componentName) {
		Assert.isNotNull(componentName);
		this.componentName = componentName;
		this.faults.add(faultString);
	}

	public void addInput(String input) {
		inputFlows.add(input);
	}

	public void addOutput(String output) {
		outputFlows.add(output);
	}

	public void addBasicEvent(SoteriaFault basicEvent) {
		basicEvents.add(basicEvent);
	}

	public void addSoteriaFormula(String propertyName, ArrayList<String> ivcSet) {
		ArrayList<SoteriaFormulaElem> formulaBody = new ArrayList<SoteriaFormulaElem>();
		for (String ivcElement : ivcSet) {
			if (ivcElement.startsWith("__fault")) {
				// TODO: get the fault name for that fault activation variable in ivcElement
				formulaBody.add(new CompFaultActivation("fault activation"));
			}else {
				// TODO: get the component guarantee name for the contract name in ivcElement
				formulaBody.add(new CompContractViolation("contract violation"));
			}
		}
		formulas.add(new SoteriaFormula(propertyName, formulaBody));
	}

}