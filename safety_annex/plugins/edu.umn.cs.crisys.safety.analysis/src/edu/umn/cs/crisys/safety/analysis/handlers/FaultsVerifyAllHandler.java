package edu.umn.cs.crisys.safety.analysis.handlers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.IHandlerActivation;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.editor.utils.EditorUtils;
import org.osate.aadl2.AadlPackage;
import org.osate.aadl2.AnnexSubclause;
import org.osate.aadl2.Classifier;
import org.osate.aadl2.Element;
import org.osate.aadl2.impl.ComponentImplementationImpl;
import org.osate.aadl2.impl.DefaultAnnexSubclauseImpl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.rockwellcollins.atc.agree.agree.GuaranteeStatement;
import com.rockwellcollins.atc.agree.analysis.Activator;
import com.rockwellcollins.atc.agree.analysis.AgreeRenaming;
import com.rockwellcollins.atc.agree.analysis.ConsistencyResult;
import com.rockwellcollins.atc.agree.analysis.handlers.RerunHandler;
import com.rockwellcollins.atc.agree.analysis.handlers.TerminateHandler;
import com.rockwellcollins.atc.agree.analysis.handlers.VerifyAllHandler;
import com.rockwellcollins.atc.agree.analysis.preferences.PreferenceConstants;
import com.rockwellcollins.atc.agree.analysis.preferences.PreferencesUtil;
import com.rockwellcollins.atc.agree.analysis.saving.AgreeFileUtil;
import com.rockwellcollins.atc.agree.analysis.views.AgreeResultsLinker;

import edu.umn.cs.crisys.safety.analysis.SafetyException;
import edu.umn.cs.crisys.safety.analysis.ast.visitors.AddFaultDriverGuardAssertionVisitor;
import edu.umn.cs.crisys.safety.analysis.ast.visitors.AddFaultDriverVisitor;
import edu.umn.cs.crisys.safety.analysis.ast.visitors.AddFaultsToNodeVisitor;
import edu.umn.cs.crisys.safety.analysis.ast.visitors.AddPairwiseFaultDriverWitnesses;
import edu.umn.cs.crisys.safety.analysis.transform.AddFaultsToAgree;
import edu.umn.cs.crisys.safety.analysis.views.SafetyResultsView;
import edu.umn.cs.crisys.safety.safety.AnalysisStatement;
import edu.umn.cs.crisys.safety.safety.ProbabilityBehavior;
import edu.umn.cs.crisys.safety.safety.SafetyContract;
import edu.umn.cs.crisys.safety.safety.SpecStatement;
import edu.umn.cs.crisys.safety.safety.impl.SafetyContractSubclauseImpl;
import edu.umn.cs.crisys.safety.util.SafetyUtil;
import jkind.JKindException;
import jkind.api.JKindApi;
import jkind.api.JRealizabilityApi;
import jkind.api.KindApi;
import jkind.api.results.AnalysisResult;
import jkind.api.results.CompositeAnalysisResult;
import jkind.api.results.JKindResult;
import jkind.api.results.JRealizabilityResult;
import jkind.api.results.PropertyResult;
import jkind.lustre.Program;
import jkind.results.InvalidProperty;
import jkind.results.ValidProperty;

public class FaultsVerifyAllHandler extends VerifyAllHandler {

	private static MenuItem item;
	private static final String RERUN_ID = "com.rockwellcollins.atc.agree.analysis.commands.rerunAgree";
	private IHandlerActivation terminateActivation;
	private IHandlerActivation terminateAllActivation;
	private IHandlerService handlerService;
	private Map<String, String> rerunAdviceMap = new HashMap<>();
	private int adviceCount = 0;

	protected Map<AnalysisResult, Map<String, Map<String, String>>> pairwiseFaultDriverProperties = new HashMap<>();

