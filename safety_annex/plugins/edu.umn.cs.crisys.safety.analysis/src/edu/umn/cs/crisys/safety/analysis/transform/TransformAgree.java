package edu.umn.cs.crisys.safety.analysis.transform;

import java.util.ArrayList;
import java.util.List;

import com.rockwellcollins.atc.agree.analysis.ast.AgreeNode;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeProgram;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeStatement;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeVar;
import com.rockwellcollins.atc.agree.analysis.ast.visitors.AgreeASTMapVisitor;
import com.rockwellcollins.atc.agree.analysis.extentions.AgreeAutomater;

import edu.umn.cs.crisys.safety.analysis.ast.visitors.SafetyASTVisitor;
import edu.umn.cs.crisys.safety.analysis.handlers.AadlHandler;
import edu.umn.cs.crisys.safety.analysis.handlers.VerifyHandler;
import jkind.lustre.visitors.TypeMapVisitor;

public class TransformAgree implements AgreeAutomater {

	/*
	 * transform:
	 * @param AgreeProgram: this is the agree program that comes in from the extension point.
	 * @return AgreeProgram: this is either the unmodified original program, 
	 * or a transformed program (safety analysis)
	 * 
	 */
	@Override
	public AgreeProgram transform(AgreeProgram program) {
		
		// Local copies of the program components we will need to change and access
		AgreeNode topNode;
		List<AgreeVar> inputs = new ArrayList<>();
		List<AgreeVar> outputs = new ArrayList<>();
		
		// Visit program: 
		// For now, it prints out each node in the program
		// (input, guarantees, etc.)
		jkind.lustre.visitors.TypeMapVisitor lustreTypeMapVisitor = new TypeMapVisitor();
		SafetyASTVisitor visitor = new SafetyASTVisitor(lustreTypeMapVisitor);
		
		visitor.visit(program);
		
		System.out.println("Visited program");
		
		
		
		Boolean analysis = VerifyHandler.getAnalysisFlag();
		
		if(analysis == false){
			System.out.println("Analysis is false, returning original agree program");
		} else {
			System.out.println("Analysis is true, transforming original agree program");
			
			// Get the top node, inputs, and outputs
			topNode = program.topNode;
			
			// Reset from any previous runs
			inputs.clear();
			outputs.clear();
			
			// Populate inputs and outputs from topNode
			inputs.addAll(topNode.inputs);
			outputs.addAll(topNode.outputs);
			
			for(AgreeVar input:inputs){
				System.out.println("TRANSFORM PROGRAM: INPUTS __________________");
				System.out.println(input.toString());
			}
			
			
			
		}
		
		// Finally, reset transform flag to false
		VerifyHandler.setTransformFlagFalse();
		
		// And return program
		return program;
	}

}
