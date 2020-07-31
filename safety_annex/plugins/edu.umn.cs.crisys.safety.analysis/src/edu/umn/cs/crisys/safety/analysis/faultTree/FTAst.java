package edu.umn.cs.crisys.safety.analysis.faultTree;

import edu.umn.cs.crisys.safety.analysis.ast.visitors.FTAstVisitor;
import edu.umn.cs.crisys.safety.analysis.ast.visitors.FTMinCutSetPrintVisitor;
import edu.umn.cs.crisys.safety.analysis.ast.visitors.FTMinCutSetTallyPrintVisitor;
import edu.umn.cs.crisys.safety.analysis.ast.visitors.FTMinCutSetTxtPrintVisitor;
import edu.umn.cs.crisys.safety.analysis.ast.visitors.FTPrettyPrintVisitor;

public abstract class FTAst {
	@Override
	public String toString() {
		FTPrettyPrintVisitor visitor = new FTPrettyPrintVisitor();
		accept(visitor);
		return visitor.toString();
	}

	public String printMinCutSet() {
		FTMinCutSetPrintVisitor visitor = new FTMinCutSetPrintVisitor();
		accept(visitor);
		return visitor.toString();
	}

	public String printMinCutSetTxt() {
		FTMinCutSetTxtPrintVisitor visitor = new FTMinCutSetTxtPrintVisitor();
		accept(visitor);
		return visitor.toString();
	}

	public String printMinCutSetTally() {
		FTMinCutSetTallyPrintVisitor visitor = new FTMinCutSetTallyPrintVisitor();
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
	public abstract <T> T accept(FTAstVisitor<T> visitor);
}
