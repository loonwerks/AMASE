/**
 */
package edu.umn.cs.crisys.safety.safety;

import com.rockwellcollins.atc.agree.agree.Arg;
import com.rockwellcollins.atc.agree.agree.NestedDotID;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Input Statement</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.InputStatement#getFault_in_conn <em>Fault in conn</em>}</li>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.InputStatement#getNom_conn <em>Nom conn</em>}</li>
 * </ul>
 *
 * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getInputStatement()
 * @model
 * @generated
 */
public interface InputStatement extends FaultSubcomponent
{
  /**
   * Returns the value of the '<em><b>Fault in conn</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Fault in conn</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Fault in conn</em>' containment reference.
   * @see #setFault_in_conn(Arg)
   * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getInputStatement_Fault_in_conn()
   * @model containment="true"
   * @generated
   */
  Arg getFault_in_conn();

  /**
   * Sets the value of the '{@link edu.umn.cs.crisys.safety.safety.InputStatement#getFault_in_conn <em>Fault in conn</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Fault in conn</em>' containment reference.
   * @see #getFault_in_conn()
   * @generated
   */
  void setFault_in_conn(Arg value);

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
   * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getInputStatement_Nom_conn()
   * @model containment="true"
   * @generated
   */
  NestedDotID getNom_conn();

  /**
   * Sets the value of the '{@link edu.umn.cs.crisys.safety.safety.InputStatement#getNom_conn <em>Nom conn</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Nom conn</em>' containment reference.
   * @see #getNom_conn()
   * @generated
   */
  void setNom_conn(NestedDotID value);

} // InputStatement
