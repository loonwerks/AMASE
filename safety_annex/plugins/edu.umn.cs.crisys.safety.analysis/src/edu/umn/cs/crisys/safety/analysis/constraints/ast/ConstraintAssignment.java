package edu.umn.cs.crisys.safety.analysis.constraints.ast;

import edu.umn.cs.crisys.safety.analysis.constraints.visitors.ConstraintAstVisitor;
import jkind.Assert;

public class ConstraintAssignment extends MistralConstraint {
	public Constraint leftConstraint;
	public Constraint rightConstraint;

	public ConstraintAssignment(Constraint leftConstraint, Constraint rightConstraint) {
		Assert.isNotNull(leftConstraint);
		Assert.isNotNull(rightConstraint);
		this.leftConstraint = leftConstraint;
		this.rightConstraint = rightConstraint;
	}

	@Override
	public <T> T accept(ConstraintAstVisitor<T> visitor) {
		return visitor.visit(this);
	}
}