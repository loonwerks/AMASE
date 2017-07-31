/**
 */
package edu.umn.cs.crisys.safety.safety;

import com.rockwellcollins.atc.agree.agree.AgreePackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.osate.aadl2.Aadl2Package;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see edu.umn.cs.crisys.safety.safety.SafetyFactory
 * @model kind="package"
 * @generated
 */
public interface SafetyPackage extends EPackage
{
  /**
   * The package name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNAME = "safety";

  /**
   * The package namespace URI.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_URI = "http://www.xtext.org/example/safety/Safety";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "safety";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  SafetyPackage eINSTANCE = edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl.init();

  /**
   * The meta object id for the '{@link edu.umn.cs.crisys.safety.safety.impl.SafetyLibraryImpl <em>Library</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.umn.cs.crisys.safety.safety.impl.SafetyLibraryImpl
   * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getSafetyLibrary()
   * @generated
   */
  int SAFETY_LIBRARY = 0;

  /**
   * The feature id for the '<em><b>Owned Element</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SAFETY_LIBRARY__OWNED_ELEMENT = Aadl2Package.ANNEX_LIBRARY__OWNED_ELEMENT;

  /**
   * The feature id for the '<em><b>Owned Comment</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SAFETY_LIBRARY__OWNED_COMMENT = Aadl2Package.ANNEX_LIBRARY__OWNED_COMMENT;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SAFETY_LIBRARY__NAME = Aadl2Package.ANNEX_LIBRARY__NAME;

  /**
   * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SAFETY_LIBRARY__QUALIFIED_NAME = Aadl2Package.ANNEX_LIBRARY__QUALIFIED_NAME;

  /**
   * The feature id for the '<em><b>Owned Property Association</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SAFETY_LIBRARY__OWNED_PROPERTY_ASSOCIATION = Aadl2Package.ANNEX_LIBRARY__OWNED_PROPERTY_ASSOCIATION;

  /**
   * The number of structural features of the '<em>Library</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SAFETY_LIBRARY_FEATURE_COUNT = Aadl2Package.ANNEX_LIBRARY_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link edu.umn.cs.crisys.safety.safety.impl.SafetySubclauseImpl <em>Subclause</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.umn.cs.crisys.safety.safety.impl.SafetySubclauseImpl
   * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getSafetySubclause()
   * @generated
   */
  int SAFETY_SUBCLAUSE = 1;

  /**
   * The feature id for the '<em><b>Owned Element</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SAFETY_SUBCLAUSE__OWNED_ELEMENT = Aadl2Package.ANNEX_SUBCLAUSE__OWNED_ELEMENT;

  /**
   * The feature id for the '<em><b>Owned Comment</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SAFETY_SUBCLAUSE__OWNED_COMMENT = Aadl2Package.ANNEX_SUBCLAUSE__OWNED_COMMENT;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SAFETY_SUBCLAUSE__NAME = Aadl2Package.ANNEX_SUBCLAUSE__NAME;

  /**
   * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SAFETY_SUBCLAUSE__QUALIFIED_NAME = Aadl2Package.ANNEX_SUBCLAUSE__QUALIFIED_NAME;

  /**
   * The feature id for the '<em><b>Owned Property Association</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SAFETY_SUBCLAUSE__OWNED_PROPERTY_ASSOCIATION = Aadl2Package.ANNEX_SUBCLAUSE__OWNED_PROPERTY_ASSOCIATION;

  /**
   * The feature id for the '<em><b>In Mode</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SAFETY_SUBCLAUSE__IN_MODE = Aadl2Package.ANNEX_SUBCLAUSE__IN_MODE;

  /**
   * The number of structural features of the '<em>Subclause</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SAFETY_SUBCLAUSE_FEATURE_COUNT = Aadl2Package.ANNEX_SUBCLAUSE_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link edu.umn.cs.crisys.safety.safety.impl.ContractImpl <em>Contract</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.umn.cs.crisys.safety.safety.impl.ContractImpl
   * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getContract()
   * @generated
   */
  int CONTRACT = 2;

  /**
   * The number of structural features of the '<em>Contract</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONTRACT_FEATURE_COUNT = 0;

  /**
   * The meta object id for the '{@link edu.umn.cs.crisys.safety.safety.impl.SpecStatementImpl <em>Spec Statement</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.umn.cs.crisys.safety.safety.impl.SpecStatementImpl
   * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getSpecStatement()
   * @generated
   */
  int SPEC_STATEMENT = 3;

  /**
   * The feature id for the '<em><b>Owned Element</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SPEC_STATEMENT__OWNED_ELEMENT = AgreePackage.SPEC_STATEMENT__OWNED_ELEMENT;

  /**
   * The feature id for the '<em><b>Owned Comment</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SPEC_STATEMENT__OWNED_COMMENT = AgreePackage.SPEC_STATEMENT__OWNED_COMMENT;

  /**
   * The number of structural features of the '<em>Spec Statement</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SPEC_STATEMENT_FEATURE_COUNT = AgreePackage.SPEC_STATEMENT_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link edu.umn.cs.crisys.safety.safety.impl.FaultSubcomponentImpl <em>Fault Subcomponent</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.umn.cs.crisys.safety.safety.impl.FaultSubcomponentImpl
   * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getFaultSubcomponent()
   * @generated
   */
  int FAULT_SUBCOMPONENT = 4;

  /**
   * The number of structural features of the '<em>Fault Subcomponent</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FAULT_SUBCOMPONENT_FEATURE_COUNT = 0;

  /**
   * The meta object id for the '{@link edu.umn.cs.crisys.safety.safety.impl.TemporalConstraintImpl <em>Temporal Constraint</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.umn.cs.crisys.safety.safety.impl.TemporalConstraintImpl
   * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getTemporalConstraint()
   * @generated
   */
  int TEMPORAL_CONSTRAINT = 5;

