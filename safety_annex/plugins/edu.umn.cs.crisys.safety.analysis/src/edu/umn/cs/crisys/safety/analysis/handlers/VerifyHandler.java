package edu.umn.cs.crisys.safety.analysis.handlers;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.MenuItem;
import org.osate.aadl2.Element;

import edu.umn.cs.crisys.safety.analysis.ast.visitors.AddFaultsToNodeVisitor;
import edu.umn.cs.crisys.safety.analysis.transform.AddFaultsToAgree;

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
		return Status.OK_STATUS;
	}

	@Override
	public Object execute(ExecutionEvent event) {
		Event selEvent = (Event) event.getTrigger();
		MenuItem item = (MenuItem) selEvent.widget;
		AddFaultsToAgree.setTransformFlag(item);
		// clear static variables before each run
		AddFaultsToNodeVisitor.init();
		return null;
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
