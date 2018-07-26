package edu.umn.cs.crisys.safety.analysis.ast.visitors;

import java.util.Map;

import edu.umn.cs.crisys.safety.analysis.soteria.CompContractViolation;
import edu.umn.cs.crisys.safety.analysis.soteria.CompFaultActivation;
import edu.umn.cs.crisys.safety.analysis.soteria.SoteriaComp;
import edu.umn.cs.crisys.safety.analysis.soteria.SoteriaCompConnection;
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

	protected void writeln(Object o) {
		sb.append(o);
		newline();
	}

	private static final String seperator = System.getProperty("line.separator");

	private void newline() {
		write(seperator);
	}

	@Override
	public Void visit(SoteriaFormula formula) {
		write("(");
		writeln("[\"" + formula.propertyName + "\"; " + "\"" + formula.propertyFaultString + "\"],");
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
		writeln("name = \"" + comp.componentName + "\";");
		writeln("faults = [\"" + comp.faultString + "\"];");
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
		writeln("];");
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
		writeln("];");
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
		writeln("];");
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
		writeln("];");
		writeln("formulas = [");
		// write each formula
		multipleElem = false;
		for (Map.Entry<String, SoteriaFormula> entry : comp.formulas.entrySet()) {
			if (multipleElem) {
				writeln("; ");
			}
			SoteriaFormula formula = entry.getValue();
			formula.accept(this);
			multipleElem = true;
		}
		writeln("]");
		write("}");
		return null;
	}

	@Override
	public Void visit(SoteriaCompLib compLib) {
		writeln("(* ----- COMPONENT LIBRARY ----- *)");
		newline();
		writeln("let " + compLib.compLibName + " = ");
		writeln("  [");
		boolean multipleElem = false;
		// write each component
		for (SoteriaComp comp : compLib.comps) {
			if (multipleElem) {
				writeln("; ");
				newline();
			}
			comp.accept(this);
			multipleElem = true;
		}
		writeln("];;");
		newline();
		// create library checks
		createLibraryChecks(compLib.compLibName);
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
		// create a model for each top level fault
		String firstModelName = null;
		for (CompContractViolation topLevelFault : model.topLevelFaults) {
			String modelName = "model_" + topLevelFault.contractString.replace(" ", "_");
			String compLibName = model.compLib.compLibName;
			writeln("(* ----- COMPONENT INSTANCES, CONNECTIONS, OUT RANGE TOP LEVEL FAULT ----- *)");
			writeln("let " + modelName + " = ");
			// write instances and connections declarations in the first model
			if (firstModelName == null) {
				firstModelName = modelName;
				writeln("{instances = ");
				write("[");
				for (SoteriaCompInst compInst : model.compInstList) {
					compInst.accept(this);
				}
				writeln("];");
				writeln("connections = ");
				write("[");
				for (SoteriaCompConnection connection : model.connectionList) {
					connection.accept(this);
				}
				writeln("];");
			}
			// create references to the instances and connections declarations in subsequent models
			else {
				writeln("{instances = " + firstModelName + ".instances;");
				writeln("connections=" + firstModelName + ".connections;");
			}
			//write top level fault for the model
			write("top_fault = (");
			write("\"" + topLevelFault.compName + "\", ");
			write("F[\"" + topLevelFault.contractString + "\"; ");
			write("\"" + topLevelFault.contractViolationFaultStr + "\"]");
			write(")");
			writeln("} ;;");
			newline();
			// create model checks
			createModelChecks(modelName, compLibName);
			// create pre-analyses model visualizations
			preAnalysesVisualizations(modelName, compLibName);
			// model analyses
			modelAnalysesAndVisualization(modelName, compLibName);
			// create post-analyses model visualizations
		}

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
					writeln("; ");
				}
				elem.accept(this);
				multipleElem = true;
			}
			write("]");
		}
		return null;
	}

	@Override
	public Void visit(SoteriaCompInst compInst) {
		write("makeInstance " + "\"" + compInst.instanceName + "\" ");
		writeln("\"" + compInst.componentName + "\"" + "();");
		return null;
	}

	@Override
	public Void visit(SoteriaCompConnection connection) {
		write("((\"" + connection.destCompName + "\", ");
		write("\"" + connection.destCompContract + "\"),");
		write("(\"" + connection.srcCompName + "\", ");
		write("\"" + connection.srcCompContract + "\")); ");
		return null;
	}

	private void createLibraryChecks(String libName) {
		writeln("(* ----- CHECK LIBRARY ----- *)");
		writeln("checkLibrary_componentUnique " + libName + ";;");
		writeln("checkLibrary_nonEmptyFaults " + libName + ";;");
		writeln("checkLibrary_disjointInputFlowsandBasicEvents " + libName + ";;");
		writeln("checkLibrary_listsAreConsistentLengths " + libName + ";;");
		writeln("checkLibrary_allOutputFaultsHaveFormulas " + libName + ";;");
		writeln("checkLibrary_formulasMakeSense " + libName + ";;");
		newline();
	}

	private void createModelChecks(String modelName, String compLibName) {
		writeln("(* ----- CHECK MODEL ----- *)");
		writeln("checkModel_instanceNameUnique " + modelName + ";;");
		writeln("checkModel_cnameInstanceIsDefinedInLibrary " + modelName + " " + compLibName + ";;");
		writeln("checkModel_exposureOfBasicIsDefinedInLibrary " + modelName + " " + compLibName + ";;");
		writeln("checkModel_validConnections " + modelName + " " + compLibName + ";;");
		writeln("checkModel_inputFlowUnique " + modelName + ";;");
		newline();
	}

	private void preAnalysesVisualizations(String modelName, String compLibName) {
		writeln("(* ----- PRE ANALYSES MODEL VISUALIZATIONS ----- *)");
		write("dot_gen_show_ph_file ~rend:\"pdf\" " + modelName + " ");
		writeln("\"" + modelName + "_physical.gv\";;");
		write("dot_gen_show_funct_file ~rend:\"pdf\" " + compLibName + " " + modelName + " ");
		writeln("\"" + modelName + "_functional.gv\";;");
		write("dot_gen_show_fault_file ~rend:\"pdf\" " + compLibName + " " + modelName + " ");
		writeln("\"" + modelName + "_fault_propagation.gv\";;");
		newline();
	}

	private void modelAnalysesAndVisualization(String modelName, String compLibName) {
		writeln("(* ----- CUTSET WITH PROBABILITIES ----- *)");
		String mdlFTName = modelName + "_ftree";
		// model analyses
		writeln("let " + mdlFTName + " = model_to_ftree " + compLibName + " " + modelName + ";;");
		writeln("probErrorCutImp " + mdlFTName + ";;");
		writeln("probErrorCut " + mdlFTName + ";;");
		newline();
		writeln("(* ----- FAULT TREE VISUALIZATIONS ----- *)");
		// fault tree visualization
		write("dot_gen_show_direct_tree_file ");
		writeln("\"" + modelName + "_direct_ftree.gv\" " + mdlFTName + " ;;");
		write("dot_gen_show_tree_file ");
		writeln("\"" + modelName + "_optimized_ftree.gv\" " + mdlFTName + " ;;");
		newline();
	}

}
