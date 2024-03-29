/*
 * Copyright (c) 2021, Collins Aerospace.
 * Developed with the sponsorship of Defense Advanced Research Projects Agency (DARPA).
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this data,
 * including any software or models in source or binary form, as well as any drawings, specifications,
 * and documentation (collectively "the Data"), to deal in the Data without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Data, and to permit persons to whom the Data is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Data.
 *
 * THE DATA IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT
 * LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS, SPONSORS, DEVELOPERS, CONTRIBUTORS, OR COPYRIGHT HOLDERS BE LIABLE
 * FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE DATA OR THE USE OR OTHER DEALINGS IN THE DATA.
 *
 * Generated by Xtext version 2.25.0.
 */

package edu.umn.cs.crisys.safety.validation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.CheckType;
import org.osate.aadl2.AadlPackage;
import org.osate.aadl2.AnnexSubclause;
import org.osate.aadl2.Classifier;
import org.osate.aadl2.ComponentImplementation;
import org.osate.aadl2.ComponentType;
import org.osate.aadl2.Element;
import org.osate.aadl2.ModelUnit;
import org.osate.aadl2.NamedElement;
import org.osate.aadl2.PublicPackageSection;
import org.osate.aadl2.Subcomponent;
import org.osate.aadl2.SystemType;
import org.osate.aadl2.impl.AadlPackageImpl;
import org.osate.aadl2.impl.DataImplementationImpl;
import org.osate.aadl2.impl.DataPortImpl;
import org.osate.aadl2.impl.DataTypeImpl;
import org.osate.aadl2.impl.PropertyImpl;

import com.rockwellcollins.atc.agree.agree.AgreeContract;
import com.rockwellcollins.atc.agree.agree.Arg;
import com.rockwellcollins.atc.agree.agree.BoolLitExpr;
import com.rockwellcollins.atc.agree.agree.DoubleDotRef;
import com.rockwellcollins.atc.agree.agree.EqStatement;
import com.rockwellcollins.atc.agree.agree.Expr;
import com.rockwellcollins.atc.agree.agree.IntLitExpr;
import com.rockwellcollins.atc.agree.agree.NamedElmExpr;
import com.rockwellcollins.atc.agree.agree.NodeDef;
import com.rockwellcollins.atc.agree.agree.PrevExpr;
import com.rockwellcollins.atc.agree.agree.PrimType;
import com.rockwellcollins.atc.agree.agree.RealLitExpr;
import com.rockwellcollins.atc.agree.agree.UnaryExpr;
import com.rockwellcollins.atc.agree.agree.impl.DoubleDotRefImpl;

import edu.umn.cs.crisys.safety.safety.ActivationStatement;
import edu.umn.cs.crisys.safety.safety.AnalysisBehavior;
import edu.umn.cs.crisys.safety.safety.AnalysisStatement;
import edu.umn.cs.crisys.safety.safety.DurationStatement;
import edu.umn.cs.crisys.safety.safety.EqValue;
import edu.umn.cs.crisys.safety.safety.FaultCountBehavior;
import edu.umn.cs.crisys.safety.safety.FaultStatement;
import edu.umn.cs.crisys.safety.safety.HWFaultStatement;
import edu.umn.cs.crisys.safety.safety.InputStatement;
import edu.umn.cs.crisys.safety.safety.IntervalEq;
import edu.umn.cs.crisys.safety.safety.OutputStatement;
import edu.umn.cs.crisys.safety.safety.ProbabilityBehavior;
import edu.umn.cs.crisys.safety.safety.ProbabilityStatement;
import edu.umn.cs.crisys.safety.safety.RangeEq;
import edu.umn.cs.crisys.safety.safety.SafetyContract;
import edu.umn.cs.crisys.safety.safety.SafetyContractSubclause;
import edu.umn.cs.crisys.safety.safety.SafetyInterval;
import edu.umn.cs.crisys.safety.safety.SafetyPackage;
import edu.umn.cs.crisys.safety.safety.SetEq;
import edu.umn.cs.crisys.safety.safety.SpecStatement;
import edu.umn.cs.crisys.safety.safety.TemporalConstraint;
import edu.umn.cs.crisys.safety.safety.TransientConstraint;
import edu.umn.cs.crisys.safety.safety.TriggerStatement;
import edu.umn.cs.crisys.safety.safety.impl.SafetyContractImpl;
import edu.umn.cs.crisys.safety.util.SafetyUtil;

