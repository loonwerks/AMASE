package edu.umn.cs.crisys.safety.analysis.generators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.osate.aadl2.NamedElement;
import org.osate.aadl2.instance.ComponentInstance;
import org.osate.aadl2.instance.ConnectionInstance;
import org.osate.aadl2.instance.ConnectionInstanceEnd;
import org.osate.aadl2.instance.impl.ComponentInstanceImpl;
import org.osate.aadl2.instance.impl.SystemInstanceImpl;

import com.rockwellcollins.atc.agree.analysis.ast.AgreeEquation;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeNode;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeProgram;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeStatement;
import com.rockwellcollins.atc.agree.analysis.translation.AgreeNodeToLustreContract;

import edu.umn.cs.crisys.safety.analysis.ast.visitors.AddFaultsToNodeVisitor;
import edu.umn.cs.crisys.safety.analysis.ast.visitors.AgreeGuaranteeCausingExprFinder;
import edu.umn.cs.crisys.safety.analysis.ast.visitors.CTBottomIdNodeVisitor;
import edu.umn.cs.crisys.safety.analysis.ast.visitors.CTWalker;
import edu.umn.cs.crisys.safety.analysis.ast.visitors.FaultNodeCausingExprFinder;
import edu.umn.cs.crisys.safety.analysis.ast.visitors.FaultyOutputFinder;
import edu.umn.cs.crisys.safety.analysis.ast.visitors.LustreExprIdReplacer;
import edu.umn.cs.crisys.safety.analysis.ast.visitors.LustreExprToCTVisitor;
import edu.umn.cs.crisys.safety.analysis.causationTree.CT;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTBottomNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTNodeBinaryOp;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTOrNode;
import edu.umn.cs.crisys.safety.analysis.transform.Fault;
import edu.umn.cs.crisys.safety.util.AgreeUtil;
import edu.umn.cs.crisys.safety.util.SafetyUtil;
import jkind.lustre.BinaryExpr;
import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.Node;
import jkind.lustre.NodeCallExpr;
import jkind.lustre.UnaryExpr;
import jkind.lustre.UnaryOp;
import jkind.lustre.VarDecl;

public class ModelToCTGenerator {

	AgreeNode topAgreeNode;
	ComponentInstance topCompInst;
	AgreeProgram agreeProgram;
	List<CT> causationTrees = new ArrayList<CT>();
	LustreExprToCTVisitor lustreExprToCTVisitor = new LustreExprToCTVisitor();
	// Map<AgreeNode, HashSet<CTNode>> agreeNodeCTNodesMap = new HashMap<AgreeNode, HashSet<CTNode>>();

	public ModelToCTGenerator(AgreeNode topAgreeNode, ComponentInstance topCompInst, AgreeProgram agreeProgram) {
		this.topAgreeNode = topAgreeNode;
		this.topCompInst = topCompInst;
		this.agreeProgram = agreeProgram;
	}

