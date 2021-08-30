package edu.umn.cs.crisys.safety.analysis.constraints.visitors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import edu.umn.cs.crisys.safety.analysis.SafetyException;
import edu.umn.cs.crisys.safety.analysis.ast.visitors.AddFaultsToNodeVisitor;
import edu.umn.cs.crisys.safety.analysis.ast.visitors.NegateLustreExprVisitor;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.ArithmeticTermDef;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.BinaryTermConstraintDef;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.BinaryTermConstraintOp;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.BooleanConstantConstraintDef;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.CompIdPair;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.Constraint;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.ConstraintAssignment;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.ConstraintListCombo;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.ExprConstraintDef;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.IntConstantTermDef;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.MistralConstraint;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.Term;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.TermAssignment;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.TermDef;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.TermIntegerMapDef;
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
import jkind.lustre.BinaryOp;
import jkind.lustre.BoolExpr;
import jkind.lustre.CastExpr;
import jkind.lustre.CondactExpr;
import jkind.lustre.Expr;
import jkind.lustre.FunctionCallExpr;
import jkind.lustre.IdExpr;
import jkind.lustre.IfThenElseExpr;
import jkind.lustre.IntExpr;
import jkind.lustre.NamedType;
import jkind.lustre.NodeCallExpr;
import jkind.lustre.RealExpr;
import jkind.lustre.RecordAccessExpr;
import jkind.lustre.RecordExpr;
import jkind.lustre.RecordUpdateExpr;
import jkind.lustre.TupleExpr;
import jkind.lustre.Type;
import jkind.lustre.UnaryExpr;
import jkind.lustre.visitors.ExprVisitor;

public class LustreExprToConstraintsVisitor implements ExprVisitor<ConstraintListCombo> {
	// distribute a negation operator to sub expressions in a Lustre expression
	private NegateLustreExprVisitor negateExprVisitor = new NegateLustreExprVisitor();
	// store the MistralConstraint translated for a Lustre Expression in a component
	private Map<String, MistralConstraint> compExprConstraintMap = new HashMap<>();
	// store the term def created for each term reference
	private Map<Term, TermDef> compTermDefMap = new HashMap<>();
	// used to add node name prefix to MistralConstraint names created
	private String nodeNamePrefix = "";
	// used to check top node name
	private String topNodeNamePrefix = "";
	// use index to help create unique names
	private int nameIndex = 0;
	// store all MistralConstraintNames created to help create unique names
	private HashSet<String> constraintNames = new HashSet<>();
	// store the component id and type
//	private Map<String, Type> idTypeMap = new HashMap<>();
	private Map<CompIdPair, Type> compIdTypeMap = new HashMap<>();
	// store the component id and term map
	private Map<CompIdPair, Term> compIdTermMap = new HashMap<>();
	// store the constant and term map
	private Map<Integer, Term> intTermMap = new HashMap<>();

	// indicate if it's the translation to assignment or not
	// by default translate to assignment unless it's overridden
	private boolean translateToAssignment = true;

	// indicate if translated to an assignment
	private boolean assignmentTranslated = false;

	public void resetNameIndex() {
		nameIndex = 0;
	}