/**
 * This class contains custom validation rules.
 *
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#validation
 * @since 2.0
 */
public class SafetyValidator extends AbstractSafetyValidator {

	private List<String> definedHWFaultsInProgram = new ArrayList<String>();

	/**
	 * (non-Javadoc)
	 * @see com.rockwellcollins.atc.agree.validation.AgreeJavaValidator#isResponsible(java.util.Map, org.eclipse.emf.ecore.EObject)
	 */
	@Override
	protected boolean isResponsible(Map<Object, Object> context, EObject eObject) {
		return (eObject.eClass().getEPackage() == SafetyPackage.eINSTANCE) || eObject instanceof AadlPackage;
	}

	@Check(CheckType.FAST)
	public void checkContractImpl(SafetyContractImpl contract) {
		List<String> faultNames = new ArrayList<String>();
		for (SpecStatement spec : contract.getSpecs()) {
			if (spec instanceof FaultStatement) {
				if (faultNames.contains(((FaultStatement) spec).getName())) {
					error(spec, "Fault name: " + ((FaultStatement) spec).getName() + " must be unique.");
				} else {
					faultNames.add(((FaultStatement) spec).getName());
				}
			}
		}
	}

	/**
	 * Checks description string, warning if empty, and checks that the
	 * fault node name is valid.
	 * @param specStmt
	 */
	@Check(CheckType.FAST)
	public void checkFaultSpecStmt(FaultStatement specStmt) {
		DoubleDotRef nodeName = specStmt.getFaultDefName();
		// Check on fault description
		if (specStmt.getStr().isEmpty()) {
			warning(specStmt, "Fault description is optional, but should " + "not be an empty string.");
		}
		// Check that the NamedElement (fault node name) is a valid node definition
		NamedElement finalNodeName = nodeName.getElm();
		if (!(finalNodeName instanceof NodeDef)) {
			error(nodeName, "The fault name must be a valid node definition.");
		}

	}

	/**
	 * Check for multiple analysis statements in SafetyContractImpl.
	 * Check behavior of analysis statements and values for n and
	 * probability.
	 * @param analysisStmt
	 */
	@Check(CheckType.FAST)
	public void checkAnalysisStatement(AnalysisStatement analysisStmt) {
		AnalysisBehavior behavior = analysisStmt.getBehavior();
		SafetyContractImpl contract = (SafetyContractImpl) analysisStmt.eContainer();
		boolean fcount = false;
		boolean probspec = false;
		for (SpecStatement spec : contract.getSpecs()) {
			if (spec instanceof AnalysisStatement) {
				if (((AnalysisStatement) spec).getBehavior() instanceof FaultCountBehavior) {
					fcount = true;
				} else if (((AnalysisStatement) spec).getBehavior() instanceof ProbabilityBehavior) {
					probspec = true;
				}
			}
		}
		if (fcount && probspec) {
			error(analysisStmt, "Only one analysis statement can be defined in the annex.");
		}
		if (behavior instanceof FaultCountBehavior) {
			FaultCountBehavior fc = (FaultCountBehavior) behavior;
			if (!testIntegerString(fc.getMaxFaults())) {
				error(analysisStmt, "Max N value must be a valid string representing a positive integer.");
			}
		} else if (behavior instanceof ProbabilityBehavior) {
			ProbabilityBehavior prob = (ProbabilityBehavior) behavior;
			if (!testProbabilityString(prob.getProbabilty())) {
				error(analysisStmt, "Probability must be a valid string between 0 and 1 inclusive.");
			}
		} else {
			error(analysisStmt,
					"Analysis behavior must be either 'analyze: max n fault' or 'analyze: probability r' for integer n and real number r.");
		}
	}

	/**
	 * Checks for empty hw fault description string and makes sure hw fault
	 * is declared in system type, not implementation.
	 *
	 * @param hwStmt
	 */
//	@Check(CheckType.FAST)
//	public void checkHWFaultStmt(HWFaultStatement hwStmt) {
//		// Check on fault description
//		if (hwStmt.getStr().isEmpty()) {
//			warning(hwStmt, "HW fault description is optional, but should not be an empty string.");
//		}
//		ComponentImplementation compImpl = EcoreUtil2.getContainerOfType(hwStmt, ComponentImplementation.class);
//		if (!(compImpl == null)) {
//			error(hwStmt, "HW faults can only be defined in component type, not implementations.");
//		}
//	}

