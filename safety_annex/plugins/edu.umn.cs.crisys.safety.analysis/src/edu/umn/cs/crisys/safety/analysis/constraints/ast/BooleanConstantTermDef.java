package edu.umn.cs.crisys.safety.analysis.constraints.ast;

import edu.umn.cs.crisys.safety.analysis.constraints.visitors.ConstraintAstVisitor;

public class BooleanConstantTermDef extends ConstantTermDef {

	public final Boolean val;

	public BooleanConstantTermDef(String termId, boolean val) {
		super(termId);
		this.val = val;
		// TODO Auto-generated constructor stub
	}

	@Override
	public <T> T accept(ConstraintAstVisitor<T> visitor) {
		return visitor.visit(this);
	}
}