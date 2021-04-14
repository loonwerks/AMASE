package edu.umn.cs.crisys.safety.analysis.constraints.visitors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import edu.umn.cs.crisys.safety.analysis.SafetyException;
import edu.umn.cs.crisys.safety.analysis.ast.visitors.NegateLustreExprVisitor;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.ArithmeticTermDef;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.BinaryTermConstraintDef;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.BinaryTermConstraintOp;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.BooleanConstantConstraintDef;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.CompIdPair;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.Constraint;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.ConstraintListCombo;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.ExprConstraintDef;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.IntConstantTermDef;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.MistralConstraint;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.Term;
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
	// use index to help create unique names
	private int nameIndex = 0;
	// store all MistralConstraintNames created to help create unique names
	private HashSet<String> constraintNames = new HashSet<>();
	// store the id and type
	private Map<String, Type> compIdTypeMap = new HashMap<>();
	// store the component id and term map
	private Map<CompIdPair, Term> compIdTermMap = new HashMap<>();

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
	}

	public void clearCompTermDefMap() {
		this.compTermDefMap.clear();
	}

	public void clearCompIdTypeMap() {
		this.compIdTypeMap.clear();
	}

	public void addEntryToCompIdTypeMap(String id, Type type) {
		compIdTypeMap.put(id, type);
	}

	public Term getTermFromCompIdTermMap(String compName, String idName) {
		return compIdTermMap.get(new CompIdPair(compName, idName));
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
				// visit the left and the right expression
				ConstraintListCombo leftReturnCombo = visit(e.left);
				ConstraintListCombo rightReturnCombo = visit(e.right);
				return createBinaryLogicalConstraint(opName, e, e.left, e.right, constraints, leftReturnCombo,
						rightReturnCombo);
			} else if (opName.equals("IMPLIES")) {
				// (a => b) <=> (not a or b)
				Expr newLeft = negateExprVisitor.visit(e.left);
				ConstraintListCombo leftReturnCombo = visit(newLeft);
				ConstraintListCombo rightReturnCombo = visit(e.right);
				return createBinaryLogicalConstraint("OR", e, newLeft, e.right, constraints, leftReturnCombo,
						rightReturnCombo);
			} else if (opName.equals("ARROW")) {
				// TODO: for now for arrow operator, only visit the left expression (initial value)
				return visit(e.left);
			} else if (opName.equals("EQUAL") || opName.equals("NOTEQUAL")) {
				ConstraintListCombo leftReturnCombo = visit(e.left);
				ConstraintListCombo rightReturnCombo = visit(e.right);
				MistralConstraint leftConstraint = leftReturnCombo.lastConstraint;
				MistralConstraint rightConstraint = rightReturnCombo.lastConstraint;
				if ((leftConstraint instanceof Term) && (rightConstraint instanceof Term)) {
					return createBinaryTermComparisonConstraint(opName, e, e.left, e.right, constraints,
							leftReturnCombo, rightReturnCombo);
				} else if ((leftConstraint instanceof Constraint) && (rightConstraint instanceof Constraint)) {
					return createBinaryLogicalConstraint(opName, e, e.left, e.right, constraints, leftReturnCombo,
							rightReturnCombo);
				}
				// otherwise throw an exception (as it means there it involves a boolean and non-boolean construct)
				else {
					// not supported
					throw new SafetyException("Expr not supported " + e.toString());
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
	// TODO: var = (if a then b else c)
	// has to be translated to
	// (not a or (var = b)) and (a or (var = c))
	public ConstraintListCombo visit(IfThenElseExpr e) {
		// if a then b else c <=>(a=>b) and (not a => c) <=> (not a or b) and (a or c)
		Expr condExpr = e.cond;
		Expr thenExpr = e.thenExpr;
		Expr elseExpr = e.elseExpr;
		Expr negCondExpr = negateExprVisitor.visit(condExpr);

		BinaryExpr exprLeft = new BinaryExpr(e.location, negCondExpr, BinaryOp.OR, thenExpr);
		BinaryExpr exprRight = new BinaryExpr(e.location, condExpr, BinaryOp.OR, elseExpr);
		BinaryExpr newExpr = new BinaryExpr(e.location, exprLeft, BinaryOp.AND, exprRight);
		return visit(newExpr);
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
			if (compIdTypeMap.get(e.id) != null) {
				type = compIdTypeMap.get(e.id);
			} else {
				// since lustre node translation does add component id in front of variable ids in the expression
				// check if after stripping the prefix before the first "__" from the id
				// see if it can be found in the map
				int underscoreIndex = e.id.indexOf("__");
				if (underscoreIndex != -1) {
					String updatedId = e.id.substring(underscoreIndex + 2);
					if (compIdTypeMap.get(updatedId) != null) {
						type = compIdTypeMap.get(updatedId);
					} else {
						// also lustre node translation does add agree node name + "." to the id name
						// check if after stripping the prefix before the first "." from the id
						// see if it can be found in the map
						int dotIndex = updatedId.indexOf(".");
						if (dotIndex != -1) {
							String furtherUpdatedId = updatedId.substring(dotIndex + 1);
							if (compIdTypeMap.get(furtherUpdatedId) != null) {
								type = compIdTypeMap.get(furtherUpdatedId);
							}
						}
					}
				}
			}
			if (type == null) {
				throw new SafetyException("Type not found for " + e.toString());
			}
			if (type instanceof NamedType) {
				NamedType namedType = (NamedType) type;
				String typeName = namedType.name;
				// if Boolean type, create a variable term and a constraint out of it and return the constraint
				if (typeName.equals("bool")) {
					return createVarTermFromBoolTypeIdVar(e, constraints);
				}
				// if Integer type, create a variable term out of it and return the term
				else if (typeName.equals("int")) {
					return createVarTermfromIntTypeIdExpr(e, constraints);
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

	private ConstraintListCombo createBinaryTermComparisonConstraint(String opName, Expr originalExpr, Expr left,
			Expr right, List<MistralConstraint> constraints, ConstraintListCombo leftReturnCombo,
			ConstraintListCombo rightReturnCombo) {
		// given a Boolean expression connected by comparison operators (==, !=, >, <, >=, <=)
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
		// create unique names with agree node name prefix if the name doesn't exist
		String termName = createValidAndUniqueName(nodeNamePrefix + "_" + value + "_term");
		// create int constant term def
		IntConstantTermDef intConstTermDef = new IntConstantTermDef("", value);
		// add to constraint list
		constraints.add(intConstTermDef);
		// create term for reference
		Term intConstTerm = new Term(termName);
		// add to compTermDefMap
		compTermDefMap.put(intConstTerm, intConstTermDef);
		// add to compExprConstraint map
		compExprConstraintMap.put(e.toString(), intConstTerm);

		ConstraintListCombo combo = new ConstraintListCombo(intConstTerm, constraints);
		return combo;
	}

	// if Boolean type, create a variable term and a constraint out of it and return the constraint
	private ConstraintListCombo createVarTermFromBoolTypeIdVar(IdExpr e, List<MistralConstraint> constraints) {
		// create unique names with agree node name prefix if the name doesn't exist
		String idName = createValidAndUniqueName(nodeNamePrefix + "_" + e.id);
		String termName = createValidAndUniqueName(nodeNamePrefix + "_" + e.id + "_term");
		// create term def
		VariableTermDef varTermDef = new VariableTermDef(termName, idName);
		constraints.add(varTermDef);
		// create term for reference
		Term varTerm = new Term(termName);
		// add to compTermDefMap
		compTermDefMap.put(varTerm, varTermDef);
		// add to compIdTermMap
		compIdTermMap.put(new CompIdPair(nodeNamePrefix, e.id), varTerm);

		// assign the value to 1
		IntConstantTermDef intConstTermDef = new IntConstantTermDef("", 1);
		// create constraint for the term
		BinaryTermConstraintDef binaryTermConstraintDef = new BinaryTermConstraintDef(idName, varTerm, intConstTermDef,
				BinaryTermConstraintOp.fromName("ATOM_EQ"));
		constraints.add(binaryTermConstraintDef);
		// create constraint for reference
		Constraint varConstraint = new Constraint(idName);
		// add to compIdTermMap map
		compExprConstraintMap.put(e.toString(), varConstraint);

		ConstraintListCombo combo = new ConstraintListCombo(varConstraint, constraints);
		return combo;
	}

	// if Integer type, create a variable term out of it and return the term
	private ConstraintListCombo createVarTermfromIntTypeIdExpr(IdExpr e, List<MistralConstraint> constraints) {
		// create unique names with agree node name prefix if the name doesn't exist
		String idName = createValidAndUniqueName(nodeNamePrefix + "_" + e.id);
		String termName = createValidAndUniqueName(nodeNamePrefix + "_" + e.id + "_term");
		// create term def
		VariableTermDef varTermDef = new VariableTermDef(termName, idName);
		constraints.add(varTermDef);
		// create term for reference
		Term varTerm = new Term(termName);
		// add to compTermDefMap
		compTermDefMap.put(varTerm, varTermDef);
		// add to compIdTermMap
		compIdTermMap.put(new CompIdPair(nodeNamePrefix, e.id), varTerm);

		ConstraintListCombo combo = new ConstraintListCombo(varTerm, constraints);
		return combo;
	}

}