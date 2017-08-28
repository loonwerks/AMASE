package edu.umn.cs.crisys.safety.analysis.transform;

import com.rockwellcollins.atc.agree.analysis.ast.AgreeProgram;
import com.rockwellcollins.atc.agree.analysis.extentions.AgreeAutomater;

public class TransformAgree implements AgreeAutomater {

	@Override
	public AgreeProgram transform(AgreeProgram program) {

		System.out.println("In transform agree method");
		
		return program;
	}

}
