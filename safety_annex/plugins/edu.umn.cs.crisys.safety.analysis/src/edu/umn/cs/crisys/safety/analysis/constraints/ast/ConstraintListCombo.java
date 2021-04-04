package edu.umn.cs.crisys.safety.analysis.constraints.ast;

import java.util.List;

public class ConstraintListCombo {

	public final Constraint lastConstraint;
	public final List<MistralConstraint> constraintList;

	public ConstraintListCombo(Constraint lastConstraint, List<MistralConstraint> constraintList) {

		this.lastConstraint = lastConstraint;
		this.constraintList = constraintList;

	}
}