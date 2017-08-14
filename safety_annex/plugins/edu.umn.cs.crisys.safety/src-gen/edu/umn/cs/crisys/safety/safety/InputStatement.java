/**
 */
package edu.umn.cs.crisys.safety.safety;

import com.rockwellcollins.atc.agree.agree.Expr;
import com.rockwellcollins.atc.agree.agree.NamedID;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Input Statement</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.InputStatement#getFault_in <em>Fault in</em>}</li>
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
   * Returns the value of the '<em><b>Fault in</b></em>' containment reference list.
   * The list contents are of type {@link com.rockwellcollins.atc.agree.agree.NamedID}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Fault in</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Fault in</em>' containment reference list.
   * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getInputStatement_Fault_in()
   * @model containment="true"
   * @generated
   */
  EList<NamedID> getFault_in();

  /**
   * Returns the value of the '<em><b>Nom conn</b></em>' containment reference list.
   * The list contents are of type {@link com.rockwellcollins.atc.agree.agree.Expr}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Nom conn</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Nom conn</em>' containment reference list.
   * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getInputStatement_Nom_conn()
   * @model containment="true"
   * @generated
   */
  EList<Expr> getNom_conn();

} // InputStatement
