package edu.umn.cs.crisys.safety.analysis.soteria;

import jkind.Assert;


public class CompContractViolation extends SoteriaFormulaElement {
	public final String contractString;

	public CompContractViolation(String contractString) {
		Assert.isNotNull(contractString);
		this.contractString = contractString;
	}
}