  /**
   * The number of structural features of the '<em>Temporal Constraint</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TEMPORAL_CONSTRAINT_FEATURE_COUNT = 0;

  /**
   * The meta object id for the '{@link edu.umn.cs.crisys.safety.safety.impl.TriggerConditionImpl <em>Trigger Condition</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.umn.cs.crisys.safety.safety.impl.TriggerConditionImpl
   * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getTriggerCondition()
   * @generated
   */
  int TRIGGER_CONDITION = 6;

  /**
   * The feature id for the '<em><b>Expr List</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRIGGER_CONDITION__EXPR_LIST = 0;

  /**
   * The number of structural features of the '<em>Trigger Condition</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRIGGER_CONDITION_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link edu.umn.cs.crisys.safety.safety.impl.SafetyEqStatementImpl <em>Eq Statement</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.umn.cs.crisys.safety.safety.impl.SafetyEqStatementImpl
   * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getSafetyEqStatement()
   * @generated
   */
  int SAFETY_EQ_STATEMENT = 7;

  /**
   * The number of structural features of the '<em>Eq Statement</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SAFETY_EQ_STATEMENT_FEATURE_COUNT = FAULT_SUBCOMPONENT_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link edu.umn.cs.crisys.safety.safety.impl.SafetyContractLibraryImpl <em>Contract Library</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.umn.cs.crisys.safety.safety.impl.SafetyContractLibraryImpl
   * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getSafetyContractLibrary()
   * @generated
   */
  int SAFETY_CONTRACT_LIBRARY = 8;

  /**
   * The feature id for the '<em><b>Owned Element</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SAFETY_CONTRACT_LIBRARY__OWNED_ELEMENT = SAFETY_LIBRARY__OWNED_ELEMENT;

  /**
   * The feature id for the '<em><b>Owned Comment</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SAFETY_CONTRACT_LIBRARY__OWNED_COMMENT = SAFETY_LIBRARY__OWNED_COMMENT;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SAFETY_CONTRACT_LIBRARY__NAME = SAFETY_LIBRARY__NAME;

  /**
   * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SAFETY_CONTRACT_LIBRARY__QUALIFIED_NAME = SAFETY_LIBRARY__QUALIFIED_NAME;

  /**
   * The feature id for the '<em><b>Owned Property Association</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SAFETY_CONTRACT_LIBRARY__OWNED_PROPERTY_ASSOCIATION = SAFETY_LIBRARY__OWNED_PROPERTY_ASSOCIATION;

  /**
   * The feature id for the '<em><b>Contract</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SAFETY_CONTRACT_LIBRARY__CONTRACT = SAFETY_LIBRARY_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Contract Library</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SAFETY_CONTRACT_LIBRARY_FEATURE_COUNT = SAFETY_LIBRARY_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link edu.umn.cs.crisys.safety.safety.impl.SafetyContractSubclauseImpl <em>Contract Subclause</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.umn.cs.crisys.safety.safety.impl.SafetyContractSubclauseImpl
   * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getSafetyContractSubclause()
   * @generated
   */
  int SAFETY_CONTRACT_SUBCLAUSE = 9;

  /**
   * The feature id for the '<em><b>Owned Element</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SAFETY_CONTRACT_SUBCLAUSE__OWNED_ELEMENT = SAFETY_SUBCLAUSE__OWNED_ELEMENT;

  /**
   * The feature id for the '<em><b>Owned Comment</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SAFETY_CONTRACT_SUBCLAUSE__OWNED_COMMENT = SAFETY_SUBCLAUSE__OWNED_COMMENT;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SAFETY_CONTRACT_SUBCLAUSE__NAME = SAFETY_SUBCLAUSE__NAME;

  /**
   * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SAFETY_CONTRACT_SUBCLAUSE__QUALIFIED_NAME = SAFETY_SUBCLAUSE__QUALIFIED_NAME;

  /**
   * The feature id for the '<em><b>Owned Property Association</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SAFETY_CONTRACT_SUBCLAUSE__OWNED_PROPERTY_ASSOCIATION = SAFETY_SUBCLAUSE__OWNED_PROPERTY_ASSOCIATION;

  /**
   * The feature id for the '<em><b>In Mode</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SAFETY_CONTRACT_SUBCLAUSE__IN_MODE = SAFETY_SUBCLAUSE__IN_MODE;

  /**
   * The feature id for the '<em><b>Contract</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SAFETY_CONTRACT_SUBCLAUSE__CONTRACT = SAFETY_SUBCLAUSE_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Contract Subclause</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SAFETY_CONTRACT_SUBCLAUSE_FEATURE_COUNT = SAFETY_SUBCLAUSE_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link edu.umn.cs.crisys.safety.safety.impl.SafetyContractImpl <em>Contract</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.umn.cs.crisys.safety.safety.impl.SafetyContractImpl
   * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getSafetyContract()
   * @generated
   */
  int SAFETY_CONTRACT = 10;

  /**
   * The feature id for the '<em><b>Specs</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SAFETY_CONTRACT__SPECS = CONTRACT_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Contract</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SAFETY_CONTRACT_FEATURE_COUNT = CONTRACT_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link edu.umn.cs.crisys.safety.safety.impl.FaultStatementImpl <em>Fault Statement</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.umn.cs.crisys.safety.safety.impl.FaultStatementImpl
   * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getFaultStatement()
   * @generated
   */
  int FAULT_STATEMENT = 11;

