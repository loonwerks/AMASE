package edu.umn.cs.crisys.safety.analysis.constraints.ast;

import java.util.HashMap;
import java.util.Map;

import edu.umn.cs.crisys.safety.analysis.constraints.visitors.ConstraintAstVisitor;

public class StringStringMapDef extends TermDef {

	public Map<String, String> stringMap = new HashMap<>();

	public StringStringMapDef(String id) {
		super(id);
	}

	public void addEntry(String srcString, String destString) {
		stringMap.put(srcString, destString);
	}

	@Override
	public <T> T accept(ConstraintAstVisitor<T> visitor) {
		return visitor.visit(this);
	}

}