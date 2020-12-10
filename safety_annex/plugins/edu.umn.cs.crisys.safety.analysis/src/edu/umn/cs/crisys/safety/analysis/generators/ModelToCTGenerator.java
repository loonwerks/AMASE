package edu.umn.cs.crisys.safety.analysis.generators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.osate.aadl2.BooleanLiteral;
import org.osate.aadl2.NamedElement;
import org.osate.aadl2.instance.ComponentInstance;
import org.osate.aadl2.instance.ConnectionInstance;
import org.osate.aadl2.instance.ConnectionInstanceEnd;

import com.rockwellcollins.atc.agree.analysis.ast.AgreeEquation;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeNode;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeProgram;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeStatement;
import com.rockwellcollins.atc.agree.analysis.translation.AgreeNodeToLustreContract;

import edu.umn.cs.crisys.safety.analysis.SafetyException;
import edu.umn.cs.crisys.safety.analysis.ast.visitors.AddFaultsToNodeVisitor;
import edu.umn.cs.crisys.safety.analysis.ast.visitors.AgreeGuaranteeCausingExprFinder;
import edu.umn.cs.crisys.safety.analysis.ast.visitors.CTBottomIdNodeVisitor;
import edu.umn.cs.crisys.safety.analysis.ast.visitors.FaultNodeCausingExprFinder;
import edu.umn.cs.crisys.safety.analysis.ast.visitors.FaultyOutputFinder;
import edu.umn.cs.crisys.safety.analysis.ast.visitors.LustreExprToCTVisitor;
import edu.umn.cs.crisys.safety.analysis.causationTree.CT;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTBottomNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTNodeBinaryOp;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTOrNode;
import edu.umn.cs.crisys.safety.analysis.transform.Fault;
import edu.umn.cs.crisys.safety.analysis.transform.FaultASTBuilder;
import edu.umn.cs.crisys.safety.safety.DisableStatement;
import edu.umn.cs.crisys.safety.safety.FaultStatement;
import edu.umn.cs.crisys.safety.safety.FaultSubcomponent;
import edu.umn.cs.crisys.safety.safety.OutputStatement;
import edu.umn.cs.crisys.safety.safety.PropagationTypeStatement;
import edu.umn.cs.crisys.safety.safety.SpecStatement;
import edu.umn.cs.crisys.safety.safety.asymmetric;
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

public class ModelToCTGenerator {

