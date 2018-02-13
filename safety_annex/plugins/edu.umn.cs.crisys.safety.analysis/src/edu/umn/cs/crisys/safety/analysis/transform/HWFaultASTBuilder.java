package edu.umn.cs.crisys.safety.analysis.transform;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;

import com.rockwellcollins.atc.agree.agree.Arg;
import com.rockwellcollins.atc.agree.agree.NestedDotID;
import com.rockwellcollins.atc.agree.agree.NodeDefExpr;
import com.rockwellcollins.atc.agree.analysis.AgreeTypeUtils;
import com.rockwellcollins.atc.agree.analysis.AgreeUtils;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeASTBuilder;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeNode;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeStatement;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeVar;

import edu.umn.cs.crisys.safety.analysis.SafetyException;
import edu.umn.cs.crisys.safety.safety.ClosedInterval;
import edu.umn.cs.crisys.safety.safety.DurationStatement;
import edu.umn.cs.crisys.safety.safety.EqValue;
import edu.umn.cs.crisys.safety.safety.FaultStatement;
import edu.umn.cs.crisys.safety.safety.FaultSubcomponent;
import edu.umn.cs.crisys.safety.safety.HWFaultStatement;
import edu.umn.cs.crisys.safety.safety.HWFaultSubcomponent;
import edu.umn.cs.crisys.safety.safety.InputStatement;
import edu.umn.cs.crisys.safety.safety.Interval;
import edu.umn.cs.crisys.safety.safety.IntervalEq;
import edu.umn.cs.crisys.safety.safety.OpenLeftInterval;
import edu.umn.cs.crisys.safety.safety.OpenRightInterval;
import edu.umn.cs.crisys.safety.safety.OutputStatement;
import edu.umn.cs.crisys.safety.safety.ProbabilityStatement;
import edu.umn.cs.crisys.safety.safety.RangeEq;
import edu.umn.cs.crisys.safety.safety.SafetyEqStatement;
import edu.umn.cs.crisys.safety.safety.SetEq;
import edu.umn.cs.crisys.safety.safety.TriggerStatement;
import edu.umn.cs.crisys.safety.util.SafetyUtil;
import jkind.lustre.BinaryExpr;
import jkind.lustre.BinaryOp;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.Node;
import jkind.lustre.RecordAccessExpr;
import jkind.lustre.TupleExpr;
import jkind.lustre.VarDecl;

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
		System.out.println("Constructed hw fault: " + elem);
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
