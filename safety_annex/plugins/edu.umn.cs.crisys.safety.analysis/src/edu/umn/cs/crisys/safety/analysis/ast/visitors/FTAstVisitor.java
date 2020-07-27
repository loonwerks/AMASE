package edu.umn.cs.crisys.safety.analysis.ast.visitors;

import edu.umn.cs.crisys.safety.analysis.faultTree.FTAndNode;
import edu.umn.cs.crisys.safety.analysis.faultTree.FTLeafNode;
import edu.umn.cs.crisys.safety.analysis.faultTree.FTNonLeafNode;
import edu.umn.cs.crisys.safety.analysis.faultTree.FTOrNode;
import edu.umn.cs.crisys.safety.analysis.faultTree.FaultTree;

public interface FTAstVisitor<T> {
	public T visit(FaultTree soteriaFT);

	public T visit(FTLeafNode soteriaFTLeafNode);

	public T visit(FTNonLeafNode soteriaNonLeafNode);

	public T visit(FTOrNode soteriaOrNode);

	public T visit(FTAndNode soteriaAndNode);

}
