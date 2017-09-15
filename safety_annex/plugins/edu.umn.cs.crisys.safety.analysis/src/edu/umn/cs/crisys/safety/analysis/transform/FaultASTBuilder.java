package edu.umn.cs.crisys.safety.analysis.transform;

import java.util.ArrayList;
import java.util.List;

import com.rockwellcollins.atc.agree.agree.NestedDotID;
import com.rockwellcollins.atc.agree.agree.NodeDefExpr;
import com.rockwellcollins.atc.agree.analysis.AgreeTypeUtils;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeASTBuilder;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeNode;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeProgram;

import edu.umn.cs.crisys.safety.analysis.SafetyException;
import edu.umn.cs.crisys.safety.safety.DurationStatement;
import edu.umn.cs.crisys.safety.safety.FaultStatement;
import edu.umn.cs.crisys.safety.safety.FaultSubcomponent;
import edu.umn.cs.crisys.safety.safety.InputStatement;
import edu.umn.cs.crisys.safety.safety.OutputStatement;
import edu.umn.cs.crisys.safety.util.SafetyUtil;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.Node;

public class FaultASTBuilder {

	// initial data: will be updated occasionally (and inefficiently!).
	
	// public FaultStatement faultStatement; 
	public AgreeProgram agreeProgram;
	public AgreeNode agreeNode;
	
	public FaultASTBuilder(AgreeProgram agreeProgram, AgreeNode agreeNode) {
		this.agreeProgram = agreeProgram;
		this.agreeNode = agreeNode;
	}
	
	public void addGlobalLustreNode(Node node) {
		List<Node> globalLustreNodes = new ArrayList<>(agreeProgram.globalLustreNodes); 
		globalLustreNodes.add(node);
		AgreeProgram newProgram = 
				new AgreeProgram(agreeProgram.agreeNodes, 
						globalLustreNodes, 
						agreeProgram.globalTypes, 
						agreeProgram.topNode);
		agreeProgram = newProgram;
	}

	private Node getLustreNode(String fnName, List<Node> nodes) {
		for (Node node : nodes) {
			if (node.id.equals(fnName)) {
				return node;
			}
		}
		return null;
		
	}

	private void setFaultNode(FaultStatement faultStatement, SafetyFault fault) {
		NodeDefExpr defExpr = SafetyUtil.getFaultNode(faultStatement);

		// to keep consistent with AGREE, we will use the AGREE functions
		// to construct names
		String fnName = AgreeTypeUtils.getNodeName(defExpr);
		fault.faultNode = this.getLustreNode(fnName, agreeProgram.globalLustreNodes);
		if (fault.faultNode == null) {
			AgreeASTBuilder builder = new AgreeASTBuilder();
			
			// This is a pretty random experiment...if we can get AgreeASTBuilder to 
			// build us a node, we will add it to our list and return it.
			builder.caseNodeDefExpr(defExpr);
			fault.faultNode = this.getLustreNode(fnName, AgreeASTBuilder.globalNodes);
			if (fault.faultNode != null) {
				this.addGlobalLustreNode(fault.faultNode);
			} else {
				throw new SafetyException("for fault node: " + defExpr.getFullName() + " unable to find it in AgreeProgram.  As a temporary hack, please add a call to this node somewhere in your AGREE annex.");
			}
		}
		System.out.println("For Agree Node: " + agreeNode.id + " we have found fault node: " + fault.faultNode.id);
	}

	public int findParam(String param, List<jkind.lustre.VarDecl> theList) {
		for (int i = 0; i < theList.size(); i++) {
			if (theList.get(i).id.equals(param)) {
				return i;
			}
		}
		
		return -1;
	}
	
	public void setInput(SafetyFault fault, InputStatement input) {
		for (int i = 0; i < input.getFault_in().size(); i++) {
			String param = input.getFault_in().get(i); 
			
			// translating expression HERE.
			AgreeASTBuilder builder = new AgreeASTBuilder();
			Expr result = builder.doSwitch(input.getNom_conn().get(i));
			fault.inputParameterMap.put(param, result);
			System.out.println("Fault node input: " + param + " is assigned " + result.toString());
		}
	}

	public void setOutput(SafetyFault fault, OutputStatement output) {
		for (int i = 0; i < output.getFault_out().size(); i++) {
			String param = output.getFault_out().get(i);
			NestedDotID compOut = output.getNom_conn().get(i);
			AgreeASTBuilder builder = new AgreeASTBuilder();
			Expr result = builder.caseNestedDotID(compOut);
			if (result == null ||
					!(result instanceof IdExpr)) {
				throw new SafetyException("for node: " + agreeNode.id + " nestedDotId for output maps to non-IdExpr: " + result.toString());
			}
			fault.faultNodeOutputMap.put(param, (IdExpr)result);
			System.out.println("Fault node output: " + result + "is assigned to node output: " + param);
			// translating NestedDotId to AgreeVars...how?
		}
	}
	
	
	public void setDuration(SafetyFault fault, DurationStatement duration) {
		fault.duration = duration;
	}
	
	public void processFaultSubcomponents(SafetyFault fault) {
		for (FaultSubcomponent fs : fault.faultStatement.getFaultDefinitions()) {
			if (fs instanceof DurationStatement) {
				setDuration(fault, (DurationStatement)fs); 
			} else if (fs instanceof InputStatement) {
				setInput(fault, (InputStatement)fs);
			} else if (fs instanceof OutputStatement) {
				setOutput(fault, (OutputStatement)fs);
			} 
		}
	}

	public SafetyFault buildFault(FaultStatement fstmt) {
		SafetyFault fault = new SafetyFault(fstmt, this.agreeProgram, this.agreeNode);
		processFaultSubcomponents(fault);
		
		return fault;
	}
}
