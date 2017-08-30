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
import org.osate.annexsupport.AnnexUtil;

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
		AgreeNode topNode;
		List<AgreeVar> inputs = new ArrayList<>();
		List<AgreeVar> outputs = new ArrayList<>();
		List<AgreeNode> nodes = new ArrayList<>();
		SafetyContract safetyContract = null;
		List<SpecStatement> safetySpecs = new ArrayList<>();
		List<SafetyInterval> safetyIntervals = new ArrayList<>();
		FaultStatement fs = null;
		List<FaultSubcomponent> faultDefs = new ArrayList<>();
		AnnexSubclause safetyannex = null;
		Element root = null;
		
		// First get the analysis flag to see if we just return original agree program.
		Boolean analysis = VerifyHandler.getAnalysisFlag();
		
		// Need a finally statement to change flag back to false
		try{
			if(analysis == false){
				System.out.println("Analysis is false, returning original agree program");
			} else {
				System.out.println("Analysis is true, transforming original agree program");
				
				
				// Visit program: 
				// For now, it prints out each node in the program
				// (input, guarantees, etc.)
				jkind.lustre.visitors.TypeMapVisitor lustreTypeMapVisitor = new TypeMapVisitor();
				AgreeASTMapVisitor visitor = new AgreeASTMapVisitor(lustreTypeMapVisitor);
				
				AgreeProgram ap = visitor.visit(program);
				ComponentImplementation ci = VerifyHandler.getComponentImplementation();
				root = VerifyHandler.getRoot();
				
				Classifier classifier = root.getContainingClassifier();
				ComponentClassifier compClass = null;
				if(classifier instanceof ComponentClassifier){
					compClass = (ComponentClassifier) classifier;
				}
				
				SafetyContractSubclause sannex = getSafetyAnnex(compClass);
				System.out.println();
				
				SafetyContract contract = (SafetyContract) sannex.getContract();
				System.out.println();
				
				EList<SpecStatement> specs = contract.getSpecs();
				fs = getFaultStatement(specs);
				faultDefs = fs.getFaultDefinitions();
				
				//safetyIntervals.addAll(getIntervalStatements(specs));
				System.out.println();

				
				
				
				
				// Get the Safety annex from the component impl
				
				List<AnnexSubclause> ownedAnnexes = ci.getOwnedAnnexSubclauses();
				
				if(ownedAnnexes.size() == 2){
					for(AnnexSubclause annex : ownedAnnexes){
						if(annex.getName().equals("safety")){
							safetyannex = annex;
							System.out.println();
						}
					}
				}else{
					new SafetyException("During transforming AGREE program: cannot locate safety annex.");
				}
				
				//safetyannex.
				
				
				// Get the top node, inputs, and outputs from agree
				topNode = ap.topNode;
				
				// Reset from any previous runs
				inputs.clear();
				outputs.clear();
				
				// Populate inputs and outputs from topNode
				inputs.addAll(topNode.inputs);
				outputs.addAll(topNode.outputs);
				
				// Populate from all the other nodes
				nodes = ap.agreeNodes;
				
				for(AgreeNode node : nodes){
//					ComponentInstance ct = node.compInst;
//					System.out.println("\nComponent Instance:\n");
//					System.out.println(ct.toString());
					
					inputs.addAll(node.inputs);
					outputs.addAll(node.outputs);
				}

//				System.out.println("TRANSFORM PROGRAM: INPUTS __________________");				
//				for(AgreeVar input : inputs){
//					System.out.println(input.toString());
//				}
//				
//
//				System.out.println("TRANSFORM PROGRAM: OUTPUTS __________________");				
//				for(AgreeVar output : outputs){
//					System.out.println(output.toString());
//				}
				
//				safetyContract = (SafetyContract) safetyannex.getContract();
//				safetySpecs = safetyContract.getSpecs();
//				
//				for(SpecStatement spec : safetySpecs){
//					if(spec instanceof OutputStatement){
//						OutputStatement safetyOutput = (OutputStatement) spec;
//						safetyOutputs.add(safetyOutput);
//					}
//				}
//				
//				System.out.println("TRANSFORM PROGRAM: Safety OUTPUTS __________________");
//				System.out.println(safetyOutputs.size());
//				for(OutputStatement output : safetyOutputs){
//					System.out.println(safetyOutputs.size());
//					System.out.println(output.getNom_conn().toString());
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
	
	private SafetyContractSubclause getSafetyAnnex(ComponentClassifier comp) {
		for (AnnexSubclause annex : AnnexUtil.getAllAnnexSubclauses(comp,
				SafetyPackage.eINSTANCE.getSafetyContractSubclause())) {
			if (annex instanceof SafetyContractSubclause) {
				// in newer versions of osate the annex this returns annexes in
				// the type
				// as well as the implementation. We want the annex in the
				// specific component
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
	

	private FaultStatement getFaultStatement(EList<SpecStatement> specs){
		
		FaultStatement fs = null;
		for(SpecStatement spec : specs){
			if(spec instanceof FaultStatement){
				fs = (FaultStatement) spec;
			}
		}
		return fs;
	}
	
//	private List<AgreeStatement> getGuaranteeStatements(EList<SpecStatement> specs) {
//		List<AgreeStatement> guarantees = new ArrayList<>();
//		for (SpecStatement spec : specs) {
//			if (spec instanceof GuaranteeStatement) {
//				GuaranteeStatement guarantee = (GuaranteeStatement) spec;
//				String str = guarantee.getStr();
//				if (guarantee.getExpr() != null) {
//					guarantees.add(new AgreeStatement(str, doSwitch(guarantee.getExpr()), guarantee));
//				} else {
//					PatternStatement pattern = guarantee.getPattern();
//					guarantees.add(new AgreePatternBuilder(str, guarantee, this).doSwitch(pattern));
//				}
//			}
//		}
//		return guarantees;
//	}


}
