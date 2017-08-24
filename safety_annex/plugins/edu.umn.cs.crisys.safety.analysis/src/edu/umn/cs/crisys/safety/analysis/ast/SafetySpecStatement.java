package edu.umn.cs.crisys.safety.analysis.ast;

import java.util.List;
import jkind.lustre.IdExpr;

public class SafetySpecStatement implements SafetyASTElement{
	
	public final String description;
	public final IdExpr id;
	public final List<FaultSubcomponent> faultDefinitions;
	
	public SafetySpecStatement(String description, IdExpr id, List<FaultSubcomponent> faultDefinitions) {
		
		this.description = description;
		this.id = id;
		this.faultDefinitions = jkind.util.Util.safeList(faultDefinitions);
		
	}
	
}
