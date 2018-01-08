package edu.umn.cs.crisys.safety.util;

import jkind.lustre.ArrayAccessExpr;
import jkind.lustre.ArrayUpdateExpr;
import jkind.lustre.Expr;

public class ArraySubscriptPathElement extends PathElement{

	private Expr subscript;
	
	public ArraySubscriptPathElement(Expr subscript){
		this.subscript = subscript;
	}
	
	@Override
	public Expr makeAccessExpr(Expr root) {
		return new ArrayAccessExpr(root, this.subscript);
	}

	@Override
	public Expr makeUpdateExpr(Expr root, Expr rhs) {
		return new ArrayUpdateExpr(root, this.subscript, rhs);
	}

	
	
}
