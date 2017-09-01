package edu.umn.cs.crisys.safety.analysis.ast.visitors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.osate.aadl2.NamedElement;

import com.rockwellcollins.atc.agree.agree.NestedDotID;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeASTElement;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeVar;
import com.rockwellcollins.atc.agree.analysis.ast.visitors.AgreeASTMapVisitor;
import com.rockwellcollins.atc.agree.analysis.ast.visitors.AgreeASTVisitor;

import edu.umn.cs.crisys.safety.analysis.SafetyException;
import edu.umn.cs.crisys.safety.safety.FaultStatement;
import edu.umn.cs.crisys.safety.safety.FaultSubcomponent;
import edu.umn.cs.crisys.safety.safety.OutputStatement;
import jkind.lustre.visitors.TypeMapVisitor;

public class SafetyASTMapVisitor extends AgreeASTMapVisitor
	         implements AgreeASTVisitor<AgreeASTElement>{
	
	// This is the FaultStatement that holds all fault definitions
	private FaultStatement fs;



	public SafetyASTMapVisitor(TypeMapVisitor lustreTypeMapVisitor, FaultStatement fs) {
		super(lustreTypeMapVisitor);
		this.fs = fs;

		
	}
		
	
	private static Map<String, EObject> mapVar = new HashMap<>();


	@Override
	public AgreeVar visit(AgreeVar e){
		
		// Here are the fault definitions within the fault statement
	    List<FaultSubcomponent> faultDefs = new ArrayList<>();
		// Fault output statement
		OutputStatement fout = null;
		// Nominal connections from fault output statement
		List<NestedDotID> foutNomConn = new ArrayList<>();
		HashMap<NestedDotID, String> nomConnWithName = new HashMap<>();
		// Nominal output list for renaming
		List<String> nominalOutputs = new ArrayList<>();
		// New node if changes required
		AgreeVar newnode = null;
		
		// Get the definitions of the fault
		faultDefs = fs.getFaultDefinitions();
		
		// Get the output statement
		fout = getOutputStatement(faultDefs);
		
		if(fout == null){
			new SafetyException("Output statement is null during transform agree program.");
		}
		
		// Nominal connections from fault output stmt
		foutNomConn = fout.getNom_conn();
		String name = "";
		NamedElement base = null;
		for(NestedDotID nomConn : foutNomConn){
			name = nomConn.getBase().getFullName();
			while(nomConn.getSub() != null){
				nomConn = nomConn.getSub();
				base = nomConn.getBase();
				if(base != null){
					name = name.concat("."+base.getFullName());
				}
			}
			nomConnWithName.put(nomConn, name);
		}
		
		
		
		mapVar.put(e.id, e.reference);
//		System.out.println("\nComponent instance name: "+e.compInst.getFullName());
//		
//		
//		System.out.println("Visiting node:" + e.id +" with reference: "+e.reference);
//		
		
	
		// Concatanate the component instance name with the id 
		// so that it matches the exact output name
		String fullNodeName = e.compInst.getFullName() +"."+ e.id;
		
		boolean changedNode = false;
		
		// Test to see if we have a match with any of the outputs
		for(NestedDotID id : nomConnWithName.keySet()){
			
			String value = nomConnWithName.get(id);
			
//			System.out.println("NomConnWithName : "+ value);
//			System.out.println("fullNodeName : "+ fullNodeName);
//			
			// If we have a match, change the node accordingly
			if(value.contains(fullNodeName)){
				
				// Create list of nominal_outputName
				String nomName = "nominal_" + e.id;
				nominalOutputs.add(nomName);
				
				// Create new node with new name
				newnode = new AgreeVar(nomName, e.type, id, e.compInst, e.featInst);
				
				changedNode = true;
				break;
				
			}
		}
		
		if(changedNode){
			System.out.println("New node:" + newnode.id +" with reference: "+newnode.reference);
			return newnode;
		}else{
			return e;
		}
		
		
		
	}

//	@Override 
//	public AgreeNode visit(AgreeNode e){
//		//printNode(e);
//		return e;
//	}
	
//	/*
//	 * printNode:
//	 * @param AgreeNode
//	 * Prints the parts of the agree node to the console (input, output, etc.)
//	 * 
//	 */
//	private void printNode(AgreeNode e){
//
//		// Print out of inputs
//		List<AgreeVar> inputs = new ArrayList<>();
//		for (AgreeVar input : e.inputs) {
//			inputs.add(this.visit(input));
//			System.out.println("Inputs:\n");
//			System.out.println(input.toString());
//		}
//		
//		// Print out the outputs
//		List<AgreeVar> outputs = new ArrayList<>();
//		for (AgreeVar output : e.outputs) {
//			outputs.add(this.visit(output));
//			System.out.println("\n\nOutputs:\n");
//			System.out.println(output.toString());
//		}
//
//		List<AgreeVar> locals = new ArrayList<>();
//		for (AgreeVar local : e.locals) {
//			locals.add(this.visit(local));
//			System.out.println("\n\nLocals:\n");
//			System.out.println(local.toString());
//		}
//		
//		// Note that nodes and connections contain cross-references to each
//		// other. But, valid model structure requires that connections
//		// refer only to features on the this node and the sub-nodes. Thus,
//		// we may visit the sub-nodes first, and then use the result of that
//		// in visiting the connections.
//		//
//		// These are the names of the subcomponents (toString gives names)
//		List<AgreeNode> subNodes = new ArrayList<>();
//		for (AgreeNode subnode : e.subNodes) {
//			subNodes.add(this.visit(subnode));
//			System.out.println("\n\nSubnodes:\n");
//			System.out.println(subnode.toString());
//		}
//
//		List<AgreeConnection> connections = new ArrayList<>();
//		for (AgreeConnection conn : e.connections) {
//			connections.add(this.visit(conn));
//			System.out.println("\n\nConnections:\n");
//			System.out.println(conn.toString());
//		}
//
//		List<AgreeStatement> assertions = new ArrayList<>();
//		for (AgreeStatement stmt : e.assertions) {
//			assertions.add(this.visit(stmt));
//			System.out.println("\n\nAssertions:\n");
//			System.out.println(stmt.toString());
//		}
//
//		List<AgreeStatement> assumptions = new ArrayList<>();
//		for (AgreeStatement stmt : e.assumptions) {
//			assumptions.add(this.visit(stmt));
//			System.out.println("\n\nAssumptions:\n");
//			System.out.println(stmt.toString());
//		}
//
//		List<AgreeStatement> guarantees = new ArrayList<>();
//		for (AgreeStatement stmt : e.guarantees) {
//			guarantees.add(this.visit(stmt));
//			System.out.println("\n\nGuarantees:\n");
//			System.out.println(stmt.toString());
//		}
//
//		List<AgreeStatement> lemmas = new ArrayList<>();
//		for (AgreeStatement stmt : e.lemmas) {
//			lemmas.add(this.visit(stmt));
//			System.out.println("\n\nLemmas:\n");
//			System.out.println(stmt.toString());
//		}
//				
//		List<AgreeStatement> patternProps = new ArrayList<>();
//		for(AgreeStatement stmt : e.patternProps){
//			patternProps.add(this.visit(stmt));
//			System.out.println("\n\nPattern Props:\n");
//			System.out.println(stmt.toString());
//		}
//				
//		List<AgreeEquation> localEqs = new ArrayList<>();
//		for(AgreeEquation eq : e.localEquations){
//			localEqs.add(this.visit(eq));
//			System.out.println("\n\nLocal Equations:\n");
//			System.out.println(eq.toString());
//		}
//		
//		
//	}
	
	public static Map<String, EObject> getMap(){
		return mapVar;
	}
	
	
	/*
	 * getOutputStatement
	 * @param List<FaultSubcomponent> faultDefs : List of the fault definitions from the annex
	 * @return OutputStatement : The output statement found in the annex.
	 */
	private OutputStatement getOutputStatement(List<FaultSubcomponent> faultDefs){
		
		OutputStatement out = null;
		
		// Go through the fault definitions and find the output statement
		for(FaultSubcomponent fsub : faultDefs){
			if(fsub instanceof OutputStatement){
				out = (OutputStatement) fsub;
				break;
			}
		}
		return out;
	}
}
