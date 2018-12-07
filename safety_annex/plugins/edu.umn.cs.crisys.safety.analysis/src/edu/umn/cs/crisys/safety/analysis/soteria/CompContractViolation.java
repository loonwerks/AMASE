package edu.umn.cs.crisys.safety.analysis.soteria;

import edu.umn.cs.crisys.safety.analysis.ast.visitors.SoteriaAstVisitor;
import jkind.Assert;


public class CompContractViolation extends SoteriaFormulaElem {
	public final String compName;
	public final String contractString;
	public final String contractViolationFaultStr = "contract violation";

	public CompContractViolation(String compName, String contractString) {
		Assert.isNotNull(compName);
		Assert.isNotNull(contractString);
		this.compName = compName;
		this.contractString = contractString;
	}

	@Override
	public <T> T accept(SoteriaAstVisitor<T> visitor) {
		return visitor.visit(this);
	}

}
