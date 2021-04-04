package edu.umn.cs.crisys.safety.analysis.constraints.ast;

public enum BinaryTermConstraintOp {
	ATOM_EQ("ATOM_EQ"), ATOM_NEQ("ATOM_NEQ"), ATOM_LEQ("ATOM_LEQ"), ATOM_LT("ATOM_LT"), ATOM_GT("ATOM_GT"), ATOM_GEQ(
			"ATOM_GEQ"), ATOM_MOD("ATOM_MOD");

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

}
