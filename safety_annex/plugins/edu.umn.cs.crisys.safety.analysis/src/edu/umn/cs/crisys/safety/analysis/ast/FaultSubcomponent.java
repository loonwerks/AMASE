package edu.umn.cs.crisys.safety.analysis.ast;

import java.util.List;

public class FaultSubcomponent implements SafetyASTElement{

    public final String id;
    public final List<SafetyVar> inputs;
    public final List<SafetyVar> outputs;
    public final List<SafetyDuration> duration;
    public final List<SafetyTrigger> triggers;
    public final List<SafetyEqStatement> eqStatement;
    

    public FaultSubcomponent(String id, List<SafetyVar> inputs, List<SafetyVar> outputs, 
    				List<SafetyDuration> duration, List<SafetyTrigger> triggers, List<SafetyEqStatement> eqStatement) {
        this.id = id;
        this.inputs = jkind.util.Util.safeList(inputs);
        this.outputs = jkind.util.Util.safeList(outputs);
        this.duration = jkind.util.Util.safeList(duration);
        this.triggers = jkind.util.Util.safeList(triggers);
        this.eqStatement = jkind.util.Util.safeList(eqStatement);
    }
}
