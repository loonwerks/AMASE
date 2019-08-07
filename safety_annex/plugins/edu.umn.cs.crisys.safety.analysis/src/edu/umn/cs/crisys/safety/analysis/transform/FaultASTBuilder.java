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
import jkind.lustre.NamedType;
import jkind.lustre.Node;
import jkind.lustre.NodeCallExpr;
import jkind.lustre.RecordAccessExpr;
import jkind.lustre.RecordType;
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
	private static Map<String, List<String>> mapAsymCompOutputToCommNodeIn = new HashMap<String, List<String>>();
	// map comm node outputs to AADL connections
	private static Map<String, ConnectionInstanceEnd> mapCommNodeOutputToConnections = new HashMap<String, ConnectionInstanceEnd>();
	// Map the name of the communication node for asymmetric faults to its list of local
	// variables in Lustre. Used in AddFaultsToAgreeNode to add assert stmts to main lustre node.
	private static Map<String, List<AgreeVar>> mapCommNodeToInputs = new HashMap<String, List<AgreeVar>>();
	// Map asym fault to their corresponding component instance names
	// Used in isTop to find trigger and make assert statement
	private static Map<String, List<String>> mapCompNameToCommNodes = new HashMap<String, List<String>>();
	// Map string of sender.sender_output to receiver.input for all connections
	private static Map<String, List<String>> mapSenderToReceiver = new HashMap<String, List<String>>();
	// List of expressions used as input for node call expression.
	private List<Expr> nodeArgs = new ArrayList<>();

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
		// Name of sender component (the one with the fanned output)
		compName = this.agreeNode.compInst.getName();
		// Find the connections of this component (need to access parent comp to get conns)
		if (this.agreeNode.compInst.eContainer() instanceof SystemInstanceImpl) {
			SystemInstanceImpl parentContainer = (SystemInstanceImpl) this.agreeNode.compInst.eContainer();
			// Go through all connections and find the ones from sender to receiver.
			for (ConnectionInstance ci : parentContainer.allConnectionInstances()) {
				if (ci.getSource().eContainer() instanceof ComponentInstanceImpl) {
					ComponentInstanceImpl thisComp = (ComponentInstanceImpl) ci.getSource().eContainer();
					if (thisComp.getName().equals(compName)) {
						// If this matches the fault output saved in senderOutput, then
						// add these connections to the local lists used later
						// to create the communication nodes.
						if (ci.getSource().getName().equals(senderOutput.getName())) {
							senderConnections.add(ci.getDestination());
							if (ci.getDestination().eContainer() instanceof SystemInstanceImpl) {
								SystemInstanceImpl sysReceiver = (SystemInstanceImpl) ci.getDestination().eContainer();
								tempReceive.add(sysReceiver.getName() + "." + ci.getDestination().getName());
							} else if (ci.getDestination().eContainer() instanceof ComponentInstanceImpl) {
								ComponentInstanceImpl sysReceiver = (ComponentInstanceImpl) ci.getDestination()
										.eContainer();
								tempReceive.add(sysReceiver.getName() + "." + ci.getDestination().getName());
							} else {
								new SafetyException("Asymmetric fault linked to non-system instance component. "
										+ "(Debug FaultASTBuilder 377");
							}
						} else {
							continue;
						}
					}
				}
				// Map sender to receiver names
				mapSenderToReceiver.put(tempSend, tempReceive);
			}
		}

		// 3. Create the communication nodes.
		// For loop goes through the connections collected in step 2
		// and creates a commNode specific for each connection.
		// The fault is passed into the creation method in order to
		// link the fault to the comm node as it is inserted into Lustre.
		//
		// 4. Add node to Lustre as it is created (inside loop).
		String nodeName = "";
		List<String> commNodeNamesInput = new ArrayList<>();
		List<String> commNodeNames = new ArrayList<>();
		for (int i = 0; i < senderConnections.size(); i++) {
			nodeName = "asym_node_" + i + "__" + compName + "__" + fault.id;
			Node commNode = createCommNode(this.agreeNode, fstmt, fault, nodeName, i);
			commNodeNamesInput.add(nodeName + "__input");
			commNodeNames.add(nodeName);
			// Make map from comm node to connections
			mapCommNodeOutputToConnections.put(nodeName + "__output", senderConnections.get(i));
			// 4. Add node to lustre
			this.addGlobalLustreNode(commNode);
		}
		// Output of sender component used to build lustre main asserts
		String senderOut = this.agreeNode.id + "__" + searchFor;
		mapAsymCompOutputToCommNodeIn.put(senderOut, commNodeNamesInput);
		mapCompNameToCommNodes.put(compName, commNodeNames);
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
		newNode = createLocalsForCommNode(newNode, fault, type);
		newNode = createEquationsForCommNode(newNode, fault, type, nodeName);

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
		node.createInput("__fault__nominal__output", type);
		AgreeVar var5 = new AgreeVar(nodeName + "____fault__nominal__output", type, fault.faultStatement);
		localsForCommNode.add(var5);
		node.createInput("fault__trigger__" + fault.id, NamedType.BOOL);
		AgreeVar var7 = new AgreeVar(nodeName + "__fault__trigger__" + fault.id, NamedType.BOOL, fault.faultStatement);
		localsForCommNode.add(var7);

		// The fault node may have more than one argument. These all need to be
		// added as locals to this node and put into the map for main.
		// They will also be used in the node call expression (line 625).
		// This helper method will add all locals as needed as well as associated
		// safetyEqAssert statements. These correspond to "eq" stmts in the fault definition.
		localsForCommNode.addAll(addInputListAndEqAsserts(node, fault, nodeName));

		// Add to map between node and list of locals in lustre
		mapCommNodeToInputs.put(nodeName, localsForCommNode);
		return node;
	}

	/*
	 * In the case when the fault node has multiple arguments, these must be added
	 * to the locals list for this commNode, added to the list of locals for main node
	 * information, and all safetyEqAsserts corresponding to them added to equations as well.
	 * This method performs those actions.
	 *
	 * Size of fault.faultInputMap was checked before this method was called.
	 * That means that there are more than two arguments to the fault node.
	 * The first argument is taken care of previously (__fault__nominal__output).
	 * The last argument is also taken care of (fault__trigger).
	 * We do the rest here.
	 */
	private List<AgreeVar> addInputListAndEqAsserts(NodeBuilder node, Fault fault, String nodename) {
		List<AgreeVar> localsForCommNode = new ArrayList<AgreeVar>();
		nodeArgs.clear();

		// Double check size of faultInputList, must be more than 1.
		if (fault.faultInputMap.size() <= 0) {
			return localsForCommNode;
		} else {
			for (String key : fault.faultInputMap.keySet()) {
				if (key.equalsIgnoreCase("val_in")) {
					// First add nominal output to argument list.
					nodeArgs.add(new IdExpr("__fault__nominal__output"));
				} else {
					// If the value is an IdExpr, get the name and type
					// from fault node inputs and add to locals.
					// The value is the same as the fault output type.
					if (fault.faultInputMap.get(key) instanceof IdExpr) {
						IdExpr value = (IdExpr) fault.faultInputMap.get(key);
						// Get type by accessing this in fault node input list.
						Type type = null;
						for (VarDecl vars : fault.faultNode.inputs) {
							if (value.id.equals(vars.id)) {
								type = vars.type;
								break;
							}
						}
						// If the type is still null, that means that this var
						// was not in the fault node input list and must
						// be a safety eq statement.
						if (type == null) {
							for (AgreeVar vars : fault.safetyEqVars) {
								if (value.id.equals(vars.id)) {
									type = vars.type;
									break;
								}
							}
						}
						// In this last case, we have checked two possible
						// locations for the input value from the map.
						// There must have been an error.
						if (type == null) {
							new SafetyException("Error in defining fault node arguments. (debug FaultASTBuilder 563)");
						}
						AgreeVar local = new AgreeVar(value.id, type, fault.faultStatement);
						IdExpr newIdForList = new IdExpr(local.id);
						nodeArgs.add(newIdForList);
						localsForCommNode.add(local);
						node.createInput(local.id, type);
					} else {
						// If it is not IdExpr, assume that it is Real/Bool/IntExpr
						// Added directly to fault node call.
						nodeArgs.add(fault.faultInputMap.get(key));
					}
				}
			}
		}
		// Lastly, add the trigger
		nodeArgs.add(new IdExpr("fault__trigger__" + fault.id));
		return localsForCommNode;
	}

	/*
	 * Find named type of output on fault node
	 */
	public NamedType getOutputTypeForFaultNode(Fault fault) {
		// The type of __fault__nominal__output is the same as what the fault node
		// takes as input. Will have statement: fault__nominal = input
		// First find this in order to create that
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
	public NodeBuilder createLocalsForCommNode(NodeBuilder node, Fault fault, Type type) {

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
					"Asymmetric fault definitions (" + fault.id + ") " + "must have one and only one output"
							+ ". (Debug FaultAST 634)");
		} else {
			id = fault.id + "__node__" + faultNodeOutputs.get(0).id;
		}
		return id;
	}

	/*
	 * creates equations for new communication node.
	 */
	public NodeBuilder createEquationsForCommNode(NodeBuilder node, Fault fault, Type type, String nodeName) {
		// assign __GUARANTEE0 : fault__nominal__output = input
		// Simple case is when it is bool, real. Otherwise if it is a record
		// type, we need to access the field of the fault node input and append
		// that to the expression (i.e. __fault__nominal__output.VAL = input.VAL)
		String field = "";
		String dotField = "";
		// Save output as full statement (in case of record type)
		Expr actualFaultOutput = null;
		if (type instanceof RecordType) {
			for (Expr faultOutput : fault.faultOutputMap.keySet()) {
				if (faultOutput instanceof RecordAccessExpr) {
					actualFaultOutput = faultOutput;
					RecordAccessExpr rac = (RecordAccessExpr) faultOutput;
					dotField = "." + rac.field;
					field = rac.field;
				}
//				else {
//					new SafetyException("Type error in fault output. (Debug FaultASTBuilder 661");
//				}
			}
		}
		RecordAccessExpr recNominal = new RecordAccessExpr(new IdExpr("__fault__nominal__output"), field);
		IdExpr faultNominalOut = new IdExpr("__fault__nominal__output" + dotField);
		Expr binEx = new BinaryExpr(faultNominalOut, BinaryOp.EQUAL,
				new IdExpr("input" + dotField));
		IdExpr guar = new IdExpr("__GUARANTEE0");
		// This links fault nominal with node input :
		// assert (__fault__nominal__output.NODE_VAL = input.NODE_VAL)
		node.addEquation(guar, binEx);

		// (__ASSUME__HIST => (__GUARANTEE0 and true)) and true)
		IdExpr assumeHist = new IdExpr("__ASSUME__HIST");
		BoolExpr trueExpr = new BoolExpr(true);
		BinaryExpr binGuar = new BinaryExpr(guar, BinaryOp.AND, trueExpr);
		BinaryExpr binAssume = new BinaryExpr(assumeHist, BinaryOp.IMPLIES, binGuar);
		BinaryExpr binAssumeAndTrue = new BinaryExpr(binAssume, BinaryOp.AND, trueExpr);

		// output = (fault_node_val_out)
		// If record type, need to reference "output.VAL"
		IdExpr toAssign = null;
		toAssign = new IdExpr(getFaultNodeOutputId(fault));
		IdExpr output;
		if (type instanceof RecordType) {
			output = new IdExpr("output" + dotField);
		} else {
			output = new IdExpr("output");
		}
		// output = val_out
		BinaryExpr outputEqualsValout = new BinaryExpr(output, BinaryOp.EQUAL, toAssign);

		// Final expression
		BinaryExpr finalExpr = new BinaryExpr(binAssumeAndTrue, BinaryOp.AND, outputEqualsValout);

		// Before finishing the assert, check to see if we have safetyEqAsserts in the fault
		// and add those to the finalExpr with "and"
		if (fault.safetyEqAsserts.isEmpty()) {
			// Assert:
			// __ASSERT = (output = (fault_node_val_out))
			// and (__ASSUME__HIST => (__GUARANTEE0 and true)))
			node.addEquation(new IdExpr("__ASSERT"), finalExpr);
		} else {
			// Build and expression with all expr in list
			Expr safetyEqExpr = buildBigAndExpr(fault.safetyEqAsserts, 0);
			BinaryExpr finalExpr2 = new BinaryExpr(safetyEqExpr, BinaryOp.AND, finalExpr);
			node.addEquation(new IdExpr("__ASSERT"), finalExpr2);
		}

		// Construct the node call expression
		// If record type, add to fault nominal expression
		if (dotField.isEmpty()) {
			NodeCallExpr nodeCall = new NodeCallExpr(fault.faultNode.id, nodeArgs);
			node.addEquation(new Equation(new IdExpr(getFaultNodeOutputId(fault)), nodeCall));
		} else {
			List<Expr> newList = new ArrayList<>();
			for (Expr ex : nodeArgs) {
				if (ex instanceof IdExpr) {
					IdExpr idEx = (IdExpr) ex;
					if (idEx.id.equalsIgnoreCase("__fault__nominal__output")) {
						IdExpr newId = new IdExpr(idEx.id + dotField);
						newList.add(newId);
					} else {
						newList.add(ex);
					}
				} else {
					newList.add(ex);
				}
			}
			NodeCallExpr nodeCall = new NodeCallExpr(fault.faultNode.id, newList);
			node.addEquation(new Equation(new IdExpr(getFaultNodeOutputId(fault)), nodeCall));
		}

		return node;
	}

	/*
	 * helper method that builds an AND expression of all things in list
	 * Base case: list is empty : append false to AND list
	 * Base case 2: list has one element : return that element.
	 * Recursive case: make AND of element of list with recursive call
	 *
	 */
	private Expr buildBigAndExpr(List<AgreeStatement> exprList, int index) {
		if (index > exprList.size() - 1) {
			return new BoolExpr(false);
		} else if (index == exprList.size() - 1) {
			return exprList.get(index).expr;
		} else {
			return new BinaryExpr(exprList.get(index).expr, BinaryOp.AND, buildBigAndExpr(exprList, index + 1));
		}
	}

	public Map<String, List<AgreeVar>> getMapCommNodeToInputs() {
		return mapCommNodeToInputs;
	}

	public Map<String, List<String>> getMapAsymCompOutputToCommNodeIn() {
		return mapAsymCompOutputToCommNodeIn;
	}

	public Map<String, List<String>> getMapCompNameToCommNodes() {
		return mapCompNameToCommNodes;
	}

	public Map<String, ConnectionInstanceEnd> getMapCommNodeOutputToConnections() {
		return mapCommNodeOutputToConnections;
	}

	public Map<String, List<String>> getMapSenderToReceiver() {
		return mapSenderToReceiver;
	}

	public static void resetAsymMaps() {
		mapCommNodeToInputs.clear();
		mapAsymCompOutputToCommNodeIn.clear();
		mapCompNameToCommNodes.clear();
		mapCommNodeOutputToConnections.clear();
		mapSenderToReceiver.clear();
		;
	}
}
