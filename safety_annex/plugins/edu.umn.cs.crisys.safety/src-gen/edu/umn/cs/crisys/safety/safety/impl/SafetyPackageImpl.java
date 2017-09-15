/**
 */
package edu.umn.cs.crisys.safety.safety.impl;

import com.rockwellcollins.atc.agree.agree.AgreePackage;

import edu.umn.cs.crisys.safety.safety.AnnexLibrary;
import edu.umn.cs.crisys.safety.safety.AnnexSubclause;
import edu.umn.cs.crisys.safety.safety.ClosedInterval;
import edu.umn.cs.crisys.safety.safety.Contract;
import edu.umn.cs.crisys.safety.safety.DurationStatement;
import edu.umn.cs.crisys.safety.safety.EnablerCondition;
import edu.umn.cs.crisys.safety.safety.EqValue;
import edu.umn.cs.crisys.safety.safety.FaultStatement;
import edu.umn.cs.crisys.safety.safety.FaultSubcomponent;
import edu.umn.cs.crisys.safety.safety.InputStatement;
import edu.umn.cs.crisys.safety.safety.Interval;
import edu.umn.cs.crisys.safety.safety.IntervalEq;
import edu.umn.cs.crisys.safety.safety.MustCondition;
import edu.umn.cs.crisys.safety.safety.OpenInterval;
import edu.umn.cs.crisys.safety.safety.OpenLeftInterval;
import edu.umn.cs.crisys.safety.safety.OpenRightInterval;
import edu.umn.cs.crisys.safety.safety.OutputStatement;
import edu.umn.cs.crisys.safety.safety.PermanentConstraint;
import edu.umn.cs.crisys.safety.safety.RangeEq;
import edu.umn.cs.crisys.safety.safety.SafetyContract;
import edu.umn.cs.crisys.safety.safety.SafetyContractLibrary;
import edu.umn.cs.crisys.safety.safety.SafetyContractSubclause;
import edu.umn.cs.crisys.safety.safety.SafetyEqStatement;
import edu.umn.cs.crisys.safety.safety.SafetyFactory;
import edu.umn.cs.crisys.safety.safety.SafetyLibrary;
import edu.umn.cs.crisys.safety.safety.SafetyPackage;
import edu.umn.cs.crisys.safety.safety.SafetySubclause;
import edu.umn.cs.crisys.safety.safety.SetEq;
import edu.umn.cs.crisys.safety.safety.SpecStatement;
import edu.umn.cs.crisys.safety.safety.TemporalConstraint;
import edu.umn.cs.crisys.safety.safety.TransientConstraint;
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
  private EClass annexLibraryEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass annexSubclauseEClass = null;

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
  private EClass temporalConstraintEClass = null;

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
  private EClass safetyEqStatementEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass intervalEClass = null;

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
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass permanentConstraintEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass transientConstraintEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass mustConditionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass enablerConditionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass eqValueEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass intervalEqEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass setEqEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass rangeEqEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass closedIntervalEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass openLeftIntervalEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass openRightIntervalEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass openIntervalEClass = null;

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
    EcorePackage.eINSTANCE.eClass();
    AgreePackage.eINSTANCE.eClass();
    Aadl2Package.eINSTANCE.eClass();

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
  public EClass getAnnexLibrary()
  {
    return annexLibraryEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getAnnexSubclause()
  {
    return annexSubclauseEClass;
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
  public EClass getTemporalConstraint()
  {
    return temporalConstraintEClass;
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
  public EClass getSafetyEqStatement()
  {
    return safetyEqStatementEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getInterval()
  {
    return intervalEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getInterval_Low()
  {
    return (EReference)intervalEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getInterval_High()
  {
    return (EReference)intervalEClass.getEStructuralFeatures().get(1);
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
  public EReference getFaultStatement_FaultDefName()
  {
    return (EReference)faultStatementEClass.getEStructuralFeatures().get(1);
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
  public EAttribute getInputStatement_Fault_in()
  {
    return (EAttribute)inputStatementEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getInputStatement_Nom_conn()
  {
    return (EReference)inputStatementEClass.getEStructuralFeatures().get(1);
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
  public EReference getOutputStatement_Nom_conn()
  {
    return (EReference)outputStatementEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getOutputStatement_Fault_out()
  {
    return (EAttribute)outputStatementEClass.getEStructuralFeatures().get(1);
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
  public EReference getDurationStatement_Tc()
  {
    return (EReference)durationStatementEClass.getEStructuralFeatures().get(0);
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
  public EClass getPermanentConstraint()
  {
    return permanentConstraintEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getTransientConstraint()
  {
    return transientConstraintEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getMustCondition()
  {
    return mustConditionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getEnablerCondition()
  {
    return enablerConditionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getEqValue()
  {
    return eqValueEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getEqValue_Lhs()
  {
    return (EReference)eqValueEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getEqValue_Expr()
  {
    return (EReference)eqValueEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getIntervalEq()
  {
    return intervalEqEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getIntervalEq_Lhs_int()
  {
    return (EAttribute)intervalEqEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getIntervalEq_Interv()
  {
    return (EReference)intervalEqEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getSetEq()
  {
    return setEqEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getSetEq_Lhs_set()
  {
    return (EAttribute)setEqEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getSetEq_L1()
  {
    return (EReference)setEqEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getSetEq_List()
  {
    return (EReference)setEqEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getRangeEq()
  {
    return rangeEqEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getRangeEq_Lhs_range()
  {
    return (EAttribute)rangeEqEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getRangeEq_L1()
  {
    return (EReference)rangeEqEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getRangeEq_L2()
  {
    return (EReference)rangeEqEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getClosedInterval()
  {
    return closedIntervalEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getOpenLeftInterval()
  {
    return openLeftIntervalEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getOpenRightInterval()
  {
    return openRightIntervalEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getOpenInterval()
  {
    return openIntervalEClass;
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
    annexLibraryEClass = createEClass(ANNEX_LIBRARY);

    annexSubclauseEClass = createEClass(ANNEX_SUBCLAUSE);

    safetyLibraryEClass = createEClass(SAFETY_LIBRARY);

    safetySubclauseEClass = createEClass(SAFETY_SUBCLAUSE);

    contractEClass = createEClass(CONTRACT);

    specStatementEClass = createEClass(SPEC_STATEMENT);

    faultSubcomponentEClass = createEClass(FAULT_SUBCOMPONENT);

    temporalConstraintEClass = createEClass(TEMPORAL_CONSTRAINT);

    triggerConditionEClass = createEClass(TRIGGER_CONDITION);
    createEReference(triggerConditionEClass, TRIGGER_CONDITION__EXPR_LIST);

    safetyEqStatementEClass = createEClass(SAFETY_EQ_STATEMENT);

    intervalEClass = createEClass(INTERVAL);
    createEReference(intervalEClass, INTERVAL__LOW);
    createEReference(intervalEClass, INTERVAL__HIGH);

    safetyContractLibraryEClass = createEClass(SAFETY_CONTRACT_LIBRARY);
    createEReference(safetyContractLibraryEClass, SAFETY_CONTRACT_LIBRARY__CONTRACT);

    safetyContractSubclauseEClass = createEClass(SAFETY_CONTRACT_SUBCLAUSE);
    createEReference(safetyContractSubclauseEClass, SAFETY_CONTRACT_SUBCLAUSE__CONTRACT);

    safetyContractEClass = createEClass(SAFETY_CONTRACT);
    createEReference(safetyContractEClass, SAFETY_CONTRACT__SPECS);

    faultStatementEClass = createEClass(FAULT_STATEMENT);
    createEAttribute(faultStatementEClass, FAULT_STATEMENT__STR);
    createEReference(faultStatementEClass, FAULT_STATEMENT__FAULT_DEF_NAME);
    createEReference(faultStatementEClass, FAULT_STATEMENT__FAULT_DEFINITIONS);

    inputStatementEClass = createEClass(INPUT_STATEMENT);
    createEAttribute(inputStatementEClass, INPUT_STATEMENT__FAULT_IN);
    createEReference(inputStatementEClass, INPUT_STATEMENT__NOM_CONN);

    outputStatementEClass = createEClass(OUTPUT_STATEMENT);
    createEReference(outputStatementEClass, OUTPUT_STATEMENT__NOM_CONN);
    createEAttribute(outputStatementEClass, OUTPUT_STATEMENT__FAULT_OUT);

    durationStatementEClass = createEClass(DURATION_STATEMENT);
    createEReference(durationStatementEClass, DURATION_STATEMENT__TC);
    createEReference(durationStatementEClass, DURATION_STATEMENT__INTERV);

    triggerStatementEClass = createEClass(TRIGGER_STATEMENT);
    createEReference(triggerStatementEClass, TRIGGER_STATEMENT__COND);
    createEAttribute(triggerStatementEClass, TRIGGER_STATEMENT__PROBABILITY);

    permanentConstraintEClass = createEClass(PERMANENT_CONSTRAINT);

    transientConstraintEClass = createEClass(TRANSIENT_CONSTRAINT);

    mustConditionEClass = createEClass(MUST_CONDITION);

    enablerConditionEClass = createEClass(ENABLER_CONDITION);

    eqValueEClass = createEClass(EQ_VALUE);
    createEReference(eqValueEClass, EQ_VALUE__LHS);
    createEReference(eqValueEClass, EQ_VALUE__EXPR);

    intervalEqEClass = createEClass(INTERVAL_EQ);
    createEAttribute(intervalEqEClass, INTERVAL_EQ__LHS_INT);
    createEReference(intervalEqEClass, INTERVAL_EQ__INTERV);

    setEqEClass = createEClass(SET_EQ);
    createEAttribute(setEqEClass, SET_EQ__LHS_SET);
    createEReference(setEqEClass, SET_EQ__L1);
    createEReference(setEqEClass, SET_EQ__LIST);

    rangeEqEClass = createEClass(RANGE_EQ);
    createEAttribute(rangeEqEClass, RANGE_EQ__LHS_RANGE);
    createEReference(rangeEqEClass, RANGE_EQ__L1);
    createEReference(rangeEqEClass, RANGE_EQ__L2);

    closedIntervalEClass = createEClass(CLOSED_INTERVAL);

    openLeftIntervalEClass = createEClass(OPEN_LEFT_INTERVAL);

    openRightIntervalEClass = createEClass(OPEN_RIGHT_INTERVAL);

    openIntervalEClass = createEClass(OPEN_INTERVAL);
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
    annexLibraryEClass.getESuperTypes().add(theAadl2Package.getAnnexLibrary());
    annexSubclauseEClass.getESuperTypes().add(theAadl2Package.getAnnexSubclause());
    safetyLibraryEClass.getESuperTypes().add(this.getAnnexLibrary());
    safetySubclauseEClass.getESuperTypes().add(this.getAnnexSubclause());
    specStatementEClass.getESuperTypes().add(theAgreePackage.getSpecStatement());
    faultSubcomponentEClass.getESuperTypes().add(theAadl2Package.getElement());
    safetyEqStatementEClass.getESuperTypes().add(theAadl2Package.getElement());
    safetyEqStatementEClass.getESuperTypes().add(this.getFaultSubcomponent());
    safetyContractLibraryEClass.getESuperTypes().add(this.getSafetyLibrary());
    safetyContractSubclauseEClass.getESuperTypes().add(this.getSafetySubclause());
    safetyContractEClass.getESuperTypes().add(this.getContract());
    faultStatementEClass.getESuperTypes().add(this.getSpecStatement());
    inputStatementEClass.getESuperTypes().add(this.getFaultSubcomponent());
    outputStatementEClass.getESuperTypes().add(this.getFaultSubcomponent());
    durationStatementEClass.getESuperTypes().add(this.getFaultSubcomponent());
    triggerStatementEClass.getESuperTypes().add(this.getFaultSubcomponent());
    permanentConstraintEClass.getESuperTypes().add(this.getTemporalConstraint());
    transientConstraintEClass.getESuperTypes().add(this.getTemporalConstraint());
    mustConditionEClass.getESuperTypes().add(this.getTriggerCondition());
    enablerConditionEClass.getESuperTypes().add(this.getTriggerCondition());
    eqValueEClass.getESuperTypes().add(this.getSafetyEqStatement());
    intervalEqEClass.getESuperTypes().add(this.getSafetyEqStatement());
    setEqEClass.getESuperTypes().add(this.getSafetyEqStatement());
    rangeEqEClass.getESuperTypes().add(this.getSafetyEqStatement());
    closedIntervalEClass.getESuperTypes().add(this.getInterval());
    openLeftIntervalEClass.getESuperTypes().add(this.getInterval());
    openRightIntervalEClass.getESuperTypes().add(this.getInterval());
    openIntervalEClass.getESuperTypes().add(this.getInterval());

    // Initialize classes and features; add operations and parameters
    initEClass(annexLibraryEClass, AnnexLibrary.class, "AnnexLibrary", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(annexSubclauseEClass, AnnexSubclause.class, "AnnexSubclause", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(safetyLibraryEClass, SafetyLibrary.class, "SafetyLibrary", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(safetySubclauseEClass, SafetySubclause.class, "SafetySubclause", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(contractEClass, Contract.class, "Contract", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(specStatementEClass, SpecStatement.class, "SpecStatement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(faultSubcomponentEClass, FaultSubcomponent.class, "FaultSubcomponent", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(temporalConstraintEClass, TemporalConstraint.class, "TemporalConstraint", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(triggerConditionEClass, TriggerCondition.class, "TriggerCondition", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getTriggerCondition_ExprList(), theAgreePackage.getExpr(), null, "exprList", null, 0, -1, TriggerCondition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(safetyEqStatementEClass, SafetyEqStatement.class, "SafetyEqStatement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(intervalEClass, Interval.class, "Interval", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getInterval_Low(), theAgreePackage.getExpr(), null, "low", null, 0, 1, Interval.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getInterval_High(), theAgreePackage.getExpr(), null, "high", null, 0, 1, Interval.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(safetyContractLibraryEClass, SafetyContractLibrary.class, "SafetyContractLibrary", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getSafetyContractLibrary_Contract(), this.getContract(), null, "contract", null, 0, 1, SafetyContractLibrary.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(safetyContractSubclauseEClass, SafetyContractSubclause.class, "SafetyContractSubclause", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getSafetyContractSubclause_Contract(), this.getContract(), null, "contract", null, 0, 1, SafetyContractSubclause.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(safetyContractEClass, SafetyContract.class, "SafetyContract", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getSafetyContract_Specs(), this.getSpecStatement(), null, "specs", null, 0, -1, SafetyContract.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(faultStatementEClass, FaultStatement.class, "FaultStatement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getFaultStatement_Str(), theEcorePackage.getEString(), "str", null, 0, 1, FaultStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getFaultStatement_FaultDefName(), theAgreePackage.getNestedDotID(), null, "faultDefName", null, 0, 1, FaultStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getFaultStatement_FaultDefinitions(), this.getFaultSubcomponent(), null, "faultDefinitions", null, 0, -1, FaultStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(inputStatementEClass, InputStatement.class, "InputStatement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getInputStatement_Fault_in(), theEcorePackage.getEString(), "fault_in", null, 0, -1, InputStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getInputStatement_Nom_conn(), theAgreePackage.getExpr(), null, "nom_conn", null, 0, -1, InputStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(outputStatementEClass, OutputStatement.class, "OutputStatement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getOutputStatement_Nom_conn(), theAgreePackage.getNestedDotID(), null, "nom_conn", null, 0, -1, OutputStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getOutputStatement_Fault_out(), theEcorePackage.getEString(), "fault_out", null, 0, -1, OutputStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(durationStatementEClass, DurationStatement.class, "DurationStatement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getDurationStatement_Tc(), this.getTemporalConstraint(), null, "tc", null, 0, 1, DurationStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getDurationStatement_Interv(), this.getInterval(), null, "interv", null, 0, 1, DurationStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(triggerStatementEClass, TriggerStatement.class, "TriggerStatement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getTriggerStatement_Cond(), this.getTriggerCondition(), null, "cond", null, 0, 1, TriggerStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getTriggerStatement_Probability(), theEcorePackage.getEString(), "probability", null, 0, 1, TriggerStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(permanentConstraintEClass, PermanentConstraint.class, "PermanentConstraint", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(transientConstraintEClass, TransientConstraint.class, "TransientConstraint", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(mustConditionEClass, MustCondition.class, "MustCondition", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(enablerConditionEClass, EnablerCondition.class, "EnablerCondition", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(eqValueEClass, EqValue.class, "EqValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getEqValue_Lhs(), theAgreePackage.getArg(), null, "lhs", null, 0, -1, EqValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getEqValue_Expr(), theAgreePackage.getExpr(), null, "expr", null, 0, 1, EqValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(intervalEqEClass, IntervalEq.class, "IntervalEq", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getIntervalEq_Lhs_int(), theEcorePackage.getEString(), "lhs_int", null, 0, 1, IntervalEq.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getIntervalEq_Interv(), this.getInterval(), null, "interv", null, 0, 1, IntervalEq.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(setEqEClass, SetEq.class, "SetEq", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getSetEq_Lhs_set(), theEcorePackage.getEString(), "lhs_set", null, 0, 1, SetEq.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getSetEq_L1(), theAgreePackage.getExpr(), null, "l1", null, 0, 1, SetEq.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getSetEq_List(), theAgreePackage.getExpr(), null, "list", null, 0, -1, SetEq.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(rangeEqEClass, RangeEq.class, "RangeEq", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getRangeEq_Lhs_range(), theEcorePackage.getEString(), "lhs_range", null, 0, 1, RangeEq.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getRangeEq_L1(), theAgreePackage.getExpr(), null, "l1", null, 0, 1, RangeEq.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getRangeEq_L2(), theAgreePackage.getExpr(), null, "l2", null, 0, 1, RangeEq.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(closedIntervalEClass, ClosedInterval.class, "ClosedInterval", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(openLeftIntervalEClass, OpenLeftInterval.class, "OpenLeftInterval", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(openRightIntervalEClass, OpenRightInterval.class, "OpenRightInterval", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(openIntervalEClass, OpenInterval.class, "OpenInterval", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    // Create resource
    createResource(eNS_URI);
  }

} //SafetyPackageImpl
