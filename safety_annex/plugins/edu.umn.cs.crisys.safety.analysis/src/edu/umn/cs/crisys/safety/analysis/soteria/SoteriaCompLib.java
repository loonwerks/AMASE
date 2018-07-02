package edu.umn.cs.crisys.safety.analysis.soteria;

import java.util.ArrayList;
import java.util.List;

import edu.umn.cs.crisys.safety.analysis.ast.visitors.SoteriaAstVisitor;

public class SoteriaCompLib extends SoteriaAst {
	public List<SoteriaComp> comps = new ArrayList<SoteriaComp>();
	public final String commentStr = "(* ----- COMPONENT LIBRARY ----- *)";
	public final String compLibName = "comp_library";

	public SoteriaCompLib() {
	}

	public void addComp(SoteriaComp comp) {
		comps.add(comp);
	}

	public void reset() {
		comps.clear();
	}

	@Override
	public <T> T accept(SoteriaAstVisitor<T> visitor) {
		return visitor.visit(this);
	}

}
