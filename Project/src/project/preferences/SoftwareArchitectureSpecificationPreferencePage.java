package project.preferences;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.hibernate.exception.JDBCConnectionException;

import project.preferences.controller.SoftwareArchitectureSpecificationPPController;

/**
 * To specify a software architecture by JUCMNav
 * 
 * @author: Eva
 */
public class SoftwareArchitectureSpecificationPreferencePage extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage {

	/**
	 * Attributes
	 */
	private Button btnUCM;
	private Button btnFileUCM;
	private Button btnEditFileUCM;
	private Button btnSave;
	private ComboViewer cboSystem;
	private SoftwareArchitectureSpecificationPPController viewController;
	private Text txtSelectUCM;
	private FileDialog chooseFile;
	private Button btnOptionNewUCM;
	private Button btnOptionOpenUCM;
	private Composite cSystemName;
	private GridData gridData;
	private boolean isCopyPaste;

	/**
	 * Contructor
	 */
	public SoftwareArchitectureSpecificationPreferencePage() {
		super(GRID);
		noDefaultAndApplyButton();
		viewController = new SoftwareArchitectureSpecificationPPController();
		this.setViewController(viewController); // NOPMD by Usuario-Pc on
												// 10/06/16 21:48
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
			this.getViewController().setForm(this);

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

			cboSystem = new ComboViewer(cSystemName, SWT.READ_ONLY);
			cboSystem.setContentProvider(ArrayContentProvider.getInstance());
			cboSystem.getCombo().setLayoutData(gridData);
			loadCombo();
			cboSystem.getCombo().addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					isCopyPaste = true;
					viewController.setModel(cboSystem);
					viewController.getView();
					prepareView();
				}
			});

			gridData = new GridData();
			gridData.horizontalSpan = 4;

			Label labelEmptyOne = new Label(parent, SWT.NULL);
			labelEmptyOne.setLayoutData(gridData);

			gridData = new GridData();
			gridData.horizontalSpan = 1;
			gridData.grabExcessHorizontalSpace = true;

			btnOptionOpenUCM = new Button(parent, SWT.RADIO);
			btnOptionOpenUCM.setText("Select UCM: ");
			btnOptionOpenUCM.setLayoutData(gridData);
			btnOptionOpenUCM.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					prepareView();
				}
			});

			gridData = new GridData();
			gridData.horizontalSpan = 1;
			gridData.widthHint = 210;
			gridData.horizontalAlignment = GridData.FILL_HORIZONTAL;
			gridData.grabExcessHorizontalSpace = true;

			txtSelectUCM = new Text(parent, SWT.SINGLE | SWT.BORDER);
			txtSelectUCM.setText("");
			txtSelectUCM.setLayoutData(gridData);
			txtSelectUCM.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					e.doit = false;
				}
			});

			// avoid copy and paste
			txtSelectUCM.addVerifyListener(new VerifyListener() {
				public void verifyText(VerifyEvent event) {
					if (!isCopyPaste) {
						if (event.text.length() > 1) {
							event.doit = false;
						}
					}
					isCopyPaste = false;
				}
			});

			gridData = new GridData();
			gridData.horizontalSpan = 1;
			gridData.horizontalAlignment = GridData.END;
			gridData.grabExcessHorizontalSpace = true;
			gridData.widthHint = 75;

			btnFileUCM = new Button(parent, SWT.PUSH);
			btnFileUCM.setText(" File ");
			btnFileUCM.setToolTipText("Search UCM file");
			btnFileUCM.setLayoutData(gridData);
			;
			btnFileUCM.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					// Open a FileDialog that show only jucm file
					chooseFile = new FileDialog(parent.getShell(), SWT.OPEN);
					chooseFile.setFilterNames(new String[] { "Jucm Files" });
					chooseFile.setFilterExtensions(new String[] { "*.jucm" });
					String filePath = chooseFile.open();
					isCopyPaste = true;
					txtSelectUCM.setText(filePath);
					txtSelectUCM.setToolTipText(filePath);
					prepareView();
				}
			});

			gridData = new GridData();
			gridData.horizontalSpan = 1;
			gridData.horizontalAlignment = GridData.END;
			gridData.widthHint = 75;
			gridData.grabExcessHorizontalSpace = true;

			btnEditFileUCM = new Button(parent, SWT.PUSH);
			btnEditFileUCM.setText(" Edit ");
			btnEditFileUCM.setToolTipText("Edit UCM file");
			btnEditFileUCM.setLayoutData(gridData);
			btnEditFileUCM.addSelectionListener(new SelectionAdapter() {
				// Open an existing jucm file
				@Override
				public void widgetSelected(SelectionEvent e) {
					openJUCMNavEditor(parent);
				}
			});

			gridData = new GridData();
			gridData.horizontalSpan = 4;

			Label labelEmptyTwo = new Label(parent, SWT.NULL);
			labelEmptyTwo.setLayoutData(gridData);

			gridData = new GridData();
			gridData.horizontalSpan = 1;
			gridData.grabExcessHorizontalSpace = true;

			btnOptionNewUCM = new Button(parent, SWT.RADIO);
			btnOptionNewUCM.setText("Create UCM: ");
			btnOptionNewUCM.setSelection(true);
			btnOptionNewUCM.setLayoutData(gridData);

			gridData = new GridData();
			gridData.horizontalSpan = 1;
			gridData.grabExcessHorizontalSpace = true;
			btnOptionOpenUCM.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					prepareView();
				}
			});

			gridData = new GridData();
			gridData.horizontalSpan = 1;
			gridData.widthHint = 75;
			gridData.grabExcessHorizontalSpace = true;

			btnUCM = new Button(parent, SWT.PUSH);
			btnUCM.setText(" New ");
			btnUCM.setToolTipText("New UCM file");
			btnUCM.setLayoutData(gridData);
			btnUCM.addSelectionListener(new SelectionAdapter() {
				// Open jUCMNav´s Wizard
				@Override
				public void widgetSelected(SelectionEvent e) {
					IWizard wizard = new seg.jUCMNav.views.wizards.NewUcmFileWizard();
					WizardDialog dialog = new WizardDialog(parent.getShell(), wizard);
					dialog.open();
					// TODO implementar que solo se cierre si no se cancela
					parent.getShell().close();
				}
			});

			gridData = new GridData();
			gridData.horizontalSpan = 2;

			Label labelEmptyTree = new Label(parent, SWT.NULL);
			labelEmptyTree.setLayoutData(gridData);

			gridData = new GridData();
			gridData.horizontalSpan = 4;
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
					if (viewController.save() == 0) {
						openJUCMNavEditor(parent);
					}
				}
			});

			this.prepareView();
		} catch (JDBCConnectionException e) {
			viewController.createErrorDialog("Postgres service is not running");
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

	public Button getBtnSave() {
		return btnSave;
	}

	public void setBtnSave(Button btnSave) {
		this.btnSave = btnSave;
	}

	/**
	 * load combo with system with state=true
	 */
	public void loadCombo() {
		this.getViewController().setModel();
	}

	/**
	 * prepare the view for the different actions that are possible
	 * 
	 * @param pabm
	 */
	public void prepareView() {
		if (!getViewController().getManager().existSystemTrue()) {
			this.getViewController().createErrorDialog("No saved systems");
		}
		if (this.getBtnOptionOpenUCM().getSelection() == true) {
			// Prepate view when button of open ucm is select
			this.getBtnFileUCM().setEnabled(true);
			this.getTxtSelectUCM().setToolTipText(this.getTxtSelectUCM().getText());
			if (!this.getTxtSelectUCM().getText().equals("")) {
				this.getBtnEditFileUCM().setEnabled(true);
			} else {
				this.getBtnEditFileUCM().setEnabled(false);
			}
			this.getBtnUCM().setEnabled(false);
		} else {
			// Prepate view when button of new ucm is select
			this.getBtnEditFileUCM().setEnabled(false);
			this.getTxtSelectUCM().setToolTipText(this.getTxtSelectUCM().getText());
			this.getBtnFileUCM().setEnabled(false);
			this.getBtnUCM().setEnabled(true);
		}
	}

	public void openJUCMNavEditor(Composite parent) {
		File file = new File(txtSelectUCM.getText());
		IFile ifile = viewController.convert(file);
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		try {
			IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(ifile.getName());
			parent.getShell().close();
			page.openEditor(new FileEditorInput(ifile), desc.getId());
		} catch (Exception e1) {
			viewController.createErrorDialog("There must be a project in Eclipse to open a file");
		}
	}

}