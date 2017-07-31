/**
 */
package edu.umn.cs.crisys.safety.safety.impl;

import edu.umn.cs.crisys.safety.safety.Interval;
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
public class IntervalEqImpl extends SafetyEqStatementImpl implements IntervalEq
{
  /**
   * The default value of the '{@link #getLhs_int() <em>Lhs int</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLhs_int()
   * @generated
   * @ordered
   */
  protected static final String LHS_INT_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getLhs_int() <em>Lhs int</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLhs_int()
   * @generated
   * @ordered
   */
  protected String lhs_int = LHS_INT_EDEFAULT;

  /**
   * The cached value of the '{@link #getInterv() <em>Interv</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInterv()
   * @generated
   * @ordered
   */
  protected Interval interv;

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
  public String getLhs_int()
  {
    return lhs_int;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setLhs_int(String newLhs_int)
  {
    String oldLhs_int = lhs_int;
    lhs_int = newLhs_int;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, SafetyPackage.INTERVAL_EQ__LHS_INT, oldLhs_int, lhs_int));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Interval getInterv()
  {
    return interv;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetInterv(Interval newInterv, NotificationChain msgs)
  {
    Interval oldInterv = interv;
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
  public void setInterv(Interval newInterv)
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
        setLhs_int((String)newValue);
        return;
      case SafetyPackage.INTERVAL_EQ__INTERV:
        setInterv((Interval)newValue);
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
        setLhs_int(LHS_INT_EDEFAULT);
        return;
      case SafetyPackage.INTERVAL_EQ__INTERV:
        setInterv((Interval)null);
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
        return LHS_INT_EDEFAULT == null ? lhs_int != null : !LHS_INT_EDEFAULT.equals(lhs_int);
      case SafetyPackage.INTERVAL_EQ__INTERV:
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
    result.append(" (lhs_int: ");
    result.append(lhs_int);
    result.append(')');
    return result.toString();
  }

} //IntervalEqImpl
