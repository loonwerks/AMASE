package edu.umn.cs.crisys.safety.analysis.soteria;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import edu.umn.cs.crisys.safety.analysis.ast.visitors.SoteriaAstVisitor;
import jkind.Assert;

public class SoteriaComp extends SoteriaAst {

	public final String componentName;
	public final String faultString = "contract violation";
	public HashSet<String> inputFlows = new HashSet<String>();
	public HashSet<String> outputFlows = new HashSet<String>();
	public HashMap<String, SoteriaFault> basicEvents = new HashMap<>();
	public HashMap<String, SoteriaFormula> formulas = new HashMap<String, SoteriaFormula>();

	public List<String> ports = new ArrayList<>();

	public SoteriaComp(String componentName) {
		Assert.isNotNull(componentName);
		this.componentName = componentName;
	}

	public void addInput(String input) {
		inputFlows.add(input);
	}

	public void addOutput(String output) {
		outputFlows.add(output);
	}

	public void addBasicEvent(String uniqueFaultName, SoteriaFault basicEvent) {
		basicEvents.put(uniqueFaultName, basicEvent);
	}

	// TODO: update to support mivc
	public void addFormula(String propertyName, SoteriaFormula formula) {
		formulas.put(propertyName, formula);
	}

	@Override
	public <T> T accept(SoteriaAstVisitor<T> visitor) {
		return visitor.visit(this);
	}

}