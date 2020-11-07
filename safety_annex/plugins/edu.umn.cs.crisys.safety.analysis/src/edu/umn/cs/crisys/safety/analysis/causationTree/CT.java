package edu.umn.cs.crisys.safety.analysis.causationTree;

import java.util.HashMap;

import edu.umn.cs.crisys.safety.analysis.ast.visitors.CTAstVisitor;

public class CT extends CTAst {
	public HashMap<String, CTNode> rootNodes = new HashMap<>();

	public void addRootNode(String nodeName, CTNode rootNode) {
		rootNodes.put(nodeName, rootNode);
	}

	@Override
	public <T> T accept(CTAstVisitor<T> visitor) {
		return visitor.visit(this);
	}
}
