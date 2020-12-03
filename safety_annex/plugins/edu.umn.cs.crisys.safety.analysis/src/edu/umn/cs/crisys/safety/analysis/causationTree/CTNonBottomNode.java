package edu.umn.cs.crisys.safety.analysis.causationTree;

import jkind.Assert;

public abstract class CTNonBottomNode extends CTNode {
	public final CTNodeBinaryOp op;

	public CTNonBottomNode(CTNodeBinaryOp op) {
		Assert.isNotNull(op);
		this.op = op;
	}

}
