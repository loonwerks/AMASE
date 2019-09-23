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

import edu.umn.cs.crisys.safety.analysis.SafetyException;
import edu.umn.cs.crisys.safety.analysis.ast.visitors.AddFaultsToNodeVisitor;
import jkind.api.results.AnalysisResult;

/**
 * Intercepts the program before JKind analysis is performed. If safety analysis
 * is selected as a menu item, performs associated analysis. If not,
 * program is returned unchanged.
 *
 * @author Danielle Stewart, Mike Whalen
 *
 */
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
	 * (2) AddFaultsToAgree.transform calls AddFaultsToNodeVisitor.visit(program)
	 * and then visit is called for the node from there.
	 * The AddFaultsToNodeVisitor.visit(node) method will set the "FaultTreeFlag" for that node
	 * to true. This tells us in later agree methods that we are generating the soteria model
	 * and hence we add the ivcs differently for that node. (Lines 159-161)
	 */

	/*
	 * transform:
	 * @param AgreeProgram: this is the agree program that comes in from the extension point.
	 * @return AgreeProgram: this is either the unmodified original program,
	 * or a transformed program (safety analysis)
	 *
	 */

	/**
	 * Transform program.
	 * If safety analysis selected as menu item, pass program to AddFaultsToNodeVisitor.
	 * If not, return unchanged program.
	 *
	 * @param program The AgreeProgram.
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
				break;
			default:
				return program;
			}
		}
		catch (Throwable t) {
			new SafetyException("Something went wrong during safety analysis: " + t.toString());

		}

		return program;
	}

	/**
	 * setTransformFlag:
	 * Sets the transform flag to int value:
	 * 0 -> No SA performed
	 * 1 -> Peform analysis with faults present
	 * 2 -> Generate minimal cut sets
	 *
	 * @param item menu item stating which kind of analysis to perform.
	 */
	public static void setTransformFlag(MenuItem item) {

		//
		if (!item.getSelection()) {
			if (item.getText().contains("Generate")) {
				transformFlag = 2;
			} else {
				transformFlag = 0;
			}
		} else {
			if (item.getText().contains("Verify")) {
				transformFlag = 1;
			} else {
				transformFlag = 0;
			}
		}
	}

	/**
	 * getTransformFlag
	 * Returns the value of the flag:
	 * 0 -> No SA performed
	 * 1 -> Peform analysis with faults present
	 * 2 -> Generate minimal cut sets
	 *
	 * @param int flag
	 */
	public static int getTransformFlag() {
		return transformFlag;
	}

	/**
	 * @see com.rockwellcollins.atc.agree.analysis.extentions.AgreeAutomater#rename(com.rockwellcollins.atc.agree.analysis.AgreeRenaming)
	 *
	 *  (1) Gets the fault Lustre names and renames them as the explanatory text
	 *  associated with that fault statement.
	 *  (2) Add to reference map: Lustre fault -> FaultStatementImpl
	 *
	 * @param renaming renaming map to find lustre names
	 */
	@Override
	public AgreeRenaming rename(AgreeRenaming renaming) {
		Map<Fault, List<String>> mapFaultToLustreName = faultVisitor.getFaultToLustreNameMap();
		Map<HWFault, List<String>> mapHWFaultToLustreName = faultVisitor.getHWFaultToLustreNameMap();

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
				renaming.addSupportRefString(name, key.explanitoryText + " (" + key.id + ")");
			}
		}
		for (HWFault key : mapHWFaultToLustreName.keySet()) {
			// Add to explicit renames map
			for (String name : mapHWFaultToLustreName.get(key)) {
				renaming.addExplicitRename(name, key.explanitoryText + " (" + key.id + ")");
				// Add to reference map
				renaming.addToRefMap(name, key.hwFaultStatement);
				// Add to supportRenames
				renaming.addSupportRename(name, name);
				// Add to supportRefString
				renaming.addSupportRefString(name, key.explanitoryText + " (" + key.id + ")");
			}
		}
		return renaming;
	}

	/**
	 * Transform analysis result: only returns unchanged analysis result
	 *
	 * @param res analysis result
	 */
	@Override
	public AnalysisResult transformResult(AnalysisResult res) {
		return res;
	}

	/**
	 * Transform layout used for counterexample information.
	 *
	 * @param layout
	 */
	@Override
	public AgreeLayout transformLayout(AgreeLayout layout) {

		Map<Fault, String> mapFaultToPath = faultVisitor.getMapFaultToPath();
		for(Fault key : mapFaultToPath.keySet()) {

			// To add an element to the layout, the path
			// is required. This full path is stored in the fault.
			// First, construct the path in dot form.
			// Then find the corresponding key in the layout map.
			// Add this fault explanatory text to the value (array list)
			// associated with that key.
			layout.addElement(pathToString(key.path), key.explanitoryText + " (" + key.id + ")",
					SigType.INPUT);
			layout.addElement(mapFaultToPath.get(key),
					key.explanitoryText + " (" + mapFaultToPath.get(key) + ", " + key.id + ")", SigType.INPUT);
		}
		return layout;
	}

	/** Takes in list of strings corresponding to a path
	 * and returns path in dot form: list = [a, b, c] return string = a.b.c
	 *
	 * @param list of strings
	 * @return string in dot form
	 */
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
