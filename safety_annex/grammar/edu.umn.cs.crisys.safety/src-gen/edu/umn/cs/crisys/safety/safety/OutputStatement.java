/**
 */
package edu.umn.cs.crisys.safety.safety;

import org.osate.aadl2.NamedElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Output Statement</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.OutputStatement#getOut_conn <em>Out conn</em>}</li>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.OutputStatement#getNom_conn <em>Nom conn</em>}</li>
 * </ul>
 *
 * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getOutputStatement()
 * @model
 * @generated
 */
public interface OutputStatement extends FaultSubcomponent
{
  /**
   * Returns the value of the '<em><b>Out conn</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Out conn</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Out conn</em>' attribute.
   * @see #setOut_conn(String)
   * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getOutputStatement_Out_conn()
   * @model
   * @generated
   */
  String getOut_conn();

  /**
   * Sets the value of the '{@link edu.umn.cs.crisys.safety.safety.OutputStatement#getOut_conn <em>Out conn</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Out conn</em>' attribute.
   * @see #getOut_conn()
   * @generated
   */
  void setOut_conn(String value);

  /**
   * Returns the value of the '<em><b>Nom conn</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Nom conn</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Nom conn</em>' reference.
   * @see #setNom_conn(NamedElement)
   * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getOutputStatement_Nom_conn()
   * @model
   * @generated
   */
  NamedElement getNom_conn();

  /**
   * Sets the value of the '{@link edu.umn.cs.crisys.safety.safety.OutputStatement#getNom_conn <em>Nom conn</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Nom conn</em>' reference.
   * @see #getNom_conn()
   * @generated
   */
  void setNom_conn(NamedElement value);

} // OutputStatement
