package edu.umn.cs.crisys.safety;

import java.util.ArrayList;
import java.util.List;

import com.rockwellcollins.atc.agree.analysis.ast.AgreeNode;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeProgram;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeStatement;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeVar;
import com.rockwellcollins.atc.agree.analysis.extentions.AgreeAutomater;

import jkind.lustre.Node;

public class TransformAgreeProgram implements AgreeAutomater{

	AgreeNode topNode;
	List<AgreeVar> ast_inputs = new ArrayList<>();
	List<AgreeStatement> ast_assertions = new ArrayList<>();
	
	@Override
	public AgreeProgram transform(AgreeProgram program) {
		
		// Reset any leftovers from previous runs
		ast_inputs.clear();
		ast_assertions.clear();

		// Make local copies of the final structures that we need to modify
		topNode = program.topNode;
		ast_inputs.addAll(topNode.inputs);
		ast_assertions.addAll(topNode.assertions);
				
		List<Node> lustreNodes = new ArrayList<>();
		lustreNodes.addAll(program.globalLustreNodes);
		
		
		return null;
	}

}
