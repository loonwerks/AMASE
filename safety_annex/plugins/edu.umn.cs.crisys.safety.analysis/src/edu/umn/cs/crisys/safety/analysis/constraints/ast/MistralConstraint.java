package edu.umn.cs.crisys.safety.analysis.constraints.ast;

import edu.umn.cs.crisys.safety.analysis.constraints.visitors.ConstraintAstVisitor;
import edu.umn.cs.crisys.safety.analysis.constraints.visitors.ConstraintPrintVisitor;

public abstract class MistralConstraint {
	@Override
	public String toString() {
		ConstraintPrintVisitor visitor = new ConstraintPrintVisitor();
		accept(visitor);
		return visitor.toString();
	}

	public abstract <T> T accept(ConstraintAstVisitor<T> visitor);
}
