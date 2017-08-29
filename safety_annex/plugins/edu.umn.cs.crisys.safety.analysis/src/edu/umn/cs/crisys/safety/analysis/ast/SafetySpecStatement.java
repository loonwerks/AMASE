package edu.umn.cs.crisys.safety.analysis.ast;

import java.util.List;
import jkind.lustre.IdExpr;

public class SafetySpecStatement implements SafetyASTElement{
	
	public final String description;
	public final IdExpr id;
	public final List<FaultSubcomponent> faultSubcomponents;
	
	public SafetySpecStatement(String description, IdExpr id, List<FaultSubcomponent> faultSubcomponents) {
		
		this.description = description;
		this.id = id;
		this.faultSubcomponents = jkind.util.Util.safeList(faultSubcomponents);
		
	}
	
}
