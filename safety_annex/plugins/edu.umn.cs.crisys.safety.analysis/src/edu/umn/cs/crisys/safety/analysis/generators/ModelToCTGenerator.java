package edu.umn.cs.crisys.safety.analysis.generators;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.osate.aadl2.NamedElement;
import org.osate.aadl2.instance.ComponentInstance;
import org.osate.aadl2.instance.ConnectionInstance;
import org.osate.aadl2.instance.ConnectionInstanceEnd;

import com.rockwellcollins.atc.agree.analysis.ast.AgreeNode;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeProgram;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeStatement;
import com.rockwellcollins.atc.agree.analysis.translation.AgreeNodeToLustreContract;

import edu.umn.cs.crisys.safety.analysis.SafetyException;
import edu.umn.cs.crisys.safety.analysis.ast.visitors.CTBottomIdNodeVisitor;
import edu.umn.cs.crisys.safety.analysis.ast.visitors.LustreExprToCTVisitor;
import edu.umn.cs.crisys.safety.analysis.causationTree.CT;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTBottomNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTNodeBinaryOp;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTOrNode;
import edu.umn.cs.crisys.safety.util.AgreeUtil;
import jkind.lustre.BinaryExpr;
import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.Node;
import jkind.lustre.UnaryExpr;
import jkind.lustre.UnaryOp;

public class ModelToCTGenerator {
	List<CT> causationTrees = new ArrayList<CT>();
	LustreExprToCTVisitor lustreExprToCTVisitor = new LustreExprToCTVisitor();

	public List<CT> generateCausationTree(Node topLustreNode, AgreeNode topAgreeNode, ComponentInstance compInst,
			AgreeProgram agreeProgram) {
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
			CTBottomIdNodeVisitor bottomIdNodeVisitor = new CTBottomIdNodeVisitor();
			bottomIdNodeVisitor.visit(rootNode);
			List<CTBottomNode> bottomIdNodes = new ArrayList<CTBottomNode>(bottomIdNodeVisitor.bottomNodesWithId);
			// Expand the bottom nodes in the current tree
			// Loop around and stop the above process until no more bottom node to develop
			while (!bottomIdNodeVisitor.bottomNodesWithId.isEmpty()) {
				bottomIdNodes = new ArrayList<CTBottomNode>(bottomIdNodeVisitor.bottomNodesWithId);
				// for each such bottom node
				for (CTBottomNode bottomIdNode : bottomIdNodes) {
					bottomIdNode.visited = true;
					bottomIdNodeVisitor.bottomNodesWithId.remove(bottomIdNode);
					// for each bottom node with Id, get the Ids in that node
					for (String id : bottomIdNode.idSet) {
						// for each Id
						// if the ID is one of the inputs, stop developing further, set isLeaf true for that node
						if (AgreeUtil.inputsContainId(topAgreeNode, id)) {
							bottomIdNode.isLeaf = true;
							continue;
						}
						// TODO: get all top level system failures
						// if the ID is one of the failures, stop developing further, set isLeaf true for that node

						// if the ID contained in that expression is an output produced by other components
						// TODO: to handle multiple verification layers
						// need to change from top level agreeNode to current level agreeNode
						else if (AgreeUtil.outputsContainId(topAgreeNode, id)) {
							HashSet<CTNode> childNodes = new HashSet<CTNode>();

							// TODO: check AGREE annex for both comp and comp impl

							// go through all connections
							// and find all the components producing the output
							// for each component

							// TODO: if more than one producing node for the output ID
							// find the assertion in the top level component implementation
							// for the relationship between the multiple outputs
							for (AgreeNode curAgreeNode : getProducingNodes(id, compInst, agreeProgram)) {
								// for each component, get the agree node and lustre node for that component
								Node curLustreNode = AgreeNodeToLustreContract.translate(curAgreeNode, agreeProgram);

								// go through all equation expr in the lustre node
								for (Equation equation : curLustreNode.equations) {
									Expr expr = equation.expr;
									if (expr instanceof BinaryExpr) {
										String opName = ((BinaryExpr) expr).op.name();
										// if the expr is of form =>
										if (opName.equals("IMPLIES")) {
											// if the right side expression of => matches the bottom node expression
											Expr rightExpr = ((BinaryExpr) expr).right;
											if (rightExpr.toString().equals(bottomIdNode.expr.toString())) {
												// develop the left side expression to CT node
												// by applying lustreExprToCTVisitor to the left side expression
												Expr leftExpr = ((BinaryExpr) expr).left;
												CTNode curNode = lustreExprToCTVisitor.visit(leftExpr);
												bottomIdNodeVisitor.visit(curNode);
												childNodes.add(curNode);
											}
											// if the right side expression of => does not match the bottom node expression
											// print it, and skip it
											else {
												System.out.println("Right side expression: " + rightExpr.toString()
														+ " does not match bottomIdNode expr: "
														+ bottomIdNode.expr.toString());
											}
										}
										// if the exp is not of the form =>, flag an exception
										else {
											throw new SafetyException("Expr not supported " + expr.toString());
										}
									} else {
										// if the exp is not of the form =>, flag an exception
										throw new SafetyException("Expr not supported " + expr.toString());
									}
								}

								// TODO: go through all failure behavioral definitions for that component
								// TODO: develop CT for the failure behavior definition

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
									while(setIterator.hasNext()){
										bottomIdNode.addChildNode(setIterator.next());
									}
								}
							}
						}
						// TODO: if the ID is one of the internal variables
						// go find the definition of the internal variable until
						// reaching one of the category of signals identified above
						else {

						}

					}
				}
			}
			// TODO: Walk through the created CT tree from bottom up and update isFailure field for each node
			causationTree.setRootNode(rootNode);
			causationTrees.add(causationTree);
		}
		return causationTrees;
	}

	// go through all connections
	// and find all the components producing the output
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

