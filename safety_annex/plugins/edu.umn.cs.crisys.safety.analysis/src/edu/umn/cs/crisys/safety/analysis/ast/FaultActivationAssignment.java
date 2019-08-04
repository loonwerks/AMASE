package edu.umn.cs.crisys.safety.analysis.ast;

import edu.umn.cs.crisys.safety.analysis.transform.BaseFault;
import jkind.Assert;

public class FaultActivationAssignment implements SafetyASTElement{

	public final String agreeBoolVarName;
	public final BaseFault faultActivated;
	public final String faultCompName;

	public FaultActivationAssignment(String agreeBoolVarName, BaseFault faultActivated, String faultCompName) {
		Assert.isNotNull(agreeBoolVarName);
		Assert.isNotNull(faultActivated);
		Assert.isNotNull(faultCompName);
		this.agreeBoolVarName = agreeBoolVarName;
		this.faultActivated = faultActivated;
		this.faultCompName = faultCompName;

	}

}
