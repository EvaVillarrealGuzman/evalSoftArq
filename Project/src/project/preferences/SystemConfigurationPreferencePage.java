package project.preferences;

import java.util.Locale;
import java.util.ResourceBundle;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
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
 * To create a new system
 * 
 * @author: Eva
 */

public class SystemConfigurationPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {
	/**
	 * Attributes
	 */
	private Button btnSave;
	private Button btnTestConnection;
	private StringFieldEditor databasePortNumber;
	private StringFieldEditor databaseUserName;
	private StringFieldEditor databasePassword;
	private SystemConfigurationPPController viewController;
	private Composite cDatabaseConfiguration;
	private Composite cSystemName;
	private ComboViewer cmbLenguajeSystem;
	private GridData gridData;

	/**
	 * Constructor
	 */
	public SystemConfigurationPreferencePage() {
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

			cSystemName = new Composite(parent, SWT.NULL);
			cSystemName.setLayout(layout);
			gridData = new GridData();
			gridData.horizontalAlignment = GridData.FILL;
			cSystemName.setLayoutData(gridData);

			Label labelSn = new Label(cSystemName, SWT.NONE);
			labelSn.setText("Language" + ":");

			gridData = new GridData();
			gridData.horizontalAlignment = GridData.FILL;
			gridData.grabExcessHorizontalSpace = true;

			cmbLenguajeSystem = new ComboViewer(cSystemName, SWT.READ_ONLY);
			cmbLenguajeSystem.setContentProvider(ArrayContentProvider.getInstance());
			cmbLenguajeSystem.getCombo().setLayoutData(gridData);
			loadCombo();
			cmbLenguajeSystem.getCombo().addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					if (((IStructuredSelection) cmbLenguajeSystem.getSelection()).getFirstElement().toString()
							.equals("English")) {
						ResourceBundle bundle1 = ResourceBundle.getBundle("messages");
					} else if (((IStructuredSelection) cmbLenguajeSystem.getSelection()).getFirstElement().toString()
							.equals("Spanish")) {
						Locale spanishLocale = new Locale("es", "AR");
						ResourceBundle bundle3 = ResourceBundle.getBundle("messages", spanishLocale);
					}
				}
			});

			new Label(parent, SWT.NULL);

			// Group for project properties
			Group groupDatabaseConfiguration = new Group(parent, SWT.SHADOW_ETCHED_IN);
			groupDatabaseConfiguration.setText("Database Configuration");
			groupDatabaseConfiguration.setLayout(layout);

			gridData = new GridData();
			gridData.horizontalAlignment = GridData.FILL;
			groupDatabaseConfiguration.setLayoutData(gridData);

			cDatabaseConfiguration = new Composite(groupDatabaseConfiguration, SWT.NONE);
			gridData = new GridData();
			gridData.grabExcessHorizontalSpace = true;
			gridData.horizontalAlignment = GridData.FILL;
			cDatabaseConfiguration.setLayoutData(gridData);

			databasePortNumber = new StringFieldEditor("Port Number", "Port Number" + ":", cDatabaseConfiguration);
			addField(databasePortNumber);

			databaseUserName = new StringFieldEditor("User name", "User name" + ":", cDatabaseConfiguration);
			addField(databaseUserName);

			databasePassword = new StringFieldEditor("Password", "Password" + ":", cDatabaseConfiguration) {
				@Override
				protected void doFillIntoGrid(Composite parent, int numColumns) {
					super.doFillIntoGrid(parent, numColumns);

					getTextControl().setEchoChar('*');
				}
			};

			addField(databasePassword);

			gridData = new GridData();
			gridData.horizontalSpan = 1;
			gridData.horizontalAlignment = GridData.END;
			gridData.grabExcessHorizontalSpace = true;
			gridData.widthHint = 75;

			btnTestConnection = new Button(cDatabaseConfiguration, SWT.PUSH);
			btnTestConnection.setText("Test Connection");
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

			this.prepareView(0);
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

	public ComboViewer getCmbLenguajeSystem() {
		return cmbLenguajeSystem;
	}

	public void setCmbLenguajeSystem(ComboViewer cmbLenguajeSystem) {
		this.cmbLenguajeSystem = cmbLenguajeSystem;
	}

	/**
	 * load combo with system whit state=true
	 */
	public void loadCombo() {
		String[] lenguages = { "English", "Spanish" };
		cmbLenguajeSystem.setInput(lenguages);
	}

	/**
	 * prepare the view for the different actions that are possible
	 * 
	 * @param pabm
	 */
	public void prepareView(int pabm) {
		switch (pabm) {
		case 1:
			this.getViewController().getView();
			break;
		case 0:
			this.getViewController().getView();
			break;
		}

	}
}