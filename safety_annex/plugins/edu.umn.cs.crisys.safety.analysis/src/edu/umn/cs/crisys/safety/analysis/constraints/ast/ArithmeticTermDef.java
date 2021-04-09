package edu.umn.cs.crisys.safety.analysis.constraints.ast;

import edu.umn.cs.crisys.safety.analysis.constraints.visitors.ConstraintAstVisitor;
import jkind.Assert;

public class ArithmeticTermDef extends TermDef {

	public final TermIntegerMapDef termIntegerMapDef;

	public ArithmeticTermDef(String termId, TermIntegerMapDef termIntegerMapDef) {
		super(termId);
		Assert.isNotNull(termIntegerMapDef);
		this.termIntegerMapDef = termIntegerMapDef;
	}

	@Override
	public <T> T accept(ConstraintAstVisitor<T> visitor) {
		return visitor.visit(this);
	}

}