	public List<CT> generateCausationTree() {
		// generate FT for each top level guarantee
		for (AgreeStatement topLevelGuarantee : agreeProgram.topNode.guarantees) {
			CT causationTree = new CT();
			// Step 1: negate the top level guarantee expression
			UnaryExpr topLevelEvent = new UnaryExpr(UnaryOp.NOT, topLevelGuarantee.expr);
			// turn it into a causation tree

			// CTRootNode rootNode = new CTRootNode(topLevelGuarantee.string);
			// TODO: mark leaf nodes in the tree as the expr to CT translation goes
			CTNode rootNode = lustreExprToCTVisitor.visit(topLevelEvent);
			// Collect all bottom nodes with Id from the causation tree
			// Start with top level agree node
			CTBottomIdNodeVisitor bottomIdNodeVisitor = new CTBottomIdNodeVisitor(topAgreeNode);
			bottomIdNodeVisitor.visit(rootNode);
			Map<CTBottomNode, AgreeNode> bottomIdNodeAgreeNodeMap = bottomIdNodeVisitor.deepCopyOfBottomIdNodeMap();
			// Expand the bottom nodes in the current tree
			// Loop around and stop the above process until no more bottom node to develop
			// Be sure to cover all situations of bottomIdNode to be able to exit the loop eventually
			while (!bottomIdNodeVisitor.isBottomIdMapEmpty()) {
				bottomIdNodeAgreeNodeMap = bottomIdNodeVisitor.deepCopyOfBottomIdNodeMap();
				// for each such bottom node and associated agree node
				for (Map.Entry<CTBottomNode, AgreeNode> entry : bottomIdNodeAgreeNodeMap.entrySet()) {
					CTBottomNode bottomIdNode = entry.getKey();
					AgreeNode currentAgreeNode = entry.getValue();
					bottomIdNodeVisitor.removeBottomIdNodeFromMap(bottomIdNode);
					// if isLeaf, no longer need to process further
					if (bottomIdNode.isLeaf) {
						continue;
					}
					// for each bottom node with Id, get the Ids in that node
					for (String id : bottomIdNode.idSet) {
						// for each Id
						// if top level node
						if (isTopNode(currentAgreeNode)) {
							// if the ID is one of the top level inputs, stop developing further, set isLeaf true for that node
							if (AgreeUtil.inputsContainId(currentAgreeNode, id)) {
								bottomIdNode.isLeaf = true;
								continue;
							}
							// TODO: In AMASE, fault behaviors are only checked for components, not top level sys
							// this needs to be communicated with users of Safety Annex

							// if the ID contained in that expression is an output produced by other components
							// TODO: to handle multiple verification layers
							// need to change from top level agreeNode to current level agreeNode
							else if (AgreeUtil.outputsContainId(currentAgreeNode, id)) {
								// TODO: if the ID is one of the eq variables
								// go find the assertions for the eq variable
								if (AgreeUtil.isEqVar(currentAgreeNode, id)) {
									handleEqId(currentAgreeNode, bottomIdNodeVisitor, bottomIdNode, id);
								}
								// otherwise, handle output ID
								else {
									handleOutputId(currentAgreeNode, bottomIdNodeVisitor, bottomIdNode, id);
								}
							}
//							// else it could be one of the subcomponents output assigned to eq variables
//							else {
//								handleOutputId(currentAgreeNode, bottomIdNodeVisitor, bottomIdNode, id);
//							}

							// TODO: if the ID is one of the failures, stop developing further, set isLeaf true for that node

						}
						// if not top level node
						else {
							// if the ID contained in that expression is an input produced by other components
							// TODO: to handle multiple verification layers
							// need to change from top level agreeNode to current level agreeNode
							if (AgreeUtil.inputsContainId(currentAgreeNode, id)) {
								handleOutputId(currentAgreeNode, bottomIdNodeVisitor, bottomIdNode, id);
							} else {
								// if the ID contained is an output of the current component
								if (AgreeUtil.outputsContainId(currentAgreeNode, id)) {
									Expr bottomIdNodeExpr = bottomIdNode.expr;
									HashSet<CTNode> childNodes = new HashSet<CTNode>();
									createContributingNodesToOutput(bottomIdNodeVisitor, id, childNodes,
											bottomIdNodeExpr, currentAgreeNode);
									addChildNodes(bottomIdNode, childNodes);

//									// find the agree node with faulty behavior added
//									AgreeNode faultyNode = faultyAgreeNodeFromNominalNode(agreeProgram.topNode.subNodes,
//											currentAgreeNode);
//									if (!faultyNode.assertions.isEmpty()) {
//										// TODO: if the ID is affected by a fault node
//										// extract the causing expr from the fault node definition
//										System.out.println("id: " + id);
//										// if the Id appears in the currentAgreeNode's assertions
//										// get the node name
//										// look for the node definition in agreeProgram's global lustre nodes
//										// and extract the causing expr from the fault node definition
//									}
								}

							}

							// TODO: if the ID is one of the failures, stop developing further, set isLeaf true for that node

							// TODO: if the ID is one of the internal variables
							// go find the definition of the internal variable until
							// reaching one of the category of signals identified above
//							else {
//
//							}
						}
					}
				}
			}
			// Walk through the created CT tree from bottom up
			// update isFailure field for each node
			// update isFeasiable field for each node
			CTWalker ctWalker = new CTWalker(agreeProgram.topNode.assumptions);
			ctWalker.visit(rootNode);
			// TODO: mark any impossible branches, e.g., the ones that contradict the top node assumptions
			// prune nodes and parent nodes if they contradict any of the top level assumptions, e.g., prune not(critical_takeoff_phase) from child nodes
			// TODO: check that at the end all the bottom nodes are marked isLeaf

			causationTree.setRootNode(rootNode);
			causationTrees.add(causationTree);
		}
		return causationTrees;
	}

