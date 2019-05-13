/**
 */
package edu.umn.cs.crisys.safety.safety.util;

import edu.umn.cs.crisys.safety.safety.*;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

import org.osate.aadl2.Element;
import org.osate.aadl2.ModalElement;
import org.osate.aadl2.NamedElement;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see edu.umn.cs.crisys.safety.safety.SafetyPackage
 * @generated
 */
public class SafetySwitch<T> extends Switch<T>
{
  /**
   * The cached model package
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static SafetyPackage modelPackage;

  /**
   * Creates an instance of the switch.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public SafetySwitch()
  {
    if (modelPackage == null)
    {
      modelPackage = SafetyPackage.eINSTANCE;
    }
  }

  /**
   * Checks whether this is a switch for the given package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param ePackage the package in question.
   * @return whether this is a switch for the given package.
   * @generated
   */
  @Override
  protected boolean isSwitchFor(EPackage ePackage)
  {
    return ePackage == modelPackage;
  }

  /**
   * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the first non-null result returned by a <code>caseXXX</code> call.
   * @generated
   */
  @Override
  protected T doSwitch(int classifierID, EObject theEObject)
  {
    switch (classifierID)
    {
      case SafetyPackage.ANNEX_LIBRARY:
      {
        AnnexLibrary annexLibrary = (AnnexLibrary)theEObject;
        T result = caseAnnexLibrary(annexLibrary);
        if (result == null) result = caseAadl2_AnnexLibrary(annexLibrary);
        if (result == null) result = caseNamedElement(annexLibrary);
        if (result == null) result = caseElement(annexLibrary);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case SafetyPackage.ANNEX_SUBCLAUSE:
      {
        AnnexSubclause annexSubclause = (AnnexSubclause)theEObject;
        T result = caseAnnexSubclause(annexSubclause);
        if (result == null) result = caseAadl2_AnnexSubclause(annexSubclause);
        if (result == null) result = caseModalElement(annexSubclause);
        if (result == null) result = caseNamedElement(annexSubclause);
        if (result == null) result = caseElement(annexSubclause);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case SafetyPackage.SAFETY_LIBRARY:
      {
        SafetyLibrary safetyLibrary = (SafetyLibrary)theEObject;
        T result = caseSafetyLibrary(safetyLibrary);
        if (result == null) result = caseAnnexLibrary(safetyLibrary);
        if (result == null) result = caseAadl2_AnnexLibrary(safetyLibrary);
        if (result == null) result = caseNamedElement(safetyLibrary);
        if (result == null) result = caseElement(safetyLibrary);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case SafetyPackage.SAFETY_SUBCLAUSE:
      {
        SafetySubclause safetySubclause = (SafetySubclause)theEObject;
        T result = caseSafetySubclause(safetySubclause);
        if (result == null) result = caseAnnexSubclause(safetySubclause);
        if (result == null) result = caseAadl2_AnnexSubclause(safetySubclause);
        if (result == null) result = caseModalElement(safetySubclause);
        if (result == null) result = caseNamedElement(safetySubclause);
        if (result == null) result = caseElement(safetySubclause);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case SafetyPackage.CONTRACT:
      {
        Contract contract = (Contract)theEObject;
        T result = caseContract(contract);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case SafetyPackage.SPEC_STATEMENT:
      {
        SpecStatement specStatement = (SpecStatement)theEObject;
        T result = caseSpecStatement(specStatement);
        if (result == null) result = caseAgree_SpecStatement(specStatement);
        if (result == null) result = caseElement(specStatement);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case SafetyPackage.ANALYSIS_BEHAVIOR:
      {
        AnalysisBehavior analysisBehavior = (AnalysisBehavior)theEObject;
        T result = caseAnalysisBehavior(analysisBehavior);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case SafetyPackage.FAULT_SUBCOMPONENT:
      {
        FaultSubcomponent faultSubcomponent = (FaultSubcomponent)theEObject;
        T result = caseFaultSubcomponent(faultSubcomponent);
        if (result == null) result = caseElement(faultSubcomponent);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case SafetyPackage.HW_FAULT_SUBCOMPONENT:
      {
        HWFaultSubcomponent hwFaultSubcomponent = (HWFaultSubcomponent)theEObject;
        T result = caseHWFaultSubcomponent(hwFaultSubcomponent);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case SafetyPackage.BYZANTINE_FAULT_SUBCOMPONENT:
      {
        ByzantineFaultSubcomponent byzantineFaultSubcomponent = (ByzantineFaultSubcomponent)theEObject;
        T result = caseByzantineFaultSubcomponent(byzantineFaultSubcomponent);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case SafetyPackage.PROPAGATION_TYPE_CONSTRAINT:
      {
        PropagationTypeConstraint propagationTypeConstraint = (PropagationTypeConstraint)theEObject;
        T result = casePropagationTypeConstraint(propagationTypeConstraint);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case SafetyPackage.TEMPORAL_CONSTRAINT:
      {
        TemporalConstraint temporalConstraint = (TemporalConstraint)theEObject;
        T result = caseTemporalConstraint(temporalConstraint);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case SafetyPackage.TRIGGER_CONDITION:
      {
        TriggerCondition triggerCondition = (TriggerCondition)theEObject;
        T result = caseTriggerCondition(triggerCondition);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case SafetyPackage.SAFETY_EQ_STATEMENT:
      {
        SafetyEqStatement safetyEqStatement = (SafetyEqStatement)theEObject;
        T result = caseSafetyEqStatement(safetyEqStatement);
        if (result == null) result = caseFaultSubcomponent(safetyEqStatement);
        if (result == null) result = caseElement(safetyEqStatement);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case SafetyPackage.INTERVAL:
      {
        Interval interval = (Interval)theEObject;
        T result = caseInterval(interval);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case SafetyPackage.SAFETY_CONTRACT_LIBRARY:
      {
        SafetyContractLibrary safetyContractLibrary = (SafetyContractLibrary)theEObject;
        T result = caseSafetyContractLibrary(safetyContractLibrary);
        if (result == null) result = caseSafetyLibrary(safetyContractLibrary);
        if (result == null) result = caseAnnexLibrary(safetyContractLibrary);
        if (result == null) result = caseAadl2_AnnexLibrary(safetyContractLibrary);
        if (result == null) result = caseNamedElement(safetyContractLibrary);
        if (result == null) result = caseElement(safetyContractLibrary);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case SafetyPackage.SAFETY_CONTRACT_SUBCLAUSE:
      {
        SafetyContractSubclause safetyContractSubclause = (SafetyContractSubclause)theEObject;
        T result = caseSafetyContractSubclause(safetyContractSubclause);
        if (result == null) result = caseSafetySubclause(safetyContractSubclause);
        if (result == null) result = caseAnnexSubclause(safetyContractSubclause);
        if (result == null) result = caseAadl2_AnnexSubclause(safetyContractSubclause);
        if (result == null) result = caseModalElement(safetyContractSubclause);
        if (result == null) result = caseNamedElement(safetyContractSubclause);
        if (result == null) result = caseElement(safetyContractSubclause);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case SafetyPackage.SAFETY_CONTRACT:
      {
        SafetyContract safetyContract = (SafetyContract)theEObject;
        T result = caseSafetyContract(safetyContract);
        if (result == null) result = caseContract(safetyContract);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case SafetyPackage.FAULT_STATEMENT:
      {
        FaultStatement faultStatement = (FaultStatement)theEObject;
        T result = caseFaultStatement(faultStatement);
        if (result == null) result = caseSpecStatement(faultStatement);
        if (result == null) result = caseAgree_SpecStatement(faultStatement);
        if (result == null) result = caseElement(faultStatement);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case SafetyPackage.ANALYSIS_STATEMENT:
      {
        AnalysisStatement analysisStatement = (AnalysisStatement)theEObject;
        T result = caseAnalysisStatement(analysisStatement);
        if (result == null) result = caseSpecStatement(analysisStatement);
        if (result == null) result = caseAgree_SpecStatement(analysisStatement);
        if (result == null) result = caseElement(analysisStatement);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case SafetyPackage.HW_FAULT_STATEMENT:
      {
        HWFaultStatement hwFaultStatement = (HWFaultStatement)theEObject;
        T result = caseHWFaultStatement(hwFaultStatement);
        if (result == null) result = caseSpecStatement(hwFaultStatement);
        if (result == null) result = caseAgree_SpecStatement(hwFaultStatement);
        if (result == null) result = caseElement(hwFaultStatement);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case SafetyPackage.PROPAGATE_STATEMENT:
      {
        PropagateStatement propagateStatement = (PropagateStatement)theEObject;
        T result = casePropagateStatement(propagateStatement);
        if (result == null) result = caseSpecStatement(propagateStatement);
        if (result == null) result = caseAgree_SpecStatement(propagateStatement);
        if (result == null) result = caseElement(propagateStatement);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case SafetyPackage.BYZANTINE_FAULT_STATEMENT:
      {
        ByzantineFaultStatement byzantineFaultStatement = (ByzantineFaultStatement)theEObject;
        T result = caseByzantineFaultStatement(byzantineFaultStatement);
        if (result == null) result = caseSpecStatement(byzantineFaultStatement);
        if (result == null) result = caseAgree_SpecStatement(byzantineFaultStatement);
        if (result == null) result = caseElement(byzantineFaultStatement);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case SafetyPackage.FAULT_COUNT_BEHAVIOR:
      {
        FaultCountBehavior faultCountBehavior = (FaultCountBehavior)theEObject;
        T result = caseFaultCountBehavior(faultCountBehavior);
        if (result == null) result = caseAnalysisBehavior(faultCountBehavior);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case SafetyPackage.PROBABILITY_BEHAVIOR:
      {
        ProbabilityBehavior probabilityBehavior = (ProbabilityBehavior)theEObject;
        T result = caseProbabilityBehavior(probabilityBehavior);
        if (result == null) result = caseAnalysisBehavior(probabilityBehavior);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case SafetyPackage.INPUT_STATEMENT:
      {
        InputStatement inputStatement = (InputStatement)theEObject;
        T result = caseInputStatement(inputStatement);
        if (result == null) result = caseFaultSubcomponent(inputStatement);
        if (result == null) result = caseElement(inputStatement);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case SafetyPackage.OUTPUT_STATEMENT:
      {
        OutputStatement outputStatement = (OutputStatement)theEObject;
        T result = caseOutputStatement(outputStatement);
        if (result == null) result = caseFaultSubcomponent(outputStatement);
        if (result == null) result = caseElement(outputStatement);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case SafetyPackage.DURATION_STATEMENT:
      {
        DurationStatement durationStatement = (DurationStatement)theEObject;
        T result = caseDurationStatement(durationStatement);
        if (result == null) result = caseFaultSubcomponent(durationStatement);
        if (result == null) result = caseHWFaultSubcomponent(durationStatement);
        if (result == null) result = caseByzantineFaultSubcomponent(durationStatement);
        if (result == null) result = caseElement(durationStatement);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case SafetyPackage.PROBABILITY_STATEMENT:
      {
        ProbabilityStatement probabilityStatement = (ProbabilityStatement)theEObject;
        T result = caseProbabilityStatement(probabilityStatement);
        if (result == null) result = caseFaultSubcomponent(probabilityStatement);
        if (result == null) result = caseHWFaultSubcomponent(probabilityStatement);
        if (result == null) result = caseByzantineFaultSubcomponent(probabilityStatement);
        if (result == null) result = caseElement(probabilityStatement);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case SafetyPackage.TRIGGER_STATEMENT:
      {
        TriggerStatement triggerStatement = (TriggerStatement)theEObject;
        T result = caseTriggerStatement(triggerStatement);
        if (result == null) result = caseFaultSubcomponent(triggerStatement);
        if (result == null) result = caseElement(triggerStatement);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case SafetyPackage.PROPAGATION_TYPE_STATEMENT:
      {
        PropagationTypeStatement propagationTypeStatement = (PropagationTypeStatement)theEObject;
        T result = casePropagationTypeStatement(propagationTypeStatement);
        if (result == null) result = caseFaultSubcomponent(propagationTypeStatement);
        if (result == null) result = caseHWFaultSubcomponent(propagationTypeStatement);
        if (result == null) result = caseElement(propagationTypeStatement);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case SafetyPackage.FAULTY_OUTPUT_STATEMENT:
      {
        FaultyOutputStatement faultyOutputStatement = (FaultyOutputStatement)theEObject;
        T result = caseFaultyOutputStatement(faultyOutputStatement);
        if (result == null) result = caseByzantineFaultSubcomponent(faultyOutputStatement);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case SafetyPackage.CONNECTIONS_STATEMENT:
      {
        ConnectionsStatement connectionsStatement = (ConnectionsStatement)theEObject;
        T result = caseConnectionsStatement(connectionsStatement);
        if (result == null) result = caseByzantineFaultSubcomponent(connectionsStatement);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case SafetyPackage.ASYMMETRIC:
      {
        asymmetric asymmetric = (asymmetric)theEObject;
        T result = caseasymmetric(asymmetric);
        if (result == null) result = casePropagationTypeConstraint(asymmetric);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case SafetyPackage.SYMMETRIC:
      {
        symmetric symmetric = (symmetric)theEObject;
        T result = casesymmetric(symmetric);
        if (result == null) result = casePropagationTypeConstraint(symmetric);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case SafetyPackage.PERMANENT_CONSTRAINT:
      {
        PermanentConstraint permanentConstraint = (PermanentConstraint)theEObject;
        T result = casePermanentConstraint(permanentConstraint);
        if (result == null) result = caseTemporalConstraint(permanentConstraint);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case SafetyPackage.TRANSIENT_CONSTRAINT:
      {
        TransientConstraint transientConstraint = (TransientConstraint)theEObject;
        T result = caseTransientConstraint(transientConstraint);
        if (result == null) result = caseTemporalConstraint(transientConstraint);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case SafetyPackage.ENABLER_CONDITION:
      {
        EnablerCondition enablerCondition = (EnablerCondition)theEObject;
        T result = caseEnablerCondition(enablerCondition);
        if (result == null) result = caseTriggerCondition(enablerCondition);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case SafetyPackage.EQ_VALUE:
      {
        EqValue eqValue = (EqValue)theEObject;
        T result = caseEqValue(eqValue);
        if (result == null) result = caseSafetyEqStatement(eqValue);
        if (result == null) result = caseFaultSubcomponent(eqValue);
        if (result == null) result = caseElement(eqValue);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case SafetyPackage.INTERVAL_EQ:
      {
        IntervalEq intervalEq = (IntervalEq)theEObject;
        T result = caseIntervalEq(intervalEq);
        if (result == null) result = caseSafetyEqStatement(intervalEq);
        if (result == null) result = caseFaultSubcomponent(intervalEq);
        if (result == null) result = caseElement(intervalEq);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case SafetyPackage.SET_EQ:
      {
        SetEq setEq = (SetEq)theEObject;
        T result = caseSetEq(setEq);
        if (result == null) result = caseSafetyEqStatement(setEq);
        if (result == null) result = caseFaultSubcomponent(setEq);
        if (result == null) result = caseElement(setEq);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case SafetyPackage.RANGE_EQ:
      {
        RangeEq rangeEq = (RangeEq)theEObject;
        T result = caseRangeEq(rangeEq);
        if (result == null) result = caseSafetyEqStatement(rangeEq);
        if (result == null) result = caseFaultSubcomponent(rangeEq);
        if (result == null) result = caseElement(rangeEq);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case SafetyPackage.CLOSED_INTERVAL:
      {
        ClosedInterval closedInterval = (ClosedInterval)theEObject;
        T result = caseClosedInterval(closedInterval);
        if (result == null) result = caseInterval(closedInterval);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case SafetyPackage.OPEN_LEFT_INTERVAL:
      {
        OpenLeftInterval openLeftInterval = (OpenLeftInterval)theEObject;
        T result = caseOpenLeftInterval(openLeftInterval);
        if (result == null) result = caseInterval(openLeftInterval);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case SafetyPackage.OPEN_RIGHT_INTERVAL:
      {
        OpenRightInterval openRightInterval = (OpenRightInterval)theEObject;
        T result = caseOpenRightInterval(openRightInterval);
        if (result == null) result = caseInterval(openRightInterval);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case SafetyPackage.OPEN_INTERVAL:
      {
        OpenInterval openInterval = (OpenInterval)theEObject;
        T result = caseOpenInterval(openInterval);
        if (result == null) result = caseInterval(openInterval);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      default: return defaultCase(theEObject);
    }
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Annex Library</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Annex Library</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseAnnexLibrary(AnnexLibrary object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Annex Subclause</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Annex Subclause</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseAnnexSubclause(AnnexSubclause object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Library</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Library</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseSafetyLibrary(SafetyLibrary object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Subclause</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Subclause</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseSafetySubclause(SafetySubclause object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Contract</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Contract</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseContract(Contract object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Spec Statement</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Spec Statement</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseSpecStatement(SpecStatement object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Analysis Behavior</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Analysis Behavior</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseAnalysisBehavior(AnalysisBehavior object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Fault Subcomponent</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Fault Subcomponent</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseFaultSubcomponent(FaultSubcomponent object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>HW Fault Subcomponent</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>HW Fault Subcomponent</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseHWFaultSubcomponent(HWFaultSubcomponent object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Byzantine Fault Subcomponent</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Byzantine Fault Subcomponent</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseByzantineFaultSubcomponent(ByzantineFaultSubcomponent object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Propagation Type Constraint</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Propagation Type Constraint</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T casePropagationTypeConstraint(PropagationTypeConstraint object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Temporal Constraint</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Temporal Constraint</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseTemporalConstraint(TemporalConstraint object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Trigger Condition</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Trigger Condition</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseTriggerCondition(TriggerCondition object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Eq Statement</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Eq Statement</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseSafetyEqStatement(SafetyEqStatement object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Interval</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Interval</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseInterval(Interval object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Contract Library</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Contract Library</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseSafetyContractLibrary(SafetyContractLibrary object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Contract Subclause</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Contract Subclause</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseSafetyContractSubclause(SafetyContractSubclause object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Contract</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Contract</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseSafetyContract(SafetyContract object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Fault Statement</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Fault Statement</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseFaultStatement(FaultStatement object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Analysis Statement</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Analysis Statement</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseAnalysisStatement(AnalysisStatement object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>HW Fault Statement</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>HW Fault Statement</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseHWFaultStatement(HWFaultStatement object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Propagate Statement</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Propagate Statement</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T casePropagateStatement(PropagateStatement object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Byzantine Fault Statement</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Byzantine Fault Statement</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseByzantineFaultStatement(ByzantineFaultStatement object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Fault Count Behavior</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Fault Count Behavior</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseFaultCountBehavior(FaultCountBehavior object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Probability Behavior</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Probability Behavior</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseProbabilityBehavior(ProbabilityBehavior object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Input Statement</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Input Statement</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseInputStatement(InputStatement object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Output Statement</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Output Statement</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseOutputStatement(OutputStatement object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Duration Statement</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Duration Statement</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseDurationStatement(DurationStatement object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Probability Statement</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Probability Statement</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseProbabilityStatement(ProbabilityStatement object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Trigger Statement</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Trigger Statement</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseTriggerStatement(TriggerStatement object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Propagation Type Statement</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Propagation Type Statement</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T casePropagationTypeStatement(PropagationTypeStatement object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Faulty Output Statement</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Faulty Output Statement</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseFaultyOutputStatement(FaultyOutputStatement object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Connections Statement</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Connections Statement</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseConnectionsStatement(ConnectionsStatement object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>asymmetric</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>asymmetric</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseasymmetric(asymmetric object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>symmetric</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>symmetric</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T casesymmetric(symmetric object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Permanent Constraint</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Permanent Constraint</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T casePermanentConstraint(PermanentConstraint object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Transient Constraint</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Transient Constraint</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseTransientConstraint(TransientConstraint object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Enabler Condition</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Enabler Condition</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseEnablerCondition(EnablerCondition object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Eq Value</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Eq Value</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseEqValue(EqValue object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Interval Eq</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Interval Eq</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseIntervalEq(IntervalEq object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Set Eq</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Set Eq</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseSetEq(SetEq object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Range Eq</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Range Eq</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseRangeEq(RangeEq object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Closed Interval</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Closed Interval</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseClosedInterval(ClosedInterval object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Open Left Interval</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Open Left Interval</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseOpenLeftInterval(OpenLeftInterval object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Open Right Interval</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Open Right Interval</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseOpenRightInterval(OpenRightInterval object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Open Interval</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Open Interval</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseOpenInterval(OpenInterval object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Element</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Element</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseElement(Element object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Named Element</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Named Element</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseNamedElement(NamedElement object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Annex Library</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Annex Library</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseAadl2_AnnexLibrary(org.osate.aadl2.AnnexLibrary object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Modal Element</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Modal Element</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseModalElement(ModalElement object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Annex Subclause</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Annex Subclause</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseAadl2_AnnexSubclause(org.osate.aadl2.AnnexSubclause object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Spec Statement</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Spec Statement</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseAgree_SpecStatement(com.rockwellcollins.atc.agree.agree.SpecStatement object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch, but this is the last case anyway.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject)
   * @generated
   */
  @Override
  public T defaultCase(EObject object)
  {
    return null;
  }

} //SafetySwitch