	/**
	 * Checks that propagate stmt defined in implementation,
	 * source fault names correct for designated component names,
	 * destination faults correct for designated component names,
	 * and the source faults are indeed hw faults.
	 * @param pStmt
	 */
//	@Check(CheckType.FAST)
//	public void checkPropagateStmt(PropagateStatement pStmt) {
//		List<NamedElement> destinationList = pStmt.getDestComp_path();
//		List<NamedElement> sourceList = pStmt.getSrcComp_path();
//		List<String> sourceFaults = pStmt.getSrcFaultList();
//		List<String> destFaults = pStmt.getDestFaultList();
//		ComponentImplementation compImpl = EcoreUtil2.getContainerOfType(pStmt, ComponentImplementation.class);
//
//		// Test for propagate stmt in non-implementation
//		if (compImpl == null) {
//			error(pStmt, "Propagation statements can only be defined in component implementation");
//		} else {
//			// Get all faults and comp names in program
//			Map<String, List<String>> mapCompNameToFaultName = collectFaultsInProgram(compImpl);
//			// Check length of source and dest lists
//			if (sourceList.size() != sourceFaults.size()) {
//				error(pStmt, "Each source fault name must have an associated component name.");
//			} else if (destinationList.size() != destFaults.size()) {
//				error(pStmt, "Each destination fault name must have an associated component name.");
//			} else {
//				for(NamedElement dest : destinationList) {
//					if (!mapCompNameToFaultName.containsKey(dest.getName())) {
//						error(pStmt, "Component: " + dest.getName() + " is undefined in program.");
//					} else {
//						int i = destinationList.indexOf(dest);
//						List<String> faultList = mapCompNameToFaultName.get(dest.getName());
//						if (!faultList.contains(destFaults.get(i))) {
//							error(pStmt,
//									"Fault: " + destFaults.get(i) + " is undefined in component: " + dest.getName());
//						}
//					}
//				}
//				for(NamedElement source : sourceList) {
//					if (!mapCompNameToFaultName.containsKey(source.getName())) {
//						error(pStmt, "Component: " + source.getName() + " is undefined in program.");
//					} else {
//						int i = sourceList.indexOf(source);
//						List<String> faultList = mapCompNameToFaultName.get(source.getName());
//						if (!faultList.contains(sourceFaults.get(i))) {
//							error(pStmt, "Fault: " + sourceFaults.get(i) + " is undefined in component: "
//									+ source.getName());
//						}
//					}
//				}
//			}
//			// Check that all source faults are hw faults
//			for (String sf : sourceFaults) {
//				if (!definedHWFaultsInProgram.contains(sf)) {
//					error(pStmt,
//							"The fault: " + sf + " is not a HW fault. All source faults must be defined as HW Faults.");
//				}
//			}
//		}
//	}

	/**
	 * Checks that activation stmt defined in implementation,
	 * the agree var is defined in agree annex of this implementation,
	 * the type of agree var is boolean,
	 * and the faults are defined on the designated components.
	 * @param actStmt
	 */
	@Check(CheckType.FAST)
	public void checkActivationStatement(ActivationStatement actStmt) {
		NamedElement faultComp = actStmt.getFaultComp_Path();
		String faultName = actStmt.getFaultName();
		String agreeVarName = actStmt.getAgreeBoolVarName();
		ComponentImplementation compImpl = EcoreUtil2.getContainerOfType(actStmt, ComponentImplementation.class);

		// Test for propagate stmt in non-implementation
		if (compImpl == null) {
			error(actStmt, "Activation statements can only be defined in component implementation");
		} else {
			ComponentType compType = compImpl.getType();
			if (compType != null) {
				Map<String, List<String>> mapCompNameToFaultNames = collectFaultsInProgram(compImpl);
				List<EObject> assignableElements = collectAssignableElementsInTypeAndImpl(compImpl);
				boolean found = false;
				for (EObject assignableElement : assignableElements) {
					if (assignableElement instanceof NamedElement) {
						NamedElement namedEl = (NamedElement) assignableElement;
						if (agreeVarName.contentEquals(namedEl.getName())) {
							found = true;
							break;
						}
					}
				}
				if (found == false) {
					error(actStmt, "The eq statement: " + agreeVarName
							+ " does not match an eq statement defined in the Agree annex.");
				}
				// Check fault names and components
				if (!mapCompNameToFaultNames.containsKey(faultComp.getName())) {
					error(actStmt, "The fault component: " + faultComp.getName()
							+ " is not a valid component name for the fault: " + faultName);
				} else {
					if (!mapCompNameToFaultNames.get(faultComp.getName()).contains(faultName)) {
						error(actStmt, "The fault: " + faultName + " does not match a fault defined in component: "
								+ faultComp.getName());
					}
				}
			}
		}
	}

