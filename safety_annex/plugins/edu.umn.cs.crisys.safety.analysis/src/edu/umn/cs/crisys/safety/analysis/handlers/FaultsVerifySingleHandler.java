package edu.umn.cs.crisys.safety.analysis.handlers;

import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.editor.utils.EditorUtils;
import org.osate.aadl2.AadlPackage;
import org.osate.aadl2.Classifier;

import com.rockwellcollins.atc.agree.analysis.handlers.VerifySingleHandler;

import edu.umn.cs.crisys.safety.analysis.SafetyException;
import edu.umn.cs.crisys.safety.analysis.ast.visitors.AddFaultsToNodeVisitor;
import edu.umn.cs.crisys.safety.analysis.transform.AddFaultsToAgree;
import edu.umn.cs.crisys.safety.util.SafetyUtil;

public class FaultsVerifySingleHandler extends VerifySingleHandler {

	private static MenuItem item;

	@Override
	public Object execute(ExecutionEvent event) {
		AddFaultsToAgree.resetStaticVars();
		Event selEvent = (Event) event.getTrigger();
		item = (MenuItem) selEvent.widget;
		AddFaultsToAgree.setTransformFlag(item);
		// clear static variables before each run
		AddFaultsToNodeVisitor.init();
		if (!SafetyUtil.containsSafetyAnnex(getClassifiers())) {
			new SafetyException("A safety annex in the implementation is required to run the fault analysis.");
			return Status.CANCEL_STATUS;
		}
		return super.execute(event);
	}

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

	private List<Classifier> getClassifiers() {
		XtextEditor xtextEditor = EditorUtils.getActiveXtextEditor();
		if (xtextEditor == null) {
			return null;
		}
		EObject original = xtextEditor.getDocument().readOnly(resource -> resource.getContents().get(0));
		AadlPackage aadlPackage = null;
		if (original instanceof AadlPackage) {
			aadlPackage = (AadlPackage) original;
		}
		if (aadlPackage == null) {
			return null;
		}
		return aadlPackage.getOwnedPublicSection().getOwnedClassifiers();
	}
}
