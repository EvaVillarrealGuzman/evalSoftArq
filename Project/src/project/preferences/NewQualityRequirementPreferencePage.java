package project.preferences;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
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
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.hibernate.exception.JDBCConnectionException;

import project.preferences.controller.NewQualityRequirementPPController;
import software.DomainModel.AnalysisEntity.Metric;
import software.DomainModel.AnalysisEntity.QualityAttribute;
import software.DomainModel.AnalysisEntity.ResponseMeasureType;

/**
 * To create a new quality requirement
 * 
 * @author: Micaela
 */
public class NewQualityRequirementPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {
	/**
	 * Attributes
	 */
	private ComboViewer cmbSystem;
	private Text txtDescription;
	private ComboViewer cmbQualityAttribute;
	private ComboViewer cmbCondition;
	private Text txtDescriptionStimulusSource;
	private Text txtDescriptionStimulus;
	private Text txtDescriptionEnvironment;
	private Text txtDescriptionArtifact;
	private Text txtDescriptionResponse;
	private Text txtDescriptionResponseMeasure;
	private StringFieldEditor txtValueStimulusSource;
	private StringFieldEditor txtValueStimulus;
	private StringFieldEditor txtValueEnvironment;
	private StringFieldEditor txtValueResponse;
	private DoubleFieldEditor txtValueResponseMeasure;
	private Group gStimulusSource;
	private Group gResponse;
	private Group gStimulus;
	private Group gArtifact;
	private Group gEnvironment;
	private Group gResponseMeasure;
	private ComboViewer cmbMetric;
	private ComboViewer cmbTypeStimulusSource;
	private ComboViewer cmbTypeStimulus;
	private ComboViewer cmbTypeEnvironment;
	private ComboViewer cmbTypeArtifact;
	private ComboViewer cmbTypeResponse;
	private ComboViewer cmbTypeResponseMeasure;
	private org.eclipse.swt.widgets.Label lblvalueResponseMeasure;
	private GridData gridData;
	private ComboViewer cmbUnit;
	private Button btnNew;
	private static NewQualityRequirementPreferencePage qualityRequirementPP;
	private NewQualityRequirementPPController viewController;

