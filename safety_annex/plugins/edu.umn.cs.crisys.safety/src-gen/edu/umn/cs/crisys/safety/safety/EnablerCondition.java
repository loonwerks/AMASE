/**
 */
package edu.umn.cs.crisys.safety.safety;

import com.rockwellcollins.atc.agree.agree.Expr;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Enabler Condition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.EnablerCondition#getExprList <em>Expr List</em>}</li>
 * </ul>
 *
 * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getEnablerCondition()
 * @model
 * @generated
 */
public interface EnablerCondition extends TriggerCondition
{
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
   * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getEnablerCondition_ExprList()
   * @model containment="true"
   * @generated
   */
  EList<Expr> getExprList();

} // EnablerCondition
