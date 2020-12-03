package edu.umn.cs.crisys.safety.analysis.ast.visitors;

import java.util.HashSet;

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

	public HashSet<CTBottomNode> bottomNodesWithId = new HashSet<CTBottomNode>();

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
		if (!node.visited) {
			bottomNodesWithId.add(node);
		}
		for (CTNode child : node.childNodes) {
			visit(child);
		}
		return null;
	}

	@Override
	public Void visit(CTUnaryIdNode node) {
		if (!node.visited) {
			bottomNodesWithId.add(node);
		}
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
		if (!node.visited) {
			bottomNodesWithId.add(node);
		}
		for (CTNode child : node.childNodes) {
			visit(child);
		}
		return null;
	}

}