	/**
	 * Constructor
	 */
	public NewQualityRequirementPreferencePage() {
		super(GRID);
		noDefaultAndApplyButton();
		viewController = new NewQualityRequirementPPController();
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

			Composite cSystemName = new Composite(parent, SWT.NULL);
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

			cmbSystem = new ComboViewer(cSystemName, SWT.READ_ONLY);
			cmbSystem.setContentProvider(ArrayContentProvider.getInstance());
			cmbSystem.getCombo().setLayoutData(gridData);
			cmbSystem.getCombo().addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					if (((IStructuredSelection) cmbSystem.getSelection()).getFirstElement() != "") {
						clearScenario();
						prepareView(5);
						cmbSystemItemStateChanged();
					} else {
						clearScenario();
						prepareView(0);
					}
				}
			});

			Label labelEmptyOne = new Label(cSystemName, SWT.NULL);
			labelEmptyOne.setLayoutData(gridData);

			gridData = new GridData();
			gridData.horizontalSpan = 4;

			Label labelEmptyTwo = new Label(cSystemName, SWT.NULL);
			labelEmptyTwo.setLayoutData(gridData);

			gridData = new GridData();
			gridData.horizontalSpan = 4;
			gridData.horizontalAlignment = GridData.FILL;

			Group gScenario = new Group(cSystemName, SWT.NONE);
			gScenario.setLayoutData(gridData);
			gScenario.setText(PreferenceConstants.Scenario_Group);
			gScenario.setLayout(new GridLayout(2, false));

			Label labelD = new Label(gScenario, SWT.NONE);
			labelD.setText(PreferenceConstants.Description_Label + ":");

			gridData = new GridData();
			gridData.horizontalAlignment = GridData.FILL;
			gridData.grabExcessHorizontalSpace = true;

			txtDescription = new Text(gScenario, SWT.BORDER | SWT.MULTI);
			txtDescription.setLayoutData(gridData);

			Label labelQA = new Label(gScenario, SWT.NONE);
			labelQA.setText(PreferenceConstants.QualityAttribute_Label + ":");

			cmbQualityAttribute = new ComboViewer(gScenario, SWT.READ_ONLY);
			cmbQualityAttribute.setContentProvider(ArrayContentProvider.getInstance());
			cmbQualityAttribute.getCombo().addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					if (((IStructuredSelection) cmbQualityAttribute.getSelection()).getFirstElement() != "") {
						cmbQualityAttributeItemStateChanged();
					} else {
						clearParts();
						prepareView(1);
					}
				}
			});

			Label labelC = new Label(gScenario, SWT.NONE);
			labelC.setText(PreferenceConstants.Condition_Label + ":");

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
			tabStimulusSource.setText(PreferenceConstants.StimulusSource_Label);

			gStimulusSource = new Group(folder, SWT.NONE);
			gStimulusSource.setLayoutData(gridData);

			gridData = new GridData();
			gridData.horizontalAlignment = GridData.FILL;
			gridData.grabExcessHorizontalSpace = true;

			Label labelSST = new Label(gStimulusSource, SWT.NONE);
			labelSST.setText(PreferenceConstants.Type_Label + ":");

			cmbTypeStimulusSource = new ComboViewer(gStimulusSource, SWT.READ_ONLY);
			cmbTypeStimulusSource.setContentProvider(ArrayContentProvider.getInstance());

			txtValueStimulusSource = new StringFieldEditor(PreferenceConstants.ValueStimulusSourceLabel,
					PreferenceConstants.ValueStimulusSourceLabel + ":", gStimulusSource);

			Label labelDSS = new Label(gStimulusSource, SWT.NONE);
			labelDSS.setText(PreferenceConstants.Description_Label + ":");

			txtDescriptionStimulusSource = new Text(gStimulusSource, SWT.BORDER | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
			txtDescriptionStimulusSource.setLayoutData(gridData);

			gridData = new GridData();
			gridData.horizontalSpan = 4;
			gridData.horizontalAlignment = GridData.FILL;

			tabStimulusSource.setControl(gStimulusSource);

			/*---------------------------------------------------------------------------*/
			TabItem tabStimulus = new TabItem(folder, SWT.NONE);
			tabStimulus.setText(PreferenceConstants.Stimulus_Label);

			gStimulus = new Group(folder, SWT.NONE);
			gStimulus.setLayoutData(gridData);

			gridData = new GridData();
			gridData.horizontalAlignment = GridData.FILL;
			gridData.grabExcessHorizontalSpace = true;

			Label labelST = new Label(gStimulus, SWT.NONE);
			labelST.setText(PreferenceConstants.Type_Label + ":");

			cmbTypeStimulus = new ComboViewer(gStimulus, SWT.READ_ONLY);
			cmbTypeStimulus.setContentProvider(ArrayContentProvider.getInstance());

			txtValueStimulus = new StringFieldEditor(PreferenceConstants.ValueStimulus_Label,
					PreferenceConstants.ValueStimulus_Label + ":", gStimulus);

			Label labelDS = new Label(gStimulus, SWT.NONE);
			labelDS.setText(PreferenceConstants.Description_Label + ":");

			txtDescriptionStimulus = new Text(gStimulus, SWT.BORDER | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
			txtDescriptionStimulus.setLayoutData(gridData);

			tabStimulus.setControl(gStimulus);

			/*---------------------------------------------------------------------------*/
			TabItem tabEnvironment = new TabItem(folder, SWT.NONE);
			tabEnvironment.setText(PreferenceConstants.Environment_Label);

			gEnvironment = new Group(folder, SWT.NONE);
			gEnvironment.setLayoutData(gridData);

			gridData = new GridData();
			gridData.horizontalAlignment = GridData.FILL;
			gridData.grabExcessHorizontalSpace = true;

			Label labelET = new Label(gEnvironment, SWT.NONE);
			labelET.setText(PreferenceConstants.Type_Label + ":");

			cmbTypeEnvironment = new ComboViewer(gEnvironment, SWT.READ_ONLY);
			cmbTypeEnvironment.setContentProvider(ArrayContentProvider.getInstance());

			txtValueEnvironment = new StringFieldEditor(PreferenceConstants.ValueEnvironment_Label,
					PreferenceConstants.ValueEnvironment_Label + ":", gEnvironment);

			Label labelDE = new Label(gEnvironment, SWT.NONE);
			labelDE.setText(PreferenceConstants.Description_Label + ":");

			txtDescriptionEnvironment = new Text(gEnvironment, SWT.BORDER | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
			txtDescriptionEnvironment.setLayoutData(gridData);

			tabEnvironment.setControl(gEnvironment);

			/*---------------------------------------------------------------------------*/
			TabItem tabArtifact = new TabItem(folder, SWT.NONE);
			tabArtifact.setText(PreferenceConstants.Artifact_Label);

			gArtifact = new Group(folder, SWT.NONE);
			gArtifact.setLayoutData(gridData);

			gridData = new GridData();
			gridData.horizontalAlignment = GridData.FILL;
			gridData.grabExcessHorizontalSpace = true;

			Label labelAT = new Label(gArtifact, SWT.NONE);
			labelAT.setText(PreferenceConstants.Type_Label + ":");

			cmbTypeArtifact = new ComboViewer(gArtifact, SWT.READ_ONLY);
			cmbTypeArtifact.setContentProvider(ArrayContentProvider.getInstance());

			Label labelDA = new Label(gArtifact, SWT.NONE);
			labelDA.setText(PreferenceConstants.Description_Label + ":");

			txtDescriptionArtifact = new Text(gArtifact, SWT.BORDER | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
			txtDescriptionArtifact.setLayoutData(gridData);

			StringFieldEditor text = new StringFieldEditor("", "", gArtifact);
			text.getLabelControl(gArtifact).setVisible(false);
			text.getTextControl(gArtifact).setVisible(false);

			tabArtifact.setControl(gArtifact);

			/*---------------------------------------------------------------------------*/
			TabItem tabResponse = new TabItem(folder, SWT.NONE);
			tabResponse.setText(PreferenceConstants.Response_Label);

			gResponse = new Group(folder, SWT.NONE);
			gResponse.setLayoutData(gridData);

			gridData = new GridData();
			gridData.horizontalAlignment = GridData.FILL;
			gridData.grabExcessHorizontalSpace = true;

			Label labelRT = new Label(gResponse, SWT.NONE);
			labelRT.setText(PreferenceConstants.Type_Label + ":");

			cmbTypeResponse = new ComboViewer(gResponse, SWT.READ_ONLY);
			cmbTypeResponse.setContentProvider(ArrayContentProvider.getInstance());

			txtValueResponse = new StringFieldEditor(PreferenceConstants.ValueResponse_Label,
					PreferenceConstants.ValueResponse_Label + ":", gResponse);

			Label labelDR = new Label(gResponse, SWT.NONE);
			labelDR.setText(PreferenceConstants.Description_Label + ":");

			txtDescriptionResponse = new Text(gResponse, SWT.BORDER | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
			txtDescriptionResponse.setLayoutData(gridData);

			/*---------------------------------------------------------------------------*/
			tabResponse.setControl(gResponse);

			TabItem tabResponseMeasure = new TabItem(folder, SWT.NONE);
			tabResponseMeasure.setText(PreferenceConstants.ResponseMeasure_Label);

			gResponseMeasure = new Group(folder, SWT.NONE);
			gResponseMeasure.setLayoutData(gridData);

			gridData = new GridData();
			gridData.horizontalAlignment = GridData.FILL;

			Label labelRMM = new Label(gResponseMeasure, SWT.NONE);
			labelRMM.setText(PreferenceConstants.Type_Label + ":");

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
			labelRMT.setText(PreferenceConstants.Metric + ":");

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

			txtValueResponseMeasure = new DoubleFieldEditor(PreferenceConstants.ResponseMeasure_Label,
					PreferenceConstants.ResponseMeasure_Label + ":", gResponseMeasure);
			txtValueResponseMeasure.setMinRange(0.0);
			txtValueResponseMeasure.setPage(this);

			Label labelRMU = new Label(gResponseMeasure, SWT.NONE);
			labelRMU.setText(PreferenceConstants.Unit_Label + ":");

			cmbUnit = new ComboViewer(gResponseMeasure, SWT.READ_ONLY);
			cmbUnit.setContentProvider(ArrayContentProvider.getInstance());

			Label labelDRM = new Label(gResponseMeasure, SWT.NONE);
			labelDRM.setText(PreferenceConstants.Description_Label + ":");

			txtDescriptionResponseMeasure = new Text(gResponseMeasure,
					SWT.BORDER | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
			txtDescriptionResponseMeasure.setLayoutData(gridData);

			tabResponseMeasure.setControl(gResponseMeasure);

			/*---------------------------------------------------------------------------*/

			gridData = new GridData();
			gridData.horizontalSpan = 4;

			Label labelEmptyFour = new Label(cSystemName, SWT.NULL);
			labelEmptyFour.setLayoutData(gridData);

			gridData = new GridData();
			gridData.horizontalSpan = 4;
			gridData.widthHint = 100;
			gridData.horizontalAlignment = GridData.END;
			gridData.verticalAlignment = SWT.BOTTOM;
			gridData.grabExcessHorizontalSpace = true;

			btnNew = new Button(cSystemName, SWT.PUSH);
			btnNew.setText(PreferenceConstants.ButtomSave_Label);
			btnNew.setLayoutData(gridData);
			btnNew.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					viewController.save();
				}
			});

			this.prepareView(0);

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
	public static NewQualityRequirementPreferencePage getQualityRequirementPP() {
		return qualityRequirementPP;
	}

	public static void setQualityRequirementPP(NewQualityRequirementPreferencePage qualityRequirementPP) {
		NewQualityRequirementPreferencePage.qualityRequirementPP = qualityRequirementPP;
	}

	public NewQualityRequirementPPController getViewController() {
		return viewController;
	}

	public void setViewController(NewQualityRequirementPPController viewController) {
		this.viewController = viewController;
	}

	public ComboViewer getCmbSystem() {
		return cmbSystem;
	}

	public void setCmbSystem(ComboViewer cmbSystem) {
		this.cmbSystem = cmbSystem;
	}

	public Label getLblvalueResponseMeasure() {
		return lblvalueResponseMeasure;
	}

	public void setLblvalueResponseMeasure(Label lblvalueResponseMeasure) {
		this.lblvalueResponseMeasure = lblvalueResponseMeasure;
	}

	public Button getBtnNew() {
		return btnNew;
	}

	public void setBtnNew(Button btnNew) {
		this.btnNew = btnNew;
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

	public Text getTxtDescriptionStimulusSource() {
		return txtDescriptionStimulusSource;
	}

	public void setTxtDescriptionStimulusSource(Text txtDescriptionStimulusSource) {
		this.txtDescriptionStimulusSource = txtDescriptionStimulusSource;
	}

	public Text getTxtDescriptionStimulus() {
		return txtDescriptionStimulus;
	}

	public void setTxtDescriptionStimulus(Text txtDescriptionStimulus) {
		this.txtDescriptionStimulus = txtDescriptionStimulus;
	}

	public Text getTxtDescriptionEnvironment() {
		return txtDescriptionEnvironment;
	}

	public void setTxtDescriptionEnvironment(Text txtDescriptionEnvironment) {
		this.txtDescriptionEnvironment = txtDescriptionEnvironment;
	}

	public Text getTxtDescriptionArtifact() {
		return txtDescriptionArtifact;
	}

	public void setTxtDescriptionArtifact(Text txtDescriptionArtifact) {
		this.txtDescriptionArtifact = txtDescriptionArtifact;
	}

	public Text getTxtDescriptionResponse() {
		return txtDescriptionResponse;
	}

	public void setTxtDescriptionResponse(Text txtDescriptionResponse) {
		this.txtDescriptionResponse = txtDescriptionResponse;
	}

	public Text getTxtDescriptionResponseMeasure() {
		return txtDescriptionResponseMeasure;
	}

	public void setTxtDescriptionResponseMeasure(Text txtDescriptionResponseMeasure) {
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

	public DoubleFieldEditor getTxtValueResponseMeasure() {
		return txtValueResponseMeasure;
	}

	public void setTxtValueResponseMeasure(DoubleFieldEditor txtValueResponseMeasure) {
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

	/**
	 * Load systems with state=true in the combo
	 */
	public void loadCmbSystem() {
		this.getViewController().setModelSystem();
	}

	/**
	 * Load all quality attributes in the combo
	 */
	public void loadCmbQualityAttribute() {
		this.getViewController().setModelQualityAttribute();
	}

	/**
	 * Load all conditions in the combo
	 */
	public void loadCmbCondition() {
		this.getViewController().setModelCondition();
	}

	/**
	 * When a system is selected, prepare the view and load quality attributes
	 * and conditions
	 */
	private void cmbSystemItemStateChanged() {// GEN-FIRST:event_cmbNombreItemStateChanged
		this.prepareView(1);
		this.loadCmbQualityAttribute();
		this.loadCmbCondition();
	}

	/**
	 * When a quality attribute is selected, enables the especification and load
	 * the generic scenario
	 */
	private void cmbQualityAttributeItemStateChanged() {// GEN-FIRST:event_cmbNombreItemStateChanged
		this.prepareView(2);
		this.loadGenericScenario(
				(QualityAttribute) ((IStructuredSelection) this.getCmbQualityAttribute().getSelection())
						.getFirstElement());
	}

	/**
	 * Load the types of the generic scenario for a specific quality attribute
	 * 
	 * @param qualityAtribute
	 */
	public void loadGenericScenario(QualityAttribute qualityAttribute) {
		this.getViewController().setModelStimulusSourceTypes(qualityAttribute);
		this.getViewController().setModelStimulusTypes(qualityAttribute);
		this.getViewController().setModelEnvironmentTypes(qualityAttribute);
		this.getViewController().setModelArtifactTypes(qualityAttribute);
		this.getViewController().setModelResponseTypes(qualityAttribute);
		this.getViewController().setModelResponseMeasureTypes(qualityAttribute);
	}

	/**
	 * When a response measure type is selected, load the metrics
	 */
	private void cmbTypeResponseMeasureItemStateChanged() {// GEN-FIRST:event_cmbNombreItemStateChanged
		this.prepareView(3);
		this.loadCmbMetric(
				(ResponseMeasureType) ((IStructuredSelection) this.getCmbTypeResponseMeasure().getSelection())
						.getFirstElement());
	}

	/**
	 * Load all metrics for a specific response measure type param
	 * responseMeasureType
	 */
	public void loadCmbMetric(ResponseMeasureType responseMeasureType) {
		this.getViewController().setModelMetric(responseMeasureType);
	}

	/**
	 * When a metric is selected, load the units
	 */
	private void cmbMetricItemStateChanged() {// GEN-FIRST:event_cmbNombreItemStateChanged
		this.prepareView(4);
		this.loadCmbUnit((Metric) ((IStructuredSelection) this.getCmbMetric().getSelection()).getFirstElement());
	}

	/**
	 * Load all units for a specific metric param metric
	 */
	public void loadCmbUnit(Metric metric) {
		this.getViewController().setModelUnit(metric);
	}

	/**
	 * Clean the parts of quality scenario (descriptions, types and values)
	 */
	public void clearParts() {
		txtDescriptionStimulusSource.setText("");
		txtDescriptionStimulus.setText("");
		txtDescriptionEnvironment.setText("");
		txtDescriptionArtifact.setText("");
		txtDescriptionResponse.setText("");
		txtDescriptionResponseMeasure.setText("");

		cmbTypeStimulusSource.setSelection(StructuredSelection.EMPTY);
		cmbTypeStimulus.setSelection(StructuredSelection.EMPTY);
		cmbTypeEnvironment.setSelection(StructuredSelection.EMPTY);
		cmbTypeArtifact.setSelection(StructuredSelection.EMPTY);
		cmbTypeResponse.setSelection(StructuredSelection.EMPTY);
		cmbTypeResponseMeasure.setSelection(StructuredSelection.EMPTY);
		cmbMetric.setSelection(StructuredSelection.EMPTY);
		cmbUnit.setSelection(StructuredSelection.EMPTY);

		txtValueStimulusSource.setStringValue("");
		txtValueStimulus.setStringValue("");
		txtValueEnvironment.setStringValue("");
		txtValueResponse.setStringValue("");
		txtValueResponseMeasure.setStringValue("0.0");
	}

	/**
	 * Clean the quality scenario (description, quality attribute, condition and
	 * parts)
	 */
	public void clearScenario() {
		txtDescription.setText("");
		cmbQualityAttribute.setSelection(StructuredSelection.EMPTY);
		cmbCondition.setSelection(StructuredSelection.EMPTY);
		this.clearParts();
	}

	/**
	 * Clean the system selected and the quality scenario
	 */
	public void clearView() {
		cmbSystem.setSelection(StructuredSelection.EMPTY);
		this.clearScenario();
	}

	/**
	 * prepare the view for the different actions that are possible
	 * 
	 * @param pabm
	 */
	public void prepareView(int pabm) {
		this.getCmbSystem().getCombo().setFocus();
		if (!getViewController().getManager().existSystemTrue()) {
			this.getViewController().createErrorDialog(PreferenceConstants.NoSavedSystem_ErrorDialog);
			pabm = 0;
		}
		switch (pabm) {
		case 0:// New quality requirement
			this.getCmbSystem().getCombo().setEnabled(true);
			loadCmbSystem();

			this.getTxtDescription().setEnabled(false);
			this.getCmbQualityAttribute().getCombo().setEnabled(false);
			this.getCmbCondition().getCombo().setEnabled(false);

			this.getTxtDescriptionStimulusSource().setEnabled(false);
			this.getTxtDescriptionStimulus().setEnabled(false);
			this.getTxtDescriptionEnvironment().setEnabled(false);
			this.getTxtDescriptionArtifact().setEnabled(false);
			this.getTxtDescriptionResponse().setEnabled(false);
			this.getTxtDescriptionResponseMeasure().setEnabled(false);

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

			this.getBtnNew().setEnabled(false);

			break;
		case 1:// With system selected
			this.getTxtDescription().setEnabled(true);
			this.getCmbQualityAttribute().getCombo().setEnabled(true);
			this.getCmbCondition().getCombo().setEnabled(true);
			this.getBtnNew().setEnabled(true);

			break;
		case 2:// With quality attribute selected
			this.getTxtDescriptionStimulusSource().setEnabled(true);
			this.getTxtDescriptionStimulus().setEnabled(true);
			this.getTxtDescriptionEnvironment().setEnabled(true);
			this.getTxtDescriptionArtifact().setEnabled(true);
			this.getTxtDescriptionResponse().setEnabled(true);
			this.getTxtDescriptionResponseMeasure().setEnabled(true);

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
		case 3:// With type response measure selected
			this.getCmbMetric().getCombo().setEnabled(true);

			break;
		case 4:// With metric selected
			this.getCmbUnit().getCombo().setEnabled(true);
			break;
		case 5:
			this.getCmbSystem().getCombo().setEnabled(true);

			this.getTxtDescription().setEnabled(true);
			this.getCmbQualityAttribute().getCombo().setEnabled(true);
			this.getCmbCondition().getCombo().setEnabled(true);

			this.getTxtDescriptionStimulusSource().setEnabled(false);
			this.getTxtDescriptionStimulus().setEnabled(false);
			this.getTxtDescriptionEnvironment().setEnabled(false);
			this.getTxtDescriptionArtifact().setEnabled(false);
			this.getTxtDescriptionResponse().setEnabled(false);
			this.getTxtDescriptionResponseMeasure().setEnabled(false);

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

			this.getBtnNew().setEnabled(true);

			break;
		}

	}

}
