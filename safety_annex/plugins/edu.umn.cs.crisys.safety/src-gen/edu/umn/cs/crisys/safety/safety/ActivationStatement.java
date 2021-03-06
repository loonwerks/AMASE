/**
 */
package edu.umn.cs.crisys.safety.safety;

import org.osate.aadl2.NamedElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Activation Statement</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.ActivationStatement#getAgreeBoolVarName <em>Agree Bool Var Name</em>}</li>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.ActivationStatement#getAgreeComp_Path <em>Agree Comp Path</em>}</li>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.ActivationStatement#getFaultName <em>Fault Name</em>}</li>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.ActivationStatement#getFaultComp_Path <em>Fault Comp Path</em>}</li>
 * </ul>
 *
 * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getActivationStatement()
 * @model
 * @generated
 */
public interface ActivationStatement extends SpecStatement
{
  /**
   * Returns the value of the '<em><b>Agree Bool Var Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Agree Bool Var Name</em>' attribute.
   * @see #setAgreeBoolVarName(String)
   * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getActivationStatement_AgreeBoolVarName()
   * @model
   * @generated
   */
  String getAgreeBoolVarName();

  /**
   * Sets the value of the '{@link edu.umn.cs.crisys.safety.safety.ActivationStatement#getAgreeBoolVarName <em>Agree Bool Var Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Agree Bool Var Name</em>' attribute.
   * @see #getAgreeBoolVarName()
   * @generated
   */
  void setAgreeBoolVarName(String value);

  /**
   * Returns the value of the '<em><b>Agree Comp Path</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Agree Comp Path</em>' reference.
   * @see #setAgreeComp_Path(NamedElement)
   * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getActivationStatement_AgreeComp_Path()
   * @model
   * @generated
   */
  NamedElement getAgreeComp_Path();

  /**
   * Sets the value of the '{@link edu.umn.cs.crisys.safety.safety.ActivationStatement#getAgreeComp_Path <em>Agree Comp Path</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Agree Comp Path</em>' reference.
   * @see #getAgreeComp_Path()
   * @generated
   */
  void setAgreeComp_Path(NamedElement value);

  /**
   * Returns the value of the '<em><b>Fault Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Fault Name</em>' attribute.
   * @see #setFaultName(String)
   * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getActivationStatement_FaultName()
   * @model
   * @generated
   */
  String getFaultName();

  /**
   * Sets the value of the '{@link edu.umn.cs.crisys.safety.safety.ActivationStatement#getFaultName <em>Fault Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Fault Name</em>' attribute.
   * @see #getFaultName()
   * @generated
   */
  void setFaultName(String value);

  /**
   * Returns the value of the '<em><b>Fault Comp Path</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Fault Comp Path</em>' reference.
   * @see #setFaultComp_Path(NamedElement)
   * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getActivationStatement_FaultComp_Path()
   * @model
   * @generated
   */
  NamedElement getFaultComp_Path();

  /**
   * Sets the value of the '{@link edu.umn.cs.crisys.safety.safety.ActivationStatement#getFaultComp_Path <em>Fault Comp Path</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Fault Comp Path</em>' reference.
   * @see #getFaultComp_Path()
   * @generated
   */
  void setFaultComp_Path(NamedElement value);

} // ActivationStatement
