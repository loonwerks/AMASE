package edu.umn.cs.crisys.safety.analysis.soteria;

import java.util.ArrayList;
import java.util.List;

import edu.umn.cs.crisys.safety.analysis.ast.visitors.SoteriaAstVisitor;
import jkind.Assert;

public class SoteriaCompInst extends SoteriaAst {

	public final String instanceName;
	public final String componentName;

	public List<String> ports = new ArrayList<>();

	public SoteriaCompInst(String instanceName, String componentName) {
		Assert.isNotNull(instanceName);
		Assert.isNotNull(componentName);
		this.instanceName = instanceName;
		this.componentName = componentName;
	}

	@Override
	public <T> T accept(SoteriaAstVisitor<T> visitor) {
		return visitor.visit(this);
	}

}