  /**
   * The feature id for the '<em><b>Owned Element</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FAULT_STATEMENT__OWNED_ELEMENT = SPEC_STATEMENT__OWNED_ELEMENT;

  /**
   * The feature id for the '<em><b>Owned Comment</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FAULT_STATEMENT__OWNED_COMMENT = SPEC_STATEMENT__OWNED_COMMENT;

  /**
   * The feature id for the '<em><b>Str</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FAULT_STATEMENT__STR = SPEC_STATEMENT_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Fault Def Name</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FAULT_STATEMENT__FAULT_DEF_NAME = SPEC_STATEMENT_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Fault Definitions</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FAULT_STATEMENT__FAULT_DEFINITIONS = SPEC_STATEMENT_FEATURE_COUNT + 2;

  /**
   * The number of structural features of the '<em>Fault Statement</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FAULT_STATEMENT_FEATURE_COUNT = SPEC_STATEMENT_FEATURE_COUNT + 3;

  /**
   * The meta object id for the '{@link edu.umn.cs.crisys.safety.safety.impl.InputStatementImpl <em>Input Statement</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.umn.cs.crisys.safety.safety.impl.InputStatementImpl
   * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getInputStatement()
   * @generated
   */
  int INPUT_STATEMENT = 12;

  /**
   * The feature id for the '<em><b>In conn</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INPUT_STATEMENT__IN_CONN = FAULT_SUBCOMPONENT_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Out conn</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INPUT_STATEMENT__OUT_CONN = FAULT_SUBCOMPONENT_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Input Statement</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INPUT_STATEMENT_FEATURE_COUNT = FAULT_SUBCOMPONENT_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link edu.umn.cs.crisys.safety.safety.impl.OutputStatementImpl <em>Output Statement</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.umn.cs.crisys.safety.safety.impl.OutputStatementImpl
   * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getOutputStatement()
   * @generated
   */
  int OUTPUT_STATEMENT = 13;

  /**
   * The feature id for the '<em><b>Out conn</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OUTPUT_STATEMENT__OUT_CONN = FAULT_SUBCOMPONENT_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Nom conn</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OUTPUT_STATEMENT__NOM_CONN = FAULT_SUBCOMPONENT_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Output Statement</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OUTPUT_STATEMENT_FEATURE_COUNT = FAULT_SUBCOMPONENT_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link edu.umn.cs.crisys.safety.safety.impl.DurationStatementImpl <em>Duration Statement</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.umn.cs.crisys.safety.safety.impl.DurationStatementImpl
   * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getDurationStatement()
   * @generated
   */
  int DURATION_STATEMENT = 14;

  /**
   * The feature id for the '<em><b>Tc</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DURATION_STATEMENT__TC = FAULT_SUBCOMPONENT_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Interv</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DURATION_STATEMENT__INTERV = FAULT_SUBCOMPONENT_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Duration Statement</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DURATION_STATEMENT_FEATURE_COUNT = FAULT_SUBCOMPONENT_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link edu.umn.cs.crisys.safety.safety.impl.TriggerStatementImpl <em>Trigger Statement</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.umn.cs.crisys.safety.safety.impl.TriggerStatementImpl
   * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getTriggerStatement()
   * @generated
   */
  int TRIGGER_STATEMENT = 15;

  /**
   * The feature id for the '<em><b>Cond</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRIGGER_STATEMENT__COND = FAULT_SUBCOMPONENT_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Probability</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRIGGER_STATEMENT__PROBABILITY = FAULT_SUBCOMPONENT_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Trigger Statement</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRIGGER_STATEMENT_FEATURE_COUNT = FAULT_SUBCOMPONENT_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link edu.umn.cs.crisys.safety.safety.impl.PermanentConstraintImpl <em>Permanent Constraint</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.umn.cs.crisys.safety.safety.impl.PermanentConstraintImpl
   * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getPermanentConstraint()
   * @generated
   */
  int PERMANENT_CONSTRAINT = 16;

  /**
   * The number of structural features of the '<em>Permanent Constraint</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PERMANENT_CONSTRAINT_FEATURE_COUNT = TEMPORAL_CONSTRAINT_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link edu.umn.cs.crisys.safety.safety.impl.TransientConstraintImpl <em>Transient Constraint</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.umn.cs.crisys.safety.safety.impl.TransientConstraintImpl
   * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getTransientConstraint()
   * @generated
   */
  int TRANSIENT_CONSTRAINT = 17;

  /**
   * The number of structural features of the '<em>Transient Constraint</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRANSIENT_CONSTRAINT_FEATURE_COUNT = TEMPORAL_CONSTRAINT_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link edu.umn.cs.crisys.safety.safety.impl.MustConditionImpl <em>Must Condition</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.umn.cs.crisys.safety.safety.impl.MustConditionImpl
   * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getMustCondition()
   * @generated
   */
  int MUST_CONDITION = 18;

  /**
   * The feature id for the '<em><b>Expr List</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MUST_CONDITION__EXPR_LIST = TRIGGER_CONDITION__EXPR_LIST;

  /**
   * The number of structural features of the '<em>Must Condition</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MUST_CONDITION_FEATURE_COUNT = TRIGGER_CONDITION_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link edu.umn.cs.crisys.safety.safety.impl.EnablerConditionImpl <em>Enabler Condition</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.umn.cs.crisys.safety.safety.impl.EnablerConditionImpl
   * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getEnablerCondition()
   * @generated
   */
  int ENABLER_CONDITION = 19;

  /**
   * The feature id for the '<em><b>Expr List</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ENABLER_CONDITION__EXPR_LIST = TRIGGER_CONDITION__EXPR_LIST;

  /**
   * The number of structural features of the '<em>Enabler Condition</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ENABLER_CONDITION_FEATURE_COUNT = TRIGGER_CONDITION_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link edu.umn.cs.crisys.safety.safety.impl.EqValueImpl <em>Eq Value</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.umn.cs.crisys.safety.safety.impl.EqValueImpl
   * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getEqValue()
   * @generated
   */
  int EQ_VALUE = 20;

  /**
   * The feature id for the '<em><b>Lhs</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EQ_VALUE__LHS = SAFETY_EQ_STATEMENT_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Expr</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EQ_VALUE__EXPR = SAFETY_EQ_STATEMENT_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Eq Value</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EQ_VALUE_FEATURE_COUNT = SAFETY_EQ_STATEMENT_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link edu.umn.cs.crisys.safety.safety.impl.IntervalEqImpl <em>Interval Eq</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.umn.cs.crisys.safety.safety.impl.IntervalEqImpl
   * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getIntervalEq()
   * @generated
   */
  int INTERVAL_EQ = 21;

