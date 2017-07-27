/**
 */
package edu.umn.cs.crisys.safety.safety;

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
   * Returns the value of the '<em><b>Str</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Str</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
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
   * Returns the value of the '<em><b>Fault Def Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Fault Def Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Fault Def Name</em>' attribute.
   * @see #setFaultDefName(String)
   * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getFaultStatement_FaultDefName()
   * @model
   * @generated
   */
  String getFaultDefName();

  /**
   * Sets the value of the '{@link edu.umn.cs.crisys.safety.safety.FaultStatement#getFaultDefName <em>Fault Def Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Fault Def Name</em>' attribute.
   * @see #getFaultDefName()
   * @generated
   */
  void setFaultDefName(String value);

  /**
   * Returns the value of the '<em><b>Fault Definitions</b></em>' containment reference list.
   * The list contents are of type {@link edu.umn.cs.crisys.safety.safety.FaultSubcomponent}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Fault Definitions</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Fault Definitions</em>' containment reference list.
   * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getFaultStatement_FaultDefinitions()
   * @model containment="true"
   * @generated
   */
  EList<FaultSubcomponent> getFaultDefinitions();

} // FaultStatement
