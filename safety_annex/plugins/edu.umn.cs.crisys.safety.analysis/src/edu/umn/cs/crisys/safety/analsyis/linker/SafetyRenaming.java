package edu.umn.cs.crisys.safety.analsyis.linker;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;

import com.rockwellcollins.atc.agree.analysis.AgreeRenaming;

import jkind.api.results.Renaming;

public class SafetyRenaming extends Renaming{

	AgreeRenaming agreeRenaming; 
	Map<String, EObject> agreeRefMap;
	Map<String, EObject> tcgRefMap = new HashMap<>();
	HashMap<String, String> testPropNameToLustreProp = new HashMap<>();
	HashMap<String, String> testPropNameToTestedExpr = new HashMap<>();
	static final boolean Debug = true;
	
	public SafetyRenaming(AgreeRenaming agreeRenaming, Map<String, EObject> refMap) {
		this.agreeRenaming = agreeRenaming;
		this.agreeRefMap = refMap;
	}
	
	
	
	public void addRenaming(String testPropName, String lustrePropName, String testedExprString) {
		testPropNameToLustreProp.put(testPropName, lustrePropName);
		testPropNameToTestedExpr.put(testPropName, testedExprString);
	
		// for tcgRefMap
		String lustrePropKey = agreeRenaming.rename(lustrePropName);
		EObject agreeRefMapVal = agreeRefMap.get(lustrePropKey);
		tcgRefMap.put(rename(testPropName), agreeRefMapVal);
	}

	public boolean isPropertyName(String name) { 
		return testPropNameToLustreProp.containsKey(name); 
	}
	
	public String agreeName(String tcgName) {
		String lustrePropName = testPropNameToLustreProp.get(tcgName);
		String agreePropName = agreeRenaming.rename(lustrePropName);
		return agreePropName;
	}

	public String conditionName(String tcgName) {
		return testPropNameToTestedExpr.get(tcgName);
	}
	@Override
	public String rename(String original) {

		String result = null; 
 
//		if (isPropertyName(original)) {
//			// Only want the test case number
//			String testNumber = original.replace(GenerateUfcObligationsVisitor.TRAP_PROP_PREFIX, "");
//			result = "Test Case " + testNumber + " ["+ agreeName(original) + "] for condition [" + conditionName(original) +  "]";
//			
//		} 
//		else {
//			result = agreeRenaming.rename(original);
//		}
		
		// Removed Ufc obligation call - will be making our own changes to the program eventually. 
		// No need for this here at this time.
		result = agreeRenaming.rename(original);
		return result;
	}

	public EObject mapTcgToEObject(String tcgName) {
		return tcgRefMap.get(tcgName);
	}
	
	public Map<String, EObject> getRefMap() {
		return tcgRefMap;
	}

}
