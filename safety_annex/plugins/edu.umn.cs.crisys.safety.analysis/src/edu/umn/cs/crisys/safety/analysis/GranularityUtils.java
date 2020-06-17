package edu.umn.cs.crisys.safety.analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.rockwellcollins.atc.agree.agree.AgreeFactory;
import com.rockwellcollins.atc.agree.agree.Expr;
import com.rockwellcollins.atc.agree.agree.GuaranteeStatement;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeEquation;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeNode;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeNodeBuilder;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeStatement;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeVar;

import jkind.lustre.BinaryOp;
import jkind.lustre.IdExpr;
import jkind.lustre.NamedType;

public class GranularityUtils {

	private static int unique = 0;
//	private static List<String> boolOps = new ArrayList<String>();
//	private static List<String> intOps = new ArrayList<String>();
//	private static List<String> numOps = new ArrayList<String>();
	// Mapping from node to a list of AgreeStatements
	// Each Eq/Lemma/Guarantee can be split into more - need
	// to add all to the new node during visit operation.
//	private HashMap<String, List<AgreeStatement>> mapNodeToAgreeStatements = new HashMap<String, List<AgreeStatement>>();
	// Nodes to replace agree nodes from initial program
//	private List<AgreeNode> newNodes = new ArrayList<AgreeNode>();
	// Hash from fresh vars to expressions
	private static HashMap<String, jkind.lustre.Expr> freshVars = new HashMap<String, jkind.lustre.Expr>();

	public static AgreeNodeBuilder decomposeNodeContracts(AgreeNode node, AgreeNodeBuilder nb) {

		for (AgreeStatement stmt : node.guarantees) {
			transformStatement(stmt, nb);
			for (String s : freshVars.keySet()) {
				nb.addIvcElement(s);
			}
			freshVars.clear();
			unique = 0;
		}
		return nb;
	}

	private static void transformStatement(AgreeStatement stmt, AgreeNodeBuilder nb) {
		if (stmt.reference instanceof GuaranteeStatement) {
//			nb.addGuarantee(splitGuaranteeAnd(stmt));

			unInline(stmt.expr);
			for (String s : freshVars.keySet()) {
				nb.addLocal(new AgreeVar(s, NamedType.BOOL, stmt.reference));

				nb.addLocalEquation(new AgreeEquation(new IdExpr(s), freshVars.get(s), stmt.reference));
//				builder.addAssertion(new AgreeStatement("", b, topNode.reference));
//				// builder.addGuarantee(new AgreeStatement("", b, topNode.reference));
			}
		}
	}

	private List<AgreeStatement> splitGuaranteeAnd(AgreeStatement agreeStmt) {
		List<AgreeStatement> newStmts = new ArrayList<AgreeStatement>();
		if (agreeStmt.expr instanceof jkind.lustre.BinaryExpr) {
			jkind.lustre.BinaryExpr binExpr = (jkind.lustre.BinaryExpr) agreeStmt.expr;
			if (binExpr.op == BinaryOp.AND) {
				recurseAnd(agreeStmt.expr, newStmts, agreeStmt.string);
			} else if (binExpr.op == BinaryOp.ARROW) {
				if (binExpr.right instanceof jkind.lustre.BinaryExpr) {
					jkind.lustre.BinaryExpr binR = (jkind.lustre.BinaryExpr) binExpr.right;
					if (binR.op == BinaryOp.AND) {
						recurseAndArrow(binExpr.right, newStmts, agreeStmt.string, binExpr.left);
					}
				}
			}
		}

		return newStmts;
	}

	private void recurseAnd(jkind.lustre.Expr expr, List<AgreeStatement> newStmts, String agreeStmtString) {
		if (expr instanceof jkind.lustre.BinaryExpr) {
			jkind.lustre.BinaryExpr binExpr = (jkind.lustre.BinaryExpr) expr;
			String newDescrL = unique + " LEFT %% ";
			String newDescrR = unique + " RIGHT %% ";
			unique++;
			GuaranteeStatement gsL = AgreeFactory.eINSTANCE.createGuaranteeStatement();
			gsL.setStr(newDescrL);
			GuaranteeStatement gsR = AgreeFactory.eINSTANCE.createGuaranteeStatement();
			gsR.setStr(newDescrR);
			AgreeStatement guarL = new AgreeStatement(newDescrL, binExpr.left, gsL);
			AgreeStatement guarR = new AgreeStatement(newDescrR, binExpr.right, gsR);
			newStmts.add(guarR);
			newStmts.add(guarL);
			if (binExpr.left instanceof jkind.lustre.BinaryExpr) {
				if (((jkind.lustre.BinaryExpr) binExpr.left).op == BinaryOp.AND) {
					recurseAnd(binExpr.left, newStmts, agreeStmtString);
				}
			}
		}
	}

