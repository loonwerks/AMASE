package edu.umn.cs.crisys.safety.analysis.generators;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.osate.aadl2.instance.ComponentInstance;
import org.osate.aadl2.instance.ConnectionInstance;
import org.osate.aadl2.instance.impl.ComponentInstanceImpl;
import org.osate.aadl2.instance.impl.SystemInstanceImpl;

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
		// generate FT for each top level guarantee
		for (AgreeStatement topLevelGuarantee : agreeProgram.topNode.guarantees) {
			resetVisitor(topAgreeNode.id, topAgreeNode);
			Node topLustreNode = AgreeNodeToLustreContract.translate(agreeProgram.topNode, agreeProgram);
			updateNodeIdTypeMap(topLustreNode);
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
					// for node with fault definitions
					if (nodeWithFaultDefinitions(agreeNode)) {
						// get agreeNode name
						String agreeNodeName = agreeNode.id;
						// get the lustre node
						Node lustreNode = getOriginalLustreNode(agreeNodeName);
						// reset visitor per component
						resetVisitor(agreeNodeName, agreeNode);
						// create top constraint def for this node
						String nodeTopConstraintName = agreeNodeName + "_Guarantees";
						TopConstraintDef nodeTopConstraintDef = new TopConstraintDef(nodeTopConstraintName);

						// for each node, inline node calls and flatten pre of the lustre node that contains the fault definitions
						Node updatedLustreNode = SafetyUtil.inlineNodeCallsFlattenPres(lustreNode, lustreProgram);

						// add inputs and locals to idTypeMap
						updateNodeIdTypeMap(updatedLustreNode);

						// translate the equations
						for (Equation equation : updatedLustreNode.equations) {
							Expr leftExpr = equation.lhs.get(0);
							Expr rightExpr = equation.expr;
							// check if it's an assert, store the constraint generated from the rightExpr to save to the top level constraint
							// and no need to translate the leftExpr
							if (equation.lhs.get(0).id.contains("__ASSERT")) {
								ConstraintListCombo rightReturnCombo = lustreExprToConstraintVisitor.visit(rightExpr);
								constraints.addAll(rightReturnCombo.constraintList);

								// get the last constraint created
								// if the last construct is a constraint, proceed
								// otherwise, thrown an exception as we need to return constraint for each AGREE guarantee
								MistralConstraint nodeLastConstraint = rightReturnCombo.lastConstraint;
								if (nodeLastConstraint instanceof Constraint) {
									nodeTopConstraintDef.addConstraint((Constraint) nodeLastConstraint);
								} else {
									throw new SafetyException("No constraint created for " + equation.toString());
								}
							} else {
								createAssignExpr(leftExpr, rightExpr);
							}
						}
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
						// reset visitor per component
						resetVisitor(agreeNodeName, agreeNode);
						// create top constraint def for this node
						String nodeTopConstraintName = agreeNodeName + "_Guarantees";
						TopConstraintDef nodeTopConstraintDef = new TopConstraintDef(nodeTopConstraintName);
						// for each component, get the agree node and lustre node for that component
						// Translate Agree Node to Lustre Node with pre-statement flatten, helper nodes inlined,
						// and variable declarations sorted so they are declared before use
						Node curLustreNode = AgreeNodeToLustreContract.translate(agreeNode, agreeProgram);

						// add inputs and locals to idTypeMap
						updateNodeIdTypeMap(curLustreNode);
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
			}

			// For all connections in this verification layer
			// add to connectivity map connecting the input and output constraints created
			// add connectivitiy(dest) = source
			String termTermMapDefName = lustreExprToConstraintVisitor.createValidAndUniqueName("connectivity");
			TermTermMapDef termTermMapDef = new TermTermMapDef(termTermMapDefName);

			addConnections(termTermMapDef);
			constraints.add(termTermMapDef);
			// Add overall constraint def
			constraints.add(topConstraintDef);
		}
		return constraints;

	}

	private void createAssignExpr(Expr leftExpr, Expr rightExpr) {
		BinaryExpr assignExpr = new BinaryExpr(rightExpr.location, leftExpr, BinaryOp.EQUAL, rightExpr);
		ConstraintListCombo returnCombo = lustreExprToConstraintVisitor.visit(assignExpr);
		constraints.addAll(returnCombo.constraintList);
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

	private void resetVisitor(String agreeNodeName, AgreeNode agreeNode) {
		// set node name prefix for constraint naming
		lustreExprToConstraintVisitor.setNodeNamePrefix(agreeNodeName);
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

//	private void updateNodeIdTypeMap(AgreeNode agreeNode) {
//		// go through all input ids and load the id and type to map
//		Node curLustreNode = AgreeNodeToLustreContract.translate(agreeNode, agreeProgram);
//		// go through all ids and load the id and type to map
//		for (VarDecl varDecl : curLustreNode.inputs) {
//			if (varDecl instanceof AgreeVar) {
//				// exclude fault ids for now
//				// TODO: revisit this
//				if (((AgreeVar) varDecl).reference != null) {
//					if ((((AgreeVar) varDecl).reference instanceof FaultStatementImpl)) {
//						continue;
//					}
//				}
//				String id = ((AgreeVar) varDecl).id;
//				Type type = ((AgreeVar) varDecl).type;
//				lustreExprToConstraintVisitor.addEntryToCompIdTypeMap(id, type);
//			}
//		}
//	}

	private void updateNodeIdTypeMap(Node lustreNode) {
		// go through all input and output ids and load the id and type to map
		for (VarDecl varDecl : lustreNode.inputs) {
			String id = varDecl.id;
			Type type = varDecl.type;
			lustreExprToConstraintVisitor.addEntryToCompIdTypeMap(id, type);
		}
		// go through all eq var ids and load the id and type to map
		for (VarDecl varDecl : lustreNode.locals) {
			String id = varDecl.id;
			Type type = varDecl.type;
			lustreExprToConstraintVisitor.addEntryToCompIdTypeMap(id, type);
		}
	}

//	private void updateNodeIdTypeMap() {
//		for (AgreeNode agreeNode : agreeProgram.agreeNodes) {
//			// go through all input ids and load the id and type to map
//			Node curLustreNode = AgreeNodeToLustreContract.translate(agreeNode, agreeProgram);
//			// go through all input and output ids and load the id and type to map
//			for (VarDecl varDecl : curLustreNode.inputs) {
//				if (varDecl instanceof AgreeVar) {
//					addIdTypeToMap((AgreeVar) varDecl);
//				}
//			}
//			// go through all eq var ids and load the id and type to map
//			for (VarDecl varDecl : curLustreNode.locals) {
//				if (varDecl instanceof AgreeVar) {
//					addIdTypeToMap((AgreeVar) varDecl);
//				}
//			}
//		}
//		// TODO: need to make sure ids added to map has no duplicate
//		// add input ids from agree nodes
//		for (Node globalLustreNode : agreeProgram.globalLustreNodes) {
//			// go through all input ids for each node and load the id and type to map
//			for (VarDecl varDecl : globalLustreNode.inputs) {
//				if (varDecl instanceof AgreeVar) {
//					addIdTypeToMap((AgreeVar) varDecl);
//				}
//			}
//			// go through all local ids for each node and load the id and type to map
//			for (VarDecl varDecl : globalLustreNode.locals) {
//				if (varDecl instanceof AgreeVar) {
//					addIdTypeToMap((AgreeVar) varDecl);
//				}
//			}
//		}
//	}

	private void addIdTypeToMap(AgreeVar agreeVar) {
		String id = agreeVar.id;
		Type type = agreeVar.type;
//		if (agreeVar.reference != null) {
//			// exclude fault ids for now
//			// TODO: revisit this
//			if (!(agreeVar.reference instanceof FaultStatementImpl)) {
//				lustreExprToConstraintVisitor.addEntryToCompIdTypeMap(id, type);
//			}
//		}
//		else {
		lustreExprToConstraintVisitor.addEntryToCompIdTypeMap(id, type);
//		}
	}

	private void addIdTypeToMap(VarDecl varDecl) {
		Type type = varDecl.type;
		lustreExprToConstraintVisitor.addEntryToCompIdTypeMap(varDecl.id, type);
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
			// if src or dest term is null, don't add it
			if ((srcTerm != null) && (destTerm != null)) {
				// add connectivitiy(dest) = source
				termTermMapDef.addEntry(destTerm, srcTerm);
			}
		}

	}
}
