package edu.umn.cs.crisys.safety.analysis.soteria.faultTree;

import java.util.List;

import edu.umn.cs.crisys.safety.analysis.ast.visitors.SoteriaFTAstVisitor;
import jkind.Assert;

public class SoteriaFTNonLeafNode extends SoteriaFTNode {
	public final String propertyName;
	public boolean isRoot = false;

	public SoteriaFTNonLeafNode(String propertyName) {
		super(propertyName);
		Assert.isNotNull(propertyName);
		this.propertyName = propertyName;
	}

	public void addChildNode(String nodeName, SoteriaFTNode childNode) {
		childNodes.put(nodeName, childNode);
	}

	public void addChildNodes(List<SoteriaFTNode> childNodesToAdd) {
		for (SoteriaFTNode child : childNodesToAdd) {
			childNodes.put(child.nodeName, child);
		}
	}

	public void replaceChildNode(String nodeName, SoteriaFTNode childNode) {
		childNodes.put(nodeName, childNode);
	}

	public void removeChildNode(String nodeName) {
		childNodes.remove(nodeName);
	}

	public void removeChildNodes(List<SoteriaFTNode> childNodesToAdd) {
		for (SoteriaFTNode child : childNodesToAdd) {
			childNodes.remove(child.nodeName);
		}
	}

	@Override
	public <T> T accept(SoteriaFTAstVisitor<T> visitor) {
		return visitor.visit(this);
	}

	public void setRoot() {
		isRoot = true;
	}

}
