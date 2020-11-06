package edu.umn.cs.crisys.safety.analysis.generators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import com.rockwellcollins.atc.agree.analysis.ast.AgreeProgram;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeStatement;

import edu.umn.cs.crisys.safety.analysis.ast.visitors.LustreExprToCTVisitor;
import edu.umn.cs.crisys.safety.analysis.faultTree.FaultTree;
import jkind.lustre.Node;
import jkind.lustre.UnaryExpr;
import jkind.lustre.UnaryOp;

public class ModelToCTGenerator {
	// FaultTree faultTree = new FaultTree();
	boolean isLowerLevel = false;
	public HashMap<UniqueID, UniqueID> elemIdMap = new HashMap<>();
	public HashSet<String> compNameSet = new HashSet<>();
	private LinkedHashMap<String, Set<List<String>>> mapPropertyToMCSs = new LinkedHashMap<String, Set<List<String>>>();
	private String componentName = "";
	List<FaultTree> faultTrees = new ArrayList<FaultTree>();
	private LustreExprToCTVisitor lustreExprToFTVisitor = new LustreExprToCTVisitor();

	public List<FaultTree> generateCausationTree(Node lustreNode, AgreeProgram agreeProgram) {
		//generate FT for each top level guarantee
		for (AgreeStatement topLevelGuarantee : agreeProgram.topNode.guarantees) {
			System.out.println("topLevelGuarantee");
			// negate the top level guarantee expression and turn it into a causation tree
			UnaryExpr topLevelEvent = new UnaryExpr(UnaryOp.NOT, topLevelGuarantee.expr);
			lustreExprToFTVisitor.visit(topLevelEvent);
			// 1. Turn the top level guarantee into a FTA
			// 2. For every leaf node, if it's not an input, output, or failure accessible to the
			// top level component implementation (stop condition)
			// 3. Find the definition of the leaf node and create a FTA for that logical definition of
			// the leaf node, repeat until each leaf node satisfy the stop condition above
			// 4. Apply NOT to the FTA and distribute it to the leaf nodes
			// 5. Resolve the FTA so it's in one of the final form per the FT Resolve visitor

			// Extend AGREE Switch to decode the guarantee expression
			// Apply negation to the expression
			// Distribute negation to individual terms
			// turn it to a fault tree
			// use resolve visitor to turn this into a final fault tree
		}

//		// initialize
//		MHSUtils.clearLocals();
//		// get current verification result
//		AnalysisResult curResult = ((CompositeAnalysisResult) result).getChildren().get(0);
//		walkthroughResults(curResult, null, linker);
//		// some node's child nodes were added before those child nodes obtained their own child nodes
//		// fix those discrepancies in the traverse
//		faultTree.updateChildNodes();
//		// sort through intermediate nodes to declare before use
//		faultTree.sortIntermediateNodes();
		return faultTrees;
	}

}
