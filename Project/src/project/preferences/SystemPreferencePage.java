package project.preferences;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.eclipse.jface.preference.*;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.swt.widgets.Button;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.toedter.calendar.JDateChooser;

import project.preferences.controller.SystemPPController;

import org.eclipse.ui.IWorkbench;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
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

		Label label = new Label(getFieldEditorParent(), SWT.NONE);
		label.setText("SystemName: ");
	    cboSystem = new ComboViewer(getFieldEditorParent(), SWT.READ_ONLY);
		//AAthis.loadCombo();

	  /*  cboSystem = new ComboFieldEditor("SystemName", "System name: ", new String[][] { { "16", "16" },
			{ "8 bit (256)", "256" }, { "16 bit (65K)", "65536" }, { "24 bit (16M)", "16777216" } },
			getFieldEditorParent());
		addField(cboSystem);*/

		/*Composite composite = new Composite(getFieldEditorParent(), SWT.EMBEDDED | SWT.NO_BACKGROUND);
		Frame frame = SWT_AWT.new_Frame(composite);
		
		JPanel panel = new JPanel(new BorderLayout());
		/*  JButton button = new JButton("Swing button");
		  JLabel label = new JLabel("Swing label");
		  panel.add(label,BorderLayout.NORTH);
		  panel.add(button,BorderLayout.CENTER);
		  frame.add(panel);*/
		
		// Group for project properties
		Group groupProject = new Group(getFieldEditorParent(), SWT.SHADOW_ETCHED_IN);
		groupProject.setText("Project");

		GridLayout layoutProject = new GridLayout();
		layoutProject.numColumns = 1;
		groupProject.setLayout(layoutProject);

		GridData dataProject = new GridData();
		groupProject.setLayoutData(dataProject);

		Composite cProject = new Composite(groupProject, SWT.NONE);
		dataProject = new GridData();
		dataProject.grabExcessHorizontalSpace = true;
		dataProject.horizontalIndent = 40;
		cProject.setLayoutData(dataProject);

		projectName = new StringFieldEditor(PreferenceConstants.P_STRING, "Project Name: ", cProject); //$NON-NLS-1$
		addField(projectName);

		lblCalendarStarDate = new Label(cProject, SWT.NONE);
		lblCalendarStarDate.setText("Start Date");

		

		/*sd = new JDateChooser();
		sd.setToolTipText("Sel");
		sd.setBounds(102, 56, 184, 20);
		panel.add(sd);
		frame.add(panel);
		*/
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
		        System.out.println("Called!");
		    }
		}); 
		
		btnEdit = new Button(getFieldEditorParent(), SWT.PUSH);
		btnEdit.setText("Edit");

		btnRemove = new Button(getFieldEditorParent(), SWT.PUSH);
		btnRemove.setText("Remove");

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
	

	/**
	 * load combo with system names
	 */
	public void loadCombo() {
		this.getViewController().setModel(this.getCboSystem());
	}

}