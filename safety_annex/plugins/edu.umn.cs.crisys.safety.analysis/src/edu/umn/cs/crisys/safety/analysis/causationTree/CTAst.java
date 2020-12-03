package edu.umn.cs.crisys.safety.analysis.causationTree;

import edu.umn.cs.crisys.safety.analysis.ast.visitors.CTAstVisitor;

public abstract class CTAst {

	/**
	 * Accept.
	 *
	 * @param <T> the generic type
	 * @param <S> the generic type
	 * @param visitor the visitor
	 * @return the t
	 */
	public abstract <T> T accept(CTAstVisitor<T> visitor);
}
