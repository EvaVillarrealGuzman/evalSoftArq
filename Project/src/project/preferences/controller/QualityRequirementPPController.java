package project.preferences.controller;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TableItem;

import project.preferences.EditQualityRequirementPreferencePage;
import project.preferences.SearchQualityRequirementPreferencePage;
import software.BusinessLogic.AnalysisManager;
import software.DomainModel.AnalysisEntity.Artifact;
import software.DomainModel.AnalysisEntity.ArtifactType;
import software.DomainModel.AnalysisEntity.Condition;
import software.DomainModel.AnalysisEntity.Environment;
import software.DomainModel.AnalysisEntity.EnvironmentType;
import software.DomainModel.AnalysisEntity.Metric;
import software.DomainModel.AnalysisEntity.QualityAttribute;
import software.DomainModel.AnalysisEntity.QualityRequirement;
import software.DomainModel.AnalysisEntity.Response;
import software.DomainModel.AnalysisEntity.ResponseMeasure;
import software.DomainModel.AnalysisEntity.ResponseMeasureType;
import software.DomainModel.AnalysisEntity.ResponseType;
import software.DomainModel.AnalysisEntity.Stimulus;
import software.DomainModel.AnalysisEntity.StimulusSource;
import software.DomainModel.AnalysisEntity.StimulusSourceType;
import software.DomainModel.AnalysisEntity.StimulusType;
import software.DomainModel.AnalysisEntity.Unit;
import software.Presentation.GUIAnalysis.FrmSystemManagement;

public class QualityRequirementPPController extends Controller {
	/**
	 * Attributes
	 */
	private static QualityRequirementPPController controller;
	private AnalysisManager manager;
	private EditQualityRequirementPreferencePage form;
	private SearchQualityRequirementPreferencePage formSearch;

	public static QualityRequirementPPController getController() {
		return controller;
	}

	public static void setController(QualityRequirementPPController controller) {
		QualityRequirementPPController.controller = controller;
	}

	public AnalysisManager getManager() {
		if (manager == null) {
			manager = new AnalysisManager();
		}
		return manager;
	}

	public void setManager(AnalysisManager manager) {
		this.manager = manager;
	}

	public EditQualityRequirementPreferencePage getForm() {
		return form;
	}

	public void setForm(EditQualityRequirementPreferencePage form) {
		this.form = form;
	}
	
	public SearchQualityRequirementPreferencePage getFormSearch() {
		return formSearch;
	}

	public void setFormSearch(SearchQualityRequirementPreferencePage formSearch) {
		this.formSearch = formSearch;
	}

	public void setModelSystem() {
		this.getForm().getCmbSystem().setInput(getManager().getComboModelSystem());
	}
	
	public void setModelSystemSearch() {
		this.getFormSearch().getCmbSystem().setInput(getManager().getComboModelSystemWithRequirements());
	}
	
	public void setModelQualityAttribute() {
		this.getForm().getCmbQualityAttribute().setInput(getManager().getComboModelQualityAttribute());
	}
	
	public void setModelCondition() {
		this.getForm().getCmbCondition().setInput(getManager().getComboModelCondition());
	}
	
	/**
	 * Sets the model of types combo
	 */
	public void setModelStimulusSourceTypes(QualityAttribute qualityAttribute) {
		this.getForm()
				.getCmbTypeStimulusSource()
				.setInput(
						getManager().getComboModelStimulusSourceType(
								qualityAttribute));
	}

	public void setModelStimulusTypes(QualityAttribute qualityAttribute) {
		this.getForm()
				.getCmbTypeStimulus()
				.setInput(
						getManager()
								.getComboModelStimulusType(qualityAttribute));
	}

	public void setModelEnvironmentTypes(QualityAttribute qualityAttribute) {
		this.getForm()
				.getCmbTypeEnvironment()
				.setInput(
						getManager().getComboModelEnvironmentType(
								qualityAttribute));
	}

	public void setModelArtifactTypes(QualityAttribute qualityAttribute) {
		this.getForm()
				.getCmbTypeArtifact()
				.setInput(
						getManager()
								.getComboModelArtifactType(qualityAttribute));
	}

	public void setModelResponseTypes(QualityAttribute qualityAttribute) {
		this.getForm()
				.getCmbTypeResponse()
				.setInput(
						getManager()
								.getComboModelResponseType(qualityAttribute));
	}

	public void setModelResponseMeasureTypes(QualityAttribute qualityAttribute) {
		this.getForm()
				.getCmbTypeResponseMeasure()
				.setInput(
						getManager().getComboModelResponseMeasureType(
								qualityAttribute));
	}

	public void setModelMetric(ResponseMeasureType type) {
		this.getForm().getCmbMetric()
				.setInput(getManager().getComboModelMetric(type));
	}

	public void setModelUnit(Metric type) {
		this.getForm().getCmbUnit()
				.setInput(getManager().getComboModelUnit(type));
	}

