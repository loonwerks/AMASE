package edu.umn.cs.crisys.safety.util;

import static java.util.stream.Collectors.toList;

import java.util.List;

import com.rockwellcollins.atc.agree.analysis.ast.AgreeVar;

public class Util {

	public static List<String> getIds(List<AgreeVar> agreeVars) {
		return agreeVars.stream().map(agreeVar -> agreeVar.id).collect(toList());
	}
}