  /**
   * The feature id for the '<em><b>Lhs int</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTERVAL_EQ__LHS_INT = SAFETY_EQ_STATEMENT_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Interv</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTERVAL_EQ__INTERV = SAFETY_EQ_STATEMENT_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Interval Eq</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTERVAL_EQ_FEATURE_COUNT = SAFETY_EQ_STATEMENT_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link edu.umn.cs.crisys.safety.safety.impl.SetEqImpl <em>Set Eq</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.umn.cs.crisys.safety.safety.impl.SetEqImpl
   * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getSetEq()
   * @generated
   */
  int SET_EQ = 22;

  /**
   * The feature id for the '<em><b>Lhs set</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SET_EQ__LHS_SET = SAFETY_EQ_STATEMENT_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>L1</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SET_EQ__L1 = SAFETY_EQ_STATEMENT_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>List</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SET_EQ__LIST = SAFETY_EQ_STATEMENT_FEATURE_COUNT + 2;

  /**
   * The number of structural features of the '<em>Set Eq</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SET_EQ_FEATURE_COUNT = SAFETY_EQ_STATEMENT_FEATURE_COUNT + 3;


  /**
   * Returns the meta object for class '{@link edu.umn.cs.crisys.safety.safety.SafetyLibrary <em>Library</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Library</em>'.
   * @see edu.umn.cs.crisys.safety.safety.SafetyLibrary
   * @generated
   */
  EClass getSafetyLibrary();

  /**
   * Returns the meta object for class '{@link edu.umn.cs.crisys.safety.safety.SafetySubclause <em>Subclause</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Subclause</em>'.
   * @see edu.umn.cs.crisys.safety.safety.SafetySubclause
   * @generated
   */
  EClass getSafetySubclause();

  /**
   * Returns the meta object for class '{@link edu.umn.cs.crisys.safety.safety.Contract <em>Contract</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Contract</em>'.
   * @see edu.umn.cs.crisys.safety.safety.Contract
   * @generated
   */
  EClass getContract();

  /**
   * Returns the meta object for class '{@link edu.umn.cs.crisys.safety.safety.SpecStatement <em>Spec Statement</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Spec Statement</em>'.
   * @see edu.umn.cs.crisys.safety.safety.SpecStatement
   * @generated
   */
  EClass getSpecStatement();

  /**
   * Returns the meta object for class '{@link edu.umn.cs.crisys.safety.safety.FaultSubcomponent <em>Fault Subcomponent</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Fault Subcomponent</em>'.
   * @see edu.umn.cs.crisys.safety.safety.FaultSubcomponent
   * @generated
   */
  EClass getFaultSubcomponent();

  /**
   * Returns the meta object for class '{@link edu.umn.cs.crisys.safety.safety.TemporalConstraint <em>Temporal Constraint</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Temporal Constraint</em>'.
   * @see edu.umn.cs.crisys.safety.safety.TemporalConstraint
   * @generated
   */
  EClass getTemporalConstraint();

  /**
   * Returns the meta object for class '{@link edu.umn.cs.crisys.safety.safety.TriggerCondition <em>Trigger Condition</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Trigger Condition</em>'.
   * @see edu.umn.cs.crisys.safety.safety.TriggerCondition
   * @generated
   */
  EClass getTriggerCondition();

  /**
   * Returns the meta object for the containment reference list '{@link edu.umn.cs.crisys.safety.safety.TriggerCondition#getExprList <em>Expr List</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Expr List</em>'.
   * @see edu.umn.cs.crisys.safety.safety.TriggerCondition#getExprList()
   * @see #getTriggerCondition()
   * @generated
   */
  EReference getTriggerCondition_ExprList();

  /**
   * Returns the meta object for class '{@link edu.umn.cs.crisys.safety.safety.SafetyEqStatement <em>Eq Statement</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Eq Statement</em>'.
   * @see edu.umn.cs.crisys.safety.safety.SafetyEqStatement
   * @generated
   */
  EClass getSafetyEqStatement();

  /**
   * Returns the meta object for class '{@link edu.umn.cs.crisys.safety.safety.SafetyContractLibrary <em>Contract Library</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Contract Library</em>'.
   * @see edu.umn.cs.crisys.safety.safety.SafetyContractLibrary
   * @generated
   */
  EClass getSafetyContractLibrary();

  /**
   * Returns the meta object for the containment reference '{@link edu.umn.cs.crisys.safety.safety.SafetyContractLibrary#getContract <em>Contract</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Contract</em>'.
   * @see edu.umn.cs.crisys.safety.safety.SafetyContractLibrary#getContract()
   * @see #getSafetyContractLibrary()
   * @generated
   */
  EReference getSafetyContractLibrary_Contract();

  /**
   * Returns the meta object for class '{@link edu.umn.cs.crisys.safety.safety.SafetyContractSubclause <em>Contract Subclause</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Contract Subclause</em>'.
   * @see edu.umn.cs.crisys.safety.safety.SafetyContractSubclause
   * @generated
   */
  EClass getSafetyContractSubclause();

  /**
   * Returns the meta object for the containment reference '{@link edu.umn.cs.crisys.safety.safety.SafetyContractSubclause#getContract <em>Contract</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Contract</em>'.
   * @see edu.umn.cs.crisys.safety.safety.SafetyContractSubclause#getContract()
   * @see #getSafetyContractSubclause()
   * @generated
   */
  EReference getSafetyContractSubclause_Contract();

  /**
   * Returns the meta object for class '{@link edu.umn.cs.crisys.safety.safety.SafetyContract <em>Contract</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Contract</em>'.
   * @see edu.umn.cs.crisys.safety.safety.SafetyContract
   * @generated
   */
  EClass getSafetyContract();

