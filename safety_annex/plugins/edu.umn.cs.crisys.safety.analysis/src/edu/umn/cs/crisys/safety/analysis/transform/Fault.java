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
	
	// Map from fault node parameter names to their actual expressions.
	public Map<String, Expr> faultInputMap = new HashMap<>();
	
	// Map from fault output names to the variables that they assign.
	public Map<String, Expr> faultOutputMap = new HashMap<>();
	
	public Node faultNode; 
	public String id;
	public String explanitoryText;
	public double probability;
	
	// Some information about the fault
	public DurationStatement duration = null;
	
	// Variables to be added to the agree node related to this fault.
	// This is probably unnecessary, as these variables are simply named by the 
	// concatenation of the fault id and the variable name.
	public List<AgreeVar> safetyEqVars = new ArrayList<>();
	
	// Asserts to be added to the agree node related to this fault.
	// This is also probably unnecessary.
	public List<AgreeStatement> safetyEqAsserts = new ArrayList<>();
	
	// triggers (currently unused)
	public List<TriggerStatement> triggers = new ArrayList<>();
	
	// initial data: 
	public FaultStatement faultStatement; 

	public Fault(FaultStatement fstmt, 
			String id) {
		this.faultStatement = fstmt;
		this.explanitoryText = fstmt.getStr();
		this.id = id;
	}

	// deep copy
	public Fault(Fault other) {
		this.faultInputMap = new HashMap<>(other.faultInputMap);
		this.faultOutputMap = new HashMap<>(other.faultOutputMap);
		
		this.faultNode = other.faultNode;
		this.id = other.id;
		this.explanitoryText = other.explanitoryText;
		this.probability = other.probability;
		this.duration = other.duration;
		this.safetyEqVars = new ArrayList<>(other.safetyEqVars);
		this.safetyEqAsserts = new ArrayList<>(other.safetyEqAsserts);
		this.triggers = new ArrayList<>(other.triggers);

		this.faultStatement = other.faultStatement;
	}
}
