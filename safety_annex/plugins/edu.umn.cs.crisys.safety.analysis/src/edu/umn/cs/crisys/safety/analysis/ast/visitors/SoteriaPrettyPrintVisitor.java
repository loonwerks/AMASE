package edu.umn.cs.crisys.safety.analysis.ast.visitors;

import edu.umn.cs.crisys.safety.analysis.soteria.CompContractViolation;
import edu.umn.cs.crisys.safety.analysis.soteria.CompFaultActivation;
import edu.umn.cs.crisys.safety.analysis.soteria.SoteriaComp;
import edu.umn.cs.crisys.safety.analysis.soteria.SoteriaCompLib;
import edu.umn.cs.crisys.safety.analysis.soteria.SoteriaFault;
import edu.umn.cs.crisys.safety.analysis.soteria.SoteriaFormula;
import edu.umn.cs.crisys.safety.analysis.soteria.SoteriaModel;

public class SoteriaPrettyPrintVisitor implements SoteriaAstVisitor<Void> {
	private StringBuilder sb = new StringBuilder();

	@Override
	public String toString() {
		return sb.toString();
	}

	protected void write(Object o) {
		sb.append(o);
	}

	private static final String seperator = System.getProperty("line.separator");

	private void newline() {
		write(seperator);
	}

	@Override
	public Void visit(SoteriaFormula soteriaFormula) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void visit(SoteriaFault soteriaFault) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void visit(SoteriaComp soteriaComp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void visit(SoteriaCompLib soteriaCompLib) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void visit(CompContractViolation compContractViolation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void visit(CompFaultActivation compFaultActivation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void visit(SoteriaModel soteriaModel) {
		write("#use \"top.ml\";;");
		newline();
		write("(* ----- COMPONENT LIBRARY ----- *)");
		newline();
		return null;
	}

}

