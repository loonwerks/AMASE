/**
 */
package edu.umn.cs.crisys.safety.safety;

import com.rockwellcollins.atc.agree.agree.NestedDotID;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Connections Statement</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.ConnectionsStatement#getFault_in <em>Fault in</em>}</li>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.ConnectionsStatement#getFaultDefName <em>Fault Def Name</em>}</li>
 * </ul>
 *
 * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getConnectionsStatement()
 * @model
 * @generated
 */
public interface ConnectionsStatement extends ByzantineFaultSubcomponent
{
  /**
   * Returns the value of the '<em><b>Fault in</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Fault in</em>' containment reference.
   * @see #setFault_in(NestedDotID)
   * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getConnectionsStatement_Fault_in()
   * @model containment="true"
   * @generated
   */
  NestedDotID getFault_in();

  /**
   * Sets the value of the '{@link edu.umn.cs.crisys.safety.safety.ConnectionsStatement#getFault_in <em>Fault in</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Fault in</em>' containment reference.
   * @see #getFault_in()
   * @generated
   */
  void setFault_in(NestedDotID value);

  /**
   * Returns the value of the '<em><b>Fault Def Name</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Fault Def Name</em>' containment reference.
   * @see #setFaultDefName(NestedDotID)
   * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getConnectionsStatement_FaultDefName()
   * @model containment="true"
   * @generated
   */
  NestedDotID getFaultDefName();

  /**
   * Sets the value of the '{@link edu.umn.cs.crisys.safety.safety.ConnectionsStatement#getFaultDefName <em>Fault Def Name</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Fault Def Name</em>' containment reference.
   * @see #getFaultDefName()
   * @generated
   */
  void setFaultDefName(NestedDotID value);

} // ConnectionsStatement
