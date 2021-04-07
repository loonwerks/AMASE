package edu.umn.cs.crisys.safety.analysis.constraints.ast;

import java.util.ArrayList;
import java.util.List;

import edu.umn.cs.crisys.safety.analysis.constraints.visitors.ConstraintAstVisitor;

public class TopConstraintDef extends ConstraintDef {

	public List<Constraint> constraintList = new ArrayList<Constraint>();

	public TopConstraintDef(String constraintId) {
		super(constraintId);
	}

	public void addConstraint(Constraint constraint) {
		constraintList.add(constraint);
	}

	@Override
	public <T> T accept(ConstraintAstVisitor<T> visitor) {
		return visitor.visit(this);
	}

}