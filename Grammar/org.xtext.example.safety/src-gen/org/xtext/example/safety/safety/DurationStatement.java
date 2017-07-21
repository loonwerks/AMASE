/**
 */
package org.xtext.example.safety.safety;

import com.rockwellcollins.atc.agree.agree.TimeInterval;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Duration Statement</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.xtext.example.safety.safety.DurationStatement#getTc <em>Tc</em>}</li>
 *   <li>{@link org.xtext.example.safety.safety.DurationStatement#getInterv <em>Interv</em>}</li>
 * </ul>
 *
 * @see org.xtext.example.safety.safety.SafetyPackage#getDurationStatement()
 * @model
 * @generated
 */
public interface DurationStatement extends FaultSubcomponent
{
  /**
   * Returns the value of the '<em><b>Tc</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Tc</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Tc</em>' attribute.
   * @see #setTc(String)
   * @see org.xtext.example.safety.safety.SafetyPackage#getDurationStatement_Tc()
   * @model
   * @generated
   */
  String getTc();

  /**
   * Sets the value of the '{@link org.xtext.example.safety.safety.DurationStatement#getTc <em>Tc</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Tc</em>' attribute.
   * @see #getTc()
   * @generated
   */
  void setTc(String value);

  /**
   * Returns the value of the '<em><b>Interv</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Interv</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Interv</em>' containment reference.
   * @see #setInterv(TimeInterval)
   * @see org.xtext.example.safety.safety.SafetyPackage#getDurationStatement_Interv()
   * @model containment="true"
   * @generated
   */
  TimeInterval getInterv();

  /**
   * Sets the value of the '{@link org.xtext.example.safety.safety.DurationStatement#getInterv <em>Interv</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Interv</em>' containment reference.
   * @see #getInterv()
   * @generated
   */
  void setInterv(TimeInterval value);

} // DurationStatement
