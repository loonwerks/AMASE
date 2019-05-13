/**
 */
package edu.umn.cs.crisys.safety.safety;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Faulty Output Statement</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.FaultyOutputStatement#getFault_out <em>Fault out</em>}</li>
 * </ul>
 *
 * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getFaultyOutputStatement()
 * @model
 * @generated
 */
public interface FaultyOutputStatement extends ByzantineFaultSubcomponent
{
  /**
   * Returns the value of the '<em><b>Fault out</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Fault out</em>' attribute.
   * @see #setFault_out(String)
   * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getFaultyOutputStatement_Fault_out()
   * @model
   * @generated
   */
  String getFault_out();

  /**
   * Sets the value of the '{@link edu.umn.cs.crisys.safety.safety.FaultyOutputStatement#getFault_out <em>Fault out</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Fault out</em>' attribute.
   * @see #getFault_out()
   * @generated
   */
  void setFault_out(String value);

} // FaultyOutputStatement
