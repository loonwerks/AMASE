package edu.umn.cs.crisys.safety.analysis.ast.visitors;

import edu.umn.cs.crisys.safety.analysis.soteria.faultTree.SoteriaFTAndNode;
import edu.umn.cs.crisys.safety.analysis.soteria.faultTree.SoteriaFTLeafNode;
import edu.umn.cs.crisys.safety.analysis.soteria.faultTree.SoteriaFTNonLeafNode;
import edu.umn.cs.crisys.safety.analysis.soteria.faultTree.SoteriaFTOrNode;
import edu.umn.cs.crisys.safety.analysis.soteria.faultTree.SoteriaFaultTree;

public interface SoteriaFTAstVisitor<T> {
	public T visit(SoteriaFaultTree soteriaFT);

	public T visit(SoteriaFTLeafNode soteriaFTLeafNode);

	public T visit(SoteriaFTNonLeafNode soteriaNonLeafNode);

	public T visit(SoteriaFTOrNode soteriaOrNode);

	public T visit(SoteriaFTAndNode soteriaAndNode);

}
