package tools.vitruv.framework.userinteraction.impl;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import tools.vitruv.framework.change.interaction.UserInteractionBase;
import tools.vitruv.framework.userinteraction.ConfirmationDialogBuilder;
import tools.vitruv.framework.userinteraction.InternalUserInteractor;
import tools.vitruv.framework.userinteraction.MultipleChoiceMultiSelectionDialogBuilder;
import tools.vitruv.framework.userinteraction.MultipleChoiceSingleSelectionDialogBuilder;
import tools.vitruv.framework.userinteraction.NotificationDialogBuilder;
import tools.vitruv.framework.userinteraction.TextInputDialogBuilder;
import tools.vitruv.framework.userinteraction.UserInteractionListener;

/**
 * Implementation of the {@link InternalUserInteractor} interface providing dialog builders for different cases of user
 * interaction. Through the {@link UserInteractionListener}, this class keeps track of user input entered
 * through any of the dialogs constructed from here to be retrieved for bookkeeping/reuse.
 * 
 * @author Dominik Klooz
 */
public class UserInteractorImpl implements InternalUserInteractor, UserInteractionListener {
    private Display display;
    private Shell shell;
    private Queue<UserInteractionBase> userInteractions = new LinkedList<>();
    private UserInteractionListener userInputListener;

    public UserInteractorImpl() {
        this.init();
    }

    public void init() {
        this.display = PlatformUI.getWorkbench().getDisplay();
        this.shell = display.getActiveShell();
    }
    
    @Override
    public void registerUserInputListener(UserInteractionListener userInputListener) {
    	this.userInputListener = userInputListener;
    }
    
    @Override
	public NotificationDialogBuilder getNotificationDialogBuilder() {
		return new NotificationDialogBuilderImpl(shell, display);
	}

	@Override
	public ConfirmationDialogBuilder getConfirmationDialogBuilder() {
		return new ConfirmationDialogBuilderImpl(shell, display, userInputListener);
	}

	@Override
	public TextInputDialogBuilder getTextInputDialogBuilder() {
		return new TextInputDialogBuilderImpl(shell, display, userInputListener);
	}

	@Override
	public MultipleChoiceSingleSelectionDialogBuilder getSingleSelectionDialogBuilder() {
		return new MultipleChoiceSingleSelectionDialogBuilderImpl(shell, display, userInputListener);
	}
	
	@Override
	public MultipleChoiceMultiSelectionDialogBuilder getMultiSelectionDialogBuilder() {
		return new MultipleChoiceMultiSelectionDialogBuilderImpl(shell, display, userInputListener);
	}
	
	@Override
	public void onUserInteractionReceived(UserInteractionBase input) {
		userInteractions.add(input);
	}

	@Override
	public Collection<UserInteractionBase> getUserInteractions() {
		return userInteractions;
	}
	
	@Override
	public void resetUserInteractions() {
		userInteractions.clear();
	}

	@Override
	public Shell getShell() {
		return shell;
	}

	@Override
	public Display getDisplay() {
		return display;
	}
}
