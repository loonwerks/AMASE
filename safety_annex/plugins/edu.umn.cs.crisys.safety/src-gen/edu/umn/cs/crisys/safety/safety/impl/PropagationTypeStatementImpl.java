/**
 */
package edu.umn.cs.crisys.safety.safety.impl;

import edu.umn.cs.crisys.safety.safety.PropagationTypeConstraint;
import edu.umn.cs.crisys.safety.safety.PropagationTypeStatement;
import edu.umn.cs.crisys.safety.safety.SafetyPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Propagation Type Statement</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.impl.PropagationTypeStatementImpl#getPty <em>Pty</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PropagationTypeStatementImpl extends FaultSubcomponentImpl implements PropagationTypeStatement
{
  /**
   * The cached value of the '{@link #getPty() <em>Pty</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPty()
   * @generated
   * @ordered
   */
  protected PropagationTypeConstraint pty;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected PropagationTypeStatementImpl()
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
    return SafetyPackage.Literals.PROPAGATION_TYPE_STATEMENT;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public PropagationTypeConstraint getPty()
  {
    return pty;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetPty(PropagationTypeConstraint newPty, NotificationChain msgs)
  {
    PropagationTypeConstraint oldPty = pty;
    pty = newPty;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SafetyPackage.PROPAGATION_TYPE_STATEMENT__PTY, oldPty, newPty);
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
  public void setPty(PropagationTypeConstraint newPty)
  {
    if (newPty != pty)
    {
      NotificationChain msgs = null;
      if (pty != null)
        msgs = ((InternalEObject)pty).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SafetyPackage.PROPAGATION_TYPE_STATEMENT__PTY, null, msgs);
      if (newPty != null)
        msgs = ((InternalEObject)newPty).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SafetyPackage.PROPAGATION_TYPE_STATEMENT__PTY, null, msgs);
      msgs = basicSetPty(newPty, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, SafetyPackage.PROPAGATION_TYPE_STATEMENT__PTY, newPty, newPty));
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
      case SafetyPackage.PROPAGATION_TYPE_STATEMENT__PTY:
        return basicSetPty(null, msgs);
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
      case SafetyPackage.PROPAGATION_TYPE_STATEMENT__PTY:
        return getPty();
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
      case SafetyPackage.PROPAGATION_TYPE_STATEMENT__PTY:
        setPty((PropagationTypeConstraint)newValue);
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
      case SafetyPackage.PROPAGATION_TYPE_STATEMENT__PTY:
        setPty((PropagationTypeConstraint)null);
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
      case SafetyPackage.PROPAGATION_TYPE_STATEMENT__PTY:
        return pty != null;
    }
    return super.eIsSet(featureID);
  }

} //PropagationTypeStatementImpl
