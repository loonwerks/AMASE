/**
 */
package edu.umn.cs.crisys.safety.safety.impl;

import com.rockwellcollins.atc.agree.agree.Expr;
import com.rockwellcollins.atc.agree.agree.NamedID;

import edu.umn.cs.crisys.safety.safety.InputStatement;
import edu.umn.cs.crisys.safety.safety.SafetyPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Input Statement</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.impl.InputStatementImpl#getFault_in <em>Fault in</em>}</li>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.impl.InputStatementImpl#getNom_conn <em>Nom conn</em>}</li>
 * </ul>
 *
 * @generated
 */
public class InputStatementImpl extends FaultSubcomponentImpl implements InputStatement
{
  /**
   * The cached value of the '{@link #getFault_in() <em>Fault in</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFault_in()
   * @generated
   * @ordered
   */
  protected EList<NamedID> fault_in;

  /**
   * The cached value of the '{@link #getNom_conn() <em>Nom conn</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getNom_conn()
   * @generated
   * @ordered
   */
  protected EList<Expr> nom_conn;

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
  public EList<NamedID> getFault_in()
  {
    if (fault_in == null)
    {
      fault_in = new EObjectContainmentEList<NamedID>(NamedID.class, this, SafetyPackage.INPUT_STATEMENT__FAULT_IN);
    }
    return fault_in;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Expr> getNom_conn()
  {
    if (nom_conn == null)
    {
      nom_conn = new EObjectContainmentEList<Expr>(Expr.class, this, SafetyPackage.INPUT_STATEMENT__NOM_CONN);
    }
    return nom_conn;
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
      case SafetyPackage.INPUT_STATEMENT__FAULT_IN:
        return ((InternalEList<?>)getFault_in()).basicRemove(otherEnd, msgs);
      case SafetyPackage.INPUT_STATEMENT__NOM_CONN:
        return ((InternalEList<?>)getNom_conn()).basicRemove(otherEnd, msgs);
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
      case SafetyPackage.INPUT_STATEMENT__FAULT_IN:
        return getFault_in();
      case SafetyPackage.INPUT_STATEMENT__NOM_CONN:
        return getNom_conn();
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
      case SafetyPackage.INPUT_STATEMENT__FAULT_IN:
        getFault_in().clear();
        getFault_in().addAll((Collection<? extends NamedID>)newValue);
        return;
      case SafetyPackage.INPUT_STATEMENT__NOM_CONN:
        getNom_conn().clear();
        getNom_conn().addAll((Collection<? extends Expr>)newValue);
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
      case SafetyPackage.INPUT_STATEMENT__FAULT_IN:
        getFault_in().clear();
        return;
      case SafetyPackage.INPUT_STATEMENT__NOM_CONN:
        getNom_conn().clear();
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
      case SafetyPackage.INPUT_STATEMENT__FAULT_IN:
        return fault_in != null && !fault_in.isEmpty();
      case SafetyPackage.INPUT_STATEMENT__NOM_CONN:
        return nom_conn != null && !nom_conn.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //InputStatementImpl
