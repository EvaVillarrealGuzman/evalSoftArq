package project.preferences;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
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
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
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
public class SoftwareArchitectureSpecificationManagementPreferencePage extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage {

	/**
	 * Attributes
	 */
	private Button btnBrowseUCM;
	private Button btnSave;
	private Button btnAdd;
	private Button btnConsult;
	private Button btnDelete;
	private ComboViewer cboSystem;
	private SoftwareArchitectureSpecificationPPController viewController;
	private FileDialog chooseFile;
	private Composite cSystemName;
	private GridData gridData;
	private TableViewer tblViewerQualityRequirement;
	private Table table;
	private TableColumn colObject;
	private TableColumn colPath;
	private TableColumn colName;

	/**
	 * Contructor
	 */
	public SoftwareArchitectureSpecificationManagementPreferencePage() {
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
					viewController.setModel(cboSystem);
					cmbSystemItemStateChanged();
					prepareView();
				}
			});

			Label labelEmptyOne = new Label(parent, SWT.NULL);
			labelEmptyOne.setLayoutData(gridData);

			gridData = new GridData();
			gridData.horizontalSpan = 4;

			Group gQualityRequirement = new Group(parent, SWT.NONE);
			gQualityRequirement.setLayoutData(gridData);
			gQualityRequirement.setText("Software Architecture Specification");
			gQualityRequirement.setLayout(new GridLayout(2, false));

			// Create column names
			String[] columnNames = new String[] { "Object", "Name", "Path" };
			// Create styles
			int style = SWT.FULL_SELECTION | SWT.BORDER;
			// create table
			table = new Table(gQualityRequirement, style);
			TableLayout tableLayout = new TableLayout();
			table.setLayout(tableLayout);
			gridData = new GridData(GridData.FILL_BOTH);
			gridData.horizontalSpan = 4;
			table.setLayoutData(gridData);
			table.setLinesVisible(true);
			table.setHeaderVisible(true);
			// Create columns
			colObject = new TableColumn(table, SWT.NONE);
			colObject.setWidth(0);
			colObject.setText("Object");

			colName = new TableColumn(table, SWT.NONE);
			colName.setWidth(200);
			colName.setText("Name");

			colPath = new TableColumn(table, SWT.NONE);
			colPath.setWidth(200);
			colPath.setText("Path");

			for (int i = 0; i < 8; i++) {
				TableItem item = new TableItem(table, SWT.NONE);
				item.setText("Item " + i);
			}

			// Create TableViewer
			tblViewerQualityRequirement = new TableViewer(table);
			tblViewerQualityRequirement.setUseHashlookup(true);
			tblViewerQualityRequirement.setColumnProperties(columnNames);

			// Create the cell editors
			CellEditor[] editors = new CellEditor[columnNames.length];
			editors[0] = null;
			editors[1] = null;
			editors[2] = null;

			// Assign the cell editors to the viewer
			tblViewerQualityRequirement.setCellEditors(editors);

			gridData = new GridData();
			gridData.horizontalSpan = 1;
			gridData.horizontalAlignment = GridData.END;
			gridData.grabExcessHorizontalSpace = true;
			gridData.widthHint = 75;

			btnAdd = new Button(gQualityRequirement, SWT.PUSH);
			btnAdd.setText(" Add ");
			btnAdd.setToolTipText("Add UCM file");
			btnAdd.setLayoutData(gridData);
			btnAdd.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					// Open a FileDialog that show only jucm file
					chooseFile = new FileDialog(parent.getShell(), SWT.OPEN);
					chooseFile.setFilterNames(new String[] { "Jucm Files" });
					chooseFile.setFilterExtensions(new String[] { "*.jucm" });
					String filePath = chooseFile.open();
					if (!viewController.isUCMDuplicate(filePath)) {
						viewController.addToTable(filePath);
					} else {
						// TODO poner bien el nombre
						viewController.createErrorDialog("The UCM already exists");
					}
				}
			});

			gridData = new GridData();
			gridData.horizontalSpan = 1;
			gridData.horizontalAlignment = GridData.END;
			gridData.grabExcessHorizontalSpace = true;
			gridData.widthHint = 75;

			btnConsult = new Button(gQualityRequirement, SWT.PUSH);
			btnConsult.setText(" Consult ");
			btnConsult.setToolTipText("Consult UCM file");
			btnConsult.setLayoutData(gridData);
			btnConsult.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					TableItem item = table.getItem(table.getSelectionIndex());
					viewController.openJUCMNavEditor(parent, item.getText(2) + "\\" + item.getText(1));
				}
			});

			gridData = new GridData();
			gridData.horizontalSpan = 1;
			gridData.horizontalAlignment = GridData.END;
			gridData.grabExcessHorizontalSpace = true;
			gridData.widthHint = 75;

			btnDelete = new Button(gQualityRequirement, SWT.PUSH);
			btnDelete.setText(" Delete ");
			btnDelete.setToolTipText("Delete UCM file");
			btnDelete.setLayoutData(gridData);
			btnDelete.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					viewController.deleteToTable();
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
					viewController.save();
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
	public Button getBtnFileUCM() {
		return btnBrowseUCM;
	}

	public void setBtnFileUCM(Button btnFileUCM) {
		this.btnBrowseUCM = btnFileUCM;
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

	public FileDialog getChooseFile() {
		return chooseFile;
	}

	public void setChooseFile(FileDialog chooseFile) {
		this.chooseFile = chooseFile;
	}

	public Button getBtnSave() {
		return btnSave;
	}

	public void setBtnSave(Button btnSave) {
		this.btnSave = btnSave;
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public Button getBtnAdd() {
		return btnAdd;
	}

	public void setBtnAdd(Button btnAdd) {
		this.btnAdd = btnAdd;
	}

	public Button getBtnDelete() {
		return btnDelete;
	}

	public void setBtnDelete(Button btnDelete) {
		this.btnDelete = btnDelete;
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
		if (((IStructuredSelection) this.getCboSystem().getSelection()).getFirstElement() == null) {
			btnAdd.setEnabled(false);
			btnDelete.setEnabled(false);
			btnConsult.setEnabled(false);
		} else {
			btnAdd.setEnabled(true);
			btnDelete.setEnabled(true);
			btnConsult.setEnabled(true);
		}

	}

	/**
	 * When a system is selected, fill table with its quality requirements and
	 * prepare the view
	 */
	private void cmbSystemItemStateChanged() {// GEN-FIRST:event_cmbNombreItemStateChanged
		this.fillTable();
	}

	/**
	 * Fill table with system's quality requirements (quality attribute,
	 * description and condition)
	 */
	public void fillTable() {
		this.getViewController().setModelPaths(
				(software.DomainModel.AnalysisEntity.System) ((IStructuredSelection) this.getCboSystem().getSelection())
						.getFirstElement());
	}

}