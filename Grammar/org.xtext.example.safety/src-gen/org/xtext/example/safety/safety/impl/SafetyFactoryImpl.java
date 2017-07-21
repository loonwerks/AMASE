/**
 */
package org.xtext.example.safety.safety.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.xtext.example.safety.safety.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class SafetyFactoryImpl extends EFactoryImpl implements SafetyFactory
{
  /**
   * Creates the default factory implementation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static SafetyFactory init()
  {
    try
    {
      SafetyFactory theSafetyFactory = (SafetyFactory)EPackage.Registry.INSTANCE.getEFactory(SafetyPackage.eNS_URI);
      if (theSafetyFactory != null)
      {
        return theSafetyFactory;
      }
    }
    catch (Exception exception)
    {
      EcorePlugin.INSTANCE.log(exception);
    }
    return new SafetyFactoryImpl();
  }

  /**
   * Creates an instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public SafetyFactoryImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EObject create(EClass eClass)
  {
    switch (eClass.getClassifierID())
    {
      case SafetyPackage.SAFETY_LIBRARY: return createSafetyLibrary();
      case SafetyPackage.SAFETY_SUBCLAUSE: return createSafetySubclause();
      case SafetyPackage.CONTRACT: return createContract();
      case SafetyPackage.SPEC_STATEMENT: return createSpecStatement();
      case SafetyPackage.FAULT_SUBCOMPONENT: return createFaultSubcomponent();
      case SafetyPackage.TRIGGER_CONDITION: return createTriggerCondition();
      case SafetyPackage.EQ_STATEMENT: return createEqStatement();
      case SafetyPackage.SAFETY_CONTRACT_LIBRARY: return createSafetyContractLibrary();
      case SafetyPackage.SAFETY_CONTRACT_SUBCLAUSE: return createSafetyContractSubclause();
      case SafetyPackage.SAFETY_CONTRACT: return createSafetyContract();
      case SafetyPackage.FAULT_STATEMENT: return createFaultStatement();
      case SafetyPackage.INPUT_STATEMENT: return createInputStatement();
      case SafetyPackage.OUTPUT_STATEMENT: return createOutputStatement();
      case SafetyPackage.DURATION_STATEMENT: return createDurationStatement();
      case SafetyPackage.TRIGGER_STATEMENT: return createTriggerStatement();
      default:
        throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public SafetyLibrary createSafetyLibrary()
  {
    SafetyLibraryImpl safetyLibrary = new SafetyLibraryImpl();
    return safetyLibrary;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public SafetySubclause createSafetySubclause()
  {
    SafetySubclauseImpl safetySubclause = new SafetySubclauseImpl();
    return safetySubclause;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Contract createContract()
  {
    ContractImpl contract = new ContractImpl();
    return contract;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public SpecStatement createSpecStatement()
  {
    SpecStatementImpl specStatement = new SpecStatementImpl();
    return specStatement;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public FaultSubcomponent createFaultSubcomponent()
  {
    FaultSubcomponentImpl faultSubcomponent = new FaultSubcomponentImpl();
    return faultSubcomponent;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public TriggerCondition createTriggerCondition()
  {
    TriggerConditionImpl triggerCondition = new TriggerConditionImpl();
    return triggerCondition;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EqStatement createEqStatement()
  {
    EqStatementImpl eqStatement = new EqStatementImpl();
    return eqStatement;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public SafetyContractLibrary createSafetyContractLibrary()
  {
    SafetyContractLibraryImpl safetyContractLibrary = new SafetyContractLibraryImpl();
    return safetyContractLibrary;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public SafetyContractSubclause createSafetyContractSubclause()
  {
    SafetyContractSubclauseImpl safetyContractSubclause = new SafetyContractSubclauseImpl();
    return safetyContractSubclause;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public SafetyContract createSafetyContract()
  {
    SafetyContractImpl safetyContract = new SafetyContractImpl();
    return safetyContract;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public FaultStatement createFaultStatement()
  {
    FaultStatementImpl faultStatement = new FaultStatementImpl();
    return faultStatement;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public InputStatement createInputStatement()
  {
    InputStatementImpl inputStatement = new InputStatementImpl();
    return inputStatement;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public OutputStatement createOutputStatement()
  {
    OutputStatementImpl outputStatement = new OutputStatementImpl();
    return outputStatement;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public DurationStatement createDurationStatement()
  {
    DurationStatementImpl durationStatement = new DurationStatementImpl();
    return durationStatement;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public TriggerStatement createTriggerStatement()
  {
    TriggerStatementImpl triggerStatement = new TriggerStatementImpl();
    return triggerStatement;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public SafetyPackage getSafetyPackage()
  {
    return (SafetyPackage)getEPackage();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @deprecated
   * @generated
   */
  @Deprecated
  public static SafetyPackage getPackage()
  {
    return SafetyPackage.eINSTANCE;
  }

} //SafetyFactoryImpl
