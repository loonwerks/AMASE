package edu.umn.cs.crisys.safety.analysis.generators;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.osate.aadl2.instance.ComponentInstance;
import org.osate.aadl2.instance.ConnectionInstance;
import org.osate.aadl2.instance.impl.ComponentInstanceImpl;
import org.osate.aadl2.instance.impl.SystemInstanceImpl;

import com.rockwellcollins.atc.agree.agree.impl.AssignStatementImpl;
import com.rockwellcollins.atc.agree.agree.impl.EqStatementImpl;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeNode;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeProgram;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeStatement;
import com.rockwellcollins.atc.agree.analysis.translation.AgreeNodeToLustreContract;

import edu.umn.cs.crisys.safety.analysis.SafetyException;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.Constraint;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.ConstraintComment;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.ConstraintListCombo;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.ExprConstraintDef;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.MistralConstraint;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.Term;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.TermTermMapDef;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.TopConstraintDef;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.expr.SingleConstraintExpr;
import edu.umn.cs.crisys.safety.analysis.constraints.visitors.LustreExprToConstraintsVisitor;
import edu.umn.cs.crisys.safety.util.SafetyUtil;
import jkind.Assert;
import jkind.lustre.BinaryExpr;
import jkind.lustre.BinaryOp;
import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.Node;
import jkind.lustre.Program;
import jkind.lustre.Type;
import jkind.lustre.UnaryExpr;
import jkind.lustre.UnaryOp;
import jkind.lustre.VarDecl;

public class ModelToConstraintsGenerator {

	AgreeNode topAgreeNode;
	ComponentInstance topCompInst;
	AgreeProgram agreeProgram;
	Program lustreProgram;
	LustreExprToConstraintsVisitor lustreExprToConstraintVisitor = new LustreExprToConstraintsVisitor();
	List<MistralConstraint> constraints = new ArrayList<MistralConstraint>();

	public ModelToConstraintsGenerator(AgreeNode topAgreeNode, ComponentInstance topCompInst, AgreeProgram agreeProgram,
			Program lustreProgram) {
		Assert.isNotNull(topAgreeNode);
		Assert.isNotNull(topCompInst);
		Assert.isNotNull(agreeProgram);
		Assert.isNotNull(lustreProgram);
		this.topAgreeNode = topAgreeNode;
		this.topCompInst = topCompInst;
		this.agreeProgram = agreeProgram;
		this.lustreProgram = lustreProgram;
	}

