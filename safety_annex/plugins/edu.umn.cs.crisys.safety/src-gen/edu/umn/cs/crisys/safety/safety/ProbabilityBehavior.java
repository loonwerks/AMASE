/**
 */
package edu.umn.cs.crisys.safety.safety;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Probability Behavior</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.ProbabilityBehavior#getProbabilty <em>Probabilty</em>}</li>
 * </ul>
 *
 * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getProbabilityBehavior()
 * @model
 * @generated
 */
public interface ProbabilityBehavior extends AnalysisBehavior
{
  /**
   * Returns the value of the '<em><b>Probabilty</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Probabilty</em>' attribute.
   * @see #setProbabilty(String)
   * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getProbabilityBehavior_Probabilty()
   * @model
   * @generated
   */
  String getProbabilty();

  /**
   * Sets the value of the '{@link edu.umn.cs.crisys.safety.safety.ProbabilityBehavior#getProbabilty <em>Probabilty</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Probabilty</em>' attribute.
   * @see #getProbabilty()
   * @generated
   */
  void setProbabilty(String value);

} // ProbabilityBehavior
