/**
 */
package edu.umn.cs.crisys.safety.safety.impl;

import com.rockwellcollins.atc.agree.agree.NamedID;

import edu.umn.cs.crisys.safety.safety.FaultOutputAssignStmt;
import edu.umn.cs.crisys.safety.safety.SafetyPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.osate.aadl2.NamedElement;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Fault Output Assign Stmt</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.impl.FaultOutputAssignStmtImpl#getFault_output_eq <em>Fault output eq</em>}</li>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.impl.FaultOutputAssignStmtImpl#getEq_stmt <em>Eq stmt</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FaultOutputAssignStmtImpl extends OutputAssignStatementImpl implements FaultOutputAssignStmt
{
  /**
   * The cached value of the '{@link #getFault_output_eq() <em>Fault output eq</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFault_output_eq()
   * @generated
   * @ordered
   */
  protected NamedElement fault_output_eq;

  /**
   * The cached value of the '{@link #getEq_stmt() <em>Eq stmt</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getEq_stmt()
   * @generated
   * @ordered
   */
  protected NamedID eq_stmt;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected FaultOutputAssignStmtImpl()
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
    return SafetyPackage.Literals.FAULT_OUTPUT_ASSIGN_STMT;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NamedElement getFault_output_eq()
  {
    if (fault_output_eq != null && ((EObject)fault_output_eq).eIsProxy())
    {
      InternalEObject oldFault_output_eq = (InternalEObject)fault_output_eq;
      fault_output_eq = (NamedElement)eResolveProxy(oldFault_output_eq);
      if (fault_output_eq != oldFault_output_eq)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, SafetyPackage.FAULT_OUTPUT_ASSIGN_STMT__FAULT_OUTPUT_EQ, oldFault_output_eq, fault_output_eq));
      }
    }
    return fault_output_eq;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NamedElement basicGetFault_output_eq()
  {
    return fault_output_eq;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setFault_output_eq(NamedElement newFault_output_eq)
  {
    NamedElement oldFault_output_eq = fault_output_eq;
    fault_output_eq = newFault_output_eq;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, SafetyPackage.FAULT_OUTPUT_ASSIGN_STMT__FAULT_OUTPUT_EQ, oldFault_output_eq, fault_output_eq));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NamedID getEq_stmt()
  {
    return eq_stmt;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetEq_stmt(NamedID newEq_stmt, NotificationChain msgs)
  {
    NamedID oldEq_stmt = eq_stmt;
    eq_stmt = newEq_stmt;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SafetyPackage.FAULT_OUTPUT_ASSIGN_STMT__EQ_STMT, oldEq_stmt, newEq_stmt);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setEq_stmt(NamedID newEq_stmt)
  {
    if (newEq_stmt != eq_stmt)
    {
      NotificationChain msgs = null;
      if (eq_stmt != null)
        msgs = ((InternalEObject)eq_stmt).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SafetyPackage.FAULT_OUTPUT_ASSIGN_STMT__EQ_STMT, null, msgs);
      if (newEq_stmt != null)
        msgs = ((InternalEObject)newEq_stmt).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SafetyPackage.FAULT_OUTPUT_ASSIGN_STMT__EQ_STMT, null, msgs);
      msgs = basicSetEq_stmt(newEq_stmt, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, SafetyPackage.FAULT_OUTPUT_ASSIGN_STMT__EQ_STMT, newEq_stmt, newEq_stmt));
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
      case SafetyPackage.FAULT_OUTPUT_ASSIGN_STMT__EQ_STMT:
        return basicSetEq_stmt(null, msgs);
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
      case SafetyPackage.FAULT_OUTPUT_ASSIGN_STMT__FAULT_OUTPUT_EQ:
        if (resolve) return getFault_output_eq();
        return basicGetFault_output_eq();
      case SafetyPackage.FAULT_OUTPUT_ASSIGN_STMT__EQ_STMT:
        return getEq_stmt();
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
      case SafetyPackage.FAULT_OUTPUT_ASSIGN_STMT__FAULT_OUTPUT_EQ:
        setFault_output_eq((NamedElement)newValue);
        return;
      case SafetyPackage.FAULT_OUTPUT_ASSIGN_STMT__EQ_STMT:
        setEq_stmt((NamedID)newValue);
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
      case SafetyPackage.FAULT_OUTPUT_ASSIGN_STMT__FAULT_OUTPUT_EQ:
        setFault_output_eq((NamedElement)null);
        return;
      case SafetyPackage.FAULT_OUTPUT_ASSIGN_STMT__EQ_STMT:
        setEq_stmt((NamedID)null);
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
      case SafetyPackage.FAULT_OUTPUT_ASSIGN_STMT__FAULT_OUTPUT_EQ:
        return fault_output_eq != null;
      case SafetyPackage.FAULT_OUTPUT_ASSIGN_STMT__EQ_STMT:
        return eq_stmt != null;
    }
    return super.eIsSet(featureID);
  }

} //FaultOutputAssignStmtImpl
