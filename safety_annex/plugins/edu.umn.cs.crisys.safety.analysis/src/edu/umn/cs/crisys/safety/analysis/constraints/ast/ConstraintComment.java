package edu.umn.cs.crisys.safety.analysis.constraints.ast;

import edu.umn.cs.crisys.safety.analysis.constraints.visitors.ConstraintAstVisitor;
import jkind.Assert;

public class ConstraintComment extends MistralConstraint {
	public String codeStr;

	public ConstraintComment(String codeStr) {
		Assert.isNotNull(codeStr);
		this.codeStr = codeStr;
	}

	@Override
	public <T> T accept(ConstraintAstVisitor<T> visitor) {
		return visitor.visit(this);
	}
}