  /**
   * Returns the meta object for the containment reference list '{@link edu.umn.cs.crisys.safety.safety.SafetyContract#getSpecs <em>Specs</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Specs</em>'.
   * @see edu.umn.cs.crisys.safety.safety.SafetyContract#getSpecs()
   * @see #getSafetyContract()
   * @generated
   */
  EReference getSafetyContract_Specs();

  /**
   * Returns the meta object for class '{@link edu.umn.cs.crisys.safety.safety.FaultStatement <em>Fault Statement</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Fault Statement</em>'.
   * @see edu.umn.cs.crisys.safety.safety.FaultStatement
   * @generated
   */
  EClass getFaultStatement();

  /**
   * Returns the meta object for the attribute '{@link edu.umn.cs.crisys.safety.safety.FaultStatement#getStr <em>Str</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Str</em>'.
   * @see edu.umn.cs.crisys.safety.safety.FaultStatement#getStr()
   * @see #getFaultStatement()
   * @generated
   */
  EAttribute getFaultStatement_Str();

  /**
   * Returns the meta object for the containment reference '{@link edu.umn.cs.crisys.safety.safety.FaultStatement#getFaultDefName <em>Fault Def Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Fault Def Name</em>'.
   * @see edu.umn.cs.crisys.safety.safety.FaultStatement#getFaultDefName()
   * @see #getFaultStatement()
   * @generated
   */
  EReference getFaultStatement_FaultDefName();

  /**
   * Returns the meta object for the containment reference list '{@link edu.umn.cs.crisys.safety.safety.FaultStatement#getFaultDefinitions <em>Fault Definitions</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Fault Definitions</em>'.
   * @see edu.umn.cs.crisys.safety.safety.FaultStatement#getFaultDefinitions()
   * @see #getFaultStatement()
   * @generated
   */
  EReference getFaultStatement_FaultDefinitions();

  /**
   * Returns the meta object for class '{@link edu.umn.cs.crisys.safety.safety.InputStatement <em>Input Statement</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Input Statement</em>'.
   * @see edu.umn.cs.crisys.safety.safety.InputStatement
   * @generated
   */
  EClass getInputStatement();

  /**
   * Returns the meta object for the attribute '{@link edu.umn.cs.crisys.safety.safety.InputStatement#getIn_conn <em>In conn</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>In conn</em>'.
   * @see edu.umn.cs.crisys.safety.safety.InputStatement#getIn_conn()
   * @see #getInputStatement()
   * @generated
   */
  EAttribute getInputStatement_In_conn();

  /**
   * Returns the meta object for the attribute '{@link edu.umn.cs.crisys.safety.safety.InputStatement#getOut_conn <em>Out conn</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Out conn</em>'.
   * @see edu.umn.cs.crisys.safety.safety.InputStatement#getOut_conn()
   * @see #getInputStatement()
   * @generated
   */
  EAttribute getInputStatement_Out_conn();

  /**
   * Returns the meta object for class '{@link edu.umn.cs.crisys.safety.safety.OutputStatement <em>Output Statement</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Output Statement</em>'.
   * @see edu.umn.cs.crisys.safety.safety.OutputStatement
   * @generated
   */
  EClass getOutputStatement();

  /**
   * Returns the meta object for the attribute '{@link edu.umn.cs.crisys.safety.safety.OutputStatement#getOut_conn <em>Out conn</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Out conn</em>'.
   * @see edu.umn.cs.crisys.safety.safety.OutputStatement#getOut_conn()
   * @see #getOutputStatement()
   * @generated
   */
  EAttribute getOutputStatement_Out_conn();

  /**
   * Returns the meta object for the attribute '{@link edu.umn.cs.crisys.safety.safety.OutputStatement#getNom_conn <em>Nom conn</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Nom conn</em>'.
   * @see edu.umn.cs.crisys.safety.safety.OutputStatement#getNom_conn()
   * @see #getOutputStatement()
   * @generated
   */
  EAttribute getOutputStatement_Nom_conn();

  /**
   * Returns the meta object for class '{@link edu.umn.cs.crisys.safety.safety.DurationStatement <em>Duration Statement</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Duration Statement</em>'.
   * @see edu.umn.cs.crisys.safety.safety.DurationStatement
   * @generated
   */
  EClass getDurationStatement();

  /**
   * Returns the meta object for the containment reference '{@link edu.umn.cs.crisys.safety.safety.DurationStatement#getTc <em>Tc</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Tc</em>'.
   * @see edu.umn.cs.crisys.safety.safety.DurationStatement#getTc()
   * @see #getDurationStatement()
   * @generated
   */
  EReference getDurationStatement_Tc();

  /**
   * Returns the meta object for the containment reference '{@link edu.umn.cs.crisys.safety.safety.DurationStatement#getInterv <em>Interv</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Interv</em>'.
   * @see edu.umn.cs.crisys.safety.safety.DurationStatement#getInterv()
   * @see #getDurationStatement()
   * @generated
   */
  EReference getDurationStatement_Interv();

  /**
   * Returns the meta object for class '{@link edu.umn.cs.crisys.safety.safety.TriggerStatement <em>Trigger Statement</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Trigger Statement</em>'.
   * @see edu.umn.cs.crisys.safety.safety.TriggerStatement
   * @generated
   */
  EClass getTriggerStatement();

  /**
   * Returns the meta object for the containment reference '{@link edu.umn.cs.crisys.safety.safety.TriggerStatement#getCond <em>Cond</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Cond</em>'.
   * @see edu.umn.cs.crisys.safety.safety.TriggerStatement#getCond()
   * @see #getTriggerStatement()
   * @generated
   */
  EReference getTriggerStatement_Cond();

