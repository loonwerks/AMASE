/**
 */
package edu.umn.cs.crisys.safety.safety.impl;

import com.rockwellcollins.atc.agree.agree.NestedDotID;

import edu.umn.cs.crisys.safety.safety.ConnectionsStatement;
import edu.umn.cs.crisys.safety.safety.SafetyPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Connections Statement</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.impl.ConnectionsStatementImpl#getFault_in <em>Fault in</em>}</li>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.impl.ConnectionsStatementImpl#getFaultDefName <em>Fault Def Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ConnectionsStatementImpl extends ByzantineFaultSubcomponentImpl implements ConnectionsStatement
{
  /**
   * The cached value of the '{@link #getFault_in() <em>Fault in</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFault_in()
   * @generated
   * @ordered
   */
  protected NestedDotID fault_in;

  /**
   * The cached value of the '{@link #getFaultDefName() <em>Fault Def Name</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFaultDefName()
   * @generated
   * @ordered
   */
  protected NestedDotID faultDefName;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ConnectionsStatementImpl()
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
    return SafetyPackage.Literals.CONNECTIONS_STATEMENT;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NestedDotID getFault_in()
  {
    return fault_in;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetFault_in(NestedDotID newFault_in, NotificationChain msgs)
  {
    NestedDotID oldFault_in = fault_in;
    fault_in = newFault_in;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SafetyPackage.CONNECTIONS_STATEMENT__FAULT_IN, oldFault_in, newFault_in);
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
  public void setFault_in(NestedDotID newFault_in)
  {
    if (newFault_in != fault_in)
    {
      NotificationChain msgs = null;
      if (fault_in != null)
        msgs = ((InternalEObject)fault_in).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SafetyPackage.CONNECTIONS_STATEMENT__FAULT_IN, null, msgs);
      if (newFault_in != null)
        msgs = ((InternalEObject)newFault_in).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SafetyPackage.CONNECTIONS_STATEMENT__FAULT_IN, null, msgs);
      msgs = basicSetFault_in(newFault_in, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, SafetyPackage.CONNECTIONS_STATEMENT__FAULT_IN, newFault_in, newFault_in));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NestedDotID getFaultDefName()
  {
    return faultDefName;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetFaultDefName(NestedDotID newFaultDefName, NotificationChain msgs)
  {
    NestedDotID oldFaultDefName = faultDefName;
    faultDefName = newFaultDefName;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SafetyPackage.CONNECTIONS_STATEMENT__FAULT_DEF_NAME, oldFaultDefName, newFaultDefName);
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
  public void setFaultDefName(NestedDotID newFaultDefName)
  {
    if (newFaultDefName != faultDefName)
    {
      NotificationChain msgs = null;
      if (faultDefName != null)
        msgs = ((InternalEObject)faultDefName).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SafetyPackage.CONNECTIONS_STATEMENT__FAULT_DEF_NAME, null, msgs);
      if (newFaultDefName != null)
        msgs = ((InternalEObject)newFaultDefName).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SafetyPackage.CONNECTIONS_STATEMENT__FAULT_DEF_NAME, null, msgs);
      msgs = basicSetFaultDefName(newFaultDefName, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, SafetyPackage.CONNECTIONS_STATEMENT__FAULT_DEF_NAME, newFaultDefName, newFaultDefName));
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
      case SafetyPackage.CONNECTIONS_STATEMENT__FAULT_IN:
        return basicSetFault_in(null, msgs);
      case SafetyPackage.CONNECTIONS_STATEMENT__FAULT_DEF_NAME:
        return basicSetFaultDefName(null, msgs);
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
      case SafetyPackage.CONNECTIONS_STATEMENT__FAULT_IN:
        return getFault_in();
      case SafetyPackage.CONNECTIONS_STATEMENT__FAULT_DEF_NAME:
        return getFaultDefName();
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
      case SafetyPackage.CONNECTIONS_STATEMENT__FAULT_IN:
        setFault_in((NestedDotID)newValue);
        return;
      case SafetyPackage.CONNECTIONS_STATEMENT__FAULT_DEF_NAME:
        setFaultDefName((NestedDotID)newValue);
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
      case SafetyPackage.CONNECTIONS_STATEMENT__FAULT_IN:
        setFault_in((NestedDotID)null);
        return;
      case SafetyPackage.CONNECTIONS_STATEMENT__FAULT_DEF_NAME:
        setFaultDefName((NestedDotID)null);
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
      case SafetyPackage.CONNECTIONS_STATEMENT__FAULT_IN:
        return fault_in != null;
      case SafetyPackage.CONNECTIONS_STATEMENT__FAULT_DEF_NAME:
        return faultDefName != null;
    }
    return super.eIsSet(featureID);
  }

} //ConnectionsStatementImpl
