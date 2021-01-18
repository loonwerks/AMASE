package edu.umn.cs.crisys.safety.analysis.ast.visitors;

import com.rockwellcollins.atc.agree.analysis.ast.AgreeNode;

import edu.umn.cs.crisys.safety.analysis.SafetyException;
import edu.umn.cs.crisys.safety.util.AgreeUtil;
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
	private AgreeNode agreeNode;
	private Expr sourceExpr;

	public Expr getSrcExpr() {
		return sourceExpr;
	}

	public ExprFinder(Expr targetExpr, AgreeNode agreeNode) {
		this.targetExpr = targetExpr;
		this.agreeNode = agreeNode;
		this.sourceExpr = null;
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
		} else {
			if (targetExpr instanceof BinaryExpr) {
				String targetOpName = ((BinaryExpr) targetExpr).op.name();
				String srcOpName = e.op.name();
				if (targetOpName.equals("EQUAL")) {
					if (srcOpName.equals("EQUAL")) {
						Expr originalTargetExpr = targetExpr;
						Expr targetLeftExpr = ((BinaryExpr)targetExpr).left;
						Expr targetRightExpr = ((BinaryExpr)targetExpr).right;
						Expr sourceLeftExpr = e.left;
						Expr sourceRightExpr = e.right;
						setTargetExpr(targetLeftExpr);
						// if left target and left source match
						if(visit(sourceLeftExpr)) {
							setTargetExpr(targetRightExpr);
							// if right target and right source match
							if(visit(sourceRightExpr)) {
								// return true
								result = true;
							}
							// if right target and right source do not match
							else {
								// check if right target is constant and right source is input
								// if yes, set sourceExpr as input = constant and return true
								result = checkTargetConstSourceInput(e, result, targetRightExpr, sourceRightExpr);
							}
						}
						// if left target and left source do not match
						// and left target and right source match
						else if(visit(sourceRightExpr)) {
							// check if right target is constant and left source is input
							// if yes, set sourceExpr as input = constant and return true
							result = checkTargetConstSourceInput(e, result, targetRightExpr, sourceLeftExpr);
						} else {
							setTargetExpr(targetRightExpr);
							// if left target and left source do not match
							// and right target and left source match
							if (visit(sourceLeftExpr)) {
								setTargetExpr(targetLeftExpr);
								// check if left target and right source match
								if (visit(sourceRightExpr)) {
									// return true
									result = true;
								}
								// if left target and right source do not match
								else {
									// check if left target is constant and right source is input
									// if yes, set sourceExpr as input = constant and return true
									result = checkTargetConstSourceInput(e, result, targetLeftExpr, sourceRightExpr);
								}
							}
							// if left target and left source do not match
							// and right target and left source do not match
							// and right target and right source match
							else if (visit(sourceRightExpr)) {
								// check if left target is constant and left source is input
								// if yes, set sourceExpr as input = constant and return true
								result = checkTargetConstSourceInput(e, result, targetLeftExpr, sourceLeftExpr);
							}
						}
					}
				}
			}
			// TODO: verify if the following branch is still accurate after the above code
			else if (visit(e.left) || visit(e.right)) {
				result = true;
			}
		}
		return result;
	}

	private Boolean checkTargetConstSourceInput(BinaryExpr e, Boolean result, Expr targetExpr, Expr sourceExpr) {
		if ((targetExpr instanceof IntExpr) || (targetExpr instanceof BoolExpr) || (targetExpr instanceof RealExpr)) {
			if (sourceExpr instanceof IdExpr) {
				String id = ((IdExpr) sourceExpr).id;
				if (AgreeUtil.inputsContainId(agreeNode, id)) {
					this.sourceExpr = new BinaryExpr(e.location, sourceExpr, e.op, targetExpr);
					result = true;
				}
			}
		}
		return result;
	}

	@Override
	public Boolean visit(IfThenElseExpr e) {
		// if a then b else c = (a=>b) and (not a => c)
		Boolean result = false;
		if (e.toString().equals(targetExpr.toString())) {
			result = true;
		} else if (visit(e.cond) || visit(e.thenExpr) || visit(e.elseExpr)) {
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
		// returns true if targetExpr is of matching Id, or of Id = true, or Id = another input id
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
