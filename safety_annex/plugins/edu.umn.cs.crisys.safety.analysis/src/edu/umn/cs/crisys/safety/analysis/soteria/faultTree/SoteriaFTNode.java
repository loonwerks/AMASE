package edu.umn.cs.crisys.safety.analysis.soteria.faultTree;

import jkind.Assert;

public abstract class SoteriaFTNode extends SoteriaFTAst {
	public final String nodeName;

	public SoteriaFTNode(String nodeName) {
		Assert.isNotNull(nodeName);
		this.nodeName = nodeName;
	}

}
