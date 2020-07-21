package edu.umn.cs.crisys.safety.analysis.ast.visitors;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class SoteriaPrintUtils {
	private static StringBuilder sb = new StringBuilder();

	@Override
	public String toString() {
		return sb.toString();
	}

	protected static void write(Object o) {
		sb.append(o);
	}

	protected static void writeln(Object o) {
		sb.append(o);
		newline();
	}

	private static final String seperator = System.getProperty("line.separator");

	protected static void newline() {
		write(seperator);
	}

	public void printEmptyTree() {
		writeln("(* ----- NO CUTSET " + "----- *)");
		newline();
	}

	public static String printHierarchicalText(HashMap<String, Set<List<String>>> mapForHFT) {

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

	public static void resetBuilder() {
		sb.setLength(0);
	}

}
