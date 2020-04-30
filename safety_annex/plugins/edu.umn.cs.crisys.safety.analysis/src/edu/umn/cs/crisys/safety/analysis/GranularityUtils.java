package edu.umn.cs.crisys.safety.analysis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.rockwellcollins.atc.agree.agree.BinaryExpr;
import com.rockwellcollins.atc.agree.agree.Expr;
import com.rockwellcollins.atc.agree.agree.GuaranteeStatement;
import com.rockwellcollins.atc.agree.agree.LemmaStatement;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeNode;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeNodeBuilder;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeStatement;

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
		unique = 0;
		return nb;
	}

	private static void transformStatement(AgreeStatement stmt, AgreeNodeBuilder nb) {
		if (stmt.reference instanceof GuaranteeStatement) {
			GuaranteeStatement oldGuar = (GuaranteeStatement) stmt;
			if (stmt.expr instanceof BinaryExpr) {
				Expr Rexpr = ((BinaryExpr) stmt).getRight();
			}
//			AgreeVar uniqueEq = new AgreeVar("unique"+unique, stmt.expr);
//			GuaranteeStatement newGuar = new GuaranteeStatement(oldGuar.d)
		} else if (stmt.reference instanceof LemmaStatement) {

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
