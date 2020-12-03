package edu.umn.cs.crisys.safety.analysis.causationTree;

import edu.umn.cs.crisys.safety.analysis.ast.visitors.CTAstVisitor;
import jkind.lustre.Expr;

public class CTIdNode extends CTBottomNode {
	public String idName = "";

	public CTIdNode(Expr expr, String id) {
		super(expr);
		this.idName = id;
		this.idSet.add(id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public <T> T accept(CTAstVisitor<T> visitor) {
		return visitor.visit(this);
	}

}
