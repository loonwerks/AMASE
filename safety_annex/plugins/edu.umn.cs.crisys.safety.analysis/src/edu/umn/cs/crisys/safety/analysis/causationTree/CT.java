package edu.umn.cs.crisys.safety.analysis.causationTree;

import edu.umn.cs.crisys.safety.analysis.ast.visitors.CTAstVisitor;

public class CT extends CTAst {
	public CTNode rootNode = null;

	public void setRootNode(CTNode rootNode) {
		this.rootNode = rootNode;
	}

	@Override
	public <T> T accept(CTAstVisitor<T> visitor) {
		return visitor.visit(this);
	}
}
