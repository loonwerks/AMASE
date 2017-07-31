/**
 */
package edu.umn.cs.crisys.safety.safety;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Interval Eq</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.IntervalEq#getLhs_int <em>Lhs int</em>}</li>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.IntervalEq#getInterv <em>Interv</em>}</li>
 * </ul>
 *
 * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getIntervalEq()
 * @model
 * @generated
 */
public interface IntervalEq extends SafetyEqStatement
{
  /**
   * Returns the value of the '<em><b>Lhs int</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Lhs int</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Lhs int</em>' attribute.
   * @see #setLhs_int(String)
   * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getIntervalEq_Lhs_int()
   * @model
   * @generated
   */
  String getLhs_int();

  /**
   * Sets the value of the '{@link edu.umn.cs.crisys.safety.safety.IntervalEq#getLhs_int <em>Lhs int</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Lhs int</em>' attribute.
   * @see #getLhs_int()
   * @generated
   */
  void setLhs_int(String value);

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
   * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getIntervalEq_Interv()
   * @model containment="true"
   * @generated
   */
  Interval getInterv();

  /**
   * Sets the value of the '{@link edu.umn.cs.crisys.safety.safety.IntervalEq#getInterv <em>Interv</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Interv</em>' containment reference.
   * @see #getInterv()
   * @generated
   */
  void setInterv(Interval value);

} // IntervalEq
