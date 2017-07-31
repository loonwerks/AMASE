/**
 */
package edu.umn.cs.crisys.safety.safety.impl;

import edu.umn.cs.crisys.safety.safety.SafetyPackage;
import edu.umn.cs.crisys.safety.safety.SetEq;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EDataTypeEList;

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
   * The default value of the '{@link #getLhs_set() <em>Lhs set</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLhs_set()
   * @generated
   * @ordered
   */
  protected static final String LHS_SET_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getLhs_set() <em>Lhs set</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLhs_set()
   * @generated
   * @ordered
   */
  protected String lhs_set = LHS_SET_EDEFAULT;

  /**
   * The default value of the '{@link #getL1() <em>L1</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getL1()
   * @generated
   * @ordered
   */
  protected static final String L1_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getL1() <em>L1</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getL1()
   * @generated
   * @ordered
   */
  protected String l1 = L1_EDEFAULT;

  /**
   * The cached value of the '{@link #getList() <em>List</em>}' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getList()
   * @generated
   * @ordered
   */
  protected EList<String> list;

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
  public String getLhs_set()
  {
    return lhs_set;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setLhs_set(String newLhs_set)
  {
    String oldLhs_set = lhs_set;
    lhs_set = newLhs_set;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, SafetyPackage.SET_EQ__LHS_SET, oldLhs_set, lhs_set));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getL1()
  {
    return l1;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setL1(String newL1)
  {
    String oldL1 = l1;
    l1 = newL1;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, SafetyPackage.SET_EQ__L1, oldL1, l1));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<String> getList()
  {
    if (list == null)
    {
      list = new EDataTypeEList<String>(String.class, this, SafetyPackage.SET_EQ__LIST);
    }
    return list;
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
        setLhs_set((String)newValue);
        return;
      case SafetyPackage.SET_EQ__L1:
        setL1((String)newValue);
        return;
      case SafetyPackage.SET_EQ__LIST:
        getList().clear();
        getList().addAll((Collection<? extends String>)newValue);
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
        setLhs_set(LHS_SET_EDEFAULT);
        return;
      case SafetyPackage.SET_EQ__L1:
        setL1(L1_EDEFAULT);
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
        return LHS_SET_EDEFAULT == null ? lhs_set != null : !LHS_SET_EDEFAULT.equals(lhs_set);
      case SafetyPackage.SET_EQ__L1:
        return L1_EDEFAULT == null ? l1 != null : !L1_EDEFAULT.equals(l1);
      case SafetyPackage.SET_EQ__LIST:
        return list != null && !list.isEmpty();
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
    result.append(" (lhs_set: ");
    result.append(lhs_set);
    result.append(", l1: ");
    result.append(l1);
    result.append(", list: ");
    result.append(list);
    result.append(')');
    return result.toString();
  }

} //SetEqImpl
