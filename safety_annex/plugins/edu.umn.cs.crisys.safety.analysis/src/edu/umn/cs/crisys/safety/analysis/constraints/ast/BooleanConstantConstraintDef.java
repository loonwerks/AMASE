package edu.umn.cs.crisys.safety.analysis.constraints.ast;

import edu.umn.cs.crisys.safety.analysis.constraints.visitors.ConstraintAstVisitor;

public class BooleanConstantConstraintDef extends ConstantConstraintDef {

	public final Boolean val;

	public BooleanConstantConstraintDef(String constraintId, boolean val) {
		super(constraintId);
		this.val = val;
		// TODO Auto-generated constructor stub
	}

	@Override
	public <T> T accept(ConstraintAstVisitor<T> visitor) {
		return visitor.visit(this);
	}
}