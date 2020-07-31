package edu.umn.cs.crisys.safety.analysis.ast.visitors;

import edu.umn.cs.crisys.safety.analysis.faultTree.FTAndNode;
import edu.umn.cs.crisys.safety.analysis.faultTree.FTLeafNode;
import edu.umn.cs.crisys.safety.analysis.faultTree.FTNode;
import edu.umn.cs.crisys.safety.analysis.faultTree.FTNonLeafNode;
import edu.umn.cs.crisys.safety.analysis.faultTree.FTOrNode;
import edu.umn.cs.crisys.safety.analysis.faultTree.FaultTree;

public class FTMinCutSetPrintVisitor implements FTAstVisitor<Void> {
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
		write(ft.includeStr);
		newline();

		// walk through the tree and print from bottom to top
		for (FTNode root : ft.resolvedRootNodes) {
			String rootName = root.nodeName;
			if (root.nodeValue == true) {
				root.accept(this);
				printRootNode(rootName);
			} else {
				printNoTreeRootNode(rootName);
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
		for (FTNode child : orNode.childNodes.values()) {
			child.accept(this);
		}
		printNonLeafNode(orNode.nodeOpStr, orNode);
		return null;
	}

	@Override
	public Void visit(FTAndNode andNode) {
		for (FTNode child : andNode.childNodes.values()) {
			child.accept(this);
		}
		printNonLeafNode(andNode.nodeOpStr, andNode);
		return null;
	}

	private void printNonLeafNode(String nodeOpStr, FTNonLeafNode nonLeaf) {
		writeln("let " + nonLeaf.propertyName + " = ");
		writeln(nodeOpStr + " [");
		boolean multipleElem = false;
		for (FTNode node : nonLeaf.childNodes.values()) {
			if (multipleElem) {
				writeln(";");
			}
			write(node.nodeName);
			multipleElem = true;
		}
		writeln("    ];;");
	}

	private void printNoTreeRootNode(String rootName) {
		writeln("(* ----- NO CUTSET for " + rootName + "----- *)");
		newline();
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

	private void printLeafNode(FTLeafNode leaf) {
		writeln("let " + leaf.soteriaFaultName + " = ");
		writeln("Leaf");
		write("    ((\"" + leaf.compName + "\",");
		write("\"" + leaf.soteriaFaultName + "\"),");
		write(leaf.failureRate + ", ");
		writeln(leaf.exposureTime + ");;");
	}

}
