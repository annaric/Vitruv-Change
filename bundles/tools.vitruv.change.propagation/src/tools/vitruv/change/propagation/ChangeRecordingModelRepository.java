package tools.vitruv.change.propagation;

import tools.vitruv.change.atomic.uuid.UuidResolver;
import tools.vitruv.change.composite.description.TransactionalChange;
import tools.vitruv.change.composite.description.VitruviusChange;
import tools.vitruv.change.correspondence.Correspondence;
import tools.vitruv.change.correspondence.model.CorrespondenceModel;
import tools.vitruv.change.correspondence.view.EditableCorrespondenceModelView;

public interface ChangeRecordingModelRepository extends ResourceAccess, AutoCloseable {
	/**
	 * Returns the correspondence model managed by this repository and used for
	 * transformations between the models managed in this repository.
	 * 
	 * @return the {@link CorrespondenceModel} managed by this repository
	 */
	EditableCorrespondenceModelView<Correspondence> getCorrespondenceModel();
	
	/**
	 * Returns the {@link UuidResolver} associated with all model resources in this repository.
	 */
	UuidResolver getUuidResolver();

	/**
	 * Applies the given change to this model repository. It resolves the change
	 * against the models in this repository and applies it afterwards.
	 * 
	 * @param change - the {@link VitrivusChange} to apply, must be unresolved
	 * @return the resolved and applied {@link VitruviusChange}
	 * @throws IllegalStateException if the the given change is resolved
	 */
	VitruviusChange applyChange(VitruviusChange change);

	/**
	 * Records the changes performed to the models in the repository while executing
	 * the given <code>changeApplicator</code>.
	 * 
	 * @param changeApplicator - the function applying changes to the models, must
	 *                         not be <code>null</code>
	 * @return the list of {@link TransactionalChange} containing the performed
	 *         changes, one for each metamodel
	 */
	Iterable<TransactionalChange> recordChanges(Runnable changeApplicator);
}
