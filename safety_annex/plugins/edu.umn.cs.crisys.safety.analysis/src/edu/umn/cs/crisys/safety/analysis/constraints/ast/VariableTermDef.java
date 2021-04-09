package edu.umn.cs.crisys.safety.analysis.constraints.ast;

import edu.umn.cs.crisys.safety.analysis.constraints.visitors.ConstraintAstVisitor;
import jkind.Assert;

public class VariableTermDef extends TermDef {
	public final String varId;

	public VariableTermDef(String termId, String varId) {
		super(termId);
		Assert.isNotNull(varId);
		this.varId = varId;
	}

	@Override
	public <T> T accept(ConstraintAstVisitor<T> visitor) {
		return visitor.visit(this);
	}
}