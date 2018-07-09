package edu.umn.cs.crisys.safety.analysis.soteria;

import edu.umn.cs.crisys.safety.analysis.ast.visitors.SoteriaAstVisitor;
import jkind.Assert;

public class SoteriaModel extends SoteriaAst {

	public SoteriaCompLib compLib;
	public final String includeStr = "#use \"top.ml\";;";

	public SoteriaModel(SoteriaCompLib compLib) {
		Assert.isNotNull(compLib);
		this.compLib = compLib;
	}

	@Override
	public <T> T accept(SoteriaAstVisitor<T> visitor) {
		return visitor.visit(this);
	}

}
