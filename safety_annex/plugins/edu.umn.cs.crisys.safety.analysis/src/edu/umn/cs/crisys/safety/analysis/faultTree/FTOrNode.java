package edu.umn.cs.crisys.safety.analysis.faultTree;

import edu.umn.cs.crisys.safety.analysis.ast.visitors.FTAstVisitor;

public class FTOrNode extends FTNonLeafNode {
	public final String nodeOpStr = "SUM";

	public FTOrNode(String propertyName, String propertyDescription) {
		super(propertyName, propertyDescription);
	}

	@Override
	public <T> T accept(FTAstVisitor<T> visitor) {
		return visitor.visit(this);
	}

	@Override
	/** Calculates probability of faults by summation, assuming negligible joint probability */
	public float getProbability() {
		float probability = 0;
		for (FTNode childNode : this.childNodes.values()) {
			probability += childNode.getProbability();
		}
		return probability;
	}

}
