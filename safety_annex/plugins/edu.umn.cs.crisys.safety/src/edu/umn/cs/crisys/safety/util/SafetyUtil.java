package edu.umn.cs.crisys.safety.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.osate.aadl2.AnnexSubclause;
import org.osate.aadl2.Classifier;
import org.osate.aadl2.ComponentClassifier;
import org.osate.aadl2.ComponentImplementation;
import org.osate.aadl2.ComponentType;
import org.osate.aadl2.NamedElement;
import org.osate.aadl2.impl.ComponentImplementationImpl;
import org.osate.aadl2.instance.impl.SystemInstanceImpl;
import org.osate.annexsupport.AnnexUtil;

import com.rockwellcollins.atc.agree.AgreeTypeSystem;
import com.rockwellcollins.atc.agree.agree.DoubleDotRef;
import com.rockwellcollins.atc.agree.agree.NodeDef;
import com.rockwellcollins.atc.agree.analysis.AgreeException;
import com.rockwellcollins.atc.agree.analysis.AgreeUtils;
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
import jkind.lustre.NamedType;
import jkind.lustre.Node;
import jkind.lustre.Program;
import jkind.lustre.RecordAccessExpr;
import jkind.lustre.RecordUpdateExpr;
import jkind.lustre.Type;
import jkind.lustre.builders.ProgramBuilder;
import jkind.translation.DistributePres;
import jkind.translation.FlattenPres;
import jkind.translation.InlineNodeCalls;
import jkind.translation.OrderEquations;

public class SafetyUtil {

	/**
	 * Gets a fault node from a fault statement.
	 * @param faultStatement fault definition statement created by user.
	 * @return NodeDef object referenced in fault statement
	 */
	public static NodeDef getFaultNode(FaultStatement faultStatement) {
		// defName: faults.fail_to
		DoubleDotRef defName = faultStatement.getFaultDefName();
		// defNameSub: fail_to
		NamedElement defNameField = null;
		if (defName != null) {
			defNameField = defName.getElm();
		} else {
			new Exception("Fault definition name in " + faultStatement.getName() + " is not allowed.");
		}
		if (!(defNameField instanceof NodeDef)) {
			new Exception(
					"Fault node definition is not working correctly. "
							+ "A possible reason is that the project needs to be cleaned.");
		} else {
			return (NodeDef) defNameField;
		}

		return null;
	}



