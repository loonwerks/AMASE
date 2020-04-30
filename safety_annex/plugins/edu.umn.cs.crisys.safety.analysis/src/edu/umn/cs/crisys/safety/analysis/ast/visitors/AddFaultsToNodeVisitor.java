package edu.umn.cs.crisys.safety.analysis.ast.visitors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.osate.aadl2.BooleanLiteral;
import org.osate.aadl2.NamedElement;
import org.osate.aadl2.instance.ComponentInstance;
import org.osate.aadl2.instance.ConnectionInstanceEnd;
import org.osate.aadl2.instance.impl.ComponentInstanceImpl;
import org.osate.aadl2.instance.impl.FeatureInstanceImpl;
import org.osate.aadl2.instance.impl.SystemInstanceImpl;

import com.rockwellcollins.atc.agree.analysis.AgreeUtils;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeAADLConnection;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeConnection;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeEquation;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeNode;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeNodeBuilder;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeProgram;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeStatement;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeVar;
import com.rockwellcollins.atc.agree.analysis.ast.visitors.AgreeASTMapVisitor;

import edu.umn.cs.crisys.safety.analysis.GranularityUtils;
import edu.umn.cs.crisys.safety.analysis.SafetyException;
import edu.umn.cs.crisys.safety.analysis.ast.FaultActivationAssignment;
import edu.umn.cs.crisys.safety.analysis.ast.SafetyNodeBuilder;
import edu.umn.cs.crisys.safety.analysis.ast.SafetyPropagation;
import edu.umn.cs.crisys.safety.analysis.transform.AddFaultsToAgree;
import edu.umn.cs.crisys.safety.analysis.transform.AsymFaultASTBuilder;
import edu.umn.cs.crisys.safety.analysis.transform.BaseFault;
import edu.umn.cs.crisys.safety.analysis.transform.Fault;
import edu.umn.cs.crisys.safety.analysis.transform.FaultASTBuilder;
import edu.umn.cs.crisys.safety.analysis.transform.HWFault;
import edu.umn.cs.crisys.safety.analysis.transform.HWFaultASTBuilder;
import edu.umn.cs.crisys.safety.safety.ActivationStatement;
import edu.umn.cs.crisys.safety.safety.AnalysisBehavior;
import edu.umn.cs.crisys.safety.safety.AnalysisStatement;
import edu.umn.cs.crisys.safety.safety.DisableStatement;
import edu.umn.cs.crisys.safety.safety.FaultCountBehavior;
import edu.umn.cs.crisys.safety.safety.FaultStatement;
import edu.umn.cs.crisys.safety.safety.FaultSubcomponent;
import edu.umn.cs.crisys.safety.safety.HWFaultStatement;
import edu.umn.cs.crisys.safety.safety.OutputStatement;
import edu.umn.cs.crisys.safety.safety.PermanentConstraint;
import edu.umn.cs.crisys.safety.safety.ProbabilityBehavior;
import edu.umn.cs.crisys.safety.safety.PropagateStatement;
import edu.umn.cs.crisys.safety.safety.PropagationTypeStatement;
import edu.umn.cs.crisys.safety.safety.SpecStatement;
import edu.umn.cs.crisys.safety.safety.TemporalConstraint;
import edu.umn.cs.crisys.safety.safety.TransientConstraint;
import edu.umn.cs.crisys.safety.safety.asymmetric;
import edu.umn.cs.crisys.safety.safety.symmetric;
import edu.umn.cs.crisys.safety.util.SafetyUtil;
import jkind.lustre.ArrayAccessExpr;
import jkind.lustre.BinaryExpr;
import jkind.lustre.BinaryOp;
import jkind.lustre.BoolExpr;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.IfThenElseExpr;
import jkind.lustre.IntExpr;
import jkind.lustre.NamedType;
import jkind.lustre.Node;
import jkind.lustre.NodeCallExpr;
import jkind.lustre.RecordAccessExpr;
import jkind.lustre.Type;
import jkind.lustre.UnaryExpr;
import jkind.lustre.UnaryOp;
import jkind.lustre.VarDecl;


/**
 * AddFaultsToNodeVisitor visits each agree node and adds fault information
 * to the lustre code. This is accomplished by creating a NodeBuilder object and
 * adding the extra fault information here. That node builder is then inserted into
 * the program. After this visit is called, the agree node visitor has added all
 * necessary fault information to each node.
 * If the node visited is the top node, additional information is added to the main
 * lustre node. This includes assertions that restrict behavior based on the fault
 * information.
 *
 * @author Danielle Stewart, Janet Liu, Mike Whalen
 *
 */
public class AddFaultsToNodeVisitor extends AgreeASTMapVisitor {
	// Global data structures for traversal:

	// This is the list of Lustre nodes that are used by the safety analysis that
	// may not be part of the set of nodes used by AGREE
	private List<Node> globalLustreNodes;
	// Top of the node hierarchy in AGREE
	private AgreeNode topNode;
	// This map is used to track, for each fault, a list of paths to instances of
	// that fault (since nodes may be used in multiple locations), in order
	// to produce the top-level global variables to activate faults.
	private Map<Fault, List<String>> mapFaultToLustreNames = new HashMap<Fault, List<String>>();
	// This map is used to track, for each hwfault, a list of paths to instances of
	// that hwfault (since nodes may be used in multiple locations), in order
	// to produce the top-level global variables to activate faults.
	private Map<HWFault, List<String>> mapHWFaultToLustreNames = new HashMap<HWFault, List<String>>();
	// It is used to properly set up the top-level node for triggering faults.
	// Fault map: stores the faults associated with a node.
	// Keying off component instance rather than AgreeNode, just so we don't
	// have problems with "stale" AgreeNode references during transformations.
	private Map<ComponentInstance, List<Fault>> faultMap = new HashMap<>();
	private Map<ComponentInstance, List<HWFault>> hwfaultMap = new HashMap<>();
	// It is used to store src to dest fault propagations
	private HashSet<SafetyPropagation> propagations = new HashSet<>();
	// fault activation assignments
	private HashSet<FaultActivationAssignment> faultActivations = new HashSet<>();
	// I am unsure as to whether this is necessary: it appears to map a fault to a
	// single
	// string. As such, I believe it is incorrectly constructed.
	private Map<Fault, String> mapFaultToPath = new HashMap<>();

	// Per node data structures: must be stored when visiting new node:

	// This maps output id to a pair consisting of the output expression and a fault.
	private Map<String, List<Pair>> faultyVarsExpr = new HashMap<String, List<Pair>>();
	// List of fault pairs that cannot occur together (i.e. they are on the same output).
	private List<FaultPair> mutualExclusiveFaults = new ArrayList<FaultPair>();
	// Map from asymmetric fault to associated connections -
	// ex: sender.out -> receiver1.in, receiver2.in, receiver3.in.
	private Map<String, List<String>> mapAsymCompOutputToCommNodeIn = new HashMap<String, List<String>>();
	// Map the name of the communication node for asymmetric faults to its list of local
	// variables in Lustre. Used in AddFaultsToAgreeNode to add assert stmts to main lustre node.
	private Map<String, List<AgreeVar>> mapCommNodeToInputs = new HashMap<String, List<AgreeVar>>();
	// Map asym faults to their corresponding commNodes.
	private Map<Fault, List<String>> mapAsymFaultToCommNodes = new HashMap<Fault, List<String>>();
	// Map asym fault to their corresponding component instance names
	// Used in isTop to find trigger and make assert statement
	private Map<Fault, String> mapAsymFaultToCompName = new HashMap<Fault, String>();
	// Maps component name to all associated communication node names.
	private Map<String, List<String>> mapCompNameToCommNodes = new HashMap<String, List<String>>();
	// map comm node outputs to AADL connections
	private Map<String, ConnectionInstanceEnd> mapCommNodeOutputToConnections = new HashMap<String, ConnectionInstanceEnd>();
	// Map sender ("sender.output") to receiver name ("receiver1.input", "receiver2.input",...)
	private Map<String, List<String>> mapSenderToReceiver = new HashMap<String, List<String>>();

	public static int maxFaultCount = 0;
	public static double probabilityThreshold = 0.0;
	public static boolean upperMostLevel = true;
	public static ArrayList<FaultSetProbability> faultCombinationsAboveThreshold = new ArrayList<>();
	public static Set<Set<String>> faultCombinationAboveThresholdStrs = new HashSet<Set<String>>();
	public static boolean maxFaultHypothesis = false;
	public static boolean probabilisticHypothesis = false;

	// Flag to indicate decomposition of contracts and eq stmts.
	public static boolean granularity = true;

	/**
	 * Call to super class.
	 */
	public AddFaultsToNodeVisitor() {
		super(new jkind.lustre.visitors.TypeMapVisitor());
	}

	/**
	 * Method initializes static vars for this class.
	 */
	public static void init() {
		maxFaultCount = 0;
		probabilityThreshold = 0.0;
		upperMostLevel = true;
		faultCombinationsAboveThreshold.clear();
		faultCombinationAboveThresholdStrs.clear();
		maxFaultHypothesis = false;
		probabilisticHypothesis = false;
	}

	@Override
	public AgreeProgram visit(AgreeProgram program) {
		globalLustreNodes = new ArrayList<>(program.globalLustreNodes);
		this.topNode = program.topNode;
		AgreeNode topNode = this.visit(program.topNode);
		program = new AgreeProgram(program.agreeNodes, globalLustreNodes, program.globalTypes, topNode,
				program.containsRealTimePatterns);
		return program;
	}

	//////////////////////////////////////////////////////////////////
	//
	// AGREENODE TRAVERSAL STARTS HERE.
	//
	// since we don't totally know the traversal order, if we only
	// want to replace in a single node, we need to store the
	// 'stacked' inNode, then restore it after a traversal.
	//
	///////////////////////////////////////////////////////////////////
	@Override
	public AgreeNode visit(AgreeNode node) {
		Map<String, List<Pair>> parentFaultyVarsExpr = faultyVarsExpr;
		boolean isTop = (node == this.topNode);

		// Gather non-hardware (dependent) faults
		List<Fault> faults = gatherFaults(globalLustreNodes, node, isTop);
		// Gather HW faults
		List<HWFault> hwFaults = gatherHWFaults(globalLustreNodes, node, isTop);
		// Rename var names in faults: fault_varName
		faults = renameFaultEqs(faults);

		if (faultMap.containsKey(node.compInst) || hwfaultMap.containsKey(node.compInst)) {
			throw new SafetyException("Node: " + node.id + " has been visited twice.");
		}
		faultMap.put(node.compInst, faults);
		hwfaultMap.put(node.compInst, hwFaults);

		faultyVarsExpr = gatherFaultyOutputs(faults, node);
		// this will traverse through the child nodes
		node = super.visit(node);
		AgreeNodeBuilder nb = new AgreeNodeBuilder(node);

		// Granularity check
		if (granularity) {
			// Send node and nb to granularity util methods
			nb = GranularityUtils.decomposeNodeContracts(node, nb);
		}

		// Change this nodes flag to reflect fault tree generation or not.
		if (AddFaultsToAgree.getIsGenMCS()) {
			nb.setFaultTreeFlag(true);
		}
		// Go through fault list first.
		// If symmetric, add fault info to agree node.
		// If asymmetric, only add fault triggers to agree node.
		for (Fault f : faults) {
			if ((f.propType == null) || (f.propType.getPty() instanceof symmetric)) {
				addFaultInputs(f, nb);
				addFaultLocalEqsAndAsserts(f, nb);
			} else {
				addFaultInputs(f, nb);
			}

		}
		// Then do the hardware fault inputs
		addHWFaultInputs(hwFaults, nb);

		addToMutualExclusionList();
		addNominalVars(node, nb);
		addFaultNodeEqs(faults, nb);

		if (isTop) {
			topNodeVisit(nb, node);
		}
		node = nb.build();
		faultyVarsExpr = parentFaultyVarsExpr;

		// Create SafetyNodeBuilder and copy over all of this node
		// information. We do this so we can access connections
		// for asymmetric faults.
		if (!this.mapSenderToReceiver.isEmpty() && isTop) {
			SafetyNodeBuilder sb = changeTopNodeAsymConnections(nb, node);
			return sb.build();
		} else {
			return node;
		}
	}

	/**
	 * Visit tasks for the top node.
	 *
	 * @param nb NodeBuilder : fault info added here.
	 * @param node Agree node that is the top node.
	 */
	private void topNodeVisit(AgreeNodeBuilder nb, AgreeNode node) {
		topNode = node;
		AnalysisBehavior maxFaults = this.gatherTopLevelFaultAnalysis(node);
		// gather path information for the faults (for creating names later)
		collectFaultPath(node, new ArrayList<>());
		this.gatherFaultPropagation(node);
		this.gatherFaultActivation(node);
		// Add top level fault declarations for asymmetric faults if there are any.
		// This is done in a separate method than the normal call
		// (addTopLevelFaultDeclarations) due to the recursive nature
		// of that method. We only add declarations for asym faults once
		// and do not want recursive calls on this activity for subnodes.
		if (!this.mapCommNodeToInputs.isEmpty()) {
			addTopLevelAsymFaultDeclarations(nb);
		}
		// empty path to pass to top level node fault
		// node id used as the path to pass to sub level node fault
		addTopLevelFaultDeclarations(node, nb);

		// add top level fault activation assertions
		addTopLevelFaultActivationAssertions(nb);

		// This checks if we are doing max faults or gen mcs behavior.
		// It will add the assertion to Lustre representing the required behavior.
		// If we want to generate mcs, this method changes in order
		// to not add assertions regarding behavior (i.e. no assertion about
		// max # faults).
		if (AddFaultsToAgree.getIsVerify()) {
			// clear static variables for every verification layer
			// when verifying with AGREE in the presence of faults
			init();
			addTopLevelFaultOccurrenceConstraints(maxFaults, node, nb);
		} else if (AddFaultsToAgree.getIsGenMCS()) {
			nb.setFaultTreeFlag(true);

			// only collect fault hypothesis from the upper most level
			// note that upperMostLevel is a static variable that gets cleared
			// in init() which is invoked when clicking the Generate Minimal Cutsets button
			if (upperMostLevel) {
				upperMostLevel = false;
				// if max fault hypothesis, collect max fault count
				if (maxFaults instanceof FaultCountBehavior) {
					maxFaultHypothesis = true;
					maxFaultCount = Integer.parseInt(((FaultCountBehavior) maxFaults).getMaxFaults());
				}
				// if probabilistic fault hypothesis, collect probabilistic hypothesis
				else if (maxFaults instanceof ProbabilityBehavior) {
					probabilisticHypothesis = true;
					probabilityThreshold = Double.parseDouble(((ProbabilityBehavior) maxFaults).getProbabilty());
				}
			}
			// if probabilistic fault hypothesis, need to collect valid fault combinations from every verification level
			// but using the probability threshold from the upper most level
			if (probabilisticHypothesis) {
				collectTopLevelMaxFaultOccurrenceConstraint(probabilityThreshold, topNode, nb);
			}
		}
	}

