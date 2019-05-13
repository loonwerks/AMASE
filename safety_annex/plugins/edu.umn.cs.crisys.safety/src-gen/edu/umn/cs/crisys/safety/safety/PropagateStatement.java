/**
 */
package edu.umn.cs.crisys.safety.safety;

import com.rockwellcollins.atc.agree.agree.NestedDotID;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Propagate Statement</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.PropagateStatement#getSrcFaultList <em>Src Fault List</em>}</li>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.PropagateStatement#getSrcComp_path <em>Src Comp path</em>}</li>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.PropagateStatement#getDestFaultList <em>Dest Fault List</em>}</li>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.PropagateStatement#getDestComp_path <em>Dest Comp path</em>}</li>
 * </ul>
 *
 * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getPropagateStatement()
 * @model
 * @generated
 */
public interface PropagateStatement extends SpecStatement
{
  /**
   * Returns the value of the '<em><b>Src Fault List</b></em>' attribute list.
   * The list contents are of type {@link java.lang.String}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Src Fault List</em>' attribute list.
   * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getPropagateStatement_SrcFaultList()
   * @model unique="false"
   * @generated
   */
  EList<String> getSrcFaultList();

  /**
   * Returns the value of the '<em><b>Src Comp path</b></em>' containment reference list.
   * The list contents are of type {@link com.rockwellcollins.atc.agree.agree.NestedDotID}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Src Comp path</em>' containment reference list.
   * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getPropagateStatement_SrcComp_path()
   * @model containment="true"
   * @generated
   */
  EList<NestedDotID> getSrcComp_path();

  /**
   * Returns the value of the '<em><b>Dest Fault List</b></em>' attribute list.
   * The list contents are of type {@link java.lang.String}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Dest Fault List</em>' attribute list.
   * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getPropagateStatement_DestFaultList()
   * @model unique="false"
   * @generated
   */
  EList<String> getDestFaultList();

  /**
   * Returns the value of the '<em><b>Dest Comp path</b></em>' containment reference list.
   * The list contents are of type {@link com.rockwellcollins.atc.agree.agree.NestedDotID}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Dest Comp path</em>' containment reference list.
   * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getPropagateStatement_DestComp_path()
   * @model containment="true"
   * @generated
   */
  EList<NestedDotID> getDestComp_path();

} // PropagateStatement
