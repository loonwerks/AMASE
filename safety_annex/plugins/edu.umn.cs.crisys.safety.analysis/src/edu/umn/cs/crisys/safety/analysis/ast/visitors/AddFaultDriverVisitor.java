package edu.umn.cs.crisys.safety.analysis.ast.visitors;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

import jkind.lustre.BinaryExpr;
import jkind.lustre.BinaryOp;
import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.NamedType;
import jkind.lustre.Node;
import jkind.lustre.NodeCallExpr;
import jkind.lustre.Program;
import jkind.lustre.UnaryExpr;
import jkind.lustre.UnaryOp;
import jkind.lustre.VarDecl;
import jkind.lustre.visitors.AstMapVisitor;

public class AddFaultDriverVisitor extends AstMapVisitor {

	public static final String FAULT_DRIVER_SUFFIX = "__fault_driver";

	private final String targetSubnodeName;
	private final String targetVarName;

	public AddFaultDriverVisitor(String targetSubnodeName, String targetVarName) {
		this.targetSubnodeName = targetSubnodeName;
		this.targetVarName = targetVarName;
	}

	public static String getFaultDriverId(String targetVarName) {
		return targetVarName + FAULT_DRIVER_SUFFIX;
	}

	// Note: Using LinkedHashSet because iteration is guaranteed to be insertion ordered
	private Set<String> liftedTargetSubnodeInputs = new LinkedHashSet<>();

	@Override
	public NodeCallExpr visit(NodeCallExpr nodeCall) {
		List<Expr> args = visitExprs(nodeCall.args);
		if (nodeCall.node.equals(targetSubnodeName)) {
			liftedTargetSubnodeInputs.stream()
					.forEach(a -> args.add(new IdExpr(targetSubnodeName + a)));
		}
		return new NodeCallExpr(nodeCall.location, nodeCall.node, args);
	}

	@Override
	public Program visit(Program program) {
		List<Node> nodes = visitNodes(program.nodes).stream().map(n -> {
			if (n.id.equals(program.main)) {
				List<VarDecl> inputs = Lists.newArrayList(n.inputs);
				List<Expr> assertions = visitAssertions(n.assertions);
				liftedTargetSubnodeInputs.stream()
						.forEach(i -> inputs.add(new VarDecl(targetSubnodeName + i, NamedType.BOOL)));
				return new Node(n.location, n.id, inputs, n.outputs, n.locals, n.equations, n.properties, assertions,
						n.realizabilityInputs, n.contract, n.ivc);
			}
			return n;
		}).collect(Collectors.toList());
		return new Program(program.location, program.types, program.constants, program.functions, nodes, program.main);
	}

	@Override
	public Node visit(Node node) {
		List<VarDecl> inputs = Lists.newArrayList(node.inputs);
		List<Equation> equations = Lists.newArrayList(node.equations);

		if (node.id.equals(targetSubnodeName)) {
			equations = visitEquations(node.equations);
			inputs.add(new VarDecl(getFaultDriverId(targetVarName), NamedType.BOOL));
			liftedTargetSubnodeInputs.add(getFaultDriverId(targetVarName));
		}
		return new Node(node.location, node.id, inputs, node.outputs, node.locals, equations, node.properties,
				node.assertions, node.realizabilityInputs, node.contract, node.ivc);
	}

	@Override
	public Equation visit(Equation equation) {
		if (equation.lhs.stream().anyMatch(v -> v.id.equals(targetVarName))) {
			return new Equation(equation.lhs,
					new BinaryExpr(new UnaryExpr(UnaryOp.NOT, new IdExpr(getFaultDriverId(targetVarName))),
							BinaryOp.IMPLIES, equation.expr));
		}
		return equation;
	}

}
