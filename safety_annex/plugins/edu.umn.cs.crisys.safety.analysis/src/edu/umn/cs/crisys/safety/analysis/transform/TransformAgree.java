package edu.umn.cs.crisys.safety.analysis.transform;

import com.rockwellcollins.atc.agree.analysis.ast.AgreeProgram;
import com.rockwellcollins.atc.agree.analysis.extentions.AgreeAutomater;

import edu.umn.cs.crisys.safety.analysis.handlers.AadlHandler;

public class TransformAgree implements AgreeAutomater {

	@Override
	public AgreeProgram transform(AgreeProgram program) {

		System.out.println("In transform agree method");
		
		Boolean analysis = AadlHandler.getAnalysisFlag();
		
		if(analysis == false){
			System.out.println("Analysis is false, returning original agree program");
		} else {
			System.out.println("Analysis is true, transforming original agree program");
		}
		
		return program;
	}

}
