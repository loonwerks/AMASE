/**
 */
package edu.umn.cs.crisys.safety.safety.impl;

import com.rockwellcollins.atc.agree.agree.Arg;
import com.rockwellcollins.atc.agree.agree.Expr;

import edu.umn.cs.crisys.safety.safety.RangeEq;
import edu.umn.cs.crisys.safety.safety.SafetyPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Range Eq</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.impl.RangeEqImpl#getLhs_range <em>Lhs range</em>}</li>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.impl.RangeEqImpl#getL1 <em>L1</em>}</li>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.impl.RangeEqImpl#getL2 <em>L2</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RangeEqImpl extends SafetyEqStatementImpl implements RangeEq
{
  /**
   * The cached value of the '{@link #getLhs_range() <em>Lhs range</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLhs_range()
   * @generated
   * @ordered
   */
  protected Arg lhs_range;

  /**
   * The cached value of the '{@link #getL1() <em>L1</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getL1()
   * @generated
   * @ordered
   */
  protected Expr l1;

  /**
   * The cached value of the '{@link #getL2() <em>L2</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getL2()
   * @generated
   * @ordered
   */
  protected Expr l2;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected RangeEqImpl()
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
    return SafetyPackage.Literals.RANGE_EQ;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Arg getLhs_range()
  {
    return lhs_range;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetLhs_range(Arg newLhs_range, NotificationChain msgs)
  {
    Arg oldLhs_range = lhs_range;
    lhs_range = newLhs_range;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SafetyPackage.RANGE_EQ__LHS_RANGE, oldLhs_range, newLhs_range);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setLhs_range(Arg newLhs_range)
  {
    if (newLhs_range != lhs_range)
    {
      NotificationChain msgs = null;
      if (lhs_range != null)
        msgs = ((InternalEObject)lhs_range).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SafetyPackage.RANGE_EQ__LHS_RANGE, null, msgs);
      if (newLhs_range != null)
        msgs = ((InternalEObject)newLhs_range).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SafetyPackage.RANGE_EQ__LHS_RANGE, null, msgs);
      msgs = basicSetLhs_range(newLhs_range, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, SafetyPackage.RANGE_EQ__LHS_RANGE, newLhs_range, newLhs_range));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Expr getL1()
  {
    return l1;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetL1(Expr newL1, NotificationChain msgs)
  {
    Expr oldL1 = l1;
    l1 = newL1;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SafetyPackage.RANGE_EQ__L1, oldL1, newL1);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setL1(Expr newL1)
  {
    if (newL1 != l1)
    {
      NotificationChain msgs = null;
      if (l1 != null)
        msgs = ((InternalEObject)l1).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SafetyPackage.RANGE_EQ__L1, null, msgs);
      if (newL1 != null)
        msgs = ((InternalEObject)newL1).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SafetyPackage.RANGE_EQ__L1, null, msgs);
      msgs = basicSetL1(newL1, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, SafetyPackage.RANGE_EQ__L1, newL1, newL1));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Expr getL2()
  {
    return l2;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetL2(Expr newL2, NotificationChain msgs)
  {
    Expr oldL2 = l2;
    l2 = newL2;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SafetyPackage.RANGE_EQ__L2, oldL2, newL2);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setL2(Expr newL2)
  {
    if (newL2 != l2)
    {
      NotificationChain msgs = null;
      if (l2 != null)
        msgs = ((InternalEObject)l2).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SafetyPackage.RANGE_EQ__L2, null, msgs);
      if (newL2 != null)
        msgs = ((InternalEObject)newL2).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SafetyPackage.RANGE_EQ__L2, null, msgs);
      msgs = basicSetL2(newL2, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, SafetyPackage.RANGE_EQ__L2, newL2, newL2));
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
      case SafetyPackage.RANGE_EQ__LHS_RANGE:
        return basicSetLhs_range(null, msgs);
      case SafetyPackage.RANGE_EQ__L1:
        return basicSetL1(null, msgs);
      case SafetyPackage.RANGE_EQ__L2:
        return basicSetL2(null, msgs);
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
      case SafetyPackage.RANGE_EQ__LHS_RANGE:
        return getLhs_range();
      case SafetyPackage.RANGE_EQ__L1:
        return getL1();
      case SafetyPackage.RANGE_EQ__L2:
        return getL2();
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
      case SafetyPackage.RANGE_EQ__LHS_RANGE:
        setLhs_range((Arg)newValue);
        return;
      case SafetyPackage.RANGE_EQ__L1:
        setL1((Expr)newValue);
        return;
      case SafetyPackage.RANGE_EQ__L2:
        setL2((Expr)newValue);
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
      case SafetyPackage.RANGE_EQ__LHS_RANGE:
        setLhs_range((Arg)null);
        return;
      case SafetyPackage.RANGE_EQ__L1:
        setL1((Expr)null);
        return;
      case SafetyPackage.RANGE_EQ__L2:
        setL2((Expr)null);
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
      case SafetyPackage.RANGE_EQ__LHS_RANGE:
        return lhs_range != null;
      case SafetyPackage.RANGE_EQ__L1:
        return l1 != null;
      case SafetyPackage.RANGE_EQ__L2:
        return l2 != null;
    }
    return super.eIsSet(featureID);
  }

} //RangeEqImpl
