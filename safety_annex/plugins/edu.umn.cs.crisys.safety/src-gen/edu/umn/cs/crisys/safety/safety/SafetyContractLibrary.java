/**
 */
package edu.umn.cs.crisys.safety.safety;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Contract Library</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.SafetyContractLibrary#getContract <em>Contract</em>}</li>
 * </ul>
 *
 * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getSafetyContractLibrary()
 * @model
 * @generated
 */
public interface SafetyContractLibrary extends SafetyLibrary
{
  /**
   * Returns the value of the '<em><b>Contract</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Contract</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Contract</em>' containment reference.
   * @see #setContract(Contract)
   * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getSafetyContractLibrary_Contract()
   * @model containment="true"
   * @generated
   */
  Contract getContract();

  /**
   * Sets the value of the '{@link edu.umn.cs.crisys.safety.safety.SafetyContractLibrary#getContract <em>Contract</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Contract</em>' containment reference.
   * @see #getContract()
   * @generated
   */
  void setContract(Contract value);

} // SafetyContractLibrary
