package edu.umn.cs.crisys.safety.analysis.constraints.ast;

import java.util.List;

import jkind.Assert;

public class TermListCombo {

	public final Term lastTerm;
	public final List<MistralConstraint> constraintList;

	public TermListCombo(Term lastTerm, List<MistralConstraint> constraintList) {
		Assert.isNotNull(lastTerm);
		Assert.isNotNull(constraintList);
		this.lastTerm = lastTerm;
		this.constraintList = constraintList;

	}
}