	/**
	 * Checks fault def name is valid,
	 * expressions passed into node match parameter types,
	 * and correct number of arguments passed in.
	 *
	 * @param inputs
	 */
	@Check(CheckType.FAST)
	public void checkInput(InputStatement inputs) {
		EObject container = inputs.eContainer();
		NamedElement defNameSub;
		List<Expr> exprList = inputs.getNom_conn();
		List<String> inputList = inputs.getFault_in();
		ArrayList<String> argNames = new ArrayList<String>();

		if (container instanceof FaultStatement) {
			FaultStatement faultStatement = (FaultStatement) container;
			DoubleDotRef defName = faultStatement.getFaultDefName();
			defNameSub = defName.getElm();

			// Make sure we have a NodeDefExpr
			if (defNameSub instanceof NodeDef) {
				List<Arg> nodeArgs = ((NodeDef) defNameSub).getArgs();
				for (Arg arg : nodeArgs) {
					argNames.add(arg.getFullName());
					if (arg.getType() instanceof DoubleDotRefImpl) {
						if ((((DoubleDotRefImpl) arg.getType()).getElm() instanceof PropertyImpl)
								|| (((DoubleDotRefImpl) arg.getType()).getElm() instanceof DataTypeImpl)) {
							error(inputs,
									"Fault node parameters are not recognized: a possible issue is that the keyword 'float' is used instead of 'real.'");
						}
					}
				}

				// If the sizes are accurate, make sure names match
				if (nodeArgs.size() - 1 == (inputList.size())) {
					// Go through input list and make sure each name is in the arg list
					for (String inputName : inputList) {
						if (!argNames.contains(inputName)) {
							error(inputs,
									"Input names must match fault node definition names. " + "The input name "
											+ inputName + " is not an input in the node definition. "
											+ "All possible input names are: " + argNames.toString());
						}
					}
				} else {
					argNames.remove("trigger");
					error(inputs, "With this fault definition, you must have " + (argNames.size() - 1) + " inputs."
							+ " These are called: " + argNames.toString());
				}
				if (inputListHasRepeats(inputList)) {
					error(inputs, "There is a repeated name in the input list: " + inputList.toString());
				}
				if (!checkInputTypes(exprList, nodeArgs)) {
					error(inputs, "Types of inputs do not match types of node parameters");
				}
			} else {
				// Not a node def expr
				error(defName, "Fault definition: " + defNameSub.getFullName()
						+ " must be a valid agree node definition name.");
			}
		} else {
			error(inputs, "Fault inputs must be defined within a fault statement.");
		}
	}

	/**
	 * Check return params of fault node against params listed
	 * in outputs.
	 * @param outputs
	 */
	@Check(CheckType.FAST)
	public void checkOutput(OutputStatement outputs) {
		List<String> faultsOut = outputs.getFault_out();
		EObject container = outputs.eContainer();
		List<Arg> retValues = new ArrayList<Arg>();
		List<String> returnNames = new ArrayList<String>();

		if (container instanceof FaultStatement) {
			retValues = getNodeReturnArgs((FaultStatement) container);
			if (retValues == null) {
				error(outputs, "Fault node definition is not valid for these outputs.");
			}
			for (Arg arg : retValues) {
				returnNames.add(arg.getFullName());
			}
			// Check sizes
			if (retValues.size() == (faultsOut.size())) {
				// Check names
				for (String outputName : faultsOut) {
					if (!returnNames.contains(outputName)) {
						error(outputs,
								"The output name: " + outputName + " is not an return value in the node definition. "
										+ "All possible output names are: " + returnNames.toString());
					}
				}
			} else {
				error(outputs, "The number of outputs must match the number of return values in the node definition."
						+ "No. of outputs must be " + returnNames.size() + ".");
			}
		} else {
			error(outputs, "Fault outputs must be in a fault statement.");
		}
		if (!checkOutputTypes(faultsOut, outputs.getNom_conn(), retValues)) {
			error(outputs, "The output types do not match the node return parameter types.");
		}
	}