	public List<MistralConstraint> generateConstraints() {
		// update idType map
		// generate FT for each top level guarantee and lemma
		List<AgreeStatement> topLevelProperties = new ArrayList<AgreeStatement>();
		topLevelProperties.addAll(agreeProgram.topNode.guarantees);
		topLevelProperties.addAll(agreeProgram.topNode.lemmas);


		for (AgreeStatement topLevelGuarantee : topLevelProperties) {
			resetVisitorPerNode(topAgreeNode.id);
			Node topLustreNode = AgreeNodeToLustreContract.translate(agreeProgram.topNode, agreeProgram);
			// add inputs and locals to compIdTypeMap
			updateNodeIdTypeMap(topAgreeNode.id, topLustreNode);
			// add comment for node name
			ConstraintComment comment = new ConstraintComment("component: " + topAgreeNode.id);
			constraints.add(comment);
			// Step 1: negate the top level guarantee expression and create constraints
			UnaryExpr topLevelEvent = new UnaryExpr(UnaryOp.NOT, topLevelGuarantee.expr);
			// add topLevelEvent to comment
			comment = new ConstraintComment(topLevelEvent.toString());
			constraints.add(comment);
			// reset flags per equation
			resetVisitorPerEquation();
			// translate topLevelEvent to constraint
			ConstraintListCombo topGuaranteeReturnCombo = lustreExprToConstraintVisitor.visit(topLevelEvent);
			constraints.addAll(topGuaranteeReturnCombo.constraintList);
			// create constraint def for TLE
			// get the last constraint created
			// if the last construct is a constraint, proceed
			// otherwise, thrown an exception as we need to return constraint for each AGREE guarantee
			MistralConstraint tleLastConstraint = topGuaranteeReturnCombo.lastConstraint;
			if ((tleLastConstraint instanceof Constraint) && !lustreExprToConstraintVisitor.getAssignmentTranslated()) {
				SingleConstraintExpr tleConstraintExpr = new SingleConstraintExpr((Constraint) tleLastConstraint);
				// add comment for TLE
				comment = new ConstraintComment("TLE constraint");
				constraints.add(comment);
				// add TLE constraint
				ExprConstraintDef tleConstraintDef = new ExprConstraintDef("TLE", tleConstraintExpr);
				constraints.add(tleConstraintDef);

			} else {
				throw new SafetyException("No constraint created for " + topLevelEvent.toString());
			}

			// overall top constraint def
			TopConstraintDef topConstraintDef = new TopConstraintDef("all_guarantees");

			// For each agree node in this verification layer
			// translate the guarantee into constraints
			for (AgreeNode agreeNode : agreeProgram.agreeNodes) {
				// only check non top node here
				if (!isTopNode(agreeNode)) {
					// for node with fault definitions
					if (nodeWithFaultDefinitions(agreeNode)) {
						// get agreeNode name
						String agreeNodeName = agreeNode.id;
						// add comment for node name
						comment = new ConstraintComment("component: " + agreeNodeName);
						constraints.add(comment);
						// get the lustre node
						Node lustreNode = getOriginalLustreNode(agreeNodeName);
						// reset visitor per component
						resetVisitorPerNode(agreeNodeName);
						// create top constraint def for this node
						String nodeTopConstraintName = agreeNodeName + "_Guarantees";
						TopConstraintDef nodeTopConstraintDef = new TopConstraintDef(nodeTopConstraintName);

						// for each node, inline node calls and flatten pre of the lustre node that contains the fault definitions
						Node updatedLustreNode = SafetyUtil.inlineNodeCallsFlattenPres(lustreNode, lustreProgram);

						// add inputs and locals to compIdTypeMap
						updateNodeIdTypeMap(agreeNodeName, updatedLustreNode);

						// translate the equations
						for (Equation equation : updatedLustreNode.equations) {
							Expr leftExpr = equation.lhs.get(0);
							Expr rightExpr = equation.expr;
							// add expression from equation to comment
							BinaryExpr assignExpr = new BinaryExpr(rightExpr.location, leftExpr, BinaryOp.EQUAL,
									rightExpr);
							comment = new ConstraintComment(assignExpr.toString());
							constraints.add(comment);
							// check if it's an assert or guarantee
							// if yes, store the constraint generated from the rightExpr to save to the top level constraint
							// and no need to translate the leftExpr
							if (equation.lhs.get(0).id.contains("__ASSERT")
									|| equation.lhs.get(0).id.contains("__GUARANTEE")) {
								// reset flags per equation
								resetVisitorPerEquation();
								ConstraintListCombo rightReturnCombo = lustreExprToConstraintVisitor.visit(rightExpr);
								constraints.addAll(rightReturnCombo.constraintList);

								// get the last constraint created
								// if the last construct is a constraint, proceed
								// otherwise, thrown an exception as we need to return constraint for each AGREE guarantee
								MistralConstraint nodeLastConstraint = rightReturnCombo.lastConstraint;
								if ((nodeLastConstraint instanceof Constraint)
										&& !lustreExprToConstraintVisitor.getAssignmentTranslated()) {
									nodeTopConstraintDef.addConstraint((Constraint) nodeLastConstraint);
								}
							}
							// else create assign expression and create constraint out of it
							// and add to component top level constraint
							else {
								MistralConstraint nodeLastConstraint = createAssignExpr(leftExpr, rightExpr);
								if ((nodeLastConstraint instanceof Constraint)
										&& !lustreExprToConstraintVisitor.getAssignmentTranslated()) {
									nodeTopConstraintDef.addConstraint((Constraint) nodeLastConstraint);
								}
							}
						}
						// add comment for nodeTopConstraint
						comment = new ConstraintComment(
								"component top level constraint for component " + agreeNode.id);
						constraints.add(comment);
						// add component top level constraint def
						constraints.add(nodeTopConstraintDef);
						// create constraint for reference
						Constraint nodeTopConstraint = new Constraint(nodeTopConstraintName);
						// add node top constraint to overall top constraint def
						topConstraintDef.addConstraint(nodeTopConstraint);
					}
					// for node without fault definitions
					else {
						// get agreeNode name
						String agreeNodeName = agreeNode.id;
						comment = new ConstraintComment("component: " + agreeNodeName);
						constraints.add(comment);
						// reset visitor per component
						resetVisitorPerNode(agreeNodeName);
						// create top constraint def for this node
						String nodeTopConstraintName = agreeNodeName + "_Guarantees";
						TopConstraintDef nodeTopConstraintDef = new TopConstraintDef(nodeTopConstraintName);
						// for each component, get the agree node and lustre node for that component
						// Translate Agree Node to Lustre Node with pre-statement flatten, helper nodes inlined,
						// and variable declarations sorted so they are declared before use
						Node curLustreNode = AgreeNodeToLustreContract.translate(agreeNode, agreeProgram);

						// add inputs and locals to idTypeMap
						updateNodeIdTypeMap(agreeNodeName, curLustreNode);
						// go through all equation expr in the lustre node and translate to constraints
						for (Equation equation : curLustreNode.equations) {
							Expr srcExpr = equation.expr;
							// add equation to comment
							BinaryExpr assignExpr = new BinaryExpr(srcExpr.location, equation.lhs.get(0),
									BinaryOp.EQUAL, srcExpr);
							comment = new ConstraintComment(assignExpr.toString());
							constraints.add(comment);

							// reset flags per equation
							resetVisitorPerEquation();
							// translate expr to constraint
							ConstraintListCombo nodeReturnCombo = lustreExprToConstraintVisitor.visit(srcExpr);
							constraints.addAll(nodeReturnCombo.constraintList);
							// check if it's a guarantee, if yes, store the constraint generated to save to the component top level constraint
							if (equation.lhs.get(0).id.contains("__GUARANTEE")) {
								// get the last constraint created
								// if the last construct is a constraint, proceed
								// otherwise, thrown an exception as we need to return constraint for each AGREE guarantee
								MistralConstraint nodeLastConstraint = nodeReturnCombo.lastConstraint;
								if ((nodeLastConstraint instanceof Constraint)
										&& !lustreExprToConstraintVisitor.getAssignmentTranslated()) {
									nodeTopConstraintDef.addConstraint((Constraint) nodeLastConstraint);
								}
							}
						}

						// add comment for nodeTopConstraint
						comment = new ConstraintComment(
								"component top level constraint for component " + agreeNode.id);
						constraints.add(comment);
						// add component top level constraint def
						constraints.add(nodeTopConstraintDef);
						// create constraint for reference
						Constraint nodeTopConstraint = new Constraint(nodeTopConstraintName);
						// add node top constraint to overall top constraint def
						topConstraintDef.addConstraint(nodeTopConstraint);
					}
				}
			}

			// translate all eq variables created for the top node
			// Note: do this after translating all subnodes as the top node eq may reference subnode variables
			for (AgreeStatement assertion : topAgreeNode.assertions) {
				if (assertion instanceof AgreeStatement) {
					if ((assertion.reference instanceof AssignStatementImpl)
							|| (assertion.reference instanceof EqStatementImpl)) {
						if (assertion.expr instanceof BinaryExpr) {
							// get current assertion
							BinaryExpr curExpr = (BinaryExpr) assertion.expr;
							// add assertion to comment
							comment = new ConstraintComment(curExpr.toString());
							constraints.add(comment);
							// reset flags per equation
							resetVisitorPerEquation();
							// translate to constraint
							ConstraintListCombo topEqReturnCombo = lustreExprToConstraintVisitor.visit(curExpr);
							constraints.addAll(topEqReturnCombo.constraintList);
						}
					}
				}
			}

			// For all connections in this verification layer
			// add to connectivity map connecting the input and output constraints created
			// add connectivitiy(dest) = source
			String termTermMapDefName = lustreExprToConstraintVisitor.createValidAndUniqueName("connectivity");
			TermTermMapDef termTermMapDef = new TermTermMapDef(termTermMapDefName);

			addConnections(termTermMapDef);
			constraints.add(termTermMapDef);
			// add comment for overall constraint
			comment = new ConstraintComment("overall top level constraint");
			constraints.add(comment);
			// Add overall constraint def
			constraints.add(topConstraintDef);
		}
		return constraints;

	}

