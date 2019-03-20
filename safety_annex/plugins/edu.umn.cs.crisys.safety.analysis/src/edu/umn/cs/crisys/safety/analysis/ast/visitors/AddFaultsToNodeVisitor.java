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

import org.osate.aadl2.instance.ComponentInstance;

import com.rockwellcollins.atc.agree.agree.NestedDotID;
import com.rockwellcollins.atc.agree.analysis.AgreeUtils;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeEquation;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeNode;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeNodeBuilder;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeProgram;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeStatement;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeVar;
import com.rockwellcollins.atc.agree.analysis.ast.visitors.AgreeASTMapVisitor;

import edu.umn.cs.crisys.safety.analysis.SafetyException;
import edu.umn.cs.crisys.safety.analysis.ast.SafetyPropagation;
import edu.umn.cs.crisys.safety.analysis.transform.AddFaultsToAgree;
import edu.umn.cs.crisys.safety.analysis.transform.BaseFault;
import edu.umn.cs.crisys.safety.analysis.transform.Fault;
import edu.umn.cs.crisys.safety.analysis.transform.FaultASTBuilder;
import edu.umn.cs.crisys.safety.analysis.transform.HWFault;
import edu.umn.cs.crisys.safety.analysis.transform.HWFaultASTBuilder;
import edu.umn.cs.crisys.safety.safety.AnalysisBehavior;
import edu.umn.cs.crisys.safety.safety.AnalysisStatement;
import edu.umn.cs.crisys.safety.safety.FaultCountBehavior;
import edu.umn.cs.crisys.safety.safety.FaultStatement;
import edu.umn.cs.crisys.safety.safety.HWFaultStatement;
import edu.umn.cs.crisys.safety.safety.PermanentConstraint;
import edu.umn.cs.crisys.safety.safety.ProbabilityBehavior;
import edu.umn.cs.crisys.safety.safety.PropagateStatement;
import edu.umn.cs.crisys.safety.safety.SpecStatement;
import edu.umn.cs.crisys.safety.safety.TemporalConstraint;
import edu.umn.cs.crisys.safety.safety.TransientConstraint;
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
import jkind.lustre.UnaryExpr;
import jkind.lustre.UnaryOp;
import jkind.lustre.VarDecl;

// If agreeNode is non-null, then we scope the replacement to only occur in one node.
// Otherwise, we do id replacement across all nodes.

public class AddFaultsToNodeVisitor extends AgreeASTMapVisitor {
	// Global data structures for traversal

	// This is the list of Lustre nodes that are used by the safety analysis that
	// may not
	// be part of the set of nodes used by AGREE
	private List<Node> globalLustreNodes;
	// Top of the node hierarchy in AGREE
	private AgreeNode topNode;
	// This map is used to track, for each fault, a list of paths to instances of
	// that fault (since nodes may be used in multiple locations), in order
	// to produce the top-level variables to activate faults. (Global)
	private Map<Fault, List<String>> mapFaultToLustreNames = new HashMap<Fault, List<String>>();
	// It is used to properly set up the top-level node for triggering faults.
	// Fault map: stores the faults associated with a node.
	// Keying off component instance rather than AgreeNode, just so we don't
	// have problems with "stale" AgreeNode references during transformations.
	private Map<ComponentInstance, List<Fault>> faultMap = new HashMap<>();
	private Map<ComponentInstance, List<HWFault>> hwfaultMap = new HashMap<>();
	// It is used to store src to dest fault propagations
	private HashSet<SafetyPropagation> propagations = new HashSet<>();

	// I am unsure as to whether this is necessary: it appears to map a fault to a
	// single
	// string. As such, I believe it is incorrectly constructed.
	private Map<Fault, String> mapFaultToPath = new HashMap<>();

	// Per node data structures: must be stored when visiting new node
	// This maps id to a pair consisting of the expression with the fault associated
	// with that
	// id and expression.
	private Map<String, List<Pair>> faultyVarsExpr = new HashMap<String, List<Pair>>();

