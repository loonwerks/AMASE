package edu.umn.cs.crisys.safety.analysis.constraints.visitors;

import edu.umn.cs.crisys.safety.analysis.constraints.ast.ArithmeticTermDef;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.BinaryTermConstraintDef;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.BooleanConstantConstraintDef;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.BooleanConstantTermDef;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.Constraint;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.ExprConstraintDef;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.FunctionTermDef;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.IntConstantTermDef;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.MistralConstraint;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.Term;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.VariableTermDef;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.expr.ConstraintBinaryExpr;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.expr.ConstraintUnaryExpr;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.expr.SingleConstraintExpr;

public class ConstraintPrintVisitor implements ConstraintAstVisitor<Void> {
	private StringBuilder sb = new StringBuilder();

	@Override
	public String toString() {
		return sb.toString();
	}

	protected void write(Object o) {
		sb.append(o);
	}

	protected void writeln(Object o) {
		sb.append(o);
		newline();
	}

	private static final String seperator = System.getProperty("line.separator");

	private void newline() {
		write(seperator);
	}

	public Void visit(MistralConstraint mistralConstraint) {
		return mistralConstraint.accept(this);
	}

	@Override
	public Void visit(ArithmeticTermDef termDef) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void visit(BooleanConstantTermDef termDef) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void visit(IntConstantTermDef termDef) {
		write("ConstantTerm::make(" + termDef.val + ")");
		return null;
	}

	@Override
	public Void visit(VariableTermDef termDef) {
		write("Term* " + termDef.termId);
		write(" = VariableTerm::make(\"");
		write(termDef.varId);
		writeln("\");");
		return null;
	}

	@Override
	public Void visit(FunctionTermDef termDef) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void visit(BinaryTermConstraintDef constraintDef) {
		write("Constraint " + constraintDef.constraintId);
		write("(");
		visit(constraintDef.leftTerm);
		write(", ");
		visit(constraintDef.rightTerm);
		write(", ");
		write(constraintDef.binaryOp.toString());
		writeln(");");
		return null;
	}

	@Override
	public Void visit(BooleanConstantConstraintDef booleanConstantConstraintDef) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void visit(Constraint constraint) {
		write(constraint.constraintId);
		return null;
	}

	@Override
	public Void visit(ExprConstraintDef exprConstraintDef) {
		write("Constraint " + exprConstraintDef.constraintId + " = ");
		write("(");
		visit(exprConstraintDef.expr);
		write(")");
		return null;
	}

	@Override
	public Void visit(Term term) {
		write(term.termId);
		return null;
	}

	@Override
	public Void visit(ConstraintBinaryExpr binaryExpr) {
		write("(");
		visit(binaryExpr.left);
		write(" " + binaryExpr.op.toString() + " ");
		visit(binaryExpr.right);
		write(")");
		return null;
	}

	@Override
	public Void visit(ConstraintUnaryExpr unaryExpr) {
		write(unaryExpr.op.toString());
		write("(");
		visit(unaryExpr.expr);
		write(")");
		return null;
	}

	@Override
	public Void visit(SingleConstraintExpr singleExpr) {
		visit(singleExpr.constraint);
		return null;
	}

}