	/**
	 * Visit IdExpr output. If symmetric, change name to fault__nominal.
	 */
	@Override
	public Expr visit(IdExpr e) {
		if (faultyVarsExpr.containsKey(e.id)) {
			List<Pair> lp = faultyVarsExpr.get(e.id);
			for (Pair p : lp) {
				// If fault is asym, do not change expression to fault nominal.
				if (!isAsymmetric(p.f)) {
					if (p.ex.toString().equals(e.id)) {
						IdExpr nom = new IdExpr(e.location, createNominalId(e.id));
						return nom;
					}
				}
			}
		}
		return e;
	}

	/**
	 * Method changes top level connections to reflect communication nodes for
	 * asymmetric faults.
	 * Finds old connections from sender to receiver and removes them.
	 * Then adds new connections from sender to communication nodes and from
	 * communication nodes to receivers.
	 *
	 * @param nb NodeBuilder for this top node.
	 * @param node The top node of the program.
	 * @return SafetyNodeBuilder with connections changed.
	 */
	private SafetyNodeBuilder changeTopNodeAsymConnections(AgreeNodeBuilder nb, AgreeNode node) {
		SafetyNodeBuilder sb = new SafetyNodeBuilder(node);
		List<AgreeConnection> agreeConns = new ArrayList<AgreeConnection>();
		int i = 0;
		// Make sure we have AgreeAADLConnection and cast to access AgreeVar
		for (AgreeConnection ac : sb.getConnections()) {
			if (ac instanceof AgreeAADLConnection) {
				AgreeAADLConnection aac = (AgreeAADLConnection) ac;
				AgreeVar sourceName = aac.sourceVarName;
				AgreeVar destName = aac.destinationVarName;
				// If we do not have the component instance, we
				// cannot perform this removal of connections.
				if ((sourceName.compInst == null) || (destName.compInst == null)) {
					continue;
				}
				String senderName = sourceName.compInst.getName() + "." + sourceName.id;
				String receiverName = destName.compInst.getName() + "." + destName.id;
				// Now check the map and if we have a match,
				// remove that element from agreeConns.
				for (String sendKey : mapSenderToReceiver.keySet()) {
					if (senderName.contentEquals(sendKey)) {
						for (String receiveVal : mapSenderToReceiver.get(sendKey)) {
							if (receiverName.equals(receiveVal)) {
								agreeConns.add(ac);
								break;
							}
						}
					}
				}
			}
			i++;
		}
		List<AgreeConnection> connList = sb.getConnections();
		for (AgreeConnection j : agreeConns) {
			connList.remove(j);
		}
		FaultASTBuilder.resetAsymMaps();
		return sb;
	}

	/**
	 * Use faultyVarsExpr list to create mutual exclusion between all
	 * faults on a single output.
	 *
	 */
	private void addToMutualExclusionList() {

		for (String output : faultyVarsExpr.keySet()) {
			List<Fault> faultsForSameOutput = new ArrayList<Fault>();
			for (Pair p : faultyVarsExpr.get(output)) {
				for (Fault curFault : faultsForSameOutput) {
					mutualExclusiveFaults.add(new FaultPair(curFault, p.f));
				}
				faultsForSameOutput.add(p.f);
			}
		}
	}

	/**
	 * Creates fault nominal input to the lustre node. Only add this if there is
	 * at least one symmetric fault defined for this node. If only asymmetric,
	 * then fault nominal does not exist in the agree node, only in comm nodes.
	 *
	 * @param node Agree node with these faults.
	 * @param nb NodeBuilder will have nominal vars added to locals.
	 */
	public void addNominalVars(AgreeNode node, AgreeNodeBuilder nb) {
		List<String> inputIdList = new ArrayList<String>();
		for (String faultyId : faultyVarsExpr.keySet()) {
			List<Pair> faultPairs = faultyVarsExpr.get(faultyId);
			boolean onlyAsym = true;
			Fault f = null;
			for (Pair p : faultPairs) {
				if (!isAsymmetric(p.f)) {
					onlyAsym = false;
				}
				f = p.f;
			}
			if (!onlyAsym) {
				AgreeVar out = findVar(node.outputs, (faultyId));
				if (out == null) {
					throw new SafetyException("A fault defined for " + node.id + " has a connection"
							+ " that is not a valid output for this component." + " Valid connections include {"
							+ node.outputs + "}");
				} else {
					for (Type nominalOutputType : getOutputTypeForFaultNode(f)) {
						if (!inputIdList.contains(faultyId)) {
							nb.addInput(new AgreeVar(createNominalId((faultyId)), nominalOutputType, out.reference));
							inputIdList.add(faultyId);
						}

					}
				}
			}
		}
	}

	/**
	 * Find named type of output on fault node
	 *
	 * @param fault Fault defining this fault node.
	 * @return NamedType The type on the output the fault is connected to.
	 */
	protected List<Type> getOutputTypeForFaultNode(Fault fault) {
		// The type of __fault__nominal__output is the same as what the fault node
		// takes as input. Will have statement: fault__nominal = input
		// First find this in order to create that
		// variable later.
		List<Type> nominalOutputTypeList = new ArrayList<Type>();
		for (VarDecl output : fault.faultNode.outputs) {
			nominalOutputTypeList.add(output.type);
		}
		return nominalOutputTypeList;
	}

	/**
	 * Adds trigger statements to inputs for this node.
	 *
	 * @param f Fault defined on the node.
	 * @param nb NodeBuilder has this input added.
	 */
	public void addFaultInputs(Fault f, AgreeNodeBuilder nb) {
		nb.addInput(new AgreeVar(createFaultNodeTriggerId(f.id), NamedType.BOOL, f.faultStatement));
	}

	/**
	 * Create trigger stmts for hardware faults.
	 *
	 * @param hwfaults List of hardware faults for this node.
	 * @param nb NodeBuilder will have triggers added to inputs.
	 */
	public void addHWFaultInputs(List<HWFault> hwfaults, AgreeNodeBuilder nb) {
		for (HWFault hwf : hwfaults) {
			nb.addInput(new AgreeVar(createFaultNodeTriggerId(hwf.id), NamedType.BOOL, hwf.hwFaultStatement));
		}
	}

	/**
	 * Add inputs and assert statements for any SafetyEqVars or SafetyEqAsserts in this fault.
	 *
	 * @param f Fault with (possibly) eq stmts.
	 * @param nb NodeBuilder has this info added.
	 */
	public void addFaultLocalEqsAndAsserts(Fault f, AgreeNodeBuilder nb) {
		nb.addInput(f.safetyEqVars);
		nb.addAssertion(f.safetyEqAsserts);
	}

	/**
	 * Create fault nominal, trigger, and other input expressions for agree node.
	 * Ex: Lustre fault node has input args: {val_out, alt_val, trigger}
	 * 	   f.faultInputMap contains:
	 * 			val_out = sender_out (agree node output),
	 * 			alt_val = 1.0 (fail to value)
	 * This method will create the list "actuals" which contains:
	 *  [__fault__nominal__val_out, 1.0, __fault__trigger__sender__fault_1]
	 *
	 * These are the actual lustre values that are passed to the fault node call.
	 *
	 *
	 * @param f The fault for this agree node - uses inputs for this fault node
	 * 			to construct lustre names.
	 * @param localFaultTriggerMap Map<Fault, Expr> from fault to trigger expression.
	 * @return List of expressions for trigger, nominal, etc.
	 */
	public List<Expr> constructNodeInputs(Fault f, Map<Fault, Expr> localFaultTriggerMap) {
		// List will hold in order all inputs for fault node.
		List<Expr> actuals = new ArrayList<>();
		for (VarDecl vd : f.faultNode.inputs) {
			Expr actual;
			// If user attempted to define a trigger, throw exception.
			if (vd.id.equalsIgnoreCase("trigger")) {
				if (f.faultInputMap.containsKey(vd.id)) {
					throw new SafetyException(
							"Trigger input for fault node should not be explicitly assigned by user.");
				}
				// Lustre name of trigger
				actual = new IdExpr(createFaultNodeTriggerId(f.id));
				localFaultTriggerMap.put(f, actual);
			} else {
				actual = f.faultInputMap.get(vd.id);
				if (actual == null) {
					throw new SafetyException("Fault node (" + f.faultNode.id + ") parameter '" + vd.id
							+ "' is not assigned anything for fault " + f.id + ". "
							+ " Check the fault node parameters and the fault definition for any missing assignments.");
				}
				// Do any name conversions on the stored expression and create nominal id.
				actual = actual.accept(this);
			}
			actuals.add(actual);
		}
		return actuals;
	}

	/**
	 * Adds fault node eqs to agree node. Will add the fault node call and
	 * the nested trigger statement to the node builder.
	 * Ex fault node call:
	 * Sender__fault_1__node__val_out =
	 * 	Common_Faults__fail_to_real(__fault__nominal__sender_out, 1.0, fault__trigger__Sender__fault_1);
	 *
	 * Ex nested trigger expr:
	 * agree_node_output =
	 * 				if fault__trigger_1 then fault__node_1__val_out
	 * 				else if fault__trigger_2 then fault__node_2__val_out
	 * 				...
	 * 				else __fault__nominal__output)
	 * @param faults List of faults for this agree node.
	 * @param nb NodeBuilder that will have these things added.
	 */
	public void addFaultNodeEqs(List<Fault> faults, AgreeNodeBuilder nb) {
		Map<Fault, Expr> faultTriggerMap = new HashMap<>();
		// Loop through all faults in this node
		for (Fault f : faults) {
			// If this fault is asymmetric, move to the next fault in the list.
			if (isAsymmetric(f)) {
				continue;

			}
			addNodeCall(nb, f, faultTriggerMap);
		}
		// Add trigger expression for each output.
		for (String lhsWithStmtName : faultyVarsExpr.keySet()) {
			addTriggerExpr(nb, faultTriggerMap, lhsWithStmtName);
		}
	}

	/**
	 * Method adds fault node call and nested trigger expression to the node builder.
	 * Ex fault node call:
	 * Sender__fault_1__node__val_out =
	 * 	Common_Faults__fail_to_real(__fault__nominal__sender_out, 1.0, fault__trigger__Sender__fault_1);
	 *
	 * Ex nested trigger expr:
	 * agree_node_output =
	 * 				if fault__trigger_1 then fault__node_1__val_out
	 * 				else if fault__trigger_2 then fault__node_2__val_out
	 * 				...
	 * 				else __fault__nominal__output)
	 *
	 * @param nb NodeBuilder has these expressions added in assert stmts.
	 * @param f Fault that holds this fault node call information.
	 */
	private void addNodeCall(AgreeNodeBuilder nb, Fault f, Map<Fault, Expr> localFaultTriggerMap) {
		List<IdExpr> lhsOfNodeCall = new ArrayList<IdExpr>();
		// make locals for fault node outputs
		// populate outputParamToActualMap
		for (VarDecl v : f.faultNode.outputs) {
			String lhsId = this.createFaultNodeEqId(f.id, v.id);
			AgreeVar actual = new AgreeVar(lhsId, v.type, f.faultStatement);
			nb.addLocal(actual);
			lhsOfNodeCall.add(new IdExpr(lhsId));
			f.outputParamToActualMap.put(v.id, actual);
		}
		// Call fault node and put this into lustre node as local equation.
		AgreeEquation eq = new AgreeEquation(lhsOfNodeCall,
				new NodeCallExpr(f.faultNode.id, constructNodeInputs(f, localFaultTriggerMap)), f.faultStatement);
		nb.addLocalEquation(eq);
	}

