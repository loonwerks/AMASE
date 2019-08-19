package edu.umn.cs.crisys.safety.analysis.transform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osate.aadl2.impl.DataPortImpl;
import org.osate.aadl2.instance.ConnectionInstanceEnd;

import com.rockwellcollins.atc.agree.analysis.ast.AgreeNode;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeStatement;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeVar;

import edu.umn.cs.crisys.safety.analysis.SafetyException;
import edu.umn.cs.crisys.safety.safety.FaultStatement;
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
import jkind.lustre.RecordType;
import jkind.lustre.Type;
import jkind.lustre.VarDecl;
import jkind.lustre.builders.NodeBuilder;

public class AsymFaultASTBuilder extends FaultASTBuilder {

	/**
	 *
	 * @param globalLustreNodes
	 * @param agreeNode
	 */
	public AsymFaultASTBuilder(List<Node> globalLustreNodes, AgreeNode agreeNode) {
		super(globalLustreNodes, agreeNode);
		// TODO Auto-generated constructor stub
	}
	// List of expressions used as input for node call expression.
	private Map<Fault, List<Expr>> nodeArguments = new HashMap<Fault, List<Expr>>();
	List<TriggerFaultPair> triggerList = new ArrayList<>();

	/**
	 * Process all faults in the list of fault statements and return a list of
	 * those faults. It is assumed that all faults in faultGroup are part of multiple
	 * faults on a single output.
	 *
	 * @param faultGroup List of asymmetric FaultStatements.
	 * @return List of processed faults.
	 */
	public List<Fault> processFaults(List<FaultStatement> faultGroup) {
		if (faultGroup.isEmpty()) {
			new SafetyException("Problem with multiple faults (Debug: AsymFaultASTBuilder)");
		}

		List<Fault> faultList = new ArrayList<Fault>();
		List<ConnectionInstanceEnd> senderConnections = new ArrayList<>();
		DataPortImpl senderOutput = null;

		// 1. Create fault nodes using parent method
		for (FaultStatement fs : faultGroup) {
			faultList.add(super.createSenderFault(fs));
		}
		// 2. Gather connections and add to parent map - can use any fstmt to do this.
		senderOutput = super.findSenderOutput(faultGroup.get(0));
		senderConnections = super.populateMapSenderToReceiver(senderOutput);

		// 3. Create communication nodes
		createCommNodes(senderConnections, senderOutput, faultList);

		return faultList;
	}

	/**
	 *
	 * @param senderConnections
	 * @param senderOutput
	 * @param faults
	 */
	private void createCommNodes(List<ConnectionInstanceEnd> senderConnections, DataPortImpl senderOutput,
			List<Fault> faults) {

		// For loop goes through the connections
		// and creates a commNode specific for each connection.
		// The faults are passed into the creation method (addFaultsToCommNode).
		String nodeName = "";
		// Get list of connections from parent component that senderOutput is connected to.
		String searchFor = senderOutput.getFullName();
		// Name of sender component (the one with the fanned output)
		String compName = this.agreeNode.compInst.getName();
		List<String> commNodeNamesInput = new ArrayList<>();
		List<String> commNodeNames = new ArrayList<>();
		for (int i = 0; i < senderConnections.size(); i++) {
			nodeName = "asym_node_" + i + "__" + compName + "__" + searchFor;

			Node commNode = addFaultsToCommNode(this.agreeNode, faults, senderOutput, nodeName, i);

			commNodeNamesInput.add(nodeName + "__input");
			commNodeNames.add(nodeName);
			// Make map from comm node to connections
			mapCommNodeOutputToConnections.put(nodeName + "__output", senderConnections.get(i));
			// 4. Add node to lustre
			this.addGlobalLustreNode(commNode);
		}

		// Output of sender component used to build lustre main asserts
		String senderOut = this.agreeNode.id + "__" + searchFor;
		super.mapAsymCompOutputToCommNodeIn.put(senderOut, commNodeNamesInput);
		super.mapCompNameToCommNodes.put(compName, commNodeNames);
	}

