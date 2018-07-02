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
	public Void visit(SoteriaFormula formula) {
		write("(");
		write("[\"" + formula.propertyName + "\"; " + "\"" + formula.propertyFaultString + "\"],");
		newline();
		// write each formula element
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
		write("faults = [\""+ comp.faultString+ "\"];");
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
		for (SoteriaFault fault : comp.basicEvents) {
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
		for (SoteriaFault fault : comp.basicEvents) {
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
		for (SoteriaFormula formula : comp.formulas) {
			if (multipleElem) {
				write("; ");
			}
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
			}
			comp.accept(this);
			multipleElem = true;
		}
		write("];;");
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
	public Void visit(SoteriaModel model) {
		write(model.includeStr);
		newline();
		model.soteriaCompLib.accept(this);
		newline();
		return null;
	}

}

