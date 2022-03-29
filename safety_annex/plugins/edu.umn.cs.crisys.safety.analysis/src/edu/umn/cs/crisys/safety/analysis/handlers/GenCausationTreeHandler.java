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

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.editor.utils.EditorUtils;
import org.osate.aadl2.AadlPackage;
import org.osate.aadl2.AnnexSubclause;
import org.osate.aadl2.Classifier;
import org.osate.aadl2.ComponentImplementation;
import org.osate.aadl2.ComponentType;
import org.osate.aadl2.Element;
import org.osate.aadl2.instance.ComponentInstance;
import org.osate.aadl2.instance.SystemInstance;
import org.osate.annexsupport.AnnexUtil;
import org.osate.ui.dialogs.Dialog;

import com.rockwellcollins.atc.agree.agree.AgreePackage;
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
import com.rockwellcollins.atc.agree.analysis.extentions.AgreeAutomater;
import com.rockwellcollins.atc.agree.analysis.extentions.AgreeAutomaterRegistry;
import com.rockwellcollins.atc.agree.analysis.extentions.ExtensionRegistry;
import com.rockwellcollins.atc.agree.analysis.handlers.VerifyHandler;
import com.rockwellcollins.atc.agree.analysis.lustre.visitors.RenamingVisitor;
import com.rockwellcollins.atc.agree.analysis.translation.LustreAstBuilder;
import com.rockwellcollins.atc.agree.analysis.translation.LustreContractAstBuilder;

import edu.umn.cs.crisys.safety.analysis.Activator;
import edu.umn.cs.crisys.safety.analysis.SafetyException;
import edu.umn.cs.crisys.safety.analysis.ast.visitors.AddFaultsToNodeVisitor;
import edu.umn.cs.crisys.safety.analysis.constraints.ast.MistralConstraint;
import edu.umn.cs.crisys.safety.analysis.generators.ModelToConstraintsGenerator;
import edu.umn.cs.crisys.safety.analysis.transform.AddFaultsToAgree;
import edu.umn.cs.crisys.safety.util.SafetyUtil;
import jkind.api.results.AnalysisResult;
import jkind.api.results.CompositeAnalysisResult;
import jkind.api.results.JKindResult;
import jkind.api.results.JRealizabilityResult;
import jkind.lustre.Node;
import jkind.lustre.Program;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class GenCausationTreeHandler extends VerifyHandler {

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

		try {
			// Make sure the user selected a component implementation
			ComponentImplementation ci = getComponentImplementation(root, implUtil);
			SystemInstance si = getSysInstance(ci, implUtil);

			URI ciURI = ci.eResource().getURI();

			// SystemType sysType = si.getSystemImplementation().getType();
			ComponentType sysType = AgreeUtils.getInstanceType(si);
			EList<AnnexSubclause> annexSubClauses = AnnexUtil.getAllAnnexSubclauses(sysType,
					AgreePackage.eINSTANCE.getAgreeContractSubclause());

			if (annexSubClauses.size() == 0) {
				throw new AgreeException("There is not an AGREE annex in the '" + sysType.getName() + "' system type.");
			}

			AgreeProgram agreeProgram = new AgreeASTBuilder().getAgreeProgram(si, false);
			AgreeNode topNode = agreeProgram.topNode;

			Program lustreProgram = LustreAstBuilder.getAssumeGuaranteeLustreProgram(agreeProgram);

			ModelToConstraintsGenerator modelConstraintGenerator = new ModelToConstraintsGenerator(topNode, si,
					agreeProgram, lustreProgram);
			List<MistralConstraint> constraints = modelConstraintGenerator.generateConstraints();

			// TODO:
			// Major:
			// 1. need to generate one tree per top level guarantee, into a separate file
			// 2. need to handle multiple verification layers
			// Minor:
			// 1. store all constraints to one string then write to file
			// 2. print to separate folder like json generate file folder in the project folder
			try {
				String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
				File constraintFile = File.createTempFile("Constraint_" + timeStamp + "_", ".cpp");
				BufferedWriter bw = new BufferedWriter(new FileWriter(constraintFile));
				for (MistralConstraint constraint : constraints) {
					bw.write(constraint.toString());
				}
				bw.close();
				org.eclipse.swt.program.Program.launch(constraintFile.toString());
			} catch (IOException e) {
				Dialog.showError("Unable to open file", e.getMessage());
				e.printStackTrace();
			}

//			ModelToCTGenerator modelToCTGenerator = new ModelToCTGenerator(topNode, si, agreeProgram);
//			List<CT> causationTrees = modelToCTGenerator.generateCausationTree();
//
//			// print each causation tree to a json file
//			// TODO: make sure that different causation trees get generated to different json files
//			// TODO: generate a confirmation message when tree is generated
//			for (CT ct : causationTrees) {
//				CTToJsonGenerator.createJson(ciURI, ct);
//			}
			AddFaultsToAgree.resetStaticVars();
			return Status.OK_STATUS;
		} catch (Throwable e) {
			String messages = getNestedMessages(e);
			return new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, messages, e);
		} finally {
			implUtil.cleanup();
		}
	}

	@Override
	public Object execute(ExecutionEvent event) {
		AddFaultsToAgree.resetStaticVars();
		Event selEvent = (Event) event.getTrigger();
		MenuItem item = (MenuItem) selEvent.widget;
		AddFaultsToAgree.setTransformFlag(item);
		// clear static variables before each run
		AddFaultsToNodeVisitor.init();
		if (!SafetyUtil.containsSafetyAnnex(getClassifiers())) {
			new SafetyException("A safety annex in the implementation is required to run the fault analysis.");
			return Status.CANCEL_STATUS;
		}

		// If isGenMCS or isGenFTA, then the user selected
		// 'Generate MCS' option or 'Generate Fault Tree' option and we should execute event.
		// Else, return null.
		if (AddFaultsToAgree.getIsGenMCS() || AddFaultsToAgree.getIsGenFTA()) {
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