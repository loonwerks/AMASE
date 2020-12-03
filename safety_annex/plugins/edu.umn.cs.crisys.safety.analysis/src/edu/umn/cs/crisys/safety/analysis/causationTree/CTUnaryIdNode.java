package edu.umn.cs.crisys.safety.analysis.causationTree;

import edu.umn.cs.crisys.safety.analysis.ast.visitors.CTAstVisitor;
import jkind.lustre.Expr;

public class CTUnaryIdNode extends CTBottomNode {

	public final CTNodeUnaryOp op;

	public CTUnaryIdNode(CTNodeUnaryOp op, Expr expr) {
		super(expr);
		this.op = op;
		// TODO Auto-generated constructor stub
	}

	@Override
	public <T> T accept(CTAstVisitor<T> visitor) {
		return visitor.visit(this);
	}
}
