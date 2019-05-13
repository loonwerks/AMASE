/**
 */
package edu.umn.cs.crisys.safety.safety.util;

import edu.umn.cs.crisys.safety.safety.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import org.osate.aadl2.Element;
import org.osate.aadl2.ModalElement;
import org.osate.aadl2.NamedElement;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see edu.umn.cs.crisys.safety.safety.SafetyPackage
 * @generated
 */
public class SafetyAdapterFactory extends AdapterFactoryImpl
{
  /**
   * The cached model package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static SafetyPackage modelPackage;

  /**
   * Creates an instance of the adapter factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public SafetyAdapterFactory()
  {
    if (modelPackage == null)
    {
      modelPackage = SafetyPackage.eINSTANCE;
    }
  }

  /**
   * Returns whether this factory is applicable for the type of the object.
   * <!-- begin-user-doc -->
   * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
   * <!-- end-user-doc -->
   * @return whether this factory is applicable for the type of the object.
   * @generated
   */
  @Override
  public boolean isFactoryForType(Object object)
  {
    if (object == modelPackage)
    {
      return true;
    }
    if (object instanceof EObject)
    {
      return ((EObject)object).eClass().getEPackage() == modelPackage;
    }
    return false;
  }

  /**
   * The switch that delegates to the <code>createXXX</code> methods.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected SafetySwitch<Adapter> modelSwitch =
    new SafetySwitch<Adapter>()
    {
      @Override
      public Adapter caseAnnexLibrary(AnnexLibrary object)
      {
        return createAnnexLibraryAdapter();
      }
      @Override
      public Adapter caseAnnexSubclause(AnnexSubclause object)
      {
        return createAnnexSubclauseAdapter();
      }
      @Override
      public Adapter caseSafetyLibrary(SafetyLibrary object)
      {
        return createSafetyLibraryAdapter();
      }
      @Override
      public Adapter caseSafetySubclause(SafetySubclause object)
      {
        return createSafetySubclauseAdapter();
      }
      @Override
      public Adapter caseContract(Contract object)
      {
        return createContractAdapter();
      }
      @Override
      public Adapter caseSpecStatement(SpecStatement object)
      {
        return createSpecStatementAdapter();
      }
      @Override
      public Adapter caseAnalysisBehavior(AnalysisBehavior object)
      {
        return createAnalysisBehaviorAdapter();
      }
      @Override
      public Adapter caseFaultSubcomponent(FaultSubcomponent object)
      {
        return createFaultSubcomponentAdapter();
      }
      @Override
      public Adapter caseHWFaultSubcomponent(HWFaultSubcomponent object)
      {
        return createHWFaultSubcomponentAdapter();
      }
      @Override
      public Adapter caseByzantineFaultSubcomponent(ByzantineFaultSubcomponent object)
      {
        return createByzantineFaultSubcomponentAdapter();
      }
      @Override
      public Adapter casePropagationTypeConstraint(PropagationTypeConstraint object)
      {
        return createPropagationTypeConstraintAdapter();
      }
      @Override
      public Adapter caseTemporalConstraint(TemporalConstraint object)
      {
        return createTemporalConstraintAdapter();
      }
      @Override
      public Adapter caseTriggerCondition(TriggerCondition object)
      {
        return createTriggerConditionAdapter();
      }
      @Override
      public Adapter caseSafetyEqStatement(SafetyEqStatement object)
      {
        return createSafetyEqStatementAdapter();
      }
      @Override
      public Adapter caseInterval(Interval object)
      {
        return createIntervalAdapter();
      }
      @Override
      public Adapter caseSafetyContractLibrary(SafetyContractLibrary object)
      {
        return createSafetyContractLibraryAdapter();
      }
      @Override
      public Adapter caseSafetyContractSubclause(SafetyContractSubclause object)
      {
        return createSafetyContractSubclauseAdapter();
      }
      @Override
      public Adapter caseSafetyContract(SafetyContract object)
      {
        return createSafetyContractAdapter();
      }
      @Override
      public Adapter caseFaultStatement(FaultStatement object)
      {
        return createFaultStatementAdapter();
      }
      @Override
      public Adapter caseAnalysisStatement(AnalysisStatement object)
      {
        return createAnalysisStatementAdapter();
      }
      @Override
      public Adapter caseHWFaultStatement(HWFaultStatement object)
      {
        return createHWFaultStatementAdapter();
      }
      @Override
      public Adapter casePropagateStatement(PropagateStatement object)
      {
        return createPropagateStatementAdapter();
      }
      @Override
      public Adapter caseByzantineFaultStatement(ByzantineFaultStatement object)
      {
        return createByzantineFaultStatementAdapter();
      }
      @Override
      public Adapter caseFaultCountBehavior(FaultCountBehavior object)
      {
        return createFaultCountBehaviorAdapter();
      }
      @Override
      public Adapter caseProbabilityBehavior(ProbabilityBehavior object)
      {
        return createProbabilityBehaviorAdapter();
      }
      @Override
      public Adapter caseInputStatement(InputStatement object)
      {
        return createInputStatementAdapter();
      }
      @Override
      public Adapter caseOutputStatement(OutputStatement object)
      {
        return createOutputStatementAdapter();
      }
      @Override
      public Adapter caseDurationStatement(DurationStatement object)
      {
        return createDurationStatementAdapter();
      }
      @Override
      public Adapter caseProbabilityStatement(ProbabilityStatement object)
      {
        return createProbabilityStatementAdapter();
      }
      @Override
      public Adapter caseTriggerStatement(TriggerStatement object)
      {
        return createTriggerStatementAdapter();
      }
      @Override
      public Adapter casePropagationTypeStatement(PropagationTypeStatement object)
      {
        return createPropagationTypeStatementAdapter();
      }
      @Override
      public Adapter caseFaultyOutputStatement(FaultyOutputStatement object)
      {
        return createFaultyOutputStatementAdapter();
      }
      @Override
      public Adapter caseConnectionsStatement(ConnectionsStatement object)
      {
        return createConnectionsStatementAdapter();
      }
      @Override
      public Adapter caseasymmetric(asymmetric object)
      {
        return createasymmetricAdapter();
      }
      @Override
      public Adapter casesymmetric(symmetric object)
      {
        return createsymmetricAdapter();
      }
      @Override
      public Adapter casePermanentConstraint(PermanentConstraint object)
      {
        return createPermanentConstraintAdapter();
      }
      @Override
      public Adapter caseTransientConstraint(TransientConstraint object)
      {
        return createTransientConstraintAdapter();
      }
      @Override
      public Adapter caseEnablerCondition(EnablerCondition object)
      {
        return createEnablerConditionAdapter();
      }
      @Override
      public Adapter caseEqValue(EqValue object)
      {
        return createEqValueAdapter();
      }
      @Override
      public Adapter caseIntervalEq(IntervalEq object)
      {
        return createIntervalEqAdapter();
      }
      @Override
      public Adapter caseSetEq(SetEq object)
      {
        return createSetEqAdapter();
      }
      @Override
      public Adapter caseRangeEq(RangeEq object)
      {
        return createRangeEqAdapter();
      }
      @Override
      public Adapter caseClosedInterval(ClosedInterval object)
      {
        return createClosedIntervalAdapter();
      }
      @Override
      public Adapter caseOpenLeftInterval(OpenLeftInterval object)
      {
        return createOpenLeftIntervalAdapter();
      }
      @Override
      public Adapter caseOpenRightInterval(OpenRightInterval object)
      {
        return createOpenRightIntervalAdapter();
      }
      @Override
      public Adapter caseOpenInterval(OpenInterval object)
      {
        return createOpenIntervalAdapter();
      }
      @Override
      public Adapter caseElement(Element object)
      {
        return createElementAdapter();
      }
      @Override
      public Adapter caseNamedElement(NamedElement object)
      {
        return createNamedElementAdapter();
      }
      @Override
      public Adapter caseAadl2_AnnexLibrary(org.osate.aadl2.AnnexLibrary object)
      {
        return createAadl2_AnnexLibraryAdapter();
      }
      @Override
      public Adapter caseModalElement(ModalElement object)
      {
        return createModalElementAdapter();
      }
      @Override
      public Adapter caseAadl2_AnnexSubclause(org.osate.aadl2.AnnexSubclause object)
      {
        return createAadl2_AnnexSubclauseAdapter();
      }
      @Override
      public Adapter caseAgree_SpecStatement(com.rockwellcollins.atc.agree.agree.SpecStatement object)
      {
        return createAgree_SpecStatementAdapter();
      }
      @Override
      public Adapter defaultCase(EObject object)
      {
        return createEObjectAdapter();
      }
    };

  /**
   * Creates an adapter for the <code>target</code>.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param target the object to adapt.
   * @return the adapter for the <code>target</code>.
   * @generated
   */
  @Override
  public Adapter createAdapter(Notifier target)
  {
    return modelSwitch.doSwitch((EObject)target);
  }


