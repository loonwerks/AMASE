package edu.umn.cs.crisys.safety.analysis.ast;

import jkind.lustre.Expr;

public class SafetyInterval implements FaultASTElement{

	public final Expr low;
    public final Expr high;
    
    public SafetyInterval(Expr low, Expr high){
        this.low = low;
        this.high = high;
    }
}
