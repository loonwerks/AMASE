package edu.umn.cs.crisys.safety.analysis;

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

import edu.umn.cs.crisys.safety.analysis.ast.visitors.AddFaultsToNodeVisitor;
import edu.umn.cs.crisys.safety.analysis.generators.UniqueID;
import jkind.JKindException;

/*
 * This class provides some utility methods specific for the
 * MHS algorithms:
 * typeContainsSafetyAnnex(Subcomponent)
 * containsSafetyAnnex(Subcomponent)
 */
public class MHSUtils {

	private static StringBuilder sourceSetSb = new StringBuilder();
	private static HashMap<edu.umn.cs.crisys.safety.analysis.generators.UniqueID, UniqueID> elemIdMap = new HashMap<>();
	private static final String seperator = System.getProperty("line.separator");
	private static int varIndex = 0;

	private static void newline() {
		write(seperator);
	}

	private static void write(Object o) {
		sourceSetSb.append(o);
	}

	private static String sourceSetToStr() {
		return sourceSetSb.toString();
	}

	private static void clearSourceSetSb() {
		sourceSetSb.setLength(0);
	}

	/**
	 * Ensures file is deleted
	 * @param file
	 */
	private static void ensureDeleted(File file) {
		if (file != null && file.exists()) {
			file.delete();
			if (file.exists()) {
				throw new JKindException("Unable to delete file: " + file);
			}
		}
	}

	/**
	 * Find adgmhs executable with path string
	 * @param path to the executable
	 * @return Exe file
	 */
	private static File findMHSalg(String path) {
		if (path == null) {
			return null;
		}

		for (String element : path.split(File.pathSeparator)) {
			File executable = new File(element, "agdmhs.exe");
			if (executable.exists()) {
				return executable;
			}
		}

		return null;
	}

	/**
	 * Find MHS algorithm through MHS_HOME and PATH
	 * @return executable file
	 * @throws Exception
	 */
	private static File findMHSalg() throws Exception {

		File executable = findMHSalg(System.getenv("MHS_HOME"));
		if (executable != null) {
			return executable;
		}

		executable = findMHSalg(System.getenv("PATH"));
		if (executable != null) {
			return executable;
		}

		throw new Exception("Unable to find MHS algorithm in MHS_HOME on system PATH");
	}

