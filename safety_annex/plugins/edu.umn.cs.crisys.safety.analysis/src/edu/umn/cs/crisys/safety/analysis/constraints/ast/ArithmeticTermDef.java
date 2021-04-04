package edu.umn.cs.crisys.safety.analysis.constraints.ast;

import edu.umn.cs.crisys.safety.analysis.constraints.visitors.ConstraintAstVisitor;

public class ArithmeticTermDef extends TermDef {

	public ArithmeticTermDef(String termId) {
		super(termId);
		// TODO Auto-generated constructor stub
	}

	@Override
	public <T> T accept(ConstraintAstVisitor<T> visitor) {
		return visitor.visit(this);
	}

}