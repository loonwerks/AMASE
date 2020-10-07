/**
 */
package edu.umn.cs.crisys.safety.safety;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Trigger Statement</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.TriggerStatement#getCond <em>Cond</em>}</li>
 * </ul>
 *
 * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getTriggerStatement()
 * @model
 * @generated
 */
public interface TriggerStatement extends FaultSubcomponent
{
  /**
   * Returns the value of the '<em><b>Cond</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Cond</em>' containment reference.
   * @see #setCond(TriggerCondition)
   * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getTriggerStatement_Cond()
   * @model containment="true"
   * @generated
   */
  TriggerCondition getCond();

  /**
   * Sets the value of the '{@link edu.umn.cs.crisys.safety.safety.TriggerStatement#getCond <em>Cond</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Cond</em>' containment reference.
   * @see #getCond()
   * @generated
   */
  void setCond(TriggerCondition value);

} // TriggerStatement
