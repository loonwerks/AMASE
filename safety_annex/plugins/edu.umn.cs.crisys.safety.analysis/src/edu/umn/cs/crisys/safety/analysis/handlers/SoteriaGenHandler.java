package edu.umn.cs.crisys.safety.analysis.handlers;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.IHandlerActivation;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.xtext.EcoreUtil2;
import org.osate.aadl2.AadlPackage;
import org.osate.aadl2.AnnexSubclause;
import org.osate.aadl2.Classifier;
import org.osate.aadl2.ComponentClassifier;
import org.osate.aadl2.ComponentImplementation;
import org.osate.aadl2.ComponentType;
import org.osate.aadl2.Element;
import org.osate.aadl2.NamedElement;
import org.osate.aadl2.instance.ComponentInstance;
import org.osate.aadl2.instance.SystemInstance;
import org.osate.aadl2.instantiation.InstantiateModel;
import org.osate.aadl2.modelsupport.util.AadlUtil;
import org.osate.annexsupport.AnnexUtil;
import org.osate.ui.dialogs.Dialog;

import com.rockwellcollins.atc.agree.agree.AgreeContractSubclause;
import com.rockwellcollins.atc.agree.agree.AgreePackage;
import com.rockwellcollins.atc.agree.agree.AgreeSubclause;
import com.rockwellcollins.atc.agree.analysis.Activator;
import com.rockwellcollins.atc.agree.analysis.AgreeException;
import com.rockwellcollins.atc.agree.analysis.AgreeLayout;
import com.rockwellcollins.atc.agree.analysis.AgreeLogger;
import com.rockwellcollins.atc.agree.analysis.AgreeRenaming;
import com.rockwellcollins.atc.agree.analysis.AgreeUtils;
import com.rockwellcollins.atc.agree.analysis.ConsistencyResult;
import com.rockwellcollins.atc.agree.analysis.EphemeralImplementationUtil;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeASTBuilder;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeNode;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeProgram;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeStatement;
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

import edu.umn.cs.crisys.safety.analysis.ast.visitors.AddFaultsToNodeVisitor;
import edu.umn.cs.crisys.safety.analysis.ast.visitors.SoteriaFTResolveVisitor;
import edu.umn.cs.crisys.safety.analysis.ast.visitors.SoteriaPrintUtils;
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

public class SoteriaGenHandler extends VerifyHandler {

	private static Element root = null;
	private static final String RERUN_ID = "com.rockwellcollins.atc.agree.analysis.commands.rerunAgree";
	private IHandlerActivation rerunActivation;
	private IHandlerActivation terminateActivation;
	private IHandlerActivation terminateAllActivation;
	private IHandlerService handlerService;
	private Map<String, String> rerunAdviceMap = new HashMap<>();
	private int adviceCount = 0;
	private boolean calledFromRerun = false;

	private enum AnalysisType {
		AssumeGuarantee, Consistency, Realizability
	};
	/*
	 * (non-Javadoc)
	 *
	 * @see edu.umn.cs.crisys.safety.analysis.handlers.AadlHandler#runJob(org.osate.aadl2.Element, org.eclipse.core.runtime.IProgressMonitor)
	 *
	 * Check for component implementation, two annexes in that implementation, save
	 * the safety package instance, and set the transform agree flag.
	 */

	@Override
	protected SystemInstance getSysInstance(ComponentImplementation ci, EphemeralImplementationUtil implUtil) {
		try {
			return InstantiateModel.buildInstanceModelFile(ci);
		} catch (Exception e) {
			Dialog.showError("Model Instantiate", "Error while re-instantiating the model: " + e.getMessage());
			throw new AgreeException("Error Instantiating model");
		}
	}

