package edu.umn.cs.crisys.safety.analysis.transform;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.osate.aadl2.AnnexSubclause;
import org.osate.aadl2.Classifier;
import org.osate.aadl2.ComponentClassifier;
import org.osate.aadl2.ComponentImplementation;
import org.osate.aadl2.Element;
import org.osate.aadl2.NamedElement;
import org.osate.annexsupport.AnnexUtil;

import com.rockwellcollins.atc.agree.agree.NestedDotID;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeNode;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeProgram;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeVar;
import com.rockwellcollins.atc.agree.analysis.ast.visitors.AgreeASTMapVisitor;
import com.rockwellcollins.atc.agree.analysis.extentions.AgreeAutomater;
import edu.umn.cs.crisys.safety.analysis.SafetyException;
import edu.umn.cs.crisys.safety.analysis.ast.SafetyInterval;
import edu.umn.cs.crisys.safety.analysis.ast.SafetySpecStatement;
import edu.umn.cs.crisys.safety.analysis.handlers.VerifyHandler;
import edu.umn.cs.crisys.safety.safety.FaultStatement;
import edu.umn.cs.crisys.safety.safety.FaultSubcomponent;
import edu.umn.cs.crisys.safety.safety.OutputStatement;
import edu.umn.cs.crisys.safety.safety.SafetyContract;
import edu.umn.cs.crisys.safety.safety.SafetyContractSubclause;
import edu.umn.cs.crisys.safety.safety.SafetyPackage;
import edu.umn.cs.crisys.safety.safety.SpecStatement;
import jkind.lustre.Expr;
import jkind.lustre.visitors.TypeMapVisitor;

public class TransformAgree implements AgreeAutomater {

	private static boolean transformFlag = false;
	