	/*
	 * public accessor for faultMap: faultMap is used to properly set up the
	 * top-level node for triggering faults. fault map: stores the faults associated
	 * with a node. Keying off component instance rather than AgreeNode, just so we
	 * don't have problems with "stale" AgreeNode references during transformations.
	 */
	public Map<ComponentInstance, List<Fault>> getFaultMap() {
		return faultMap;
	}

	public AddFaultsToNodeVisitor() {
		super(new jkind.lustre.visitors.TypeMapVisitor());
	}

	@Override
	public AgreeProgram visit(AgreeProgram program) {
		globalLustreNodes = new ArrayList<>(program.globalLustreNodes);
		this.topNode = program.topNode;

		// do not call back to 'super'. This is BROKEN!
		AgreeNode topNode = this.visit(program.topNode);

		program = new AgreeProgram(program.agreeNodes, globalLustreNodes, program.globalTypes, topNode,
				program.containsRealTimePatterns);
		return program;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// AGREENODE TRAVERSAL STARTS HERE.
	//
	// since we don't totally know the traversal order, if we only
	// want to replace in a single node, we need to store the
	// 'stacked' inNode, then restore it after a traversal.
	//
	////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public AgreeNode visit(AgreeNode node) {
		Map<String, List<Pair>> parentFaultyVarsExpr = faultyVarsExpr;
		boolean isTop = (node == this.topNode);
		// gather SW/SYS faults
		List<Fault> faults = gatherFaults(globalLustreNodes, node, isTop);
		// gather HW faults
		List<HWFault> hwFaults = gatherHWFaults(globalLustreNodes, node, isTop);
		// rename var names in faults, like faultname_varname
		faults = renameFaultEqs(faults);

		if (faultMap.containsKey(node.compInst) || hwfaultMap.containsKey(node.compInst)) {
			throw new SafetyException("Node: " + node.id + " has been visited twice during AddFaultsToNodeVisitor!");
		}
		faultMap.put(node.compInst, faults);
		hwfaultMap.put(node.compInst, hwFaults);

		faultyVarsExpr = gatherFaultyOutputs(faults, node);
		// this will traverse through the decendent nodes
		node = super.visit(node);

		AgreeNodeBuilder nb = new AgreeNodeBuilder(node);
		// Change this nodes flag to reflect fault tree generation or not.
		if (AddFaultsToAgree.getTransformFlag() == 2) {
			nb.setFaultTreeFlag(true);
		}
		addNominalVars(node, nb);
		addFaultInputs(faults, nb);
		addHWFaultInputs(hwFaults, nb);
		addFaultLocalEqsAndAsserts(faults, nb);
		addFaultNodeEqs(faults, nb);

		if (isTop) {
			topNode = node;
			AnalysisBehavior maxFaults = this.gatherTopLevelFaultAnalysis(node);
			// gather path information for the faults (for creating names later)
			collectFaultPath(node, new ArrayList<>());
			this.gatherFaultPropagation(node);
			// empty path to pass to top level node fault
			// node id used as the path to pass to sub level node fault
			addTopLevelFaultDeclarations(node, nb);

			// This checks if we are doing max faults or probability behavior.
			// It will add the assertion to Lustre representing the required behavior.
			// If we want to generate the fault tree, this method changes in order
			// to not add assertions regarding behavior (i.e. no assertion about
			// max # faults).
			// The reason that the maxFault nullity check is here is because when we
			// are not at a top node (SystemInstanceImpl), we do not care about the
			// top level analysis constraints and hence maxFaults (the return value
			// from gatherTopLevelFaultAnalysis) is null.
			// if ((AddFaultsToAgree.getTransformFlag() == 1) && (maxFaults != null)) {
			if (AddFaultsToAgree.getTransformFlag() == 1) {
				addTopLevelFaultOccurrenceConstraints(maxFaults, node, nb);
			}
		}

		node = nb.build();
		faultyVarsExpr = parentFaultyVarsExpr;
		return node;
	}

	public void addNominalVars(AgreeNode node, AgreeNodeBuilder nb) {
		// Get key (faultyId string = root) and iterate through list of paths
		// (faultPath)
		// Create new nominal variables for each pair (root.path).
		for (String faultyId : faultyVarsExpr.keySet()) {
			AgreeVar out = findVar(node.outputs, (faultyId));
			if (out == null) {
				throw new SafetyException("A fault defined for " + node.id + "has a connection"
						+ " that is not a valid output for this component.");
			}
			nb.addInput(new AgreeVar(createNominalId((faultyId)), out.type, out.reference));
		}
	}

