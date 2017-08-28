package edu.umn.cs.crisys.safety.analysis.ast.visitors;

import java.util.ArrayList;
import java.util.List;

import com.rockwellcollins.atc.agree.analysis.ast.AgreeConnection;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeEquation;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeNode;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeStatement;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeVar;
import com.rockwellcollins.atc.agree.analysis.ast.visitors.AgreeASTMapVisitor;
import jkind.lustre.visitors.TypeMapVisitor;

public class SafetyASTVisitor extends AgreeASTMapVisitor {

	public SafetyASTVisitor(TypeMapVisitor lustreTypeMapVisitor) {
		super(lustreTypeMapVisitor);
		// TODO Auto-generated constructor stub
	}


	@Override
	public AgreeNode visit(AgreeNode e){
		
		printNode(e);
		
		
		
		return e;
	}

	
	/*
	 * printNode:
	 * @param AgreeNode
	 * Prints the parts of the agree node to the console (input, output, etc.)
	 * 
	 */
	private void printNode(AgreeNode e){

		// Print out of inputs
		List<AgreeVar> inputs = new ArrayList<>();
		for (AgreeVar input : e.inputs) {
			inputs.add(this.visit(input));
			System.out.println("Inputs:\n");
			System.out.println(input.toString());
		}
		
		// Print out the outputs
		List<AgreeVar> outputs = new ArrayList<>();
		for (AgreeVar output : e.outputs) {
			outputs.add(this.visit(output));
			System.out.println("\n\nOutputs:\n");
			System.out.println(output.toString());
		}

		List<AgreeVar> locals = new ArrayList<>();
		for (AgreeVar local : e.locals) {
			locals.add(this.visit(local));
			System.out.println("\n\nLocals:\n");
			System.out.println(local.toString());
		}
		
		// Note that nodes and connections contain cross-references to each
		// other. But, valid model structure requires that connections
		// refer only to features on the this node and the sub-nodes. Thus,
		// we may visit the sub-nodes first, and then use the result of that
		// in visiting the connections.
		//
		// These are the names of the subcomponents (toString gives names)
		List<AgreeNode> subNodes = new ArrayList<>();
		for (AgreeNode subnode : e.subNodes) {
			subNodes.add(this.visit(subnode));
			System.out.println("\n\nSubnodes:\n");
			System.out.println(subnode.toString());
		}

		List<AgreeConnection> connections = new ArrayList<>();
		for (AgreeConnection conn : e.connections) {
			connections.add(this.visit(conn));
			System.out.println("\n\nConnections:\n");
			System.out.println(conn.toString());
		}

		List<AgreeStatement> assertions = new ArrayList<>();
		for (AgreeStatement stmt : e.assertions) {
			assertions.add(this.visit(stmt));
			System.out.println("\n\nAssertions:\n");
			System.out.println(stmt.toString());
		}

		List<AgreeStatement> assumptions = new ArrayList<>();
		for (AgreeStatement stmt : e.assumptions) {
			assumptions.add(this.visit(stmt));
			System.out.println("\n\nAssumptions:\n");
			System.out.println(stmt.toString());
		}

		List<AgreeStatement> guarantees = new ArrayList<>();
		for (AgreeStatement stmt : e.guarantees) {
			guarantees.add(this.visit(stmt));
			System.out.println("\n\nGuarantees:\n");
			System.out.println(stmt.toString());
		}

		List<AgreeStatement> lemmas = new ArrayList<>();
		for (AgreeStatement stmt : e.lemmas) {
			lemmas.add(this.visit(stmt));
			System.out.println("\n\nLemmas:\n");
			System.out.println(stmt.toString());
		}
				
		List<AgreeStatement> patternProps = new ArrayList<>();
		for(AgreeStatement stmt : e.patternProps){
			patternProps.add(this.visit(stmt));
			System.out.println("\n\nPattern Props:\n");
			System.out.println(stmt.toString());
		}
				
		List<AgreeEquation> localEqs = new ArrayList<>();
		for(AgreeEquation eq : e.localEquations){
			localEqs.add(this.visit(eq));
			System.out.println("\n\nLocal Equations:\n");
			System.out.println(eq.toString());
		}
		
		
	}
}
