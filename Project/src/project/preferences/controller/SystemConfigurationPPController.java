package project.preferences.controller;

import BusinessLogic.SystemConfigurationManager;
import project.preferences.DatabaseConfigurationPreferencePage;
import project.preferences.PreferenceConstants;

/**
 * Controller for SystemConfigurationPreferencePage
 * 
 * @author FEM
 *
 */
public class SystemConfigurationPPController extends Controller {

	/**
	 * Attributes
	 */
	private static DatabaseConfigurationPreferencePage controller;
	private SystemConfigurationManager manager;
	private DatabaseConfigurationPreferencePage form;

	/**
	 * Getters and Setters
	 */
	public static DatabaseConfigurationPreferencePage getController() {
		return controller;
	}

	public SystemConfigurationManager getManager() {
		if (manager == null) {
			manager = new SystemConfigurationManager();
		}
		return manager;
	}

	public void setManager(SystemConfigurationManager manager) {
		this.manager = manager;
	}

	public DatabaseConfigurationPreferencePage getForm() {
		return form;
	}

	public void setForm(DatabaseConfigurationPreferencePage form) {
		this.form = form;
	}

	public void testConnection() {
		if (this.getManager().isConnection(this.getForm().getDatabasePassword().getStringValue(),
				this.getForm().getDatabaseUserName().getStringValue(),
				this.getForm().getDatabasePortNumber().getStringValue(),
				this.getForm().getDatabaseName().getStringValue())) {
			this.getForm().getBtnSave().setEnabled(true);
			this.createSuccessDialog("The connection is established to the database");
		} else {
			this.getForm().getBtnSave().setEnabled(false);
			this.createErrorDialog(PreferenceConstants.ConnectionDatabase_ErrorDialog);
		}
	}

	/**
	 * Configure the view
	 */
	public void getView() {
		this.getForm().getDatabasePassword().setStringValue(this.getManager().getPassword());
		this.getForm().getDatabasePortNumber().setStringValue(this.getManager().getPortNumber());
		this.getForm().getDatabaseUserName().setStringValue(this.getManager().getUserName());
		this.getForm().getDatabaseName().setStringValue(this.getManager().getDatabaseName());
	}

	public void updateConnectionData() {
		if (this.getManager().updateConnectionData(this.getForm().getDatabasePassword().getStringValue(),
				this.getForm().getDatabaseUserName().getStringValue(),
				this.getForm().getDatabasePortNumber().getStringValue(),
				this.getForm().getDatabaseName().getStringValue())) {
			this.createObjectSuccessDialog();
		} else {
			this.createErrorDialog(PreferenceConstants.SaveData_ErrorDialog);
		}
	}

}
