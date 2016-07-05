package project.preferences.controller;

import org.eclipse.jface.viewers.IStructuredSelection;

import project.preferences.NewQualityRequirementPreferencePage;
import software.BusinessLogic.AnalysisManager;
import software.DomainModel.AnalysisEntity.Artifact;
import software.DomainModel.AnalysisEntity.ArtifactType;
import software.DomainModel.AnalysisEntity.Condition;
import software.DomainModel.AnalysisEntity.Environment;
import software.DomainModel.AnalysisEntity.EnvironmentType;
import software.DomainModel.AnalysisEntity.Metric;
import software.DomainModel.AnalysisEntity.QualityAttribute;
import software.DomainModel.AnalysisEntity.Response;
import software.DomainModel.AnalysisEntity.ResponseMeasure;
import software.DomainModel.AnalysisEntity.ResponseMeasureType;
import software.DomainModel.AnalysisEntity.ResponseType;
import software.DomainModel.AnalysisEntity.Stimulus;
import software.DomainModel.AnalysisEntity.StimulusSource;
import software.DomainModel.AnalysisEntity.StimulusSourceType;
import software.DomainModel.AnalysisEntity.StimulusType;
import software.DomainModel.AnalysisEntity.Unit;

public class NewQualityRequirementPPController extends Controller {
	/**
	 * Attributes
	 */
	private static NewQualityRequirementPPController controller;
	private AnalysisManager manager;
	private NewQualityRequirementPreferencePage form;

	public static NewQualityRequirementPPController getController() {
		return controller;
	}

