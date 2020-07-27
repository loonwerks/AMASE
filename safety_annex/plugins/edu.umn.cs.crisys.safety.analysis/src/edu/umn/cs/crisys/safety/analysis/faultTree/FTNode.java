package edu.umn.cs.crisys.safety.analysis.faultTree;

import java.util.HashMap;

import jkind.Assert;

public abstract class FTNode extends FTAst {
	public final String nodeName;
	// public SoteriaFTNode parentNode = null;
	public boolean resolved = false;
	public boolean nodeValue = true;
	public HashMap<String, FTNode> childNodes = new HashMap<>();


	public FTNode(String nodeName) {
		Assert.isNotNull(nodeName);
		this.nodeName = nodeName;
	}

//	public void addParentNode(SoteriaFTNode parentNode) {
//		this.parentNode = parentNode;
//	}

	public abstract float getProbability();
}