  /**
   * Returns the meta object for the attribute '{@link edu.umn.cs.crisys.safety.safety.TriggerStatement#getProbability <em>Probability</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Probability</em>'.
   * @see edu.umn.cs.crisys.safety.safety.TriggerStatement#getProbability()
   * @see #getTriggerStatement()
   * @generated
   */
  EAttribute getTriggerStatement_Probability();

  /**
   * Returns the meta object for class '{@link edu.umn.cs.crisys.safety.safety.PermanentConstraint <em>Permanent Constraint</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Permanent Constraint</em>'.
   * @see edu.umn.cs.crisys.safety.safety.PermanentConstraint
   * @generated
   */
  EClass getPermanentConstraint();

  /**
   * Returns the meta object for class '{@link edu.umn.cs.crisys.safety.safety.TransientConstraint <em>Transient Constraint</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Transient Constraint</em>'.
   * @see edu.umn.cs.crisys.safety.safety.TransientConstraint
   * @generated
   */
  EClass getTransientConstraint();

  /**
   * Returns the meta object for class '{@link edu.umn.cs.crisys.safety.safety.MustCondition <em>Must Condition</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Must Condition</em>'.
   * @see edu.umn.cs.crisys.safety.safety.MustCondition
   * @generated
   */
  EClass getMustCondition();

  /**
   * Returns the meta object for class '{@link edu.umn.cs.crisys.safety.safety.EnablerCondition <em>Enabler Condition</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Enabler Condition</em>'.
   * @see edu.umn.cs.crisys.safety.safety.EnablerCondition
   * @generated
   */
  EClass getEnablerCondition();

  /**
   * Returns the meta object for class '{@link edu.umn.cs.crisys.safety.safety.EqValue <em>Eq Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Eq Value</em>'.
   * @see edu.umn.cs.crisys.safety.safety.EqValue
   * @generated
   */
  EClass getEqValue();

  /**
   * Returns the meta object for the containment reference list '{@link edu.umn.cs.crisys.safety.safety.EqValue#getLhs <em>Lhs</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Lhs</em>'.
   * @see edu.umn.cs.crisys.safety.safety.EqValue#getLhs()
   * @see #getEqValue()
   * @generated
   */
  EReference getEqValue_Lhs();

  /**
   * Returns the meta object for the containment reference '{@link edu.umn.cs.crisys.safety.safety.EqValue#getExpr <em>Expr</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Expr</em>'.
   * @see edu.umn.cs.crisys.safety.safety.EqValue#getExpr()
   * @see #getEqValue()
   * @generated
   */
  EReference getEqValue_Expr();

  /**
   * Returns the meta object for class '{@link edu.umn.cs.crisys.safety.safety.IntervalEq <em>Interval Eq</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Interval Eq</em>'.
   * @see edu.umn.cs.crisys.safety.safety.IntervalEq
   * @generated
   */
  EClass getIntervalEq();

  /**
   * Returns the meta object for the attribute '{@link edu.umn.cs.crisys.safety.safety.IntervalEq#getLhs_int <em>Lhs int</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Lhs int</em>'.
   * @see edu.umn.cs.crisys.safety.safety.IntervalEq#getLhs_int()
   * @see #getIntervalEq()
   * @generated
   */
  EAttribute getIntervalEq_Lhs_int();

  /**
   * Returns the meta object for the containment reference '{@link edu.umn.cs.crisys.safety.safety.IntervalEq#getInterv <em>Interv</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Interv</em>'.
   * @see edu.umn.cs.crisys.safety.safety.IntervalEq#getInterv()
   * @see #getIntervalEq()
   * @generated
   */
  EReference getIntervalEq_Interv();

  /**
   * Returns the meta object for class '{@link edu.umn.cs.crisys.safety.safety.SetEq <em>Set Eq</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Set Eq</em>'.
   * @see edu.umn.cs.crisys.safety.safety.SetEq
   * @generated
   */
  EClass getSetEq();

  /**
   * Returns the meta object for the attribute '{@link edu.umn.cs.crisys.safety.safety.SetEq#getLhs_set <em>Lhs set</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Lhs set</em>'.
   * @see edu.umn.cs.crisys.safety.safety.SetEq#getLhs_set()
   * @see #getSetEq()
   * @generated
   */
  EAttribute getSetEq_Lhs_set();

  /**
   * Returns the meta object for the attribute '{@link edu.umn.cs.crisys.safety.safety.SetEq#getL1 <em>L1</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>L1</em>'.
   * @see edu.umn.cs.crisys.safety.safety.SetEq#getL1()
   * @see #getSetEq()
   * @generated
   */
  EAttribute getSetEq_L1();

  /**
   * Returns the meta object for the attribute list '{@link edu.umn.cs.crisys.safety.safety.SetEq#getList <em>List</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>List</em>'.
   * @see edu.umn.cs.crisys.safety.safety.SetEq#getList()
   * @see #getSetEq()
   * @generated
   */
  EAttribute getSetEq_List();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
  SafetyFactory getSafetyFactory();

  /**
   * <!-- begin-user-doc -->
   * Defines literals for the meta objects that represent
   * <ul>
   *   <li>each class,</li>
   *   <li>each feature of each class,</li>
   *   <li>each enum,</li>
   *   <li>and each data type</li>
   * </ul>
   * <!-- end-user-doc -->
   * @generated
   */
  interface Literals
  {
    /**
     * The meta object literal for the '{@link edu.umn.cs.crisys.safety.safety.impl.SafetyLibraryImpl <em>Library</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.umn.cs.crisys.safety.safety.impl.SafetyLibraryImpl
     * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getSafetyLibrary()
     * @generated
     */
    EClass SAFETY_LIBRARY = eINSTANCE.getSafetyLibrary();

    /**
     * The meta object literal for the '{@link edu.umn.cs.crisys.safety.safety.impl.SafetySubclauseImpl <em>Subclause</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.umn.cs.crisys.safety.safety.impl.SafetySubclauseImpl
     * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getSafetySubclause()
     * @generated
     */
    EClass SAFETY_SUBCLAUSE = eINSTANCE.getSafetySubclause();

