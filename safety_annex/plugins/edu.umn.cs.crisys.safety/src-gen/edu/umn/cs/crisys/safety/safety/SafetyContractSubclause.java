/**
 */
package edu.umn.cs.crisys.safety.safety;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Contract Subclause</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.SafetyContractSubclause#getContract <em>Contract</em>}</li>
 * </ul>
 *
 * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getSafetyContractSubclause()
 * @model
 * @generated
 */
public interface SafetyContractSubclause extends SafetySubclause
{
  /**
   * Returns the value of the '<em><b>Contract</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Contract</em>' containment reference.
   * @see #setContract(Contract)
   * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getSafetyContractSubclause_Contract()
   * @model containment="true"
   * @generated
   */
  Contract getContract();

  /**
   * Sets the value of the '{@link edu.umn.cs.crisys.safety.safety.SafetyContractSubclause#getContract <em>Contract</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Contract</em>' containment reference.
   * @see #getContract()
   * @generated
   */
  void setContract(Contract value);

} // SafetyContractSubclause
