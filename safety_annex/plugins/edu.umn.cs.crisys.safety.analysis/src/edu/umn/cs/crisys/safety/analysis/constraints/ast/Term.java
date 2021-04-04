package edu.umn.cs.crisys.safety.analysis.constraints.ast;

import edu.umn.cs.crisys.safety.analysis.constraints.visitors.ConstraintAstVisitor;
import jkind.Assert;

public class Term extends MistralConstraint {
	public final String termId;

	public Term(String termId) {
		Assert.isNotNull(termId);
		this.termId = termId;
	}

	@Override
	public <T> T accept(ConstraintAstVisitor<T> visitor) {
		return visitor.visit(this);
	}
}