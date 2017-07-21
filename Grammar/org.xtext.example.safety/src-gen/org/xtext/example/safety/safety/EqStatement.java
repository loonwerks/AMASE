/**
 */
package org.xtext.example.safety.safety;

import com.rockwellcollins.atc.agree.agree.Expr;
import com.rockwellcollins.atc.agree.agree.TimeInterval;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Eq Statement</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.xtext.example.safety.safety.EqStatement#getInterv <em>Interv</em>}</li>
 *   <li>{@link org.xtext.example.safety.safety.EqStatement#getExprList <em>Expr List</em>}</li>
 * </ul>
 *
 * @see org.xtext.example.safety.safety.SafetyPackage#getEqStatement()
 * @model
 * @generated
 */
public interface EqStatement extends FaultSubcomponent, com.rockwellcollins.atc.agree.agree.EqStatement
{
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
   * @see org.xtext.example.safety.safety.SafetyPackage#getEqStatement_Interv()
   * @model containment="true"
   * @generated
   */
  TimeInterval getInterv();

  /**
   * Sets the value of the '{@link org.xtext.example.safety.safety.EqStatement#getInterv <em>Interv</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Interv</em>' containment reference.
   * @see #getInterv()
   * @generated
   */
  void setInterv(TimeInterval value);

  /**
   * Returns the value of the '<em><b>Expr List</b></em>' containment reference list.
   * The list contents are of type {@link com.rockwellcollins.atc.agree.agree.Expr}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Expr List</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Expr List</em>' containment reference list.
   * @see org.xtext.example.safety.safety.SafetyPackage#getEqStatement_ExprList()
   * @model containment="true"
   * @generated
   */
  EList<Expr> getExprList();

} // EqStatement
