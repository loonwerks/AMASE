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
   * Returns a new object of class '<em>Fault Subcomponent</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Fault Subcomponent</em>'.
   * @generated
   */
  FaultSubcomponent createFaultSubcomponent();

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
   * Returns a new object of class '<em>Trigger Statement</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Trigger Statement</em>'.
   * @generated
   */
  TriggerStatement createTriggerStatement();

  /**
   * Returns a new object of class '<em>Eq</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Eq</em>'.
   * @generated
   */
  Eq createEq();

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
   * Returns the package supported by this factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the package supported by this factory.
   * @generated
   */
  SafetyPackage getSafetyPackage();

} //SafetyFactory
