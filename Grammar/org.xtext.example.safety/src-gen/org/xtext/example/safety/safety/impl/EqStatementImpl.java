/**
 */
package org.xtext.example.safety.safety.impl;

import com.rockwellcollins.atc.agree.agree.AgreePackage;
import com.rockwellcollins.atc.agree.agree.Arg;
import com.rockwellcollins.atc.agree.agree.Expr;
import com.rockwellcollins.atc.agree.agree.SpecStatement;
import com.rockwellcollins.atc.agree.agree.TimeInterval;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.osate.aadl2.Aadl2Package;
import org.osate.aadl2.Comment;
import org.osate.aadl2.Element;

import org.xtext.example.safety.safety.EqStatement;
import org.xtext.example.safety.safety.SafetyPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Eq Statement</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.xtext.example.safety.safety.impl.EqStatementImpl#getOwnedElements <em>Owned Element</em>}</li>
 *   <li>{@link org.xtext.example.safety.safety.impl.EqStatementImpl#getOwnedComments <em>Owned Comment</em>}</li>
 *   <li>{@link org.xtext.example.safety.safety.impl.EqStatementImpl#getLhs <em>Lhs</em>}</li>
 *   <li>{@link org.xtext.example.safety.safety.impl.EqStatementImpl#getExpr <em>Expr</em>}</li>
 *   <li>{@link org.xtext.example.safety.safety.impl.EqStatementImpl#getInterv <em>Interv</em>}</li>
 *   <li>{@link org.xtext.example.safety.safety.impl.EqStatementImpl#getExprList <em>Expr List</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EqStatementImpl extends FaultSubcomponentImpl implements EqStatement
{
  /**
   * The cached value of the '{@link #getOwnedComments() <em>Owned Comment</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getOwnedComments()
   * @generated
   * @ordered
   */
  protected EList<Comment> ownedComments;

  /**
   * The cached value of the '{@link #getLhs() <em>Lhs</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLhs()
   * @generated
   * @ordered
   */
  protected EList<Arg> lhs;

  /**
   * The cached value of the '{@link #getExpr() <em>Expr</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getExpr()
   * @generated
   * @ordered
   */
  protected Expr expr;

  /**
   * The cached value of the '{@link #getInterv() <em>Interv</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInterv()
   * @generated
   * @ordered
   */
  protected TimeInterval interv;

  /**
   * The cached value of the '{@link #getExprList() <em>Expr List</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getExprList()
   * @generated
   * @ordered
   */
  protected EList<Expr> exprList;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected EqStatementImpl()
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
    return SafetyPackage.Literals.EQ_STATEMENT;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Element> getOwnedElements()
  {
    // TODO: implement this method to return the 'Owned Element' reference list
    // Ensure that you remove @generated or mark it @generated NOT
    // The list is expected to implement org.eclipse.emf.ecore.util.InternalEList and org.eclipse.emf.ecore.EStructuralFeature.Setting
    // so it's likely that an appropriate subclass of org.eclipse.emf.ecore.util.EcoreEList should be used.
    throw new UnsupportedOperationException();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Comment> getOwnedComments()
  {
    if (ownedComments == null)
    {
      ownedComments = new EObjectContainmentEList<Comment>(Comment.class, this, SafetyPackage.EQ_STATEMENT__OWNED_COMMENT);
    }
    return ownedComments;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Arg> getLhs()
  {
    if (lhs == null)
    {
      lhs = new EObjectContainmentEList<Arg>(Arg.class, this, SafetyPackage.EQ_STATEMENT__LHS);
    }
    return lhs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Expr getExpr()
  {
    return expr;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetExpr(Expr newExpr, NotificationChain msgs)
  {
    Expr oldExpr = expr;
    expr = newExpr;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SafetyPackage.EQ_STATEMENT__EXPR, oldExpr, newExpr);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setExpr(Expr newExpr)
  {
    if (newExpr != expr)
    {
      NotificationChain msgs = null;
      if (expr != null)
        msgs = ((InternalEObject)expr).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SafetyPackage.EQ_STATEMENT__EXPR, null, msgs);
      if (newExpr != null)
        msgs = ((InternalEObject)newExpr).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SafetyPackage.EQ_STATEMENT__EXPR, null, msgs);
      msgs = basicSetExpr(newExpr, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, SafetyPackage.EQ_STATEMENT__EXPR, newExpr, newExpr));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public TimeInterval getInterv()
  {
    return interv;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetInterv(TimeInterval newInterv, NotificationChain msgs)
  {
    TimeInterval oldInterv = interv;
    interv = newInterv;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SafetyPackage.EQ_STATEMENT__INTERV, oldInterv, newInterv);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setInterv(TimeInterval newInterv)
  {
    if (newInterv != interv)
    {
      NotificationChain msgs = null;
      if (interv != null)
        msgs = ((InternalEObject)interv).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SafetyPackage.EQ_STATEMENT__INTERV, null, msgs);
      if (newInterv != null)
        msgs = ((InternalEObject)newInterv).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SafetyPackage.EQ_STATEMENT__INTERV, null, msgs);
      msgs = basicSetInterv(newInterv, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, SafetyPackage.EQ_STATEMENT__INTERV, newInterv, newInterv));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Expr> getExprList()
  {
    if (exprList == null)
    {
      exprList = new EObjectContainmentEList<Expr>(Expr.class, this, SafetyPackage.EQ_STATEMENT__EXPR_LIST);
    }
    return exprList;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Element getOwner()
  {
    // TODO: implement this method
    // Ensure that you remove @generated or mark it @generated NOT
    throw new UnsupportedOperationException();
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
      case SafetyPackage.EQ_STATEMENT__OWNED_COMMENT:
        return ((InternalEList<?>)getOwnedComments()).basicRemove(otherEnd, msgs);
      case SafetyPackage.EQ_STATEMENT__LHS:
        return ((InternalEList<?>)getLhs()).basicRemove(otherEnd, msgs);
      case SafetyPackage.EQ_STATEMENT__EXPR:
        return basicSetExpr(null, msgs);
      case SafetyPackage.EQ_STATEMENT__INTERV:
        return basicSetInterv(null, msgs);
      case SafetyPackage.EQ_STATEMENT__EXPR_LIST:
        return ((InternalEList<?>)getExprList()).basicRemove(otherEnd, msgs);
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
      case SafetyPackage.EQ_STATEMENT__OWNED_ELEMENT:
        return getOwnedElements();
      case SafetyPackage.EQ_STATEMENT__OWNED_COMMENT:
        return getOwnedComments();
      case SafetyPackage.EQ_STATEMENT__LHS:
        return getLhs();
      case SafetyPackage.EQ_STATEMENT__EXPR:
        return getExpr();
      case SafetyPackage.EQ_STATEMENT__INTERV:
        return getInterv();
      case SafetyPackage.EQ_STATEMENT__EXPR_LIST:
        return getExprList();
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
      case SafetyPackage.EQ_STATEMENT__OWNED_COMMENT:
        getOwnedComments().clear();
        getOwnedComments().addAll((Collection<? extends Comment>)newValue);
        return;
      case SafetyPackage.EQ_STATEMENT__LHS:
        getLhs().clear();
        getLhs().addAll((Collection<? extends Arg>)newValue);
        return;
      case SafetyPackage.EQ_STATEMENT__EXPR:
        setExpr((Expr)newValue);
        return;
      case SafetyPackage.EQ_STATEMENT__INTERV:
        setInterv((TimeInterval)newValue);
        return;
      case SafetyPackage.EQ_STATEMENT__EXPR_LIST:
        getExprList().clear();
        getExprList().addAll((Collection<? extends Expr>)newValue);
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
      case SafetyPackage.EQ_STATEMENT__OWNED_COMMENT:
        getOwnedComments().clear();
        return;
      case SafetyPackage.EQ_STATEMENT__LHS:
        getLhs().clear();
        return;
      case SafetyPackage.EQ_STATEMENT__EXPR:
        setExpr((Expr)null);
        return;
      case SafetyPackage.EQ_STATEMENT__INTERV:
        setInterv((TimeInterval)null);
        return;
      case SafetyPackage.EQ_STATEMENT__EXPR_LIST:
        getExprList().clear();
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
      case SafetyPackage.EQ_STATEMENT__OWNED_ELEMENT:
        return !getOwnedElements().isEmpty();
      case SafetyPackage.EQ_STATEMENT__OWNED_COMMENT:
        return ownedComments != null && !ownedComments.isEmpty();
      case SafetyPackage.EQ_STATEMENT__LHS:
        return lhs != null && !lhs.isEmpty();
      case SafetyPackage.EQ_STATEMENT__EXPR:
        return expr != null;
      case SafetyPackage.EQ_STATEMENT__INTERV:
        return interv != null;
      case SafetyPackage.EQ_STATEMENT__EXPR_LIST:
        return exprList != null && !exprList.isEmpty();
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass)
  {
    if (baseClass == Element.class)
    {
      switch (derivedFeatureID)
      {
        case SafetyPackage.EQ_STATEMENT__OWNED_ELEMENT: return Aadl2Package.ELEMENT__OWNED_ELEMENT;
        case SafetyPackage.EQ_STATEMENT__OWNED_COMMENT: return Aadl2Package.ELEMENT__OWNED_COMMENT;
        default: return -1;
      }
    }
    if (baseClass == SpecStatement.class)
    {
      switch (derivedFeatureID)
      {
        default: return -1;
      }
    }
    if (baseClass == com.rockwellcollins.atc.agree.agree.EqStatement.class)
    {
      switch (derivedFeatureID)
      {
        case SafetyPackage.EQ_STATEMENT__LHS: return AgreePackage.EQ_STATEMENT__LHS;
        case SafetyPackage.EQ_STATEMENT__EXPR: return AgreePackage.EQ_STATEMENT__EXPR;
        default: return -1;
      }
    }
    return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass)
  {
    if (baseClass == Element.class)
    {
      switch (baseFeatureID)
      {
        case Aadl2Package.ELEMENT__OWNED_ELEMENT: return SafetyPackage.EQ_STATEMENT__OWNED_ELEMENT;
        case Aadl2Package.ELEMENT__OWNED_COMMENT: return SafetyPackage.EQ_STATEMENT__OWNED_COMMENT;
        default: return -1;
      }
    }
    if (baseClass == SpecStatement.class)
    {
      switch (baseFeatureID)
      {
        default: return -1;
      }
    }
    if (baseClass == com.rockwellcollins.atc.agree.agree.EqStatement.class)
    {
      switch (baseFeatureID)
      {
        case AgreePackage.EQ_STATEMENT__LHS: return SafetyPackage.EQ_STATEMENT__LHS;
        case AgreePackage.EQ_STATEMENT__EXPR: return SafetyPackage.EQ_STATEMENT__EXPR;
        default: return -1;
      }
    }
    return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
  }

} //EqStatementImpl
