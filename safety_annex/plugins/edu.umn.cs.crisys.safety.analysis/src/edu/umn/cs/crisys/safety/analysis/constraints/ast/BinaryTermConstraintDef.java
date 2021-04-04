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

//	String idName = e.id;
//	String termName = e.id + "_";
//	//create term def
//	VariableTermDef varTermDef = new VariableTermDef(termName, idName);
//	constraints.add(varTermDef);
//	//create term for reference
//	Term varTerm = new Term(termName);
//	// TODO: create a map for the type of the Id, to know what value to assign in the constraint
//	// for now assign it to 1
//	IntConstantTermDef intConstTermDef = new IntConstantTermDef("1", 1);
//	// create constraint for the term
//	BinaryTermConstraintDef binaryTermConstraintDef = new BinaryTermConstraintDef(idName, varTerm,
//			intConstTermDef,
//			BinaryTermConstraintOp.fromName("ATOM_EQ"));

}