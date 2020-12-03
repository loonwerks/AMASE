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

							// TODO: if the ID is one of the failures, stop developing further, set isLeaf true for that node

							// if the ID contained in that expression is an output produced by other components
							// TODO: to handle multiple verification layers
							// need to change from top level agreeNode to current level agreeNode
							else if (AgreeUtil.outputsContainId(currentAgreeNode, id)) {
								handleOutputId(bottomIdNodeVisitor, bottomIdNode, id);
							}

							// TODO: if the ID is one of the internal variables
							// go find the definition of the internal variable until
							// reaching one of the category of signals identified above
							else {

							}
						}
						// if not top level node
						else {
							// TODO: if the ID is one of the failures, stop developing further, set isLeaf true for that node

							// if the ID contained in that expression is an input produced by other components
							// TODO: to handle multiple verification layers
							// need to change from top level agreeNode to current level agreeNode
							if (AgreeUtil.inputsContainId(currentAgreeNode, id)) {
								handleOutputId(bottomIdNodeVisitor, bottomIdNode, id);
							}
							// TODO: if the ID is one of the internal variables
							// go find the definition of the internal variable until
							// reaching one of the category of signals identified above
							else {

							}
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
			// for each component, get the agree node and lustre node for that component
			// Translate Agree Node to Lustre Node with pre-statement flatten, helper nodes inlined,
			// and variable declarations sorted so they are declared before use
			Node curLustreNode = AgreeNodeToLustreContract.translate(nextAgreeNode, agreeProgram);
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
							bottomIdNodeVisitor.setCurAgreeNode(nextAgreeNode);
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
				while (setIterator.hasNext()) {
					bottomIdNode.addChildNode(setIterator.next());
				}
			}
		}
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
}
