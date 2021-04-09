package edu.umn.cs.crisys.safety.analysis.constraints.ast;

import java.util.HashMap;
import java.util.Map;

import edu.umn.cs.crisys.safety.analysis.constraints.visitors.ConstraintAstVisitor;

public class TermIntegerMapDef extends TermDef {

	public Map<Term, Integer> termMap = new HashMap<>();

	public TermIntegerMapDef(String id) {
		super(id);
	}

	public void addEntry(Term term, Integer val) {
		termMap.put(term, val);
	}

	@Override
	public <T> T accept(ConstraintAstVisitor<T> visitor) {
		return visitor.visit(this);
	}

}