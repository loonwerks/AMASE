package edu.umn.cs.crisys.safety.analysis.ast.visitors;

import edu.umn.cs.crisys.safety.analysis.causationTree.CTIdNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTNode;
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

public class LustreExprToCTVisitor implements ExprVisitor<CTNode> {

	private NegateLustreExprVisitor negateExprVisitor = new NegateLustreExprVisitor();

	public CTNode visit(Expr expr) {
		return expr.accept(this);
	}

	@Override
	public CTNode visit(BinaryExpr e) {
		CTNode leftNode = visit(e.left);
		CTNode rightNode = visit(e.right);
		CTNode returnNode = null;
		String opName = e.op.name();
//		if (opName.equals("AND")) {
//			returnNode = new
//			newOp = BinaryOp.OR;
//			newExpr = new BinaryExpr(e.location, left, newOp, right);
//		} else if (opName.equals("OR")) {
//			newOp = BinaryOp.AND;
//			newExpr = new BinaryExpr(e.location, left, newOp, right);
//		} else if (opName.equals("EQUAL")) {
//			newOp = BinaryOp.NOTEQUAL;
//			newExpr = new BinaryExpr(e.location, left, newOp, right);
//		} else if (opName.equals("NOTEQUAL")) {
//			newOp = BinaryOp.EQUAL;
//			newExpr = new BinaryExpr(e.location, left, newOp, right);
//		} else if (opName.equals("GREATER")) {
//			newOp = BinaryOp.LESSEQUAL;
//			newExpr = new BinaryExpr(e.location, left, newOp, right);
//		} else if (opName.equals("LESS")) {
//			newOp = BinaryOp.GREATEREQUAL;
//			newExpr = new BinaryExpr(e.location, left, newOp, right);
//		} else if (opName.equals("GREATEREQUAL")) {
//			newOp = BinaryOp.LESS;
//			newExpr = new BinaryExpr(e.location, left, newOp, right);
//		} else if (opName.equals("LESSEQUAL")) {
//			newOp = BinaryOp.GREATER;
//			newExpr = new BinaryExpr(e.location, left, newOp, right);
//		} else if (opName.equals("PLUS")) {
//			if ((left instanceof IntExpr) && (right instanceof IntExpr)) {
//				newExpr = new UnaryExpr(e.location, UnaryOp.NOT,
//						new IntExpr(e.location, ((IntExpr) left).value.add(((IntExpr) right).value)));
//			} else if ((left instanceof RealExpr) && (right instanceof RealExpr)) {
//				newExpr = new UnaryExpr(e.location, UnaryOp.NOT,
//						new RealExpr(e.location, ((RealExpr) left).value.add(((RealExpr) right).value)));
//			} else {
//				// not supported
//				throw new IllegalArgumentException();
//			}
//		} else if (opName.equals("MINUS")) {
//			if ((left instanceof IntExpr) && (right instanceof IntExpr)) {
//				newExpr = new UnaryExpr(e.location, UnaryOp.NOT,
//						new IntExpr(e.location, ((IntExpr) left).value.add(((IntExpr) right).value.negate())));
//			} else if ((left instanceof RealExpr) && (right instanceof RealExpr)) {
//				newExpr = new UnaryExpr(e.location, UnaryOp.NOT,
//						new RealExpr(e.location, ((RealExpr) left).value.add(((RealExpr) right).value.negate())));
//			} else {
//				// not supported
//				throw new IllegalArgumentException();
//			}
//		} else if (opName.equals("MULTIPLY")) {
//			if ((left instanceof IntExpr) && (right instanceof IntExpr)) {
//				newExpr = new UnaryExpr(e.location, UnaryOp.NOT,
//						new IntExpr(e.location, ((IntExpr) left).value.multiply(((IntExpr) right).value)));
//			} else if ((left instanceof RealExpr) && (right instanceof RealExpr)) {
//				newExpr = new UnaryExpr(e.location, UnaryOp.NOT,
//						new RealExpr(e.location, ((RealExpr) left).value.multiply(((RealExpr) right).value)));
//			} else {
//				// not supported
//				throw new IllegalArgumentException();
//			}
//		} else if (opName.equals("DIVIDE")) {
//			if ((left instanceof IntExpr) && (right instanceof IntExpr)) {
//				newExpr = new UnaryExpr(e.location, UnaryOp.NOT,
//						new IntExpr(e.location, ((IntExpr) left).value.divide(((IntExpr) right).value)));
//			} else if ((left instanceof RealExpr) && (right instanceof RealExpr)) {
//				newExpr = new UnaryExpr(e.location, UnaryOp.NOT,
//						new RealExpr(e.location, ((RealExpr) left).value.divide(((RealExpr) right).value)));
//			} else {
//				// not supported
//				throw new IllegalArgumentException();
//			}
//		} else if (opName.equals("IMPLIES")) {
//			newOp = BinaryOp.AND;
//			newExpr = new BinaryExpr(e.location, left, newOp, new UnaryExpr(e.location, UnaryOp.NOT, right));
//		} else if (opName.equals("ARROW")) {
//			newExpr = new BinaryExpr(e.location, new UnaryExpr(e.location, UnaryOp.NOT, left), e.op,
//					new UnaryExpr(e.location, UnaryOp.NOT, right));
//		} else {
//			// not supported
//			throw new IllegalArgumentException();
//		}
		return null;
	}

	@Override
	public CTNode visit(BoolExpr e) {
		return null;
	}

	@Override
	public CTNode visit(CastExpr e) {
		return null;
	}

	@Override
	public CTNode visit(CondactExpr e) {
		throw new IllegalArgumentException();
	}

	@Override
	public CTNode visit(IdExpr e) {
		CTIdNode idNode = new CTIdNode(e.id);
		return idNode;
	}

	@Override
	public CTNode visit(IfThenElseExpr e) {
		return null;
	}

	@Override
	public CTNode visit(IntExpr e) {
		return null;
	}

	@Override
	public CTNode visit(FunctionCallExpr e) {
		throw new IllegalArgumentException();
	}

	@Override
	public CTNode visit(NodeCallExpr e) {
		throw new IllegalArgumentException();
	}

	@Override
	public CTNode visit(RealExpr e) {
		throw new IllegalArgumentException();
	}

	@Override
	public CTNode visit(RecordAccessExpr e) {
		throw new IllegalArgumentException();
	}

	@Override
	public CTNode visit(RecordExpr e) {
		return null;
	}

	@Override
	public CTNode visit(RecordUpdateExpr e) {
		return null;
	}

	@Override
	public CTNode visit(TupleExpr e) {
		throw new IllegalArgumentException();
	}

	@Override
	public CTNode visit(UnaryExpr e) {
		// CTNode node = e.expr.accept(this);
		String opName = e.op.name();
		if (opName.equals("NEGATIVE") || opName.equals("NOT")) {
			// distribute the negation to associate with individual Id node
			Expr newExpr = negateExprVisitor.visit(e);
			// then visit the new expression
			return visit(newExpr);
		} else if (opName.equals("PRE")) {
			// TODO:
		}
		return null;
	}


	@Override
	public CTNode visit(ArrayAccessExpr e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CTNode visit(ArrayExpr e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CTNode visit(ArrayUpdateExpr e) {
		// TODO Auto-generated method stub
		return null;
	}

}