	/**
	 * Check that only permanent is used and no interval is associated with it.
	 * @param durationStmt
	 */
	@Check
	public void checkDuration(DurationStatement durationStmt) {
		TemporalConstraint tc = durationStmt.getTc();
		if (tc instanceof TransientConstraint) {
			error(durationStmt, "Transient faults are currently not supported. Duration must be 'permanent.'");

		}
	}

	/**
	 * Check that probability defined on fault is valid.
	 * @param probStmt
	 */
	@Check(CheckType.FAST)
	public void checkProbability(ProbabilityStatement probStmt) {
		String prob = probStmt.getProbability();
		if (!testProbabilityString(prob)) {
			error(probStmt, "Probability must be valid decimal string between 0 and 1 inclusive.");
		}

	}

	/**
	 * Trigger stmts are not yet supported.
	 * @param triggerStmt
	 */
	@Check(CheckType.FAST)
	public void checkTriggerStatement(TriggerStatement triggerStmt) {
		error(triggerStmt, "Trigger statements are currently not supported.");
	}

	/**
	 * Utilize Agree validator to check arg of eq statement.
	 * @param eqStmt
	 */
	@Check(CheckType.FAST)
	public void checkEqStatement(EqValue eqStmt) {
		// For each arg in the list, call agree 'checkArg' method for validation
		List<Arg> args = eqStmt.getLhs();
		for (Arg arg : args) {
			checkArg(arg);
		}
	}

	/**
	 * Interval eq stmts:
	 * Checks:
	 * - Only primitive types supported for intervals.
	 * - The interval type matches high and low parameters of interval.
	 * - High and low parameters of interval are same type.
	 * @param intervalEq
	 */
	@Check(CheckType.FAST)
	public void checkIntervalEqStatement(IntervalEq intervalEq) {
		Arg arg = intervalEq.getLhs_int();
		String typeName = "";
		SafetyInterval interval = intervalEq.getInterv();
		Expr low = interval.getLow();
		Expr high = interval.getHigh();

		if (arg.getType() instanceof PrimType) {
			typeName = ((PrimType) arg.getType()).getName();
			if (typeName.equalsIgnoreCase("bool")) {
				error(arg, "Boolean intervals are not allowed. Only real or int intervals are supported.");
			}
		} else {
			error(arg, "The only types that are supported for intervals are real and int.");
		}

		// Negative values are allowed: hence the check for UnaryExpr
		if (low instanceof UnaryExpr) {
			UnaryExpr unex_low = (UnaryExpr) low;
			if (!(unex_low.getExpr() instanceof IntLitExpr) & !(unex_low.getExpr() instanceof RealLitExpr)) {
				error(low, "Only real and integer types are supported for intervals.");
			}
			if (high instanceof UnaryExpr) {
				UnaryExpr unex_high = (UnaryExpr) high;
				if (!(unex_high.getExpr() instanceof IntLitExpr) & !(unex_high.getExpr() instanceof RealLitExpr)) {
					error(high, "Only real and integer types are supported for intervals.");
				}
				testLowAndHighTypes(intervalEq, typeName, unex_low.getExpr(), unex_high.getExpr());
			} else {
				testLowAndHighTypes(intervalEq, typeName, unex_low.getExpr(), high);
			}
		} else {
			testLowAndHighTypes(intervalEq, typeName, low, high);
		}

	}

	/**
	 * Helper method to test high and low vals of interval statements.
	 *
	 * @param interval IntervalEq statement to be tested
	 * @param typeName The name declared as the type of interval
	 * @param low low parameter of interval
	 * @param high high parameter of interval
	 */
	private void testLowAndHighTypes(IntervalEq interval, String typeName, Expr low, Expr high) {
		if (low instanceof RealLitExpr & high instanceof RealLitExpr) {
			if (!typeName.contentEquals("real")) {
				error(interval, "Type of interval must match high and low parameters of interval.");
			}
		} else if (low instanceof IntLitExpr & high instanceof IntLitExpr) {
			if (!typeName.contentEquals("int")) {
				error(interval, "Type of interval must match high and low parameters of interval.");
			}
		} else {
			error(interval, "Type of interval must match high and low parameters of interval.");
		}
	}

	/**
	 * Set eq stmts not yet supported.
	 * @param setEq
	 */
	@Check(CheckType.FAST)
	public void checkSetEqStatement(SetEq setEq) {
		error(setEq, "Set eq statements are not currently supported.");
	}

