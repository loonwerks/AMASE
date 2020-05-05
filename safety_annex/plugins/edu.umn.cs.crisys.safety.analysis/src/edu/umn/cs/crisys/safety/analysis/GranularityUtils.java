package edu.umn.cs.crisys.safety.analysis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

import com.rockwellcollins.atc.agree.agree.AgreeFactory;
import com.rockwellcollins.atc.agree.agree.BinaryExpr;
import com.rockwellcollins.atc.agree.agree.Expr;
import com.rockwellcollins.atc.agree.agree.GuaranteeStatement;
import com.rockwellcollins.atc.agree.agree.LemmaStatement;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeNode;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeNodeBuilder;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeStatement;

import jkind.lustre.BinaryOp;
import jkind.lustre.NamedType;

public class GranularityUtils {

	private static int unique = 0;
	private static List<String> boolOps = new ArrayList<String>();
	private static List<String> intOps = new ArrayList<String>();
	private static List<String> numOps = new ArrayList<String>();

	public static AgreeNodeBuilder decomposeNodeContracts(AgreeNode node, AgreeNodeBuilder nb) {
		initLists();
		for (AgreeStatement stmt : node.guarantees) {
			transformStatement(stmt, nb);
		}
		for (AgreeStatement stmt : node.lemmas) {
			transformStatement(stmt, nb);
		}
//		unique = 0;
		return nb;
	}

	private static void transformStatement(AgreeStatement stmt, AgreeNodeBuilder nb) {
		if (stmt.reference instanceof GuaranteeStatement) {
//			GuaranteeStatement oldGuar = (GuaranteeStatement) stmt;
			nb.addGuarantee(splitGuaranteeAnd(stmt));
//			AgreeVar uniqueEq = new AgreeVar("unique"+unique, stmt.expr);
//			GuaranteeStatement newGuar = new GuaranteeStatement(oldGuar.d)
		} else if (stmt.reference instanceof LemmaStatement) {

		}
	}

	private static List<AgreeStatement> splitGuaranteeAnd(AgreeStatement agreeStmt) {
		List<AgreeStatement> newStmts = new ArrayList<AgreeStatement>();

		// Check for top level operator 'AND'
		// Create two new guarantees, one for each operand.

		if (agreeStmt.expr instanceof jkind.lustre.BinaryExpr) {
			jkind.lustre.BinaryExpr binExpr = (jkind.lustre.BinaryExpr) agreeStmt.expr;
			if (binExpr.op == BinaryOp.AND) {
				recurseAnd(agreeStmt.expr, newStmts, agreeStmt.string, agreeStmt.reference);

//				String newDescrL = unique + " LEFT %% " + agreeStmt.string;
//				String newDescrR = unique + " RIGHT %% " + agreeStmt.string;
//				unique++;
//				AgreeStatement guarL = new AgreeStatement(newDescrL, binExpr.left,
//						agreeStmt.reference);
//				AgreeStatement guarR = new AgreeStatement(newDescrR, binExpr.right,
//						agreeStmt.reference);
//				newStmts.add(guarR);
//				newStmts.add(guarL);

			} else if (binExpr.op == BinaryOp.ARROW) {
				if (binExpr.right instanceof jkind.lustre.BinaryExpr) {
					jkind.lustre.BinaryExpr binR = (jkind.lustre.BinaryExpr) binExpr.right;
					if (binR.op == BinaryOp.AND) {
						recurseAnd(binExpr.right, newStmts, agreeStmt.string, agreeStmt.reference);
					}
				}
			}
		}

		return newStmts;
	}

	private static void recurseAnd(jkind.lustre.Expr expr, List<AgreeStatement> newStmts, String agreeStmtString,
			EObject ref) {
		if (expr instanceof jkind.lustre.BinaryExpr) {
			jkind.lustre.BinaryExpr binExpr = (jkind.lustre.BinaryExpr) expr;
			String newDescrL = unique + " LEFT %% ";
			String newDescrR = unique + " RIGHT %% ";
			unique++;
			GuaranteeStatement gsL = AgreeFactory.eINSTANCE.createGuaranteeStatement();
//			gsL.setExpr((Expr) binExpr.left);
			gsL.setStr(newDescrL);
			GuaranteeStatement gsR = AgreeFactory.eINSTANCE.createGuaranteeStatement();
//			gsR.setExpr((Expr) binExpr.right);
			gsR.setStr(newDescrR);
			AgreeStatement guarL = new AgreeStatement(newDescrL, binExpr.left, gsL);
			AgreeStatement guarR = new AgreeStatement(newDescrR, binExpr.right, gsR);
			newStmts.add(guarR);
			newStmts.add(guarL);
			if (binExpr.left instanceof jkind.lustre.BinaryExpr) {
				if (((jkind.lustre.BinaryExpr) binExpr.left).op == BinaryOp.AND) {
					recurseAnd(binExpr.left, newStmts, agreeStmtString, ref);
				}
			}
		}
	}

	private NamedType getTypeOfExpr(Expr expr) {
		NamedType type = null;
		if (expr instanceof BinaryExpr) {
			BinaryExpr binEx = (BinaryExpr) expr;
			if (boolOps.contains(binEx.getOp())) {
				return new NamedType("bool");
			} else if (intOps.contains(binEx.getOp())) {
				return new NamedType("int");
			} else {
				return getDeeperType(expr);
			}
		}
		return type;
	}

	private NamedType getDeeperType(Expr expr) {

		return null;
	}

	private static void initLists() {
		boolOps.addAll(
				Arrays.asList("and", "or", "xor", "->", "=", ">", ">=", "<", "<=", "<>", "=>"));

		intOps.addAll(Arrays.asList("div", "mod"));

		numOps.addAll(Arrays.asList("/", "*", "+", "-"));
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
