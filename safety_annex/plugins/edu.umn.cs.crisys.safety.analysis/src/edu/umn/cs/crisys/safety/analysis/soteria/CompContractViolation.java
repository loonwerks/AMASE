package edu.umn.cs.crisys.safety.analysis.soteria;

import edu.umn.cs.crisys.safety.analysis.ast.visitors.SoteriaAstVisitor;
import jkind.Assert;


public class CompContractViolation extends SoteriaFormulaElem {
	public final String contractString;

	public CompContractViolation(String contractString) {
		Assert.isNotNull(contractString);
		this.contractString = contractString;
	}

	@Override
	public <T> T accept(SoteriaAstVisitor<T> visitor) {
		return visitor.visit(this);
	}

}
