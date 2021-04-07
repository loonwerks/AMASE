package edu.umn.cs.crisys.safety.analysis.generators;

import java.util.ArrayList;
import java.util.List;

import org.osate.aadl2.instance.ComponentInstance;

import com.rockwellcollins.atc.agree.agree.impl.AssignStatementImpl;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeNode;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeProgram;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeStatement;
import com.rockwellcollins.atc.agree.analysis.translation.AgreeNodeToLustreContract;

import edu.umn.cs.crisys.safety.analysis.constraints.ast.Constraint;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.ConstraintListCombo;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.ExprConstraintDef;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.MistralConstraint;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.TopConstraintDef;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.expr.SingleConstraintExpr;
import edu.umn.cs.crisys.safety.analysis.constraints.visitors.LustreExprToConstraintsVisitor;
import jkind.lustre.BinaryExpr;
import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.Node;
import jkind.lustre.UnaryExpr;
import jkind.lustre.UnaryOp;

public class ModelToConstraintsGenerator {

	AgreeNode topAgreeNode;
	ComponentInstance topCompInst;
	AgreeProgram agreeProgram;
	LustreExprToConstraintsVisitor lustreExprToConstraintVisitor = new LustreExprToConstraintsVisitor();
	List<MistralConstraint> constraints = new ArrayList<MistralConstraint>();

	public ModelToConstraintsGenerator(AgreeNode topAgreeNode, ComponentInstance topCompInst,
			AgreeProgram agreeProgram) {
		this.topAgreeNode = topAgreeNode;
		this.topCompInst = topCompInst;
		this.agreeProgram = agreeProgram;
	}

	public List<MistralConstraint> generateConstraints() {
		// generate FT for each top level guarantee
		for (AgreeStatement topLevelGuarantee : agreeProgram.topNode.guarantees) {
			// reset visitor per component
			resetVisitor("Top");
			// Step 1: negate the top level guarantee expression and create constraints
			UnaryExpr topLevelEvent = new UnaryExpr(UnaryOp.NOT, topLevelGuarantee.expr);
			ConstraintListCombo topGuaranteeReturnCombo = lustreExprToConstraintVisitor.visit(topLevelEvent);
			constraints.addAll(topGuaranteeReturnCombo.constraintList);
			// create constraint def for TLE
			SingleConstraintExpr tleConstraintExpr = new SingleConstraintExpr(topGuaranteeReturnCombo.lastConstraint);
			ExprConstraintDef tleConstraintDef = new ExprConstraintDef("TLE", tleConstraintExpr);
			constraints.add(tleConstraintDef);

			// translate all eq variables created for the top node
			for (AgreeStatement assertion : topAgreeNode.assertions) {
				if (assertion instanceof AgreeStatement) {
					if (assertion.reference instanceof AssignStatementImpl) {
						if (assertion.expr instanceof BinaryExpr) {
							BinaryExpr curExpr = (BinaryExpr) assertion.expr;
							ConstraintListCombo topEqReturnCombo = lustreExprToConstraintVisitor.visit(curExpr);
							constraints.addAll(topEqReturnCombo.constraintList);
						}
					}
				}
			}

			// overall top constraint def
			TopConstraintDef topConstraintDef = new TopConstraintDef("all_guarantees");

			// For each agree node in this verification layer
			// translate the guarantee into constraints
			for (AgreeNode agreeNode : agreeProgram.agreeNodes) {
				// only check non top node here
				if (!isTopNode(agreeNode)) {
					// get agreeNode name
					String agreeNodeName = agreeNode.id;
					// reset visitor per component
					resetVisitor(agreeNodeName);
					// create top constraint def for this node
					String nodeTopConstraintName = agreeNodeName + "_Guarantees";
					TopConstraintDef nodeTopConstraintDef = new TopConstraintDef(nodeTopConstraintName);
					// for each component, get the agree node and lustre node for that component
					// Translate Agree Node to Lustre Node with pre-statement flatten, helper nodes inlined,
					// and variable declarations sorted so they are declared before use
					Node curLustreNode = AgreeNodeToLustreContract.translate(agreeNode, agreeProgram);
					// go through all equation expr in the lustre node and translate to constraints
					for (Equation equation : curLustreNode.equations) {
						Expr srcExpr = equation.expr;
						ConstraintListCombo nodeReturnCombo = lustreExprToConstraintVisitor.visit(srcExpr);
						constraints.addAll(nodeReturnCombo.constraintList);
						// check if it's a guarantee, if yes, store the constraint generated to save to the top level constraint
						if (equation.lhs.get(0).id.contains("__GUARANTEE")) {
							nodeTopConstraintDef.addConstraint(nodeReturnCombo.lastConstraint);
						}
					}
					constraints.add(nodeTopConstraintDef);
					// create constraint for reference
					Constraint nodeTopConstraint = new Constraint(nodeTopConstraintName);
					// add node top constraint to overall top constraint def
					topConstraintDef.addConstraint(nodeTopConstraint);
				}
			}

			// TODO: For all connections in this verification layer
			// add to connectivity map connecting the input and output constraints created

			// Add overall constraint def
			constraints.add(topConstraintDef);
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

	private void resetVisitor(String agreeNodeName) {
		// set node name prefix for constraint naming
		lustreExprToConstraintVisitor.setNodeNamePrefix(agreeNodeName + "_");
		// clear index for constraint naming
		lustreExprToConstraintVisitor.resetNameIndex();
		// clear component constraint map
		lustreExprToConstraintVisitor.clearCompExprConstraintMap();
	}

}
