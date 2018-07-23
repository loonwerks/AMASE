package edu.umn.cs.crisys.safety.analysis.soteria;

import edu.umn.cs.crisys.safety.analysis.ast.visitors.SoteriaAstVisitor;
import jkind.Assert;

public class SoteriaCompConnection extends SoteriaAst {

	public final String srcCompName;
	public final String srcCompContract;
	public final String destCompName;
	public final String destCompContract;

	public SoteriaCompConnection(String sourceCompName, String sourceCompContract, String destCompName,
			String destCompContract) {
		Assert.isNotNull(sourceCompName);
		Assert.isNotNull(sourceCompContract);
		Assert.isNotNull(destCompName);
		Assert.isNotNull(destCompContract);
		this.srcCompName = sourceCompName;
		this.srcCompContract = sourceCompContract;
		this.destCompName = destCompName;
		this.destCompContract = destCompContract;
	}

	@Override
	public <T> T accept(SoteriaAstVisitor<T> visitor) {
		return visitor.visit(this);
	}

}