	public void addFaultInputs(List<Fault> faults, AgreeNodeBuilder nb) {
		for (Fault f : faults) {
			nb.addInput(new AgreeVar(createFaultNodeInputId(f.id), NamedType.BOOL, f.faultStatement));
		}
	}

	public void addHWFaultInputs(List<HWFault> hwfaults, AgreeNodeBuilder nb) {
		for (HWFault hwf : hwfaults) {
			nb.addInput(new AgreeVar(createFaultNodeInputId(hwf.id), NamedType.BOOL, hwf.hwFaultStatement));
		}
	}

	public void addFaultLocalEqsAndAsserts(List<Fault> faults, AgreeNodeBuilder nb) {
		for (Fault f : faults) {
			nb.addInput(f.safetyEqVars);
			nb.addAssertion(f.safetyEqAsserts);
		}
	}

	public List<Expr> constructNodeInputs(Fault f) {
		List<Expr> actuals = new ArrayList<>();
		for (VarDecl vd : f.faultNode.inputs) {
			// there is an extra "trigger" input
			Expr actual;
			if (vd.id.equalsIgnoreCase("trigger")) {
				if (f.faultInputMap.containsKey(vd.id)) {
					throw new SafetyException(
							"Trigger input for fault node should not be explicitly assigned by user.");
				}
				actual = new IdExpr(createFaultNodeInputId(f.id));
			} else {
				actual = f.faultInputMap.get(vd.id);
				// do any name conversions on the stored expression.
				actual = actual.accept(this);
				if (actual == null) {
					throw new SafetyException("fault node input: '" + vd.id + "' is not assigned.");
				}
			}
			actuals.add(actual);
		}
		return actuals;
	}

	public void addFaultNodeEqs(List<Fault> faults, AgreeNodeBuilder nb) {
		for (Fault f : faults) {
			List<IdExpr> lhs = new ArrayList<IdExpr>();
			for (VarDecl v : f.faultNode.outputs) {
				String lhsId = this.createFaultNodeEqId(f.id, v.id);
				AgreeVar actual = new AgreeVar(lhsId, v.type, f.faultStatement);
				nb.addLocal(actual);
				lhs.add(new IdExpr(lhsId));
				f.outputParamToActualMap.put(v.id, actual);
			}
			AgreeEquation eq = new AgreeEquation(lhs, new NodeCallExpr(f.faultNode.id, constructNodeInputs(f)),
					f.faultStatement);
			nb.addLocalEquation(eq);
		}

		// Binding happens HERE and is based on the map faultyVarsExpr.
		// Create an equality between the id and a nested WITH expression for each expr
		// in the list.
		for (String lhsWithStmtName : faultyVarsExpr.keySet()) {

			List<Pair> list = faultyVarsExpr.get(lhsWithStmtName);
			// Create nominal id name with key from this map
			String nomId = createNominalId(lhsWithStmtName);
			// base is the root of the WITH expression.
			Expr toAssign = new IdExpr(nomId);

			// Go through pairs of the list and create with statements.
			for (Pair pair : list) {
				// base : replace the expression with nominal expression
				// repl : go from the fault to the actual
				// toAssign: createNestedUpdateExpr using base, repl
				Expr base = replPathIdExpr(pair.ex, toAssign);
				Expr repl = faultToActual(pair.f, pair.ex);
				toAssign = SafetyUtil.createNestedUpdateExpr(base, repl);
			}
			// create new assertion expression : id = ((nominal_id with p1 := f1) with ...)
			nb.addAssertion(new AgreeStatement("Adding new safety analysis BinaryExpr",
					new BinaryExpr(new IdExpr(lhsWithStmtName), BinaryOp.EQUAL, toAssign), null));
		}
	}

