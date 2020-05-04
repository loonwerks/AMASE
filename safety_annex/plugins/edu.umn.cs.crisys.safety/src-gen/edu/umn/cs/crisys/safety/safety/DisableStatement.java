/**
 */
package edu.umn.cs.crisys.safety.safety;

import org.osate.aadl2.BooleanLiteral;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Disable Statement</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.DisableStatement#getCond <em>Cond</em>}</li>
 * </ul>
 *
 * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getDisableStatement()
 * @model
 * @generated
 */
public interface DisableStatement extends FaultSubcomponent
{
  /**
   * Returns the value of the '<em><b>Cond</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Cond</em>' containment reference.
   * @see #setCond(BooleanLiteral)
   * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getDisableStatement_Cond()
   * @model containment="true"
   * @generated
   */
  BooleanLiteral getCond();

  /**
   * Sets the value of the '{@link edu.umn.cs.crisys.safety.safety.DisableStatement#getCond <em>Cond</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Cond</em>' containment reference.
   * @see #getCond()
   * @generated
   */
  void setCond(BooleanLiteral value);

} // DisableStatement