	/**
	 * Adds the nested lustre stmt that constrains agree node output based
	 * on which fault trigger is active. Thus, the agree node output reflects
	 * the fault node call linked to a specific trigger.
	 * Ex nested trigger expr:
	 * agree_node_output =
	 * 				if fault__trigger_1 then fault__node_1__val_out
	 * 				else if fault__trigger_2 then fault__node_2__val_out
	 * 				...
	 * 				else __fault__nominal__output)
	 *
	 * @param nb NodeBuilder that will have this assertion added to it.
	 * @param localFaultTriggerMap Map<Fault, Expr> from fault to trigger expr.
	 * @param lhsWithStmtName String of agree_node_output.
	 */
	private void addTriggerExpr(AgreeNodeBuilder nb, Map<Fault, Expr> localFaultTriggerMap,
			String lhsWithStmtName) {
		List<Pair> list = faultyVarsExpr.get(lhsWithStmtName);
		String nomId = createNominalId(lhsWithStmtName);
		Expr defaultExpr = new IdExpr(nomId);
		List<TriggerFaultOutPair> triggerList = new ArrayList<>();
		boolean isOnlyAsym = true;
		Expr outputExpr = null;
		// Go through pairs of the list and create with statements.
		for (Pair pair : list) {
			if (isAsymmetric(pair.f)) {
				continue;
			}
			isOnlyAsym = false;
			outputExpr = pair.ex;
			// base : replace the expression with nominal expression
			// repl : go from the fault to the actual
			// toAssign: createNestedUpdateExpr using base, repl
			//Expr base = replPathIdExpr(pair.ex, defaultExpr);
			//Expr repl = faultToActual(pair.f, pair.ex);
			//defaultExpr = SafetyUtil.createNestedUpdateExpr(base, repl);
			Expr faultNodeOut = faultToActual(pair.f, pair.ex);
			// Collect elements to build the nested if then else stmt described above.
			Expr ftTrigger = localFaultTriggerMap.get(pair.f);
			triggerList.add(new TriggerFaultOutPair(ftTrigger, faultNodeOut));
		}
		// Send the list of triggers with associated fault out statements
		// to a recursive function that builds the nested if-then-else statement.
		// If there are only asym faults here, then we do not want to add
		// anything in the sender node regarding fault nominal.
		if (!isOnlyAsym) {
			nb.addAssertion(new AgreeStatement("Adding new safety analysis BinaryExpr",
					new BinaryExpr(outputExpr, BinaryOp.EQUAL,
							createNestedIfThenElseExpr(triggerList, defaultExpr, 0)),
					null));
		}
	}

	/**
	 * Replaces original expression with a nested record update expr.
	 * ex: fault__nominal -> output{val:= fault__nominal}
	 *
	 * @param original Original expression
	 * @param toAssign What needs to be replaced and assigned in original
	 * @return returns completed expression
	 */
	private Expr replPathIdExpr(Expr original, Expr toAssign) {
		if (original instanceof IdExpr) {
			return toAssign;
		} else if (original instanceof RecordAccessExpr) {
			RecordAccessExpr rae = (RecordAccessExpr) original;
			Expr newBase = replPathIdExpr(rae.record, toAssign);
			return new RecordAccessExpr(newBase, rae.field);
		} else if (original instanceof ArrayAccessExpr) {
			ArrayAccessExpr aae = (ArrayAccessExpr) original;
			Expr newBase = replPathIdExpr(aae.array, aae.index);
			return new ArrayAccessExpr(newBase, aae.index);
		} else {
			new Exception("Problem with record expressions in safety analysis");
			return null;
		}
	}

	/**
	 * Recursive function creates nested if-then-else expression given a list of pairs
	 * linking the trigger and fault output.
	 * agree_node_output =
	 * 				if fault__trigger_1 then fault__node_1__val_out
	 * 				else if fault__trigger_2 then fault__node_2__val_out
	 * 				...
	 * 				else __fault__nominal__output)
	 *
	 * @param list List<TriggerFaultOutPair> links the trigger stmt with the
	 * 				fault node output.
	 * @param nominal Expr of __fault__nominal__output for the "else" portion.
	 * @param index Recursive function begins at index 0.
	 * @return Expr holding nested IfThenElseExpr.
	 */
	private Expr createNestedIfThenElseExpr(List<TriggerFaultOutPair> list, Expr nominal, int index) {
		if (index > list.size() - 1) {
			return nominal;
		} else if (index == list.size() - 1) {
			return new IfThenElseExpr(list.get(index).trigger, list.get(index).faultOut, nominal);
		} else {
			return new IfThenElseExpr(list.get(index).trigger, list.get(index).faultOut,
					createNestedIfThenElseExpr(list, nominal, index + 1));
		}
	}

	/**
	 * Finds the lustre expr for output that fault is attached to.
	 * Ex:
	 * val_out -> Sender__fault_3__node__val_out
	 *
	 * @param f Fault in question
	 * @param ex Expression of output
	 * @return Lustre expression with id set correctly.
	 */
	private Expr faultToActual(Fault f, Expr ex) {
		// Match pair.ex -> key of faultOutputMap
		// If this expression is not in map, return exception message
		String outputName = f.faultOutputMap.get(ex);
		if (outputName == null) {
			new SafetyException("Cannot find fault output for fault " + f.id);
		}
		// Use outputName to get value from outputParamToActualMap
		AgreeVar actual = f.outputParamToActualMap.get(outputName);
		if (f.outputParamToActualMap.isEmpty()) {
			new SafetyException("Something went wrong with fault output parameter. Fault is: " + f.id + " and expr is: "
					+ ex.toString());
		}
		// Create IdExpr out of actual string
		return new IdExpr(actual.id);
	}

	/**
	 * Returns mapping from eq vars to lustre names.
	 *
	 * @param f Fault in question.
	 * @param eqVars safety eq vars in this fault statement
	 * @return Map<String, String> maps the fault id to the safetyEqVars
	 */
	public Map<String, String> constructEqIdMap(Fault f, List<AgreeVar> eqVars) {
		HashMap<String, String> theMap = new HashMap<>();
		for (AgreeVar eqVar : eqVars) {
			theMap.put(eqVar.id, createFaultEqId(f.id, eqVar.id));
		}
		return theMap;
	}

	/**
	 * Renames safety eq stmts to lustre names. Uses renameEqId
	 * as helper method.
	 *
	 * @param faults List of faults on this node that need eq stmts renamed.
	 * @return List of faults with eq stmts renamed.
	 */
	public List<Fault> renameFaultEqs(List<Fault> faults) {
		List<Fault> newFaults = new ArrayList<>();
		for (Fault f : faults) {
			Map<String, String> idMap = constructEqIdMap(f, f.safetyEqVars);
			newFaults.add(this.renameEqId(f, idMap));
		}
		return newFaults;
	}

	/**
	 * Renames eq var id to match lustre name.
	 * Ex:
	 * eq some_var : bool;
	 * Sender_fault_1_some_var : bool;
	 *
	 * @param f Fault with safety eq var stmts.
	 * @param idMap Map<String, String> from user defined var to lustre name.
	 * @return Returns fault with var renamed.
	 */
	public Fault renameEqId(Fault f, Map<String, String> idMap) {
		Fault newFault = new Fault(f);
		newFault.safetyEqVars.clear();
		newFault.safetyEqAsserts.clear();
		newFault.faultOutputMap.clear();
		newFault.faultInputMap.clear();

		if (!f.triggers.isEmpty()) {
			throw new SafetyException("User-defined triggers are currently unsupported.");
		}

		// update the variable declarations
		for (AgreeVar eq : f.safetyEqVars) {
			if (idMap.containsKey(eq.id)) {
				eq = new AgreeVar(idMap.get(eq.id), eq.type, eq.reference);
			}
			newFault.safetyEqVars.add(eq);
		}

		ReplaceIdVisitor visitor = new ReplaceIdVisitor(idMap);
		for (AgreeStatement s : f.safetyEqAsserts) {
			newFault.safetyEqAsserts.add(visitor.visit(s));
		}

		for (Map.Entry<Expr, String> element : f.faultOutputMap.entrySet()) {
			newFault.faultOutputMap.put(element.getKey().accept(visitor), element.getValue());
		}

		for (Map.Entry<String, Expr> element : f.faultInputMap.entrySet()) {
			newFault.faultInputMap.put(element.getKey(), element.getValue().accept(visitor));
		}

		return newFault;
	}

	/**
	 * Used by addNominalVars: Finds var of interest in list with the id
	 * matching the string sent in. Returns this var.
	 *
	 * @param vars List of AgreeVar
	 * @param id String of the id needed to find in list.
	 * @return Returns AgreeVar with id matching that of string.
	 */
	public static AgreeVar findVar(List<AgreeVar> vars, String id) {
		for (AgreeVar v : vars) {
			if (v.id.equals(id)) {
				return v;
			}
		}
		return null;
	}

	/**
	 * Method populates faultyVarsExpr map with faults associated with outputs
	 * they are attached to.
	 * Return map is :
	 * 		output1 -> {(outputExpr, fault1), (outputExpr, fault2)...}
	 *
	 * @param faults List of all faults in this agree node.
	 * @param node The agree node.
	 * @return HashMap<String, List<Pair>> faultyVarsExpr map.
	 */
	private HashMap<String, List<Pair>> gatherFaultyOutputs(List<Fault> faults, AgreeNode node) {
		HashMap<String, List<Pair>> outputMap = new HashMap<String, List<Pair>>();
		String id = "";
		for (Fault f : faults) {
			for (Expr ide : f.faultOutputMap.keySet()) {
				id = AgreeUtils.getExprRoot(ide).id;
				addIdToMap(outputMap, ide, id, f);
			}
		}
		return outputMap;
	}

	/**
	 * Method adds the output expression to the output map pair list.
	 * (output1 -> {(outputExpr, fault1), (outputExpr, fault2)...})
	 *
	 * @param outputMap The map to populate.
	 * @param ex Output expression
	 * @param id String "output"
	 * @param f Fault that will be in pair for this output.
	 */
	private void addIdToMap(HashMap<String, List<Pair>> outputMap, Expr ex, String id, Fault f) {
		Pair pair = new Pair(ex, f);
		if (outputMap.containsKey(id)) {
			outputMap.get(id).add(pair);
		} else {
			List<Pair> list = new ArrayList<Pair>();
			list.add(pair);
			outputMap.put(id, list);
		}
	}

	/**
	 * Create fault event id given base.
	 * The base corresponds to agreeNodeName__faultName
	 *
	 * @param base nodeName__faultName
	 * @return __fault__event__nodeName__faultName
	 */
	public String createFaultEventId(String base) {
		return "__fault__event__" + base;
	}

	/**
	 * Create indep active given base.
	 * The base corresponds to agreeNodeName__faultName
	 *
	 * @param base nodeName__faultName
	 * @return __fault__independently__active__nodeName__faultName
	 */
	public String createFaultIndependentActiveId(String base) {
		return "__fault__independently__active__" + base;
	}

	/**
	 * Create dep active given base.
	 * The base corresponds to agreeNodeName__faultName
	 *
	 * @param base nodeName__faultName
	 * @return __fault__dependently__active__nodeName__faultName
	 */
	public String createFaultDependentActiveId(String base) {
		return "__fault__dependently__active__" + base;
	}

	/**
	 * Create trigger given base.
	 * The base corresponds to agreeNodeName__faultName
	 *
	 * @param base nodeName__faultName
	 * @return fault__trigger__nodeName__faultName
	 */
	public String createFaultNodeTriggerId(String base) {
		return "fault__trigger__" + base;
	}

	/**
	 * Create nominal id given base.
	 * The base corresponds to agreeNodeName__faultName
	 *
	 * @param output of the Agree node
	 * @return __fault__nominal__output string
	 */
	public String createNominalId(String output) {
		return "__fault__nominal__" + output;
	}

	/**
	 * Create new safety eq var name given fault name and variable name.
	 *
	 * @param fault Fault this eq stmt is in.
	 * @param var Var name of this eq stmt.
	 * @return faultName__varName
	 */
	public String createFaultEqId(String fault, String var) {
		return fault + "__" + var;
	}

	/**
	 * Create fault node eq id stmt.
	 *
	 * @param fault Name of fault
	 * @param var name of var
	 * @return faultName__node__varName
	 */
	public String createFaultNodeEqId(String fault, String var) {

		return fault + "__node__" + var;
	}

