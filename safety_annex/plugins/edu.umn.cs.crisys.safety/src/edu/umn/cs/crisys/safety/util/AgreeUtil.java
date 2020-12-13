package edu.umn.cs.crisys.safety.util;

import java.util.List;

import com.rockwellcollins.atc.agree.analysis.ast.AgreeNode;

import jkind.lustre.BinaryExpr;
import jkind.lustre.BoolExpr;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.UnaryExpr;

public class AgreeUtil {

	public static boolean inputsContainId(AgreeNode curNode, String idStr) {
		boolean containsId = false;
		List<String> inputs = Util.getIds(curNode.inputs);
		if (inputs.contains(idStr)) {
			containsId = true;
		}
		return containsId;
	}

	public static boolean outputsContainId(AgreeNode curNode, String idStr) {
		boolean containsId = false;
		List<String> outputs = Util.getIds(curNode.outputs);
		if (outputs.contains(idStr)) {
			containsId = true;
		}
		return containsId;
	}

	public static boolean exprsEqual(Expr expr1, Expr expr2) {
		Boolean result = false;
		if (expr1.toString().equals(expr2.toString())) {
			result = true;
		} else if (expr1 instanceof BinaryExpr) {
			Expr leftExpr = ((BinaryExpr) expr1).left;
			Expr rightExpr = ((BinaryExpr) expr1).right;
			String binaryOpName = ((BinaryExpr) expr1).op.name();

			if (binaryOpName.equals("EQUAL")) {
				if (leftExpr instanceof IdExpr) {
					String leftExprId = ((IdExpr) leftExpr).id;
					if (expr2 instanceof IdExpr) {
						String expr2Id = ((IdExpr) expr2).id;
						if (leftExprId.equals(expr2Id)) {
							if (rightExpr instanceof BoolExpr) {
								if (((BoolExpr) rightExpr).value) {
									result = true;
								}
							}
						}
					} else if (expr2 instanceof UnaryExpr) {
						String unaryOpName = ((UnaryExpr) expr2).op.name();
						if (unaryOpName.equals("NEGATIVE") || unaryOpName.equals("NOT")) {
							if (((UnaryExpr) expr2).expr instanceof IdExpr) {
								String expr2Id = ((IdExpr) ((UnaryExpr) expr2).expr).id;
								if (leftExprId.equals(expr2Id)) {
									if (rightExpr instanceof BoolExpr) {
										if (!((BoolExpr) rightExpr).value) {
											result = true;
										}
									}
								}
							}
						}
					}
				}
			}
		} else if (expr2 instanceof BinaryExpr) {
			Expr leftExpr = ((BinaryExpr) expr2).left;
			Expr rightExpr = ((BinaryExpr) expr2).right;
			String binaryOpName = ((BinaryExpr) expr2).op.name();

			if (binaryOpName.equals("EQUAL")) {
				if (leftExpr instanceof IdExpr) {
					String leftExprId = ((IdExpr) leftExpr).id;
					if (expr1 instanceof IdExpr) {
						String expr1Id = ((IdExpr) expr1).id;
						if (leftExprId.equals(expr1Id)) {
							if (rightExpr instanceof BoolExpr) {
								if (((BoolExpr) rightExpr).value) {
									result = true;
								}
							}
						}
					} else if (expr1 instanceof UnaryExpr) {
						String unaryOpName = ((UnaryExpr) expr1).op.name();
						if (unaryOpName.equals("NEGATIVE") || unaryOpName.equals("NOT")) {
							if (((UnaryExpr) expr1).expr instanceof IdExpr) {
								String expr1Id = ((IdExpr) ((UnaryExpr) expr1).expr).id;
								if (leftExprId.equals(expr1Id)) {
									if (rightExpr instanceof BoolExpr) {
										if (!((BoolExpr) rightExpr).value) {
											result = true;
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return result;
	}

	public static boolean exprsContradict(Expr expr1, Expr expr2) {
		Boolean result = false;
		if (expr1 instanceof BinaryExpr) {
			Expr leftExpr = ((BinaryExpr) expr1).left;
			Expr rightExpr = ((BinaryExpr) expr1).right;
			String binaryOpName = ((BinaryExpr) expr1).op.name();

			if (binaryOpName.equals("EQUAL")) {
				if (leftExpr instanceof IdExpr) {
					String leftExprId = ((IdExpr) leftExpr).id;
					if (expr2 instanceof IdExpr) {
						String expr2Id = ((IdExpr) expr2).id;
						if (leftExprId.equals(expr2Id)) {
							if (rightExpr instanceof BoolExpr) {
								if (!((BoolExpr) rightExpr).value) {
									result = true;
								}
							}
						}
					} else if (expr2 instanceof UnaryExpr) {
						String unaryOpName = ((UnaryExpr) expr2).op.name();
						if (unaryOpName.equals("NEGATIVE") || unaryOpName.equals("NOT")) {
							if (((UnaryExpr) expr2).expr instanceof IdExpr) {
								String expr2Id = ((IdExpr) ((UnaryExpr) expr2).expr).id;
								if (leftExprId.equals(expr2Id)) {
									if (rightExpr instanceof BoolExpr) {
										if (((BoolExpr) rightExpr).value) {
											result = true;
										}
									}
								}
							}
						}
					}
				}
			}
		} else if (expr2 instanceof BinaryExpr) {
			Expr leftExpr = ((BinaryExpr) expr2).left;
			Expr rightExpr = ((BinaryExpr) expr2).right;
			String binaryOpName = ((BinaryExpr) expr2).op.name();

			if (binaryOpName.equals("EQUAL")) {
				if (leftExpr instanceof IdExpr) {
					String leftExprId = ((IdExpr) leftExpr).id;
					if (expr1 instanceof IdExpr) {
						String expr1Id = ((IdExpr) expr1).id;
						if (leftExprId.equals(expr1Id)) {
							if (rightExpr instanceof BoolExpr) {
								if (!((BoolExpr) rightExpr).value) {
									result = true;
								}
							}
						}
					} else if (expr1 instanceof UnaryExpr) {
						String unaryOpName = ((UnaryExpr) expr1).op.name();
						if (unaryOpName.equals("NEGATIVE") || unaryOpName.equals("NOT")) {
							if (((UnaryExpr) expr1).expr instanceof IdExpr) {
								String expr1Id = ((IdExpr) ((UnaryExpr) expr1).expr).id;
								if (leftExprId.equals(expr1Id)) {
									if (rightExpr instanceof BoolExpr) {
										if (((BoolExpr) rightExpr).value) {
											result = true;
										}
									}
								}
							}
						}
					}
				}
			}
		} else if (expr1 instanceof IdExpr) {
			if (expr2 instanceof UnaryExpr) {
				String unaryOpName = ((UnaryExpr) expr2).op.name();
				if (unaryOpName.equals("NEGATIVE") || unaryOpName.equals("NOT")) {
					if (((UnaryExpr) expr2).expr instanceof IdExpr) {
						String expr1Id = ((IdExpr) expr1).id;
						String expr2Id = ((IdExpr) ((UnaryExpr) expr2).expr).id;
						if (expr1Id.equals(expr2Id)) {
							result = true;
						}
					}
				}
			}
		} else if (expr2 instanceof IdExpr) {
			if (expr1 instanceof UnaryExpr) {
				String unaryOpName = ((UnaryExpr) expr1).op.name();
				if (unaryOpName.equals("NEGATIVE") || unaryOpName.equals("NOT")) {
					if (((UnaryExpr) expr1).expr instanceof IdExpr) {
						String expr2Id = ((IdExpr) expr2).id;
						String expr1Id = ((IdExpr) ((UnaryExpr) expr1).expr).id;
						if (expr1Id.equals(expr2Id)) {
							result = true;
						}
					}
				}
			}
		}
		return result;
	}
}
