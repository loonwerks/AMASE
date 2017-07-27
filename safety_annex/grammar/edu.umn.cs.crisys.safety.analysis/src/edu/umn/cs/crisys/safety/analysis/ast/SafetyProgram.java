package edu.umn.cs.crisys.safety.analysis.ast;
// For now, I just have this equivalent to AgreeProgram
// Will change later
import java.util.List;

import com.rockwellcollins.atc.agree.analysis.ast.AgreeNode;

import jkind.lustre.Node;
import jkind.lustre.Type;

public class SafetyProgram implements FaultASTElement{

	public final List<AgreeNode> agreeNodes;
	public final List<Node> globalLustreNodes;
	public final List<Type> globalTypes;
	public final AgreeNode topNode;

	public SafetyProgram(List<AgreeNode> agreeNodes, List<Node> globalLustreNodes, List<Type> globalTypes,
			AgreeNode topNode) {
		
		this.agreeNodes = jkind.util.Util.safeList(agreeNodes);
		this.globalLustreNodes = jkind.util.Util.safeList(globalLustreNodes);
		this.globalTypes = jkind.util.Util.safeList(globalTypes);
		this.topNode = topNode;
	}
}
