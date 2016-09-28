package project.preferences;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.hibernate.exception.JDBCConnectionException;

import project.preferences.controller.SystemConfigurationPPController;

/**
 * To system configuration
 * 
 * @author: FEM
 */

public class DatabaseConfigurationPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {
	/**
	 * Attributes
	 */
	private Button btnSave;
	private Button btnTestConnection;
	private StringFieldEditor databasePortNumber;
	private StringFieldEditor databaseUserName;
	private StringFieldEditor databaseName;
	private StringFieldEditor databasePassword;
	private SystemConfigurationPPController viewController;
	private Composite cDatabaseConfiguration;
	private GridData gridData;

	/**
	 * Constructor
	 */
	public DatabaseConfigurationPreferencePage() {
		super(GRID);
		noDefaultAndApplyButton();
		viewController = new SystemConfigurationPPController();
		this.setViewController(viewController);
		this.getViewController().setForm(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.preference.FieldEditorPreferencePage#createContents(org
	 * .eclipse.swt.widgets.Composite)
	 */
	protected Control createContents(Composite parent) {
		try {
			GridLayout layout = new GridLayout();
			layout.numColumns = 1;
			parent.setLayout(layout);

			// Group for project properties
			Group groupDatabaseConfiguration = new Group(parent, SWT.SHADOW_ETCHED_IN);
			groupDatabaseConfiguration.setText(PreferenceConstants.DatabaseConfiguration_Label);
			groupDatabaseConfiguration.setLayout(layout);

			gridData = new GridData();
			gridData.horizontalAlignment = GridData.FILL;
			groupDatabaseConfiguration.setLayoutData(gridData);

			cDatabaseConfiguration = new Composite(groupDatabaseConfiguration, SWT.NONE);
			gridData = new GridData();
			gridData.grabExcessHorizontalSpace = true;
			gridData.horizontalAlignment = GridData.FILL;
			cDatabaseConfiguration.setLayoutData(gridData);

			databasePortNumber = new StringFieldEditor(PreferenceConstants.PortNumber_Label,
					PreferenceConstants.PortNumber_Label + ":", cDatabaseConfiguration);
			addField(databasePortNumber);
			databasePortNumber.getTextControl(cDatabaseConfiguration).addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					getBtnSave().setEnabled(false);
				}
			});

			databaseName = new StringFieldEditor(PreferenceConstants.DatabaseName_Label,
					PreferenceConstants.DatabaseName_Label + ":", cDatabaseConfiguration);
			addField(databaseName);
			databaseName.getTextControl(cDatabaseConfiguration).addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					getBtnSave().setEnabled(false);
				}
			});

			databaseUserName = new StringFieldEditor(PreferenceConstants.UserName_Label,
					PreferenceConstants.UserName_Label + ":", cDatabaseConfiguration);
			addField(databaseUserName);
			databaseUserName.getTextControl(cDatabaseConfiguration).addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					getBtnSave().setEnabled(false);
				}
			});

			databasePassword = new StringFieldEditor(PreferenceConstants.Password_Label,
					PreferenceConstants.Password_Label + ":", cDatabaseConfiguration) {
				@Override
				protected void doFillIntoGrid(Composite parent, int numColumns) {
					super.doFillIntoGrid(parent, numColumns);

					getTextControl().setEchoChar('*');
				}
			};
			addField(databasePassword);
			databasePassword.getTextControl(cDatabaseConfiguration).addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					getBtnSave().setEnabled(false);
				}
			});

			new Label(cDatabaseConfiguration, SWT.LEFT);

			gridData = new GridData();
			gridData.horizontalAlignment = GridData.END;
			gridData.verticalAlignment = SWT.BOTTOM;
			gridData.grabExcessHorizontalSpace = true;

			btnTestConnection = new Button(cDatabaseConfiguration, SWT.PUSH);
			btnTestConnection.setText(PreferenceConstants.ButtomTestConnection_Label);
			btnTestConnection.setLayoutData(gridData);
			btnTestConnection.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					viewController.testConnection();
				}
			});

			new Label(parent, SWT.LEFT);

			gridData = new GridData();
			gridData.horizontalSpan = 1;
			gridData.widthHint = 100;
			gridData.horizontalAlignment = GridData.END;
			gridData.verticalAlignment = SWT.BOTTOM;
			gridData.grabExcessHorizontalSpace = true;

			btnSave = new Button(parent, SWT.PUSH);
			btnSave.setText(PreferenceConstants.ButtomSave_Label);
			btnSave.setLayoutData(gridData);
			btnSave.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					viewController.updateConnectionData();
				}
			});

			this.prepareView();
		} catch (

		JDBCConnectionException e) {
			viewController.createErrorDialog(PreferenceConstants.Postgres_ErrorDialog);
		}

		return new Composite(parent, SWT.NULL);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.preference.FieldEditorPreferencePage#createFieldEditors
	 * ()
	 */
	@Override
	protected void createFieldEditors() {
	}

	/**
	 * Getters and Setters
	 */
	public SystemConfigurationPPController getViewController() {
		return viewController;
	}

	public void setViewController(SystemConfigurationPPController viewController) {
		this.viewController = viewController;
	}

	public Button getBtnSave() {
		return btnSave;
	}

	public void setBtnSave(Button btnSave) {
		this.btnSave = btnSave;
	}

	public Composite getcDatabaseConfiguration() {
		return cDatabaseConfiguration;
	}

	public void setcDatabaseConfiguration(Composite cDatabaseConfiguration) {
		this.cDatabaseConfiguration = cDatabaseConfiguration;
	}

	public Button getBtnTestConnection() {
		return btnTestConnection;
	}

	public void setBtnTestConnection(Button btnTestConnection) {
		this.btnTestConnection = btnTestConnection;
	}

	public StringFieldEditor getDatabasePortNumber() {
		return databasePortNumber;
	}

	public void setDatabasePortNumber(StringFieldEditor databasePortNumber) {
		this.databasePortNumber = databasePortNumber;
	}

	public StringFieldEditor getDatabaseUserName() {
		return databaseUserName;
	}

	public void setDatabaseUserName(StringFieldEditor databaseUserName) {
		this.databaseUserName = databaseUserName;
	}

	public StringFieldEditor getDatabasePassword() {
		return databasePassword;
	}

	public void setDatabasePassword(StringFieldEditor databasePassword) {
		this.databasePassword = databasePassword;
	}

	public StringFieldEditor getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(StringFieldEditor databaseName) {
		this.databaseName = databaseName;
	}

	/**
	 * prepare the view
	 * 
	 */
	public void prepareView() {
		this.getViewController().getView();
		this.getBtnSave().setEnabled(false);
	}
}