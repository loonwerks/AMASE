/*
 * generated by Xtext
 */
package edu.umn.cs.crisys.safety;

/**
 * Initialization support for running Xtext languages 
 * without equinox extension registry
 */
public class SafetyStandaloneSetup extends SafetyStandaloneSetupGenerated{

	public static void doSetup() {
		new SafetyStandaloneSetup().createInjectorAndDoEMFRegistration();
	}
}

