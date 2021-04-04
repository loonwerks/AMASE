package edu.umn.cs.crisys.safety.analysis.constraints.ast;

import edu.umn.cs.crisys.safety.analysis.constraints.ast.expr.ConstraintExpr;
import edu.umn.cs.crisys.safety.analysis.constraints.visitors.ConstraintAstVisitor;
import jkind.Assert;

public class ExprConstraintDef extends ConstraintDef {

	public ConstraintExpr expr;

	public ExprConstraintDef(String constraintId, ConstraintExpr expr) {
		super(constraintId);
		Assert.isNotNull(expr);
		this.expr = expr;
	}

	@Override
	public <T> T accept(ConstraintAstVisitor<T> visitor) {
		return visitor.visit(this);
	}

}