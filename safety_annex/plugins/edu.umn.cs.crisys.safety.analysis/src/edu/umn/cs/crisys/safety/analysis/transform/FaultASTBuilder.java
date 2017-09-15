package edu.umn.cs.crisys.safety.analysis.transform;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;

import com.rockwellcollins.atc.agree.agree.Arg;
import com.rockwellcollins.atc.agree.agree.NestedDotID;
import com.rockwellcollins.atc.agree.agree.NodeDefExpr;
import com.rockwellcollins.atc.agree.analysis.AgreeTypeUtils;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeASTBuilder;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeNode;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeProgram;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeStatement;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeVar;

import edu.umn.cs.crisys.safety.analysis.SafetyException;
import edu.umn.cs.crisys.safety.safety.ClosedInterval;
import edu.umn.cs.crisys.safety.safety.DurationStatement;
import edu.umn.cs.crisys.safety.safety.EqValue;
import edu.umn.cs.crisys.safety.safety.FaultStatement;
import edu.umn.cs.crisys.safety.safety.FaultSubcomponent;
import edu.umn.cs.crisys.safety.safety.InputStatement;
import edu.umn.cs.crisys.safety.safety.Interval;
import edu.umn.cs.crisys.safety.safety.IntervalEq;
import edu.umn.cs.crisys.safety.safety.OpenLeftInterval;
import edu.umn.cs.crisys.safety.safety.OpenRightInterval;
import edu.umn.cs.crisys.safety.safety.OutputStatement;
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
import jkind.lustre.TupleExpr;
import jkind.lustre.VarDecl;

public class FaultASTBuilder {

	// initial data: will be updated occasionally (and inefficiently!).
	
	// public FaultStatement faultStatement; 
	private AgreeProgram agreeProgram;
	private AgreeNode agreeNode;
	private AgreeASTBuilder builder = new AgreeASTBuilder();
	
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

	public static int findParam(String param, List<jkind.lustre.VarDecl> theList) {
		for (int i = 0; i < theList.size(); i++) {
			if (theList.get(i).id.equals(param)) {
				return i;
			}
		}
		return -1;
	}
	
	private void setInput(SafetyFault fault, InputStatement input) {
		for (int i = 0; i < input.getFault_in().size(); i++) {
			String param = input.getFault_in().get(i); 
			
			// translating expression HERE.
			Expr result = builder.doSwitch(input.getNom_conn().get(i));
			fault.inputParameterMap.put(param, result);
			System.out.println("Fault node input: " + param + " is assigned " + result.toString());
		}
	}

