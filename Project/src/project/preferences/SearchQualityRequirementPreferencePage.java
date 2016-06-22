package project.preferences;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
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
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.hibernate.exception.JDBCConnectionException;

import project.preferences.controller.QualityRequirementPPController;
import software.DomainModel.AnalysisEntity.QualityRequirement;

public class SearchQualityRequirementPreferencePage extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage {
	/**
	 * Attributes
	 */
	private Group groupPropierties;
	private Composite cPropierties;
	private ComboViewer cmbSystem;
	private Group groupQualityRequirement;
	private Composite cQualityRequirement;
	private TableViewer tblViewerQualityRequirement;
	private Table table;
	private TableColumn colObject;
	private TableColumn colCondition;
	private TableColumn colQualityAttribute;
	private TableColumn colDescriptionScenario;
	private Button btnConsult;
	private static SearchQualityRequirementPreferencePage qualityRequirementPP;
	private QualityRequirementPPController viewController;

	public SearchQualityRequirementPreferencePage() {
		super(GRID);
		noDefaultAndApplyButton();
		viewController = new QualityRequirementPPController();
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

	protected Control createContents(Composite parent) {
		try {
			this.getViewController().setFormSearch(this);

			groupPropierties = new Group(parent, SWT.SHADOW_ETCHED_IN);
			groupPropierties.setText("Propierties");

			GridLayout layoutPropierties = new GridLayout();
			layoutPropierties.numColumns = 1;
			groupPropierties.setLayout(layoutPropierties);

			GridData dataPropierties = new GridData();
			groupPropierties.setLayoutData(dataPropierties);

			cPropierties = new Composite(groupPropierties, SWT.NONE);
			dataPropierties = new GridData();
			dataPropierties.grabExcessHorizontalSpace = true;
			dataPropierties.horizontalIndent = 40;
			cPropierties.setLayoutData(dataPropierties);

			addField(new StringFieldEditor(PreferenceConstants.P_STRING, "Project Name: ", cPropierties));

			Label labelS = new Label(cPropierties, SWT.NONE);
			labelS.setText("System: ");

			cmbSystem = new ComboViewer(cPropierties, SWT.READ_ONLY);

			cmbSystem.setContentProvider(ArrayContentProvider.getInstance());

			cmbSystem.getCombo().addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					if (((IStructuredSelection) cmbSystem.getSelection()).getFirstElement() != "") {
						cmbSystemItemStateChanged();
					} else {
						clearScenario();
						prepareView(0);
					}
				}
			});

			groupQualityRequirement = new Group(parent, SWT.SHADOW_ETCHED_IN);
			groupQualityRequirement.setText("Quality Requirements");

			GridLayout layoutQualityRequirement = new GridLayout();
			layoutQualityRequirement.numColumns = 1;
			groupQualityRequirement.setLayout(layoutQualityRequirement);

			GridData dataQualityRequirement = new GridData();
			groupQualityRequirement.setLayoutData(dataQualityRequirement);

			cQualityRequirement = new Composite(groupQualityRequirement, SWT.NONE);
			dataQualityRequirement = new GridData();
			dataQualityRequirement.grabExcessHorizontalSpace = true;
			dataQualityRequirement.horizontalIndent = 40;
			cQualityRequirement.setLayoutData(dataQualityRequirement);

			addField(new StringFieldEditor(PreferenceConstants.P_STRING, "Project Name: ", cQualityRequirement));
			
			//Create column names
			String[] columnNames = new String[] {"Object", "Condition", "Quality Attribute", "Description Scenario"};
			//Create styles
			int style = SWT.FULL_SELECTION | SWT.BORDER | SWT.V_SCROLL;	
			//create table
			table = new Table(cQualityRequirement, style);
			TableLayout layout = new TableLayout(); 
	        table.setLayout(layout); 
	        GridData gridData = new GridData(GridData.FILL_BOTH); 
	        table.setLayoutData(gridData); 
	        table.setLinesVisible(true); 
	        table.setHeaderVisible(true);
	        //Create columns
	        colObject = new TableColumn(table, SWT.NONE);
			colObject.setWidth(0);
			colObject.setText("Object");

			colCondition = new TableColumn(table, SWT.NONE);
			colCondition.setWidth(200);
			colCondition.setText("Condition");

			colQualityAttribute = new TableColumn(table, SWT.NONE);
			colQualityAttribute.setWidth(200);
			colQualityAttribute.setText("Quality Attribute");

			colDescriptionScenario = new TableColumn(table, SWT.NONE);
			colDescriptionScenario.setWidth(200);
			colDescriptionScenario.setText("Description Scenario");

	        //Create TableViewer
			tblViewerQualityRequirement = new TableViewer(table); 
			tblViewerQualityRequirement.setUseHashlookup(true); 
	        tblViewerQualityRequirement.setColumnProperties(columnNames); 
	        
