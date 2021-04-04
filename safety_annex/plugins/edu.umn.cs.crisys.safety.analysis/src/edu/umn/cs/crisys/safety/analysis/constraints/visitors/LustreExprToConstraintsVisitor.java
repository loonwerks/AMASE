package edu.umn.cs.crisys.safety.analysis.constraints.visitors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.umn.cs.crisys.safety.analysis.SafetyException;
import edu.umn.cs.crisys.safety.analysis.ast.visitors.NegateLustreExprVisitor;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.BinaryTermConstraintDef;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.BinaryTermConstraintOp;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.Constraint;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.ConstraintListCombo;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.ExprConstraintDef;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.IntConstantTermDef;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.MistralConstraint;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.Term;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.VariableTermDef;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.expr.ConstraintBinaryExpr;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.expr.ConstraintBinaryOp;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.expr.SingleConstraintExpr;
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

public class LustreExprToConstraintsVisitor implements ExprVisitor<ConstraintListCombo> {

	private NegateLustreExprVisitor negateExprVisitor = new NegateLustreExprVisitor();
	private Map<String, Constraint> varConstraintMap = new HashMap<>();

	public ConstraintListCombo visit(Expr expr) {
		return expr.accept(this);
	}

	@Override
	public ConstraintListCombo visit(BinaryExpr e) {
		List<MistralConstraint> constraints = new ArrayList<MistralConstraint>();
		String opName = e.op.name();
		if (opName.equals("AND")) {
			return createBinaryExprConstraint(opName, e, constraints);
		}
		else if (opName.equals("OR")) {
			return createBinaryExprConstraint(opName, e, constraints);
		}
//		else if (opName.equals("IMPLIES")) {
//			// (a => b) = (not a or b)
//			Expr newLeft = negateExprVisitor.visit(e.left);
//			// then visit the new expression
//			CTNode leftNode = visit(newLeft);
//			CTNode rightNode = visit(e.right);
//			CTNodeBinaryOp op = CTNodeBinaryOp.fromName("OR");
//			returnNode = new CTOrNode(op);
//			returnNode.addChildNode(leftNode);
//			returnNode.addChildNode(rightNode);
//		} else if (opName.equals("ARROW")) {
//			returnNode = visitArrowOpExpr(e);
//		} else if (opName.equals("EQUAL") || opName.equals("NOTEQUAL") || opName.equals("GREATER")
//				|| opName.equals("LESS") || opName.equals("GREATEREQUAL") || opName.equals("LESSEQUAL")) {
//			returnNode = visitRelationalOpExpr(e);
//		} else if (opName.equals("PLUS") || opName.equals("MINUS") || opName.equals("MULTIPLY")
//				|| opName.equals("DIVIDE")) {
//			returnNode = visitArithmeticOpExpr(e);
//		} else {
//			// not supported
//			throw new SafetyException("Expr not supported " + e.toString());
//		}
		return null;
	}

	private ConstraintListCombo createBinaryExprConstraint(String opName, BinaryExpr e,
			List<MistralConstraint> constraints) {
		ConstraintListCombo leftReturnCombo = visit(e.left);
		ConstraintListCombo rightReturnCombo = visit(e.right);
		Constraint leftConstraint = leftReturnCombo.lastConstraint;
		Constraint rightConstraint = rightReturnCombo.lastConstraint;
		List<MistralConstraint> leftConstraintList = leftReturnCombo.constraintList;
		List<MistralConstraint> rightConstraintList = rightReturnCombo.constraintList;
		// TODO: revisit naming here
		String andConstraintName = leftConstraint.constraintId + "_" + opName + "_" + rightConstraint.constraintId;

		SingleConstraintExpr leftConstraintExpr = new SingleConstraintExpr(leftConstraint);
		SingleConstraintExpr rightConstraintExpr = new SingleConstraintExpr(rightConstraint);


		ConstraintBinaryExpr binaryConstraintExpr = new ConstraintBinaryExpr(leftConstraintExpr,
				ConstraintBinaryOp.fromName(opName), rightConstraintExpr);
		// create constraint def
		ExprConstraintDef exprConstraintDef = new ExprConstraintDef(andConstraintName, binaryConstraintExpr);
		constraints.addAll(leftConstraintList);
		constraints.addAll(rightConstraintList);
		constraints.add(exprConstraintDef);
		// create constraint for reference
		Constraint binaryConstraint = new Constraint(andConstraintName);
		ConstraintListCombo combo = new ConstraintListCombo(binaryConstraint, constraints);
		return combo;
	}

