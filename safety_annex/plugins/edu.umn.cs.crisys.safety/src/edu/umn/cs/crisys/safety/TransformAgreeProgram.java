package edu.umn.cs.crisys.safety;

import java.util.List;

import com.rockwellcollins.atc.agree.analysis.AgreeRenaming;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeNode;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeProgram;
import com.rockwellcollins.atc.agree.analysis.extentions.AgreeAutomater;

import jkind.api.results.AnalysisResult;
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

	@Override
	public AgreeRenaming rename(AgreeRenaming renaming) {
		// TODO Auto-generated method stub
		return renaming;
	}

	@Override
	public AnalysisResult transformResult(AnalysisResult res) {
		// TODO Auto-generated method stub
		return res;
	}
	
	
	
	

}
