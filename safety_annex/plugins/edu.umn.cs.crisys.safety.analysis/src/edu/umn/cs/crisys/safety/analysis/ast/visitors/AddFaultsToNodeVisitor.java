package edu.umn.cs.crisys.safety.analysis.ast.visitors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osate.aadl2.instance.ComponentInstance;

import com.rockwellcollins.atc.agree.analysis.ast.AgreeEquation;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeNode;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeNodeBuilder;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeProgram;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeStatement;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeVar;
import com.rockwellcollins.atc.agree.analysis.ast.visitors.AgreeASTMapVisitor;

import edu.umn.cs.crisys.safety.analysis.SafetyException;
import edu.umn.cs.crisys.safety.analysis.transform.Fault;
import edu.umn.cs.crisys.safety.analysis.transform.FaultASTBuilder;
import edu.umn.cs.crisys.safety.safety.AnalysisStatement;
import edu.umn.cs.crisys.safety.safety.FaultStatement;
import edu.umn.cs.crisys.safety.safety.TemporalConstraint;
import edu.umn.cs.crisys.safety.safety.TransientConstraint;
import edu.umn.cs.crisys.safety.safety.PermanentConstraint;
import edu.umn.cs.crisys.safety.safety.SpecStatement;
import edu.umn.cs.crisys.safety.util.SafetyUtil;
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
import jkind.lustre.UnaryExpr;
import jkind.lustre.UnaryOp;
import jkind.lustre.VarDecl;


// If agreeNode is non-null, then we scope the replacement to only occur in one node.
// Otherwise, we do id replacement across all nodes.

public class AddFaultsToNodeVisitor extends AgreeASTMapVisitor {

	private List<Node> globalLustreNodes;
	private AgreeNode topNode; 
	private Set<String> faultyVars = new HashSet<>();
	
	// Fault map: stores the faults associated with a node.
	// Keying off component instance rather than AgreeNode, just so we don't
	// have problems with "stale" AgreeNode references during transformations.

	// It is used to properly set up the top-level node for triggering faults. 
	private Map<ComponentInstance, List<Fault>> faultMap = new HashMap<>(); 
	
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
		
		// do not call back to 'super'.  This is BROKEN!
		AgreeNode topNode = this.visit(program.topNode);

