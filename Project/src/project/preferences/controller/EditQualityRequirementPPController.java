package project.preferences.controller;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TableItem;

import project.preferences.EditQualityRequirementPreferencePage;
import project.preferences.PreferenceConstants;
import software.BusinessLogic.AnalysisManager;
import software.DomainModel.AnalysisEntity.ArtifactType;
import software.DomainModel.AnalysisEntity.EnvironmentType;
import software.DomainModel.AnalysisEntity.Metric;
import software.DomainModel.AnalysisEntity.QualityAttribute;
import software.DomainModel.AnalysisEntity.QualityRequirement;
import software.DomainModel.AnalysisEntity.ResponseMeasureType;
import software.DomainModel.AnalysisEntity.ResponseType;
import software.DomainModel.AnalysisEntity.StimulusSourceType;
import software.DomainModel.AnalysisEntity.StimulusType;
import software.DomainModel.AnalysisEntity.Unit;

/**
 * Controller for EditQualityRequirementPreferencePage
 * 
 * @author FEM
 *
 */
public class EditQualityRequirementPPController extends Controller {
	/**
	 * Attributes
	 */
	private static EditQualityRequirementPPController controller;
	private AnalysisManager manager;
	private EditQualityRequirementPreferencePage formSearch;

	/**
	 * Getters and Setters
	 */
	public static EditQualityRequirementPPController getController() {
		return controller;
	}

