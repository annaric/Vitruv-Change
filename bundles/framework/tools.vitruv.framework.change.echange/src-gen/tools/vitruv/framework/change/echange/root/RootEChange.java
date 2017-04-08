/**
 */
package tools.vitruv.framework.change.echange.root;

import org.eclipse.emf.ecore.resource.Resource;

import tools.vitruv.framework.change.echange.AtomicEChange;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>EChange</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * Abstract EChange which inserts or removes a root element.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.change.echange.root.RootEChange#getUri <em>Uri</em>}</li>
 *   <li>{@link tools.vitruv.framework.change.echange.root.RootEChange#isFileCreatedOrDeleted <em>File Created Or Deleted</em>}</li>
 *   <li>{@link tools.vitruv.framework.change.echange.root.RootEChange#getResource <em>Resource</em>}</li>
 *   <li>{@link tools.vitruv.framework.change.echange.root.RootEChange#getIndex <em>Index</em>}</li>
 * </ul>
 *
 * @see tools.vitruv.framework.change.echange.root.RootPackage#getRootEChange()
 * @model abstract="true"
 * @generated
 */
public interface RootEChange extends AtomicEChange {
	/**
	 * Returns the value of the '<em><b>Uri</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * The uri of the resource.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Uri</em>' attribute.
	 * @see #setUri(String)
	 * @see tools.vitruv.framework.change.echange.root.RootPackage#getRootEChange_Uri()
	 * @model unique="false"
	 * @generated
	 */
	String getUri();

	/**
	 * Sets the value of the '{@link tools.vitruv.framework.change.echange.root.RootEChange#getUri <em>Uri</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Uri</em>' attribute.
	 * @see #getUri()
	 * @generated
	 */
	void setUri(String value);

	/**
	 * Returns the value of the '<em><b>File Created Or Deleted</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * The
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>File Created Or Deleted</em>' attribute.
	 * @see #setFileCreatedOrDeleted(boolean)
	 * @see tools.vitruv.framework.change.echange.root.RootPackage#getRootEChange_FileCreatedOrDeleted()
	 * @model unique="false"
	 * @generated
	 */
	boolean isFileCreatedOrDeleted();

	/**
	 * Sets the value of the '{@link tools.vitruv.framework.change.echange.root.RootEChange#isFileCreatedOrDeleted <em>File Created Or Deleted</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>File Created Or Deleted</em>' attribute.
	 * @see #isFileCreatedOrDeleted()
	 * @generated
	 */
	void setFileCreatedOrDeleted(boolean value);

	/**
	 * Returns the value of the '<em><b>Resource</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * The resource where the root element will be inserted or removed from.
	 * Is only valid in resolved changes.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Resource</em>' attribute.
	 * @see #setResource(Resource)
	 * @see tools.vitruv.framework.change.echange.root.RootPackage#getRootEChange_Resource()
	 * @model unique="false" dataType="tools.vitruv.framework.change.echange.root.Resource"
	 * @generated
	 */
	Resource getResource();

	/**
	 * Sets the value of the '{@link tools.vitruv.framework.change.echange.root.RootEChange#getResource <em>Resource</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Resource</em>' attribute.
	 * @see #getResource()
	 * @generated
	 */
	void setResource(Resource value);

	/**
	 * Returns the value of the '<em><b>Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * The index where the root element is inserted or removed.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Index</em>' attribute.
	 * @see #setIndex(int)
	 * @see tools.vitruv.framework.change.echange.root.RootPackage#getRootEChange_Index()
	 * @model unique="false"
	 * @generated
	 */
	int getIndex();

	/**
	 * Sets the value of the '{@link tools.vitruv.framework.change.echange.root.RootEChange#getIndex <em>Index</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Index</em>' attribute.
	 * @see #getIndex()
	 * @generated
	 */
	void setIndex(int value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns if all proxy EObjects of the change are resolved to concrete EObjects of a resource set.
	 * Needs to be true to apply the change.
	 * @return	All proxy EObjects are resolved to concrete EObjects.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return (super.isResolved() && (!<%com.google.common.base.Objects%>.equal(this.getResource(), null)));'"
	 * @generated
	 */
	boolean isResolved();

} // RootEChange
