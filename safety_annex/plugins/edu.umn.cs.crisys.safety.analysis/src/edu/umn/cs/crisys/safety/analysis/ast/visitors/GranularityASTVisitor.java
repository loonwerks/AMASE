package edu.umn.cs.crisys.safety.analysis.ast.visitors;

import java.util.ArrayList;
import java.util.List;

import com.rockwellcollins.atc.agree.analysis.ast.AgreeNode;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeNodeBuilder;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeProgram;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeStatement;
import com.rockwellcollins.atc.agree.analysis.ast.visitors.AgreeASTMapVisitor;

import jkind.lustre.Expr;
import jkind.lustre.Node;

public class GranularityASTVisitor extends AgreeASTMapVisitor {

	// This is the list of Lustre nodes that are used by the safety analysis that
	// may not be part of the set of nodes used by AGREE
	private List<Node> globalLustreNodes;
	// Top of the node hierarchy in AGREE
	private AgreeNode topNode;

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
		String id = e.id;

		List<AgreeStatement> assumptions = new ArrayList<>();
		for (AgreeStatement stmt : e.assumptions) {
			assumptions.add(this.visit(stmt));
		}

		List<AgreeStatement> guarantees = new ArrayList<>();
		for (AgreeStatement stmt : e.guarantees) {
			guarantees.add(this.visit(stmt));
		}

		List<AgreeStatement> lemmas = new ArrayList<>();
		for (AgreeStatement stmt : e.lemmas) {
			lemmas.add(this.visit(stmt));
		}

		AgreeNodeBuilder builder = new AgreeNodeBuilder(id);
		builder.addInput(e.inputs);
		builder.addOutput(e.outputs);
		builder.addLocal(e.locals);
		builder.addConnection(e.connections);
		builder.addSubNode(e.subNodes);
		builder.addAssertion(e.assertions);
		builder.addAssumption(assumptions);
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

		AgreeNode result = builder.build();
		visitedNodes.put(e.compInst, result);

		return result;
	}

	@Override
	public AgreeStatement visit(AgreeStatement agreeStmt) {
		// Rewrite each statement in order to uninline nested structures
		Expr ex = agreeStmt.expr;
		return agreeStmt;
	}
}
