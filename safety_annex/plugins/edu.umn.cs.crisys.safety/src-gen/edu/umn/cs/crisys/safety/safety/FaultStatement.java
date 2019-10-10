/**
 */
package edu.umn.cs.crisys.safety.safety;

import com.rockwellcollins.atc.agree.agree.DoubleDotRef;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Fault Statement</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.FaultStatement#getName <em>Name</em>}</li>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.FaultStatement#getStr <em>Str</em>}</li>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.FaultStatement#getFaultDefName <em>Fault Def Name</em>}</li>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.FaultStatement#getFaultDefinitions <em>Fault Definitions</em>}</li>
 * </ul>
 *
 * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getFaultStatement()
 * @model
 * @generated
 */
public interface FaultStatement extends SpecStatement
{
  /**
   * Returns the value of the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Name</em>' attribute.
   * @see #setName(String)
   * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getFaultStatement_Name()
   * @model
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link edu.umn.cs.crisys.safety.safety.FaultStatement#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #getName()
   * @generated
   */
  void setName(String value);

  /**
   * Returns the value of the '<em><b>Str</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Str</em>' attribute.
   * @see #setStr(String)
   * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getFaultStatement_Str()
   * @model
   * @generated
   */
  String getStr();

  /**
   * Sets the value of the '{@link edu.umn.cs.crisys.safety.safety.FaultStatement#getStr <em>Str</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Str</em>' attribute.
   * @see #getStr()
   * @generated
   */
  void setStr(String value);

  /**
   * Returns the value of the '<em><b>Fault Def Name</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Fault Def Name</em>' containment reference.
   * @see #setFaultDefName(DoubleDotRef)
   * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getFaultStatement_FaultDefName()
   * @model containment="true"
   * @generated
   */
  DoubleDotRef getFaultDefName();

  /**
   * Sets the value of the '{@link edu.umn.cs.crisys.safety.safety.FaultStatement#getFaultDefName <em>Fault Def Name</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Fault Def Name</em>' containment reference.
   * @see #getFaultDefName()
   * @generated
   */
  void setFaultDefName(DoubleDotRef value);

  /**
   * Returns the value of the '<em><b>Fault Definitions</b></em>' containment reference list.
   * The list contents are of type {@link edu.umn.cs.crisys.safety.safety.FaultSubcomponent}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Fault Definitions</em>' containment reference list.
   * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getFaultStatement_FaultDefinitions()
   * @model containment="true"
   * @generated
   */
  EList<FaultSubcomponent> getFaultDefinitions();

} // FaultStatement
