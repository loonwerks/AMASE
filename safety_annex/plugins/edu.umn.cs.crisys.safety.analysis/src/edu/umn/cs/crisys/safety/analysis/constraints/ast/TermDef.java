package edu.umn.cs.crisys.safety.analysis.constraints.ast;

import jkind.Assert;

public abstract class TermDef extends MistralConstraint {
	public final String termId;

	public TermDef(String termId) {
		Assert.isNotNull(termId);
		this.termId = termId;
	}
}