/**
 */
package edu.umn.cs.crisys.safety.safety.impl;

import com.rockwellcollins.atc.agree.agree.Arg;
import com.rockwellcollins.atc.agree.agree.Expr;

import edu.umn.cs.crisys.safety.safety.OutputStatement;
import edu.umn.cs.crisys.safety.safety.SafetyPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Output Statement</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.impl.OutputStatementImpl#getNom_conn <em>Nom conn</em>}</li>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.impl.OutputStatementImpl#getFault_out_conn <em>Fault out conn</em>}</li>
 * </ul>
 *
 * @generated
 */
public class OutputStatementImpl extends FaultSubcomponentImpl implements OutputStatement
{
  /**
   * The cached value of the '{@link #getNom_conn() <em>Nom conn</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getNom_conn()
   * @generated
   * @ordered
   */
  protected Expr nom_conn;

  /**
   * The cached value of the '{@link #getFault_out_conn() <em>Fault out conn</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFault_out_conn()
   * @generated
   * @ordered
   */
  protected Arg fault_out_conn;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected OutputStatementImpl()
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
    return SafetyPackage.Literals.OUTPUT_STATEMENT;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Expr getNom_conn()
  {
    return nom_conn;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetNom_conn(Expr newNom_conn, NotificationChain msgs)
  {
    Expr oldNom_conn = nom_conn;
    nom_conn = newNom_conn;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SafetyPackage.OUTPUT_STATEMENT__NOM_CONN, oldNom_conn, newNom_conn);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setNom_conn(Expr newNom_conn)
  {
    if (newNom_conn != nom_conn)
    {
      NotificationChain msgs = null;
      if (nom_conn != null)
        msgs = ((InternalEObject)nom_conn).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SafetyPackage.OUTPUT_STATEMENT__NOM_CONN, null, msgs);
      if (newNom_conn != null)
        msgs = ((InternalEObject)newNom_conn).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SafetyPackage.OUTPUT_STATEMENT__NOM_CONN, null, msgs);
      msgs = basicSetNom_conn(newNom_conn, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, SafetyPackage.OUTPUT_STATEMENT__NOM_CONN, newNom_conn, newNom_conn));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Arg getFault_out_conn()
  {
    return fault_out_conn;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetFault_out_conn(Arg newFault_out_conn, NotificationChain msgs)
  {
    Arg oldFault_out_conn = fault_out_conn;
    fault_out_conn = newFault_out_conn;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SafetyPackage.OUTPUT_STATEMENT__FAULT_OUT_CONN, oldFault_out_conn, newFault_out_conn);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setFault_out_conn(Arg newFault_out_conn)
  {
    if (newFault_out_conn != fault_out_conn)
    {
      NotificationChain msgs = null;
      if (fault_out_conn != null)
        msgs = ((InternalEObject)fault_out_conn).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SafetyPackage.OUTPUT_STATEMENT__FAULT_OUT_CONN, null, msgs);
      if (newFault_out_conn != null)
        msgs = ((InternalEObject)newFault_out_conn).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SafetyPackage.OUTPUT_STATEMENT__FAULT_OUT_CONN, null, msgs);
      msgs = basicSetFault_out_conn(newFault_out_conn, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, SafetyPackage.OUTPUT_STATEMENT__FAULT_OUT_CONN, newFault_out_conn, newFault_out_conn));
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
      case SafetyPackage.OUTPUT_STATEMENT__NOM_CONN:
        return basicSetNom_conn(null, msgs);
      case SafetyPackage.OUTPUT_STATEMENT__FAULT_OUT_CONN:
        return basicSetFault_out_conn(null, msgs);
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
      case SafetyPackage.OUTPUT_STATEMENT__NOM_CONN:
        return getNom_conn();
      case SafetyPackage.OUTPUT_STATEMENT__FAULT_OUT_CONN:
        return getFault_out_conn();
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
      case SafetyPackage.OUTPUT_STATEMENT__NOM_CONN:
        setNom_conn((Expr)newValue);
        return;
      case SafetyPackage.OUTPUT_STATEMENT__FAULT_OUT_CONN:
        setFault_out_conn((Arg)newValue);
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
      case SafetyPackage.OUTPUT_STATEMENT__NOM_CONN:
        setNom_conn((Expr)null);
        return;
      case SafetyPackage.OUTPUT_STATEMENT__FAULT_OUT_CONN:
        setFault_out_conn((Arg)null);
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
      case SafetyPackage.OUTPUT_STATEMENT__NOM_CONN:
        return nom_conn != null;
      case SafetyPackage.OUTPUT_STATEMENT__FAULT_OUT_CONN:
        return fault_out_conn != null;
    }
    return super.eIsSet(featureID);
  }

} //OutputStatementImpl
