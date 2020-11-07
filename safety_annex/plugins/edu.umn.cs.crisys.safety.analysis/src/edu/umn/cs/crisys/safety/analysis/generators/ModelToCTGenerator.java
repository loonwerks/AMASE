package edu.umn.cs.crisys.safety.analysis.generators;

import java.util.ArrayList;
import java.util.List;

import com.rockwellcollins.atc.agree.analysis.ast.AgreeProgram;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeStatement;

import edu.umn.cs.crisys.safety.analysis.ast.visitors.LustreExprToCTVisitor;
import edu.umn.cs.crisys.safety.analysis.causationTree.CT;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTRootNode;
import jkind.lustre.Node;
import jkind.lustre.UnaryExpr;
import jkind.lustre.UnaryOp;

public class ModelToCTGenerator {
	List<CT> causationTrees = new ArrayList<CT>();
	private LustreExprToCTVisitor lustreExprToFTVisitor = new LustreExprToCTVisitor();

	public List<CT> generateCausationTree(Node lustreNode, AgreeProgram agreeProgram) {
		//generate FT for each top level guarantee
		for (AgreeStatement topLevelGuarantee : agreeProgram.topNode.guarantees) {
			CT causationTree = new CT();
			// negate the top level guarantee expression and turn it into a causation tree
			UnaryExpr topLevelEvent = new UnaryExpr(UnaryOp.NOT, topLevelGuarantee.expr);
			CTRootNode rootNode = new CTRootNode(topLevelGuarantee.string);
			CTNode childNode = lustreExprToFTVisitor.visit(topLevelEvent);
			rootNode.addChildNode(childNode.nodeName, childNode);
			causationTree.addRootNode(rootNode.nodeName, rootNode);
			causationTrees.add(causationTree);
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
		return causationTrees;
	}

}
