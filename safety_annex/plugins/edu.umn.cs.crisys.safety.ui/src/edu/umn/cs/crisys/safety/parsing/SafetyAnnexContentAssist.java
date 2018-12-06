package edu.umn.cs.crisys.safety.parsing;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.resource.EObjectAtOffsetHelper;
import org.eclipse.xtext.resource.XtextResource;
import org.osate.aadl2.AadlPackage;
import org.osate.aadl2.AnnexLibrary;
import org.osate.aadl2.AnnexSubclause;
import org.osate.aadl2.ComponentImplementation;
import org.osate.aadl2.DataPort;
import org.osate.aadl2.DefaultAnnexLibrary;
import org.osate.aadl2.DefaultAnnexSubclause;
import org.osate.aadl2.EventDataPort;
import org.osate.aadl2.NamedElement;
import org.osate.annexsupport.AnnexContentAssist;
import org.osate.annexsupport.AnnexUtil;
import org.osate.xtext.aadl2.properties.ui.contentassist.PropertiesProposalProvider;

import com.google.inject.Injector;
import com.rockwellcollins.atc.agree.agree.Arg;
import com.rockwellcollins.atc.agree.agree.ConstStatement;
import com.rockwellcollins.atc.agree.agree.NestedDotID;
import com.rockwellcollins.atc.agree.agree.RecordDefExpr;
import com.rockwellcollins.atc.agree.agree.RecordType;
import com.rockwellcollins.atc.agree.agree.SpecStatement;
import com.rockwellcollins.atc.agree.agree.Type;

import edu.umn.cs.crisys.safety.safety.SafetyContract;
import edu.umn.cs.crisys.safety.safety.SafetyContractLibrary;
import edu.umn.cs.crisys.safety.safety.SafetyLibrary;
import edu.umn.cs.crisys.safety.safety.SafetyPackage;
import edu.umn.cs.crisys.safety.ui.contentassist.SafetyProposalProvider;
import edu.umn.cs.crisys.safety.ui.internal.SafetyActivator;

public class SafetyAnnexContentAssist implements AnnexContentAssist{

	final private Injector injector = SafetyActivator.getInstance().getInjector(
			SafetyActivator.EDU_UMN_CS_CRISYS_SAFETY_SAFETY);


	private PropertiesProposalProvider propPropProv;
	private EObjectAtOffsetHelper offsetHelper;


	protected PropertiesProposalProvider getLinkingService() {
		if (propPropProv == null) {
			propPropProv = injector.getInstance(SafetyProposalProvider.class);
		}
		return propPropProv;
	}

	protected EObjectAtOffsetHelper getOffsetHelper() {
		if(offsetHelper == null){
			offsetHelper = injector.getInstance(EObjectAtOffsetHelper.class);
		}
		return offsetHelper;
	}

	@Override
	public List<String> annexCompletionSuggestions(EObject defaultAnnex, int offset) {

		offset = (offset <= 0) ? 0 : offset - 1; //get one character back
		EObjectAtOffsetHelper helper = getOffsetHelper();
		EObject grammerObject = null;
		//EObjectAtOffsetHelper
		if(defaultAnnex instanceof DefaultAnnexLibrary){
			AnnexLibrary annexLib = ((DefaultAnnexLibrary)defaultAnnex).getParsedAnnexLibrary();
			XtextResource resource = (XtextResource)annexLib.eResource();
			grammerObject = helper.resolveContainedElementAt(resource, offset);
		}else if(defaultAnnex instanceof DefaultAnnexSubclause){
			AnnexSubclause annexSub = ((DefaultAnnexSubclause)defaultAnnex).getParsedAnnexSubclause();
			XtextResource resource = (XtextResource)annexSub.eResource();
			grammerObject = helper.resolveContainedElementAt(resource, offset);
		}

		List<String> results = new ArrayList<>();
		if(grammerObject instanceof NestedDotID){
			results.addAll(getNestedDotIDCandidates((NestedDotID)grammerObject));
		}

		return results;
	}

	private List<String> getNestedDotIDCandidates(AadlPackage aadlPackage) {

		SafetyContract contract = null;
		List<String> results = new ArrayList<>();
		for (AnnexLibrary annex :  AnnexUtil.getAllActualAnnexLibraries(aadlPackage, SafetyPackage.eINSTANCE.getSafetyContractLibrary())) {
            if (annex instanceof SafetyLibrary) {
            	contract = (SafetyContract) ((SafetyContractLibrary) annex).getContract();
            }
        }

		if(contract != null){
			for(SpecStatement spec : contract.getSpecs()){
				if(spec instanceof ConstStatement){
					results.add(((ConstStatement) spec).getName());
				}
			}

		}

		return results;
	}

	private List<String> getNestedDotIDCandidates(NamedElement namedEl) {
		List<String> results = new ArrayList<>();

		List<NamedElement> namedEls = new ArrayList<NamedElement>();
		if(namedEl instanceof ComponentImplementation){
			namedEls.addAll(((ComponentImplementation) namedEl).getAllSubcomponents());
		}else if(namedEl instanceof RecordDefExpr){
			namedEls.addAll(((RecordDefExpr) namedEl).getArgs());
		}
		for(NamedElement el : namedEls){
			results.add(el.getName());
		}
		return results;
	}

	private List<String> getNestedDotIDCandidates(NestedDotID id) {

		NamedElement base = id.getBase();
		NamedElement namedEl = null;

		if(base instanceof Arg){
			Type type = ((Arg) base).getType();
			NestedDotID elID = (NestedDotID) ((RecordType) type).getRecord();
    		namedEl = elID.getBase();
		}else if(base instanceof DataPort){
			namedEl = ((DataPort) base).getDataFeatureClassifier();
		}else if(base instanceof EventDataPort){
			namedEl = ((EventDataPort) base).getDataFeatureClassifier();
		}else if(base instanceof AadlPackage){
			return getNestedDotIDCandidates((AadlPackage)base);
		}else{
			return new ArrayList<>();
		}

		return getNestedDotIDCandidates(namedEl);
	}

}
