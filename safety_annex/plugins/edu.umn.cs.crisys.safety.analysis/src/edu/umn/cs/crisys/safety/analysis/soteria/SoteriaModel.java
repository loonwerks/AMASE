package edu.umn.cs.crisys.safety.analysis.soteria;

import java.util.ArrayList;

import edu.umn.cs.crisys.safety.analysis.ast.visitors.SoteriaAstVisitor;
import jkind.Assert;

public class SoteriaModel extends SoteriaAst {

	public SoteriaCompLib compLib;
	public final String includeStr = "#use \"top.ml\";;";
	public ArrayList<SoteriaCompInst> compInstList = new ArrayList<SoteriaCompInst>();
	public ArrayList<CompContractViolation> topLevelFaults = new ArrayList<CompContractViolation>();

	@Override
	public <T> T accept(SoteriaAstVisitor<T> visitor) {
		return visitor.visit(this);
	}

	public void createCompInst() {

	}

	public void addCompLib(SoteriaCompLib compLib) {
		Assert.isNotNull(compLib);
		this.compLib = compLib;
	}

	public void addTopLevelFault(CompContractViolation contractViolation) {
		topLevelFaults.add(contractViolation);
	}

}
