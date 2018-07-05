package edu.umn.cs.crisys.safety.analysis.soteria;

import java.util.ArrayList;

import edu.umn.cs.crisys.safety.analysis.ast.visitors.SoteriaAstVisitor;
import jkind.Assert;

public class SoteriaFormulaSubgroup extends SoteriaAst {
	public ArrayList<SoteriaFormulaElem> formulaSubGroup = new ArrayList<SoteriaFormulaElem>();
	public final String propertyName;

	public SoteriaFormulaSubgroup(String propertyName) {
		Assert.isNotNull(propertyName);
		this.propertyName = propertyName;
	}

	public void addFormulaElem(SoteriaFormulaElem elem) {
		formulaSubGroup.add(elem);
	}

	@Override
	public <T> T accept(SoteriaAstVisitor<T> visitor) {
		return visitor.visit(this);
	}

}
