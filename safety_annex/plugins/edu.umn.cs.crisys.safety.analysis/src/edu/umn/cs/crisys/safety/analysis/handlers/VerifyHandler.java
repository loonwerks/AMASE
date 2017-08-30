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
import org.osate.annexsupport.AnnexUtil;
import com.rockwellcollins.atc.agree.agree.AgreePackage;
import com.rockwellcollins.atc.agree.analysis.Activator;
import edu.umn.cs.crisys.safety.analysis.SafetyException;
import edu.umn.cs.crisys.safety.analysis.transform.TransformAgree;
import edu.umn.cs.crisys.safety.safety.SafetyPackage;

public class VerifyHandler extends AadlHandler {

	private static Element root = null;
	
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

		// Set root
		setRoot(root);
		
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
			return Status.OK_STATUS;
        
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

    /*
     * setRoot
     * @param Element root : This sets the component implementation to the current root Element.
     * @return void
     */
    public static void setRoot(Element root){
    	VerifyHandler.root = root;
    }
    
    /*
     * getRoot
     * @param none
     * @return Element root : This contains the component implementation and will 
     * be used in transformAgree in order to access the safety annex.
     */
    public static Element getRoot(){
    	return VerifyHandler.root;
    }
}
