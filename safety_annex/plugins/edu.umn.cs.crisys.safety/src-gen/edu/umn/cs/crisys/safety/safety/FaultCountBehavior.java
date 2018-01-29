/**
 */
package edu.umn.cs.crisys.safety.safety;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Fault Count Behavior</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.FaultCountBehavior#getMaxFaults <em>Max Faults</em>}</li>
 * </ul>
 *
 * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getFaultCountBehavior()
 * @model
 * @generated
 */
public interface FaultCountBehavior extends AnalysisBehavior
{
  /**
   * Returns the value of the '<em><b>Max Faults</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Max Faults</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Max Faults</em>' attribute.
   * @see #setMaxFaults(String)
   * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getFaultCountBehavior_MaxFaults()
   * @model
   * @generated
   */
  String getMaxFaults();

  /**
   * Sets the value of the '{@link edu.umn.cs.crisys.safety.safety.FaultCountBehavior#getMaxFaults <em>Max Faults</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Max Faults</em>' attribute.
   * @see #getMaxFaults()
   * @generated
   */
  void setMaxFaults(String value);

} // FaultCountBehavior
