package edu.umn.cs.crisys.safety.analysis.generators;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.osate.aadl2.NamedElement;
import org.osate.aadl2.instance.ComponentInstance;
import org.osate.aadl2.instance.ConnectionInstance;
import org.osate.aadl2.instance.ConnectionInstanceEnd;

import com.rockwellcollins.atc.agree.analysis.ast.AgreeNode;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeProgram;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeStatement;

import edu.umn.cs.crisys.safety.analysis.ast.visitors.LustreExprToCTVisitor;
import edu.umn.cs.crisys.safety.analysis.causationTree.CT;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTIdNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTNode;
import edu.umn.cs.crisys.safety.util.Util;
import jkind.lustre.Node;
import jkind.lustre.UnaryExpr;
import jkind.lustre.UnaryOp;

public class ModelToCTGenerator {
	List<CT> causationTrees = new ArrayList<CT>();

	public List<CT> generateCausationTree(Node lustreNode, AgreeNode agreeNode, ComponentInstance compInst,
			AgreeProgram agreeProgram) {
		//generate FT for each top level guarantee
		for (AgreeStatement topLevelGuarantee : agreeProgram.topNode.guarantees) {
			CT causationTree = new CT();
			// Step 1: negate the top level guarantee expression and turn it into a causation tree
			UnaryExpr topLevelEvent = new UnaryExpr(UnaryOp.NOT, topLevelGuarantee.expr);
			// CTRootNode rootNode = new CTRootNode(topLevelGuarantee.string);
			LustreExprToCTVisitor lustreExprToFTVisitor = new LustreExprToCTVisitor();
			// TODO: mark leaf nodes in the tree as the expr to CT translation goes
			CTNode rootNode = lustreExprToFTVisitor.visit(topLevelEvent);
			// TODO: expand the leaf nodes in the current tree
			for (CTNode leafNode : lustreExprToFTVisitor.leafNodes) {
				if (leafNode instanceof CTIdNode) {
					// get all top level system inputs
					// if the ID is one of the inputs, stop
					if (inputsContainId(agreeNode, leafNode.nodeName)) {
						break;
					}
					// TODO: get all top level system failures
					// if the ID is one of the failures, stop

					// if the ID is not one of the above, need to develop further
					else if (outputsContainId(agreeNode, leafNode.nodeName)) {
						// get all top level system outputs
						// if the ID is one of the outputs
						// go through all connections
						// and find all the components producing the output
						// for each component, find the agree node for that component
						// and develop a causation tree for the signal in that component based on the agree guarantees
						for (AgreeNode curNode : getProducingNodes(leafNode.nodeName, compInst, agreeProgram)) {
							// TODO: check AGREE annex for both comp and comp impl
							// for the signal of interest, identify all AGREE guarantees and failure behavioral definitions that are relevant
							for (AgreeStatement currentGuarantee : curNode.guarantees) {
								// transform each specifications into a logical formula
								// showing the implication relationship connecting
								// disjunction or conjunction of
								// input signals and failures
								// with the constraint of the output signal as appeared in the current leaf node

								// prune any implication relationships that do not involve the constraint of the signal of interest
								// as appeared in the current leaf node

								// expand the current leaf node by turning the left side of the implications into child nodes
								// connected by logical AND/OR operators
							}
							// Use AND operator to connect all the nodes created above to the original leaf node as parent node
							// make sure it extends the leaf node in the original tree
						}
						// TODO: if more than one, find the assertion in the top level component implementation
						// for the relationship between the multiple outputs
					}
					// TODO: if the ID is one of the internal variables, go find the definition of the internal variable until
					// reaching one of the category of signals identified above
					else {

					}
				}
				// TODO: handle other types of leaf nodes that are not CTIdNode
			}

			// rootNode.addChildNode(rootNode.nodeName, childNode);
			causationTree.setRootNode(rootNode);
			// Walk through the fault tree code to mark all failure nodes
			// a) When a basic event is a failure event, then it’s red
			// b) When a basic event is not a failure event (they call it “conditional event” in CAFTA), then it’s blue
			// c) For an OR node, if one of its child node is blue, mark it blue; if all of its child nodes are red, mark it red
			// d) For an AND node, if both of its child nodes are blue, mark it blue; if any of its child nodes is red, mark it red

			// Resolve the FTA so it's in one of the final form per the FT Resolve visitor

			causationTrees.add(causationTree);
		}
		return causationTrees;
	}

	private boolean inputsContainId(AgreeNode curNode, String idStr) {
		boolean containsId = false;
		List<String> inputs = Util.getIds(curNode.inputs);
		if (inputs.contains(idStr)) {
			containsId = true;
		}
		return containsId;
	}

	private boolean outputsContainId(AgreeNode curNode, String idStr) {
		boolean containsId = false;
		List<String> outputs = Util.getIds(curNode.outputs);
		if (outputs.contains(idStr)) {
			containsId = true;
		}
		return containsId;
	}

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

}