	/**
	 * Method gathers fault statements within this agree node and
	 * begins processing.
	 * Creates FaultASTBuilder object, creates faults for each fault
	 * stmt, populates asym maps, separates asym/sym faults for
	 * separate processing, and returns processed faults.
	 *
	 * @param globalLustreNodes List of Nodes
	 * @param node This agree node
	 * @param isTop flag to determine if this is the top node of the program.
	 * @return list of faults associated with the fault stmts in this node.
	 */
	public List<Fault> gatherFaults(List<Node> globalLustreNodes, AgreeNode node, boolean isTop) {
		List<SpecStatement> specs = SafetyUtil.collapseAnnexes(SafetyUtil.getSafetyAnnexes(node, isTop));

		List<Fault> faults = new ArrayList<>();
		// reset fault count for the new Agree Node
		FaultASTBuilder.resetFaultCounter();

		// Before looping through spec statements, separate out the asymmetric multiple
		// faults on a single output with the sym/asym single faults on a single output.
		// 1. Collect all fault statements and put into list.
		// Do not collect any that are disabled.
		// 2. Separate out multiple asym faults on one output and single faults on one output.
		// 3. Perform necessary processing on each of these lists.
		List<FaultStatement> allFaultStmts = new ArrayList<FaultStatement>();
		for (SpecStatement s : specs) {
			if (s instanceof FaultStatement) {
				if (!isDisabled((FaultStatement) s)) {
					allFaultStmts.add((FaultStatement) s);
				}
			}
		}
		List<FaultStatement> remainderFS = new ArrayList<FaultStatement>();
		Map<String, List<FaultStatement>> multipleAsymFS = new HashMap<String, List<FaultStatement>>();
		separateFaultStmts(allFaultStmts, remainderFS, multipleAsymFS);

		// the map multipleAsymFS holds output name as key with a list of fault
		// stmts associated with that output. We loop through these keys and
		// for each fault group, create an AsymFaultASTBuilder object which
		// processes these stmts, creates faults, and creates associated comm nodes.
		for (String output : multipleAsymFS.keySet()) {
			if (multipleAsymFS.get(output).size() == 1) {
				continue;
			}
			AsymFaultASTBuilder asymBuilder = new AsymFaultASTBuilder(globalLustreNodes, node);
			List<Fault> safetyFaults = asymBuilder.processFaults(multipleAsymFS.get(output));

			mapAsymCompOutputToCommNodeIn = asymBuilder.getMapAsymCompOutputToCommNodeIn();
			mapCommNodeToInputs = asymBuilder.getMapCommNodeToInputs();
			mapCommNodeOutputToConnections = asymBuilder.getMapCommNodeOutputToConnections();
			mapSenderToReceiver = asymBuilder.getMapSenderToReceiver();
			mapCompNameToCommNodes = asymBuilder.getMapCompNameToCommNodes();

			// Create local mapAsymFaultToCompName
			if (node.compInst instanceof ComponentInstance) {
				ComponentInstance comp = node.compInst;
				String name = comp.getName();
				for (Fault safetyFault : safetyFaults) {
					mapAsymFaultToCompName.put(safetyFault, name);
					// Create mapping from fault to associated commNodes.
					// Use mapping from fault to comp name, then map from comp name to
					// comm nodes.
					mapAsymFaultToCommNodes.put(safetyFault, mapCompNameToCommNodes.get(name));
				}
			}
			faults.addAll(safetyFaults);
		}

		// Now process all the rest of the fault stmts.
		for (SpecStatement s : remainderFS) {
			if (s instanceof FaultStatement) {
				FaultStatement fs = (FaultStatement) s;
				FaultASTBuilder builder = new FaultASTBuilder(globalLustreNodes, node);
				// Process fault determines if we have a
				// symmetric or asymmetric fault and builds it accordingly.
				Fault safetyFault = builder.processFault(fs);
				// If this is an asymmetric fault we just built,
				// collect the mappings that were created by FaultASTBuilder
				// and use them later when we reach the top node to add assert stmts
				// to main lustre node.
				if (isAsymmetric(fs)) {
					mapAsymCompOutputToCommNodeIn = builder.getMapAsymCompOutputToCommNodeIn();
					mapCommNodeToInputs = builder.getMapCommNodeToInputs();
					mapCommNodeOutputToConnections = builder.getMapCommNodeOutputToConnections();
					mapSenderToReceiver = builder.getMapSenderToReceiver();
					mapCompNameToCommNodes = builder.getMapCompNameToCommNodes();
					// Create local mapAsymFaultToCompName
					if (node.compInst instanceof ComponentInstance) {
						ComponentInstance comp = node.compInst;
						String name = comp.getName();
						mapAsymFaultToCompName.put(safetyFault, name);
						// Create mapping from fault to associated commNodes.
						// Use mapping from fault to comp name, then map from comp name to
						// comm nodes.
						mapAsymFaultToCommNodes.put(safetyFault, mapCompNameToCommNodes.get(name));
					}
				}
				faults.add(safetyFault);
			}
		}
		return faults;
	}

	/**
	 * Checks fault stmt for DisableStatement. If found, returns value
	 * of disable statement. Else returns false.
	 * @param fs FaultStatement
	 * @return bool: isDisabled
	 */
	private Boolean isDisabled(FaultStatement fs) {
		Boolean disableFound = false;
		List<FaultSubcomponent> subcomps = fs.getFaultDefinitions();
		for (FaultSubcomponent fsc : subcomps) {
			if (fsc instanceof DisableStatement) {
				disableFound = true;
				DisableStatement ds = (DisableStatement) fsc;
				BooleanLiteral bl = ds.getCond();
				return bl.getValue();
			}
		}
		return disableFound;
	}

	/**
	 * This method uses a list of fault statements and divides them into multiple asym faults on
	 * a single output and everything else.
	 *
	 * @param allFS List<FaultStatement> All fault statements in this agree node
	 * @param remainderFS List<FaultStatement> List to add all single asym faults on single output AND
	 * 							sym faults.
	 * @param multipleAsymFS List<FaultStatement> List to add all multiple asym faults on single output.
	 */
	private void separateFaultStmts(List<FaultStatement> allFS, List<FaultStatement> remainderFS,
			Map<String, List<FaultStatement>> asymMap) {

		// 1. Add sym faults to remainder list and process asym faults such that they
		// are inserted into a map from outputName -> List(fault statements).
		// 2. If the list of fault statements associated with an output is of size 1,
		// append these to remainderFS list.
		// Else append to multipleAsymFS list.
		for (FaultStatement fs : allFS) {
			int count = fs.getFaultDefinitions().size();
			for (FaultSubcomponent fc : fs.getFaultDefinitions()) {
				count--;
				if (fc instanceof PropagationTypeStatement) {
					if (((PropagationTypeStatement) fc).getPty() instanceof asymmetric) {
						// Asym fault needs to be processed further and added to map.
						String outputName = getOutputNameFromFaultStatement(fs);
						if (outputName.isEmpty()) {
							new SafetyException(
									"Error processing asymmetric fault: the output name is undefined for fault statement:"
											+ fs.getName());
						} else {
							List<FaultStatement> tempAsymFaults = new ArrayList<FaultStatement>();
							tempAsymFaults.add(fs);
							if (asymMap.containsKey(outputName)) {
								asymMap.get(outputName).addAll(tempAsymFaults);
							} else {
								asymMap.put(outputName, tempAsymFaults);
							}
							break;
						}
					} else {
						// symmetric faults added to remainderFS list
						remainderFS.add(fs);
						break;
					}
				}
				// If we haven't broken out of the loop and we have traversed
				// all definitions, then we have no prop type stmt. It is sym.
				if (count == 0) {
					remainderFS.add(fs);
				}
			}
		}
		// Now all sym faults in remainder list and all asym faults in map.
		// Process map and add single asym faults to remainder list.
		// Add multiples to multiple list.
		for (String key : asymMap.keySet()) {
			if (asymMap.get(key).size() == 1) {
				remainderFS.addAll(asymMap.get(key));
			}
		}
	}

	/**
	 * Given a fault statement, returns the string name of the
	 * output this fault stmt is connected to.
	 *
	 * @param fs FaultStatement in question
	 * @return String name of the output
	 */
	private String getOutputNameFromFaultStatement(FaultStatement fs) {
		String output = "";
		for (FaultSubcomponent fc : fs.getFaultDefinitions()) {
			if (fc instanceof OutputStatement) {
				EList<NamedElement> outputType = ((OutputStatement) fc).getNom_conn();
				// TODO: Assume the output is first in list. (????)
				if (outputType.size() > 0) {
					NamedElement id = outputType.get(0);
//					output = id.getBase().getName();
					output = id.toString();
					return output;
				}
				break;
			}
		}

		return output;
	}

	/**
	 * Gather hardware faults (dep faults) from the agree node fault
	 * statements and process them.
	 * @param globalLustreNodes List of Nodes
	 * @param node Agree node
	 * @param isTop flag states if this agree node is top of program.
	 * @return List of hardware faults in the agree node annex.
	 */
	public List<HWFault> gatherHWFaults(List<Node> globalLustreNodes, AgreeNode node, boolean isTop) {
		List<SpecStatement> specs = SafetyUtil.collapseAnnexes(SafetyUtil.getSafetyAnnexes(node, isTop));

		List<HWFault> hwFaults = new ArrayList<>();

		for (SpecStatement s : specs) {
			if (s instanceof HWFaultStatement) {
				HWFaultStatement hwfs = (HWFaultStatement) s;
				HWFaultASTBuilder builder = new HWFaultASTBuilder(globalLustreNodes, node);
				HWFault safetyFault = builder.buildHWFault(hwfs);
				hwFaults.add(safetyFault);
			}
		}
		return hwFaults;
	}

	/**
	 * Method gets the analysis statement located in the top node
	 * system implementation. Determines if max no. faults or prob.
	 *
	 * @param node Top agree node.
	 * @return Analysis behavior stated in the annex.
	 */
	public AnalysisBehavior gatherTopLevelFaultAnalysis(AgreeNode node) {
		AnalysisBehavior ab = null;
		boolean found = false;
		List<SpecStatement> specs = SafetyUtil.collapseAnnexes(SafetyUtil.getSafetyAnnexes(node, true));

		for (SpecStatement s : specs) {
			if (s instanceof AnalysisStatement) {
				AnalysisStatement as = (AnalysisStatement) s;
				ab = as.getBehavior();
				if (ab instanceof FaultCountBehavior) {
					int maxFaults = Integer.valueOf(((FaultCountBehavior) ab).getMaxFaults());
					if (maxFaults < 0) {
						throw new SafetyException("Maximum number of faults must be non-negative.");
					}
				} else if (ab instanceof ProbabilityBehavior) {
					double minProbability = Double.valueOf(((ProbabilityBehavior) ab).getProbabilty());
					if (minProbability > 1 || minProbability < 0) {
						throw new SafetyException("Probability out of range [0, 1]");
					}
				}
				if (found) {
					throw new SafetyException(
							"Multiple analysis specification statements found.  Only one can be processed");
				}
				found = true;
			}
		}
		if (!found) {
			throw new SafetyException("No analysis statement; unable to run safety analysis");
		}
		return ab;
	}

	/**
	 * Identify the fault associated with a ComponentInstance
	 * given the fault name and the component path.
	 *
	 * @param faultName Name of fault to be found
	 * @param faultComp_Path Component instance path
	 * @return Fault with name matching string.
	 */
	public BaseFault findFaultInCompInst(String faultName, NamedElement faultComp_Path) {
		List<ComponentInstance> compInsts = new ArrayList<ComponentInstance>(faultMap.keySet());

		for (ComponentInstance compInst : compInsts) {
			if (compInst.getName().equals(faultComp_Path.getName())) {
				List<Fault> faults = new ArrayList<Fault>(faultMap.get(compInst));
				for (Fault fault : faults) {
					if (fault.name.equals(faultName)) {
						return fault;
					}
				}
			}
		}

		compInsts = new ArrayList<ComponentInstance>(hwfaultMap.keySet());
		for (ComponentInstance compInst : compInsts) {
			if (compInst.getName().equals(faultComp_Path.getName())) {
				List<HWFault> hwfaults = new ArrayList<HWFault>(hwfaultMap.get(compInst));
				for (HWFault hwfault : hwfaults) {
					if (hwfault.name.equals(faultName)) {
						return hwfault;
					}
				}
			}
		}
		return null;
	}

	/**
	 * Gathers list of fault activations for hardware faults.
	 * Adds to faultActivations list.
	 *
	 * @param node Agree node with these spec statements.
	 */
	private void gatherFaultActivation(AgreeNode node) {
		List<SpecStatement> specs = SafetyUtil.collapseAnnexes(SafetyUtil.getSafetyAnnexes(node, true));

		for (SpecStatement s : specs) {
			if (s instanceof ActivationStatement) {
				ActivationStatement as = (ActivationStatement) s;
				String agreeBoolVarName = as.getAgreeBoolVarName();
				// the following can be null
				NamedElement agreeComp_Path = as.getAgreeComp_Path();
				String agreeBoolVarPrefix = "";
				if (agreeComp_Path != null) {
					agreeBoolVarPrefix = agreeComp_Path.getName() + "__";
				}
				// compose agreeBoolVarName in main node input
				agreeBoolVarName = agreeBoolVarPrefix + agreeBoolVarName;
				String faultName = as.getFaultName();
				// the following can be null
				NamedElement faultComp_Path = as.getFaultComp_Path();
				BaseFault fault = null;
				if (faultComp_Path != null) {
					fault = findFaultInCompInst(faultName, faultComp_Path);
				}
				if (fault != null) {
					FaultActivationAssignment faultActAssign = new FaultActivationAssignment(agreeBoolVarName, fault,
							faultComp_Path.getName());
					faultActivations.add(faultActAssign);
				} else {
					throw new SafetyException("Unable to identify fault in fault activation statement:"
							+ " a possibility is that this fault is disabled.");
				}
			}
		}
	}

	/**
	 * Method gathers source and destination faults for hardware
	 * (dependent) faults. Adds these to propagations list.
	 *
	 * @param node Agree Node with these spec statements.
	 */
	public void gatherFaultPropagation(AgreeNode node) {
		List<SpecStatement> specs = SafetyUtil.collapseAnnexes(SafetyUtil.getSafetyAnnexes(node, true));
		for (SpecStatement s : specs) {
			if (s instanceof PropagateStatement) {
				PropagateStatement ps = (PropagateStatement) s;
				Iterator<String> srcFaultIt = ps.getSrcFaultList().iterator();
				Iterator<NamedElement> srcCompPathIt = ps.getSrcComp_path().iterator();

				// create a SafetyPropagation
				BaseFault srcFault = null;
				// for each src fault name and path, locate the fault
				while (srcFaultIt.hasNext() && srcCompPathIt.hasNext()) {
					NamedElement srcCompPath = srcCompPathIt.next();
					String srcFaultName = srcFaultIt.next();
					srcFault = findFaultInCompInst(srcFaultName, srcCompPath);
					if (srcFault != null) {
						// for each destination fault name and path, locate the fault
						BaseFault destFault = null;
						Iterator<String> destFaultIt = ps.getDestFaultList().iterator();
						Iterator<NamedElement> destCompPathIt = ps.getDestComp_path()
								.iterator();
						while (destFaultIt.hasNext() && destCompPathIt.hasNext()) {
							NamedElement destCompPath = destCompPathIt.next();
							String destFaultName = destFaultIt.next();
							destFault = findFaultInCompInst(destFaultName, destCompPath);
							SafetyPropagation propagation = new SafetyPropagation(srcFault, destFault);
							propagations.add(propagation);
						}
					} else {
						throw new SafetyException("Unable to identify fault in propagation statement:"
								+ " a possibility is that the source or destination fault is disabled.");
					}
				}
			}
		}
	}

