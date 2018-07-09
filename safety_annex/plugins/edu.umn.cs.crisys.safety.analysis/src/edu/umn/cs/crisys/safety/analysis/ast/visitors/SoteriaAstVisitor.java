package edu.umn.cs.crisys.safety.analysis.ast.visitors;

import edu.umn.cs.crisys.safety.analysis.soteria.CompContractViolation;
import edu.umn.cs.crisys.safety.analysis.soteria.CompFaultActivation;
import edu.umn.cs.crisys.safety.analysis.soteria.SoteriaComp;
import edu.umn.cs.crisys.safety.analysis.soteria.SoteriaCompLib;
import edu.umn.cs.crisys.safety.analysis.soteria.SoteriaFault;
import edu.umn.cs.crisys.safety.analysis.soteria.SoteriaFormula;
import edu.umn.cs.crisys.safety.analysis.soteria.SoteriaFormulaSubgroup;
import edu.umn.cs.crisys.safety.analysis.soteria.SoteriaModel;

public interface SoteriaAstVisitor<T> {

	public T visit(SoteriaFormula soteriaFormula);

	public T visit(SoteriaFault soteriaFault);

	public T visit(SoteriaComp soteriaComp);

	public T visit(SoteriaCompLib soteriaCompLib);

	public T visit(CompContractViolation compContractViolation);

	public T visit(CompFaultActivation compFaultActivation);

	public T visit(SoteriaModel soteriaModel);

	public T visit(SoteriaFormulaSubgroup soteriaFormulaSubgroup);
}
