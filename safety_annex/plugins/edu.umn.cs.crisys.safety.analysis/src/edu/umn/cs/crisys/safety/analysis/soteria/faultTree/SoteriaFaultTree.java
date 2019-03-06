package edu.umn.cs.crisys.safety.analysis.soteria.faultTree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.umn.cs.crisys.safety.analysis.ast.visitors.SoteriaFTAstVisitor;

public class SoteriaFaultTree extends SoteriaFTAst {
	public final String includeStr = "#use \"top.ml\";;";
	public HashMap<String, SoteriaFTLeafNode> leafNodes = new HashMap<>();
	public HashMap<String, SoteriaFTNonLeafNode> rootNodes = new HashMap<>();
	public HashMap<String, SoteriaFTNonLeafNode> intermediateNodes = new HashMap<>();
	public List<SoteriaFTNonLeafNode> sortedIntermediateNodes = new ArrayList<SoteriaFTNonLeafNode>();
	public List<SoteriaFTNode> resolvedRootNodes = new ArrayList<SoteriaFTNode>();

	public void addLeafNode(String nodeName, SoteriaFTLeafNode leafNode) {
		leafNodes.put(nodeName, leafNode);
	}

	public void addRootNode(String nodeName, SoteriaFTNonLeafNode rootNode) {
		rootNodes.put(nodeName, rootNode);
	}

	public void addIntermediateNode(String nodeName, SoteriaFTNonLeafNode intermediateNode) {
		intermediateNodes.put(nodeName, intermediateNode);
	}

	public void addResolvedRootNode(SoteriaFTNode intermediateNode) {
		resolvedRootNodes.add(intermediateNode);
	}

	@Override
	public <T> T accept(SoteriaFTAstVisitor<T> visitor) {
		return visitor.visit(this);
	}

	// some node's child nodes were added before those child nodes obtained their own child nodes
	// walk through all intermediate nodes to fix any discrepancies
	public void updateChildNodes() {
		for (SoteriaFTNonLeafNode intermediateNode : intermediateNodes.values()) {
			for (String childName : intermediateNode.childNodes.keySet()) {
				if (intermediateNodes.containsKey(childName)) {
					intermediateNode.addChildNode(childName, intermediateNodes.get(childName));
				}
			}
		}
	}

	// sort through intermediate nodes to declare before use
	public void sortIntermediateNodes() {
		for (SoteriaFTNonLeafNode intermediateNode : intermediateNodes.values()) {
			declareBeforeUse(intermediateNode);
		}
	}

	private void declareBeforeUse(SoteriaFTNonLeafNode node) {
		// if it's not yet in the sortedIntermediateNodes
		if (!sortedIntermediateNodes.contains(node)) {
			// go through each child node
			// if a child node is not a leaf node
			// declare before use that child node
			for (SoteriaFTNode childNode : node.childNodes.values()) {
				if (childNode instanceof SoteriaFTNonLeafNode) {
					declareBeforeUse((SoteriaFTNonLeafNode) childNode);
				}
			}
			// after going through the child node, add the node to sortedIntermediateNodes
			sortedIntermediateNodes.add(node);
		}
	}
}