	/**
	 * Adds "__" for path delimiters - replace "."
	 *
	 * @param path The path to be delimited.
	 * @param var Name of var
	 * @return new path name for var.
	 */
	public String addPathDelimiters(List<String> path, String var) {
		String id = "";
		for (String p : path) {
			id = id + p + "__";
		}
		return id + var;
	}

	/**
	 * Method latches the event of a fault so that it becomes permanent.
	 *
	 * @param varId Id of fault to be latched.
	 * @param eventExpr The event expression of this fault.
	 * @return Returns binary expression that latches this fault (now perm.)
	 */
	public Expr createPermanentExpr(Expr varId, Expr eventExpr) {
		Expr latch = new BinaryExpr(eventExpr, BinaryOp.ARROW,
				new BinaryExpr(eventExpr, BinaryOp.OR, new UnaryExpr(UnaryOp.PRE, varId)));
		Expr equate = new BinaryExpr(varId, BinaryOp.EQUAL, latch);

		return equate;
	}

	/**
	 * Method creates transient fault - not permanently latched.
	 *
	 * @param varId Id of fault
	 * @param expr Expression of transience.
	 * @return Return id = transExpr
	 */
	public Expr createTransientExpr(Expr varId, Expr expr) {
		Expr equate = new BinaryExpr(varId, BinaryOp.EQUAL, expr);
		return equate;
	}

	/**
	 * Constrains fault activity to permanent expression.
	 * If user attempts to create transient, exception is thrown.
	 *
	 * @param f Fault
	 * @param nameBase Lustre name of fault
	 * @param builder The node builder that will have these assertions added.
	 */
	public void constrainFaultActive(Fault f, String nameBase, AgreeNodeBuilder builder) {
		IdExpr independentlyActiveExpr = new IdExpr(this.createFaultIndependentActiveId(nameBase));
		IdExpr dependentlyActiveExpr = new IdExpr(this.createFaultDependentActiveId(nameBase));
		IdExpr independentEventExpr = new IdExpr(this.createFaultEventId(nameBase));
		List<Expr> faultExprs = new ArrayList<>();
		// collect the list of source faults in the propagations
		// whose target fault is the current fault
		// the names of those source faults are created through
		// createFaultIndependentActiveId
		getSrcFaultExprList(f, faultExprs);
		// create a disjunction of the source faults as the triggering event
		// for the dependent fault
		Expr dependentEventExpr = buildFaultDisjunctionExpr(faultExprs, 0);
		Expr assertIndependentExpr;
		Expr assertDependentExpr;

		TemporalConstraint tc = f.duration.getTc();
		if (tc instanceof PermanentConstraint) {
			assertIndependentExpr = createPermanentExpr(independentlyActiveExpr, independentEventExpr);
			assertDependentExpr = createPermanentExpr(dependentlyActiveExpr, dependentEventExpr);
		} else if (tc instanceof TransientConstraint) {
			System.out.println("WARNING: ignoring duration on transient faults");
			assertIndependentExpr = createTransientExpr(independentlyActiveExpr, independentEventExpr);
			assertDependentExpr = createTransientExpr(dependentlyActiveExpr, dependentEventExpr);
		} else {
			throw new SafetyException("Unknown constraint type during translation of fault " + f.id
					+ ". Constraint must be 'permanent'.");
		}
		builder.addAssertion(new AgreeStatement("", assertIndependentExpr, f.faultStatement));
		builder.addAssertion(new AgreeStatement("", assertDependentExpr, f.faultStatement));
	}

	/**
	 * Method constrains hardware faults to be permanent.
	 *
	 * @param hwf HardwareFault to be constrained.
	 * @param nameBase Name of fault.
	 * @param builder AgreeNodeBuilder will have assertions added.
	 */
	public void constrainFaultActive(HWFault hwf, String nameBase, AgreeNodeBuilder builder) {
		IdExpr independentlyActiveExpr = new IdExpr(this.createFaultIndependentActiveId(nameBase));
		IdExpr dependentlyActiveExpr = new IdExpr(this.createFaultDependentActiveId(nameBase));
		IdExpr independentEventExpr = new IdExpr(this.createFaultEventId(nameBase));

		List<Expr> faultExprs = new ArrayList<>();
		// collect the list of source faults in the propagations
		// whose target fault is the current fault
		// the names of those source faults are created through
		// createFaultIndependentActiveId
		getSrcFaultExprList(hwf, faultExprs);
		// create a disjunction of the source faults as the triggering event
		// for the dependent fault
		Expr dependentEventExpr = buildFaultDisjunctionExpr(faultExprs, 0);

		Expr assertIndependentExpr;
		Expr assertDependentExpr;

		TemporalConstraint tc = hwf.duration.getTc();
		if (tc instanceof PermanentConstraint) {
			assertIndependentExpr = createPermanentExpr(independentlyActiveExpr, independentEventExpr);
			assertDependentExpr = createPermanentExpr(dependentlyActiveExpr, dependentEventExpr);
		} else if (tc instanceof TransientConstraint) {
			System.out.println("WARNING: ignoring duration on transient faults");
			assertIndependentExpr = createTransientExpr(independentlyActiveExpr, independentEventExpr);
			assertDependentExpr = createTransientExpr(dependentlyActiveExpr, dependentEventExpr);
		} else {
			throw new SafetyException("Unknown constraint type during translation of fault " + hwf.id
					+ ". Constraint must be 'permanent'.");
		}
		builder.addAssertion(new AgreeStatement("", assertIndependentExpr, hwf.hwFaultStatement));
		builder.addAssertion(new AgreeStatement("", assertDependentExpr, hwf.hwFaultStatement));
	}

	/**
	 * Method links trigger with indep/dep active stmt.
	 *
	 * Ex:
	 *   assert (Sender__fault__trigger__Sender__fault_1 =
	 *   	(__fault__independently__active__Sender__Sender__fault_1
	 *   or  __fault__dependently__active__Sender__Sender__fault_1));

	 *
	 * @param f Fault
	 * @param path path of fault (where in program it is located)
	 * @param base
	 * @param builder Node builder that will have these assertions added
	 */
	public void mapFaultActiveToNodeInterface(Fault f, List<String> path, String base, AgreeNodeBuilder builder) {
		String interfaceVarId = addPathDelimiters(path, this.createFaultNodeTriggerId(f.id));
		String indepentlyActiveVarId = this.createFaultIndependentActiveId(base);
		String depentlyActiveVarId = this.createFaultDependentActiveId(base);

		// Create map from fault to its relative path
		// for the counterexample layout
		for (String str : path) {
			mapFaultToPath.put(f, str);
		}
		Expr equate = new BinaryExpr(new IdExpr(interfaceVarId), BinaryOp.EQUAL,
				new BinaryExpr(new IdExpr(indepentlyActiveVarId), BinaryOp.OR, new IdExpr(depentlyActiveVarId)));
		builder.addAssertion(new AgreeStatement("", equate, f.faultStatement));
	}

	/**
	 * Method maps the trigger to dep/indep active ids.
	 * Ex:
	 *   assert (Sender__fault__trigger__Sender__fault_2 =
	 *   (__fault__independently__active__Sender__Sender__fault_2
	 *   or __fault__dependently__active__Sender__Sender__fault_2));

	 *
	 * @param hwf
	 * @param path
	 * @param base
	 * @param builder
	 */
	public void mapFaultActiveToNodeInterface(HWFault hwf, List<String> path, String base, AgreeNodeBuilder builder) {
		String interfaceVarId = addPathDelimiters(path, this.createFaultNodeTriggerId(hwf.id));
		String indepentlyActiveVarId = this.createFaultIndependentActiveId(base);
		String depentlyActiveVarId = this.createFaultDependentActiveId(base);

		Expr equate = new BinaryExpr(new IdExpr(interfaceVarId), BinaryOp.EQUAL,
				new BinaryExpr(new IdExpr(indepentlyActiveVarId), BinaryOp.OR, new IdExpr(depentlyActiveVarId)));
		builder.addAssertion(new AgreeStatement("", equate, hwf.hwFaultStatement));
	}

	/**
	 * Method updates fault map with the path, also sets path for each
	 * fault. (Path corresponds to agree node.)
	 *
	 * @param currentNode Agree node with this fault.
	 * @param path Path name (node name)
	 */
	public void collectFaultPath(AgreeNode currentNode, List<String> path) {

		List<Fault> faults = this.faultMap.get(currentNode.compInst);
		List<HWFault> hwfaults = this.hwfaultMap.get(currentNode.compInst);

		// Add unconstrained input and constrained local to represent fault event and
		// whether or not fault is currently active.
		int index = 0;
		for (Fault f : faults) {
			// update fault name base
			f.setPath(path);
			// update fault list
			faults.set(index, f);
			index++;
		}
		// update faultMap
		this.faultMap.put(currentNode.compInst, faults);

		index = 0;
		for (HWFault hwf : hwfaults) {
			// update fault name base
			hwf.setPath(path);
			// update fault list
			hwfaults.set(index, hwf);
			index++;
		}
		// update faultMap
		this.faultMap.put(currentNode.compInst, faults);
		this.hwfaultMap.put(currentNode.compInst, hwfaults);

		// repeating it for the decendents of the node
		for (AgreeNode n : currentNode.subNodes) {
			List<String> ext = new ArrayList<>(path);
			ext.add(n.id);
			collectFaultPath(n, ext);
		}
	}

	/**
	 * Method adds all top level fault information to main node.
	 * Assume hist stmts
	 * Locals
	 * Count constraints
	 * Fault assertions
	 * Fault node calls
	 * Changes connections for communication nodes.
	 *
	 * @param nb Node builder will hold all this new info.
	 */
	public void addTopLevelAsymFaultDeclarations(AgreeNodeBuilder nb) {
		addAssumeHistStmts(nb);
		addLocalsForCommNodes(nb);
		addAsymCountConstraints(nb);
		addAsymFaultAssertions(nb);
		addAsymNodeCalls(nb);
		changeAsymConnections(nb);
	}

	/**
	 * Method adds activation assertions for hardware faults.
	 * If source is active, then sink is also active.
	 *
	 * @param nb Node builder will have this assertion added to main.
	 */
	private void addTopLevelFaultActivationAssertions(AgreeNodeBuilder nb) {
		for (FaultActivationAssignment faultAct : faultActivations) {
			addFaultActivationAssertion(faultAct.agreeBoolVarName, faultAct.faultActivated, faultAct.faultCompName, nb);
		}
	}

	/**
	 * Method adds fault activations to lower levels.
	 * AgreeVar = fault_trigger
	 *
	 * @param agreeVarName Name of agree var
	 * @param f Fault def
	 * @param faultCompName Name of component with fault
	 * @param nb Node builder will have assertion added
	 */
	private void addFaultActivationAssertion(String agreeVarName, BaseFault f, String faultCompName,
			AgreeNodeBuilder nb) {

		IdExpr trigger = new IdExpr(faultCompName + "__fault__trigger__" + f.getID());
		Expr equate = new BinaryExpr(new IdExpr(agreeVarName), BinaryOp.EQUAL, trigger);
		nb.addAssertion(new AgreeStatement("", equate, topNode.reference));
	}

	/**
	 * Method adds fault information to main node.
	 * This includes locals and inputs. It also checks to see if IVC
	 * analysis is being performed. If so, the fault indep vars are added
	 * to lustre (set equal to false) and given the IVC command.
	 *
	 * @param currentNode Top node
	 * @param nb Node builder has assertions, locals, etc added.
	 */
	public void addTopLevelFaultDeclarations(AgreeNode currentNode, AgreeNodeBuilder nb) {

		List<Fault> faults = this.faultMap.get(currentNode.compInst);
		// Add unconstrained input and constrained local to represent fault event and
		// whether or not fault is currently active.
		for (Fault f : faults) {
			String base = addPathDelimiters(f.path, f.id);
			nb.addInput(new AgreeVar(this.createFaultEventId(base), NamedType.BOOL, f.faultStatement));

			addFaultIndepVarsToLustre(base, f, nb);

			// Add dependent as per usual.
			nb.addInput(new AgreeVar(this.createFaultDependentActiveId(base), NamedType.BOOL, f.faultStatement));

			addToLustreFaultMap(base, f);

			// constrain fault-active depending on transient / permanent & map it to a
			// fault in the node interface
			// take the propagation map when constrainFaultActive
			constrainFaultActive(f, base, nb);
			mapFaultActiveToNodeInterface(f, f.path, base, nb);
		}

		List<HWFault> hwfaults = this.hwfaultMap.get(currentNode.compInst);

		addLocalsAndInputForHWFaults(hwfaults, nb);
		// Add hw faults to lustre fault mapping
		for (HWFault hwf : hwfaults) {
			String base = addPathDelimiters(hwf.path, hwf.id);
			addToLustreHWFaultMap(base, hwf);
		}

		for (AgreeNode n : currentNode.subNodes) {
			addTopLevelFaultDeclarations(n, nb);
		}
	}

	/**
	 * Method adds unconstrained input and constrained local to represent fault event and
	 * whether or not fault is currently active.
	 *
	 * @param hwfaults List of hardware faults to loop through.
	 * @param nb Node builder has inputs added.
	 */
	private void addLocalsAndInputForHWFaults(List<HWFault> hwfaults, AgreeNodeBuilder nb) {
		for (HWFault hwf : hwfaults) {
			String base = addPathDelimiters(hwf.path, hwf.id);

			nb.addInput(new AgreeVar(this.createFaultEventId(base), NamedType.BOOL, hwf.hwFaultStatement));
			nb.addInput(new AgreeVar(this.createFaultIndependentActiveId(base), NamedType.BOOL, hwf.hwFaultStatement));
			nb.addInput(new AgreeVar(this.createFaultDependentActiveId(base), NamedType.BOOL, hwf.hwFaultStatement));

			// constrain fault-active depending on transient / permanent & map it to a
			// fault in the node interface
			// take the propagation map when constrainFaultActive
			constrainFaultActive(hwf, base, nb);
			mapFaultActiveToNodeInterface(hwf, hwf.path, base, nb);
		}
	}

