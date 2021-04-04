package edu.umn.cs.crisys.safety.analysis.constraints.ast;

import edu.umn.cs.crisys.safety.analysis.constraints.visitors.ConstraintAstVisitor;
import jkind.Assert;

public class Constraint extends MistralConstraint {
	public final String constraintId;

	public Constraint(String constraintId) {
		Assert.isNotNull(constraintId);
		this.constraintId = constraintId;
	}

	@Override
	public <T> T accept(ConstraintAstVisitor<T> visitor) {
		return visitor.visit(this);
	}
}