	/**
	 * Range eq stmts not yet supported.
	 * @param range
	 */
	@Check
	public void checkRangeEqStatement(RangeEq range) {
		error(range, "Range eq statements are not currently supported.");
	}

	/**
	 * Collects all faults in the program. First through the subcomponents
	 * defined for this implementation and then goes through each included package
	 * and gathers those.
	 * @param compImpl
	 * @return Map<String, List<String>> mapping from component instance name
	 * to a list of associated faults.
	 */
	private Map<String, List<String>> collectFaultsInProgram(ComponentImplementation compImpl) {
		Map<String, List<String>> mapCompNameToFaultNames = new HashMap<String, List<String>>();
		mapCompNameToFaultNames.putAll(collectFaultsFromSubcomponents(compImpl));
		mapCompNameToFaultNames.putAll(collectFaultsFromPackages(compImpl));
		return mapCompNameToFaultNames;
	}

	/**
	 * Collect all subcomponents of this component implementation and
	 * iterate through them finding the faults defined for each.
	 * @param compImpl
	 * @return Map<String, List<String>> mapping from component instance name
	 * to a list of associated faults.
	 */
	private Map<String, List<String>> collectFaultsFromSubcomponents(ComponentImplementation compImpl) {
		Map<String, List<String>> mapCompNameToFaultNames = new HashMap<String, List<String>>();
		for (Subcomponent sub : compImpl.getAllSubcomponents()) {
			if (sub.getComponentType() instanceof SystemType) {
				List<AnnexSubclause> annexes = ((SystemType) sub.getComponentType()).getOwnedAnnexSubclauses();
				for (AnnexSubclause annex : annexes) {
					if (annex.getName().contains("safety")) {
						for (Element child : annex.getChildren()) {
							List<String> faultNames = new ArrayList<String>();
							if (child instanceof SafetyContractSubclause) {
								SafetyContractSubclause safetyChild = (SafetyContractSubclause) child;
								SafetyContract cont = (SafetyContract) safetyChild.getContract();
								faultNames.addAll(getFaultNamesFromSpecs(cont.getSpecs()));
								mapCompNameToFaultNames.put(sub.getName(), faultNames);
							}
						}
					}
				}
			}
		}
		return mapCompNameToFaultNames;

	}

	/**
	 * Get packages defined in this component impl and collect all faults from
	 * all included packages.
	 * @param compImpl
	 * @return Map<String, List<String>> mapping from component instance name
	 * to a list of associated faults.
	 */
	private Map<String, List<String>> collectFaultsFromPackages(ComponentImplementation compImpl) {
		Map<String, List<String>> mapCompNameToFaultNames = new HashMap<String, List<String>>();
		List<AadlPackageImpl> packages = new ArrayList<AadlPackageImpl>();
		AadlPackageImpl aadlPackage = getAadlPackageImpl(compImpl);
		packages.add(aadlPackage);
		PublicPackageSection pps = aadlPackage.getOwnedPublicSection();
		// Collect all imported packages and iterate through
		// annexes to find safety annexes with faults.
		List<ModelUnit> imports = pps.getImportedUnits();
		for (ModelUnit imp : imports) {
			if (imp instanceof AadlPackageImpl) {
				packages.add((AadlPackageImpl) imp);
			}
		}
		for (AadlPackageImpl aadlPack : packages) {
			PublicPackageSection pub = aadlPack.getPublicSection();
			for (Classifier cl : pub.getOwnedClassifiers()) {
				if (cl instanceof SystemType) {
					for (AnnexSubclause sub : cl.getOwnedAnnexSubclauses()) {
						if (sub.getName().contains("safety")) {
							for (Element child : sub.getChildren()) {
								List<String> faultNames = new ArrayList<String>();
								if (child instanceof SafetyContractSubclause) {
									SafetyContractSubclause safetyChild = (SafetyContractSubclause) child;
									SafetyContract cont = (SafetyContract) safetyChild.getContract();
									faultNames.addAll(getFaultNamesFromSpecs(cont.getSpecs()));
									mapCompNameToFaultNames.put(cl.getName(), faultNames);
								}
							}
						}
					}
				}
			}
		}
		return mapCompNameToFaultNames;
	}

