package edu.umn.cs.crisys.safety.analysis.constraints.ast;

import java.util.List;

import jkind.Assert;

public class ConstraintListCombo {

	public final MistralConstraint lastConstraint;
	public final List<MistralConstraint> constraintList;

	public ConstraintListCombo(MistralConstraint lastConstraint, List<MistralConstraint> constraintList) {
		Assert.isNotNull(lastConstraint);
		Assert.isNotNull(constraintList);
		this.lastConstraint = lastConstraint;
		this.constraintList = constraintList;

	}
}