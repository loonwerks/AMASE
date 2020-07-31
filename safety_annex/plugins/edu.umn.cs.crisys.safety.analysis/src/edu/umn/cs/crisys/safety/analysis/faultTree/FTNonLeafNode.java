package edu.umn.cs.crisys.safety.analysis.faultTree;

import java.util.List;

import edu.umn.cs.crisys.safety.analysis.SafetyException;
import edu.umn.cs.crisys.safety.analysis.ast.visitors.FTAstVisitor;
import jkind.Assert;

public class FTNonLeafNode extends FTNode {
	public final String propertyName;
	public final String propertyDescription;
	public boolean isRoot = false;

	public FTNonLeafNode(String propertyName, String propertyDescription) {
		super(propertyName);
		Assert.isNotNull(propertyName);
		Assert.isNotNull(propertyDescription);
		this.propertyName = propertyName;
		this.propertyDescription = propertyDescription;
	}

	public void addChildNode(String nodeName, FTNode childNode) {
		childNodes.put(nodeName, childNode);
	}

	public void addChildNodes(List<FTNode> childNodesToAdd) {
		for (FTNode child : childNodesToAdd) {
			childNodes.put(child.nodeName, child);
		}
	}

	public void replaceChildNode(String nodeName, FTNode childNode) {
		childNodes.put(nodeName, childNode);
	}

	public void removeChildNode(String nodeName) {
		childNodes.remove(nodeName);
	}

	public void removeChildNodes(List<FTNode> childNodesToAdd) {
		for (FTNode child : childNodesToAdd) {
			childNodes.remove(child.nodeName);
		}
	}

	@Override
	public <T> T accept(FTAstVisitor<T> visitor) {
		return visitor.visit(this);
	}

	public void setRoot() {
		isRoot = true;
	}

	@Override
	public float getProbability() {
		throw new SafetyException("Impossible to compute the probability for a general intermediate node");
	}

}
