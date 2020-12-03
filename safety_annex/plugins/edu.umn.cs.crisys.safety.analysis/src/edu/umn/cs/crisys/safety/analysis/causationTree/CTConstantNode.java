package edu.umn.cs.crisys.safety.analysis.causationTree;

import edu.umn.cs.crisys.safety.analysis.ast.visitors.CTAstVisitor;
import jkind.lustre.Expr;

public class CTConstantNode extends CTBottomNode {

	public CTConstantNode(Expr expr) {
		super(expr);
		// TODO Auto-generated constructor stub
	}

	@Override
	public <T> T accept(CTAstVisitor<T> visitor) {
		return visitor.visit(this);
	}

}
