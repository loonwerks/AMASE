package edu.umn.cs.crisys.safety.analysis.ast.visitors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.umn.cs.crisys.safety.analysis.MHSUtils;
import edu.umn.cs.crisys.safety.analysis.SafetyException;
import edu.umn.cs.crisys.safety.analysis.soteria.faultTree.SoteriaFTAndNode;
import edu.umn.cs.crisys.safety.analysis.soteria.faultTree.SoteriaFTLeafNode;
import edu.umn.cs.crisys.safety.analysis.soteria.faultTree.SoteriaFTNode;
import edu.umn.cs.crisys.safety.analysis.soteria.faultTree.SoteriaFTNonLeafNode;
import edu.umn.cs.crisys.safety.analysis.soteria.faultTree.SoteriaFTOrNode;
import edu.umn.cs.crisys.safety.analysis.soteria.faultTree.SoteriaFaultTree;

public class SoteriaFTResolveVisitor implements SoteriaFTAstVisitor<SoteriaFTNode> {

	@Override
	public SoteriaFTNode visit(SoteriaFaultTree ft) {
		for (SoteriaFTNonLeafNode root : ft.rootNodes.values()) {
			SoteriaFTNode newRoot = root.accept(this);
			ft.addResolvedRootNode(newRoot);
		}
		return null;
	}

	@Override
	public SoteriaFTLeafNode visit(SoteriaFTLeafNode node) {
		node.resolved = true;
		return node;
	}

	@Override
	public SoteriaFTNonLeafNode visit(SoteriaFTNonLeafNode node) {
		return node;
	}

	@Override
	public SoteriaFTNonLeafNode visit(SoteriaFTOrNode node) {
		SoteriaFTNonLeafNode returnNode = null;
		boolean isRoot = node.isRoot;
		List<SoteriaFTNode> childNodesToRemove = new ArrayList<SoteriaFTNode>();
		// if not resolved, go visit its child nodes
		if (!isORNodeResolved(node, isRoot)) {
			for (SoteriaFTNode child : node.childNodes.values()) {
				SoteriaFTNode childReturn = child.accept(this);
				if (childReturn.nodeValue == false) {
					childNodesToRemove.add(child);
				} else {
					node.replaceChildNode(childReturn.nodeName, childReturn);
				}
			}
			node.removeChildNodes(childNodesToRemove);
			// if no child node left for this node
			// set it can be removed at the upper level
			if (node.childNodes.size() == 0) {
				node.nodeValue = false;
				return node;
			}
			// after visiting, if not resolved, go resolve it
			if (!isORNodeResolved(node, isRoot)) {
				returnNode = resolveOrNode(node, isRoot);
			}
		}
		if (returnNode != null) {
			return prune(returnNode);
		} else {
			return prune(node);
		}
	}

	@Override
	public SoteriaFTNode visit(SoteriaFTAndNode node) {
		SoteriaFTNonLeafNode returnNode = null;
		boolean isRoot = node.isRoot;
		List<SoteriaFTNode> childNodesToRemove = new ArrayList<SoteriaFTNode>();
		// if not resolved, go visit its child nodes
		if (!isANDNodeResolved(node)) {
			for (SoteriaFTNode child : node.childNodes.values()) {
				SoteriaFTNode childReturn = child.accept(this);
				if (childReturn.nodeValue == false) {
					childNodesToRemove.add(child);
				} else {
					node.replaceChildNode(childReturn.nodeName, childReturn);
				}
			}
			node.removeChildNodes(childNodesToRemove);
			// if no child node left for this node
			// set it can be removed at the upper level
			if (node.childNodes.size() == 0) {
				node.nodeValue = false;
				return node;
			}
			// after visiting, if not resolved, go resolve it
			if (!isANDNodeResolved(node)) {
				returnNode = resolveAndNode(node);
			}
		}

		if (returnNode != null) {
			return prune(returnNode);
		} else {
			return prune(node);
		}
	}

