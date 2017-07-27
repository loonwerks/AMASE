package edu.umn.cs.crisys.safety.analysis.extensions;

import edu.umn.cs.crisys.safety.analysis.ast.SafetyProgram;

public interface SafetyAutomater {

	public SafetyProgram transform(SafetyProgram program);
}
