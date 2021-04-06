package edu.umn.cs.crisys.safety.analysis.constraints.visitors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import edu.umn.cs.crisys.safety.analysis.SafetyException;
import edu.umn.cs.crisys.safety.analysis.ast.visitors.NegateLustreExprVisitor;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.BinaryTermConstraintDef;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.BinaryTermConstraintOp;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.BooleanConstantConstraintDef;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.Constraint;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.ConstraintListCombo;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.ExprConstraintDef;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.IntConstantTermDef;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.MistralConstraint;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.Term;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.VariableTermDef;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.expr.ConstraintBinaryExpr;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.expr.ConstraintBinaryOp;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.expr.ConstraintUnaryExpr;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.expr.ConstraintUnaryOp;
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
	private Map<String, Constraint> compExprConstraintMap = new HashMap<>();
	private String nodeNamePrefix = "";
	private int nameIndex = 0;
	private HashSet<String> constraintNames = new HashSet<>();

	public void resetNameIndex() {
		nameIndex = 0;
	}

	private String createUniqueConstraintName(String name) {
		String updatedName = name; // + "_" + varIndex;
		while (constraintNames.contains(updatedName)) {
			nameIndex++;
			updatedName = name + "_" + nameIndex;
		}
		constraintNames.add(updatedName);
		return updatedName;
	}

	public ConstraintListCombo visit(Expr expr) {
		return expr.accept(this);
	}

	public void setNodeNamePrefix(String nodeNamePrefix) {
		this.nodeNamePrefix = nodeNamePrefix;
	}

	public String getNodeNamePrefix() {
		return nodeNamePrefix;
	}

	public void clearCompExprConstraintMap() {
		this.compExprConstraintMap.clear();
		;
	}

	@Override
	public ConstraintListCombo visit(BinaryExpr e) {
		List<MistralConstraint> constraints = new ArrayList<MistralConstraint>();
		// if the entire expr already defined for within this node, just retrieve the constraint
		if (compExprConstraintMap.containsKey(e.toString())) {
			ConstraintListCombo combo = new ConstraintListCombo(compExprConstraintMap.get(e.toString()), constraints);
			return combo;
		} else {
			String opName = e.op.name();
			if (opName.equals("AND")) {
				return createBinaryExprConstraint(opName, e, e.left, e.right, constraints);
			} else if (opName.equals("OR")) {
				return createBinaryExprConstraint(opName, e, e.left, e.right, constraints);
			} else if (opName.equals("IMPLIES")) {
				// (a => b) <=> (not a or b)
				Expr newLeft = negateExprVisitor.visit(e.left);
				return createBinaryExprConstraint("OR", e, newLeft, e.right, constraints);
			} else if (opName.equals("ARROW")) {
				throw new SafetyException("Expr not supported " + e.toString());
			} else if (opName.equals("EQUAL") || opName.equals("NOTEQUAL") || opName.equals("GREATER")
					|| opName.equals("LESS") || opName.equals("GREATEREQUAL") || opName.equals("LESSEQUAL")) {
				return createBinaryExprConstraint(opName, e, e.left, e.right, constraints);
			} else if (opName.equals("PLUS") || opName.equals("MINUS") || opName.equals("MULTIPLY")
					|| opName.equals("DIVIDE")) {
				return createBinaryExprConstraint(opName, e, e.left, e.right, constraints);
			} else {
				// not supported
				throw new SafetyException("Expr not supported " + e.toString());
			}
		}
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
		throw new SafetyException("Expr not supported " + e.toString());
	}

	@Override
	public ConstraintListCombo visit(UnaryExpr e) {
		List<MistralConstraint> constraints = new ArrayList<MistralConstraint>();
		// if the entire expr already defined for within this node, just retrieve the constraint
		if (compExprConstraintMap.containsKey(e.toString())) {
			ConstraintListCombo combo = new ConstraintListCombo(compExprConstraintMap.get(e.toString()), constraints);
			return combo;
		}
		// otherwise create the constraint
		else {
			String opName = e.op.name();
			if (opName.equals("NEGATIVE") || opName.equals("NOT")) {
				if (e.expr instanceof IdExpr) {
					// if e.expr is an Id expression
					// visit the Id expression
					ConstraintListCombo returnCombo = visit(e.expr);
					Constraint returnConstraint = returnCombo.lastConstraint;
					List<MistralConstraint> returanConstraintList = returnCombo.constraintList;
					// create unique name
					String notConstraintName = createUniqueConstraintName("not_" + returnConstraint.constraintId);

					SingleConstraintExpr returnConstraintExpr = new SingleConstraintExpr(returnConstraint);

					ConstraintUnaryExpr unaryConstraintExpr = new ConstraintUnaryExpr(ConstraintUnaryOp.fromName("NOT"),
							returnConstraintExpr);
					// create constraint def
					ExprConstraintDef exprConstraintDef = new ExprConstraintDef(notConstraintName, unaryConstraintExpr);
					constraints.addAll(returanConstraintList);
					constraints.add(exprConstraintDef);
					// create constraint for reference
					Constraint unaryConstraint = new Constraint(notConstraintName);
					// add to map
					compExprConstraintMap.put(e.toString(), unaryConstraint);
					ConstraintListCombo combo = new ConstraintListCombo(unaryConstraint, constraints);
					return combo;
				} else {
					// if e.expr is not an Id expression
					// distribute the negation to associate with individual Id node
					Expr newExpr = negateExprVisitor.visit(e.expr);
					// then visit the new expression
					return visit(newExpr);
				}
			} else if (opName.equals("PRE")) {
				throw new SafetyException("Expr not supported " + e.toString());
			} else {
				throw new SafetyException("Expr not supported " + e.toString());
			}
		}
	}

	@Override
	public ConstraintListCombo visit(BoolExpr e) {
		List<MistralConstraint> constraints = new ArrayList<MistralConstraint>();

		// if expr already defined for within this node, just retrieve the constraint
		if (compExprConstraintMap.containsKey(e.toString())) {
			ConstraintListCombo combo = new ConstraintListCombo(compExprConstraintMap.get(e.toString()), constraints);
			return combo;
		}
		// otherwise create term & constraint def and assignments
		else {
			// create unique names with agree node name prefix if the name doesn't exist
			String constantConstraintName = createUniqueConstraintName(nodeNamePrefix + e.value);
			//create a BooleanConstantConstraintDef
			BooleanConstantConstraintDef constantConstraintDef = new BooleanConstantConstraintDef(constantConstraintName, e.value);
			constraints.add(constantConstraintDef);
			// create constraint for reference
			Constraint constantConstraint = new Constraint(constantConstraintName);
			// add to map
			compExprConstraintMap.put(e.toString(), constantConstraint);

			ConstraintListCombo combo = new ConstraintListCombo(constantConstraint, constraints);
			return combo;
		}
	}

	@Override
	public ConstraintListCombo visit(IdExpr e) {
		List<MistralConstraint> constraints = new ArrayList<MistralConstraint>();
		// if expr already defined for within this node, just retrieve the constraint
		if (compExprConstraintMap.containsKey(e.toString())) {
			ConstraintListCombo combo = new ConstraintListCombo(compExprConstraintMap.get(e.toString()), constraints);
			return combo;
		}
		// otherwise create term & constraint def and assignments
		else {
			// create unique names with agree node name prefix if the name doesn't exist
			String idName = createUniqueConstraintName(nodeNamePrefix + e.id);
			String termName = createUniqueConstraintName(nodeNamePrefix + e.id + "_term");
			// create term def
			VariableTermDef varTermDef = new VariableTermDef(termName, idName);
			constraints.add(varTermDef);
			// create term for reference
			Term varTerm = new Term(termName);
			// TODO: create a map for the type of the Id, to know what value to assign in the constraint
			// for now assign it to 1
			IntConstantTermDef intConstTermDef = new IntConstantTermDef("1", 1);
			// create constraint for the term
			BinaryTermConstraintDef binaryTermConstraintDef = new BinaryTermConstraintDef(idName, varTerm,
					intConstTermDef, BinaryTermConstraintOp.fromName("ATOM_EQ"));
			constraints.add(binaryTermConstraintDef);
			// create constraint for reference
			Constraint varConstraint = new Constraint(idName);
			// add to map
			compExprConstraintMap.put(e.toString(), varConstraint);

			ConstraintListCombo combo = new ConstraintListCombo(varConstraint, constraints);
			return combo;
		}
	}

	@Override
	public ConstraintListCombo visit(IntExpr e) {
		List<MistralConstraint> constraints = new ArrayList<MistralConstraint>();
		throw new SafetyException("Expr not supported " + e.toString());
	}

	@Override
	public ConstraintListCombo visit(RealExpr e) {
		List<MistralConstraint> constraints = new ArrayList<MistralConstraint>();
		throw new SafetyException("Expr not supported " + e.toString());
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

	private ConstraintListCombo createBinaryExprConstraint(String opName, Expr originalExpr, Expr left, Expr right,
			List<MistralConstraint> constraints) {

		ConstraintListCombo leftReturnCombo = visit(left);
		ConstraintListCombo rightReturnCombo = visit(right);
		Constraint leftConstraint = leftReturnCombo.lastConstraint;
		Constraint rightConstraint = rightReturnCombo.lastConstraint;
		List<MistralConstraint> leftConstraintList = leftReturnCombo.constraintList;
		List<MistralConstraint> rightConstraintList = rightReturnCombo.constraintList;

		String binaryConstraintName = createUniqueConstraintName(nodeNamePrefix + "Constraint");

		SingleConstraintExpr leftConstraintExpr = new SingleConstraintExpr(leftConstraint);
		SingleConstraintExpr rightConstraintExpr = new SingleConstraintExpr(rightConstraint);

		ConstraintBinaryExpr binaryConstraintExpr = new ConstraintBinaryExpr(leftConstraintExpr,
				ConstraintBinaryOp.fromName(opName), rightConstraintExpr);
		// create constraint def
		ExprConstraintDef exprConstraintDef = new ExprConstraintDef(binaryConstraintName, binaryConstraintExpr);
		constraints.addAll(leftConstraintList);
		constraints.addAll(rightConstraintList);
		constraints.add(exprConstraintDef);
		// create constraint for reference
		Constraint binaryConstraint = new Constraint(binaryConstraintName);
		// add to map
		compExprConstraintMap.put(originalExpr.toString(), binaryConstraint);
		ConstraintListCombo combo = new ConstraintListCombo(binaryConstraint, constraints);
		return combo;
	}
}
