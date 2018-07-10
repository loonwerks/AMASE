package edu.umn.cs.crisys.safety.analysis.transform;


import java.util.List;
import java.util.Map;

import org.eclipse.swt.widgets.MenuItem;

import com.rockwellcollins.atc.agree.analysis.AgreeLayout;
import com.rockwellcollins.atc.agree.analysis.AgreeLayout.SigType;
import com.rockwellcollins.atc.agree.analysis.AgreeRenaming;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeProgram;
import com.rockwellcollins.atc.agree.analysis.ast.visitors.AgreeASTPrettyprinter;
import com.rockwellcollins.atc.agree.analysis.extentions.AgreeAutomater;

import edu.umn.cs.crisys.safety.analysis.ast.visitors.AddFaultsToNodeVisitor;
import jkind.api.results.AnalysisResult;

public class AddFaultsToAgree implements AgreeAutomater {

	private static int transformFlag = 0;

	private AddFaultsToNodeVisitor faultVisitor = new AddFaultsToNodeVisitor();

	/*
	 * For each AgreeProgram:
	 * (1) AgreeASTBuilder contains the extension point for the program.
	 * It will call this class (AddFaultsToAgree.transform) and pass in the program.
	 * If we have the transformFlag selected, then we will make changes to the program
	 * and send it back. If not, the program is returned unchanged.
	 *
	 * (2) AddFaultsToAgree.transform calls AddFaultsToNodeVisitor.visit(program) and then visit is called
	 * for the node from there. The AddFaultsToNodeVisitor.visit(node) method will set the "FaultTreeFlag" for that node
	 * to true. This tells us in later agree methods that we are generating the soteria model
	 * and hence we add the ivcs differently for that node. (Lines 159-161)
	 *
	 * This visit(node) method also adds top level fault analysis results depending on
	 * what type of analysis is being run. If max no. faults or probabilistic, lines 183-185
	 * add top level constraints. These are not added when generating soteria model.
	 *
	 * Another thing of import that occurs in visit(node) method is that the method:
	 * addTopLevelFaultDeclarations is called (on line 176) on the node and node builder.
	 * Here is where fault declarations are added differently depending on what type
	 * of analysis is run.
	 * --> If max/prob, then independent faults added to inputs. (Line 775-778)
	 * --> If soteria generation, then independent faults are added locally,
	 * assigned to false, and given the --%IVC command. This takes place in lines 778-793.
	 *
	 * The dependent faults are added the same in any type of fault analysis (796).
	 *
	 * (3) Once the program is changed with fault information added, we send it back to
	 * AgreeASTBuilder.getAgreeProgram.
	 *
	 * (4) From here, the program is returned to SoteriaGenHandler.wrapVerificationResults (434)
	 *
	 * (5) Depending on the type of analysis selected, a different LustreASTBuilder is called.
	 * When it is not monolithic analysis, wrapVerificationResults makes a call to
	 * LustreASTBuilder.getAssumeGuaranteeLustreProgram.
	 * The string of calls is as follows in that class:
	 * - getAssumeGuaranteeLustreProgram (173)
	 * - flattenAgreeNode (644)
	 * - addSubnodeLustre (877)
	 * - getLustreNode (535)
	 * Here is where we add the guarantee ivcs based on whether or not the node
	 * has subcomponents. This addition is found on lines 571-585.
	 *
	 */

