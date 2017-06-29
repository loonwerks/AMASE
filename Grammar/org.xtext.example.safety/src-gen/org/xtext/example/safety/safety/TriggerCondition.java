/**
 */
package org.xtext.example.safety.safety;

import com.rockwellcollins.atc.agree.agree.Expr;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Trigger Condition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.xtext.example.safety.safety.TriggerCondition#getExprList <em>Expr List</em>}</li>
 * </ul>
 *
 * @see org.xtext.example.safety.safety.SafetyPackage#getTriggerCondition()
 * @model
 * @generated
 */
public interface TriggerCondition extends EObject
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
   * @see org.xtext.example.safety.safety.SafetyPackage#getTriggerCondition_ExprList()
   * @model containment="true"
   * @generated
   */
  EList<Expr> getExprList();

} // TriggerCondition
