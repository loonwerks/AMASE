package edu.umn.cs.crisys.safety.analysis.ast.visitors;

import java.util.Map;

import edu.umn.cs.crisys.safety.analysis.SafetyException;
import edu.umn.cs.crisys.safety.util.AgreeUtil;
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
import jkind.lustre.visitors.ExprVisitor;

public class FaultNodeCausingExprFinder implements ExprVisitor<Expr> {

	// identify the value assignment from the thenExpr and elseExpr
	// to find the one that matches the bottomIdNode expr
	// then get the conjunction of the condExpr
	// and construct CT node from that
	// and add as child node to the original bottomIdNode
	private Expr targetExpr;
	private String outputId;
	private String faultId;
	private Map<String, String> faultNodeInputToOriginalSignalMap;

	public FaultNodeCausingExprFinder(Expr targetExpr, String outputId, String faultId,
			Map<String, String> faultNodeInputToOriginalSignalMap) {
		this.targetExpr = targetExpr;
		this.outputId = outputId;
		this.faultId = faultId;
		this.faultNodeInputToOriginalSignalMap = faultNodeInputToOriginalSignalMap;
	}

	public void setTargetExpr(Expr targetExpr) {
		this.targetExpr = targetExpr;
	}

	public Expr getTargetExpr() {
		return targetExpr;
	}

	public void setOutputId(String outputId) {
		this.outputId = outputId;
	}

	public String getOutputId() {
		return outputId;
	}

	public void setFaultId(String faultId) {
		this.faultId = faultId;
	}

	public String getFaultId() {
		return faultId;
	}

	public void setFaultNodeSignalMap(Map<String, String> map) {
		this.faultNodeInputToOriginalSignalMap = map;
	}

	public Map<String, String> getFaultNodeSignalMap() {
		return faultNodeInputToOriginalSignalMap;
	}

	public Expr visit(Expr expr) {
		return expr.accept(this);
	}

	@Override
	public Expr visit(IfThenElseExpr e) {
		Expr condExpr = e.cond;
		Expr thenExpr = e.thenExpr;
		Expr elseExpr = e.elseExpr;
		Expr returnExpr = null;
		BinaryOp andOp = BinaryOp.AND;
		BinaryOp equalOp = BinaryOp.EQUAL;
		Boolean toEvalElseExpr = true;

		if (condExpr instanceof IdExpr) {
			String condExprId = ((IdExpr) condExpr).id;
			if (condExprId.equals("trigger")) {
				// find the corresponding fault in the triggerFaultMap
				condExpr = new IdExpr(faultId);
				// no need to evaluate elseExpr here as it's when the fault trigger is not activated
				toEvalElseExpr = false;
			}
			else {
				condExpr = visit(condExpr);
			}
		}
		else {
			condExpr = visit(condExpr);
		}

		if (thenExpr instanceof IfThenElseExpr) {
			Expr thenExprReturn = visit(thenExpr);
			if (thenExprReturn != null) {
				return new BinaryExpr(e.location, condExpr, andOp, thenExprReturn);
			}
		} else {
			Expr thenExprAssign = new BinaryExpr(e.location, new IdExpr(outputId), equalOp, thenExpr);
			// TODO: develop a visitor to compare if two expressions are considered equal
			// including comparing with the same value expressions
			if (AgreeUtil.exprsEqual(thenExprAssign, targetExpr)) {
				return condExpr;
			}
		}

		if (toEvalElseExpr) {
			if (elseExpr instanceof IfThenElseExpr) {
				Expr elseExprReturn = visit(elseExpr);
				if (elseExprReturn != null) {
					return new BinaryExpr(e.location, condExpr, andOp, elseExprReturn);
				}
			} else {
				Expr elseExprAssign = new BinaryExpr(e.location, new IdExpr(outputId), equalOp, elseExpr);
				// TODO: develop method to compare if two expressions are considered equal
				if (AgreeUtil.exprsEqual(elseExprAssign, targetExpr)) {
					return new UnaryExpr(e.location, UnaryOp.NOT, condExpr);
				}
			}

		}
		return returnExpr;
	}

	@Override
	public Expr visit(BinaryExpr e) {
		Expr returnExpr = new BinaryExpr(e.location, visit(e.left), e.op, visit(e.right));
		return returnExpr;
	}

	@Override
	public Expr visit(UnaryExpr e) {
		Expr returnExpr = new UnaryExpr(e.location, e.op, visit(e.expr));
		return returnExpr;
	}

	@Override
	public Expr visit(BoolExpr e) {
		return e;
	}

	@Override
	public Expr visit(IdExpr e) {
		Expr returnExpr = new IdExpr(faultNodeInputToOriginalSignalMap.get(e.id));
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
	public Expr visit(FunctionCallExpr e) {
		throw new SafetyException("Expr not supported " + e.toString());
	}

	@Override
	public Expr visit(NodeCallExpr e) {
		throw new SafetyException("Expr not supported " + e.toString());
	}

	@Override
	public Expr visit(CastExpr e) {
		// not supported
		throw new SafetyException("Expr not supported " + e.toString());
	}

	@Override
	public Expr visit(CondactExpr e) {
		// not supported
		throw new SafetyException("Expr not supported " + e.toString());
	}

	@Override
	public Expr visit(RecordAccessExpr e) {
		// not supported
		throw new SafetyException("Expr not supported " + e.toString());
	}

	@Override
	public Expr visit(RecordExpr e) {
		// not supported
		throw new SafetyException("Expr not supported " + e.toString());
	}

	@Override
	public Expr visit(RecordUpdateExpr e) {
		// not supported
		throw new SafetyException("Expr not supported " + e.toString());
	}

	@Override
	public Expr visit(TupleExpr e) {
		// not supported
		throw new SafetyException("Expr not supported " + e.toString());
	}

	@Override
	public Expr visit(ArrayAccessExpr e) {
		// not supported
		throw new SafetyException("Expr not supported " + e.toString());
	}

	@Override
	public Expr visit(ArrayExpr e) {
		// not supported
		throw new SafetyException("Expr not supported " + e.toString());
	}

	@Override
	public Expr visit(ArrayUpdateExpr e) {
		// not supported
		throw new SafetyException("Expr not supported " + e.toString());
	}
}
