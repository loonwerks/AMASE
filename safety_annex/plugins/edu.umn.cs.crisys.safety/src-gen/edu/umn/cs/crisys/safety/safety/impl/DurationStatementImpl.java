/**
 */
package edu.umn.cs.crisys.safety.safety.impl;

import com.rockwellcollins.atc.agree.agree.TimeInterval;

import edu.umn.cs.crisys.safety.safety.DurationStatement;
import edu.umn.cs.crisys.safety.safety.SafetyPackage;
import edu.umn.cs.crisys.safety.safety.TemporalConstraint;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Duration Statement</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.impl.DurationStatementImpl#getTc <em>Tc</em>}</li>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.impl.DurationStatementImpl#getInterv <em>Interv</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DurationStatementImpl extends FaultSubcomponentImpl implements DurationStatement
{
  /**
   * The cached value of the '{@link #getTc() <em>Tc</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTc()
   * @generated
   * @ordered
   */
  protected TemporalConstraint tc;

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
  public TemporalConstraint getTc()
  {
    return tc;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetTc(TemporalConstraint newTc, NotificationChain msgs)
  {
    TemporalConstraint oldTc = tc;
    tc = newTc;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SafetyPackage.DURATION_STATEMENT__TC, oldTc, newTc);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setTc(TemporalConstraint newTc)
  {
    if (newTc != tc)
    {
      NotificationChain msgs = null;
      if (tc != null)
        msgs = ((InternalEObject)tc).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SafetyPackage.DURATION_STATEMENT__TC, null, msgs);
      if (newTc != null)
        msgs = ((InternalEObject)newTc).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SafetyPackage.DURATION_STATEMENT__TC, null, msgs);
      msgs = basicSetTc(newTc, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, SafetyPackage.DURATION_STATEMENT__TC, newTc, newTc));
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
      case SafetyPackage.DURATION_STATEMENT__TC:
        return basicSetTc(null, msgs);
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
        setTc((TemporalConstraint)newValue);
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
        setTc((TemporalConstraint)null);
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
        return tc != null;
      case SafetyPackage.DURATION_STATEMENT__INTERV:
        return interv != null;
    }
    return super.eIsSet(featureID);
  }

} //DurationStatementImpl
