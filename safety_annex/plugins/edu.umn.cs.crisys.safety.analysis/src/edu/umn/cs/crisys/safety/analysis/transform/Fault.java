package edu.umn.cs.crisys.safety.analysis.transform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.rockwellcollins.atc.agree.analysis.ast.AgreeStatement;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeVar;

import edu.umn.cs.crisys.safety.safety.DurationStatement;
import edu.umn.cs.crisys.safety.safety.FaultStatement;
import edu.umn.cs.crisys.safety.safety.TriggerStatement;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.Node;

public class Fault {
	
	// Map from fault node output names to the assigned vars
	public Map<String, Expr> faultInputMap = new HashMap<>();
	public Map<String, Expr> faultOutputMap = new HashMap<>();
	
	public Node faultNode; 
	public String id;
	public String explanitoryText;
	
	// Some information about the fault
	public DurationStatement duration = null;
	public List<AgreeVar> safetyEqVars = new ArrayList<>();
	public List<AgreeStatement> safetyEqAsserts = new ArrayList<>();
	public List<TriggerStatement> triggers = new ArrayList<>();
	
	// initial data: 
	public FaultStatement faultStatement; 

	public Fault(FaultStatement fstmt, 
			String id) {
		this.faultStatement = fstmt;
		this.id = id;
	}

}
