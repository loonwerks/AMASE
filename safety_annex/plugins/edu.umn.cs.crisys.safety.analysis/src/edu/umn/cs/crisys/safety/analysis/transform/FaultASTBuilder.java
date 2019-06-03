package edu.umn.cs.crisys.safety.analysis.transform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.osate.aadl2.impl.DataPortImpl;

import com.rockwellcollins.atc.agree.agree.Arg;
import com.rockwellcollins.atc.agree.agree.NestedDotID;
import com.rockwellcollins.atc.agree.agree.NodeDefExpr;
import com.rockwellcollins.atc.agree.analysis.AgreeTypeUtils;
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
import edu.umn.cs.crisys.safety.safety.InputStatement;
import edu.umn.cs.crisys.safety.safety.Interval;
import edu.umn.cs.crisys.safety.safety.IntervalEq;
import edu.umn.cs.crisys.safety.safety.OpenLeftInterval;
import edu.umn.cs.crisys.safety.safety.OpenRightInterval;
import edu.umn.cs.crisys.safety.safety.OutputStatement;
import edu.umn.cs.crisys.safety.safety.ProbabilityStatement;
import edu.umn.cs.crisys.safety.safety.PropagationTypeStatement;
import edu.umn.cs.crisys.safety.safety.RangeEq;
import edu.umn.cs.crisys.safety.safety.SafetyEqStatement;
import edu.umn.cs.crisys.safety.safety.SetEq;
import edu.umn.cs.crisys.safety.safety.TriggerStatement;
import edu.umn.cs.crisys.safety.safety.asymmetric;
import edu.umn.cs.crisys.safety.util.SafetyUtil;
import jkind.lustre.BinaryExpr;
import jkind.lustre.BinaryOp;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.Node;
import jkind.lustre.RecordAccessExpr;
import jkind.lustre.TupleExpr;
import jkind.lustre.VarDecl;

public class FaultASTBuilder {

	private static int faultCounter = 0;

	// globalLustreNodes will be updated occasionally as faults are added
	// if "fault" Lustre nodes are not used by the non-faulty AGREE nodes.
	private List<Node> globalLustreNodes;
	// Maps the asymmetric fault statement to corresponding connections in AADL.
	private Map<FaultStatement, List<DataPortImpl>> mapAsymFaultToConnections = new HashMap<FaultStatement, List<DataPortImpl>>();

	private AgreeNode agreeNode;
	private AgreeASTBuilder builder = new AgreeASTBuilder();

	public FaultASTBuilder(List<Node> globalLustreNodes, AgreeNode agreeNode) {
		this.globalLustreNodes = globalLustreNodes;
		this.agreeNode = agreeNode;
	}

	// To reset fault counter when going through faults for a new Agree Node
	public static void resetFaultCounter() {
		faultCounter = 0;
	}

	public void addGlobalLustreNode(Node node) {
		globalLustreNodes.add(node);
	}

	private void setFaultNode(FaultStatement faultStatement, Fault fault) {
		NodeDefExpr defExpr = SafetyUtil.getFaultNode(faultStatement);

		// to keep consistent with AGREE, we will use the AGREE functions
		// to construct names
		String fnName = AgreeTypeUtils.getNodeName(defExpr);
		fault.faultNode = SafetyUtil.findNode(fnName, globalLustreNodes);
		if (fault.faultNode == null) {
			// if we can get AgreeASTBuilder to
			// build us a node, we will add it to our list and return it.
			builder.caseNodeDefExpr(defExpr);
			fault.faultNode = SafetyUtil.findNode(fnName, AgreeASTBuilder.globalNodes);
			if (fault.faultNode != null) {
				this.addGlobalLustreNode(fault.faultNode);
			} else {
				throw new SafetyException("for fault node: " + defExpr.getFullName() + " unable to find it in AgreeProgram.  As a temporary hack, please add a call to this node somewhere in your AGREE annex.");
			}
		}
	}

	public static int findParam(String param, List<jkind.lustre.VarDecl> theList) {
		for (int i = 0; i < theList.size(); i++) {
			if (theList.get(i).id.equals(param)) {
				return i;
			}
		}
		return -1;
	}

	private void setInput(Fault fault, InputStatement input) {
		for (int i = 0; i < input.getFault_in().size(); i++) {
			String param = input.getFault_in().get(i);

			// translating expression HERE.
			Expr result = builder.doSwitch(input.getNom_conn().get(i));
			fault.faultInputMap.put(param, result);
		}
	}

	private void setOutput(Fault fault, OutputStatement output) {
		for (int i = 0; i < output.getFault_out().size(); i++) {
			String param = output.getFault_out().get(i);
			NestedDotID compOut = output.getNom_conn().get(i);
			Expr result = builder.caseNestedDotID(compOut);
			Expr resultRecord = null;

			if(result instanceof RecordAccessExpr) {
				resultRecord = ((RecordAccessExpr) result).record;
				fault.faultOutputMap.put(result, param);
			}else if(result instanceof IdExpr) {
				fault.faultOutputMap.put(result, param);
			}
			else  {
				throw new SafetyException("for node: " + agreeNode.id + " nestedDotId for output maps to non-IdExpr: " + result.toString());
			}
		}
	}