		program = new AgreeProgram(program.agreeNodes, globalLustreNodes, program.globalTypes, topNode, program.containsRealTimePatterns);
		return program;
	}
	
	// since we don't totally know the traversal order, if we only
	// want to replace in a single node, we need to store the 
	// 'stacked' inNode, then restore it after a traversal.
	@Override
	public AgreeNode visit(AgreeNode node) {
		Set<String> oldFaultyVars = faultyVars;

		boolean isTop = (node == this.topNode);
		List<Fault> faults = gatherFaults(globalLustreNodes, node, isTop); 
		faults = renameFaultEqs(faults);
		
		if (faultMap.containsKey(node.compInst)) {
			System.out.println("Node: " + node.id + " has already been visited!");
			throw new SafetyException("Node: " + node.id + " has been visited twice during AddFaultsToNodeVisitor!");
		}
		faultMap.put(node.compInst, faults);

		faultyVars = gatherFaultyOutputs(faults, node);
		node = super.visit(node);

		AgreeNodeBuilder nb = new AgreeNodeBuilder(node);
		addNominalVars(node, nb); 
		addFaultInputs(faults, nb);
		addFaultLocalEqsAndAsserts(faults, nb); 
		addFaultNodeEqs(faults, nb); 

		if (isTop) {
			topNode = node;
			int maxFaults = this.gatherTopLevelFaultCount(node);
			addTopLevelFaultDeclarations(node, new ArrayList<>(), nb);
			addTopLevelFaultOccurrenceConstraints(maxFaults, node, nb);
		}
		
		node = nb.build();		
		
		faultyVars = oldFaultyVars;
		return node;
	}
	
	public void addNominalVars(AgreeNode node, AgreeNodeBuilder nb) {
		for (String faultyId : faultyVars) {
			AgreeVar out = findVar(node.outputs, faultyId);
			nb.addInput(new AgreeVar(createNominalId(faultyId), out.type, out.reference));
		}
	}

	public void addFaultInputs(List<Fault> faults, AgreeNodeBuilder nb) {
		for (Fault f: faults) {
			nb.addInput(new AgreeVar(createFaultNodeInputId(f.id), NamedType.BOOL, f.faultStatement));
		}
	}
	
	public void addFaultLocalEqsAndAsserts(List<Fault> faults, AgreeNodeBuilder nb) {
		for (Fault f: faults) {
			nb.addInput(f.safetyEqVars);
			nb.addAssertion(f.safetyEqAsserts);
		}
	}

	public List<Expr> constructNodeInputs(Fault f) {
		List<Expr> actuals = new ArrayList<>(); 
		for (VarDecl vd: f.faultNode.inputs) {
			// there is an extra "trigger" input 
			Expr actual ;
			if (vd.id.equalsIgnoreCase("trigger")) {
				if (f.faultInputMap.containsKey(vd.id)) {
					throw new SafetyException("Trigger input for fault node should not be explicitly assigned by user.");
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
		for (Fault f: faults) {
			List<IdExpr> lhs = new ArrayList<IdExpr>();
			for (VarDecl v: f.faultNode.outputs) {
				String lhsId = this.createFaultNodeEqId(f.id, v.id);
				nb.addLocal(new AgreeVar(lhsId, v.type, f.faultStatement));
				lhs.add(new IdExpr(lhsId));
			}
			
			AgreeEquation eq = new AgreeEquation(lhs, 
					new NodeCallExpr(f.faultNode.id, constructNodeInputs(f)), f.faultStatement);
			nb.addLocalEquation(eq);
			
			for(Map.Entry<String, IdExpr> outMap: f.faultOutputMap.entrySet()) {
				String lhsId = this.createFaultNodeEqId(f.id,  outMap.getKey());
				nb.addAssertion(
					new AgreeStatement("",
						new BinaryExpr(new IdExpr(lhsId), BinaryOp.EQUAL, outMap.getValue()),
						f.faultStatement));
			}
		}
	}
	
	public Map<String, String> constructEqIdMap(Fault f, List<AgreeVar> eqVars) {
		Map<String, String> theMap = new HashMap<>(); 
		for (AgreeVar eqVar: eqVars) {
			theMap.put(eqVar.id, createFaultEqId(f.id, eqVar.id));
		}
		return theMap;
	}
	
	public Fault renameEqId(Fault f, Map<String, String> idMap) {
		Fault newFault = new Fault(f.faultStatement, f.id);
		newFault.duration = f.duration;
		newFault.explanitoryText = f.explanitoryText; 
		newFault.faultNode = f.faultNode; 
		newFault.triggers = f.triggers;
		
		if (!f.triggers.isEmpty()) {
			throw new SafetyException("Triggers are currently unsupported for translation");
		}
		
		// update the variable declarations
		for (AgreeVar eq: f.safetyEqVars) {
			if (idMap.containsKey(eq.id)) {
				eq = new AgreeVar(createFaultEqId(f.id, eq.id), eq.type, eq.reference);
			}
			newFault.safetyEqVars.add(eq); 
		}
		
		ReplaceIdVisitor visitor = new ReplaceIdVisitor(idMap);
		for (AgreeStatement s: f.safetyEqAsserts) {
			newFault.safetyEqAsserts.add(visitor.visit(s));
		}
		
		for (Map.Entry<String, IdExpr> element: f.faultOutputMap.entrySet()) {
			newFault.faultOutputMap.put(element.getKey(), visitor.visit(element.getValue()));
		}
		
		for (Map.Entry<String, Expr> element: f.faultInputMap.entrySet()) {
			newFault.faultInputMap.put(element.getKey(), element.getValue().accept(visitor));
		}
		
		return newFault;
	}
	
	public List<Fault> renameFaultEqs(List<Fault> faults) {
		List<Fault> newFaults = new ArrayList<>(); 
		for (Fault f: faults) {
			Map<String, String> idMap = constructEqIdMap(f, f.safetyEqVars);
			newFaults.add(this.renameEqId(f, idMap));
		}
		return newFaults;
	}
	
	
	@Override
	public Expr visit(IdExpr e) {
		if (faultyVars.contains(e.id)) {
			return new IdExpr(e.location, createNominalId(e.id));
		} else {
			return e;
		}
	}

	public static AgreeVar findVar(List<AgreeVar> vars, String id) {
		for (AgreeVar v: vars) {
			if (v.id.equals(id)) {
				return v;
			}
		}
		return null;
	}
	
	private Set<String> gatherFaultyOutputs(List<Fault> faults, AgreeNode node) {
		Set<String> outputSet = new HashSet<>(); 
		for (Fault f: faults) {
			for (IdExpr ide: f.faultOutputMap.values()) {
				if (outputSet.contains(ide.id)) {
					throw new SafetyException("Error: more than one fault affect output: '" + 
							ide.id + "'.");
				} else {
					outputSet.add(ide.id);
				}
			}
		}
		return outputSet;
	}
	
	public String createFaultEventId(String base) {
		return "__fault__event__" + base;		
	}
	
	public String createFaultActiveId(String base) {
		return "__fault__active__" + base;		
	}
	
	public String createFaultNodeInputId(String base) {
		return "__fault__trigger__" + base;		
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
		List<SpecStatement> specs = 
			SafetyUtil.collapseAnnexes(
				SafetyUtil.getSafetyAnnexes(node, isTop));
		
		List<Fault> faults = new ArrayList<>(); 
		for (SpecStatement s : specs) {
			if (s instanceof FaultStatement) {
				FaultStatement fs = (FaultStatement)s;
				FaultASTBuilder builder = new FaultASTBuilder(globalLustreNodes, node);
				Fault safetyFault = builder.buildFault(fs);
				faults.add(safetyFault); 
			}
		}
		return faults;
	}

	public int gatherTopLevelFaultCount(AgreeNode node) {
		int maxFaults = 1;
		boolean found = false;

		List<SpecStatement> specs = 
			SafetyUtil.collapseAnnexes(
				SafetyUtil.getSafetyAnnexes(node, true));

		 
		for (SpecStatement s : specs) {
			if (s instanceof AnalysisStatement) {
				AnalysisStatement as = (AnalysisStatement)s;
				maxFaults = Integer.valueOf(as.getMaxFaults());
				if (maxFaults < 0) {
					throw new SafetyException("Maximum number of faults must be non-negative.");
				}
				if (found) {
					throw new SafetyException("Multiple analysis specifications found.  Only one can be processed");
				}
				found = true;
			} 
		}
		return maxFaults;
	}
	
	
	/*
	 * 1. For each subcomponent node
		For each subcomponent fault (depth-first)
			0. Perform a traversal to find all the node/fault pairs
			1a. Define an unconstrained local eq. to represent each fault-event 
			1b. Define a constrained local eq. to assign fault-active value depending on 
				fault duration in node.
			1c. Assign subcomponent fault input to fault-active eq with assertions (yay!) 
	(test: print updated AST)
		2. Assign faults-active equation to sum of all fault-active values
			(test: print updated AST)
		3. Assert that this value is <= 1 (FOR NOW!)	
			(test: print updated AST)
		4. Use shiny new fault annex to perform safety analysis
			(test: analysis results)
	 */

	
	public String addPathDelimiters(List<String> path, String var) {
		String id = ""; 
		for (String p: path) {
			id = id + p + "__";
		}
		return id + var;
	}

	public Expr createPermanentExpr(Expr varId, Expr expr) {
		Expr latch = 
			new BinaryExpr(expr, BinaryOp.ARROW, 
				new BinaryExpr(expr, BinaryOp.OR, 
					new UnaryExpr(UnaryOp.PRE, varId)));
		Expr equate = 
			new BinaryExpr(varId, BinaryOp.EQUAL, latch);
		
		return equate;
	}
	
	public Expr createTransientExpr(Expr varId, Expr expr) {
		Expr equate = 
				new BinaryExpr(varId, BinaryOp.EQUAL, expr);
		return equate;
	}
	
	public void constrainFaultActive(Fault f, String nameBase, AgreeNodeBuilder builder) {
		IdExpr eventExpr = new IdExpr(this.createFaultEventId(nameBase));
		IdExpr activeExpr = new IdExpr(this.createFaultActiveId(nameBase));
		Expr assertExpr;
		
		TemporalConstraint tc = f.duration.getTc();
		if (tc instanceof PermanentConstraint) {
			assertExpr = createPermanentExpr(activeExpr, eventExpr); 
		} else if (tc instanceof TransientConstraint){
			System.out.println("WARNING: ignoring duration on transient faults");
			assertExpr = createTransientExpr(activeExpr, eventExpr);
		} else {
			throw new SafetyException("Unknown constraint type during translation of fault "+ f.id);
		}
		builder.addAssertion(new AgreeStatement("", assertExpr, f.faultStatement));
	}

	public void mapFaultActiveToNodeInterface(Fault f, List<String> path, String base, AgreeNodeBuilder builder) {
		String interfaceVarId = addPathDelimiters(path, this.createFaultNodeInputId(f.id));
		String activeVarId = this.createFaultActiveId(base);
		Expr equate = new BinaryExpr(new IdExpr(interfaceVarId), BinaryOp.EQUAL, new IdExpr(activeVarId));
		builder.addAssertion(new AgreeStatement("", equate, f.faultStatement));
	}
	
	
	public void addTopLevelFaultDeclarations(
			AgreeNode currentNode, 
			List<String> path, 
			AgreeNodeBuilder nb) {
		
		List<Fault> faults = this.faultMap.get(currentNode.compInst) ; 
		
		// Add unconstrained input and constrained local to represent fault event and 
		// whether or not fault is currently active.
		for (Fault f: faults) {
			String base = addPathDelimiters(path, f.id);
			nb.addInput(new AgreeVar(this.createFaultEventId(base), NamedType.BOOL, f.faultStatement));
			nb.addInput(new AgreeVar(this.createFaultActiveId(base), NamedType.BOOL, f.faultStatement));

			// constrain fault-active depending on transient / permanent & map it to a 
			// fault in the node interface
			constrainFaultActive(f, base, nb);
			mapFaultActiveToNodeInterface(f, path, base, nb);
		}
		
		for (AgreeNode n: currentNode.subNodes) {
			List<String> ext = new ArrayList<>(path);
			ext.add(n.id);
			addTopLevelFaultDeclarations(n, ext, nb);
		}
	}

	public Expr createSumExpr(Expr cond) {
		return new IfThenElseExpr(cond, new IntExpr(1), new IntExpr(0));
	}
	
	public void getFaultCountExprList(
			AgreeNode currentNode, 
			List<String> path, 
			List<Expr> sumExprs) {

		List<Fault> faults = this.faultMap.get(currentNode.compInst) ; 
		for (Fault f: faults) {
			String base = addPathDelimiters(path, f.id);
			sumExprs.add(
				createSumExpr(new IdExpr(this.createFaultActiveId(base))));
		}
		
		for (AgreeNode n: currentNode.subNodes) {
			List<String> ext = new ArrayList<>(path);
			ext.add(n.id);
			getFaultCountExprList(n, ext, sumExprs);
		}
	}
	
	public Expr buildFaultCountExpr(List<Expr> exprList, int index) {
		if (index > exprList.size() - 1) {
			return new IntExpr(0);
		}
		else if (index == exprList.size() - 1) {
			return exprList.get(index);
		} else {
			return new BinaryExpr(exprList.get(index), BinaryOp.PLUS, 
					buildFaultCountExpr(exprList, index+1));
		}
	}
	
	public void addTopLevelFaultOccurrenceConstraints(
			int maxFaults, 
			AgreeNode topNode,
			AgreeNodeBuilder builder) {
		
		// add a global fault count
		String id = "__fault__global_count"; 
		builder.addInput(new AgreeVar(id, NamedType.INT, topNode.reference));

		// assign it.
		List<Expr> sumExprs = new ArrayList<>(); 
		getFaultCountExprList(topNode, new ArrayList<>(), sumExprs);
		Expr faultCountExpr = buildFaultCountExpr(sumExprs, 0);
		Expr equate = 
			new BinaryExpr(new IdExpr(id), BinaryOp.EQUAL, faultCountExpr);
		builder.addAssertion(new AgreeStatement("", equate, topNode.reference));

		// assert that the value is <= 1
		Expr lessEqual = 
			new BinaryExpr(new IdExpr(id), BinaryOp.LESSEQUAL, new IntExpr(maxFaults));
		builder.addAssertion(new AgreeStatement("", lessEqual, topNode.reference));
		
		// and Viola!
	}
}