	public static void setController(EditQualityRequirementPPController controller) {
		EditQualityRequirementPPController.controller = controller;
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

	public EditQualityRequirementPreferencePage getFormSearch() {
		return formSearch;
	}

	public void setFormSearch(EditQualityRequirementPreferencePage formSearch) {
		this.formSearch = formSearch;
	}

	/**
	 * Sets the model of system combo
	 */
	public void setModelSystemSearch() {
		this.getFormSearch().getCmbSystem().setInput(getManager().getComboModelSystemWithRequirements());
	}

	/**
	 * Sets the model of quality attribute combo
	 */
	public void setModelQualityAttribute() {
		this.getFormSearch().getCmbQualityAttribute().setInput(getManager().getComboModelQualityAttribute());
	}

	/**
	 * Sets the model of stimulus source type combo for a specific quality
	 * attribute
	 * 
	 * @param qualityAttribute
	 */
	public void setModelStimulusSourceTypes(QualityAttribute qualityAttribute) {
		this.getFormSearch().getCmbTypeStimulusSource()
				.setInput(getManager().getComboModelStimulusSourceType(qualityAttribute));
	}

	/**
	 * Sets the model of stimulus type combo for a specific quality attribute
	 * 
	 * @param qualityAttribute
	 */
	public void setModelStimulusTypes(QualityAttribute qualityAttribute) {
		this.getFormSearch().getCmbTypeStimulus().setInput(getManager().getComboModelStimulusType(qualityAttribute));
	}

	/**
	 * Sets the model of environment type combo for a specific quality attribute
	 * 
	 * @param qualityAttribute
	 */
	public void setModelEnvironmentTypes(QualityAttribute qualityAttribute) {
		this.getFormSearch().getCmbTypeEnvironment()
				.setInput(getManager().getComboModelEnvironmentType(qualityAttribute));
	}

	/**
	 * Sets the model of artifact type combo for a specific quality attribute
	 * 
	 * @param qualityAttribute
	 */
	public void setModelArtifactTypes(QualityAttribute qualityAttribute) {
		this.getFormSearch().getCmbTypeArtifact().setInput(getManager().getComboModelArtifactType(qualityAttribute));
	}

	/**
	 * Sets the model of response type combo for a specific quality attribute
	 * 
	 * @param qualityAttribute
	 */
	public void setModelResponseTypes(QualityAttribute qualityAttribute) {
		this.getFormSearch().getCmbTypeResponse().setInput(getManager().getComboModelResponseType(qualityAttribute));
	}

	/**
	 * Sets the model of response measure type combo for a specific quality
	 * attribute
	 * 
	 * @param qualityAttribute
	 */
	public void setModelResponseMeasureTypes(QualityAttribute qualityAttribute) {
		this.getFormSearch().getCmbTypeResponseMeasure()
				.setInput(getManager().getComboModelResponseMeasureType(qualityAttribute));
	}

	/**
	 * Sets the model of metric combo for a specific quality attribute
	 * 
	 * @param qualityAttribute
	 */
	public void setModelMetric(ResponseMeasureType type) {
		this.getFormSearch().getCmbMetric().setInput(getManager().getComboModelMetric(type));
	}

	/**
	 * Sets the model of unit combo for a specific quality attribute
	 * 
	 * @param qualityAttribute
	 */
	public void setModelUnit(Metric type) {
		this.getFormSearch().getCmbUnit().setInput(getManager().getComboModelUnit(type));
	}

	/**
	 * Update the quality requirement and prepare the view
	 */
	public Boolean save() {
		int err;
		err = this.setQualityRequirement();
		if (err == 0) {
			this.getFormSearch().prepareView(7);
			this.getFormSearch().fillTable();
			return this.getManager().updateQualityRequirement();
		}
		return null;
	}

	/**
	 * Remove a quality requirement and prepare the view
	 */
	public void remove() {
		this.getManager().removeQualityRequirement();
		this.getFormSearch().prepareView(7);
		this.getFormSearch().fillTable();
	}

	/**
	 * Update a quality requirement
	 * 
	 * @return int (indicates if the quality requirement was updated
	 *         successfully)
	 */
	public int setQualityRequirement() {
		if (this.isValidData()) {
			this.getManager().setDescriptionScenario(this.getFormSearch().getTxtDescription().getText());
			this.getManager().setStimulusSource(this.getFormSearch().getTxtDescriptionStimulusSource().getText(),
					this.getFormSearch().getTxtValueStimulusSource().getStringValue(),
					(StimulusSourceType) ((IStructuredSelection) this.getFormSearch().getCmbTypeStimulusSource()
							.getSelection()).getFirstElement());
			this.getManager().setStimulus(this.getFormSearch().getTxtDescriptionStimulus().getText(),
					this.getFormSearch().getTxtValueStimulus().getStringValue(),
					(StimulusType) ((IStructuredSelection) this.getFormSearch().getCmbTypeStimulus().getSelection())
							.getFirstElement());
			this.getManager().setEnvironment(this.getFormSearch().getTxtDescriptionEnvironment().getText(),
					this.getFormSearch().getTxtValueEnvironment().getStringValue(),
					(EnvironmentType) ((IStructuredSelection) this.getFormSearch().getCmbTypeEnvironment()
							.getSelection()).getFirstElement());
			this.getManager().setArtifact(this.getFormSearch().getTxtDescriptionArtifact().getText(),
					(ArtifactType) ((IStructuredSelection) this.getFormSearch().getCmbTypeArtifact().getSelection())
							.getFirstElement());
			this.getManager().setResponse(this.getFormSearch().getTxtDescriptionResponse().getText(),
					this.getFormSearch().getTxtValueResponse().getStringValue(),
					(ResponseType) ((IStructuredSelection) this.getFormSearch().getCmbTypeResponse().getSelection())
							.getFirstElement());
			this.getManager().setResponseMeasure(this.getFormSearch().getTxtDescriptionResponseMeasure().getText(),
					this.getFormSearch().getTxtValueResponseMeasure().getDoubleValue(),
					(ResponseMeasureType) ((IStructuredSelection) this.getFormSearch().getCmbTypeResponseMeasure()
							.getSelection()).getFirstElement(),
					(Metric) ((IStructuredSelection) this.getFormSearch().getCmbMetric().getSelection())
							.getFirstElement(),
					(Unit) ((IStructuredSelection) this.getFormSearch().getCmbUnit().getSelection()).getFirstElement());
			return 0;
		} else {
			return 1;
		}
	}

	/**
	 * Validate the necessary data for the update of the quality requirement
	 * 
	 * @return boolean (is true if they have completed the required fields)
	 */
	public boolean isValidData() {
		if (this.isEmpty(this.getFormSearch().getCmbSystem())) {
			this.createErrorDialog(PreferenceConstants.SelectSystem_ErrorDialog);
			this.getFormSearch().getCmbSystem().getCombo().setFocus();
			return false;
		}
		if (this.isEmpty(this.getFormSearch().getTxtDescription())) {
			this.createErrorDialog(PreferenceConstants.EmptyDescription_ErrorDialog);
			this.getFormSearch().getTxtDescription().setFocus();
			return false;
		}
		if (this.isEmpty(this.getFormSearch().getCmbQualityAttribute())) {
			this.createErrorDialog(PreferenceConstants.SelectQualityAttribute_ErrorDialog);
			this.getFormSearch().getCmbQualityAttribute().getCombo().setFocus();
			return false;
		} else if (this.isEmpty(this.getFormSearch().getTxtValueStimulusSource())) {
			this.createErrorDialog(PreferenceConstants.EmptyStimulusSourceValue_ErrorDialog);
			return false;
		} else if (this.isEmpty(this.getFormSearch().getTxtValueStimulus())) {
			this.createErrorDialog(PreferenceConstants.EmptyStimulusValue_ErrorDialog);
			return false;
		} else if (this.isEmpty(this.getFormSearch().getTxtValueEnvironment())) {
			this.createErrorDialog(PreferenceConstants.EmptyEnvironmentValue_ErrorDialog);
			return false;
		} else if (this.isEmpty(this.getFormSearch().getTxtValueResponse())) {
			this.createErrorDialog(PreferenceConstants.EmptyResponseValue_ErrorDialog);
			return false;
		} else if (this.isEmpty(this.getFormSearch().getCmbTypeStimulusSource())) {
			this.createErrorDialog(PreferenceConstants.SelectStimulusSourceType_ErrorDialog);
			this.getFormSearch().getCmbTypeStimulusSource().getCombo().setFocus();
			return false;
		} else if (this.isEmpty(this.getFormSearch().getCmbTypeStimulus())) {
			this.createErrorDialog(PreferenceConstants.SelectStimulusType_ErrorDialog);
			this.getFormSearch().getCmbTypeStimulus().getCombo().setFocus();
			return false;
		} else if (this.isEmpty(this.getFormSearch().getCmbTypeEnvironment())) {
			this.createErrorDialog(PreferenceConstants.SelectEnvironmentType_ErrorDialog);
			this.getFormSearch().getCmbTypeEnvironment().getCombo().setFocus();
			return false;
		} else if (this.isEmpty(this.getFormSearch().getCmbTypeArtifact())) {
			this.createErrorDialog(PreferenceConstants.SelectArtifactType_ErrorDialog);
			this.getFormSearch().getCmbTypeArtifact().getCombo().setFocus();
			return false;
		} else if (this.isEmpty(this.getFormSearch().getCmbTypeResponse())) {
			this.createErrorDialog(PreferenceConstants.SelectResponseType_ErrorDialog);
			this.getFormSearch().getCmbTypeResponse().getCombo().setFocus();
			return false;
		} else if (this.isEmpty(this.getFormSearch().getCmbTypeResponseMeasure())) {
			this.createErrorDialog(PreferenceConstants.SelectResponseMeasureType_ErrorDialog);
			this.getFormSearch().getCmbTypeResponseMeasure().getCombo().setFocus();
			return false;
		} else if (this.isEmpty(this.getFormSearch().getCmbMetric())) {
			this.createErrorDialog(PreferenceConstants.SelectMetric_ErrorDialog);
			this.getFormSearch().getCmbMetric().getCombo().setFocus();
			return false;
		} else if (this.isEmpty(this.getFormSearch().getTxtValueResponseMeasure())) {
			this.createErrorDialog(PreferenceConstants.EmptyResponseMeasureValue_ErrorDialog);
			return false;
		} else if (!this.getFormSearch().getTxtValueResponseMeasure().isValid()) {
			this.createErrorDialog(PreferenceConstants.InvalidResponseMeasureValue_ErrorDialog);
			return false;
		} else if (this.getFormSearch().getTxtValueResponseMeasure().getDoubleValue() <= 0) {
			this.createErrorDialog(PreferenceConstants.InvalidResponseMeasureValueNegative_ErrorDialog);
			return false;
		} else if (this.isEmpty(this.getFormSearch().getCmbUnit())) {
			this.createErrorDialog(PreferenceConstants.SelectUnit_ErrorDialog);
			this.getFormSearch().getCmbUnit().getCombo().setFocus();
			return false;
		}

		return true;
	}

	/**
	 * Sets the model table of the quality requirements of a specific system
	 * 
	 * @param ptype
	 */
	public void setModelQualityRequirement(software.DomainModel.AnalysisEntity.System ptype) { // NOPMD
																								// by
																								// Usuario-Pc
																								// on
																								// 10/06/16
																								// 21:46
		this.getManager().setSystem(ptype);
		while (this.getFormSearch().getTable().getItems().length > 0) {
			this.getFormSearch().getTable().remove(0);
		}
		for (QualityRequirement dp : this.getManager().getQualityRequirements()) {
			if (dp.isState()) {
				TableItem item = new TableItem(this.getFormSearch().getTable(), SWT.NONE);
				item.setData(dp);
				item.setText(new String[] { dp.toString(), dp.getQualityScenario().getQualityAttribute().toString(),
						dp.getQualityScenario().getDescription().toString() });
			}
		}
	}

	/**
	 * Sets the manager's model (its quality requirement)
	 * 
	 * @param pmodel
	 */
	public void setModel(QualityRequirement pmodel) {
		this.getManager().setQualityRequirement(pmodel);
	}

	/**
	 * Configure the view when a table's item is selected
	 */
	public void getView() {
		this.getFormSearch().loadCmbQualityAttribute();

		this.getFormSearch().loadGenericScenario(this.getManager().getQualityAttribute());
		this.getFormSearch().loadCmbMetric(this.getManager().getTypeResponseMeasure());
		this.getFormSearch().loadCmbUnit(this.getManager().getMetric());

		this.getFormSearch().getTxtDescription().setText(this.getManager().getDescriptionScenario());
		this.getFormSearch().getCmbQualityAttribute()
				.setSelection(new StructuredSelection(this.getManager().getQualityAttribute()));

		this.getFormSearch().getTxtDescriptionStimulusSource()
				.setText(this.getManager().getDescriptionStimulusSource());
		this.getFormSearch().getCmbTypeStimulusSource()
				.setSelection(new StructuredSelection(this.getManager().getTypeStimulusSource()));
		this.getFormSearch().getTxtValueStimulusSource().setStringValue(this.getManager().getValueStimulusSource());

		this.getFormSearch().getTxtDescriptionStimulus().setText(this.getManager().getDescriptionStimulus());
		this.getFormSearch().getCmbTypeStimulus()
				.setSelection(new StructuredSelection(this.getManager().getTypeStimulus()));
		this.getFormSearch().getTxtValueStimulus().setStringValue(this.getManager().getValueStimulus());

		this.getFormSearch().getTxtDescriptionEnvironment().setText(this.getManager().getDescriptionEnvironment());
		this.getFormSearch().getCmbTypeEnvironment()
				.setSelection(new StructuredSelection(this.getManager().getTypeEnvironment()));
		this.getFormSearch().getTxtValueEnvironment().setStringValue(this.getManager().getValueEnvironment());

		this.getFormSearch().getTxtDescriptionArtifact().setText(this.getManager().getDescriptionArtifact());
		this.getFormSearch().getCmbTypeArtifact()
				.setSelection(new StructuredSelection(this.getManager().getTypeArtifact()));

		this.getFormSearch().getTxtDescriptionResponse().setText(this.getManager().getDescriptionResponse());
		this.getFormSearch().getCmbTypeResponse()
				.setSelection(new StructuredSelection(this.getManager().getTypeResponse()));
		this.getFormSearch().getTxtValueResponse().setStringValue(this.getManager().getValueResponse());

		this.getFormSearch().getTxtDescriptionResponseMeasure()
				.setText(this.getManager().getDescriptionResponseMeasure());
		this.getFormSearch().getCmbTypeResponseMeasure()
				.setSelection(new StructuredSelection(this.getManager().getTypeResponseMeasure()));
		this.getFormSearch().getCmbMetric().setSelection(new StructuredSelection(this.getManager().getMetric()));
		this.getFormSearch().getTxtValueResponseMeasure().setStringValue(this.getManager().getValueResponseMeasure());
		this.getFormSearch().getCmbUnit().setSelection(new StructuredSelection(this.getManager().getUnit()));
	}

}
