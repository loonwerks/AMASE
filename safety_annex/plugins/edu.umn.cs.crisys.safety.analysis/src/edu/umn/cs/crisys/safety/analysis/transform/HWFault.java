package edu.umn.cs.crisys.safety.analysis.transform;

import java.util.ArrayList;
import java.util.List;

import edu.umn.cs.crisys.safety.safety.DurationStatement;
import edu.umn.cs.crisys.safety.safety.HWFaultStatement;
import edu.umn.cs.crisys.safety.safety.TriggerStatement;

public class HWFault implements BaseFault {
	public String id;
	public String name;
	public String explanitoryText;
	public double probability;
	// Some information about the fault
	public DurationStatement duration = null;

	// triggers (currently unused)
	public List<TriggerStatement> triggers = new ArrayList<>();

	// path to create the fault name
	public List<String> path = new ArrayList<>();

	// initial data:
	public HWFaultStatement hwFaultStatement;

	public HWFault(HWFaultStatement hwfstmt, String id, String name) {
		this.hwFaultStatement = hwfstmt;
		this.explanitoryText = hwfstmt.getStr();
		this.id = id;
		this.name = name;
	}

	@Override
	public void setPath(List<String> path) {
		this.path = new ArrayList<>(path);
	}

	@Override
	public String getID() {
		return id;
	}

}
