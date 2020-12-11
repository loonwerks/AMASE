package edu.umn.cs.crisys.safety.analysis.causationTree;

import edu.umn.cs.crisys.safety.analysis.ast.visitors.CTAstVisitor;
import edu.umn.cs.crisys.safety.util.AgreeUtil;
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

	@Override
	public Boolean equalsNode(CTNode node) {
		Boolean result = false;
		if (node instanceof CTConstantNode) {
			CTConstantNode newNode = (CTConstantNode) node;
			if (newNode.isFailure == (this.isFailure)) {
				if (newNode.isLeaf == this.isLeaf) {
					if (AgreeUtil.exprsEqual(newNode.expr, this.expr)) {
						if (newNode.idSet.equals(this.idSet)) {
							if (newNode.childNodes.equals(this.childNodes)) {
								result = true;
							}
						}
					}
				}
			}
		}
		return result;
	}

}
