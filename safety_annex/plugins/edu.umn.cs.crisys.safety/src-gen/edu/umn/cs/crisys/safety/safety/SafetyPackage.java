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
   * The meta object id for the '{@link edu.umn.cs.crisys.safety.safety.impl.AnnexLibraryImpl <em>Annex Library</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.umn.cs.crisys.safety.safety.impl.AnnexLibraryImpl
   * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getAnnexLibrary()
   * @generated
   */
  int ANNEX_LIBRARY = 0;

  /**
   * The feature id for the '<em><b>Owned Element</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ANNEX_LIBRARY__OWNED_ELEMENT = Aadl2Package.ANNEX_LIBRARY__OWNED_ELEMENT;

  /**
   * The feature id for the '<em><b>Owned Comment</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ANNEX_LIBRARY__OWNED_COMMENT = Aadl2Package.ANNEX_LIBRARY__OWNED_COMMENT;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ANNEX_LIBRARY__NAME = Aadl2Package.ANNEX_LIBRARY__NAME;

  /**
   * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ANNEX_LIBRARY__QUALIFIED_NAME = Aadl2Package.ANNEX_LIBRARY__QUALIFIED_NAME;

  /**
   * The feature id for the '<em><b>Owned Property Association</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ANNEX_LIBRARY__OWNED_PROPERTY_ASSOCIATION = Aadl2Package.ANNEX_LIBRARY__OWNED_PROPERTY_ASSOCIATION;

  /**
   * The number of structural features of the '<em>Annex Library</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ANNEX_LIBRARY_FEATURE_COUNT = Aadl2Package.ANNEX_LIBRARY_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link edu.umn.cs.crisys.safety.safety.impl.AnnexSubclauseImpl <em>Annex Subclause</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.umn.cs.crisys.safety.safety.impl.AnnexSubclauseImpl
   * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getAnnexSubclause()
   * @generated
   */
  int ANNEX_SUBCLAUSE = 1;

  /**
   * The feature id for the '<em><b>Owned Element</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ANNEX_SUBCLAUSE__OWNED_ELEMENT = Aadl2Package.ANNEX_SUBCLAUSE__OWNED_ELEMENT;

  /**
   * The feature id for the '<em><b>Owned Comment</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ANNEX_SUBCLAUSE__OWNED_COMMENT = Aadl2Package.ANNEX_SUBCLAUSE__OWNED_COMMENT;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ANNEX_SUBCLAUSE__NAME = Aadl2Package.ANNEX_SUBCLAUSE__NAME;

  /**
   * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ANNEX_SUBCLAUSE__QUALIFIED_NAME = Aadl2Package.ANNEX_SUBCLAUSE__QUALIFIED_NAME;

  /**
   * The feature id for the '<em><b>Owned Property Association</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ANNEX_SUBCLAUSE__OWNED_PROPERTY_ASSOCIATION = Aadl2Package.ANNEX_SUBCLAUSE__OWNED_PROPERTY_ASSOCIATION;

  /**
   * The feature id for the '<em><b>In Mode</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ANNEX_SUBCLAUSE__IN_MODE = Aadl2Package.ANNEX_SUBCLAUSE__IN_MODE;

  /**
   * The number of structural features of the '<em>Annex Subclause</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ANNEX_SUBCLAUSE_FEATURE_COUNT = Aadl2Package.ANNEX_SUBCLAUSE_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link edu.umn.cs.crisys.safety.safety.impl.SafetyLibraryImpl <em>Library</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.umn.cs.crisys.safety.safety.impl.SafetyLibraryImpl
   * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getSafetyLibrary()
   * @generated
   */
  int SAFETY_LIBRARY = 2;

  /**
   * The feature id for the '<em><b>Owned Element</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SAFETY_LIBRARY__OWNED_ELEMENT = ANNEX_LIBRARY__OWNED_ELEMENT;

  /**
   * The feature id for the '<em><b>Owned Comment</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SAFETY_LIBRARY__OWNED_COMMENT = ANNEX_LIBRARY__OWNED_COMMENT;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SAFETY_LIBRARY__NAME = ANNEX_LIBRARY__NAME;

  /**
   * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SAFETY_LIBRARY__QUALIFIED_NAME = ANNEX_LIBRARY__QUALIFIED_NAME;

  /**
   * The feature id for the '<em><b>Owned Property Association</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SAFETY_LIBRARY__OWNED_PROPERTY_ASSOCIATION = ANNEX_LIBRARY__OWNED_PROPERTY_ASSOCIATION;

  /**
   * The number of structural features of the '<em>Library</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SAFETY_LIBRARY_FEATURE_COUNT = ANNEX_LIBRARY_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link edu.umn.cs.crisys.safety.safety.impl.SafetySubclauseImpl <em>Subclause</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.umn.cs.crisys.safety.safety.impl.SafetySubclauseImpl
   * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getSafetySubclause()
   * @generated
   */
  int SAFETY_SUBCLAUSE = 3;

  /**
   * The feature id for the '<em><b>Owned Element</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SAFETY_SUBCLAUSE__OWNED_ELEMENT = ANNEX_SUBCLAUSE__OWNED_ELEMENT;

  /**
   * The feature id for the '<em><b>Owned Comment</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SAFETY_SUBCLAUSE__OWNED_COMMENT = ANNEX_SUBCLAUSE__OWNED_COMMENT;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SAFETY_SUBCLAUSE__NAME = ANNEX_SUBCLAUSE__NAME;

  /**
   * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SAFETY_SUBCLAUSE__QUALIFIED_NAME = ANNEX_SUBCLAUSE__QUALIFIED_NAME;

  /**
   * The feature id for the '<em><b>Owned Property Association</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SAFETY_SUBCLAUSE__OWNED_PROPERTY_ASSOCIATION = ANNEX_SUBCLAUSE__OWNED_PROPERTY_ASSOCIATION;

  /**
   * The feature id for the '<em><b>In Mode</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SAFETY_SUBCLAUSE__IN_MODE = ANNEX_SUBCLAUSE__IN_MODE;

  /**
   * The number of structural features of the '<em>Subclause</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SAFETY_SUBCLAUSE_FEATURE_COUNT = ANNEX_SUBCLAUSE_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link edu.umn.cs.crisys.safety.safety.impl.ContractImpl <em>Contract</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.umn.cs.crisys.safety.safety.impl.ContractImpl
   * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getContract()
   * @generated
   */
  int CONTRACT = 4;

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
  int SPEC_STATEMENT = 5;

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
   * The meta object id for the '{@link edu.umn.cs.crisys.safety.safety.impl.AnalysisBehaviorImpl <em>Analysis Behavior</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.umn.cs.crisys.safety.safety.impl.AnalysisBehaviorImpl
   * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getAnalysisBehavior()
   * @generated
   */
  int ANALYSIS_BEHAVIOR = 6;

  /**
   * The number of structural features of the '<em>Analysis Behavior</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ANALYSIS_BEHAVIOR_FEATURE_COUNT = 0;

  /**
   * The meta object id for the '{@link edu.umn.cs.crisys.safety.safety.impl.FaultSubcomponentImpl <em>Fault Subcomponent</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.umn.cs.crisys.safety.safety.impl.FaultSubcomponentImpl
   * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getFaultSubcomponent()
   * @generated
   */
  int FAULT_SUBCOMPONENT = 7;

  /**
   * The feature id for the '<em><b>Owned Element</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FAULT_SUBCOMPONENT__OWNED_ELEMENT = Aadl2Package.ELEMENT__OWNED_ELEMENT;

  /**
   * The feature id for the '<em><b>Owned Comment</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FAULT_SUBCOMPONENT__OWNED_COMMENT = Aadl2Package.ELEMENT__OWNED_COMMENT;

  /**
   * The number of structural features of the '<em>Fault Subcomponent</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FAULT_SUBCOMPONENT_FEATURE_COUNT = Aadl2Package.ELEMENT_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link edu.umn.cs.crisys.safety.safety.impl.HWFaultSubcomponentImpl <em>HW Fault Subcomponent</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.umn.cs.crisys.safety.safety.impl.HWFaultSubcomponentImpl
   * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getHWFaultSubcomponent()
   * @generated
   */
  int HW_FAULT_SUBCOMPONENT = 8;

  /**
   * The number of structural features of the '<em>HW Fault Subcomponent</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int HW_FAULT_SUBCOMPONENT_FEATURE_COUNT = 0;

  /**
   * The meta object id for the '{@link edu.umn.cs.crisys.safety.safety.impl.PropagationTypeConstraintImpl <em>Propagation Type Constraint</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.umn.cs.crisys.safety.safety.impl.PropagationTypeConstraintImpl
   * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getPropagationTypeConstraint()
   * @generated
   */
  int PROPAGATION_TYPE_CONSTRAINT = 9;

  /**
   * The number of structural features of the '<em>Propagation Type Constraint</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPAGATION_TYPE_CONSTRAINT_FEATURE_COUNT = 0;

  /**
   * The meta object id for the '{@link edu.umn.cs.crisys.safety.safety.impl.TemporalConstraintImpl <em>Temporal Constraint</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.umn.cs.crisys.safety.safety.impl.TemporalConstraintImpl
   * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getTemporalConstraint()
   * @generated
   */
  int TEMPORAL_CONSTRAINT = 10;

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
  int TRIGGER_CONDITION = 11;

  /**
   * The number of structural features of the '<em>Trigger Condition</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRIGGER_CONDITION_FEATURE_COUNT = 0;

  /**
   * The meta object id for the '{@link edu.umn.cs.crisys.safety.safety.impl.SafetyEqStatementImpl <em>Eq Statement</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.umn.cs.crisys.safety.safety.impl.SafetyEqStatementImpl
   * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getSafetyEqStatement()
   * @generated
   */
  int SAFETY_EQ_STATEMENT = 12;

  /**
   * The feature id for the '<em><b>Owned Element</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SAFETY_EQ_STATEMENT__OWNED_ELEMENT = Aadl2Package.ELEMENT__OWNED_ELEMENT;

  /**
   * The feature id for the '<em><b>Owned Comment</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SAFETY_EQ_STATEMENT__OWNED_COMMENT = Aadl2Package.ELEMENT__OWNED_COMMENT;

  /**
   * The number of structural features of the '<em>Eq Statement</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SAFETY_EQ_STATEMENT_FEATURE_COUNT = Aadl2Package.ELEMENT_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link edu.umn.cs.crisys.safety.safety.impl.IntervalImpl <em>Interval</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.umn.cs.crisys.safety.safety.impl.IntervalImpl
   * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getInterval()
   * @generated
   */
  int INTERVAL = 13;

  /**
   * The feature id for the '<em><b>Low</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTERVAL__LOW = 0;

  /**
   * The feature id for the '<em><b>High</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTERVAL__HIGH = 1;

  /**
   * The number of structural features of the '<em>Interval</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTERVAL_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link edu.umn.cs.crisys.safety.safety.impl.SafetyContractLibraryImpl <em>Contract Library</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.umn.cs.crisys.safety.safety.impl.SafetyContractLibraryImpl
   * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getSafetyContractLibrary()
   * @generated
   */
  int SAFETY_CONTRACT_LIBRARY = 14;

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
  int SAFETY_CONTRACT_SUBCLAUSE = 15;

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
  int SAFETY_CONTRACT = 16;

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
  int FAULT_STATEMENT = 17;

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
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FAULT_STATEMENT__NAME = SPEC_STATEMENT_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Str</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FAULT_STATEMENT__STR = SPEC_STATEMENT_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Fault Def Name</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FAULT_STATEMENT__FAULT_DEF_NAME = SPEC_STATEMENT_FEATURE_COUNT + 2;

  /**
   * The feature id for the '<em><b>Fault Definitions</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FAULT_STATEMENT__FAULT_DEFINITIONS = SPEC_STATEMENT_FEATURE_COUNT + 3;

  /**
   * The number of structural features of the '<em>Fault Statement</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FAULT_STATEMENT_FEATURE_COUNT = SPEC_STATEMENT_FEATURE_COUNT + 4;

  /**
   * The meta object id for the '{@link edu.umn.cs.crisys.safety.safety.impl.AnalysisStatementImpl <em>Analysis Statement</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.umn.cs.crisys.safety.safety.impl.AnalysisStatementImpl
   * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getAnalysisStatement()
   * @generated
   */
  int ANALYSIS_STATEMENT = 18;

  /**
   * The feature id for the '<em><b>Owned Element</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ANALYSIS_STATEMENT__OWNED_ELEMENT = SPEC_STATEMENT__OWNED_ELEMENT;

  /**
   * The feature id for the '<em><b>Owned Comment</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ANALYSIS_STATEMENT__OWNED_COMMENT = SPEC_STATEMENT__OWNED_COMMENT;

  /**
   * The feature id for the '<em><b>Behavior</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ANALYSIS_STATEMENT__BEHAVIOR = SPEC_STATEMENT_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Analysis Statement</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ANALYSIS_STATEMENT_FEATURE_COUNT = SPEC_STATEMENT_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link edu.umn.cs.crisys.safety.safety.impl.HWFaultStatementImpl <em>HW Fault Statement</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.umn.cs.crisys.safety.safety.impl.HWFaultStatementImpl
   * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getHWFaultStatement()
   * @generated
   */
  int HW_FAULT_STATEMENT = 19;

  /**
   * The feature id for the '<em><b>Owned Element</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int HW_FAULT_STATEMENT__OWNED_ELEMENT = SPEC_STATEMENT__OWNED_ELEMENT;

  /**
   * The feature id for the '<em><b>Owned Comment</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int HW_FAULT_STATEMENT__OWNED_COMMENT = SPEC_STATEMENT__OWNED_COMMENT;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int HW_FAULT_STATEMENT__NAME = SPEC_STATEMENT_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Str</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int HW_FAULT_STATEMENT__STR = SPEC_STATEMENT_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Fault Definitions</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int HW_FAULT_STATEMENT__FAULT_DEFINITIONS = SPEC_STATEMENT_FEATURE_COUNT + 2;

  /**
   * The number of structural features of the '<em>HW Fault Statement</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int HW_FAULT_STATEMENT_FEATURE_COUNT = SPEC_STATEMENT_FEATURE_COUNT + 3;

  /**
   * The meta object id for the '{@link edu.umn.cs.crisys.safety.safety.impl.PropagateStatementImpl <em>Propagate Statement</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.umn.cs.crisys.safety.safety.impl.PropagateStatementImpl
   * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getPropagateStatement()
   * @generated
   */
  int PROPAGATE_STATEMENT = 20;

  /**
   * The feature id for the '<em><b>Owned Element</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPAGATE_STATEMENT__OWNED_ELEMENT = SPEC_STATEMENT__OWNED_ELEMENT;

  /**
   * The feature id for the '<em><b>Owned Comment</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPAGATE_STATEMENT__OWNED_COMMENT = SPEC_STATEMENT__OWNED_COMMENT;

  /**
   * The feature id for the '<em><b>Src Fault List</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPAGATE_STATEMENT__SRC_FAULT_LIST = SPEC_STATEMENT_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Src Comp path</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPAGATE_STATEMENT__SRC_COMP_PATH = SPEC_STATEMENT_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Dest Fault List</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPAGATE_STATEMENT__DEST_FAULT_LIST = SPEC_STATEMENT_FEATURE_COUNT + 2;

  /**
   * The feature id for the '<em><b>Dest Comp path</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPAGATE_STATEMENT__DEST_COMP_PATH = SPEC_STATEMENT_FEATURE_COUNT + 3;

  /**
   * The number of structural features of the '<em>Propagate Statement</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPAGATE_STATEMENT_FEATURE_COUNT = SPEC_STATEMENT_FEATURE_COUNT + 4;

  /**
   * The meta object id for the '{@link edu.umn.cs.crisys.safety.safety.impl.ActivationStatementImpl <em>Activation Statement</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.umn.cs.crisys.safety.safety.impl.ActivationStatementImpl
   * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getActivationStatement()
   * @generated
   */
  int ACTIVATION_STATEMENT = 21;

  /**
   * The feature id for the '<em><b>Owned Element</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ACTIVATION_STATEMENT__OWNED_ELEMENT = SPEC_STATEMENT__OWNED_ELEMENT;

  /**
   * The feature id for the '<em><b>Owned Comment</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ACTIVATION_STATEMENT__OWNED_COMMENT = SPEC_STATEMENT__OWNED_COMMENT;

  /**
   * The feature id for the '<em><b>Agree Bool Var Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ACTIVATION_STATEMENT__AGREE_BOOL_VAR_NAME = SPEC_STATEMENT_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Agree Comp Path</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ACTIVATION_STATEMENT__AGREE_COMP_PATH = SPEC_STATEMENT_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Fault Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ACTIVATION_STATEMENT__FAULT_NAME = SPEC_STATEMENT_FEATURE_COUNT + 2;

  /**
   * The feature id for the '<em><b>Fault Comp Path</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ACTIVATION_STATEMENT__FAULT_COMP_PATH = SPEC_STATEMENT_FEATURE_COUNT + 3;

  /**
   * The number of structural features of the '<em>Activation Statement</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ACTIVATION_STATEMENT_FEATURE_COUNT = SPEC_STATEMENT_FEATURE_COUNT + 4;

  /**
   * The meta object id for the '{@link edu.umn.cs.crisys.safety.safety.impl.FaultCountBehaviorImpl <em>Fault Count Behavior</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.umn.cs.crisys.safety.safety.impl.FaultCountBehaviorImpl
   * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getFaultCountBehavior()
   * @generated
   */
  int FAULT_COUNT_BEHAVIOR = 22;

  /**
   * The feature id for the '<em><b>Max Faults</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FAULT_COUNT_BEHAVIOR__MAX_FAULTS = ANALYSIS_BEHAVIOR_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Fault Count Behavior</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FAULT_COUNT_BEHAVIOR_FEATURE_COUNT = ANALYSIS_BEHAVIOR_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link edu.umn.cs.crisys.safety.safety.impl.ProbabilityBehaviorImpl <em>Probability Behavior</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.umn.cs.crisys.safety.safety.impl.ProbabilityBehaviorImpl
   * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getProbabilityBehavior()
   * @generated
   */
  int PROBABILITY_BEHAVIOR = 23;

  /**
   * The feature id for the '<em><b>Probabilty</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROBABILITY_BEHAVIOR__PROBABILTY = ANALYSIS_BEHAVIOR_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Probability Behavior</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROBABILITY_BEHAVIOR_FEATURE_COUNT = ANALYSIS_BEHAVIOR_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link edu.umn.cs.crisys.safety.safety.impl.InputStatementImpl <em>Input Statement</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.umn.cs.crisys.safety.safety.impl.InputStatementImpl
   * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getInputStatement()
   * @generated
   */
  int INPUT_STATEMENT = 24;

  /**
   * The feature id for the '<em><b>Owned Element</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INPUT_STATEMENT__OWNED_ELEMENT = FAULT_SUBCOMPONENT__OWNED_ELEMENT;

  /**
   * The feature id for the '<em><b>Owned Comment</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INPUT_STATEMENT__OWNED_COMMENT = FAULT_SUBCOMPONENT__OWNED_COMMENT;

  /**
   * The feature id for the '<em><b>Fault in</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INPUT_STATEMENT__FAULT_IN = FAULT_SUBCOMPONENT_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Nom conn</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INPUT_STATEMENT__NOM_CONN = FAULT_SUBCOMPONENT_FEATURE_COUNT + 1;

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
  int OUTPUT_STATEMENT = 25;

  /**
   * The feature id for the '<em><b>Owned Element</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OUTPUT_STATEMENT__OWNED_ELEMENT = FAULT_SUBCOMPONENT__OWNED_ELEMENT;

  /**
   * The feature id for the '<em><b>Owned Comment</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OUTPUT_STATEMENT__OWNED_COMMENT = FAULT_SUBCOMPONENT__OWNED_COMMENT;

  /**
   * The feature id for the '<em><b>Nom conn</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OUTPUT_STATEMENT__NOM_CONN = FAULT_SUBCOMPONENT_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Fault out</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OUTPUT_STATEMENT__FAULT_OUT = FAULT_SUBCOMPONENT_FEATURE_COUNT + 1;

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
  int DURATION_STATEMENT = 26;

  /**
   * The feature id for the '<em><b>Owned Element</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DURATION_STATEMENT__OWNED_ELEMENT = FAULT_SUBCOMPONENT__OWNED_ELEMENT;

  /**
   * The feature id for the '<em><b>Owned Comment</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DURATION_STATEMENT__OWNED_COMMENT = FAULT_SUBCOMPONENT__OWNED_COMMENT;

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
   * The meta object id for the '{@link edu.umn.cs.crisys.safety.safety.impl.ProbabilityStatementImpl <em>Probability Statement</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.umn.cs.crisys.safety.safety.impl.ProbabilityStatementImpl
   * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getProbabilityStatement()
   * @generated
   */
  int PROBABILITY_STATEMENT = 27;

  /**
   * The feature id for the '<em><b>Owned Element</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROBABILITY_STATEMENT__OWNED_ELEMENT = FAULT_SUBCOMPONENT__OWNED_ELEMENT;

  /**
   * The feature id for the '<em><b>Owned Comment</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROBABILITY_STATEMENT__OWNED_COMMENT = FAULT_SUBCOMPONENT__OWNED_COMMENT;

  /**
   * The feature id for the '<em><b>Probability</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROBABILITY_STATEMENT__PROBABILITY = FAULT_SUBCOMPONENT_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Probability Statement</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROBABILITY_STATEMENT_FEATURE_COUNT = FAULT_SUBCOMPONENT_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link edu.umn.cs.crisys.safety.safety.impl.TriggerStatementImpl <em>Trigger Statement</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.umn.cs.crisys.safety.safety.impl.TriggerStatementImpl
   * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getTriggerStatement()
   * @generated
   */
  int TRIGGER_STATEMENT = 28;

  /**
   * The feature id for the '<em><b>Owned Element</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRIGGER_STATEMENT__OWNED_ELEMENT = FAULT_SUBCOMPONENT__OWNED_ELEMENT;

  /**
   * The feature id for the '<em><b>Owned Comment</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRIGGER_STATEMENT__OWNED_COMMENT = FAULT_SUBCOMPONENT__OWNED_COMMENT;

  /**
   * The feature id for the '<em><b>Cond</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRIGGER_STATEMENT__COND = FAULT_SUBCOMPONENT_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Trigger Statement</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRIGGER_STATEMENT_FEATURE_COUNT = FAULT_SUBCOMPONENT_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link edu.umn.cs.crisys.safety.safety.impl.PropagationTypeStatementImpl <em>Propagation Type Statement</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.umn.cs.crisys.safety.safety.impl.PropagationTypeStatementImpl
   * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getPropagationTypeStatement()
   * @generated
   */
  int PROPAGATION_TYPE_STATEMENT = 29;

  /**
   * The feature id for the '<em><b>Owned Element</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPAGATION_TYPE_STATEMENT__OWNED_ELEMENT = FAULT_SUBCOMPONENT__OWNED_ELEMENT;

  /**
   * The feature id for the '<em><b>Owned Comment</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPAGATION_TYPE_STATEMENT__OWNED_COMMENT = FAULT_SUBCOMPONENT__OWNED_COMMENT;

  /**
   * The feature id for the '<em><b>Pty</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPAGATION_TYPE_STATEMENT__PTY = FAULT_SUBCOMPONENT_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Propagation Type Statement</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPAGATION_TYPE_STATEMENT_FEATURE_COUNT = FAULT_SUBCOMPONENT_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link edu.umn.cs.crisys.safety.safety.impl.asymmetricImpl <em>asymmetric</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.umn.cs.crisys.safety.safety.impl.asymmetricImpl
   * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getasymmetric()
   * @generated
   */
  int ASYMMETRIC = 30;

  /**
   * The number of structural features of the '<em>asymmetric</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ASYMMETRIC_FEATURE_COUNT = PROPAGATION_TYPE_CONSTRAINT_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link edu.umn.cs.crisys.safety.safety.impl.symmetricImpl <em>symmetric</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.umn.cs.crisys.safety.safety.impl.symmetricImpl
   * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getsymmetric()
   * @generated
   */
  int SYMMETRIC = 31;

  /**
   * The number of structural features of the '<em>symmetric</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SYMMETRIC_FEATURE_COUNT = PROPAGATION_TYPE_CONSTRAINT_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link edu.umn.cs.crisys.safety.safety.impl.PermanentConstraintImpl <em>Permanent Constraint</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.umn.cs.crisys.safety.safety.impl.PermanentConstraintImpl
   * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getPermanentConstraint()
   * @generated
   */
  int PERMANENT_CONSTRAINT = 32;

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
  int TRANSIENT_CONSTRAINT = 33;

  /**
   * The number of structural features of the '<em>Transient Constraint</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRANSIENT_CONSTRAINT_FEATURE_COUNT = TEMPORAL_CONSTRAINT_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link edu.umn.cs.crisys.safety.safety.impl.EnablerConditionImpl <em>Enabler Condition</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.umn.cs.crisys.safety.safety.impl.EnablerConditionImpl
   * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getEnablerCondition()
   * @generated
   */
  int ENABLER_CONDITION = 34;

  /**
   * The feature id for the '<em><b>Expr List</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ENABLER_CONDITION__EXPR_LIST = TRIGGER_CONDITION_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Enabler Condition</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ENABLER_CONDITION_FEATURE_COUNT = TRIGGER_CONDITION_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link edu.umn.cs.crisys.safety.safety.impl.EqValueImpl <em>Eq Value</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.umn.cs.crisys.safety.safety.impl.EqValueImpl
   * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getEqValue()
   * @generated
   */
  int EQ_VALUE = 35;

  /**
   * The feature id for the '<em><b>Owned Element</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EQ_VALUE__OWNED_ELEMENT = SAFETY_EQ_STATEMENT__OWNED_ELEMENT;

  /**
   * The feature id for the '<em><b>Owned Comment</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EQ_VALUE__OWNED_COMMENT = SAFETY_EQ_STATEMENT__OWNED_COMMENT;

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
  int INTERVAL_EQ = 36;

  /**
   * The feature id for the '<em><b>Owned Element</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTERVAL_EQ__OWNED_ELEMENT = SAFETY_EQ_STATEMENT__OWNED_ELEMENT;

  /**
   * The feature id for the '<em><b>Owned Comment</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTERVAL_EQ__OWNED_COMMENT = SAFETY_EQ_STATEMENT__OWNED_COMMENT;

  /**
   * The feature id for the '<em><b>Lhs int</b></em>' containment reference.
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
  int SET_EQ = 37;

  /**
   * The feature id for the '<em><b>Owned Element</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SET_EQ__OWNED_ELEMENT = SAFETY_EQ_STATEMENT__OWNED_ELEMENT;

  /**
   * The feature id for the '<em><b>Owned Comment</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SET_EQ__OWNED_COMMENT = SAFETY_EQ_STATEMENT__OWNED_COMMENT;

  /**
   * The feature id for the '<em><b>Lhs set</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SET_EQ__LHS_SET = SAFETY_EQ_STATEMENT_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>L1</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SET_EQ__L1 = SAFETY_EQ_STATEMENT_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>List</b></em>' containment reference list.
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
   * The meta object id for the '{@link edu.umn.cs.crisys.safety.safety.impl.RangeEqImpl <em>Range Eq</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.umn.cs.crisys.safety.safety.impl.RangeEqImpl
   * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getRangeEq()
   * @generated
   */
  int RANGE_EQ = 38;

  /**
   * The feature id for the '<em><b>Owned Element</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RANGE_EQ__OWNED_ELEMENT = SAFETY_EQ_STATEMENT__OWNED_ELEMENT;

  /**
   * The feature id for the '<em><b>Owned Comment</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RANGE_EQ__OWNED_COMMENT = SAFETY_EQ_STATEMENT__OWNED_COMMENT;

  /**
   * The feature id for the '<em><b>Lhs range</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RANGE_EQ__LHS_RANGE = SAFETY_EQ_STATEMENT_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>L1</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RANGE_EQ__L1 = SAFETY_EQ_STATEMENT_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>L2</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RANGE_EQ__L2 = SAFETY_EQ_STATEMENT_FEATURE_COUNT + 2;

  /**
   * The number of structural features of the '<em>Range Eq</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RANGE_EQ_FEATURE_COUNT = SAFETY_EQ_STATEMENT_FEATURE_COUNT + 3;

  /**
   * The meta object id for the '{@link edu.umn.cs.crisys.safety.safety.impl.ClosedIntervalImpl <em>Closed Interval</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.umn.cs.crisys.safety.safety.impl.ClosedIntervalImpl
   * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getClosedInterval()
   * @generated
   */
  int CLOSED_INTERVAL = 39;

  /**
   * The feature id for the '<em><b>Low</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CLOSED_INTERVAL__LOW = INTERVAL__LOW;

  /**
   * The feature id for the '<em><b>High</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CLOSED_INTERVAL__HIGH = INTERVAL__HIGH;

  /**
   * The number of structural features of the '<em>Closed Interval</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CLOSED_INTERVAL_FEATURE_COUNT = INTERVAL_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link edu.umn.cs.crisys.safety.safety.impl.OpenLeftIntervalImpl <em>Open Left Interval</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.umn.cs.crisys.safety.safety.impl.OpenLeftIntervalImpl
   * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getOpenLeftInterval()
   * @generated
   */
  int OPEN_LEFT_INTERVAL = 40;

  /**
   * The feature id for the '<em><b>Low</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OPEN_LEFT_INTERVAL__LOW = INTERVAL__LOW;

  /**
   * The feature id for the '<em><b>High</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OPEN_LEFT_INTERVAL__HIGH = INTERVAL__HIGH;

  /**
   * The number of structural features of the '<em>Open Left Interval</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OPEN_LEFT_INTERVAL_FEATURE_COUNT = INTERVAL_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link edu.umn.cs.crisys.safety.safety.impl.OpenRightIntervalImpl <em>Open Right Interval</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.umn.cs.crisys.safety.safety.impl.OpenRightIntervalImpl
   * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getOpenRightInterval()
   * @generated
   */
  int OPEN_RIGHT_INTERVAL = 41;

  /**
   * The feature id for the '<em><b>Low</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OPEN_RIGHT_INTERVAL__LOW = INTERVAL__LOW;

  /**
   * The feature id for the '<em><b>High</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OPEN_RIGHT_INTERVAL__HIGH = INTERVAL__HIGH;

  /**
   * The number of structural features of the '<em>Open Right Interval</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OPEN_RIGHT_INTERVAL_FEATURE_COUNT = INTERVAL_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link edu.umn.cs.crisys.safety.safety.impl.OpenIntervalImpl <em>Open Interval</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see edu.umn.cs.crisys.safety.safety.impl.OpenIntervalImpl
   * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getOpenInterval()
   * @generated
   */
  int OPEN_INTERVAL = 42;

  /**
   * The feature id for the '<em><b>Low</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OPEN_INTERVAL__LOW = INTERVAL__LOW;

  /**
   * The feature id for the '<em><b>High</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OPEN_INTERVAL__HIGH = INTERVAL__HIGH;

  /**
   * The number of structural features of the '<em>Open Interval</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OPEN_INTERVAL_FEATURE_COUNT = INTERVAL_FEATURE_COUNT + 0;


  /**
   * Returns the meta object for class '{@link edu.umn.cs.crisys.safety.safety.AnnexLibrary <em>Annex Library</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Annex Library</em>'.
   * @see edu.umn.cs.crisys.safety.safety.AnnexLibrary
   * @generated
   */
  EClass getAnnexLibrary();

  /**
   * Returns the meta object for class '{@link edu.umn.cs.crisys.safety.safety.AnnexSubclause <em>Annex Subclause</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Annex Subclause</em>'.
   * @see edu.umn.cs.crisys.safety.safety.AnnexSubclause
   * @generated
   */
  EClass getAnnexSubclause();

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
   * Returns the meta object for class '{@link edu.umn.cs.crisys.safety.safety.AnalysisBehavior <em>Analysis Behavior</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Analysis Behavior</em>'.
   * @see edu.umn.cs.crisys.safety.safety.AnalysisBehavior
   * @generated
   */
  EClass getAnalysisBehavior();

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
   * Returns the meta object for class '{@link edu.umn.cs.crisys.safety.safety.HWFaultSubcomponent <em>HW Fault Subcomponent</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>HW Fault Subcomponent</em>'.
   * @see edu.umn.cs.crisys.safety.safety.HWFaultSubcomponent
   * @generated
   */
  EClass getHWFaultSubcomponent();

  /**
   * Returns the meta object for class '{@link edu.umn.cs.crisys.safety.safety.PropagationTypeConstraint <em>Propagation Type Constraint</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Propagation Type Constraint</em>'.
   * @see edu.umn.cs.crisys.safety.safety.PropagationTypeConstraint
   * @generated
   */
  EClass getPropagationTypeConstraint();

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
   * Returns the meta object for class '{@link edu.umn.cs.crisys.safety.safety.SafetyEqStatement <em>Eq Statement</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Eq Statement</em>'.
   * @see edu.umn.cs.crisys.safety.safety.SafetyEqStatement
   * @generated
   */
  EClass getSafetyEqStatement();

  /**
   * Returns the meta object for class '{@link edu.umn.cs.crisys.safety.safety.Interval <em>Interval</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Interval</em>'.
   * @see edu.umn.cs.crisys.safety.safety.Interval
   * @generated
   */
  EClass getInterval();

  /**
   * Returns the meta object for the containment reference '{@link edu.umn.cs.crisys.safety.safety.Interval#getLow <em>Low</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Low</em>'.
   * @see edu.umn.cs.crisys.safety.safety.Interval#getLow()
   * @see #getInterval()
   * @generated
   */
  EReference getInterval_Low();

  /**
   * Returns the meta object for the containment reference '{@link edu.umn.cs.crisys.safety.safety.Interval#getHigh <em>High</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>High</em>'.
   * @see edu.umn.cs.crisys.safety.safety.Interval#getHigh()
   * @see #getInterval()
   * @generated
   */
  EReference getInterval_High();

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
   * Returns the meta object for the attribute '{@link edu.umn.cs.crisys.safety.safety.FaultStatement#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see edu.umn.cs.crisys.safety.safety.FaultStatement#getName()
   * @see #getFaultStatement()
   * @generated
   */
  EAttribute getFaultStatement_Name();

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
   * Returns the meta object for the reference '{@link edu.umn.cs.crisys.safety.safety.FaultStatement#getFaultDefName <em>Fault Def Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Fault Def Name</em>'.
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
   * Returns the meta object for class '{@link edu.umn.cs.crisys.safety.safety.AnalysisStatement <em>Analysis Statement</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Analysis Statement</em>'.
   * @see edu.umn.cs.crisys.safety.safety.AnalysisStatement
   * @generated
   */
  EClass getAnalysisStatement();

  /**
   * Returns the meta object for the containment reference '{@link edu.umn.cs.crisys.safety.safety.AnalysisStatement#getBehavior <em>Behavior</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Behavior</em>'.
   * @see edu.umn.cs.crisys.safety.safety.AnalysisStatement#getBehavior()
   * @see #getAnalysisStatement()
   * @generated
   */
  EReference getAnalysisStatement_Behavior();

  /**
   * Returns the meta object for class '{@link edu.umn.cs.crisys.safety.safety.HWFaultStatement <em>HW Fault Statement</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>HW Fault Statement</em>'.
   * @see edu.umn.cs.crisys.safety.safety.HWFaultStatement
   * @generated
   */
  EClass getHWFaultStatement();

  /**
   * Returns the meta object for the attribute '{@link edu.umn.cs.crisys.safety.safety.HWFaultStatement#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see edu.umn.cs.crisys.safety.safety.HWFaultStatement#getName()
   * @see #getHWFaultStatement()
   * @generated
   */
  EAttribute getHWFaultStatement_Name();

  /**
   * Returns the meta object for the attribute '{@link edu.umn.cs.crisys.safety.safety.HWFaultStatement#getStr <em>Str</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Str</em>'.
   * @see edu.umn.cs.crisys.safety.safety.HWFaultStatement#getStr()
   * @see #getHWFaultStatement()
   * @generated
   */
  EAttribute getHWFaultStatement_Str();

  /**
   * Returns the meta object for the containment reference list '{@link edu.umn.cs.crisys.safety.safety.HWFaultStatement#getFaultDefinitions <em>Fault Definitions</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Fault Definitions</em>'.
   * @see edu.umn.cs.crisys.safety.safety.HWFaultStatement#getFaultDefinitions()
   * @see #getHWFaultStatement()
   * @generated
   */
  EReference getHWFaultStatement_FaultDefinitions();

  /**
   * Returns the meta object for class '{@link edu.umn.cs.crisys.safety.safety.PropagateStatement <em>Propagate Statement</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Propagate Statement</em>'.
   * @see edu.umn.cs.crisys.safety.safety.PropagateStatement
   * @generated
   */
  EClass getPropagateStatement();

  /**
   * Returns the meta object for the attribute list '{@link edu.umn.cs.crisys.safety.safety.PropagateStatement#getSrcFaultList <em>Src Fault List</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Src Fault List</em>'.
   * @see edu.umn.cs.crisys.safety.safety.PropagateStatement#getSrcFaultList()
   * @see #getPropagateStatement()
   * @generated
   */
  EAttribute getPropagateStatement_SrcFaultList();

  /**
   * Returns the meta object for the reference list '{@link edu.umn.cs.crisys.safety.safety.PropagateStatement#getSrcComp_path <em>Src Comp path</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference list '<em>Src Comp path</em>'.
   * @see edu.umn.cs.crisys.safety.safety.PropagateStatement#getSrcComp_path()
   * @see #getPropagateStatement()
   * @generated
   */
  EReference getPropagateStatement_SrcComp_path();

  /**
   * Returns the meta object for the attribute list '{@link edu.umn.cs.crisys.safety.safety.PropagateStatement#getDestFaultList <em>Dest Fault List</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Dest Fault List</em>'.
   * @see edu.umn.cs.crisys.safety.safety.PropagateStatement#getDestFaultList()
   * @see #getPropagateStatement()
   * @generated
   */
  EAttribute getPropagateStatement_DestFaultList();

  /**
   * Returns the meta object for the reference list '{@link edu.umn.cs.crisys.safety.safety.PropagateStatement#getDestComp_path <em>Dest Comp path</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference list '<em>Dest Comp path</em>'.
   * @see edu.umn.cs.crisys.safety.safety.PropagateStatement#getDestComp_path()
   * @see #getPropagateStatement()
   * @generated
   */
  EReference getPropagateStatement_DestComp_path();

  /**
   * Returns the meta object for class '{@link edu.umn.cs.crisys.safety.safety.ActivationStatement <em>Activation Statement</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Activation Statement</em>'.
   * @see edu.umn.cs.crisys.safety.safety.ActivationStatement
   * @generated
   */
  EClass getActivationStatement();

  /**
   * Returns the meta object for the attribute '{@link edu.umn.cs.crisys.safety.safety.ActivationStatement#getAgreeBoolVarName <em>Agree Bool Var Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Agree Bool Var Name</em>'.
   * @see edu.umn.cs.crisys.safety.safety.ActivationStatement#getAgreeBoolVarName()
   * @see #getActivationStatement()
   * @generated
   */
  EAttribute getActivationStatement_AgreeBoolVarName();

  /**
   * Returns the meta object for the reference '{@link edu.umn.cs.crisys.safety.safety.ActivationStatement#getAgreeComp_Path <em>Agree Comp Path</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Agree Comp Path</em>'.
   * @see edu.umn.cs.crisys.safety.safety.ActivationStatement#getAgreeComp_Path()
   * @see #getActivationStatement()
   * @generated
   */
  EReference getActivationStatement_AgreeComp_Path();

  /**
   * Returns the meta object for the attribute '{@link edu.umn.cs.crisys.safety.safety.ActivationStatement#getFaultName <em>Fault Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Fault Name</em>'.
   * @see edu.umn.cs.crisys.safety.safety.ActivationStatement#getFaultName()
   * @see #getActivationStatement()
   * @generated
   */
  EAttribute getActivationStatement_FaultName();

  /**
   * Returns the meta object for the reference '{@link edu.umn.cs.crisys.safety.safety.ActivationStatement#getFaultComp_Path <em>Fault Comp Path</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Fault Comp Path</em>'.
   * @see edu.umn.cs.crisys.safety.safety.ActivationStatement#getFaultComp_Path()
   * @see #getActivationStatement()
   * @generated
   */
  EReference getActivationStatement_FaultComp_Path();

  /**
   * Returns the meta object for class '{@link edu.umn.cs.crisys.safety.safety.FaultCountBehavior <em>Fault Count Behavior</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Fault Count Behavior</em>'.
   * @see edu.umn.cs.crisys.safety.safety.FaultCountBehavior
   * @generated
   */
  EClass getFaultCountBehavior();

  /**
   * Returns the meta object for the attribute '{@link edu.umn.cs.crisys.safety.safety.FaultCountBehavior#getMaxFaults <em>Max Faults</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Max Faults</em>'.
   * @see edu.umn.cs.crisys.safety.safety.FaultCountBehavior#getMaxFaults()
   * @see #getFaultCountBehavior()
   * @generated
   */
  EAttribute getFaultCountBehavior_MaxFaults();

  /**
   * Returns the meta object for class '{@link edu.umn.cs.crisys.safety.safety.ProbabilityBehavior <em>Probability Behavior</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Probability Behavior</em>'.
   * @see edu.umn.cs.crisys.safety.safety.ProbabilityBehavior
   * @generated
   */
  EClass getProbabilityBehavior();

  /**
   * Returns the meta object for the attribute '{@link edu.umn.cs.crisys.safety.safety.ProbabilityBehavior#getProbabilty <em>Probabilty</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Probabilty</em>'.
   * @see edu.umn.cs.crisys.safety.safety.ProbabilityBehavior#getProbabilty()
   * @see #getProbabilityBehavior()
   * @generated
   */
  EAttribute getProbabilityBehavior_Probabilty();

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
   * Returns the meta object for the attribute list '{@link edu.umn.cs.crisys.safety.safety.InputStatement#getFault_in <em>Fault in</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Fault in</em>'.
   * @see edu.umn.cs.crisys.safety.safety.InputStatement#getFault_in()
   * @see #getInputStatement()
   * @generated
   */
  EAttribute getInputStatement_Fault_in();

  /**
   * Returns the meta object for the containment reference list '{@link edu.umn.cs.crisys.safety.safety.InputStatement#getNom_conn <em>Nom conn</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Nom conn</em>'.
   * @see edu.umn.cs.crisys.safety.safety.InputStatement#getNom_conn()
   * @see #getInputStatement()
   * @generated
   */
  EReference getInputStatement_Nom_conn();

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
   * Returns the meta object for the reference list '{@link edu.umn.cs.crisys.safety.safety.OutputStatement#getNom_conn <em>Nom conn</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference list '<em>Nom conn</em>'.
   * @see edu.umn.cs.crisys.safety.safety.OutputStatement#getNom_conn()
   * @see #getOutputStatement()
   * @generated
   */
  EReference getOutputStatement_Nom_conn();

  /**
   * Returns the meta object for the attribute list '{@link edu.umn.cs.crisys.safety.safety.OutputStatement#getFault_out <em>Fault out</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Fault out</em>'.
   * @see edu.umn.cs.crisys.safety.safety.OutputStatement#getFault_out()
   * @see #getOutputStatement()
   * @generated
   */
  EAttribute getOutputStatement_Fault_out();

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
   * Returns the meta object for class '{@link edu.umn.cs.crisys.safety.safety.ProbabilityStatement <em>Probability Statement</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Probability Statement</em>'.
   * @see edu.umn.cs.crisys.safety.safety.ProbabilityStatement
   * @generated
   */
  EClass getProbabilityStatement();

  /**
   * Returns the meta object for the attribute '{@link edu.umn.cs.crisys.safety.safety.ProbabilityStatement#getProbability <em>Probability</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Probability</em>'.
   * @see edu.umn.cs.crisys.safety.safety.ProbabilityStatement#getProbability()
   * @see #getProbabilityStatement()
   * @generated
   */
  EAttribute getProbabilityStatement_Probability();

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
   * Returns the meta object for class '{@link edu.umn.cs.crisys.safety.safety.PropagationTypeStatement <em>Propagation Type Statement</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Propagation Type Statement</em>'.
   * @see edu.umn.cs.crisys.safety.safety.PropagationTypeStatement
   * @generated
   */
  EClass getPropagationTypeStatement();

  /**
   * Returns the meta object for the containment reference '{@link edu.umn.cs.crisys.safety.safety.PropagationTypeStatement#getPty <em>Pty</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Pty</em>'.
   * @see edu.umn.cs.crisys.safety.safety.PropagationTypeStatement#getPty()
   * @see #getPropagationTypeStatement()
   * @generated
   */
  EReference getPropagationTypeStatement_Pty();

  /**
   * Returns the meta object for class '{@link edu.umn.cs.crisys.safety.safety.asymmetric <em>asymmetric</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>asymmetric</em>'.
   * @see edu.umn.cs.crisys.safety.safety.asymmetric
   * @generated
   */
  EClass getasymmetric();

  /**
   * Returns the meta object for class '{@link edu.umn.cs.crisys.safety.safety.symmetric <em>symmetric</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>symmetric</em>'.
   * @see edu.umn.cs.crisys.safety.safety.symmetric
   * @generated
   */
  EClass getsymmetric();

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
   * Returns the meta object for class '{@link edu.umn.cs.crisys.safety.safety.EnablerCondition <em>Enabler Condition</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Enabler Condition</em>'.
   * @see edu.umn.cs.crisys.safety.safety.EnablerCondition
   * @generated
   */
  EClass getEnablerCondition();

  /**
   * Returns the meta object for the containment reference list '{@link edu.umn.cs.crisys.safety.safety.EnablerCondition#getExprList <em>Expr List</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Expr List</em>'.
   * @see edu.umn.cs.crisys.safety.safety.EnablerCondition#getExprList()
   * @see #getEnablerCondition()
   * @generated
   */
  EReference getEnablerCondition_ExprList();

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
   * Returns the meta object for the containment reference '{@link edu.umn.cs.crisys.safety.safety.IntervalEq#getLhs_int <em>Lhs int</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Lhs int</em>'.
   * @see edu.umn.cs.crisys.safety.safety.IntervalEq#getLhs_int()
   * @see #getIntervalEq()
   * @generated
   */
  EReference getIntervalEq_Lhs_int();

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
   * Returns the meta object for the containment reference '{@link edu.umn.cs.crisys.safety.safety.SetEq#getLhs_set <em>Lhs set</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Lhs set</em>'.
   * @see edu.umn.cs.crisys.safety.safety.SetEq#getLhs_set()
   * @see #getSetEq()
   * @generated
   */
  EReference getSetEq_Lhs_set();

  /**
   * Returns the meta object for the containment reference '{@link edu.umn.cs.crisys.safety.safety.SetEq#getL1 <em>L1</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>L1</em>'.
   * @see edu.umn.cs.crisys.safety.safety.SetEq#getL1()
   * @see #getSetEq()
   * @generated
   */
  EReference getSetEq_L1();

  /**
   * Returns the meta object for the containment reference list '{@link edu.umn.cs.crisys.safety.safety.SetEq#getList <em>List</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>List</em>'.
   * @see edu.umn.cs.crisys.safety.safety.SetEq#getList()
   * @see #getSetEq()
   * @generated
   */
  EReference getSetEq_List();

  /**
   * Returns the meta object for class '{@link edu.umn.cs.crisys.safety.safety.RangeEq <em>Range Eq</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Range Eq</em>'.
   * @see edu.umn.cs.crisys.safety.safety.RangeEq
   * @generated
   */
  EClass getRangeEq();

  /**
   * Returns the meta object for the containment reference '{@link edu.umn.cs.crisys.safety.safety.RangeEq#getLhs_range <em>Lhs range</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Lhs range</em>'.
   * @see edu.umn.cs.crisys.safety.safety.RangeEq#getLhs_range()
   * @see #getRangeEq()
   * @generated
   */
  EReference getRangeEq_Lhs_range();

  /**
   * Returns the meta object for the containment reference '{@link edu.umn.cs.crisys.safety.safety.RangeEq#getL1 <em>L1</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>L1</em>'.
   * @see edu.umn.cs.crisys.safety.safety.RangeEq#getL1()
   * @see #getRangeEq()
   * @generated
   */
  EReference getRangeEq_L1();

  /**
   * Returns the meta object for the containment reference '{@link edu.umn.cs.crisys.safety.safety.RangeEq#getL2 <em>L2</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>L2</em>'.
   * @see edu.umn.cs.crisys.safety.safety.RangeEq#getL2()
   * @see #getRangeEq()
   * @generated
   */
  EReference getRangeEq_L2();

  /**
   * Returns the meta object for class '{@link edu.umn.cs.crisys.safety.safety.ClosedInterval <em>Closed Interval</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Closed Interval</em>'.
   * @see edu.umn.cs.crisys.safety.safety.ClosedInterval
   * @generated
   */
  EClass getClosedInterval();

  /**
   * Returns the meta object for class '{@link edu.umn.cs.crisys.safety.safety.OpenLeftInterval <em>Open Left Interval</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Open Left Interval</em>'.
   * @see edu.umn.cs.crisys.safety.safety.OpenLeftInterval
   * @generated
   */
  EClass getOpenLeftInterval();

  /**
   * Returns the meta object for class '{@link edu.umn.cs.crisys.safety.safety.OpenRightInterval <em>Open Right Interval</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Open Right Interval</em>'.
   * @see edu.umn.cs.crisys.safety.safety.OpenRightInterval
   * @generated
   */
  EClass getOpenRightInterval();

  /**
   * Returns the meta object for class '{@link edu.umn.cs.crisys.safety.safety.OpenInterval <em>Open Interval</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Open Interval</em>'.
   * @see edu.umn.cs.crisys.safety.safety.OpenInterval
   * @generated
   */
  EClass getOpenInterval();

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
     * The meta object literal for the '{@link edu.umn.cs.crisys.safety.safety.impl.AnnexLibraryImpl <em>Annex Library</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.umn.cs.crisys.safety.safety.impl.AnnexLibraryImpl
     * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getAnnexLibrary()
     * @generated
     */
    EClass ANNEX_LIBRARY = eINSTANCE.getAnnexLibrary();

    /**
     * The meta object literal for the '{@link edu.umn.cs.crisys.safety.safety.impl.AnnexSubclauseImpl <em>Annex Subclause</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.umn.cs.crisys.safety.safety.impl.AnnexSubclauseImpl
     * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getAnnexSubclause()
     * @generated
     */
    EClass ANNEX_SUBCLAUSE = eINSTANCE.getAnnexSubclause();

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
     * The meta object literal for the '{@link edu.umn.cs.crisys.safety.safety.impl.AnalysisBehaviorImpl <em>Analysis Behavior</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.umn.cs.crisys.safety.safety.impl.AnalysisBehaviorImpl
     * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getAnalysisBehavior()
     * @generated
     */
    EClass ANALYSIS_BEHAVIOR = eINSTANCE.getAnalysisBehavior();

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
     * The meta object literal for the '{@link edu.umn.cs.crisys.safety.safety.impl.HWFaultSubcomponentImpl <em>HW Fault Subcomponent</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.umn.cs.crisys.safety.safety.impl.HWFaultSubcomponentImpl
     * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getHWFaultSubcomponent()
     * @generated
     */
    EClass HW_FAULT_SUBCOMPONENT = eINSTANCE.getHWFaultSubcomponent();

    /**
     * The meta object literal for the '{@link edu.umn.cs.crisys.safety.safety.impl.PropagationTypeConstraintImpl <em>Propagation Type Constraint</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.umn.cs.crisys.safety.safety.impl.PropagationTypeConstraintImpl
     * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getPropagationTypeConstraint()
     * @generated
     */
    EClass PROPAGATION_TYPE_CONSTRAINT = eINSTANCE.getPropagationTypeConstraint();

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
     * The meta object literal for the '{@link edu.umn.cs.crisys.safety.safety.impl.SafetyEqStatementImpl <em>Eq Statement</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.umn.cs.crisys.safety.safety.impl.SafetyEqStatementImpl
     * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getSafetyEqStatement()
     * @generated
     */
    EClass SAFETY_EQ_STATEMENT = eINSTANCE.getSafetyEqStatement();

    /**
     * The meta object literal for the '{@link edu.umn.cs.crisys.safety.safety.impl.IntervalImpl <em>Interval</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.umn.cs.crisys.safety.safety.impl.IntervalImpl
     * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getInterval()
     * @generated
     */
    EClass INTERVAL = eINSTANCE.getInterval();

    /**
     * The meta object literal for the '<em><b>Low</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference INTERVAL__LOW = eINSTANCE.getInterval_Low();

    /**
     * The meta object literal for the '<em><b>High</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference INTERVAL__HIGH = eINSTANCE.getInterval_High();

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
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FAULT_STATEMENT__NAME = eINSTANCE.getFaultStatement_Name();

    /**
     * The meta object literal for the '<em><b>Str</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FAULT_STATEMENT__STR = eINSTANCE.getFaultStatement_Str();

    /**
     * The meta object literal for the '<em><b>Fault Def Name</b></em>' reference feature.
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
     * The meta object literal for the '{@link edu.umn.cs.crisys.safety.safety.impl.AnalysisStatementImpl <em>Analysis Statement</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.umn.cs.crisys.safety.safety.impl.AnalysisStatementImpl
     * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getAnalysisStatement()
     * @generated
     */
    EClass ANALYSIS_STATEMENT = eINSTANCE.getAnalysisStatement();

    /**
     * The meta object literal for the '<em><b>Behavior</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ANALYSIS_STATEMENT__BEHAVIOR = eINSTANCE.getAnalysisStatement_Behavior();

    /**
     * The meta object literal for the '{@link edu.umn.cs.crisys.safety.safety.impl.HWFaultStatementImpl <em>HW Fault Statement</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.umn.cs.crisys.safety.safety.impl.HWFaultStatementImpl
     * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getHWFaultStatement()
     * @generated
     */
    EClass HW_FAULT_STATEMENT = eINSTANCE.getHWFaultStatement();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute HW_FAULT_STATEMENT__NAME = eINSTANCE.getHWFaultStatement_Name();

    /**
     * The meta object literal for the '<em><b>Str</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute HW_FAULT_STATEMENT__STR = eINSTANCE.getHWFaultStatement_Str();

    /**
     * The meta object literal for the '<em><b>Fault Definitions</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference HW_FAULT_STATEMENT__FAULT_DEFINITIONS = eINSTANCE.getHWFaultStatement_FaultDefinitions();

    /**
     * The meta object literal for the '{@link edu.umn.cs.crisys.safety.safety.impl.PropagateStatementImpl <em>Propagate Statement</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.umn.cs.crisys.safety.safety.impl.PropagateStatementImpl
     * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getPropagateStatement()
     * @generated
     */
    EClass PROPAGATE_STATEMENT = eINSTANCE.getPropagateStatement();

    /**
     * The meta object literal for the '<em><b>Src Fault List</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PROPAGATE_STATEMENT__SRC_FAULT_LIST = eINSTANCE.getPropagateStatement_SrcFaultList();

    /**
     * The meta object literal for the '<em><b>Src Comp path</b></em>' reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference PROPAGATE_STATEMENT__SRC_COMP_PATH = eINSTANCE.getPropagateStatement_SrcComp_path();

    /**
     * The meta object literal for the '<em><b>Dest Fault List</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PROPAGATE_STATEMENT__DEST_FAULT_LIST = eINSTANCE.getPropagateStatement_DestFaultList();

    /**
     * The meta object literal for the '<em><b>Dest Comp path</b></em>' reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference PROPAGATE_STATEMENT__DEST_COMP_PATH = eINSTANCE.getPropagateStatement_DestComp_path();

    /**
     * The meta object literal for the '{@link edu.umn.cs.crisys.safety.safety.impl.ActivationStatementImpl <em>Activation Statement</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.umn.cs.crisys.safety.safety.impl.ActivationStatementImpl
     * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getActivationStatement()
     * @generated
     */
    EClass ACTIVATION_STATEMENT = eINSTANCE.getActivationStatement();

    /**
     * The meta object literal for the '<em><b>Agree Bool Var Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ACTIVATION_STATEMENT__AGREE_BOOL_VAR_NAME = eINSTANCE.getActivationStatement_AgreeBoolVarName();

    /**
     * The meta object literal for the '<em><b>Agree Comp Path</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ACTIVATION_STATEMENT__AGREE_COMP_PATH = eINSTANCE.getActivationStatement_AgreeComp_Path();

    /**
     * The meta object literal for the '<em><b>Fault Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ACTIVATION_STATEMENT__FAULT_NAME = eINSTANCE.getActivationStatement_FaultName();

    /**
     * The meta object literal for the '<em><b>Fault Comp Path</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ACTIVATION_STATEMENT__FAULT_COMP_PATH = eINSTANCE.getActivationStatement_FaultComp_Path();

    /**
     * The meta object literal for the '{@link edu.umn.cs.crisys.safety.safety.impl.FaultCountBehaviorImpl <em>Fault Count Behavior</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.umn.cs.crisys.safety.safety.impl.FaultCountBehaviorImpl
     * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getFaultCountBehavior()
     * @generated
     */
    EClass FAULT_COUNT_BEHAVIOR = eINSTANCE.getFaultCountBehavior();

    /**
     * The meta object literal for the '<em><b>Max Faults</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FAULT_COUNT_BEHAVIOR__MAX_FAULTS = eINSTANCE.getFaultCountBehavior_MaxFaults();

    /**
     * The meta object literal for the '{@link edu.umn.cs.crisys.safety.safety.impl.ProbabilityBehaviorImpl <em>Probability Behavior</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.umn.cs.crisys.safety.safety.impl.ProbabilityBehaviorImpl
     * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getProbabilityBehavior()
     * @generated
     */
    EClass PROBABILITY_BEHAVIOR = eINSTANCE.getProbabilityBehavior();

    /**
     * The meta object literal for the '<em><b>Probabilty</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PROBABILITY_BEHAVIOR__PROBABILTY = eINSTANCE.getProbabilityBehavior_Probabilty();

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
     * The meta object literal for the '<em><b>Fault in</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute INPUT_STATEMENT__FAULT_IN = eINSTANCE.getInputStatement_Fault_in();

    /**
     * The meta object literal for the '<em><b>Nom conn</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference INPUT_STATEMENT__NOM_CONN = eINSTANCE.getInputStatement_Nom_conn();

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
     * The meta object literal for the '<em><b>Nom conn</b></em>' reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference OUTPUT_STATEMENT__NOM_CONN = eINSTANCE.getOutputStatement_Nom_conn();

    /**
     * The meta object literal for the '<em><b>Fault out</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute OUTPUT_STATEMENT__FAULT_OUT = eINSTANCE.getOutputStatement_Fault_out();

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
     * The meta object literal for the '{@link edu.umn.cs.crisys.safety.safety.impl.ProbabilityStatementImpl <em>Probability Statement</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.umn.cs.crisys.safety.safety.impl.ProbabilityStatementImpl
     * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getProbabilityStatement()
     * @generated
     */
    EClass PROBABILITY_STATEMENT = eINSTANCE.getProbabilityStatement();

    /**
     * The meta object literal for the '<em><b>Probability</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PROBABILITY_STATEMENT__PROBABILITY = eINSTANCE.getProbabilityStatement_Probability();

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
     * The meta object literal for the '{@link edu.umn.cs.crisys.safety.safety.impl.PropagationTypeStatementImpl <em>Propagation Type Statement</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.umn.cs.crisys.safety.safety.impl.PropagationTypeStatementImpl
     * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getPropagationTypeStatement()
     * @generated
     */
    EClass PROPAGATION_TYPE_STATEMENT = eINSTANCE.getPropagationTypeStatement();

    /**
     * The meta object literal for the '<em><b>Pty</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference PROPAGATION_TYPE_STATEMENT__PTY = eINSTANCE.getPropagationTypeStatement_Pty();

    /**
     * The meta object literal for the '{@link edu.umn.cs.crisys.safety.safety.impl.asymmetricImpl <em>asymmetric</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.umn.cs.crisys.safety.safety.impl.asymmetricImpl
     * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getasymmetric()
     * @generated
     */
    EClass ASYMMETRIC = eINSTANCE.getasymmetric();

    /**
     * The meta object literal for the '{@link edu.umn.cs.crisys.safety.safety.impl.symmetricImpl <em>symmetric</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.umn.cs.crisys.safety.safety.impl.symmetricImpl
     * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getsymmetric()
     * @generated
     */
    EClass SYMMETRIC = eINSTANCE.getsymmetric();

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
     * The meta object literal for the '{@link edu.umn.cs.crisys.safety.safety.impl.EnablerConditionImpl <em>Enabler Condition</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.umn.cs.crisys.safety.safety.impl.EnablerConditionImpl
     * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getEnablerCondition()
     * @generated
     */
    EClass ENABLER_CONDITION = eINSTANCE.getEnablerCondition();

    /**
     * The meta object literal for the '<em><b>Expr List</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ENABLER_CONDITION__EXPR_LIST = eINSTANCE.getEnablerCondition_ExprList();

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
     * The meta object literal for the '<em><b>Lhs int</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference INTERVAL_EQ__LHS_INT = eINSTANCE.getIntervalEq_Lhs_int();

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
     * The meta object literal for the '<em><b>Lhs set</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference SET_EQ__LHS_SET = eINSTANCE.getSetEq_Lhs_set();

    /**
     * The meta object literal for the '<em><b>L1</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference SET_EQ__L1 = eINSTANCE.getSetEq_L1();

    /**
     * The meta object literal for the '<em><b>List</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference SET_EQ__LIST = eINSTANCE.getSetEq_List();

    /**
     * The meta object literal for the '{@link edu.umn.cs.crisys.safety.safety.impl.RangeEqImpl <em>Range Eq</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.umn.cs.crisys.safety.safety.impl.RangeEqImpl
     * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getRangeEq()
     * @generated
     */
    EClass RANGE_EQ = eINSTANCE.getRangeEq();

    /**
     * The meta object literal for the '<em><b>Lhs range</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference RANGE_EQ__LHS_RANGE = eINSTANCE.getRangeEq_Lhs_range();

    /**
     * The meta object literal for the '<em><b>L1</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference RANGE_EQ__L1 = eINSTANCE.getRangeEq_L1();

    /**
     * The meta object literal for the '<em><b>L2</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference RANGE_EQ__L2 = eINSTANCE.getRangeEq_L2();

    /**
     * The meta object literal for the '{@link edu.umn.cs.crisys.safety.safety.impl.ClosedIntervalImpl <em>Closed Interval</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.umn.cs.crisys.safety.safety.impl.ClosedIntervalImpl
     * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getClosedInterval()
     * @generated
     */
    EClass CLOSED_INTERVAL = eINSTANCE.getClosedInterval();

    /**
     * The meta object literal for the '{@link edu.umn.cs.crisys.safety.safety.impl.OpenLeftIntervalImpl <em>Open Left Interval</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.umn.cs.crisys.safety.safety.impl.OpenLeftIntervalImpl
     * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getOpenLeftInterval()
     * @generated
     */
    EClass OPEN_LEFT_INTERVAL = eINSTANCE.getOpenLeftInterval();

    /**
     * The meta object literal for the '{@link edu.umn.cs.crisys.safety.safety.impl.OpenRightIntervalImpl <em>Open Right Interval</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.umn.cs.crisys.safety.safety.impl.OpenRightIntervalImpl
     * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getOpenRightInterval()
     * @generated
     */
    EClass OPEN_RIGHT_INTERVAL = eINSTANCE.getOpenRightInterval();

    /**
     * The meta object literal for the '{@link edu.umn.cs.crisys.safety.safety.impl.OpenIntervalImpl <em>Open Interval</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see edu.umn.cs.crisys.safety.safety.impl.OpenIntervalImpl
     * @see edu.umn.cs.crisys.safety.safety.impl.SafetyPackageImpl#getOpenInterval()
     * @generated
     */
    EClass OPEN_INTERVAL = eINSTANCE.getOpenInterval();

  }

} //SafetyPackage
