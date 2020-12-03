package edu.umn.cs.crisys.safety.analysis.causationTree;

import edu.umn.cs.crisys.safety.analysis.ast.visitors.CTAstVisitor;
import jkind.lustre.Expr;

public class CTBinaryIdNode extends CTBottomNode {

	public CTBinaryIdNode(Expr expr) {
		super(expr);
	}

	@Override
	public <T> T accept(CTAstVisitor<T> visitor) {
		return visitor.visit(this);
	}

}
