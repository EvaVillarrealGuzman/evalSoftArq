package Presentation.controllerConfiguration;

import BusinessLogic.SystemConfigurationManager;
import Presentation.Controller;
import Presentation.preferenceConfiguration.DatabaseConfigurationPreferencePage;
import Presentation.preferences.Messages;
import Presentation.preferences.PreferenceConstants;

/**
 * Controller for SystemConfigurationPreferencePage
 * 
 * @author FEM
 *
 */
public class DatabaseConfigurationPPController extends Controller {

	/**
	 * Attributes
	 */
	private SystemConfigurationManager manager;
	private DatabaseConfigurationPreferencePage form;

	/**s
	 * Getters and Setters
	 */
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
			this.createSuccessDialog(Messages.getString("UCM2DEVS_ConnectionDatabase_Dialog"));
		} else {
			this.getForm().getBtnSave().setEnabled(false);
			this.createErrorDialog(Messages.getString("UCM2DEVS_ConnectionDatabase_ErrorDialog") );
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
			this.createErrorDialog(Messages.getString("UCM2DEVS_SaveData_ErrorDialog") );
		}
	}

}
