package edu.umn.cs.crisys.safety.analysis.soteria;

import jkind.Assert;

public class CompFaultActivation implements SoteriaFormulaElem {
	public final String faultName;

	public CompFaultActivation(String faultName) {
		Assert.isNotNull(faultName);
		this.faultName = faultName;
	}
}
