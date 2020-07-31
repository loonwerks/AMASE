package edu.umn.cs.crisys.safety.analysis.ast.visitors;

import edu.umn.cs.crisys.safety.analysis.faultTree.FTAndNode;
import edu.umn.cs.crisys.safety.analysis.faultTree.FTLeafNode;
import edu.umn.cs.crisys.safety.analysis.faultTree.FTNonLeafNode;
import edu.umn.cs.crisys.safety.analysis.faultTree.FTOrNode;
import edu.umn.cs.crisys.safety.analysis.faultTree.FaultTree;

public interface FTAstVisitor<T> {
	public T visit(FaultTree faultTree);

	public T visit(FTLeafNode ftLeafNode);

	public T visit(FTNonLeafNode nonLeafNode);

	public T visit(FTOrNode orNode);

	public T visit(FTAndNode andNode);

}
