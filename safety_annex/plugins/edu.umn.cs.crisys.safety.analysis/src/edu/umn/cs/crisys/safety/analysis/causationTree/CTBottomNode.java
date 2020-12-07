package edu.umn.cs.crisys.safety.analysis.causationTree;

import jkind.Assert;
import jkind.lustre.Expr;

public abstract class CTBottomNode extends CTNode {
	public Expr expr;
	public boolean isLeaf = false;

	public CTBottomNode(Expr expr) {
		Assert.isNotNull(expr);
		this.expr = expr;
	}

}
