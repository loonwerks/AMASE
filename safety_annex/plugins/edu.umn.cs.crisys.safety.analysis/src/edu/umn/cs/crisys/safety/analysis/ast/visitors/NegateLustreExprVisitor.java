package edu.umn.cs.crisys.safety.analysis.ast.visitors;

import jkind.lustre.ArrayAccessExpr;
import jkind.lustre.ArrayExpr;
import jkind.lustre.ArrayUpdateExpr;
import jkind.lustre.BinaryExpr;
import jkind.lustre.BinaryOp;
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
import jkind.lustre.UnaryOp;
import jkind.lustre.visitors.ExprMapVisitor;

public class NegateLustreExprVisitor extends ExprMapVisitor {

	public Expr visit(Expr expr) {
		return expr.accept(this);
	}

	@Override
	public Expr visit(BinaryExpr e) {
		String opName = e.op.name();
		BinaryOp newOp = null;
		Expr newExpr = null;
		if (opName.equals("AND")) {
			// not(a and b) = not(a) or not(b)
			newOp = BinaryOp.OR;
			newExpr = new BinaryExpr(e.location, visit(e.left), newOp, visit(e.right));
		}
		else if (opName.equals("OR")) {
			// not(a or b) = not(a) and not(b)
			newOp = BinaryOp.AND;
			newExpr = new BinaryExpr(e.location, visit(e.left), newOp, visit(e.right));
		}
		else if (opName.equals("IMPLIES")) {
			newOp = BinaryOp.AND;
			// not(a=>b) = not(not(a) or b) = a or not(b)
			newExpr = new BinaryExpr(e.location, e.left, newOp, visit(e.right));
		} else if (opName.equals("ARROW")) {
			newExpr = new BinaryExpr(e.location, visit(e.left), e.op, visit(e.right));
		}
		else if (opName.equals("EQUAL")) {
			newOp = BinaryOp.NOTEQUAL;
			newExpr = new BinaryExpr(e.location, e.left, newOp, e.right);
		}
		else if (opName.equals("NOTEQUAL")) {
			newOp = BinaryOp.EQUAL;
			newExpr = new BinaryExpr(e.location, e.left, newOp, e.right);
		}
		else if (opName.equals("GREATER")) {
			newOp = BinaryOp.LESSEQUAL;
			newExpr = new BinaryExpr(e.location, e.left, newOp, e.right);
		} else if (opName.equals("LESS")) {
			newOp = BinaryOp.GREATEREQUAL;
			newExpr = new BinaryExpr(e.location, e.left, newOp, e.right);
		} else if (opName.equals("GREATEREQUAL")) {
			newOp = BinaryOp.LESS;
			newExpr = new BinaryExpr(e.location, e.left, newOp, e.right);
		} else if (opName.equals("LESSEQUAL")) {
			newOp = BinaryOp.GREATER;
			newExpr = new BinaryExpr(e.location, e.left, newOp, e.right);
		} else if (opName.equals("PLUS")) {
			if ((e.left instanceof IntExpr) && (e.right instanceof IntExpr)) {
				newExpr = new UnaryExpr(e.location, UnaryOp.NOT,
						new IntExpr(e.location, ((IntExpr) e.left).value.add(((IntExpr) e.right).value)));
			}
			else if ((e.left instanceof RealExpr) && (e.right instanceof RealExpr)) {
				newExpr = new UnaryExpr(e.location, UnaryOp.NOT,
						new RealExpr(e.location, ((RealExpr) e.left).value.add(((RealExpr) e.right).value)));
			}
			else {
				// not supported
				throw new IllegalArgumentException();
			}
		} else if (opName.equals("MINUS")) {
			if ((e.left instanceof IntExpr) && (e.right instanceof IntExpr)) {
				newExpr = new UnaryExpr(e.location, UnaryOp.NOT,
						new IntExpr(e.location, ((IntExpr) e.left).value.add(((IntExpr) e.right).value.negate())));
			} else if ((e.left instanceof RealExpr) && (e.right instanceof RealExpr)) {
				newExpr = new UnaryExpr(e.location, UnaryOp.NOT,
						new RealExpr(e.location, ((RealExpr) e.left).value.add(((RealExpr) e.right).value.negate())));
			} else {
				// not supported
				throw new IllegalArgumentException();
			}
		}
		else if (opName.equals("MULTIPLY")) {
			if ((e.left instanceof IntExpr) && (e.right instanceof IntExpr)) {
				newExpr = new UnaryExpr(e.location, UnaryOp.NOT,
						new IntExpr(e.location, ((IntExpr) e.left).value.multiply(((IntExpr) e.right).value)));
			} else if ((e.left instanceof RealExpr) && (e.right instanceof RealExpr)) {
				newExpr = new UnaryExpr(e.location, UnaryOp.NOT,
						new RealExpr(e.location, ((RealExpr) e.left).value.multiply(((RealExpr) e.right).value)));
			} else {
				// not supported
				throw new IllegalArgumentException();
			}
		}
		else if (opName.equals("DIVIDE")) {
			if ((e.left instanceof IntExpr) && (e.right instanceof IntExpr)) {
				newExpr = new UnaryExpr(e.location, UnaryOp.NOT,
						new IntExpr(e.location, ((IntExpr) e.left).value.divide(((IntExpr) e.right).value)));
			} else if ((e.left instanceof RealExpr) && (e.right instanceof RealExpr)) {
				newExpr = new UnaryExpr(e.location, UnaryOp.NOT,
						new RealExpr(e.location, ((RealExpr) e.left).value.divide(((RealExpr) e.right).value)));
			} else {
				// not supported
				throw new IllegalArgumentException();
			}
		} else {
			// not supported
			throw new IllegalArgumentException();
		}
		return newExpr;
	}

