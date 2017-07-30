/**
 */
package edu.umn.cs.crisys.safety.safety.impl;

import com.rockwellcollins.atc.agree.agree.Expr;

import edu.umn.cs.crisys.safety.safety.InputStatement;
import edu.umn.cs.crisys.safety.safety.SafetyPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.osate.aadl2.Element;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Input Statement</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.impl.InputStatementImpl#getIn_conn <em>In conn</em>}</li>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.impl.InputStatementImpl#getOut_conn <em>Out conn</em>}</li>
 * </ul>
 *
 * @generated
 */
public class InputStatementImpl extends FaultSubcomponentImpl implements InputStatement
{
  /**
   * The cached value of the '{@link #getIn_conn() <em>In conn</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getIn_conn()
   * @generated
   * @ordered
   */
  protected Element in_conn;

  /**
   * The cached value of the '{@link #getOut_conn() <em>Out conn</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getOut_conn()
   * @generated
   * @ordered
   */
  protected Expr out_conn;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected InputStatementImpl()
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
    return SafetyPackage.Literals.INPUT_STATEMENT;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Element getIn_conn()
  {
    if (in_conn != null && ((EObject)in_conn).eIsProxy())
    {
      InternalEObject oldIn_conn = (InternalEObject)in_conn;
      in_conn = (Element)eResolveProxy(oldIn_conn);
      if (in_conn != oldIn_conn)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, SafetyPackage.INPUT_STATEMENT__IN_CONN, oldIn_conn, in_conn));
      }
    }
    return in_conn;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Element basicGetIn_conn()
  {
    return in_conn;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setIn_conn(Element newIn_conn)
  {
    Element oldIn_conn = in_conn;
    in_conn = newIn_conn;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, SafetyPackage.INPUT_STATEMENT__IN_CONN, oldIn_conn, in_conn));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Expr getOut_conn()
  {
    return out_conn;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetOut_conn(Expr newOut_conn, NotificationChain msgs)
  {
    Expr oldOut_conn = out_conn;
    out_conn = newOut_conn;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SafetyPackage.INPUT_STATEMENT__OUT_CONN, oldOut_conn, newOut_conn);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setOut_conn(Expr newOut_conn)
  {
    if (newOut_conn != out_conn)
    {
      NotificationChain msgs = null;
      if (out_conn != null)
        msgs = ((InternalEObject)out_conn).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SafetyPackage.INPUT_STATEMENT__OUT_CONN, null, msgs);
      if (newOut_conn != null)
        msgs = ((InternalEObject)newOut_conn).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SafetyPackage.INPUT_STATEMENT__OUT_CONN, null, msgs);
      msgs = basicSetOut_conn(newOut_conn, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, SafetyPackage.INPUT_STATEMENT__OUT_CONN, newOut_conn, newOut_conn));
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
      case SafetyPackage.INPUT_STATEMENT__OUT_CONN:
        return basicSetOut_conn(null, msgs);
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
      case SafetyPackage.INPUT_STATEMENT__IN_CONN:
        if (resolve) return getIn_conn();
        return basicGetIn_conn();
      case SafetyPackage.INPUT_STATEMENT__OUT_CONN:
        return getOut_conn();
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
      case SafetyPackage.INPUT_STATEMENT__IN_CONN:
        setIn_conn((Element)newValue);
        return;
      case SafetyPackage.INPUT_STATEMENT__OUT_CONN:
        setOut_conn((Expr)newValue);
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
      case SafetyPackage.INPUT_STATEMENT__IN_CONN:
        setIn_conn((Element)null);
        return;
      case SafetyPackage.INPUT_STATEMENT__OUT_CONN:
        setOut_conn((Expr)null);
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
      case SafetyPackage.INPUT_STATEMENT__IN_CONN:
        return in_conn != null;
      case SafetyPackage.INPUT_STATEMENT__OUT_CONN:
        return out_conn != null;
    }
    return super.eIsSet(featureID);
  }

} //InputStatementImpl
