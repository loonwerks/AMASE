package edu.umn.cs.crisys.safety.analysis.soteria;

import java.util.ArrayList;

import jkind.Assert;

public class SoteriaFormula {
	public final String propertyName;
	public ArrayList<SoteriaFormulaElement> formulaBody = new ArrayList<SoteriaFormulaElement>();

	public SoteriaFormula(String propertyName, ArrayList<SoteriaFormulaElement> formulaBody) {
		Assert.isNotNull(propertyName);
		Assert.isNotNull(formulaBody);
		this.propertyName = propertyName;
		this.formulaBody = formulaBody;
	}
}
