package edu.umn.cs.crisys.safety.analysis.ast.visitors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

import com.rockwellcollins.atc.agree.agree.GuaranteeStatement;
import com.rockwellcollins.atc.agree.agree.LemmaStatement;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeNode;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeProgram;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeStatement;
import com.rockwellcollins.atc.agree.analysis.ast.visitors.AgreeASTMapVisitor;

import edu.umn.cs.crisys.safety.analysis.ast.SafetyNodeBuilder;
import jkind.lustre.BinaryExpr;
import jkind.lustre.BinaryOp;
import jkind.lustre.Expr;
import jkind.lustre.Node;

public class GranularityASTVisitor extends AgreeASTMapVisitor {

	// This is the list of Lustre nodes that are used by the safety analysis that
	// may not be part of the set of nodes used by AGREE
	private List<Node> globalLustreNodes;
	// Top of the node hierarchy in AGREE
	private AgreeNode topNode;
	// Counter for unique ids
	static int unique = 0;
	// Mapping from node to a list of AgreeStatements
	// Each Eq/Lemma/Guarantee can be split into more - need
	// to add all to the new node during visit operation.
	private HashMap<String, List<AgreeStatement>> mapNodeToAgreeStatements = new HashMap<String, List<AgreeStatement>>();

	/**
	 * Call to super class.
	 */
	public GranularityASTVisitor() {
		super(new jkind.lustre.visitors.TypeMapVisitor());
	}

	@Override
	public AgreeProgram visit(AgreeProgram program) {
		globalLustreNodes = new ArrayList<>(program.globalLustreNodes);
		this.topNode = program.topNode;
		AgreeNode topNode = this.visit(program.topNode);
		program = new AgreeProgram(program.agreeNodes, globalLustreNodes, program.globalTypes, topNode,
				program.containsRealTimePatterns);
		return program;
	}

	@Override
	public AgreeNode visit(AgreeNode e) {
		boolean isTop = (e == this.topNode);
		if (!isTop) {
			System.out.println();
		}
		// Testing: how to reach lowest level nodes
		// This should traverse through the child nodes.
		e = super.visit(e);

		String id = e.id;

		// Need to get the generated list of all statements
		// Each stmt can produce multiple new ones -
		// the visit method creates that list and saves it in
		// the mapNodeToAgreeStatements. After visit is called
		// on all particular statements in the node,
		// the associated list in the map can be added based
		// on type of statement.
		List<AgreeStatement> eList = new ArrayList<>();

		for (AgreeStatement stmt : e.guarantees) {
//			this.visit(stmt);
			eList.addAll(transformStatements(stmt));
		}
		for (AgreeStatement stmt : e.lemmas) {
			eList.addAll(transformStatements(stmt));
		}

		mapNodeToAgreeStatements.put(e.id, eList);

		List<AgreeStatement> guarantees = new ArrayList<>();
		List<AgreeStatement> lemmas = new ArrayList<>();
		for (String key : mapNodeToAgreeStatements.keySet()) {
			List<AgreeStatement> list = mapNodeToAgreeStatements.get(key);
			for (AgreeStatement stmt : list) {
				if (stmt.reference instanceof GuaranteeStatement) {
					guarantees.add(stmt);
				} else if (stmt.reference instanceof LemmaStatement) {
					lemmas.add(stmt);
				}
			}
		}

		// THE ORIGINAL CODE BELOW
		//////////////////////////////////////////////////////////////
//		List<AgreeStatement> guarantees = new ArrayList<>();
//		for (AgreeStatement stmt : e.guarantees) {
//			guarantees.add(this.visit(stmt));
//		}
//
//		List<AgreeStatement> lemmas = new ArrayList<>();
//		for (AgreeStatement stmt : e.lemmas) {
//			lemmas.add(this.visit(stmt));
//		}

//		AgreeNodeBuilder builder = new AgreeNodeBuilder(id);
		SafetyNodeBuilder builder = new SafetyNodeBuilder(id);
		builder.addInput(e.inputs);
		builder.addOutput(e.outputs);
		builder.addLocal(e.locals);
		builder.addConnection(e.connections);
		builder.addSubNode(e.subNodes);
		builder.addAssertion(e.assertions);
		builder.addAssumption(e.assumptions);
		builder.addGuarantee(guarantees);
		builder.addLemma(lemmas);
		builder.addLocalEquation(e.localEquations);
		builder.addPatternProp(e.patternProps);
		builder.setClockConstraint(e.clockConstraint);
		builder.setInitialConstraint(e.initialConstraint);
		builder.setClockVar(e.clockVar);
		builder.setReference(e.reference);
		builder.setTiming(e.timing);
		builder.setCompInst(e.compInst);
		builder.addTimeFall(e.timeFallMap);
		builder.addTimeRise(e.timeRiseMap);
		builder.addTimeOf(e.timeOfMap);
		builder.addIvcElements(e.ivcElements);
		builder.setFaultTreeFlag(true);

//		AgreeNode result = builder.build();
		// visitedNodes.put(e.compInst, result);

		return builder.build();
	}

	@Override
	public AgreeStatement visit(AgreeStatement e) {
		String string = e.string;
		Expr expr = e.expr.accept(this);
		EObject reference = e.reference;

		return new AgreeStatement(string, expr, reference);
	}

	private List<AgreeStatement> transformStatements(AgreeStatement agreeStmt) {
		// Rewrite each statement in order to uninline nested structures
		// Initially, this will only look for the top level operator
		// and if it is AND on a guarantee or lemma, create two stmts.
		List<AgreeStatement> listStmts = new ArrayList<AgreeStatement>();

		if (agreeStmt.reference instanceof LemmaStatement) {
			listStmts.addAll(splitLemmaAnd(agreeStmt));
		} else if (agreeStmt.reference instanceof GuaranteeStatement) {
			listStmts.addAll(splitGuaranteeAnd(agreeStmt));
		}

		return listStmts;
	}

	private List<AgreeStatement> splitGuaranteeAnd(AgreeStatement agreeStmt) {
		List<AgreeStatement> newStmts = new ArrayList<AgreeStatement>();

		// Check for top level operator 'AND'
		// Create two new guarantees, one for each operand.
		Expr expr = agreeStmt.expr;
		if (expr instanceof BinaryExpr) {
			BinaryExpr binExpr = (BinaryExpr) expr;
			if (binExpr.op == BinaryOp.AND) {
				String newDescrL = unique + " LEFT %% " + agreeStmt.string;
				String newDescrR = unique + " RIGHT %% " + agreeStmt.string;
				unique++;
				AgreeStatement guarL = new AgreeStatement(newDescrL, binExpr.left, agreeStmt.reference);
				AgreeStatement guarR = new AgreeStatement(newDescrR, binExpr.right, agreeStmt.reference);
				newStmts.add(guarR);
				newStmts.add(guarL);

			}
		}

		return newStmts;
	}

	private List<AgreeStatement> splitLemmaAnd(AgreeStatement agreeStmt) {
		List<AgreeStatement> newStmts = new ArrayList<AgreeStatement>();

		return newStmts;
	}
}
