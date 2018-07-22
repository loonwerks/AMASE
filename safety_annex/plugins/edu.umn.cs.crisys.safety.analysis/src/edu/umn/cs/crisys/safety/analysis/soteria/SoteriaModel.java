package edu.umn.cs.crisys.safety.analysis.soteria;

import java.util.ArrayList;

import edu.umn.cs.crisys.safety.analysis.ast.visitors.SoteriaAstVisitor;
import jkind.Assert;

public class SoteriaModel extends SoteriaAst {

	public SoteriaCompLib compLib;
	public final String includeStr = "#use \"top.ml\";;";
	public ArrayList<SoteriaCompInst> compInstList = new ArrayList<SoteriaCompInst>();
	public ArrayList<SoteriaCompConnection> connectionList = new ArrayList<SoteriaCompConnection>();
	public ArrayList<CompContractViolation> topLevelFaults = new ArrayList<CompContractViolation>();

	@Override
	public <T> T accept(SoteriaAstVisitor<T> visitor) {
		return visitor.visit(this);
	}

	public void createCompInst() {
		// go over all components in the compLib, and create component instance for each
		for (SoteriaComp comp : compLib.comps) {
			SoteriaCompInst curInst = new SoteriaCompInst(comp.componentName, comp.componentName);
			compInstList.add(curInst);
		}
	}

	public void createConnections() {
		for (SoteriaComp destComp : compLib.comps) {
			for (String destCompContract : destComp.inputFlows) {
				for (SoteriaComp srcComp : compLib.comps) {
					for (String srcCompContract : srcComp.outputFlows) {
						if (destCompContract.equals(srcCompContract)) {
							SoteriaCompConnection connection = new SoteriaCompConnection(srcComp.componentName,
									srcCompContract, destComp.componentName, destCompContract);
							connectionList.add(connection);
						}
					}
				}
			}
		}
	}

	public void addCompLib(SoteriaCompLib compLib) {
		Assert.isNotNull(compLib);
		this.compLib = compLib;
	}

	public void addTopLevelFault(CompContractViolation contractViolation) {
		topLevelFaults.add(contractViolation);
	}

}
