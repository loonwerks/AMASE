package edu.umn.cs.crisys.safety.analysis.handlers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.osate.ui.dialogs.Dialog;

import com.rockwellcollins.atc.agree.analysis.AgreeException;
import com.rockwellcollins.atc.agree.analysis.AgreeRenaming;
import com.rockwellcollins.atc.agree.analysis.views.AgreeResultsLinker;

import edu.umn.cs.crisys.safety.analysis.SafetyException;
import edu.umn.cs.crisys.safety.analysis.soteria.CompContractViolation;
import edu.umn.cs.crisys.safety.analysis.soteria.CompFaultActivation;
import edu.umn.cs.crisys.safety.analysis.soteria.SoteriaComp;
import edu.umn.cs.crisys.safety.analysis.soteria.SoteriaCompLib;
import edu.umn.cs.crisys.safety.analysis.soteria.SoteriaFault;
import edu.umn.cs.crisys.safety.analysis.soteria.SoteriaFormula;
import edu.umn.cs.crisys.safety.analysis.soteria.SoteriaFormulaSubgroup;
import edu.umn.cs.crisys.safety.analysis.soteria.SoteriaModel;
import edu.umn.cs.crisys.safety.safety.FaultSubcomponent;
import edu.umn.cs.crisys.safety.safety.impl.FaultStatementImpl;
import edu.umn.cs.crisys.safety.safety.impl.ProbabilityStatementImpl;
import jkind.api.results.AnalysisResult;
import jkind.api.results.CompositeAnalysisResult;
import jkind.api.results.JKindResult;
import jkind.api.results.PropertyResult;
import jkind.api.results.Renaming;
import jkind.results.ValidProperty;

public class CompositionalFaultAnalyzer {
	SoteriaCompLib compLib = new SoteriaCompLib();
	SoteriaModel model = new SoteriaModel();
	boolean isLowerLevel = false;
	public HashMap<UniqueID, UniqueID> elemIdMap = new HashMap<>();
	public HashSet<String> compNameSet = new HashSet<>();
	public HashMap<String, Set<List<String>>> propertyMinCutSets = new HashMap<>();


	private StringBuilder mivcSb = new StringBuilder();

	private static final String seperator = System.getProperty("line.separator");

	private void newline() {
		write(seperator);
	}

	private void write(Object o) {
		mivcSb.append(o);
	}

	private void writeln(Object o) {
		mivcSb.append(o);
		newline();
	}

	private String writeMivcSbToString() {
		return mivcSb.toString();
	}

	private void clearMivcSb() {
		mivcSb.setLength(0);
	}

	public SoteriaModel generateMinCutSet(AnalysisResult result, AgreeResultsLinker linker) {
		// get current verification result
		AnalysisResult curResult = ((CompositeAnalysisResult) result).getChildren().get(0);
		walkthroughResults(curResult, null, linker);
		model.addCompLib(compLib);
		// create component instances for the base model
		model.createCompInst();
		// create component connections for the base model
		model.createConnections();
		return model;
	}