	private void handleEqId(AgreeNode curAgreeNode, CTBottomIdNodeVisitor bottomIdNodeVisitor,
			CTBottomNode bottomIdNode, String id) {
		// find the id in the assertions
		for (AgreeStatement assertion : curAgreeNode.assertions) {
			if (assertion.expr instanceof BinaryExpr) {
				BinaryExpr curExpr = (BinaryExpr) assertion.expr;
				Expr leftExpr = curExpr.left;
				if (leftExpr instanceof IdExpr) {
					if (((IdExpr) leftExpr).id.equals(id)) {
						// get the right side of the expression
						Expr rightExpr = curExpr.right;
						CTNode curNode = lustreExprToCTVisitor.visit(rightExpr);
						bottomIdNodeVisitor.setCurAgreeNode(curAgreeNode);
						bottomIdNodeVisitor.visit(curNode);
						HashSet<CTNode> childNodes = new HashSet<CTNode>();
						childNodes.add(curNode);
						addChildNodes(bottomIdNode, childNodes);
					}
				}
			}
		}
	}

	private void handleOutputId(AgreeNode curAgreeNode, CTBottomIdNodeVisitor bottomIdNodeVisitor,
			CTBottomNode bottomIdNode, String id) {
		HashSet<CTNode> childNodes = new HashSet<CTNode>();
		Expr bottomIdNodeExpr = bottomIdNode.expr;
		// reaching one of the category of signals identified above

		// TODO: check AGREE annex for both comp and comp impl

		// go through all connections
		// and find all the components producing the output
		// for each component

		// TODO: if more than one producing node for the output ID
		// find the assertion in the top level component implementation
		// for the relationship between the multiple outputs

		// TODO: save the CT node/tree generated for a given agree node
		// so if the same agree node being visited again, the saved CT node can be used
		// TODO: get producint node and Id here
		for (NodeIdPair compIdPair : getProducingNodes(curAgreeNode, id, topCompInst, agreeProgram)) {
//			HashSet<CTNode> storedChildNodes = localAgreeNodeCTNodesMap.get(nextAgreeNode);
//			if (storedChildNodes != null) {
//				addChildNodes(bottomIdNode, storedChildNodes);
//				return;
//			}
			AgreeNode nextAgreeNode = compIdPair.node;
			if (!compIdPair.id.equals(id)) {
				// replace the id used in bottomIdNode
				LustreExprIdReplacer idReplacer = new LustreExprIdReplacer(id, compIdPair.id);
				bottomIdNodeExpr = idReplacer.visit(bottomIdNode.expr);
				id = compIdPair.id;
			}
			if (isTopNode(nextAgreeNode)) {
				bottomIdNode.isLeaf = true;
				continue;
			} else {
				createContributingNodesToOutput(bottomIdNodeVisitor, id, childNodes, bottomIdNodeExpr, nextAgreeNode);
			}
			// localAgreeNodeCTNodesMap.put(nextAgreeNode, childNodes);
		}

		addChildNodes(bottomIdNode, childNodes);
	}

