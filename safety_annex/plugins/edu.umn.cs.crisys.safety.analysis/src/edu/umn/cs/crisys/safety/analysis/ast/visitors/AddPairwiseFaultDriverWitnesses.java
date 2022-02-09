package edu.umn.cs.crisys.safety.analysis.ast.visitors;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeASTBuilder;

import jkind.lustre.BinaryExpr;
import jkind.lustre.BinaryOp;
import jkind.lustre.Equation;
import jkind.lustre.IdExpr;
import jkind.lustre.NamedType;
import jkind.lustre.Node;
import jkind.lustre.Program;
import jkind.lustre.VarDecl;
import jkind.lustre.visitors.AstMapVisitor;

public class AddPairwiseFaultDriverWitnesses extends AstMapVisitor {

	// WARNING: THe name must contain the substring "__nodeLemma" to get past
	// the renaming when the property is added to the result. This isn't so bad
	// as these witnesses can rightly be described as node lemmas.
	public static final String FAULT_DRIVER_PAIR_WITNESS_BASENAME = AgreeASTBuilder.dotChar
			+ "fault_driver_pair_witness__nodeLemma";

	private final List<Equation> faultDriverPairWitnesses;

	public AddPairwiseFaultDriverWitnesses(List<String> faultDrivers) {
		int count = 0;
		List<Equation> workingList = Lists.newArrayList();
		for (int i = 0; i < faultDrivers.size(); ++i) {
			for (int j = i + 1; j < faultDrivers.size(); ++j) {
				workingList.add(new Equation(
						Lists.newArrayList(new IdExpr(FAULT_DRIVER_PAIR_WITNESS_BASENAME + count++)),
						new BinaryExpr(new IdExpr(faultDrivers.get(i)), BinaryOp.OR, new IdExpr(faultDrivers.get(j)))));
			}
		}
		this.faultDriverPairWitnesses = Collections.unmodifiableList(workingList);
	}

	@Override
	public Program visit(Program program) {
		List<Node> nodes = visitNodes(program.nodes).stream().map(n -> {
			if (n.id.equals(program.main)) {
				List<VarDecl> locals = Lists.newArrayList(n.locals);
				List<Equation> equations = Lists.newArrayList(n.equations);
				List<String> properties = Lists.newArrayList(n.properties);
				locals.addAll(faultDriverPairWitnesses.stream()
						.map(eq -> new VarDecl(eq.lhs.get(0).id, NamedType.BOOL))
								.collect(Collectors.toList()));
				equations.addAll(faultDriverPairWitnesses);
				properties.addAll(
						faultDriverPairWitnesses.stream().map(eq -> eq.lhs.get(0).id).collect(Collectors.toList()));
				return new Node(n.location, n.id, n.inputs, n.outputs, locals, equations, properties, n.assertions,
						n.realizabilityInputs, n.contract, n.ivc);
			}
			return n;
		}).collect(Collectors.toList());
		return new Program(program.location, program.types, program.constants, program.functions, nodes, program.main);
	}

	public List<String> getProperties() {
		return Lists.newArrayList(
				faultDriverPairWitnesses.stream().map(eq -> eq.lhs.get(0).id).collect(Collectors.toList()));
	}

}
