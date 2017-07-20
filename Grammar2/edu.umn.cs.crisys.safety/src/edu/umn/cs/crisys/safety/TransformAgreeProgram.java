package edu.umn.cs.crisys.safety;

import java.util.List;

import com.rockwellcollins.atc.agree.analysis.ast.AgreeNode;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeProgram;
import com.rockwellcollins.atc.agree.analysis.extentions.AgreeAutomater;

import edu.umn.cs.crisys.safety.safety.InputStatement;
import jkind.lustre.Node;
import jkind.lustre.Type;

public class TransformAgreeProgram implements AgreeAutomater{

	AgreeNode topNode;
	List<AgreeNode> agreeNodes;
	List<Type> globalTypes;
	List<Node> lustreNodes;
	
	@Override
	public AgreeProgram transform(AgreeProgram program) {
		
		topNode = program.topNode;
		agreeNodes = program.agreeNodes;
		
		
		// for each node in program: 
		//   connect to safety annex info?
		
		
		return program;
	}
	
	
	
	

}
