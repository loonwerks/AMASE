package edu.umn.cs.crisys.safety.analysis.transform;

import com.rockwellcollins.atc.agree.analysis.ast.AgreeProgram;
import com.rockwellcollins.atc.agree.analysis.ast.visitors.AgreeASTMapVisitor;
import com.rockwellcollins.atc.agree.analysis.extentions.AgreeAutomater;

import edu.umn.cs.crisys.safety.analysis.ast.visitors.SafetyASTVisitor;
import edu.umn.cs.crisys.safety.analysis.handlers.AadlHandler;
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
		
		
		// Visit program: 
		// For now, it prints out each node in the program
		// (input, guarantees, etc.)
		jkind.lustre.visitors.TypeMapVisitor lustreTypeMapVisitor = new TypeMapVisitor();
		SafetyASTVisitor visitor = new SafetyASTVisitor(lustreTypeMapVisitor);
		
		visitor.visit(program);
		
		System.out.println("Visited program");
		
		// For each component/subcomponent, make sure that if there is a 
		// safety annex, there is also an agree annex. 
		// If not, output error and exit analysis. 
		// This should probably be done in by the visitor since it has
		// access to all the nodes. 
		
		
		
		Boolean analysis = AadlHandler.getAnalysisFlag();
		
		if(analysis == false){
			System.out.println("Analysis is false, returning original agree program");
		} else {
			System.out.println("Analysis is true, transforming original agree program");
		}
		
		return program;
	}

}
