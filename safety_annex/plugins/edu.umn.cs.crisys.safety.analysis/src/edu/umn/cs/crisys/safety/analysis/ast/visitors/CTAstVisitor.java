package edu.umn.cs.crisys.safety.analysis.ast.visitors;

import edu.umn.cs.crisys.safety.analysis.causationTree.CT;

public interface CTAstVisitor<T> {
	public T visit(CT ct);

}
