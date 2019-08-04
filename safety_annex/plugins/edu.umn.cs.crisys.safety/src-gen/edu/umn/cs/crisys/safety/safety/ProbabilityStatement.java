/**
 */
package edu.umn.cs.crisys.safety.safety;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Probability Statement</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.ProbabilityStatement#getProbability <em>Probability</em>}</li>
 * </ul>
 *
 * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getProbabilityStatement()
 * @model
 * @generated
 */
public interface ProbabilityStatement extends FaultSubcomponent, HWFaultSubcomponent
{
  /**
   * Returns the value of the '<em><b>Probability</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Probability</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Probability</em>' attribute.
   * @see #setProbability(String)
   * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getProbabilityStatement_Probability()
   * @model
   * @generated
   */
  String getProbability();

  /**
   * Sets the value of the '{@link edu.umn.cs.crisys.safety.safety.ProbabilityStatement#getProbability <em>Probability</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Probability</em>' attribute.
   * @see #getProbability()
   * @generated
   */
  void setProbability(String value);

} // ProbabilityStatement
