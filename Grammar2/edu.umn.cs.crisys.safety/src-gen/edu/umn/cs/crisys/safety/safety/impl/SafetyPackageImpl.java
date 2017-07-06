/**
 */
package edu.umn.cs.crisys.safety.safety.impl;

import com.rockwellcollins.atc.agree.agree.AgreePackage;

import edu.umn.cs.crisys.safety.safety.Contract;
import edu.umn.cs.crisys.safety.safety.DurationStatement;
import edu.umn.cs.crisys.safety.safety.EqStatement;
import edu.umn.cs.crisys.safety.safety.FaultStatement;
import edu.umn.cs.crisys.safety.safety.FaultSubcomponent;
import edu.umn.cs.crisys.safety.safety.InputStatement;
import edu.umn.cs.crisys.safety.safety.OutputStatement;
import edu.umn.cs.crisys.safety.safety.SafetyContract;
import edu.umn.cs.crisys.safety.safety.SafetyContractLibrary;
import edu.umn.cs.crisys.safety.safety.SafetyContractSubclause;
import edu.umn.cs.crisys.safety.safety.SafetyFactory;
import edu.umn.cs.crisys.safety.safety.SafetyLibrary;
import edu.umn.cs.crisys.safety.safety.SafetyPackage;
import edu.umn.cs.crisys.safety.safety.SafetySubclause;
import edu.umn.cs.crisys.safety.safety.SpecStatement;
import edu.umn.cs.crisys.safety.safety.TriggerCondition;
import edu.umn.cs.crisys.safety.safety.TriggerStatement;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.osate.aadl2.Aadl2Package;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class SafetyPackageImpl extends EPackageImpl implements SafetyPackage
{
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass safetyLibraryEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass safetySubclauseEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass contractEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass specStatementEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass faultSubcomponentEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass triggerConditionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass eqStatementEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass safetyContractLibraryEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass safetyContractSubclauseEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass safetyContractEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass faultStatementEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass inputStatementEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass outputStatementEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass durationStatementEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass triggerStatementEClass = null;

  /**
   * Creates an instance of the model <b>Package</b>, registered with
   * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
   * package URI value.
   * <p>Note: the correct way to create the package is via the static
   * factory method {@link #init init()}, which also performs
   * initialization of the package, or returns the registered package,
   * if one already exists.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.emf.ecore.EPackage.Registry
   * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#eNS_URI
   * @see #init()
   * @generated
   */
  private SafetyPackageImpl()
  {
    super(eNS_URI, SafetyFactory.eINSTANCE);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static boolean isInited = false;

  /**
   * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
   * 
   * <p>This method is used to initialize {@link SafetyPackage#eINSTANCE} when that field is accessed.
   * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #eNS_URI
   * @see #createPackageContents()
   * @see #initializePackageContents()
   * @generated
   */
  public static SafetyPackage init()
  {
    if (isInited) return (SafetyPackage)EPackage.Registry.INSTANCE.getEPackage(SafetyPackage.eNS_URI);

    // Obtain or create and register package
    SafetyPackageImpl theSafetyPackage = (SafetyPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof SafetyPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new SafetyPackageImpl());

    isInited = true;

    // Initialize simple dependencies
    AgreePackage.eINSTANCE.eClass();

    // Create package meta-data objects
    theSafetyPackage.createPackageContents();

    // Initialize created meta-data
    theSafetyPackage.initializePackageContents();

    // Mark meta-data to indicate it can't be changed
    theSafetyPackage.freeze();

  
    // Update the registry and return the package
    EPackage.Registry.INSTANCE.put(SafetyPackage.eNS_URI, theSafetyPackage);
    return theSafetyPackage;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getSafetyLibrary()
  {
    return safetyLibraryEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getSafetySubclause()
  {
    return safetySubclauseEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getContract()
  {
    return contractEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getSpecStatement()
  {
    return specStatementEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getFaultSubcomponent()
  {
    return faultSubcomponentEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getTriggerCondition()
  {
    return triggerConditionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getTriggerCondition_ExprList()
  {
    return (EReference)triggerConditionEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getEqStatement()
  {
    return eqStatementEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getEqStatement_Interv()
  {
    return (EReference)eqStatementEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getEqStatement_ExprList()
  {
    return (EReference)eqStatementEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getSafetyContractLibrary()
  {
    return safetyContractLibraryEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getSafetyContractLibrary_Contract()
  {
    return (EReference)safetyContractLibraryEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getSafetyContractSubclause()
  {
    return safetyContractSubclauseEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getSafetyContractSubclause_Contract()
  {
    return (EReference)safetyContractSubclauseEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getSafetyContract()
  {
    return safetyContractEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getSafetyContract_Specs()
  {
    return (EReference)safetyContractEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getFaultStatement()
  {
    return faultStatementEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getFaultStatement_Str()
  {
    return (EAttribute)faultStatementEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getFaultStatement_FaultDefName()
  {
    return (EAttribute)faultStatementEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getFaultStatement_FaultDefinitions()
  {
    return (EReference)faultStatementEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getInputStatement()
  {
    return inputStatementEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getInputStatement_In_conn()
  {
    return (EReference)inputStatementEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getInputStatement_Out_conn()
  {
    return (EAttribute)inputStatementEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getOutputStatement()
  {
    return outputStatementEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getOutputStatement_Out_conn()
  {
    return (EAttribute)outputStatementEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getOutputStatement_Nom_conn()
  {
    return (EReference)outputStatementEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getDurationStatement()
  {
    return durationStatementEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getDurationStatement_Tc()
  {
    return (EAttribute)durationStatementEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getDurationStatement_Interv()
  {
    return (EReference)durationStatementEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getTriggerStatement()
  {
    return triggerStatementEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getTriggerStatement_Cond()
  {
    return (EReference)triggerStatementEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getTriggerStatement_Probability()
  {
    return (EAttribute)triggerStatementEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public SafetyFactory getSafetyFactory()
  {
    return (SafetyFactory)getEFactoryInstance();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private boolean isCreated = false;

  /**
   * Creates the meta-model objects for the package.  This method is
   * guarded to have no affect on any invocation but its first.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void createPackageContents()
  {
    if (isCreated) return;
    isCreated = true;

    // Create classes and their features
    safetyLibraryEClass = createEClass(SAFETY_LIBRARY);

    safetySubclauseEClass = createEClass(SAFETY_SUBCLAUSE);

    contractEClass = createEClass(CONTRACT);

    specStatementEClass = createEClass(SPEC_STATEMENT);

    faultSubcomponentEClass = createEClass(FAULT_SUBCOMPONENT);

    triggerConditionEClass = createEClass(TRIGGER_CONDITION);
    createEReference(triggerConditionEClass, TRIGGER_CONDITION__EXPR_LIST);

    eqStatementEClass = createEClass(EQ_STATEMENT);
    createEReference(eqStatementEClass, EQ_STATEMENT__INTERV);
    createEReference(eqStatementEClass, EQ_STATEMENT__EXPR_LIST);

    safetyContractLibraryEClass = createEClass(SAFETY_CONTRACT_LIBRARY);
    createEReference(safetyContractLibraryEClass, SAFETY_CONTRACT_LIBRARY__CONTRACT);

    safetyContractSubclauseEClass = createEClass(SAFETY_CONTRACT_SUBCLAUSE);
    createEReference(safetyContractSubclauseEClass, SAFETY_CONTRACT_SUBCLAUSE__CONTRACT);

    safetyContractEClass = createEClass(SAFETY_CONTRACT);
    createEReference(safetyContractEClass, SAFETY_CONTRACT__SPECS);

    faultStatementEClass = createEClass(FAULT_STATEMENT);
    createEAttribute(faultStatementEClass, FAULT_STATEMENT__STR);
    createEAttribute(faultStatementEClass, FAULT_STATEMENT__FAULT_DEF_NAME);
    createEReference(faultStatementEClass, FAULT_STATEMENT__FAULT_DEFINITIONS);

    inputStatementEClass = createEClass(INPUT_STATEMENT);
    createEReference(inputStatementEClass, INPUT_STATEMENT__IN_CONN);
    createEAttribute(inputStatementEClass, INPUT_STATEMENT__OUT_CONN);

    outputStatementEClass = createEClass(OUTPUT_STATEMENT);
    createEAttribute(outputStatementEClass, OUTPUT_STATEMENT__OUT_CONN);
    createEReference(outputStatementEClass, OUTPUT_STATEMENT__NOM_CONN);

    durationStatementEClass = createEClass(DURATION_STATEMENT);
    createEAttribute(durationStatementEClass, DURATION_STATEMENT__TC);
    createEReference(durationStatementEClass, DURATION_STATEMENT__INTERV);

    triggerStatementEClass = createEClass(TRIGGER_STATEMENT);
    createEReference(triggerStatementEClass, TRIGGER_STATEMENT__COND);
    createEAttribute(triggerStatementEClass, TRIGGER_STATEMENT__PROBABILITY);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private boolean isInitialized = false;

  /**
   * Complete the initialization of the package and its meta-model.  This
   * method is guarded to have no affect on any invocation but its first.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void initializePackageContents()
  {
    if (isInitialized) return;
    isInitialized = true;

    // Initialize package
    setName(eNAME);
    setNsPrefix(eNS_PREFIX);
    setNsURI(eNS_URI);

    // Obtain other dependent packages
    Aadl2Package theAadl2Package = (Aadl2Package)EPackage.Registry.INSTANCE.getEPackage(Aadl2Package.eNS_URI);
    AgreePackage theAgreePackage = (AgreePackage)EPackage.Registry.INSTANCE.getEPackage(AgreePackage.eNS_URI);
    EcorePackage theEcorePackage = (EcorePackage)EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);

    // Create type parameters

    // Set bounds for type parameters

    // Add supertypes to classes
    safetyLibraryEClass.getESuperTypes().add(theAadl2Package.getAnnexLibrary());
    safetySubclauseEClass.getESuperTypes().add(theAadl2Package.getAnnexSubclause());
    specStatementEClass.getESuperTypes().add(theAgreePackage.getSpecStatement());
    eqStatementEClass.getESuperTypes().add(this.getFaultSubcomponent());
    eqStatementEClass.getESuperTypes().add(theAgreePackage.getEqStatement());
    safetyContractLibraryEClass.getESuperTypes().add(this.getSafetyLibrary());
    safetyContractSubclauseEClass.getESuperTypes().add(this.getSafetySubclause());
    safetyContractEClass.getESuperTypes().add(this.getContract());
    faultStatementEClass.getESuperTypes().add(this.getSpecStatement());
    inputStatementEClass.getESuperTypes().add(this.getFaultSubcomponent());
    outputStatementEClass.getESuperTypes().add(this.getFaultSubcomponent());
    durationStatementEClass.getESuperTypes().add(this.getFaultSubcomponent());
    triggerStatementEClass.getESuperTypes().add(this.getFaultSubcomponent());

    // Initialize classes and features; add operations and parameters
    initEClass(safetyLibraryEClass, SafetyLibrary.class, "SafetyLibrary", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(safetySubclauseEClass, SafetySubclause.class, "SafetySubclause", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(contractEClass, Contract.class, "Contract", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(specStatementEClass, SpecStatement.class, "SpecStatement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(faultSubcomponentEClass, FaultSubcomponent.class, "FaultSubcomponent", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(triggerConditionEClass, TriggerCondition.class, "TriggerCondition", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getTriggerCondition_ExprList(), theAgreePackage.getExpr(), null, "exprList", null, 0, -1, TriggerCondition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(eqStatementEClass, EqStatement.class, "EqStatement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getEqStatement_Interv(), theAgreePackage.getTimeInterval(), null, "interv", null, 0, 1, EqStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getEqStatement_ExprList(), theAgreePackage.getExpr(), null, "exprList", null, 0, -1, EqStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(safetyContractLibraryEClass, SafetyContractLibrary.class, "SafetyContractLibrary", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getSafetyContractLibrary_Contract(), this.getContract(), null, "contract", null, 0, 1, SafetyContractLibrary.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(safetyContractSubclauseEClass, SafetyContractSubclause.class, "SafetyContractSubclause", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getSafetyContractSubclause_Contract(), this.getContract(), null, "contract", null, 0, 1, SafetyContractSubclause.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(safetyContractEClass, SafetyContract.class, "SafetyContract", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getSafetyContract_Specs(), this.getSpecStatement(), null, "specs", null, 0, -1, SafetyContract.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(faultStatementEClass, FaultStatement.class, "FaultStatement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getFaultStatement_Str(), theEcorePackage.getEString(), "str", null, 0, 1, FaultStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getFaultStatement_FaultDefName(), theEcorePackage.getEString(), "faultDefName", null, 0, 1, FaultStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getFaultStatement_FaultDefinitions(), this.getFaultSubcomponent(), null, "faultDefinitions", null, 0, -1, FaultStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(inputStatementEClass, InputStatement.class, "InputStatement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getInputStatement_In_conn(), theAadl2Package.getNamedElement(), null, "in_conn", null, 0, 1, InputStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getInputStatement_Out_conn(), theEcorePackage.getEString(), "out_conn", null, 0, 1, InputStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(outputStatementEClass, OutputStatement.class, "OutputStatement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getOutputStatement_Out_conn(), theEcorePackage.getEString(), "out_conn", null, 0, 1, OutputStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getOutputStatement_Nom_conn(), theAadl2Package.getNamedElement(), null, "nom_conn", null, 0, 1, OutputStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(durationStatementEClass, DurationStatement.class, "DurationStatement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getDurationStatement_Tc(), theEcorePackage.getEString(), "tc", null, 0, 1, DurationStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getDurationStatement_Interv(), theAgreePackage.getTimeInterval(), null, "interv", null, 0, 1, DurationStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(triggerStatementEClass, TriggerStatement.class, "TriggerStatement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getTriggerStatement_Cond(), this.getTriggerCondition(), null, "cond", null, 0, 1, TriggerStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getTriggerStatement_Probability(), theEcorePackage.getEString(), "probability", null, 0, 1, TriggerStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    // Create resource
    createResource(eNS_URI);
  }

} //SafetyPackageImpl
