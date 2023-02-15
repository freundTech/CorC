/**
 */
package de.tu_bs.cs.isf.cbc.cbcmodel;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Base Variable</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.BaseVariable#getName <em>Name</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.BaseVariable#getType <em>Type</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.BaseVariable#isIsNonNull <em>Is Non Null</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.BaseVariable#getDisplayName <em>Display Name</em>}</li>
 * </ul>
 *
 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getBaseVariable()
 * @model abstract="true"
 * @generated
 */
public interface BaseVariable extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * The default value is <code>"int a"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getBaseVariable_Name()
	 * @model default="int a"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.BaseVariable#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see #setType(String)
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getBaseVariable_Type()
	 * @model
	 * @generated
	 */
	String getType();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.BaseVariable#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see #getType()
	 * @generated
	 */
	void setType(String value);

	/**
	 * Returns the value of the '<em><b>Is Non Null</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Is Non Null</em>' attribute.
	 * @see #setIsNonNull(boolean)
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getBaseVariable_IsNonNull()
	 * @model
	 * @generated
	 */
	boolean isIsNonNull();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.BaseVariable#isIsNonNull <em>Is Non Null</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Non Null</em>' attribute.
	 * @see #isIsNonNull()
	 * @generated
	 */
	void setIsNonNull(boolean value);

	/**
	 * Returns the value of the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Display Name</em>' attribute.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getBaseVariable_DisplayName()
	 * @model changeable="false" derived="true"
	 * @generated
	 */
	String getDisplayName();

} // BaseVariable