	@Override
	public Object execute(ExecutionEvent event) {
		AddFaultsToAgree.resetStaticVars();
		Event selEvent = (Event) event.getTrigger();
		item = (MenuItem) selEvent.widget;
		AddFaultsToAgree.setTransformFlag(item);
		// clear static variables before each run
		AddFaultsToNodeVisitor.init();
		pairwiseFaultDriverProperties.clear();
		if (!SafetyUtil.containsSafetyAnnex(getClassifiers())) {
			new SafetyException("A safety annex in the implementation is required to run the fault analysis.");
			return Status.CANCEL_STATUS;
		}
		if (isProbabilisticAnalysis()) {
			new SafetyException("Probabilistic behavior cannot be analyzed using "
					+ "this compositional approach. You will have to compositionally generate minimal cut sets.");
			return Status.CANCEL_STATUS;
		}
		return super.execute(event);
	}

	protected List<JKindResult> getChildContractResults(JKindResult result) {
		AnalysisResult parent = result.getParent();
		List<JKindResult> children = Lists.newArrayList();
		if (parent instanceof CompositeAnalysisResult) {
			((CompositeAnalysisResult) parent).getChildren()
					.stream()
					.filter(r -> r instanceof CompositeAnalysisResult)
					.forEach(c -> children.addAll(((CompositeAnalysisResult) c).getChildren()
							.stream()
							// WARNING: the string literal "Contract Guarantees" in the line below needs to match that in
							// com.rockwellcollins.atc.agree.analysis.handlers.VerifyHandler#wrapVerificationResult(ComponentInstance, CompositeAnalysisResult)
							.filter(r -> (r instanceof JKindResult && "Contract Guarantees".equals(r.getName())))
							.map(JKindResult.class::cast)
							.collect(Collectors.toList())));
		}
		return children;
	}

	protected Program doFaultPropagationInjection(JKindResult result, Program program) {
		List<JKindResult> childVerifications = getChildContractResults(result);
		// WARNING: the string literal "Contract Guarantees" in the line below needs to match that in
		// com.rockwellcollins.atc.agree.analysis.VerifyHandler#wrapVerificationResult(ComponentInstance, CompositeAnalysisResult)
		if ("Contract Guarantees".equals(result.getName())) {
			for (JKindResult childResult : childVerifications) {
				AgreeRenaming childRenaming = (AgreeRenaming) linker.getRenaming(childResult);
				for (PropertyResult propertyResult : childResult.getPropertyResults()) {
					// WARNING: The string literal "__GUARANTEE" comes from
					// com.rockwellcollins.atc.agree.analysis.translation.LustreAstBuilder
					// where it is protected and we need to duplicate the literal here.
					if (propertyResult.getProperty() instanceof InvalidProperty && childRenaming.getRefMap()
							.get(propertyResult.getProperty().getName()) instanceof GuaranteeStatement) {
						String guaranteeName = propertyResult.getProperty().getName();
						String lustreVarName = childRenaming.getLustreNameFromAgreeVar(guaranteeName);
						// WARNING: Here we assume that the subnode id of interest is named as given below.
						// We need to introduce this literal "_TOP__" here because the computation is hidden in AGREE
						// literals in com.rockwellcollins.atc.agree.analysis.LustreAstBuilder#getAssumeGuaranteeLustreProgram(AgreeProgram)
						// WARNING: the string literal "Verification for " in the line below needs to match that in
						// com.rockwellcollins.atc.agree.analysis.handlers.VerifyHandler#runJob(Element, IProgressMonitor) and
						// com.rockwellcollins.atc.agree.analysis.handlers.VerifyHandler#buildAnalysisResult(String, ComponentInstance)
						String subnodeName = "_TOP__"
								+ childResult.getParent().getName().replaceFirst("Verification for ", "");
						// TODO: The string concatenation is also done in the AddFaultDriverVisitor; unify them
						program = new AddFaultDriverVisitor(subnodeName, lustreVarName).visit(program);
					} else if (propertyResult.getProperty() instanceof ValidProperty && propertyResult.getProperty()
							.getName()
							.contains(childRenaming
									.forceRename(AddPairwiseFaultDriverWitnesses.FAULT_DRIVER_PAIR_WITNESS_BASENAME))
							&& pairwiseFaultDriverProperties.containsKey(childResult)
							&& pairwiseFaultDriverProperties.get(childResult).containsKey(propertyResult.getName())
					// Need to also check that both the guarantees referred to by this pairwise check are
					// invalidated and have corresponding fault drivers that are present in this verification
							&& pairwiseFaultDriverProperties.get(childResult)
									.get(propertyResult.getName())
									.entrySet()
									.stream()
									.allMatch(e -> {
										PropertyResult p = childResult
												.getPropertyResult(childRenaming.rename(e.getKey()));
										return (p != null) ? p.getProperty() instanceof InvalidProperty : false;
									})
					) {
						program = new AddFaultDriverGuardAssertionVisitor(program.main,
								pairwiseFaultDriverProperties.get(childResult).get(propertyResult.getName()).values().stream().collect(Collectors.toList()))
										.visit(program);
					}
				}
			}

			/* If not the top analysis, that is the parent of the composite parent of the composite parent of this result is not null */
			if (result.getParent().getParent().getParent() != null) {
				Map<PropertyResult, String> accumulatedGuarantees = Maps.newLinkedHashMap();
				for (PropertyResult propertyResult : result.getPropertyResults()) {
					AgreeRenaming renaming = (AgreeRenaming) linker.getRenaming(result);
					if (renaming.getRefMap()
							.get(propertyResult.getName()) instanceof GuaranteeStatement) {
						String guaranteeName = propertyResult.getName();
						String lustreVarName = renaming.getLustreNameFromAgreeVar(guaranteeName);
						accumulatedGuarantees.put(propertyResult, lustreVarName);
					}
				}
				AddPairwiseFaultDriverWitnesses pairwiseFaultVisitor = new AddPairwiseFaultDriverWitnesses(
						Lists.newArrayList(accumulatedGuarantees.values()));
				program = pairwiseFaultVisitor.visit(program);
				result.addProperties(pairwiseFaultVisitor.getProperties());
				// WARNING: the string literal "Verification for " in the line below needs to match that in
				// com.rockwellcollins.atc.agree.analysis.handlers.VerifyHandler#runJob(Element, IProgressMonitor) and
				// com.rockwellcollins.atc.agree.analysis.handlers.VerifyHandler#buildAnalysisResult(String, ComponentInstance)
				// TODO: the concatenation of nodeName with fault driver is done elsewhere too, unify
				String nodeName = "_TOP__" + result.getParent().getName().replaceFirst("Verification for ", "");
				pairwiseFaultDriverProperties.put(result,
						pairwiseFaultVisitor.getPairwiseWitnesses()
								.entrySet()
								.stream()
								.collect(Collectors.toMap(
										e -> ((AgreeRenaming) linker.getRenaming(result)).forceRename(e.getKey()),
										e -> e.getValue()
												.stream()
												.collect(Collectors.toMap(id -> id, id -> nodeName
														+ AddFaultDriverVisitor.getFaultDriverId(id))))));
			}
		}

		return program;
	}

