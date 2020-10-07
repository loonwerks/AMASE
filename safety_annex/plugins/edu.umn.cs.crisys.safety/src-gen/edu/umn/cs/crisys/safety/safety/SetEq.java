/**
 */
package edu.umn.cs.crisys.safety.safety;

import com.rockwellcollins.atc.agree.agree.Arg;
import com.rockwellcollins.atc.agree.agree.Expr;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Set Eq</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.SetEq#getLhs_set <em>Lhs set</em>}</li>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.SetEq#getL1 <em>L1</em>}</li>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.SetEq#getList <em>List</em>}</li>
 * </ul>
 *
 * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getSetEq()
 * @model
 * @generated
 */
public interface SetEq extends SafetyEqStatement
{
  /**
   * Returns the value of the '<em><b>Lhs set</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Lhs set</em>' containment reference.
   * @see #setLhs_set(Arg)
   * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getSetEq_Lhs_set()
   * @model containment="true"
   * @generated
   */
  Arg getLhs_set();

  /**
   * Sets the value of the '{@link edu.umn.cs.crisys.safety.safety.SetEq#getLhs_set <em>Lhs set</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Lhs set</em>' containment reference.
   * @see #getLhs_set()
   * @generated
   */
  void setLhs_set(Arg value);

  /**
   * Returns the value of the '<em><b>L1</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>L1</em>' containment reference.
   * @see #setL1(Expr)
   * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getSetEq_L1()
   * @model containment="true"
   * @generated
   */
  Expr getL1();

  /**
   * Sets the value of the '{@link edu.umn.cs.crisys.safety.safety.SetEq#getL1 <em>L1</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>L1</em>' containment reference.
   * @see #getL1()
   * @generated
   */
  void setL1(Expr value);

  /**
   * Returns the value of the '<em><b>List</b></em>' containment reference list.
   * The list contents are of type {@link com.rockwellcollins.atc.agree.agree.Expr}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>List</em>' containment reference list.
   * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getSetEq_List()
   * @model containment="true"
   * @generated
   */
  EList<Expr> getList();

} // SetEq
