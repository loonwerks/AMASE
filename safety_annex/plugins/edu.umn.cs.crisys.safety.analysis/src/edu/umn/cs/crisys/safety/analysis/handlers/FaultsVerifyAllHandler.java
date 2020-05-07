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
import org.osate.aadl2.AnnexSubclause;
import org.osate.aadl2.Classifier;
import org.osate.aadl2.impl.ComponentImplementationImpl;
import org.osate.aadl2.impl.DefaultAnnexSubclauseImpl;

import com.rockwellcollins.atc.agree.analysis.handlers.VerifyAllHandler;

import edu.umn.cs.crisys.safety.analysis.SafetyException;
import edu.umn.cs.crisys.safety.analysis.ast.visitors.AddFaultsToNodeVisitor;
import edu.umn.cs.crisys.safety.analysis.transform.AddFaultsToAgree;
import edu.umn.cs.crisys.safety.safety.AnalysisStatement;
import edu.umn.cs.crisys.safety.safety.ProbabilityBehavior;
import edu.umn.cs.crisys.safety.safety.SafetyContract;
import edu.umn.cs.crisys.safety.safety.SpecStatement;
import edu.umn.cs.crisys.safety.safety.impl.SafetyContractSubclauseImpl;

public class FaultsVerifyAllHandler extends VerifyAllHandler {

	private static MenuItem item;

	@Override
	public Object execute(ExecutionEvent event) {
		AddFaultsToAgree.resetStaticVars();
		Event selEvent = (Event) event.getTrigger();
		item = (MenuItem) selEvent.widget;
		AddFaultsToAgree.setTransformFlag(item);
		// clear static variables before each run
		AddFaultsToNodeVisitor.init();
		if (isProbabilisticAnalysis()) {
			new SafetyException("Probabilistic behavior cannot be analyzed using "
					+ "this compositional approach. Either choose monolithic "
					+ "analysis or compositionally generate MinCutSets.");
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
		return "Fault analysis: compositional";
	}

	private boolean isProbabilisticAnalysis() {
		List<Classifier> classifiers = getClassifiers();
		if (classifiers == null) {
			return false;
		}
		// This is a partial fix on the problem:
		// If multiple implementations and none have max n behavior,
		// then error out. If any impl have max n, run analysis.
		// Doesn't fix the problem, only partially.
		// Need to access the implementation selected for analysis
		// and check that spec stmt. To do this is taking more time than
		// what priority allows for.
		// VerifyHandler has methods that perform this type of check, but to
		// implement them, need access to Element root. This access is granted in
		// runJob method which will need to be overridden from VerifyHandler.
		// TODO: Finish addressing this issue.
		for (Classifier cl : classifiers) {
			// Get impl of this level
			if (cl instanceof ComponentImplementationImpl) {
				List<AnnexSubclause> asList = cl.getOwnedAnnexSubclauses();
				for (AnnexSubclause as : asList) {
					if (as.getName().equalsIgnoreCase("safety")) {
						if (as instanceof DefaultAnnexSubclauseImpl) {
							DefaultAnnexSubclauseImpl defaultAnnex = (DefaultAnnexSubclauseImpl) as;
							SafetyContractSubclauseImpl safetyAnnex = (SafetyContractSubclauseImpl) defaultAnnex
									.getParsedAnnexSubclause();

							// Get analysis stmt
							List<SpecStatement> specs = ((SafetyContract) safetyAnnex.getContract()).getSpecs();
							for (SpecStatement spec : specs) {
								if (spec instanceof AnalysisStatement) {
									if (!(((AnalysisStatement) spec).getBehavior() instanceof ProbabilityBehavior)) {
										return false;
									}
								}
							}
						}
					}
				}
			}
		}
		return true;
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
