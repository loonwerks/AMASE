package edu.umn.cs.crisys.safety.analysis.ast.visitors;

import edu.umn.cs.crisys.safety.analysis.SafetyException;
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
		// print min cut set for each root node
		for (SoteriaFTNode root : ft.resolvedRootNodes) {
			String rootName = root.nodeName;
			writeln("cutsets for " + rootName + ";;");
			for (SoteriaFTNode child : root.childNodes.values()) {
				child.accept(this);
			}
		}
	return null;

	}

	@Override
	public Void visit(SoteriaFTLeafNode leaf) {
		writeln("let " + leaf.faultName + " = ");
		writeln("Leaf");
		write("    ((\"" + leaf.compName + "\",");
		write("\"" + leaf.faultName + "\"),");
		write(leaf.failureRate + ", ");
		writeln(leaf.exposureTime + ");;");
		return null;
	}

	@Override
	public Void visit(SoteriaFTNonLeafNode nonLeaf) {
		if (nonLeaf instanceof SoteriaFTOrNode) {
			SoteriaFTOrNode orNode = (SoteriaFTOrNode) nonLeaf;
			orNode.accept(this);
		} else if (nonLeaf instanceof SoteriaFTAndNode) {
			SoteriaFTAndNode andNode = (SoteriaFTAndNode) nonLeaf;
			andNode.accept(this);
		} else {
			throw new SafetyException("Not instanstiated non leaf node " + nonLeaf.nodeName);
		}
		return null;
	}

	@Override
	public Void visit(SoteriaFTOrNode orNode) {
		printNonLeafNode(orNode.nodeOpStr, orNode);
		return null;
	}

	@Override
	public Void visit(SoteriaFTAndNode andNode) {
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

}
