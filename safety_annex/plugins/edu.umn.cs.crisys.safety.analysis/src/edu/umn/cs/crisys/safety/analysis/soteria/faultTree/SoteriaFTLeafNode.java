package edu.umn.cs.crisys.safety.analysis.soteria.faultTree;

import edu.umn.cs.crisys.safety.analysis.ast.visitors.SoteriaFTAstVisitor;
import jkind.Assert;

public class SoteriaFTLeafNode extends SoteriaFTNode {
	public final String compName;
	public final String faultName;
	public final float failureRate;
	public final float exposureTime;

	public SoteriaFTLeafNode(String compName, String faultName, float failureRate, float exposureTime) {
		super(faultName);
		Assert.isNotNull(compName);
		Assert.isNotNull(faultName);
		Assert.isNotNull(failureRate);
		Assert.isNotNull(exposureTime);
		this.compName = compName;
		this.faultName = faultName;
		this.failureRate = failureRate;
		this.exposureTime = exposureTime;
	}

	@Override
	public <T> T accept(SoteriaFTAstVisitor<T> visitor) {
		return visitor.visit(this);
	}

}
