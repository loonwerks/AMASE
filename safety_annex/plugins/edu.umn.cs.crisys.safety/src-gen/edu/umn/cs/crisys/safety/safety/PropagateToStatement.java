/**
 */
package edu.umn.cs.crisys.safety.safety;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Propagate To Statement</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.PropagateToStatement#getFaultList <em>Fault List</em>}</li>
 * </ul>
 *
 * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getPropagateToStatement()
 * @model
 * @generated
 */
public interface PropagateToStatement extends HWFaultSubcomponent
{
  /**
   * Returns the value of the '<em><b>Fault List</b></em>' attribute list.
   * The list contents are of type {@link java.lang.String}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Fault List</em>' attribute list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Fault List</em>' attribute list.
   * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getPropagateToStatement_FaultList()
   * @model unique="false"
   * @generated
   */
  EList<String> getFaultList();

} // PropagateToStatement
