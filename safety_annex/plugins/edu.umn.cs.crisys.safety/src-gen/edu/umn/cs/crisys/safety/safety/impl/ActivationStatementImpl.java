/**
 */
package edu.umn.cs.crisys.safety.safety.impl;

import com.rockwellcollins.atc.agree.agree.NestedDotID;

import edu.umn.cs.crisys.safety.safety.ActivationStatement;
import edu.umn.cs.crisys.safety.safety.SafetyPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Activation Statement</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.impl.ActivationStatementImpl#getAgreeBoolVarName <em>Agree Bool Var Name</em>}</li>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.impl.ActivationStatementImpl#getAgreeComp_Path <em>Agree Comp Path</em>}</li>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.impl.ActivationStatementImpl#getFaultName <em>Fault Name</em>}</li>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.impl.ActivationStatementImpl#getFaultComp_Path <em>Fault Comp Path</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ActivationStatementImpl extends SpecStatementImpl implements ActivationStatement
{
  /**
   * The default value of the '{@link #getAgreeBoolVarName() <em>Agree Bool Var Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAgreeBoolVarName()
   * @generated
   * @ordered
   */
  protected static final String AGREE_BOOL_VAR_NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getAgreeBoolVarName() <em>Agree Bool Var Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAgreeBoolVarName()
   * @generated
   * @ordered
   */
  protected String agreeBoolVarName = AGREE_BOOL_VAR_NAME_EDEFAULT;

  /**
   * The cached value of the '{@link #getAgreeComp_Path() <em>Agree Comp Path</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAgreeComp_Path()
   * @generated
   * @ordered
   */
  protected NestedDotID agreeComp_Path;

  /**
   * The default value of the '{@link #getFaultName() <em>Fault Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFaultName()
   * @generated
   * @ordered
   */
  protected static final String FAULT_NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getFaultName() <em>Fault Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFaultName()
   * @generated
   * @ordered
   */
  protected String faultName = FAULT_NAME_EDEFAULT;

  /**
   * The cached value of the '{@link #getFaultComp_Path() <em>Fault Comp Path</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFaultComp_Path()
   * @generated
   * @ordered
   */
  protected NestedDotID faultComp_Path;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ActivationStatementImpl()
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
    return SafetyPackage.Literals.ACTIVATION_STATEMENT;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getAgreeBoolVarName()
  {
    return agreeBoolVarName;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setAgreeBoolVarName(String newAgreeBoolVarName)
  {
    String oldAgreeBoolVarName = agreeBoolVarName;
    agreeBoolVarName = newAgreeBoolVarName;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, SafetyPackage.ACTIVATION_STATEMENT__AGREE_BOOL_VAR_NAME, oldAgreeBoolVarName, agreeBoolVarName));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NestedDotID getAgreeComp_Path()
  {
    return agreeComp_Path;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetAgreeComp_Path(NestedDotID newAgreeComp_Path, NotificationChain msgs)
  {
    NestedDotID oldAgreeComp_Path = agreeComp_Path;
    agreeComp_Path = newAgreeComp_Path;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SafetyPackage.ACTIVATION_STATEMENT__AGREE_COMP_PATH, oldAgreeComp_Path, newAgreeComp_Path);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setAgreeComp_Path(NestedDotID newAgreeComp_Path)
  {
    if (newAgreeComp_Path != agreeComp_Path)
    {
      NotificationChain msgs = null;
      if (agreeComp_Path != null)
        msgs = ((InternalEObject)agreeComp_Path).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SafetyPackage.ACTIVATION_STATEMENT__AGREE_COMP_PATH, null, msgs);
      if (newAgreeComp_Path != null)
        msgs = ((InternalEObject)newAgreeComp_Path).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SafetyPackage.ACTIVATION_STATEMENT__AGREE_COMP_PATH, null, msgs);
      msgs = basicSetAgreeComp_Path(newAgreeComp_Path, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, SafetyPackage.ACTIVATION_STATEMENT__AGREE_COMP_PATH, newAgreeComp_Path, newAgreeComp_Path));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getFaultName()
  {
    return faultName;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setFaultName(String newFaultName)
  {
    String oldFaultName = faultName;
    faultName = newFaultName;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, SafetyPackage.ACTIVATION_STATEMENT__FAULT_NAME, oldFaultName, faultName));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NestedDotID getFaultComp_Path()
  {
    return faultComp_Path;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetFaultComp_Path(NestedDotID newFaultComp_Path, NotificationChain msgs)
  {
    NestedDotID oldFaultComp_Path = faultComp_Path;
    faultComp_Path = newFaultComp_Path;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SafetyPackage.ACTIVATION_STATEMENT__FAULT_COMP_PATH, oldFaultComp_Path, newFaultComp_Path);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setFaultComp_Path(NestedDotID newFaultComp_Path)
  {
    if (newFaultComp_Path != faultComp_Path)
    {
      NotificationChain msgs = null;
      if (faultComp_Path != null)
        msgs = ((InternalEObject)faultComp_Path).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SafetyPackage.ACTIVATION_STATEMENT__FAULT_COMP_PATH, null, msgs);
      if (newFaultComp_Path != null)
        msgs = ((InternalEObject)newFaultComp_Path).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SafetyPackage.ACTIVATION_STATEMENT__FAULT_COMP_PATH, null, msgs);
      msgs = basicSetFaultComp_Path(newFaultComp_Path, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, SafetyPackage.ACTIVATION_STATEMENT__FAULT_COMP_PATH, newFaultComp_Path, newFaultComp_Path));
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
      case SafetyPackage.ACTIVATION_STATEMENT__AGREE_COMP_PATH:
        return basicSetAgreeComp_Path(null, msgs);
      case SafetyPackage.ACTIVATION_STATEMENT__FAULT_COMP_PATH:
        return basicSetFaultComp_Path(null, msgs);
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
      case SafetyPackage.ACTIVATION_STATEMENT__AGREE_BOOL_VAR_NAME:
        return getAgreeBoolVarName();
      case SafetyPackage.ACTIVATION_STATEMENT__AGREE_COMP_PATH:
        return getAgreeComp_Path();
      case SafetyPackage.ACTIVATION_STATEMENT__FAULT_NAME:
        return getFaultName();
      case SafetyPackage.ACTIVATION_STATEMENT__FAULT_COMP_PATH:
        return getFaultComp_Path();
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
      case SafetyPackage.ACTIVATION_STATEMENT__AGREE_BOOL_VAR_NAME:
        setAgreeBoolVarName((String)newValue);
        return;
      case SafetyPackage.ACTIVATION_STATEMENT__AGREE_COMP_PATH:
        setAgreeComp_Path((NestedDotID)newValue);
        return;
      case SafetyPackage.ACTIVATION_STATEMENT__FAULT_NAME:
        setFaultName((String)newValue);
        return;
      case SafetyPackage.ACTIVATION_STATEMENT__FAULT_COMP_PATH:
        setFaultComp_Path((NestedDotID)newValue);
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
      case SafetyPackage.ACTIVATION_STATEMENT__AGREE_BOOL_VAR_NAME:
        setAgreeBoolVarName(AGREE_BOOL_VAR_NAME_EDEFAULT);
        return;
      case SafetyPackage.ACTIVATION_STATEMENT__AGREE_COMP_PATH:
        setAgreeComp_Path((NestedDotID)null);
        return;
      case SafetyPackage.ACTIVATION_STATEMENT__FAULT_NAME:
        setFaultName(FAULT_NAME_EDEFAULT);
        return;
      case SafetyPackage.ACTIVATION_STATEMENT__FAULT_COMP_PATH:
        setFaultComp_Path((NestedDotID)null);
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
      case SafetyPackage.ACTIVATION_STATEMENT__AGREE_BOOL_VAR_NAME:
        return AGREE_BOOL_VAR_NAME_EDEFAULT == null ? agreeBoolVarName != null : !AGREE_BOOL_VAR_NAME_EDEFAULT.equals(agreeBoolVarName);
      case SafetyPackage.ACTIVATION_STATEMENT__AGREE_COMP_PATH:
        return agreeComp_Path != null;
      case SafetyPackage.ACTIVATION_STATEMENT__FAULT_NAME:
        return FAULT_NAME_EDEFAULT == null ? faultName != null : !FAULT_NAME_EDEFAULT.equals(faultName);
      case SafetyPackage.ACTIVATION_STATEMENT__FAULT_COMP_PATH:
        return faultComp_Path != null;
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
    result.append(" (agreeBoolVarName: ");
    result.append(agreeBoolVarName);
    result.append(", faultName: ");
    result.append(faultName);
    result.append(')');
    return result.toString();
  }

} //ActivationStatementImpl