	@Override
	public ConstraintListCombo visit(IfThenElseExpr e) {
		List<MistralConstraint> constraints = new ArrayList<MistralConstraint>();
//		// if a then b else c = (a=>b) and (not a => c)
//		// = (not a or b) and (a or c)
//		CTNode returnNode = null;
//		CTNode condNode = visit(e.cond);
//		Expr negCondExpr = negateExprVisitor.visit(e.cond);
//		CTNode negCondNode = visit(negCondExpr);
//		CTNode thenCondNode = visit(e.thenExpr);
//		CTNode elseCondNode = visit(e.elseExpr);
//		CTNodeBinaryOp orOp = CTNodeBinaryOp.fromName("OR");
//		CTNodeBinaryOp andOp = CTNodeBinaryOp.fromName("AND");
//		CTOrNode leftNode = new CTOrNode(orOp);
//		leftNode.addChildNode(negCondNode);
//		leftNode.addChildNode(thenCondNode);
//		CTOrNode rightNode = new CTOrNode(orOp);
//		rightNode.addChildNode(condNode);
//		rightNode.addChildNode(elseCondNode);
//		returnNode = new CTAndNode(andOp);
//		rightNode.addChildNode(leftNode);
//		rightNode.addChildNode(rightNode);
		return null;
	}

	@Override
	public ConstraintListCombo visit(UnaryExpr e) {
		List<MistralConstraint> constraints = new ArrayList<MistralConstraint>();
		String opName = e.op.name();
		if (opName.equals("NEGATIVE") || opName.equals("NOT")) {
			if (e.expr instanceof IdExpr) {
//				// if e.expr is an Id expression
//				// visit the Id expression
//				ConstraintListCombo returnCombo = visit(e.expr);
//				// get the return constraint and negate it to create a new constraint
//				MistralConstraint idConstraint = returnCombo.lastConstraint;
//				List<MistralConstraint> constraintList = returnCombo.constraintList;
//				String negateIdConstraintName = "not_" + idConstraint.constraintId;
//				SingleConstraintExpr negateIdConstraintExpr = new SingleConstraintExpr(idConstraint);
//				ConstraintUnaryExpr notIdExpr = new ConstraintUnaryExpr(ConstraintUnaryOp.fromName("NOT"),
//						negateIdConstraintExpr);
//				ExprConstraint exprConstraint = new ExprConstraint(negateIdConstraintName, notIdExpr);
//				constraints.addAll(constraintList);
//				constraints.add(exprConstraint);
//				ConstraintListCombo combo = new ConstraintListCombo(exprConstraint, constraints);
//				return combo;
				return null;
			} else {
				// if e.expr is not an Id expression
				// distribute the negation to associate with individual Id node
				Expr newExpr = negateExprVisitor.visit(e.expr);
				// then visit the new expression
				return visit(newExpr);
			}
		} else if (opName.equals("PRE")) {
			throw new SafetyException("Expr not supported " + e.toString());
//			CTNode exprNode = visit(e.expr);
//			if (exprNode instanceof CTBottomNode) {
//				if (exprNode instanceof CTIdNode) {
//					CTNodeUnaryOp preOp = CTNodeUnaryOp.fromName("PRE");
//					String id = ((CTIdNode) exprNode).idName;
//					// returnNode = new CTUnaryIdNode(preOp, ((CTBottomNode) exprNode).expr);
//					returnNode = new CTUnaryIdNode(preOp, e);
//					returnNode.addId(id);
//				} else if (exprNode instanceof CTUnaryIdNode) {
//					CTNodeUnaryOp preOp = CTNodeUnaryOp.fromName("PRE");
//					String id = ((CTIdNode) exprNode).idName;
//					// returnNode = new CTUnaryIdNode(preOp, ((CTBottomNode) exprNode).expr);
//					returnNode = new CTUnaryIdNode(preOp, e);
//					returnNode.addId(id);
//				} else if (exprNode instanceof CTConstantNode) {
//					throw new SafetyException("Expr not supported " + e.toString());
//				} else if (exprNode instanceof CTBinaryIdNode) {
//					throw new SafetyException("Expr not supported " + e.toString());
//				}
//
//			} else {
//				// PRE operator should have been distributed to var in the lustre expression
//				// of the lustre node
//				throw new SafetyException("Expr not supported " + e.toString());
//			}
		}
		return null;
	}

