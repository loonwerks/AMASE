/**
 */
package edu.umn.cs.crisys.safety.safety;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>HW Fault Statement</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.HWFaultStatement#getName <em>Name</em>}</li>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.HWFaultStatement#getStr <em>Str</em>}</li>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.HWFaultStatement#getFaultDefinitions <em>Fault Definitions</em>}</li>
 * </ul>
 *
 * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getHWFaultStatement()
 * @model
 * @generated
 */
public interface HWFaultStatement extends SpecStatement
{
  /**
   * Returns the value of the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Name</em>' attribute.
   * @see #setName(String)
   * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getHWFaultStatement_Name()
   * @model
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link edu.umn.cs.crisys.safety.safety.HWFaultStatement#getName <em>Name</em>}' attribute.
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
   * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getHWFaultStatement_Str()
   * @model
   * @generated
   */
  String getStr();

  /**
   * Sets the value of the '{@link edu.umn.cs.crisys.safety.safety.HWFaultStatement#getStr <em>Str</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Str</em>' attribute.
   * @see #getStr()
   * @generated
   */
  void setStr(String value);

  /**
   * Returns the value of the '<em><b>Fault Definitions</b></em>' containment reference list.
   * The list contents are of type {@link edu.umn.cs.crisys.safety.safety.HWFaultSubcomponent}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Fault Definitions</em>' containment reference list.
   * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getHWFaultStatement_FaultDefinitions()
   * @model containment="true"
   * @generated
   */
  EList<HWFaultSubcomponent> getFaultDefinitions();

} // HWFaultStatement
