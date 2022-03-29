package edu.umn.cs.crisys.safety.analysis.constraints.visitors;

import java.util.Map;

import edu.umn.cs.crisys.safety.analysis.constraints.ast.ArithmeticTermDef;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.BinaryTermConstraintDef;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.BooleanConstantConstraintDef;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.Constraint;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.ConstraintAssignment;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.ConstraintComment;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.ExprConstraintDef;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.FunctionTermDef;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.IntConstantTermDef;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.MistralConstraint;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.StringStringMapDef;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.Term;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.TermAssignment;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.TermIntegerMapDef;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.TermTermMapDef;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.TopConstraintDef;
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
		writeln("Term* " + termDef.termId + " = ArithmeticTerm::make(" + termDef.termIntegerMapDef.termId + ");");
		return null;
	}

	@Override
	public Void visit(IntConstantTermDef termDef) {
		writeln("Term* " + termDef.termId + " = ConstantTerm::make(" + termDef.val + ");");
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
		write(constraintDef.binaryOp.name());
		writeln(");");
		return null;
	}

	@Override
	public Void visit(BooleanConstantConstraintDef constraintDef) {
		write("Constraint ");
		write(constraintDef.constraintId);
		write("(");
		if (constraintDef.val) {
			write("true");
		} else {
			write("false");
		}
		writeln(");");
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
		// write("(");
		visit(exprConstraintDef.expr);
		// writeln(");");
		writeln(";");
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

	@Override
	public Void visit(TopConstraintDef topConstraintDef) {
		write("Constraint " + topConstraintDef.constraintId + " = ");
		boolean multipleElem = false;
		for (Constraint constraint : topConstraintDef.constraintList) {
			if (multipleElem) {
				write(" & ");
			}
			visit(constraint);
			multipleElem = true;
		}
		writeln(";");
		return null;
	}

	@Override
	public Void visit(TermIntegerMapDef termIntegerMapDef) {
		String mapName = termIntegerMapDef.termId;
		writeln("map<Term*, long int> " + mapName + ";");

		for (Map.Entry<Term, Integer> entry : termIntegerMapDef.termMap.entrySet()) {
			write(mapName + "[");
			visit(entry.getKey());
			writeln("] = " + entry.getValue() + ";");
		}
		return null;
	}

	@Override
	public Void visit(TermTermMapDef termTermMapDef) {
		String mapName = termTermMapDef.termId;
		writeln("map<Term*, Term*> " + mapName + ";");

		for (Map.Entry<Term, Term> entry : termTermMapDef.termMap.entrySet()) {
			write(mapName + "[");
			visit(entry.getKey());
			writeln("] = " + entry.getValue() + ";");
		}
		return null;
	}

	@Override
	public Void visit(ConstraintComment comment) {
		writeln("/* Translation for */");
		writeln("/*" + comment.codeStr + "*/");
		return null;
	}

	@Override
	public Void visit(ConstraintAssignment constraintAssignment) {
		write("Constraint ");
		visit(constraintAssignment.leftConstraint);
		write(" = ");
		visit(constraintAssignment.rightConstraint);
		writeln(";");
		return null;
	}

	@Override
	public Void visit(TermAssignment termAssignment) {
		// Term* C2___fault__nominal__out2_term = Constant_3_term;
		write("Term* ");
		visit(termAssignment.leftTerm);
		write(" = ");
		visit(termAssignment.rightTerm);
		writeln(";");
		return null;
	}

	@Override
	public Void visit(StringStringMapDef stringStringMapDef) {
		String mapName = stringStringMapDef.termId;
		writeln("map<string, string> " + mapName + ";");

		for (Map.Entry<String, String> entry : stringStringMapDef.stringMap.entrySet()) {
			write(mapName + "[\"");
			write(entry.getKey());
			writeln("\"] = " + "\"" + entry.getValue() + "\"" + ";");
		}
		return null;
	}
}