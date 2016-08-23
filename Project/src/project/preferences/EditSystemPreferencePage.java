package project.preferences;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.hibernate.exception.JDBCConnectionException;

import project.preferences.controller.EditSystemPPController;

/**
 * To search, consult, edit or remove a system
 * @author: Eva 
 */

public class EditSystemPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {
	/**
	 * Attributes
	 */
	private DateTime calendarStartDate;
	private DateTime calendarFinishDate;
	private Label lblCalendarStarDate;
	private Label lblCalendarFinishDate;
	private Button btnSave;
	private Button btnRemove;
	private Button btnEdit;
	private ComboViewer cmbSystem;
	private StringFieldEditor projectName;
	private static EditSystemPreferencePage SystemPP; // NOPMD by Usuario-Pc on 10/06/16 21:50
	private EditSystemPPController viewController;
	private Composite cProject;
	private Composite cSystemName;
	private GridData gridData;

	/**
	 * Constructor
	 */
	public EditSystemPreferencePage() {
		super(GRID);
		noDefaultAndApplyButton();
		viewController = new EditSystemPPController();
		this.setViewController(viewController); // NOPMD by Usuario-Pc on 10/06/16 21:49
		this.getViewController().setForm(this);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.preference.FieldEditorPreferencePage#createContents(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createContents(Composite parent) {
		try {
			GridLayout layout = new GridLayout();
			layout.numColumns = 4;
			parent.setLayout(layout);

			cSystemName = new Composite(parent, SWT.NULL);
			cSystemName.setLayout(layout);
			gridData = new GridData();
			gridData.horizontalSpan = 4;
			gridData.horizontalAlignment = GridData.FILL;
			cSystemName.setLayoutData(gridData);

			Label labelSn = new Label(cSystemName, SWT.NONE);
			labelSn.setText("System Name: ");

			gridData = new GridData();
			gridData.horizontalAlignment = GridData.FILL;
			gridData.grabExcessHorizontalSpace = true;

			cmbSystem = new ComboViewer(cSystemName, SWT.READ_ONLY);
			cmbSystem.setContentProvider(ArrayContentProvider.getInstance());
			cmbSystem.getCombo().setLayoutData(gridData);
			cmbSystem.getCombo().addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					viewController.setModel(cmbSystem);
					viewController.getView();
					prepareView(1);
				}
			});
			
			gridData = new GridData();
			gridData.horizontalSpan = 4;

			Label labelEmptyOne = new Label(parent, SWT.NULL);
			labelEmptyOne.setLayoutData(gridData);

			// Group for project properties
			Group groupProject = new Group(parent, SWT.SHADOW_ETCHED_IN);
			groupProject.setText("Project");
			groupProject.setLayout(layout);

			gridData = new GridData();
			gridData.horizontalAlignment = GridData.FILL;
			gridData.horizontalSpan = 4;
			groupProject.setLayoutData(gridData);

			cProject = new Composite(groupProject, SWT.NONE);
			gridData = new GridData();
			gridData.horizontalSpan = 4;
			gridData.grabExcessHorizontalSpace = true;
			gridData.horizontalAlignment = GridData.FILL;
			cProject.setLayoutData(gridData);

			projectName = new StringFieldEditor(PreferenceConstants.P_STRING, "Project Name: ", cProject);

			addField(projectName);

			lblCalendarStarDate = new Label(cProject, SWT.NONE);
			lblCalendarStarDate.setText("Start Date");

			calendarStartDate = new DateTime(cProject, SWT.DATE | SWT.DROP_DOWN);

			lblCalendarFinishDate = new Label(cProject, SWT.NONE);
			lblCalendarFinishDate.setText("Finish Date");

			calendarFinishDate = new DateTime(cProject, SWT.DATE | SWT.DROP_DOWN);

			gridData = new GridData();
			gridData.horizontalSpan = 4;

			Label labelEmptyTwo = new Label(parent, SWT.NULL);
			labelEmptyTwo.setLayoutData(gridData);
			
			gridData = new GridData();
			gridData.horizontalSpan = 2;

			Label labelEmptyTre = new Label(parent, SWT.NULL);
			labelEmptyTre.setLayoutData(gridData);
			
			gridData = new GridData();
			gridData.horizontalSpan = 1;
			gridData.widthHint = 100;
			gridData.horizontalAlignment = GridData.END;
			gridData.verticalAlignment = SWT.BOTTOM;
			gridData.grabExcessHorizontalSpace = true;

			btnSave = new Button(parent, SWT.PUSH);
			btnSave.setText(" Save ");
			btnSave.setLayoutData(gridData);
			btnSave.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					viewController.save();
				}
			});

			gridData = new GridData();
			gridData.horizontalSpan = 1;
			gridData.widthHint = 100;
			gridData.horizontalAlignment = GridData.END;
			gridData.verticalAlignment = SWT.BOTTOM;
			gridData.grabExcessHorizontalSpace = true;

			btnRemove = new Button(parent, SWT.PUSH);
			btnRemove.setText("Remove");
			btnRemove.setLayoutData(gridData);
			btnRemove.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					if (viewController.createDeleteDialog() == true) {
						viewController.remove();	
					}
				}
			});

			this.prepareView(0);

		} catch (JDBCConnectionException e) {
			viewController.createErrorDialog("Postgres service is not running");
		}

		return new Composite(parent, SWT.NULL);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.preference.FieldEditorPreferencePage#createFieldEditors()
	 */
	@Override
	protected void createFieldEditors() {
	}

	/**
	 * Getters and Setters
	 */
	public DateTime getCalendarStartDate() {
		return calendarStartDate;
	}

	public void setCalendarStartDate(DateTime calendarStartDate) {
		this.calendarStartDate = calendarStartDate;
	}

	public DateTime getCalendarFinishDate() {
		return calendarFinishDate;
	}

	public void setCalendarFinishDate(DateTime calendarFinishDate) {
		this.calendarFinishDate = calendarFinishDate;
	}

	public Label getLblCalendarStarDate() {
		return lblCalendarStarDate;
	}

	public void setLblCalendarStarDate(Label lblCalendarStarDate) {
		this.lblCalendarStarDate = lblCalendarStarDate;
	}

	public Label getLblCalendarFinishDate() {
		return lblCalendarFinishDate;
	}

	public void setLblCalendarFinishDate(Label lblCalendarFinishDate) {
		this.lblCalendarFinishDate = lblCalendarFinishDate;
	}

	public Button getBtnSave() {
		return btnSave;
	}

	public void setBtnSave(Button btnSave) {
		this.btnSave = btnSave;
	}

	public Button getBtnRemove() {
		return btnRemove;
	}

	public void setBtnRemove(Button btnRemove) {
		this.btnRemove = btnRemove;
	}

	public Button getBtnEdit() {
		return btnEdit;
	}

	public void setBtnEdit(Button btnEdit) {
		this.btnEdit = btnEdit;
	}

	public ComboViewer getCboSystem() {
		return cmbSystem;
	}

	public void setCboSystem(ComboViewer cboSystem) {
		this.cmbSystem = cboSystem;
	}

	public StringFieldEditor getProjectName() {
		return projectName;
	}

	public void setProjectName(StringFieldEditor projectName) {
		this.projectName = projectName;
	}

	public static EditSystemPreferencePage getSystemPP() {
		return SystemPP;
	}

	public static void setSystemPP(EditSystemPreferencePage systemPP) {
		SystemPP = systemPP;
	}

	public EditSystemPPController getViewController() {
		return viewController;
	}

	public void setViewController(EditSystemPPController viewController) {
		this.viewController = viewController;
	}

	public Composite getcProject() {
		return cProject;
	}

	public void setcProject(Composite cProject) {
		this.cProject = cProject;
	}

	// TODO ver
	public Composite getParent() {
		return getParent();
	}

	/**
	 * load combo with system whit state=true
	 */
	public void loadCombo() {
		this.getViewController().setModel();
	}

	/**
	 * prepare the view for the different actions that are possible
	 * @param pabm
	 */
	public void prepareView(int pabm) { // NOPMD by Usuario-Pc on 11/06/16 12:34
		this.getCboSystem().getCombo().setFocus();
		if (!getViewController().getManager().existSystemTrue()) {
			this.getViewController().createErrorDialog("No saved systems");
			pabm = 0;
		}
		switch (pabm) {
		case 0:// Without system
			this.getCalendarStartDate().setEnabled(false);
			this.getCalendarFinishDate().setEnabled(false);
			this.getProjectName().getTextControl(this.getcProject()).setEnabled(false);
			this.getBtnRemove().setEnabled(false);
			this.getBtnSave().setEnabled(false);
			projectName.getTextControl(cProject).setText("");
			loadCombo();
			break;
		case 1:// With system
			this.getCalendarStartDate().setEnabled(true);
			this.getCalendarFinishDate().setEnabled(true);
			this.getProjectName().getTextControl(this.getcProject()).setEnabled(true);
			this.getBtnRemove().setEnabled(true);
			this.getBtnSave().setEnabled(true);
			break;
		}

	}
}