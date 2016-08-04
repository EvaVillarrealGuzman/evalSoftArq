package project.preferences.controller;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TableItem;

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

public class QualityRequirementPPController extends Controller {
	/**
	 * Attributes
	 */
	private static QualityRequirementPPController controller;
	private AnalysisManager manager;
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

	public SearchQualityRequirementPreferencePage getFormSearch() {
		return formSearch;
	}

	public void setFormSearch(SearchQualityRequirementPreferencePage formSearch) {
		this.formSearch = formSearch;
	}


	public void setModelSystemSearch() {
		this.getFormSearch().getCmbSystem().setInput(getManager().getComboModelSystemWithRequirements());
	}

	public void setModelQualityAttribute() {
		this.getFormSearch().getCmbQualityAttribute().setInput(getManager().getComboModelQualityAttribute());
	}

	public void setModelCondition() {
		this.getFormSearch().getCmbCondition().setInput(getManager().getComboModelCondition());
	}

	/**
	 * Sets the model of types combo
	 */
	public void setModelStimulusSourceTypes(QualityAttribute qualityAttribute) {
		this.getFormSearch().getCmbTypeStimulusSource()
				.setInput(getManager().getComboModelStimulusSourceType(qualityAttribute));
	}

	public void setModelStimulusTypes(QualityAttribute qualityAttribute) {
		this.getFormSearch().getCmbTypeStimulus().setInput(getManager().getComboModelStimulusType(qualityAttribute));
	}

	public void setModelEnvironmentTypes(QualityAttribute qualityAttribute) {
		this.getFormSearch().getCmbTypeEnvironment().setInput(getManager().getComboModelEnvironmentType(qualityAttribute));
	}

	public void setModelArtifactTypes(QualityAttribute qualityAttribute) {
		this.getFormSearch().getCmbTypeArtifact().setInput(getManager().getComboModelArtifactType(qualityAttribute));
	}

	public void setModelResponseTypes(QualityAttribute qualityAttribute) {
		this.getFormSearch().getCmbTypeResponse().setInput(getManager().getComboModelResponseType(qualityAttribute));
	}

	public void setModelResponseMeasureTypes(QualityAttribute qualityAttribute) {
		this.getFormSearch().getCmbTypeResponseMeasure()
				.setInput(getManager().getComboModelResponseMeasureType(qualityAttribute));
	}

	public void setModelMetric(ResponseMeasureType type) {
		this.getFormSearch().getCmbMetric().setInput(getManager().getComboModelMetric(type));
	}

