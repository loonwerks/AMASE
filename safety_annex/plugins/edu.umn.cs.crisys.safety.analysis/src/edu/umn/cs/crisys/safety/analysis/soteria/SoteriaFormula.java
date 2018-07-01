package edu.umn.cs.crisys.safety.analysis.soteria;

import java.util.ArrayList;

import jkind.Assert;

public class SoteriaFormula {
	public final String propertyName;
	public ArrayList<SoteriaFormulaElem> formulaBody = new ArrayList<SoteriaFormulaElem>();

	public SoteriaFormula(String propertyName, ArrayList<SoteriaFormulaElem> formulaBody) {
		Assert.isNotNull(propertyName);
		Assert.isNotNull(formulaBody);
		this.propertyName = propertyName;
		this.formulaBody = formulaBody;
	}
}
