package edu.umn.cs.crisys.safety.analysis.ast.visitors;

import edu.umn.cs.crisys.safety.analysis.SafetyException;
import edu.umn.cs.crisys.safety.analysis.faultTree.FTAndNode;
import edu.umn.cs.crisys.safety.analysis.faultTree.FTLeafNode;
import edu.umn.cs.crisys.safety.analysis.faultTree.FTNode;
import edu.umn.cs.crisys.safety.analysis.faultTree.FTNonLeafNode;
import edu.umn.cs.crisys.safety.analysis.faultTree.FTOrNode;
import edu.umn.cs.crisys.safety.analysis.faultTree.FaultTree;

public class FTPrettyPrintVisitor implements FTAstVisitor<Void> {
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
		// print leaf nodes
		for (FTLeafNode leaf : ft.leafNodes.values()) {
			leaf.accept(this);
		}
		// print non leaf nodes
		for (FTNonLeafNode nonLeaf : ft.sortedIntermediateNodes) {
			nonLeaf.accept(this);
		}
		//compute cutsets and probabilities for each root node
		for (FTNonLeafNode root : ft.rootNodes.values()) {
			String rootName = root.propertyName;
			writeln("(* ----- CUTSET WITH PROBABILITIES ----- *)");
			writeln("cutsets "+rootName+";;");
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

	return null;

	}

	@Override
	public Void visit(FTLeafNode leaf) {
		writeln("let " + leaf.soteriaFaultName + " = ");
		writeln("Leaf");
		write("    ((\"" + leaf.compName + "\",");
		write("\"" + leaf.soteriaFaultName + "\"),");
		write(leaf.failureRate + ", ");
		writeln(leaf.exposureTime + ");;");
		return null;
	}

	@Override
	public Void visit(FTNonLeafNode nonLeaf) {
		if (nonLeaf instanceof FTOrNode) {
			FTOrNode orNode = (FTOrNode) nonLeaf;
			orNode.accept(this);
		} else if (nonLeaf instanceof FTAndNode) {
			FTAndNode andNode = (FTAndNode) nonLeaf;
			andNode.accept(this);
		} else {
			throw new SafetyException("Not instanstiated non leaf node " + nonLeaf.nodeName);
		}
		return null;
	}

	@Override
	public Void visit(FTOrNode orNode) {
		printNonLeafNode(orNode.nodeOpStr, orNode);
		return null;
	}

	@Override
	public Void visit(FTAndNode andNode) {
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

}
