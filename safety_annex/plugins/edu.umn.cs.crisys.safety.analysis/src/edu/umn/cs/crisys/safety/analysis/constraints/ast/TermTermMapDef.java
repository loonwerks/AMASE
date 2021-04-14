package edu.umn.cs.crisys.safety.analysis.constraints.ast;

import java.util.HashMap;
import java.util.Map;

import edu.umn.cs.crisys.safety.analysis.constraints.visitors.ConstraintAstVisitor;

public class TermTermMapDef extends TermDef {

	public Map<Term, Term> termMap = new HashMap<>();

	public TermTermMapDef(String id) {
		super(id);
	}

	public void addEntry(Term srcTerm, Term destTerm) {
		termMap.put(srcTerm, destTerm);
	}

	@Override
	public <T> T accept(ConstraintAstVisitor<T> visitor) {
		return visitor.visit(this);
	}

}