	private void walkthroughResults(AnalysisResult result, SoteriaComp comp, AgreeResultsLinker linker) {
		// if one layer, the curResult is JKindResult for the current component verified
		if (result instanceof JKindResult) {
			Renaming renaming = linker.getRenaming(result);
			if (renaming instanceof AgreeRenaming) {
				// for each propertyResult
				for (PropertyResult propertyResult : ((JKindResult) result).getPropertyResults()) {
					try {
					extractPropertyResult(comp, (AgreeRenaming) renaming, propertyResult);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			} else {
				throw new AgreeException("Not AGREE Renaming");
			}
		} else if (result instanceof CompositeAnalysisResult) {
			// get component name
			String compName = result.getName().replaceFirst("Verification for ", "");
			if (compName.contains(".")) {
				compName = compName.replaceAll("\\.", "_");
				// compName = compName.substring(0, compName.lastIndexOf('.'));
			}
			if (!compNameSet.contains(compName)) {
				compNameSet.add(compName);
				// build Soteria model for the current component
				// get current component name
				SoteriaComp curComp = new SoteriaComp(compName);
				for (AnalysisResult curResult : ((CompositeAnalysisResult) result).getChildren()) {
					// recursively call walkthroughResults
					walkthroughResults(curResult, curComp, linker);
					// only the first result contains the top level properties
					if (!isLowerLevel) {
						isLowerLevel = true;
					}
				}
				compLib.addComp(curComp);
			}

		} else {
			throw new AgreeException("Not JKindResult or CompositeAnalysisResult");
		}
	}

	private static File findSHDExe(String path) {
		if (path == null) {
			return null;
		}

		for (String element : path.split(File.pathSeparator)) {
			File executable = new File(element, "shd.exe");
			if (executable.exists()) {
				return executable;
			}
		}

		return null;
	}

	private static File findSHDExe() throws Exception {

		File executable = findSHDExe(System.getenv("SHD_HOME"));
		if (executable != null) {
			return executable;
		}

		executable = findSHDExe(System.getenv("PATH"));
		if (executable != null) {
			return executable;
		}

		throw new Exception("Unable to find shd.exe in SHD_HOME or on system PATH");
	}

	private void extractPropertyResult(SoteriaComp comp, AgreeRenaming renaming, PropertyResult propertyResult)
			throws InterruptedException {
		// get original property name
		String origPropertyName = propertyResult.getName();
		String lustreName = renaming.getLustreNameFromAgreeVar(origPropertyName);
		String propertyName = updateElemName(comp.componentName + "_" + lustreName);
		// if it is a guarantee
		if (lustreName.startsWith("__GUARANTEE")) {
			// if it's a valid guarantee
			if (propertyResult.getStatus().equals(jkind.api.results.Status.VALID)) {
				// add property as an output to the soteria map
				comp.addOutput(propertyName);
				// add property violation as a top level fault to the model
				if (!isLowerLevel) {
					CompContractViolation contractViolation = new CompContractViolation(comp.componentName,
							propertyName);
					model.addTopLevelFault(contractViolation);
					System.out.println("top level property to verify");
				}
				System.out.println("property to verify " + propertyName);

				ValidProperty property = (ValidProperty) propertyResult.getProperty();
				SoteriaFormula formula = new SoteriaFormula(propertyName);

				// 1. compute Minimal Hitting Set from the MIVCs
				// to turn MIVCs to Minimal Cut Sets
				HashMap<Integer, String> intToStrMap = new HashMap<>();
				HashMap<String, Integer> strToIntMap = new HashMap<>();
				Integer elemIdNum = new Integer(1);

				// 1.1 turn string elements in MIVCs to unique numbers
				boolean multipleSets = false;
				clearMivcSb();
				for (List<String> ivcSet : property.getIvcSets()) {
					if (multipleSets) {
						newline();
					}
					for (String ivcElem : ivcSet) {
						//update ivcElemName
						String ivcElemName = updateElemName(ivcElem);
						//if the string is already in the map
						//write its integer id number
						if(strToIntMap.containsKey(ivcElemName)) {
							write(strToIntMap.get(ivcElemName) + " ");
						}
						//if the string is not yet in the map
						//assign it an unique integer id number
						//update both maps
						else {
							intToStrMap.putIfAbsent(elemIdNum, ivcElemName);
							strToIntMap.putIfAbsent(ivcElemName, elemIdNum);
							write(elemIdNum + " ");
							elemIdNum++;
						}
					}
					multipleSets = true;
				}

				Set<List<String>> mhs = new HashSet<List<String>>();
				try {
					// 1.2 write MIVC numbers to file
					File mivcFile = File.createTempFile("mivcSets_", ".dat");
					BufferedWriter bw = new BufferedWriter(new FileWriter(mivcFile));
					bw.write(writeMivcSbToString());
					bw.close();
					clearMivcSb();

					String shdPath = findSHDExe().getAbsolutePath();
					// System.out.println("shdPath is: " + shdPath);
					// 1.3 write MHS numbers to file
					File mhsFile = File.createTempFile("mhs_", ".dat");
					// System.out.println("mhs file path is: " + mhsFile.getAbsolutePath());

					// 1.4 invoke shd.exe to generate minimal hitting sets from the numbered MIVC sets
					ProcessBuilder pb = new ProcessBuilder(
							shdPath, "0",
							mivcFile.getAbsolutePath(),
							mhsFile.getAbsolutePath());
					// inherit IO
					// pb.inheritIO();
					Process process = pb.start();
					process.waitFor();

					// 1.5 read in numbered minimal hitting sets
					BufferedReader br = new BufferedReader(new FileReader(mhsFile));
					String sCurrentLine;

					// 1.6 convert numbered minimal hitting set to minimal hitting set in strings
					while ((sCurrentLine = br.readLine()) != null) {
						String[] curMHSElemNums = sCurrentLine.split(" ");
						List<String> curMHSElemStrs = new ArrayList<>();
						System.out.println("current minimal cut set");
						for (int i = 0; i < curMHSElemNums.length; i++) {
							Integer mhsElemNum = new Integer(curMHSElemNums[i]);
							String mhsElemStr = intToStrMap.get(mhsElemNum);
							curMHSElemStrs.add(mhsElemStr);
							System.out.println(mhsElemStr);
						}
						mhs.add(curMHSElemStrs);
					}
					propertyMinCutSets.put(propertyName, mhs);

				} catch (Exception e) {
					Dialog.showError("Unable to open file", e.getMessage());
					e.printStackTrace();
				}

				// then handle multiple minimal cut sets
				// for (List<String> minCutSet : minCutSets) {
				for (List<String> ivcSet : property.getIvcSets()) {
					SoteriaFormulaSubgroup formulaSubgroup = new SoteriaFormulaSubgroup(propertyName);
					extractIvcSets(comp, renaming, formulaSubgroup, ivcSet);
					if (!formulaSubgroup.elmeList.isEmpty()) {
						formula.addFormulaSubgroup(formulaSubgroup);
					}
				}
				if (!formula.formulaBody.isEmpty()) {
					comp.addFormula(propertyName, formula);
				}
			} else if (propertyResult.getStatus().equals(jkind.api.results.Status.CANCELED)) {

//				JOptionPane.showMessageDialog(null,
//						"One of the properties was canceled in the process of model checking."
//								+ " Rerun this analysis to proceed.",
//						"Safety Analysis Error", JOptionPane.ERROR_MESSAGE);

				throw new SafetyException("One of the properties was canceled in the process of model checking."
						+ " Rerun this analysis to proceed.");
			} else if (propertyResult.getStatus().equals(jkind.api.results.Status.INVALID)) {
//				JOptionPane.showMessageDialog(null,
//						"One of the properties is invalid. The model must be valid using AGREE Verify All Layers."
//								+ " The invalid property is shown in the AGREE console.",
//						"Safety Analysis Error",
//						JOptionPane.ERROR_MESSAGE);

				throw new SafetyException(
						"One of the properties is invalid. The model must be valid using AGREE Verify All Layers.");
			}
		}
	}

	private void extractIvcSets(SoteriaComp comp, AgreeRenaming renaming, SoteriaFormulaSubgroup formulaSubgroup,
			List<String> ivcSet) {
		for (String ivcElem : ivcSet) {
			extractIvcElem(comp, renaming, formulaSubgroup, ivcElem);
		}
	}

	private void extractIvcElem(SoteriaComp comp, AgreeRenaming renaming, SoteriaFormulaSubgroup formulaSubgroup,
			String ivcElem) {
		String ivcElemName = updateElemName(ivcElem);
		// add each ivc element to formulaSubgroup
		if (ivcElem.startsWith("__fault")) {
			String refStr = renaming.getSupportRefString(ivcElem);
			extractFaultIvcElem(comp, renaming, formulaSubgroup, ivcElemName, refStr);
		} else {
			extractContractIvcElem(comp, formulaSubgroup, ivcElemName);
		}
	}

	private void extractContractIvcElem(SoteriaComp comp, SoteriaFormulaSubgroup formulaSubgroup, String propertyName) {
		// add each ivc element that are verified contracts from subsequent layer to component inputs (sans duplicate)
		comp.addInput(propertyName);
		CompContractViolation contractViolation = new CompContractViolation(comp.componentName, propertyName);
		formulaSubgroup.addFormulaElem(contractViolation);
	}

	private void extractFaultIvcElem(SoteriaComp comp, AgreeRenaming renaming, SoteriaFormulaSubgroup formulaSubgroup,
			String faultName, String faultRefName) {
		// get the fault name for that fault activation variable in ivcElement
		CompFaultActivation faultActivation = new CompFaultActivation(comp.componentName, faultName);
		formulaSubgroup.addFormulaElem(faultActivation);
		// if ivcElem is not yet in basicEvents
		if (!comp.basicEvents.containsKey(faultName)) {
			FaultStatementImpl faultStmtImpl = (FaultStatementImpl) renaming.getRefMap().get(faultRefName);
			for (FaultSubcomponent faultSub : faultStmtImpl.getFaultDefinitions()) {
				if (faultSub instanceof ProbabilityStatementImpl) {
					String probStr = ((ProbabilityStatementImpl) faultSub).getProbability();
					float failureProb = Float.parseFloat(probStr);
					// TODO: need to have component specify failure rate and exposure time in the future
					// currently treat exposure time as (float) 1.0
					// and treat the failure probability from the fault statement as the failure rate
					SoteriaFault basicEvent = new SoteriaFault(updateElemName(faultName), failureProb, (float) 1.0);
					comp.addBasicEvent(faultName, basicEvent);
				}
			}
		}
	}

	public String updateElemName(String name) {
		String updatedName = null;
		UniqueID originalNameId = new UniqueID(name);
		// first check if the original name is already in the keys of the map
		// if yes, retrieve the updated name from its value
		if (elemIdMap.containsKey(originalNameId)) {
			updatedName = elemIdMap.get(originalNameId).id;
		}
		// if not, update the name
		else {
			// replace all non-alphanumeric characters with "_"
			// remove leading _
			updatedName = name.replaceAll("\\P{Alnum}", "_").replaceAll("^_+", "");
			// add a new entry into the map
			elemIdMap.put(originalNameId, new UniqueID(updatedName));
		}

		return updatedName;
	}
}