	public void save() {
		int err;
		err = this.setQualityRequirement();
		if (err == 0) {
			this.getManager().updateQualityRequirement();
		}
	}
	
	public void remove() {
		this.getManager().removeQualityRequirement();
	}
	
	public int setQualityRequirement() {
		if (this.isValidData()) {
			StimulusSource stimulusSource = new StimulusSource(
					this.getForm().getTxtDescriptionStimulusSource().getStringValue(), 
					this.getForm().getTxtValueStimulusSource().getStringValue(),
					(StimulusSourceType) ((IStructuredSelection) this.getForm().getCmbTypeStimulusSource().getSelection()).getFirstElement());
			Stimulus stimulus = new Stimulus(
					this.getForm().getTxtDescriptionStimulus().getStringValue(), 
					this.getForm().getTxtValueStimulus().getStringValue(), 
					(StimulusType) ((IStructuredSelection) this.getForm().getCmbTypeStimulus().getSelection()).getFirstElement());
			Environment environment = new Environment(
					this.getForm().getTxtDescriptionEnvironment().getStringValue(), 
					this.getForm().getTxtValueEnvironment().getStringValue(), 
					(EnvironmentType) ((IStructuredSelection) this.getForm().getCmbTypeEnvironment().getSelection()).getFirstElement());
			Artifact artifact = new Artifact(
					this.getForm().getTxtDescriptionArtifact().getStringValue(), 
					(ArtifactType) ((IStructuredSelection) this.getForm().getCmbTypeArtifact().getSelection()).getFirstElement());
			Response response = new Response(
					this.getForm().getTxtDescriptionResponse().getStringValue(), 
					this.getForm().getTxtValueResponse().getStringValue(), 
					(ResponseType) ((IStructuredSelection) this.getForm().getCmbTypeResponse().getSelection()).getFirstElement());
			ResponseMeasure responseMeasure = new ResponseMeasure(
					this.getForm().getTxtDescriptionResponseMeasure().getStringValue(),
					Double.parseDouble(this.getForm().getTxtValueResponseMeasure().getStringValue()),
					(ResponseMeasureType) ((IStructuredSelection) this.getForm().getCmbTypeResponseMeasure().getSelection()).getFirstElement(),
					(Metric) ((IStructuredSelection) this.getForm().getCmbMetric().getSelection()).getFirstElement(), 
					(Unit) ((IStructuredSelection) this.getForm().getCmbUnit().getSelection()).getFirstElement());
			
			this.getManager().newQualityRequirement(
					(software.DomainModel.AnalysisEntity.System) ((IStructuredSelection) this.getForm().getCmbSystem().getSelection()).getFirstElement(),
					this.getForm().getTxtDescription().getText(),
					true,
					(QualityAttribute) ((IStructuredSelection) this.getForm().getCmbQualityAttribute().getSelection()).getFirstElement(),
					stimulusSource,
					stimulus,
					artifact,
					environment,
					response,
					responseMeasure,
					(Condition) ((IStructuredSelection) this.getForm().getCmbCondition().getSelection()).getFirstElement());
			return 0;
		} else {
			return 1;
		}
	}
	
