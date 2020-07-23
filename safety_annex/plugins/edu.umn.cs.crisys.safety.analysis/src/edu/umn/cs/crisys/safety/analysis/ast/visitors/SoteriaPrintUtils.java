package edu.umn.cs.crisys.safety.analysis.ast.visitors;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class SoteriaPrintUtils {
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

	private final String seperator = System.getProperty("line.separator");

	protected void newline() {
		write(seperator);
	}

	public void printEmptyTree() {
		writeln("(* ----- NO CUTSET " + "----- *)");
		newline();
	}

	public String printHierarchicalText(HashMap<String, Set<List<String>>> mapForHFT) {

		for (String prop : mapForHFT.keySet()) {
			writeln(prop);
			writeln("---------------------------------");
			if (mapForHFT.get(prop).isEmpty()) {
				writeln("There are currently no contributing violations for " + prop + ".");
				newline();
			}
			int i = 1;
			for (List<String> mcsList : mapForHFT.get(prop)) {
				writeln("Set " + i + " for " + prop);
				for (String mcs : mcsList) {
					writeln(mcs);
				}
				newline();
				i++;
			}
		}
		newline();
		return sb.toString();
	}
}