	/*
	 * transform:
	 * @param AgreeProgram: this is the agree program that comes in from the extension point.
	 * @return AgreeProgram: this is either the unmodified original program, 
	 * or a transformed program (safety analysis)
	 * 
	 */
	@Override
	public AgreeProgram transform(AgreeProgram program) {
		
		// Local copies of the program components we will need to change and access
		Element root = null;
		AgreeNode topNode;
		List<AgreeVar> agreeinputs = new ArrayList<>();
		List<AgreeVar> agreeoutputs = new ArrayList<>();
		List<AgreeNode> agreenodes = new ArrayList<>();
		// This is the FaultStatement that holds all fault definitions
		FaultStatement fs = null;
		// Here are the fault defitions within the fault statement
		List<FaultSubcomponent> faultDefs = new ArrayList<>();
		//String of the fault statement description
		String faultDesc = "";
		// Fault output statement
		OutputStatement fout = null;
		// Nominal connections from fault output statement
		List<NestedDotID> foutNomConn = new ArrayList<>();
		// List of nominal connection names
		List<String> nomConnNames = new ArrayList<>();
		
		
		// First get the analysis flag to see if we just return original agree program.
		Boolean analysis = VerifyHandler.getAnalysisFlag();
		
		// Need a finally statement to change flag back to false
		try{
			if(analysis == false){
				System.out.println("Analysis is false, returning original agree program");
			} else {
				System.out.println("Analysis is true, transforming original agree program");
				
				
				// Visit program
				jkind.lustre.visitors.TypeMapVisitor lustreTypeMapVisitor = new TypeMapVisitor();
				AgreeASTMapVisitor visitor = new AgreeASTMapVisitor(lustreTypeMapVisitor);
				
				AgreeProgram ap = visitor.visit(program);
				
				// Get Element root: This will hold the containing classifier which is
				// how we can access the safety annex
				root = VerifyHandler.getRoot();
				
				// Get the classifier
				Classifier classifier = root.getContainingClassifier();
				ComponentClassifier compClass = null;
				
				// Cast to ComponentClassifier
				if(classifier instanceof ComponentClassifier){
					compClass = (ComponentClassifier) classifier;
				}
				
				// Grab the safety annex
				SafetyContractSubclause safetyannex = getSafetyAnnex(compClass);
				
				// and the safety annex's contract
				SafetyContract contract = (SafetyContract) safetyannex.getContract();
				
				// The specs from the contract is where we can access the fault defintions
				EList<SpecStatement> specs = contract.getSpecs();
				fs = getFaultStatement(specs);
				
				// Test for null fault statement
				if(fs == null){
					new SafetyException("Fault statement is null during transform agree program.");
				}
				
				// Get the definitions of the fault
				faultDefs = fs.getFaultDefinitions();
				// And the string description
				faultDesc  = fs.getStr();
				
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
					nomConnNames.add(name);
					
				}
				
				//System.out.println(foutNomConn.toString());				
				System.out.println("OUTPUT FAULT NAMES: " + nomConnNames);
				
				// Get the top node, inputs, and outputs from agree
				topNode = ap.topNode;
				
				// Reset from any previous runs
				agreeinputs.clear();
				agreeoutputs.clear();
				
				// Populate inputs and outputs from topNode
				agreeinputs.addAll(topNode.inputs);
				agreeoutputs.addAll(topNode.outputs);
				
				// Populate from all the other nodes
				agreenodes = ap.agreeNodes;
				
				// Add each node's input to our list
				for(AgreeNode node : agreenodes){
					
					agreeinputs.addAll(node.inputs);
					agreeoutputs.addAll(node.outputs);
				}

//				System.out.println("TRANSFORM PROGRAM: INPUTS __________________");				
//				for(AgreeVar input : inputs){
//					System.out.println(input.toString());
//				}
//				
//
//				System.out.println("TRANSFORM PROGRAM: OUTPUTS __________________");				
//				for(AgreeVar output : agreeoutputs){
//					System.out.println(output.toString());
//				}
				
				
				
				
				
			
			
			}
		// Finally, reset transform flag to false
		}
		finally{
			setTransformFlag(false);
		}
		// And return program
		return program;
	}
	
	/*
	 * setTransformFlag:
	 * @param none
	 * @return none
	 * Sets the transform flag to true.  
	 */
	public static void setTransformFlag(boolean flag) {
		
		transformFlag = flag;
		
	}
	
	/*
	 * getTransformFlag
	 * @param none
	 * @return boolean flag
	 * Returns the value of the flag.
	 */
	public static boolean getTransformFlag(){
		return transformFlag;
	}
	
	/*
	 * getSafetyAnnex 
	 * @param ComponentClassifier comp : The component classifier in question will contain
	 * the safety annex. 
	 * @return SafetyContractSubclause : This is the safety annex. 
	 */
	private SafetyContractSubclause getSafetyAnnex(ComponentClassifier comp) {
		
		// Grab the annex subclause using the safety package instance
		for (AnnexSubclause annex : AnnexUtil.getAllAnnexSubclauses(comp,
				SafetyPackage.eINSTANCE.getSafetyContractSubclause())) {
			
			// If we do have the safety contract subclause (and not the agree one)
			// Then get the component classifier from that and return the annex
			if (annex instanceof SafetyContractSubclause) {
				// In newer versions of osate the annex this returns annexes in
				// the type as well as the implementation. We want the annex in the
				// specific component.
				EObject container = annex.eContainer();
				while (!(container instanceof ComponentClassifier)) {
					container = container.eContainer();
				}
				if (container == comp) {
					return (SafetyContractSubclause) annex;
				}
			}
		}
		return null;
	}
	
	/*
	 * getFaultStatement
	 * @param EList<SpecStatement> specs : The spec statements from the contract.
	 * @return FaultStatement : The fault statement associated with the spec statement.
	 * This is how all annex statements will be accessed (inputs, outputs, etc). 
	 */
	private FaultStatement getFaultStatement(EList<SpecStatement> specs){
		
		FaultStatement fs = null;
		
		// For each of the spec stmts in the list, check
		// to see if it is a fault statement. 
		// If there is one, cast it and return that.
		// Else return null. 
		for(SpecStatement spec : specs){
			if(spec instanceof FaultStatement){
				fs = (FaultStatement) spec;
				break;
			}
		}
		return fs;
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
	
	/*
	 * getMatchingAgreeOutput
	 * @param List<AgreeVar> agreeOutput : All agree vars that make up the agree outputs
	 * 		  String faultOutNames : All safety output nominal connection names
	 * @return AgreeVar : The agree var that matches output name
	 */
	private AgreeVar getMatchingAgreeOutput(List<AgreeVar> agreeOutput, String faultOutNames){
		
		AgreeVar matchingOutput = null;
		
		
		
		return matchingOutput;
	}


}
