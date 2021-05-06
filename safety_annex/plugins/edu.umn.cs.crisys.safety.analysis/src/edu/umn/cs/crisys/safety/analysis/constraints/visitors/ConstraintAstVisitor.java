package edu.umn.cs.crisys.safety.analysis.constraints.visitors;

import edu.umn.cs.crisys.safety.analysis.constraints.ast.ArithmeticTermDef;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.BinaryTermConstraintDef;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.BooleanConstantConstraintDef;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.Constraint;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.ConstraintAssignment;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.ConstraintComment;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.ExprConstraintDef;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.FunctionTermDef;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.IntConstantTermDef;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.Term;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.TermAssignment;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.TermIntegerMapDef;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.TermTermMapDef;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.TopConstraintDef;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.VariableTermDef;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.expr.ConstraintBinaryExpr;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.expr.ConstraintUnaryExpr;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.expr.SingleConstraintExpr;

public interface ConstraintAstVisitor<T> {

	public T visit(BinaryTermConstraintDef binaryTermConstraintDef);

	public T visit(BooleanConstantConstraintDef booleanConstantConstraintDef);

	public T visit(ExprConstraintDef exprConstraintDef);

	public T visit(TopConstraintDef topConstraintDef);

	public T visit(Constraint constraint);

	public T visit(ArithmeticTermDef arithmeticTermDef);

	public T visit(FunctionTermDef functionTermDef);

	public T visit(IntConstantTermDef intConstantTermDef);

	public T visit(TermIntegerMapDef termIntegerMap);

	public T visit(TermTermMapDef termTermMapDef);

	public T visit(Term term);

	public T visit(VariableTermDef variableTermDef);

	public T visit(ConstraintBinaryExpr binaryExpr);

	public T visit(ConstraintUnaryExpr unaryExpr);

	public T visit(SingleConstraintExpr singleExpr);

	public T visit(ConstraintComment comment);

	public T visit(ConstraintAssignment constraintAssignment);

	public T visit(TermAssignment termAssignment);

}
