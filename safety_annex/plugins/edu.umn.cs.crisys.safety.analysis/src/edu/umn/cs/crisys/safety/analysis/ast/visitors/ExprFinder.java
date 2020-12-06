package edu.umn.cs.crisys.safety.analysis.ast.visitors;

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

public class ExprFinder implements ExprVisitor<Boolean> {

	// look for the given expr, if found, return true
	private Expr targetExpr;

	public ExprFinder(Expr targetExpr) {
		this.targetExpr = targetExpr;
	}

	public void setTargetExpr(Expr targetExpr) {
		this.targetExpr = targetExpr;
	}

	public Boolean visit(Expr expr) {
		return expr.accept(this);
	}

	@Override
	public Boolean visit(BinaryExpr e) {
		Boolean result = false;
		if (e.toString().equals(targetExpr.toString())) {
			result = true;
		} else if (visit(e.left) || visit(e.right)) {
			result = true;
		}
		return result;
	}

	@Override
	public Boolean visit(IfThenElseExpr e) {
		// if a then b else c = (a=>b) and (not a => c)
		Boolean result = false;
		if (e.toString().equals(targetExpr.toString())) {
			result = true;
		}
		else if (visit(e.cond) || visit(e.thenExpr) || visit(e.elseExpr)) {
			result = true;
		}
		return result;
	}

	@Override
	public Boolean visit(UnaryExpr e) {
		// returns true if targetExpr is of matching expr
		// of if e is of not(Id), and targetExpr is of Id = false
		Boolean result = false;
		String opName = e.op.name();
		if (e.toString().equals(targetExpr.toString())) {
			result = true;
		} else {
			if (opName.equals("NEGATIVE") || opName.equals("NOT")) {
				if (e.expr instanceof IdExpr) {
					if (targetExpr instanceof BinaryExpr) {
						Expr leftExpr = ((BinaryExpr) targetExpr).left;
						Expr rightExpr = ((BinaryExpr) targetExpr).right;
						String targetOpName = ((BinaryExpr) targetExpr).op.name();

						if (targetOpName.equals("EQUAL")) {
							if (leftExpr instanceof IdExpr) {
								if (((IdExpr) leftExpr).id.equals(((IdExpr) e.expr).id)) {
									if (rightExpr instanceof BoolExpr) {
										if (!((BoolExpr) rightExpr).value) {
											result = true;
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return result;
	}

	@Override
	public Boolean visit(BoolExpr e) {
		Boolean result = false;
		if (targetExpr instanceof BoolExpr) {
			if (((BoolExpr) targetExpr).value == e.value) {
				result = true;
			}
		}
		return result;
	}

	@Override
	public Boolean visit(IdExpr e) {
		// returns true if targetExpr is of matching Id, or of Id = true
		Boolean result = false;
		if (targetExpr instanceof IdExpr) {
			if (((IdExpr) targetExpr).id.equals(e.id)) {
				result = true;
			}
		} else {
			if (targetExpr instanceof BinaryExpr) {
				Expr leftExpr = ((BinaryExpr) targetExpr).left;
				Expr rightExpr = ((BinaryExpr) targetExpr).right;
				String opName = ((BinaryExpr) targetExpr).op.name();

				if (opName.equals("EQUAL")) {
					if (leftExpr instanceof IdExpr) {
						if (((IdExpr) leftExpr).id.equals(e.id)) {
							if (rightExpr instanceof BoolExpr) {
								if (((BoolExpr) rightExpr).value) {
									result = true;
								}
							}
						}
					}
				}
			}
		}
		return result;
	}

	@Override
	public Boolean visit(IntExpr e) {
		Boolean result = false;
		if (targetExpr instanceof IntExpr) {
			if (((IntExpr) targetExpr).value.equals(e.value)) {
				result = true;
			}
		}
		return result;
	}

	@Override
	public Boolean visit(RealExpr e) {
		Boolean result = false;
		if (targetExpr instanceof IntExpr) {
			if (((RealExpr) targetExpr).value.equals(e.value)) {
				result = true;
			}
		}
		return result;
	}

	@Override
	public Boolean visit(FunctionCallExpr e) {
		throw new SafetyException("Expr not supported " + e.toString());
	}

	@Override
	public Boolean visit(NodeCallExpr e) {
		throw new SafetyException("Expr not supported " + e.toString());
	}

	@Override
	public Boolean visit(CastExpr e) {
		// not supported
		throw new SafetyException("Expr not supported " + e.toString());
	}

	@Override
	public Boolean visit(CondactExpr e) {
		// not supported
		throw new SafetyException("Expr not supported " + e.toString());
	}

	@Override
	public Boolean visit(RecordAccessExpr e) {
		// not supported
		throw new SafetyException("Expr not supported " + e.toString());
	}

	@Override
	public Boolean visit(RecordExpr e) {
		// not supported
		throw new SafetyException("Expr not supported " + e.toString());
	}

	@Override
	public Boolean visit(RecordUpdateExpr e) {
		// not supported
		throw new SafetyException("Expr not supported " + e.toString());
	}

	@Override
	public Boolean visit(TupleExpr e) {
		// not supported
		throw new SafetyException("Expr not supported " + e.toString());
	}

	@Override
	public Boolean visit(ArrayAccessExpr e) {
		// not supported
		throw new SafetyException("Expr not supported " + e.toString());
	}

	@Override
	public Boolean visit(ArrayExpr e) {
		// not supported
		throw new SafetyException("Expr not supported " + e.toString());
	}

	@Override
	public Boolean visit(ArrayUpdateExpr e) {
		// not supported
		throw new SafetyException("Expr not supported " + e.toString());
	}
}
