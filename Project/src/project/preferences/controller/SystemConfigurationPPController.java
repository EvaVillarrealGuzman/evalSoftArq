package project.preferences.controller;

import project.preferences.SystemConfigurationPreferencePage;
import software.BusinessLogic.SystemConfigurationManager;

/**
 * Controller for NewSystemPreferencePage
 * 
 * @author Eva
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
				this.getForm().getDatabasePortNumber().getStringValue())) {
			this.createSuccessDialog("The connection is established to the database");
		} else {
			this.createErrorDialog("The connection is not established to the database");
		}
	}

	/**
	 * Configure the view
	 */
	public void getView() {
		this.getForm().getDatabasePassword().setStringValue(this.getManager().getPassword());
		this.getForm().getDatabasePortNumber().setStringValue(this.getManager().getPortNumber());
		this.getForm().getDatabaseUserName().setStringValue(this.getManager().getUserName());
	}

	public void updateConnectionData() {
		if (this.getManager().updateConnectionData(this.getForm().getDatabasePassword().getStringValue(),
				this.getForm().getDatabaseUserName().getStringValue(),
				this.getForm().getDatabasePortNumber().getStringValue())) {
			// TODO cambiar
			this.createObjectSuccessDialog();
		} else {
			this.createErrorDialog("could not be saved data");
		}
	}

}
