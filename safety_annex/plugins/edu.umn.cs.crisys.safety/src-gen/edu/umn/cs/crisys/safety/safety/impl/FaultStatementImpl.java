/**
 */
package edu.umn.cs.crisys.safety.safety.impl;

import com.rockwellcollins.atc.agree.agree.DoubleDotRef;

import edu.umn.cs.crisys.safety.safety.FaultStatement;
import edu.umn.cs.crisys.safety.safety.FaultSubcomponent;
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
 * An implementation of the model object '<em><b>Fault Statement</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.impl.FaultStatementImpl#getName <em>Name</em>}</li>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.impl.FaultStatementImpl#getStr <em>Str</em>}</li>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.impl.FaultStatementImpl#getFaultDefName <em>Fault Def Name</em>}</li>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.impl.FaultStatementImpl#getFaultDefinitions <em>Fault Definitions</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FaultStatementImpl extends SpecStatementImpl implements FaultStatement
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
   * The cached value of the '{@link #getFaultDefName() <em>Fault Def Name</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFaultDefName()
   * @generated
   * @ordered
   */
  protected DoubleDotRef faultDefName;

  /**
   * The cached value of the '{@link #getFaultDefinitions() <em>Fault Definitions</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFaultDefinitions()
   * @generated
   * @ordered
   */
  protected EList<FaultSubcomponent> faultDefinitions;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected FaultStatementImpl()
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
    return SafetyPackage.Literals.FAULT_STATEMENT;
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
      eNotify(new ENotificationImpl(this, Notification.SET, SafetyPackage.FAULT_STATEMENT__NAME, oldName, name));
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
      eNotify(new ENotificationImpl(this, Notification.SET, SafetyPackage.FAULT_STATEMENT__STR, oldStr, str));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public DoubleDotRef getFaultDefName()
  {
    return faultDefName;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetFaultDefName(DoubleDotRef newFaultDefName, NotificationChain msgs)
  {
    DoubleDotRef oldFaultDefName = faultDefName;
    faultDefName = newFaultDefName;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SafetyPackage.FAULT_STATEMENT__FAULT_DEF_NAME, oldFaultDefName, newFaultDefName);
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
  public void setFaultDefName(DoubleDotRef newFaultDefName)
  {
    if (newFaultDefName != faultDefName)
    {
      NotificationChain msgs = null;
      if (faultDefName != null)
        msgs = ((InternalEObject)faultDefName).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SafetyPackage.FAULT_STATEMENT__FAULT_DEF_NAME, null, msgs);
      if (newFaultDefName != null)
        msgs = ((InternalEObject)newFaultDefName).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SafetyPackage.FAULT_STATEMENT__FAULT_DEF_NAME, null, msgs);
      msgs = basicSetFaultDefName(newFaultDefName, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, SafetyPackage.FAULT_STATEMENT__FAULT_DEF_NAME, newFaultDefName, newFaultDefName));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<FaultSubcomponent> getFaultDefinitions()
  {
    if (faultDefinitions == null)
    {
      faultDefinitions = new EObjectContainmentEList<FaultSubcomponent>(FaultSubcomponent.class, this, SafetyPackage.FAULT_STATEMENT__FAULT_DEFINITIONS);
    }
    return faultDefinitions;
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
      case SafetyPackage.FAULT_STATEMENT__FAULT_DEF_NAME:
        return basicSetFaultDefName(null, msgs);
      case SafetyPackage.FAULT_STATEMENT__FAULT_DEFINITIONS:
        return ((InternalEList<?>)getFaultDefinitions()).basicRemove(otherEnd, msgs);
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
      case SafetyPackage.FAULT_STATEMENT__NAME:
        return getName();
      case SafetyPackage.FAULT_STATEMENT__STR:
        return getStr();
      case SafetyPackage.FAULT_STATEMENT__FAULT_DEF_NAME:
        return getFaultDefName();
      case SafetyPackage.FAULT_STATEMENT__FAULT_DEFINITIONS:
        return getFaultDefinitions();
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
      case SafetyPackage.FAULT_STATEMENT__NAME:
        setName((String)newValue);
        return;
      case SafetyPackage.FAULT_STATEMENT__STR:
        setStr((String)newValue);
        return;
      case SafetyPackage.FAULT_STATEMENT__FAULT_DEF_NAME:
        setFaultDefName((DoubleDotRef)newValue);
        return;
      case SafetyPackage.FAULT_STATEMENT__FAULT_DEFINITIONS:
        getFaultDefinitions().clear();
        getFaultDefinitions().addAll((Collection<? extends FaultSubcomponent>)newValue);
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
      case SafetyPackage.FAULT_STATEMENT__NAME:
        setName(NAME_EDEFAULT);
        return;
      case SafetyPackage.FAULT_STATEMENT__STR:
        setStr(STR_EDEFAULT);
        return;
      case SafetyPackage.FAULT_STATEMENT__FAULT_DEF_NAME:
        setFaultDefName((DoubleDotRef)null);
        return;
      case SafetyPackage.FAULT_STATEMENT__FAULT_DEFINITIONS:
        getFaultDefinitions().clear();
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
      case SafetyPackage.FAULT_STATEMENT__NAME:
        return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
      case SafetyPackage.FAULT_STATEMENT__STR:
        return STR_EDEFAULT == null ? str != null : !STR_EDEFAULT.equals(str);
      case SafetyPackage.FAULT_STATEMENT__FAULT_DEF_NAME:
        return faultDefName != null;
      case SafetyPackage.FAULT_STATEMENT__FAULT_DEFINITIONS:
        return faultDefinitions != null && !faultDefinitions.isEmpty();
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

} //FaultStatementImpl
