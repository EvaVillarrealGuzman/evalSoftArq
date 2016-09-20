package project.preferences.controller;

import project.preferences.PreferenceConstants;
import project.preferences.SystemConfigurationPreferencePage;
import software.BusinessLogic.SystemConfigurationManager;

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
	private static SystemConfigurationPreferencePage controller;
	private SystemConfigurationManager manager;
	private SystemConfigurationPreferencePage form;

	/**
	 * Getters and Setters
	 */
	public static SystemConfigurationPreferencePage getController() {
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

	public SystemConfigurationPreferencePage getForm() {
		return form;
	}

	public void setForm(SystemConfigurationPreferencePage form) {
		this.form = form;
	}

	public void testConnection() {
		if (this.getManager().isConnection(this.getForm().getDatabasePassword().getStringValue(),
				this.getForm().getDatabaseUserName().getStringValue(),
				this.getForm().getDatabasePortNumber().getStringValue(),
				this.getForm().getDatabaseName().getStringValue())) {
			this.createSuccessDialog("The connection is established to the database");
		} else {
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
