/**
 */
package edu.umn.cs.crisys.safety.safety;

import com.rockwellcollins.atc.agree.agree.Expr;

import org.osate.aadl2.Element;

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
   * @see #setIn_conn(Element)
   * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getInputStatement_In_conn()
   * @model
   * @generated
   */
  Element getIn_conn();

  /**
   * Sets the value of the '{@link edu.umn.cs.crisys.safety.safety.InputStatement#getIn_conn <em>In conn</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>In conn</em>' reference.
   * @see #getIn_conn()
   * @generated
   */
  void setIn_conn(Element value);

  /**
   * Returns the value of the '<em><b>Out conn</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Out conn</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Out conn</em>' containment reference.
   * @see #setOut_conn(Expr)
   * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getInputStatement_Out_conn()
   * @model containment="true"
   * @generated
   */
  Expr getOut_conn();

  /**
   * Sets the value of the '{@link edu.umn.cs.crisys.safety.safety.InputStatement#getOut_conn <em>Out conn</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Out conn</em>' containment reference.
   * @see #getOut_conn()
   * @generated
   */
  void setOut_conn(Expr value);

} // InputStatement
