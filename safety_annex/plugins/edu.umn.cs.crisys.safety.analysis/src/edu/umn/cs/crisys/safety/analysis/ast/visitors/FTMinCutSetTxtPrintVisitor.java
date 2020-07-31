package edu.umn.cs.crisys.safety.analysis.ast.visitors;

import java.util.Comparator;
import java.util.LinkedList;

import edu.umn.cs.crisys.safety.analysis.faultTree.FTAndNode;
import edu.umn.cs.crisys.safety.analysis.faultTree.FTLeafNode;
import edu.umn.cs.crisys.safety.analysis.faultTree.FTNode;
import edu.umn.cs.crisys.safety.analysis.faultTree.FTNonLeafNode;
import edu.umn.cs.crisys.safety.analysis.faultTree.FTOrNode;
import edu.umn.cs.crisys.safety.analysis.faultTree.FaultTree;

public class FTMinCutSetTxtPrintVisitor implements FTAstVisitor<Void> {
	private StringBuilder sb = new StringBuilder();

	@Override
	public String toString() {
		return sb.toString();
	}

	protected void write(Object o) {
		sb.append(o);
	}

	protected void writeln(Object o) {
		sb.append(o);
		newline();
	}

	private static final String seperator = System.getProperty("line.separator");

	private void newline() {
		write(seperator);
	}

	@Override
	public Void visit(FaultTree ft) {
		// walk through the tree and print from bottom to top
		for (FTNode root : ft.resolvedRootNodes) {
			String rootName = root.nodeName;
			if (root.nodeValue == true) {
				printRootNode(root, rootName);
				root.accept(this);
			} else {
				printNoTreeRootNode(root, rootName);
			}
		}
		return null;

	}

	@Override
	public Void visit(FTLeafNode leaf) {
		printLeafNode(leaf);
		return null;
	}

	@Override
	public Void visit(FTNonLeafNode nonLeaf) {
		return null;
	}

	@Override
	public Void visit(FTOrNode orNode) {
		LinkedList<FTNode> sortedChildren = new LinkedList<FTNode>(orNode.childNodes.values());
		sortedChildren.sort(Comparator.comparing(FTNode::getProbability).reversed());
		writeln("Total " + orNode.childNodes.size() + " Minimal Cut Sets found for this property");
		writeln("Probability of failure for the overall property: " + orNode.getProbability());
		writeln("");
		int minCutSetNum = 0;
		for (FTNode child : sortedChildren) {
			minCutSetNum++;
			writeln("Minimal Cut Set # " + minCutSetNum);
			if (child instanceof FTLeafNode) {
				writeln("Cardinality 1");
			}
			child.accept(this);
			newline();
		}
		return null;
	}

	@Override
	public Void visit(FTAndNode andNode) {
		writeln("Cardinality " + andNode.childNodes.size());
		writeln("Joint probability for this Minimal Cut Set: " + andNode.getProbability());
		writeln("[");
		int faultNum = 0;
		for (FTNode child : andNode.childNodes.values()) {
			faultNum++;
			writeln("Fault # " + faultNum);
			child.accept(this);
		}
		writeln("];");
		return null;
	}

	private void printNoTreeRootNode(FTNode root, String rootName) {
		writeln("No Minimal Cut Sets for property violation:");
		writeln("property lustre name: " + rootName);
		if (root instanceof FTNonLeafNode) {
			String propertyDescription = ((FTNonLeafNode) root).propertyDescription;
			writeln("property description: " + propertyDescription);
		}
		newline();
	}

	private void printRootNode(FTNode root, String rootName) {
		writeln("Minimal Cut Sets for property violation:");
		writeln("property lustre name: " + rootName);
		if (root instanceof FTNonLeafNode) {
			String propertyDescription = ((FTNonLeafNode) root).propertyDescription;
			writeln("property description: " + propertyDescription);
		}
	}

	private void printLeafNode(FTLeafNode leaf) {
		writeln("original fault name, description: " + leaf.faultUserName + ", \"" + leaf.faultUserExplanation
				+ "\"");
		writeln("lustre component, fault name: " + leaf.compName + ", " + leaf.soteriaFaultName);
		writeln("probability: " + leaf.getProbability());
	}

}
