package edu.umn.cs.crisys.safety.analysis.constraints.ast.expr;

public enum ConstraintUnaryOp {
	NOT("!");

	private String str;

	private ConstraintUnaryOp(String str) {
		this.str = str;
	}

	@Override
	public String toString() {
		return str;
	}

	public static ConstraintUnaryOp fromName(String name) {
		for (ConstraintUnaryOp op : ConstraintUnaryOp.values()) {
			if (op.name().equals(name)) {
				return op;
			}
		}
		return null;
	}
}
