package edu.umn.cs.crisys.safety.analysis.faultTree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.umn.cs.crisys.safety.analysis.ast.visitors.FTAstVisitor;

public class FaultTree extends FTAst {
	public final String includeStr = "#use \"top.ml\";;";
	public HashMap<String, FTLeafNode> leafNodes = new HashMap<>();
	public HashMap<String, FTNonLeafNode> rootNodes = new HashMap<>();
	public HashMap<String, FTNonLeafNode> intermediateNodes = new HashMap<>();
	public List<FTNonLeafNode> sortedIntermediateNodes = new ArrayList<FTNonLeafNode>();
	public List<FTNode> resolvedRootNodes = new ArrayList<FTNode>();

	public void addLeafNode(String nodeName, FTLeafNode leafNode) {
		leafNodes.put(nodeName, leafNode);
	}

	public void addRootNode(String nodeName, FTNonLeafNode rootNode) {
		rootNodes.put(nodeName, rootNode);
	}

	public void addIntermediateNode(String nodeName, FTNonLeafNode intermediateNode) {
		intermediateNodes.put(nodeName, intermediateNode);
	}

	public void addResolvedRootNode(FTNode intermediateNode) {
		resolvedRootNodes.add(intermediateNode);
	}

	@Override
	public <T> T accept(FTAstVisitor<T> visitor) {
		return visitor.visit(this);
	}

	// some node's child nodes were added before those child nodes obtained their own child nodes
	// walk through all intermediate nodes to fix any discrepancies
	public void updateChildNodes() {
		List<FTNonLeafNode> nodesToRemove = new ArrayList<FTNonLeafNode>();
		for (FTNonLeafNode intermediateNode : intermediateNodes.values()) {
			List<FTNode> childNodesToRemove = new ArrayList<FTNode>();
			for (String childName : intermediateNode.childNodes.keySet()) {
				if (intermediateNodes.containsKey(childName)) {
					// update child node if it has child node
					FTNonLeafNode childNode = intermediateNodes.get(childName);
					if ((childNode instanceof FTOrNode) || (childNode instanceof FTAndNode)) {
						intermediateNode.addChildNode(childName, intermediateNodes.get(childName));
					}
					else {
						// if parent node is an OR node
						// remove this child
						if (intermediateNode instanceof FTOrNode) {
							childNodesToRemove.add(childNode);
							nodesToRemove.add(childNode);
						}
						// if parent node is an AND node
						// remove the parent node and child node
						// and set their values to false in case they are referenced by other nodes
						else if (intermediateNode instanceof FTAndNode) {
							nodesToRemove.add(intermediateNode);
							nodesToRemove.add(childNode);
							// mark the nodes as they are not getting removed till after the loop
							intermediateNode.resolved = true;
							intermediateNode.nodeValue = false;
							childNode.resolved = true;
							childNode.nodeValue = false;
						}
					}
				}
			}
			intermediateNode.removeChildNodes(childNodesToRemove);
			// if no child node left for this intermediate node,
			// then its parent node should remove this intermediate node as well
		}
		// remove the no child nodes
		for (FTNonLeafNode node : nodesToRemove) {
			intermediateNodes.remove(node.nodeName);
		}
	}

	// sort through intermediate nodes to declare before use
	public void sortIntermediateNodes() {
		for (FTNonLeafNode intermediateNode : intermediateNodes.values()) {
			declareBeforeUse(intermediateNode);
		}
	}

	private void declareBeforeUse(FTNonLeafNode node) {
		// if it's not yet in the sortedIntermediateNodes
		if (!sortedIntermediateNodes.contains(node)) {
			// go through each child node
			// if a child node is not a leaf node
			// declare before use that child node
			for (FTNode childNode : node.childNodes.values()) {
				if (childNode instanceof FTNonLeafNode) {
					declareBeforeUse((FTNonLeafNode) childNode);
				}
			}
			// after going through the child node, add the node to sortedIntermediateNodes
			sortedIntermediateNodes.add(node);
		}
	}
}
