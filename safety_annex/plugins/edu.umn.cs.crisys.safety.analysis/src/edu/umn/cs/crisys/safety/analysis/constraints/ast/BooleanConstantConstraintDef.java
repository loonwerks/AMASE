package edu.umn.cs.crisys.safety.analysis.constraints.ast;

import edu.umn.cs.crisys.safety.analysis.constraints.visitors.ConstraintAstVisitor;
import jkind.Assert;

public class BooleanConstantConstraintDef extends ConstraintDef {

	public final Boolean val;

	public BooleanConstantConstraintDef(String constraintId, boolean val) {
		super(constraintId);
		Assert.isNotNull(val);
		this.val = val;
	}

	@Override
	public <T> T accept(ConstraintAstVisitor<T> visitor) {
		return visitor.visit(this);
	}
}