	@Override
	public ConstraintListCombo visit(BoolExpr e) {
		List<MistralConstraint> constraints = new ArrayList<MistralConstraint>();
//		CTConstantNode returnNode = new CTConstantNode(e);
		return null;
	}

	@Override
	public ConstraintListCombo visit(IdExpr e) {
		List<MistralConstraint> constraints = new ArrayList<MistralConstraint>();
		// if ID already defined, just reference the term
		if (varConstraintMap.containsKey(e.id)) {
			ConstraintListCombo combo = new ConstraintListCombo(varConstraintMap.get(e.id), constraints);
			return combo;
		}
		// otherwise create term def and constraint for assignment
		else {
			String idName = e.id;
			String termName = e.id + "_";
			//create term def
			VariableTermDef varTermDef = new VariableTermDef(termName, idName);
			constraints.add(varTermDef);
			//create term for reference
			Term varTerm = new Term(termName);
			// TODO: create a map for the type of the Id, to know what value to assign in the constraint
			// for now assign it to 1
			IntConstantTermDef intConstTermDef = new IntConstantTermDef("1", 1);
			// create constraint for the term
			BinaryTermConstraintDef binaryTermConstraintDef = new BinaryTermConstraintDef(idName, varTerm,
					intConstTermDef,
					BinaryTermConstraintOp.fromName("ATOM_EQ"));
			constraints.add(binaryTermConstraintDef);
			// create constraint for reference
			Constraint varConstraint = new Constraint(idName);
			//add to map
			varConstraintMap.put(termName, varConstraint);

			ConstraintListCombo combo = new ConstraintListCombo(varConstraint, constraints);
			return combo;
		}
	}

	@Override
	public ConstraintListCombo visit(IntExpr e) {
		List<MistralConstraint> constraints = new ArrayList<MistralConstraint>();
//		CTConstantNode returnNode = new CTConstantNode(e);
		return null;
	}

	@Override
	public ConstraintListCombo visit(RealExpr e) {
		List<MistralConstraint> constraints = new ArrayList<MistralConstraint>();
//		CTConstantNode returnNode = new CTConstantNode(e);
		return null;
	}

	@Override
	public ConstraintListCombo visit(FunctionCallExpr e) {
		throw new SafetyException("Expr not supported " + e.toString());
	}

	@Override
	public ConstraintListCombo visit(NodeCallExpr e) {
		throw new SafetyException("Expr not supported " + e.toString());
	}

	@Override
	public ConstraintListCombo visit(CastExpr e) {
		// not supported
		throw new SafetyException("Expr not supported " + e.toString());
	}

	@Override
	public ConstraintListCombo visit(CondactExpr e) {
		// not supported
		throw new SafetyException("Expr not supported " + e.toString());
	}

	@Override
	public ConstraintListCombo visit(RecordAccessExpr e) {
		// not supported
		throw new SafetyException("Expr not supported " + e.toString());
	}

	@Override
	public ConstraintListCombo visit(RecordExpr e) {
		// not supported
		throw new SafetyException("Expr not supported " + e.toString());
	}

	@Override
	public ConstraintListCombo visit(RecordUpdateExpr e) {
		// not supported
		throw new SafetyException("Expr not supported " + e.toString());
	}

	@Override
	public ConstraintListCombo visit(TupleExpr e) {
		// not supported
		throw new SafetyException("Expr not supported " + e.toString());
	}

	@Override
	public ConstraintListCombo visit(ArrayAccessExpr e) {
		// not supported
		throw new SafetyException("Expr not supported " + e.toString());
	}

	@Override
	public ConstraintListCombo visit(ArrayExpr e) {
		// not supported
		throw new SafetyException("Expr not supported " + e.toString());
	}

