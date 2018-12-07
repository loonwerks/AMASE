package edu.umn.cs.crisys.safety.analysis.soteria;

import edu.umn.cs.crisys.safety.analysis.ast.visitors.SoteriaAstVisitor;
import jkind.Assert;

public class CompFaultActivation extends SoteriaFormulaElem {
	public final String compName;
	public final String faultName;

	public CompFaultActivation(String compName, String faultName) {
		Assert.isNotNull(compName);
		Assert.isNotNull(faultName);
		this.compName = compName;
		this.faultName = faultName;
	}

	@Override
	public <T> T accept(SoteriaAstVisitor<T> visitor) {
		return visitor.visit(this);
	}
}
