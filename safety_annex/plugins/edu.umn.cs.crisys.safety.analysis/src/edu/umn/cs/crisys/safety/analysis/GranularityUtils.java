package edu.umn.cs.crisys.safety.analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.rockwellcollins.atc.agree.agree.AgreeFactory;
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
	private static HashMap<String, jkind.lustre.BinaryExpr> freshVars = new HashMap<String, jkind.lustre.BinaryExpr>();
	private static HashMap<AgreeStatement, AgreeStatement> newGuars = new HashMap<AgreeStatement, AgreeStatement>();
	private static List<String> IVCElements = new ArrayList<String>();

	public static AgreeNodeBuilder decomposeNodeContracts(AgreeNode node, AgreeNodeBuilder nb, boolean decomposeAnd,
			boolean uninline) {
		for (AgreeStatement stmt : node.guarantees) {
			transformStatement(stmt, nb, decomposeAnd, uninline);
			for (String s : freshVars.keySet()) {
				// If fresh var maps to binary expr with both fresh vars
				// then do not add to ivcs.
				// Only add to ivc elements when one or more are not fresh
				addToIVCElements(s);

//				nb.addIvcElement(s);
			}
			nb.addIvcElements(IVCElements);
			IVCElements.clear();
			freshVars.clear();
			unique = 0;
		}
		return nb;
	}

	/**
	 * Transform a guarantee based on flag: uninline or decomposeAnd
	 * @param stmt guarantee
	 * @param nb node builder containing guarantees
	 * @param decomposeAnd bool flag
	 * @param uninline bool flag
	 */
	private static void transformStatement(AgreeStatement stmt, AgreeNodeBuilder nb, boolean decomposeAnd,
			boolean uninline) {
		if (stmt.reference instanceof GuaranteeStatement) {
			if (decomposeAnd) {
				nb.addGuarantee(splitGuaranteeAnd(stmt));
			} else if (uninline) {
				unInlineInit(stmt);
				for (String s : freshVars.keySet()) {
					nb.addLocal(new AgreeVar(s, NamedType.BOOL, stmt.reference));
					nb.addLocalEquation(new AgreeEquation(new IdExpr(s), freshVars.get(s), stmt.reference));
				}
				for (AgreeStatement as : newGuars.keySet()) {
					nb.addGuarantee(as);
					// TODO: remove old guarantee from node builder
				}
				newGuars.clear();
			}
		}
	}

	/**
	 * If guarantee has AND as upper level operator, split recursively into
	 * separate guarantees.
	 * @param agreeStmt Guarantee
	 * @return List of guarantees
	 */
	private static List<AgreeStatement> splitGuaranteeAnd(AgreeStatement agreeStmt) {
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

	/**
	 * Recursive method that creates new guarantees given top level AND operator.
	 * @param expr The guarantee expression
	 * @param newStmts List of new guarantees
	 * @param agreeStmtString Description string
	 */
	private static void recurseAnd(jkind.lustre.Expr expr, List<AgreeStatement> newStmts, String agreeStmtString) {
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

	/**
	 * When arrow operator, need to take this into account when creating new
	 * guarantees.
	 * @param expr Guarantee expression
	 * @param newStmts List of new guarantees
	 * @param agreeStmtString Guarantee string description
	 * @param arrowExpr lhs of arrow
	 */
	private static void recurseAndArrow(jkind.lustre.Expr expr, List<AgreeStatement> newStmts, String agreeStmtString,
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

	private static void unInlineInit(AgreeStatement stmt) {
		// Base case: if guarantee expr is not binary or does
		// not have safe bool operator, do not unInline this guar.
		if (stmt.expr instanceof jkind.lustre.BinaryExpr) {
			jkind.lustre.BinaryExpr guarEx = (jkind.lustre.BinaryExpr) stmt.expr;
			if (isSafeBoolOp(guarEx.op)) {
				// Now we know we can recurse over the guarantee
				String descr = "NEW GUARANTEE, FRESH VARS: " + stmt.string;
				IdExpr id = new IdExpr("freshVar" + unique);
				freshVars.put(id.id, guarEx);
				unique++;
				GuaranteeStatement gs = AgreeFactory.eINSTANCE.createGuaranteeStatement();
				gs.setStr(descr);
				AgreeStatement guarNew = new AgreeStatement(descr, id, gs);
				// Save new guarantee, map to old guarantee for removal
				newGuars.put(guarNew, stmt);
				// Recurse over guar expr
				unInline(guarEx, id);
			}
		}
	}

	/**
	 *
	 * @param expr
	 * @param fresh
	 */
	private static void unInline(jkind.lustre.BinaryExpr expr, IdExpr fresh) {
		// We know expr is binary with safe bool operator
		// and guar : fresh, but fresh is not assigned yet.
		jkind.lustre.Expr finalLeft;
		jkind.lustre.Expr finalRight;
		// Check left for binary with safe operator
		if (expr.left instanceof jkind.lustre.BinaryExpr) {
			jkind.lustre.BinaryExpr leftEx = (jkind.lustre.BinaryExpr) expr.left;
			if (isSafeBoolOp(leftEx.op)) {
				IdExpr idL = new IdExpr("freshVar" + unique);
				unique++;
				finalLeft = idL;
				unInline(leftEx, idL);
			} else {
				finalLeft = leftEx;
			}
		} else {
			finalLeft = expr.left;
		}
		// Check right for binary with safe operator
		if (expr.right instanceof jkind.lustre.BinaryExpr) {
			jkind.lustre.BinaryExpr rightEx = (jkind.lustre.BinaryExpr) expr.right;
			if (isSafeBoolOp(rightEx.op)) {
				IdExpr idR = new IdExpr("freshVar" + unique);
				unique++;
				finalRight = idR;
				unInline(rightEx, idR);
			} else {
				finalRight = rightEx;
			}
		} else {
			finalRight = expr.right;
		}
		// Now, finalLeft and finalRight should be assigned to 'fresh'
		// and given the top level operator
		freshVars.put(fresh.id, new jkind.lustre.BinaryExpr(finalLeft, expr.op, finalRight));

//		// Base Case 1: expr is not Binary
//		if (!(expr instanceof jkind.lustre.BinaryExpr)) {
//			freshVars.put(fresh.id, expr);
//		}
//		// Base Case 2: expr is Binary, but without bool operator
//		else if (expr instanceof jkind.lustre.BinaryExpr) {
//			jkind.lustre.BinaryExpr binEx = (jkind.lustre.BinaryExpr) expr;
////			if (!(isSafeBoolOp(binEx.op))) {
////				freshVars.put(fresh.id, expr);
////			} else {
//			if (isSafeBoolOp(binEx.op)) {
//				// Possible recursive Case: expr is Binary with bool operator
//				// Check left for binary
//				if (binEx.left instanceof jkind.lustre.BinaryExpr) {
//					jkind.lustre.BinaryExpr binExL = (jkind.lustre.BinaryExpr) binEx.left;
//					if (isSafeBoolOp(binExL.op)) {
//
//					}
//				}
//
////				IdExpr id1 = new IdExpr("freshVar" + unique);
////				unique++;
////				IdExpr id2 = new IdExpr("freshVar" + unique);
////				unique++;
////				freshVars.put(fresh.id, new jkind.lustre.BinaryExpr(id1, binEx.op, id2));
////				unInline(binEx.left, id1);
////				unInline(binEx.right, id2);
//			}
//		}
	}

	/**
	 *  Break apart guarantee and create fresh variables for each boolean:
	 *  Recursive method:
	 *  - Base case: not boolean operator -> freshVars map is complete
	 *  - Recursive case: boolean operator -> check l and r expression for bool op
	 *  		If bool op, recurse l/r
	 *  expression nested within
	 * @param expr Guarantee expression
	 */
//	private static void unInline(jkind.lustre.Expr expr) {
//		if (expr instanceof jkind.lustre.BinaryExpr) {
//			jkind.lustre.BinaryExpr exBin = (jkind.lustre.BinaryExpr) expr;
////			IdExpr id = new IdExpr("freshVar" + unique);
////			freshVars.put(id.id, exBin);
////			unique++;
//
//			if (isBoolOp(exBin.op)) {
//				// Set left/right to fresh var if binary & bool op
//				if (exBin.left instanceof jkind.lustre.BinaryExpr) {
//					if (isBoolOp(((jkind.lustre.BinaryExpr) exBin.left).op)) {
//						IdExpr idL = new IdExpr("freshVar" + unique);
//						freshVars.put(idL.id, exBin.left);
//						unique++;
//						unInline(exBin.left);
//					}
//				}
//				if (exBin.right instanceof jkind.lustre.BinaryExpr) {
//					if (isBoolOp(((jkind.lustre.BinaryExpr) exBin.right).op)) {
//						IdExpr idR = new IdExpr("freshVar" + unique);
//						freshVars.put(idR.id, exBin.right);
//						unique++;
//						unInline(exBin.right);
//					}
//				}
//			}
//		}
//	}

	private static void addToIVCElements(String s) {
		// Get mapping
		jkind.lustre.BinaryExpr ex = freshVars.get(s);
		if (!(ex.left.toString().contains("fresh")) && !(ex.right.toString().contains("fresh"))) {
			IVCElements.add(s);
		}

	}

	/**
	 * Checks if operator is bool
	 * @param op operator of JKind expr
	 * @return true if bool
	 */
	private static boolean isSafeBoolOp(BinaryOp op) {
		if (op.equals(BinaryOp.EQUAL) || op.equals(BinaryOp.NOTEQUAL)
				|| op.equals(BinaryOp.OR) || op.equals(BinaryOp.AND) || op.equals(BinaryOp.XOR)
				|| op.equals(BinaryOp.IMPLIES) || op.equals(BinaryOp.ARROW)) {
			return true;
		} else {
			return false;
		}
	}
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
