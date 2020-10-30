/**
 */
package edu.umn.cs.crisys.safety.safety;

import com.rockwellcollins.atc.agree.agree.Arg;

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
   * Returns the value of the '<em><b>Lhs int</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Lhs int</em>' containment reference.
   * @see #setLhs_int(Arg)
   * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getIntervalEq_Lhs_int()
   * @model containment="true"
   * @generated
   */
  Arg getLhs_int();

  /**
   * Sets the value of the '{@link edu.umn.cs.crisys.safety.safety.IntervalEq#getLhs_int <em>Lhs int</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Lhs int</em>' containment reference.
   * @see #getLhs_int()
   * @generated
   */
  void setLhs_int(Arg value);

  /**
   * Returns the value of the '<em><b>Interv</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Interv</em>' containment reference.
   * @see #setInterv(SafetyInterval)
   * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getIntervalEq_Interv()
   * @model containment="true"
   * @generated
   */
  SafetyInterval getInterv();

  /**
   * Sets the value of the '{@link edu.umn.cs.crisys.safety.safety.IntervalEq#getInterv <em>Interv</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Interv</em>' containment reference.
   * @see #getInterv()
   * @generated
   */
  void setInterv(SafetyInterval value);

} // IntervalEq
