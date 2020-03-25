package edu.umn.cs.crisys.safety.analysis.soteria.faultTree;

import java.util.List;

import edu.umn.cs.crisys.safety.analysis.SafetyException;
import edu.umn.cs.crisys.safety.analysis.ast.visitors.SoteriaFTAstVisitor;
import jkind.Assert;

public class SoteriaFTNonLeafNode extends SoteriaFTNode {
	public final String propertyName;
	public final String propertyDescription;
	public boolean isRoot = false;

	public SoteriaFTNonLeafNode(String propertyName, String propertyDescription) {
		super(propertyName);
		Assert.isNotNull(propertyName);
		Assert.isNotNull(propertyDescription);
		this.propertyName = propertyName;
		this.propertyDescription = propertyDescription;
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

	@Override
	public float getProbability() {
		throw new SafetyException("Impossible to compute the probability for a general intermediate node");
	}

}
