package edu.umn.cs.crisys.safety.analysis.ast;

public class FaultDuration implements FaultASTElement{
	
	public final TemporalConstraint tempConstraint;
	public final FaultInterval interval;
	
	public FaultDuration(TemporalConstraint temporalConstraint, FaultInterval interv){
		
		this.tempConstraint = temporalConstraint;
		this.interval = interv;
		
	}
	
}
