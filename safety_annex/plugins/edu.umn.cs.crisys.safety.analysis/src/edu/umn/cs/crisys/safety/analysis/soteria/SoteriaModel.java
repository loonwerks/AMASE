package edu.umn.cs.crisys.safety.analysis.soteria;

import edu.umn.cs.crisys.safety.analysis.ast.visitors.SoteriaAstVisitor;
import jkind.Assert;

public class SoteriaModel extends SoteriaAst {

	public final SoteriaCompLib soteriaCompLib;
	public final String includeStr = "#use \"top.ml\";;";

	public SoteriaModel(SoteriaCompLib compLib) {
		Assert.isNotNull(compLib);
		this.soteriaCompLib = compLib;
	}

	@Override
	public <T> T accept(SoteriaAstVisitor<T> visitor) {
		return visitor.visit(this);
	}

}