	// An root OR node is resolved if
	// all its child nodes are either leaf nodes, or resolved AND nodes
	// An non root OR node is resolved if
	// all its child nodes are leaf nodes
	private boolean isORNodeResolved(SoteriaFTNonLeafNode node, boolean isRoot) {
		if (node.resolved) {
			return true;
		}
		for (SoteriaFTNode child : node.childNodes.values()) {

			if (!(child instanceof SoteriaFTLeafNode)) {
				if (!isRoot) {
					return false;
				} else {
					if ((!child.resolved) || (!(child instanceof SoteriaFTAndNode))) {
						return false;
					}
				}
			} else {
				child.resolved = true;
			}
		}
		node.resolved = true;
		return true;
	}

	private SoteriaFTNonLeafNode prune(SoteriaFTNonLeafNode node) {
		// only prune AND node
		if (node instanceof SoteriaFTAndNode) {
			if (AddFaultsToNodeVisitor.maxFaultCount != 0) {
				if (node.childNodes.size() > AddFaultsToNodeVisitor.maxFaultCount) {
					node.nodeValue = false;
				} else {
					node.nodeValue = true;
				}
			} else if (!AddFaultsToNodeVisitor.faultCombinationsAboveThreshold.isEmpty()) {
				// TODO: prune according to valid fault combinations
				node.nodeValue = true;
			}
		}
		return node;
	}

	// An AND node is resolved if
	// all its child nodes are leaf nodes
	private boolean isANDNodeResolved(SoteriaFTNonLeafNode node) {
		if (node.resolved) {
			return true;
		}
		for (SoteriaFTNode child : node.childNodes.values()) {
			if (!(child instanceof SoteriaFTLeafNode)) {
				return false;
			} else {
				child.resolved = true;
			}
		}
		node.resolved = true;
		return true;
	}

	private SoteriaFTNonLeafNode resolveOrNode(SoteriaFTNonLeafNode node, boolean isRoot) {
		SoteriaFTNonLeafNode returnNode = null;
		// go through all child nodes that are resolved OR node, and promote the grand child nodes
		promoteNode(node);
		if ((isORNodeResolved(node, isRoot))) {
			return node;
		}

		// if non root node
		if (!isRoot) {
			// for the rest of the child nodes that are resolved AND nodes, transform the parent node
			returnNode = transformNode(node);
		}
		if (returnNode == null) {
			// set resolved to true
			node.resolved = true;
			return node;
		} else {
			returnNode.resolved = true;
			return returnNode;
		}
	}

	private SoteriaFTNonLeafNode resolveAndNode(SoteriaFTNonLeafNode node) {
		// go through all child nodes that are resolved AND node, and promote the grand child nodes
		promoteNode(node);

		if ((isANDNodeResolved(node))) {
			return node;
		}
		// for the rest of the child nodes that are resolved OR node, transform the parent node
		SoteriaFTNonLeafNode returnNode = transformNode(node);
		// if no new node created from transformNode, return node
		if (returnNode == null) {
			// set resolved to true
			node.resolved = true;
			return node;
		}
		// else return new node created from transformNode
		else {
			returnNode.resolved = true;
			return returnNode;
		}
	}

