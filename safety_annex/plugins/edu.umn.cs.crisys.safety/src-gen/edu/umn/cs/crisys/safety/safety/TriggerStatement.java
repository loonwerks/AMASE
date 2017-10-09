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
 *   <li>{@link edu.umn.cs.crisys.safety.safety.TriggerStatement#getProbability <em>Probability</em>}</li>
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
   * <p>
   * If the meaning of the '<em>Cond</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
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

  /**
   * Returns the value of the '<em><b>Probability</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Probability</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Probability</em>' attribute.
   * @see #setProbability(String)
   * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getTriggerStatement_Probability()
   * @model
   * @generated
   */
  String getProbability();

  /**
   * Sets the value of the '{@link edu.umn.cs.crisys.safety.safety.TriggerStatement#getProbability <em>Probability</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Probability</em>' attribute.
   * @see #getProbability()
   * @generated
   */
  void setProbability(String value);

} // TriggerStatement
