/**
 */
package edu.umn.cs.crisys.safety.safety.impl;

import edu.umn.cs.crisys.safety.safety.SafetyPackage;
import edu.umn.cs.crisys.safety.safety.TriggerCondition;
import edu.umn.cs.crisys.safety.safety.TriggerStatement;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Trigger Statement</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.impl.TriggerStatementImpl#getCond <em>Cond</em>}</li>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.impl.TriggerStatementImpl#getProbability <em>Probability</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TriggerStatementImpl extends FaultSubcomponentImpl implements TriggerStatement
{
  /**
   * The cached value of the '{@link #getCond() <em>Cond</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCond()
   * @generated
   * @ordered
   */
  protected TriggerCondition cond;

  /**
   * The default value of the '{@link #getProbability() <em>Probability</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getProbability()
   * @generated
   * @ordered
   */
  protected static final String PROBABILITY_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getProbability() <em>Probability</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getProbability()
   * @generated
   * @ordered
   */
  protected String probability = PROBABILITY_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected TriggerStatementImpl()
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
    return SafetyPackage.Literals.TRIGGER_STATEMENT;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public TriggerCondition getCond()
  {
    return cond;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetCond(TriggerCondition newCond, NotificationChain msgs)
  {
    TriggerCondition oldCond = cond;
    cond = newCond;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SafetyPackage.TRIGGER_STATEMENT__COND, oldCond, newCond);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setCond(TriggerCondition newCond)
  {
    if (newCond != cond)
    {
      NotificationChain msgs = null;
      if (cond != null)
        msgs = ((InternalEObject)cond).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SafetyPackage.TRIGGER_STATEMENT__COND, null, msgs);
      if (newCond != null)
        msgs = ((InternalEObject)newCond).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SafetyPackage.TRIGGER_STATEMENT__COND, null, msgs);
      msgs = basicSetCond(newCond, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, SafetyPackage.TRIGGER_STATEMENT__COND, newCond, newCond));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getProbability()
  {
    return probability;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setProbability(String newProbability)
  {
    String oldProbability = probability;
    probability = newProbability;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, SafetyPackage.TRIGGER_STATEMENT__PROBABILITY, oldProbability, probability));
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
      case SafetyPackage.TRIGGER_STATEMENT__COND:
        return basicSetCond(null, msgs);
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
      case SafetyPackage.TRIGGER_STATEMENT__COND:
        return getCond();
      case SafetyPackage.TRIGGER_STATEMENT__PROBABILITY:
        return getProbability();
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
      case SafetyPackage.TRIGGER_STATEMENT__COND:
        setCond((TriggerCondition)newValue);
        return;
      case SafetyPackage.TRIGGER_STATEMENT__PROBABILITY:
        setProbability((String)newValue);
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
      case SafetyPackage.TRIGGER_STATEMENT__COND:
        setCond((TriggerCondition)null);
        return;
      case SafetyPackage.TRIGGER_STATEMENT__PROBABILITY:
        setProbability(PROBABILITY_EDEFAULT);
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
      case SafetyPackage.TRIGGER_STATEMENT__COND:
        return cond != null;
      case SafetyPackage.TRIGGER_STATEMENT__PROBABILITY:
        return PROBABILITY_EDEFAULT == null ? probability != null : !PROBABILITY_EDEFAULT.equals(probability);
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
    result.append(" (probability: ");
    result.append(probability);
    result.append(')');
    return result.toString();
  }

} //TriggerStatementImpl
