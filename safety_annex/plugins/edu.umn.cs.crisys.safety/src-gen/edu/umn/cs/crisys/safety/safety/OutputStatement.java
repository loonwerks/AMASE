/**
 */
package edu.umn.cs.crisys.safety.safety;

import com.rockwellcollins.atc.agree.agree.Arg;
import com.rockwellcollins.atc.agree.agree.NestedDotID;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Output Statement</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.OutputStatement#getNom_conn <em>Nom conn</em>}</li>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.OutputStatement#getFault_out_conn <em>Fault out conn</em>}</li>
 * </ul>
 *
 * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getOutputStatement()
 * @model
 * @generated
 */
public interface OutputStatement extends FaultSubcomponent
{
  /**
   * Returns the value of the '<em><b>Nom conn</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Nom conn</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Nom conn</em>' containment reference.
   * @see #setNom_conn(NestedDotID)
   * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getOutputStatement_Nom_conn()
   * @model containment="true"
   * @generated
   */
  NestedDotID getNom_conn();

  /**
   * Sets the value of the '{@link edu.umn.cs.crisys.safety.safety.OutputStatement#getNom_conn <em>Nom conn</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Nom conn</em>' containment reference.
   * @see #getNom_conn()
   * @generated
   */
  void setNom_conn(NestedDotID value);

  /**
   * Returns the value of the '<em><b>Fault out conn</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Fault out conn</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Fault out conn</em>' containment reference.
   * @see #setFault_out_conn(Arg)
   * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getOutputStatement_Fault_out_conn()
   * @model containment="true"
   * @generated
   */
  Arg getFault_out_conn();

  /**
   * Sets the value of the '{@link edu.umn.cs.crisys.safety.safety.OutputStatement#getFault_out_conn <em>Fault out conn</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Fault out conn</em>' containment reference.
   * @see #getFault_out_conn()
   * @generated
   */
  void setFault_out_conn(Arg value);

} // OutputStatement
