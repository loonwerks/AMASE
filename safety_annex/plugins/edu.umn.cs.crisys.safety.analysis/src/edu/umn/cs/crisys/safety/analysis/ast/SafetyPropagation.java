package edu.umn.cs.crisys.safety.analysis.ast;

import edu.umn.cs.crisys.safety.analysis.transform.BaseFault;

public class SafetyPropagation implements SafetyASTElement{
	
	public final BaseFault srcFault;
	public final BaseFault destFault;
	
	public SafetyPropagation(BaseFault srcFault, BaseFault destFault){
		
		this.srcFault = srcFault;
		this.destFault = destFault;
		
	}
	
}
