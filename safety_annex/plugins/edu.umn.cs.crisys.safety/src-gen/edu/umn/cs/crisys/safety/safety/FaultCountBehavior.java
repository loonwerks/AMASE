/**
 * Copyright (c) 2021, Collins Aerospace.
 * Developed with the sponsorship of Defense Advanced Research Projects Agency (DARPA).
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this data, 
 * including any software or models in source or binary form, as well as any drawings, specifications, 
 * and documentation (collectively "the Data"), to deal in the Data without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, 
 * and/or sell copies of the Data, and to permit persons to whom the Data is furnished to do so, 
 * subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or 
 * substantial portions of the Data.
 * 
 * THE DATA IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT 
 * LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. 
 * IN NO EVENT SHALL THE AUTHORS, SPONSORS, DEVELOPERS, CONTRIBUTORS, OR COPYRIGHT HOLDERS BE LIABLE 
 * FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, 
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE DATA OR THE USE OR OTHER DEALINGS IN THE DATA.
 * 
 * Generated by Xtext version 2.25.0.
 */
package edu.umn.cs.crisys.safety.safety;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Fault Count Behavior</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.umn.cs.crisys.safety.safety.FaultCountBehavior#getMaxFaults <em>Max Faults</em>}</li>
 * </ul>
 *
 * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getFaultCountBehavior()
 * @model
 * @generated
 */
public interface FaultCountBehavior extends AnalysisBehavior
{
  /**
   * Returns the value of the '<em><b>Max Faults</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Max Faults</em>' attribute.
   * @see #setMaxFaults(String)
   * @see edu.umn.cs.crisys.safety.safety.SafetyPackage#getFaultCountBehavior_MaxFaults()
   * @model
   * @generated
   */
  String getMaxFaults();

  /**
   * Sets the value of the '{@link edu.umn.cs.crisys.safety.safety.FaultCountBehavior#getMaxFaults <em>Max Faults</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Max Faults</em>' attribute.
   * @see #getMaxFaults()
   * @generated
   */
  void setMaxFaults(String value);

} // FaultCountBehavior
