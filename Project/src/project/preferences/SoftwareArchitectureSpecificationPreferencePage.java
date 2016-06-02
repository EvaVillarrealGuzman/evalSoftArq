package project.preferences;

import java.io.File;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.preference.*;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Button;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.hibernate.exception.JDBCConnectionException;
import project.preferences.controller.SoftwareArchitectureSpecificationPPController;
import seg.jUCMNav.editors.UCMNavMultiPageEditor;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
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

public class SoftwareArchitectureSpecificationPreferencePage extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage {

	private Button btnUCM;
	private Button btnFileUCM;
	private Button btnEditFileUCM;
	private ComboViewer cboSystem;
	private SoftwareArchitectureSpecificationPPController viewController;
	private Text txtSelectUCM;
	private FileDialog chooseFile;
	private Button btnOptionNewUCM;
	private Button btnOptionOpenUCM;
	private UCMNavMultiPageEditor editor;

	public SoftwareArchitectureSpecificationPreferencePage() {
		super(GRID);
		noDefaultAndApplyButton();
		viewController = new SoftwareArchitectureSpecificationPPController();
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

		try {
			this.getViewController().setForm(this);

			GridLayout layout = new GridLayout(2, false);
			getFieldEditorParent().setLayout(layout);

			Label labelSn = new Label(getFieldEditorParent(), SWT.NONE);
			labelSn.setText("System Name: ");
			cboSystem = new ComboViewer(getFieldEditorParent(), SWT.READ_ONLY);
			cboSystem.setContentProvider(ArrayContentProvider.getInstance());
			loadCombo();

			String[][] radioButtonOptions = new String[][] { { "Select UCM: ", "Select UCM" } };

			final RadioGroupFieldEditor radioButtonGroup = new RadioGroupFieldEditor("PrefValue", " UCM ", 1,
					radioButtonOptions, getFieldEditorParent(), true);
			addField(radioButtonGroup);

			btnOptionOpenUCM = new Button(getFieldEditorParent(), SWT.RADIO);
			btnOptionOpenUCM.setText("Select UCM: ");
			btnOptionOpenUCM.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					prepareView();
				}
			});

			txtSelectUCM = new Text(getFieldEditorParent(), SWT.SINGLE | SWT.BORDER);
			txtSelectUCM.setText("");

			btnFileUCM = new Button(getFieldEditorParent(), SWT.PUSH);
			btnFileUCM.setText(" File ");
			btnFileUCM.setToolTipText("Search UCM file");

			btnFileUCM.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					// Open a FileDialog that show only jucm file
					chooseFile = new FileDialog(getFieldEditorParent().getShell(), SWT.OPEN);
					chooseFile.setFilterNames(new String[] { "Jucm Files" });
					chooseFile.setFilterExtensions(new String[] { "*.jucm" });
					String filePath = chooseFile.open();
					txtSelectUCM.setText(filePath);
					txtSelectUCM.setToolTipText(filePath);
					prepareView();
				}
			});

			btnEditFileUCM = new Button(getFieldEditorParent(), SWT.PUSH);
			btnEditFileUCM.setText(" Edit ");
			btnEditFileUCM.setToolTipText("Edit UCM file");
			btnEditFileUCM.addSelectionListener(new SelectionAdapter() {
				// Open an existing jucm file
				@Override
				public void widgetSelected(SelectionEvent e) {
					File file = new File(txtSelectUCM.getText());
					IFile ifile = viewController.convert(file);
					IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
					IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry()
							.getDefaultEditor(ifile.getName());
					getFieldEditorParent().getShell().close();
					try {
						editor = (UCMNavMultiPageEditor) page.openEditor(new FileEditorInput(ifile), desc.getId());
					} catch (PartInitException e1) {
						viewController.createErrorDialog("There must be a project to open a file");
					}
				}
			});

			btnOptionNewUCM = new Button(getFieldEditorParent(), SWT.RADIO);
			btnOptionNewUCM.setText("Create UCM: ");
			btnOptionNewUCM.setSelection(true);
			btnOptionOpenUCM.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					prepareView();
				}
			});

			btnUCM = new Button(getFieldEditorParent(), SWT.PUSH);
			btnUCM.setText(" New ");
			btnUCM.setToolTipText("New UCM file");
			btnUCM.addSelectionListener(new SelectionAdapter() {
				// Open jUCMNav´s Wizard
				@Override
				public void widgetSelected(SelectionEvent e) {
					IWizard wizard = new seg.jUCMNav.views.wizards.NewUcmFileWizard();
					WizardDialog dialog = new WizardDialog(getFieldEditorParent().getShell(), wizard);
					dialog.open();
					getFieldEditorParent().getShell().close();
				}
			});

			this.prepareView();
		} catch (JDBCConnectionException e) {
			viewController.createErrorDialog("Postgres service is not running");
		}

	}

	// Getters and Setters
	public Button getBtnUCM() {
		return btnUCM;
	}

	public void setBtnUCM(Button btnUCM) {
		this.btnUCM = btnUCM;
	}

	public Button getBtnFileUCM() {
		return btnFileUCM;
	}

	public void setBtnFileUCM(Button btnFileUCM) {
		this.btnFileUCM = btnFileUCM;
	}

	public Button getBtnEditFileUCM() {
		return btnEditFileUCM;
	}

	public void setBtnEditFileUCM(Button btnEditFileUCM) {
		this.btnEditFileUCM = btnEditFileUCM;
	}

	public ComboViewer getCboSystem() {
		return cboSystem;
	}

	public void setCboSystem(ComboViewer cboSystem) {
		this.cboSystem = cboSystem;
	}

	public SoftwareArchitectureSpecificationPPController getViewController() {
		return viewController;
	}

	public void setViewController(SoftwareArchitectureSpecificationPPController viewController) {
		this.viewController = viewController;
	}

	public Text getTxtSelectUCM() {
		return txtSelectUCM;
	}

	public void setTxtSelectUCM(Text txtSelectUCM) {
		this.txtSelectUCM = txtSelectUCM;
	}

	public FileDialog getChooseFile() {
		return chooseFile;
	}

	public void setChooseFile(FileDialog chooseFile) {
		this.chooseFile = chooseFile;
	}

	public Button getBtnOptionNewUCM() {
		return btnOptionNewUCM;
	}

	public void setBtnOptionNewUCM(Button btnOptionNewUCM) {
		this.btnOptionNewUCM = btnOptionNewUCM;
	}

	public Button getBtnOptionOpenUCM() {
		return btnOptionOpenUCM;
	}

	public void setBtnOptionOpenUCM(Button btnOptionOpenUCM) {
		this.btnOptionOpenUCM = btnOptionOpenUCM;
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
	public void prepareView() {
		if (this.getBtnOptionOpenUCM().getSelection() == true) {
			// Prepate view when button of open ucm is select
			this.getBtnFileUCM().setEnabled(true);
			this.getTxtSelectUCM().setEnabled(true);
			if (!this.getTxtSelectUCM().getText().equals("")) {
				this.getBtnEditFileUCM().setEnabled(true);
			} else {
				this.getBtnEditFileUCM().setEnabled(false);
			}
			this.getBtnUCM().setEnabled(false);
		} else {
			// Prepate view when button of new ucm is select
			this.getBtnEditFileUCM().setEnabled(false);
			this.getBtnFileUCM().setEnabled(false);
			this.getTxtSelectUCM().setEnabled(false);
			this.getBtnUCM().setEnabled(true);
		}
	}

}