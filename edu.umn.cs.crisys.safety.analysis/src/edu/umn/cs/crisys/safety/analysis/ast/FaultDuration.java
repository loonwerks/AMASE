package edu.umn.cs.crisys.safety.analysis.ast;

public class FaultDuration implements FaultASTElement{
	
	public final String temporalConstraint;
	
	public FaultDuration(String temporalConstraint, FaultInterval interv){
		
		this.temporalConstraint = temporalConstraint;
		
	}
	
}