	private void setDuration(Fault fault, DurationStatement duration) {
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

	private void addSafetyEqVal(Fault fault, EqValue stmt) {
		if (stmt.getExpr() != null) {
			Expr lhsExpr = constructEqLhsExpr(stmt);
			Expr rhsExpr = builder.doSwitch(stmt.getExpr());
			Expr expr = new BinaryExpr(lhsExpr, BinaryOp.EQUAL, rhsExpr);
			fault.safetyEqAsserts.add(new AgreeStatement("", expr, stmt));
		}
		List<VarDecl> vars =
			builder.agreeVarsFromArgs(stmt.getLhs(), agreeNode.compInst);
		for (VarDecl var : vars) {


			fault.safetyEqVars.add((AgreeVar) var);
		}
	}


	private void addSafetyEqInterval(Fault fault, IntervalEq stmt) {
		Expr lhsIdExpr = new IdExpr(stmt.getLhs_int().getName() );
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

	private void addSafetyRangeEq(Fault fault, RangeEq stmt) {
		throw new SafetyException("Error: range equations are not yet implemented in translator!");
	}

	private void addSafetySetEq(Fault fault, SetEq stmt) {
		throw new SafetyException("Error: set equations are not yet implemented in translator!");
	}

	public void addSafetyEq(Fault fault, SafetyEqStatement stmt) {
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

	public void addTrigger(Fault fault, TriggerStatement stmt) {
		throw new SafetyException("Error: trigger equations are not yet implemented in translator!");
	}

	public void addProbability(Fault fault, ProbabilityStatement stmt) {
		fault.probability = Double.parseDouble(stmt.getProbability());
	}

	private void addPropagationType(Fault fault, PropagationTypeStatement pts) {
		fault.propType = pts;

	}

	public void processFaultSubcomponents(Fault fault) {
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
			} else if (fs instanceof ProbabilityStatement) {
				addProbability(fault, (ProbabilityStatement)fs);
			} else if (fs instanceof PropagationTypeStatement) {
				addPropagationType(fault, (PropagationTypeStatement) fs);
			} else {
				throw new SafetyException("Unrecognized Fault Statement type");
			}
		}
	}


	public String mkUniqueFaultId(FaultStatement fstmt) {
		faultCounter++;
		String elem = this.agreeNode.id + "__" + "fault_" + faultCounter;
		return elem;
	}

	public Fault processFault(FaultStatement fstmt) {

		// If one of the fault subcomponents in this statement
		// is asymmetric prop type, then we handle the building of
		// the fault slightly differently. This boolean flag
		// is used to determine which type we have.
		boolean asymFlag = false;
		for (FaultSubcomponent fs : fstmt.getFaultDefinitions()) {
			if (fs instanceof PropagationTypeStatement) {
				if (((PropagationTypeStatement) fs).getPty() instanceof asymmetric) {
					asymFlag = true;
				} else {
					asymFlag = false;
				}
				break;
			}
		}

		if (!asymFlag) {
			return buildSymmetricFault(fstmt);
		} else {
			return buildAsymmetricFault(fstmt);
		}

	}

	/*
	 * buildSymmetricFault creates unique string name,
	 * creates new fault for this fault statement,
	 * builds the fault node for Lustre, and processes the
	 * fault subcomponents. Returns symmetric fault.
	 */
	private Fault buildSymmetricFault(FaultStatement fstmt) {

		String faultId = mkUniqueFaultId(fstmt);
		// incorporate user-given fault name in the fault info
		String faultName = fstmt.getName();

		Fault fault = new Fault(fstmt, faultId, faultName);
		setFaultNode(fstmt, fault);
		processFaultSubcomponents(fault);
		return fault;
	}

	/*
	 * buildAsymmetricFault
	 */
	private Fault buildAsymmetricFault(FaultStatement fstmt) {

		DataPortImpl senderOutput = null;
		DataPortImpl destination = null;

		// 1.Find out how many components the node is connected to.
		// This is how many communication nodes we need to make.

		// Get output that fault statement is linked to
		for (FaultSubcomponent fs : fstmt.getFaultDefinitions()) {
			if (fs instanceof OutputStatement) {
				List<NestedDotID> nominalConns = ((OutputStatement) fs).getNom_conn();
				if ((nominalConns.isEmpty()) || (nominalConns.size() > 1)) {
					new SafetyException("Cannot define asymmetric fault on zero OR more than one output.");
				} else {
					senderOutput = (DataPortImpl) nominalConns.get(0).getBase();
				}
				break;
			}
		}

		// Get list of connections from parent component that senderOutput is connected to.
//		TreeIterator<EObject> iterate = this.agreeNode.compInst.eContainer().eAllContents();

//		for (ConnectionInstance ci : this.agreeNode.compInst.eContainer().) {
//			FeatureInstance fi = (FeatureInstance) ci.getSource();
//
//		}

		Node commNode = SafetyUtil.createCommNode(this.agreeNode, fstmt, 1);

		// Add node to lustre
		this.addGlobalLustreNode(commNode);

		String faultId = mkUniqueFaultId(fstmt);
		// incorporate user-given fault name in the fault info
		String faultName = fstmt.getName();

		Fault fault = new Fault(fstmt, faultId, faultName);
		setFaultNode(fstmt, fault);
		processFaultSubcomponents(fault);

		return fault;
	}


}