	public String createValidAndUniqueName(String name) {
		// first update the name to make it valid
		// replace all non-alphanumeric characters with "_"
		// remove leading _
		String updatedName = name.replaceAll("\\P{Alnum}", "_").replaceAll("^_+", "");
		// second make sure it's unique
		while (constraintNames.contains(updatedName)) {
			nameIndex++;
			updatedName = updatedName + "_" + nameIndex;
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

	public void setTopNodeNamePrefix(String topNamePrefix) {
		this.topNodeNamePrefix = topNamePrefix;
	}

	public String getTopNodeNamePrefix() {
		return topNodeNamePrefix;
	}

	public void clearCompExprConstraintMap() {
		this.compExprConstraintMap.clear();
	}

	public void clearCompTermDefMap() {
		this.compTermDefMap.clear();
	}

	public void clearCompIdTypeMap() {
		this.compIdTypeMap.clear();
	}

	public void addEntryToCompIdTypeMap(String compName, String idName, Type type) {
		compIdTypeMap.put(new CompIdPair(compName, idName), type);
	}

	public Type getTypeFromCompIdTypeMap(String compName, String idName) {
		return compIdTypeMap.get(new CompIdPair(compName, idName));
	}

	public Term getTermFromCompIdTermMap(String compName, String idName) {
		return compIdTermMap.get(new CompIdPair(compName, idName));
	}

	public boolean getTranslateToAssignment() {
		return translateToAssignment;
	}

	public void setTranslateToAssignment(boolean createAssignment) {
		translateToAssignment = createAssignment;
	}

	public boolean getAssignmentTranslated() {
		return assignmentTranslated;
	}

	public void setAssignmentTranslated(boolean translated) {
		assignmentTranslated = translated;
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
			if (opName.equals("AND") || opName.equals("OR")) {
				//logical expression, set translateToAssignment to false
				translateToAssignment = false;
				// visit the left and the right expression
				ConstraintListCombo leftReturnCombo = visit(e.left);
				ConstraintListCombo rightReturnCombo = visit(e.right);
				return createBinaryLogicalConstraint(opName, e, e.left, e.right, constraints, leftReturnCombo,
						rightReturnCombo);
			} else if (opName.equals("IMPLIES")) {
				// first check if the left expression contains __ASSUME__HIST
				// if yes, return true constraint
				if (e.left instanceof IdExpr) {
					if (((IdExpr) e.left).id.contains("__ASSUME__HIST")) {
						return visit(new BoolExpr(true));
					}
				}
				// else
				// (a => b) <=> (not a or b)
				//logical expression, set translateToAssignment to false
				translateToAssignment = false;
				Expr newLeft = negateExprVisitor.visit(e.left);
				ConstraintListCombo leftReturnCombo = visit(newLeft);
				ConstraintListCombo rightReturnCombo = visit(e.right);
				return createBinaryLogicalConstraint("OR", e, newLeft, e.right, constraints, leftReturnCombo,
						rightReturnCombo);
			} else if (opName.equals("ARROW")) {
				// TODO: for now for arrow operator, only visit the left expression (initial value)
				return visit(e.left);
			} else if (opName.equals("NOTEQUAL")) {
				return createEqualityBinaryTermOrConstraint(e, constraints, opName);
			} else if (opName.equals("EQUAL")) {
				// EQUAL operator can mean equality or assignment, need to differentiate the two
				// first check if the right expression is if-then-else
				// if yes, translate to the following:
				// left = (if a then b else c)
				// <=>
				// (not a or (left = b)) and (a or (left = c))
				if (e.right instanceof IfThenElseExpr) {
					Expr condExpr = ((IfThenElseExpr) e.right).cond;
					Expr thenExpr = ((IfThenElseExpr) e.right).thenExpr;
					Expr elseExpr = ((IfThenElseExpr) e.right).elseExpr;
					Expr negCondExpr = negateExprVisitor.visit(condExpr);

					BinaryExpr assignLeftToThenExpr = new BinaryExpr(e.location, e.left, BinaryOp.EQUAL, thenExpr);
					BinaryExpr assignLeftToElseExpr = new BinaryExpr(e.location, e.left, BinaryOp.EQUAL, elseExpr);

					BinaryExpr exprLeft = new BinaryExpr(e.location, negCondExpr, BinaryOp.OR, assignLeftToThenExpr);
					BinaryExpr exprRight = new BinaryExpr(e.location, condExpr, BinaryOp.OR, assignLeftToElseExpr);
					BinaryExpr newExpr = new BinaryExpr(e.location, exprLeft, BinaryOp.AND, exprRight);
					//logical expression, set translateToAssignment to false
					translateToAssignment = false;
					return visit(newExpr);
				}
				// else
				else {
					// if not translate to assignment
					if (!translateToAssignment) {
						// treat it the same way as the != operator expression
						// and create equality constraint expression
						return createEqualityBinaryTermOrConstraint(e, constraints, opName);
					} else {
						if (e.left instanceof IdExpr) {
//						// if left is an IdExpr and right is an IdExpr, or BoolExpr, or IntExpr, or BinaryExpr create assignment
//						if ((e.left instanceof IdExpr) && ((e.right instanceof IdExpr) || (e.right instanceof BoolExpr)
//								|| (e.right instanceof IntExpr))) {
							// treat the EQUAL operator as an assignment
							return createAssignmentBinaryTermOrConstraint(e, constraints, opName);
						} else {
							translateToAssignment = false;
							// treat it the same way as the != operator expression
							// and create equality constraint expression
							return createEqualityBinaryTermOrConstraint(e, constraints, opName);
						}
					}

				}
			} else if (opName.equals("GREATER") || opName.equals("LESS") || opName.equals("GREATEREQUAL")
					|| opName.equals("LESSEQUAL")) {
				// visit the left and the right expression
				ConstraintListCombo leftReturnCombo = visit(e.left);
				ConstraintListCombo rightReturnCombo = visit(e.right);
				return createBinaryTermComparisonConstraint(opName, e, e.left, e.right, constraints, leftReturnCombo,
						rightReturnCombo);
			} else if (opName.equals("PLUS") || opName.equals("MINUS") || opName.equals("MULTIPLY")
					|| opName.equals("DIVIDE")) {
				return createArithmeticTerm(opName, e, e.left, e.right, constraints);
			} else {
				// not supported
				throw new SafetyException("Expr not supported " + e.toString());
			}
		}
	}

	@Override
	public ConstraintListCombo visit(IfThenElseExpr e) {
		// throw an exception, as it needs to be handled in the assign binary expression above it
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
			// for NOT operator
			if (opName.equals("NOT")) {
				// visit the expression, if it returns a constraint, create a constraint out of it and return
				// otherwise throw an exception (as it means there it involves a non-boolean construct)
				if (e.expr instanceof IdExpr) {
					// if e.expr is an Id expression
					// visit the Id expression
					ConstraintListCombo returnCombo = visit(e.expr);
					MistralConstraint returnConstruct = returnCombo.lastConstraint;
					List<MistralConstraint> returanConstraintList = returnCombo.constraintList;

					// if it returns a constraint
					// create a constraint out of it and return
					if (returnConstruct instanceof Constraint) {
						Constraint returnConstraint = (Constraint) returnConstruct;
						// create unique name
						String notConstraintName = createValidAndUniqueName("not_" + returnConstraint.constraintId);

						SingleConstraintExpr returnConstraintExpr = new SingleConstraintExpr(returnConstraint);

						ConstraintUnaryExpr unaryConstraintExpr = new ConstraintUnaryExpr(
								ConstraintUnaryOp.fromName("NOT"), returnConstraintExpr);
						// create constraint def
						ExprConstraintDef exprConstraintDef = new ExprConstraintDef(notConstraintName,
								unaryConstraintExpr);
						constraints.addAll(returanConstraintList);
						constraints.add(exprConstraintDef);
						// create constraint for reference
						Constraint unaryConstraint = new Constraint(notConstraintName);
						// add to compExprConstraint map
						compExprConstraintMap.put(e.toString(), unaryConstraint);
						ConstraintListCombo combo = new ConstraintListCombo(unaryConstraint, constraints);
						return combo;
					}
					// otherwise throw an exception (as it means there it involves a non-boolean construct)
					else {
						// not supported
						throw new SafetyException("Expr not supported " + e.toString());
					}
				} else {
					// if e.expr is not an Id expression
					// distribute the negation to associate with individual Id node
					Expr newExpr = negateExprVisitor.visit(e.expr);
					// then visit the new expression
					return visit(newExpr);
				}
			}
			// for PRE operator, throw an exception, as PRE operator should be associated with -> operator in the
			// lustre code, and we only visit the left side of the -> operator
			// TODO: revisit this in the future
			else if (opName.equals("PRE")) {
				throw new SafetyException("Expr not supported " + e.toString());
			}
			// for Negation (-) operator, visit the expression
			// if it returns a term, create an arithmetic term out of it using the map construct, and return the term
			// otherwise throw an exception (as it means there it involves a boolean construct)
			else if (opName.equals("NEGATIVE")) {
				ConstraintListCombo returnCombo = visit(e.expr);
				MistralConstraint constraint = returnCombo.lastConstraint;

				// if it returns a term, create an arithmetic term out of it using the map construct, and return the term
				if (constraint instanceof Term) {
					List<MistralConstraint> constraintList = returnCombo.constraintList;
					Term term = (Term) constraint;
					TermDef termDef = compTermDefMap.get(term);
					if (termDef == null) {
						throw new SafetyException("Term Def not found for " + term.toString());
					} else {
						// if it's an ArithmeticTerm, update the map
						if (termDef instanceof ArithmeticTermDef) {
							String termIntegerMapDefName = createValidAndUniqueName(nodeNamePrefix + "_TermIntegerMap");
							TermIntegerMapDef termIntegerMapDef = new TermIntegerMapDef(termIntegerMapDefName);
							// multiply left value to every entry's value in the rightTerm map
							for (Map.Entry<Term, Integer> entry : ((ArithmeticTermDef) termDef).termIntegerMapDef.termMap
									.entrySet()) {
								termIntegerMapDef.termMap.put(entry.getKey(), entry.getValue() * (-1));
							}
							return storeArithmeticTermSingleConstraintList(e, constraints, constraintList,
									termIntegerMapDef);
						}
						// else if it's an IntConstantTerm
						// create an arithmetic term out of it using the map construct, and return the term
						else if (termDef instanceof IntConstantTermDef) {
							String termIntegerMapDefName = createValidAndUniqueName(nodeNamePrefix + "_TermIntegerMap");

							TermIntegerMapDef termIntegerMapDef = new TermIntegerMapDef(termIntegerMapDefName);
							termIntegerMapDef.addEntry(term, (-1));

							return storeArithmeticTermSingleConstraintList(e, constraints, constraintList,
									termIntegerMapDef);
						}
						// otherwise throw an exception
						else {
							throw new SafetyException("Expr not supported " + e.toString());
						}
					}
				}
				// otherwise throw an exception (as it means there it involves a boolean construct)
				else {
					throw new SafetyException("Expr not supported " + e.toString());
				}
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
		// otherwise
		// for Bool literal (such as true, false),
		// create a bool constraint out of it and return the constraint
		else {
			// create unique names with agree node name prefix if the name doesn't exist
			String constantConstraintName = createValidAndUniqueName(nodeNamePrefix + "_" + e.value);
			// create a BooleanConstantConstraintDef
			BooleanConstantConstraintDef constantConstraintDef = new BooleanConstantConstraintDef(
					constantConstraintName, e.value);
			constraints.add(constantConstraintDef);
			// create constraint for reference
			Constraint constantConstraint = new Constraint(constantConstraintName);
			// add to compExprConstraint map
			compExprConstraintMap.put(e.toString(), constantConstraint);

			ConstraintListCombo combo = new ConstraintListCombo(constantConstraint, constraints);
			return combo;
		}
	}

	@Override
	public ConstraintListCombo visit(IdExpr e) {
		// visit IdExpr and create def
		return visitIdExpr(e, true);
	}

	@Override
	public ConstraintListCombo visit(IntExpr e) {
		List<MistralConstraint> constraints = new ArrayList<MistralConstraint>();

		// if expr already defined for within this node, just retrieve the constraint
		if (compExprConstraintMap.containsKey(e.toString())) {
			ConstraintListCombo combo = new ConstraintListCombo(compExprConstraintMap.get(e.toString()), constraints);
			return combo;
		}
		// otherwise
		// For Int literal (such as -1,0,1,2,…),
		// create a constant term out of it and return the term
		else {
			return createIntConstantTermfromIntExpr(e, constraints);
		}
	}

	@Override
	public ConstraintListCombo visit(RealExpr e) {
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

	private ConstraintListCombo createBinaryLogicalConstraint(String opName, Expr originalExpr, Expr left, Expr right,
			List<MistralConstraint> constraints, ConstraintListCombo leftReturnCombo,
			ConstraintListCombo rightReturnCombo) {
		// For expressions connected by logical operators (AND, OR),
		MistralConstraint leftConstraint = leftReturnCombo.lastConstraint;
		MistralConstraint rightConstraint = rightReturnCombo.lastConstraint;

		// if both return a constraint, create a constraint that combines the two and return
		if ((leftConstraint instanceof Constraint) && (rightConstraint instanceof Constraint)) {
			List<MistralConstraint> leftConstraintList = leftReturnCombo.constraintList;
			List<MistralConstraint> rightConstraintList = rightReturnCombo.constraintList;

			String binaryConstraintName = createValidAndUniqueName(nodeNamePrefix + "_Constraint");

			SingleConstraintExpr leftConstraintExpr = new SingleConstraintExpr((Constraint) leftConstraint);
			SingleConstraintExpr rightConstraintExpr = new SingleConstraintExpr((Constraint) rightConstraint);

			ConstraintBinaryExpr binaryConstraintExpr = new ConstraintBinaryExpr(leftConstraintExpr,
					ConstraintBinaryOp.fromName(opName), rightConstraintExpr);
			// create constraint def
			ExprConstraintDef exprConstraintDef = new ExprConstraintDef(binaryConstraintName, binaryConstraintExpr);
			constraints.addAll(leftConstraintList);
			constraints.addAll(rightConstraintList);
			constraints.add(exprConstraintDef);
			// create constraint for reference
			Constraint binaryConstraint = new Constraint(binaryConstraintName);
			// add to compExprConstraint map
			compExprConstraintMap.put(originalExpr.toString(), binaryConstraint);
			ConstraintListCombo combo = new ConstraintListCombo(binaryConstraint, constraints);
			return combo;
		}
		// otherwise throw an exception (as it means there it involves a boolean and non-boolean construct)
		else {
			// not supported
			throw new SafetyException("Expr not supported " + originalExpr.toString());
		}
	}

	private ConstraintListCombo createConstraintAssignment(String opName, Expr originalExpr, Expr left, Expr right,
			List<MistralConstraint> constraints, ConstraintListCombo leftReturnCombo,
			ConstraintListCombo rightReturnCombo) {
		MistralConstraint leftConstraint = leftReturnCombo.lastConstraint;
		MistralConstraint rightConstraint = rightReturnCombo.lastConstraint;

		// if both return a constraint, create a constraint assignment out of it and return
		if ((leftConstraint instanceof Constraint) && (rightConstraint instanceof Constraint)) {
			List<MistralConstraint> leftConstraintList = leftReturnCombo.constraintList;
			List<MistralConstraint> rightConstraintList = rightReturnCombo.constraintList;

			ConstraintAssignment constraintAssignment = new ConstraintAssignment((Constraint) leftConstraint,
					(Constraint) rightConstraint);
			constraints.addAll(leftConstraintList);
			constraints.addAll(rightConstraintList);
			constraints.add(constraintAssignment);
			// use the leftConstraint as reference to return
			// add to compExprConstraint map
			compExprConstraintMap.put(originalExpr.toString(), leftConstraint);
			// set assignmentTranslated true to indicate the constraint returned is a constraint reference for an assignment
			assignmentTranslated = true;

			ConstraintListCombo combo = new ConstraintListCombo(leftConstraint, constraints);
			return combo;
		}
		// otherwise throw an exception (as it means there it involves a boolean and non-boolean construct)
		else {
			// not supported
			throw new SafetyException("Expr not supported " + originalExpr.toString());
		}
	}

	private ConstraintListCombo createBinaryEqualityConstraint(String opName, Expr originalExpr, Expr left, Expr right,
			List<MistralConstraint> constraints, ConstraintListCombo leftReturnCombo,
			ConstraintListCombo rightReturnCombo) {
		// For expressions connected by logical operators (!=, ==),
		MistralConstraint leftConstraint = leftReturnCombo.lastConstraint;
		MistralConstraint rightConstraint = rightReturnCombo.lastConstraint;

		// if both return a constraint, create a constraint that combines the two and return
		if ((leftConstraint instanceof Constraint) && (rightConstraint instanceof Constraint)) {
			List<MistralConstraint> leftConstraintList = leftReturnCombo.constraintList;
			List<MistralConstraint> rightConstraintList = rightReturnCombo.constraintList;

			String binaryConstraintName = createValidAndUniqueName(nodeNamePrefix + "_Constraint");

			SingleConstraintExpr leftConstraintExpr = new SingleConstraintExpr((Constraint) leftConstraint);
			SingleConstraintExpr rightConstraintExpr = new SingleConstraintExpr((Constraint) rightConstraint);

			ConstraintUnaryExpr notLeftConstraintExpr = new ConstraintUnaryExpr(ConstraintUnaryOp.fromName("NOT"),
					leftConstraintExpr);
			ConstraintUnaryExpr notRightConstraintExpr = new ConstraintUnaryExpr(ConstraintUnaryOp.fromName("NOT"),
					rightConstraintExpr);

			ConstraintBinaryExpr binaryConstraintExpr = null;
			// for a == b where a and b are both constraints, translate to
			// (a => b) and (b => a), which is the same as (!a or b) and (a or !b)
			if (opName.equals("EQUAL")) {
				ConstraintBinaryExpr newLeftConstraintExpr = new ConstraintBinaryExpr(notLeftConstraintExpr,
						ConstraintBinaryOp.fromName("OR"), rightConstraintExpr);
				ConstraintBinaryExpr newRightConstraintExpr = new ConstraintBinaryExpr(leftConstraintExpr,
						ConstraintBinaryOp.fromName("OR"), notRightConstraintExpr);
				binaryConstraintExpr = new ConstraintBinaryExpr(newLeftConstraintExpr,
						ConstraintBinaryOp.fromName("AND"), newRightConstraintExpr);
			}
			// for a != b where a and b are both constraints, translate to
			// !(a=>b) or !(b => a), which is the same as (a and !b) or (!a and b)
			else if (opName.equals("NOTEQUAL")) {
				ConstraintBinaryExpr newLeftConstraintExpr = new ConstraintBinaryExpr(leftConstraintExpr,
						ConstraintBinaryOp.fromName("AND"), notRightConstraintExpr);
				ConstraintBinaryExpr newRightConstraintExpr = new ConstraintBinaryExpr(notLeftConstraintExpr,
						ConstraintBinaryOp.fromName("AND"), rightConstraintExpr);
				binaryConstraintExpr = new ConstraintBinaryExpr(newLeftConstraintExpr,
						ConstraintBinaryOp.fromName("OR"), newRightConstraintExpr);
			} else {
				// not supported
				throw new SafetyException("Expr not supported " + originalExpr.toString());
			}

			// create constraint def
			ExprConstraintDef exprConstraintDef = new ExprConstraintDef(binaryConstraintName, binaryConstraintExpr);
			constraints.addAll(leftConstraintList);
			constraints.addAll(rightConstraintList);
			constraints.add(exprConstraintDef);
			// create constraint for reference
			Constraint binaryConstraint = new Constraint(binaryConstraintName);
			// add to compExprConstraint map
			compExprConstraintMap.put(originalExpr.toString(), binaryConstraint);
			ConstraintListCombo combo = new ConstraintListCombo(binaryConstraint, constraints);
			return combo;
		}
		// otherwise throw an exception (as it means there it involves a boolean and non-boolean construct)
		else {
			// not supported
			throw new SafetyException("Expr not supported " + originalExpr.toString());
		}
	}

	private ConstraintListCombo createTermAssignment(String opName, Expr originalExpr, Expr left, Expr right,
			List<MistralConstraint> constraints, ConstraintListCombo leftReturnCombo,
			ConstraintListCombo rightReturnCombo) {
		MistralConstraint leftConstraint = leftReturnCombo.lastConstraint;
		MistralConstraint rightConstraint = rightReturnCombo.lastConstraint;

		// if both return a term, create a term assignment out of it and return
		if ((leftConstraint instanceof Term) && (rightConstraint instanceof Term)) {
			List<MistralConstraint> leftConstraintList = leftReturnCombo.constraintList;
			List<MistralConstraint> rightConstraintList = rightReturnCombo.constraintList;
			TermAssignment termAssignment = new TermAssignment((Term) leftConstraint, (Term) rightConstraint);
			constraints.addAll(leftConstraintList);
			constraints.addAll(rightConstraintList);
			constraints.add(termAssignment);
			// use the leftConstraint as reference to return
			// add to compExprConstraint map
			compExprConstraintMap.put(originalExpr.toString(), leftConstraint);
			// set assignmentTranslated true to indicate the term returned is a term reference for an assignment
			assignmentTranslated = true;

			ConstraintListCombo combo = new ConstraintListCombo(leftConstraint, constraints);
			return combo;
		}
		// otherwise throw an exception (as it means there it involves a boolean and non-boolean construct)
		else {
			// not supported
			throw new SafetyException("Expr not supported " + originalExpr.toString());
		}
	}

	private ConstraintListCombo createBinaryTermComparisonConstraint(String opName, Expr originalExpr, Expr left,
			Expr right, List<MistralConstraint> constraints, ConstraintListCombo leftReturnCombo,
			ConstraintListCombo rightReturnCombo) {
		// given a Boolean expression connected by comparison operators (>, <, >=, <=)
		MistralConstraint leftConstraint = leftReturnCombo.lastConstraint;
		MistralConstraint rightConstraint = rightReturnCombo.lastConstraint;

		// if both return a term, create a constraint out of it and return,
		// e.g., Constraint c1(t4, t2, ATOM_LEQ)
		if ((leftConstraint instanceof Term) && (rightConstraint instanceof Term)) {
			List<MistralConstraint> leftConstraintList = leftReturnCombo.constraintList;
			List<MistralConstraint> rightConstraintList = rightReturnCombo.constraintList;

			String binaryConstraintName = createValidAndUniqueName(nodeNamePrefix + "_Constraint");

			// create constraint for the term
			BinaryTermConstraintDef binaryTermConstraintDef = new BinaryTermConstraintDef(binaryConstraintName,
					leftConstraint, rightConstraint, BinaryTermConstraintOp.fromStr(opName));
			constraints.addAll(leftConstraintList);
			constraints.addAll(rightConstraintList);
			constraints.add(binaryTermConstraintDef);
			// create constraint for reference
			Constraint binaryConstraint = new Constraint(binaryConstraintName);
			// add to compExprConstraint map
			compExprConstraintMap.put(originalExpr.toString(), binaryConstraint);

			ConstraintListCombo combo = new ConstraintListCombo(binaryConstraint, constraints);
			return combo;
		}
		// otherwise throw an exception (as it means there it involves a boolean and non-boolean construct)
		else {
			// not supported
			throw new SafetyException("Expr not supported " + originalExpr.toString());
		}
	}

	private ConstraintListCombo createAssignmentBinaryTermOrConstraint(BinaryExpr e,
			List<MistralConstraint> constraints, String opName) {
		// visit the right expression
		ConstraintListCombo rightReturnCombo = visit(e.right);
		MistralConstraint rightConstraint = rightReturnCombo.lastConstraint;
		// visit the left Id expression
		if (e.left instanceof IdExpr) {
			// visit Id expression and don't create def
			ConstraintListCombo leftReturnCombo = visitIdExpr((IdExpr) e.left, false);
			MistralConstraint leftConstraint = leftReturnCombo.lastConstraint;
			// assign right to left
			if ((leftConstraint instanceof Term) && (rightConstraint instanceof Term)) {
				return createTermAssignment(opName, e, e.left, e.right, constraints, leftReturnCombo, rightReturnCombo);
			} else if ((leftConstraint instanceof Constraint) && (rightConstraint instanceof Constraint)) {
				return createConstraintAssignment(opName, e, e.left, e.right, constraints, leftReturnCombo,
						rightReturnCombo);
			}
			// otherwise throw an exception (as it means there it involves a boolean and non-boolean construct)
			else {
				// not supported
				throw new SafetyException("Expr not supported " + e.toString());
			}
		} else {
			// not supported
			throw new SafetyException("Expr not supported " + e.toString());
		}
	}

	private ConstraintListCombo createEqualityBinaryTermOrConstraint(BinaryExpr e, List<MistralConstraint> constraints,
			String opName) {
		// given a Boolean expression connected by comparison operators (==, !=)
		ConstraintListCombo leftReturnCombo = visit(e.left);
		ConstraintListCombo rightReturnCombo = visit(e.right);
		MistralConstraint leftConstraint = leftReturnCombo.lastConstraint;
		MistralConstraint rightConstraint = rightReturnCombo.lastConstraint;
		if ((leftConstraint instanceof Term) && (rightConstraint instanceof Term)) {
			return createBinaryTermComparisonConstraint(opName, e, e.left, e.right, constraints, leftReturnCombo,
					rightReturnCombo);
		} else if ((leftConstraint instanceof Constraint) && (rightConstraint instanceof Constraint)) {
			return createBinaryEqualityConstraint(opName, e, e.left, e.right, constraints, leftReturnCombo,
					rightReturnCombo);
		}
		// otherwise throw an exception (as it means there it involves a boolean and non-boolean construct)
		else {
			// not supported
			throw new SafetyException("Expr not supported " + e.toString());
		}
	}

	private ConstraintListCombo createArithmeticTerm(String opName, Expr originalExpr, Expr left, Expr right,
			List<MistralConstraint> constraints) {
		// given an Integer expression connected by +, -, *, / operators
		// visit the left and the right expression
		ConstraintListCombo leftReturnCombo = visit(left);
		ConstraintListCombo rightReturnCombo = visit(right);
		MistralConstraint leftConstraint = leftReturnCombo.lastConstraint;
		MistralConstraint rightConstraint = rightReturnCombo.lastConstraint;

		// if both return a term
		// create an arithmetic term out of it using the map construct
		// and return the term
		if ((leftConstraint instanceof Term) && (rightConstraint instanceof Term)) {
			List<MistralConstraint> leftConstraintList = leftReturnCombo.constraintList;
			List<MistralConstraint> rightConstraintList = rightReturnCombo.constraintList;
			Term leftTerm = (Term) leftConstraint;
			Term rightTerm = (Term) rightConstraint;
			TermDef leftTermDef = compTermDefMap.get(leftTerm);
			TermDef rightTermDef = compTermDefMap.get(rightTerm);
			// if both are Int Constant Terms
			// create an arithmetic term and return
			if ((leftTermDef instanceof IntConstantTermDef) && (rightTermDef instanceof IntConstantTermDef)) {
				return createArithmeticTermforTwoInts(opName, originalExpr, constraints, leftConstraintList,
						rightConstraintList, leftTerm, rightTerm, leftTermDef, rightTermDef);
			} else if ((leftTermDef instanceof VariableTermDef) && (rightTermDef instanceof VariableTermDef)) {
				return createArithmeticTermForTwoVars(opName, originalExpr, constraints, leftConstraintList,
						rightConstraintList, leftTerm, rightTerm, leftTermDef, rightTermDef);
			}
			// else if one is an Int Constant Term, one is a Variable Term
			// create an arithmetic term and return
			else if (((leftTermDef instanceof IntConstantTermDef) && (rightTermDef instanceof VariableTermDef))
					|| ((leftTermDef instanceof VariableTermDef) && (rightTermDef instanceof IntConstantTermDef))) {
				return createArithmeticTermForIntAndVar(opName, originalExpr, constraints, leftConstraintList,
						rightConstraintList, leftTerm, rightTerm, leftTermDef, rightTermDef);
			}
			// else if one is a Variable Term, and one is an Arithmetic term
			// merge them into one arithmetic term and return
			else if (((leftTermDef instanceof VariableTermDef) && (rightTermDef instanceof ArithmeticTermDef))
					|| ((leftTermDef instanceof ArithmeticTermDef) && (rightTermDef instanceof VariableTermDef))) {
				return createArithmeticTermForVarAndArithmetic(opName, originalExpr, constraints, leftConstraintList,
						rightConstraintList, leftTerm, rightTerm, leftTermDef, rightTermDef);
			}
			// else if one is an Int Constant Term, and one is an Arithmetic term
			// merge them into one arithmetic term and return
			else if (((leftTermDef instanceof IntConstantTermDef) && (rightTermDef instanceof ArithmeticTermDef))
					|| ((leftTermDef instanceof ArithmeticTermDef) && (rightTermDef instanceof IntConstantTermDef))) {
				return createArithmeticTermForIntAndArithmetic(opName, originalExpr, constraints, leftConstraintList,
						rightConstraintList, leftTerm, rightTerm, leftTermDef, rightTermDef);
			}
			// else if both are Arithmetic Terms
			// merge them into one arithmetic term and return
			else if (((leftTermDef instanceof ArithmeticTermDef) && (rightTermDef instanceof ArithmeticTermDef))) {
				return createArithmeticTermForTwoArithmetics(opName, originalExpr, constraints, leftConstraintList,
						rightConstraintList, leftTerm, rightTerm, leftTermDef, rightTermDef);
			} else {
				// not supported
				throw new SafetyException("Expr not supported " + originalExpr.toString());
			}
		}
		// otherwise thrown an exception (as those operators don't apply to boolean types)
		else {
			// not supported
			throw new SafetyException("Expr not supported " + originalExpr.toString());
		}
	}

	private ConstraintListCombo createArithmeticTermforTwoInts(String opName, Expr originalExpr,
			List<MistralConstraint> constraints, List<MistralConstraint> leftConstraintList,
			List<MistralConstraint> rightConstraintList, Term leftTerm, Term rightTerm, TermDef leftTermDef,
			TermDef rightTermDef) {
		String termIntegerMapDefName = createValidAndUniqueName(nodeNamePrefix + "_TermIntegerMap");

		TermIntegerMapDef termIntegerMapDef = new TermIntegerMapDef(termIntegerMapDefName);
		if (opName.equals("PLUS") || opName.equals("MINUS")) {
			termIntegerMapDef.addEntry(leftTerm, 1);
			if (opName.equals("PLUS")) {
				termIntegerMapDef.addEntry(rightTerm, 1);
			} else {
				termIntegerMapDef.addEntry(rightTerm, -1);
			}
		} else if (opName.equals("MULTIPLY")) {
			termIntegerMapDef.addEntry(leftTerm, ((IntConstantTermDef) rightTermDef).val);
		} else {
			// not supporting DIVIDE operator
			throw new SafetyException("Expr not supported " + originalExpr.toString());
		}

		return storeArithmeticTermTwoConstraintLists(originalExpr, constraints, leftConstraintList, rightConstraintList,
				termIntegerMapDef);
	}

	private ConstraintListCombo createArithmeticTermForIntAndVar(String opName, Expr originalExpr,
			List<MistralConstraint> constraints, List<MistralConstraint> leftConstraintList,
			List<MistralConstraint> rightConstraintList, Term leftTerm, Term rightTerm, TermDef leftTermDef,
			TermDef rightTermDef) {
		String termIntegerMapDefName = createValidAndUniqueName(nodeNamePrefix + "_TermIntegerMap");

		TermIntegerMapDef termIntegerMapDef = new TermIntegerMapDef(termIntegerMapDefName);
		if (opName.equals("PLUS") || opName.equals("MINUS")) {
			termIntegerMapDef.addEntry(leftTerm, 1);
			if (opName.equals("PLUS")) {
				termIntegerMapDef.addEntry(rightTerm, 1);
			} else {
				termIntegerMapDef.addEntry(rightTerm, -1);
			}
		} else if (opName.equals("MULTIPLY")) {
			if (leftTermDef instanceof IntConstantTermDef) {
				termIntegerMapDef.addEntry(rightTerm, ((IntConstantTermDef) leftTermDef).val);
			} else {
				termIntegerMapDef.addEntry(leftTerm, ((IntConstantTermDef) rightTermDef).val);
			}
		} else {
			// not supporting DIVIDE operator
			throw new SafetyException("Expr not supported " + originalExpr.toString());
		}

		return storeArithmeticTermTwoConstraintLists(originalExpr, constraints, leftConstraintList, rightConstraintList,
				termIntegerMapDef);
	}

	private ConstraintListCombo createArithmeticTermForTwoVars(String opName, Expr originalExpr,
			List<MistralConstraint> constraints, List<MistralConstraint> leftConstraintList,
			List<MistralConstraint> rightConstraintList, Term leftTerm, Term rightTerm, TermDef leftTermDef,
			TermDef rightTermDef) {
		String termIntegerMapDefName = createValidAndUniqueName(nodeNamePrefix + "_TermIntegerMap");

		TermIntegerMapDef termIntegerMapDef = new TermIntegerMapDef(termIntegerMapDefName);
		if (opName.equals("PLUS") || opName.equals("MINUS")) {
			termIntegerMapDef.addEntry(leftTerm, 1);
			if (opName.equals("PLUS")) {
				termIntegerMapDef.addEntry(rightTerm, 1);
			} else {
				termIntegerMapDef.addEntry(rightTerm, -1);
			}
		} else {
			// not supporting MULTIPLE or DIVIDE operator between two vars
			throw new SafetyException("Expr not supported " + originalExpr.toString());
		}

		return storeArithmeticTermTwoConstraintLists(originalExpr, constraints, leftConstraintList, rightConstraintList,
				termIntegerMapDef);
	}

	private ConstraintListCombo createArithmeticTermForVarAndArithmetic(String opName, Expr originalExpr,
			List<MistralConstraint> constraints, List<MistralConstraint> leftConstraintList,
			List<MistralConstraint> rightConstraintList, Term leftTerm, Term rightTerm, TermDef leftTermDef,
			TermDef rightTermDef) {
		String termIntegerMapDefName = createValidAndUniqueName(nodeNamePrefix + "_TermIntegerMap");

		TermIntegerMapDef termIntegerMapDef = new TermIntegerMapDef(termIntegerMapDefName);

		if (leftTermDef instanceof ArithmeticTermDef) {
			termIntegerMapDef.termMap.putAll(((ArithmeticTermDef) leftTermDef).termIntegerMapDef.termMap);
			if (opName.equals("PLUS")) {
				termIntegerMapDef.addEntry(rightTerm, 1);
			} else if (opName.equals("MINUS")) {
				termIntegerMapDef.addEntry(rightTerm, -1);
			} else {
				// not supporting MULTIPLE or DIVIDE operator between arithmetic term and var term
				throw new SafetyException("Expr not supported " + originalExpr.toString());
			}
		} else {
			if (opName.equals("PLUS")) {
				termIntegerMapDef.addEntry(leftTerm, 1);
				termIntegerMapDef.termMap.putAll(((ArithmeticTermDef) rightTermDef).termIntegerMapDef.termMap);
			} else if (opName.equals("MINUS")) {
				termIntegerMapDef.addEntry(leftTerm, 1);
				// apply -1 to every entry's value in the rightTerm map
				for (Map.Entry<Term, Integer> entry : ((ArithmeticTermDef) rightTermDef).termIntegerMapDef.termMap
						.entrySet()) {
					termIntegerMapDef.termMap.put(entry.getKey(), entry.getValue() * -1);
				}
			} else {
				// not supporting MULTIPLE or DIVIDE operator between arithmetic term and var term
				throw new SafetyException("Expr not supported " + originalExpr.toString());
			}
		}

		return storeArithmeticTermTwoConstraintLists(originalExpr, constraints, leftConstraintList, rightConstraintList,
				termIntegerMapDef);
	}

	private ConstraintListCombo createArithmeticTermForIntAndArithmetic(String opName, Expr originalExpr,
			List<MistralConstraint> constraints, List<MistralConstraint> leftConstraintList,
			List<MistralConstraint> rightConstraintList, Term leftTerm, Term rightTerm, TermDef leftTermDef,
			TermDef rightTermDef) {
		String termIntegerMapDefName = createValidAndUniqueName(nodeNamePrefix + "_TermIntegerMap");

		TermIntegerMapDef termIntegerMapDef = new TermIntegerMapDef(termIntegerMapDefName);

		if (leftTermDef instanceof ArithmeticTermDef) {
			if (opName.equals("PLUS")) {
				termIntegerMapDef.termMap.putAll(((ArithmeticTermDef) leftTermDef).termIntegerMapDef.termMap);
				termIntegerMapDef.addEntry(rightTerm, 1);
			} else if (opName.equals("MINUS")) {
				termIntegerMapDef.termMap.putAll(((ArithmeticTermDef) leftTermDef).termIntegerMapDef.termMap);
				termIntegerMapDef.addEntry(rightTerm, -1);
			} else if (opName.equals("MULTIPLY")) {
				// multiply right value to every entry's value in the leftTerm map
				for (Map.Entry<Term, Integer> entry : ((ArithmeticTermDef) leftTermDef).termIntegerMapDef.termMap
						.entrySet()) {
					termIntegerMapDef.termMap.put(entry.getKey(),
							entry.getValue() * ((IntConstantTermDef) rightTermDef).val);
				}
			} else {
				// not supporting DIVIDE operator between arithmetic term and int term
				throw new SafetyException("Expr not supported " + originalExpr.toString());
			}
		} else {
			if (opName.equals("PLUS")) {
				termIntegerMapDef.addEntry(leftTerm, 1);
				termIntegerMapDef.termMap.putAll(((ArithmeticTermDef) rightTermDef).termIntegerMapDef.termMap);
			} else if (opName.equals("MINUS")) {
				termIntegerMapDef.addEntry(leftTerm, 1);
				// apply -1 to every entry's value in the rightTerm map
				for (Map.Entry<Term, Integer> entry : ((ArithmeticTermDef) rightTermDef).termIntegerMapDef.termMap
						.entrySet()) {
					termIntegerMapDef.termMap.put(entry.getKey(), entry.getValue() * -1);
				}
			} else if (opName.equals("MULTIPLY")) {
				// multiply left value to every entry's value in the rightTerm map
				for (Map.Entry<Term, Integer> entry : ((ArithmeticTermDef) rightTermDef).termIntegerMapDef.termMap
						.entrySet()) {
					termIntegerMapDef.termMap.put(entry.getKey(),
							entry.getValue() * ((IntConstantTermDef) leftTermDef).val);
				}
			} else {
				// not supporting DIVIDE operator between arithmetic term and int term
				throw new SafetyException("Expr not supported " + originalExpr.toString());
			}
		}

		return storeArithmeticTermTwoConstraintLists(originalExpr, constraints, leftConstraintList, rightConstraintList,
				termIntegerMapDef);
	}

	private ConstraintListCombo createArithmeticTermForTwoArithmetics(String opName, Expr originalExpr,
			List<MistralConstraint> constraints, List<MistralConstraint> leftConstraintList,
			List<MistralConstraint> rightConstraintList, Term leftTerm, Term rightTerm, TermDef leftTermDef,
			TermDef rightTermDef) {
		String termIntegerMapDefName = createValidAndUniqueName(nodeNamePrefix + "_TermIntegerMap");

		TermIntegerMapDef termIntegerMapDef = new TermIntegerMapDef(termIntegerMapDefName);

		if (opName.equals("PLUS")) {
			termIntegerMapDef.termMap.putAll(((ArithmeticTermDef) leftTermDef).termIntegerMapDef.termMap);
			termIntegerMapDef.termMap.putAll(((ArithmeticTermDef) rightTermDef).termIntegerMapDef.termMap);
		} else if (opName.equals("MINUS")) {
			termIntegerMapDef.termMap.putAll(((ArithmeticTermDef) leftTermDef).termIntegerMapDef.termMap);
			// apply -1 to every entry's value in the rightTerm map
			for (Map.Entry<Term, Integer> entry : ((ArithmeticTermDef) rightTermDef).termIntegerMapDef.termMap
					.entrySet()) {
				termIntegerMapDef.termMap.put(entry.getKey(), entry.getValue() * -1);
			}
		} else {
			// not supporting MULTIPLE or DIVIDE operator between two arithmetic terms
			throw new SafetyException("Expr not supported " + originalExpr.toString());
		}

		return storeArithmeticTermTwoConstraintLists(originalExpr, constraints, leftConstraintList, rightConstraintList,
				termIntegerMapDef);
	}

	private ConstraintListCombo storeArithmeticTermTwoConstraintLists(Expr originalExpr,
			List<MistralConstraint> constraints, List<MistralConstraint> leftConstraintList,
			List<MistralConstraint> rightConstraintList, TermIntegerMapDef termIntegerMapDef) {
		constraints.addAll(leftConstraintList);
		constraints.addAll(rightConstraintList);

		constraints.add(termIntegerMapDef);

		// create arithmetic term def
		String arithmeticTermName = createValidAndUniqueName(nodeNamePrefix + "_ArithmeticTerm");
		ArithmeticTermDef arithmeticTermDef = new ArithmeticTermDef(arithmeticTermName, termIntegerMapDef);
		constraints.add(arithmeticTermDef);

		// create term for reference
		Term arithmeticTerm = new Term(arithmeticTermName);

		// add to compExprConstraint map
		compExprConstraintMap.put(originalExpr.toString(), arithmeticTerm);

		// add to compTermDefMap
		compTermDefMap.put(arithmeticTerm, arithmeticTermDef);

		ConstraintListCombo combo = new ConstraintListCombo(arithmeticTerm, constraints);
		return combo;
	}

	private ConstraintListCombo storeArithmeticTermSingleConstraintList(UnaryExpr e,
			List<MistralConstraint> constraints, List<MistralConstraint> constraintList,
			TermIntegerMapDef termIntegerMapDef) {
		constraints.addAll(constraintList);
		constraints.add(termIntegerMapDef);

		// create arithmetic term def
		String arithmeticTermName = createValidAndUniqueName(nodeNamePrefix + "_ArithmeticTerm");
		ArithmeticTermDef arithmeticTermDef = new ArithmeticTermDef(arithmeticTermName, termIntegerMapDef);
		constraints.add(arithmeticTermDef);

		// create term for reference
		Term arithmeticTerm = new Term(arithmeticTermName);

		// add to compExprConstraint map
		compExprConstraintMap.put(e.toString(), arithmeticTerm);

		// add to compTermDefMap
		compTermDefMap.put(arithmeticTerm, arithmeticTermDef);

		ConstraintListCombo combo = new ConstraintListCombo(arithmeticTerm, constraints);
		return combo;
	}

	private ConstraintListCombo createIntConstantTermfromIntExpr(IntExpr e, List<MistralConstraint> constraints) {
		int value = e.value.intValue();

		// check if constant term already exists, if yes, retrieve it
		Term constantTerm = null;
		if (intTermMap.containsKey(new Integer(value))) {
			constantTerm = intTermMap.get(new Integer(value));
		}
		// if does not exist, create it
		if (constantTerm == null) {
			// create unique names with agree node name prefix if the name doesn't exist
			String intTermName = createValidAndUniqueName("Constant_" + value + "_term");
			// create int constant term def
			IntConstantTermDef intConstTermDef = new IntConstantTermDef(intTermName, value);
			// add to constraint list
			constraints.add(intConstTermDef);
			// create term for reference
			constantTerm = new Term(intTermName);
			// add to compTermDefMap
			compTermDefMap.put(constantTerm, intConstTermDef);
			// add to compExprConstraint map
			compExprConstraintMap.put(e.toString(), constantTerm);
			// add to intTermMap
			intTermMap.put(new Integer(value), constantTerm);
		}
		//if it's in the verification's intTermMap but not in this node's compTermDefMap, add it to compTermDefMap
		else {
			if (compTermDefMap.get(constantTerm) == null) {
				// create unique names with agree node name prefix if the name doesn't exist
				String intTermName = createValidAndUniqueName("Constant_" + value + "_term");
				// create int constant term def
				IntConstantTermDef intConstTermDef = new IntConstantTermDef(intTermName, value);
				// add to constraint list
				constraints.add(intConstTermDef);
				// add to compTermDefMap
				compTermDefMap.put(constantTerm, intConstTermDef);
				// add to compExprConstraint map
				compExprConstraintMap.put(e.toString(), constantTerm);
			}
		}

		ConstraintListCombo combo = new ConstraintListCombo(constantTerm, constraints);
		return combo;
	}

	// if Boolean type, create a constraint reference without definition as it'll be used in constraint assignment
	private ConstraintListCombo createConstraintRefFromBoolTypeIdVarWithoutDef(IdExpr e,
			List<MistralConstraint> constraints) {
		// create constraint name
		String binaryConstraintName = createValidAndUniqueName(nodeNamePrefix + "_Constraint");
		// create constraint for reference
		Constraint varConstraint = new Constraint(binaryConstraintName);
		// add to compExprConstraintMap
		compExprConstraintMap.put(e.toString(), varConstraint);

		ConstraintListCombo combo = new ConstraintListCombo(varConstraint, constraints);
		return combo;
	}

	// if Boolean type, create a variable term out of it and return the term
	// and a constraint out of it and return the constraint
	private ConstraintListCombo createVarTermFromBoolTypeIdVarWithDef(IdExpr e, List<MistralConstraint> constraints) {
		// add nodeNamePrefix except for fault triggers
		// fault triggers are globally unique and we want to keep that way
		String idName = "";
		if (AddFaultsToNodeVisitor.faultTriggerToFaultMap.containsKey(e.id)) {
			idName = e.id;
		} else {
			idName = nodeNamePrefix + "_" + e.id;
		}
		Term boolTypeTerm = compIdTermMap.get(new CompIdPair(nodeNamePrefix, idName));
		if (boolTypeTerm == null) {
			// create unique names with agree node name prefix if the name doesn't exist
			String termName = createValidAndUniqueName(idName + "_term");
			// create term def
			VariableTermDef varTermDef = new VariableTermDef(termName, idName);
			constraints.add(varTermDef);
			// create term for reference
			boolTypeTerm = new Term(termName);
			// add to compTermDefMap
			compTermDefMap.put(boolTypeTerm, varTermDef);
			// add to compIdTermMap
			// use the original id to add to the map
			compIdTermMap.put(new CompIdPair(nodeNamePrefix, e.id), boolTypeTerm);
		} else {
			TermDef termDef = compTermDefMap.get(boolTypeTerm);
			if (termDef != null) {
				if (termDef instanceof VariableTermDef) {
					idName = ((VariableTermDef) termDef).varId;
				}
			}
		}
		// check if constant term already exists, if yes, retrieve it
		Term constantTerm = null;
		if (intTermMap.containsKey(new Integer(1))) {
			constantTerm = intTermMap.get(new Integer(1));
		}
		// if does not exist, create it
		if (constantTerm == null) {
			// create unique name
			String intTermName = createValidAndUniqueName("Constant_" + "1" + "_term");
			IntConstantTermDef intConstTermDef = new IntConstantTermDef(intTermName, 1);
			constraints.add(intConstTermDef);
			// create term for reference
			constantTerm = new Term(intTermName);
			// add to compTermDefMap
			compTermDefMap.put(constantTerm, intConstTermDef);
			// add to intTermMap
			intTermMap.put(new Integer(1), constantTerm);
		}
		else {
			// if it's in the verification's intTermMap but not in this node's compTermDefMap
			// add it to compTermDefMap
			if (compTermDefMap.get(constantTerm) == null) {
				// create unique name
				String intTermName = createValidAndUniqueName("Constant_" + "1" + "_term");
				IntConstantTermDef intConstTermDef = new IntConstantTermDef(intTermName, 1);
				constraints.add(intConstTermDef);
				// add to compTermDefMap
				compTermDefMap.put(constantTerm, intConstTermDef);
			}
		}

		// create constraint name
		String binaryConstraintName = createValidAndUniqueName(nodeNamePrefix + "_Constraint");
		// create constraint for the term
		BinaryTermConstraintDef binaryTermConstraintDef = new BinaryTermConstraintDef(binaryConstraintName,
				boolTypeTerm, constantTerm, BinaryTermConstraintOp.fromName("ATOM_EQ"));
		constraints.add(binaryTermConstraintDef);
		// create constraint for reference
		Constraint varConstraint = new Constraint(binaryConstraintName);
		// add to compExprConstraintMap
		compExprConstraintMap.put(e.toString(), varConstraint);

		ConstraintListCombo combo = new ConstraintListCombo(varConstraint, constraints);
		return combo;
	}

	// if Integer type, create a variable term out of it and return the term
	private ConstraintListCombo createVarTermfromIntTypeIdExprWithDef(IdExpr e, List<MistralConstraint> constraints) {
		String idName = nodeNamePrefix + "_" + e.id;
		Term intTypeTerm = compIdTermMap.get(new CompIdPair(nodeNamePrefix, idName));
		if (intTypeTerm == null) {
			// create unique names with agree node name prefix if the name doesn't exist
			String termName = createValidAndUniqueName(idName + "_term");
			// create term def
			VariableTermDef varTermDef = new VariableTermDef(termName, idName);
			constraints.add(varTermDef);
			// create term for reference
			intTypeTerm = new Term(termName);
			// add to compTermDefMap
			compTermDefMap.put(intTypeTerm, varTermDef);
			// use the original id to add to compIdTermMap
			compIdTermMap.put(new CompIdPair(nodeNamePrefix, e.id), intTypeTerm);
			// add to compExprConstraint map
			compExprConstraintMap.put(e.toString(), intTypeTerm);
		}
		ConstraintListCombo combo = new ConstraintListCombo(intTypeTerm, constraints);
		return combo;
	}

	// if Integer type, create a term reference without definition as it will be used in term assignment
	private ConstraintListCombo createVarTermfromIntTypeIdExprWithoutDef(IdExpr e,
			List<MistralConstraint> constraints) {
		String idName = nodeNamePrefix + "_" + e.id;
		Term intTypeTerm = compIdTermMap.get(new CompIdPair(nodeNamePrefix, idName));
		if (intTypeTerm == null) {
			// create unique names with agree node name prefix if the name doesn't exist
			String termName = createValidAndUniqueName(idName + "_term");
			// create term for reference
			intTypeTerm = new Term(termName);
			// add to compIdTermMap
			// use the original id to add to the map
			compIdTermMap.put(new CompIdPair(nodeNamePrefix, e.id), intTypeTerm);
			// add to compExprConstraint map
			compExprConstraintMap.put(e.toString(), intTypeTerm);
		} else {
			// if it's already defined, then it's being reassigned
			// throw an exception
			throw new SafetyException(idName + " being reassigned");
		}
		ConstraintListCombo combo = new ConstraintListCombo(intTypeTerm, constraints);
		return combo;
	}

	private ConstraintListCombo visitIdExpr(IdExpr e, boolean createDef) {
		List<MistralConstraint> constraints = new ArrayList<MistralConstraint>();
		// if expr already defined for within this node, just retrieve the constraint
		if (compExprConstraintMap.containsKey(e.toString())) {
			ConstraintListCombo combo = new ConstraintListCombo(compExprConstraintMap.get(e.toString()), constraints);
			return combo;
		}
		// otherwise
		// find the type of the id
		// if Boolean type, create a variable term and a constraint out of it and return the constraint
		// if Integer type, create a variable term out of it and return the term
		// for other types, throw an exception (as we don't support other types yet)
		else {
			Type type = null;
			// find the type of the id
			type = getTypeFromCompIdTypeMap(nodeNamePrefix, e.id);
			if(type == null){
				// since lustre node translation does add component id in front of variable ids in the expression
				// check if after stripping the prefix before the first "__" from the id
				// see if it can be found in the map
				int underscoreIndex = e.id.indexOf("__");
				if (underscoreIndex != -1) {
					String updatedId = e.id.substring(underscoreIndex + 2);
					String updatedNodeName = e.id.substring(0, underscoreIndex);
					type = getTypeFromCompIdTypeMap(updatedNodeName, updatedId);
				}
				// see if it's a variable that belongs to the top level node
				else {
					type = getTypeFromCompIdTypeMap(topNodeNamePrefix, e.id);
				}
			}
			if (type == null) {
				throw new SafetyException("Type not found for " + e.id);
			}

			if (type instanceof NamedType) {
				NamedType namedType = (NamedType) type;
				String typeName = namedType.name;
				// if Boolean type, create a variable term and a constraint out of it and return the constraint
				if (typeName.equals("bool")) {
					if (createDef) {
						return createVarTermFromBoolTypeIdVarWithDef(e, constraints);
					} else {
						return createConstraintRefFromBoolTypeIdVarWithoutDef(e, constraints);
					}
				}
				// if Integer type, create a variable term out of it and return the term
				else if (typeName.equals("int")) {
					if (createDef) {
						return createVarTermfromIntTypeIdExprWithDef(e, constraints);
					} else {
						return createVarTermfromIntTypeIdExprWithoutDef(e, constraints);
					}
				}
				// for other types, throw an exception (as we don't support other types yet)
				else {
					throw new SafetyException(e.id + " type " + type.toString() + " not supported");
				}

			} else {
				throw new SafetyException(e.id + " type " + type.toString() + " not supported");
			}
		}
	}
}