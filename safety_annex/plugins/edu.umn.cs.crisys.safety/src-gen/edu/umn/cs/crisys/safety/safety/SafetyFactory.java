/**
 */
package edu.umn.cs.crisys.safety.safety;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see edu.umn.cs.crisys.safety.safety.SafetyPackage
 * @generated
 */
public interface SafetyFactory extends EFactory
{
  /**
   * The singleton instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  SafetyFactory eINSTANCE = edu.umn.cs.crisys.safety.safety.impl.SafetyFactoryImpl.init();

  /**
   * Returns a new object of class '<em>Annex Library</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Annex Library</em>'.
   * @generated
   */
  AnnexLibrary createAnnexLibrary();

  /**
   * Returns a new object of class '<em>Annex Subclause</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Annex Subclause</em>'.
   * @generated
   */
  AnnexSubclause createAnnexSubclause();

  /**
   * Returns a new object of class '<em>Library</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Library</em>'.
   * @generated
   */
  SafetyLibrary createSafetyLibrary();

  /**
   * Returns a new object of class '<em>Subclause</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Subclause</em>'.
   * @generated
   */
  SafetySubclause createSafetySubclause();

  /**
   * Returns a new object of class '<em>Contract</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Contract</em>'.
   * @generated
   */
  Contract createContract();

  /**
   * Returns a new object of class '<em>Spec Statement</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Spec Statement</em>'.
   * @generated
   */
  SpecStatement createSpecStatement();

  /**
   * Returns a new object of class '<em>Analysis Behavior</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Analysis Behavior</em>'.
   * @generated
   */
  AnalysisBehavior createAnalysisBehavior();

  /**
   * Returns a new object of class '<em>Fault Subcomponent</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Fault Subcomponent</em>'.
   * @generated
   */
  FaultSubcomponent createFaultSubcomponent();

  /**
   * Returns a new object of class '<em>Temporal Constraint</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Temporal Constraint</em>'.
   * @generated
   */
  TemporalConstraint createTemporalConstraint();

  /**
   * Returns a new object of class '<em>Trigger Condition</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Trigger Condition</em>'.
   * @generated
   */
  TriggerCondition createTriggerCondition();

  /**
   * Returns a new object of class '<em>Eq Statement</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Eq Statement</em>'.
   * @generated
   */
  SafetyEqStatement createSafetyEqStatement();

  /**
   * Returns a new object of class '<em>Interval</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Interval</em>'.
   * @generated
   */
  Interval createInterval();

  /**
   * Returns a new object of class '<em>Contract Library</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Contract Library</em>'.
   * @generated
   */
  SafetyContractLibrary createSafetyContractLibrary();

  /**
   * Returns a new object of class '<em>Contract Subclause</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Contract Subclause</em>'.
   * @generated
   */
  SafetyContractSubclause createSafetyContractSubclause();

  /**
   * Returns a new object of class '<em>Contract</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Contract</em>'.
   * @generated
   */
  SafetyContract createSafetyContract();

  /**
   * Returns a new object of class '<em>Fault Statement</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Fault Statement</em>'.
   * @generated
   */
  FaultStatement createFaultStatement();

  /**
   * Returns a new object of class '<em>Analysis Statement</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Analysis Statement</em>'.
   * @generated
   */
  AnalysisStatement createAnalysisStatement();

  /**
   * Returns a new object of class '<em>Fault Count Behavior</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Fault Count Behavior</em>'.
   * @generated
   */
  FaultCountBehavior createFaultCountBehavior();

  /**
   * Returns a new object of class '<em>Probability Behavior</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Probability Behavior</em>'.
   * @generated
   */
  ProbabilityBehavior createProbabilityBehavior();

  /**
   * Returns a new object of class '<em>Input Statement</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Input Statement</em>'.
   * @generated
   */
  InputStatement createInputStatement();

  /**
   * Returns a new object of class '<em>Output Statement</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Output Statement</em>'.
   * @generated
   */
  OutputStatement createOutputStatement();

  /**
   * Returns a new object of class '<em>Duration Statement</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Duration Statement</em>'.
   * @generated
   */
  DurationStatement createDurationStatement();

  /**
   * Returns a new object of class '<em>Probability Statement</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Probability Statement</em>'.
   * @generated
   */
  ProbabilityStatement createProbabilityStatement();

  /**
   * Returns a new object of class '<em>Trigger Statement</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Trigger Statement</em>'.
   * @generated
   */
  TriggerStatement createTriggerStatement();

  /**
   * Returns a new object of class '<em>Permanent Constraint</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Permanent Constraint</em>'.
   * @generated
   */
  PermanentConstraint createPermanentConstraint();

  /**
   * Returns a new object of class '<em>Transient Constraint</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Transient Constraint</em>'.
   * @generated
   */
  TransientConstraint createTransientConstraint();

  /**
   * Returns a new object of class '<em>Enabler Condition</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Enabler Condition</em>'.
   * @generated
   */
  EnablerCondition createEnablerCondition();

  /**
   * Returns a new object of class '<em>Eq Value</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Eq Value</em>'.
   * @generated
   */
  EqValue createEqValue();

  /**
   * Returns a new object of class '<em>Interval Eq</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Interval Eq</em>'.
   * @generated
   */
  IntervalEq createIntervalEq();

  /**
   * Returns a new object of class '<em>Set Eq</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Set Eq</em>'.
   * @generated
   */
  SetEq createSetEq();

  /**
   * Returns a new object of class '<em>Range Eq</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Range Eq</em>'.
   * @generated
   */
  RangeEq createRangeEq();

  /**
   * Returns a new object of class '<em>Closed Interval</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Closed Interval</em>'.
   * @generated
   */
  ClosedInterval createClosedInterval();

  /**
   * Returns a new object of class '<em>Open Left Interval</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Open Left Interval</em>'.
   * @generated
   */
  OpenLeftInterval createOpenLeftInterval();

  /**
   * Returns a new object of class '<em>Open Right Interval</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Open Right Interval</em>'.
   * @generated
   */
  OpenRightInterval createOpenRightInterval();

  /**
   * Returns a new object of class '<em>Open Interval</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Open Interval</em>'.
   * @generated
   */
  OpenInterval createOpenInterval();

  /**
   * Returns the package supported by this factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the package supported by this factory.
   * @generated
   */
  SafetyPackage getSafetyPackage();

} //SafetyFactory