	/**
	 * Method adds fault to lustre names mapping for later use.
	 *
	 * @param base nodeName__faultName used to make lustre name.
	 * @param f Fault to add to map.
	 */
	private void addToLustreFaultMap(String base, Fault f) {
		// add to lustre fault map with the explanatory text (string given for fault
		// definition)
		if (mapFaultToLustreNames.containsKey(f)) {
			mapFaultToLustreNames.get(f).add(this.createFaultIndependentActiveId(base));
			mapFaultToLustreNames.get(f).add(this.createFaultDependentActiveId(base));
		} else {
			List<String> names = new ArrayList<>();
			names.add(this.createFaultIndependentActiveId(base));
			names.add(this.createFaultDependentActiveId(base));
			mapFaultToLustreNames.put(f, names);
		}
	}

	/**
	 * Method adds hw fault to lustre names mapping for later use.
	 *
	 * @param base nodeName__hwfaultName used to make lustre name.
	 * @param f HWFault to add to map.
	 */
	private void addToLustreHWFaultMap(String base, HWFault f) {
		// add to lustre fault map with the explanatory text (string given for fault
		// definition)
		if (mapHWFaultToLustreNames.containsKey(f)) {
			mapHWFaultToLustreNames.get(f).add(this.createFaultIndependentActiveId(base));
			mapHWFaultToLustreNames.get(f).add(this.createFaultDependentActiveId(base));
		} else {
			List<String> names = new ArrayList<>();
			names.add(this.createFaultIndependentActiveId(base));
			names.add(this.createFaultDependentActiveId(base));
			mapHWFaultToLustreNames.put(f, names);
		}
	}

	/**
	 * Here is where fault indep variables are added to lustre.
	 * For fault tree generation, this must be added locally,
	 * assigned to false, and given the --%IVC command.
	 *
	 * @param base String nodeName__faultName
	 * @param f Fault in question
	 * @param nb Node builder has assertions and locals added
	 */
	private void addFaultIndepVarsToLustre(String base, Fault f, AgreeNodeBuilder nb) {
		if (AddFaultsToAgree.getIsVerify()) {
			// If isVerify is true, that means we are performing verify fault analysis
			nb.addInput(new AgreeVar(this.createFaultIndependentActiveId(base), NamedType.BOOL, f.faultStatement));
		} else if (AddFaultsToAgree.getIsGenMCS()) {
			// Else we want to generate mcs.
			// In this case, we add the indep as a local var.
			AgreeVar newVar = new AgreeVar(this.createFaultIndependentActiveId(base), NamedType.BOOL, f.faultStatement);
			// Add this as a local variable to the node builder (and hence later it will be local in the lustre program).
			nb.addLocal(newVar);

			// Then equate this to false
			IdExpr idExpr = new IdExpr(newVar.id);
			AgreeEquation ae = new AgreeEquation(idExpr, new BoolExpr(false), null);
			// And add as a local equation
			nb.addLocalEquation(ae);
			// Add independent fault as ivc element
			nb.addIvcElement(this.createFaultIndependentActiveId(base));
		}

	}

	/*****************************************************************
	 *
	 * # of occurrence-based fault calculations
	 *
	 ******************************************************************/

	/**
	 * Method creates sum expression to count no. of occurrences.
	 * Ex:
	 *   ((if __fault__independently__active__Sender__Sender__fault_1
	 *     then 1 else 0)
	 *  + (if __fault__independently__active__Sender__Sender__fault_2
	 *      then 1 else 0)));

	 * @param cond indep active stmt.
	 * @return Returns if-then-else expr
	 */
	public Expr createSumExpr(Expr cond) {
		return new IfThenElseExpr(cond, new IntExpr(1), new IntExpr(0));
	}

	/**
	 * Get the list of faults that will contribute to the count.
	 *
	 * @param currentNode This agree node.
	 * @param sumExprs Expressions to be summed.
	 */
	public void getFaultCountExprList(AgreeNode currentNode, List<Expr> sumExprs) {
		List<Fault> faults = this.faultMap.get(currentNode.compInst);
		for (Fault f : faults) {
			// only add independently active fault to sumExprs
			String base = addPathDelimiters(f.path, f.id);
			sumExprs.add(createSumExpr(new IdExpr(this.createFaultIndependentActiveId(base))));
		}
		for (HWFault hwf : this.hwfaultMap.get(currentNode.compInst)) {
			String base = addPathDelimiters(hwf.path, hwf.id);
			sumExprs.add(createSumExpr(new IdExpr(this.createFaultIndependentActiveId(base))));
		}
		for (AgreeNode n : currentNode.subNodes) {
			// List<String> ext = new ArrayList<>(path);
			// ext.add(n.id);
			getFaultCountExprList(n, sumExprs);
		}
	}

	/**
	 * Method collect the list of expressions for the source faults
	 * in the propagations whose target fault is the current fault.
	 *
	 * @param f Fault in question (base fault)
	 * @param faultExprs List of all fault expressions.
	 */
	public void getSrcFaultExprList(BaseFault f, List<Expr> faultExprs) {

		for (SafetyPropagation propagation : propagations) {
			if (propagation.destFault.equals(f)) {
				if (propagation.srcFault instanceof HWFault) {
					// use the triggering event for HW fault in the src fault expr, so to get the
					// effect
					// from both hw dependent and hw independent faults
					String interfaceVarId = addPathDelimiters(((HWFault) propagation.srcFault).path,
							this.createFaultNodeTriggerId(((HWFault) propagation.srcFault).id));
					faultExprs.add(new IdExpr(interfaceVarId));
				} else if (propagation.srcFault instanceof Fault) {
					String base = addPathDelimiters(((Fault) propagation.srcFault).path,
							((Fault) propagation.srcFault).id);
					faultExprs.add(new IdExpr(this.createFaultIndependentActiveId(base)));
				}
			}
		}
	}

	/**
	 * Recursive method builds expression for constraining fault
	 * count in lustre.
	 * Base case 1: list is empty: returns integer expr (0)
	 * Base case 2: list has one element: returns list
	 * Recursive case: create nested binary expr and call this method
	 * to create next iteration.
	 *
	 * @param exprList list of expressions for the count
	 * @param index begins at 0
	 * @return Expr of the full count.
	 */
	public Expr buildFaultCountExpr(List<Expr> exprList, int index) {
		if (index > exprList.size() - 1) {
			return new IntExpr(0);
		} else if (index == exprList.size() - 1) {
			return exprList.get(index);
		} else {
			return new BinaryExpr(exprList.get(index), BinaryOp.PLUS, buildFaultCountExpr(exprList, index + 1));
		}
	}

	/**
	 * Recursive method builds disjunctive expr for everything in list.
	 * Base case 1: list is empty: returns false
	 * Base case 2: list has one element: returns list
	 * Recursive case: create nested disjunctive binary expr and
	 * call this method to create next iteration.
	 *
	 * @param exprList list of disjuncts
	 * @param index begins at 0
	 * @return nested disjunctive expr of all items in list.
	 */
	public Expr buildFaultDisjunctionExpr(List<Expr> exprList, int index) {
		if (index > exprList.size() - 1) {
			return new BoolExpr(false);
		} else if (index == exprList.size() - 1) {
			return exprList.get(index);
		} else {
			return new BinaryExpr(exprList.get(index), BinaryOp.OR, buildFaultDisjunctionExpr(exprList, index + 1));
		}
	}

	/**
	 * Method creates indep active id for fault.
	 *
	 * @param f Fault in question
	 * @return fault indep id for fault.
	 */
	private String getMainNodeFaultIndepStr(Fault f) {
		String base = addPathDelimiters(f.path, f.id);
		return this.createFaultIndependentActiveId(base);
	}

	/**
	 * Method adds count constraints to top level assertions.
	 *
	 * @param maxFaults No. of faults allowed in count.
	 * @param topNode This node (main)
	 * @param builder node builder will have constraints added.
	 */
	public void addTopLevelMaxFaultOccurrenceConstraint(int maxFaults, AgreeNode topNode, AgreeNodeBuilder builder) {
		// add a global fault count
		String id = "__fault__global_count";
		builder.addInput(new AgreeVar(id, NamedType.INT, topNode.reference));

		// assign it.
		List<Expr> sumExprs = new ArrayList<>();
		// getFaultCountExprList(topNode, new ArrayList<>(), sumExprs);
		getFaultCountExprList(topNode, sumExprs);
		Expr faultCountExpr = buildFaultCountExpr(sumExprs, 0);
		Expr equate = new BinaryExpr(new IdExpr(id), BinaryOp.EQUAL, faultCountExpr);
		builder.addAssertion(new AgreeStatement("", equate, topNode.reference));

		// add assertions that the mutually exclusive fault activations
		for (FaultPair curPair : mutualExclusiveFaults) {
			// assert that the pair cannot both be true
			// get fault triggers
			String ft1Trigger = this.getMainNodeFaultIndepStr(curPair.ft1);
			String ft2Trigger = this.getMainNodeFaultIndepStr(curPair.ft2);
			Expr ft1False = new UnaryExpr(UnaryOp.NOT, new IdExpr(ft1Trigger));
			Expr ft2False = new UnaryExpr(UnaryOp.NOT, new IdExpr(ft2Trigger));
			Expr ft1FalseOrft2False = new BinaryExpr(ft1False, BinaryOp.OR, ft2False);
			builder.addAssertion(new AgreeStatement("", ft1FalseOrft2False, topNode.reference));
		}

		// assert that the value is <= maxFaults
		Expr lessEqual = new BinaryExpr(new IdExpr(id), BinaryOp.LESSEQUAL, new IntExpr(maxFaults));
		builder.addAssertion(new AgreeStatement("", lessEqual, topNode.reference));
	}

	/**
	 * Get probabilities of all faults.
	 *
	 * @param currentNode Agree node we are visiting currently
	 * @param path path to this agree node
	 * @param probabilities prob list
	 */
	public void getFaultProbExprList(AgreeNode currentNode, List<String> path, List<FaultProbability> probabilities) {

		List<Fault> faults = this.faultMap.get(currentNode.compInst);
		for (Fault f : faults) {
			String base = addPathDelimiters(path, f.id);
			probabilities.add(new FaultProbability(this.createFaultIndependentActiveId(base), f.probability, f));
		}

		for (AgreeNode n : currentNode.subNodes) {
			List<String> ext = new ArrayList<>(path);
			ext.add(n.id);
			getFaultProbExprList(n, ext, probabilities);
		}
	}

	/**
	 * Build statement for fault not occurring.
	 *
	 * @param elements fault prob elements
	 * @return returns expression stating fault cannot occur
	 */
	public Expr getNoFaultProposition(Set<FaultProbability> elements) {
		Expr noFaultExpr = null;
		for (FaultProbability fp : elements) {
			Expr local = new UnaryExpr(UnaryOp.NOT, new IdExpr(fp.faultName));
			if (noFaultExpr == null) {
				noFaultExpr = local;
			} else {
				noFaultExpr = new BinaryExpr(local, BinaryOp.AND, noFaultExpr);
			}
		}
		assert (noFaultExpr != null);
		return noFaultExpr;
	}

	/**
	 * Sets eqn local AND fault1 AND fault2 AND ...
	 *
	 * @param elements All fault probability elements
	 * @return Returns the conjunction of all these elements.
	 */
	private Expr getAllFaultProposition(Set<FaultProbability> elements) {
		Expr allFaultExpr = null;
		for (FaultProbability fp : elements) {
			Expr local = new IdExpr(fp.faultName);
			if (allFaultExpr == null) {
				allFaultExpr = local;
			} else {
				allFaultExpr = new BinaryExpr(local, BinaryOp.AND, allFaultExpr);
			}
		}
		assert (allFaultExpr != null);
		return allFaultExpr;
	}

	/**
	 * Method collects max fault occurrance constraints then builds
	 * the associated assertions for the node.
	 *
	 * @param minProbability threshold
	 * @param topNode top agree node
	 * @param builder node builder will have assertions added
	 */
	public void addTopLevelMaxFaultOccurrenceConstraint(double minProbability, AgreeNode topNode,
			AgreeNodeBuilder builder) {

		ArrayList<FaultProbability> elementProbabilities = new ArrayList<>();
		PriorityQueue<FaultSetProbability> pq = new PriorityQueue<>();

		collectFaultOccurrenceConstraint(minProbability, topNode, elementProbabilities, faultCombinationsAboveThreshold,
				pq);

		buildNonFaultCombinationAssertions(topNode, builder, elementProbabilities, faultCombinationsAboveThreshold);
	}