	/*
	 * Replace AgreeVar id with nominal id
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

	/*
	 * Used to traverse two maps: fault -> output param -> actuals
	 */
	private Expr faultToActual(Fault f, Expr ex) {
		// Match pair.ex -> key of faultOutputMap
		// If this expression is not in map, return exception message
		String outputName = f.faultOutputMap.get(ex);
		if (outputName == null) {
			new Exception("Cannot find expression in mapping: faultToActual (AddFaultsToNodeVisitor class)");
		}
		// Use outputName to get value from outputParamToActualMap
		AgreeVar actual = f.outputParamToActualMap.get(outputName);
		// Create IdExpr out of actual string
		return new IdExpr(actual.id);
	}

	public Map<String, String> constructEqIdMap(Fault f, List<AgreeVar> eqVars) {
		HashMap<String, String> theMap = new HashMap<>();
		for (AgreeVar eqVar : eqVars) {
			theMap.put(eqVar.id, createFaultEqId(f.id, eqVar.id));
		}
		return theMap;
	}

	public Fault renameEqId(Fault f, Map<String, String> idMap) {
		Fault newFault = new Fault(f);
		newFault.safetyEqVars.clear();
		newFault.safetyEqAsserts.clear();
		newFault.faultOutputMap.clear();
		newFault.faultInputMap.clear();

		if (!f.triggers.isEmpty()) {
			throw new SafetyException("Triggers are currently unsupported for translation");
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

	public List<Fault> renameFaultEqs(List<Fault> faults) {
		List<Fault> newFaults = new ArrayList<>();
		for (Fault f : faults) {
			Map<String, String> idMap = constructEqIdMap(f, f.safetyEqVars);
			newFaults.add(this.renameEqId(f, idMap));
		}
		return newFaults;
	}

	@Override
	public Expr visit(IdExpr e) {
		if (faultyVarsExpr.containsKey(e.id)) {
			return new IdExpr(e.location, createNominalId(e.id));

		} else {
			return e;
		}
	}

	public static AgreeVar findVar(List<AgreeVar> vars, String id) {
		for (AgreeVar v : vars) {
			if (v.id.equals(id)) {
				return v;
			}
		}
		return null;
	}

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

	private void addIdToMap(HashMap<String, List<Pair>> faultyVarsExpr, Expr ex, String id, Fault f) {
		Pair pair = new Pair(ex, f);
		if (faultyVarsExpr.containsKey(id)) {
			faultyVarsExpr.get(id).add(pair);
		} else {
			List<Pair> list = new ArrayList<Pair>();
			list.add(pair);
			faultyVarsExpr.put(id, list);
		}
	}

	public String createFaultEventId(String base) {
		return "__fault__event__" + base;
	}

	public String createFaultIndependentActiveId(String base) {
		return "__fault__independently__active__" + base;
	}

	public String createFaultDependentActiveId(String base) {
		return "__fault__dependently__active__" + base;
	}

	public String createFaultNodeInputId(String base) {
		return "fault__trigger__" + base;
	}

	public String createNominalId(String output) {
		return "__fault__nominal__" + output;
	}

	public String createFaultEqId(String fault, String var) {
		return fault + "__" + var;
	}

	public String createFaultNodeEqId(String fault, String var) {

		return fault + "__node__" + var;
	}

	public List<Fault> gatherFaults(List<Node> globalLustreNodes, AgreeNode node, boolean isTop) {
		List<SpecStatement> specs = SafetyUtil.collapseAnnexes(SafetyUtil.getSafetyAnnexes(node, isTop));

		List<Fault> faults = new ArrayList<>();
		// reset fault count for the new Agree Node
		FaultASTBuilder.resetFaultCounter();

		for (SpecStatement s : specs) {
			if (s instanceof FaultStatement) {
				FaultStatement fs = (FaultStatement) s;
				FaultASTBuilder builder = new FaultASTBuilder(globalLustreNodes, node);
				Fault safetyFault = builder.buildFault(fs);
				faults.add(safetyFault);
			}
		}
		return faults;
	}

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

	public AnalysisBehavior gatherTopLevelFaultAnalysis(AgreeNode node) {

//		// Make sure this is the top node. We do not need to check
//		// top level fault analysis information if we are not at the top node.
//		// Because of the way Agree performs its analysis, at any given comopositional
//		// run, the 'top node' is the containing component for that analysis run. this is not
//		// the true top node, so we check to see if we have a system instance implementation.
//		// This is the actual top node.
//		if (!(node.compInst instanceof SystemInstanceImpl)) {
//			return null;
//		}

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
					throw new SafetyException("Multiple analysis specifications found.  Only one can be processed");
				}
				found = true;
			}
		}
		if (!found) {
			throw new SafetyException("No analysis statement; unable to run safety analysis");
		}
		return ab;
	}

	// Identify the fault associated with a ComponentInstance given the fault name
	// and the component path
	public BaseFault findFaultInCompInst(String faultName, NestedDotID compPath) {
		List<ComponentInstance> compInsts = new ArrayList<ComponentInstance>(faultMap.keySet());

		for (ComponentInstance compInst : compInsts) {
			if (compInst.getName().equals(compPath.getBase().getName())) {
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
			if (compInst.getName().equals(compPath.getBase().getName())) {
				List<HWFault> hwfaults = new ArrayList<HWFault>(hwfaultMap.get(compInst));
				for (HWFault hwfault : hwfaults) {
					if (hwfault.name.equals(faultName)) {
						return hwfault;
					}
				}
			}
		}
		return null;
		// throw new SafetyException("Unable to identify fault for " + faultName + "@" + compPath.getBase().getName());
	}

	public void gatherFaultPropagation(AgreeNode node) {

		List<SpecStatement> specs = SafetyUtil.collapseAnnexes(SafetyUtil.getSafetyAnnexes(node, true));

		for (SpecStatement s : specs) {
			if (s instanceof PropagateStatement) {
				PropagateStatement ps = (PropagateStatement) s;
				Iterator<String> srcFaultIt = ps.getSrcFaultList().iterator();
				Iterator<NestedDotID> srcCompPathIt = ps.getSrcComp_path().iterator();

				// create a SafetyPropagation
				BaseFault srcFault = null;
				// for each src fault name and path, locate the fault
				while (srcFaultIt.hasNext() && srcCompPathIt.hasNext()) {
					NestedDotID srcCompPath = srcCompPathIt.next();
					String srcFaultName = srcFaultIt.next();
					srcFault = findFaultInCompInst(srcFaultName, srcCompPath);
					if (srcFault != null) {
						// for each destination fault name and path, locate the fault
						BaseFault destFault = null;
						Iterator<String> destFaultIt = ps.getDestFaultList().iterator();
						Iterator<NestedDotID> destCompPathIt = ps.getDestComp_path().iterator();
						while (destFaultIt.hasNext() && destCompPathIt.hasNext()) {
							NestedDotID destCompPath = destCompPathIt.next();
							String destFaultName = destFaultIt.next();
							destFault = findFaultInCompInst(destFaultName, destCompPath);
							SafetyPropagation propagation = new SafetyPropagation(srcFault, destFault);
							propagations.add(propagation);
						}
					}
				}
			}
		}
	}

	/*
	 * 1. For each subcomponent node For each subcomponent fault (depth-first)
	 * 0.Perform a traversal to find all the node/fault pairs
	 * 1a. Define an unconstrained local eq. to represent each fault-event
	 * 1b. Define a constrained local eq. to assign fault-active value depending on fault
	 * duration in node.
	 * 1c. Assign subcomponent fault input to fault-active eq with
	 * assertions (yay!) (test: print updated AST)
	 * 2. Assign faults-active equation
	 * to sum of all fault-active values (test: print updated AST)
	 * 3. Assert that
	 * this value is <= 1 (FOR NOW!) (test: print updated AST)
	 * 4. Use shiny new
	 * fault annex to perform safety analysis (test: analysis results)
	 */

	public String addPathDelimiters(List<String> path, String var) {
		String id = "";
		for (String p : path) {
			id = id + p + "__";
		}
		return id + var;
	}

	public Expr createPermanentExpr(Expr varId, Expr eventExpr) {
		Expr latch = new BinaryExpr(eventExpr, BinaryOp.ARROW,
				new BinaryExpr(eventExpr, BinaryOp.OR, new UnaryExpr(UnaryOp.PRE, varId)));
		Expr equate = new BinaryExpr(varId, BinaryOp.EQUAL, latch);

		return equate;
	}

	public Expr createTransientExpr(Expr varId, Expr expr) {
		Expr equate = new BinaryExpr(varId, BinaryOp.EQUAL, expr);
		return equate;
	}

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
			throw new SafetyException("Unknown constraint type during translation of fault " + f.id);
		}
		builder.addAssertion(new AgreeStatement("", assertIndependentExpr, f.faultStatement));
		builder.addAssertion(new AgreeStatement("", assertDependentExpr, f.faultStatement));
	}

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
			throw new SafetyException("Unknown constraint type during translation of fault " + hwf.id);
		}
		builder.addAssertion(new AgreeStatement("", assertIndependentExpr, hwf.hwFaultStatement));
		builder.addAssertion(new AgreeStatement("", assertDependentExpr, hwf.hwFaultStatement));
	}

	/*
	 * Create Lustre name using addPathDelimiters Create map from fault to the
	 * constructed Lustre name (used in renaming) Add in the __fault__active__
	 * portion of the Lustre name Add assertion to the builder with the fault
	 * statement equated to the active var id.
	 */
	public void mapFaultActiveToNodeInterface(Fault f, List<String> path, String base, AgreeNodeBuilder builder) {
		String interfaceVarId = addPathDelimiters(path, this.createFaultNodeInputId(f.id));
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

	public void mapFaultActiveToNodeInterface(HWFault hwf, List<String> path, String base, AgreeNodeBuilder builder) {
		String interfaceVarId = addPathDelimiters(path, this.createFaultNodeInputId(hwf.id));
		String indepentlyActiveVarId = this.createFaultIndependentActiveId(base);
		String depentlyActiveVarId = this.createFaultDependentActiveId(base);

		Expr equate = new BinaryExpr(new IdExpr(interfaceVarId), BinaryOp.EQUAL,
				new BinaryExpr(new IdExpr(indepentlyActiveVarId), BinaryOp.OR, new IdExpr(depentlyActiveVarId)));
		builder.addAssertion(new AgreeStatement("", equate, hwf.hwFaultStatement));
	}

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

	public void addTopLevelFaultDeclarations(AgreeNode currentNode, AgreeNodeBuilder nb) {

		List<Fault> faults = this.faultMap.get(currentNode.compInst);

		// Add unconstrained input and constrained local to represent fault event and
		// whether or not fault is currently active.
		for (Fault f : faults) {
			String base = addPathDelimiters(f.path, f.id);
			nb.addInput(new AgreeVar(this.createFaultEventId(base), NamedType.BOOL, f.faultStatement));

			// Here is where fault indep variables are added to lustre.
			// For fault tree generation, this must be added locally,
			// assigned to false, and given the --%IVC command.
			if (AddFaultsToAgree.getTransformFlag() == 1) {
				// If transform flag is 1, that means we are doing the max/prob analysis
				nb.addInput(new AgreeVar(this.createFaultIndependentActiveId(base), NamedType.BOOL, f.faultStatement));
			} else {
				// If transform flag is 2, then we want to generate fault tree.
				// In this case, we add the indep as a local var.
				AgreeVar newVar = new AgreeVar(this.createFaultIndependentActiveId(base), NamedType.BOOL,
						f.faultStatement);
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

			// Add dependent as per usual.
			nb.addInput(new AgreeVar(this.createFaultDependentActiveId(base), NamedType.BOOL, f.faultStatement));

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

			// constrain fault-active depending on transient / permanent & map it to a
			// fault in the node interface
			// take the propagation map when constrainFaultActive
			constrainFaultActive(f, base, nb);
			mapFaultActiveToNodeInterface(f, f.path, base, nb);
		}

		List<HWFault> hwfaults = this.hwfaultMap.get(currentNode.compInst);

		// Add unconstrained input and constrained local to represent fault event and
		// whether or not fault is currently active.
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

		for (AgreeNode n : currentNode.subNodes) {
			addTopLevelFaultDeclarations(n, nb);
		}
	}

	/*****************************************************************
	 *
	 * # of occurrence-based fault calculations
	 *
	 ******************************************************************/

	public Expr createSumExpr(Expr cond) {
		return new IfThenElseExpr(cond, new IntExpr(1), new IntExpr(0));
	}

	public void getFaultCountExprList(AgreeNode currentNode, List<String> path, List<Expr> sumExprs) {

		List<Fault> faults = this.faultMap.get(currentNode.compInst);
		for (Fault f : faults) {
			// only add independently active fault to sumExprs
			String base = addPathDelimiters(path, f.id);
			sumExprs.add(createSumExpr(new IdExpr(this.createFaultIndependentActiveId(base))));
		}
		for (AgreeNode n : currentNode.subNodes) {
			List<String> ext = new ArrayList<>(path);
			ext.add(n.id);
			getFaultCountExprList(n, ext, sumExprs);
		}
	}

	// collect the list of expressions for the source faults in the propagations
	// whose target fault is the current fault
	// the names of those source faults are created through
	// createFaultIndependentActiveId
	public void getSrcFaultExprList(BaseFault f, List<Expr> faultExprs) {

		for (SafetyPropagation propagation : propagations) {
			if (propagation.destFault.equals(f)) {
				if (propagation.srcFault instanceof HWFault) {
					// use the triggering event for HW fault in the src fault expr, so to get the
					// effect
					// from both hw dependent and hw independent faults
					String interfaceVarId = addPathDelimiters(((HWFault) propagation.srcFault).path,
							this.createFaultNodeInputId(((HWFault) propagation.srcFault).id));
					faultExprs.add(new IdExpr(interfaceVarId));
				} else if (propagation.srcFault instanceof Fault) {
					String base = addPathDelimiters(((Fault) propagation.srcFault).path,
							((Fault) propagation.srcFault).id);
					faultExprs.add(new IdExpr(this.createFaultIndependentActiveId(base)));
				}
			}
		}
	}

	public Expr buildFaultCountExpr(List<Expr> exprList, int index) {
		if (index > exprList.size() - 1) {
			return new IntExpr(0);
		} else if (index == exprList.size() - 1) {
			return exprList.get(index);
		} else {
			return new BinaryExpr(exprList.get(index), BinaryOp.PLUS, buildFaultCountExpr(exprList, index + 1));
		}
	}

	public Expr buildFaultDisjunctionExpr(List<Expr> exprList, int index) {
		if (index > exprList.size() - 1) {
			return new BoolExpr(false);
		} else if (index == exprList.size() - 1) {
			return exprList.get(index);
		} else {
			return new BinaryExpr(exprList.get(index), BinaryOp.OR, buildFaultDisjunctionExpr(exprList, index + 1));
		}
	}

	public void addTopLevelMaxFaultOccurrenceConstraint(int maxFaults, AgreeNode topNode, AgreeNodeBuilder builder) {

		// add a global fault count
		String id = "__fault__global_count";
		builder.addInput(new AgreeVar(id, NamedType.INT, topNode.reference));

		// assign it.
		List<Expr> sumExprs = new ArrayList<>();
		getFaultCountExprList(topNode, new ArrayList<>(), sumExprs);
		Expr faultCountExpr = buildFaultCountExpr(sumExprs, 0);
		Expr equate = new BinaryExpr(new IdExpr(id), BinaryOp.EQUAL, faultCountExpr);
		builder.addAssertion(new AgreeStatement("", equate, topNode.reference));

		// assert that the value is <= 1
		Expr lessEqual = new BinaryExpr(new IdExpr(id), BinaryOp.LESSEQUAL, new IntExpr(maxFaults));
		builder.addAssertion(new AgreeStatement("", lessEqual, topNode.reference));

		// and Viola!
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

	class FaultSetProbability implements Comparable<FaultSetProbability> {
		// invariant: probability should equal the multiple of all probabilities in
		// elements.
		public double probability;
		public Set<FaultProbability> elements;

		public FaultSetProbability(double probability, FaultProbability fp) {
			this.probability = probability;
			this.elements = Collections.singleton(fp);
		}

		public FaultSetProbability(double probability, FaultSetProbability base, FaultProbability fp) {
			this.probability = probability;
			this.elements = new HashSet<>(base.elements);
			this.elements.add(fp);
		}

		@Override
		public int compareTo(FaultSetProbability o) {
			// Want to sort LARGEST first, so negate probabilities.
			return Double.compare(-probability, -o.probability);
		}

		@Override
		public String toString() {
			return "(" + probability + ", " + elements.toString() + " )";
		}
	}

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

	public void addTopLevelMaxFaultOccurrenceConstraint(double minProbability, AgreeNode topNode,
			AgreeNodeBuilder builder) {

		ArrayList<FaultProbability> elementProbabilities = new ArrayList<>();
		ArrayList<FaultSetProbability> faultCombinationsAboveThreshold = new ArrayList<>();
		PriorityQueue<FaultSetProbability> pq = new PriorityQueue<>();

		// gather all fault probabilities. If there are no faults, exit out.
		getFaultProbExprList(topNode, new ArrayList<>(), elementProbabilities);
		Collections.sort(elementProbabilities);
		if (elementProbabilities.isEmpty()) {
			return;
		}

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

		// So...now we have a priority queue with remaining fault combinations to be
		// checked
		// for addition. The PQ preserves the invariant that highest probability
		// elements are
		// first. We attempt to combine with remainder (also in priority order).
		// If unable to combine because combination below threshold, remove rest of
		// elementProbability list (the rest will be below threshold for all subsequent
		// elements).
		// Complete when either the PQ or the element list is empty.
		while (!pq.isEmpty() && !remainder.isEmpty()) {
			FaultSetProbability fsp = pq.remove();
			for (int i = 0; i < remainder.size(); i++) {
				FaultProbability fp = remainder.get(i);
				double setProbability = fp.probability * fsp.probability;
				if (setProbability < minProbability) {
					remainder.subList(i, remainder.size()).clear();
				} else if (!fsp.elements.contains(fp)) {
					FaultSetProbability newSet = new FaultSetProbability(setProbability, fsp, fp);
					pq.add(newSet);
					faultCombinationsAboveThreshold.add(newSet);
				}
			}
		}

		// Now faultCombinationsAboveThreshold contains all the valid fault combinations
		// of the independently active faults

		// If the propagations set is not empty, then for each fault in each valid fault
		// combination
		// see if it appears as a source fault in the propagations
		// if yes, insert the destination fault in the same valid fault combination
		// (fault set)
		// without changing the fault set's probability
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

		// With the valid fault combinations including dependent faults, and
		// noFaultExpr has the default (no-fault) case. Let's construct a proposition.
		Set<FaultProbability> elementProbabilitySet = new HashSet<>(elementProbabilities);
		Expr faultHypothesis = getNoFaultProposition(elementProbabilitySet);
		for (FaultSetProbability fsp : faultCombinationsAboveThreshold) {
			Set<FaultProbability> goodElements = new HashSet<>(elementProbabilities);
			goodElements.removeAll(fsp.elements);
			Expr local = getNoFaultProposition(goodElements);
			faultHypothesis = new BinaryExpr(local, BinaryOp.OR, faultHypothesis);
		}

		// Add this fault hypothesis as an assertion.
		builder.addAssertion(new AgreeStatement("", faultHypothesis, topNode.reference));
	}

	// This will add max fault behavior or probability behavior depending on what is
	// entered in the top level annex.
	// If the user chooses the menu item for fault tree generation, we wish to
	// not add the assertions for the max/prob behavior, but instead add in the
	// IVC commands.
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

	/*
	 * Public accessor for the Map<Fault, String: LustreName> This map is created in
	 * mapFaultToActiveNodeInterface (line 410).
	 */
	public Map<Fault, List<String>> getFaultToLustreNameMap() {
		return mapFaultToLustreNames;
	}

	/*
	 * Public accessor for the Map<Fault,String:faultyOutputName> This map is
	 * created in gatherFaultyOutputs (line 270).
	 */
	public Map<Fault, String> getMapFaultToPath() {
		return mapFaultToPath;
	}

	public class Pair {
		private Expr ex;
		private Fault f;

		public Pair(Expr ex, Fault f) {
			this.ex = ex;
			this.f = f;
		}
	}

}
