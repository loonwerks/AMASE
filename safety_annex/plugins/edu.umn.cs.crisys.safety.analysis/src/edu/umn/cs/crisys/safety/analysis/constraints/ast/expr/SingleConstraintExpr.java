package edu.umn.cs.crisys.safety.analysis.constraints.ast.expr;

import edu.umn.cs.crisys.safety.analysis.constraints.ast.Constraint;
import edu.umn.cs.crisys.safety.analysis.constraints.visitors.ConstraintAstVisitor;
import jkind.Assert;

public class SingleConstraintExpr extends ConstraintExpr {
	public Constraint constraint;

	public SingleConstraintExpr(Constraint constraint) {
		Assert.isNotNull(constraint);
		this.constraint = constraint;
	}

	@Override
	public <T> T accept(ConstraintAstVisitor<T> visitor) {
		return visitor.visit(this);
	}

}
