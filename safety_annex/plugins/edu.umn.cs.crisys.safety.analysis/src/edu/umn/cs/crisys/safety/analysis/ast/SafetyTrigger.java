package edu.umn.cs.crisys.safety.analysis.ast;

import java.util.List;
import jkind.lustre.Expr;

public class SafetyTrigger implements FaultASTElement{

	public final TriggerCondition tc;
	public final List<Expr> expressions;
	public Expr probability;
	
	public SafetyTrigger(TriggerCondition tc, List<Expr> expr){
		
		this.tc = tc;
		this.expressions = jkind.util.Util.safeList(expr);
		
	}
	
	// Alternative constructor for probabilistic case
	public SafetyTrigger(TriggerCondition tc, List<Expr> expr, Expr probability){
		
		this.tc = tc;
		this.expressions = jkind.util.Util.safeList(expr);
		this.probability = probability;
	}
}
