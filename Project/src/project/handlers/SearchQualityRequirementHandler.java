package project.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.PreferencesUtil;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class SearchQualityRequirementHandler extends AbstractHandler {

	/**
	 * The constructor.
	 */
	public SearchQualityRequirementHandler() {

	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) {
		PreferenceDialog pref = PreferencesUtil.createPreferenceDialogOn(
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
				"project.preferences.SearchQualityRequirementPreferencePage",
				new String[] { "project.preferences.SearchQualityRequirementPreferencePage" }, null);
		if (pref != null)
			pref.open();
		return null;
	}

}
