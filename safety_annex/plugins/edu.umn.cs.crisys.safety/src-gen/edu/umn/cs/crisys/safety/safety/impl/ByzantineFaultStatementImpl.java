/**
 */
package edu.umn.cs.crisys.safety.safety.impl;

import edu.umn.cs.crisys.safety.safety.ByzantineFaultStatement;
import edu.umn.cs.crisys.safety.safety.ByzantineFaultSubcomponent;
import edu.umn.cs.crisys.safety.safety.SafetyPackage;

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
 * An implementation of the model object '<em><b>Byzantine Fault Statement</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.impl.ByzantineFaultStatementImpl#getName <em>Name</em>}</li>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.impl.ByzantineFaultStatementImpl#getStr <em>Str</em>}</li>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.impl.ByzantineFaultStatementImpl#getByzantine <em>Byzantine</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ByzantineFaultStatementImpl extends SpecStatementImpl implements ByzantineFaultStatement
{
  /**
   * The default value of the '{@link #getName() <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getName()
   * @generated
   * @ordered
   */
  protected static final String NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getName()
   * @generated
   * @ordered
   */
  protected String name = NAME_EDEFAULT;

  /**
   * The default value of the '{@link #getStr() <em>Str</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getStr()
   * @generated
   * @ordered
   */
  protected static final String STR_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getStr() <em>Str</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getStr()
   * @generated
   * @ordered
   */
  protected String str = STR_EDEFAULT;

  /**
   * The cached value of the '{@link #getByzantine() <em>Byzantine</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getByzantine()
   * @generated
   * @ordered
   */
  protected EList<ByzantineFaultSubcomponent> byzantine;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ByzantineFaultStatementImpl()
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
    return SafetyPackage.Literals.BYZANTINE_FAULT_STATEMENT;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getName()
  {
    return name;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setName(String newName)
  {
    String oldName = name;
    name = newName;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, SafetyPackage.BYZANTINE_FAULT_STATEMENT__NAME, oldName, name));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getStr()
  {
    return str;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setStr(String newStr)
  {
    String oldStr = str;
    str = newStr;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, SafetyPackage.BYZANTINE_FAULT_STATEMENT__STR, oldStr, str));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<ByzantineFaultSubcomponent> getByzantine()
  {
    if (byzantine == null)
    {
      byzantine = new EObjectContainmentEList<ByzantineFaultSubcomponent>(ByzantineFaultSubcomponent.class, this, SafetyPackage.BYZANTINE_FAULT_STATEMENT__BYZANTINE);
    }
    return byzantine;
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
      case SafetyPackage.BYZANTINE_FAULT_STATEMENT__BYZANTINE:
        return ((InternalEList<?>)getByzantine()).basicRemove(otherEnd, msgs);
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
      case SafetyPackage.BYZANTINE_FAULT_STATEMENT__NAME:
        return getName();
      case SafetyPackage.BYZANTINE_FAULT_STATEMENT__STR:
        return getStr();
      case SafetyPackage.BYZANTINE_FAULT_STATEMENT__BYZANTINE:
        return getByzantine();
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
      case SafetyPackage.BYZANTINE_FAULT_STATEMENT__NAME:
        setName((String)newValue);
        return;
      case SafetyPackage.BYZANTINE_FAULT_STATEMENT__STR:
        setStr((String)newValue);
        return;
      case SafetyPackage.BYZANTINE_FAULT_STATEMENT__BYZANTINE:
        getByzantine().clear();
        getByzantine().addAll((Collection<? extends ByzantineFaultSubcomponent>)newValue);
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
      case SafetyPackage.BYZANTINE_FAULT_STATEMENT__NAME:
        setName(NAME_EDEFAULT);
        return;
      case SafetyPackage.BYZANTINE_FAULT_STATEMENT__STR:
        setStr(STR_EDEFAULT);
        return;
      case SafetyPackage.BYZANTINE_FAULT_STATEMENT__BYZANTINE:
        getByzantine().clear();
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
      case SafetyPackage.BYZANTINE_FAULT_STATEMENT__NAME:
        return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
      case SafetyPackage.BYZANTINE_FAULT_STATEMENT__STR:
        return STR_EDEFAULT == null ? str != null : !STR_EDEFAULT.equals(str);
      case SafetyPackage.BYZANTINE_FAULT_STATEMENT__BYZANTINE:
        return byzantine != null && !byzantine.isEmpty();
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

    StringBuilder result = new StringBuilder(super.toString());
    result.append(" (name: ");
    result.append(name);
    result.append(", str: ");
    result.append(str);
    result.append(')');
    return result.toString();
  }

} //ByzantineFaultStatementImpl
