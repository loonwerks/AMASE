package edu.umn.cs.crisys.safety.analysis.soteria;

import java.util.ArrayList;

import edu.umn.cs.crisys.safety.analysis.ast.visitors.SoteriaAstVisitor;
import jkind.Assert;

public class SoteriaFormula extends SoteriaAst {
	public final String propertyName;
	public ArrayList<SoteriaFormulaElem> formulaBody = new ArrayList<SoteriaFormulaElem>();

	public SoteriaFormula(String propertyName, ArrayList<SoteriaFormulaElem> formulaBody) {
		Assert.isNotNull(propertyName);
		Assert.isNotNull(formulaBody);
		this.propertyName = propertyName;
		this.formulaBody = formulaBody;
	}

	@Override
	public <T> T accept(SoteriaAstVisitor<T> visitor) {
		return visitor.visit(this);
	}
}
