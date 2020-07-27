package edu.umn.cs.crisys.safety.analysis.faultTree;

import edu.umn.cs.crisys.safety.analysis.ast.visitors.FTAstVisitor;

public class FTAndNode extends FTNonLeafNode {
	public final String nodeOpStr = "PRO";

	public FTAndNode(String propertyName, String propertyDescription) {
		super(propertyName, propertyDescription);
	}

	@Override
	public <T> T accept(FTAstVisitor<T> visitor) {
		return visitor.visit(this);
	}

	@Override
	/** Calculates probability of faults by summation, assuming event probabilistic independence */
	public float getProbability() {
		float probability = 1;
		for (FTNode childNode : this.childNodes.values()) {
			probability *= childNode.getProbability();
		}
		return probability;
	}

}
