package edu.umn.cs.crisys.safety.analysis.constraints.ast.expr;

import edu.umn.cs.crisys.safety.analysis.constraints.visitors.ConstraintAstVisitor;
import jkind.Assert;

public class ConstraintUnaryExpr extends ConstraintExpr {
	public final ConstraintUnaryOp op;
	public ConstraintExpr expr;

	public ConstraintUnaryExpr(ConstraintUnaryOp op, ConstraintExpr expr) {
		Assert.isNotNull(op);
		Assert.isNotNull(expr);
		this.op = op;
		this.expr = expr;
	}

	@Override
	public <T> T accept(ConstraintAstVisitor<T> visitor) {
		return visitor.visit(this);
	}
}
