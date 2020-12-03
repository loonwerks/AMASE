package edu.umn.cs.crisys.safety.analysis.causationTree;

import edu.umn.cs.crisys.safety.analysis.ast.visitors.CTAstVisitor;

public class CTAndNode extends CTNonBottomNode {

	public CTAndNode(CTNodeBinaryOp op) {
		super(op);
		// TODO Auto-generated constructor stub
	}

	@Override
	public <T> T accept(CTAstVisitor<T> visitor) {
		return visitor.visit(this);
	}

}
