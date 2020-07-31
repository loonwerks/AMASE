package edu.umn.cs.crisys.safety.analysis.faultTree;

import edu.umn.cs.crisys.safety.analysis.ast.visitors.FTAstVisitor;
import jkind.Assert;

public class FTLeafNode extends FTNode {
	public final String compName;
	public final String soteriaFaultName; // this is the fault name used in soteria file (no leading "__")
	public final String lustreFaultName; // this is the fault name appeared in the original lustre code
	public final float failureRate;
	public final float exposureTime;
	public final float probability;
	public final String faultUserName; // this is the fault name specified by the user
	public final String faultUserExplanation; // this is the fault explanation specified by the user

	public FTLeafNode(String compName, String faultName, float failureRate, float exposureTime,
			float probability, String originalFaultName, String faultUserName, String faultUserExplanation) {
		super(faultName);
		Assert.isNotNull(compName);
		Assert.isNotNull(faultName);
		Assert.isNotNull(failureRate);
		Assert.isNotNull(exposureTime);
		Assert.isNotNull(faultUserName);
		Assert.isNotNull(faultUserExplanation);
		this.compName = compName;
		this.soteriaFaultName = faultName;
		this.failureRate = failureRate;
		this.exposureTime = exposureTime;
		this.probability = probability;
		this.lustreFaultName = originalFaultName;
		this.faultUserName = faultUserName;
		this.faultUserExplanation = faultUserExplanation;
	}

	@Override
	public <T> T accept(FTAstVisitor<T> visitor) {
		return visitor.visit(this);
	}

	@Override
	public float getProbability() {
		return (this.probability);
	}

}