	public void setModelUnit(Metric type) {
		this.getFormSearch().getCmbUnit().setInput(getManager().getComboModelUnit(type));
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
			this.getManager().setDescriptionScenario(this.getFormSearch().getTxtDescription().getText());
			this.getManager().setConditionScenario((Condition)((IStructuredSelection) this.getFormSearch().getCmbCondition()
					.getSelection()).getFirstElement());
			this.getManager().setStimulusSource(
					this.getFormSearch().getTxtDescriptionStimulusSource().getStringValue(),
					this.getFormSearch().getTxtValueStimulusSource().getStringValue(),
					(StimulusSourceType) ((IStructuredSelection) this.getFormSearch().getCmbTypeStimulusSource()
							.getSelection()).getFirstElement());
			this.getManager().setStimulus(this.getFormSearch().getTxtDescriptionStimulus().getStringValue(),
					this.getFormSearch().getTxtValueStimulus().getStringValue(),
					(StimulusType) ((IStructuredSelection) this.getFormSearch().getCmbTypeStimulus().getSelection())
							.getFirstElement());
			this.getManager().setEnvironment(this.getFormSearch().getTxtDescriptionEnvironment().getStringValue(),
					this.getFormSearch().getTxtValueEnvironment().getStringValue(),
					(EnvironmentType) ((IStructuredSelection) this.getFormSearch().getCmbTypeEnvironment().getSelection())
							.getFirstElement());
			this.getManager().setArtifact(this.getFormSearch().getTxtDescriptionArtifact().getStringValue(),
					(ArtifactType) ((IStructuredSelection) this.getFormSearch().getCmbTypeArtifact().getSelection())
							.getFirstElement());
			this.getManager().setResponse(this.getFormSearch().getTxtDescriptionResponse().getStringValue(),
					this.getFormSearch().getTxtValueResponse().getStringValue(),
					(ResponseType) ((IStructuredSelection) this.getFormSearch().getCmbTypeResponse().getSelection())
							.getFirstElement());
			this.getManager().setResponseMeasure(
					this.getFormSearch().getTxtDescriptionResponseMeasure().getStringValue(),
					this.getFormSearch().getTxtValueResponseMeasure().getDoubleValue(),
					(ResponseMeasureType) ((IStructuredSelection) this.getFormSearch().getCmbTypeResponseMeasure()
							.getSelection()).getFirstElement(),
					(Metric) ((IStructuredSelection) this.getFormSearch().getCmbMetric().getSelection()).getFirstElement(),
					(Unit) ((IStructuredSelection) this.getFormSearch().getCmbUnit().getSelection()).getFirstElement());
			return 0;
		} else {
			return 1;
		}
	}

	public boolean isValidData() {
		if (this.isEmpty(this.getFormSearch().getCmbSystem())) {
			this.createErrorDialog("Select system");
			this.getFormSearch().getCmbSystem().getCombo().setFocus();
			return false;
		}
		if (this.isEmpty(this.getFormSearch().getTxtDescription())) {
			this.createErrorDialog("Empty description");
			this.getFormSearch().getTxtDescription().setFocus();
			return false;
		}
		if (this.isEmpty(this.getFormSearch().getCmbQualityAttribute())) {
			this.createErrorDialog("Select quality attribute");
			this.getFormSearch().getCmbQualityAttribute().getCombo().setFocus();
			return false;
		}
		if (this.isEmpty(this.getFormSearch().getCmbCondition())) {
			this.createErrorDialog("Select condition");
			this.getFormSearch().getCmbCondition().getCombo().setFocus();
			return false;
		} else if (this.isEmpty(this.getFormSearch().getTxtDescriptionStimulusSource())) {
			this.createErrorDialog("Empty stimulus source description");
			return false;
		} else if (this.isEmpty(this.getFormSearch().getTxtDescriptionStimulus())) {
			this.createErrorDialog("Empty stimulus description");
			return false;
		} else if (this.isEmpty(this.getFormSearch().getTxtDescriptionEnvironment())) {
			this.createErrorDialog("Empty environment description");
			return false;
		} else if (this.isEmpty(this.getFormSearch().getTxtDescriptionArtifact())) {
			this.createErrorDialog("Empty artifact description");
			return false;
		} else if (this.isEmpty(this.getFormSearch().getTxtDescriptionResponse())) {
			this.createErrorDialog("Empty response description");
			return false;
		} else if (this.isEmpty(this.getFormSearch().getTxtDescriptionResponseMeasure())) {
			this.createErrorDialog("Empty response measure description");
			return false;
		} else if (this.isEmpty(this.getFormSearch().getTxtValueStimulusSource())) {
			this.createErrorDialog("Empty stimulus source value");
			return false;
		} else if (this.isEmpty(this.getFormSearch().getTxtValueStimulus())) {
			this.createErrorDialog("Empty stimulus value");
			return false;
		} else if (this.isEmpty(this.getFormSearch().getTxtValueEnvironment())) {
			this.createErrorDialog("Empty environment value");
			return false;
		} else if (this.isEmpty(this.getFormSearch().getTxtValueResponse())) {
			this.createErrorDialog("Empty response value");
			return false;
		} else if (this.isEmpty(this.getFormSearch().getCmbTypeStimulusSource())) {
			this.createErrorDialog("Select stimulus source type");
			this.getFormSearch().getCmbTypeStimulusSource().getCombo().setFocus();
			return false;
		} else if (this.isEmpty(this.getFormSearch().getCmbTypeStimulus())) {
			this.createErrorDialog("Select stimulus type");
			this.getFormSearch().getCmbTypeStimulus().getCombo().setFocus();
			return false;
		} else if (this.isEmpty(this.getFormSearch().getCmbTypeEnvironment())) {
			this.createErrorDialog("Select environment type");
			this.getFormSearch().getCmbTypeEnvironment().getCombo().setFocus();
			return false;
		} else if (this.isEmpty(this.getFormSearch().getCmbTypeArtifact())) {
			this.createErrorDialog("Select artifact type");
			this.getFormSearch().getCmbTypeArtifact().getCombo().setFocus();
			return false;
		} else if (this.isEmpty(this.getFormSearch().getCmbTypeResponse())) {
			this.createErrorDialog("Select response type");
			this.getFormSearch().getCmbTypeResponse().getCombo().setFocus();
			return false;
		} else if (this.isEmpty(this.getFormSearch().getCmbTypeResponseMeasure())) {
			this.createErrorDialog("Select response measure type");
			this.getFormSearch().getCmbTypeResponseMeasure().getCombo().setFocus();
			return false;
		} else if (this.isEmpty(this.getFormSearch().getCmbMetric())) {
			this.createErrorDialog("Select metric");
			this.getFormSearch().getCmbMetric().getCombo().setFocus();
			return false;
		} else if (this.isEmpty(this.getFormSearch().getTxtValueResponseMeasure())) {
			this.createErrorDialog("Empty response measure value");
			return false;
		} else if (!this.getFormSearch().getTxtValueResponseMeasure().isValid()) {
			this.createErrorDialog("Invalid response measure value");
			return false;
		} else if (this.getFormSearch().getTxtValueResponseMeasure().getDoubleValue() <= 0) {
			this.createErrorDialog("Invalid response measure value (negative number)");
			return false;
		} else if (this.isEmpty(this.getFormSearch().getCmbUnit())) {
			this.createErrorDialog("Select unit");
			this.getFormSearch().getCmbUnit().getCombo().setFocus();
			return false;
		}

		return true;
	}

	/**
	 * Sets the model table of the quality requirements of a specific system
	 * 
	 * @param ptype
	 *            (System)
	 */
	public void setModelQualityRequirement(software.DomainModel.AnalysisEntity.System ptype) { // NOPMD by Usuario-Pc on 10/06/16 21:46
		this.getManager().setSystem(ptype);
		while (this.getFormSearch().getTable().getItems().length > 0) {
			this.getFormSearch().getTable().remove(0);
		}
		for (QualityRequirement dp : this.getManager().getQualityRequirements()) {
			if (dp.isState()) {
				TableItem item = new TableItem(this.getFormSearch().getTable(), SWT.NONE);
				item.setData(dp);
				item.setText(new String[] { dp.toString(), dp.getQualityScenario().getQualityAttribute().toString(),
						dp.getQualityScenario().getCondition().toString(),
						dp.getQualityScenario().getDescription().toString() });
			}
		}
	}
	
	public void setModel(QualityRequirement pmodel) {
		this.getManager().setQualityRequirement(pmodel);
	}

	public void getView() {
		this.getFormSearch().loadCmbQualityAttribute();
		this.getFormSearch().loadCmbCondition();
		
		this.getFormSearch().loadGenericScenario(this.getManager().getQualityAttribute());
		this.getFormSearch().loadCmbMetric(this.getManager().getTypeResponseMeasure());
		this.getFormSearch().loadCmbUnit(this.getManager().getMetric());

		this.getFormSearch().getTxtDescription().setText(this.getManager().getDescriptionScenario());
		this.getFormSearch().getCmbQualityAttribute().setSelection(new StructuredSelection(this.getManager().getQualityAttribute()));
		this.getFormSearch().getCmbCondition().setSelection(new StructuredSelection(this.getManager().getConditionScenario()));

		this.getFormSearch().getTxtDescriptionStimulusSource().setStringValue(this.getManager().getDescriptionStimulusSource());
		this.getFormSearch().getCmbTypeStimulusSource().setSelection(new StructuredSelection(this.getManager().getTypeStimulusSource()));
		this.getFormSearch().getTxtValueStimulusSource().setStringValue(this.getManager().getValueStimulusSource());

		this.getFormSearch().getTxtDescriptionStimulus().setStringValue(this.getManager().getDescriptionStimulus());
		this.getFormSearch().getCmbTypeStimulus().setSelection(new StructuredSelection(this.getManager().getTypeStimulus()));
		this.getFormSearch().getTxtValueStimulus().setStringValue(this.getManager().getValueStimulus());

		this.getFormSearch().getTxtDescriptionEnvironment().setStringValue(this.getManager().getDescriptionEnvironment());
		this.getFormSearch().getCmbTypeEnvironment().setSelection(new StructuredSelection(this.getManager().getTypeEnvironment()));
		this.getFormSearch().getTxtValueEnvironment().setStringValue(this.getManager().getValueEnvironment());

		this.getFormSearch().getTxtDescriptionArtifact().setStringValue(this.getManager().getDescriptionArtifact());
		this.getFormSearch().getCmbTypeArtifact().setSelection(new StructuredSelection(this.getManager().getTypeArtifact()));

		this.getFormSearch().getTxtDescriptionResponse().setStringValue(this.getManager().getDescriptionResponse());
		this.getFormSearch().getCmbTypeResponse().setSelection(new StructuredSelection(this.getManager().getTypeResponse()));
		this.getFormSearch().getTxtValueResponse().setStringValue(this.getManager().getValueResponse());

		this.getFormSearch().getTxtDescriptionResponseMeasure().setStringValue(this.getManager().getDescriptionResponseMeasure());
		this.getFormSearch().getCmbTypeResponseMeasure().setSelection(new StructuredSelection(this.getManager().getTypeResponseMeasure()));
		this.getFormSearch().getCmbMetric().setSelection(new StructuredSelection(this.getManager().getMetric()));
		this.getFormSearch().getTxtValueResponseMeasure().setStringValue(this.getManager().getValueResponseMeasure());
		this.getFormSearch().getCmbUnit().setSelection(new StructuredSelection(this.getManager().getUnit()));
	}

}