	private void recurseAndArrow(jkind.lustre.Expr expr, List<AgreeStatement> newStmts, String agreeStmtString,
			jkind.lustre.Expr arrowExpr) {
		if (expr instanceof jkind.lustre.BinaryExpr) {
			jkind.lustre.BinaryExpr binExpr = (jkind.lustre.BinaryExpr) expr;
			String newDescrL = unique + " LEFT %% ";
			String newDescrR = unique + " RIGHT %% ";
			unique++;
			GuaranteeStatement gsL = AgreeFactory.eINSTANCE.createGuaranteeStatement();
			gsL.setStr(newDescrL);
			GuaranteeStatement gsR = AgreeFactory.eINSTANCE.createGuaranteeStatement();
			gsR.setStr(newDescrR);
			AgreeStatement guarL = new AgreeStatement(newDescrL,
					new jkind.lustre.BinaryExpr(arrowExpr, BinaryOp.ARROW, binExpr.left), gsL);
			AgreeStatement guarR = new AgreeStatement(newDescrR,
					new jkind.lustre.BinaryExpr(arrowExpr, BinaryOp.ARROW, binExpr.right), gsR);
			newStmts.add(guarR);
			newStmts.add(guarL);
			if (binExpr.left instanceof jkind.lustre.BinaryExpr) {
				if (((jkind.lustre.BinaryExpr) binExpr.left).op == BinaryOp.AND) {
					recurseAndArrow(binExpr.left, newStmts, agreeStmtString, arrowExpr);
				}
			}
		}
	}

	private static void unInline(jkind.lustre.Expr expr) {
		if (expr instanceof jkind.lustre.BinaryExpr) {
			jkind.lustre.BinaryExpr exBin = (jkind.lustre.BinaryExpr) expr;
//			IdExpr id = new IdExpr("freshVar" + unique);
//			freshVars.put(id.id, exBin);
//			unique++;

			if (isBoolOp(exBin.op)) {
				// Set left/right to fresh var if binary & bool op
				if (exBin.left instanceof jkind.lustre.BinaryExpr) {
					if (isBoolOp(((jkind.lustre.BinaryExpr) exBin.left).op)) {
						IdExpr idL = new IdExpr("freshVar" + unique);
						freshVars.put(idL.id, exBin.left);
						unique++;
						unInline(exBin.left);
					}
				}
				if (exBin.right instanceof jkind.lustre.BinaryExpr) {
					if (isBoolOp(((jkind.lustre.BinaryExpr) exBin.right).op)) {
						IdExpr idR = new IdExpr("freshVar" + unique);
						freshVars.put(idR.id, exBin.right);
						unique++;
						unInline(exBin.right);
					}
				}
			}
		}
	}

	private static boolean isBoolOp(BinaryOp op) {
		if (op.equals(BinaryOp.EQUAL) || op.equals(BinaryOp.NOTEQUAL) || op.equals(BinaryOp.GREATER)
				|| op.equals(BinaryOp.LESS) || op.equals(BinaryOp.GREATEREQUAL) || op.equals(BinaryOp.LESSEQUAL)
				|| op.equals(BinaryOp.OR) || op.equals(BinaryOp.AND) || op.equals(BinaryOp.XOR)
				|| op.equals(BinaryOp.IMPLIES) || op.equals(BinaryOp.ARROW)) {
			return true;
		} else {
			return false;
		}
	}

//	private NamedType getTypeOfExpr(Expr expr) {
//		NamedType type = null;
//		if (expr instanceof BinaryExpr) {
//			BinaryExpr binEx = (BinaryExpr) expr;
//			if (boolOps.contains(binEx.getOp())) {
//				return new NamedType("bool");
//			} else if (intOps.contains(binEx.getOp())) {
//				return new NamedType("int");
//			} else {
//				return getDeeperType(expr);
//			}
//		}
//		return type;
//	}

	private NamedType getDeeperType(Expr expr) {

		return null;
	}

//	private static void initLists() {
//		boolOps.addAll(
//				Arrays.asList("and", "or", "xor", "->", "=", ">", ">=", "<", "<=", "<>", "=>"));
//
//		intOps.addAll(Arrays.asList("div", "mod"));
//
//		numOps.addAll(Arrays.asList("/", "*", "+", "-"));
//	}

}
//PLUS ("+"),
//MINUS ("-"),
//MULTIPLY ("*"),
//DIVIDE ("/"),
//INT_DIVIDE ("div"),
//MODULUS ("mod"),
//EQUAL ("="),
//NOTEQUAL ("<>"),
//GREATER (">"),
//LESS ("<"),
//GREATEREQUAL (">="),
//LESSEQUAL ("<="),
//OR ("or"),
//AND ("and"),
//XOR ("xor"),
//IMPLIES ("=>"),
//ARROW ("->");