	private MistralConstraint createAssignExpr(Expr leftExpr, Expr rightExpr) {
		BinaryExpr assignExpr = new BinaryExpr(rightExpr.location, leftExpr, BinaryOp.EQUAL, rightExpr);
		// reset flags per equation
		resetVisitorPerEquation();
		ConstraintListCombo returnCombo = lustreExprToConstraintVisitor.visit(assignExpr);
		constraints.addAll(returnCombo.constraintList);
		return returnCombo.lastConstraint;
	}

	private Boolean isTopNode(AgreeNode agreeNode) {
		if (agreeNode.id.equals(topAgreeNode.id)) {
			return true;
		} else {
			return false;
		}
	}

	private Node getOriginalLustreNode(String agreeNodeName) {
		for (Node node : lustreProgram.nodes) {
			if (lustreAndAgreeNodeNamesMatch(node.id, agreeNodeName)) {
				return node;
			}
		}
		return null;
	}

	private void resetVisitorPerNode(String agreeNodeName) {
		// set node name prefix for constraint naming
		lustreExprToConstraintVisitor.setNodeNamePrefix(agreeNodeName);
		// clear index for constraint naming
		lustreExprToConstraintVisitor.resetNameIndex();
		// clear component constraint map
		lustreExprToConstraintVisitor.clearCompExprConstraintMap();
		// clear component term def map
		lustreExprToConstraintVisitor.clearCompTermDefMap();
	}

