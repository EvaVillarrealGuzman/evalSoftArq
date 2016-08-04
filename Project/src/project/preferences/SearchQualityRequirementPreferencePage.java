package project.preferences;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
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
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.hibernate.exception.JDBCConnectionException;

import project.preferences.controller.QualityRequirementPPController;
import software.DomainModel.AnalysisEntity.Metric;
import software.DomainModel.AnalysisEntity.QualityAttribute;
import software.DomainModel.AnalysisEntity.QualityRequirement;
import software.DomainModel.AnalysisEntity.ResponseMeasureType;

public class SearchQualityRequirementPreferencePage extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage {
	/**
	 * Attributes
	 */
	private ComboViewer cmbSystem;
	private Group groupQualityRequirement;
	private TableViewer tblViewerQualityRequirement;
	private Table table;
	GridData gridData;
	private Text txtDescription;
	private ComboViewer cmbQualityAttribute;
	private ComboViewer cmbCondition;
	private StringFieldEditor txtDescriptionStimulusSource;
	private StringFieldEditor txtDescriptionStimulus;
	private StringFieldEditor txtDescriptionEnvironment;
	private StringFieldEditor txtDescriptionArtifact;
	private StringFieldEditor txtDescriptionResponse;
	private StringFieldEditor txtDescriptionResponseMeasure;
	private StringFieldEditor txtValueStimulusSource;
	private StringFieldEditor txtValueStimulus;
	private StringFieldEditor txtValueEnvironment;
	private StringFieldEditor txtValueResponse;
	private DoubleFieldEditor2 txtValueResponseMeasure;
	private ComboViewer cmbMetric;
	private ComboViewer cmbTypeStimulusSource;
	private ComboViewer cmbTypeStimulus;
	private ComboViewer cmbTypeEnvironment;
	private ComboViewer cmbTypeArtifact;
	private ComboViewer cmbTypeResponse;
	private ComboViewer cmbTypeResponseMeasure;
	private Group gStimulusSource;
	private Group gResponse;
	private Group gStimulus;
	private Group gArtifact;
	private Group gEnvironment;
	private Group gResponseMeasure;
	private ComboViewer cmbUnit;
	private Button btnSave;
	private Button btnRemove;
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

