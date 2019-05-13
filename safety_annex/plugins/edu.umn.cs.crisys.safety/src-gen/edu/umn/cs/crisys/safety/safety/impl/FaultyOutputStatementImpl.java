/**
 */
package edu.umn.cs.crisys.safety.safety.impl;

import edu.umn.cs.crisys.safety.safety.FaultyOutputStatement;
import edu.umn.cs.crisys.safety.safety.SafetyPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Faulty Output Statement</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.impl.FaultyOutputStatementImpl#getFault_out <em>Fault out</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FaultyOutputStatementImpl extends ByzantineFaultSubcomponentImpl implements FaultyOutputStatement
{
  /**
   * The default value of the '{@link #getFault_out() <em>Fault out</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFault_out()
   * @generated
   * @ordered
   */
  protected static final String FAULT_OUT_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getFault_out() <em>Fault out</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFault_out()
   * @generated
   * @ordered
   */
  protected String fault_out = FAULT_OUT_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected FaultyOutputStatementImpl()
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
    return SafetyPackage.Literals.FAULTY_OUTPUT_STATEMENT;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getFault_out()
  {
    return fault_out;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setFault_out(String newFault_out)
  {
    String oldFault_out = fault_out;
    fault_out = newFault_out;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, SafetyPackage.FAULTY_OUTPUT_STATEMENT__FAULT_OUT, oldFault_out, fault_out));
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
      case SafetyPackage.FAULTY_OUTPUT_STATEMENT__FAULT_OUT:
        return getFault_out();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case SafetyPackage.FAULTY_OUTPUT_STATEMENT__FAULT_OUT:
        setFault_out((String)newValue);
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
      case SafetyPackage.FAULTY_OUTPUT_STATEMENT__FAULT_OUT:
        setFault_out(FAULT_OUT_EDEFAULT);
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
      case SafetyPackage.FAULTY_OUTPUT_STATEMENT__FAULT_OUT:
        return FAULT_OUT_EDEFAULT == null ? fault_out != null : !FAULT_OUT_EDEFAULT.equals(fault_out);
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

} //FaultyOutputStatementImpl
