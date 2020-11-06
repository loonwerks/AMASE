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
		Expr left = e.left.accept(this);
		Expr right = e.right.accept(this);
		String opName = e.op.name();
		BinaryOp newOp = null;
		Expr newExpr = null;
		if (opName.equals("AND")) {
			newOp = BinaryOp.OR;
			newExpr = new BinaryExpr(e.location, left, newOp, right);
		}
		else if (opName.equals("OR")) {
			newOp = BinaryOp.AND;
			newExpr = new BinaryExpr(e.location, left, newOp, right);
		}
		else if (opName.equals("EQUAL")) {
			newOp = BinaryOp.NOTEQUAL;
			newExpr = new BinaryExpr(e.location, left, newOp, right);
		}
		else if (opName.equals("NOTEQUAL")) {
			newOp = BinaryOp.EQUAL;
			newExpr = new BinaryExpr(e.location, left, newOp, right);
		}
		else if (opName.equals("GREATER")) {
			newOp = BinaryOp.LESSEQUAL;
			newExpr = new BinaryExpr(e.location, left, newOp, right);
		} else if (opName.equals("LESS")) {
			newOp = BinaryOp.GREATEREQUAL;
			newExpr = new BinaryExpr(e.location, left, newOp, right);
		} else if (opName.equals("GREATEREQUAL")) {
			newOp = BinaryOp.LESS;
			newExpr = new BinaryExpr(e.location, left, newOp, right);
		} else if (opName.equals("LESSEQUAL")) {
			newOp = BinaryOp.GREATER;
			newExpr = new BinaryExpr(e.location, left, newOp, right);
		} else if (opName.equals("PLUS")) {
			if ((left instanceof IntExpr) && (right instanceof IntExpr)) {
				newExpr = new UnaryExpr(e.location, UnaryOp.NOT,
						new IntExpr(e.location, ((IntExpr) left).value.add(((IntExpr) right).value)));
			}
			else if ((left instanceof RealExpr) && (right instanceof RealExpr)) {
				newExpr = new UnaryExpr(e.location, UnaryOp.NOT,
						new RealExpr(e.location, ((RealExpr) left).value.add(((RealExpr) right).value)));
			}
			else {
				// not supported
				throw new IllegalArgumentException();
			}
		} else if (opName.equals("MINUS")) {
			if ((left instanceof IntExpr) && (right instanceof IntExpr)) {
				newExpr = new UnaryExpr(e.location, UnaryOp.NOT,
						new IntExpr(e.location, ((IntExpr) left).value.add(((IntExpr) right).value.negate())));
			} else if ((left instanceof RealExpr) && (right instanceof RealExpr)) {
				newExpr = new UnaryExpr(e.location, UnaryOp.NOT,
						new RealExpr(e.location, ((RealExpr) left).value.add(((RealExpr) right).value.negate())));
			} else {
				// not supported
				throw new IllegalArgumentException();
			}
		}
		else if (opName.equals("MULTIPLY")) {
			if ((left instanceof IntExpr) && (right instanceof IntExpr)) {
				newExpr = new UnaryExpr(e.location, UnaryOp.NOT,
						new IntExpr(e.location, ((IntExpr) left).value.multiply(((IntExpr) right).value)));
			} else if ((left instanceof RealExpr) && (right instanceof RealExpr)) {
				newExpr = new UnaryExpr(e.location, UnaryOp.NOT,
						new RealExpr(e.location, ((RealExpr) left).value.multiply(((RealExpr) right).value)));
			} else {
				// not supported
				throw new IllegalArgumentException();
			}
		}
		else if (opName.equals("DIVIDE")) {
			if ((left instanceof IntExpr) && (right instanceof IntExpr)) {
				newExpr = new UnaryExpr(e.location, UnaryOp.NOT,
						new IntExpr(e.location, ((IntExpr) left).value.divide(((IntExpr) right).value)));
			} else if ((left instanceof RealExpr) && (right instanceof RealExpr)) {
				newExpr = new UnaryExpr(e.location, UnaryOp.NOT,
						new RealExpr(e.location, ((RealExpr) left).value.divide(((RealExpr) right).value)));
			} else {
				// not supported
				throw new IllegalArgumentException();
			}
		}
		else if (opName.equals("IMPLIES")) {
			newOp = BinaryOp.AND;
			newExpr = new BinaryExpr(e.location, left, newOp, new UnaryExpr(e.location, UnaryOp.NOT, right));
		} else if (opName.equals("ARROW")) {
			newExpr = new BinaryExpr(e.location, new UnaryExpr(e.location, UnaryOp.NOT, left), e.op,
					new UnaryExpr(e.location, UnaryOp.NOT, right));
		} else {
			// not supported
			throw new IllegalArgumentException();
		}
		return newExpr;
	}

	@Override
	public Expr visit(UnaryExpr e) {
		String opName = e.op.name();
		Expr newExpr = null;
		if (opName.equals("NEGATIVE") || opName.equals("NOT")) {
			newExpr = e.expr;
		} else if (opName.equals("PRE")) {
			newExpr = new UnaryExpr(e.location, e.op, new UnaryExpr(e.location, UnaryOp.NOT, e.expr));
		}

		return newExpr;
	}

	@Override
	public Expr visit(IfThenElseExpr e) {
		// if a then b else c = (a=>b) and (not a => c)
		// not(if a then b else c)
		// = not(a=>b) or not(not a=>c)
		// = (a and (not b)) or (not a and c)
		BinaryExpr exprLeft = new BinaryExpr(e.location, e.cond, BinaryOp.AND,
				new UnaryExpr(e.location, UnaryOp.NOT, e.thenExpr));
		BinaryExpr exprRight = new BinaryExpr(e.location, new UnaryExpr(e.location, UnaryOp.NOT, e.cond), BinaryOp.AND,
				e.elseExpr);
		BinaryExpr returnExpr = new BinaryExpr(e.location, exprLeft, BinaryOp.OR, exprRight);
		return returnExpr;
	}

	@Override
	public Expr visit(BoolExpr e) {
		return new BoolExpr(!(e.value));
	}

	@Override
	public Expr visit(IdExpr e) {
		UnaryExpr newExpr = new UnaryExpr(e.location, UnaryOp.NOT, e);
		return newExpr;
	}

	@Override
	public Expr visit(IntExpr e) {
		UnaryExpr newExpr = new UnaryExpr(e.location, UnaryOp.NOT, e);
		return newExpr;
	}

	@Override
	public Expr visit(RealExpr e) {
		UnaryExpr newExpr = new UnaryExpr(e.location, UnaryOp.NOT, e);
		return newExpr;
	}

	@Override
	public Expr visit(NodeCallExpr e) {
		UnaryExpr newExpr = new UnaryExpr(e.location, UnaryOp.NOT, e);
		return newExpr;
	}

	@Override
	public Expr visit(FunctionCallExpr e) {
		UnaryExpr newExpr = new UnaryExpr(e.location, UnaryOp.NOT, e);
		return newExpr;
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
