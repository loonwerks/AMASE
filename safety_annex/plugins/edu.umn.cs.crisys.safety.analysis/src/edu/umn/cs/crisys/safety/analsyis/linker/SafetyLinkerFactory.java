package edu.umn.cs.crisys.safety.analsyis.linker;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.osate.aadl2.AnnexSubclause;
import org.osate.aadl2.ComponentClassifier;
import org.osate.aadl2.ComponentImplementation;
import org.osate.aadl2.ComponentType;
import org.osate.aadl2.instance.ComponentInstance;
import org.osate.aadl2.instance.SystemInstance;
import org.osate.aadl2.instantiation.InstantiateModel;
import org.osate.annexsupport.AnnexUtil;
import org.osate.ui.dialogs.Dialog;

import com.rockwellcollins.atc.agree.agree.AgreeContractSubclause;
import com.rockwellcollins.atc.agree.agree.AgreePackage;
import com.rockwellcollins.atc.agree.agree.AgreeSubclause;
import com.rockwellcollins.atc.agree.analysis.AgreeException;
import com.rockwellcollins.atc.agree.analysis.AgreeLayout;
import com.rockwellcollins.atc.agree.analysis.AgreeLogger;
import com.rockwellcollins.atc.agree.analysis.AgreeRenaming;
import com.rockwellcollins.atc.agree.analysis.AgreeUtils;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeASTBuilder;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeProgram;
import com.rockwellcollins.atc.agree.analysis.lustre.visitors.RenamingVisitor;
import com.rockwellcollins.atc.agree.analysis.translation.LustreAstBuilder;
import com.rockwellcollins.atc.agree.analysis.views.AgreeResultsLinker;

import edu.umn.cs.crisys.safety.analysis.SafetyException;
import edu.umn.cs.crisys.safety.safety.SafetyPackage;
import jkind.api.results.AnalysisResult;
import jkind.api.results.CompositeAnalysisResult;
import jkind.api.results.JKindResult;
import jkind.lustre.Node;
import jkind.lustre.Program;

public class SafetyLinkerFactory {

	private SystemInstance si;
	private boolean monolithicAnalysis;
	private AnalysisResult result;
	private AnalysisProgramLinker linker = new AnalysisProgramLinker();
	private Queue<JKindResult> queue = new ArrayDeque<>();;

	public SystemInstance getSysInstance(ComponentImplementation ci) {
		try {
			return InstantiateModel.buildInstanceModelFile(ci);
		} catch (Exception e) {
			Dialog.showError("Model Instantiate", "Error while re-instantiating the model: " + e.getMessage());
			throw new AgreeException("Error Instantiating model");
		}
	}
	
	public SafetyLinkerFactory(ComponentImplementation ci, boolean monolithicAnalysis, boolean allLayers) {
		this.si = getSysInstance(ci);
		this.monolithicAnalysis = monolithicAnalysis;

		// check for AGREE Annex
		
        ComponentType sysType = AgreeUtils.getInstanceType(si);
        EList<AnnexSubclause> annexSubClauses = AnnexUtil.getAllAnnexSubclauses(sysType,
                AgreePackage.eINSTANCE.getAgreeContractSubclause());
        
        annexSubClauses.addAll(AnnexUtil.getAllAnnexSubclauses(sysType,
                SafetyPackage.eINSTANCE.getSafetyContractSubclause()));
        
        EList agreeSubclauses = AnnexUtil.getAllAnnexSubclauses(sysType, AgreePackage.eINSTANCE.getAgreeContractSubclause());
        System.out.println();
        EList safetySubclauses = AnnexUtil.getAllAnnexSubclauses(sysType, SafetyPackage.eINSTANCE.getSafetyContractSubclause());
        EClass agreetest = AgreePackage.eINSTANCE.getAgreeContractSubclause();
        EClass test = SafetyPackage.eINSTANCE.getSafetyContractSubclause();
        
        
//        EList<AnnexSubclause> safetySubclause = AnnexUtil.getAllAnnexSubclauses(sysType,
//                SafetyPackage.eINSTANCE.getSafetyContractSubclause());
        
        
        int size = annexSubClauses.size();
        System.out.println();

        if (annexSubClauses.size() == 0) {
            throw new SafetyException(
                    "There is no AGREE annex or Safety annex in the '" + sysType.getName() + "' system type.");
        }
        
        if(annexSubClauses.size() == 3){
        	throw new SafetyException(
        			"There must be both an AGREE annex and a Safety annex in the '" + sysType.getName() + "' system type.");
        }
        
        

        CompositeAnalysisResult wrapper = new CompositeAnalysisResult("");

        // check if we are analyzing all layers
		if (allLayers) {
            result = buildAnalysisResult(ci.getName(), si);
            wrapper.addChild(result);
            result = wrapper;
		} else {
            wrapVerificationResult(si, wrapper);
            result = wrapper;
		}
	}
	
