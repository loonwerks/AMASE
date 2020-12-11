package edu.umn.cs.crisys.safety.analysis.ast.visitors;

import java.util.HashMap;
import java.util.Map;

import com.rockwellcollins.atc.agree.analysis.ast.AgreeNode;

import edu.umn.cs.crisys.safety.analysis.causationTree.CT;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTAndNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTAst;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTBinaryIdNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTBottomNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTConstantNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTIdNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTOrNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTUnaryIdNode;

public class CTBottomIdNodeVisitor implements CTAstVisitor<Void> {
	private AgreeNode curAgreeNode;
	private Map<CTBottomNode, AgreeNode> bottomIdNodeAgreeNodeMap = new HashMap<CTBottomNode, AgreeNode>();

	public CTBottomIdNodeVisitor(AgreeNode agreeNode) {
		this.curAgreeNode = agreeNode;
	}

	public void setCurAgreeNode(AgreeNode agreeNode) {
		this.curAgreeNode = agreeNode;
	}

	public AgreeNode getCurAgreeNode() {
		return curAgreeNode;
	}

	public void removeBottomIdNodeFromMap(CTBottomNode newNode) {
		bottomIdNodeAgreeNodeMap.remove(newNode);
	}

	public Boolean isBottomIdMapEmpty() {
		return bottomIdNodeAgreeNodeMap.isEmpty();
	}

	public Map<CTBottomNode, AgreeNode> deepCopyOfBottomIdNodeMap() {
		Map<CTBottomNode, AgreeNode> copy = new HashMap<CTBottomNode, AgreeNode>();
		for (Map.Entry<CTBottomNode, AgreeNode> entry : bottomIdNodeAgreeNodeMap.entrySet()) {
			copy.put(entry.getKey(), entry.getValue());
		}
		return copy;
	}

	public Void visit(CTAst ast) {
		return ast.accept(this);
	}

	@Override
	public Void visit(CT ct) {
		visit(ct.rootNode);
		return null;
	}

	@Override
	public Void visit(CTAndNode node) {
		for (CTNode child : node.childNodes) {
			visit(child);
		}
		return null;
	}

	@Override
	public Void visit(CTOrNode node) {
		for (CTNode child : node.childNodes) {
			visit(child);
		}
		return null;
	}

	@Override
	public Void visit(CTBinaryIdNode node) {
		addToBottomIdNodeAgreeNodeMap(node);
		// bottomIdNodeAgreeNodeMap.put(node, curAgreeNode);

		for (CTNode child : node.childNodes) {
			visit(child);
		}
		return null;
	}

	@Override
	public Void visit(CTUnaryIdNode node) {

		// bottomIdNodeAgreeNodeMap.put(node, curAgreeNode);
		addToBottomIdNodeAgreeNodeMap(node);
		for (CTNode child : node.childNodes) {
			visit(child);
		}
		return null;
	}

	@Override
	public Void visit(CTConstantNode node) {
		return null;
	}

	@Override
	public Void visit(CTIdNode node) {

		// bottomIdNodeAgreeNodeMap.put(node, curAgreeNode);
		addToBottomIdNodeAgreeNodeMap(node);
		for (CTNode child : node.childNodes) {
			visit(child);
		}
		return null;
	}

	private void addToBottomIdNodeAgreeNodeMap(CTBottomNode newNode) {
		Boolean toAdd = true;
		for (Map.Entry<CTBottomNode, AgreeNode> entry : bottomIdNodeAgreeNodeMap.entrySet()) {
			CTBottomNode bottomIdNode = entry.getKey();
			if (bottomIdNode.equalsNode(newNode)) {
				toAdd = false;
				break;
			}
		}
		if (toAdd) {
			bottomIdNodeAgreeNodeMap.put(newNode, curAgreeNode);
		}
	}

}