	@Override
	public Expr visit(UnaryExpr e) {
		String opName = e.op.name();
		Expr returnExpr = null;
		if (opName.equals("NEGATIVE") || opName.equals("NOT")) {
			returnExpr = visit(e.expr);
		} else if (opName.equals("PRE")) {
			returnExpr = new UnaryExpr(e.location, e.op, visit(e.expr));
		}

		return returnExpr;
	}

	@Override
	public Expr visit(IfThenElseExpr e) {
		// if a then b else c = (a=>b) and (not a => c)
		// not(if a then b else c)
		// = not(a=>b) or not(not a=>c)
		// = (a and (not b)) or (not a and c)
		BinaryExpr exprLeft = new BinaryExpr(e.location, e.cond, BinaryOp.AND,
				visit(e.thenExpr));
		BinaryExpr exprRight = new BinaryExpr(e.location, visit(e.cond), BinaryOp.AND,
				e.elseExpr);
		BinaryExpr returnExpr = new BinaryExpr(e.location, exprLeft, BinaryOp.OR, exprRight);
		return returnExpr;
	}

	@Override
	public Expr visit(BoolExpr e) {
		return e;
		// return new BoolExpr(!(e.value));
	}

	@Override
	public Expr visit(IdExpr e) {
		return e;
		// UnaryExpr newExpr = new UnaryExpr(e.location, UnaryOp.NOT, e);
		// return newExpr;
	}

	@Override
	public Expr visit(IntExpr e) {
		return e;
//		UnaryExpr newExpr = new UnaryExpr(e.location, UnaryOp.NOT, e);
//		return newExpr;
	}

	@Override
	public Expr visit(RealExpr e) {
		return e;
//		UnaryExpr newExpr = new UnaryExpr(e.location, UnaryOp.NOT, e);
//		return newExpr;
	}

	@Override
	public Expr visit(NodeCallExpr e) {
		return e;
//		UnaryExpr newExpr = new UnaryExpr(e.location, UnaryOp.NOT, e);
//		return newExpr;
	}

	@Override
	public Expr visit(FunctionCallExpr e) {
		return e;
//		UnaryExpr newExpr = new UnaryExpr(e.location, UnaryOp.NOT, e);
//		return newExpr;
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
