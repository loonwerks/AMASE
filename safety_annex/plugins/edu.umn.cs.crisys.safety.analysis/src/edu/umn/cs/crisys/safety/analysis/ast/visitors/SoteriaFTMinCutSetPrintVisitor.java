package edu.umn.cs.crisys.safety.analysis.ast.visitors;

import edu.umn.cs.crisys.safety.analysis.soteria.faultTree.SoteriaFTAndNode;
import edu.umn.cs.crisys.safety.analysis.soteria.faultTree.SoteriaFTLeafNode;
import edu.umn.cs.crisys.safety.analysis.soteria.faultTree.SoteriaFTNode;
import edu.umn.cs.crisys.safety.analysis.soteria.faultTree.SoteriaFTNonLeafNode;
import edu.umn.cs.crisys.safety.analysis.soteria.faultTree.SoteriaFTOrNode;
import edu.umn.cs.crisys.safety.analysis.soteria.faultTree.SoteriaFaultTree;

public class SoteriaFTMinCutSetPrintVisitor implements SoteriaFTAstVisitor<Void> {
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
		write(ft.includeStr);
		newline();

		// walk through the tree and print from bottom to top
		for (SoteriaFTNode root : ft.resolvedRootNodes) {
			String rootName = root.nodeName;
			root.accept(this);
			printRootNode(rootName);
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
		for (SoteriaFTNode child : orNode.childNodes.values()) {
			child.accept(this);
		}
		printNonLeafNode(orNode.nodeOpStr, orNode);
		return null;
	}

	@Override
	public Void visit(SoteriaFTAndNode andNode) {
		for (SoteriaFTNode child : andNode.childNodes.values()) {
			child.accept(this);
		}
		printNonLeafNode(andNode.nodeOpStr, andNode);
		return null;
	}

	private void printNonLeafNode(String nodeOpStr, SoteriaFTNonLeafNode nonLeaf) {
		writeln("let " + nonLeaf.propertyName + " = ");
		writeln(nodeOpStr + " [");
		boolean multipleElem = false;
		for (SoteriaFTNode node : nonLeaf.childNodes.values()) {
			if (multipleElem) {
				writeln(";");
			}
			write(node.nodeName);
			multipleElem = true;
		}
		writeln("    ];;");
	}

	private void printRootNode(String rootName) {
		writeln("(* ----- CUTSET WITH PROBABILITIES ----- *)");
		writeln("cutsets " + rootName + ";;");
		writeln("probErrorCut " + rootName + ";;");
		writeln("probErrorCutImp " + rootName + ";;");
		writeln("(* ----- FAULT TREE VISUALIZATIONS ----- *)");
		// fault tree visualization
		write("dot_gen_show_direct_tree_file ~rend:\"pdf\" ");
		writeln("\"" + rootName + "_direct_ftree.gv\" " + rootName + " ;;");
		write("dot_gen_show_tree_file ~rend:\"pdf\" ");
		writeln("\"" + rootName + "_optimized_ftree.gv\" " + rootName + " ;;");
		newline();
	}

	private void printLeafNode(SoteriaFTLeafNode leaf) {
		writeln("let " + leaf.faultName + " = ");
		writeln("Leaf");
		write("    ((\"" + leaf.compName + "\",");
		write("\"" + leaf.faultName + "\"),");
		write(leaf.failureRate + ", ");
		writeln(leaf.exposureTime + ");;");
	}

}
