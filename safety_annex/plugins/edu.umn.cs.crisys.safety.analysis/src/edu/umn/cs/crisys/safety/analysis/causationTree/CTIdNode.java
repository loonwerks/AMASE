package edu.umn.cs.crisys.safety.analysis.causationTree;

import edu.umn.cs.crisys.safety.analysis.ast.visitors.CTAstVisitor;
import edu.umn.cs.crisys.safety.analysis.transform.Fault;
import edu.umn.cs.crisys.safety.util.AgreeUtil;
import jkind.lustre.Expr;

public class CTIdNode extends CTBottomNode {
	public String idName = "";
	public Fault fault = null;

	public CTIdNode(Expr expr, String id) {
		super(expr);
		this.idName = id;
		this.idSet.add(id);
	}

	public void setFault(Fault fault) {
		this.fault = fault;
	}

	public Fault getFault() {
		return fault;
	}

	@Override
	public <T> T accept(CTAstVisitor<T> visitor) {
		return visitor.visit(this);
	}

	@Override
	public Boolean equalsNode(CTNode node) {
		Boolean result = false;
		if (node instanceof CTIdNode) {
			CTIdNode newNode = (CTIdNode) node;
			if (newNode.idName.equals(this.idName)) {
				if (this.fault != null) {
					if (newNode.fault == null) {
						return result;
					} else {
						if (!newNode.fault.equals(this.fault)) {
							return result;
						}
					}
				}
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
		}
		return result;
	}

}
