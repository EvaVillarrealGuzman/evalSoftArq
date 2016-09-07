package project.preferences;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.TreeColumnLayout;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
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
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.hibernate.exception.JDBCConnectionException;

import project.preferences.controller.ReportsPPController;
import project.preferences.controller.SoftwareArchitectureSpecificationPPController;
import software.DomainModel.AnalysisEntity.QualityAttribute;
import software.DomainModel.AnalysisEntity.QualityRequirement;

/**
 * To view a report for a specific architecture and a specific quality requirement
 * 
 * @author: Flor
 */
public class ReportsPreferencePage extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage {

	/**
	 * Attributes
	 */
	private Button btnBrowseUCM;
	private Button btnReport;
	private ComboViewer cboSystem;
	private ReportsPPController viewController;
	private FileDialog chooseFile;
	private Composite cSystemName;
	private GridData gridData;
	private TableViewer tblViewerSoftArchSpecification;
	private Table table;
	private TableColumn colObject;
	private TableColumn colPath;
	private TableColumn colName;
	private Composite treeViewerComposite;
	private TreeViewer	treeViewerQualRequirement;

	/**
	 * Contructor
	 */
	public ReportsPreferencePage() {
		super(GRID);
		noDefaultAndApplyButton();
		viewController = new ReportsPPController();
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
			labelSn.setText(PreferenceConstants.SystemName_Label + ":");

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
			gQualityRequirement.setText(PreferenceConstants.SoftwareArchitectureSpecification_Group);
			gQualityRequirement.setLayout(new GridLayout(2, false));

			// Create column names
			String[] columnNames = new String[] { PreferenceConstants.Object_Column, PreferenceConstants.Name_Column,
					PreferenceConstants.Path_Column };
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
			colObject.setText(PreferenceConstants.Object_Column);

			colName = new TableColumn(table, SWT.NONE);
			colName.setWidth(200);
			colName.setText(PreferenceConstants.Name_Column);

			colPath = new TableColumn(table, SWT.NONE);
			colPath.setWidth(200);
			colPath.setText(PreferenceConstants.Path_Column);

			for (int i = 0; i < 8; i++) {
				TableItem item = new TableItem(table, SWT.NONE);
				item.setText("Item " + i);
			}

			// Create TableViewer
			tblViewerSoftArchSpecification = new TableViewer(table);
			tblViewerSoftArchSpecification.setUseHashlookup(true);
			tblViewerSoftArchSpecification.setColumnProperties(columnNames);
			table.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					table.showSelection();
					//TODO implementar
				}
			});

			// Create the cell editors
			CellEditor[] editors = new CellEditor[columnNames.length];
			editors[0] = null;
			editors[1] = null;
			editors[2] = null;

			// Assign the cell editors to the viewer
			tblViewerSoftArchSpecification.setCellEditors(editors);

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

			//TODO lo que agregué para el tree
			treeViewerComposite = new Composite(parent, SWT.NONE);
		      
			Tree tree = new Tree(treeViewerComposite, SWT.BORDER | SWT.MULTI);
			tree.setHeaderVisible(true);
			TreeColumnLayout columnLayout = new TreeColumnLayout();
			treeViewerComposite.setLayout(columnLayout);

			TreeColumn column = new TreeColumn(tree, SWT.NONE);
			column.setText("Attribute/Requirement");
			columnLayout.setColumnData(column, new ColumnWeightData(3,0));

			column = new TreeColumn(tree, SWT.NONE);
			column.setText("Condition");
			columnLayout.setColumnData(column, new ColumnWeightData(3,0));
			      
			column = new TreeColumn(tree, SWT.NONE);
			column.setText("");
			columnLayout.setColumnData(column, new ColumnWeightData(3,0));
			              
			      
			GridDataFactory.fillDefaults().grab(true, true).hint(10, 10).applyTo(treeViewerComposite);

			TreeViewer treeViewer = new TreeViewer(tree);
			treeViewer.setContentProvider(new MyTreeContentProvider());
			treeViewer.setLabelProvider(new MyTreeLabelProvider());
			treeViewer.getTree().setHeaderVisible(true);
			//tree.addKeyListener(new TestListener());
			//TODO hasta aca agregué del tree
			
			btnReport = new Button(parent, SWT.PUSH);
			//TODO internacionalizar
			btnReport.setText("Report");
			btnReport.setLayoutData(gridData);
			btnReport.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					if (viewController.createSaveChangedDialog() == true) {
						viewController.save();
					}
				}
			});

			this.prepareView();
		} catch (JDBCConnectionException e) {
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

	public ReportsPPController getViewController() {
		return viewController;
	}

	public void setViewController(ReportsPPController viewController) {
		this.viewController = viewController;
	}

	public FileDialog getChooseFile() {
		return chooseFile;
	}

	public void setChooseFile(FileDialog chooseFile) {
		this.chooseFile = chooseFile;
	}

	public Button getBtnSave() {
		return btnReport;
	}

	public void setBtnSave(Button btnSave) {
		this.btnReport = btnSave;
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
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
			this.getViewController().createErrorDialog(PreferenceConstants.NoSavedSystem_ErrorDialog);
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