package edu.umn.cs.crisys.safety.analsyis.linker;

import jkind.api.results.AnalysisResult;

import java.util.HashMap;
import java.util.Map;

import com.rockwellcollins.atc.agree.analysis.ast.AgreeProgram;
import com.rockwellcollins.atc.agree.analysis.views.AgreeResultsLinker;

public class AnalysisProgramLinker extends AgreeResultsLinker {
	
	// Associate a jkind analysis result with an agree program
	// by use of hash maps (result=key, program=value)
	
	private Map<AnalysisResult, AgreeProgram> agreePrograms = new HashMap<>();

    public Map<AnalysisResult, AgreeProgram> getAgreePrograms() { return agreePrograms; }

    public AgreeProgram getAgreeProgram(AnalysisResult result) {
		return agreePrograms.get(result);
	}

	public void setAgreeProgram(AnalysisResult result, AgreeProgram agreeProgram) {
		agreePrograms.put(result, agreeProgram);
	}
}
