package project.preferences;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.hibernate.exception.JDBCConnectionException;

import project.preferences.controller.QualityRequirementPPController;
import software.DomainModel.AnalysisEntity.Metric;
import software.DomainModel.AnalysisEntity.QualityAttribute;
import software.DomainModel.AnalysisEntity.ResponseMeasureType;

public class EditQualityRequirementPreferencePage extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage {
	/**
	 * Attributes
	 */
	private Group groupPropierties;
	private Composite cPropierties;
	private ComboViewer cmbSystem;
	private Group groupScenario;
	private Composite cScenario;
	private Text txtDescription;
	private ComboViewer cmbQualityAttribute;
	private ComboViewer cmbCondition;
	private Group groupParts;
	private Composite cParts;
	private Label lblDescription;
	private StringFieldEditor txtDescriptionStimulusSource;
	private StringFieldEditor txtDescriptionStimulus;
	private StringFieldEditor txtDescriptionEnvironment;
	private StringFieldEditor txtDescriptionArtifact;
	private StringFieldEditor txtDescriptionResponse;
	private StringFieldEditor txtDescriptionResponseMeasure;
	private Label lblValue;
	private StringFieldEditor txtValueStimulusSource;
	private StringFieldEditor txtValueStimulus;
	private StringFieldEditor txtValueEnvironment;
	private StringFieldEditor txtValueResponse;
	private DoubleFieldEditor txtValueResponseMeasure;
	private Label lblMetric;
	private ComboViewer cmbMetric;
	private Label lblType;
	private ComboViewer cmbTypeStimulusSource;
	private ComboViewer cmbTypeStimulus;
	private ComboViewer cmbTypeEnvironment;
	private ComboViewer cmbTypeArtifact;
	private ComboViewer cmbTypeResponse;
	private ComboViewer cmbTypeResponseMeasure;
	private Label lblUnit;
	private ComboViewer cmbUnit;
	private Button btnSave;
	private Button btnRemove;
	private static EditQualityRequirementPreferencePage qualityRequirementPP;
	private QualityRequirementPPController viewController;

