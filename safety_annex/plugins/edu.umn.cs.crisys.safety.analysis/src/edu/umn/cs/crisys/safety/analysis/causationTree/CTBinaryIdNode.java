package edu.umn.cs.crisys.safety.analysis.causationTree;

import edu.umn.cs.crisys.safety.analysis.ast.visitors.CTAstVisitor;
import edu.umn.cs.crisys.safety.util.AgreeUtil;
import jkind.lustre.Expr;

public class CTBinaryIdNode extends CTBottomNode {

	public CTBinaryIdNode(Expr expr) {
		super(expr);
	}

	@Override
	public <T> T accept(CTAstVisitor<T> visitor) {
		return visitor.visit(this);
	}

	@Override
	public Boolean equalsNode(CTNode node) {
		Boolean result = false;
		if (node instanceof CTBinaryIdNode) {
			CTBinaryIdNode newNode = (CTBinaryIdNode) node;
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
