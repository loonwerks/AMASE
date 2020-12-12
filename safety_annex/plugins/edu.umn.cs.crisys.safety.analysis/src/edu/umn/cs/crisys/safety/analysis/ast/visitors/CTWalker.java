package edu.umn.cs.crisys.safety.analysis.ast.visitors;

import edu.umn.cs.crisys.safety.analysis.causationTree.CT;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTAndNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTAst;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTBinaryIdNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTConstantNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTIdNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTOrNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTUnaryIdNode;

//walk through the CT from bottom up to mark isFailure
//true: isFailure true
//false: isFailure false

public class CTWalker implements CTAstVisitor<Void> {

	public Void visit(CTAst ast) {
		return ast.accept(this);
	}

	@Override
	public Void visit(CT ct) {
		visit(ct.rootNode);
		return null;
	}

	// for AND node
	// if all child nodes has isFailure false, then mark it isFailure false
	// if any of its child nodes isFailure true, then mark it isFailure true
	// however, we should still visit every node from bottomup, to update its isFailure field
	// for accurate visualization
	@Override
	public Void visit(CTAndNode node) {
		for (CTNode child : node.childNodes) {
			visit(child);
			if (child.isFailure) {
				node.isFailure = true;
			}
		}
		return null;
	}

	// for OR node
	// if one of its child node isFailure false, mark it isFailure false
	// if all of its child nodes isFailure true, mark it isFailure true
	// however, we should still visit every node from bottomup, to update its isFailure field
	// for accurate visualization
	@Override
	public Void visit(CTOrNode node) {
		for (CTNode child : node.childNodes) {
			visit(child);
			if (!child.isFailure) {
				node.isFailure = false;
			}
		}
		return null;
	}

	@Override
	public Void visit(CTBinaryIdNode node) {
		if (!node.isLeaf) {
			CTNode child = node.childNodes.get(0);
			visit(child);
			if (child.isFailure) {
				node.isFailure = true;
			}
		}
		return null;
	}

	@Override
	public Void visit(CTUnaryIdNode node) {
		if (!node.isLeaf) {
			CTNode child = node.childNodes.get(0);
			visit(child);
			if (child.isFailure) {
				node.isFailure = true;
			}
		}
		return null;
	}

	@Override
	public Void visit(CTConstantNode node) {
		return null;
	}

	@Override
	public Void visit(CTIdNode node) {
		if (!node.isLeaf) {
			CTNode child = node.childNodes.get(0);
			visit(child);
			if (child.isFailure) {
				node.isFailure = true;
			}
		}
		return null;
	}

}
