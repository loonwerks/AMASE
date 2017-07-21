package edu.umn.cs.crisys.safety.analysis.ast;

import java.util.List;

public class FaultSubcomponent implements FaultASTElement{
//    public enum TimingModel {
//        SYNC, ASYNC, LATCHED
//    };

    public final String id;
    public final List<FaultVar> inputs;
    public final List<FaultVar> outputs;
    public final List<FaultDuration> duration;
    public final List<FaultTrigger> triggers;
    public final List<FaultEqStmt> eqStatement;
    

    public FaultSubcomponent(String id, List<FaultVar> inputs, List<FaultVar> outputs, 
    				List<FaultDuration> duration, List<FaultTrigger> triggers, List<FaultEqStmt> eqStatement) {
        this.id = id;
        this.inputs = jkind.util.Util.safeList(inputs);
        this.outputs = jkind.util.Util.safeList(outputs);
        this.duration = jkind.util.Util.safeList(duration);
        this.triggers = jkind.util.Util.safeList(triggers);
        this.eqStatement = jkind.util.Util.safeList(eqStatement);
    }
}
