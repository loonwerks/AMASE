package edu.umn.cs.crisys.safety.analysis.soteria.faultTree;

import java.util.HashMap;

import edu.umn.cs.crisys.safety.analysis.ast.visitors.SoteriaFTAstVisitor;

public class SoteriaFaultTree extends SoteriaFTAst {
	public final String includeStr = "#use \"top.ml\";;";
	public HashMap<String, SoteriaFTLeafNode> leafNodes = new HashMap<>();
	public HashMap<String, SoteriaFTNonLeafNode> rootNodes = new HashMap<>();
	public HashMap<String, SoteriaFTNonLeafNode> intermediateNodes = new HashMap<>();

	public void addLeafNode(String nodeName, SoteriaFTLeafNode leafNode) {
		leafNodes.put(nodeName, leafNode);
	}

	public void addRootNode(String nodeName, SoteriaFTNonLeafNode rootNode) {
		rootNodes.put(nodeName, rootNode);
	}

	public void addIntermediateNode(String nodeName, SoteriaFTNonLeafNode intermediateNode) {
		intermediateNodes.put(nodeName, intermediateNode);
	}

	@Override
	public <T> T accept(SoteriaFTAstVisitor<T> visitor) {
		return visitor.visit(this);
	}

}
