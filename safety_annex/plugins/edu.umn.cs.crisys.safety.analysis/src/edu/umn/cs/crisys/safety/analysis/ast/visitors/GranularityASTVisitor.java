package edu.umn.cs.crisys.safety.analysis.ast.visitors;

import java.util.ArrayList;
import java.util.List;

import com.rockwellcollins.atc.agree.analysis.ast.AgreeNode;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeProgram;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeStatement;
import com.rockwellcollins.atc.agree.analysis.ast.visitors.AgreeASTMapVisitor;

import jkind.lustre.Node;
import jkind.lustre.visitors.TypeMapVisitor;

public class GranularityASTVisitor extends AgreeASTMapVisitor {

	// This is the list of Lustre nodes that are used by the safety analysis that
	// may not be part of the set of nodes used by AGREE
	private List<Node> globalLustreNodes;
	// Top of the node hierarchy in AGREE
	private AgreeNode topNode;

	/**
	 * Call to super class.
	 */
	public GranularityASTVisitor(TypeMapVisitor lustreTypeMapVisitor) {
		super(lustreTypeMapVisitor);
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
	public AgreeNode visit(AgreeNode node) {
		boolean isTop = (node == this.topNode);
		return node;
	}

	@Override
	public AgreeStatement visit(AgreeStatement agreeStmt) {
		return agreeStmt;
	}
}