	        // Create the cell editors 
	        CellEditor[] editors = new CellEditor[columnNames.length]; 
	        editors[0] = null; 
	        editors[1] = null; 
	        editors[2] = null;
	        editors[3] = null; 
	 
	        // Assign the cell editors to the viewer 
	        tblViewerQualityRequirement.setCellEditors(editors); 
	        
			btnConsult = new Button(parent, SWT.PUSH);
			btnConsult.setText(" Consult ");
			btnConsult.setToolTipText("Consult");
			btnConsult.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					getViewController().setModel((QualityRequirement)table.getItem(table.getSelectionIndex()).getData());
					//getViewController().getForm().setView();
					//TODO falta mostrar la vista.
				}
			});

			this.prepareView(0);
		} catch (JDBCConnectionException e) {
			viewController.createErrorDialog("Postgres service is not running");
		}

		return new Composite(parent, SWT.NULL);

	}

	@Override
	protected void createFieldEditors() {
	}

	public static SearchQualityRequirementPreferencePage getQualityRequirementPP() {
		return qualityRequirementPP;
	}

	public static void setQualityRequirementPP(SearchQualityRequirementPreferencePage qualityRequirementPP) {
		SearchQualityRequirementPreferencePage.qualityRequirementPP = qualityRequirementPP;
	}

	public QualityRequirementPPController getViewController() {
		return viewController;
	}

	public void setViewController(QualityRequirementPPController viewController) {
		this.viewController = viewController;
	}

	public Group getGroupPropierties() {
		return groupPropierties;
	}

	public void setGroupPropierties(Group groupPropierties) {
		this.groupPropierties = groupPropierties;
	}

	public Composite getcPropierties() {
		return cPropierties;
	}

	public void setcPropierties(Composite cPropierties) {
		this.cPropierties = cPropierties;
	}

	public ComboViewer getCmbSystem() {
		return cmbSystem;
	}

	public void setCmbSystem(ComboViewer cmbSystem) {
		this.cmbSystem = cmbSystem;
	}

	public Group getGroupQualityRequirement() {
		return groupQualityRequirement;
	}

	public void setGroupQualityRequirement(Group groupQualityRequirement) {
		this.groupQualityRequirement = groupQualityRequirement;
	}

	public Composite getcQualityRequirement() {
		return cQualityRequirement;
	}

	public void setcQualityRequirement(Composite cQualityRequirement) {
		this.cQualityRequirement = cQualityRequirement;
	}

	public TableViewer getTblViewerQualityRequirement() {
		return tblViewerQualityRequirement;
	}

	public void setTblViewerQualityRequirement(TableViewer tblViewerQualityRequirement) {
		this.tblViewerQualityRequirement = tblViewerQualityRequirement;
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public TableColumn getColObject() {
		return colObject;
	}

	public void setColObject(TableColumn colObject) {
		this.colObject = colObject;
	}

	public TableColumn getColCondition() {
		return colCondition;
	}

	public void setColCondition(TableColumn colCondition) {
		this.colCondition = colCondition;
	}

	public TableColumn getColQualityAttribute() {
		return colQualityAttribute;
	}

	public void setColQualityAttribute(TableColumn colQualityAttribute) {
		this.colQualityAttribute = colQualityAttribute;
	}

	public TableColumn getColDescriptionScenario() {
		return colDescriptionScenario;
	}

	public void setColDescriptionScenario(TableColumn colDescriptionScenario) {
		this.colDescriptionScenario = colDescriptionScenario;
	}

	public Button getBtnConsult() {
		return btnConsult;
	}

	public void setBtnConsult(Button btnConsult) {
		this.btnConsult = btnConsult;
	}

	public void loadCmbSystem() {
		this.getViewController().setModelSystemSearch();
	}

	private void cmbSystemItemStateChanged() {// GEN-FIRST:event_cmbNombreItemStateChanged
		this.fillTable();
		this.prepareView(1);
	}

	public void fillTable() {
		this.getViewController().setModelQualityRequirement(
				(software.DomainModel.AnalysisEntity.System) ((IStructuredSelection) this.getCmbSystem().getSelection())
						.getFirstElement());
	}

	public void clearParts() {
		// TODO
	}

	public void clearScenario() {
		// TODO
		this.clearParts();
	}

	public void clearView() {
		cmbSystem.getCombo().clearSelection();
		this.clearScenario();
	}

	public void prepareView(int pabm) {
		this.getCmbSystem().getCombo().setFocus();
		if (!getViewController().getManager().existSystemTrueWithQualityRequirementTrue()) {
			this.getViewController().createErrorDialog("No saved systems with quality requirements");
			pabm = 0;
		}
		switch (pabm) {
		case 0:// Search quality requirement
			this.getCmbSystem().getCombo().setEnabled(true);
			loadCmbSystem();

			this.getTblViewerQualityRequirement().getTable().setEnabled(false);

			this.getBtnConsult().setEnabled(true);

			break;
		case 1:// With system selected
			this.getTblViewerQualityRequirement().getTable().setEnabled(true);

			break;
		}

	}
}
