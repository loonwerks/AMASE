/**
 */
package edu.umn.cs.crisys.safety.safety.impl;

import edu.umn.cs.crisys.safety.safety.ProbabilityBehavior;
import edu.umn.cs.crisys.safety.safety.SafetyPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Probability Behavior</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.impl.ProbabilityBehaviorImpl#getProbabilty <em>Probabilty</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ProbabilityBehaviorImpl extends AnalysisBehaviorImpl implements ProbabilityBehavior
{
  /**
   * The default value of the '{@link #getProbabilty() <em>Probabilty</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getProbabilty()
   * @generated
   * @ordered
   */
  protected static final String PROBABILTY_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getProbabilty() <em>Probabilty</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getProbabilty()
   * @generated
   * @ordered
   */
  protected String probabilty = PROBABILTY_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ProbabilityBehaviorImpl()
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
    return SafetyPackage.Literals.PROBABILITY_BEHAVIOR;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getProbabilty()
  {
    return probabilty;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setProbabilty(String newProbabilty)
  {
    String oldProbabilty = probabilty;
    probabilty = newProbabilty;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, SafetyPackage.PROBABILITY_BEHAVIOR__PROBABILTY, oldProbabilty, probabilty));
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
      case SafetyPackage.PROBABILITY_BEHAVIOR__PROBABILTY:
        return getProbabilty();
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
      case SafetyPackage.PROBABILITY_BEHAVIOR__PROBABILTY:
        setProbabilty((String)newValue);
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
      case SafetyPackage.PROBABILITY_BEHAVIOR__PROBABILTY:
        setProbabilty(PROBABILTY_EDEFAULT);
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
      case SafetyPackage.PROBABILITY_BEHAVIOR__PROBABILTY:
        return PROBABILTY_EDEFAULT == null ? probabilty != null : !PROBABILTY_EDEFAULT.equals(probabilty);
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
    result.append(" (probabilty: ");
    result.append(probabilty);
    result.append(')');
    return result.toString();
  }

} //ProbabilityBehaviorImpl
