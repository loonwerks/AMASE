package edu.umn.cs.crisys.safety.util;

import jkind.lustre.Expr;

public abstract class PathElement {

	abstract Expr makeAccessExpr(Expr root);
	abstract Expr makeUpdateExpr(Expr root, Expr rhs);
	
}