	/**
	 *
	 * @param sourceSets
	 * @param mhsLimit: 0 for no limit; > 0 for upper limit on the mhs set size
	 * @return
	 */
	public static Set<List<String>> computeMHS(Set<List<String>> sourceSets, int mhsLimit,
			boolean prunePerProbability) {
		HashMap<Integer, String> intToStrMap = new HashMap<>();
		HashMap<String, Integer> strToIntMap = new HashMap<>();
		Integer elemIdNum = new Integer(1);
		Set<Set<Integer>> faultCombinationAboveThresholdInt = new HashSet<Set<Integer>>();

		// 1.1 turn string elements in sourceSets to unique numbers
		// System.out.println("source sets:");
		boolean multipleSets = false;
		clearSourceSetSb();
		for (List<String> curSet : sourceSets) {
			// System.out.println("source cur set: " + curSet);
			if (multipleSets) {
				newline();
			}
			for (String curElem : curSet) {
				// update ivcElemName
				// String curElemName = updateElemName(curElem);
				String curElemName = curElem;
				// if the string is already in the map
				// write its integer id number
				if (strToIntMap.containsKey(curElemName)) {
					write(strToIntMap.get(curElemName) + " ");
				}
				// if the string is not yet in the map
				// assign it an unique integer id number
				// update both maps
				else {
					intToStrMap.putIfAbsent(elemIdNum, curElemName);
					strToIntMap.putIfAbsent(curElemName, elemIdNum);
					write(elemIdNum + " ");
					elemIdNum++;
				}
			}
			multipleSets = true;
		}

		// if prunePerProbability
		// then convert all strings from faultCombinationAboveThresholdStrs to numbers as well
		if (prunePerProbability) {
			Set<Integer> curSetInt = new HashSet<Integer>();
			for (Set<String> curSet : AddFaultsToNodeVisitor.faultCombinationAboveThresholdStrs) {
				for (String curElem : curSet) {
					// update ivcElemName
					// String curElemName = updateElemName(curElem);
					String curElemName = curElem;
					// if the string is already in the map
					// get its integer id number to the set
					if (strToIntMap.containsKey(curElemName)) {
						curSetInt.add(strToIntMap.get(curElemName));
					}
					// if the string is not yet in the map
					// assign it an unique integer id number
					// update both maps
					else {
						intToStrMap.putIfAbsent(elemIdNum, curElemName);
						strToIntMap.putIfAbsent(curElemName, elemIdNum);
						curSetInt.add(elemIdNum);
						elemIdNum++;
					}
				}
			}
			faultCombinationAboveThresholdInt.add(curSetInt);
		}

		Set<List<String>> destSets = new HashSet<List<String>>();
		try {
			// 1.2 write elem numbers to file
			File sourceSetFile = File.createTempFile("sourceSets_", ".dat");
			BufferedWriter bw = new BufferedWriter(new FileWriter(sourceSetFile));
			bw.write(sourceSetToStr());
			bw.close();
			clearSourceSetSb();

			// get mhs algorithm executable path
			String MHSalgPath = findMHSalg().getAbsolutePath();

			// 1.3 write MHS numbers to file
			File destFile = File.createTempFile("mhs_", ".dat");
			ProcessBuilder pb;
			// 1.4 invoke MHS algorithm to generate minimal hitting sets from the numbered source sets
			String mhsLimitStr = ((mhsLimit <= 0) ? "0" : String.valueOf(mhsLimit));
			pb = new ProcessBuilder(MHSalgPath, sourceSetFile.getAbsolutePath(), destFile.getAbsolutePath(), "-a",
					"pmmcs", "-c", mhsLimitStr); // Note: mhsLimitStr=="0" means no limit

			// inherit IO
			// pb.inheritIO();
			Process process = pb.start();
			process.waitFor();

			// 1.5 read in numbered minimal hitting sets
			BufferedReader br = new BufferedReader(new FileReader(destFile));
			String sCurrentLine;

			// System.out.println("dest sets:");
			// 1.6 convert numbered minimal hitting set to minimal hitting set in strings
			while ((sCurrentLine = br.readLine()) != null) {
				String[] curMHSElemNums = sCurrentLine.split(" ");
				List<String> curMHSElemStrs = new ArrayList<>();
				List<Integer> curMHSElemInt = new ArrayList<>();

				for (int i = 0; i < curMHSElemNums.length; i++) {
					Integer mhsElemNum = new Integer(curMHSElemNums[i]);
					String mhsElemStr = intToStrMap.get(mhsElemNum);
					curMHSElemStrs.add(mhsElemStr);
					curMHSElemInt.add(mhsElemNum);
				}

				boolean addCurMHSElemStrs = false;
				// if prunePerProbability
				// add to destSets if curMHSElemInt is a subset of faultCombinationsAboveThreshold
				if (prunePerProbability) {
					for (Set<Integer> curSetInt : faultCombinationAboveThresholdInt) {
						if (curSetInt.containsAll(curMHSElemInt)) {
							addCurMHSElemStrs = true;
							break;
						}
					}
				} else {
					addCurMHSElemStrs = true;
				}
				if (addCurMHSElemStrs) {
					destSets.add(curMHSElemStrs);
				}
				// System.out.println("dest current set: " + curMHSElemStrs);
			}
			br.close();
			ensureDeleted(sourceSetFile);
			ensureDeleted(destFile);
		} catch (Exception e) {
			Dialog.showError("File I/O or MHSalg algorithm execution exception", e.getMessage());
			e.printStackTrace();
		}
		return destSets;
	}

	/**
	 * Clear static vars
	 */
	public static void clearLocals() {
		elemIdMap.clear();
		clearSourceSetSb();
		varIndex = 0;
	}

	/**
	 * Update name of element for ocaml soteria model
	 * @param name of element
	 * @return updated name
	 */
	public static String updateElemName(String name) {
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
			// start with lower case letter for names as otherwise ocaml flags error for variable declarations
			updatedName = Character.toLowerCase(updatedName.charAt(0)) + updatedName.substring(1);
			// add a new entry into the map
			elemIdMap.put(originalNameId, new UniqueID(updatedName));
		}

		return updatedName;
	}

	/**
	 * Create unique name from string and static counter var.
	 * @param name original name
	 * @return unique name
	 */
	public static String createUniqueElemName(String name) {
		String updatedName = name + "_" + varIndex;
		while (elemIdMap.containsValue(new UniqueID(updatedName))) {
			varIndex++;
			updatedName = name + "_" + varIndex;
		}
		elemIdMap.put(new UniqueID(name), new UniqueID(updatedName));
		return updatedName;
	}

}