	public EditQualityRequirementPreferencePage() {
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
			this.getViewController().setForm(this);

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

			groupScenario = new Group(parent, SWT.SHADOW_ETCHED_IN);
			groupScenario.setText("Scenario");

			GridLayout layoutScenario = new GridLayout();
			layoutScenario.numColumns = 1;
			groupScenario.setLayout(layoutScenario);

			GridData dataScenario = new GridData();
			groupScenario.setLayoutData(dataScenario);

			cScenario = new Composite(groupScenario, SWT.NONE);
			dataScenario = new GridData();
			dataScenario.grabExcessHorizontalSpace = true;
			dataScenario.horizontalIndent = 40;
			cScenario.setLayoutData(dataScenario);

			addField(new StringFieldEditor(PreferenceConstants.P_STRING, "Project Name: ", cScenario));

			Label labelD = new Label(cScenario, SWT.NONE);
			labelD.setText("Description: ");

			txtDescription = new Text(cScenario, SWT.BORDER | SWT.MULTI);

			Label labelQA = new Label(cScenario, SWT.NONE);
			labelQA.setText("Quality Attribute: ");

			cmbQualityAttribute = new ComboViewer(cScenario, SWT.READ_ONLY);

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

			Label labelC = new Label(cScenario, SWT.NONE);
			labelC.setText("Condition: ");

			cmbCondition = new ComboViewer(cScenario, SWT.READ_ONLY);

			cmbCondition.setContentProvider(ArrayContentProvider.getInstance());

			groupParts = new Group(cScenario, SWT.SHADOW_ETCHED_IN);
			groupParts.setText("Parts");

			GridLayout layoutParts = new GridLayout();
			layoutParts.numColumns = 5;
			groupParts.setLayout(layoutParts);

			GridData dataParts = new GridData();
			groupParts.setLayoutData(dataParts);

			cParts = new Composite(groupParts, SWT.NONE);
			dataParts = new GridData();
			dataParts.grabExcessHorizontalSpace = true;
			dataParts.horizontalIndent = 40;
			cPropierties.setLayoutData(dataParts);

			lblDescription = new Label(cParts, SWT.NONE);
			lblDescription.setText("Description");

			lblType = new Label(cParts, SWT.NONE);
			lblType.setText("Type");

			lblMetric = new Label(cParts, SWT.NONE);
			lblMetric.setText("Metric");

			lblValue = new Label(cParts, SWT.NONE);
			lblValue.setText("Value");

			lblUnit = new Label(cParts, SWT.NONE);
			lblUnit.setText("Unit");

			txtDescriptionStimulusSource = new StringFieldEditor(PreferenceConstants.P_STRING, "Stimulus Source: ",
					cParts);
			addField(txtDescriptionStimulusSource);
			cmbTypeStimulusSource = new ComboViewer(cScenario, SWT.READ_ONLY);
			cmbTypeStimulusSource.setContentProvider(ArrayContentProvider.getInstance());
			txtValueStimulusSource = new StringFieldEditor(PreferenceConstants.P_STRING, "", cParts);
			addField(txtValueStimulusSource);

			txtDescriptionStimulus = new StringFieldEditor(PreferenceConstants.P_STRING, "Stimulus Source: ", cParts);
			addField(txtDescriptionStimulus);
			cmbTypeStimulus = new ComboViewer(cScenario, SWT.READ_ONLY);
			cmbTypeStimulus.setContentProvider(ArrayContentProvider.getInstance());
			txtValueStimulus = new StringFieldEditor(PreferenceConstants.P_STRING, "", cParts);
			addField(txtValueStimulus);

			txtDescriptionEnvironment = new StringFieldEditor(PreferenceConstants.P_STRING, "Environment: ", cParts);
			addField(txtDescriptionEnvironment);
			cmbTypeEnvironment = new ComboViewer(cScenario, SWT.READ_ONLY);
			cmbTypeEnvironment.setContentProvider(ArrayContentProvider.getInstance());
			txtValueEnvironment = new StringFieldEditor(PreferenceConstants.P_STRING, "", cParts);
			addField(txtValueEnvironment);

			txtDescriptionArtifact = new StringFieldEditor(PreferenceConstants.P_STRING, "Artifact: ", cParts);
			addField(txtDescriptionArtifact);
			cmbTypeArtifact = new ComboViewer(cScenario, SWT.READ_ONLY);
			cmbTypeArtifact.setContentProvider(ArrayContentProvider.getInstance());

			txtDescriptionResponse = new StringFieldEditor(PreferenceConstants.P_STRING, "Response: ", cParts);
			addField(txtDescriptionResponse);
			cmbTypeResponse = new ComboViewer(cScenario, SWT.READ_ONLY);
			cmbTypeResponse.setContentProvider(ArrayContentProvider.getInstance());
			txtValueResponse = new StringFieldEditor(PreferenceConstants.P_STRING, "", cParts);
			addField(txtValueResponse);

			txtDescriptionResponseMeasure = new StringFieldEditor(PreferenceConstants.P_STRING, "Response Measure: ",
					cParts);
			addField(txtDescriptionResponseMeasure);
			cmbTypeResponseMeasure = new ComboViewer(cScenario, SWT.READ_ONLY);
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
			txtValueResponseMeasure = new DoubleFieldEditor(PreferenceConstants.P_STRING, "", cParts);
			txtValueResponseMeasure.setMinRange(0.0);
			addField(txtValueResponseMeasure);

			cmbMetric = new ComboViewer(cScenario, SWT.READ_ONLY);
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

			cmbUnit = new ComboViewer(cScenario, SWT.READ_ONLY);
			cmbUnit.setContentProvider(ArrayContentProvider.getInstance());

			btnSave = new Button(parent, SWT.PUSH);
			btnSave.setText(" Save ");
			btnSave.setToolTipText("Save");
			btnSave.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					viewController.save();
					clearView();
					prepareView(0);
				}
			});

			btnRemove = new Button(parent, SWT.PUSH);
			btnRemove.setText("Remove");
			btnRemove.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					if (viewController.createDeleteDialog() == 0) {
						viewController.remove();
						clearView();
						prepareView(0);
					}
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

	public static EditQualityRequirementPreferencePage getQualityRequirementPP() {
		return qualityRequirementPP;
	}

	public static void setQualityRequirementPP(EditQualityRequirementPreferencePage qualityRequirementPP) {
		EditQualityRequirementPreferencePage.qualityRequirementPP = qualityRequirementPP;
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

	public Group getGroupScenario() {
		return groupScenario;
	}

	public void setGroupScenario(Group groupScenario) {
		this.groupScenario = groupScenario;
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

	public Group getGroupParts() {
		return groupParts;
	}

	public void setGroupParts(Group groupParts) {
		this.groupParts = groupParts;
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

	public Composite getcScenario() {
		return cScenario;
	}

	public void setcScenario(Composite cScenario) {
		this.cScenario = cScenario;
	}

	public Composite getcParts() {
		return cParts;
	}

	public void setcParts(Composite cParts) {
		this.cParts = cParts;
	}

	public void loadCmbSystem() {
		this.getViewController().setModelSystem();
	}

	public void loadCmbQualityAttribute() {
		this.getViewController().setModelQualityAttribute();
	}

	public void loadCmbCondition() {
		this.getViewController().setModelCondition();
	}

	private void cmbSystemItemStateChanged() {// GEN-FIRST:event_cmbNombreItemStateChanged
		this.prepareView(1);
		this.loadCmbQualityAttribute();
		this.loadCmbCondition();
	}

	private void cmbQualityAttributeItemStateChanged() {// GEN-FIRST:event_cmbNombreItemStateChanged
		this.prepareView(2);
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
		this.prepareView(3);
		this.loadCmbMetric(
				(ResponseMeasureType) ((IStructuredSelection) this.getCmbTypeResponseMeasure().getSelection())
						.getFirstElement());
	}

	public void loadCmbMetric(ResponseMeasureType responseMeasureType) {
		this.getViewController().setModelMetric(responseMeasureType);
	}

	private void cmbMetricItemStateChanged() {// GEN-FIRST:event_cmbNombreItemStateChanged
		this.prepareView(4);
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
		if (!getViewController().getManager().existSystemTrue()) {
			this.getViewController().createErrorDialog("No saved systems");
			pabm = 0;
		}
		switch (pabm) {
		case 0:// New quality requirement
			this.getCmbSystem().getCombo().setEnabled(true);
			loadCmbSystem();

			this.getTxtDescription().setEnabled(false);
			this.getCmbQualityAttribute().getCombo().setEnabled(false);
			this.getCmbCondition().getCombo().setEnabled(false);

			this.getTxtDescriptionStimulusSource().setEnabled(false, cParts);
			this.getTxtDescriptionStimulus().setEnabled(false, cParts);
			this.getTxtDescriptionEnvironment().setEnabled(false, cParts);
			this.getTxtDescriptionArtifact().setEnabled(false, cParts);
			this.getTxtDescriptionResponse().setEnabled(false, cParts);
			this.getTxtDescriptionResponseMeasure().setEnabled(false, cParts);

			this.getCmbTypeStimulusSource().getCombo().setEnabled(false);
			this.getCmbTypeStimulus().getCombo().setEnabled(false);
			this.getCmbTypeEnvironment().getCombo().setEnabled(false);
			this.getCmbTypeArtifact().getCombo().setEnabled(false);
			this.getCmbTypeResponse().getCombo().setEnabled(false);
			this.getCmbTypeResponseMeasure().getCombo().setEnabled(false);
			this.getCmbMetric().getCombo().setEnabled(false);
			this.getCmbUnit().getCombo().setEnabled(false);

			this.getTxtValueStimulusSource().setEnabled(false, cParts);
			this.getTxtValueStimulus().setEnabled(false, cParts);
			this.getTxtValueEnvironment().setEnabled(false, cParts);
			this.getTxtValueResponse().setEnabled(false, cParts);
			this.getTxtValueResponseMeasure().setEnabled(false, cParts);

			this.getBtnSave().setEnabled(true);
			this.getBtnRemove().setEnabled(true);

			break;
		case 1:// With system selected
			this.getTxtDescription().setEnabled(true);
			this.getCmbQualityAttribute().getCombo().setEnabled(true);
			this.getCmbCondition().getCombo().setEnabled(true);

			break;
		case 2:// With quality attribute selected
			this.getTxtDescriptionStimulusSource().setEnabled(true, cParts);
			this.getTxtDescriptionStimulus().setEnabled(true, cParts);
			this.getTxtDescriptionEnvironment().setEnabled(true, cParts);
			this.getTxtDescriptionArtifact().setEnabled(true, cParts);
			this.getTxtDescriptionResponse().setEnabled(true, cParts);
			this.getTxtDescriptionResponseMeasure().setEnabled(true, cParts);

			this.getCmbTypeStimulusSource().getCombo().setEnabled(true);
			this.getCmbTypeStimulus().getCombo().setEnabled(true);
			this.getCmbTypeEnvironment().getCombo().setEnabled(true);
			this.getCmbTypeArtifact().getCombo().setEnabled(true);
			this.getCmbTypeResponse().getCombo().setEnabled(true);
			this.getCmbTypeResponseMeasure().getCombo().setEnabled(true);

			this.getTxtValueStimulusSource().setEnabled(true, cParts);
			this.getTxtValueStimulus().setEnabled(true, cParts);
			this.getTxtValueEnvironment().setEnabled(true, cParts);
			this.getTxtValueResponse().setEnabled(true, cParts);
			this.getTxtValueResponseMeasure().setEnabled(true, cParts);

			break;
		case 3:// With type response measure selected
			this.getCmbMetric().getCombo().setEnabled(true);

			break;
		case 4:// With metric selected
			this.getCmbUnit().getCombo().setEnabled(true);

			break;
		}

	}
	
	public void setView() {
		this.getViewController().getView();
	}

}