	/**
	 * Method gathers possible fault combinations given probabilities
	 * on them (and top level threshold).
	 * Will adjust for dependent faults as well.
	 *
	 * @param minProbability Threshold
	 * @param topNode Top agree node
	 * @param elementProbabilities prob of all fault elements
	 * @param faultCombinationsAboveThreshold allowable combinations
	 * @param pq priority queue of faults
	 */
	private void collectFaultOccurrenceConstraint(double minProbability, AgreeNode topNode,
			ArrayList<FaultProbability> elementProbabilities,
			ArrayList<FaultSetProbability> faultCombinationsAboveThreshold, PriorityQueue<FaultSetProbability> pq) {
		// gather all fault probabilities. If there are no faults, exit out.
		getFaultProbExprList(topNode, new ArrayList<>(), elementProbabilities);
		Collections.sort(elementProbabilities);
		if (elementProbabilities.isEmpty()) {
			return;
		}
		ArrayList<FaultProbability> remainder = arrangePriorityQueue(elementProbabilities, pq, minProbability);
		checkFaultCombinations(pq, remainder, faultCombinationsAboveThreshold, minProbability);

		// Now faultCombinationsAboveThreshold contains all the valid
		// fault combinations of the independently active faults
		adjustForDependentFaults(faultCombinationsAboveThreshold, elementProbabilities);
		// convert fault combination to str set
		convertFaultCombinationToStrSet();
	}

	/**
	 * Method adjusts allowable combinations for the dependent faults.
	 *
	 * If the propagations set is not empty, then for each fault in
	 * each valid fault combination, see if it appears as a source
	 * fault in the propagations. If yes, insert the destination fault
	 * in the same valid fault combination (fault set)
	 * without changing the fault set's probability.
	 *
	 * @param faultCombinationsAboveThreshold Allowable combinations
	 * @param elementProbabilities Prob of elements
	 */
	private void adjustForDependentFaults(ArrayList<FaultSetProbability> faultCombinationsAboveThreshold,
			ArrayList<FaultProbability> elementProbabilities) {
		if (!propagations.isEmpty()) {
			int index = 0;
			// go through the fault combinations
			for (FaultSetProbability fsp : faultCombinationsAboveThreshold) {
				FaultSetProbability currentSet = fsp;
				boolean changed = false;
				// for each fault probability in a combination set
				for (FaultProbability fp : fsp.elements) {
					// see if the fault appears as a source fault in a propagation
					for (SafetyPropagation propagation : propagations) {
						// if yes, get the destination fault
						if (propagation.srcFault.equals(fp.fault)) {
							BaseFault newFault = propagation.destFault;
							changed = true;
							// find the destination fault probability in the original list
							// and add that to the combination set
							// (instead of creating a new one, as applicable fault probabilities
							// will be removed from the original list later)
							for (FaultProbability element : elementProbabilities) {
								if (newFault.equals(element.fault)) {
									currentSet = new FaultSetProbability(fsp.probability, currentSet, element);
								}
							}
						}
					}
				}
				if (changed) {
					faultCombinationsAboveThreshold.set(index, currentSet);
				}
				index++;
			}
		}

	}

	/**
	 * So...now we have a priority queue with remaining fault combinations to be
	 * checked for addition. The PQ preserves the invariant that
	 * highest probability elements are first.
	 * We attempt to combine with remainder (also in priority order).
	 * If unable to combine because combination below threshold,
	 * remove rest of elementProbability list (the rest will be
	 * below threshold for all subsequent elements).
	 * Complete when either the PQ or the element list is empty.
	 *
	 * @param pq Priority queue
	 * @param remainder Leftovers
	 * @param faultCombinationsAboveThreshold Allowed combinations
	 * @param minProbability min prob value between 0 and 1 inclusive
	 */
	private void checkFaultCombinations(PriorityQueue<FaultSetProbability> pq, ArrayList<FaultProbability> remainder,
			ArrayList<FaultSetProbability> faultCombinationsAboveThreshold, double minProbability) {
		while (!pq.isEmpty() && !remainder.isEmpty()) {
			FaultSetProbability fsp = pq.remove();
			for (int i = 0; i < remainder.size(); i++) {
				FaultProbability fp = remainder.get(i);
				double setProbability = fp.probability * fsp.probability;
				// once we found out that the reminder's first element (when combined with fsp),
				// does not yield a probability result that is higher than the threshold
				// that means we no longer needs to check the rest of the remainder,
				// and we can clear the remainder list from the element onward
				if (setProbability < minProbability) {
					remainder.subList(i, remainder.size()).clear();
				}
				//add to faultCombinationAboveThreshold only when
				//fp is not a subset of fsp
				// and fsp, fp are not an existing set (disregard the order of faults) in faultCombinationAboveThreshold
				else if (!fsp.elements.contains(fp)) {
					FaultSetProbability newSet = new FaultSetProbability(setProbability, fsp, fp);
					if (!isContained(newSet, faultCombinationsAboveThreshold)) {
						pq.add(newSet);
						faultCombinationsAboveThreshold.add(newSet);
					}
				}
			}
		}

	}

	/**
	 * Checks to see if fault combination is an allowed combination
	 * based on combined probabilities.
	 *
	 * @param newSet FaultSetProbability to check if allowed combination
	 * @param faultCombinationsAboveThreshold List of FaultSetProbability
	 * 		  elements that are allowed combinations.
	 * @return boolean - is contained or not.
	 */
	private boolean isContained(FaultSetProbability newSet,
			ArrayList<FaultSetProbability> faultCombinationsAboveThreshold) {
		boolean isContained = false;

		HashSet<String> newSetStrSet = newSet.toStringSet();

		for (FaultSetProbability faultCombination : faultCombinationsAboveThreshold) {
			HashSet<String> faultCombinationSet = faultCombination.toStringSet();
			if (faultCombinationSet.equals(newSetStrSet)) {
				isContained = true;
				return isContained;
			}
		}

		return isContained;
	}

	/**
	 * Arranges priority queue of faults based on prob calculations.
	 *
	 * @param elementProbabilities Prob of all elements in fault list.
	 * @param pq Priority queue of these fault set probs
	 * @param minProbability double value of minimum prob.
	 */
	private ArrayList<FaultProbability> arrangePriorityQueue(ArrayList<FaultProbability> elementProbabilities,
			PriorityQueue<FaultSetProbability> pq, double minProbability) {
		// remove elements from list that are too unlikely & add remaining elements to
		// 'good' set.
		ArrayList<FaultProbability> remainder = new ArrayList<>(elementProbabilities);
		for (int i = 0; i < remainder.size(); i++) {
			FaultProbability elementProbability = remainder.get(i);
			if (elementProbability.probability < minProbability) {
				remainder.subList(i, remainder.size()).clear();
			} else {
				FaultSetProbability fsp = new FaultSetProbability(elementProbability.probability, elementProbability);
				faultCombinationsAboveThreshold.add(fsp);
				pq.add(fsp);
			}
		}
		return remainder;
	}

	/**
	 * Converts the fault combinations that are above threshold
	 * to a hash set of strings.
	 */
	private void convertFaultCombinationToStrSet() {
		for (FaultSetProbability faultCombination : faultCombinationsAboveThreshold) {
			HashSet<String> faultCombinationSet = faultCombination.toStringSet();
			faultCombinationAboveThresholdStrs.add(faultCombinationSet);
		}
	}

	/**
	 * Method calls will collect max fault behavior or probability behavior
	 * depending on what is entered in the top level annex.
	 * If fault tree generation is chosen by user,
	 * we do not add the assertions for the max/prob behavior,
	 * but instead add fault indep vars in the IVC commands.
	 *
	 * This method calls helpers depending on which analysis behavior
	 * is required.
	 *
	 * @param ab Top level analysis behavior
	 * @param topNode top level agree node
	 * @param builder node builder will have these constraints added
	 */
	public void addTopLevelFaultOccurrenceConstraints(AnalysisBehavior ab, AgreeNode topNode,
			AgreeNodeBuilder builder) {

		if (ab instanceof FaultCountBehavior) {
			addTopLevelMaxFaultOccurrenceConstraint(Integer.parseInt(((FaultCountBehavior) ab).getMaxFaults()), topNode,
					builder);
		} else if (ab instanceof ProbabilityBehavior) {
			addTopLevelMaxFaultOccurrenceConstraint(Double.parseDouble(((ProbabilityBehavior) ab).getProbabilty()),
					topNode, builder);
		}
	}

	/**
	 * Checks analysis behavior and sets local static vars to reflect this.
	 * (maxFaultHypothesis, maxFaultCount, or probabilisticHypothesis)
	 *
	 * @param ab Top level analysis behavior
	 * @param topNode top level agree node
	 * @param builder node builder will have these constraints added
	 */
	public void collectTopLevelFaultOccurrenceConstraints(AnalysisBehavior ab, AgreeNode topNode,
			AgreeNodeBuilder builder) {

		if (ab instanceof FaultCountBehavior) {
			maxFaultHypothesis = true;
			maxFaultCount = Integer.parseInt(((FaultCountBehavior) ab).getMaxFaults());
		} else if (ab instanceof ProbabilityBehavior) {
			probabilisticHypothesis = true;
			collectTopLevelMaxFaultOccurrenceConstraint(Double.parseDouble(((ProbabilityBehavior) ab).getProbabilty()),
					topNode, builder);
		}
	}

	/**
	 * Method collects occurrance constraints for top level given
	 * fault probabilities.
	 *
	 * @param minProbability Min prob value
	 * @param topNode Top agree node
	 * @param builder node builder has these assertions added
	 */
	private void collectTopLevelMaxFaultOccurrenceConstraint(double minProbability, AgreeNode topNode,
			AgreeNodeBuilder builder) {

		ArrayList<FaultProbability> elementProbabilities = new ArrayList<>();
		PriorityQueue<FaultSetProbability> pq = new PriorityQueue<>();

		collectFaultOccurrenceConstraint(minProbability, topNode, elementProbabilities, faultCombinationsAboveThreshold,
				pq);
	}

	/**
	 * Method builds combinations of faults that cannot occur
	 * together based on probability values.
	 *
	 * @param topNode AgreeNode, top of program
	 * @param builder Node builder will have assertions added.
	 * @param elementProbabilities Prob of elements
	 * @param faultCombinationsAboveThreshold Which FaultSetProbabilities
	 * are above threshold given in top level annex.
	 */
	private void buildNonFaultCombinationAssertions(AgreeNode topNode, AgreeNodeBuilder builder,
			ArrayList<FaultProbability> elementProbabilities,
			ArrayList<FaultSetProbability> faultCombinationsAboveThreshold) {
		// With the valid fault combinations including dependent faults, and
		// noFaultExpr has the default (no-fault) case. Let's construct a proposition.
		Set<FaultProbability> elementProbabilitySet = new HashSet<>(elementProbabilities);
		// the default (no-fault) case
		Expr faultHypothesis = getNoFaultProposition(elementProbabilitySet);
		for (FaultSetProbability fsp : faultCombinationsAboveThreshold) {
			Set<FaultProbability> goodElements = new HashSet<>(elementProbabilities);
			goodElements.removeAll(fsp.elements);
			// if there are only a subset of faults in the current combination
			// add the assertion that the rest of the faults are not to happen
			if (!goodElements.isEmpty()) {
				Expr local = getNoFaultProposition(goodElements);
				faultHypothesis = new BinaryExpr(local, BinaryOp.OR, faultHypothesis);
			}
			// if there are all faults in the current combination
			// add the assertion that all faults are allowed to happen
			// which will be ORed with the default no fault case
			else {
				Expr local = getAllFaultProposition(fsp.elements);
				faultHypothesis = new BinaryExpr(local, BinaryOp.OR, faultHypothesis);
			}
		}

		// Add this fault hypothesis as an assertion if not null.
		if (faultHypothesis == null) {
			new SafetyException("There is a problem with fault hypothesis for component: " + topNode.id
					+ ". A possible problem is that single layer analysis"
					+ " is being run with no faults defined in lower layer."
					+ " Check hypothesis statements and fault defs in this analysis.");
		}
		builder.addAssertion(new AgreeStatement("", faultHypothesis, topNode.reference));
	}

	/**
	 * Method checks to see if a fault stmt defines an asymmetric fault.
	 *
	 * @param fstmt FaultStatement to be checked for symmetry.
	 * @return Boolean isAsymmetric value
	 */
	private boolean isAsymmetric(FaultStatement fstmt) {
		boolean isAsym = false;
		for (FaultSubcomponent fs : fstmt.getFaultDefinitions()) {
			if (fs instanceof PropagationTypeStatement) {
				if (fs != null) {
					if (((PropagationTypeStatement) fs).getPty() instanceof asymmetric) {
						isAsym = true;
					} else {
						isAsym = false;
					}
					break;
				}
			}
		}
		return isAsym;
	}

	/**
	 * Method checks to see if a fault is an asymmetric fault.
	 *
	 * @param f Fault that is either symmetric or asymmetric.
	 * @return Boolean isAsymmetric value
	 */
	private boolean isAsymmetric(Fault f) {
		boolean isAsym = false;
		if (f.propType != null) {
			if (f.propType.getPty() instanceof asymmetric) {
				isAsym = true;
			}
		}
		return isAsym;
	}

	/**
	 * Method is used to assert that the assume hist statements
	 * for each comm node is true. This is added to main.
	 * Ex:
	 * assert (asym_node_0__Sender__sender_out____ASSUME__HIST = __HIST(true));
	 *
	 * @param nb NodeBuilder that will have these assertions added.
	 */
	private void addAssumeHistStmts(AgreeNodeBuilder nb) {
		for (String commNodes : mapCommNodeToInputs.keySet()) {
			List<AgreeVar> inputs = mapCommNodeToInputs.get(commNodes);
			for (AgreeVar input : inputs) {
				if (input.id.contains("__ASSUME__HIST")) {
					NodeCallExpr nodeCall = new NodeCallExpr("__HIST", new BoolExpr(true));
					Expr eq = new BinaryExpr(new IdExpr(input.id), BinaryOp.EQUAL, nodeCall);
					nb.addAssertion(new AgreeStatement("", eq, topNode.reference));
					break;
				}
			}
		}
	}

