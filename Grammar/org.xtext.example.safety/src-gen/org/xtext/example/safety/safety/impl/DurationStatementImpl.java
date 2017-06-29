/**
 */
package org.xtext.example.safety.safety.impl;

import com.rockwellcollins.atc.agree.agree.TimeInterval;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.xtext.example.safety.safety.DurationStatement;
import org.xtext.example.safety.safety.SafetyPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Duration Statement</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.xtext.example.safety.safety.impl.DurationStatementImpl#getTc <em>Tc</em>}</li>
 *   <li>{@link org.xtext.example.safety.safety.impl.DurationStatementImpl#getInterv <em>Interv</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DurationStatementImpl extends FaultSubcomponentImpl implements DurationStatement
{
  /**
   * The default value of the '{@link #getTc() <em>Tc</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTc()
   * @generated
   * @ordered
   */
  protected static final String TC_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getTc() <em>Tc</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTc()
   * @generated
   * @ordered
   */
  protected String tc = TC_EDEFAULT;

  /**
   * The cached value of the '{@link #getInterv() <em>Interv</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInterv()
   * @generated
   * @ordered
   */
  protected TimeInterval interv;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected DurationStatementImpl()
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
    return SafetyPackage.Literals.DURATION_STATEMENT;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getTc()
  {
    return tc;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setTc(String newTc)
  {
    String oldTc = tc;
    tc = newTc;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, SafetyPackage.DURATION_STATEMENT__TC, oldTc, tc));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public TimeInterval getInterv()
  {
    return interv;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetInterv(TimeInterval newInterv, NotificationChain msgs)
  {
    TimeInterval oldInterv = interv;
    interv = newInterv;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SafetyPackage.DURATION_STATEMENT__INTERV, oldInterv, newInterv);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setInterv(TimeInterval newInterv)
  {
    if (newInterv != interv)
    {
      NotificationChain msgs = null;
      if (interv != null)
        msgs = ((InternalEObject)interv).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SafetyPackage.DURATION_STATEMENT__INTERV, null, msgs);
      if (newInterv != null)
        msgs = ((InternalEObject)newInterv).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SafetyPackage.DURATION_STATEMENT__INTERV, null, msgs);
      msgs = basicSetInterv(newInterv, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, SafetyPackage.DURATION_STATEMENT__INTERV, newInterv, newInterv));
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
      case SafetyPackage.DURATION_STATEMENT__INTERV:
        return basicSetInterv(null, msgs);
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
      case SafetyPackage.DURATION_STATEMENT__TC:
        return getTc();
      case SafetyPackage.DURATION_STATEMENT__INTERV:
        return getInterv();
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
      case SafetyPackage.DURATION_STATEMENT__TC:
        setTc((String)newValue);
        return;
      case SafetyPackage.DURATION_STATEMENT__INTERV:
        setInterv((TimeInterval)newValue);
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
      case SafetyPackage.DURATION_STATEMENT__TC:
        setTc(TC_EDEFAULT);
        return;
      case SafetyPackage.DURATION_STATEMENT__INTERV:
        setInterv((TimeInterval)null);
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
      case SafetyPackage.DURATION_STATEMENT__TC:
        return TC_EDEFAULT == null ? tc != null : !TC_EDEFAULT.equals(tc);
      case SafetyPackage.DURATION_STATEMENT__INTERV:
        return interv != null;
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
    result.append(" (tc: ");
    result.append(tc);
    result.append(')');
    return result.toString();
  }

} //DurationStatementImpl