//	private Boolean exprMatchesConstraintsOfLeafNode(Expr expr, CTNode node) {
//		Boolean result = false;
//		if (expr instanceof BinaryExpr) {
//			// if the expr is an implication expr
//			if (((BinaryExpr) expr).op.name().equals("IMPLIES")) {
//				Expr rightExpr = ((BinaryExpr) expr).right;
//				// The right expr is ok if it's the target IdExpr (as appeared in the current leaf node)
//				// or negation of the IdExpr
//				// or IdExpr with relation to a constant or inputs of the current node
//				isRightExprOk = rightExprFinalForm(agreeNode, rightExpr, leafNodeId);
//			}
//		}
//		// TODO: think if it's not binary expr would it be acceptable
//		result = isRightExprOk && isLeftExprOk;
//		return result;
//	}
//
//	private Boolean exprReadyForCTNode(AgreeNode agreeNode, Expr expr, String leafNodeId) {
//		Boolean result = false;
//		// check if the expr is in a form ready for CT node
//		Boolean isRightExprOk = false;
//		Boolean isLeftExprOk = false;
//		CheckLeftExprFinalFormVisitor leftExprChecker = new CheckLeftExprFinalFormVisitor(agreeNode, leafNodeId);
//		if (expr instanceof BinaryExpr) {
//			// if the expr is an implication expr
//			if (((BinaryExpr) expr).op.name().equals("IMPLIES")) {
//				Expr leftExpr = ((BinaryExpr) expr).left;
//				Expr rightExpr = ((BinaryExpr) expr).right;
//				// The left expr is ok if it's the disjunction or conjunction of
//				// input signals and/or failures
//				isLeftExprOk = leftExprChecker.visit(leftExpr);
//
//				// The right expr is ok if it's the target IdExpr (as appeared in the current leaf node)
//				// or negation of the IdExpr
//				// or IdExpr with relation to a constant or inputs of the current node
//				isRightExprOk = rightExprFinalForm(agreeNode, rightExpr, leafNodeId);
//			}
//		}
//		// TODO: think if it's not binary expr would it be acceptable
//		result = isRightExprOk && isLeftExprOk;
//		return result;
//	}
//
//	private Boolean rightExprMatchingConstraint(Expr expr, CTNode node) {
//		Boolean result = false;
//		if (expr instanceof IdExpr) {
//			result = idExprMatchingLeafNodeId((IdExpr) expr, leafNodeId);
//		} else if (expr instanceof UnaryExpr) {
//			if (((UnaryExpr) expr).expr instanceof IdExpr) {
//				result = idExprMatchingLeafNodeId((IdExpr) expr, leafNodeId);
//			}
//		} else if (expr instanceof BinaryExpr) {
//			String opName = ((BinaryExpr) expr).op.name();
//			Expr rightExpr1 = ((BinaryExpr) expr).left;
//			Expr rightExpr2 = ((BinaryExpr) expr).right;
//
//			if (opName.equals("AND") || opName.equals("OR") || opName.equals("IMPLIES") || opName.equals("ARROW")
//					|| opName.equals("EQUAL") || opName.equals("NOTEQUAL") || opName.equals("GREATER")
//					|| opName.equals("LESS") || opName.equals("GREATEREQUAL") || opName.contentEquals("LESSEQUAL")) {
//				if (exprMatchingIdExpr(rightExpr1, leafNodeId) && isConstantorInputExpr(agreeNode, rightExpr2)) {
//					result = true;
//				} else if (exprMatchingIdExpr(rightExpr2, leafNodeId) && isConstantorInputExpr(agreeNode, rightExpr1)) {
//					result = true;
//				}
//			}
//		}
//		return result;
//	}
//
//	private Boolean rightExprFinalForm(AgreeNode agreeNode, Expr expr, String leafNodeId) {
//		Boolean result = false;
//		if (expr instanceof IdExpr) {
//			result = idExprMatchingLeafNodeId((IdExpr) expr, leafNodeId);
//		} else if (expr instanceof UnaryExpr) {
//			if (((UnaryExpr) expr).expr instanceof IdExpr) {
//				result = idExprMatchingLeafNodeId((IdExpr) expr, leafNodeId);
//			}
//		} else if (expr instanceof BinaryExpr) {
//			String opName = ((BinaryExpr) expr).op.name();
//			Expr rightExpr1 = ((BinaryExpr) expr).left;
//			Expr rightExpr2 = ((BinaryExpr) expr).right;
//
//			if (opName.equals("AND") || opName.equals("OR") || opName.equals("IMPLIES") || opName.equals("ARROW")
//					|| opName.equals("EQUAL") || opName.equals("NOTEQUAL") || opName.equals("GREATER")
//					|| opName.equals("LESS") || opName.equals("GREATEREQUAL") || opName.contentEquals("LESSEQUAL")) {
//				if (exprMatchingIdExpr(rightExpr1, leafNodeId) && isConstantorInputExpr(agreeNode, rightExpr2)) {
//					result = true;
//				} else if (exprMatchingIdExpr(rightExpr2, leafNodeId) && isConstantorInputExpr(agreeNode, rightExpr1)) {
//					result = true;
//				}
//			}
//		}
//		return result;
//	}
//
//	private Boolean exprMatchingIdExpr(Expr expr, String leafNodeId) {
//		Boolean result = false;
//		if (expr instanceof IdExpr) {
//			result = idExprMatchingLeafNodeId((IdExpr) expr, leafNodeId);
//		}
//		return result;
//	}
//
//	private Boolean isConstantorInputExpr(AgreeNode agreeNode, Expr expr) {
//		Boolean result = false;
//		if (expr instanceof BoolExpr) {
//			result = true;
//		} else if (expr instanceof IntExpr) {
//			result = true;
//		} else if (expr instanceof RealExpr) {
//			result = true;
//		}
//		else if (expr instanceof IdExpr) {
//			result = AgreeUtil.inputsContainId(agreeNode, ((IdExpr) expr).id);
//		}
//		return result;
//	}
//
//	private Boolean idExprMatchingLeafNodeId(IdExpr idExpr, String leafNodeId) {
//		if (idExpr.id.equals(leafNodeId)) {
//			return true;
//		} else {
//			return false;
//		}
//
//	}

}
