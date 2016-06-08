package project.preferences.controller;

import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredSelection;

import project.preferences.EditSystemPreferencePage;
import software.BusinessLogic.AnalysisManager;

public class SystemPPController extends Controller {

	/**
	 * Attributes
	 */
	private static SystemPPController controller;
	private AnalysisManager manager;
	private EditSystemPreferencePage form;

	/**
	 * Getters and Setters
	 */
	public static SystemPPController getController() {
		return controller;
	}

	public static void setController(SystemPPController controller) {
		SystemPPController.controller = controller;
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

	public EditSystemPreferencePage getForm() {
		return form;
	}

	public void setForm(EditSystemPreferencePage form) {
		this.form = form;
	}

	public void save() {
		int err;
		err = this.setSystem();
		if (err == 0) {
			this.getManager().updateSystem();
		}
	}
	
	public void remove() {
		this.getManager().removeSystem();
	}

	/**
	 * Open the form
	 * 
	 * @param pabm
	 */
	public void open() {
		// TODO
	}

	/**
	 * Update a system
	 */
	public int setSystem() {
		if (this.isValidData()) {
			this.getManager().setSystemName(
					((IStructuredSelection) this.getForm().getCboSystem().getSelection()).getFirstElement().toString());
			this.getManager().setProjectName(this.getForm().getProjectName().getStringValue());
			this.getManager().setStartDate(convertDateTimeToDate(this.getForm().getCalendarStartDate()));
			this.getManager().setFinishDate(convertDateTimeToDate(this.getForm().getCalendarFinishDate()));
			return 0;
		} else {
			return 1;
		}
	}

	/**
	 * Configure the form when a Combo´s item is selected
	 */
	public void getView() {
		this.getForm().getProjectName().setStringValue(this.getManager().getProjectName());
		this.getForm().getCalendarStartDate().setDay(getDay(this.getManager().getStartDate()));
		this.getForm().getCalendarStartDate().setMonth(getMonth(this.getManager().getStartDate()));
		this.getForm().getCalendarStartDate().setYear(getYear(this.getManager().getStartDate()));
		this.getForm().getCalendarFinishDate().setDay(getDay(this.getManager().getFinishDate()));
		this.getForm().getCalendarFinishDate().setMonth(getMonth(this.getManager().getFinishDate()));
		this.getForm().getCalendarFinishDate().setYear(getYear(this.getManager().getFinishDate()));
	}
	// TODO ver los showoptioDialog
	/**
	 * return true if they have completed the required fields
	 */
	public boolean isValidData() {
		if (this.isEmpty(this.getForm().getCboSystem())) {
			this.createErrorDialog("System name empty");
			this.getForm().getCboSystem().getCombo().setFocus();
			return false;
		}
		if (this.isEmpty(this.getForm().getProjectName())) {
			this.createErrorDialog("Empty project name");
			this.getForm().getProjectName().getTextControl(this.getForm().getParent()).setFocus();
			return false;
		} else if (isAfter(this.getForm().getCalendarStartDate(), this.getForm().getCalendarFinishDate())) {
			this.createErrorDialog("The finish date is less than the start date");
			getForm().getCalendarStartDate().setFocus();
			return false;
		}

		return true;
	}

	/**
	 * Sets the model of system combo
	 */
	public void setModel() {
		this.getForm().getCboSystem().setInput(getManager().getComboModelSystem());
	}

	public void setModel(ComboViewer pcmb) {
		this.setModel((software.DomainModel.AnalysisEntity.System) ((IStructuredSelection) pcmb.getSelection())
				.getFirstElement());
	}

	private void setModel(software.DomainModel.AnalysisEntity.System pmodel) {
		this.getManager().setSystem(pmodel);
	}

}