    /**
     * The meta object literal for the '{@link edu.umn.cs.crisys.safety.safety.impl.ContractImpl <em>Contract</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.umn.cs.crisys.safety.safety.impl.ContractImpl
     * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getContract()
     * @generated
     */
    EClass CONTRACT = eINSTANCE.getContract();

    /**
     * The meta object literal for the '{@link edu.umn.cs.crisys.safety.safety.impl.SpecStatementImpl <em>Spec Statement</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.umn.cs.crisys.safety.safety.impl.SpecStatementImpl
     * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getSpecStatement()
     * @generated
     */
    EClass SPEC_STATEMENT = eINSTANCE.getSpecStatement();

    /**
     * The meta object literal for the '{@link edu.umn.cs.crisys.safety.safety.impl.FaultSubcomponentImpl <em>Fault Subcomponent</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.umn.cs.crisys.safety.safety.impl.FaultSubcomponentImpl
     * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getFaultSubcomponent()
     * @generated
     */
    EClass FAULT_SUBCOMPONENT = eINSTANCE.getFaultSubcomponent();

    /**
     * The meta object literal for the '{@link edu.umn.cs.crisys.safety.safety.impl.TemporalConstraintImpl <em>Temporal Constraint</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.umn.cs.crisys.safety.safety.impl.TemporalConstraintImpl
     * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getTemporalConstraint()
     * @generated
     */
    EClass TEMPORAL_CONSTRAINT = eINSTANCE.getTemporalConstraint();

    /**
     * The meta object literal for the '{@link edu.umn.cs.crisys.safety.safety.impl.TriggerConditionImpl <em>Trigger Condition</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.umn.cs.crisys.safety.safety.impl.TriggerConditionImpl
     * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getTriggerCondition()
     * @generated
     */
    EClass TRIGGER_CONDITION = eINSTANCE.getTriggerCondition();

    /**
     * The meta object literal for the '<em><b>Expr List</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference TRIGGER_CONDITION__EXPR_LIST = eINSTANCE.getTriggerCondition_ExprList();

    /**
     * The meta object literal for the '{@link edu.umn.cs.crisys.safety.safety.impl.SafetyEqStatementImpl <em>Eq Statement</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.umn.cs.crisys.safety.safety.impl.SafetyEqStatementImpl
     * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getSafetyEqStatement()
     * @generated
     */
    EClass SAFETY_EQ_STATEMENT = eINSTANCE.getSafetyEqStatement();

    /**
     * The meta object literal for the '{@link edu.umn.cs.crisys.safety.safety.impl.SafetyContractLibraryImpl <em>Contract Library</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.umn.cs.crisys.safety.safety.impl.SafetyContractLibraryImpl
     * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getSafetyContractLibrary()
     * @generated
     */
    EClass SAFETY_CONTRACT_LIBRARY = eINSTANCE.getSafetyContractLibrary();

    /**
     * The meta object literal for the '<em><b>Contract</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference SAFETY_CONTRACT_LIBRARY__CONTRACT = eINSTANCE.getSafetyContractLibrary_Contract();

    /**
     * The meta object literal for the '{@link edu.umn.cs.crisys.safety.safety.impl.SafetyContractSubclauseImpl <em>Contract Subclause</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.umn.cs.crisys.safety.safety.impl.SafetyContractSubclauseImpl
     * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getSafetyContractSubclause()
     * @generated
     */
    EClass SAFETY_CONTRACT_SUBCLAUSE = eINSTANCE.getSafetyContractSubclause();

    /**
     * The meta object literal for the '<em><b>Contract</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference SAFETY_CONTRACT_SUBCLAUSE__CONTRACT = eINSTANCE.getSafetyContractSubclause_Contract();

    /**
     * The meta object literal for the '{@link edu.umn.cs.crisys.safety.safety.impl.SafetyContractImpl <em>Contract</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.umn.cs.crisys.safety.safety.impl.SafetyContractImpl
     * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getSafetyContract()
     * @generated
     */
    EClass SAFETY_CONTRACT = eINSTANCE.getSafetyContract();

    /**
     * The meta object literal for the '<em><b>Specs</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference SAFETY_CONTRACT__SPECS = eINSTANCE.getSafetyContract_Specs();

    /**
     * The meta object literal for the '{@link edu.umn.cs.crisys.safety.safety.impl.FaultStatementImpl <em>Fault Statement</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.umn.cs.crisys.safety.safety.impl.FaultStatementImpl
     * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getFaultStatement()
     * @generated
     */
    EClass FAULT_STATEMENT = eINSTANCE.getFaultStatement();

    /**
     * The meta object literal for the '<em><b>Str</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FAULT_STATEMENT__STR = eINSTANCE.getFaultStatement_Str();

    /**
     * The meta object literal for the '<em><b>Fault Def Name</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference FAULT_STATEMENT__FAULT_DEF_NAME = eINSTANCE.getFaultStatement_FaultDefName();

    /**
     * The meta object literal for the '<em><b>Fault Definitions</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference FAULT_STATEMENT__FAULT_DEFINITIONS = eINSTANCE.getFaultStatement_FaultDefinitions();

    /**
     * The meta object literal for the '{@link edu.umn.cs.crisys.safety.safety.impl.InputStatementImpl <em>Input Statement</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.umn.cs.crisys.safety.safety.impl.InputStatementImpl
     * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getInputStatement()
     * @generated
     */
    EClass INPUT_STATEMENT = eINSTANCE.getInputStatement();

