/**
 */
package edu.umn.cs.crisys.safety.safety.impl;

import com.rockwellcollins.atc.agree.agree.NestedDotID;

import edu.umn.cs.crisys.safety.safety.OutputStatement;
import edu.umn.cs.crisys.safety.safety.SafetyPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EDataTypeEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Output Statement</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.impl.OutputStatementImpl#getNom_conn <em>Nom conn</em>}</li>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.impl.OutputStatementImpl#getFault_out <em>Fault out</em>}</li>
 * </ul>
 *
 * @generated
 */
public class OutputStatementImpl extends FaultSubcomponentImpl implements OutputStatement
{
  /**
   * The cached value of the '{@link #getNom_conn() <em>Nom conn</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getNom_conn()
   * @generated
   * @ordered
   */
  protected EList<NestedDotID> nom_conn;

  /**
   * The cached value of the '{@link #getFault_out() <em>Fault out</em>}' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFault_out()
   * @generated
   * @ordered
   */
  protected EList<String> fault_out;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected OutputStatementImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass()
  {
    return SafetyPackage.Literals.OUTPUT_STATEMENT;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<NestedDotID> getNom_conn()
  {
    if (nom_conn == null)
    {
      nom_conn = new EObjectContainmentEList<NestedDotID>(NestedDotID.class, this, SafetyPackage.OUTPUT_STATEMENT__NOM_CONN);
    }
    return nom_conn;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<String> getFault_out()
  {
    if (fault_out == null)
    {
      fault_out = new EDataTypeEList<String>(String.class, this, SafetyPackage.OUTPUT_STATEMENT__FAULT_OUT);
    }
    return fault_out;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
  {
    switch (featureID)
    {
      case SafetyPackage.OUTPUT_STATEMENT__NOM_CONN:
        return ((InternalEList<?>)getNom_conn()).basicRemove(otherEnd, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
    switch (featureID)
    {
      case SafetyPackage.OUTPUT_STATEMENT__NOM_CONN:
        return getNom_conn();
      case SafetyPackage.OUTPUT_STATEMENT__FAULT_OUT:
        return getFault_out();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @SuppressWarnings("unchecked")
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case SafetyPackage.OUTPUT_STATEMENT__NOM_CONN:
        getNom_conn().clear();
        getNom_conn().addAll((Collection<? extends NestedDotID>)newValue);
        return;
      case SafetyPackage.OUTPUT_STATEMENT__FAULT_OUT:
        getFault_out().clear();
        getFault_out().addAll((Collection<? extends String>)newValue);
        return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eUnset(int featureID)
  {
    switch (featureID)
    {
      case SafetyPackage.OUTPUT_STATEMENT__NOM_CONN:
        getNom_conn().clear();
        return;
      case SafetyPackage.OUTPUT_STATEMENT__FAULT_OUT:
        getFault_out().clear();
        return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID)
  {
    switch (featureID)
    {
      case SafetyPackage.OUTPUT_STATEMENT__NOM_CONN:
        return nom_conn != null && !nom_conn.isEmpty();
      case SafetyPackage.OUTPUT_STATEMENT__FAULT_OUT:
        return fault_out != null && !fault_out.isEmpty();
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString()
  {
    if (eIsProxy()) return super.toString();

    StringBuilder result = new StringBuilder(super.toString());
    result.append(" (fault_out: ");
    result.append(fault_out);
    result.append(')');
    return result.toString();
  }

} //OutputStatementImpl
