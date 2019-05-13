/**
 */
package edu.umn.cs.crisys.safety.safety;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Contract</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.SafetyContract#getSpecs <em>Specs</em>}</li>
 * </ul>
 *
 * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getSafetyContract()
 * @model
 * @generated
 */
public interface SafetyContract extends Contract
{
  /**
   * Returns the value of the '<em><b>Specs</b></em>' containment reference list.
   * The list contents are of type {@link edu.umn.cs.crisys.safety.safety.SpecStatement}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Specs</em>' containment reference list.
   * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getSafetyContract_Specs()
   * @model containment="true"
   * @generated
   */
  EList<SpecStatement> getSpecs();

} // SafetyContract
