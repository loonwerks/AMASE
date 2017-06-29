/**
 */
package org.xtext.example.safety.safety.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.osate.aadl2.NamedElement;

import org.xtext.example.safety.safety.OutputStatement;
import org.xtext.example.safety.safety.SafetyPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Output Statement</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.xtext.example.safety.safety.impl.OutputStatementImpl#getOut_conn <em>Out conn</em>}</li>
 *   <li>{@link org.xtext.example.safety.safety.impl.OutputStatementImpl#getNom_conn <em>Nom conn</em>}</li>
 * </ul>
 *
 * @generated
 */
public class OutputStatementImpl extends FaultSubcomponentImpl implements OutputStatement
{
  /**
   * The default value of the '{@link #getOut_conn() <em>Out conn</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getOut_conn()
   * @generated
   * @ordered
   */
  protected static final String OUT_CONN_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getOut_conn() <em>Out conn</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getOut_conn()
   * @generated
   * @ordered
   */
  protected String out_conn = OUT_CONN_EDEFAULT;

  /**
   * The cached value of the '{@link #getNom_conn() <em>Nom conn</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getNom_conn()
   * @generated
   * @ordered
   */
  protected NamedElement nom_conn;

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
  public String getOut_conn()
  {
    return out_conn;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setOut_conn(String newOut_conn)
  {
    String oldOut_conn = out_conn;
    out_conn = newOut_conn;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, SafetyPackage.OUTPUT_STATEMENT__OUT_CONN, oldOut_conn, out_conn));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NamedElement getNom_conn()
  {
    if (nom_conn != null && ((EObject)nom_conn).eIsProxy())
    {
      InternalEObject oldNom_conn = (InternalEObject)nom_conn;
      nom_conn = (NamedElement)eResolveProxy(oldNom_conn);
      if (nom_conn != oldNom_conn)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, SafetyPackage.OUTPUT_STATEMENT__NOM_CONN, oldNom_conn, nom_conn));
      }
    }
    return nom_conn;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NamedElement basicGetNom_conn()
  {
    return nom_conn;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setNom_conn(NamedElement newNom_conn)
  {
    NamedElement oldNom_conn = nom_conn;
    nom_conn = newNom_conn;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, SafetyPackage.OUTPUT_STATEMENT__NOM_CONN, oldNom_conn, nom_conn));
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
      case SafetyPackage.OUTPUT_STATEMENT__OUT_CONN:
        return getOut_conn();
      case SafetyPackage.OUTPUT_STATEMENT__NOM_CONN:
        if (resolve) return getNom_conn();
        return basicGetNom_conn();
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
      case SafetyPackage.OUTPUT_STATEMENT__OUT_CONN:
        setOut_conn((String)newValue);
        return;
      case SafetyPackage.OUTPUT_STATEMENT__NOM_CONN:
        setNom_conn((NamedElement)newValue);
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
      case SafetyPackage.OUTPUT_STATEMENT__OUT_CONN:
        setOut_conn(OUT_CONN_EDEFAULT);
        return;
      case SafetyPackage.OUTPUT_STATEMENT__NOM_CONN:
        setNom_conn((NamedElement)null);
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
      case SafetyPackage.OUTPUT_STATEMENT__OUT_CONN:
        return OUT_CONN_EDEFAULT == null ? out_conn != null : !OUT_CONN_EDEFAULT.equals(out_conn);
      case SafetyPackage.OUTPUT_STATEMENT__NOM_CONN:
        return nom_conn != null;
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
    result.append(" (out_conn: ");
    result.append(out_conn);
    result.append(')');
    return result.toString();
  }

} //OutputStatementImpl
