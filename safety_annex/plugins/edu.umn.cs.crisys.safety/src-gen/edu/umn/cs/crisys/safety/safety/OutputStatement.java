/**
 */
package edu.umn.cs.crisys.safety.safety;

import com.rockwellcollins.atc.agree.agree.NestedDotID;

import org.eclipse.emf.common.util.EList;

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
 *   <li>{@link edu.umn.cs.crisys.safety.safety.OutputStatement#getFault_out <em>Fault out</em>}</li>
 * </ul>
 *
 * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getOutputStatement()
 * @model
 * @generated
 */
public interface OutputStatement extends FaultSubcomponent
{
  /**
   * Returns the value of the '<em><b>Nom conn</b></em>' containment reference list.
   * The list contents are of type {@link com.rockwellcollins.atc.agree.agree.NestedDotID}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Nom conn</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Nom conn</em>' containment reference list.
   * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getOutputStatement_Nom_conn()
   * @model containment="true"
   * @generated
   */
  EList<NestedDotID> getNom_conn();

  /**
   * Returns the value of the '<em><b>Fault out</b></em>' attribute list.
   * The list contents are of type {@link java.lang.String}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Fault out</em>' attribute list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Fault out</em>' attribute list.
   * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getOutputStatement_Fault_out()
   * @model unique="false"
   * @generated
   */
  EList<String> getFault_out();

} // OutputStatement
