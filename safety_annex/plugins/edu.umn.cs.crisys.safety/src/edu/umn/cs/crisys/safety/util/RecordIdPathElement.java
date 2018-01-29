package edu.umn.cs.crisys.safety.util;

import jkind.lustre.Expr;
import jkind.lustre.RecordAccessExpr;
import jkind.lustre.RecordUpdateExpr;

public class RecordIdPathElement extends PathElement{

	private String id;
	
	public RecordIdPathElement(String id){
		this.id = id;
	}
	
	@Override
	public Expr makeAccessExpr(Expr root) {
		return new RecordAccessExpr(root, this.id);
	}

	@Override
	public Expr makeUpdateExpr(Expr root, Expr rhs) {
		return new RecordUpdateExpr(root, this.id, rhs);
	}

}
