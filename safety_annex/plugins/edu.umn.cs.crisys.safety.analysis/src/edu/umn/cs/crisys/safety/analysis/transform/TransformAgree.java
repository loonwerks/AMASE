package edu.umn.cs.crisys.safety.analysis.transform;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.osate.aadl2.AnnexSubclause;
import org.osate.aadl2.ComponentClassifier;
import org.osate.annexsupport.AnnexUtil;

import com.rockwellcollins.atc.agree.analysis.ast.AgreeNode;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeNodeBuilder;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeProgram;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeVar;
import com.rockwellcollins.atc.agree.analysis.extentions.AgreeAutomater;

import edu.umn.cs.crisys.safety.safety.FaultStatement;
import edu.umn.cs.crisys.safety.safety.SafetyContract;
import edu.umn.cs.crisys.safety.safety.SafetyContractSubclause;
import edu.umn.cs.crisys.safety.safety.SafetyPackage;
import edu.umn.cs.crisys.safety.safety.SpecStatement;

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

		// check to make sure we are supposed to transform program
		if (!TransformAgree.getTransformFlag()) {
			return program;
		}

		AgreeNode topNode = null;
		List<AgreeNode> agreeNodes = new ArrayList<>();

		try{
			for (AgreeNode n : program.agreeNodes) {
				AgreeNode newNode = this.addFaultsToNode(program, n);

				// set the transformed top-level node for the new AgreeProgram
				if (n == program.topNode) {
					newNode = setTopNodeFaultSpecification(newNode);
					topNode = newNode;
				}
				agreeNodes.add(newNode);
			}
		}
		finally {
			System.out.println("completed performing safety analysis transformation");
		}
		// And return rebuilt program
		return new AgreeProgram(agreeNodes, program.globalLustreNodes, program.globalTypes, topNode);

	}

	// New SafetyASTVisitor
	// jkind.lustre.visitors.TypeMapVisitor lustreTypeMapVisitor = new TypeMapVisitor();
	// SafetyASTMapVisitor safetyvisitor;

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
	 *
	 * @param EObject comp : The component classifier in question will contain
	 * the safety annex.
	 *
	 * @return SafetyContractSubclause : This is the safety annex.
	 *
	 * MWW: generalized to return a list in case of multiple annex instances.
	 * Also generalized the lookup type to support the different kinds of
	 * EObjects used by AGREE to represent references.
	 */
	private List<SafetyContractSubclause> getSafetyAnnexes(AgreeNode node) {
		List<SafetyContractSubclause> subclauses = new ArrayList<>();

		// find the component classifier
		// MWW: get the component instance from the AGREE program: less brittle than storing a global ref
		// in verify handler - esp. if the user selects a different impl.
		ComponentClassifier comp = node.compInst.getClassifier();


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
					subclauses.add((SafetyContractSubclause) annex);
				}
			}
		}
		return subclauses;
	}

	public List<SpecStatement> collapseAnnexes(List<SafetyContractSubclause> annexes) {

		List<SpecStatement> allSpecs = new ArrayList<>();

		for (SafetyContractSubclause safetyannex : annexes) {
			// and the safety annex's contract
			SafetyContract contract = (SafetyContract) safetyannex.getContract();

			// The specs from the contract is where we can access the fault definitions
			allSpecs.addAll(contract.getSpecs());
		}

		return allSpecs;
	}

	public List<AgreeVar> getFaultyOutputs(AgreeNode n, List<SpecStatement> specs) {
		List<AgreeVar> outputs = new ArrayList<>();
		for (SpecStatement spec: specs) {
		}
		return outputs;
	}
	
	public AgreeNode addFaultsToNode(AgreeProgram program, AgreeNode node) {
		AgreeNodeBuilder nb = new AgreeNodeBuilder(node);

		List<SpecStatement> specs = collapseAnnexes(getSafetyAnnexes(node));
		System.out.println("For node: " + node.id + " safety annex specifications are: ");
		for (SpecStatement s : specs) {
			if (s instanceof FaultStatement) {
				FaultStatement fs = (FaultStatement)s;
				FaultASTBuilder builder = new FaultASTBuilder(program, node);
				SafetyFault safetyFault = builder.buildFault(fs);
				program = builder.agreeProgram; 
			}
			System.out.println(s);
		}

		
		/*
		 * 1. Find the set of "faulty" outputs. Ensure that only one fault occurs per output
		 * (test: print them & verify)
		 * 		We need to map "faulty" outputs as they are define in the error 
		 * 		annex to their names in AGREE. 
		 * 
		 * 2. For each of these outputs define a "nominal" value
		 * (test: print updated AST)
		 * 3. For each property involving a faulty output, replace the faulty output id with the
		 * nominal output id.
		 * (test: print updated AST)
		 * 4. For each fault, define a "fault_occurred" input (unique name?)
		 * 4a. Create eq with unique name for each local eq. in the fault
		 * (test: print updated AST)
		 * 5. For each fault, define an assignment equation that assigns unique "fault" names
		 * to the fault node outputs.
		 * (test: print updated AST)
		 * 6. For each of the "faulty" outputs assign it to the appropriate "fault" name node output.
		 * (test: print updated AST)
		 *
		 */
		return nb.build();
	}

	/*
	 * getFaultStatement
	 * @param EList<SpecStatement> specs : The spec statements from the contract.
	 * @return FaultStatement : The fault statement associated with the spec statement.
	 * This is how all annex statements will be accessed (inputs, outputs, etc).
	 */
	private List<FaultStatement> getFaultStatements(EList<SpecStatement> specs) {

		List<FaultStatement> faults = new ArrayList<>();

		// For each of the spec stmts in the list, check
		// to see if it is a fault statement.
		// If there is one, cast it and return that.
		// Else return null.
		for(SpecStatement spec : specs){
			if(spec instanceof FaultStatement){
				faults.add((FaultStatement) spec);
			}
		}
		return faults;
	}


	private AgreeNode setTopNodeFaultSpecification(AgreeNode node) {
		AgreeNodeBuilder nb = new AgreeNodeBuilder(node);

		List<SpecStatement> specs = collapseAnnexes(getSafetyAnnexes(node));
		System.out.println("For TOP LEVEL node: " + node.id + " safety annex specifications are: ");
		for (SpecStatement s : specs) {
			System.out.println(s);
		}

		return nb.build();
	}

}
