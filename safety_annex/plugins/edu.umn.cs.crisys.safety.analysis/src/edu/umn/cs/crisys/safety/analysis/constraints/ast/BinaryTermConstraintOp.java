package edu.umn.cs.crisys.safety.analysis.constraints.ast;

public enum BinaryTermConstraintOp {
	ATOM_EQ("EQUAL"), ATOM_NEQ("NOTEQUAL"), ATOM_LEQ("LESSEQUAL"), ATOM_LT("LESS"), ATOM_GT("GREATER"), ATOM_GEQ(
			"GREATEREQUAL");

	private String str;

	private BinaryTermConstraintOp(String str) {
		this.str = str;
	}

	@Override
	public String toString() {
		return str;
	}

	public static BinaryTermConstraintOp fromName(String name) {
		for (BinaryTermConstraintOp op : BinaryTermConstraintOp.values()) {
			if (op.name().equals(name)) {
				return op;
			}
		}
		return null;
	}

	public static BinaryTermConstraintOp fromStr(String str) {
		for (BinaryTermConstraintOp op : BinaryTermConstraintOp.values()) {
			if (op.str.equals(str)) {
				return op;
			}
		}
		return null;
	}

}
