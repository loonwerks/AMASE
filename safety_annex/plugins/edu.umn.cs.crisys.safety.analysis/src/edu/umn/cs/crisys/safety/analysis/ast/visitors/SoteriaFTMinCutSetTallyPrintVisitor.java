package edu.umn.cs.crisys.safety.analysis.ast.visitors;

import java.util.Map;
import java.util.TreeMap;

import edu.umn.cs.crisys.safety.analysis.soteria.faultTree.SoteriaFTAndNode;
import edu.umn.cs.crisys.safety.analysis.soteria.faultTree.SoteriaFTLeafNode;
import edu.umn.cs.crisys.safety.analysis.soteria.faultTree.SoteriaFTNode;
import edu.umn.cs.crisys.safety.analysis.soteria.faultTree.SoteriaFTNonLeafNode;
import edu.umn.cs.crisys.safety.analysis.soteria.faultTree.SoteriaFTOrNode;
import edu.umn.cs.crisys.safety.analysis.soteria.faultTree.SoteriaFaultTree;

public class SoteriaFTMinCutSetTallyPrintVisitor implements SoteriaFTAstVisitor<Void> {
	private StringBuilder sb = new StringBuilder();
	private Tally tally = new Tally();

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
			tally.initTally();
			if (root.nodeValue == true) {
				printRootNode(root, rootName);
				root.accept(this);
				tally.printTally();
			} else {
				printNoTreeRootNode(root, rootName);
			}
		}
		return null;

	}

	@Override
	public Void visit(SoteriaFTLeafNode leaf) {
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
			// writeln("Minimal Cut Set # " + minCutSetNum);
			if (child instanceof SoteriaFTLeafNode) {
				// writeln("Cardinality 1");
				tally.addToTally(1);
			}
			child.accept(this);
			// newline();
		}
		return null;
	}

	@Override
	public Void visit(SoteriaFTAndNode andNode) {
		// writeln("Cardinality " + andNode.childNodes.size());
		tally.addToTally(andNode.childNodes.size());
		return null;
	}

	private void printNoTreeRootNode(SoteriaFTNode root, String rootName) {
		writeln("No Minimal Cut Sets for property violation:");
		writeln("property lustre name: " + rootName);
		if (root instanceof SoteriaFTNonLeafNode) {
			String propertyDescription = ((SoteriaFTNonLeafNode) root).propertyDescription;
			writeln("property description: " + propertyDescription);
		}
		newline();
	}

	private void printRootNode(SoteriaFTNode root, String rootName) {
		writeln("Minimal Cut Sets for property violation:");
		writeln("property lustre name: " + rootName);
		if (root instanceof SoteriaFTNonLeafNode) {
			String propertyDescription = ((SoteriaFTNonLeafNode) root).propertyDescription;
			writeln("property description: " + propertyDescription);
		}
	}

	class Tally {
		// sorted map
		private Map<Integer, Counter> tallyMap = new TreeMap<Integer, Counter>();

		void initTally() {
			tallyMap.clear();
		}

		void addToTally(int count) {
			if (tallyMap.containsKey(count)) {
				tallyMap.get(count).increment();
			} else {
				Counter counter = new Counter();
				tallyMap.put(count, counter);
			}
		}

		void printTally() {
			// Iterate per order
			for (Map.Entry<Integer, Counter> entry : tallyMap.entrySet()) {
				writeln("Cardinality " + entry.getKey() + " number: " + entry.getValue().getCount());
			}
		}
	}

	class Counter {

		private int count;

		public Counter() {
			count = 1;
		}

		public void increment() {
			count = count + 1;
		}

		public int getCount() {
			return count;
		}

	}

}
