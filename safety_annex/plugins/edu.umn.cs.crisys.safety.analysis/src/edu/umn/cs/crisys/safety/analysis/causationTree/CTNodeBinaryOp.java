package edu.umn.cs.crisys.safety.analysis.causationTree;

public enum CTNodeBinaryOp {
	AND("AND"), OR("OR");

	private String str;

	private CTNodeBinaryOp(String str) {
		this.str = str;
	}

	@Override
	public String toString() {
		return str;
	}

	public static CTNodeBinaryOp fromName(String name) {
		for (CTNodeBinaryOp op : CTNodeBinaryOp.values()) {
			if (op.name().equals(name)) {
				return op;
			}
		}
		return null;
	}

}
