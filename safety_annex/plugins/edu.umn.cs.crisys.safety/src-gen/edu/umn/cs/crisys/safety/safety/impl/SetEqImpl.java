/**
 */
package edu.umn.cs.crisys.safety.safety.impl;

import com.rockwellcollins.atc.agree.agree.Arg;
import com.rockwellcollins.atc.agree.agree.Expr;

import edu.umn.cs.crisys.safety.safety.SafetyPackage;
import edu.umn.cs.crisys.safety.safety.SetEq;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Set Eq</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.impl.SetEqImpl#getLhs_set <em>Lhs set</em>}</li>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.impl.SetEqImpl#getL1 <em>L1</em>}</li>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.impl.SetEqImpl#getList <em>List</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SetEqImpl extends SafetyEqStatementImpl implements SetEq
{
  /**
   * The cached value of the '{@link #getLhs_set() <em>Lhs set</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLhs_set()
   * @generated
   * @ordered
   */
  protected Arg lhs_set;

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
   * The cached value of the '{@link #getList() <em>List</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getList()
   * @generated
   * @ordered
   */
  protected EList<Expr> list;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected SetEqImpl()
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
    return SafetyPackage.Literals.SET_EQ;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Arg getLhs_set()
  {
    return lhs_set;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetLhs_set(Arg newLhs_set, NotificationChain msgs)
  {
    Arg oldLhs_set = lhs_set;
    lhs_set = newLhs_set;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SafetyPackage.SET_EQ__LHS_SET, oldLhs_set, newLhs_set);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setLhs_set(Arg newLhs_set)
  {
    if (newLhs_set != lhs_set)
    {
      NotificationChain msgs = null;
      if (lhs_set != null)
        msgs = ((InternalEObject)lhs_set).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SafetyPackage.SET_EQ__LHS_SET, null, msgs);
      if (newLhs_set != null)
        msgs = ((InternalEObject)newLhs_set).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SafetyPackage.SET_EQ__LHS_SET, null, msgs);
      msgs = basicSetLhs_set(newLhs_set, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, SafetyPackage.SET_EQ__LHS_SET, newLhs_set, newLhs_set));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
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
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SafetyPackage.SET_EQ__L1, oldL1, newL1);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setL1(Expr newL1)
  {
    if (newL1 != l1)
    {
      NotificationChain msgs = null;
      if (l1 != null)
        msgs = ((InternalEObject)l1).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SafetyPackage.SET_EQ__L1, null, msgs);
      if (newL1 != null)
        msgs = ((InternalEObject)newL1).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SafetyPackage.SET_EQ__L1, null, msgs);
      msgs = basicSetL1(newL1, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, SafetyPackage.SET_EQ__L1, newL1, newL1));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Expr> getList()
  {
    if (list == null)
    {
      list = new EObjectContainmentEList<Expr>(Expr.class, this, SafetyPackage.SET_EQ__LIST);
    }
    return list;
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
      case SafetyPackage.SET_EQ__LHS_SET:
        return basicSetLhs_set(null, msgs);
      case SafetyPackage.SET_EQ__L1:
        return basicSetL1(null, msgs);
      case SafetyPackage.SET_EQ__LIST:
        return ((InternalEList<?>)getList()).basicRemove(otherEnd, msgs);
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
      case SafetyPackage.SET_EQ__LHS_SET:
        return getLhs_set();
      case SafetyPackage.SET_EQ__L1:
        return getL1();
      case SafetyPackage.SET_EQ__LIST:
        return getList();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @SuppressWarnings("unchecked")
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case SafetyPackage.SET_EQ__LHS_SET:
        setLhs_set((Arg)newValue);
        return;
      case SafetyPackage.SET_EQ__L1:
        setL1((Expr)newValue);
        return;
      case SafetyPackage.SET_EQ__LIST:
        getList().clear();
        getList().addAll((Collection<? extends Expr>)newValue);
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
      case SafetyPackage.SET_EQ__LHS_SET:
        setLhs_set((Arg)null);
        return;
      case SafetyPackage.SET_EQ__L1:
        setL1((Expr)null);
        return;
      case SafetyPackage.SET_EQ__LIST:
        getList().clear();
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
      case SafetyPackage.SET_EQ__LHS_SET:
        return lhs_set != null;
      case SafetyPackage.SET_EQ__L1:
        return l1 != null;
      case SafetyPackage.SET_EQ__LIST:
        return list != null && !list.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //SetEqImpl
