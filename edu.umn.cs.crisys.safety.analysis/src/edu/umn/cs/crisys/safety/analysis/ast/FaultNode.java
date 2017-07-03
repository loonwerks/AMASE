package edu.umn.cs.crisys.safety.analysis.ast;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.umn.cs.crisys.safety.analysis.ast.*;

//import jkind.lustre.Expr;

public class FaultNode implements FaultASTElement{
//    public enum TimingModel {
//        SYNC, ASYNC, LATCHED
//    };

    public final String id;
    public final List<FaultInput> inputs;
    public final List<FaultOutput> outputs;
    public final List<FaultDuration> duration;
    public final List<FaultTrigger> triggers;
    

    public FaultNode(String id, List<FaultInput> inputs, List<FaultOutput> outputs, 
    				List<FaultDuration> duration, List<FaultTrigger> triggers) {
        this.id = id;
        //this.inputs = jkind.util.Util.safeList(inputs);
        this.inputs = inputs;
        this.outputs = outputs;
        this.duration = duration;
        this.triggers = triggers;
    }
}
