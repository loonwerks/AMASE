package edu.umn.cs.crisys.safety.analysis.transform;

import java.util.ArrayList;
import java.util.List;

import edu.umn.cs.crisys.safety.safety.ByzantineFaultStatement;
import edu.umn.cs.crisys.safety.safety.ConnectionsStatement;
import edu.umn.cs.crisys.safety.safety.DurationStatement;
import edu.umn.cs.crisys.safety.safety.FaultyOutputStatement;
import edu.umn.cs.crisys.safety.safety.TriggerStatement;

public class ByzFault implements BaseFault {

	public String id;
	public String name;
	public String explanitoryText;
	public double probability;
	// Some information about the fault
	public DurationStatement duration = null;
	public ConnectionsStatement connStmt = null;
	public FaultyOutputStatement faultyOutStmt = null;

	// triggers (currently unused)
	public List<TriggerStatement> triggers = new ArrayList<>();

	// path to create the fault name
	public List<String> path = new ArrayList<>();

	// initial data:
	public ByzantineFaultStatement byzFaultStatement;

	public ByzFault(ByzantineFaultStatement byzfstmt, String id, String name) {
		this.byzFaultStatement = byzfstmt;
		this.explanitoryText = byzfstmt.getStr();
		this.id = id;
		this.name = name;
	}

	@Override
	public void setPath(List<String> path) {
		this.path = new ArrayList<>(path);
	}

}
