/**
 */
package edu.umn.cs.crisys.safety.safety.impl;

import com.rockwellcollins.atc.agree.agree.NestedDotID;

import edu.umn.cs.crisys.safety.safety.PropagateStatement;
import edu.umn.cs.crisys.safety.safety.SafetyPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EDataTypeEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Propagate Statement</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.impl.PropagateStatementImpl#getSrcFaultList <em>Src Fault List</em>}</li>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.impl.PropagateStatementImpl#getSrcComp_path <em>Src Comp path</em>}</li>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.impl.PropagateStatementImpl#getDestFaultList <em>Dest Fault List</em>}</li>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.impl.PropagateStatementImpl#getDestComp_path <em>Dest Comp path</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PropagateStatementImpl extends SpecStatementImpl implements PropagateStatement
{
  /**
   * The cached value of the '{@link #getSrcFaultList() <em>Src Fault List</em>}' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSrcFaultList()
   * @generated
   * @ordered
   */
  protected EList<String> srcFaultList;

  /**
   * The cached value of the '{@link #getSrcComp_path() <em>Src Comp path</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSrcComp_path()
   * @generated
   * @ordered
   */
  protected EList<NestedDotID> srcComp_path;

  /**
   * The cached value of the '{@link #getDestFaultList() <em>Dest Fault List</em>}' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDestFaultList()
   * @generated
   * @ordered
   */
  protected EList<String> destFaultList;

  /**
   * The cached value of the '{@link #getDestComp_path() <em>Dest Comp path</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDestComp_path()
   * @generated
   * @ordered
   */
  protected EList<NestedDotID> destComp_path;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected PropagateStatementImpl()
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
    return SafetyPackage.Literals.PROPAGATE_STATEMENT;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<String> getSrcFaultList()
  {
    if (srcFaultList == null)
    {
      srcFaultList = new EDataTypeEList<String>(String.class, this, SafetyPackage.PROPAGATE_STATEMENT__SRC_FAULT_LIST);
    }
    return srcFaultList;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<NestedDotID> getSrcComp_path()
  {
    if (srcComp_path == null)
    {
      srcComp_path = new EObjectContainmentEList<NestedDotID>(NestedDotID.class, this, SafetyPackage.PROPAGATE_STATEMENT__SRC_COMP_PATH);
    }
    return srcComp_path;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<String> getDestFaultList()
  {
    if (destFaultList == null)
    {
      destFaultList = new EDataTypeEList<String>(String.class, this, SafetyPackage.PROPAGATE_STATEMENT__DEST_FAULT_LIST);
    }
    return destFaultList;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<NestedDotID> getDestComp_path()
  {
    if (destComp_path == null)
    {
      destComp_path = new EObjectContainmentEList<NestedDotID>(NestedDotID.class, this, SafetyPackage.PROPAGATE_STATEMENT__DEST_COMP_PATH);
    }
    return destComp_path;
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
      case SafetyPackage.PROPAGATE_STATEMENT__SRC_COMP_PATH:
        return ((InternalEList<?>)getSrcComp_path()).basicRemove(otherEnd, msgs);
      case SafetyPackage.PROPAGATE_STATEMENT__DEST_COMP_PATH:
        return ((InternalEList<?>)getDestComp_path()).basicRemove(otherEnd, msgs);
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
      case SafetyPackage.PROPAGATE_STATEMENT__SRC_FAULT_LIST:
        return getSrcFaultList();
      case SafetyPackage.PROPAGATE_STATEMENT__SRC_COMP_PATH:
        return getSrcComp_path();
      case SafetyPackage.PROPAGATE_STATEMENT__DEST_FAULT_LIST:
        return getDestFaultList();
      case SafetyPackage.PROPAGATE_STATEMENT__DEST_COMP_PATH:
        return getDestComp_path();
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
      case SafetyPackage.PROPAGATE_STATEMENT__SRC_FAULT_LIST:
        getSrcFaultList().clear();
        getSrcFaultList().addAll((Collection<? extends String>)newValue);
        return;
      case SafetyPackage.PROPAGATE_STATEMENT__SRC_COMP_PATH:
        getSrcComp_path().clear();
        getSrcComp_path().addAll((Collection<? extends NestedDotID>)newValue);
        return;
      case SafetyPackage.PROPAGATE_STATEMENT__DEST_FAULT_LIST:
        getDestFaultList().clear();
        getDestFaultList().addAll((Collection<? extends String>)newValue);
        return;
      case SafetyPackage.PROPAGATE_STATEMENT__DEST_COMP_PATH:
        getDestComp_path().clear();
        getDestComp_path().addAll((Collection<? extends NestedDotID>)newValue);
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
      case SafetyPackage.PROPAGATE_STATEMENT__SRC_FAULT_LIST:
        getSrcFaultList().clear();
        return;
      case SafetyPackage.PROPAGATE_STATEMENT__SRC_COMP_PATH:
        getSrcComp_path().clear();
        return;
      case SafetyPackage.PROPAGATE_STATEMENT__DEST_FAULT_LIST:
        getDestFaultList().clear();
        return;
      case SafetyPackage.PROPAGATE_STATEMENT__DEST_COMP_PATH:
        getDestComp_path().clear();
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
      case SafetyPackage.PROPAGATE_STATEMENT__SRC_FAULT_LIST:
        return srcFaultList != null && !srcFaultList.isEmpty();
      case SafetyPackage.PROPAGATE_STATEMENT__SRC_COMP_PATH:
        return srcComp_path != null && !srcComp_path.isEmpty();
      case SafetyPackage.PROPAGATE_STATEMENT__DEST_FAULT_LIST:
        return destFaultList != null && !destFaultList.isEmpty();
      case SafetyPackage.PROPAGATE_STATEMENT__DEST_COMP_PATH:
        return destComp_path != null && !destComp_path.isEmpty();
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
    result.append(" (srcFaultList: ");
    result.append(srcFaultList);
    result.append(", destFaultList: ");
    result.append(destFaultList);
    result.append(')');
    return result.toString();
  }

} //PropagateStatementImpl
