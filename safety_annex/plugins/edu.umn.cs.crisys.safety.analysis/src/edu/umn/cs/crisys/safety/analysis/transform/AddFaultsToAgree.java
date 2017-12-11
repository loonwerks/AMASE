package edu.umn.cs.crisys.safety.analysis.transform;


import java.util.List;
import java.util.Map;

import com.rockwellcollins.atc.agree.analysis.AgreeLayout;
import com.rockwellcollins.atc.agree.analysis.AgreeLayout.SigType;
import com.rockwellcollins.atc.agree.analysis.AgreeRenaming;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeProgram;
import com.rockwellcollins.atc.agree.analysis.ast.visitors.AgreeASTPrettyprinter;
import com.rockwellcollins.atc.agree.analysis.extentions.AgreeAutomater;

import edu.umn.cs.crisys.safety.analysis.ast.visitors.AddFaultsToNodeVisitor;
import jkind.api.results.AnalysisResult;

public class AddFaultsToAgree implements AgreeAutomater {

	private static boolean transformFlag = false;
	
	private AddFaultsToNodeVisitor faultVisitor = new AddFaultsToNodeVisitor(); 

	
	/* For each AgreeNode:
	 * ! 1. Find the set of "faulty" outputs. Ensure that only one fault occurs per output
	 * (test: print them & verify)
	 * 		We need to map "faulty" outputs as they are define in the error 
	 * 		annex to their names in AGREE. 
	 * 
	 * ! 2. For each of these outputs define a "nominal" value
	 * (test: print updated AST)
	 * ! 3. For each property involving a faulty output, replace the faulty output id with the
	 * nominal output id.
	 * (test: print updated AST)
	 * ! 4. For each fault, define a "fault_occurred" input (unique name?)
	 * ! 4a. Create eq with unique name for each local eq. in the fault
	 * (test: print updated AST)
	 * ! 5. For each fault, define an assignment equation that assigns unique "fault" names
	 * to the fault node outputs.
	 * (test: print updated AST)
	 * ! 6. For each of the "faulty" outputs assign it to the appropriate "fault" name node output.
	 * (test: print updated AST)
	 *
	 * For the top-level node:
	 * 	
	 * ! 1. For each subcomponent node
		For each subcomponent fault (depth-first)
			0. Perform a traversal to find all the node/fault pairs
			1a. Define an unconstrained local eq. to represent each fault-event 
			1b. Define a constrained local eq. to assign fault-active value depending on 
				fault duration in node.
			1c. Assign subcomponent fault input to fault-active eq with assertions (yay!) 
	            (test: print updated AST)
		! 2. Assign faults-active equation to sum of all fault-active values
			(test: print updated AST)
		3. Assert that this value is <= 1 (FOR NOW!)	
			(test: print updated AST)
		4. Use shiny new fault annex to perform safety analysis
			(test: analysis results)
	 


	 */

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
		if (!AddFaultsToAgree.getTransformFlag()) {
			return program;
		}

		// System.out.println("Ta da!");
		
		faultVisitor = new AddFaultsToNodeVisitor();
		
		try{
			program = faultVisitor.visit(program);
			AgreeASTPrettyprinter pp = new AgreeASTPrettyprinter();
			pp.visit(program);
			System.out.println("Initial printing");
			System.out.println(pp.toString()); 
		}
		catch (Throwable t) {
			System.out.println("Something went wrong during safety analysis: " + t.toString());
		}
		finally {
			System.out.println("completed performing safety analysis transformation");
			System.out.println("...and we're done!");
		}
				
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
	 * (non-Javadoc)
	 * @see com.rockwellcollins.atc.agree.analysis.extentions.AgreeAutomater#rename(com.rockwellcollins.atc.agree.analysis.AgreeRenaming)
	 * 
	 *  (1) Gets the fault Lustre names and renames them as the explanatory text
	 *  associated with that fault statement. 
	 *  (2) Add to reference map: Lustre fault -> FaultStatementImpl
	 *  
	 */
	@Override
	public AgreeRenaming rename(AgreeRenaming renaming) {
		Map<Fault, List<String>> mapFaultToLustreName = faultVisitor.getFaultToLustreNameMap();
		 
		for(Fault key : mapFaultToLustreName.keySet()) {
			
			// Add to explicit renames map
			for(String name : mapFaultToLustreName.get(key)) {
				renaming.addExplicitRename(name, key.explanitoryText);
				// Add to reference map
				renaming.addToRefMap(name, key.faultStatement);
			}	
		}
		return renaming;
	}
	


	@Override
	public AnalysisResult transformResult(AnalysisResult res) {
		// TODO Auto-generated method stub
		return res;
	}

	// @Override
	public AgreeLayout transformLayout(AgreeLayout layout) {
		
		Map<Fault, String> mapFaultToPath = faultVisitor.getMapFaultToPath();
		for(Fault key : mapFaultToPath.keySet()) {
			layout.addElement(mapFaultToPath.get(key), key.explanitoryText, SigType.OUTPUT);
		}
		return layout;
	}



	
}
