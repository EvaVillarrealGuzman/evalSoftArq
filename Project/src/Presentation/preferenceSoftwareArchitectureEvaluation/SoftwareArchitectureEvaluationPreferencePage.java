package Presentation.preferenceSoftwareArchitectureEvaluation;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.hibernate.exception.JDBCConnectionException;

import Presentation.controllerSoftwareArchitectureEvaluation.SoftwareArchitectureEvaluationPPController;
import Presentation.preferences.DoubleFieldEditor;
import Presentation.preferences.Messages;

/**
 * To specify a software architecture by JUCMNav
 * 
 * @author: FEM
 */
public class SoftwareArchitectureEvaluationPreferencePage extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage {

	/**
	 * Attributes
	 */
	private Button btnEvaluate;
	private ComboViewer cmbSystem;
	private ComboViewer cmbUnit;
	private SoftwareArchitectureEvaluationPPController viewController;
	private Composite cSystemName;
	private GridData gridData;
	private TableViewer tblViewerQualityRequirement;
	private Table table;
	private TableColumn colObject;
	private TableColumn colPath;
	private TableColumn colName;
	private DoubleFieldEditor simulationTime;

	/**
	 * Contructor
	 */
	public SoftwareArchitectureEvaluationPreferencePage() {
		super(GRID);
		noDefaultAndApplyButton();
		viewController = new SoftwareArchitectureEvaluationPPController();
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
			labelSn.setText(Messages.getString("UCM2DEVS_SystemName_Label") + ":");

			gridData = new GridData();
			gridData.widthHint = 200;
			gridData.grabExcessHorizontalSpace = true;

			cmbSystem = new ComboViewer(cSystemName, SWT.READ_ONLY);
			cmbSystem.setContentProvider(ArrayContentProvider.getInstance());
			cmbSystem.getCombo().setLayoutData(gridData);
			loadCombo();
			cmbSystem.getCombo().addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					viewController.setModel(cmbSystem);
					cmbSystemItemStateChanged();
					prepareView(2);
				}
			});

			gridData = new GridData();
			gridData.horizontalSpan = 4;

			Label labelEmptyOne = new Label(parent, SWT.NULL);
			labelEmptyOne.setLayoutData(gridData);

			gridData = new GridData();
			gridData.horizontalSpan = 4;

			Group gQualityRequirement = new Group(parent, SWT.NONE);
			gQualityRequirement.setLayoutData(gridData);
			gQualityRequirement.setText(Messages.getString("UCM2DEVS_SoftArcSpec_Group"));
			gQualityRequirement.setLayout(layout);

			// Create column names
			String[] columnNames = new String[] { Messages.getString("UCM2DEVS_Object_Column"),
					Messages.getString("UCM2DEVS_Name_Column"), Messages.getString("UCM2DEVS_Path_Column") };
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
			colObject.setText(Messages.getString("UCM2DEVS_Object_Column"));

			colName = new TableColumn(table, SWT.NONE);
			colName.setWidth(100);
			colName.setText(Messages.getString("UCM2DEVS_Name_Column"));

			colPath = new TableColumn(table, SWT.NONE);
			colPath.setWidth(300);
			colPath.setText(Messages.getString("UCM2DEVS_Path_Column"));

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

			table.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					table.showSelection();
					prepareView(3);
				}
			});

			gridData = new GridData();
			gridData.horizontalSpan = 2;

			Label labelEmptyTwo = new Label(parent, SWT.NULL);
			labelEmptyTwo.setLayoutData(gridData);

			gridData = new GridData();
			gridData.horizontalSpan = 3;
			gridData.widthHint = 100;
			gridData.horizontalAlignment = GridData.END;
			gridData.verticalAlignment = SWT.BOTTOM;
			gridData.grabExcessHorizontalSpace = true;

			simulationTime = new DoubleFieldEditor(Messages.getString("UCM2DEVS_SimulationTime_Label"),
					Messages.getString("UCM2DEVS_SimulationTime_Label") + ":", parent);
			simulationTime.setMinRange(0.0);
			simulationTime.setPage(this);

			addField(simulationTime);

			cmbUnit = new ComboViewer(parent, SWT.READ_ONLY);
			cmbUnit.setContentProvider(ArrayContentProvider.getInstance());
			cmbUnit.getCombo().setLayoutData(gridData);
			loadComboUnit();

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

			btnEvaluate = new Button(parent, SWT.PUSH);
			btnEvaluate.setText(Messages.getString("UCM2DEVS_Evaluate_Buttom"));
			btnEvaluate.setLayoutData(gridData);
			btnEvaluate.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					viewController.evaluate();
				}
			});

			this.prepareView(1);
		} catch (JDBCConnectionException e) {
			viewController.createErrorDialog(Messages.getString("UCM2DEVS_Postgres_ErrorDialog"));
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

	public ComboViewer getCboSystem() {
		return cmbSystem;
	}

	public void setCboSystem(ComboViewer cboSystem) {
		this.cmbSystem = cboSystem;
	}

	public DoubleFieldEditor getSimulationTime() {
		return simulationTime;
	}

	public void setSimulationTime(DoubleFieldEditor simulationTime) {
		this.simulationTime = simulationTime;
	}

	public ComboViewer getCmbUnit() {
		return cmbUnit;
	}

	public void setCmbUnit(ComboViewer cmbUnit) {
		this.cmbUnit = cmbUnit;
	}

	public SoftwareArchitectureEvaluationPPController getViewController() {
		return viewController;
	}

	public void setViewController(SoftwareArchitectureEvaluationPPController viewController) {
		this.viewController = viewController;
	}

	public Button getBtnEvaluate() {
		return btnEvaluate;
	}

	public void setBtnEvaluate(Button btnEvaluate) {
		this.btnEvaluate = btnEvaluate;
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
	 * load combo with units
	 */
	public void loadComboUnit() {
		this.getViewController().setModelUnit();
	}

	/**
	 * prepare the view for the different actions that are possible
	 * 
	 * @param pabm
	 */
	public void prepareView(int pabm) {
		this.getCboSystem().getCombo().setFocus();
		if (!getViewController().getManager().existSystemTrueWithArchitecture()) {
			// TODO CAMBIAR MENSAJE
			this.getViewController().createErrorDialog(Messages.getString("UCM2DEVS_NoSavedSystemArch_ErrorDialog"));
			pabm = 0;
		}
		switch (pabm) {
		case 0:// No system with architecture
			this.getCboSystem().getCombo().setEnabled(false);
			this.getTable().setEnabled(false);
			this.getSimulationTime().setEnabled(false);
			this.getCmbUnit().getCombo().setEnabled(false);
			// TODO
			this.getBtnEvaluate().setEnabled(false);

			break;
		case 1: // There are systems with architecture
			this.getCboSystem().getCombo().setEnabled(true);
			this.getTable().setEnabled(false);
			this.getSimulationTime().setEnabled(false);
			this.getCmbUnit().getCombo().setEnabled(false);
			this.getBtnEvaluate().setEnabled(false);

			break;
		case 2: // With system selected
			this.getTable().setEnabled(true);
			this.getSimulationTime().setEnabled(false);
			this.getCmbUnit().getCombo().setEnabled(false);

			break;
		case 3:// With architecture selected
			this.getSimulationTime().setEnabled(true);
			this.getCmbUnit().getCombo().setEnabled(true);
			this.getBtnEvaluate().setEnabled(true);

			break;
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
				(DomainModel.AnalysisEntity.System) ((IStructuredSelection) this.getCboSystem().getSelection())
						.getFirstElement());
	}

}