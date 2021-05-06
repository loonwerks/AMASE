package edu.umn.cs.crisys.safety.analysis.constraints.ast;

import edu.umn.cs.crisys.safety.analysis.constraints.visitors.ConstraintAstVisitor;
import jkind.Assert;

public class TermAssignment extends MistralConstraint {
	public Term leftTerm;
	public Term rightTerm;

	public TermAssignment(Term leftTerm, Term rightTerm) {
		Assert.isNotNull(leftTerm);
		Assert.isNotNull(rightTerm);
		this.leftTerm = leftTerm;
		this.rightTerm = rightTerm;
	}

	@Override
	public <T> T accept(ConstraintAstVisitor<T> visitor) {
		return visitor.visit(this);
	}

}