package edu.umn.cs.crisys.safety.analysis.handlers;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.handlers.IHandlerActivation;
import org.eclipse.ui.handlers.IHandlerService;
import org.osate.aadl2.ComponentImplementation;
import org.osate.aadl2.Element;

import com.rockwellcollins.atc.agree.analysis.Activator;
import com.rockwellcollins.atc.agree.analysis.AgreeException;
import com.rockwellcollins.atc.agree.analysis.AgreeUtils;
import com.rockwellcollins.atc.agree.analysis.views.AgreeResultsLinker;

import edu.umn.cs.crisys.safety.analsyis.linker.SafetyLinkerFactory;
import jkind.api.results.AnalysisResult;
import jkind.api.results.JKindResult;

public class VerifyHandler extends AadlHandler {

	protected Queue<JKindResult> queue = new ArrayDeque<>();
    protected AgreeResultsLinker linker = new AgreeResultsLinker();
	protected IHandlerService handlerService;
    protected AtomicReference<IProgressMonitor> monitorRef = new AtomicReference<>();
	protected boolean Debug = false;
	private IHandlerActivation terminateActivation;
	
	
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

		try {
			SafetyLinkerFactory factory = 
				new SafetyLinkerFactory((ComponentImplementation)root, isMonolithic(), isRecursive()); 
            
            AnalysisResult result = factory.getAnalysisResult();
            linker = factory.getLinker();
            queue = factory.getWorkQueue();
            
            //showView(result, linker);
            return doAnalysis(root, monitor);
        } catch (Throwable e) {
            String messages = getNestedMessages(e);
            return new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, messages, e);
        }
	}



	private boolean isMonolithic() {
		// TODO Auto-generated method stub
		return false;
	}

	private IStatus doAnalysis(Element root, IProgressMonitor monitor) {
		// TODO Auto-generated method stub
		return Status.OK_STATUS;
	}

	@Override
	protected String getJobName() {
		return "Safety Analysis results";
	}

}
