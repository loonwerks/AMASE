package edu.umn.cs.crisys.safety.analysis.soteria;

import jkind.Assert;

public class SoteriaFault {
	public final String faultName;
	public final float failureRate;
	public final float exposureTime;

	public SoteriaFault(String faultName, float failureRate, float exposureTime) {
		Assert.isNotNull(faultName);
		Assert.isNotNull(failureRate);
		Assert.isNotNull(exposureTime);
		this.faultName = faultName;
		this.failureRate = failureRate;
		this.exposureTime = exposureTime;
	}

}