	private void setOutput(SafetyFault fault, OutputStatement output) {
		for (int i = 0; i < output.getFault_out().size(); i++) {
			String param = output.getFault_out().get(i);
			NestedDotID compOut = output.getNom_conn().get(i);
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
	
	
	private void setDuration(SafetyFault fault, DurationStatement duration) {
		fault.duration = duration;
	}
	
	private Expr constructEqLhsExpr(EqValue stmt) {
		EList<Arg> lhs = stmt.getLhs();
		if (lhs.size() != 1) {
			List<Expr> ids = new ArrayList<>();
			for (Arg arg : lhs) {
				ids.add(new IdExpr(arg.getName()));
			}
			return new TupleExpr(ids);
		} else {
			return new IdExpr(lhs.get(0).getName());
		}
	}
	
	private void addSafetyEqVal(SafetyFault fault, EqValue stmt) {
		if (stmt.getExpr() != null) {
			Expr lhsExpr = constructEqLhsExpr(stmt);
			Expr rhsExpr = builder.doSwitch(stmt.getExpr());
			Expr expr = new BinaryExpr(lhsExpr, BinaryOp.EQUAL, rhsExpr);
			fault.safetyEqAsserts.add(new AgreeStatement("", expr, stmt));
		}
		List<VarDecl> vars = 
			builder.agreeVarsFromArgs(stmt.getLhs(), fault.agreeNode.compInst);
		for (VarDecl var : vars) {
			fault.safetyEqVars.add((AgreeVar) var);
		}
	}
	
	
	private void addSafetyEqInterval(SafetyFault fault, IntervalEq stmt) {
		Expr lhsIdExpr = new IdExpr(stmt.getLhs_int().getName());
		Interval iv =stmt.getInterv(); 
		BinaryOp leftOp = 
				((iv instanceof ClosedInterval) || 
				(iv instanceof OpenRightInterval)) ? 
						BinaryOp.GREATEREQUAL : 
						BinaryOp.GREATER;
		BinaryOp rightOp = 
				((iv instanceof ClosedInterval) || 
				 (iv instanceof OpenLeftInterval)) ?
						 BinaryOp.LESSEQUAL : 
						 BinaryOp.LESS;
		Expr leftSideExpr = 
				new BinaryExpr(lhsIdExpr, leftOp, builder.doSwitch(iv.getLow()));
		Expr rightSideExpr = 
				new BinaryExpr(lhsIdExpr, rightOp, builder.doSwitch(iv.getHigh()));
		Expr expr = 
				new BinaryExpr(leftSideExpr, BinaryOp.AND, rightSideExpr);
		fault.safetyEqAsserts.add(new AgreeStatement("", expr, stmt));
		fault.safetyEqVars.add(
			(AgreeVar)builder.agreeVarFromArg(
				stmt.getLhs_int(), this.agreeNode.compInst));
	}
	
	private void addSafetyRangeEq(SafetyFault fault, RangeEq stmt) {
		throw new SafetyException("Error: range equations are not yet implemented in translator!");
	}
	
	private void addSafetySetEq(SafetyFault fault, SetEq stmt) {
		throw new SafetyException("Error: set equations are not yet implemented in translator!");
	}
	
	public void addSafetyEq(SafetyFault fault, SafetyEqStatement stmt) {
		if (stmt instanceof EqValue) {
			addSafetyEqVal(fault, (EqValue)stmt);
		} else if (stmt instanceof IntervalEq) {
			addSafetyEqInterval(fault, (IntervalEq)stmt);
		} else if (stmt instanceof RangeEq) {
			addSafetyRangeEq(fault, (RangeEq)stmt);
		} else if (stmt instanceof SetEq) {
			addSafetySetEq(fault, (SetEq)stmt);
		}
	}
	
	public void addTrigger(SafetyFault fault, TriggerStatement stmt) {
		throw new SafetyException("Error: set equations are not yet implemented in translator!");
	}

	public void processFaultSubcomponents(SafetyFault fault) {
		for (FaultSubcomponent fs : fault.faultStatement.getFaultDefinitions()) {
			if (fs instanceof DurationStatement) {
				setDuration(fault, (DurationStatement)fs); 
			} else if (fs instanceof InputStatement) {
				setInput(fault, (InputStatement)fs);
			} else if (fs instanceof OutputStatement) {
				setOutput(fault, (OutputStatement)fs);
			} else if (fs instanceof SafetyEqStatement) {
				addSafetyEq(fault, (SafetyEqStatement)fs); 
			} else if (fs instanceof TriggerStatement) {
				addTrigger(fault, (TriggerStatement)fs);
			}
		}
	}

	private int faultCounter = 0; 
	
	public String mkUniqueFaultId(FaultStatement fstmt) {
		faultCounter++; 
		return this.agreeNode.id + "__" + "fault_" + faultCounter;
	}
	
	public SafetyFault buildFault(FaultStatement fstmt) {
		String faultId = mkUniqueFaultId(fstmt); 
		SafetyFault fault = new SafetyFault(fstmt, this.agreeProgram, this.agreeNode, faultId);
		setFaultNode(fstmt, fault);
		processFaultSubcomponents(fault);
		
		return fault;
	}
}
