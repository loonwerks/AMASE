package edu.umn.cs.crisys.safety.analysis.soteria.faultTree;

import java.util.HashMap;

import edu.umn.cs.crisys.safety.analysis.ast.visitors.SoteriaFTAstVisitor;
import jkind.Assert;

public class SoteriaFTNonLeafNode extends SoteriaFTNode {
	public final String propertyName;
	public HashMap<String, SoteriaFTNode> childNodes = new HashMap<>();

	public SoteriaFTNonLeafNode(String propertyName) {
		super(propertyName);
		Assert.isNotNull(propertyName);
		this.propertyName = propertyName;
	}

	public void addChildNode(String nodeName, SoteriaFTNode childNode) {
		childNodes.put(nodeName, childNode);
	}

	@Override
	public <T> T accept(SoteriaFTAstVisitor<T> visitor) {
		return visitor.visit(this);
	}

}
