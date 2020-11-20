package edu.umn.cs.crisys.safety.analysis.ast.visitors;

import java.util.ArrayList;
import java.util.List;

import edu.umn.cs.crisys.safety.analysis.causationTree.CTAndNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTDivideNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTEqualNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTFalseNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTGreaterEqualNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTGreaterNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTIdNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTInitNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTIntNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTLessEqualNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTLessNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTMinusNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTMultiplyNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTNonEqualNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTNonInitNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTOrNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTPlusNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTPreNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTRealNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTTrueNode;
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

//	private Node lustreNode;
//	private AgreeNode agreeNode;
//	private ComponentInstance compInst;
//	private AgreeProgram agreeProgram;
	public List<CTNode> leafNodes = new ArrayList<CTNode>();

	private NegateLustreExprVisitor negateExprVisitor = new NegateLustreExprVisitor();

//	public LustreExprToCTVisitor(Node lustreNode, AgreeNode agreeNode, ComponentInstance compInst,
//			AgreeProgram agreeProgram) {
//		this.lustreNode = lustreNode;
//		this.agreeNode = agreeNode;
//		this.compInst = compInst;
//		this.agreeProgram = agreeProgram;
//	}

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
			returnNode = new CTAndNode(leftNode.nodeName + " AND " + rightNode.nodeName);
			returnNode.addChildNode(leftNode.nodeName, leftNode);
			returnNode.addChildNode(rightNode.nodeName, rightNode);
		} else if (opName.equals("OR")) {
			CTNode leftNode = visit(e.left);
			CTNode rightNode = visit(e.right);
			returnNode = new CTOrNode(leftNode.nodeName + " OR " + rightNode.nodeName);
			returnNode.addChildNode(leftNode.nodeName, leftNode);
			returnNode.addChildNode(rightNode.nodeName, rightNode);
		} else if (opName.equals("IMPLIES")) {
			Expr newLeft = negateExprVisitor.visit(e.left);
			// then visit the new expression
			CTNode leftNode = visit(newLeft);
			CTNode rightNode = visit(e.right);
			returnNode = new CTAndNode(leftNode.nodeName + " OR " + rightNode.nodeName);
			returnNode.addChildNode(leftNode.nodeName, leftNode);
			returnNode.addChildNode(rightNode.nodeName, rightNode);
		} else if (opName.equals("ARROW")) {
			// need to apply init to the left node
			// and apply non init to the right node
			CTNode leftNode = visit(e.left);
			CTNode rightNode = visit(e.right);
			CTInitNode initNode = new CTInitNode(leftNode.nodeName);
			initNode.addChildNode(leftNode.nodeName, leftNode);
			CTNonInitNode nonInitNode = new CTNonInitNode(rightNode.nodeName);
			nonInitNode.addChildNode(rightNode.nodeName, rightNode);
			returnNode = new CTAndNode(initNode.nodeName + " AND " + nonInitNode.nodeName);
			returnNode.addChildNode(initNode.nodeName, initNode);
			returnNode.addChildNode(nonInitNode.nodeName, nonInitNode);
		} else if (opName.equals("EQUAL")) {
			CTNode leftNode = visit(e.left);
			CTNode rightNode = visit(e.right);
			returnNode = new CTEqualNode(leftNode.nodeName + " = " + rightNode.nodeName);
			returnNode.addChildNode(leftNode.nodeName, leftNode);
			returnNode.addChildNode(rightNode.nodeName, rightNode);
		} else if (opName.equals("NOTEQUAL")) {
			CTNode leftNode = visit(e.left);
			CTNode rightNode = visit(e.right);
			returnNode = new CTNonEqualNode(leftNode.nodeName + " != " + rightNode.nodeName);
			returnNode.addChildNode(leftNode.nodeName, leftNode);
			returnNode.addChildNode(rightNode.nodeName, rightNode);
		} else if (opName.equals("GREATER")) {
			CTNode leftNode = visit(e.left);
			CTNode rightNode = visit(e.right);
			returnNode = new CTGreaterNode(leftNode.nodeName + " > " + rightNode.nodeName);
			returnNode.addChildNode(leftNode.nodeName, leftNode);
			returnNode.addChildNode(rightNode.nodeName, rightNode);
		} else if (opName.equals("LESS")) {
			CTNode leftNode = visit(e.left);
			CTNode rightNode = visit(e.right);
			returnNode = new CTLessNode(leftNode.nodeName + " < " + rightNode.nodeName);
			returnNode.addChildNode(leftNode.nodeName, leftNode);
			returnNode.addChildNode(rightNode.nodeName, rightNode);
		} else if (opName.equals("GREATEREQUAL")) {
			CTNode leftNode = visit(e.left);
			CTNode rightNode = visit(e.right);
			returnNode = new CTGreaterEqualNode(leftNode.nodeName + " >= " + rightNode.nodeName);
			returnNode.addChildNode(leftNode.nodeName, leftNode);
			returnNode.addChildNode(rightNode.nodeName, rightNode);
		} else if (opName.equals("LESSEQUAL")) {
			CTNode leftNode = visit(e.left);
			CTNode rightNode = visit(e.right);
			returnNode = new CTLessEqualNode(leftNode.nodeName + " <= " + rightNode.nodeName);
			returnNode.addChildNode(leftNode.nodeName, leftNode);
			returnNode.addChildNode(rightNode.nodeName, rightNode);
		} else if (opName.equals("PLUS")) {
			CTNode leftNode = visit(e.left);
			CTNode rightNode = visit(e.right);
			returnNode = new CTPlusNode(leftNode.nodeName + " + " + rightNode.nodeName);
			returnNode.addChildNode(leftNode.nodeName, leftNode);
			returnNode.addChildNode(rightNode.nodeName, rightNode);
		} else if (opName.equals("MINUS")) {
			CTNode leftNode = visit(e.left);
			CTNode rightNode = visit(e.right);
			returnNode = new CTMinusNode(leftNode.nodeName + " - " + rightNode.nodeName);
			returnNode.addChildNode(leftNode.nodeName, leftNode);
			returnNode.addChildNode(rightNode.nodeName, rightNode);
		} else if (opName.equals("MULTIPLY")) {
			CTNode leftNode = visit(e.left);
			CTNode rightNode = visit(e.right);
			returnNode = new CTMultiplyNode(leftNode.nodeName + " * " + rightNode.nodeName);
			returnNode.addChildNode(leftNode.nodeName, leftNode);
			returnNode.addChildNode(rightNode.nodeName, rightNode);
		} else if (opName.equals("DIVIDE")) {
			CTNode leftNode = visit(e.left);
			CTNode rightNode = visit(e.right);
			returnNode = new CTDivideNode(leftNode.nodeName + " / " + rightNode.nodeName);
			returnNode.addChildNode(leftNode.nodeName, leftNode);
			returnNode.addChildNode(rightNode.nodeName, rightNode);
		} else {
			// not supported
			throw new IllegalArgumentException();
		}
		return returnNode;
	}

	@Override
	public CTNode visit(UnaryExpr e) {
		CTNode returnNode = null;
		String opName = e.op.name();
		if (opName.equals("NEGATIVE") || opName.equals("NOT")) {
			// distribute the negation to associate with individual Id node
			Expr newExpr = negateExprVisitor.visit(e);
			// then visit the new expression
			returnNode = visit(newExpr);
		} else if (opName.equals("PRE")) {
			CTNode childNode = visit(e.expr);
			returnNode = new CTPreNode("Previous Step" + childNode.nodeName);
			returnNode.addChildNode(childNode.nodeName, childNode);
		}
		return returnNode;
	}

	@Override
	public CTNode visit(IfThenElseExpr e) {
		// if a then b else c = (a=>b) and (not a => c)
		// not(if a then b else c)
		// = not(a=>b) or not(not a=>c)
		// = (a and (not b)) or (not a and c)
		CTNode returnNode = null;
		CTNode condNode = visit(e.cond);
		Expr negCondExpr = negateExprVisitor.visit(e.cond);
		CTNode negCondNode = visit(negCondExpr);
		Expr negThenCondExpr = negateExprVisitor.visit(e.thenExpr);
		CTNode negThenCondNode = visit(negThenCondExpr);
		CTNode elseCondNode = visit(e.elseExpr);
		CTAndNode leftNode = new CTAndNode(condNode.nodeName + " AND " + negThenCondNode.nodeName);
		leftNode.addChildNode(condNode.nodeName, condNode);
		leftNode.addChildNode(negThenCondNode.nodeName, negThenCondNode);
		CTAndNode rightNode = new CTAndNode(negCondNode.nodeName + " AND " + elseCondNode.nodeName);
		rightNode.addChildNode(negCondNode.nodeName, negCondNode);
		rightNode.addChildNode(elseCondNode.nodeName, elseCondNode);
		returnNode = new CTOrNode(leftNode.nodeName + " OR " + rightNode.nodeName);
		rightNode.addChildNode(leftNode.nodeName, leftNode);
		rightNode.addChildNode(rightNode.nodeName, rightNode);
		return returnNode;
	}

	@Override
	public CTNode visit(BoolExpr e) {
		CTNode returnNode = null;
		if (e.value) {
			returnNode = new CTTrueNode("TRUE");
		} else {
			returnNode = new CTFalseNode("FALSE");
		}
		return returnNode;
	}

	@Override
	public CTNode visit(IdExpr e) {
		CTIdNode returnNode = new CTIdNode(e.id);
		leafNodes.add(returnNode);
		return returnNode;
	}

	@Override
	public CTNode visit(IntExpr e) {
		CTIntNode returnNode = new CTIntNode(e.value.toString(), e.value);
		return returnNode;
	}

	@Override
	public CTNode visit(RealExpr e) {
		CTRealNode returnNode = new CTRealNode(e.value.toString(), e.value);
		return returnNode;
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
	public CTNode visit(CastExpr e) {
		// not supported
		throw new IllegalArgumentException();
	}

	@Override
	public CTNode visit(CondactExpr e) {
		// not supported
		throw new IllegalArgumentException();
	}

	@Override
	public CTNode visit(RecordAccessExpr e) {
		// not supported
		throw new IllegalArgumentException();
	}

	@Override
	public CTNode visit(RecordExpr e) {
		// not supported
		throw new IllegalArgumentException();
	}

	@Override
	public CTNode visit(RecordUpdateExpr e) {
		// not supported
		throw new IllegalArgumentException();
	}

	@Override
	public CTNode visit(TupleExpr e) {
		// not supported
		throw new IllegalArgumentException();
	}

	@Override
	public CTNode visit(ArrayAccessExpr e) {
		// not supported
		throw new IllegalArgumentException();
	}

	@Override
	public CTNode visit(ArrayExpr e) {
		// not supported
		throw new IllegalArgumentException();
	}

	@Override
	public CTNode visit(ArrayUpdateExpr e) {
		// not supported
		throw new IllegalArgumentException();
	}
}
