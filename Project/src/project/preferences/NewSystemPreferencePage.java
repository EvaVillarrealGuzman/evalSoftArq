package project.preferences;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.eclipse.jface.preference.*;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.swt.widgets.Button;
import org.eclipse.ui.IWorkbenchPreferencePage;
import project.preferences.controller.NewSystemPPController;
import org.eclipse.ui.IWorkbench;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

/**
 * This class represents a preference page that is contributed to the
 * Preferences dialog. By subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows us to create a page
 * that is small and knows how to save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They are stored in the
 * preference store that belongs to the main plug-in class. That way,
 * preferences can be accessed directly via the preference store.
 */

public class NewSystemPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	private DateTime calendarStartDate;
	private DateTime calendarFinishDate;
	private Label lblCalendarStarDate;
	private Label lblCalendarFinishDate;
	private Button btnNew;
	private StringFieldEditor systemName;
	private Group groupProject;
	private StringFieldEditor projectName;
	private static NewSystemPreferencePage SystemPP;
	private NewSystemPPController viewController;
	private Composite cProject;

	public NewSystemPreferencePage() {
		super(GRID);
		noDefaultAndApplyButton();
		viewController = new NewSystemPPController();
		this.setViewController(viewController);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}

	@Override
	protected void createFieldEditors() {

		this.getViewController().setForm(this);

		GridLayout layout = new GridLayout(2, false);
		getFieldEditorParent().setLayout(layout);

		systemName = new StringFieldEditor("systemName", "System Name: ", getFieldEditorParent());

		// Group for project properties
		Group groupProject = new Group(getFieldEditorParent(), SWT.SHADOW_ETCHED_IN);
		groupProject.setText("Project");

		GridLayout layoutProject = new GridLayout();
		layoutProject.numColumns = 1;
		groupProject.setLayout(layoutProject);

		GridData dataProject = new GridData();
		groupProject.setLayoutData(dataProject);

		cProject = new Composite(groupProject, SWT.NONE);
		dataProject = new GridData();
		dataProject.grabExcessHorizontalSpace = true;
		dataProject.horizontalIndent = 40;
		cProject.setLayoutData(dataProject);

		projectName = new StringFieldEditor(PreferenceConstants.P_STRING, "Project Name: ", cProject);

		addField(projectName);

		lblCalendarStarDate = new Label(cProject, SWT.NONE);
		lblCalendarStarDate.setText("Start Date");

		calendarStartDate = new DateTime(cProject, SWT.DATE | SWT.DROP_DOWN);

		lblCalendarFinishDate = new Label(cProject, SWT.NONE);
		lblCalendarFinishDate.setText("Finish Date");

		calendarFinishDate = new DateTime(cProject, SWT.DATE | SWT.DROP_DOWN);

		Label label1 = new Label(getFieldEditorParent(), SWT.LEFT);

		btnNew = new Button(getFieldEditorParent(), SWT.PUSH);
		btnNew.setText("Save");
		btnNew.setToolTipText("Save");
		btnNew.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				viewController.save();
				prepareView(1);
			}
		});

		this.prepareView(0);

	}

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

	public static NewSystemPreferencePage getSystemPP() {
		return SystemPP;
	}

	public static void setSystemPP(NewSystemPreferencePage systemPP) {
		SystemPP = systemPP;
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
	
	public Composite getParent(){
		return getFieldEditorParent();
	}

	/**
	 * manages the various types of views
	 * 
	 * @param pabm
	 */
	public void prepareView(int pabm) {
		this.getSystemName().getTextControl(this.getFieldEditorParent()).setFocus();
		switch (pabm) {
		case 1:// System created
			Calendar currentDate = GregorianCalendar.getInstance();
			this.getCalendarStartDate().setDate(currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DAY_OF_MONTH));
			this.getCalendarFinishDate().setDate(currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DAY_OF_MONTH));
			this.getProjectName().getTextControl(this.getcProject()).setText("");
			this.getSystemName().getTextControl(this.getFieldEditorParent()).setText("");
			this.getBtnNew().setEnabled(true);
			break;
		case 0:// New system
			this.getCalendarStartDate().setEnabled(true);
			this.getCalendarFinishDate().setEnabled(true);
			this.getProjectName().getTextControl(this.getcProject()).setEnabled(true);
			this.getSystemName().getTextControl(this.getFieldEditorParent()).setEnabled(true);
			this.getBtnNew().setEnabled(true);
			break;
		}
		
	}
}