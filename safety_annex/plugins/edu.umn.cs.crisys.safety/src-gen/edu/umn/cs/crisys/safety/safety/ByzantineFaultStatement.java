/**
 */
package edu.umn.cs.crisys.safety.safety;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Byzantine Fault Statement</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.ByzantineFaultStatement#getName <em>Name</em>}</li>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.ByzantineFaultStatement#getStr <em>Str</em>}</li>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.ByzantineFaultStatement#getByzantine <em>Byzantine</em>}</li>
 * </ul>
 *
 * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getByzantineFaultStatement()
 * @model
 * @generated
 */
public interface ByzantineFaultStatement extends SpecStatement
{
  /**
   * Returns the value of the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Name</em>' attribute.
   * @see #setName(String)
   * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getByzantineFaultStatement_Name()
   * @model
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link edu.umn.cs.crisys.safety.safety.ByzantineFaultStatement#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #getName()
   * @generated
   */
  void setName(String value);

  /**
   * Returns the value of the '<em><b>Str</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Str</em>' attribute.
   * @see #setStr(String)
   * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getByzantineFaultStatement_Str()
   * @model
   * @generated
   */
  String getStr();

  /**
   * Sets the value of the '{@link edu.umn.cs.crisys.safety.safety.ByzantineFaultStatement#getStr <em>Str</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Str</em>' attribute.
   * @see #getStr()
   * @generated
   */
  void setStr(String value);

  /**
   * Returns the value of the '<em><b>Byzantine</b></em>' containment reference list.
   * The list contents are of type {@link edu.umn.cs.crisys.safety.safety.ByzantineFaultSubcomponent}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Byzantine</em>' containment reference list.
   * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getByzantineFaultStatement_Byzantine()
   * @model containment="true"
   * @generated
   */
  EList<ByzantineFaultSubcomponent> getByzantine();

} // ByzantineFaultStatement
