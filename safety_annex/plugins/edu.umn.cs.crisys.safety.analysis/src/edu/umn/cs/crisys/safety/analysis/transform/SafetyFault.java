package edu.umn.cs.crisys.safety.analysis.transform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.rockwellcollins.atc.agree.analysis.ast.AgreeEquation;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeNode;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeProgram;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeVar;

import edu.umn.cs.crisys.safety.safety.DurationStatement;
import edu.umn.cs.crisys.safety.safety.FaultStatement;
import edu.umn.cs.crisys.safety.safety.TriggerStatement;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.Node;

public class SafetyFault {

	// Map from fault node output names to the assigned vars
	public Map<String, IdExpr> faultNodeOutputMap = new HashMap<>();
	public Map<String, Expr> inputParameterMap = new HashMap<>();
	
	public Node faultNode; 
	public String id;
	public String explanitoryText;
	
	// Some information about the fault
	public DurationStatement duration = null;
	public List<AgreeEquation> safetyEqs = new ArrayList<>();
	public List<TriggerStatement> triggers = new ArrayList<>();
	
	// initial data: 
	public FaultStatement faultStatement; 
	public AgreeProgram agreeProgram;
	public AgreeNode agreeNode;

	public SafetyFault(FaultStatement fstmt, 
			AgreeProgram program, 
			AgreeNode node) {
		this.faultStatement = fstmt;
		this.agreeNode = node;
		this.agreeProgram = program;
	}

}
