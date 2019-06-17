package edu.umn.cs.crisys.safety.analysis.transform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.osate.aadl2.impl.DataPortImpl;
import org.osate.aadl2.instance.ConnectionInstance;
import org.osate.aadl2.instance.ConnectionInstanceEnd;
import org.osate.aadl2.instance.impl.ComponentInstanceImpl;
import org.osate.aadl2.instance.impl.SystemInstanceImpl;

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
import jkind.lustre.BoolExpr;
import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.IfThenElseExpr;
import jkind.lustre.NamedType;
import jkind.lustre.Node;
import jkind.lustre.NodeCallExpr;
import jkind.lustre.RecordAccessExpr;
import jkind.lustre.TupleExpr;
import jkind.lustre.Type;
import jkind.lustre.VarDecl;
import jkind.lustre.builders.NodeBuilder;

public class FaultASTBuilder {

	private static int faultCounter = 0;

	// globalLustreNodes will be updated occasionally as faults are added
	// if "fault" Lustre nodes are not used by the non-faulty AGREE nodes.
	private List<Node> globalLustreNodes;
	// Maps the asymmetric fault statement to corresponding comm node inputs
	private Map<String, List<String>> mapAsymCompOutputToCommNodeIn = new HashMap<String, List<String>>();
	// map comm node outputs to AADL connections
	private Map<String, ConnectionInstanceEnd> mapCommNodeOutputToConnections = new HashMap<String, ConnectionInstanceEnd>();
	// Map the name of the communication node for asymmetric faults to its list of local
	// variables in Lustre. Used in AddFaultsToAgreeNode to add assert stmts to main lustre node.
	private Map<String, List<AgreeVar>> mapCommNodeToInputs = new HashMap<String, List<AgreeVar>>();
	// Map asym fault to their corresponding component instance names
	// Used in isTop to find trigger and make assert statement
	private Map<Fault, String> mapAsymFaultToCompName = new HashMap<Fault, String>();
	// Map string of sender.sender_output to receiver.input for all connections
	private Map<String, List<String>> mapSenderToReceiver = new HashMap<String, List<String>>();

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
		List<ConnectionInstanceEnd> senderConnections = new ArrayList<>();

		// 1. Create fault for Sender node
		// We will use this fault to help define each communication
		// node.
		String faultId = mkUniqueFaultId(fstmt);
		// incorporate user-given fault name in the fault info
		String faultName = fstmt.getName();
		// containing component name used in creation of comm node name
		String compName = "";

		Fault fault = new Fault(fstmt, faultId, faultName);
		setFaultNode(fstmt, fault);
		processFaultSubcomponents(fault);

		// 2. Find out how many components the node is connected to.
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
		String searchFor = senderOutput.getFullName();
		// Will be key for mapSenderToReceiver
		String tempSend = this.agreeNode.compInst.getName() + "." + searchFor;
		List<String> tempReceive = new ArrayList<String>();
		if (this.agreeNode.compInst.eContainer() instanceof SystemInstanceImpl) {
			SystemInstanceImpl parentContainer = (SystemInstanceImpl) this.agreeNode.compInst.eContainer();
			for (ConnectionInstance ci : parentContainer.allConnectionInstances()) {
				if (ci.getFullName().contains(searchFor)) {
					// Add connection to senderCollection list
					senderConnections.add(ci.getDestination());
					// Gather name of parent and connection ("receiver1.sender_input") for map
					if (ci.getDestination().eContainer() instanceof ComponentInstanceImpl) {
						ComponentInstanceImpl compInst = (ComponentInstanceImpl) ci.getDestination().eContainer();
						// Will be value for mapSenderToReceiver
						tempReceive.add(compInst.getName() + "." + ci.getDestination().getName());
					} else {
						new SafetyException("The sender component on asymmetric fault is connected"
								+ "to an unallowable component. (Debug: FaultASTBuilder 375)");
					}
				}
			}
			// Comp name used in mapAsymFaultToCompName
			compName = this.agreeNode.compInst.getFullName();
			mapAsymFaultToCompName.put(fault, compName);
			// Map sender to receiver names
			mapSenderToReceiver.put(tempSend, tempReceive);
		}

		// 3. Create the communication nodes.
		// For loop goes through the connections collected in step 2
		// and creates a commNode specific for each connection.
		// The fault is passed into the creation method in order to
		// link the fault to the comm node as it is inserted into Lustre.
		//
		// 4. Add node to Lustre as it is created (inside loop).
		String nodeName = "";
		List<String> commNodeNames = new ArrayList<>();
		for (int i = 0; i < senderConnections.size(); i++) {
			nodeName = "asym_node_" + i + "__" + compName + "__" + fault.id;
			Node commNode = createCommNode(this.agreeNode, fstmt, fault, nodeName, i);
			commNodeNames.add(nodeName + "__input");
			// Make map from comm node to connections
			mapCommNodeOutputToConnections.put(nodeName + "__output", senderConnections.get(i));
			// 4. Add node to lustre
			this.addGlobalLustreNode(commNode);
		}
		// Output of sender component used to build lustre main asserts
		String senderOut = this.agreeNode.id + "__" + searchFor;
		mapAsymCompOutputToCommNodeIn.put(senderOut, commNodeNames);

