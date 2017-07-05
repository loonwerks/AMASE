package edu.umn.cs.crisys.safety.analysis.ast;

public class FaultDuration implements FaultASTElement{
	
	public final TemporalConstraint tempConstraint;
	
	public FaultDuration(TemporalConstraint temporalConstraint, FaultInterval interv){
		
		this.tempConstraint = temporalConstraint;
		
	}
	
}