	@Override
	public ConstraintListCombo visit(ArrayUpdateExpr e) {
		// not supported
		throw new SafetyException("Expr not supported " + e.toString());
	}

//	private Constraint visitArrowOpExpr(BinaryExpr e) {
//		CTNode returnNode = null;
//		// (a -> b ) = (FIRST_STEP(a) and BEYOND_FIRST_STEP(b))
//		CTNodeUnaryOp initOp = CTNodeUnaryOp.fromName("FIRST_STEP");
//		CTNodeUnaryOp nonInitOp = CTNodeUnaryOp.fromName("BEYOND_FIRST_STEP");
//
//		CTNode leftNode = visit(e.left);
//		CTNode rightNode = visit(e.right);
//
//		CTBottomNode newLeftNode = null;
//		CTBottomNode newRightNode = null;
//
//		if ((leftNode instanceof CTNonBottomNode) || (rightNode instanceof CTNonBottomNode)) {
//			throw new SafetyException("Expr not supported " + e.toString());
//		} else {
//			if (leftNode instanceof CTIdNode) {
//				String leftId = ((CTIdNode) leftNode).idName;
//				newLeftNode = new CTUnaryIdNode(initOp, e.left);
//				newLeftNode.addId(leftId);
//			} else if (leftNode instanceof CTUnaryIdNode) {
//				newLeftNode = new CTUnaryIdNode(initOp, e.left);
//				newLeftNode.addIds(leftNode.idSet);
//			} else if (leftNode instanceof CTConstantNode) {
//				newLeftNode = new CTConstantNode(e.left);
//			} else if (leftNode instanceof CTBinaryIdNode) {
//				newLeftNode = new CTUnaryIdNode(initOp, e.left);
//				newLeftNode.addIds(leftNode.idSet);
//			}
//
//			if (rightNode instanceof CTIdNode) {
//				String rightId = ((CTIdNode) rightNode).idName;
//				newRightNode = new CTUnaryIdNode(nonInitOp, e.right);
//				newRightNode.addId(rightId);
//			} else if (rightNode instanceof CTUnaryIdNode) {
//				newRightNode = new CTUnaryIdNode(nonInitOp, e.right);
//				newRightNode.addIds(rightNode.idSet);
//			} else if (rightNode instanceof CTConstantNode) {
//				newRightNode = new CTConstantNode(e.right);
//			} else if (rightNode instanceof CTBinaryIdNode) {
//				newRightNode = new CTUnaryIdNode(nonInitOp, e.right);
//				newRightNode.addIds(rightNode.idSet);
//			}
//		}
//
//		CTNodeBinaryOp op = CTNodeBinaryOp.fromName("AND");
//		returnNode = new CTAndNode(op);
//		returnNode.addChildNode(newLeftNode);
//		returnNode.addChildNode(newRightNode);
//
//		return returnNode;
//	}
//
//	private Constraint visitRelationalOpExpr(BinaryExpr e) {
//		CTNode returnNode = null;
//		CTNode leftNode = visit(e.left);
//		CTNode rightNode = visit(e.right);
//		if ((leftNode instanceof CTNonBottomNode) || (rightNode instanceof CTNonBottomNode)) {
//			throw new SafetyException("Expr not supported " + e.toString());
//		} else {
//			if ((leftNode instanceof CTConstantNode) && (rightNode instanceof CTConstantNode)) {
//				returnNode = new CTConstantNode(e);
//			} else {
//				returnNode = new CTBinaryIdNode(e);
//				// TODO: the following code were used where id from eq variables
//				// got updated to turn comp_name___id_name to id_name
////				Expr leftExpr = e.left;
////				Expr rightExpr = e.right;
////				BinaryOp op = e.op;
////				// The Id nodes may get updated from the above visit
////				// Need to use the updated IdStr in the expression for the node to return
////				if (leftNode instanceof CTIdNode) {
////					leftExpr = ((CTIdNode) leftNode).expr;
////				}
////				if (rightNode instanceof CTIdNode) {
////					rightExpr = ((CTIdNode) rightNode).expr;
////				}
////				BinaryExpr updatedExpr = new BinaryExpr(leftExpr, op, rightExpr);
////				returnNode = new CTBinaryIdNode(updatedExpr);
//				returnNode.addIds(leftNode.idSet);
//				returnNode.addIds(rightNode.idSet);
//			}
//		}
//		return returnNode;
//	}
//
//	private Constraint visitArithmeticOpExpr(BinaryExpr e) {
//		CTNode returnNode = null;
//		CTNode leftNode = visit(e.left);
//		CTNode rightNode = visit(e.right);
//		if ((leftNode instanceof CTNonBottomNode) || (rightNode instanceof CTNonBottomNode)) {
//			throw new IllegalArgumentException();
//		} else {
//			if ((leftNode instanceof CTConstantNode) && (rightNode instanceof CTConstantNode)) {
//				returnNode = new CTConstantNode(e);
//			} else {
//				throw new SafetyException("Expr not supported " + e.toString());
//			}
//		}
//		return returnNode;
//	}
}
