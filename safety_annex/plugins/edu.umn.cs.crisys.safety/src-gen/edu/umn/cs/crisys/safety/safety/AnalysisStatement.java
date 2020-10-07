/**
 */
package edu.umn.cs.crisys.safety.safety;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Analysis Statement</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.AnalysisStatement#getBehavior <em>Behavior</em>}</li>
 * </ul>
 *
 * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getAnalysisStatement()
 * @model
 * @generated
 */
public interface AnalysisStatement extends SpecStatement
{
  /**
   * Returns the value of the '<em><b>Behavior</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Behavior</em>' containment reference.
   * @see #setBehavior(AnalysisBehavior)
   * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getAnalysisStatement_Behavior()
   * @model containment="true"
   * @generated
   */
  AnalysisBehavior getBehavior();

  /**
   * Sets the value of the '{@link edu.umn.cs.crisys.safety.safety.AnalysisStatement#getBehavior <em>Behavior</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Behavior</em>' containment reference.
   * @see #getBehavior()
   * @generated
   */
  void setBehavior(AnalysisBehavior value);

} // AnalysisStatement
