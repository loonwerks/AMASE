package edu.umn.cs.crisys.safety.analysis.soteria.faultTree;

import edu.umn.cs.crisys.safety.analysis.ast.visitors.SoteriaFTAstVisitor;

public class SoteriaFTAndNode extends SoteriaFTNonLeafNode {
	public final String nodeOpStr = "PRO";

	public SoteriaFTAndNode(String propertyName, String propertyDescription) {
		super(propertyName, propertyDescription);
	}

	@Override
	public <T> T accept(SoteriaFTAstVisitor<T> visitor) {
		return visitor.visit(this);
	}

}
