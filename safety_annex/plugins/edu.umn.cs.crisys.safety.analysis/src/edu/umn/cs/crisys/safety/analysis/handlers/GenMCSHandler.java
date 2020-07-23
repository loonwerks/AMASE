package edu.umn.cs.crisys.safety.analysis.handlers;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.IHandlerActivation;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.editor.utils.EditorUtils;
import org.osate.aadl2.AadlPackage;
import org.osate.aadl2.AnnexSubclause;
import org.osate.aadl2.Classifier;
import org.osate.aadl2.ComponentImplementation;
import org.osate.aadl2.ComponentType;
import org.osate.aadl2.Element;
import org.osate.aadl2.NamedElement;
import org.osate.aadl2.instance.ComponentInstance;
import org.osate.aadl2.instance.SystemInstance;
import org.osate.annexsupport.AnnexUtil;
import org.osate.ui.dialogs.Dialog;

import com.rockwellcollins.atc.agree.agree.AgreePackage;
import com.rockwellcollins.atc.agree.analysis.Activator;
import com.rockwellcollins.atc.agree.analysis.AgreeException;
import com.rockwellcollins.atc.agree.analysis.AgreeLayout;
import com.rockwellcollins.atc.agree.analysis.AgreeLogger;
import com.rockwellcollins.atc.agree.analysis.AgreeRenaming;
import com.rockwellcollins.atc.agree.analysis.AgreeUtils;
import com.rockwellcollins.atc.agree.analysis.ConsistencyResult;
import com.rockwellcollins.atc.agree.analysis.EphemeralImplementationUtil;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeASTBuilder;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeProgram;
import com.rockwellcollins.atc.agree.analysis.extentions.AgreeAutomater;
import com.rockwellcollins.atc.agree.analysis.extentions.AgreeAutomaterRegistry;
import com.rockwellcollins.atc.agree.analysis.extentions.ExtensionRegistry;
import com.rockwellcollins.atc.agree.analysis.handlers.RerunHandler;
import com.rockwellcollins.atc.agree.analysis.handlers.TerminateHandler;
import com.rockwellcollins.atc.agree.analysis.handlers.VerifyHandler;
import com.rockwellcollins.atc.agree.analysis.lustre.visitors.RenamingVisitor;
import com.rockwellcollins.atc.agree.analysis.translation.LustreAstBuilder;
import com.rockwellcollins.atc.agree.analysis.translation.LustreContractAstBuilder;
import com.rockwellcollins.atc.agree.analysis.views.AgreeResultsLinker;

import edu.umn.cs.crisys.safety.analysis.SafetyException;
import edu.umn.cs.crisys.safety.analysis.SafetyUtils;
import edu.umn.cs.crisys.safety.analysis.ast.visitors.AddFaultsToNodeVisitor;
import edu.umn.cs.crisys.safety.analysis.ast.visitors.SoteriaFTResolveVisitor;
import edu.umn.cs.crisys.safety.analysis.ast.visitors.SoteriaPrintUtils;
import edu.umn.cs.crisys.safety.analysis.generators.IvcToSoteriaFTGenerator;
import edu.umn.cs.crisys.safety.analysis.preferences.PreferencesUtil;
import edu.umn.cs.crisys.safety.analysis.soteria.faultTree.SoteriaFaultTree;
import edu.umn.cs.crisys.safety.analysis.transform.AddFaultsToAgree;
import jkind.JKindException;
import jkind.api.JKindApi;
import jkind.api.JRealizabilityApi;
import jkind.api.KindApi;
import jkind.api.results.AnalysisResult;
import jkind.api.results.CompositeAnalysisResult;
import jkind.api.results.JKindResult;
import jkind.api.results.JRealizabilityResult;
import jkind.lustre.Node;
import jkind.lustre.Program;

public class GenMCSHandler extends VerifyHandler {

	private static Element root = null;
	private static final String RERUN_ID = "com.rockwellcollins.atc.agree.analysis.commands.rerunAgree";
	private IHandlerActivation rerunActivation;
	private IHandlerActivation terminateActivation;
	private IHandlerActivation terminateAllActivation;
	private IHandlerService handlerService;
	private Map<String, String> rerunAdviceMap = new HashMap<>();
	private boolean calledFromRerun = false;

	private enum AnalysisType {
		AssumeGuarantee, Consistency, Realizability
	};


