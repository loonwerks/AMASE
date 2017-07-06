package edu.umn.cs.crisys.safety.analysis.ast;

import jkind.lustre.Expr;

public class FaultInterval {
    public final Expr low;
    public final Expr high;
    
    public FaultInterval(Expr low, Expr high){
        this.low = low;
        this.high = high;
    }
}
