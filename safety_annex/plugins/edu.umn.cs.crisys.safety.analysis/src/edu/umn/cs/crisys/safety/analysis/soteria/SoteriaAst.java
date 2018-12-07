package edu.umn.cs.crisys.safety.analysis.soteria;

import edu.umn.cs.crisys.safety.analysis.ast.visitors.SoteriaAstVisitor;
import edu.umn.cs.crisys.safety.analysis.ast.visitors.SoteriaPrettyPrintVisitor;

public abstract class SoteriaAst {
	@Override
	public String toString() {
		SoteriaPrettyPrintVisitor visitor = new SoteriaPrettyPrintVisitor();
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
	public abstract <T> T accept(SoteriaAstVisitor<T> visitor);
}