	/**
	 * Gets all agree vars defined in this component type and implementation.
	 * @param compImpl
	 * @return List<String> of agree var names.
	 */
	private List<EObject> collectAssignableElementsInTypeAndImpl(ComponentImplementation compImpl) {
		List<EObject> assignableElements = new ArrayList<>();
		// Get impl contract
		List<AgreeContract> typeContracts = EcoreUtil2.getAllContentsOfType(compImpl, AgreeContract.class);
		// And now check component type contract
		typeContracts.addAll(EcoreUtil2.getAllContentsOfType(compImpl.getType(), AgreeContract.class));
		for (AgreeContract ac : typeContracts) {
			assignableElements.addAll(EcoreUtil2.getAllContentsOfType(ac, EqStatement.class)
					.stream()
					.map(eq -> eq.getLhs())
					.flatMap(List::stream)
					.collect(Collectors.toList()));
		}
		return assignableElements;
	}

	/**
	 * Collects the AadlPackageImpl of this component impl.
	 * @param compImpl
	 * @return AadlPackageImpl of this component impl.
	 */
	private AadlPackageImpl getAadlPackageImpl(EObject compImpl) {
		EObject cont = compImpl.eContainer();
		if (cont instanceof AadlPackageImpl) {
			return (AadlPackageImpl) cont;
		} else {
			return getAadlPackageImpl(cont);
		}
	}

	/**
	 * Collects fault names from the safety spec statements.
	 * @param specs Safety SpecStatements
	 * @return List<String> of all fault names defined in these specs.
	 */
	private List<String> getFaultNamesFromSpecs(List<SpecStatement> specs) {
		List<String> faultNames = new ArrayList<String>();
		for (SpecStatement sp : specs) {
			if (sp instanceof FaultStatement) {
				FaultStatement fs = (FaultStatement) sp;
				faultNames.add(fs.getName());
			} else if (sp instanceof HWFaultStatement) {
				HWFaultStatement hwfs = (HWFaultStatement) sp;
				faultNames.add(hwfs.getName());
				if (!definedHWFaultsInProgram.contains(hwfs.getName())) {
					definedHWFaultsInProgram.add(hwfs.getName());
				}
			}
		}
		return faultNames;
	}

