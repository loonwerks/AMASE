package edu.umn.cs.crisys.safety.analysis.soteria.faultTree;

import edu.umn.cs.crisys.safety.analysis.ast.visitors.SoteriaFTAstVisitor;
import edu.umn.cs.crisys.safety.analysis.ast.visitors.SoteriaFTMinCutSetPrintVisitor;
import edu.umn.cs.crisys.safety.analysis.ast.visitors.SoteriaFTPrettyPrintVisitor;

public abstract class SoteriaFTAst {
	@Override
	public String toString() {
		SoteriaFTPrettyPrintVisitor visitor = new SoteriaFTPrettyPrintVisitor();
		accept(visitor);
		return visitor.toString();
	}

	public String printMinCutSet() {
		SoteriaFTMinCutSetPrintVisitor visitor = new SoteriaFTMinCutSetPrintVisitor();
		accept(visitor);
		return visitor.toString();
	}

	/**
	 * Accept.
	 *
	 * @param <T> the generic type
	 * @param <S> the generic type
	 * @param visitor the visitor
	 * @return the t
	 */
	public abstract <T> T accept(SoteriaFTAstVisitor<T> visitor);
}
