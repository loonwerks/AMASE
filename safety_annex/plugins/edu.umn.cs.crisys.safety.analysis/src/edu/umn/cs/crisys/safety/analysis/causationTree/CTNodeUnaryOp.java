package edu.umn.cs.crisys.safety.analysis.causationTree;

public enum CTNodeUnaryOp {
	NOT("NOT"), PRE("PRE"), FIRST_STEP("FIRST_STEP"), BEYOND_FIRST_STEP("BEYOND_FIRST_STEP");

	private String str;

	private CTNodeUnaryOp(String str) {
		this.str = str;
	}

	@Override
	public String toString() {
		return str;
	}

	public static CTNodeUnaryOp fromName(String name) {
		for (CTNodeUnaryOp op : CTNodeUnaryOp.values()) {
			if (op.name().equals(name)) {
				return op;
			}
		}
		return null;
	}

}
