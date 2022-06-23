package tools.vitruv.change.composite.propagation;

import tools.vitruv.change.composite.description.PropagatedChange;
import tools.vitruv.change.composite.description.VitruviusChange;

/**
 * Listener for the change propagation status.
 */
public interface ChangePropagationListener {

	/**
	 * Called before the change propagation is started.
	 * 
	 * @param changeToPropagate the change to be propagated, must not be <code>null</code>
	 */
	void startedChangePropagation(VitruviusChange changeToPropagate);

	/**
	 * Called after the change propagation is finished.
	 * 
	 * @param propagatedChanges the changes that have been generated during propagation, must not be <code>null</code>
	 */
	void finishedChangePropagation(Iterable<PropagatedChange> propagatedChanges);

	/**
	 * Called if the change propagation has been aborted.
	 *
	 * @param cause The cause for the abortion.
	 */
	void abortedChangePropagation(ChangePropagationAbortCause cause);

}