	public static void setController(NewQualityRequirementPPController controller) {
		NewQualityRequirementPPController.controller = controller;
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

	public NewQualityRequirementPreferencePage getForm() {
		return form;
	}

	public void setForm(NewQualityRequirementPreferencePage form) {
		this.form = form;
	}

	public void setModelSystem() {
		this.getForm().getCmbSystem().setInput(getManager().getComboModelSystem());
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
		this.getForm().getCmbTypeStimulusSource()
				.setInput(getManager().getComboModelStimulusSourceType(qualityAttribute));
	}

	public void setModelStimulusTypes(QualityAttribute qualityAttribute) {
		this.getForm().getCmbTypeStimulus().setInput(getManager().getComboModelStimulusType(qualityAttribute));
	}

	public void setModelEnvironmentTypes(QualityAttribute qualityAttribute) {
		this.getForm().getCmbTypeEnvironment().setInput(getManager().getComboModelEnvironmentType(qualityAttribute));
	}

	public void setModelArtifactTypes(QualityAttribute qualityAttribute) {
		this.getForm().getCmbTypeArtifact().setInput(getManager().getComboModelArtifactType(qualityAttribute));
	}

	public void setModelResponseTypes(QualityAttribute qualityAttribute) {
		this.getForm().getCmbTypeResponse().setInput(getManager().getComboModelResponseType(qualityAttribute));
	}

	public void setModelResponseMeasureTypes(QualityAttribute qualityAttribute) {
		this.getForm().getCmbTypeResponseMeasure()
				.setInput(getManager().getComboModelResponseMeasureType(qualityAttribute));
	}

	public void setModelMetric(ResponseMeasureType type) {
		this.getForm().getCmbMetric().setInput(getManager().getComboModelMetric(type));
	}

	public void setModelUnit(Metric type) {
		this.getForm().getCmbUnit().setInput(getManager().getComboModelUnit(type));
	}

	public void save() {
		int err = this.newQualityRequirement();
		if (err == 0) {
			this.getManager().saveQualityRequirement();
		}
	}

	public int newQualityRequirement() {
		if (this.isValidData()) {
			StimulusSource stimulusSource = new StimulusSource(
					this.getForm().getTxtDescriptionStimulusSource().getStringValue(),
					this.getForm().getTxtValueStimulusSource().getStringValue(),
					(StimulusSourceType) ((IStructuredSelection) this.getForm().getCmbTypeStimulusSource()
							.getSelection()).getFirstElement());
			Stimulus stimulus = new Stimulus(this.getForm().getTxtDescriptionStimulus().getStringValue(),
					this.getForm().getTxtValueStimulus().getStringValue(),
					(StimulusType) ((IStructuredSelection) this.getForm().getCmbTypeStimulus().getSelection())
							.getFirstElement());
			Environment environment = new Environment(this.getForm().getTxtDescriptionEnvironment().getStringValue(),
					this.getForm().getTxtValueEnvironment().getStringValue(),
					(EnvironmentType) ((IStructuredSelection) this.getForm().getCmbTypeEnvironment().getSelection())
							.getFirstElement());
			Artifact artifact = new Artifact(this.getForm().getTxtDescriptionArtifact().getStringValue(),
					(ArtifactType) ((IStructuredSelection) this.getForm().getCmbTypeArtifact().getSelection())
							.getFirstElement());
			Response response = new Response(this.getForm().getTxtDescriptionResponse().getStringValue(),
					this.getForm().getTxtValueResponse().getStringValue(),
					(ResponseType) ((IStructuredSelection) this.getForm().getCmbTypeResponse().getSelection())
							.getFirstElement());
			ResponseMeasure responseMeasure = new ResponseMeasure(
					this.getForm().getTxtDescriptionResponseMeasure().getStringValue(),
					this.getForm().getTxtValueResponseMeasure().getDoubleValue(),
					(ResponseMeasureType) ((IStructuredSelection) this.getForm().getCmbTypeResponseMeasure()
							.getSelection()).getFirstElement(),
					(Metric) ((IStructuredSelection) this.getForm().getCmbMetric().getSelection()).getFirstElement(),
					(Unit) ((IStructuredSelection) this.getForm().getCmbUnit().getSelection()).getFirstElement());

			this.getManager()
					.newQualityRequirement(
							(software.DomainModel.AnalysisEntity.System) ((IStructuredSelection) this.getForm()
									.getCmbSystem().getSelection()).getFirstElement(),
							this.getForm().getTxtDescription().getText(), true,
							(QualityAttribute) ((IStructuredSelection) this.getForm().getCmbQualityAttribute()
									.getSelection()).getFirstElement(),
							stimulusSource, stimulus, artifact, environment, response, responseMeasure,
							(Condition) ((IStructuredSelection) this.getForm().getCmbCondition().getSelection())
									.getFirstElement());
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
			return false;
		} else if (this.isEmpty(this.getForm().getTxtDescriptionStimulus())) {
			this.createErrorDialog("Empty stimulus description");
			return false;
		} else if (this.isEmpty(this.getForm().getTxtDescriptionEnvironment())) {
			this.createErrorDialog("Empty environment description");
			return false;
		} else if (this.isEmpty(this.getForm().getTxtDescriptionArtifact())) {
			this.createErrorDialog("Empty artifact description");
			return false;
		} else if (this.isEmpty(this.getForm().getTxtDescriptionResponse())) {
			this.createErrorDialog("Empty response description");
			return false;
		} else if (this.isEmpty(this.getForm().getTxtDescriptionResponseMeasure())) {
			this.createErrorDialog("Empty response measure description");
			return false;
		} else if (this.isEmpty(this.getForm().getTxtValueStimulusSource())) {
			this.createErrorDialog("Empty stimulus source value");
			return false;
		} else if (this.isEmpty(this.getForm().getTxtValueStimulus())) {
			this.createErrorDialog("Empty stimulus value");
			return false;
		} else if (this.isEmpty(this.getForm().getTxtValueEnvironment())) {
			this.createErrorDialog("Empty environment value");
			return false;
		} else if (this.isEmpty(this.getForm().getTxtValueResponse())) {
			this.createErrorDialog("Empty response value");
			return false;
		} else if (this.isEmpty(this.getForm().getCmbTypeStimulusSource())) {
			this.createErrorDialog("Select stimulus source type");
			this.getForm().getCmbTypeStimulusSource().getCombo().setFocus();
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
			this.getForm().getCmbTypeResponseMeasure().getCombo().setFocus();
			return false;
		} else if (this.isEmpty(this.getForm().getCmbMetric())) {
			this.createErrorDialog("Select metric");
			this.getForm().getCmbMetric().getCombo().setFocus();
			return false;
		} else if (this.isEmpty(this.getForm().getTxtValueResponseMeasure())) {
			this.createErrorDialog("Empty response measure value");
			return false;
		} else if (!this.getForm().getTxtValueResponseMeasure().isValid()) {
			this.createErrorDialog("Invalid response measure value");
			return false;
		} else if (this.getForm().getTxtValueResponseMeasure().getDoubleValue() <= 0) {
			this.createErrorDialog("Invalid response measure value (negative number)");
			return false;
		} else if (this.isEmpty(this.getForm().getCmbUnit())) {
			this.createErrorDialog("Select unit");
			this.getForm().getCmbUnit().getCombo().setFocus();
			return false;
		}

		return true;
	}

}