	public boolean isValidData() {
		if (this.isEmpty(this.getForm().getCmbSystem())) {
			this.createErrorDialog("Select system");
			this.getForm().getCmbSystem().getCombo().setFocus();
			return false;
		}
		if (this.isEmpty(this.getForm().getTxtDescription())) {
			this.createErrorDialog("Empty description");
			this.getForm().getTxtDescription().setFocus();
			return false;
		}
		if (this.isEmpty(this.getForm().getCmbQualityAttribute())) {
			this.createErrorDialog("Select quality attribute");
			this.getForm().getCmbQualityAttribute().getCombo().setFocus();
			return false;
		}
		if (this.isEmpty(this.getForm().getCmbCondition())) {
			this.createErrorDialog("Select condition");
			this.getForm().getCmbCondition().getCombo().setFocus();
			return false;
		} else if (this.isEmpty(this.getForm().getTxtDescriptionStimulusSource())) {
			this.createErrorDialog("Empty stimulus source description");
			this.getForm()
					.getTxtDescriptionStimulusSource().getTextControl(this.getForm().getcParts()).setFocus();
			return false;
		} else if (this.isEmpty(this.getForm().getTxtDescriptionStimulus())) {
			this.createErrorDialog("Empty stimulus description");
			this.getForm()
					.getTxtDescriptionStimulus().getTextControl(this.getForm().getcParts()).setFocus();
			return false;
		} else if (this.isEmpty(this.getForm().getTxtDescriptionEnvironment())) {
			this.createErrorDialog("Empty environment description");
			this.getForm()
					.getTxtDescriptionEnvironment().getTextControl(this.getForm().getcParts()).setFocus();
			return false;
		} else if (this.isEmpty(this.getForm().getTxtDescriptionArtifact())) {
			this.createErrorDialog("Empty artifact description");
			this.getForm()
					.getTxtDescriptionArtifact().getTextControl(this.getForm().getcParts()).setFocus();
			return false;
		} else if (this.isEmpty(this.getForm().getTxtDescriptionResponse())) {
			this.createErrorDialog("Empty response description");
			this.getForm()
					.getTxtDescriptionResponse().getTextControl(this.getForm().getcParts()).setFocus();
			return false;
		} else if (this.isEmpty(this.getForm().getTxtDescriptionResponseMeasure())) {
			this.createErrorDialog("Empty response measure description");
			this.getForm()
					.getTxtDescriptionResponseMeasure().getTextControl(this.getForm().getcParts()).setFocus();
			return false;
		} else if (this.isEmpty(this.getForm().getTxtValueStimulusSource())) {
			this.createErrorDialog("Empty stimulus source value");
			this.getForm()
					.getTxtValueStimulusSource().getTextControl(this.getForm().getcParts()).setFocus();
			return false;
		} else if (this.isEmpty(this.getForm().getTxtValueStimulus())) {
			this.createErrorDialog("Empty stimulus value");
			this.getForm().getTxtValueStimulus().getTextControl(this.getForm().getcParts()).setFocus();
			return false;
		} else if (this.isEmpty(this.getForm().getTxtValueEnvironment())) {
			this.createErrorDialog("Empty environment value");
			this.getForm().getTxtValueEnvironment().getTextControl(this.getForm().getcParts()).setFocus();
			return false;
		} else if (this.isEmpty(this.getForm().getTxtValueResponse())) {
			this.createErrorDialog("Empty response value");
			this.getForm().getTxtValueResponse().getTextControl(this.getForm().getcParts()).setFocus();
			return false;
		} else if (this.isEmpty(this.getForm().getCmbTypeStimulusSource())) {
			this.createErrorDialog("Select stimulus source type");
			this.getForm()
					.getCmbTypeStimulusSource().getCombo().setFocus();
			return false;
		} else if (this.isEmpty(this.getForm().getCmbTypeStimulus())) {
			this.createErrorDialog("Select stimulus type");
			this.getForm().getCmbTypeStimulus().getCombo().setFocus();
			return false;
		} else if (this.isEmpty(this.getForm().getCmbTypeEnvironment())) {
			this.createErrorDialog("Select environment type");
			this.getForm().getCmbTypeEnvironment().getCombo().setFocus();
			return false;
		} else if (this.isEmpty(this.getForm().getCmbTypeArtifact())) {
			this.createErrorDialog("Select artifact type");
			this.getForm().getCmbTypeArtifact().getCombo().setFocus();
			return false;
		} else if (this.isEmpty(this.getForm().getCmbTypeResponse())) {
			this.createErrorDialog("Select response type");
			this.getForm().getCmbTypeResponse().getCombo().setFocus();
			return false;
		} else if (this.isEmpty(this.getForm().getCmbTypeResponseMeasure())) {
			this.createErrorDialog("Select response measure type");
			this.getForm()
					.getCmbTypeResponseMeasure().getCombo().setFocus();
			return false;
		} else if (this.isEmpty(this.getForm().getCmbMetric())) {
			this.createErrorDialog("Select metric");
			this.getForm().getCmbMetric().getCombo().setFocus();
			return false;
		} else if (this.isEmpty(this.getForm().getTxtValueResponseMeasure())) {
			this.createErrorDialog("Empty response measure value");
			this.getForm()
					.getTxtValueResponseMeasure().getTextControl(this.getForm().getcParts()).setFocus();
			return false;
		} else if (Double.parseDouble(this.getForm().getTxtValueResponseMeasure().getStringValue()) <= 0) {
			this.createErrorDialog("Invalid response measure value");
			this.getForm()
					.getTxtValueResponseMeasure().getTextControl(this.getForm().getcParts()).setFocus();
			return false;
		} else if (this.isEmpty(this.getForm().getCmbUnit())) {
			this.createErrorDialog("Select unit");
			this.getForm().getCmbUnit().getCombo().setFocus();
			return false;
		}

		return true;
	}
	
	/**
	 * Sets the model table of the quality requirements of a specific system
	 * @param ptype (System)
	 */
	public void setModelQualityRequirement(software.DomainModel.AnalysisEntity.System ptype) {
		this.getManager().setSystem(ptype);
		while (this.getFormSearch().getTable().getItems().length > 0) {
			this.getFormSearch().getTable().remove(0);
		}
		int i=0;
		for (QualityRequirement dp : this.getManager().getQualityRequirements()) {
			if (dp.isState()) {
				TableItem item = new TableItem(this.getFormSearch().getTable(), SWT.NONE);
				item.setText(new String[]{dp.toString(), dp.getQualityScenario().getQualityAttribute().toString(), dp.getQualityScenario().getCondition().toString(), dp.getQualityScenario().getDescription().toString()});	
			}
		}
	}
	
	public boolean checkPoint(String ptext) {
		return ptext.contains(".");
	}

	public boolean firstPoint(String ptext) {
		if (ptext.length() > 0) {
			return false;
		}
		return true;
	}

}
