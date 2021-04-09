package edu.umn.cs.crisys.safety.analysis.constraints.ast.expr;

public enum ConstraintBinaryOp {
	EQUAL("=="), NOTEQUAL("!="), OR("|"), AND("&");

	private String str;

	private ConstraintBinaryOp(String str) {
		this.str = str;
	}

	@Override
	public String toString() {
		return str;
	}

	public static ConstraintBinaryOp fromName(String name) {
		for (ConstraintBinaryOp op : ConstraintBinaryOp.values()) {
			if (op.name().equals(name)) {
				return op;
			}
		}
		return null;
	}

	public static ConstraintBinaryOp fromStr(String str) {
		for (ConstraintBinaryOp op : ConstraintBinaryOp.values()) {
			if (op.str.equals(str)) {
				return op;
			}
		}
		return null;
	}

}
