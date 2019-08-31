package edu.umn.cs.crisys.safety.analysis.ast.visitors;

import edu.umn.cs.crisys.safety.analysis.soteria.faultTree.SoteriaFTAndNode;
import edu.umn.cs.crisys.safety.analysis.soteria.faultTree.SoteriaFTLeafNode;
import edu.umn.cs.crisys.safety.analysis.soteria.faultTree.SoteriaFTNode;
import edu.umn.cs.crisys.safety.analysis.soteria.faultTree.SoteriaFTNonLeafNode;
import edu.umn.cs.crisys.safety.analysis.soteria.faultTree.SoteriaFTOrNode;
import edu.umn.cs.crisys.safety.analysis.soteria.faultTree.SoteriaFaultTree;

public class SoteriaFTMinCutSetTxtPrintVisitor implements SoteriaFTAstVisitor<Void> {
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
	public Void visit(SoteriaFaultTree ft) {
		// walk through the tree and print from bottom to top
		for (SoteriaFTNode root : ft.resolvedRootNodes) {
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
	public Void visit(SoteriaFTLeafNode leaf) {
		printLeafNode(leaf);
		return null;
	}

	@Override
	public Void visit(SoteriaFTNonLeafNode nonLeaf) {
		return null;
	}

	@Override
	public Void visit(SoteriaFTOrNode orNode) {
		writeln("Total " + orNode.childNodes.size() + " Minimal Cut Sets");
		int minCutSetNum = 0;
		for (SoteriaFTNode child : orNode.childNodes.values()) {
			minCutSetNum++;
			writeln("Minimal Cut Set # " + minCutSetNum);
			child.accept(this);
		}
		return null;
	}

	@Override
	public Void visit(SoteriaFTAndNode andNode) {
		writeln("Cardinality " + andNode.childNodes.size());
		writeln("[");
		int faultNum = 0;
		for (SoteriaFTNode child : andNode.childNodes.values()) {
			faultNum++;
			writeln("Fault # " + faultNum);
			child.accept(this);
		}
		writeln("];");
		return null;
	}

	private void printNoTreeRootNode(SoteriaFTNode root, String rootName) {
		writeln("No Minimal Cut Sets for property:");
		writeln("property lustre name: " + rootName);
		if (root instanceof SoteriaFTNonLeafNode) {
			String propertyDescription = ((SoteriaFTNonLeafNode) root).propertyDescription;
			writeln("property description: " + propertyDescription);
		}
		newline();
	}

	private void printRootNode(SoteriaFTNode root, String rootName) {
		writeln("Minimal Cut Sets for property:");
		writeln("property lustre name: " + rootName);
		if (root instanceof SoteriaFTNonLeafNode) {
			String propertyDescription = ((SoteriaFTNonLeafNode) root).propertyDescription;
			writeln("property description: " + propertyDescription);
		}
	}

	private void printLeafNode(SoteriaFTLeafNode leaf) {
		writeln("original fault name, description: " + leaf.faultUserName + ", \"" + leaf.faultUserExplanation
				+ "\"");
		writeln("lustre component, fault name: " + leaf.compName + ", " + leaf.soteriaFaultName);
		writeln("failureRate, default exposure time: " + leaf.failureRate + ", " + leaf.exposureTime);
	}

}
