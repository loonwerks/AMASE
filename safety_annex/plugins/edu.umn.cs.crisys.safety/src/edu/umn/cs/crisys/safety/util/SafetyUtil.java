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
import edu.umn.cs.crisys.safety.safety.FaultSubcomponent;
import edu.umn.cs.crisys.safety.safety.OutputStatement;
import edu.umn.cs.crisys.safety.safety.SafetyContract;
import edu.umn.cs.crisys.safety.safety.SafetyContractSubclause;
import edu.umn.cs.crisys.safety.safety.SafetyPackage;
import edu.umn.cs.crisys.safety.safety.SpecStatement;
import jkind.lustre.ArrayAccessExpr;
import jkind.lustre.ArrayUpdateExpr;
import jkind.lustre.BinaryExpr;
import jkind.lustre.BinaryOp;
import jkind.lustre.BoolExpr;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.NamedType;
import jkind.lustre.Node;
import jkind.lustre.RecordAccessExpr;
import jkind.lustre.RecordUpdateExpr;
import jkind.lustre.Type;
import jkind.lustre.builders.NodeBuilder;

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

	// Create Lustre node for asymmetric fault connection nodes.
	// Method parameter "node" corresponds to the "sender" node
	// that has the fan out connections.
	// This is needed to access the type of connection for input/output
	// of this node.
	public static Node createCommNode(AgreeNode node, FaultStatement fstmt, int connNumber) {

//		// 1. Create unique node name
//
//		AgreeNodeBuilder newNode = new AgreeNodeBuilder("asym_conn_node_"+connNumber);
//
//		// 2. Get the output/input type from the node and the fstmt
//		List<AgreeVar> nodeOutputs = node.outputs;
//		AgreeVar outputOfInterest = null;
//		// Assume asymmetric fault first in list.
//		// Will have to display this to user somewhere.
//		List<NestedDotID> nomFaultConn = new ArrayList<NestedDotID>();
//
//		for(FaultSubcomponent fs : fstmt.getFaultDefinitions()) {
//			if(fs instanceof OutputStatement) {
//				nomFaultConn = ((OutputStatement) fs).getNom_conn();
//			}
//		}
//
//		for(AgreeVar agreeVar : nodeOutputs) {
//			String temp = agreeVar.id;
//			if(temp.contentEquals(nomFaultConn.get(0).getBase().getName())) {
//				System.out.println("Found it: "+temp+" and "+nomFaultConn.get(0).getBase().getName());
//				outputOfInterest = agreeVar;
//			}
//		}
//
//		// The output from the sender node is the input to this new node.
//		// We must only change the id string to reflect that it is now an input.
//		AgreeVar connNodeIn = new AgreeVar("commNode" + connNumber + "_input", outputOfInterest.type,
//				outputOfInterest.reference, outputOfInterest.compInst, outputOfInterest.featInst);
//		newNode.addInput(connNodeIn);
//
//		// The output of the new node is the input to what the sender
//		// is connecting to (it takes sender's outputs as its own).
//		AgreeVar connNodeOut = new AgreeVar("commNode" + connNumber + "_output", outputOfInterest.type,
//				outputOfInterest.reference, outputOfInterest.compInst, outputOfInterest.featInst);
//		newNode.addOutput(connNodeOut);
//
//		// New agree statement (guarantee)
//		IdExpr in = new IdExpr("commNode_input");
//		IdExpr out = new IdExpr("commNode_output");
//
//		Expr binEx = new BinaryExpr(out, BinaryOp.EQUAL, in);
//		AgreeStatement guar = new AgreeStatement("Comm node " + connNumber + ": in = out", binEx,
//				outputOfInterest.reference);
//		newNode.addGuarantee(guar);
//
//		return newNode.build();

		// 1. Create unique node name

		NodeBuilder newNode = new NodeBuilder("asym_conn_node_" + connNumber);

		// 2. Get the output/input type from the node and the fstmt
		List<AgreeVar> nodeOutputs = node.outputs;
		AgreeVar outputOfInterest = null;
		// Assume asymmetric fault first in list.
		// Will have to display this to user somewhere.
		List<NestedDotID> nomFaultConn = new ArrayList<NestedDotID>();

		for(FaultSubcomponent fs : fstmt.getFaultDefinitions()) {
			if(fs instanceof OutputStatement) {
				nomFaultConn = ((OutputStatement) fs).getNom_conn();
			}
		}

		for(AgreeVar agreeVar : nodeOutputs) {
			String temp = agreeVar.id;
			if(temp.contentEquals(nomFaultConn.get(0).getBase().getName())) {
				System.out.println("Found it: "+temp+" and "+nomFaultConn.get(0).getBase().getName());
				outputOfInterest = agreeVar;
			}
		}

		// Now the same type on the AgreeNode outputOfInterest
		// is the same as what we will create for the type of
		// both input and output of commNode.
		Type type = outputOfInterest.type;

		newNode = createInputForCommNode(newNode, outputOfInterest.type);
		newNode = createOutputForCommNode(newNode);
		newNode = createLocalsForCommNode(newNode);
		newNode = createEquationsForCommNode(newNode);

		return newNode.build();
	}

	/*
	 * creates inputs for new communication node.
	 */
	public static NodeBuilder createInputForCommNode(NodeBuilder node, Type type) {

		node.createInput("commNode_input", type);
		node.createInput("commNode_output", type);
		node.createInput("__ASSUME__HIST", NamedType.BOOL);
		node.createInput("time", NamedType.REAL);
		return node;
	}

	/*
	 * creates outputs for new communication node.
	 */
	public static NodeBuilder createOutputForCommNode(NodeBuilder node) {

		node.createOutput("__ASSERT", NamedType.BOOL);
		return node;
	}

	/*
	 * creates locals for new communication node.
	 */
	public static NodeBuilder createLocalsForCommNode(NodeBuilder node) {

		node.createLocal("__GUARANTEE0", NamedType.BOOL);
		return node;
	}

	/*
	 * creates equations for new communication node.
	 */
	public static NodeBuilder createEquationsForCommNode(NodeBuilder node) {

		// assign guarantee0 : out = in
		Expr binEx = new BinaryExpr(new IdExpr("commNode_output"), BinaryOp.EQUAL, new IdExpr("commNode_input"));
		IdExpr guar = new IdExpr("__GUARANTEE0");
		node.addEquation(guar, binEx);

		// Assign assert
		BoolExpr trueExpr = new BoolExpr(true);
		IdExpr assumeHist = new IdExpr("__ASSUME__HIST");
		BinaryExpr binEx1 = new BinaryExpr(guar, BinaryOp.AND, trueExpr);
		BinaryExpr binEx2 = new BinaryExpr(assumeHist, BinaryOp.IMPLIES, binEx1);
		BinaryExpr binEx3 = new BinaryExpr(trueExpr, BinaryOp.AND, binEx2);
		BinaryExpr binEx4 = new BinaryExpr(trueExpr, BinaryOp.AND, binEx3);
		node.addEquation(new IdExpr("__ASSERT"), binEx4);

		return node;
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
}
