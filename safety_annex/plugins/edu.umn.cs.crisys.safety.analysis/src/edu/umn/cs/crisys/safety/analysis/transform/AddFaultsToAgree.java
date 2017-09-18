package edu.umn.cs.crisys.safety.analysis.transform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.osate.aadl2.AnnexSubclause;
import org.osate.aadl2.ComponentClassifier;
import org.osate.aadl2.ComponentImplementation;
import org.osate.annexsupport.AnnexUtil;

import com.rockwellcollins.atc.agree.analysis.ast.AgreeNode;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeNodeBuilder;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeProgram;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeVar;
import com.rockwellcollins.atc.agree.analysis.ast.visitors.AgreeASTMapVisitor;
import com.rockwellcollins.atc.agree.analysis.ast.visitors.AgreeASTPrettyprinter;
import com.rockwellcollins.atc.agree.analysis.extentions.AgreeAutomater;

import edu.umn.cs.crisys.safety.analysis.SafetyException;
import edu.umn.cs.crisys.safety.analysis.ast.visitors.AddFaultsToNodeVisitor;
import edu.umn.cs.crisys.safety.analysis.ast.visitors.DummyVisitor;
import edu.umn.cs.crisys.safety.analysis.ast.visitors.ReplaceIdVisitor;
import edu.umn.cs.crisys.safety.safety.FaultStatement;
import edu.umn.cs.crisys.safety.safety.SafetyContract;
import edu.umn.cs.crisys.safety.safety.SafetyContractSubclause;
import edu.umn.cs.crisys.safety.safety.SafetyPackage;
import edu.umn.cs.crisys.safety.safety.SpecStatement;
import jkind.lustre.IdExpr;
import jkind.lustre.Node;

public class AddFaultsToAgree implements AgreeAutomater {

	private static boolean transformFlag = false;

	
	/*
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

		AddFaultsToNodeVisitor faultVisitor = new AddFaultsToNodeVisitor();
		
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

	/*		{ 
	AgreeASTPrettyprinter pp = new AgreeASTPrettyprinter();
	pp.visit(program);
	System.out.println(pp.toString());
}
*/		
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
	private AgreeNode setTopNodeFaultSpecification(AgreeNode node) {
		AgreeNodeBuilder nb = new AgreeNodeBuilder(node);

		List<SpecStatement> specs = collapseAnnexes(getSafetyAnnexes(node, true));
		System.out.println("For TOP LEVEL node: " + node.id + " safety annex specifications are: ");
		for (SpecStatement s : specs) {
			System.out.println(s);
		}

		return nb.build();
	}
	*/
}
