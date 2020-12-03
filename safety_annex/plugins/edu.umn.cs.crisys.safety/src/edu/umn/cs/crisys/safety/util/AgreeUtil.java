package edu.umn.cs.crisys.safety.util;

import java.util.List;

import com.rockwellcollins.atc.agree.analysis.ast.AgreeNode;

public class AgreeUtil {

	public static boolean inputsContainId(AgreeNode curNode, String idStr) {
		boolean containsId = false;
		List<String> inputs = Util.getIds(curNode.inputs);
		if (inputs.contains(idStr)) {
			containsId = true;
		}
		return containsId;
	}

	public static boolean outputsContainId(AgreeNode curNode, String idStr) {
		boolean containsId = false;
		List<String> outputs = Util.getIds(curNode.outputs);
		if (outputs.contains(idStr)) {
			containsId = true;
		}
		return containsId;
	}
}