  /**
   * Creates a new adapter for an object of class '{@link edu.umn.cs.crisys.safety.safety.AnnexLibrary <em>Annex Library</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see edu.umn.cs.crisys.safety.safety.AnnexLibrary
   * @generated
   */
  public Adapter createAnnexLibraryAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link edu.umn.cs.crisys.safety.safety.AnnexSubclause <em>Annex Subclause</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see edu.umn.cs.crisys.safety.safety.AnnexSubclause
   * @generated
   */
  public Adapter createAnnexSubclauseAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link edu.umn.cs.crisys.safety.safety.SafetyLibrary <em>Library</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see edu.umn.cs.crisys.safety.safety.SafetyLibrary
   * @generated
   */
  public Adapter createSafetyLibraryAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link edu.umn.cs.crisys.safety.safety.SafetySubclause <em>Subclause</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see edu.umn.cs.crisys.safety.safety.SafetySubclause
   * @generated
   */
  public Adapter createSafetySubclauseAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link edu.umn.cs.crisys.safety.safety.Contract <em>Contract</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see edu.umn.cs.crisys.safety.safety.Contract
   * @generated
   */
  public Adapter createContractAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link edu.umn.cs.crisys.safety.safety.SpecStatement <em>Spec Statement</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see edu.umn.cs.crisys.safety.safety.SpecStatement
   * @generated
   */
  public Adapter createSpecStatementAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link edu.umn.cs.crisys.safety.safety.AnalysisBehavior <em>Analysis Behavior</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see edu.umn.cs.crisys.safety.safety.AnalysisBehavior
   * @generated
   */
  public Adapter createAnalysisBehaviorAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link edu.umn.cs.crisys.safety.safety.FaultSubcomponent <em>Fault Subcomponent</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see edu.umn.cs.crisys.safety.safety.FaultSubcomponent
   * @generated
   */
  public Adapter createFaultSubcomponentAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link edu.umn.cs.crisys.safety.safety.HWFaultSubcomponent <em>HW Fault Subcomponent</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see edu.umn.cs.crisys.safety.safety.HWFaultSubcomponent
   * @generated
   */
  public Adapter createHWFaultSubcomponentAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link edu.umn.cs.crisys.safety.safety.ByzantineFaultSubcomponent <em>Byzantine Fault Subcomponent</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see edu.umn.cs.crisys.safety.safety.ByzantineFaultSubcomponent
   * @generated
   */
  public Adapter createByzantineFaultSubcomponentAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link edu.umn.cs.crisys.safety.safety.PropagationTypeConstraint <em>Propagation Type Constraint</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see edu.umn.cs.crisys.safety.safety.PropagationTypeConstraint
   * @generated
   */
  public Adapter createPropagationTypeConstraintAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link edu.umn.cs.crisys.safety.safety.TemporalConstraint <em>Temporal Constraint</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see edu.umn.cs.crisys.safety.safety.TemporalConstraint
   * @generated
   */
  public Adapter createTemporalConstraintAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link edu.umn.cs.crisys.safety.safety.TriggerCondition <em>Trigger Condition</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see edu.umn.cs.crisys.safety.safety.TriggerCondition
   * @generated
   */
  public Adapter createTriggerConditionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link edu.umn.cs.crisys.safety.safety.SafetyEqStatement <em>Eq Statement</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see edu.umn.cs.crisys.safety.safety.SafetyEqStatement
   * @generated
   */
  public Adapter createSafetyEqStatementAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link edu.umn.cs.crisys.safety.safety.Interval <em>Interval</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see edu.umn.cs.crisys.safety.safety.Interval
   * @generated
   */
  public Adapter createIntervalAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link edu.umn.cs.crisys.safety.safety.SafetyContractLibrary <em>Contract Library</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see edu.umn.cs.crisys.safety.safety.SafetyContractLibrary
   * @generated
   */
  public Adapter createSafetyContractLibraryAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link edu.umn.cs.crisys.safety.safety.SafetyContractSubclause <em>Contract Subclause</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see edu.umn.cs.crisys.safety.safety.SafetyContractSubclause
   * @generated
   */
  public Adapter createSafetyContractSubclauseAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link edu.umn.cs.crisys.safety.safety.SafetyContract <em>Contract</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see edu.umn.cs.crisys.safety.safety.SafetyContract
   * @generated
   */
  public Adapter createSafetyContractAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link edu.umn.cs.crisys.safety.safety.FaultStatement <em>Fault Statement</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see edu.umn.cs.crisys.safety.safety.FaultStatement
   * @generated
   */
  public Adapter createFaultStatementAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link edu.umn.cs.crisys.safety.safety.AnalysisStatement <em>Analysis Statement</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see edu.umn.cs.crisys.safety.safety.AnalysisStatement
   * @generated
   */
  public Adapter createAnalysisStatementAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link edu.umn.cs.crisys.safety.safety.HWFaultStatement <em>HW Fault Statement</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see edu.umn.cs.crisys.safety.safety.HWFaultStatement
   * @generated
   */
  public Adapter createHWFaultStatementAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link edu.umn.cs.crisys.safety.safety.PropagateStatement <em>Propagate Statement</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see edu.umn.cs.crisys.safety.safety.PropagateStatement
   * @generated
   */
  public Adapter createPropagateStatementAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link edu.umn.cs.crisys.safety.safety.ByzantineFaultStatement <em>Byzantine Fault Statement</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see edu.umn.cs.crisys.safety.safety.ByzantineFaultStatement
   * @generated
   */
  public Adapter createByzantineFaultStatementAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link edu.umn.cs.crisys.safety.safety.FaultCountBehavior <em>Fault Count Behavior</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see edu.umn.cs.crisys.safety.safety.FaultCountBehavior
   * @generated
   */
  public Adapter createFaultCountBehaviorAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link edu.umn.cs.crisys.safety.safety.ProbabilityBehavior <em>Probability Behavior</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see edu.umn.cs.crisys.safety.safety.ProbabilityBehavior
   * @generated
   */
  public Adapter createProbabilityBehaviorAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link edu.umn.cs.crisys.safety.safety.InputStatement <em>Input Statement</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see edu.umn.cs.crisys.safety.safety.InputStatement
   * @generated
   */
  public Adapter createInputStatementAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link edu.umn.cs.crisys.safety.safety.OutputStatement <em>Output Statement</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see edu.umn.cs.crisys.safety.safety.OutputStatement
   * @generated
   */
  public Adapter createOutputStatementAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link edu.umn.cs.crisys.safety.safety.DurationStatement <em>Duration Statement</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see edu.umn.cs.crisys.safety.safety.DurationStatement
   * @generated
   */
  public Adapter createDurationStatementAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link edu.umn.cs.crisys.safety.safety.ProbabilityStatement <em>Probability Statement</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see edu.umn.cs.crisys.safety.safety.ProbabilityStatement
   * @generated
   */
  public Adapter createProbabilityStatementAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link edu.umn.cs.crisys.safety.safety.TriggerStatement <em>Trigger Statement</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see edu.umn.cs.crisys.safety.safety.TriggerStatement
   * @generated
   */
  public Adapter createTriggerStatementAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link edu.umn.cs.crisys.safety.safety.PropagationTypeStatement <em>Propagation Type Statement</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see edu.umn.cs.crisys.safety.safety.PropagationTypeStatement
   * @generated
   */
  public Adapter createPropagationTypeStatementAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link edu.umn.cs.crisys.safety.safety.FaultyOutputStatement <em>Faulty Output Statement</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see edu.umn.cs.crisys.safety.safety.FaultyOutputStatement
   * @generated
   */
  public Adapter createFaultyOutputStatementAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link edu.umn.cs.crisys.safety.safety.ConnectionsStatement <em>Connections Statement</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see edu.umn.cs.crisys.safety.safety.ConnectionsStatement
   * @generated
   */
  public Adapter createConnectionsStatementAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link edu.umn.cs.crisys.safety.safety.asymmetric <em>asymmetric</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see edu.umn.cs.crisys.safety.safety.asymmetric
   * @generated
   */
  public Adapter createasymmetricAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link edu.umn.cs.crisys.safety.safety.symmetric <em>symmetric</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see edu.umn.cs.crisys.safety.safety.symmetric
   * @generated
   */
  public Adapter createsymmetricAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link edu.umn.cs.crisys.safety.safety.PermanentConstraint <em>Permanent Constraint</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see edu.umn.cs.crisys.safety.safety.PermanentConstraint
   * @generated
   */
  public Adapter createPermanentConstraintAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link edu.umn.cs.crisys.safety.safety.TransientConstraint <em>Transient Constraint</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see edu.umn.cs.crisys.safety.safety.TransientConstraint
   * @generated
   */
  public Adapter createTransientConstraintAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link edu.umn.cs.crisys.safety.safety.EnablerCondition <em>Enabler Condition</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see edu.umn.cs.crisys.safety.safety.EnablerCondition
   * @generated
   */
  public Adapter createEnablerConditionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link edu.umn.cs.crisys.safety.safety.EqValue <em>Eq Value</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see edu.umn.cs.crisys.safety.safety.EqValue
   * @generated
   */
  public Adapter createEqValueAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link edu.umn.cs.crisys.safety.safety.IntervalEq <em>Interval Eq</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see edu.umn.cs.crisys.safety.safety.IntervalEq
   * @generated
   */
  public Adapter createIntervalEqAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link edu.umn.cs.crisys.safety.safety.SetEq <em>Set Eq</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see edu.umn.cs.crisys.safety.safety.SetEq
   * @generated
   */
  public Adapter createSetEqAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link edu.umn.cs.crisys.safety.safety.RangeEq <em>Range Eq</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see edu.umn.cs.crisys.safety.safety.RangeEq
   * @generated
   */
  public Adapter createRangeEqAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link edu.umn.cs.crisys.safety.safety.ClosedInterval <em>Closed Interval</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see edu.umn.cs.crisys.safety.safety.ClosedInterval
   * @generated
   */
  public Adapter createClosedIntervalAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link edu.umn.cs.crisys.safety.safety.OpenLeftInterval <em>Open Left Interval</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see edu.umn.cs.crisys.safety.safety.OpenLeftInterval
   * @generated
   */
  public Adapter createOpenLeftIntervalAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link edu.umn.cs.crisys.safety.safety.OpenRightInterval <em>Open Right Interval</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see edu.umn.cs.crisys.safety.safety.OpenRightInterval
   * @generated
   */
  public Adapter createOpenRightIntervalAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link edu.umn.cs.crisys.safety.safety.OpenInterval <em>Open Interval</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see edu.umn.cs.crisys.safety.safety.OpenInterval
   * @generated
   */
  public Adapter createOpenIntervalAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.osate.aadl2.Element <em>Element</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.osate.aadl2.Element
   * @generated
   */
  public Adapter createElementAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.osate.aadl2.NamedElement <em>Named Element</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.osate.aadl2.NamedElement
   * @generated
   */
  public Adapter createNamedElementAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.osate.aadl2.AnnexLibrary <em>Annex Library</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.osate.aadl2.AnnexLibrary
   * @generated
   */
  public Adapter createAadl2_AnnexLibraryAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.osate.aadl2.ModalElement <em>Modal Element</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.osate.aadl2.ModalElement
   * @generated
   */
  public Adapter createModalElementAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.osate.aadl2.AnnexSubclause <em>Annex Subclause</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.osate.aadl2.AnnexSubclause
   * @generated
   */
  public Adapter createAadl2_AnnexSubclauseAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.rockwellcollins.atc.agree.agree.SpecStatement <em>Spec Statement</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.rockwellcollins.atc.agree.agree.SpecStatement
   * @generated
   */
  public Adapter createAgree_SpecStatementAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for the default case.
   * <!-- begin-user-doc -->
   * This default implementation returns null.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @generated
   */
  public Adapter createEObjectAdapter()
  {
    return null;
  }

} //SafetyAdapterFactory
