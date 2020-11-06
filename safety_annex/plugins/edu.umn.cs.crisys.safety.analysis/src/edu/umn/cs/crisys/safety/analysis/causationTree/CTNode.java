package edu.umn.cs.crisys.safety.analysis.causationTree;

import java.util.HashMap;

import jkind.Assert;

public abstract class CTNode extends CTAst {
	public final String nodeName;
//	// public FTNode parentNode = null;
//	public boolean resolved = false;
//	public boolean nodeValue = true;
//	public HashMap<String, CausationTreeNode> childNodes = new HashMap<>();
	public HashMap<String, CTNode> childNodes = new HashMap<>();

	public CTNode(String nodeName) {
		Assert.isNotNull(nodeName);
		this.nodeName = nodeName;
	}

//	public void addParentNode(FTNode parentNode) {
//		this.parentNode = parentNode;
//	}

//	public abstract float getProbability();
}
