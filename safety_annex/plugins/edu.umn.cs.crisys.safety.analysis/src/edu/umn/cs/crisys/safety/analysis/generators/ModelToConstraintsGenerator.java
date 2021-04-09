package edu.umn.cs.crisys.safety.analysis.generators;

import java.util.ArrayList;
import java.util.List;

import org.osate.aadl2.instance.ComponentInstance;

import com.rockwellcollins.atc.agree.agree.impl.AssignStatementImpl;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeNode;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeProgram;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeStatement;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeVar;
import com.rockwellcollins.atc.agree.analysis.translation.AgreeNodeToLustreContract;

import edu.umn.cs.crisys.safety.analysis.SafetyException;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.Constraint;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.ConstraintListCombo;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.ExprConstraintDef;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.MistralConstraint;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.TopConstraintDef;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.expr.SingleConstraintExpr;
import edu.umn.cs.crisys.safety.analysis.constraints.visitors.LustreExprToConstraintsVisitor;
import edu.umn.cs.crisys.safety.safety.impl.FaultStatementImpl;
import jkind.lustre.BinaryExpr;
import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.Node;
import jkind.lustre.Type;
import jkind.lustre.UnaryExpr;
import jkind.lustre.UnaryOp;
import jkind.lustre.VarDecl;

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
		// update idType map
		updateNodeIdTypeMap();
		// generate FT for each top level guarantee
		for (AgreeStatement topLevelGuarantee : agreeProgram.topNode.guarantees) {
			// reset visitor per component
			resetVisitor("Top", topAgreeNode);
			// Step 1: negate the top level guarantee expression and create constraints
			UnaryExpr topLevelEvent = new UnaryExpr(UnaryOp.NOT, topLevelGuarantee.expr);
			ConstraintListCombo topGuaranteeReturnCombo = lustreExprToConstraintVisitor.visit(topLevelEvent);
			constraints.addAll(topGuaranteeReturnCombo.constraintList);
			// create constraint def for TLE
			// get the last constraint created
			// if the last construct is a constraint, proceed
			// otherwise, thrown an exception as we need to return constraint for each AGREE guarantee
			MistralConstraint tleLastConstraint = topGuaranteeReturnCombo.lastConstraint;
			if (tleLastConstraint instanceof Constraint) {
				SingleConstraintExpr tleConstraintExpr = new SingleConstraintExpr((Constraint) tleLastConstraint);
				ExprConstraintDef tleConstraintDef = new ExprConstraintDef("TLE", tleConstraintExpr);
				constraints.add(tleConstraintDef);

			} else {
				throw new SafetyException("No constraint created for " + topLevelEvent.toString());
			}

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
					resetVisitor(agreeNodeName, agreeNode);
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
							// get the last constraint created
							// if the last construct is a constraint, proceed
							// otherwise, thrown an exception as we need to return constraint for each AGREE guarantee
							MistralConstraint nodeLastConstraint = nodeReturnCombo.lastConstraint;
							if (nodeLastConstraint instanceof Constraint) {
								nodeTopConstraintDef.addConstraint((Constraint) nodeLastConstraint);
							} else {
								throw new SafetyException("No constraint created for " + topLevelEvent.toString());
							}
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

	private void resetVisitor(String agreeNodeName, AgreeNode agreeNode) {
		// set node name prefix for constraint naming
		lustreExprToConstraintVisitor.setNodeNamePrefix(agreeNodeName + "_");
		// clear index for constraint naming
		lustreExprToConstraintVisitor.resetNameIndex();
		// clear component constraint map
		lustreExprToConstraintVisitor.clearCompExprConstraintMap();
		// clear component term def map
		lustreExprToConstraintVisitor.clearCompTermDefMap();
		// clear component id type map
		// lustreExprToConstraintVisitor.clearCompIdTypeMap();
		// populate node component id type map
		// updateNodeIdTypeMap(agreeNode);
	}

	private void updateNodeIdTypeMap(AgreeNode agreeNode) {
		// go through all input ids and load the id and type to map
		Node curLustreNode = AgreeNodeToLustreContract.translate(agreeNode, agreeProgram);
		// go through all ids and load the id and type to map
		for (VarDecl varDecl : curLustreNode.inputs) {
			if (varDecl instanceof AgreeVar) {
				// exclude fault ids for now
				// TODO: revisit this
				if (((AgreeVar) varDecl).reference != null) {
					if ((((AgreeVar) varDecl).reference instanceof FaultStatementImpl)) {
						continue;
					}
				}
				String id = ((AgreeVar) varDecl).id;
				Type type = ((AgreeVar) varDecl).type;
				lustreExprToConstraintVisitor.addEntryToCompIdTypeMap(id, type);
			}
		}
	}

	private void updateNodeIdTypeMap() {
		for (AgreeNode agreeNode : agreeProgram.agreeNodes) {
			// go through all input ids and load the id and type to map
			Node curLustreNode = AgreeNodeToLustreContract.translate(agreeNode, agreeProgram);
			// go through all ids and load the id and type to map
			for (VarDecl varDecl : curLustreNode.inputs) {
				if (varDecl instanceof AgreeVar) {
					// exclude fault ids for now
					// TODO: revisit this
					if (((AgreeVar) varDecl).reference != null) {
						if ((((AgreeVar) varDecl).reference instanceof FaultStatementImpl)) {
							continue;
						}
					}
					String id = ((AgreeVar) varDecl).id;
					Type type = ((AgreeVar) varDecl).type;
					lustreExprToConstraintVisitor.addEntryToCompIdTypeMap(id, type);
				}
			}
		}
	}
}
