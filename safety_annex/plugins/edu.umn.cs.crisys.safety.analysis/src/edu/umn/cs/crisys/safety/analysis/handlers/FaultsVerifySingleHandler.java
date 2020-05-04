package edu.umn.cs.crisys.safety.analysis.handlers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.xtext.EcoreUtil2;
import org.osate.aadl2.AadlPackage;
import org.osate.aadl2.ComponentImplementation;
import org.osate.aadl2.ComponentType;
import org.osate.aadl2.modelsupport.util.AadlUtil;

import com.rockwellcollins.atc.agree.analysis.handlers.VerifySingleHandler;

import edu.umn.cs.crisys.safety.analysis.ast.visitors.AddFaultsToNodeVisitor;
import edu.umn.cs.crisys.safety.analysis.transform.AddFaultsToAgree;

public class FaultsVerifySingleHandler extends VerifySingleHandler {

	private static MenuItem item;

	@Override
	public Object execute(ExecutionEvent event) {
		Event selEvent = (Event) event.getTrigger();
		item = (MenuItem) selEvent.widget;
		AddFaultsToAgree.setTransformFlag(item);
		// clear static variables before each run
		AddFaultsToNodeVisitor.init();
		return super.execute(event);
	}

//	@Override
//	protected IStatus runJob(Element root, IProgressMonitor monitor) {
//
//		EphemeralImplementationUtil implUtil = new EphemeralImplementationUtil(monitor);
//		Classifier classifier = getOutermostClassifier(root);
//		if (classifier instanceof ComponentImplementation) {
//			ComponentImplementation compImpl = (ComponentImplementation) classifier;
//			List<Subcomponent> subComps = compImpl.getAllSubcomponents();
//		} else {
//
//		}
//
////		try {
////			// Make sure the user selected a component implementation
////			ComponentImplementation ci = getComponentImplementation(root, implUtil);
////
////			SystemInstance si = getSysInstance(ci, implUtil);
////
////
////			return doAnalysis(ci, monitor);
////		} catch (Throwable e) {
////			String messages = getNestedMessages(e);
////			return new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, messages, e);
////		} finally {
////			implUtil.cleanup();
////		}
//		return Status.OK_STATUS;
//	}

	/**
	 * (non-Javadoc)
	 *
	 * @see edu.umn.cs.crisys.safety.analysis.handlers.AadlHandler#getJobName()
	 *
	 * Output job name (will see it at the bottom of Osate2 after selected)
	 */
	@Override
	protected String getJobName() {
		return "Fault analysis: single layer";
	}

	private List<ComponentImplementation> getComponentImplementations(ComponentType ct) {
		List<ComponentImplementation> result = new ArrayList<>();
		AadlPackage pkg = AadlUtil.getContainingPackage(ct);
		for (ComponentImplementation ci : EcoreUtil2.getAllContentsOfType(pkg, ComponentImplementation.class)) {
			if (ci.getType().equals(ct)) {
				result.add(ci);
			}
		}
		return result;
	}

}