	/**
	 * getSafetyAnnexes from an AgreeNode
	 *
	 * @param EObject comp : The component classifier in question will contain
	 * the safety annex.
	 * @return SafetyContractSubclause : This is the safety annex.
	 *
	 * MWW: generalized to return a list in case of multiple annex instances.
	 * Also generalized the lookup type to support the different kinds of
	 * EObjects used by AGREE to represent references.
	 */
	public static List<SafetyContractSubclause> getSafetyAnnexes(AgreeNode node, boolean isTop) {
		List<SafetyContractSubclause> subclauses = new ArrayList<>();
		// get the component instance from the AGREE program: less brittle than storing a global ref
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

	/**
	 * From list of classifiers (aadl), find annexes and see if safety
	 * annex is one of them.
	 * @param classifiers in AADL program
	 * @return safety annex found
	 */
	public static boolean containsSafetyAnnex(List<Classifier> classifiers) {
		boolean foundSafetyAnnex = false;
		for (Classifier cl : classifiers) {
			// Get impl of this level
			if (cl instanceof ComponentImplementationImpl) {
				List<AnnexSubclause> asList = cl.getOwnedAnnexSubclauses();
				for (AnnexSubclause as : asList) {
					if (as.getName().equalsIgnoreCase("safety")) {
						foundSafetyAnnex = true;
					}
				}
			}
		}
		return foundSafetyAnnex;
	}

	/**
	 *
	 * @param subComp Subcomponent: is the subcomponent to check for associated safety annex
	 * @return boolean: true if safety annex found, else false
	 * This is found by calling typeContainsSafetyAnnex(Subcomponent)
	 *
	 */
	public static boolean containsSafetyAnnex(ComponentImplementation compImpl) {

		if (compImpl != null) {
			EList<AnnexSubclause> annexes = AnnexUtil.getAllAnnexSubclauses(compImpl,
					SafetyPackage.eINSTANCE.getSafetyContractSubclause());
			for (AnnexSubclause annex : annexes) {
				EObject container = AgreeUtils.getClosestContainerOfType(annex, ComponentImplementation.class);
				if (compImpl.getName().equals(((ComponentImplementation) container).getName())) {
					return true;
				}
			}
		}
		return typeContainsSafetyAnnex(compImpl);
	}

	/**
	 *  typeContainsSafetyAnnex
	 * @param subComp Subcomponent: is the subcomponent to check for associated safety annex
	 * @return boolean true if annex found, else false
	 *
	 */
	public static boolean typeContainsSafetyAnnex(ComponentImplementation compImpl) {

		ComponentType compType = compImpl.getType();
		if (compType != null) {
			EList<AnnexSubclause> annexes = AnnexUtil.getAllAnnexSubclauses(compType,
					SafetyPackage.eINSTANCE.getSafetyContractSubclause());
			for (AnnexSubclause annex : annexes) {
				EObject container = AgreeUtils.getClosestContainerOfType(annex, ComponentType.class);
				if (compType.getName().equals(((ComponentType) container).getName())) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Return a list of spec statements from a list of annexes
	 * @param annexes Safety contracts
	 * @return list of spec stmts
	 */
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

	/**
	 * Given a list of AgreeVars, find one by id
	 * @param vars list of AgreeVars
	 * @param id string name of the var of interest
	 * @return var of interest (name matches id)
	 */
	public static AgreeVar findVar(List<AgreeVar> vars, String id) {
		for (AgreeVar v: vars) {
			if (v.id.equals(id)) {
				return v;
			}
		}
		return null;
	}

	/**
	 * Find a node based on string id
	 * @param name Name of node to find
	 * @param nodes list of nodes
	 * @return node with matching name
	 */
	public static Node findNode(String name, List<Node> nodes) {
		for (Node node : nodes) {
			if (node.id.equals(name)) {
				return node;
			}
		}
		return null;

	}

	/**
	 * Create a nested update expression: recursive
	 * Possibilities:
	 * (1) Path is IdExpr -> return path
	 * (2) path is RecordAccessExpr -> record is either id or recordUpdateExpr
	 * 		recurse over record and field
	 * (3) Path is ArrayAccessExpr
	 * 		if path is id -> return idExpr
	 * 		else recurse
	 * Base case: record is IdExpr or RecordUpdateExpr
	 * @param path
	 * @param repl
	 * @return nested expression
	 */
	public static Expr createNestedUpdateExpr(Expr path, Expr repl) {
		if(path instanceof IdExpr) {
			return repl;
		} else if (path instanceof RecordAccessExpr) {
			RecordAccessExpr rae = (RecordAccessExpr) path;
			// Base Case: Must have both IdExpr or RecordUpdateExpr here.
			if ((rae.record instanceof IdExpr) || (rae.record instanceof RecordUpdateExpr)) {
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


	/**
	 * Gets primitive Lustre type from agree type
	 * @param agreeType
	 * @return real, int, bool NamedType
	 */
	public static Type getLustreType(com.rockwellcollins.atc.agree.AgreeTypeSystem.TypeDef agreeType) {
		if (agreeType == AgreeTypeSystem.Prim.IntTypeDef) {
			return NamedType.INT;
		} else if (agreeType == AgreeTypeSystem.Prim.RealTypeDef) {
			return NamedType.REAL;
		} else if (agreeType == AgreeTypeSystem.Prim.BoolTypeDef) {
			return NamedType.BOOL;
		} else {
			return null;
		}
	}

	public static AgreeNode faultyAgreeNodeFromNominalNode(List<AgreeNode> nodes, AgreeNode sourceNode) {
		if (sourceNode == null) {
			return null;
		}
		for (AgreeNode node : nodes) {
			if (sourceNode.id.equals(node.id)) {
				return node;
			}
		}
		return null;
	}

	public static Node inlineNodeCallsFlattenPres(Node lustreNode, Program lustreProgram) {

		List<Node> nodes = new ArrayList<>();

		// add all nodes except for the given lustreNode
		for (Node node : lustreProgram.nodes) {
			if (!node.id.equals(lustreNode.id)) {
				nodes.add(node);
			}
		}

		// this node needs to be the last in the list
		// so that it is set as the main node in the program
		nodes.add(lustreNode);

		Program program = new ProgramBuilder().addTypes(lustreProgram.types).addNodes(nodes).build();

		program = InlineNodeCalls.program(program);
		program = FlattenPres.program(program);

		Node main = DistributePres.node(program.getMainNode());
		main = OrderEquations.node(main);

		return main;
	}
}
