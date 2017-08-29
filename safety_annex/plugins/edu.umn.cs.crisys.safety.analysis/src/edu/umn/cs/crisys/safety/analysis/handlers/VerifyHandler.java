package edu.umn.cs.crisys.safety.analysis.handlers;

import java.io.PrintWriter;
import java.io.StringWriter;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.osate.aadl2.AnnexSubclause;
import org.osate.aadl2.ComponentImplementation;
import org.osate.aadl2.Element;
import org.osate.aadl2.instance.SystemInstance;
import org.osate.aadl2.instantiation.InstantiateModel;
import org.osate.annexsupport.AnnexUtil;
import org.osate.ui.dialogs.Dialog;

import com.rockwellcollins.atc.agree.agree.AgreePackage;
import com.rockwellcollins.atc.agree.analysis.Activator;
import com.rockwellcollins.atc.agree.analysis.AgreeException;
import edu.umn.cs.crisys.safety.analysis.SafetyException;
import edu.umn.cs.crisys.safety.analysis.transform.TransformAgree;
import edu.umn.cs.crisys.safety.safety.SafetyPackage;

public class VerifyHandler extends AadlHandler {

	private static boolean transformFlag = false;
	private static SafetyPackage safetyPackage = null;
	
    /*
     * (non-Javadoc)
     * @see edu.umn.cs.crisys.safety.analysis.handlers.AadlHandler#runJob(org.osate.aadl2.Element, org.eclipse.core.runtime.IProgressMonitor)
     *
     * Check for component implementation, two annexes in that implementation, save
     * the safety package instance, and set the transform agree flag. 
     */
	@Override
	protected final IStatus runJob(Element root, IProgressMonitor monitor) {
		
		// Make sure selected component implementation
		if (!(root instanceof ComponentImplementation)) {
			return new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Must select an AADL Component Implementation");
		}

		// Check for annexes here
		ComponentImplementation ci = (ComponentImplementation) root;
		 EList<AnnexSubclause> annexSubClauses = AnnexUtil.getAllAnnexSubclauses(ci,
	                AgreePackage.eINSTANCE.getAgreeContractSubclause());
	        
	     annexSubClauses.addAll(AnnexUtil.getAllAnnexSubclauses(ci,
	                SafetyPackage.eINSTANCE.getSafetyContractSubclause()));
	     
	     // If no annexes found, output error. 
	     if (annexSubClauses.size() == 0) {
	         throw new SafetyException(
	                    "There is no AGREE annex or Safety annex in the '" + ci.getName() + "' system type.");
	     }
	     
	     // If only one annex found, output error. 
	     if(annexSubClauses.size() == 1){
	    	 throw new SafetyException(
	        			"There must be both an AGREE annex and a Safety annex in the '" + ci.getName() + "' system type.");
	     }
		
		// Set transform flag to true
	    // Set SafetyPackage to the current instance of the package
		try {
			TransformAgree.setTransformFlag(true);
            return setSafetyPackage(SafetyPackage.eINSTANCE);
        
		} catch (Throwable e) {
            String messages = getNestedMessages(e);
            return new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, messages, e);
        }
		
		
	}




	/*
	 * (non-Javadoc)
	 * @see edu.umn.cs.crisys.safety.analysis.handlers.AadlHandler#getJobName()
	 * 
	 * Output job name (will see it at the bottom of Osate2 after selected)
	 */
	@Override
	protected String getJobName() {
		return "Safety Analysis results";
	}
	
	/*
	 * getSysInstance
	 * @param ComponentImplementation ci
	 * @return SystemInstance the instantiated model
	 * Builds the instance model file from the component implementation.
	 */
	public SystemInstance getSysInstance(ComponentImplementation ci) {
		try {
			return InstantiateModel.buildInstanceModelFile(ci);
		} catch (Exception e) {
			Dialog.showError("Model Instantiate", "Error while re-instantiating the model: " + e.getMessage());
			throw new AgreeException("Error Instantiating model");
		}
	}
	
	
	/*
	 * setSafetyPackage
	 * @param SafetyPackage sp
	 * @return IStatus
	 * If the safety package is not null, then we set the package and return OK
	 * else return ERROR.
	 */
	private IStatus setSafetyPackage(SafetyPackage sp){
		if(sp != null){
			safetyPackage = sp;
			return Status.OK_STATUS;
		} else{
			return Status.CANCEL_STATUS;
		}
	}
	
	/*
	 * getSafetyPackage
	 * @param none
	 * @return SafetyPackage
	 * Returns the current instance of the safety package. 
	 */
	public static SafetyPackage getSafetyPackage(){
		return safetyPackage;
	}
	
	/*
	 * getNestedMessages
	 * @param Throwable : exception
	 * @return String : exception message
	 */
    protected String getNestedMessages(Throwable e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        while (e != null) {
            if (e.getMessage() != null && !e.getMessage().isEmpty()) {
                pw.println(e.getMessage());
            }
            e = e.getCause();
        }
        pw.close();
        return sw.toString();
    }

}
