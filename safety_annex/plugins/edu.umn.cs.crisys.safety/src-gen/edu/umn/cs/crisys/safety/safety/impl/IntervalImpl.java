/**
 */
package edu.umn.cs.crisys.safety.safety.impl;

import com.rockwellcollins.atc.agree.agree.Expr;

import edu.umn.cs.crisys.safety.safety.Interval;
import edu.umn.cs.crisys.safety.safety.SafetyPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Interval</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.impl.IntervalImpl#getLow <em>Low</em>}</li>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.impl.IntervalImpl#getHigh <em>High</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IntervalImpl extends MinimalEObjectImpl.Container implements Interval
{
  /**
   * The cached value of the '{@link #getLow() <em>Low</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLow()
   * @generated
   * @ordered
   */
  protected Expr low;

  /**
   * The cached value of the '{@link #getHigh() <em>High</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getHigh()
   * @generated
   * @ordered
   */
  protected Expr high;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected IntervalImpl()
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
    return SafetyPackage.Literals.INTERVAL;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Expr getLow()
  {
    return low;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetLow(Expr newLow, NotificationChain msgs)
  {
    Expr oldLow = low;
    low = newLow;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SafetyPackage.INTERVAL__LOW, oldLow, newLow);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setLow(Expr newLow)
  {
    if (newLow != low)
    {
      NotificationChain msgs = null;
      if (low != null)
        msgs = ((InternalEObject)low).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SafetyPackage.INTERVAL__LOW, null, msgs);
      if (newLow != null)
        msgs = ((InternalEObject)newLow).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SafetyPackage.INTERVAL__LOW, null, msgs);
      msgs = basicSetLow(newLow, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, SafetyPackage.INTERVAL__LOW, newLow, newLow));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Expr getHigh()
  {
    return high;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetHigh(Expr newHigh, NotificationChain msgs)
  {
    Expr oldHigh = high;
    high = newHigh;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SafetyPackage.INTERVAL__HIGH, oldHigh, newHigh);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setHigh(Expr newHigh)
  {
    if (newHigh != high)
    {
      NotificationChain msgs = null;
      if (high != null)
        msgs = ((InternalEObject)high).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SafetyPackage.INTERVAL__HIGH, null, msgs);
      if (newHigh != null)
        msgs = ((InternalEObject)newHigh).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SafetyPackage.INTERVAL__HIGH, null, msgs);
      msgs = basicSetHigh(newHigh, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, SafetyPackage.INTERVAL__HIGH, newHigh, newHigh));
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
      case SafetyPackage.INTERVAL__LOW:
        return basicSetLow(null, msgs);
      case SafetyPackage.INTERVAL__HIGH:
        return basicSetHigh(null, msgs);
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
      case SafetyPackage.INTERVAL__LOW:
        return getLow();
      case SafetyPackage.INTERVAL__HIGH:
        return getHigh();
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
      case SafetyPackage.INTERVAL__LOW:
        setLow((Expr)newValue);
        return;
      case SafetyPackage.INTERVAL__HIGH:
        setHigh((Expr)newValue);
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
      case SafetyPackage.INTERVAL__LOW:
        setLow((Expr)null);
        return;
      case SafetyPackage.INTERVAL__HIGH:
        setHigh((Expr)null);
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
      case SafetyPackage.INTERVAL__LOW:
        return low != null;
      case SafetyPackage.INTERVAL__HIGH:
        return high != null;
    }
    return super.eIsSet(featureID);
  }

} //IntervalImpl
