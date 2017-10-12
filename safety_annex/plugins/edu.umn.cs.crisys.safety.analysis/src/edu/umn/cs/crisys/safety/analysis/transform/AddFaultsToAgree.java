package edu.umn.cs.crisys.safety.analysis.transform;


import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.xtext.util.Pair;
import org.osate.aadl2.ComponentClassifier;
import org.osate.aadl2.instance.ComponentInstance;
import org.osate.aadl2.instance.ConnectionInstance;
import org.osate.aadl2.instance.FeatureInstance;

import com.rockwellcollins.atc.agree.analysis.AgreeRenaming;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeProgram;
import com.rockwellcollins.atc.agree.analysis.ast.visitors.AgreeASTPrettyprinter;
import com.rockwellcollins.atc.agree.analysis.extentions.AgreeAutomater;

import edu.umn.cs.crisys.safety.analysis.SafetyException;
import edu.umn.cs.crisys.safety.analysis.ast.visitors.AddFaultsToNodeVisitor;
import jkind.api.results.AnalysisResult;
import jkind.lustre.IdExpr;

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

		faultVisitor = new AddFaultsToNodeVisitor();
		
		try{
			program = faultVisitor.visit(program);
			{ 
				AgreeASTPrettyprinter pp = new AgreeASTPrettyprinter();
				pp.visit(program);
				System.out.println(pp.toString());
			}
		}
		catch (Throwable t) {
			System.out.println("Something went wrong during safety analysis: " + t.toString());
		}
		finally {
			System.out.println("completed performing safety analysis transformation");
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
	 * (1) Create new map that associates a fault.id ("blue_skid__fault_19")
	 * 	   with a pair. That pair is (ComponentInstance, Fault).
	 * (2) Go through the explicitRenames:
	 * 		- for each key:
	 * 			- Find associated fault id
	 * 			- addExplicitRename(explicitRename.key, fault.explanatoryText)
	 *     
	 * There are a couple of helper functions commented out (as 
	 * well as the code that calls them). I am leaving them for now in 
	 * case I need to utilize them later. I don't think I will be
	 * needing them... 
	 * 
	 * I did add ComponentInstance to the map
	 * because we very well may be needing it. 
	 * It will be easy to remove if not. 
	 */
	@Override
	public AgreeRenaming rename(AgreeRenaming renaming) {
		Map<ComponentInstance, List<Fault>> faultMap = faultVisitor.getFaultMap();
		Map<String, String> explicitRenames = renaming.getExplicitRenames();
		Map<String, SimpleEntry<Fault, ComponentInstance>> outerMap = new HashMap<>();
		String faultId = null;
		outerMap = createOuterMap(faultMap);
		
		for(String explicitKey : explicitRenames.keySet()) {
			//String renamedExplicitKey = null;
			//List<String> pair = new ArrayList<>();
			
			faultId = findFaultId(outerMap, explicitKey);
			if(faultId == null) {
				continue;
			}
			
//			renamedExplicitKey = explicitKey.replace("__fault__active__","");
//			String explicitKey2 = replaceCharacters(explicitKey, faultId);
//			pair = getSplitString(renamedExplicitKey);
			
			SimpleEntry<Fault, ComponentInstance> entry = outerMap.get(faultId);
			Fault f = (Fault) entry.getValue();
			renaming.addExplicitRename(explicitKey, f.explanitoryText);
		}
		return renaming;
	}

	@Override
	public AnalysisResult transformResult(AnalysisResult res) {
		// TODO Auto-generated method stub
		return res;
	}


//	private String replaceCharacters(String fullString, String faultId) {
//		
//		String lhs1 = fullString.replace(faultId, "");
//		
//		// Replace "__" with "." in first string part
//		String lhs = lhs1.replace("__", ".");
//		// Replace "__" with "_" in second string part
//		String rhs = faultId.replace("__", "_");
//		
//		return (lhs+rhs);
//	}
	
	private Map<String, SimpleEntry<Fault, ComponentInstance>> createOuterMap(Map<ComponentInstance, List<Fault>> faultMap){
		
		Map<String, SimpleEntry<Fault, ComponentInstance>> outerMap = new HashMap<>();
		
		// Create new outer map: fault.id -> (fault -> ComponentInstance)
		for(ComponentInstance ci : faultMap.keySet()) {
			List<Fault> faults = faultMap.get(ci);
			for(Fault f : faults) {
				SimpleEntry<Fault, ComponentInstance> innerMap = new SimpleEntry(ci, f);
				outerMap.put(f.id, innerMap);
			}
		}
		return outerMap;
	}
	
	private String findFaultId(Map<String, SimpleEntry<Fault, ComponentInstance>> outerMap, String explicitKey) {
		
		for(String faultId : outerMap.keySet()) {
			if(explicitKey.contains(faultId)) {
				return faultId;
			}
		}
		return null;
	}
	
//	private List<String> getSplitString(String str){
//		List<String> pair = new ArrayList<>();
//		String str1 = null;
//		String str2 = null;
//		
//		int index = str.lastIndexOf('.');
//		if(index >= 0) {
//			str1 = str.substring(0,index);
//			str2 = str.substring(index+1);
//		}
//		else {
//			new SafetyException("Something is wrong in counterexample renaming: "
//					+"no periods in fault name");
//		}
//		
//		pair.add(str1);
//		pair.add(str2);
//		
//		return pair;
//	}
	
}