	// Get analysis results, agree linker, and jkind queue
	public AnalysisResult getAnalysisResult() { return result; }
	public AgreeResultsLinker getLinker() { return linker; }
	public Queue<JKindResult> getWorkQueue() { return queue; }
	
	// Routines for actually building the verification results...
	
    private void wrapVerificationResult(ComponentInstance si, CompositeAnalysisResult wrapper) {
        AgreeProgram agreeProgram = new AgreeASTBuilder().getAgreeProgram(si, monolithicAnalysis);
        Program program;
        program = LustreAstBuilder.getAssumeGuaranteeLustreProgram(agreeProgram);
        wrapper.addChild(
                createVerification("Contract Test Cases", si, program, agreeProgram));
    }

    protected AnalysisResult createVerification(String resultName, ComponentInstance compInst, Program lustreProgram, AgreeProgram agreeProgram) {

    	// Renaming: organizes things between jkind and agree results? 
		AgreeRenaming agreeRenaming = new AgreeRenaming(); 
		AgreeLayout layout = new AgreeLayout();
		RenamingVisitor.addRenamings(lustreProgram, agreeRenaming, layout);
		SafetyRenaming renaming = new SafetyRenaming(agreeRenaming, agreeRenaming.getRefMap());
        Node mainNode = lustreProgram.getMainNode();
        
        if (mainNode == null) {
            throw new AgreeException("Could not find main lustre node after translation");
        }

        List<String> properties = new ArrayList<>();
        
        JKindResult result;
        result = new JKindResult(resultName, properties, renaming);
        queue.add(result);

        // Set the program, component, contract, layout, log, and renaming
        ComponentImplementation compImpl = AgreeUtils.getInstanceImplementation(compInst);
        linker.setAgreeProgram(result, agreeProgram);
        linker.setProgram(result, lustreProgram);
        linker.setComponent(result, compImpl);
        linker.setContract(result, getContract(compImpl));
        linker.setLayout(result, layout);
        linker.setReferenceMap(result, renaming.getRefMap());
        linker.setLog(result, AgreeLogger.getLog());
        linker.setRenaming(result, renaming);

        // Print the jkind result 
        //System.out.println(result);
        //System.out.println(agreeProgram);
        //System.out.println(lustreProgram);
        return result;

    }

    // Get agree subclause from the component implementation
    private AgreeSubclause getContract(ComponentImplementation ci) {
        ComponentType ct = ci.getOwnedRealization().getImplemented();
        for (AnnexSubclause annex : ct.getOwnedAnnexSubclauses()) {
            if (annex instanceof AgreeSubclause) {
                return (AgreeSubclause) annex;
            }
        }
        return null;
    }

    // Build jkind analysis
    private AnalysisResult buildAnalysisResult(String name, ComponentInstance ci) {
        CompositeAnalysisResult result = new CompositeAnalysisResult("Safety analysis for " + name);

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

    // Check to see if the component instance contains an agree annex
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


}