	AgreeNode topAgreeNode;
	ComponentInstance topCompInst;
	AgreeProgram agreeProgram;
	List<CT> causationTrees = new ArrayList<CT>();
	LustreExprToCTVisitor lustreExprToCTVisitor = new LustreExprToCTVisitor();

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
			Map<CTBottomNode, AgreeNode> bottomIdNodeAgreeNodeMap = deepCopy(
					bottomIdNodeVisitor.bottomIdNodeAgreeNodeMap);
			// Expand the bottom nodes in the current tree
			// Loop around and stop the above process until no more bottom node to develop
			// Be sure to cover all situations of bottomIdNode to be able to exit the loop eventually
			while (!bottomIdNodeVisitor.bottomIdNodeAgreeNodeMap.isEmpty()) {
				bottomIdNodeAgreeNodeMap = deepCopy(bottomIdNodeVisitor.bottomIdNodeAgreeNodeMap);
				// for each such bottom node and associated agree node
				for (Map.Entry<CTBottomNode, AgreeNode> entry : bottomIdNodeAgreeNodeMap.entrySet()) {
					CTBottomNode bottomIdNode = entry.getKey();
					AgreeNode currentAgreeNode = entry.getValue();
					bottomIdNodeVisitor.bottomIdNodeAgreeNodeMap.remove(bottomIdNode);
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
								handleOutputId(bottomIdNodeVisitor, bottomIdNode, id);
							}

							// TODO: if the ID is one of the failures, stop developing further, set isLeaf true for that node

							// TODO: if the ID is one of the internal variables
							// go find the definition of the internal variable until
							// reaching one of the category of signals identified above
//							else {
//
//							}
						}
						// if not top level node
						else {
							// if the ID contained in that expression is an input produced by other components
							// TODO: to handle multiple verification layers
							// need to change from top level agreeNode to current level agreeNode
							if (AgreeUtil.inputsContainId(currentAgreeNode, id)) {
								handleOutputId(bottomIdNodeVisitor, bottomIdNode, id);
							} else {
								// if the ID contained is an output of the current component
								if (AgreeUtil.outputsContainId(currentAgreeNode, id)) {
									// find the agree node with faulty behavior added
									AgreeNode faultyNode = faultyAgreeNodeFromNominalNode(agreeProgram.topNode.subNodes,
											currentAgreeNode);
									if (!faultyNode.assertions.isEmpty()) {
										// TODO: if the ID is affected by a fault node
										// extract the causing expr from the fault node definition
										System.out.println("id: " + id);
										// if the Id appears in the currentAgreeNode's assertions
										// get the node name
										// look for the node definition in agreeProgram's global lustre nodes
										// and extract the causing expr from the fault node definition
									}
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
			// TODO: Walk through the created CT tree from bottom up
			// mark any impossible branches, e.g., the ones that contradict the top node assumptions
			// and update isFailure field for each node

			// TODO: check that at the end all the bottom nodes are marked isLeaf

			causationTree.setRootNode(rootNode);
			causationTrees.add(causationTree);
		}
		return causationTrees;
	}

	private void handleOutputId(CTBottomIdNodeVisitor bottomIdNodeVisitor, CTBottomNode bottomIdNode, String id) {
		HashSet<CTNode> childNodes = new HashSet<CTNode>();

		// TODO: check AGREE annex for both comp and comp impl

		// go through all connections
		// and find all the components producing the output
		// for each component

		// TODO: if more than one producing node for the output ID
		// find the assertion in the top level component implementation
		// for the relationship between the multiple outputs
		for (AgreeNode nextAgreeNode : getProducingNodes(id, topCompInst, agreeProgram)) {
			if (isTopNode(nextAgreeNode)) {
				bottomIdNode.isLeaf = true;
				continue;
			} else {
				// for each component, get the agree node and lustre node for that component
				// Translate Agree Node to Lustre Node with pre-statement flatten, helper nodes inlined,
				// and variable declarations sorted so they are declared before use
				Node curLustreNode = AgreeNodeToLustreContract.translate(nextAgreeNode, agreeProgram);
				// go through all equation expr in the lustre node
				// and identify the causing expr for the target expr in bottomIdNode.expr

				for (Equation equation : curLustreNode.equations) {
					Expr srcExpr = equation.expr;
					Expr targetExpr = bottomIdNode.expr;
					// Set target expr
					AgreeGuaranteeCausingExprFinder agreeGuaranteeCausingExprFinder = new AgreeGuaranteeCausingExprFinder(
							targetExpr);
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
				AgreeNode faultyNode = faultyAgreeNodeFromNominalNode(agreeProgram.topNode.subNodes, nextAgreeNode);
				// when there are faulty behavior definitions, faultyNode assertions exist
				if (!faultyNode.assertions.isEmpty()) {
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
										// TODO: identify the fault associated with the faulty output
										List<Fault> faults = gatherFaults(agreeProgram.globalLustreNodes,
												nextAgreeNode, isTopNode(nextAgreeNode));
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
																// get the expression from that equation
																// identify the value assignment from the thenExpr and elseExpr
																// to find the one that matches the bottomIdNode expr
																// then get the conjunction of the condExprs
																FaultNodeCausingExprFinder faultNodeCausingExprFinder = new FaultNodeCausingExprFinder(
																		bottomIdNode.expr, id, fault.id);
																Expr faultNodeCausingExpr = faultNodeCausingExprFinder
																		.visit(faultNodeEquation.expr);
																CTNode curNode = lustreExprToCTVisitor
																		.visit(faultNodeCausingExpr);
																// construct CT nodes from it
																bottomIdNodeVisitor.visit(curNode);
																childNodes.add(curNode);
																// TODO: add fault description and probability info to be built into the CT node

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

	// go through all connections
	// and find all the components producing the output and the associated norminal agree node with that
	private List<AgreeNode> getProducingNodes(String idStr, ComponentInstance compInst, AgreeProgram agreeProgram) {
		List<AgreeNode> sourceNodes = new ArrayList<>();
		// go through all connection instances
		EList<ConnectionInstance> connectionInstances = compInst.getAllEnclosingConnectionInstances();
		for (ConnectionInstance connectionInstance : connectionInstances) {
			// find the connection that produces the output
			// TODO: connectionReference is currently null; in the future, check that also
			if (connectionInstance.getSource().getName().equals(idStr)) {
				// find the agree node for that component instance
				// TODO: if supporting extend AGREE, go to the predecessor
				// using getExtended() method
				ConnectionInstanceEnd sourceEndInstance = connectionInstance.getSource();
				ComponentInstance sourceComponentInstance = sourceEndInstance.getComponentInstance();
				AgreeNode sourceNode = agreeNodeFromNamedEl(agreeProgram.agreeNodes, sourceComponentInstance);
				sourceNodes.add(sourceNode);
			}
		}
		return sourceNodes;
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

	private AgreeNode faultyAgreeNodeFromNominalNode(List<AgreeNode> nodes, AgreeNode sourceNode) {
		if (sourceNode == null) {
			return null;
		}
		for (AgreeNode node : nodes) {
			if (sourceNode.id.equals(node.id)) {
				return node;
			}
		}
		return null;
	}

	private Map<CTBottomNode, AgreeNode> deepCopy(Map<CTBottomNode, AgreeNode> bottomIdNodeAgreeNodeMap) {
		Map<CTBottomNode, AgreeNode> copy = new HashMap<CTBottomNode, AgreeNode>();
		for (Map.Entry<CTBottomNode, AgreeNode> entry : bottomIdNodeAgreeNodeMap.entrySet()) {
			copy.put(entry.getKey(), entry.getValue());
		}
		return copy;
	}

	private Boolean isTopNode(AgreeNode agreeNode) {
		if (agreeNode.equals(topAgreeNode)) {
			return true;
		} else {
			return false;
		}
	}

	private List<Fault> gatherFaults(List<Node> globalLustreNodes, AgreeNode node, boolean isTop) {
		List<SpecStatement> specs = SafetyUtil.collapseAnnexes(SafetyUtil.getSafetyAnnexes(node, isTop));

		List<Fault> faults = new ArrayList<>();

		// Before looping through spec statements, separate out the asymmetric multiple
		// faults on a single output with the sym/asym single faults on a single output.
		// 1. Collect all fault statements and put into list.
		// Do not collect any that are disabled.
		// 2. Separate out multiple asym faults on one output and single faults on one output.
		// 3. Perform necessary processing on each of these lists.
		List<FaultStatement> allFaultStmts = new ArrayList<FaultStatement>();
		for (SpecStatement s : specs) {
			if (s instanceof FaultStatement) {
				if (!isDisabled((FaultStatement) s)) {

					allFaultStmts.add((FaultStatement) s);
				}
			}
		}
		List<FaultStatement> remainderFS = new ArrayList<FaultStatement>();
		Map<String, List<FaultStatement>> multipleAsymFS = new HashMap<String, List<FaultStatement>>();
		// separate symmetric and asymetric faults
		separateFaultStmts(allFaultStmts, remainderFS, multipleAsymFS);

		// Currently only process symmetric faults
		for (SpecStatement s : remainderFS) {
			if (s instanceof FaultStatement) {
				FaultStatement fs = (FaultStatement) s;
				FaultASTBuilder builder = new FaultASTBuilder(globalLustreNodes, node);
				// Process fault determines if we have a
				// symmetric or asymmetric fault and builds it accordingly.
				Fault safetyFault = builder.processFault(fs);
				faults.add(safetyFault);
			}
		}
		return faults;
	}

	/**
	 * Given a fault statement, returns the string name of the
	 * output this fault stmt is connected to.
	 *
	 * @param fs FaultStatement in question
	 * @return String name of the output
	 */
	private String getOutputNameFromFaultStatement(FaultStatement fs) {
		String output = "";
		for (FaultSubcomponent fc : fs.getFaultDefinitions()) {
			if (fc instanceof OutputStatement) {
				EList<NamedElement> outputType = ((OutputStatement) fc).getNom_conn();
				// TODO: Assume the output is first in list. (????)
				if (outputType.size() > 0) {
					NamedElement id = outputType.get(0);
//					output = id.getBase().getName();
					output = id.toString();
					return output;
				}
				break;
			}
		}

		return output;
	}

	/**
	 * This method uses a list of fault statements and divides them into multiple asym faults on
	 * a single output and everything else.
	 *
	 * @param allFS List<FaultStatement> All fault statements in this agree node
	 * @param remainderFS List<FaultStatement> List to add all single asym faults on single output AND
	 * 							sym faults.
	 * @param multipleAsymFS List<FaultStatement> List to add all multiple asym faults on single output.
	 */
	private void separateFaultStmts(List<FaultStatement> allFS, List<FaultStatement> remainderFS,
			Map<String, List<FaultStatement>> asymMap) {

		// 1. Add sym faults to remainder list and process asym faults such that they
		// are inserted into a map from outputName -> List(fault statements).
		// 2. If the list of fault statements associated with an output is of size 1,
		// append these to remainderFS list.
		// Else append to multipleAsymFS list.
		for (FaultStatement fs : allFS) {
			int count = fs.getFaultDefinitions().size();
			for (FaultSubcomponent fc : fs.getFaultDefinitions()) {
				count--;
				if (fc instanceof PropagationTypeStatement) {
					if (((PropagationTypeStatement) fc).getPty() instanceof asymmetric) {
						// Asym fault needs to be processed further and added to map.
						String outputName = getOutputNameFromFaultStatement(fs);
						if (outputName.isEmpty()) {
							new SafetyException(
									"Error processing asymmetric fault: the output name is undefined for fault statement:"
											+ fs.getName());
						} else {
							List<FaultStatement> tempAsymFaults = new ArrayList<FaultStatement>();
							tempAsymFaults.add(fs);
							if (asymMap.containsKey(outputName)) {
								asymMap.get(outputName).addAll(tempAsymFaults);
							} else {
								asymMap.put(outputName, tempAsymFaults);
							}
							break;
						}
					} else {
						// symmetric faults added to remainderFS list
						remainderFS.add(fs);
						break;
					}
				}
				// If we haven't broken out of the loop and we have traversed
				// all definitions, then we have no prop type stmt. It is sym.
				if (count == 0) {
					remainderFS.add(fs);
				}
			}
		}
		// Now all sym faults in remainder list and all asym faults in map.
		// Process map and add single asym faults to remainder list.
		// Add multiples to multiple list.
		for (String key : asymMap.keySet()) {
			if (asymMap.get(key).size() == 1) {
				remainderFS.addAll(asymMap.get(key));
			}
		}
	}

	/**
	 * Checks fault stmt for DisableStatement. If found, returns value
	 * of disable statement. Else returns false.
	 * @param fs FaultStatement
	 * @return bool: isDisabled
	 */
	private Boolean isDisabled(FaultStatement fs) {
		Boolean disableFound = false;
		List<FaultSubcomponent> subcomps = fs.getFaultDefinitions();
		for (FaultSubcomponent fsc : subcomps) {
			if (fsc instanceof DisableStatement) {
				disableFound = true;
				DisableStatement ds = (DisableStatement) fsc;
				BooleanLiteral bl = ds.getCond();
				return bl.getValue();
			}
		}
		return disableFound;
	}

}
