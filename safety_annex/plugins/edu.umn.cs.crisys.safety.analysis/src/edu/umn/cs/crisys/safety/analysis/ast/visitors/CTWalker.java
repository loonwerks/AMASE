package edu.umn.cs.crisys.safety.analysis.ast.visitors;

import java.util.ArrayList;
import java.util.List;

import com.rockwellcollins.atc.agree.analysis.ast.AgreeStatement;

import edu.umn.cs.crisys.safety.analysis.causationTree.CT;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTAndNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTAst;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTBinaryIdNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTConstantNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTIdNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTOrNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTUnaryIdNode;
import edu.umn.cs.crisys.safety.util.AgreeUtil;

//walk through the CT from bottom up to mark isFailure and isFeasible

public class CTWalker implements CTAstVisitor<Void> {

	private List<AgreeStatement> topLevelAssumptions = new ArrayList<AgreeStatement>();

	public CTWalker(List<AgreeStatement> topLevelAssumptions) {
		this.topLevelAssumptions = topLevelAssumptions;
	}

	public Void visit(CTAst ast) {
		return ast.accept(this);
	}

	@Override
	public Void visit(CT ct) {
		visit(ct.rootNode);
		return null;
	}

	// marking isFailure for AND node:
	// by default isFailure is false
	// if all child nodes has isFailure false, then mark it isFailure false
	// if any of its child nodes isFailure true, then mark it isFailure true
	// however, we should still visit every node from bottomup, to update its isFailure field
	// for accurate visualization
	// marking isFeasible for AND node:
	// by default isFeasible is true
	// if any of child nodes has isFeasible false, then mark it isFeasible false
	// if all of child nodes have isFeasible true, then mark it isFeasible true
	@Override
	public Void visit(CTAndNode node) {
		for (CTNode child : node.childNodes) {
			visit(child);
			if (child.isFailure) {
				node.isFailure = true;
			}
			if (!child.isFeasible) {
				node.isFeasible = false;
			}
		}
		return null;
	}

	// marking isFailure for OR node:
	// by default isFailure is false
	// if one of its child node isFailure false, mark it isFailure false
	// if all of its child nodes isFailure true, mark it isFailure true
	// however, we should still visit every node from bottomup, to update its isFailure field
	// for accurate visualization
	// marking isFeasible for OR node:
	// by default isFeasible is true
	// if any of child nodes has isFeasible true, then mark it isFeasible true
	// if all of child nodes have isFeasible false, then mark it isFeasible false
	@Override
	public Void visit(CTOrNode node) {
		Boolean allFailure = true;
		Boolean allNotFeasible = true;
		for (CTNode child : node.childNodes) {
			visit(child);
			if (!child.isFailure) {
				node.isFailure = false;
				allFailure = false;
			}
			if (child.isFeasible) {
				node.isFeasible = true;
				allNotFeasible = false;
			}
		}
		if (allFailure) {
			node.isFailure = true;
		}
		if (allNotFeasible) {
			node.isFeasible = false;
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
			if (!child.isFeasible) {
				node.isFeasible = false;
			}
		} else {
			for (AgreeStatement assumption : topLevelAssumptions) {
				if (AgreeUtil.exprsContradict(node.expr, assumption.expr)) {
					node.isFeasible = false;
					break;
				}
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
			if (!child.isFeasible) {
				node.isFeasible = false;
			}
		} else {
			for (AgreeStatement assumption : topLevelAssumptions) {
				if (AgreeUtil.exprsContradict(node.expr, assumption.expr)) {
					node.isFeasible = false;
					break;
				}
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
			if (!child.isFeasible) {
				node.isFeasible = false;
			}
		} else {
			for (AgreeStatement assumption : topLevelAssumptions) {
				if (AgreeUtil.exprsContradict(node.expr, assumption.expr)) {
					node.isFeasible = false;
					break;
				}
			}
		}
		return null;
	}

}