	/*
	 * For each AgreeNode:
	 * *** Note: This is specific to max/prob analysis. This changes in different ways when
	 * *** performing soteria/fault tree generation. See notes above for those details.
	 *
	 * ! 1. Find the set of "faulty" outputs. Ensure that only one fault occurs per output
	 * (test: print them & verify)
	 * We need to map "faulty" outputs as they are define in the error
	 * annex to their names in AGREE.
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
	 * For each subcomponent fault (depth-first)
	 * 0. Perform a traversal to find all the node/fault pairs
	 * 1a. Define an unconstrained local eq. to represent each fault-event
	 * 1b. Define a constrained local eq. to assign fault-active value depending on
	 * fault duration in node.
	 * 1c. Assign subcomponent fault input to fault-active eq with assertions (yay!)
	 * (test: print updated AST)
	 * ! 2. Assign faults-active equation to sum of all fault-active values
	 * (test: print updated AST)
	 * 3. Assert that this value is <= 1 (FOR NOW!)
	 * (test: print updated AST)
	 * 4. Use shiny new fault annex to perform safety analysis
	 * (test: analysis results)
	 *
	 *
	 *
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
		if (AddFaultsToAgree.getTransformFlag() == 0) {
			return program;
		}

		faultVisitor = new AddFaultsToNodeVisitor();

		try{

			switch (transformFlag) {
			case 1:
			case 2:
				program = faultVisitor.visit(program);
				AgreeASTPrettyprinter pp = new AgreeASTPrettyprinter();
				pp.visit(program);
				System.out.println("Initial printing");
				System.out.println(pp.toString());
				break;
			default:
				return program;
			}
			/*
			 * program = faultVisitor.visit(program);
			 * AgreeASTPrettyprinter pp = new AgreeASTPrettyprinter();
			 * pp.visit(program);
			 * System.out.println("Initial printing");
			 * System.out.println(pp.toString());
			 */
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
	 *
	 * @param none
	 *
	 * @return none
	 * Sets the transform flag to int value:
	 * 0 -> No SA performed
	 * 1 -> SA selected
	 * 2 -> SOTERIA model generation selected
	 */
	public static void setTransformFlag(MenuItem item) {

		// If item.getSelection is false, nothing is selected --> 0
		// If item.getSelection is true, either safety analysis or SOTERIA is selected:
		// Get string of menu item:
		// If string is Safety analysis --> 1
		// If string is SOTERIA --> 2
		if (!item.getSelection()) {
			transformFlag = 0;
		} else {
			String text = item.getText();
			if (text.contains("Safety Analysis")) {
				transformFlag = 1;
			} else if (text.contains("Generate SOTERIA model")) {
				transformFlag = 2;
			} else {
				transformFlag = 0;
			}
		}
	}

	/*
	 * getTransformFlag
	 * @param none
	 * @return boolean flag
	 * Returns the value of the flag.
	 */
	public static int getTransformFlag() {
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
				renaming.addExplicitRename(name,
						key.explanitoryText + " (" + key.id + ")");

				// Add to reference map
				renaming.addToRefMap(name, key.faultStatement);

				// Add to supportRenames
				renaming.addSupportRename(name, name);

				// Add to supportRefString
				renaming.addSupportRefString(name, "fault: " + key.explanitoryText);

			}
		}
		return renaming;
	}



	@Override
	public AnalysisResult transformResult(AnalysisResult res) {
		System.out.println("RESULT ++++++++++++++++++++++++++");
		return res;
	}

	// @Override
	@Override
	public AgreeLayout transformLayout(AgreeLayout layout) {

		Map<Fault, String> mapFaultToPath = faultVisitor.getMapFaultToPath();
		for(Fault key : mapFaultToPath.keySet()) {

			// To add an element to the layout, the path
			// is required. This full path is stored in the fault.
			// First, construct the path in dot form.
			// Then find the corresponding key in the layout map.
			// Add this fault explanitory text to the value (array list)
			// associated with that key.

			layout.addElement(pathToString(key.path), key.explanitoryText + " (" + key.id + ")",
					SigType.INPUT);

			layout.addElement(mapFaultToPath.get(key),
					key.explanitoryText + " (" + mapFaultToPath.get(key) + ", " + key.id + ")", SigType.INPUT);

		}
		return layout;
	}

	// Takes in list of strings corresponding to a path
	// and returns path in dot form:
	// list = [a, b, c]
	// return string = a.b.c
	public String pathToString(List<String> list) {

		String path = "";
		for (int i = 0; i < list.size(); i++) {
			if (i == 0) {
				path += list.get(i);
			} else {
				path += "." + list.get(i);
			}
		}
		return path;
	}


}
