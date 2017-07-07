/**
 */
package edu.umn.cs.crisys.safety.safety.impl;

import com.rockwellcollins.atc.agree.agree.Arg;
import com.rockwellcollins.atc.agree.agree.TimeInterval;

import edu.umn.cs.crisys.safety.safety.IntervalEq;
import edu.umn.cs.crisys.safety.safety.SafetyPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Interval Eq</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.impl.IntervalEqImpl#getLhs_int <em>Lhs int</em>}</li>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.impl.IntervalEqImpl#getInterv <em>Interv</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IntervalEqImpl extends EqStatementImpl implements IntervalEq
{
  /**
   * The cached value of the '{@link #getLhs_int() <em>Lhs int</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLhs_int()
   * @generated
   * @ordered
   */
  protected Arg lhs_int;

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
  protected IntervalEqImpl()
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
    return SafetyPackage.Literals.INTERVAL_EQ;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Arg getLhs_int()
  {
    return lhs_int;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetLhs_int(Arg newLhs_int, NotificationChain msgs)
  {
    Arg oldLhs_int = lhs_int;
    lhs_int = newLhs_int;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SafetyPackage.INTERVAL_EQ__LHS_INT, oldLhs_int, newLhs_int);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setLhs_int(Arg newLhs_int)
  {
    if (newLhs_int != lhs_int)
    {
      NotificationChain msgs = null;
      if (lhs_int != null)
        msgs = ((InternalEObject)lhs_int).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SafetyPackage.INTERVAL_EQ__LHS_INT, null, msgs);
      if (newLhs_int != null)
        msgs = ((InternalEObject)newLhs_int).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SafetyPackage.INTERVAL_EQ__LHS_INT, null, msgs);
      msgs = basicSetLhs_int(newLhs_int, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, SafetyPackage.INTERVAL_EQ__LHS_INT, newLhs_int, newLhs_int));
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
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SafetyPackage.INTERVAL_EQ__INTERV, oldInterv, newInterv);
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
        msgs = ((InternalEObject)interv).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SafetyPackage.INTERVAL_EQ__INTERV, null, msgs);
      if (newInterv != null)
        msgs = ((InternalEObject)newInterv).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SafetyPackage.INTERVAL_EQ__INTERV, null, msgs);
      msgs = basicSetInterv(newInterv, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, SafetyPackage.INTERVAL_EQ__INTERV, newInterv, newInterv));
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
      case SafetyPackage.INTERVAL_EQ__LHS_INT:
        return basicSetLhs_int(null, msgs);
      case SafetyPackage.INTERVAL_EQ__INTERV:
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
      case SafetyPackage.INTERVAL_EQ__LHS_INT:
        return getLhs_int();
      case SafetyPackage.INTERVAL_EQ__INTERV:
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
      case SafetyPackage.INTERVAL_EQ__LHS_INT:
        setLhs_int((Arg)newValue);
        return;
      case SafetyPackage.INTERVAL_EQ__INTERV:
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
      case SafetyPackage.INTERVAL_EQ__LHS_INT:
        setLhs_int((Arg)null);
        return;
      case SafetyPackage.INTERVAL_EQ__INTERV:
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
      case SafetyPackage.INTERVAL_EQ__LHS_INT:
        return lhs_int != null;
      case SafetyPackage.INTERVAL_EQ__INTERV:
        return interv != null;
    }
    return super.eIsSet(featureID);
  }

} //IntervalEqImpl
