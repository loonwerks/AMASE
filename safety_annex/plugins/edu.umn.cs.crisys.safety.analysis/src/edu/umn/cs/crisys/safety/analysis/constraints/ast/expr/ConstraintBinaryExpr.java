package edu.umn.cs.crisys.safety.analysis.constraints.ast.expr;

import edu.umn.cs.crisys.safety.analysis.constraints.visitors.ConstraintAstVisitor;
import jkind.Assert;

public class ConstraintBinaryExpr extends ConstraintExpr {
	public ConstraintExpr left;
	public final ConstraintBinaryOp op;
	public ConstraintExpr right;

	public ConstraintBinaryExpr(ConstraintExpr left, ConstraintBinaryOp op, ConstraintExpr right) {
		Assert.isNotNull(left);
		Assert.isNotNull(op);
		Assert.isNotNull(right);
		this.left = left;
		this.op = op;
		this.right = right;
	}

	@Override
	public <T> T accept(ConstraintAstVisitor<T> visitor) {
		return visitor.visit(this);
	}

}