	protected Control createContents(Composite parent) {
		try {
			this.getViewController().setFormSearch(this);

			GridLayout layout = new GridLayout();
			layout.numColumns = 4;
			parent.setLayout(layout);

			Composite cSystemName = new Composite(parent, SWT.NULL);
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

			cmbSystem = new ComboViewer(cSystemName, SWT.READ_ONLY);
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
					btnConsult.setEnabled(false);
				}
			});

			gridData = new GridData();
			gridData.horizontalSpan = 4;

			Label labelEmptyOne = new Label(cSystemName, SWT.NULL);
			labelEmptyOne.setLayoutData(gridData);

			gridData = new GridData();
			gridData.horizontalSpan = 4;
			gridData.horizontalAlignment = GridData.FILL;

			Group gQualityRequirement = new Group(cSystemName, SWT.NONE);
			gQualityRequirement.setLayoutData(gridData);
			gQualityRequirement.setText("Quality Requirements");
			gQualityRequirement.setLayout(new GridLayout(2, false));

			// Create column names
			String[] columnNames = new String[] { "Object", "Condition", "Quality Attribute", "Description Scenario" };
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

			colQualityAttribute = new TableColumn(table, SWT.NONE);
			colQualityAttribute.setWidth(200);
			colQualityAttribute.setText("Quality Attribute");
			
			colCondition = new TableColumn(table, SWT.NONE);
			colCondition.setWidth(200);
			colCondition.setText("Condition");

			colDescriptionScenario = new TableColumn(table, SWT.NONE);
			colDescriptionScenario.setWidth(200);
			colDescriptionScenario.setText("Description Scenario");

			for (int i = 0; i < 6; i++) {
			      TableItem item = new TableItem(table, SWT.NONE);
			      item.setText("Item " + i);
			}
			
			for (int i = 0; i < 6; i++) {
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
			editors[3] = null;

			// Assign the cell editors to the viewer
			tblViewerQualityRequirement.setCellEditors(editors);

			table.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					table.showSelection();
					btnConsult.setEnabled(true);
				}
			});

			gridData = new GridData();
			gridData.horizontalSpan = 1;
			gridData.widthHint = 100;
			gridData.horizontalAlignment = GridData.END;
			gridData.verticalAlignment = SWT.BOTTOM;
			gridData.grabExcessHorizontalSpace = true;

			btnConsult = new Button(gQualityRequirement, SWT.PUSH);
			btnConsult.setText(" Consult ");
			btnConsult.setEnabled(false);
			btnConsult.setLayoutData(gridData);
			btnConsult.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					getViewController().setModel((QualityRequirement) table.getItem(table.getSelectionIndex()).getData());
					getViewController().getFormSearch().setView();
					prepareView(6);
				}
			});
			
			//DESDE ACA LO QUE AGREGUÉ
			GridLayout layout1 = new GridLayout();
			layout.numColumns = 4;
			parent.setLayout(layout1);

			gridData = new GridData();
			gridData.horizontalSpan = 4;
			gridData.horizontalAlignment = GridData.FILL;

			Group gScenario = new Group(cSystemName, SWT.NONE);
			gScenario.setLayoutData(gridData);
			gScenario.setText("Scenario");
			gScenario.setLayout(new GridLayout(2, false));

			Label labelD = new Label(gScenario, SWT.NONE);
			labelD.setText("Description: ");

			gridData = new GridData();
			gridData.horizontalAlignment = GridData.FILL;
			gridData.grabExcessHorizontalSpace = true;

			txtDescription = new Text(gScenario, SWT.BORDER | SWT.MULTI);
			txtDescription.setLayoutData(gridData);

			Label labelQA = new Label(gScenario, SWT.NONE);
			labelQA.setText("Quality Attribute: ");

			cmbQualityAttribute = new ComboViewer(gScenario, SWT.READ_ONLY);
			cmbQualityAttribute.setContentProvider(ArrayContentProvider.getInstance());
			cmbQualityAttribute.getCombo().addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					if (((IStructuredSelection) cmbQualityAttribute.getSelection()).getFirstElement() != "") {
						cmbQualityAttributeItemStateChanged();
					} else {
						clearParts();
						prepareView(2);
					}
				}
			});

			Label labelC = new Label(gScenario, SWT.NONE);
			labelC.setText("Condition: ");

			cmbCondition = new ComboViewer(gScenario, SWT.READ_ONLY);
			cmbCondition.setContentProvider(ArrayContentProvider.getInstance());

			gridData = new GridData();
			gridData.horizontalSpan = 4;

			Label labelEmptyTree = new Label(cSystemName, SWT.NULL);
			labelEmptyTree.setLayoutData(gridData);

			gridData = new GridData();
			gridData.horizontalSpan = 4;
			gridData.horizontalAlignment = GridData.FILL;

			TabFolder folder = new TabFolder(cSystemName, SWT.NONE);
			folder.setLayoutData(gridData);

			/*---------------------------------------------------------------------------*/
			TabItem tabStimulusSource = new TabItem(folder, SWT.NONE);
			tabStimulusSource.setText("Stimulus Source");

			gStimulusSource = new Group(folder, SWT.NONE);
			gStimulusSource.setLayoutData(gridData);

			txtDescriptionStimulusSource = new StringFieldEditor("description", "Description: ", gStimulusSource);

			gridData = new GridData();
			gridData.horizontalAlignment = GridData.FILL;
			gridData.grabExcessHorizontalSpace = true;

			txtDescriptionStimulusSource.getTextControl(gStimulusSource).setLayoutData(gridData);

			Label labelSST = new Label(gStimulusSource, SWT.NONE);
			labelSST.setText("Type: ");

			cmbTypeStimulusSource = new ComboViewer(gStimulusSource, SWT.READ_ONLY);
			cmbTypeStimulusSource.setContentProvider(ArrayContentProvider.getInstance());

			txtValueStimulusSource = new StringFieldEditor(PreferenceConstants.P_STRING, "Value: ", gStimulusSource);

			gridData = new GridData();
			gridData.horizontalSpan = 4;
			gridData.horizontalAlignment = GridData.FILL;

			tabStimulusSource.setControl(gStimulusSource);

			/*---------------------------------------------------------------------------*/
			TabItem tabStimulus = new TabItem(folder, SWT.NONE);
			tabStimulus.setText("Stimulus");

			gStimulus = new Group(folder, SWT.NONE);
			gStimulus.setLayoutData(gridData);

			txtDescriptionStimulus = new StringFieldEditor("description", "Description: ", gStimulus);

			gridData = new GridData();
			gridData.horizontalAlignment = GridData.FILL;
			gridData.grabExcessHorizontalSpace = true;

			txtDescriptionStimulus.getTextControl(gStimulus).setLayoutData(gridData);

			Label labelST = new Label(gStimulus, SWT.NONE);
			labelST.setText("Type: ");

			cmbTypeStimulus = new ComboViewer(gStimulus, SWT.READ_ONLY);
			cmbTypeStimulus.setContentProvider(ArrayContentProvider.getInstance());

			txtValueStimulus = new StringFieldEditor(PreferenceConstants.P_STRING, "Value: ", gStimulus);

			tabStimulus.setControl(gStimulus);

			/*---------------------------------------------------------------------------*/
			TabItem tabEnvironment = new TabItem(folder, SWT.NONE);
			tabEnvironment.setText("Environment");

			gEnvironment = new Group(folder, SWT.NONE);
			gEnvironment.setLayoutData(gridData);

			txtDescriptionEnvironment = new StringFieldEditor("description", "Description: ", gEnvironment);

			gridData = new GridData();
			gridData.horizontalAlignment = GridData.FILL;
			gridData.grabExcessHorizontalSpace = true;

			txtDescriptionEnvironment.getTextControl(gEnvironment).setLayoutData(gridData);

			Label labelET = new Label(gEnvironment, SWT.NONE);
			labelET.setText("Type: ");

			cmbTypeEnvironment = new ComboViewer(gEnvironment, SWT.READ_ONLY);
			cmbTypeEnvironment.setContentProvider(ArrayContentProvider.getInstance());

			txtValueEnvironment = new StringFieldEditor(PreferenceConstants.P_STRING, "Value: ", gEnvironment);

			gridData = new GridData();
			gridData.horizontalSpan = 4;
			gridData.horizontalAlignment = GridData.FILL;

			tabEnvironment.setControl(gEnvironment);

			/*---------------------------------------------------------------------------*/
			TabItem tabArtifact = new TabItem(folder, SWT.NONE);
			tabArtifact.setText("Artifact");

			gArtifact = new Group(folder, SWT.NONE);
			gArtifact.setLayoutData(gridData);

			txtDescriptionArtifact = new StringFieldEditor("description", "Description: ", gArtifact);

			gridData = new GridData();
			gridData.horizontalAlignment = GridData.FILL;
			gridData.grabExcessHorizontalSpace = true;

			txtDescriptionArtifact.getTextControl(gArtifact).setLayoutData(gridData);

			Label labelAT = new Label(gArtifact, SWT.NONE);
			labelAT.setText("Type: ");

			cmbTypeArtifact = new ComboViewer(gArtifact, SWT.READ_ONLY);
			cmbTypeArtifact.setContentProvider(ArrayContentProvider.getInstance());

			tabArtifact.setControl(gArtifact);

			/*---------------------------------------------------------------------------*/
			TabItem tabResponse = new TabItem(folder, SWT.NONE);
			tabResponse.setText("Response");

			gResponse = new Group(folder, SWT.NONE);
			gResponse.setLayoutData(gridData);

			txtDescriptionResponse = new StringFieldEditor("description", "Description: ", gResponse);

			gridData = new GridData();
			gridData.horizontalAlignment = GridData.FILL;
			gridData.grabExcessHorizontalSpace = true;

			txtDescriptionResponse.getTextControl(gResponse).setLayoutData(gridData);

			Label labelRT = new Label(gResponse, SWT.NONE);
			labelRT.setText("Type: ");

			cmbTypeResponse = new ComboViewer(gResponse, SWT.READ_ONLY);
			cmbTypeResponse.setContentProvider(ArrayContentProvider.getInstance());

			txtValueResponse = new StringFieldEditor(PreferenceConstants.P_STRING, "Value: ", gResponse);

			/*---------------------------------------------------------------------------*/
			tabResponse.setControl(gResponse);

			TabItem tabResponseMeasure = new TabItem(folder, SWT.NONE);
			tabResponseMeasure.setText("Response Measure");

			gResponseMeasure = new Group(folder, SWT.NONE);
			gResponseMeasure.setLayoutData(gridData);

			txtDescriptionResponseMeasure = new StringFieldEditor("description", "Description: ", gResponseMeasure);

			gridData = new GridData();
			gridData.horizontalAlignment = GridData.FILL;
			gridData.grabExcessHorizontalSpace = true;

			txtDescriptionResponseMeasure.getTextControl(gResponseMeasure).setLayoutData(gridData);

			Label labelRMM = new Label(gResponseMeasure, SWT.NONE);
			labelRMM.setText("Type: ");

			cmbTypeResponseMeasure = new ComboViewer(gResponseMeasure, SWT.READ_ONLY);
			cmbTypeResponseMeasure.setContentProvider(ArrayContentProvider.getInstance());
			cmbTypeResponseMeasure.getCombo().addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					if (((IStructuredSelection) cmbTypeResponseMeasure.getSelection()).getFirstElement() != "") {
						cmbTypeResponseMeasureItemStateChanged();
					} else {
						getCmbMetric().getCombo().clearSelection();
						getCmbUnit().getCombo().clearSelection();
					}
				}
			});

			Label labelRMT = new Label(gResponseMeasure, SWT.NONE);
			labelRMT.setText("Metric: ");

			cmbMetric = new ComboViewer(gResponseMeasure, SWT.READ_ONLY);
			cmbMetric.setContentProvider(ArrayContentProvider.getInstance());
			cmbMetric.getCombo().addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					if (((IStructuredSelection) cmbMetric.getSelection()).getFirstElement() != "") {
						cmbMetricItemStateChanged();
					} else {
						getCmbUnit().getCombo().clearSelection();
					}
				}
			});

			txtValueResponseMeasure = new DoubleFieldEditor2("value", "Value: ", gResponseMeasure,this);
			txtValueResponseMeasure.setMinRange(0.0);

			Label labelRMU = new Label(gResponseMeasure, SWT.NONE);
			labelRMU.setText("Unit: ");

			cmbUnit = new ComboViewer(gResponseMeasure, SWT.READ_ONLY);
			cmbUnit.setContentProvider(ArrayContentProvider.getInstance());

			tabResponseMeasure.setControl(gResponseMeasure);

			/*---------------------------------------------------------------------------*/

			gridData = new GridData();
			gridData.horizontalSpan = 2;

			Label labelEmptyFour = new Label(cSystemName, SWT.NULL);
			labelEmptyFour.setLayoutData(gridData);

			gridData = new GridData();
			gridData.horizontalSpan = 1;
			gridData.widthHint = 100;
			gridData.horizontalAlignment = GridData.END;
			gridData.verticalAlignment = SWT.BOTTOM;
			gridData.grabExcessHorizontalSpace = true;

			btnSave = new Button(cSystemName, SWT.PUSH);
			btnSave.setText(" Save ");
			btnSave.setLayoutData(gridData);
			btnSave.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					viewController.save();
					clearView();
					prepareView(0);
				}
			});

			gridData = new GridData();
			gridData.horizontalSpan = 1;
			gridData.widthHint = 100;
			gridData.horizontalAlignment = GridData.END;
			gridData.verticalAlignment = SWT.BOTTOM;
			gridData.grabExcessHorizontalSpace = true;

			btnRemove = new Button(cSystemName, SWT.PUSH);
			btnRemove.setText("Remove");
			btnRemove.setLayoutData(gridData);
			btnRemove.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					if (viewController.createDeleteRequirementDialog() == 0) {
						viewController.remove();
						clearView();
						prepareView(0);
					}
				}
			});

			//HASTA ACA

			this.prepareView(0);

		} catch (JDBCConnectionException e) {
			viewController.createErrorDialog("Postgres service is not running");
		}

		return new Composite(parent, SWT.NULL);

	}

	@Override
	protected void createFieldEditors() {
	}

	public Text getTxtDescription() {
		return txtDescription;
	}

	public void setTxtDescription(Text txtDescription) {
		this.txtDescription = txtDescription;
	}

	public ComboViewer getCmbQualityAttribute() {
		return cmbQualityAttribute;
	}

	public void setCmbQualityAttribute(ComboViewer cmbQualityAttribute) {
		this.cmbQualityAttribute = cmbQualityAttribute;
	}

	public ComboViewer getCmbCondition() {
		return cmbCondition;
	}

	public void setCmbCondition(ComboViewer cmbCondition) {
		this.cmbCondition = cmbCondition;
	}

	public StringFieldEditor getTxtDescriptionStimulusSource() {
		return txtDescriptionStimulusSource;
	}

	public void setTxtDescriptionStimulusSource(StringFieldEditor txtDescriptionStimulusSource) {
		this.txtDescriptionStimulusSource = txtDescriptionStimulusSource;
	}

	public StringFieldEditor getTxtDescriptionStimulus() {
		return txtDescriptionStimulus;
	}

	public void setTxtDescriptionStimulus(StringFieldEditor txtDescriptionStimulus) {
		this.txtDescriptionStimulus = txtDescriptionStimulus;
	}

	public StringFieldEditor getTxtDescriptionEnvironment() {
		return txtDescriptionEnvironment;
	}

	public void setTxtDescriptionEnvironment(StringFieldEditor txtDescriptionEnvironment) {
		this.txtDescriptionEnvironment = txtDescriptionEnvironment;
	}

	public StringFieldEditor getTxtDescriptionArtifact() {
		return txtDescriptionArtifact;
	}

	public void setTxtDescriptionArtifact(StringFieldEditor txtDescriptionArtifact) {
		this.txtDescriptionArtifact = txtDescriptionArtifact;
	}

	public StringFieldEditor getTxtDescriptionResponse() {
		return txtDescriptionResponse;
	}

	public void setTxtDescriptionResponse(StringFieldEditor txtDescriptionResponse) {
		this.txtDescriptionResponse = txtDescriptionResponse;
	}

	public StringFieldEditor getTxtDescriptionResponseMeasure() {
		return txtDescriptionResponseMeasure;
	}

	public void setTxtDescriptionResponseMeasure(StringFieldEditor txtDescriptionResponseMeasure) {
		this.txtDescriptionResponseMeasure = txtDescriptionResponseMeasure;
	}

	public StringFieldEditor getTxtValueStimulusSource() {
		return txtValueStimulusSource;
	}

	public void setTxtValueStimulusSource(StringFieldEditor txtValueStimulusSource) {
		this.txtValueStimulusSource = txtValueStimulusSource;
	}

	public StringFieldEditor getTxtValueStimulus() {
		return txtValueStimulus;
	}

	public void setTxtValueStimulus(StringFieldEditor txtValueStimulus) {
		this.txtValueStimulus = txtValueStimulus;
	}

	public StringFieldEditor getTxtValueEnvironment() {
		return txtValueEnvironment;
	}

	public void setTxtValueEnvironment(StringFieldEditor txtValueEnvironment) {
		this.txtValueEnvironment = txtValueEnvironment;
	}

	public StringFieldEditor getTxtValueResponse() {
		return txtValueResponse;
	}

	public void setTxtValueResponse(StringFieldEditor txtValueResponse) {
		this.txtValueResponse = txtValueResponse;
	}

	public DoubleFieldEditor2 getTxtValueResponseMeasure() {
		return txtValueResponseMeasure;
	}

	public void setTxtValueResponseMeasure(DoubleFieldEditor2 txtValueResponseMeasure) {
		this.txtValueResponseMeasure = txtValueResponseMeasure;
	}

	public ComboViewer getCmbMetric() {
		return cmbMetric;
	}

	public void setCmbMetric(ComboViewer cmbMetric) {
		this.cmbMetric = cmbMetric;
	}

	public ComboViewer getCmbTypeStimulusSource() {
		return cmbTypeStimulusSource;
	}

	public void setCmbTypeStimulusSource(ComboViewer cmbTypeStimulusSource) {
		this.cmbTypeStimulusSource = cmbTypeStimulusSource;
	}

	public ComboViewer getCmbTypeStimulus() {
		return cmbTypeStimulus;
	}

	public void setCmbTypeStimulus(ComboViewer cmbTypeStimulus) {
		this.cmbTypeStimulus = cmbTypeStimulus;
	}

	public ComboViewer getCmbTypeEnvironment() {
		return cmbTypeEnvironment;
	}

	public void setCmbTypeEnvironment(ComboViewer cmbTypeEnvironment) {
		this.cmbTypeEnvironment = cmbTypeEnvironment;
	}

	public ComboViewer getCmbTypeArtifact() {
		return cmbTypeArtifact;
	}

	public void setCmbTypeArtifact(ComboViewer cmbTypeArtifact) {
		this.cmbTypeArtifact = cmbTypeArtifact;
	}

	public ComboViewer getCmbTypeResponse() {
		return cmbTypeResponse;
	}

	public void setCmbTypeResponse(ComboViewer cmbTypeResponse) {
		this.cmbTypeResponse = cmbTypeResponse;
	}

	public ComboViewer getCmbTypeResponseMeasure() {
		return cmbTypeResponseMeasure;
	}

	public void setCmbTypeResponseMeasure(ComboViewer cmbTypeResponseMeasure) {
		this.cmbTypeResponseMeasure = cmbTypeResponseMeasure;
	}

	public ComboViewer getCmbUnit() {
		return cmbUnit;
	}

	public void setCmbUnit(ComboViewer cmbUnit) {
		this.cmbUnit = cmbUnit;
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
	
	private void cmbQualityAttributeItemStateChanged() {// GEN-FIRST:event_cmbNombreItemStateChanged
		this.prepareView(3);
		this.loadGenericScenario(
				(QualityAttribute) ((IStructuredSelection) this.getCmbQualityAttribute().getSelection())
						.getFirstElement());
	}

	public void loadGenericScenario(QualityAttribute qualityAttribute) {
		this.getViewController().setModelStimulusSourceTypes(qualityAttribute);
		this.getViewController().setModelStimulusTypes(qualityAttribute);
		this.getViewController().setModelEnvironmentTypes(qualityAttribute);
		this.getViewController().setModelArtifactTypes(qualityAttribute);
		this.getViewController().setModelResponseTypes(qualityAttribute);
		this.getViewController().setModelResponseMeasureTypes(qualityAttribute);
	}

	private void cmbTypeResponseMeasureItemStateChanged() {// GEN-FIRST:event_cmbNombreItemStateChanged
		this.prepareView(4);
		this.loadCmbMetric(
				(ResponseMeasureType) ((IStructuredSelection) this.getCmbTypeResponseMeasure().getSelection())
						.getFirstElement());
	}

	public void loadCmbMetric(ResponseMeasureType responseMeasureType) {
		this.getViewController().setModelMetric(responseMeasureType);
	}

	private void cmbMetricItemStateChanged() {// GEN-FIRST:event_cmbNombreItemStateChanged
		this.prepareView(5);
		this.loadCmbUnit((Metric) ((IStructuredSelection) this.getCmbMetric().getSelection()).getFirstElement());
	}

	public void loadCmbUnit(Metric metric) {
		this.getViewController().setModelUnit(metric);
	}

	public void clearParts() {
		txtDescriptionStimulusSource.setStringValue("");
		txtDescriptionStimulus.setStringValue("");
		txtDescriptionEnvironment.setStringValue("");
		txtDescriptionArtifact.setStringValue("");
		txtDescriptionResponse.setStringValue("");
		txtDescriptionResponseMeasure.setStringValue("");

		cmbTypeStimulusSource.getCombo().clearSelection();
		cmbTypeStimulus.getCombo().clearSelection();
		cmbTypeEnvironment.getCombo().clearSelection();
		cmbTypeArtifact.getCombo().clearSelection();
		cmbTypeResponse.getCombo().clearSelection();
		cmbTypeResponseMeasure.getCombo().clearSelection();
		cmbMetric.getCombo().clearSelection();
		cmbUnit.getCombo().clearSelection();

		txtValueStimulusSource.setStringValue("");
		txtValueStimulus.setStringValue("");
		txtValueEnvironment.setStringValue("");
		txtValueResponse.setStringValue("");
		txtValueResponseMeasure.setStringValue("");
	}

	public void clearScenario() {
		txtDescription.setText("");
		cmbQualityAttribute.getCombo().clearSelection();
		cmbCondition.getCombo().clearSelection();
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
			this.getTxtDescription().setEnabled(false);
			this.getCmbQualityAttribute().getCombo().setEnabled(false);
			this.getCmbCondition().getCombo().setEnabled(false);

			this.getTxtDescriptionStimulusSource().setEnabled(false, gStimulusSource);
			this.getTxtDescriptionStimulus().setEnabled(false, gStimulus);
			this.getTxtDescriptionEnvironment().setEnabled(false, gEnvironment);
			this.getTxtDescriptionArtifact().setEnabled(false, gArtifact);
			this.getTxtDescriptionResponse().setEnabled(false, gResponse);
			this.getTxtDescriptionResponseMeasure().setEnabled(false, gResponseMeasure);

			this.getCmbTypeStimulusSource().getCombo().setEnabled(false);
			this.getCmbTypeStimulus().getCombo().setEnabled(false);
			this.getCmbTypeEnvironment().getCombo().setEnabled(false);
			this.getCmbTypeArtifact().getCombo().setEnabled(false);
			this.getCmbTypeResponse().getCombo().setEnabled(false);
			this.getCmbTypeResponseMeasure().getCombo().setEnabled(false);
			this.getCmbMetric().getCombo().setEnabled(false);
			this.getCmbUnit().getCombo().setEnabled(false);

			this.getTxtValueStimulusSource().setEnabled(false, gStimulusSource);
			this.getTxtValueStimulus().setEnabled(false, gStimulus);
			this.getTxtValueEnvironment().setEnabled(false, gEnvironment);
			this.getTxtValueResponse().setEnabled(false, gResponse);
			this.getTxtValueResponseMeasure().setEnabled(false, gResponseMeasure);

			this.getBtnSave().setEnabled(true);
			this.getBtnRemove().setEnabled(true);

			// this.getBtnConsult().setEnabled(true);

			break;
		case 1:// With system selected
			this.getTblViewerQualityRequirement().getTable().setEnabled(true);

			break;
		case 2:// With system selected
			this.getTxtDescription().setEnabled(true);
			this.getCmbQualityAttribute().getCombo().setEnabled(true);
			this.getCmbCondition().getCombo().setEnabled(true);

			break;
		case 3:// With quality attribute selected
			this.getTxtDescriptionStimulusSource().setEnabled(true, gStimulusSource);
			this.getTxtDescriptionStimulus().setEnabled(true, gStimulus);
			this.getTxtDescriptionEnvironment().setEnabled(true, gEnvironment);
			this.getTxtDescriptionArtifact().setEnabled(true, gArtifact);
			this.getTxtDescriptionResponse().setEnabled(true, gResponse);
			this.getTxtDescriptionResponseMeasure().setEnabled(true, gResponseMeasure);

			this.getCmbTypeStimulusSource().getCombo().setEnabled(true);
			this.getCmbTypeStimulus().getCombo().setEnabled(true);
			this.getCmbTypeEnvironment().getCombo().setEnabled(true);
			this.getCmbTypeArtifact().getCombo().setEnabled(true);
			this.getCmbTypeResponse().getCombo().setEnabled(true);
			this.getCmbTypeResponseMeasure().getCombo().setEnabled(true);

			this.getTxtValueStimulusSource().setEnabled(true, gStimulusSource);
			this.getTxtValueStimulus().setEnabled(true, gStimulus);
			this.getTxtValueEnvironment().setEnabled(true, gEnvironment);
			this.getTxtValueResponse().setEnabled(true, gResponse);
			this.getTxtValueResponseMeasure().setEnabled(true, gResponseMeasure);

			break;
		case 4:// With type response measure selected
			this.getCmbMetric().getCombo().setEnabled(true);

			break;
		case 5:// With metric selected
			this.getCmbUnit().getCombo().setEnabled(true);

			break;
		case 6: //With bottom consult selected
			this.getCmbQualityAttribute().getCombo().setEnabled(true);
			this.getCmbCondition().getCombo().setEnabled(true);
			
			this.getTxtDescriptionStimulusSource().setEnabled(true, gStimulusSource);
			this.getTxtDescriptionStimulus().setEnabled(true, gStimulus);
			this.getTxtDescriptionEnvironment().setEnabled(true, gEnvironment);
			this.getTxtDescriptionArtifact().setEnabled(true, gArtifact);
			this.getTxtDescriptionResponse().setEnabled(true, gResponse);
			this.getTxtDescriptionResponseMeasure().setEnabled(true, gResponseMeasure);

			this.getCmbTypeStimulusSource().getCombo().setEnabled(true);
			this.getCmbTypeStimulus().getCombo().setEnabled(true);
			this.getCmbTypeEnvironment().getCombo().setEnabled(true);
			this.getCmbTypeArtifact().getCombo().setEnabled(true);
			this.getCmbTypeResponse().getCombo().setEnabled(true);
			this.getCmbTypeResponseMeasure().getCombo().setEnabled(true);

			this.getTxtValueStimulusSource().setEnabled(true, gStimulusSource);
			this.getTxtValueStimulus().setEnabled(true, gStimulus);
			this.getTxtValueEnvironment().setEnabled(true, gEnvironment);
			this.getTxtValueResponse().setEnabled(true, gResponse);
			this.getTxtValueResponseMeasure().setEnabled(true, gResponseMeasure);

			this.getCmbMetric().getCombo().setEnabled(true);

			this.getCmbUnit().getCombo().setEnabled(true);

			break;
		}
	}

	public void setView() {
		this.getViewController().getView();
	}

	public void loadCmbCondition() {
		this.getViewController().setModelCondition();
	}

	public void loadCmbQualityAttribute() {
		this.getViewController().setModelQualityAttribute();
		
	}

}
