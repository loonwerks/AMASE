package edu.umn.cs.crisys.safety.analysis.handlers;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.ui.handlers.IHandlerActivation;
import org.eclipse.ui.handlers.IHandlerService;
import org.osate.aadl2.AnnexSubclause;
import org.osate.aadl2.ComponentImplementation;
import org.osate.aadl2.ComponentType;
import org.osate.aadl2.Element;
import org.osate.aadl2.instance.SystemInstance;
import org.osate.aadl2.instantiation.InstantiateModel;
import org.osate.annexsupport.AnnexUtil;
import org.osate.ui.dialogs.Dialog;

import com.rockwellcollins.atc.agree.agree.AgreePackage;
import com.rockwellcollins.atc.agree.analysis.Activator;
import com.rockwellcollins.atc.agree.analysis.AgreeException;
import com.rockwellcollins.atc.agree.analysis.AgreeUtils;
import com.rockwellcollins.atc.agree.analysis.views.AgreeResultsLinker;

import edu.umn.cs.crisys.safety.analsyis.linker.SafetyLinkerFactory;
import edu.umn.cs.crisys.safety.analysis.SafetyException;
import edu.umn.cs.crisys.safety.safety.SafetyPackage;
import jkind.api.results.AnalysisResult;
import jkind.api.results.JKindResult;

public class VerifyHandler extends AadlHandler {

	protected Queue<JKindResult> queue = new ArrayDeque<>();
    protected AgreeResultsLinker linker = new AgreeResultsLinker();
	protected IHandlerService handlerService;
    protected AtomicReference<IProgressMonitor> monitorRef = new AtomicReference<>();
	protected boolean Debug = false;
	private IHandlerActivation terminateActivation;
	private static boolean transformFlag = false;
	
	public boolean isRecursive() {
		return false;
	}

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
	
	@Override
	protected final IStatus runJob(Element root, IProgressMonitor monitor) {
		
		handlerService = (IHandlerService) getWindow().getService(IHandlerService.class);

		if (!(root instanceof ComponentImplementation)) {
			return new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Must select an AADL Component Implementation");
		}
        
		if (isRecursive() && AgreeUtils.usingKind2()){
            throw new AgreeException("Kind2 only supports monolithic verification");
        }

//		SystemInstance si = getSysInstance((ComponentImplementation)root);
//		ComponentType sysType = AgreeUtils.getInstanceType(si);
		
		// Check for annexes here
		ComponentImplementation ci = (ComponentImplementation) root;
		 EList<AnnexSubclause> annexSubClauses = AnnexUtil.getAllAnnexSubclauses(ci,
	                AgreePackage.eINSTANCE.getAgreeContractSubclause());
	        
	     annexSubClauses.addAll(AnnexUtil.getAllAnnexSubclauses(ci,
	                SafetyPackage.eINSTANCE.getSafetyContractSubclause()));
	     
	     if (annexSubClauses.size() == 0) {
	         throw new SafetyException(
	                    "There is no AGREE annex or Safety annex in the '" + ci.getName() + "' system type.");
	     }
	        
	     if(annexSubClauses.size() == 1){
	    	 throw new SafetyException(
	        			"There must be both an AGREE annex and a Safety annex in the '" + ci.getName() + "' system type.");
	     }
		
		
		try {
			
            return setTransformFlag(root, monitor);
        
		} catch (Throwable e) {
            String messages = getNestedMessages(e);
            return new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, messages, e);
        }
	}



	private IStatus setTransformFlag(Element root, IProgressMonitor monitor) {
		
		transformFlag = true;
		
		return Status.OK_STATUS;
	}

	@Override
	protected String getJobName() {
		return "Safety Analysis results";
	}

	public SystemInstance getSysInstance(ComponentImplementation ci) {
		try {
			return InstantiateModel.buildInstanceModelFile(ci);
		} catch (Exception e) {
			Dialog.showError("Model Instantiate", "Error while re-instantiating the model: " + e.getMessage());
			throw new AgreeException("Error Instantiating model");
		}
	}
	
	public static boolean getTransformFlag(){
		return transformFlag;
	}
	
	public static void setTransformFlagFalse(){
		transformFlag = false;
	}
	

}