	@Override
	protected IStatus doAnalysis(final Element root, final IProgressMonitor globalMonitor) {

		Thread analysisThread = new Thread() {
			@Override
			public void run() {

				// Record the analysis start time and get model hashcode for
				// saving to property analysis log, if necessary
				String modelHash = "";
				long startTime = 0;
				if (Activator.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.PREF_PROP_LOG)) {
					try {
						modelHash = AgreeFileUtil.getModelHashcode(root);
						startTime = System.currentTimeMillis();
					} catch (Exception e) {
						System.out.println(e.getMessage());
						return;
					}
				}

				activateTerminateHandlers(globalMonitor);
				KindApi api = PreferencesUtil.getKindApi();
				KindApi consistApi = PreferencesUtil.getConsistencyApi();
				JRealizabilityApi realApi = PreferencesUtil.getJRealizabilityApi();

				// Due to the way the queue is constructed in traversal,
				// reversing the queue will result in subcomponent instances
				// being analyzed prior to their enclosing component instance.
				// Reverse the queue using a stack.
				{
					Stack<JKindResult> stack = new Stack<>();
					while (!queue.isEmpty()) {
						stack.push(queue.remove());
					}
					while (!stack.empty()) {
						queue.add(stack.pop());
					}
				}

				while (!queue.isEmpty() && !globalMonitor.isCanceled()) {
					JKindResult result = queue.peek();
					NullProgressMonitor subMonitor = new NullProgressMonitor();
					monitorRef.set(subMonitor);

					Program program = doFaultPropagationInjection(result, linker.getProgram(result));
					linker.setProgram(result, program);

					if (api instanceof JKindApi) {
						String resultName = result.getName();
						String adviceFileName = rerunAdviceMap.get(resultName);
						if (adviceFileName == null) {
							adviceFileName = "agree_advice" + adviceCount++;
							rerunAdviceMap.put(resultName, adviceFileName);
						} else {
							((JKindApi) api).setReadAdviceFile(adviceFileName);
						}
						((JKindApi) api).setWriteAdviceFile(adviceFileName);
					}

					try {
						if (result instanceof ConsistencyResult) {
							consistApi.execute(program, result, subMonitor);
						} else if (result instanceof JRealizabilityResult) {
							realApi.execute(program, (JRealizabilityResult) result, subMonitor);
						} else {
							api.execute(program, result, subMonitor);
						}
					} catch (JKindException e) {

						System.out.println("******** JKindException Text ********");
						e.printStackTrace(System.out);
						String errStr = e.getMessage();
						int l = Math.min(errStr.length(), 300);
						System.out.println(e.getMessage().substring(0, l));
						break;
					}

					// Print to property analysis log, if necessary
					if (Activator.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.PREF_PROP_LOG)) {
						AgreeFileUtil.printLog(result, startTime, modelHash);
					}

					queue.remove();
				}

				while (!queue.isEmpty()) {
					queue.remove().cancel();
				}
				AddFaultsToAgree.resetStaticVars();
				deactivateTerminateHandlers();
				enableRerunHandler(root);

			}
		};
		analysisThread.start();
		return Status.OK_STATUS;
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

	private void enableRerunHandler(final Element root) {

		getWindow().getShell().getDisplay().syncExec(() -> {
			IHandlerService handlerService = getHandlerService();
			handlerService.activateHandler(RERUN_ID, new RerunHandler(root, FaultsVerifyAllHandler.this));
		});
	}

	private void activateTerminateHandlers(final IProgressMonitor globalMonitor) {
		getWindow().getShell().getDisplay().syncExec(() -> {
			terminateActivation = handlerService.activateHandler(TERMINATE_ID, new TerminateHandler(monitorRef));
			terminateAllActivation = handlerService.activateHandler(TERMINATE_ALL_ID,
					new TerminateHandler(monitorRef, globalMonitor));
		});
	}

	private void deactivateTerminateHandlers() {
		getWindow().getShell().getDisplay().syncExec(() -> {
			handlerService.deactivateHandler(terminateActivation);
			handlerService.deactivateHandler(terminateAllActivation);
		});
	}

	private IHandlerService getHandlerService() {
		return getWindow().getService(IHandlerService.class);
	}

	@Override
	protected void showView(final AnalysisResult result, final AgreeResultsLinker linker) {
		/*
		 * This command is executed while the xtext document is locked. Thus it must be async
		 * otherwise we can get a deadlock condition if the UI tries to lock the document,
		 * e.g., to pull up hover information.
		 */
		getWindow().getShell().getDisplay().asyncExec(() -> {
			try {
				SafetyResultsView page = (SafetyResultsView) getWindow().getActivePage().showView(SafetyResultsView.ID);
				page.setInput(result, linker);
			} catch (PartInitException e) {
				e.printStackTrace();
			}
		});
	}

	@Override
	protected void clearView() {
		getWindow().getShell().getDisplay().syncExec(() -> {
			try {
				SafetyResultsView page = (SafetyResultsView) getWindow().getActivePage().showView(SafetyResultsView.ID);
				page.setInput(new CompositeAnalysisResult("empty"), null);
			} catch (PartInitException e) {
				e.printStackTrace();
			}
		});
	}

	@Override
	protected SafetyResultsView findView() {
		SafetyResultsView result = null;
		IViewPart part = getWindow().getActivePage().findView(SafetyResultsView.ID);
		if (part instanceof SafetyResultsView) {
			result = (SafetyResultsView) part;
		} else {
			try {
				result = (SafetyResultsView) getWindow().getActivePage().showView(SafetyResultsView.ID);
			} catch (PartInitException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

}
