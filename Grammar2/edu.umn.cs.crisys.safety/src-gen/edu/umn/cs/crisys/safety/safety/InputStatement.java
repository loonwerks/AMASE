/**
 */
package edu.umn.cs.crisys.safety.safety;

import org.osate.aadl2.NamedElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Input Statement</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.InputStatement#getIn_conn <em>In conn</em>}</li>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.InputStatement#getOut_conn <em>Out conn</em>}</li>
 * </ul>
 *
 * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getInputStatement()
 * @model
 * @generated
 */
public interface InputStatement extends FaultSubcomponent
{
  /**
   * Returns the value of the '<em><b>In conn</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>In conn</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>In conn</em>' reference.
   * @see #setIn_conn(NamedElement)
   * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getInputStatement_In_conn()
   * @model
   * @generated
   */
  NamedElement getIn_conn();

  /**
   * Sets the value of the '{@link edu.umn.cs.crisys.safety.safety.InputStatement#getIn_conn <em>In conn</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>In conn</em>' reference.
   * @see #getIn_conn()
   * @generated
   */
  void setIn_conn(NamedElement value);

  /**
   * Returns the value of the '<em><b>Out conn</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Out conn</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Out conn</em>' attribute.
   * @see #setOut_conn(String)
   * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getInputStatement_Out_conn()
   * @model
   * @generated
   */
  String getOut_conn();

  /**
   * Sets the value of the '{@link edu.umn.cs.crisys.safety.safety.InputStatement#getOut_conn <em>Out conn</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Out conn</em>' attribute.
   * @see #getOut_conn()
   * @generated
   */
  void setOut_conn(String value);

} // InputStatement
