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
				printRootNode(rootName);
				root.accept(this);
			} else {
				printNoTreeRootNode(rootName);
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
		for (SoteriaFTNode child : orNode.childNodes.values()) {
			writeln("Minimal Cut Set:");
			child.accept(this);
		}
		return null;
	}

	@Override
	public Void visit(SoteriaFTAndNode andNode) {
		writeln("Cardinality " + andNode.childNodes.size());
		writeln("[");
		for (SoteriaFTNode child : andNode.childNodes.values()) {
			child.accept(this);
		}
		writeln("];");
		return null;
	}

	private void printNoTreeRootNode(String rootName) {
		writeln("----- NO CUTSET for " + rootName + "-----");
		newline();
	}

	private void printRootNode(String rootName) {
		writeln("----- CUTSET with probabilities for " + rootName + " -----");
	}

//	[
//	--temp_sensor_stuck_at_high "temp sensor stuck at high"
//	 (("Reactor_Temp_Ctrl","reactor_Temp_Ctrl_fault__independently__active__Temp_Sensor3__Temp_Sensor3__fault_1"),1.0E-5, 1.0);
//	 --temp_sensor_stuck_at_high "temp sensor stuck at high"
//	 (("Reactor_Temp_Ctrl","reactor_Temp_Ctrl_fault__independently__active__Temp_Sensor2__Temp_Sensor2__fault_1"),1.0E-5, 1.0)
//	]

	private void printLeafNode(SoteriaFTLeafNode leaf) {
		writeln("-- ");
		write("    ((\"" + leaf.compName + "\",");
		write("\"" + leaf.soteriaFaultName + "\"),");
		write(leaf.failureRate + ", ");
		writeln(leaf.exposureTime + ");");
	}

}