	private ComponentImplementation getComponentImplementation(Element root, EphemeralImplementationUtil implUtil) {
		Classifier classifier = getOutermostClassifier(root);
		if (isRealizability()) {
			if (!(classifier instanceof ComponentType)) {
				throw new AgreeException("Must select an AADL Component Type");
			}
			ComponentImplementation result;
			try {
				result = implUtil.generateEphemeralCompImplFromType((ComponentType) classifier);
			} catch (Exception e) {
				e.printStackTrace();
				throw new AgreeException("Error creating component implementation.");
			}
			return result;
		} else {
			if (classifier instanceof ComponentImplementation) {
				return (ComponentImplementation) classifier;
			}
			if (!(classifier instanceof ComponentType)) {
				throw new AgreeException("Must select an AADL Component Type or Implementation");
			}
			ComponentType ct = (ComponentType) classifier;
			List<ComponentImplementation> cis = getComponentImplementations(ct);
			if (cis.size() == 0) {
				throw new AgreeException("AADL Component Type has no implementation to verify");
			} else if (cis.size() == 1) {
				ComponentImplementation ci = cis.get(0);
				Shell shell = getWindow().getShell();
				String message = "User selected " + ct.getFullName() + ".\nRunning analysis on " + ci.getFullName()
						+ " instead.";
				shell.getDisplay()
						.asyncExec(() -> MessageDialog.openInformation(shell, "Analysis information", message));
				return ci;
			} else {
				throw new AgreeException(
						"AADL Component Type has multiple implementations to verify: please select just one");
			}
		}
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
			// Option pane window reminding user to run compositional analysis on model first.
			// The user can exit out of fault analysis if desired or continue.
			// If return value is 0, user selected OK and wants to run analysis.
			// If -1, user closed window and if 1 user canceled operation.
//			if (showCompositionalAnalysisReminder() != 0) {
//				return Status.OK_STATUS;
//			}
//
//			// Output reminder that analysis is underway, but it might take a while.
//			JOptionPane.showMessageDialog(null,
//					"Fault analysis is underway. \n Depending on how large the model is,\n it could take some time.",
//					"Fault Analysis Message", JOptionPane.PLAIN_MESSAGE);

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
						String resultName = result.getName();
//						String adviceFileName = rerunAdviceMap.get(resultName);
//						if (adviceFileName == null) {
//							adviceFileName = "agree_advice" + adviceCount++;
//							rerunAdviceMap.put(resultName, adviceFileName);
//						} else {
//							((JKindApi) api).setReadAdviceFile(adviceFileName);
//						}
//						((JKindApi) api).setWriteAdviceFile(adviceFileName);
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
				// Create progress bar to display to users on long analysis runs.
				Display display = new Display();
				Shell shell = createProgressBar(display);

//				// generate soteria model from the result
//				IvcToSoteriaGenerator soteriaGenerator = new IvcToSoteriaGenerator();
//				SoteriaModel soteriaModel = soteriaGenerator.generateModel(result, linker);
//				try {
//					File file = File.createTempFile("soteriaMdl_", ".ml");
//					BufferedWriter bw = new BufferedWriter(new FileWriter(file));
//					bw.write(soteriaModel.toString());
//					bw.close();
//					org.eclipse.swt.program.Program.launch(file.toString());
//				} catch (IOException e) {
//					Dialog.showError("Unable to open file", e.getMessage());
//					e.printStackTrace();
//				}

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
						File file = File.createTempFile("soteriaResolvedFT_", ".ml");
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
					shell.open();
					// uncomment to see progress bar until user closes it
					// (for testing purposes)
//					while (!shell.isDisposed()) {
//						if (!display.readAndDispatch()) {
//							display.sleep();
//						}
//					}
					IvcToSoteriaFTGenerator soteriaFTGenerator = new IvcToSoteriaFTGenerator();
					SoteriaFaultTree soteriaFT = soteriaFTGenerator.generateSoteriaFT(result, linker);
//					try {
//						File file = File.createTempFile("soteriaFT_", ".ml");
//						BufferedWriter bw = new BufferedWriter(new FileWriter(file));
//						bw.write(soteriaFT.toString());
//						bw.close();
//						org.eclipse.swt.program.Program.launch(file.toString());
//					} catch (IOException e) {
//						Dialog.showError("Unable to open file", e.getMessage());
//						e.printStackTrace();
//					}

					SoteriaFTResolveVisitor resolveVisitor = new SoteriaFTResolveVisitor();
					resolveVisitor.visit(soteriaFT);

//					try {
//						File soteriaFTFile = File.createTempFile("soteriaResolvedFT_", ".ml");
//						BufferedWriter bw = new BufferedWriter(new FileWriter(soteriaFTFile));
//						bw.write(soteriaFT.printMinCutSet());
//						bw.close();
//						org.eclipse.swt.program.Program.launch(soteriaFTFile.toString());
//					} catch (IOException e) {
//						Dialog.showError("Unable to open file", e.getMessage());
//						e.printStackTrace();
//					}

					try {
						File minCutSetFile = File.createTempFile("MinCutSet_", ".txt");
						BufferedWriter bw = new BufferedWriter(new FileWriter(minCutSetFile));
						bw.write(soteriaFT.printMinCutSetTxt());
						bw.close();
						display.dispose();
						org.eclipse.swt.program.Program.launch(minCutSetFile.toString());
					} catch (IOException e) {
						// close progress bar
						display.dispose();
						Dialog.showError("Unable to open file", e.getMessage());
						e.printStackTrace();
					}

					try {
						File minCutSetTallyFile = File.createTempFile("MinCutSetTally_", ".txt");
						BufferedWriter bw = new BufferedWriter(new FileWriter(minCutSetTallyFile));
						bw.write(soteriaFT.printMinCutSetTally());
						bw.close();
						display.dispose();
						org.eclipse.swt.program.Program.launch(minCutSetTallyFile.toString());
					} catch (IOException e) {
						// close progress bar
						display.dispose();
						Dialog.showError("Unable to open file", e.getMessage());
						e.printStackTrace();
					}

				}

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
		Event selEvent = (Event) event.getTrigger();
		MenuItem item = (MenuItem) selEvent.widget;
		AddFaultsToAgree.setTransformFlag(item);
		// clear static variables before each run
		AddFaultsToNodeVisitor.init();
		// If the transform flag is 2, then the user selected
		// 'Compositional Fault Analysis for All Layers' option and we should execute event.
		// Else, return null.
		if (AddFaultsToAgree.getTransformFlag() == 2) {
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
	 * getNestedMessages
	 *
	 * @param Throwable : exception
	 *
	 * @return String : exception message
	 */
	@Override
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
	 *
	 * @param Element root : This sets the component implementation to the current root Element.
	 *
	 * @return void
	 */
	public static void setRoot(Element root) {
		SoteriaGenHandler.root = root;
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
		return SoteriaGenHandler.root;
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

	// The following private methods are copied from AGREE VerifyHandler

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

	private void addProperties(AgreeRenaming renaming, List<String> properties, Node mainNode,
			AgreeProgram agreeProgram) {

		// there is a special case in the AgreeRenaming which handles this
		// translation
		if (AgreeUtils.usingKind2()) {
			addKind2Properties(agreeProgram.topNode, properties, renaming, "_TOP", "");
		} else {
			properties.addAll(mainNode.properties);
		}

		Set<String> strs = new HashSet<>();
		for (String prop : properties) {
			String renamed = renaming.rename(prop);
			if (!strs.add(renamed)) {
				throw new AgreeException("Multiple properties named \"" + renamed + "\"");
			}
		}

	}

	private AgreeSubclause getContract(ComponentImplementation ci) {
		ComponentType ct = ci.getOwnedRealization().getImplemented();
		for (AnnexSubclause annex : ct.getOwnedAnnexSubclauses()) {
			if (annex instanceof AgreeSubclause) {
				return (AgreeSubclause) annex;
			}
		}
		return null;
	}

	private AnalysisResult buildAnalysisResult(String name, ComponentInstance ci) {
		CompositeAnalysisResult result = new CompositeAnalysisResult("Verification for " + name);
		if (containsAGREEAnnex(ci)) {
			wrapVerificationResult(ci, result);
			ComponentImplementation compImpl = AgreeUtils.getInstanceImplementation(ci);
			for (ComponentInstance subInst : ci.getComponentInstances()) {
				if (AgreeUtils.getInstanceImplementation(subInst) != null) {
					AnalysisResult buildAnalysisResult = buildAnalysisResult(subInst.getName(), subInst);
					if (buildAnalysisResult != null) {
						result.addChild(buildAnalysisResult);
					}
				}
			}

			if (result.getChildren().size() != 0) {
				linker.setComponent(result, compImpl);
				return result;
			}
		}
		return null;
	}

	private boolean containsAGREEAnnex(ComponentInstance ci) {
		ComponentClassifier compClass = ci.getComponentClassifier();
		if (compClass instanceof ComponentImplementation) {
			compClass = ((ComponentImplementation) compClass).getType();
		}
		for (AnnexSubclause annex : AnnexUtil.getAllAnnexSubclauses(compClass,
				AgreePackage.eINSTANCE.getAgreeContractSubclause())) {
			if (annex instanceof AgreeContractSubclause) {
				return true;
			}
		}
		return false;
	}

	private void wrapVerificationResult(ComponentInstance si, CompositeAnalysisResult wrapper) {
		AgreeProgram agreeProgram = new AgreeASTBuilder().getAgreeProgram(si, isMonolithic());

		// generate different lustre depending on which model checker we are
		// using

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

	void addKind2Properties(AgreeNode agreeNode, List<String> properties, AgreeRenaming renaming, String prefix,
			String userPropPrefix) {
		int i = 0;

		String propPrefix = (userPropPrefix.equals("")) ? "" : userPropPrefix + ": ";
		for (AgreeStatement statement : agreeNode.lemmas) {
			renaming.addExplicitRename(prefix + "[" + (++i) + "]", propPrefix + statement.string);
			properties.add(prefix.replaceAll("\\.", AgreeASTBuilder.dotChar) + "[" + i + "]");
		}
		for (AgreeStatement statement : agreeNode.guarantees) {
			renaming.addExplicitRename(prefix + "[" + (++i) + "]", propPrefix + statement.string);
			properties.add(prefix.replaceAll("\\.", AgreeASTBuilder.dotChar) + "[" + i + "]");
		}

		userPropPrefix = userPropPrefix.equals("") ? "" : userPropPrefix + ".";
		for (AgreeNode subNode : agreeNode.subNodes) {
			addKind2Properties(subNode, properties, renaming, prefix + "." + subNode.id, userPropPrefix + subNode.id);
		}
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
			rerunActivation = handlerService.activateHandler(RERUN_ID, new RerunHandler(root, SoteriaGenHandler.this));
		});
	}

	private IHandlerService getHandlerService() {
		return getWindow().getService(IHandlerService.class);
	}

	/*
	 * Pop up box for users who select fault tree generation.
	 * This is a reminder to run compositional analysis on nominal model
	 * before doing this analysis.
	 */
//	private int showCompositionalAnalysisReminder() {
//
//		int n = JOptionPane.showConfirmDialog(null,
//				"When performing fault tree analysis, we assume that the nominal model\n has been "
//						+ "verified compositionally (AGREE: Verify All Layers).\n"
//						+ "Select OK to continue with the safety analysis \n or CANCEL to end the process.",
//				"Compositional verification requirement", JOptionPane.OK_CANCEL_OPTION);
//
//		return n;
//	}

}
