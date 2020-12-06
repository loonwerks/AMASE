package edu.umn.cs.crisys.safety.analysis.ast.visitors;

import java.util.ArrayList;
import java.util.List;

import edu.umn.cs.crisys.safety.analysis.SafetyException;
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
import jkind.lustre.visitors.ExprVisitor;

public class CausingExprFinder implements ExprVisitor<List<Expr>> {

	private Expr targetExpr;
	private ExprFinder exprFinder;

	public CausingExprFinder(Expr targetExpr) {
		this.targetExpr = targetExpr;
		exprFinder = new ExprFinder(targetExpr);
	}

	public void setTargetExpr(Expr targetExpr) {
		this.targetExpr = targetExpr;
	}

	public Expr getTargetExpr() {
		return targetExpr;
	}

	public List<Expr> visit(Expr expr) {
		return expr.accept(this);
	}

	@Override
	public List<Expr> visit(BinaryExpr e) {
		List<Expr> resultList = new ArrayList<Expr>();
		String opName = e.op.name();
		if (opName.equals("IMPLIES")) {
			// if the right expr contains the target expr
			// then add the left expr to the resultList
			// otherwise skip
			if (exprFinder.visit(e.right)) {
				resultList.add(e.left);
			}
		}
		else {
			resultList.addAll(visit(e.left));
			resultList.addAll(visit(e.right));
		}
		return resultList;
	}

	@Override
	public List<Expr> visit(IfThenElseExpr e) {
		// if a then b else c = (a=>b) and (not a => c)
		// = (not a or b) and (a or c)
		List<Expr> resultList = new ArrayList<Expr>();
		if (exprFinder.visit(e.thenExpr) || exprFinder.visit(e.elseExpr)) {
			resultList.add(e.cond);
		}
		return resultList;
	}

	@Override
	public List<Expr> visit(UnaryExpr e) {
		List<Expr> resultList = new ArrayList<Expr>();
		resultList.addAll(visit(e.expr));
		return resultList;
	}

	@Override
	public List<Expr> visit(BoolExpr e) {
		List<Expr> resultList = new ArrayList<Expr>();
		return resultList;
	}

	@Override
	public List<Expr> visit(IdExpr e) {
		List<Expr> resultList = new ArrayList<Expr>();
		return resultList;
	}

	@Override
	public List<Expr> visit(IntExpr e) {
		List<Expr> resultList = new ArrayList<Expr>();
		return resultList;
	}

	@Override
	public List<Expr> visit(RealExpr e) {
		List<Expr> resultList = new ArrayList<Expr>();
		return resultList;
	}

	@Override
	public List<Expr> visit(FunctionCallExpr e) {
		throw new SafetyException("Expr not supported " + e.toString());
	}

	@Override
	public List<Expr> visit(NodeCallExpr e) {
		throw new SafetyException("Expr not supported " + e.toString());
	}

	@Override
	public List<Expr> visit(CastExpr e) {
		// not supported
		throw new SafetyException("Expr not supported " + e.toString());
	}

	@Override
	public List<Expr> visit(CondactExpr e) {
		// not supported
		throw new SafetyException("Expr not supported " + e.toString());
	}

	@Override
	public List<Expr> visit(RecordAccessExpr e) {
		// not supported
		throw new SafetyException("Expr not supported " + e.toString());
	}

	@Override
	public List<Expr> visit(RecordExpr e) {
		// not supported
		throw new SafetyException("Expr not supported " + e.toString());
	}

	@Override
	public List<Expr> visit(RecordUpdateExpr e) {
		// not supported
		throw new SafetyException("Expr not supported " + e.toString());
	}

	@Override
	public List<Expr> visit(TupleExpr e) {
		// not supported
		throw new SafetyException("Expr not supported " + e.toString());
	}

	@Override
	public List<Expr> visit(ArrayAccessExpr e) {
		// not supported
		throw new SafetyException("Expr not supported " + e.toString());
	}

	@Override
	public List<Expr> visit(ArrayExpr e) {
		// not supported
		throw new SafetyException("Expr not supported " + e.toString());
	}

	@Override
	public List<Expr> visit(ArrayUpdateExpr e) {
		// not supported
		throw new SafetyException("Expr not supported " + e.toString());
	}
}