	private void createContributingNodesToOutput(CTBottomIdNodeVisitor bottomIdNodeVisitor, String id,
			HashSet<CTNode> childNodes, Expr bottomIdNodeExpr, AgreeNode nextAgreeNode) {
		// for each component, get the agree node and lustre node for that component
		// Translate Agree Node to Lustre Node with pre-statement flatten, helper nodes inlined,
		// and variable declarations sorted so they are declared before use
		Node curLustreNode = AgreeNodeToLustreContract.translate(nextAgreeNode, agreeProgram);
		// go through all equation expr in the lustre node
		// and identify the causing expr for the target expr in bottomIdNodeExpr

		for (Equation equation : curLustreNode.equations) {
			Expr srcExpr = equation.expr;
			Expr targetExpr = bottomIdNodeExpr;
			// Set target expr
			AgreeGuaranteeCausingExprFinder agreeGuaranteeCausingExprFinder = new AgreeGuaranteeCausingExprFinder(
					targetExpr, nextAgreeNode);
			// look for the => operator
			// Once found the expr with the => operator
			// see if the target expr is
			// contained on the right side of that => operator
			// if yes
			// return the left side expression of that => operator
			List<Expr> agreeGuaranteeCausingExprs = agreeGuaranteeCausingExprFinder.visit(srcExpr);
			// develop the left side expressions to CT node
			// by applying lustreExprToCTVisitor to the left side expression
			for (Expr expr : agreeGuaranteeCausingExprs) {
				CTNode curNode = lustreExprToCTVisitor.visit(expr);
				bottomIdNodeVisitor.setCurAgreeNode(nextAgreeNode);
				bottomIdNodeVisitor.visit(curNode);
				childNodes.add(curNode);
			}
		}

		// TODO: go through all failure behavioral definitions for that component
		// TODO: develop CT for the failure behavior definition

		// find the agree node with faulty behavior added
		AgreeNode faultyNode = SafetyUtil.faultyAgreeNodeFromNominalNode(agreeProgram.topNode.subNodes, nextAgreeNode);
		if (faultyNode == null) {
			System.out.println("null faulty node " + nextAgreeNode.id);
		}
		// when there are faulty behavior definitions, faultyNode assertions exist
		else if (!faultyNode.assertions.isEmpty()) {
			// for each assertion expr
			for (AgreeStatement assertion : faultyNode.assertions) {
				if (assertion.expr instanceof BinaryExpr) {
					Expr leftExpr = ((BinaryExpr) assertion.expr).left;
					Expr rightExpr = ((BinaryExpr) assertion.expr).right;
					if (leftExpr instanceof IdExpr) {
						// if the left IdExpr matches the output id
						if (((IdExpr) leftExpr).id.equals(id)) {
							// find the faulty outputs for the corresponding output id
							FaultyOutputFinder faultyOutputFinder = new FaultyOutputFinder();
							List<String> faultyOutputs = faultyOutputFinder.visit(rightExpr);
							// for each faulty output
							for (String faultyOutput : faultyOutputs) {
								// identify the fault associated with the faulty output
								Fault fault = AddFaultsToNodeVisitor.triggerToFaultMap.get(faultyOutput);
								// TODO: need to differentiate between enabled and disabled faults
								// identify the node call from localEquations for the faulty output
								for (AgreeEquation localEquation : faultyNode.localEquations) {
									if (localEquation.lhs.get(0).id.equals(faultyOutput)) {
										Expr localEquationExpr = localEquation.expr;
										if (localEquationExpr instanceof NodeCallExpr) {
											String nodeName = ((NodeCallExpr) localEquationExpr).node;
											for (Node node : agreeProgram.globalLustreNodes) {
												// find the node call in agreeProgram.globalLustreNodes
												if (node.id.equals(nodeName)) {
													// go through the equations from the node call
													for (Equation faultNodeEquation : node.equations) {
														// TODO: handle the situation when there are multiple equations
														// in a fault node
														// we need to find the equation that matches specific output
//																if (faultNodeEquation.lhs.get(0).id
//																		.equals(node.outputs)) {

														// build a translation map
														// between list of inputs passed to localEquationExpr (original system signals)
														// and fault node arguments
														Map<String, String> faultNodeInputToOriginalSignalMap = new HashMap<String, String>();
														int i = 0;
														for (VarDecl nodeInput : node.inputs) {
															faultNodeInputToOriginalSignalMap.put(nodeInput.id,
																	((IdExpr) ((NodeCallExpr) localEquationExpr).args
																			.get(i)).id);
															i++;
														}
														// get the expression from that equation
														// identify the value assignment from the thenExpr and elseExpr
														// to find the one that matches the bottomIdNode expr
														// then get the conjunction of the condExprs
														// pass the fault id and the translation map so to use the original signal name
														// in the expr returned
														FaultNodeCausingExprFinder faultNodeCausingExprFinder = new FaultNodeCausingExprFinder(
																bottomIdNodeExpr, id, fault.id,
																faultNodeInputToOriginalSignalMap);
														Expr faultNodeCausingExpr = faultNodeCausingExprFinder
																.visit(faultNodeEquation.expr);
														// if no faultNodeCausingExpr found
														// meaning no fault expr causing the bottomIdNode expr
														// not creating CT node for it

														if (faultNodeCausingExpr == null) {
															System.out.println(
																	"no fault node causing expr found");
														} else {
															lustreExprToCTVisitor.setCurrentFault(fault);
															CTNode curNode = lustreExprToCTVisitor
																	.visit(faultNodeCausingExpr);
															// construct CT nodes from it
															bottomIdNodeVisitor.visit(curNode);
															childNodes.add(curNode);
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	private void addChildNodes(CTBottomNode bottomIdNode, HashSet<CTNode> childNodes) {
		if (childNodes.isEmpty()) {
			bottomIdNode.isLeaf = true;
		} else {
			// if there are more than one child nodes
			// use OR operator to connect them and add the OR node as a child node to the original bottomIdNode
			// otherwise, add the child node to the original bottomIdNode
			if (childNodes.size() > 1) {
				CTNodeBinaryOp orOp = CTNodeBinaryOp.fromName("OR");
				CTOrNode orNode = new CTOrNode(orOp);
				List<CTNode> childNodesList = new ArrayList<CTNode>(childNodes);
				orNode.addChildNodes(childNodesList);
				bottomIdNode.addChildNode(orNode);
			} else {
				Iterator<CTNode> setIterator = childNodes.iterator();
				while (setIterator.hasNext()) {
					bottomIdNode.addChildNode(setIterator.next());
				}
			}
		}
	}

	// go through all connections
	// and find all the components producing the output and the associated norminal agree node with that
	private List<NodeIdPair> getProducingNodes(AgreeNode destAgreeNode, String idStr, ComponentInstance compInst,
			AgreeProgram agreeProgram) {
		List<NodeIdPair> compIdPairs = new ArrayList<>();
		// go through all connection instances
		EList<ConnectionInstance> connectionInstances = compInst.getAllEnclosingConnectionInstances();
		for (ConnectionInstance connectionInstance : connectionInstances) {
			// find the connection that produces the output
			// TODO: connectionReference is currently null; in the future, check that also
			if (connectionInstance.getDestination().getName().equals(idStr)) {
//
//			}
//			if (connectionInstance.getSource().getName().equals(idStr)) {
				// get destination name
				String destName = "";
				if (connectionInstance.getDestination().eContainer() instanceof ComponentInstanceImpl) {
					destName = ((ComponentInstanceImpl) connectionInstance.getDestination().eContainer()).getName();
				} else if (connectionInstance.getDestination().eContainer() instanceof SystemInstanceImpl) {
					destName = ((SystemInstanceImpl) connectionInstance.getDestination().eContainer()).getName();
				}
				// if the destination matches the destAgreeNode name
				// add it to sourceNodes
				if (destName.equals(destAgreeNode.id)) {
					// TODO: if supporting extend AGREE, go to the predecessor
					// using getExtended() method
					ConnectionInstanceEnd sourceEndInstance = connectionInstance.getSource();
					ComponentInstance sourceComponentInstance = sourceEndInstance.getComponentInstance();
					AgreeNode sourceNode = agreeNodeFromNamedEl(agreeProgram.agreeNodes, sourceComponentInstance);
					NodeIdPair compIdPair = new NodeIdPair(sourceNode, sourceEndInstance.getName());
					compIdPairs.add(compIdPair);
				}
			}
		}
		return compIdPairs;
	}

	private AgreeNode agreeNodeFromNamedEl(List<AgreeNode> nodes, NamedElement comp) {
		if (comp == null) {
			return null;
		}
		for (AgreeNode node : nodes) {
			if (comp.getName().equals(node.id)) {
				return node;
			}
		}
		return null;
	}

	private Boolean isTopNode(AgreeNode agreeNode) {
		if (agreeNode.id.equals(topAgreeNode.id)) {
			return true;
		} else {
			return false;
		}
	}

	private class NodeIdPair {
		private AgreeNode node;
		private String id;

		public NodeIdPair(AgreeNode node, String id) {
			this.node = node;
			this.id = id;
		}
	}

}