	/**
	 * Creates a lustre node with all fault node calls defined for a particular agree
	 * node output. Returns completed Lustre node.
	 *
	 * @param agreeNode The AgreeNode which has the fault list associated with an output.
	 * @param faults The List<Fault> that is on an output of the agree node.
	 * @param senderOutput DataPortImpl is the agreeNode output that these faults are connected to.
	 * @param nodeName String of the name of the node to be created.
	 * @param i The unique id number associated with the node to be created.
	 * @return Node to be inserted into Lustre.
	 */
	private Node addFaultsToCommNode(AgreeNode agreeNode, List<Fault> faults, DataPortImpl senderOutput,
			String nodeName, int i) {
		// Create unique node name
		NodeBuilder newNode = new NodeBuilder(nodeName);
		List<AgreeVar> nodeOutputs = agreeNode.outputs;
		AgreeVar outputOfInterest = null;

		// Get name of output according to senderOutput data port impl
		String nameOfOutput = senderOutput.getName();

		// Find the output in agree node outputs in order to access type.
		for (AgreeVar agreeVar : nodeOutputs) {
			String temp = agreeVar.id;
			if (temp.contentEquals(nameOfOutput)) {
				// This agreeVar is the sender var we want to save for the
				// later mapping to the receiver var.
				outputOfInterest = agreeVar;
			}
		}
		if(outputOfInterest == null) {
			new SafetyException("Cannot locate output on agree var for asym fault. (Debug: AsymFaultASTBuilder)");
		}
		// Now the same type on the AgreeNode outputOfInterest
		// is the same as what we will create for the type of
		// both input and output of commNode.
		Type type = outputOfInterest.type;
		newNode = createInputForCommNode(agreeNode, newNode, faults, outputOfInterest.type, nodeName);
		newNode = super.createOutputForCommNode(newNode);
		newNode = createLocalsForCommNode(newNode, faults);
		newNode = createEquationsForCommNode(newNode, faults, type, nodeName);

		return newNode.build();
	}

	/**
	 * Creates equation between fault nominal output and input and an equation
	 * regarding the comm node output and the fault node output.
	 *
	 * @param newNode NodeBuilder for comm node that needs these equations added.
	 * @param faults List of faults to traverse for this comm node.
	 * @param type The type on the agree node output.
	 * @param nodeName The name of this comm node.
	 * @return NodeBuilder with these equations added.
	 */
	private NodeBuilder createEquationsForCommNode(NodeBuilder newNode, List<Fault> faults, Type type,
			String nodeName) {
		// assign __GUARANTEE0 : fault__nominal__output = input
		// Simple case is when it is bool, real. Otherwise if it is a record
		// type, we need to access the field of the fault node input and append
		// that to the expression (i.e. __fault__nominal__output.VAL = input.VAL)
		String field = "";
		String dotField = "";
		if (type instanceof RecordType) {
			for (Expr faultOutput : faults.get(0).faultOutputMap.keySet()) {
				if (faultOutput instanceof RecordAccessExpr) {
					RecordAccessExpr rac = (RecordAccessExpr) faultOutput;
					dotField = "." + rac.field;
					field = rac.field;
				}
			}
		}
		IdExpr faultNominalOut = new IdExpr("__fault__nominal__output" + dotField);
		Expr binEx = new BinaryExpr(faultNominalOut, BinaryOp.EQUAL, new IdExpr("input" + dotField));
		IdExpr guar = new IdExpr("__GUARANTEE0");
		// This links fault nominal with node input :
		// assert (__fault__nominal__output.NODE_VAL = input.NODE_VAL)
		newNode.addEquation(guar, binEx);
		BinaryExpr binAssumeAndTrue = super.createAssumeHistStmt(guar);
		Expr assertStmt = null;
		IdExpr output = null;
		for (Fault fault : faults) {
			// output = (fault_node_val_out)
			// If record type, need to reference "output.VAL"
			IdExpr toAssign = null;
			toAssign = new IdExpr(getFaultNodeOutputId(fault));
			if (type instanceof RecordType) {
				output = new IdExpr("output" + dotField);
			} else {
				output = new IdExpr("output");
			}
		}

		// Go through trigger list and add a nested if-then-else stmt
		// that enforces mutual exclusivity.
		Expr nested = createNestedIfThenElseExpr(triggerList, new IdExpr("__fault__nominal__output"), 0);
		BinaryExpr outputEqualsValout = new BinaryExpr(output, BinaryOp.EQUAL, nested);

		BinaryExpr finalExpr1 = new BinaryExpr(binAssumeAndTrue, BinaryOp.AND, outputEqualsValout);
		// Before finishing the assert, check to see if we have safetyEqAsserts in the fault
		// and add those to the finalExpr with "and"
		assertStmt = addSafetyEqStmts(newNode, faults, new BoolExpr(true));

		BinaryExpr finalExpr2 = new BinaryExpr(finalExpr1, BinaryOp.AND, assertStmt);

		// Assert:
		// __ASSERT = (output = (fault_node_val_out))
		// and (__ASSUME__HIST => (__GUARANTEE0 and true)))
		newNode.addEquation(new IdExpr("__ASSERT"), finalExpr2);
		// Construct the node call expression
		// If record type, add to fault nominal expression
		constructNodeCalls(newNode, dotField);
		triggerList.clear();
		return newNode;
	}

