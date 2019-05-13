/**
 */
package edu.umn.cs.crisys.safety.safety;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Propagation Type Statement</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.PropagationTypeStatement#getPty <em>Pty</em>}</li>
 * </ul>
 *
 * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getPropagationTypeStatement()
 * @model
 * @generated
 */
public interface PropagationTypeStatement extends FaultSubcomponent, HWFaultSubcomponent
{
  /**
   * Returns the value of the '<em><b>Pty</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Pty</em>' containment reference.
   * @see #setPty(PropagationTypeConstraint)
   * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getPropagationTypeStatement_Pty()
   * @model containment="true"
   * @generated
   */
  PropagationTypeConstraint getPty();

  /**
   * Sets the value of the '{@link edu.umn.cs.crisys.safety.safety.PropagationTypeStatement#getPty <em>Pty</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Pty</em>' containment reference.
   * @see #getPty()
   * @generated
   */
  void setPty(PropagationTypeConstraint value);

} // PropagationTypeStatement
