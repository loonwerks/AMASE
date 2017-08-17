/**
 */
package edu.umn.cs.crisys.safety.safety.impl;

import edu.umn.cs.crisys.safety.safety.RangeEq;
import edu.umn.cs.crisys.safety.safety.SafetyPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

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
   * The default value of the '{@link #getLhs_range() <em>Lhs range</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLhs_range()
   * @generated
   * @ordered
   */
  protected static final String LHS_RANGE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getLhs_range() <em>Lhs range</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLhs_range()
   * @generated
   * @ordered
   */
  protected String lhs_range = LHS_RANGE_EDEFAULT;

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
   * The default value of the '{@link #getL2() <em>L2</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getL2()
   * @generated
   * @ordered
   */
  protected static final String L2_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getL2() <em>L2</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getL2()
   * @generated
   * @ordered
   */
  protected String l2 = L2_EDEFAULT;

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
  public String getLhs_range()
  {
    return lhs_range;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setLhs_range(String newLhs_range)
  {
    String oldLhs_range = lhs_range;
    lhs_range = newLhs_range;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, SafetyPackage.RANGE_EQ__LHS_RANGE, oldLhs_range, lhs_range));
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
      eNotify(new ENotificationImpl(this, Notification.SET, SafetyPackage.RANGE_EQ__L1, oldL1, l1));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getL2()
  {
    return l2;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setL2(String newL2)
  {
    String oldL2 = l2;
    l2 = newL2;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, SafetyPackage.RANGE_EQ__L2, oldL2, l2));
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
        setLhs_range((String)newValue);
        return;
      case SafetyPackage.RANGE_EQ__L1:
        setL1((String)newValue);
        return;
      case SafetyPackage.RANGE_EQ__L2:
        setL2((String)newValue);
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
        setLhs_range(LHS_RANGE_EDEFAULT);
        return;
      case SafetyPackage.RANGE_EQ__L1:
        setL1(L1_EDEFAULT);
        return;
      case SafetyPackage.RANGE_EQ__L2:
        setL2(L2_EDEFAULT);
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
        return LHS_RANGE_EDEFAULT == null ? lhs_range != null : !LHS_RANGE_EDEFAULT.equals(lhs_range);
      case SafetyPackage.RANGE_EQ__L1:
        return L1_EDEFAULT == null ? l1 != null : !L1_EDEFAULT.equals(l1);
      case SafetyPackage.RANGE_EQ__L2:
        return L2_EDEFAULT == null ? l2 != null : !L2_EDEFAULT.equals(l2);
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
    result.append(" (lhs_range: ");
    result.append(lhs_range);
    result.append(", l1: ");
    result.append(l1);
    result.append(", l2: ");
    result.append(l2);
    result.append(')');
    return result.toString();
  }

} //RangeEqImpl
