/**
 */
package edu.umn.cs.crisys.safety.safety.impl;

import edu.umn.cs.crisys.safety.safety.AnalysisStatement;
import edu.umn.cs.crisys.safety.safety.SafetyPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Analysis Statement</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.impl.AnalysisStatementImpl#getMaxFaults <em>Max Faults</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AnalysisStatementImpl extends SpecStatementImpl implements AnalysisStatement
{
  /**
   * The default value of the '{@link #getMaxFaults() <em>Max Faults</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMaxFaults()
   * @generated
   * @ordered
   */
  protected static final String MAX_FAULTS_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getMaxFaults() <em>Max Faults</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMaxFaults()
   * @generated
   * @ordered
   */
  protected String maxFaults = MAX_FAULTS_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected AnalysisStatementImpl()
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
    return SafetyPackage.Literals.ANALYSIS_STATEMENT;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getMaxFaults()
  {
    return maxFaults;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setMaxFaults(String newMaxFaults)
  {
    String oldMaxFaults = maxFaults;
    maxFaults = newMaxFaults;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, SafetyPackage.ANALYSIS_STATEMENT__MAX_FAULTS, oldMaxFaults, maxFaults));
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
      case SafetyPackage.ANALYSIS_STATEMENT__MAX_FAULTS:
        return getMaxFaults();
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
      case SafetyPackage.ANALYSIS_STATEMENT__MAX_FAULTS:
        setMaxFaults((String)newValue);
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
      case SafetyPackage.ANALYSIS_STATEMENT__MAX_FAULTS:
        setMaxFaults(MAX_FAULTS_EDEFAULT);
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
      case SafetyPackage.ANALYSIS_STATEMENT__MAX_FAULTS:
        return MAX_FAULTS_EDEFAULT == null ? maxFaults != null : !MAX_FAULTS_EDEFAULT.equals(maxFaults);
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

    StringBuffer result = new StringBuffer(super.toString());
    result.append(" (maxFaults: ");
    result.append(maxFaults);
    result.append(')');
    return result.toString();
  }

} //AnalysisStatementImpl
