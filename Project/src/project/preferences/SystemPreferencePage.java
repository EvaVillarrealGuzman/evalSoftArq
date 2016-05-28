package project.preferences;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.eclipse.jface.preference.*;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.swt.widgets.Button;
import org.eclipse.ui.IWorkbenchPreferencePage;
import project.preferences.controller.SystemPPController;
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

public class SystemPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	private DateTime calendarStartDate;
	private DateTime calendarFinishDate;
	private Label lblCalendarStarDate;
	private Label lblCalendarFinishDate;
	private Button btnSave;
	private Button btnRemove;
	private Button btnEdit;
	private ComboViewer cboSystem;
	private Group groupProject;
	private StringFieldEditor projectName;
	private static SystemPreferencePage SystemPP;
	private SystemPPController viewController;
	private Composite cProject;

	public SystemPreferencePage() {
		super(GRID);
		noDefaultAndApplyButton();
		viewController = new SystemPPController();
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

		Label labelSn = new Label(getFieldEditorParent(), SWT.NONE);
		labelSn.setText("System Name: ");
		cboSystem = new ComboViewer(getFieldEditorParent(), SWT.READ_ONLY);

		cboSystem.setContentProvider(ArrayContentProvider.getInstance());

		cboSystem.getCombo().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				viewController.setModel(cboSystem);
				viewController.getView();
				prepareView(1);
			}
		});

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

		btnSave = new Button(getFieldEditorParent(), SWT.PUSH);
		btnSave.setText(" Save ");
		btnSave.setToolTipText("Save");
		btnSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				viewController.save();
				prepareView(1);
			}
		});

		btnRemove = new Button(getFieldEditorParent(), SWT.PUSH);
		btnRemove.setText("Remove");
		btnRemove.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (JOptionPane.showOptionDialog(null, "Do you want to delete the system?", "Warning",
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE,
						new ImageIcon(SystemPreferencePage.class.getResource("/Icons/error.png")),
						new Object[] { "Yes", "No" }, "Yes") == 0) {
					viewController.remove();
					prepareView(0);
				}
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
		return cboSystem;
	}

	public void setCboSystem(ComboViewer cboSystem) {
		this.cboSystem = cboSystem;
	}

	public StringFieldEditor getProjectName() {
		return projectName;
	}

	public void setProjectName(StringFieldEditor projectName) {
		this.projectName = projectName;
	}

	public static SystemPreferencePage getSystemPP() {
		return SystemPP;
	}

	public static void setSystemPP(SystemPreferencePage systemPP) {
		SystemPP = systemPP;
	}

	public SystemPPController getViewController() {
		return viewController;
	}

	public void setViewController(SystemPPController viewController) {
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
	 * load combo with system names
	 */
	public void loadCombo() {
		this.getViewController().setModel();
	}

	/**
	 * manages the various types of views
	 * 
	 * @param pabm
	 */
	public void prepareView(int pabm) {
		this.getCboSystem().getCombo().setFocus();
		if (!getViewController().getManager().existSystemTrue()) {
			JOptionPane.showOptionDialog(null, "No saved systems", "Warning", JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.ERROR_MESSAGE,
					new ImageIcon(SystemPreferencePage.class.getResource("/Icons/error.png")), new Object[] { "OK" },
					"OK");
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