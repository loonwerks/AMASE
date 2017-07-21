package edu.umn.cs.crisys.safety.analysis.ast;

import java.util.List;
import jkind.lustre.IdExpr;

public class FaultSpecStatement implements FaultASTElement{
	
	public final String description;
	public final IdExpr id;
	public final List<FaultSubcomponent> faultDefinitions;
	
	public FaultSpecStatement(String description, IdExpr id, List<FaultSubcomponent> faultDefinitions) {
		
		this.description = description;
		this.id = id;
		this.faultDefinitions = jkind.util.Util.safeList(faultDefinitions);
		
	}
	
}
