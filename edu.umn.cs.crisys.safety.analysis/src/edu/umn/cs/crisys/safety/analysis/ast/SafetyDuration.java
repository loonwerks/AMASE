package edu.umn.cs.crisys.safety.analysis.ast;

import com.rockwellcollins.atc.agree.agree.TimeInterval;

public class SafetyDuration implements SafetyASTElement{
	
	public final TemporalConstraint tempConstraint;
	public final TimeInterval interval;
	
	public SafetyDuration(TemporalConstraint temporalConstraint, TimeInterval interv){
		
		this.tempConstraint = temporalConstraint;
		this.interval = interv;
		
	}
	
}