		return fault;
	}

	// Create Lustre node for asymmetric fault connection nodes.
	// Method parameter "node" corresponds to the "sender" node
	// that has the fan out connections.
	// This is needed to access the type of connection for input/output
	// of this node.
	public Node createCommNode(AgreeNode node, FaultStatement fstmt, Fault fault, String nodeName, int connNumber) {

		// 1. Create unique node name
		NodeBuilder newNode = new NodeBuilder(nodeName);

		// 2. Get the output/input type from the node and the fstmt
		List<AgreeVar> nodeOutputs = node.outputs;
		AgreeVar outputOfInterest = null;
		// Assume asymmetric fault first in list.
		// Will have to display this to user somewhere.
		List<NestedDotID> nomFaultConn = new ArrayList<NestedDotID>();

		for (FaultSubcomponent fs : fstmt.getFaultDefinitions()) {
			if (fs instanceof OutputStatement) {
				nomFaultConn = ((OutputStatement) fs).getNom_conn();
			}
		}

		for (AgreeVar agreeVar : nodeOutputs) {
			String temp = agreeVar.id;
			if (temp.contentEquals(nomFaultConn.get(0).getBase().getName())) {
				// This agreeVar is the sender var we want to save for the
				// later mapping to the receiver var.
				outputOfInterest = agreeVar;
			}
		}

		// Now the same type on the AgreeNode outputOfInterest
		// is the same as what we will create for the type of
		// both input and output of commNode.
		Type type = outputOfInterest.type;

		newNode = createInputForCommNode(newNode, fault, outputOfInterest.type,
				nodeName);
		newNode = createOutputForCommNode(newNode);
		newNode = createLocalsForCommNode(newNode, fault);
		newNode = createEquationsForCommNode(newNode, fault, nodeName);

		return newNode.build();
	}

	/*
	 * creates inputs for new communication node.
	 */
	public NodeBuilder createInputForCommNode(NodeBuilder node, Fault fault, Type type, String nodeName) {
		// This list is used to map the fault to the top node locals that
		// will reference this node name with its corresponding inputs and outputs.
		// Hence the AgreeVars that are created are specifically named for this purpose.
		List<AgreeVar> localsForCommNode = new ArrayList<>();

		// The type of __fault__nominal__output is the same as what the fault node
		// returns as its output. First find this in order to create that
		// variable later.
		NamedType nominalOutputType = getOutputTypeForFaultNode(fault);

		node.createInput("input", type);
		AgreeVar var1 = new AgreeVar(nodeName + "__input", type, fault.faultStatement);
		localsForCommNode.add(var1);
		node.createInput("output", type);
		AgreeVar var2 = new AgreeVar(nodeName + "__output", type, fault.faultStatement);
		localsForCommNode.add(var2);
		node.createInput("__ASSUME__HIST", NamedType.BOOL);
		AgreeVar var3 = new AgreeVar(nodeName + "____ASSUME__HIST", NamedType.BOOL, fault.faultStatement);
		localsForCommNode.add(var3);
		node.createInput("time", NamedType.REAL);
		AgreeVar var4 = new AgreeVar(nodeName + "__time", NamedType.REAL, fault.faultStatement);
		localsForCommNode.add(var4);
		node.createInput("__fault__nominal__output", nominalOutputType);
		AgreeVar var5 = new AgreeVar(nodeName + "____fault__nominal__output", nominalOutputType, fault.faultStatement);
		localsForCommNode.add(var5);
		node.createInput("fault__trigger__" + fault.id, NamedType.BOOL);
		AgreeVar var7 = new AgreeVar(nodeName + "__fault__trigger__" + fault.id, NamedType.BOOL, fault.faultStatement);
		localsForCommNode.add(var7);

		// Add to map between node and list of locals in lustre
		mapCommNodeToInputs.put(nodeName, localsForCommNode);
		return node;
	}

	/*
	 * Find named type of output on fault node
	 */
	public NamedType getOutputTypeForFaultNode(Fault fault) {
		// The type of __fault__nominal__output is the same as what the fault node
		// returns as its output. First find this in order to create that
		// variable later.
		NamedType nominalOutputType = null;
		if (fault.faultNode.outputs.size() > 1) {
			new SafetyException("Fault node " + fault.faultNode.id + " can only " + "have one output.");
		} else {
			for (VarDecl output : fault.faultNode.outputs) {
				nominalOutputType = (NamedType) output.type;
			}
		}
		return nominalOutputType;
	}

	/*
	 * creates outputs for new communication node.
	 */
	public NodeBuilder createOutputForCommNode(NodeBuilder node) {

		node.createOutput("__ASSERT", NamedType.BOOL);
		return node;
	}

	/*
	 * creates locals for new communication node.
	 */
	public NodeBuilder createLocalsForCommNode(NodeBuilder node, Fault fault) {

		node.createLocal("__GUARANTEE0", NamedType.BOOL);

		// Create local using fault node output id
		// The type of fault output id is the same as what the fault node
		// returns as its output. First find this in order to create that
		// variable later.
		NamedType nominalOutputType = getOutputTypeForFaultNode(fault);
		node.createLocal(getFaultNodeOutputId(fault), nominalOutputType);

		return node;
	}

	public String getFaultNodeOutputId(Fault fault) {
		String id = "";
		List<VarDecl> faultNodeOutputs = fault.faultNode.outputs;
		if ((faultNodeOutputs.size() == 0) || (faultNodeOutputs.size() > 1)) {
			new SafetyException(
					"Asymmetric fault definitions (" + fault.id + ") " + "must have one and only one output.");
		} else {
			id = fault.id + "__node__" + faultNodeOutputs.get(0).id;
		}
		return id;
	}

	/*
	 * creates equations for new communication node.
	 */
	public NodeBuilder createEquationsForCommNode(NodeBuilder node, Fault fault, String nodeName) {
		// List of expressions used as input for node call expression later
		// We collect these as we build them for other expressions.
		List<Expr> nodeArgs = new ArrayList<>();

		// assign __GUARANTEE0 : fault__nominal__output = input
		IdExpr faultNominalOut = new IdExpr("__fault__nominal__output");
		Expr binEx = new BinaryExpr(faultNominalOut, BinaryOp.EQUAL,
				new IdExpr("input"));
		IdExpr guar = new IdExpr("__GUARANTEE0");
		node.addEquation(guar, binEx);

		// (__ASSUME__HIST => (__GUARANTEE0 and true)) and true)
		IdExpr assumeHist = new IdExpr("__ASSUME__HIST");
		BoolExpr trueExpr = new BoolExpr(true);
		BinaryExpr binGuar = new BinaryExpr(guar, BinaryOp.AND, trueExpr);
		BinaryExpr binAssume = new BinaryExpr(assumeHist, BinaryOp.IMPLIES, binGuar);
		BinaryExpr binAssumeAndTrue = new BinaryExpr(binAssume, BinaryOp.AND, trueExpr);

		// output = (if fault_trigger then fault_node_val_out else fault_nominal)
		IdExpr cond = new IdExpr("fault__trigger__" + fault.id);
		IdExpr faultNodeOutputId = new IdExpr(getFaultNodeOutputId(fault));
		IdExpr elseCond = new IdExpr("__fault__nominal__output");
		Expr exprITE = new IfThenElseExpr(cond, faultNodeOutputId, elseCond);
		Expr output = new IdExpr("output");
		BinaryExpr binOutputEquals = new BinaryExpr(output, BinaryOp.EQUAL, exprITE);

		// true and output = ...
		BinaryExpr trueAndOutputEquals = new BinaryExpr(binAssumeAndTrue, BinaryOp.AND, binOutputEquals);

		// Final expression
		BinaryExpr finalExpr = new BinaryExpr(trueAndOutputEquals, BinaryOp.AND, trueExpr);

		// Assert:
		// __ASSERT = (if fault_trigger then fault_node_val_out else fault_nominal)
		// and (true and (__ASSUME__HIST => (__GUARANTEE0 and true)) and true)
		node.addEquation(new IdExpr("__ASSERT"), finalExpr);

		// Construct the node call expression
		Expr arg;
		for (String ex : fault.faultInputMap.keySet()) {
			// val_in is fault__nominal
			if (ex.equalsIgnoreCase("val_in")) {
				arg = new IdExpr("__fault__nominal__output");
			} else {
				// if alt_val exists, input that value
				arg = fault.faultInputMap.get(ex);
			}
			// Make sure it got assigned properly
			if (arg == null) {
				new SafetyException("The arguments in the fault " + fault.id + " are not correct.");
			} else {
				// add arg to list
				nodeArgs.add(arg);
			}
		}
		// Lastly, add the trigger
		nodeArgs.add(new IdExpr("fault__trigger__" + fault.id));

		Expr nodeCall = new NodeCallExpr(fault.faultNode.id, nodeArgs);
		// Now create equation to add
		node.addEquation(new Equation(faultNodeOutputId, nodeCall));

		return node;
	}

	public Map<String, List<AgreeVar>> getMapCommNodeToInputs() {
		return mapCommNodeToInputs;
	}

	public Map<String, List<String>> getMapAsymCompOutputToCommNodeIn() {
		return mapAsymCompOutputToCommNodeIn;
	}

	public Map<Fault, String> getMapAsymFaultToCompName() {
		return mapAsymFaultToCompName;
	}

	public Map<String, ConnectionInstanceEnd> getMapCommNodeOutputToConnections() {
		return mapCommNodeOutputToConnections;
	}

	public Map<String, List<String>> getMapSenderToReceiver() {
		return mapSenderToReceiver;
	}
}
