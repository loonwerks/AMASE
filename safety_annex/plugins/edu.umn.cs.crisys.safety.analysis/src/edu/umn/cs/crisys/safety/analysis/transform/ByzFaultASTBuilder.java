package edu.umn.cs.crisys.safety.analysis.transform;

import java.util.List;

import com.rockwellcollins.atc.agree.analysis.ast.AgreeNode;

import edu.umn.cs.crisys.safety.analysis.SafetyException;
import edu.umn.cs.crisys.safety.safety.ByzantineFaultStatement;
import edu.umn.cs.crisys.safety.safety.ByzantineFaultSubcomponent;
import edu.umn.cs.crisys.safety.safety.ConnectionsStatement;
import edu.umn.cs.crisys.safety.safety.DurationStatement;
import edu.umn.cs.crisys.safety.safety.FaultyOutputStatement;
import edu.umn.cs.crisys.safety.safety.ProbabilityStatement;
import edu.umn.cs.crisys.safety.safety.TriggerStatement;
import jkind.lustre.Node;

public class ByzFaultASTBuilder {

	private static int faultCounter = 0;

	// globalLustreNodes will be updated occasionally as faults are added
	// if "fault" Lustre nodes are not used by the non-faulty AGREE nodes.
	private List<Node> globalLustreNodes;

	private AgreeNode agreeNode;

	public ByzFaultASTBuilder(List<Node> globalLustreNodes, AgreeNode agreeNode) {
		this.globalLustreNodes = globalLustreNodes;
		this.agreeNode = agreeNode;
	}

	public void addGlobalLustreNode(Node node) {
		globalLustreNodes.add(node);
	}

	private void setDuration(ByzFault byzfault, DurationStatement duration) {
		byzfault.duration = duration;
	}

	public void addTrigger(ByzFault fault, TriggerStatement stmt) {
		throw new SafetyException("Error: trigger equations are not yet implemented in translator!");
	}

	public void addProbability(ByzFault byzfault, ProbabilityStatement stmt) {
		byzfault.probability = Double.parseDouble(stmt.getProbability());
	}

	public void addConnections(ByzFault byzFault, ConnectionsStatement stmt) {
		byzFault.connStmt = stmt;
	}

	public void addFaultyOutput(ByzFault byzFault, FaultyOutputStatement stmt) {
		byzFault.faultyOutStmt = stmt;
	}

	public void processByzFaultSubcomponents(ByzFault byzfault) {
		for (ByzantineFaultSubcomponent byzfs : byzfault.byzFaultStatement.getByzantine()) {
			if (byzfs instanceof DurationStatement) {
				setDuration(byzfault, (DurationStatement) byzfs);
			} else if (byzfs instanceof ProbabilityStatement) {
				addProbability(byzfault, (ProbabilityStatement) byzfs);
			} else if (byzfs instanceof ConnectionsStatement) {
				addConnections(byzfault, (ConnectionsStatement) byzfs);
			} else if (byzfs instanceof FaultyOutputStatement) {
				addFaultyOutput(byzfault, (FaultyOutputStatement) byzfs);
			}

			else {
				throw new SafetyException("Unrecognized Byzantine Fault Statement type");
			}
		}
	}

	public String mkUniqueByzFaultId(ByzantineFaultStatement byzfstmt) {
		faultCounter++;
		String elem = this.agreeNode.id + "__" + "byzfault_" + faultCounter;
		return elem;
	}

	public ByzFault buildByzFault(ByzantineFaultStatement byzfstmt) {
		String faultId = mkUniqueByzFaultId(byzfstmt);
		// incorporate user-given fault name in the fault info
		String faultName = byzfstmt.getName();

		ByzFault byzfault = new ByzFault(byzfstmt, faultId, faultName);
		processByzFaultSubcomponents(byzfault);

		return byzfault;
	}
}
