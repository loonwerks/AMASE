/*
 * generated by Xtext
 */
package edu.umn.cs.crisys.safety.validation;
 
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EPackage;

public class AbstractSafetyJavaValidator extends com.rockwellcollins.atc.agree.validation.AgreeJavaValidator {

	@Override
	protected List<EPackage> getEPackages() {
	    List<EPackage> result = new ArrayList<EPackage>();
	    result.add(edu.umn.cs.crisys.safety.safety.SafetyPackage.eINSTANCE);
	    result.add(EPackage.Registry.INSTANCE.getEPackage("http://aadl.info/AADL/2.0"));
		return result;
	}

}
