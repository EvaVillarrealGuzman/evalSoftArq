package project.preferences.controller;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

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
import software.Presentation.GUIAnalysis.FrmSystemManagement;

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
		int err = 0;
		err = this.newQualityRequirement();
		if (err == 0) {
			this.getManager().saveQualityRequirement();
		}
	}
	
	public int newQualityRequirement() {
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
		if (this.isEmpty(this.getForm()
				.getCmbSystem())) {
			JOptionPane.showOptionDialog(
					null,
					"Select system",
					"Warning",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.ERROR_MESSAGE,
					new ImageIcon(FrmSystemManagement.class
							.getResource("/Icons/error.png")),
					new Object[] { "OK" }, "OK");
			this.getForm().getCmbSystem().getCombo().setFocus();
			return false;
		}
		if (this.isEmpty(this.getForm()
				.getTxtDescription())) {
			JOptionPane.showOptionDialog(
					null,
					"Empty description",
					"Warning",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.ERROR_MESSAGE,
					new ImageIcon(FrmSystemManagement.class
							.getResource("/Icons/error.png")),
					new Object[] { "OK" }, "OK");
			this.getForm().getTxtDescription().setFocus();
			return false;
		}
		if (this.isEmpty(this.getForm()
				.getCmbQualityAttribute())) {
			JOptionPane.showOptionDialog(
					null,
					"Select Quality Attribute",
					"Warning",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.ERROR_MESSAGE,
					new ImageIcon(FrmSystemManagement.class
							.getResource("/Icons/error.png")),
					new Object[] { "OK" }, "OK");
			this.getForm().getCmbQualityAttribute().getCombo().setFocus();
			return false;
		}
		if (this.isEmpty(this.getForm()
				.getCmbCondition())) {
			JOptionPane.showOptionDialog(
					null,
					"Select condition",
					"Warning",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.ERROR_MESSAGE,
					new ImageIcon(FrmSystemManagement.class
							.getResource("/Icons/error.png")),
					new Object[] { "OK" }, "OK");
			this.getForm().getCmbCondition().getCombo().setFocus();
			return false;
		} else if (this.isEmpty(this.getForm()
				.getTxtDescriptionStimulusSource())) {
			JOptionPane.showOptionDialog(
					null,
					"Empty stimulus source description",
					"Warning",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.ERROR_MESSAGE,
					new ImageIcon(FrmSystemManagement.class
							.getResource("/Icons/error.png")),
					new Object[] { "OK" }, "OK");
			this.getForm()
					.getTxtDescriptionStimulusSource().getTextControl(this.getForm().getcParts()).setFocus();
			return false;
		} else if (this.isEmpty(this.getForm()
				.getTxtDescriptionStimulus())) {
			JOptionPane.showOptionDialog(
					null,
					"Empty stimulus description",
					"Warning",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.ERROR_MESSAGE,
					new ImageIcon(FrmSystemManagement.class
							.getResource("/Icons/error.png")),
					new Object[] { "OK" }, "OK");
			this.getForm()
					.getTxtDescriptionStimulus().getTextControl(this.getForm().getcParts()).setFocus();
			return false;
		} else if (this.isEmpty(this.getForm()
				.getTxtDescriptionEnvironment())) {
			JOptionPane.showOptionDialog(
					null,
					"mpty environment description",
					"Warning",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.ERROR_MESSAGE,
					new ImageIcon(FrmSystemManagement.class
							.getResource("/Icons/error.png")),
					new Object[] { "OK" }, "OK");
			this.getForm()
					.getTxtDescriptionEnvironment().getTextControl(this.getForm().getcParts()).setFocus();
			return false;
		} else if (this.isEmpty(this.getForm()
				.getTxtDescriptionArtifact())) {
			JOptionPane.showOptionDialog(
					null,
					"Empty artifact description",
					"Warning",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.ERROR_MESSAGE,
					new ImageIcon(FrmSystemManagement.class
							.getResource("/Icons/error.png")),
					new Object[] { "OK" }, "OK");
			this.getForm()
					.getTxtDescriptionArtifact().getTextControl(this.getForm().getcParts()).setFocus();
			return false;
		} else if (this.isEmpty(this.getForm()
				.getTxtDescriptionResponse())) {
			JOptionPane.showOptionDialog(
					null,
					"Empty response description",
					"Warning",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.ERROR_MESSAGE,
					new ImageIcon(FrmSystemManagement.class
							.getResource("/Icons/error.png")),
					new Object[] { "OK" }, "OK");
			this.getForm()
					.getTxtDescriptionResponse().getTextControl(this.getForm().getcParts()).setFocus();
			return false;
		} else if (this.isEmpty(this.getForm()
				.getTxtDescriptionResponseMeasure())) {
			JOptionPane.showOptionDialog(
					null,
					"Empty response measure description",
					"Warning",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.ERROR_MESSAGE,
					new ImageIcon(FrmSystemManagement.class
							.getResource("/Icons/error.png")),
					new Object[] { "OK" }, "OK");
			this.getForm()
					.getTxtDescriptionResponseMeasure().getTextControl(this.getForm().getcParts()).setFocus();
			return false;
		} else if (this.isEmpty(this.getForm()
				.getTxtValueStimulusSource())) {
			JOptionPane.showOptionDialog(
					null,
					"Empty stimulus source value",
					"Warning",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.ERROR_MESSAGE,
					new ImageIcon(FrmSystemManagement.class
							.getResource("/Icons/error.png")),
					new Object[] { "OK" }, "OK");
			this.getForm()
					.getTxtValueStimulusSource().getTextControl(this.getForm().getcParts()).setFocus();
			return false;
		} else if (this.isEmpty(this.getForm()
				.getTxtValueStimulus())) {
			JOptionPane.showOptionDialog(
					null,
					"Empty stimulus value",
					"Warning",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.ERROR_MESSAGE,
					new ImageIcon(FrmSystemManagement.class
							.getResource("/Icons/error.png")),
					new Object[] { "OK" }, "OK");
			this.getForm().getTxtValueStimulus().getTextControl(this.getForm().getcParts()).setFocus();
			return false;
		} else if (this.isEmpty(this.getForm()
				.getTxtValueEnvironment())) {
			JOptionPane.showOptionDialog(
					null,
					"Empty environment value",
					"Warning",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.ERROR_MESSAGE,
					new ImageIcon(FrmSystemManagement.class
							.getResource("/Icons/error.png")),
					new Object[] { "OK" }, "OK");
			this.getForm().getTxtValueEnvironment().getTextControl(this.getForm().getcParts()).setFocus();
			return false;
		} else if (this.isEmpty(this.getForm()
				.getTxtValueResponse())) {
			JOptionPane.showOptionDialog(
					null,
					"Empty response value",
					"Warning",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.ERROR_MESSAGE,
					new ImageIcon(FrmSystemManagement.class
							.getResource("/Icons/error.png")),
					new Object[] { "OK" }, "OK");
			this.getForm().getTxtValueResponse().getTextControl(this.getForm().getcParts()).setFocus();
			return false;
		} else if (this.isEmpty(this.getForm()
				.getCmbTypeStimulusSource())) {
			JOptionPane.showOptionDialog(
					null,
					"Select stimulus source type",
					"Warning",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.ERROR_MESSAGE,
					new ImageIcon(FrmSystemManagement.class
							.getResource("/Icons/error.png")),
					new Object[] { "OK" }, "OK");
			this.getForm()
					.getCmbTypeStimulusSource().getCombo().setFocus();
			return false;
		} else if (this.isEmpty(this.getForm()
				.getCmbTypeStimulus())) {
			JOptionPane.showOptionDialog(
					null,
					"Select stimulus type",
					"Warning",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.ERROR_MESSAGE,
					new ImageIcon(FrmSystemManagement.class
							.getResource("/Icons/error.png")),
					new Object[] { "OK" }, "OK");
			this.getForm().getCmbTypeStimulus().getCombo().setFocus();
			return false;
		} else if (this.isEmpty(this.getForm()
				.getCmbTypeEnvironment())) {
			JOptionPane.showOptionDialog(
					null,
					"Select environment type",
					"Warning",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.ERROR_MESSAGE,
					new ImageIcon(FrmSystemManagement.class
							.getResource("/Icons/error.png")),
					new Object[] { "OK" }, "OK");
			this.getForm().getCmbTypeEnvironment().getCombo().setFocus();
			return false;
		} else if (this.isEmpty(this.getForm()
				.getCmbTypeArtifact())) {
			JOptionPane.showOptionDialog(
					null,
					"Select artifact type",
					"Warning",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.ERROR_MESSAGE,
					new ImageIcon(FrmSystemManagement.class
							.getResource("/Icons/error.png")),
					new Object[] { "OK" }, "OK");
			this.getForm().getCmbTypeArtifact().getCombo().setFocus();
			return false;
		} else if (this.isEmpty(this.getForm()
				.getCmbTypeResponse())) {
			JOptionPane.showOptionDialog(
					null,
					"Select response type",
					"Warning",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.ERROR_MESSAGE,
					new ImageIcon(FrmSystemManagement.class
							.getResource("/Icons/error.png")),
					new Object[] { "OK" }, "OK");
			this.getForm().getCmbTypeResponse().getCombo().setFocus();
			return false;
		} else if (this.isEmpty(this.getForm()
				.getCmbTypeResponseMeasure())) {
			JOptionPane.showOptionDialog(
					null,
					"Select response measure type",
					"Warning",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.ERROR_MESSAGE,
					new ImageIcon(FrmSystemManagement.class
							.getResource("/Icons/error.png")),
					new Object[] { "OK" }, "OK");
			this.getForm()
					.getCmbTypeResponseMeasure().getCombo().setFocus();
			return false;
		} else if (this.isEmpty(this.getForm()
				.getCmbMetric())) {
			JOptionPane.showOptionDialog(
					null,
					"Select metric",
					"Warning",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.ERROR_MESSAGE,
					new ImageIcon(FrmSystemManagement.class
							.getResource("/Icons/error.png")),
					new Object[] { "OK" }, "OK");
			this.getForm().getCmbMetric().getCombo().setFocus();
			return false;
		} else if (this.isEmpty(this.getForm()
				.getTxtValueResponseMeasure())) {
			JOptionPane.showOptionDialog(
					null,
					"Empty response measure value",
					"Warning",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.ERROR_MESSAGE,
					new ImageIcon(FrmSystemManagement.class
							.getResource("/Icons/error.png")),
					new Object[] { "OK" }, "OK");
			this.getForm()
					.getTxtValueResponseMeasure().getTextControl(this.getForm().getcParts()).setFocus();
			return false;
		} else if (Double.parseDouble(this.getForm()
				.getTxtValueResponseMeasure().getStringValue()) <= 0) {
			JOptionPane.showOptionDialog(
					null,
					"Invalid response measure value",
					"Warning",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.ERROR_MESSAGE,
					new ImageIcon(FrmSystemManagement.class
							.getResource("/Icons/error.png")),
					new Object[] { "OK" }, "OK");
			this.getForm()
					.getTxtValueResponseMeasure().getTextControl(this.getForm().getcParts()).setFocus();
			return false;
		} else if (this.isEmpty(this.getForm()
				.getCmbUnit())) {
			JOptionPane.showOptionDialog(
					null,
					"Select unit",
					"Warning",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.ERROR_MESSAGE,
					new ImageIcon(FrmSystemManagement.class
							.getResource("/Icons/error.png")),
					new Object[] { "OK" }, "OK");
			this.getForm().getCmbUnit().getCombo().setFocus();
			return false;
		}

		return true;
	}


}
