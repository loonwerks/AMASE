package edu.umn.cs.crisys.safety.analysis.ast;

import edu.umn.cs.crisys.safety.analysis.transform.Fault;

public class SafetyPropagation implements SafetyASTElement{
	
	public final Fault srcFault;
	public final Fault destFault;
	
	public SafetyPropagation(Fault srcFault, Fault destFault){
		
		this.srcFault = srcFault;
		this.destFault = destFault;
		
	}
	
}
