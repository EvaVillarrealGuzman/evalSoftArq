package Presentation.preferenceAnalysis;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
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

import Presentation.controllerAnalysis.NewSystemPPController;
import Presentation.preferences.PreferenceConstants;

/**
 * To create a new system
 * 
 * @author: FEM
 */

public class NewSystemPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {
	/**
	 * Attributes
	 */
	private DateTime calendarStartDate;
	private DateTime calendarFinishDate;
	private Label lblCalendarStarDate;
	private Label lblCalendarFinishDate;
	private Button btnNew;
	private StringFieldEditor systemName;
	private StringFieldEditor projectName;
	private NewSystemPPController viewController;
	private Composite cProject;
	private Composite cSystemName;
	private GridData gridData;

	/**
	 * Constructor
	 */
	public NewSystemPreferencePage() {
		super(GRID);
		noDefaultAndApplyButton();
		viewController = new NewSystemPPController();
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

			systemName = new StringFieldEditor(PreferenceConstants.SystemName_Label,
					PreferenceConstants.SystemName_Label + ":", cSystemName);

			new Label(parent, SWT.NULL);

			// Group for project properties
			Group groupProject = new Group(parent, SWT.SHADOW_ETCHED_IN);
			groupProject.setText(PreferenceConstants.CompositeProject_Label);
			groupProject.setLayout(layout);

			gridData = new GridData();
			gridData.horizontalAlignment = GridData.FILL;
			groupProject.setLayoutData(gridData);

			cProject = new Composite(groupProject, SWT.NONE);
			gridData = new GridData();
			gridData.grabExcessHorizontalSpace = true;
			gridData.horizontalAlignment = GridData.FILL;
			cProject.setLayoutData(gridData);

			projectName = new StringFieldEditor(PreferenceConstants.ProjectName_Label,
					PreferenceConstants.ProjectName_Label + ":", cProject);

			addField(projectName);

			lblCalendarStarDate = new Label(cProject, SWT.NONE);
			lblCalendarStarDate.setText(PreferenceConstants.CalendarStarDate_Label + ":");

			calendarStartDate = new DateTime(cProject, SWT.DATE | SWT.DROP_DOWN);

			lblCalendarFinishDate = new Label(cProject, SWT.NONE);
			lblCalendarFinishDate.setText(PreferenceConstants.CalendarFinishDate_Label + ":");

			calendarFinishDate = new DateTime(cProject, SWT.DATE | SWT.DROP_DOWN);

			new Label(parent, SWT.LEFT);

			gridData = new GridData();
			gridData.horizontalSpan = 1;
			gridData.widthHint = 100;
			gridData.horizontalAlignment = GridData.END;
			gridData.verticalAlignment = SWT.BOTTOM;
			gridData.grabExcessHorizontalSpace = true;

			btnNew = new Button(parent, SWT.PUSH);
			btnNew.setText(PreferenceConstants.ButtomSave_Label);
			btnNew.setLayoutData(gridData);
			btnNew.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					if (viewController.save()) {
						viewController.createObjectSuccessDialog();
					} else {
						viewController.createObjectDontSaveErrorDialog();
					}
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

	public Button getBtnNew() {
		return btnNew;
	}

	public void setBtnNew(Button btnNew) {
		this.btnNew = btnNew;
	}

	public StringFieldEditor getSystemName() {
		return systemName;
	}

	public void setSystemName(StringFieldEditor systemName) {
		this.systemName = systemName;
	}

	public StringFieldEditor getProjectName() {
		return projectName;
	}

	public void setProjectName(StringFieldEditor projectName) {
		this.projectName = projectName;
	}

	public NewSystemPPController getViewController() {
		return viewController;
	}

	public void setViewController(NewSystemPPController viewController) {
		this.viewController = viewController;
	}

	public Composite getcProject() {
		return cProject;
	}

	public void setcProject(Composite cProject) {
		this.cProject = cProject;
	}

	/**
	 * prepare the view for the different actions that are possible
	 * 
	 * @param pabm
	 */
	public void prepareView(int pabm) {
		this.getSystemName().getTextControl(cSystemName).setFocus();
		switch (pabm) {
		case 1:// System created
			Calendar currentDate = GregorianCalendar.getInstance();
			this.getCalendarStartDate().setDate(currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH),
					currentDate.get(Calendar.DAY_OF_MONTH));
			this.getCalendarFinishDate().setDate(currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH),
					currentDate.get(Calendar.DAY_OF_MONTH));
			this.getProjectName().getTextControl(this.getcProject()).setText("");
			this.getSystemName().getTextControl(cSystemName).setText("");
			this.getBtnNew().setEnabled(true);
			break;
		case 0:// New system
			this.getCalendarStartDate().setEnabled(true);
			this.getCalendarFinishDate().setEnabled(true);
			this.getProjectName().getTextControl(this.getcProject()).setEnabled(true);
			this.getSystemName().getTextControl(cSystemName).setEnabled(true);
			this.getBtnNew().setEnabled(true);
			break;
		}

	}
}