/**
 */
package edu.umn.cs.crisys.safety.safety.impl;

import edu.umn.cs.crisys.safety.safety.Contract;
import edu.umn.cs.crisys.safety.safety.SafetyContractSubclause;
import edu.umn.cs.crisys.safety.safety.SafetyPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Contract Subclause</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.impl.SafetyContractSubclauseImpl#getContract <em>Contract</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SafetyContractSubclauseImpl extends SafetySubclauseImpl implements SafetyContractSubclause
{
  /**
   * The cached value of the '{@link #getContract() <em>Contract</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getContract()
   * @generated
   * @ordered
   */
  protected Contract contract;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected SafetyContractSubclauseImpl()
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
    return SafetyPackage.Literals.SAFETY_CONTRACT_SUBCLAUSE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Contract getContract()
  {
    return contract;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetContract(Contract newContract, NotificationChain msgs)
  {
    Contract oldContract = contract;
    contract = newContract;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SafetyPackage.SAFETY_CONTRACT_SUBCLAUSE__CONTRACT, oldContract, newContract);
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
  public void setContract(Contract newContract)
  {
    if (newContract != contract)
    {
      NotificationChain msgs = null;
      if (contract != null)
        msgs = ((InternalEObject)contract).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SafetyPackage.SAFETY_CONTRACT_SUBCLAUSE__CONTRACT, null, msgs);
      if (newContract != null)
        msgs = ((InternalEObject)newContract).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SafetyPackage.SAFETY_CONTRACT_SUBCLAUSE__CONTRACT, null, msgs);
      msgs = basicSetContract(newContract, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, SafetyPackage.SAFETY_CONTRACT_SUBCLAUSE__CONTRACT, newContract, newContract));
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
      case SafetyPackage.SAFETY_CONTRACT_SUBCLAUSE__CONTRACT:
        return basicSetContract(null, msgs);
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
      case SafetyPackage.SAFETY_CONTRACT_SUBCLAUSE__CONTRACT:
        return getContract();
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
      case SafetyPackage.SAFETY_CONTRACT_SUBCLAUSE__CONTRACT:
        setContract((Contract)newValue);
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
      case SafetyPackage.SAFETY_CONTRACT_SUBCLAUSE__CONTRACT:
        setContract((Contract)null);
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
      case SafetyPackage.SAFETY_CONTRACT_SUBCLAUSE__CONTRACT:
        return contract != null;
    }
    return super.eIsSet(featureID);
  }

} //SafetyContractSubclauseImpl
