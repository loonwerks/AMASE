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

public class FaultyOutputFinder implements ExprVisitor<List<String>> {

	public List<String> visit(Expr expr) {
		return expr.accept(this);
	}

	@Override
	public List<String> visit(IfThenElseExpr e) {
		List<String> returnList = new ArrayList<>();
		if(e.thenExpr instanceof IdExpr) {
			returnList.add(((IdExpr)e.thenExpr).id);
		}
		returnList.addAll(visit(e.elseExpr));
		return returnList;
	}

	@Override
	public List<String> visit(BinaryExpr e) {
		List<String> returnList = new ArrayList<>();
		return returnList;
	}


	@Override
	public List<String> visit(UnaryExpr e) {
		List<String> returnList = new ArrayList<>();
		return returnList;
	}

	@Override
	public List<String> visit(BoolExpr e) {
		List<String> returnList = new ArrayList<>();
		return returnList;
	}

	@Override
	public List<String> visit(IdExpr e) {
		List<String> returnList = new ArrayList<>();
		return returnList;
	}

	@Override
	public List<String> visit(IntExpr e) {
		List<String> returnList = new ArrayList<>();
		return returnList;
	}

	@Override
	public List<String> visit(RealExpr e) {
		List<String> returnList = new ArrayList<>();
		return returnList;
	}

	@Override
	public List<String> visit(FunctionCallExpr e) {
		throw new SafetyException("Expr not supported " + e.toString());
	}

	@Override
	public List<String> visit(NodeCallExpr e) {
		throw new SafetyException("Expr not supported " + e.toString());
	}

	@Override
	public List<String> visit(CastExpr e) {
		// not supported
		throw new SafetyException("Expr not supported " + e.toString());
	}

	@Override
	public List<String> visit(CondactExpr e) {
		// not supported
		throw new SafetyException("Expr not supported " + e.toString());
	}

	@Override
	public List<String> visit(RecordAccessExpr e) {
		// not supported
		throw new SafetyException("Expr not supported " + e.toString());
	}

	@Override
	public List<String> visit(RecordExpr e) {
		// not supported
		throw new SafetyException("Expr not supported " + e.toString());
	}

	@Override
	public List<String> visit(RecordUpdateExpr e) {
		// not supported
		throw new SafetyException("Expr not supported " + e.toString());
	}

	@Override
	public List<String> visit(TupleExpr e) {
		// not supported
		throw new SafetyException("Expr not supported " + e.toString());
	}

	@Override
	public List<String> visit(ArrayAccessExpr e) {
		// not supported
		throw new SafetyException("Expr not supported " + e.toString());
	}

	@Override
	public List<String> visit(ArrayExpr e) {
		// not supported
		throw new SafetyException("Expr not supported " + e.toString());
	}

	@Override
	public List<String> visit(ArrayUpdateExpr e) {
		// not supported
		throw new SafetyException("Expr not supported " + e.toString());
	}
}