    /**
     * The meta object literal for the '<em><b>In conn</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute INPUT_STATEMENT__IN_CONN = eINSTANCE.getInputStatement_In_conn();

    /**
     * The meta object literal for the '<em><b>Out conn</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute INPUT_STATEMENT__OUT_CONN = eINSTANCE.getInputStatement_Out_conn();

    /**
     * The meta object literal for the '{@link edu.umn.cs.crisys.safety.safety.impl.OutputStatementImpl <em>Output Statement</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.umn.cs.crisys.safety.safety.impl.OutputStatementImpl
     * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getOutputStatement()
     * @generated
     */
    EClass OUTPUT_STATEMENT = eINSTANCE.getOutputStatement();

    /**
     * The meta object literal for the '<em><b>Out conn</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute OUTPUT_STATEMENT__OUT_CONN = eINSTANCE.getOutputStatement_Out_conn();

    /**
     * The meta object literal for the '<em><b>Nom conn</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute OUTPUT_STATEMENT__NOM_CONN = eINSTANCE.getOutputStatement_Nom_conn();

    /**
     * The meta object literal for the '{@link edu.umn.cs.crisys.safety.safety.impl.DurationStatementImpl <em>Duration Statement</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.umn.cs.crisys.safety.safety.impl.DurationStatementImpl
     * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getDurationStatement()
     * @generated
     */
    EClass DURATION_STATEMENT = eINSTANCE.getDurationStatement();

    /**
     * The meta object literal for the '<em><b>Tc</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference DURATION_STATEMENT__TC = eINSTANCE.getDurationStatement_Tc();

    /**
     * The meta object literal for the '<em><b>Interv</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference DURATION_STATEMENT__INTERV = eINSTANCE.getDurationStatement_Interv();

    /**
     * The meta object literal for the '{@link edu.umn.cs.crisys.safety.safety.impl.TriggerStatementImpl <em>Trigger Statement</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.umn.cs.crisys.safety.safety.impl.TriggerStatementImpl
     * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getTriggerStatement()
     * @generated
     */
    EClass TRIGGER_STATEMENT = eINSTANCE.getTriggerStatement();

    /**
     * The meta object literal for the '<em><b>Cond</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference TRIGGER_STATEMENT__COND = eINSTANCE.getTriggerStatement_Cond();

    /**
     * The meta object literal for the '<em><b>Probability</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute TRIGGER_STATEMENT__PROBABILITY = eINSTANCE.getTriggerStatement_Probability();

    /**
     * The meta object literal for the '{@link edu.umn.cs.crisys.safety.safety.impl.PermanentConstraintImpl <em>Permanent Constraint</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.umn.cs.crisys.safety.safety.impl.PermanentConstraintImpl
     * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getPermanentConstraint()
     * @generated
     */
    EClass PERMANENT_CONSTRAINT = eINSTANCE.getPermanentConstraint();

    /**
     * The meta object literal for the '{@link edu.umn.cs.crisys.safety.safety.impl.TransientConstraintImpl <em>Transient Constraint</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.umn.cs.crisys.safety.safety.impl.TransientConstraintImpl
     * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getTransientConstraint()
     * @generated
     */
    EClass TRANSIENT_CONSTRAINT = eINSTANCE.getTransientConstraint();

    /**
     * The meta object literal for the '{@link edu.umn.cs.crisys.safety.safety.impl.MustConditionImpl <em>Must Condition</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.umn.cs.crisys.safety.safety.impl.MustConditionImpl
     * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getMustCondition()
     * @generated
     */
    EClass MUST_CONDITION = eINSTANCE.getMustCondition();

    /**
     * The meta object literal for the '{@link edu.umn.cs.crisys.safety.safety.impl.EnablerConditionImpl <em>Enabler Condition</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.umn.cs.crisys.safety.safety.impl.EnablerConditionImpl
     * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getEnablerCondition()
     * @generated
     */
    EClass ENABLER_CONDITION = eINSTANCE.getEnablerCondition();

    /**
     * The meta object literal for the '{@link edu.umn.cs.crisys.safety.safety.impl.EqValueImpl <em>Eq Value</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.umn.cs.crisys.safety.safety.impl.EqValueImpl
     * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getEqValue()
     * @generated
     */
    EClass EQ_VALUE = eINSTANCE.getEqValue();

    /**
     * The meta object literal for the '<em><b>Lhs</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference EQ_VALUE__LHS = eINSTANCE.getEqValue_Lhs();

    /**
     * The meta object literal for the '<em><b>Expr</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference EQ_VALUE__EXPR = eINSTANCE.getEqValue_Expr();

    /**
     * The meta object literal for the '{@link edu.umn.cs.crisys.safety.safety.impl.IntervalEqImpl <em>Interval Eq</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.umn.cs.crisys.safety.safety.impl.IntervalEqImpl
     * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getIntervalEq()
     * @generated
     */
    EClass INTERVAL_EQ = eINSTANCE.getIntervalEq();

    /**
     * The meta object literal for the '<em><b>Lhs int</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute INTERVAL_EQ__LHS_INT = eINSTANCE.getIntervalEq_Lhs_int();

    /**
     * The meta object literal for the '<em><b>Interv</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference INTERVAL_EQ__INTERV = eINSTANCE.getIntervalEq_Interv();

    /**
     * The meta object literal for the '{@link edu.umn.cs.crisys.safety.safety.impl.SetEqImpl <em>Set Eq</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.umn.cs.crisys.safety.safety.impl.SetEqImpl
     * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getSetEq()
     * @generated
     */
    EClass SET_EQ = eINSTANCE.getSetEq();

    /**
     * The meta object literal for the '<em><b>Lhs set</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute SET_EQ__LHS_SET = eINSTANCE.getSetEq_Lhs_set();

    /**
     * The meta object literal for the '<em><b>L1</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute SET_EQ__L1 = eINSTANCE.getSetEq_L1();

    /**
     * The meta object literal for the '<em><b>List</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute SET_EQ__LIST = eINSTANCE.getSetEq_List();

  }

} //SafetyPackage
