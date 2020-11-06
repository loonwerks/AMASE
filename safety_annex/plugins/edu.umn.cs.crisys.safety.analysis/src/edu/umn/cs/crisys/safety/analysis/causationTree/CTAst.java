package edu.umn.cs.crisys.safety.analysis.causationTree;

import edu.umn.cs.crisys.safety.analysis.ast.visitors.CTAstVisitor;

public abstract class CTAst {
//	@Override
//	public String toString() {
//		FTPrettyPrintVisitor visitor = new FTPrettyPrintVisitor();
//		accept(visitor);
//		return visitor.toString();
//	}
//
//	public String printMinCutSet() {
//		FTMinCutSetPrintVisitor visitor = new FTMinCutSetPrintVisitor();
//		accept(visitor);
//		return visitor.toString();
//	}
//
//	public String printMinCutSetTxt() {
//		FTMinCutSetTxtPrintVisitor visitor = new FTMinCutSetTxtPrintVisitor();
//		accept(visitor);
//		return visitor.toString();
//	}
//
//	public String printMinCutSetTally() {
//		FTMinCutSetTallyPrintVisitor visitor = new FTMinCutSetTallyPrintVisitor();
//		accept(visitor);
//		return visitor.toString();
//	}


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
