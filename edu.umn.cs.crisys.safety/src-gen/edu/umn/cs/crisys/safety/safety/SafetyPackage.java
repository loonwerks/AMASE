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
   * The meta object id for the '{@link edu.umn.cs.crisys.safety.safety.impl.TriggerConditionImpl <em>Trigger Condition</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.umn.cs.crisys.safety.safety.impl.TriggerConditionImpl
   * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getTriggerCondition()
   * @generated
   */
  int TRIGGER_CONDITION = 5;

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
   * The meta object id for the '{@link edu.umn.cs.crisys.safety.safety.impl.EqStatementImpl <em>Eq Statement</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.umn.cs.crisys.safety.safety.impl.EqStatementImpl
   * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getEqStatement()
   * @generated
   */
  int EQ_STATEMENT = 6;

  /**
   * The feature id for the '<em><b>Owned Element</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EQ_STATEMENT__OWNED_ELEMENT = FAULT_SUBCOMPONENT_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Owned Comment</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EQ_STATEMENT__OWNED_COMMENT = FAULT_SUBCOMPONENT_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Lhs</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EQ_STATEMENT__LHS = FAULT_SUBCOMPONENT_FEATURE_COUNT + 2;

  /**
   * The feature id for the '<em><b>Expr</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EQ_STATEMENT__EXPR = FAULT_SUBCOMPONENT_FEATURE_COUNT + 3;

  /**
   * The feature id for the '<em><b>Interv</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EQ_STATEMENT__INTERV = FAULT_SUBCOMPONENT_FEATURE_COUNT + 4;

  /**
   * The feature id for the '<em><b>Expr List</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EQ_STATEMENT__EXPR_LIST = FAULT_SUBCOMPONENT_FEATURE_COUNT + 5;

  /**
   * The number of structural features of the '<em>Eq Statement</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EQ_STATEMENT_FEATURE_COUNT = FAULT_SUBCOMPONENT_FEATURE_COUNT + 6;

  /**
   * The meta object id for the '{@link edu.umn.cs.crisys.safety.safety.impl.SafetyContractLibraryImpl <em>Contract Library</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.umn.cs.crisys.safety.safety.impl.SafetyContractLibraryImpl
   * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getSafetyContractLibrary()
   * @generated
   */
  int SAFETY_CONTRACT_LIBRARY = 7;

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
  int SAFETY_CONTRACT_SUBCLAUSE = 8;

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
  int SAFETY_CONTRACT = 9;

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
  int FAULT_STATEMENT = 10;

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
   * The feature id for the '<em><b>Fault Def Name</b></em>' attribute.
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
  int INPUT_STATEMENT = 11;

  /**
   * The feature id for the '<em><b>In conn</b></em>' reference.
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
   * The feature id for the '<em><b>Expr</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INPUT_STATEMENT__EXPR = FAULT_SUBCOMPONENT_FEATURE_COUNT + 2;

  /**
   * The number of structural features of the '<em>Input Statement</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INPUT_STATEMENT_FEATURE_COUNT = FAULT_SUBCOMPONENT_FEATURE_COUNT + 3;

  /**
   * The meta object id for the '{@link edu.umn.cs.crisys.safety.safety.impl.OutputStatementImpl <em>Output Statement</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.umn.cs.crisys.safety.safety.impl.OutputStatementImpl
   * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getOutputStatement()
   * @generated
   */
  int OUTPUT_STATEMENT = 12;

  /**
   * The feature id for the '<em><b>Out conn</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OUTPUT_STATEMENT__OUT_CONN = FAULT_SUBCOMPONENT_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Nom conn</b></em>' reference.
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
  int DURATION_STATEMENT = 13;

  /**
   * The feature id for the '<em><b>Tc</b></em>' attribute.
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
  int TRIGGER_STATEMENT = 14;

  /**
   * The feature id for the '<em><b>Cond</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRIGGER_STATEMENT__COND = FAULT_SUBCOMPONENT_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Probability</b></em>' containment reference.
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
   * Returns the meta object for class '{@link edu.umn.cs.crisys.safety.safety.EqStatement <em>Eq Statement</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Eq Statement</em>'.
   * @see edu.umn.cs.crisys.safety.safety.EqStatement
   * @generated
   */
  EClass getEqStatement();

  /**
   * Returns the meta object for the containment reference '{@link edu.umn.cs.crisys.safety.safety.EqStatement#getInterv <em>Interv</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Interv</em>'.
   * @see edu.umn.cs.crisys.safety.safety.EqStatement#getInterv()
   * @see #getEqStatement()
   * @generated
   */
  EReference getEqStatement_Interv();

  /**
   * Returns the meta object for the containment reference list '{@link edu.umn.cs.crisys.safety.safety.EqStatement#getExprList <em>Expr List</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Expr List</em>'.
   * @see edu.umn.cs.crisys.safety.safety.EqStatement#getExprList()
   * @see #getEqStatement()
   * @generated
   */
  EReference getEqStatement_ExprList();

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
   * Returns the meta object for the attribute '{@link edu.umn.cs.crisys.safety.safety.FaultStatement#getFaultDefName <em>Fault Def Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Fault Def Name</em>'.
   * @see edu.umn.cs.crisys.safety.safety.FaultStatement#getFaultDefName()
   * @see #getFaultStatement()
   * @generated
   */
  EAttribute getFaultStatement_FaultDefName();

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
   * Returns the meta object for the reference '{@link edu.umn.cs.crisys.safety.safety.InputStatement#getIn_conn <em>In conn</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>In conn</em>'.
   * @see edu.umn.cs.crisys.safety.safety.InputStatement#getIn_conn()
   * @see #getInputStatement()
   * @generated
   */
  EReference getInputStatement_In_conn();

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
   * Returns the meta object for the containment reference '{@link edu.umn.cs.crisys.safety.safety.InputStatement#getExpr <em>Expr</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Expr</em>'.
   * @see edu.umn.cs.crisys.safety.safety.InputStatement#getExpr()
   * @see #getInputStatement()
   * @generated
   */
  EReference getInputStatement_Expr();

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
   * Returns the meta object for the reference '{@link edu.umn.cs.crisys.safety.safety.OutputStatement#getNom_conn <em>Nom conn</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Nom conn</em>'.
   * @see edu.umn.cs.crisys.safety.safety.OutputStatement#getNom_conn()
   * @see #getOutputStatement()
   * @generated
   */
  EReference getOutputStatement_Nom_conn();

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
   * Returns the meta object for the attribute '{@link edu.umn.cs.crisys.safety.safety.DurationStatement#getTc <em>Tc</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Tc</em>'.
   * @see edu.umn.cs.crisys.safety.safety.DurationStatement#getTc()
   * @see #getDurationStatement()
   * @generated
   */
  EAttribute getDurationStatement_Tc();

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
   * Returns the meta object for the containment reference '{@link edu.umn.cs.crisys.safety.safety.TriggerStatement#getProbability <em>Probability</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Probability</em>'.
   * @see edu.umn.cs.crisys.safety.safety.TriggerStatement#getProbability()
   * @see #getTriggerStatement()
   * @generated
   */
  EReference getTriggerStatement_Probability();

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
     * The meta object literal for the '{@link edu.umn.cs.crisys.safety.safety.impl.EqStatementImpl <em>Eq Statement</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.umn.cs.crisys.safety.safety.impl.EqStatementImpl
     * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getEqStatement()
     * @generated
     */
    EClass EQ_STATEMENT = eINSTANCE.getEqStatement();

    /**
     * The meta object literal for the '<em><b>Interv</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference EQ_STATEMENT__INTERV = eINSTANCE.getEqStatement_Interv();

    /**
     * The meta object literal for the '<em><b>Expr List</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference EQ_STATEMENT__EXPR_LIST = eINSTANCE.getEqStatement_ExprList();

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
     * The meta object literal for the '<em><b>Fault Def Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FAULT_STATEMENT__FAULT_DEF_NAME = eINSTANCE.getFaultStatement_FaultDefName();

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
     * The meta object literal for the '<em><b>In conn</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference INPUT_STATEMENT__IN_CONN = eINSTANCE.getInputStatement_In_conn();

    /**
     * The meta object literal for the '<em><b>Out conn</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute INPUT_STATEMENT__OUT_CONN = eINSTANCE.getInputStatement_Out_conn();

    /**
     * The meta object literal for the '<em><b>Expr</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference INPUT_STATEMENT__EXPR = eINSTANCE.getInputStatement_Expr();

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
     * The meta object literal for the '<em><b>Nom conn</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference OUTPUT_STATEMENT__NOM_CONN = eINSTANCE.getOutputStatement_Nom_conn();

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
     * The meta object literal for the '<em><b>Tc</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute DURATION_STATEMENT__TC = eINSTANCE.getDurationStatement_Tc();

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
     * The meta object literal for the '<em><b>Probability</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference TRIGGER_STATEMENT__PROBABILITY = eINSTANCE.getTriggerStatement_Probability();

  }

} //SafetyPackage