	@Override
	protected IStatus runJob(Element root, IProgressMonitor monitor) {
		EphemeralImplementationUtil implUtil = new EphemeralImplementationUtil(monitor);
		// this flag is set by the rerun handler to prevent clearing the advice map
		if (!calledFromRerun) {
			rerunAdviceMap.clear();
		}
		calledFromRerun = false;

		disableRerunHandler();
		handlerService = getWindow().getService(IHandlerService.class);

		try {
			// Make sure the user selected a component implementation
			ComponentImplementation ci = getComponentImplementation(root, implUtil);
			SystemInstance si = getSysInstance(ci, implUtil);

			AnalysisResult result;
			CompositeAnalysisResult wrapper = new CompositeAnalysisResult("");

			// SystemType sysType = si.getSystemImplementation().getType();
			ComponentType sysType = AgreeUtils.getInstanceType(si);
			EList<AnnexSubclause> annexSubClauses = AnnexUtil.getAllAnnexSubclauses(sysType,
					AgreePackage.eINSTANCE.getAgreeContractSubclause());

			if (annexSubClauses.size() == 0) {
				throw new AgreeException("There is not an AGREE annex in the '" + sysType.getName() + "' system type.");
			}

			if (isRecursive()) {
				if (AgreeUtils.usingKind2()) {
					throw new AgreeException("Kind2 only supports monolithic verification");
				}
				result = buildAnalysisResult(((NamedElement) root).getName(), si);
				wrapper.addChild(result);
				result = wrapper;
			} else if (isRealizability()) {
				AgreeProgram agreeProgram = new AgreeASTBuilder().getAgreeProgram(si, false);
				Program program = LustreAstBuilder.getRealizabilityLustreProgram(agreeProgram);
				wrapper.addChild(createVerification("Realizability Check", si, program, agreeProgram,
						AnalysisType.Realizability));
				result = wrapper;
			} else {
				wrapVerificationResult(si, wrapper);
				result = wrapper;
			}
			showView(result, linker);
			return doAnalysis(root, monitor, result, linker);
		} catch (Throwable e) {
			String messages = getNestedMessages(e);
			return new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, messages, e);
		} finally {
			implUtil.cleanup();
		}
	}


	// The following method is copied and modified from AGREE VerifyHandler
	private IStatus doAnalysis(final Element root, final IProgressMonitor globalMonitor, AnalysisResult result,
			AgreeResultsLinker linker) {

		Thread analysisThread = new Thread() {
			@Override
			public void run() {

				activateTerminateHandlers(globalMonitor);
				KindApi api = PreferencesUtil.getKindApi();
				KindApi consistApi = PreferencesUtil.getConsistencyApi();
				JRealizabilityApi realApi = PreferencesUtil.getJRealizabilityApi();

				while (!queue.isEmpty() && !globalMonitor.isCanceled()) {
					JKindResult result = queue.peek();
					NullProgressMonitor subMonitor = new NullProgressMonitor();
					monitorRef.set(subMonitor);

					Program program = linker.getProgram(result);

					if (api instanceof JKindApi) {
						result.getName();
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
						new SafetyException("JKind exception: " + e.getMessage());
						System.out.println("******** JKindException Text ********");
						e.printStackTrace(System.out);
						System.out.println("******** JKind Output ********");
						System.out.println(result.getText());
						System.out.println("******** Agree Lustre ********");
						System.out.println(program);
						break;
					}
					queue.remove();
				}

				while (!queue.isEmpty()) {
					queue.remove().cancel();
				}
				// COMMENTED OUT:
				// This form of display is not platform independent apparently.
				// Create progress bar to display to users on long analysis runs.
//				Display display = new Display();
//				Shell shell = createProgressBar(display);

				// generate soteria fault tree from the result
				// TODO: if zero max N fault hypothesis and empty fault combination for probabilistic analysis
				// generate empty tree

				// if no fault hypothesis
				// or if max fault hypothesis but N = 0
				// or if probablistic hypothesis but fault combinations above threshold is empty
				// then print empty min cut set fault tree
				if ((!AddFaultsToNodeVisitor.maxFaultHypothesis && !AddFaultsToNodeVisitor.probabilisticHypothesis)
						|| (AddFaultsToNodeVisitor.maxFaultHypothesis && (AddFaultsToNodeVisitor.maxFaultCount == 0))
						|| (AddFaultsToNodeVisitor.probabilisticHypothesis
								&& AddFaultsToNodeVisitor.faultCombinationsAboveThreshold.isEmpty())) {
					SoteriaPrintUtils printUtils = new SoteriaPrintUtils();
					printUtils.printEmptyTree();

					try {
						String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
						File file = File.createTempFile("soteriaResolvedFT_" + timeStamp + "_", ".ml");
						BufferedWriter bw = new BufferedWriter(new FileWriter(file));
						bw.write(printUtils.toString());
						bw.close();
						org.eclipse.swt.program.Program.launch(file.toString());
					} catch (IOException e) {
						Dialog.showError("Unable to open file", e.getMessage());
						e.printStackTrace();
					}
				}
				else {
					// open progress bar
//					shell.open();
					IvcToSoteriaFTGenerator soteriaFTGenerator = new IvcToSoteriaFTGenerator();
					SoteriaFaultTree soteriaFT = soteriaFTGenerator.generateSoteriaFT(result, linker);
					HashMap<String, Set<List<String>>> mapForHFT = soteriaFTGenerator.getMapPropertyToMCSs();

					try {
						String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
						File hierarchyFTFile = File.createTempFile("HierarchicalCausalFactors_" + timeStamp + "_",
								".txt");
						BufferedWriter bw = new BufferedWriter(new FileWriter(hierarchyFTFile));
						SoteriaPrintUtils printUtils = new SoteriaPrintUtils();
						bw.write(printUtils.printHierarchicalText(mapForHFT));
						bw.close();
//						display.dispose();
						org.eclipse.swt.program.Program.launch(hierarchyFTFile.toString());
					} catch (IOException e) {
						// close progress bar
//						display.dispose();
						Dialog.showError("Unable to open file", e.getMessage());
						e.printStackTrace();
					}

					SoteriaFTResolveVisitor resolveVisitor = new SoteriaFTResolveVisitor();
					resolveVisitor.visit(soteriaFT);

					try {
						String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
						File minCutSetFile = File.createTempFile("MinCutSet_" + timeStamp + "_", ".txt");
						BufferedWriter bw = new BufferedWriter(new FileWriter(minCutSetFile));
						bw.write(soteriaFT.printMinCutSetTxt());
						bw.close();
//						display.dispose();
						org.eclipse.swt.program.Program.launch(minCutSetFile.toString());
					} catch (IOException e) {
						// close progress bar
//						display.dispose();
						Dialog.showError("Unable to open file", e.getMessage());
						e.printStackTrace();
					}

					try {
						String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
						File minCutSetTallyFile = File.createTempFile("MinCutSetTally_" + timeStamp + "_", ".txt");
						BufferedWriter bw = new BufferedWriter(new FileWriter(minCutSetTallyFile));
						bw.write(soteriaFT.printMinCutSetTally());
						bw.close();
//						display.dispose();
						org.eclipse.swt.program.Program.launch(minCutSetTallyFile.toString());
					} catch (IOException e) {
						// close progress bar
//						display.dispose();
						Dialog.showError("Unable to open file", e.getMessage());
						e.printStackTrace();
					}

				}
				AddFaultsToAgree.resetStaticVars();
				deactivateTerminateHandlers();
				enableRerunHandler(root);

			}
		};
		analysisThread.start();
		return Status.OK_STATUS;
	}

	private Shell createProgressBar(Display display) {
		Shell shell = new Shell();
		shell.setText("Compositional Safety Analysis");
		shell.setSize(450, 200);

		ProgressBar progressBar = new ProgressBar(shell, SWT.INDETERMINATE);
		progressBar.setBounds(50, 80, 250, 20);
		Label label = new Label(shell, SWT.NULL);
		label.setText("Minimal Cut Set Generation");
		label.setAlignment(SWT.LEFT);
		label.setBounds(50, 30, 450, 20);
		return shell;
	}

	@Override
	public Object execute(ExecutionEvent event) {
		AddFaultsToAgree.resetStaticVars();
		Event selEvent = (Event) event.getTrigger();
		MenuItem item = (MenuItem) selEvent.widget;
		AddFaultsToAgree.setTransformFlag(item);
		// clear static variables before each run
		AddFaultsToNodeVisitor.init();
		if (!SafetyUtils.containsSafetyAnnex(getClassifiers())) {
			new SafetyException("A safety annex in the implementation is required to run the fault analysis.");
			return Status.CANCEL_STATUS;
		}
		// If isGenMCS, then the user selected
		// 'Generate MCS' option and we should execute event.
		// Else, return null.
		if (AddFaultsToAgree.getIsGenMCS()) {
			return super.execute(event);
		} else {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see edu.umn.cs.crisys.safety.analysis.handlers.AadlHandler#getJobName()
	 *
	 * Output job name (will see it at the bottom of Osate2 after selected)
	 */
	@Override
	protected String getJobName() {
		return "Safety Analysis results";
	}

	/*
	 * setRoot
	 *
	 * @param Element root : This sets the component implementation to the current root Element.
	 *
	 * @return void
	 */
	public static void setRoot(Element root) {
		GenMCSHandler.root = root;
	}

	/*
	 * getRoot
	 *
	 * @param none
	 *
	 * @return Element root : This contains the component implementation and will
	 * be used in transformAgree in order to access the safety annex.
	 */
	public static Element getRoot() {
		return GenMCSHandler.root;
	}

	@Override
	protected boolean isRecursive() {
		return true;
	}

	@Override
	protected boolean isMonolithic() {
		return false;
	}

	@Override
	protected boolean isRealizability() {
		return false;
	}

	/**
	 * Copied from AGREE VerifyHandler - allows for min cut set Analysis Type in
	 * param analysisType
	 *
	 * @param resultName
	 * @param compInst
	 * @param lustreProgram
	 * @param agreeProgram
	 * @param analysisType
	 * @return
	 */
	private AnalysisResult createVerification(String resultName, ComponentInstance compInst, Program lustreProgram,
			AgreeProgram agreeProgram, AnalysisType analysisType) {
		AgreeAutomaterRegistry aAReg = (AgreeAutomaterRegistry) ExtensionRegistry
				.getRegistry(ExtensionRegistry.AGREE_AUTOMATER_EXT_ID);
		List<AgreeAutomater> automaters = aAReg.getAgreeAutomaters();
		AgreeRenaming renaming = new AgreeRenaming();
		AgreeLayout layout = new AgreeLayout();
		Node mainNode = null;
		for (Node node : lustreProgram.nodes) {
			if (node.id.equals(lustreProgram.main)) {
				mainNode = node;
				break;
			}
		}
		if (mainNode == null) {
			throw new AgreeException("Could not find main lustre node after translation");
		}

		List<String> properties = new ArrayList<>();

		RenamingVisitor.addRenamings(lustreProgram, renaming, compInst, layout);
		addProperties(renaming, properties, mainNode, agreeProgram);

		for (AgreeAutomater aa : automaters) {
			renaming = aa.rename(renaming);
			layout = aa.transformLayout(layout);
		}

		JKindResult result;
		switch (analysisType) {
		case Consistency:
			result = new ConsistencyResult(resultName, mainNode.properties, Collections.singletonList(true), renaming);
			break;
		case Realizability:
			result = new JRealizabilityResult(resultName, renaming);
			break;
		case AssumeGuarantee:
			result = new JKindResult(resultName, properties, renaming);
			break;
		default:
			throw new AgreeException("Unhandled Analysis Type");
		}
		queue.add(result);

		ComponentImplementation compImpl = AgreeUtils.getInstanceImplementation(compInst);
		linker.setProgram(result, lustreProgram);
		linker.setComponent(result, compImpl);
		linker.setContract(result, getContract(compImpl));
		linker.setLayout(result, layout);
		linker.setReferenceMap(result, renaming.getRefMap());
		linker.setLog(result, AgreeLogger.getLog());
		linker.setRenaming(result, renaming);

		return result;

	}

	@Override
	protected void wrapVerificationResult(ComponentInstance si, CompositeAnalysisResult wrapper) {
		AgreeProgram agreeProgram = new AgreeASTBuilder().getAgreeProgram(si, isMonolithic());
		Program program;
		if (AgreeUtils.usingKind2()) {
			if (!isMonolithic()) {
				throw new AgreeException("Kind2 now only supports monolithic verification");
			}
			program = LustreContractAstBuilder.getContractLustreProgram(agreeProgram);
		} else {
			program = LustreAstBuilder.getAssumeGuaranteeLustreProgram(agreeProgram);
		}

		wrapper.addChild(
				createVerification("Contract Guarantees", si, program, agreeProgram, AnalysisType.AssumeGuarantee));

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

	private void enableRerunHandler(final Element root) {
		getWindow().getShell().getDisplay().syncExec(() -> {
			IHandlerService handlerService = getHandlerService();
			rerunActivation = handlerService.activateHandler(RERUN_ID, new RerunHandler(root, GenMCSHandler.this));
		});
	}

	protected void disableRerunHandler() {
		if (rerunActivation != null) {
			getWindow().getShell().getDisplay().syncExec(() -> {
				IHandlerService handlerService = getHandlerService();
				handlerService.deactivateHandler(rerunActivation);
				rerunActivation = null;
			});
		}
	}

	private IHandlerService getHandlerService() {
		return getWindow().getService(IHandlerService.class);
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
