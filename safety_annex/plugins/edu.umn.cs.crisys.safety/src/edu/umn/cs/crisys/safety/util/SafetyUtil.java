package edu.umn.cs.crisys.safety.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.osate.aadl2.AnnexSubclause;
import org.osate.aadl2.ComponentClassifier;
import org.osate.aadl2.ComponentImplementation;
import org.osate.aadl2.NamedElement;
import org.osate.aadl2.instance.impl.SystemInstanceImpl;
import org.osate.annexsupport.AnnexUtil;

import com.rockwellcollins.atc.agree.agree.NestedDotID;
import com.rockwellcollins.atc.agree.agree.NodeDefExpr;
import com.rockwellcollins.atc.agree.analysis.AgreeException;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeNode;
import com.rockwellcollins.atc.agree.analysis.ast.AgreeVar;

import edu.umn.cs.crisys.safety.safety.FaultStatement;
import edu.umn.cs.crisys.safety.safety.SafetyContract;
import edu.umn.cs.crisys.safety.safety.SafetyContractSubclause;
import edu.umn.cs.crisys.safety.safety.SafetyPackage;
import edu.umn.cs.crisys.safety.safety.SpecStatement;
import jkind.lustre.ArrayAccessExpr;
import jkind.lustre.ArrayUpdateExpr;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.Node;
import jkind.lustre.RecordAccessExpr;
import jkind.lustre.RecordUpdateExpr;

public class SafetyUtil {

	public static NodeDefExpr getFaultNode(FaultStatement faultStatement) {
		// defName: faults.fail_to
		NestedDotID defName;
		// defNameSub: fail_to
		NamedElement defNameSub;

		defName = faultStatement.getFaultDefName();
		
		while(defName.getSub() != null){
			defName = defName.getSub();
		}
				
		defNameSub = defName.getBase();
		
		if (!(defNameSub instanceof NodeDefExpr)) {
			throw new IllegalArgumentException("Fault definition name must be an instance of NodeDefExpr."
					+" It is: "+defNameSub.getFullName()+".");
		}
		
		return (NodeDefExpr)defNameSub;
	}
	
	/*
	 * getSafetyAnnex
	 *
	 * @param EObject comp : The component classifier in question will contain
	 * the safety annex.
	 *
	 * @return SafetyContractSubclause : This is the safety annex.
	 *
	 * MWW: generalized to return a list in case of multiple annex instances.
	 * Also generalized the lookup type to support the different kinds of
	 * EObjects used by AGREE to represent references.
	 */
	public static List<SafetyContractSubclause> getSafetyAnnexes(AgreeNode node, boolean isTop) {
		List<SafetyContractSubclause> subclauses = new ArrayList<>();

		// find the component classifier
		// MWW: get the component instance from the AGREE program: less brittle than storing a global ref
		// in verify handler - esp. if the user selects a different impl.
		ComponentClassifier comp ;
		comp = node.compInst.getClassifier();
		if (!isTop && comp instanceof ComponentImplementation) {
			comp = ((ComponentImplementation)comp).getType();
		} else if (isTop && node.compInst instanceof SystemInstanceImpl) {
			comp = ((SystemInstanceImpl)node.compInst).getComponentImplementation(); 
		}


		// Grab the annex subclause using the safety package instance
		for (AnnexSubclause annex : AnnexUtil.getAllAnnexSubclauses(comp,
				SafetyPackage.eINSTANCE.getSafetyContractSubclause())) {

			// If we do have the safety contract subclause (and not the agree one)
			// Then get the component classifier from that and return the annex
			if (annex instanceof SafetyContractSubclause) {
				// In newer versions of osate the annex this returns annexes in
				// the type as well as the implementation. We want the annex in the
				// specific component.
				EObject container = annex.eContainer();
				while (!(container instanceof ComponentClassifier)) {
					container = container.eContainer();
				}
				if (container == comp) {
					subclauses.add((SafetyContractSubclause) annex);
				}
			}
		}
		return subclauses;
	}
	
	public static List<SpecStatement> collapseAnnexes(List<SafetyContractSubclause> annexes) {
		List<SpecStatement> allSpecs = new ArrayList<>();

		for (SafetyContractSubclause safetyannex : annexes) {
			// and the safety annex's contract
			SafetyContract contract = (SafetyContract) safetyannex.getContract();

			// The specs from the contract is where we can access the fault definitions
			allSpecs.addAll(contract.getSpecs());
		}

		return allSpecs;
	}

	public static AgreeVar findVar(List<AgreeVar> vars, String id) {
		for (AgreeVar v: vars) {
			if (v.id.equals(id)) {
				return v;
			}
		}
		return null;
	}
	
	public static Node findNode(String fnName, List<Node> nodes) {
		for (Node node : nodes) {
			if (node.id.equals(fnName)) {
				return node;
			}
		}
		return null;
		
	}
	
	public static Expr createNestedUpdateExpr(Expr path, Expr repl) {
		if(path instanceof IdExpr) {
			return repl;
		}
		else if(path instanceof RecordAccessExpr) {
			RecordAccessExpr rae = (RecordAccessExpr) path;
			// Base Case
			if(rae.record instanceof IdExpr) {
				return new RecordUpdateExpr(rae.record, rae.field, repl);
			} 
			// Recursive Case
			else {
				return createNestedUpdateExpr(rae.record, new RecordUpdateExpr(rae.record, rae.field, repl));
			}
			
		}else if(path instanceof ArrayAccessExpr){
			ArrayAccessExpr aae = (ArrayAccessExpr) path;
			// Base Case
			if(aae.array instanceof IdExpr) {
				return new ArrayUpdateExpr(aae.array, aae.index, repl);
			} 
			// Recursive Case
			else {
				return createNestedUpdateExpr(aae.array, new ArrayUpdateExpr(aae.array, aae.index, repl));
			}
		}else {
			// Change to SafetyException once the files are moved around.
			new AgreeException("Problem with record expression during safety analysis");
			return null;
		}
	}
}
