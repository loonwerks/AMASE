package edu.umn.cs.crisys.safety.analysis.ast.visitors;

import edu.umn.cs.crisys.safety.analysis.SafetyException;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTAndNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTBinaryIdNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTBottomNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTConstantNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTIdNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTNodeBinaryOp;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTNodeUnaryOp;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTNonBottomNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTOrNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTUnaryIdNode;
import edu.umn.cs.crisys.safety.analysis.transform.Fault;
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
	private Fault curFault = null;

	public void setCurrentFault(Fault fault) {
		this.curFault = fault;
	}

	public Fault getCurrentFault() {
		return curFault;
	}

	public CTNode visit(Expr expr) {
		return expr.accept(this);
	}

	@Override
	public CTNode visit(BinaryExpr e) {
		CTNode returnNode = null;
		String opName = e.op.name();
		if (opName.equals("AND")) {
			CTNode leftNode = visit(e.left);
			CTNode rightNode = visit(e.right);
			CTNodeBinaryOp op = CTNodeBinaryOp.fromName(opName);
			returnNode = new CTAndNode(op);
			returnNode.addChildNode(leftNode);
			returnNode.addChildNode(rightNode);
		} else if (opName.equals("OR")) {
			CTNode leftNode = visit(e.left);
			CTNode rightNode = visit(e.right);
			CTNodeBinaryOp op = CTNodeBinaryOp.fromName(opName);
			returnNode = new CTAndNode(op);
			returnNode.addChildNode(leftNode);
			returnNode.addChildNode(rightNode);
		} else if (opName.equals("IMPLIES")) {
			// (a => b) = (not a or b)
			Expr newLeft = negateExprVisitor.visit(e.left);
			// then visit the new expression
			CTNode leftNode = visit(newLeft);
			CTNode rightNode = visit(e.right);
			CTNodeBinaryOp op = CTNodeBinaryOp.fromName("OR");
			returnNode = new CTOrNode(op);
			returnNode.addChildNode(leftNode);
			returnNode.addChildNode(rightNode);
		} else if (opName.equals("ARROW")) {
			returnNode = visitArrowOpExpr(e);
		} else if (opName.equals("EQUAL") || opName.equals("NOTEQUAL") || opName.equals("GREATER")
				|| opName.equals("LESS") || opName.equals("GREATEREQUAL") || opName.equals("LESSEQUAL")) {
			returnNode = visitRelationalOpExpr(e);
		} else if (opName.equals("PLUS") || opName.equals("MINUS") || opName.equals("MULTIPLY")
				|| opName.equals("DIVIDE")) {
			returnNode = visitArithmeticOpExpr(e);
		} else {
			// not supported
			throw new SafetyException("Expr not supported " + e.toString());
		}
		return returnNode;
	}

	@Override
	public CTNode visit(IfThenElseExpr e) {
		// if a then b else c = (a=>b) and (not a => c)
		// = (not a or b) and (a or c)
		CTNode returnNode = null;
		CTNode condNode = visit(e.cond);
		Expr negCondExpr = negateExprVisitor.visit(e.cond);
		CTNode negCondNode = visit(negCondExpr);
		CTNode thenCondNode = visit(e.thenExpr);
		CTNode elseCondNode = visit(e.elseExpr);
		CTNodeBinaryOp orOp = CTNodeBinaryOp.fromName("OR");
		CTNodeBinaryOp andOp = CTNodeBinaryOp.fromName("AND");
		CTOrNode leftNode = new CTOrNode(orOp);
		leftNode.addChildNode(negCondNode);
		leftNode.addChildNode(thenCondNode);
		CTOrNode rightNode = new CTOrNode(orOp);
		rightNode.addChildNode(condNode);
		rightNode.addChildNode(elseCondNode);
		returnNode = new CTAndNode(andOp);
		rightNode.addChildNode(leftNode);
		rightNode.addChildNode(rightNode);
		return returnNode;
	}

	@Override
	public CTNode visit(UnaryExpr e) {
		CTNode returnNode = null;
		String opName = e.op.name();
		if (opName.equals("NEGATIVE") || opName.equals("NOT")) {
			if (e.expr instanceof IdExpr) {
				// if e.expr is an Id expression
				// create an UnaryIdNode
				String id = ((IdExpr) e.expr).id;
				CTNodeUnaryOp notOp = CTNodeUnaryOp.fromName("NOT");
				returnNode = new CTUnaryIdNode(notOp, e);
				returnNode.idSet.add(id);
			} else {
				// if e.expr is not an Id expression
				// distribute the negation to associate with individual Id node
				Expr newExpr = negateExprVisitor.visit(e.expr);
				// then visit the new expression
				returnNode = visit(newExpr);
			}
		} else if (opName.equals("PRE")) {
			CTNode exprNode = visit(e.expr);
			if (exprNode instanceof CTBottomNode) {
				if (exprNode instanceof CTIdNode) {
					CTNodeUnaryOp preOp = CTNodeUnaryOp.fromName("PRE");
					String id = ((CTIdNode) exprNode).idName;
					// returnNode = new CTUnaryIdNode(preOp, ((CTBottomNode) exprNode).expr);
					returnNode = new CTUnaryIdNode(preOp, e);
					returnNode.addId(id);
				} else if (exprNode instanceof CTUnaryIdNode) {
					CTNodeUnaryOp preOp = CTNodeUnaryOp.fromName("PRE");
					String id = ((CTIdNode) exprNode).idName;
					// returnNode = new CTUnaryIdNode(preOp, ((CTBottomNode) exprNode).expr);
					returnNode = new CTUnaryIdNode(preOp, e);
					returnNode.addId(id);
				} else if (exprNode instanceof CTConstantNode) {
					throw new SafetyException("Expr not supported " + e.toString());
				} else if (exprNode instanceof CTBinaryIdNode) {
					throw new SafetyException("Expr not supported " + e.toString());
				}

			} else {
				// PRE operator should have been distributed to var in the lustre expression
				// of the lustre node
				throw new SafetyException("Expr not supported " + e.toString());
			}
		}
		return returnNode;
	}

	@Override
	public CTNode visit(BoolExpr e) {
		CTConstantNode returnNode = new CTConstantNode(e);
		return returnNode;
	}

	@Override
	public CTNode visit(IdExpr e) {
		CTIdNode returnNode = new CTIdNode(e, e.id);
		if (curFault != null) {
			if (e.id.equals(curFault.id)) {
				returnNode.setFault(curFault);
				returnNode.isFailure = true;
				returnNode.isLeaf = true;
			}
		}
		return returnNode;
	}

	@Override
	public CTNode visit(IntExpr e) {
		CTConstantNode returnNode = new CTConstantNode(e);
		return returnNode;
	}

	@Override
	public CTNode visit(RealExpr e) {
		CTConstantNode returnNode = new CTConstantNode(e);
		return returnNode;
	}

	@Override
	public CTNode visit(FunctionCallExpr e) {
		throw new SafetyException("Expr not supported " + e.toString());
	}

	@Override
	public CTNode visit(NodeCallExpr e) {
		throw new SafetyException("Expr not supported " + e.toString());
	}

	@Override
	public CTNode visit(CastExpr e) {
		// not supported
		throw new SafetyException("Expr not supported " + e.toString());
	}

	@Override
	public CTNode visit(CondactExpr e) {
		// not supported
		throw new SafetyException("Expr not supported " + e.toString());
	}

	@Override
	public CTNode visit(RecordAccessExpr e) {
		// not supported
		throw new SafetyException("Expr not supported " + e.toString());
	}

	@Override
	public CTNode visit(RecordExpr e) {
		// not supported
		throw new SafetyException("Expr not supported " + e.toString());
	}

	@Override
	public CTNode visit(RecordUpdateExpr e) {
		// not supported
		throw new SafetyException("Expr not supported " + e.toString());
	}

	@Override
	public CTNode visit(TupleExpr e) {
		// not supported
		throw new SafetyException("Expr not supported " + e.toString());
	}

	@Override
	public CTNode visit(ArrayAccessExpr e) {
		// not supported
		throw new SafetyException("Expr not supported " + e.toString());
	}

	@Override
	public CTNode visit(ArrayExpr e) {
		// not supported
		throw new SafetyException("Expr not supported " + e.toString());
	}

	@Override
	public CTNode visit(ArrayUpdateExpr e) {
		// not supported
		throw new SafetyException("Expr not supported " + e.toString());
	}

	private CTNode visitArrowOpExpr(BinaryExpr e) {
		CTNode returnNode = null;
		// (a -> b ) = (FIRST_STEP(a) and BEYOND_FIRST_STEP(b))
		CTNodeUnaryOp initOp = CTNodeUnaryOp.fromName("FIRST_STEP");
		CTNodeUnaryOp nonInitOp = CTNodeUnaryOp.fromName("BEYOND_FIRST_STEP");

		CTNode leftNode = visit(e.left);
		CTNode rightNode = visit(e.right);

		CTBottomNode newLeftNode = null;
		CTBottomNode newRightNode = null;

		if ((leftNode instanceof CTNonBottomNode) || (rightNode instanceof CTNonBottomNode)) {
			throw new SafetyException("Expr not supported " + e.toString());
		} else {
			if (leftNode instanceof CTIdNode) {
				String leftId = ((CTIdNode) leftNode).idName;
				newLeftNode = new CTUnaryIdNode(initOp, e.left);
				newLeftNode.addId(leftId);
			} else if (leftNode instanceof CTUnaryIdNode) {
				newLeftNode = new CTUnaryIdNode(initOp, e.left);
				newLeftNode.addIds(leftNode.idSet);
			} else if (leftNode instanceof CTConstantNode) {
				newLeftNode = new CTConstantNode(e.left);
			} else if (leftNode instanceof CTBinaryIdNode) {
				newLeftNode = new CTUnaryIdNode(initOp, e.left);
				newLeftNode.addIds(leftNode.idSet);
			}

			if (rightNode instanceof CTIdNode) {
				String rightId = ((CTIdNode) rightNode).idName;
				newRightNode = new CTUnaryIdNode(nonInitOp, e.right);
				newRightNode.addId(rightId);
			} else if (rightNode instanceof CTUnaryIdNode) {
				newRightNode = new CTUnaryIdNode(nonInitOp, e.right);
				newRightNode.addIds(rightNode.idSet);
			} else if (rightNode instanceof CTConstantNode) {
				newRightNode = new CTConstantNode(e.right);
			} else if (rightNode instanceof CTBinaryIdNode) {
				newRightNode = new CTUnaryIdNode(nonInitOp, e.right);
				newRightNode.addIds(rightNode.idSet);
			}
		}

		CTNodeBinaryOp op = CTNodeBinaryOp.fromName("AND");
		returnNode = new CTAndNode(op);
		returnNode.addChildNode(newLeftNode);
		returnNode.addChildNode(newRightNode);

		return returnNode;
	}

	private CTNode visitRelationalOpExpr(BinaryExpr e) {
		CTNode returnNode = null;
		CTNode leftNode = visit(e.left);
		CTNode rightNode = visit(e.right);
		if ((leftNode instanceof CTNonBottomNode) || (rightNode instanceof CTNonBottomNode)) {
			throw new SafetyException("Expr not supported " + e.toString());
		} else {
			if ((leftNode instanceof CTConstantNode) && (rightNode instanceof CTConstantNode)) {
				returnNode = new CTConstantNode(e);
			} else {
				returnNode = new CTBinaryIdNode(e);
				returnNode.addIds(leftNode.idSet);
				returnNode.addIds(rightNode.idSet);
			}
		}
		return returnNode;
	}

	private CTNode visitArithmeticOpExpr(BinaryExpr e) {
		CTNode returnNode = null;
		CTNode leftNode = visit(e.left);
		CTNode rightNode = visit(e.right);
		if ((leftNode instanceof CTNonBottomNode) || (rightNode instanceof CTNonBottomNode)) {
			throw new IllegalArgumentException();
		} else {
			if ((leftNode instanceof CTConstantNode) && (rightNode instanceof CTConstantNode)) {
				returnNode = new CTConstantNode(e);
			} else {
				throw new SafetyException("Expr not supported " + e.toString());
			}
		}
		return returnNode;
	}
}
