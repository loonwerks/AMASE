package edu.umn.cs.crisys.safety.analysis.constraints.ast;

import edu.umn.cs.crisys.safety.analysis.constraints.visitors.ConstraintAstVisitor;

public class BinaryTermConstraintDef extends ConstraintDef {

	public MistralConstraint leftTerm;
	public MistralConstraint rightTerm;
	public final BinaryTermConstraintOp binaryOp;

	public BinaryTermConstraintDef(String constraintId, MistralConstraint leftTerm, MistralConstraint rightTerm,
			BinaryTermConstraintOp binaryOp) {
		super(constraintId);
		this.leftTerm = leftTerm;
		this.rightTerm = rightTerm;
		this.binaryOp = binaryOp;
	}

	@Override
	public <T> T accept(ConstraintAstVisitor<T> visitor) {
		return visitor.visit(this);
	}

}