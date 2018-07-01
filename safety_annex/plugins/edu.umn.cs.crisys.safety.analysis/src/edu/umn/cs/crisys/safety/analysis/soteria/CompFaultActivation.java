package edu.umn.cs.crisys.safety.analysis.soteria;

import jkind.Assert;

public class CompFaultActivation extends SoteriaFormulaElement {
	public final String faultName;

	public CompFaultActivation(String faultName) {
		Assert.isNotNull(faultName);
		this.faultName = faultName;
	}
}
