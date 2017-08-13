/**
 */
package edu.umn.cs.crisys.safety.safety;

import com.rockwellcollins.atc.agree.agree.NamedID;

import org.osate.aadl2.NamedElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Fault Output Assign Stmt</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.FaultOutputAssignStmt#getFault_output_eq <em>Fault output eq</em>}</li>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.FaultOutputAssignStmt#getEq_stmt <em>Eq stmt</em>}</li>
 * </ul>
 *
 * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getFaultOutputAssignStmt()
 * @model
 * @generated
 */
public interface FaultOutputAssignStmt extends OutputAssignStatement
{
  /**
   * Returns the value of the '<em><b>Fault output eq</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Fault output eq</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Fault output eq</em>' reference.
   * @see #setFault_output_eq(NamedElement)
   * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getFaultOutputAssignStmt_Fault_output_eq()
   * @model
   * @generated
   */
  NamedElement getFault_output_eq();

  /**
   * Sets the value of the '{@link edu.umn.cs.crisys.safety.safety.FaultOutputAssignStmt#getFault_output_eq <em>Fault output eq</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Fault output eq</em>' reference.
   * @see #getFault_output_eq()
   * @generated
   */
  void setFault_output_eq(NamedElement value);

  /**
   * Returns the value of the '<em><b>Eq stmt</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Eq stmt</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Eq stmt</em>' containment reference.
   * @see #setEq_stmt(NamedID)
   * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getFaultOutputAssignStmt_Eq_stmt()
   * @model containment="true"
   * @generated
   */
  NamedID getEq_stmt();

  /**
   * Sets the value of the '{@link edu.umn.cs.crisys.safety.safety.FaultOutputAssignStmt#getEq_stmt <em>Eq stmt</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Eq stmt</em>' containment reference.
   * @see #getEq_stmt()
   * @generated
   */
  void setEq_stmt(NamedID value);

} // FaultOutputAssignStmt
