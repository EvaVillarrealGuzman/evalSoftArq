package project.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.PreferencesUtil;

import software.Presentation.ControllerAnalysis.QualityRequirementController;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class NewSystemHandler extends AbstractHandler {
	private QualityRequirementController qualityRequirementController;
	/**
	 * The constructor.
	 */
	public NewSystemHandler() {

	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) {
        PreferenceDialog pref = PreferencesUtil.createPreferenceDialogOn(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
                "project.preferences.NewSystemPreferencePage", new String[] { "project.preferences.NewSystemPreferencePage" }, null); 
        if (pref != null)
            pref.open();
		//this.getQualityRequirementController().openFrmQualityRequirementManagement(3);
		return null;
	}
	
	public QualityRequirementController getQualityRequirementController() {
		qualityRequirementController = QualityRequirementController.getController();
		return qualityRequirementController;
	}
	
}