	/**
	 * Add any safety eq stmts to the main assert for this node.
	 *
	 * @param node NodeBuilder for this lustre node
	 * @param fault Fault associated with the sender component
	 * @param assertExpr  The main assert stmt linking the output to the fault val out
	 */
	private Expr addSafetyEqStmts(NodeBuilder node, List<Fault> faults, Expr assertExpr) {
		List<AgreeStatement> safetyEqList = new ArrayList<AgreeStatement>();
		// Collect all safetyEq stmts in one list
		for (Fault f : faults) {
			safetyEqList.addAll(f.safetyEqAsserts);
		}

		if (!safetyEqList.isEmpty()) {
			// Build and expression with all expr in list
			Expr safetyEqExpr = super.buildBigAndExpr(safetyEqList, 0);
			BinaryExpr finalExpr2 = new BinaryExpr(safetyEqExpr, BinaryOp.AND, assertExpr);
			return finalExpr2;
		} else {
			return assertExpr;
		}
	}

	/**
	 * Construct the calls to the fault nodes with arguments in proper order.
	 * Add this to the comm node equations.
	 *
	 * @param newNode NodeBuilder for this comm node.
	 * @param fault Fault definition for fault node.
	 * @param dotField String that holds field if record type.
	 */
	private void constructNodeCalls(NodeBuilder newNode, String dotField) {
		if (dotField.isEmpty()) {
			for (Fault output : nodeArguments.keySet()) {
				NodeCallExpr nodeCall = new NodeCallExpr(output.faultNode.id, nodeArguments.get(output));
				newNode.addEquation(new Equation(
						new IdExpr(output.id + "__node__" + output.faultNode.outputs.get(0).id), nodeCall));
			}
		} else {
			List<Expr> newList = new ArrayList<>();
			for (Fault output : nodeArguments.keySet()) {
				for (Expr ex : nodeArguments.get(output)) {
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
				NodeCallExpr nodeCall = new NodeCallExpr(output.faultNode.id, newList);
				newNode.addEquation(new Equation(
						new IdExpr(output.id + "__node__" + output.faultNode.outputs.get(0).id), nodeCall));
			}
		}
	}

	/**
	 * Create locals for the communication node. This is the guarantee stmt and
	 * all fault output stmts from the fault list.
	 *
	 * @param newNode NodeBuilder has locals added to it in this method.
	 * @param faults Fault list of all faults associated with this agree node.
	 * @return
	 */
	private NodeBuilder createLocalsForCommNode(NodeBuilder newNode, List<Fault> faults) {
		newNode.createLocal("__GUARANTEE0", NamedType.BOOL);

		// Create local using fault node output id
		// The type of fault output id is the same as what the fault node
		// returns as its output. First find this in order to create that
		// variable later.
		for (Fault fault : faults) {
			NamedType nominalOutputType = super.getOutputTypeForFaultNode(fault);
			newNode.createLocal(super.getFaultNodeOutputId(fault), nominalOutputType);
		}

		return newNode;
	}

	/**
	 * Creates local input for comm node and collects fault node arguments and adds
	 * the needed ones to the inputs for this node.
	 *
	 * @param agreeNode AgreeNode with these faults associated.
	 * @param newNode NodeBuilder for this comm node.
	 * @param faults List of faults on the agree node.
	 * @param type Type of output of agree node.
	 * @param nodeName Name of this comm node.
	 * @return NodeBuilder with all inputs added.
	 */
	private NodeBuilder createInputForCommNode(AgreeNode agreeNode, NodeBuilder newNode, List<Fault> faults, Type type,
			String nodeName) {
		// This list is used to map the fault to the top node locals that
		// will reference this node name with its corresponding inputs and outputs.
		// Hence the AgreeVars that are created are specifically named for this purpose.
		List<AgreeVar> localsForCommNode = new ArrayList<>();

		// Each fault will need to be referenced in order to add ids to the triggers
		// The rest of the inputs will reference the agree node for the AgreeVar
		// reference (linked in counterexample).

		newNode.createInput("input", type);
		AgreeVar var1 = new AgreeVar(nodeName + "__input", type, agreeNode.reference);
		localsForCommNode.add(var1);
		newNode.createInput("output", type);
		AgreeVar var2 = new AgreeVar(nodeName + "__output", type, agreeNode.reference);
		localsForCommNode.add(var2);
		newNode.createInput("__ASSUME__HIST", NamedType.BOOL);
		AgreeVar var3 = new AgreeVar(nodeName + "____ASSUME__HIST", NamedType.BOOL, agreeNode.reference);
		localsForCommNode.add(var3);
		newNode.createInput("time", NamedType.REAL);
		AgreeVar var4 = new AgreeVar(nodeName + "__time", NamedType.REAL, agreeNode.reference);
		localsForCommNode.add(var4);
		newNode.createInput("__fault__nominal__output", type);
		AgreeVar var5 = new AgreeVar(nodeName + "____fault__nominal__output", type, agreeNode.reference);
		localsForCommNode.add(var5);

		// Triggers need to be added per fault.
		// Populate trigger list for use in later assertion for mutual exclusion.
		for (Fault fault : faults) {
			newNode.createInput("__fault__trigger__" + fault.id, NamedType.BOOL);
			AgreeVar var7 = new AgreeVar(nodeName + "__fault__trigger__" + fault.id, NamedType.BOOL,
					fault.faultStatement);
			localsForCommNode.add(var7);
			IdExpr out = new IdExpr(super.getFaultNodeOutputId(fault));
			IdExpr trigger = new IdExpr("__fault__trigger__" + fault.id);
			TriggerFaultPair triggerPair = new TriggerFaultPair(trigger, out);
			triggerList.add(triggerPair);
		}

		// The fault node may have more than one argument. These all need to be
		// added as locals to this node and put into the map for main.
		// They will also be used in the node call expression.
		// This helper method will add all locals as needed as well as associated
		// safetyEqAssert statements. These correspond to "eq" stmts in the fault definition.
		// Since we have a list of faults, this must be done for all faults.
		for (Fault fault : faults) {
			localsForCommNode.addAll(addFaultNodeArgsToList(newNode, fault));
		}
		// Add to map between node and list of locals in lustre
		super.mapCommNodeToInputs.put(nodeName, localsForCommNode);
		return newNode;
	}

	/**
	 * Goes through the fault and adds all arguments to the fault node into a local list.
	 * These are saved in the order the node must be called.
	 * The list is also saved in the class list of list: nodeArguments. These are used
	 * in the node call expression formation.
	 *
	 * @param newNode NodeBuilder has inputs created in this method corresponding to the
	 * 			fault node arguments it uses.
	 * @param fault Fault that holds fault node definition.
	 * @return
	 */
	private List<AgreeVar> addFaultNodeArgsToList(NodeBuilder newNode, Fault fault) {
		List<AgreeVar> localsForCommNode = new ArrayList<AgreeVar>();
		List<Expr> nodeArgList = new ArrayList<Expr>();

		// Double check size of faultInputList, must be more than 1.
		if (fault.faultInputMap.size() <= 0) {
			return localsForCommNode;
		} else {
			for (VarDecl inputVar : fault.faultNode.inputs) {
				String key = inputVar.id;
				if (key.equals("trigger")) {
					continue;
				}
				if (key.equalsIgnoreCase("val_in")) {
					// First add nominal output to argument list.
					nodeArgList.add(new IdExpr("__fault__nominal__output"));
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
						nodeArgList.add(newIdForList);
						localsForCommNode.add(local);
						newNode.createInput(local.id, type);
					} else {
						// If it is not IdExpr, assume that it is Real/Bool/IntExpr
						// Added directly to fault node call.
						nodeArgList.add(fault.faultInputMap.get(key));
					}
				}
			}
		}
		// Lastly, add the trigger
		nodeArgList.add(new IdExpr("__fault__trigger__" + fault.id));
		nodeArguments.put(fault, nodeArgList);
		return localsForCommNode;
	}

	/**
	 *
	 * @param list
	 * @param nominal
	 * @param index
	 * @return
	 */
	private Expr createNestedIfThenElseExpr(List<TriggerFaultPair> list, Expr nominal, int index) {
		if (index > list.size() - 1) {
			return nominal;
		} else if (index == list.size() - 1) {
			return new IfThenElseExpr(list.get(index).trigger, list.get(index).faultOut, nominal);
		} else {
			return new IfThenElseExpr(list.get(index).trigger, list.get(index).faultOut,
					createNestedIfThenElseExpr(list, nominal, index + 1));
		}
	}

	public class TriggerFaultPair {
		private Expr trigger;
		private Expr faultOut;

		public TriggerFaultPair(Expr trigger, Expr faultOut) {
			this.trigger = trigger;
			this.faultOut = faultOut;
		}
	}
}

