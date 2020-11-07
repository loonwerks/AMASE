package edu.umn.cs.crisys.safety.analysis.causationTree;

import java.util.HashMap;
import java.util.List;

import jkind.Assert;

public abstract class CTNode extends CTAst {
	public final String nodeName;
	public HashMap<String, CTNode> childNodes = new HashMap<>();


	public CTNode(String nodeName) {
		Assert.isNotNull(nodeName);
		this.nodeName = nodeName;
	}

	public void addChildNode(String nodeName, CTNode childNode) {
		childNodes.put(nodeName, childNode);
	}

	public void addChildNodes(List<CTNode> childNodesToAdd) {
		for (CTNode child : childNodesToAdd) {
			childNodes.put(child.nodeName, child);
		}
	}

}
