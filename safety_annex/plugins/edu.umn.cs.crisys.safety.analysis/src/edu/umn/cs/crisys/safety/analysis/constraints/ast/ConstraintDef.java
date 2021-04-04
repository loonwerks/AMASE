package edu.umn.cs.crisys.safety.analysis.constraints.ast;

import jkind.Assert;

public abstract class ConstraintDef extends MistralConstraint {
	public final String constraintId;

	public ConstraintDef(String constraintId) {
		Assert.isNotNull(constraintId);
		this.constraintId = constraintId;
	}
}