	private void resetVisitorPerEquation() {
		// by default translate to assignment
		lustreExprToConstraintVisitor.setTranslateToAssignment(true);
		// by default not yet translated to assignment
		lustreExprToConstraintVisitor.setAssignmentTranslated(false);
	}

	private void updateNodeIdTypeMap(String agreeNodeName, Node lustreNode) {
		// go through all input and output ids and load the id and type to map
		for (VarDecl varDecl : lustreNode.inputs) {
			String id = varDecl.id;
			Type type = varDecl.type;
			lustreExprToConstraintVisitor.addEntryToCompIdTypeMap(agreeNodeName, id, type);
		}
		// go through all eq var ids and load the id and type to map
		for (VarDecl varDecl : lustreNode.locals) {
			String id = varDecl.id;
			Type type = varDecl.type;
			lustreExprToConstraintVisitor.addEntryToCompIdTypeMap(agreeNodeName, id, type);
		}
	}

	private boolean nodeWithFaultDefinitions(AgreeNode agreeNode) {
		boolean result = false;
		AgreeNode faultyNode = SafetyUtil.faultyAgreeNodeFromNominalNode(agreeProgram.topNode.subNodes, agreeNode);
		if (faultyNode != null) {
			if (!faultyNode.assertions.isEmpty()) {
				result = true;
			}
		}
		return result;
	}

	private boolean lustreAndAgreeNodeNamesMatch(String lustreNodeName, String agreeNodeName) {
		boolean result = false;

		if (lustreNodeName != null && lustreNodeName.startsWith("_TOP__")) {
			String newLustreNodeName = lustreNodeName.split("_TOP__")[1];
			if (newLustreNodeName.equals(agreeNodeName)) {
				result = true;
			}
		}
		return result;
	}

	// For all connections in this verification layer
	// add to connectivity map connecting the input and output constraints created
	private void addConnections(TermTermMapDef termTermMapDef) {
		// go through all connection instances
		EList<ConnectionInstance> connectionInstances = topCompInst.getAllEnclosingConnectionInstances();
		for (ConnectionInstance connectionInstance : connectionInstances) {
			// get src comp name
			String srcCompName = "";
			if (connectionInstance.getSource().eContainer() instanceof ComponentInstanceImpl) {
				srcCompName = ((ComponentInstanceImpl) connectionInstance.getSource().eContainer()).getName();
			} else if (connectionInstance.getSource().eContainer() instanceof SystemInstanceImpl) {
				srcCompName = ((SystemInstanceImpl) connectionInstance.getSource().eContainer()).getName();
			}
			// get src id name
			String srcIdName = connectionInstance.getSource().getName();
			// look up to get the term
			Term srcTerm = lustreExprToConstraintVisitor.getTermFromCompIdTermMap(srcCompName, srcIdName);

			// get dest comp name
			String destCompName = "";
			if (connectionInstance.getDestination().eContainer() instanceof ComponentInstanceImpl) {
				destCompName = ((ComponentInstanceImpl) connectionInstance.getDestination().eContainer()).getName();
			} else if (connectionInstance.getDestination().eContainer() instanceof SystemInstanceImpl) {
				destCompName = ((SystemInstanceImpl) connectionInstance.getDestination().eContainer()).getName();
			}
			// get dest id name
			String destIdName = connectionInstance.getDestination().getName();
			// look up to get the term
			Term destTerm = lustreExprToConstraintVisitor.getTermFromCompIdTermMap(destCompName, destIdName);

			// add connection to comment
			ConstraintComment comment = new ConstraintComment(
					srcCompName + "." + srcIdName + " -> " + destCompName + "." + destIdName);
			constraints.add(comment);

			// if src or dest term is null, don't add it
			if ((srcTerm != null) && (destTerm != null)) {
				// add connectivitiy(dest) = source
				termTermMapDef.addEntry(destTerm, srcTerm);
			}
		}

	}
}
