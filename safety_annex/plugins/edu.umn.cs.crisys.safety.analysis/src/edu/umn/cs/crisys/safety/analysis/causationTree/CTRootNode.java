package edu.umn.cs.crisys.safety.analysis.causationTree;

import edu.umn.cs.crisys.safety.analysis.ast.visitors.CTAstVisitor;

public class CTRootNode extends CTNode {

	public CTRootNode(String nodeName) {
		super(nodeName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public <T> T accept(CTAstVisitor<T> visitor) {
		// TODO Auto-generated method stub
		return null;
	}

}
