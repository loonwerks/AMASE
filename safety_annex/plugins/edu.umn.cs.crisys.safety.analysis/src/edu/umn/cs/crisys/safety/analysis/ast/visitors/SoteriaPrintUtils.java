package edu.umn.cs.crisys.safety.analysis.ast.visitors;

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

	private static final String seperator = System.getProperty("line.separator");

	protected void newline() {
		write(seperator);
	}

	public void printEmptyTree() {
		writeln("(* ----- NO CUTSET " + "----- *)");
		newline();
	}

}
