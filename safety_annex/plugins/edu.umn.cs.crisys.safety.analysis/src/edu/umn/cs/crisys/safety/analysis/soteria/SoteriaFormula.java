package edu.umn.cs.crisys.safety.analysis.soteria;

import java.util.ArrayList;

import edu.umn.cs.crisys.safety.analysis.ast.visitors.SoteriaAstVisitor;
import jkind.Assert;

public class SoteriaFormula extends SoteriaAst {
	public final String propertyName;
	public final String propertyFaultString = "contract violation";
	public ArrayList<SoteriaFormulaSubgroup> formulaBody = new ArrayList<SoteriaFormulaSubgroup>();

	public SoteriaFormula(String propertyName) {
		Assert.isNotNull(propertyName);
		this.propertyName = propertyName;
	}

	public void addFormulaSubgroup(SoteriaFormulaSubgroup SoteriaFormulaSubgroup) {
		formulaBody.add(SoteriaFormulaSubgroup);
	}

	@Override
	public <T> T accept(SoteriaAstVisitor<T> visitor) {
		return visitor.visit(this);
	}
}
