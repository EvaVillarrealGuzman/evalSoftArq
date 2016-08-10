package project.preferences.controller;

import project.preferences.NewSystemPreferencePage;
import software.BusinessLogic.AnalysisManager;

/**
 * Controller for NewSystemPreferencePage
 * @author Eva
 *
 */
public class NewSystemPPController extends Controller {

	/**
	 * Attributes
	 */
	private static NewSystemPPController controller;
	private AnalysisManager manager;
	private NewSystemPreferencePage form;

	/**
	 * Getters and Setters
	 */
	public static NewSystemPPController getController() {
		return controller;
	}

	public static void setController(NewSystemPPController controller) {
		NewSystemPPController.controller = controller;
	}

	public AnalysisManager getManager() {
		if (manager == null) {
			manager = new AnalysisManager();
		}
		return manager;
	}

	public void setManager(AnalysisManager manager) {
		this.manager = manager;
	}

	public NewSystemPreferencePage getForm() {
		return form;
	}

	public void setForm(NewSystemPreferencePage form) {
		this.form = form;
	}

	/**
	 * Create the system and prepare the view
	 */
	public void save() {
		int err;
		err = this.newSystem();
		if (err == 0) {
			this.getManager().saveSystem();
			this.getForm().prepareView(1);
		}
	}

	/**
	 * Create a new system
	 * @return int (indicates if the system was created successfully)
	 */
	public int newSystem() {
		if (this.isValidData()) {
			this.getManager().newSystem(this.getForm().getSystemName().getStringValue(),
					this.getForm().getProjectName().getStringValue(),
					convertDateTimeToDate(this.getForm().getCalendarStartDate()),
					convertDateTimeToDate(this.getForm().getCalendarFinishDate()), true);
			return 0;
		} else {
			return 1;
		}
	}

	/**
	 * Validate the necessary data for the creation of the system 
	 * @return boolean (is true if they have completed the required fields)
	 */
	public boolean isValidData() {
		if (this.isEmpty(this.getForm().getSystemName())) {
			this.createErrorDialog("System name empty");
			return false;
		}
		if (this.isEmpty(this.getForm().getProjectName())) {
			this.createErrorDialog("Project name empty");
			return false;
		} else if (isAfter(this.getForm().getCalendarStartDate(), this.getForm().getCalendarFinishDate())) {
			this.createErrorDialog("The finish date is less than the start date");
			getForm().getCalendarStartDate().setFocus();
			return false;
		}

		return true;
	}

}