	private SoteriaFTNonLeafNode transformNode(SoteriaFTNonLeafNode node) {
		HashMap<String, SoteriaFTNode> nodesMap = new HashMap<>();
		Set<List<String>> sourceSets = new HashSet<List<String>>();
		SoteriaFTNonLeafNode returnNode = null;
		boolean originalAndNode = false;
		int oppositeChildNum = 0;
		int siblingLeafNum = 0;

		if (node instanceof SoteriaFTAndNode) {
			originalAndNode = true;
		} else {
			originalAndNode = false;
		}

		// compute hitting set from the following set:
		// 1) one set for the child nodes of each node whose AND/OR is opposite to the parent node
		// 2) one set for each member of the sibling leaf node of the parent node
		for (SoteriaFTNode child : node.childNodes.values()) {
			if (child instanceof SoteriaFTLeafNode) {
				siblingLeafNum++;
				List<String> curList = new ArrayList<>();
				nodesMap.put(child.nodeName, child);
				curList.add(child.nodeName);
				sourceSets.add(curList);
			} else if (!child.getClass().equals(node.getClass())) {
				oppositeChildNum++;
				List<String> curList = new ArrayList<>();
				for (SoteriaFTNode curNode : child.childNodes.values()) {
					nodesMap.put(curNode.nodeName, curNode);
					curList.add(curNode.nodeName);
				}
				sourceSets.add(curList);
			} else {
				throw new SafetyException("Child node " + child.nodeName + " should be promoted");
			}
		}

		if (oppositeChildNum == 0) {
			return returnNode;
		} else {
			if ((oppositeChildNum == 1) && (siblingLeafNum == 0)) {
				String newNodeName = node.nodeName; // MHSUtils.createUniqueElemName(node.nodeName);
				if (originalAndNode) {
					returnNode = new SoteriaFTOrNode(newNodeName);
				} else {
					returnNode = new SoteriaFTAndNode(newNodeName);
				}

				for (SoteriaFTNode child : nodesMap.values()) {
					// add the child node to returnNode
					returnNode.addChildNode(child.nodeName, child);
				}
				return returnNode;
			}

			// make each minimal hitting set an node whose AND/OR that matches parent node, and whose child nodes are the elements of that mhs set
			// connect all mhs sets via a node whose AND/OR is the opposite of the original parent node, and replace the original parent node with this new node
			// TODO: set mhs set size according to fault hypothesis
			Set<List<String>> destSets = new HashSet<List<String>>();

			// if original AND node, transform to an OR node with child nodes as AND nodes connecting leaf nodes
			// the child nodes for each AND node corresponds to a mhs returned
			// - they can be pruned according to fault hypothesis
			if (originalAndNode) {
				if (AddFaultsToNodeVisitor.maxFaultCount != 0) {
					destSets = MHSUtils.computeMHS(sourceSets, AddFaultsToNodeVisitor.maxFaultCount);
				} else if (!AddFaultsToNodeVisitor.faultCombinationsAboveThreshold.isEmpty()) {
					// TODO: prune according to valid fault combinations
				}
			}
			// else no pruning
			else {
				destSets = MHSUtils.computeMHS(sourceSets, 0);
			}

			if (destSets.size() == 0) {
				return returnNode;
			} else {
				String newNodeName = node.nodeName; // MHSUtils.createUniqueElemName(node.nodeName);
				if (originalAndNode) {
					returnNode = new SoteriaFTOrNode(newNodeName);
				} else {
					returnNode = new SoteriaFTAndNode(newNodeName);
				}

				if (destSets.size() > 1) {
					for (List<String> curSet : destSets) {
						SoteriaFTNonLeafNode curNode;
						String curNodeName = MHSUtils.createUniqueElemName(node.nodeName);
						if (originalAndNode) {
							curNode = new SoteriaFTAndNode(curNodeName);
						} else {
							curNode = new SoteriaFTOrNode(curNodeName);
						}
						for (String curChildName : curSet) {
							// get original FT node corresponding to the string
							SoteriaFTNode childNode = nodesMap.get(curChildName);
							// add the child node to curNode
							curNode.addChildNode(curChildName, childNode);
						}
						returnNode.addChildNode(curNodeName, curNode);
					}
				} else {
					for (List<String> curSet : destSets) {
						for (String curChildName : curSet) {
							// get original FT node corresponding to the string
							SoteriaFTNode childNode = nodesMap.get(curChildName);
							// add the child node to returnNode
							returnNode.addChildNode(curChildName, childNode);
						}
					}
				}
			}
			return returnNode;
		}
	}

	// replace a node with its child nodes in its parent node's child nodes
	private void promoteNode(SoteriaFTNonLeafNode node) {
		List<SoteriaFTNode> childNodesToAdd = new ArrayList<SoteriaFTNode>();
		List<SoteriaFTNode> childNodesToRemove = new ArrayList<SoteriaFTNode>();

		for (SoteriaFTNode childNode : node.childNodes.values()) {
			// if child node not yet resolved, throw exception
			if (!childNode.resolved) {
				throw new SafetyException(
						"Unresolved child node " + childNode.nodeName + " for parent node " + node.nodeName);
			} else {
				if ((childNode.childNodes.size() == 1) || childNode.getClass().equals(node.getClass())) {
					for (SoteriaFTNode grandChild : childNode.childNodes.values()) {
						childNodesToAdd.add(grandChild);
					}
					childNodesToRemove.add(childNode);
				}
			}
		}

		node.addChildNodes(childNodesToAdd);
		node.removeChildNodes(childNodesToRemove);
	}

}
