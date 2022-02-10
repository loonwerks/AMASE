package edu.umn.cs.crisys.safety.analysis.ast.visitors;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

import jkind.Assert;
import jkind.lustre.BinaryExpr;
import jkind.lustre.BinaryOp;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.Node;
import jkind.lustre.UnaryExpr;
import jkind.lustre.UnaryOp;
import jkind.lustre.visitors.AstMapVisitor;

public class AddFaultDriverGuardAssertionVisitor extends AstMapVisitor {

	private final String nodeId;
	private final List<String> faultDriverIds;

	public AddFaultDriverGuardAssertionVisitor(String nodeId, List<String> faultDriverIds) {
		Assert.isNotNull(nodeId);
		Assert.isNotNull(faultDriverIds);
		Assert.isTrue(faultDriverIds.stream().allMatch(it -> it != null));
		Assert.isTrue(!faultDriverIds.isEmpty());
		this.nodeId = nodeId;
		this.faultDriverIds = Collections.unmodifiableList(faultDriverIds);
	}

	@Override
	public Node visit(Node node) {
		if (nodeId.equals(node.id)) {
			List<Expr> assertions = Lists.newArrayList(node.assertions);
			Expr expr = new IdExpr(faultDriverIds.get(0));
			for (int i = 1; i < faultDriverIds.size(); ++i) {
				expr = new BinaryExpr(expr, BinaryOp.AND, new IdExpr(faultDriverIds.get(i)));
			}
			assertions.add(new UnaryExpr(UnaryOp.NOT, expr));
			return new Node(node.location, node.id, node.inputs, node.outputs, node.locals, node.equations,
					node.properties, assertions, node.realizabilityInputs, node.contract, node.ivc);
		}
		return node;
	}

}
