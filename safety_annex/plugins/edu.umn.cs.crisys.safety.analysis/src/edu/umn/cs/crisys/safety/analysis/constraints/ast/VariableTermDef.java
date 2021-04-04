package edu.umn.cs.crisys.safety.analysis.constraints.ast;

import edu.umn.cs.crisys.safety.analysis.constraints.visitors.ConstraintAstVisitor;

public class VariableTermDef extends TermDef {
	public final String varId;

	public VariableTermDef(String termId, String varId) {
		super(termId);
		this.varId = varId;
	}

	@Override
	public <T> T accept(ConstraintAstVisitor<T> visitor) {
		return visitor.visit(this);
	}
}