package edu.umn.cs.crisys.safety.analysis.transform;

import java.util.List;

import com.rockwellcollins.atc.agree.analysis.ast.AgreeNode;

import edu.umn.cs.crisys.safety.analysis.SafetyException;
import edu.umn.cs.crisys.safety.safety.DurationStatement;
import edu.umn.cs.crisys.safety.safety.HWFaultStatement;
import edu.umn.cs.crisys.safety.safety.HWFaultSubcomponent;
import edu.umn.cs.crisys.safety.safety.ProbabilityStatement;
import edu.umn.cs.crisys.safety.safety.TriggerStatement;
import jkind.lustre.Node;

public class HWFaultASTBuilder {

	private static int faultCounter = 0; 
	
	// globalLustreNodes will be updated occasionally as faults are added
	// if "fault" Lustre nodes are not used by the non-faulty AGREE nodes.
	private List<Node> globalLustreNodes;


	private AgreeNode agreeNode;
	
	public HWFaultASTBuilder(List<Node> globalLustreNodes, AgreeNode agreeNode) {
		this.globalLustreNodes = globalLustreNodes;
		this.agreeNode = agreeNode;
	}
	
	public void addGlobalLustreNode(Node node) {
		globalLustreNodes.add(node);
	}
	
	private void setDuration(HWFault hwfault, DurationStatement duration) {
		hwfault.duration = duration;
	}
	
	public void addTrigger(HWFault fault, TriggerStatement stmt) {
		throw new SafetyException("Error: trigger equations are not yet implemented in translator!");
	}
	
	public void addProbability(HWFault hwfault, ProbabilityStatement stmt) {
		hwfault.probability = Double.parseDouble(stmt.getProbability());
	}

	public void processHWFaultSubcomponents(HWFault hwfault) {
		for (HWFaultSubcomponent hwfs : hwfault.hwFaultStatement.getFaultDefinitions()) {
			if (hwfs instanceof DurationStatement) {
				setDuration(hwfault, (DurationStatement)hwfs); 
			} else if (hwfs instanceof TriggerStatement) {
				addTrigger(hwfault, (TriggerStatement)hwfs);
			} else if (hwfs instanceof ProbabilityStatement) {
				addProbability(hwfault, (ProbabilityStatement)hwfs);
			} else {
				throw new SafetyException("Unrecognized HW_fault Statement type");
			}
		}
	}
	
	public String mkUniqueHWFaultId(HWFaultStatement hwfstmt) {
		faultCounter++; 
		String elem = this.agreeNode.id + "__" + "hwfault_" + faultCounter;
		return elem;
	}
	
	public HWFault buildHWFault(HWFaultStatement hwfstmt) {
		String faultId = mkUniqueHWFaultId(hwfstmt); 
		//incorporate user-given fault name in the fault info
		String faultName = hwfstmt.getName();
		
		HWFault hwfault = new HWFault(hwfstmt, faultId, faultName);
		processHWFaultSubcomponents(hwfault);
		
		return hwfault;
	}

}
