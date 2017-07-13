package edu.umn.cs.crisys.safety;

import java.util.ArrayList;
import java.util.List;

import com.rockwellcollins.atc.agree.analysis.ast.AgreeNode;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeProgram;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeStatement;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeVar;
import com.rockwellcollins.atc.agree.analysis.extentions.AgreeAutomater;

import jkind.lustre.Node;
import jkind.lustre.Type;

public class TransformAgreeProgram implements AgreeAutomater{

	AgreeNode topNode;
	List<AgreeNode> agreeNodes;
	List<Type> globalTypes;
	List<Node> lustreNodes;
	
	@Override
	public AgreeProgram transform(AgreeProgram program) {
		
		
		
		
		return null;
	}

}
