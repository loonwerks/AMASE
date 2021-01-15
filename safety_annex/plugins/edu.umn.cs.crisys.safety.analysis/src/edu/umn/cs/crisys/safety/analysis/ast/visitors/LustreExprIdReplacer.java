package edu.umn.cs.crisys.safety.analysis.ast.visitors;

import jkind.lustre.ArrayAccessExpr;
import jkind.lustre.ArrayExpr;
import jkind.lustre.ArrayUpdateExpr;
import jkind.lustre.BinaryExpr;
import jkind.lustre.BoolExpr;
import jkind.lustre.CastExpr;
import jkind.lustre.CondactExpr;
import jkind.lustre.Expr;
import jkind.lustre.FunctionCallExpr;
import jkind.lustre.IdExpr;
import jkind.lustre.IfThenElseExpr;
import jkind.lustre.IntExpr;
import jkind.lustre.NodeCallExpr;
import jkind.lustre.RealExpr;
import jkind.lustre.RecordAccessExpr;
import jkind.lustre.RecordExpr;
import jkind.lustre.RecordUpdateExpr;
import jkind.lustre.TupleExpr;
import jkind.lustre.UnaryExpr;
import jkind.lustre.visitors.ExprMapVisitor;

public class LustreExprIdReplacer extends ExprMapVisitor {

	private String originalId = "";
	private String newId = "";

	public LustreExprIdReplacer(String originalId, String newId) {
		this.originalId = originalId;
		this.newId = newId;
	}

	public Expr visit(Expr expr) {
		return expr.accept(this);
	}

	@Override
	public Expr visit(BinaryExpr e) {
		BinaryExpr returnExpr = new BinaryExpr(e.location, visit(e.left), e.op, visit(e.right));
		return returnExpr;
	}

	@Override
	public Expr visit(UnaryExpr e) {
		UnaryExpr returnExpr = new UnaryExpr(e.location, e.op, visit(e.expr));
		return returnExpr;
	}

	@Override
	public Expr visit(IfThenElseExpr e) {
		IfThenElseExpr returnExpr = new IfThenElseExpr(e.location, visit(e.cond), visit(e.elseExpr), visit(e.thenExpr));
		return returnExpr;
	}

	@Override
	public Expr visit(BoolExpr e) {
		return e;
	}

	@Override
	public Expr visit(IdExpr e) {
		IdExpr returnExpr = e;
		if (e.id.equals(originalId)) {
			returnExpr = new IdExpr(e.location, newId);
		}
		return returnExpr;
	}

	@Override
	public Expr visit(IntExpr e) {
		return e;
	}

	@Override
	public Expr visit(RealExpr e) {
		return e;
	}

	@Override
	public Expr visit(NodeCallExpr e) {
		return e;
	}

	@Override
	public Expr visit(FunctionCallExpr e) {
		return e;
	}


	@Override
	public Expr visit(RecordAccessExpr e) {
		// not supported
		throw new IllegalArgumentException();
	}

	@Override
	public Expr visit(RecordExpr e) {
		// not supported
		throw new IllegalArgumentException();
	}

	@Override
	public Expr visit(RecordUpdateExpr e) {
		// not supported
		throw new IllegalArgumentException();
	}

	@Override
	public Expr visit(TupleExpr e) {
		// not supported
		throw new IllegalArgumentException();
	}

	@Override
	public Expr visit(ArrayAccessExpr e) {
		// not supported
		throw new IllegalArgumentException();
	}

	@Override
	public Expr visit(ArrayExpr e) {
		// not supported
		throw new IllegalArgumentException();
	}

	@Override
	public Expr visit(ArrayUpdateExpr e) {
		// not supported
		throw new IllegalArgumentException();
	}

	@Override
	public Expr visit(CastExpr e) {
		// not supported
		throw new IllegalArgumentException();
	}

	@Override
	public Expr visit(CondactExpr e) {
		// not supported
		throw new IllegalArgumentException();
	}
}
