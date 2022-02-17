package edu.umn.cs.crisys.safety.analysis.ast.visitors;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import jkind.Assert;
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
	public static final String FAULT_DRIVER_PAIR_WITNESS_BASENAME = "__cannot_fail_together__nodeLemma";
	public static final Pattern FAULT_DRIVER_PAIR_WITNESS_PATTERN = Pattern
			.compile("(\\.|__)cannot_fail_together(\\.|__)nodeLemma");

	public static String getPairwiseWitnessName(String left, String right) {
		return left + "__and__" + right + FAULT_DRIVER_PAIR_WITNESS_BASENAME;
	}

	/**
	 * Note: due to the way this is constructed in {@link #AddPairwiseFaultDriverWitnesses(List)}
	 * it is guaranteed that each value in the map will contain exactly two elements.  Further,
	 * we can assume that in the code in this class.
	 */
	private final Map<String, List<String>> faultDriverPairWitnesses;

	public AddPairwiseFaultDriverWitnesses(List<String> faultDrivers) {
		Assert.isNotNull(faultDrivers);
		Assert.isTrue(faultDrivers.stream().allMatch(it -> it != null));
		final Map<String, List<String>> workingMap = Maps.newLinkedHashMap();
		for (int i = 0; i < faultDrivers.size(); ++i) {
			for (int j = i + 1; j < faultDrivers.size(); ++j) {
				workingMap.put(getPairwiseWitnessName(faultDrivers.get(i), faultDrivers.get(j)),
						Lists.newArrayList(faultDrivers.get(i), faultDrivers.get(j)));
			}
		}
		this.faultDriverPairWitnesses = Collections.unmodifiableMap(workingMap);
	}

	@Override
	public Program visit(Program program) {
		List<Node> nodes = visitNodes(program.nodes).stream().map(n -> {
			if (n.id.equals(program.main)) {
				List<VarDecl> locals = Lists.newArrayList(n.locals);
				List<Equation> equations = Lists.newArrayList(n.equations);
				List<String> properties = Lists.newArrayList(n.properties);
				locals.addAll(faultDriverPairWitnesses.keySet().stream()
						.map(id -> new VarDecl(id, NamedType.BOOL)).collect(Collectors.toList()));
				equations.addAll(getEquations());
				properties.addAll(faultDriverPairWitnesses.keySet());
				return new Node(n.location, n.id, n.inputs, n.outputs, locals, equations, properties, n.assertions,
						n.realizabilityInputs, n.contract, n.ivc);
			}
			return n;
		}).collect(Collectors.toList());
		return new Program(program.location, program.types, program.constants, program.functions, nodes, program.main);
	}

	public List<String> getProperties() {
		return Collections.unmodifiableList(faultDriverPairWitnesses.keySet().stream().collect(Collectors.toList()));
	}

	public Map<String, List<String>> getPairwiseWitnesses() {
		return Collections.unmodifiableMap(faultDriverPairWitnesses);
	}

	public List<Equation> getEquations() {
		return Collections.unmodifiableList(faultDriverPairWitnesses.entrySet()
				.stream()
				.map(e -> new Equation(new IdExpr(e.getKey()),
								new BinaryExpr(new IdExpr(e.getValue().get(0)), BinaryOp.OR,
										new IdExpr(e.getValue().get(1)))))
				.collect(Collectors.toList()));
	}

}
