package edu.umn.cs.crisys.safety.analysis.ast;

import java.util.List;
import org.osate.aadl2.NamedElement;
import jkind.lustre.Expr;

public class SafetyEqStatement implements FaultASTElement{
	public final List<NamedElement> arg;
	public Expr expr;
	public List<Expr> exprList;
	public SafetyInterval interval;
	
	// This constructor is for the case when we have complete nondeterminism
	public SafetyEqStatement(List<NamedElement> arg){
		
		this.arg = arg;
	}
	
	// This is the case when we are in an EQ statement
	public SafetyEqStatement(List<NamedElement> arg, Expr expr){
		
		this.arg = arg;
		this.expr = expr;
	}
	
	// This is the case when we have a SETEQ 
	public SafetyEqStatement(List<NamedElement> arg, List<Expr> exprList){
		
		this.arg = arg;
		this.exprList = exprList;
		
	}
	
	// And this is the case of an INTERVALEQ
	public SafetyEqStatement(List<NamedElement> arg, SafetyInterval interval){
		
		this.arg = arg;
		this.interval = interval;
		
	}
}
