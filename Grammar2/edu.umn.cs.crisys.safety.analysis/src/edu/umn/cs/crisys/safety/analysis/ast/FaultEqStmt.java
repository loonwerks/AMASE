package edu.umn.cs.crisys.safety.analysis.ast;

import java.util.List;
import org.osate.aadl2.NamedElement;
import jkind.lustre.Expr;

public class FaultEqStmt implements FaultASTElement{
	
	public final List<NamedElement> arg;
	public Expr expr;
	public List<Expr> exprList;
	public FaultInterval interval;
	
	// This constructor is for the case when we have complete nondeterminism
	public FaultEqStmt(List<NamedElement> arg){
		
		this.arg = arg;
	}
	
	// This is the case when we are in an EQ statement
	public FaultEqStmt(List<NamedElement> arg, Expr expr){
		
		this.arg = arg;
		this.expr = expr;
	}
	
	// This is the case when we have a SETEQ 
	public FaultEqStmt(List<NamedElement> arg, List<Expr> exprList){
		
		this.arg = arg;
		this.exprList = exprList;
		
	}
	
	// And this is the case of an INTERVALEQ
	public FaultEqStmt(List<NamedElement> arg, FaultInterval interval){
		
		this.arg = arg;
		this.interval = interval;
		
	}
	
}
