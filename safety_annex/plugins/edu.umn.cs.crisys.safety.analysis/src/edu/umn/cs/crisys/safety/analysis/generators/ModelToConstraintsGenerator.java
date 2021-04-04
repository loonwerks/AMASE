package edu.umn.cs.crisys.safety.analysis.generators;

import java.util.ArrayList;
import java.util.List;

import org.osate.aadl2.instance.ComponentInstance;

import com.rockwellcollins.atc.agree.analysis.ast.AgreeNode;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeProgram;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeStatement;

import edu.umn.cs.crisys.safety.analysis.constraints.ast.ConstraintListCombo;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.ExprConstraintDef;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.MistralConstraint;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.expr.SingleConstraintExpr;
import edu.umn.cs.crisys.safety.analysis.constraints.visitors.LustreExprToConstraintsVisitor;
import jkind.lustre.UnaryExpr;
import jkind.lustre.UnaryOp;

public class ModelToConstraintsGenerator {

	AgreeNode topAgreeNode;
	ComponentInstance topCompInst;
	AgreeProgram agreeProgram;
	LustreExprToConstraintsVisitor lustreExprToConstraintVisitor = new LustreExprToConstraintsVisitor();
	List<MistralConstraint> constraints = new ArrayList<MistralConstraint>();

	public ModelToConstraintsGenerator(AgreeNode topAgreeNode, ComponentInstance topCompInst, AgreeProgram agreeProgram) {
		this.topAgreeNode = topAgreeNode;
		this.topCompInst = topCompInst;
		this.agreeProgram = agreeProgram;
	}

	public List<MistralConstraint> generateConstraints() {
		// generate FT for each top level guarantee
		for (AgreeStatement topLevelGuarantee : agreeProgram.topNode.guarantees) {
			// Step 1: negate the top level guarantee expression and create constraints
			UnaryExpr topLevelEvent = new UnaryExpr(UnaryOp.NOT, topLevelGuarantee.expr);
			ConstraintListCombo returnCombo = lustreExprToConstraintVisitor.visit(topLevelEvent);
			constraints.addAll(returnCombo.constraintList);
			// create constraint def for TLE
			SingleConstraintExpr tleConstraintExpr = new SingleConstraintExpr(returnCombo.lastConstraint);
			ExprConstraintDef tleConstraintDef = new ExprConstraintDef("TLE", tleConstraintExpr);
			constraints.add(tleConstraintDef);

			// Step 2: for agree node in this verification layer
			// translate the guarantee into constraints
			// Step 3: for all connections in this verification layer
			// add to connectivity map connecting the input and output constraints created
		}
		return constraints;
	}

	private Boolean isTopNode(AgreeNode agreeNode) {
		if (agreeNode.id.equals(topAgreeNode.id)) {
			return true;
		} else {
			return false;
		}
	}

}