	/**
	 * Method is used when asymmetric fault is being added to the top node.
	 * It adds all locals to the top node that are present in new comm nodes.
	 * Ex:
	 * asym_node_1__Sender__input : Base_Types__Float;
	 * asym_node_1__Sender__output : Base_Types__Float;
	 * asym_node_1__Sender____ASSUME__HIST : bool;
	 *
	 * ... etc
	 *
	 * @param nb NodeBuilder that will have this information added.
	 */
	private void addLocalsForCommNodes(AgreeNodeBuilder nb) {
		// Access inputs for each comm node.
		for (String commNodes : mapCommNodeToInputs.keySet()) {
			List<AgreeVar> inputs = mapCommNodeToInputs.get(commNodes);
			List<AgreeVar> inputsToAdd = new ArrayList<AgreeVar>();
			List<AgreeVar> intputsToRemove = new ArrayList<AgreeVar>();
			for (AgreeVar var : inputs) {
				if (!(var.id.contains(commNodes))) {
					// Create new AgreeVar with new id
					AgreeVar newVar = new AgreeVar(commNodes + "__" + var.id, var.type, var.reference);
					// Delete old agree var
					intputsToRemove.add(var);
					inputsToAdd.add(newVar);
				}
			}
			for (AgreeVar varToAdd : inputsToAdd) {
				inputs.add(varToAdd);
			}
			for (AgreeVar varToRemove : intputsToRemove) {
				inputs.remove(varToRemove);
			}
			nb.addInput(mapCommNodeToInputs.get(commNodes));
		}
	}

	/**
	 * Method adds the local variable for the count of asymmetric faults.
	 * Constraints for this count are added in assertions.
	 * Ex:
	 * 	__fault__Sender__fault_1_count : int; (as local in main)
	 *
	 * assert (__fault__Sender__fault_1_count =
	 * 		((if asym_node_0__fault__trigger__Sender__fault_1
	 * 		  then 1
	 * 		  else 0)
	 *
	 * assert (__fault__Sender__fault_1_count <= 3); (where 3 is total no. of connections)
	 *
	 * @param nb NodeBuilder that will have this information added.
	 */
	private void addAsymCountConstraints(AgreeNodeBuilder nb) {
		// Access the asym faults through map and create count for each.
		// Make local map saving said count with its fault.
		for (Fault f : mapAsymFaultToCommNodes.keySet()) {
			String id = "__fault__" + f.id + "_count";
			AgreeVar count = new AgreeVar(id, NamedType.INT, topNode.reference);
			nb.addInput(count);
			// Get nodes to build assert stmts
			List<String> nodes = mapAsymFaultToCommNodes.get(f);
			List<Expr> sumExprs = new ArrayList<>();
			for (String n : nodes) {
				sumExprs.add(createSumExpr(new IdExpr(n + "__fault__trigger__" + f.id)));
			}
			// Add the constraints associated with the count.
			Expr faultCountExpr = buildFaultCountExpr(sumExprs, 0);
			Expr equate = new BinaryExpr(new IdExpr(id), BinaryOp.EQUAL, faultCountExpr);
			nb.addAssertion(new AgreeStatement("", equate, topNode.reference));

			// Restrict to less than the total number of connections
			Expr restrict = new BinaryExpr(new IdExpr(id), BinaryOp.LESSEQUAL, new IntExpr(nodes.size()));
			nb.addAssertion(new AgreeStatement("", restrict, topNode.reference));
		}
	}

	/**
	 * Method adds assertions associated with the asym fault event.
	 * Adds triggers for the communication node faults:
	 * 		__fault__trigger__Sender__fault_1 : bool;
	 * Adds trigger expression linking fault of sender node to the comm node
	 * behavior:
	 * 		output = if __fault__trigger__Sender__fault_1
	 * 				 then Sender__fault_1__node__val_out
	 * 				 else __fault__nominal__output
	 *
	 * @param nb NodeBuilder that will have these assertions added.
	 */
	private void addAsymFaultAssertions(AgreeNodeBuilder nb) {
		// List of idExpr holding dep ids and list for indep ids
		List<Expr> triggerList = new ArrayList<>();
		for (Fault fault : mapAsymFaultToCommNodes.keySet()) {
			for (String nodeName : mapAsymFaultToCommNodes.get(fault)) {
				// Create trigger statements for each of the faults comm nodes
				IdExpr trigger = new IdExpr(nodeName + "__fault__trigger__" + fault.id);
				triggerList.add(trigger);
			}
			// Create trigger expression that links fault of sender node to comm node trigger.
			String compName = mapAsymFaultToCompName.get(fault);
			IdExpr trigger = new IdExpr(compName + "__fault__trigger__" + fault.id);
			Expr bigOrExpr = buildBigOrExpr(triggerList, 0);
			Expr notBigOrExpr = new UnaryExpr(UnaryOp.NOT, bigOrExpr);
			Expr ifThenElse = new IfThenElseExpr(trigger, bigOrExpr, notBigOrExpr);
			nb.addAssertion(new AgreeStatement("", ifThenElse, this.topNode.reference));
			triggerList.clear();
		}
	}

	/**
	 * Method adds the asym communication node calls into main node in lustre.
	 *
	 * @param nb NodeBuilder has these node calls added.
	 */
	private void addAsymNodeCalls(AgreeNodeBuilder nb) {
		// For each key in map, get name of node and list of lustre inputs.
		List<Expr> tempIds = new ArrayList<>();
		for (String nodeName : mapCommNodeToInputs.keySet()) {
			for (AgreeVar av : mapCommNodeToInputs.get(nodeName)) {
				IdExpr id = new IdExpr(av.id);
				tempIds.add(id);
			}
			// Create node call expression
			NodeCallExpr nodeCall = new NodeCallExpr(nodeName, tempIds);
			nb.addAssertion(new AgreeStatement("", nodeCall, this.topNode.reference));
			tempIds.clear();
		}
	}

	/**
	 * Method will remove the previous connections in the main lustre node
	 * from sender to receivers and add in the new connections from sender
	 * to commNode and from commNode to receiver.
	 * Ex: What used to be:
	 * 	   Sender_out = reciever1.in
	 * 	   Sender_out = reciever2.in
	 * 	   Sender_out = reciever3.in
	 * Is now:
	 * 	   Sender_out = asym0.in
	 * 	   Sender_out = asym1.in
	 * 	   Sender_out = asym2.in
	 * 	   asym0.out = reciever1.in
	 * 	   asym1.out = reciever2.in
	 * 	   asym2.out = reciever3.in
	 *
	 * @param nb NodeBuilder for the main lustre node.
	 */
	private void changeAsymConnections(AgreeNodeBuilder nb) {
		// Insert connections sender_output = commNode_input
		for (String output : mapAsymCompOutputToCommNodeIn.keySet()) {
			for (String nodeName : mapAsymCompOutputToCommNodeIn.get(output)) {
				Expr eq = new BinaryExpr(new IdExpr(output), BinaryOp.EQUAL, new IdExpr(nodeName));
				nb.addAssertion(new AgreeStatement("", eq, this.topNode.reference));
			}
		}
		// Insert connections commNode_output = receiver_input.
		for (String output : mapCommNodeOutputToConnections.keySet()) {
			String featureName = "";
			String componentName = "";
			// First access name of receiving component and its input
			if (mapCommNodeOutputToConnections.get(output).eContainer() instanceof SystemInstanceImpl) {
				FeatureInstanceImpl fi = (FeatureInstanceImpl) mapCommNodeOutputToConnections.get(output);
				componentName = "";
				featureName = fi.getName();
			} else if (mapCommNodeOutputToConnections.get(output) instanceof FeatureInstanceImpl) {
				FeatureInstanceImpl fi = (FeatureInstanceImpl) mapCommNodeOutputToConnections.get(output);
				featureName = fi.getName();
				if (fi.eContainer() instanceof ComponentInstanceImpl) {
					ComponentInstanceImpl ci = (ComponentInstanceImpl) fi.eContainer();
					componentName = ci.getName() + "__";
				} else {
					new SafetyException("Asymmetric fault must be connected to a component instance.");
				}
			} else {
				new SafetyException("Asymmetric fault must have an allowable connection.");
			}
			// Create lustre connection name, add to builder.
			IdExpr connectionName = new IdExpr(componentName + featureName);
			Expr eq = new BinaryExpr(new IdExpr(output), BinaryOp.EQUAL, connectionName);
			nb.addAssertion(new AgreeStatement("", eq, this.topNode.reference));
		}
	}

	/**
	 * Method builds an OR expression of all things in list.
	 * Base case: list is empty : append false to or list
	 * Base case 2: list has one element : return that element.
	 * Recursive case: make or of element of list with recursive call.
	 *
	 * @param exprList List of expressions for disjunction.
	 * @param index Recursive function begins with 0 as index.
	 * @return Expr: disjunction of all expr in list.
	 */
	private Expr buildBigOrExpr(List<Expr> exprList, int index) {
		if (index > exprList.size() - 1) {
			return new BoolExpr(false);
		} else if (index == exprList.size() - 1) {
			return exprList.get(index);
		} else {
			return new BinaryExpr(exprList.get(index), BinaryOp.OR, buildBigOrExpr(exprList, index + 1));
		}
	}

	/**
	 * Public accessor for the mapping from a fault to its corresponding lustre name.
	 * @return Map<Fault, List<String>> faultToLustreNameMap
	 */
	public Map<Fault, List<String>> getFaultToLustreNameMap() {
		return mapFaultToLustreNames;
	}

	/**
	 * Public accessor for the mapping from a hwfault to its
	 * corresponding lustre name.
	 * @return Map<HWFault, List<String>> faultToLustreNameMap
	 */
	public Map<HWFault, List<String>> getHWFaultToLustreNameMap() {
		return mapHWFaultToLustreNames;
	}

	/**
	 * Public accessor for the map from a fault to its associated agree node name.
	 *
	 * @return Map<Fault, String> mapFaultToPath
	 */
	public Map<Fault, String> getMapFaultToPath() {
		return mapFaultToPath;
	}

	/**
	 * Public accessor for faultMap: faultMap is used to properly set up the
	 * top-level node for triggering faults - faultMap stores the faults associated
	 * with a node.
	 * @return Map<ComponentInstance, List<Fault>> faultMap
	 */
	public Map<ComponentInstance, List<Fault>> getFaultMap() {
		return faultMap;
	}

	/*****************************************************************
	 *
	 * probability-based fault calculations
	 *
	 ******************************************************************/
	class FaultProbability implements Comparable<FaultProbability> {
		public double probability;
		public String faultName;
		public final BaseFault fault;

		public FaultProbability(String faultName, double probability, BaseFault fault) {
			this.probability = probability;
			this.faultName = faultName;
			this.fault = fault;
		}

		@Override
		public int compareTo(FaultProbability o) {
			// Want to sort LARGEST first, so negate probabilities.
			return Double.compare(-probability, -o.probability);
		}

		@Override
		public String toString() {
			return "(" + probability + ", " + faultName + ")";
		}
	};

	/**
	 * Class used to define probabilitiy over elements in a set.
	 * @author Danielle Stewart, Mike Whalen, Janet Liu
	 *
	 */
	class FaultSetProbability implements Comparable<FaultSetProbability> {
		// invariant: probability should equal the multiple of all probabilities in
		// elements.
		public double probability;
		public Set<FaultProbability> elements;

		/**
		 * Constructor method.
		 *
		 * @param probability double prob value (btwn 0 and 1 inclusive)
		 * @param fp Fault probability
		 */
		public FaultSetProbability(double probability, FaultProbability fp) {
			this.probability = probability;
			this.elements = Collections.singleton(fp);
		}

		/**
		 * Second constructor has base.
		 *
		 * @param probability
		 * @param base
		 * @param fp
		 */
		public FaultSetProbability(double probability, FaultSetProbability base, FaultProbability fp) {
			this.probability = probability;
			this.elements = new HashSet<>(base.elements);
			this.elements.add(fp);
		}

		/**
		 * Method compares probabilities in fault set.
		 */
		@Override
		public int compareTo(FaultSetProbability o) {
			// Want to sort LARGEST first, so negate probabilities.
			return Double.compare(-probability, -o.probability);
		}

		/**
		 * Returns string of prob elements
		 */
		@Override
		public String toString() {
			return "(" + probability + ", " + elements.toString() + " )";
		}

		/**
		 * Method takes fault prob elements and returns them as a
		 * hash set of strings.
		 *
		 * @return HashSet<String> of element fault names.
		 */
		public HashSet<String> toStringSet() {
			HashSet<String> elemSet = new HashSet<String>();
			for (FaultProbability element : elements) {
				elemSet.add(element.faultName);
			}
			return elemSet;
		}
	}

	/**
	 * Class defines pair Expr, Fault
	 * @author Danielle Stewart
	 *
	 */
	public class Pair {
		private Expr ex;
		private Fault f;

		public Pair(Expr ex, Fault f) {
			this.ex = ex;
			this.f = f;
		}
	}

	/**
	 * Class defines pair Fault, Fault
	 * @author Danielle Stewart
	 *
	 */
	public class FaultPair {
		private Fault ft1;
		private Fault ft2;

		public FaultPair(Fault ft1, Fault ft2) {
			this.ft1 = ft1;
			this.ft2 = ft2;
		}
	}

	/**
	 * Class defines pair of Expr, Expr
	 * used for trigger stmt and fault output stmt.
	 * @author Danielle Stewart
	 *
	 */
	public class TriggerFaultOutPair {
		private Expr trigger;
		private Expr faultOut;

		public TriggerFaultOutPair(Expr trigger, Expr faultOut) {
			this.trigger = trigger;
			this.faultOut = faultOut;
		}
	}
}