	/**
	 * Check that input types between these lists match.
	 * Assume lists are in order.
	 * @param exprList
	 * @param nodeArgs
	 * @return bool : valid or not
	 */
	private boolean checkInputTypes(List<Expr> exprList, List<Arg> nodeArgs) {
		// TODO: If I cannot access the type (e.g., complex nested
		// type), the string remains empty. If string is empty, I let
		// the type check say "all is well." This needs to be addressed.
		for (int i = 0; i < exprList.size(); i++) {
			String argType = getArgType(nodeArgs.get(i));
			String exprType = getExprType(exprList.get(i));
			if (!argType.equals(exprType) && !exprType.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Check that output types between these lists match.
	 * Assume lists are in order.
	 * @param faultsOut fault node output names
	 * @param nom_conn nominal output connections
	 * @param retValues arguments of return values of fault node
	 * @return valid
	 */
	private boolean checkOutputTypes(List<String> faultsOut, EList<NamedElement> nom_conn, List<Arg> retValues) {
		// TODO: If I cannot access the type (e.g., complex nested
		// type), the string remains empty. If string is empty, I let
		// the type check say "all is well." This needs to be addressed.
		String type = "";
		for (int i = 0; i < faultsOut.size(); i++) {
			if (nom_conn.get(i) instanceof DataPortImpl) {
				type = getDataPortType((DataPortImpl) nom_conn.get(i));
				String argType = getArgType(retValues.get(i));
				if (type.equals(argType) || (!type.isEmpty() || !argType.isEmpty())) {
					return true;
				} else {
					return false;
				}
			} else if (nom_conn.get(i) instanceof Arg) {
				type = getArgType((Arg) nom_conn.get(i));
				String argType = getArgType(retValues.get(i));
				if (type.equals(argType) && !type.isEmpty() && !argType.isEmpty()) {
					return true;
				} else {
					return false;
				}
			}
		}
		return true;
	}

	private String getArgType(Arg arg) {
		String type = "";
		if (arg.getType() instanceof PrimType) {
			type = ((PrimType) arg.getType()).getName();
		}
		return type;
	}

	private String getExprType(Expr ex) {
		String type = "";
		if (ex instanceof NamedElmExpr) {
			NamedElmExpr elm = (NamedElmExpr) ex;
			if (elm.getElm() instanceof Arg) {
				return getArgType((Arg) elm.getElm());
			} else if (elm.getElm() instanceof DataPortImpl) {
				type = getDataPortType((DataPortImpl) elm.getElm());
			}
		} else if (ex instanceof BoolLitExpr) {
			type = "bool";
		} else if (ex instanceof RealLitExpr) {
			type = "real";
		} else if (ex instanceof IntLitExpr) {
			type = "int";
		} else if (ex instanceof PrevExpr) {
			Expr init = ((PrevExpr) ex).getInit();
			if (init instanceof BoolLitExpr) {
				type = "bool";
			} else if (init instanceof RealLitExpr) {
				type = "real";
			} else if (init instanceof IntLitExpr) {
				type = "int";
			}
		}
		return type;
	}

	private String getDataPortType(DataPortImpl dataport) {
		String type = "";
		if (dataport.basicGetFeatureClassifier() instanceof DataTypeImpl) {
			DataTypeImpl datatype = (DataTypeImpl) dataport.basicGetDataFeatureClassifier();
			String typePort = datatype.getName();
			if (typePort.contains("Float")) {
				type = "real";
			} else if (typePort.contains("Bool")) {
				type = "bool";
			} else if (typePort.contains("Int")) {
				type = "int";
			}
		} else if (dataport.basicGetFeatureClassifier() instanceof DataImplementationImpl) {
			DataImplementationImpl datatype = (DataImplementationImpl) dataport.basicGetDataFeatureClassifier();
			String typePort = datatype.getName();
			if (typePort.contains("Float")) {
				type = "real";
			} else if (typePort.contains("Bool")) {
				type = "bool";
			} else if (typePort.contains("Int")) {
				type = "int";
			}
		}
		return type;
	}

	/**
	 * Collects all agree vars defined in the spec statements parameter.
	 * @param specs The Agree SpecStatements
	 * @return List<String> of all agree var names in these specs.
	 */
	private List<Arg> populateAgreeVarList(List<com.rockwellcollins.atc.agree.agree.SpecStatement> specs) {
		List<Arg> agreeVarList = new ArrayList<Arg>();
		for (com.rockwellcollins.atc.agree.agree.SpecStatement sp : specs) {
			if (sp instanceof EqStatement) {
				for (Arg left : ((EqStatement) sp).getLhs()) {
					agreeVarList.add(left);
				}
			}
		}
		return agreeVarList;
	}

	/**
	 * Method finds return values of fault node given a fault statement.
	 * @param fStmt
	 * @return List<Arg> of return arguments.
	 */
	private List<Arg> getNodeReturnArgs(FaultStatement fStmt) {
		List<Arg> returnArgs = new ArrayList<Arg>();
		NodeDef nodeDef = null;
		try {
			nodeDef = SafetyUtil.getFaultNode(fStmt);
		} catch (IllegalArgumentException e) {
			return null;
		}
		// Get the return values
		if (nodeDef != null) {
			returnArgs = nodeDef.getRets();
		} else {
			return null;
		}
		return returnArgs;
	}

	/**
	 * Tests probability string: returns false if number format exception
	 * or prob value falls outside [0,1].
	 * @param prob
	 * @return bool : valid or not
	 */
	private boolean testProbabilityString(String prob) {
		try {
			double p = Double.parseDouble(prob);
			if ((p < 0.0) || (p > 1.0)) {
				return false;
			} else {
				return true;
			}
		} catch (NumberFormatException nfe) {
			return false;
		}
	}

	/**
	 * Tests integer string: returns false if number format exception
	 * or negative integer.
	 * @param integer
	 * @return bool : valid or not
	 */
	private boolean testIntegerString(String integer) {
		try {
			int n = Integer.parseInt(integer);
			if (n < 0) {
				return false;
			} else {
				return true;
			}
		} catch (NumberFormatException nfe) {
			return false;
		}
	}

	/**
	 * Checks to see if any parameters have been repeated when assigning
	 * inputs in fault definition.
	 *
	 * @param inputs to the parameter
	 * @return bool: true if repeats found, false otherwise
	 */
	private boolean inputListHasRepeats(List<String> inputs) {
		boolean hasRepeats = false;
		for (int i = 0; i < inputs.size(); i++) {
			for (int j = i + 1; j < inputs.size(); j++) {
				if (inputs.get(i).equals(inputs.get(j))) {
					hasRepeats = true;
					break;
				}
			}
		}
		return hasRepeats;
	}

}
