package project.preferences;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import java.io.File;
import org.eclipse.jface.preference.*;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Button;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.part.FileEditorInput;

import project.preferences.controller.SystemPPController;
import seg.jUCMNav.editors.UCMNavMultiPageEditor;

import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
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

public class SoftwareArchitectureSpecificationPage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	private Button btnUCM;
	private Button btnFileUCM;
	private Button btnEditFileUCM;
	private ComboViewer cboSystem;
	private SystemPPController viewController;
	 private UCMNavMultiPageEditor editor;
	
private Text txtSelectUCM;
	
	public SoftwareArchitectureSpecificationPage() {
		super(GRID);
		noDefaultAndApplyButton();
		//viewController = new SystemPPController();
		//this.setViewController(viewController);
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

		//this.getViewController().setForm(this);

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
				//prepareView(1);
			}
		});
		
		String[][] radioButtonOptions = new String[][] { { "Select UCM: ", "Select UCM" } };
		
		final RadioGroupFieldEditor radioButtonGroup = new RadioGroupFieldEditor("PrefValue", " UCM ", 1, radioButtonOptions, getFieldEditorParent(), true);
		addField(radioButtonGroup);
		
		new Button(getFieldEditorParent(), SWT.RADIO).setText("Select UCM: ");   
		txtSelectUCM = new Text(getFieldEditorParent(), SWT.SINGLE | SWT.BORDER);
		btnFileUCM = new Button(getFieldEditorParent(), SWT.PUSH);
		btnFileUCM.setText(" File ");
		btnFileUCM.setToolTipText("Search UCM file");
		
		//DirectoryFieldEditor
		
		btnEditFileUCM = new Button(getFieldEditorParent(), SWT.PUSH);
		btnEditFileUCM.setText(" Edit ");
		btnEditFileUCM.setToolTipText("Edit UCM file");
		btnEditFileUCM.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				File f = new File("");
		        IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		        IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(f.getName());
		     //   editor = (UCMNavMultiPageEditor) page.openEditor(new FileEditorInput(f), desc.getId());
			}
		});
		
		new Button(getFieldEditorParent(), SWT.RADIO).setText("Create UCM: ");
		
		btnUCM = new Button(getFieldEditorParent(), SWT.PUSH);
		btnUCM.setText(" New ");
		btnUCM.setToolTipText("New UCM file");
		btnUCM.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				  IWizard wizard = new seg.jUCMNav.views.wizards.NewUcmFileWizard();
				  WizardDialog dialog = new WizardDialog(getFieldEditorParent().getShell(), wizard);
				  dialog.open();
			}
		});
		
		
		
		

		
		// Group for project properties
	/*	Group groupProject = new Group(getFieldEditorParent(), SWT.SHADOW_ETCHED_IN);
		groupProject.setText("UCM");

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
		
		String[][] radioButtonOptions = new String[][] { { "Button1", "button1" }, { "Button2", "button2" } };
		
		final RadioGroupFieldEditor radioButtonGroup = new RadioGroupFieldEditor("PrefValue", "Choose Button1 or Button2", 2, radioButtonOptions, cProject, true);

		/*projectName = new StringFieldEditor(PreferenceConstants.P_STRING, "Project Name: ", cProject);

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
						new ImageIcon(SoftwareArchitectureSpecification.class.getResource("/Icons/error.png")),
						new Object[] { "Yes", "No" }, "Yes") == 0) {
					viewController.remove();
					prepareView(0);
				}
			}
		});

		this.prepareView(0);*/

	}

/*	public DateTime getCalendarStartDate() {
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

	public static SoftwareArchitectureSpecification getSystemPP() {
		return SystemPP;
	}

	public static void setSystemPP(SoftwareArchitectureSpecification systemPP) {
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
	}*/

	/**
	 * load combo with system names
	 */
	public void loadCombo() {
		//this.getViewController().setModel();
	}

	/**
	 * manages the various types of views
	 * 
	 * @param pabm
	 */
	/*public void prepareView(int pabm) {
		this.getCboSystem().getCombo().setFocus();
		if (!getViewController().getManager().existSystemTrue()) {
			JOptionPane.showOptionDialog(null, "No saved systems", "Warning", JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.ERROR_MESSAGE,
					new ImageIcon(SoftwareArchitectureSpecification.class.getResource("/Icons/error.png")), new Object[] { "OK" },
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
			//loadCombo();
			break;
		case 1:// With system
			this.getCalendarStartDate().setEnabled(true);
			this.getCalendarFinishDate().setEnabled(true);
			this.getProjectName().getTextControl(this.getcProject()).setEnabled(true);
			this.getBtnRemove().setEnabled(true);
			this.getBtnSave().setEnabled(true);
			break;
		}
		
	}*/
}