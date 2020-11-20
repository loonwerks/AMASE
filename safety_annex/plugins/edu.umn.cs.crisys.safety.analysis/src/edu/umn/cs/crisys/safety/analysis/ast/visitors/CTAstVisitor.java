package edu.umn.cs.crisys.safety.analysis.ast.visitors;

import edu.umn.cs.crisys.safety.analysis.causationTree.CT;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTAndNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTDivideNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTEqualNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTFalseNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTGreaterEqualNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTGreaterNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTIdNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTInitNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTIntNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTLessEqualNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTLessNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTMinusNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTMultiplyNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTNonEqualNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTNonInitNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTOrNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTPlusNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTPreNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTRealNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTRootNode;
import edu.umn.cs.crisys.safety.analysis.causationTree.CTTrueNode;

public interface CTAstVisitor<T> {
	public T visit(CT ct);

	public T visit(CTAndNode node);

	public T visit(CTDivideNode node);

	public T visit(CTEqualNode node);

	public T visit(CTFalseNode node);

	public T visit(CTGreaterEqualNode node);

	public T visit(CTGreaterNode node);

	public T visit(CTIdNode node);

	public T visit(CTInitNode node);

	public T visit(CTIntNode node);

	public T visit(CTLessEqualNode node);

	public T visit(CTLessNode node);

	public T visit(CTMinusNode node);

	public T visit(CTMultiplyNode node);

	public T visit(CTNonEqualNode node);

	public T visit(CTNonInitNode node);

	public T visit(CTOrNode node);

	public T visit(CTPlusNode node);

	public T visit(CTPreNode node);

	public T visit(CTRealNode node);

	public T visit(CTRootNode node);

	public T visit(CTTrueNode node);

}
