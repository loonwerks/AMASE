package edu.umn.cs.crisys.safety.analysis.constraints.ast;

import edu.umn.cs.crisys.safety.analysis.constraints.visitors.ConstraintAstVisitor;

public class IntConstantTermDef extends TermDef {

	public final int val;

	public IntConstantTermDef(String termId, int val) {
		super(termId);
		this.val = val;
		// TODO Auto-generated constructor stub
	}

	@Override
	public <T> T accept(ConstraintAstVisitor<T> visitor) {
		return visitor.visit(this);
	}
}