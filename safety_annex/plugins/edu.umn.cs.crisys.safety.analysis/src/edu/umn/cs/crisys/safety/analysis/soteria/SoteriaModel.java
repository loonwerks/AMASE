package edu.umn.cs.crisys.safety.analysis.soteria;

import edu.umn.cs.crisys.safety.analysis.ast.visitors.SoteriaAstVisitor;

public class SoteriaModel extends SoteriaAst {

	public SoteriaCompLib soteriaCompLib;
	public final String includeStr = "#use \"top.ml\";;";

	public SoteriaModel() {
		soteriaCompLib = new SoteriaCompLib();
	}

	@Override
	public <T> T accept(SoteriaAstVisitor<T> visitor) {
		return visitor.visit(this);
	}

}
