/**
 */
package edu.umn.cs.crisys.safety.safety;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Duration Statement</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.DurationStatement#getTc <em>Tc</em>}</li>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.DurationStatement#getInterv <em>Interv</em>}</li>
 * </ul>
 *
 * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getDurationStatement()
 * @model
 * @generated
 */
public interface DurationStatement extends FaultSubcomponent
{
  /**
   * Returns the value of the '<em><b>Tc</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Tc</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Tc</em>' containment reference.
   * @see #setTc(TemporalConstraint)
   * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getDurationStatement_Tc()
   * @model containment="true"
   * @generated
   */
  TemporalConstraint getTc();

  /**
   * Sets the value of the '{@link edu.umn.cs.crisys.safety.safety.DurationStatement#getTc <em>Tc</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Tc</em>' containment reference.
   * @see #getTc()
   * @generated
   */
  void setTc(TemporalConstraint value);

  /**
   * Returns the value of the '<em><b>Interv</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Interv</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Interv</em>' containment reference.
   * @see #setInterv(Interval)
   * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getDurationStatement_Interv()
   * @model containment="true"
   * @generated
   */
  Interval getInterv();

  /**
   * Sets the value of the '{@link edu.umn.cs.crisys.safety.safety.DurationStatement#getInterv <em>Interv</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Interv</em>' containment reference.
   * @see #getInterv()
   * @generated
   */
  void setInterv(Interval value);

} // DurationStatement
