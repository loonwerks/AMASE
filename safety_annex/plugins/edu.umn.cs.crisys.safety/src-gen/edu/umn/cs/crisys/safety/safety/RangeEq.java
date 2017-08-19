/**
 */
package edu.umn.cs.crisys.safety.safety;

import com.rockwellcollins.atc.agree.agree.Expr;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Range Eq</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.RangeEq#getLhs_range <em>Lhs range</em>}</li>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.RangeEq#getL1 <em>L1</em>}</li>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.RangeEq#getL2 <em>L2</em>}</li>
 * </ul>
 *
 * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getRangeEq()
 * @model
 * @generated
 */
public interface RangeEq extends SafetyEqStatement
{
  /**
   * Returns the value of the '<em><b>Lhs range</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Lhs range</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Lhs range</em>' attribute.
   * @see #setLhs_range(String)
   * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getRangeEq_Lhs_range()
   * @model
   * @generated
   */
  String getLhs_range();

  /**
   * Sets the value of the '{@link edu.umn.cs.crisys.safety.safety.RangeEq#getLhs_range <em>Lhs range</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Lhs range</em>' attribute.
   * @see #getLhs_range()
   * @generated
   */
  void setLhs_range(String value);

  /**
   * Returns the value of the '<em><b>L1</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>L1</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>L1</em>' containment reference.
   * @see #setL1(Expr)
   * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getRangeEq_L1()
   * @model containment="true"
   * @generated
   */
  Expr getL1();

  /**
   * Sets the value of the '{@link edu.umn.cs.crisys.safety.safety.RangeEq#getL1 <em>L1</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>L1</em>' containment reference.
   * @see #getL1()
   * @generated
   */
  void setL1(Expr value);

  /**
   * Returns the value of the '<em><b>L2</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>L2</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>L2</em>' containment reference.
   * @see #setL2(Expr)
   * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getRangeEq_L2()
   * @model containment="true"
   * @generated
   */
  Expr getL2();

  /**
   * Sets the value of the '{@link edu.umn.cs.crisys.safety.safety.RangeEq#getL2 <em>L2</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>L2</em>' containment reference.
   * @see #getL2()
   * @generated
   */
  void setL2(Expr value);

} // RangeEq
