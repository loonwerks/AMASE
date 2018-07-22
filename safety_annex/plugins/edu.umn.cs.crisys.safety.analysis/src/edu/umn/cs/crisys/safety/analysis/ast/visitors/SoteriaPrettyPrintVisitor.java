package edu.umn.cs.crisys.safety.analysis.ast.visitors;

import java.util.Map;

import edu.umn.cs.crisys.safety.analysis.soteria.CompContractViolation;
import edu.umn.cs.crisys.safety.analysis.soteria.CompFaultActivation;
import edu.umn.cs.crisys.safety.analysis.soteria.SoteriaComp;
import edu.umn.cs.crisys.safety.analysis.soteria.SoteriaCompInst;
import edu.umn.cs.crisys.safety.analysis.soteria.SoteriaCompLib;
import edu.umn.cs.crisys.safety.analysis.soteria.SoteriaFault;
import edu.umn.cs.crisys.safety.analysis.soteria.SoteriaFormula;
import edu.umn.cs.crisys.safety.analysis.soteria.SoteriaFormulaElem;
import edu.umn.cs.crisys.safety.analysis.soteria.SoteriaFormulaSubgroup;
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
	public Void visit(SoteriaFormula formula) {
		write("(");
		write("[\"" + formula.propertyName + "\"; " + "\"" + formula.propertyFaultString + "\"],");
		newline();
		// TODO: if multiple groups, use conjunction to connect the groups
		if (formula.formulaBody.size() == 1) {
			SoteriaFormulaSubgroup subgroup = formula.formulaBody.get(0);
			subgroup.accept(this);
		}
		write(")");
		return null;
	}

	@Override
	public Void visit(SoteriaFault fault) {
		return null;
	}

	@Override
	public Void visit(SoteriaComp comp) {
		write("{");
		write("name = \"" + comp.componentName + "\";");
		newline();
		write("faults = [\"" + comp.faultString + "\"];");
		newline();
		write("input_flows = [");
		// write each input
		boolean multipleElem = false;
		for (String input : comp.inputFlows) {
			if (multipleElem) {
				write("; ");
			}
			write("\"" + input + "\"");
			multipleElem = true;
		}
		write("];");
		newline();
		write("basic_events = [");
		multipleElem = false;
		// write each basic event name
		for (SoteriaFault fault : comp.basicEvents.values()) {
			if (multipleElem) {
				write("; ");
			}
			write("\"" + fault.faultName + "\"");
			multipleElem = true;
		}
		write("];");
		newline();
		write("event_info = [");
		multipleElem = false;
		// write each basic event failure rate and exposure time
		for (SoteriaFault fault : comp.basicEvents.values()) {
			if (multipleElem) {
				write("; ");
			}
			write("(" + fault.failureRate + ", " + fault.exposureTime + ")");
			multipleElem = true;
		}
		write("];");
		newline();
		write("output_flows = [");
		// write each output
		multipleElem = false;
		for (String output : comp.outputFlows) {
			if (multipleElem) {
				write("; ");
			}
			write("\"" + output + "\"");
			multipleElem = true;
		}
		write("];");
		newline();
		write("formulas = [");
		newline();
		// write each formula
		multipleElem = false;
		for (Map.Entry<String, SoteriaFormula> entry : comp.formulas.entrySet()) {
			if (multipleElem) {
				write("; ");
				newline();
			}
			SoteriaFormula formula = entry.getValue();
			formula.accept(this);
			multipleElem = true;
		}
		write("]");
		newline();
		write("}");
		return null;
	}

	@Override
	public Void visit(SoteriaCompLib compLib) {
		write(compLib.commentStr);
		newline();
		write("let " + compLib.compLibName + " = ");
		newline();
		write("  [");
		newline();
		boolean multipleElem = false;
		// write each component
		for (SoteriaComp comp : compLib.comps) {
			if (multipleElem) {
				write("; ");
				newline();
			}
			comp.accept(this);
			multipleElem = true;
		}
		write("];;");
		return null;
	}

	@Override
	public Void visit(CompContractViolation violation) {
		write("F[" + "\"" + violation.contractString + "\"; ");
		write("\"" + violation.contractViolationFaultStr + "\"");
		write("]");
		return null;
	}

	@Override
	public Void visit(CompFaultActivation activation) {
		write("F[" + "\"" + activation.faultName + "\"]");
		return null;
	}

	@Override
	public Void visit(SoteriaModel model) {
		write(model.includeStr);
		newline();
		model.compLib.accept(this);
		newline();
		return null;
	}

	@Override
	public Void visit(SoteriaFormulaSubgroup subgroup) {
		// use disjunction if more than one element
		if (subgroup.elmeList.size() == 1) {
			subgroup.elmeList.get(0).accept(this);
		} else {
			write("Or[");
			boolean multipleElem = false;
			// write each element
			for (SoteriaFormulaElem elem : subgroup.elmeList) {
				if (multipleElem) {
					write("; ");
					newline();
				}
				elem.accept(this);
				multipleElem = true;
			}
			write("]");
		}
		return null;
	}

	@Override
	public Void visit(SoteriaCompInst soteriaCompInst) {
		// TODO Auto-generated method stub
		return null;
	}

}
