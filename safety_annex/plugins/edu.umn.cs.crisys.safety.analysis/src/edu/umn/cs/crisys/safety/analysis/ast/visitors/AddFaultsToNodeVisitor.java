package edu.umn.cs.crisys.safety.analysis.ast.visitors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
import edu.umn.cs.crisys.safety.safety.FaultStatement;
import edu.umn.cs.crisys.safety.safety.SpecStatement;
import edu.umn.cs.crisys.safety.util.SafetyUtil;
import jkind.lustre.BinaryExpr;
import jkind.lustre.BinaryOp;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.NamedType;
import jkind.lustre.Node;
import jkind.lustre.NodeCallExpr;
import jkind.lustre.VarDecl;
import jkind.lustre.visitors.ExprMapVisitor;


// If agreeNode is non-null, then we scope the replacement to only occur in one node.
// Otherwise, we do id replacement across all nodes.

public class AddFaultsToNodeVisitor extends AgreeASTMapVisitor {

	List<Node> globalLustreNodes;
	AgreeNode topNode; 
	Set<String> faultyVars = new HashSet<>();
	
	public AddFaultsToNodeVisitor() {
		super(new jkind.lustre.visitors.TypeMapVisitor());
	}

	@Override
	public AgreeProgram visit(AgreeProgram program) {
		globalLustreNodes = new ArrayList<>(program.globalLustreNodes);
		this.topNode = program.topNode;  
		program = super.visit(program);
		program = new AgreeProgram(program.agreeNodes, globalLustreNodes, program.globalTypes, program.topNode, program.containsRealTimePatterns);
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
		faultyVars = gatherFaultyOutputs(faults, node);
		
		node = super.visit(node);

		AgreeNodeBuilder nb = new AgreeNodeBuilder(node);
		addNominalVars(node, nb); 
		addFaultInputs(faults, nb);
		addFaultLocalEqsAndAsserts(faults, nb); 
		addFaultNodeEqs(faults, nb); 
		
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
			nb.addInput(new AgreeVar(createFaultId(f.id), NamedType.BOOL, f.faultStatement));
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
				actual = new IdExpr(createFaultId(f.id));
			} else {
				actual = f.faultInputMap.get(vd.id);
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
			System.out.println("Attempting to print node call: " + f.faultNode.id);
			System.out.println(eq.toString());
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
	
	public String createFaultId(String base) {
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

}
