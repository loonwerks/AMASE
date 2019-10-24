/**
 */
package edu.umn.cs.crisys.safety.safety.impl;

import edu.umn.cs.crisys.safety.safety.ActivationStatement;
import edu.umn.cs.crisys.safety.safety.SafetyPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.osate.aadl2.NamedElement;

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
   * The cached value of the '{@link #getAgreeComp_Path() <em>Agree Comp Path</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAgreeComp_Path()
   * @generated
   * @ordered
   */
  protected NamedElement agreeComp_Path;

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
   * The cached value of the '{@link #getFaultComp_Path() <em>Fault Comp Path</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFaultComp_Path()
   * @generated
   * @ordered
   */
  protected NamedElement faultComp_Path;

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
  @Override
  public String getAgreeBoolVarName()
  {
    return agreeBoolVarName;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
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
  @Override
  public NamedElement getAgreeComp_Path()
  {
    if (agreeComp_Path != null && ((EObject)agreeComp_Path).eIsProxy())
    {
      InternalEObject oldAgreeComp_Path = (InternalEObject)agreeComp_Path;
      agreeComp_Path = (NamedElement)eResolveProxy(oldAgreeComp_Path);
      if (agreeComp_Path != oldAgreeComp_Path)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, SafetyPackage.ACTIVATION_STATEMENT__AGREE_COMP_PATH, oldAgreeComp_Path, agreeComp_Path));
      }
    }
    return agreeComp_Path;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NamedElement basicGetAgreeComp_Path()
  {
    return agreeComp_Path;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setAgreeComp_Path(NamedElement newAgreeComp_Path)
  {
    NamedElement oldAgreeComp_Path = agreeComp_Path;
    agreeComp_Path = newAgreeComp_Path;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, SafetyPackage.ACTIVATION_STATEMENT__AGREE_COMP_PATH, oldAgreeComp_Path, agreeComp_Path));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getFaultName()
  {
    return faultName;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
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
  @Override
  public NamedElement getFaultComp_Path()
  {
    if (faultComp_Path != null && ((EObject)faultComp_Path).eIsProxy())
    {
      InternalEObject oldFaultComp_Path = (InternalEObject)faultComp_Path;
      faultComp_Path = (NamedElement)eResolveProxy(oldFaultComp_Path);
      if (faultComp_Path != oldFaultComp_Path)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, SafetyPackage.ACTIVATION_STATEMENT__FAULT_COMP_PATH, oldFaultComp_Path, faultComp_Path));
      }
    }
    return faultComp_Path;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NamedElement basicGetFaultComp_Path()
  {
    return faultComp_Path;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setFaultComp_Path(NamedElement newFaultComp_Path)
  {
    NamedElement oldFaultComp_Path = faultComp_Path;
    faultComp_Path = newFaultComp_Path;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, SafetyPackage.ACTIVATION_STATEMENT__FAULT_COMP_PATH, oldFaultComp_Path, faultComp_Path));
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
        if (resolve) return getAgreeComp_Path();
        return basicGetAgreeComp_Path();
      case SafetyPackage.ACTIVATION_STATEMENT__FAULT_NAME:
        return getFaultName();
      case SafetyPackage.ACTIVATION_STATEMENT__FAULT_COMP_PATH:
        if (resolve) return getFaultComp_Path();
        return basicGetFaultComp_Path();
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
        setAgreeComp_Path((NamedElement)newValue);
        return;
      case SafetyPackage.ACTIVATION_STATEMENT__FAULT_NAME:
        setFaultName((String)newValue);
        return;
      case SafetyPackage.ACTIVATION_STATEMENT__FAULT_COMP_PATH:
        setFaultComp_Path((NamedElement)newValue);
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
        setAgreeComp_Path((NamedElement)null);
        return;
      case SafetyPackage.ACTIVATION_STATEMENT__FAULT_NAME:
        setFaultName(FAULT_NAME_EDEFAULT);
        return;
      case SafetyPackage.ACTIVATION_STATEMENT__FAULT_COMP_PATH:
        setFaultComp_Path((NamedElement)null);
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

    StringBuilder result = new StringBuilder(super.toString());
    result.append(" (agreeBoolVarName: ");
    result.append(agreeBoolVarName);
    result.append(", faultName: ");
    result.append(faultName);
    result.append(')');
    return result.toString();
  }

} //ActivationStatementImpl
