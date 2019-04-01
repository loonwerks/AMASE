package edu.umn.cs.crisys.safety.analysis.soteria.faultTree;

import java.util.HashMap;

import jkind.Assert;

public abstract class SoteriaFTNode extends SoteriaFTAst {
	public final String nodeName;
	// public SoteriaFTNode parentNode = null;
	public boolean resolved = false;
	public boolean nodeValue = true;
	public HashMap<String, SoteriaFTNode> childNodes = new HashMap<>();


	public SoteriaFTNode(String nodeName) {
		Assert.isNotNull(nodeName);
		this.nodeName = nodeName;
	}

//	public void addParentNode(SoteriaFTNode parentNode) {
//		this.parentNode = parentNode;
//	}

}
