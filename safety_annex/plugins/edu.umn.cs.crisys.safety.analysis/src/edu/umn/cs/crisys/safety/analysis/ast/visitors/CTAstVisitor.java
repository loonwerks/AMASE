package edu.umn.cs.crisys.safety.analysis.ast.visitors;

import edu.umn.cs.crisys.safety.analysis.causationTree.CT;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTAndNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTBinaryIdNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTConstantNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTIdNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTOrNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTUnaryIdNode;

public interface CTAstVisitor<T> {
	public T visit(CT ct);

	public T visit(CTAndNode node);

	public T visit(CTOrNode node);

	public T visit(CTBinaryIdNode node);

	public T visit(CTUnaryIdNode node);

	public T visit(CTConstantNode node);

	public T visit(CTIdNode node);
}
