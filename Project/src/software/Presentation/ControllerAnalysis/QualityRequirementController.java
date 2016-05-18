package software.Presentation.ControllerAnalysis;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

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
import software.Presentation.GUIAnalysis.FrmQualityRequirementManagement;
import software.Presentation.GUIAnalysis.FrmQualityRequirementSearch;
import software.Presentation.GUIAnalysis.FrmSystemManagement;

/**
 * This class is responsible for the management of forms:
 * QualityRequirementManagement and QualityRequirementSearch
 * 
 * @author: FEM
 * @version: 06/11/2015
 */

public class QualityRequirementController {

	/**
	 * Attributes
	 */
	private static QualityRequirementController controller;
	AnalysisManager manager;
	FrmQualityRequirementManagement frmQualityRequirementManagement;
	FrmQualityRequirementSearch frmQualityRequirementSearch;
	public int opcABM;

	/**
	 * Builder
	 */
	private QualityRequirementController() {
		super();
	}

	/**
	 * Get QualityRequirementController. Applied Singleton pattern
	 */
	public static QualityRequirementController getController() {
		if (controller == null) {
			synchronized (QualityRequirementController.class) {
				controller = new QualityRequirementController();
			}
		}
		return controller;
	}

	/**
	 * Set QualityRequirementController
	 */
	public static void setController(QualityRequirementController controller) {
		QualityRequirementController.controller = controller;
	}

	/**
	 * Getters and Setters
	 */
	public AnalysisManager getManager() {
		manager = AnalysisManager.getManager();
		return manager;
	}

	public void setManager(AnalysisManager pmanager) {
		this.manager = pmanager;
	}

	public FrmQualityRequirementManagement getFrmQualityRequirementManagement() {
		frmQualityRequirementManagement = FrmQualityRequirementManagement
				.getFrmQualityRequirementManagement(this);
		return frmQualityRequirementManagement;
	}

	public void setFrmQualityRequirementManagement(
			FrmQualityRequirementManagement pfrmQualityRequirementManagement) {
		this.frmQualityRequirementManagement = pfrmQualityRequirementManagement;
	}

	public FrmQualityRequirementSearch getFrmQualityRequirementSearch() {
		frmQualityRequirementSearch = FrmQualityRequirementSearch
				.getFrmQualityRequirementSearch(this);
		return frmQualityRequirementSearch;
	}

	public void setFrmQualityRequirementSearch(
			FrmQualityRequirementSearch pfrmQualityRequirementSearch) {
		this.frmQualityRequirementSearch = pfrmQualityRequirementSearch;
	}

	public int getOpcABM() {
		return opcABM;
	}

	public void setOpcABM(int popcABM) {
		this.opcABM = popcABM;
	}

	/**
	 * Open the form FrmQualityRequirementManagement. Before validating that
	 * systems with state=true
	 * 
	 * @param pabm
	 */
	public void openFrmQualityRequirementManagement(int pabm) {
		if (this.getManager().existSystemTrue()) {
			this.setOpcABM(pabm);
			this.getFrmQualityRequirementManagement().setVisible(true);
		} else {
			JOptionPane.showOptionDialog(
					null,
					"No saved systems",
					"Warning",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.ERROR_MESSAGE,
					new ImageIcon(FrmSystemManagement.class
							.getResource("/Icons/error.png")),
					new Object[] { "OK" }, "OK");

		}
	}

	/**
	 * Open the form FrmQualityRequirementSearch
	 * 
	 * @param pabm
	 */
	public void openFrmQualityRequirementSearch(int pabm) {
		this.setOpcABM(pabm);
		this.getFrmQualityRequirementSearch().prepareView(this.getOpcABM());
		this.getFrmQualityRequirementSearch().setVisible(true);
	}

	/**
	 * Sets the model of quality attribute combo
	 */
	public void setModelQualityAttribute() {
		this.getFrmQualityRequirementManagement().getCmbQualityAttribute()
				.setModel(getManager().getComboModelQualityAttribute());
	}

	/**
	 * Sets the model of system combo
	 */
	public void setModelSystem() {
		this.getFrmQualityRequirementManagement().getCmbSystem()
				.setModel(getManager().getComboModelSystem());
	}

	/**
	 * Sets the model of system combo with systems that have quality
	 * requirements state=true
	 */
	public void setModelSystemSearch() {
		this.getFrmQualityRequirementSearch().getCmbSystem()
				.setModel(getManager().getComboModelSystemWithRequirements());
	}

	/**
	 * Sets the model of condition combo
	 */
	public void setModelCondition() {
		this.getFrmQualityRequirementManagement().getCmbCondition()
				.setModel(getManager().getComboModelCondition());
	}

	/**
	 * Sets the model of types combo
	 */
	public void setModelStimulusSourceTypes(QualityAttribute qualityAttribute) {
		this.getFrmQualityRequirementManagement()
				.getCmbTypeStimulusSource()
				.setModel(
						getManager().getComboModelStimulusSourceType(
								qualityAttribute));
	}

	public void setModelStimulusTypes(QualityAttribute qualityAttribute) {
		this.getFrmQualityRequirementManagement()
				.getCmbTypeStimulus()
				.setModel(
						getManager()
								.getComboModelStimulusType(qualityAttribute));
	}

	public void setModelEnvironmentTypes(QualityAttribute qualityAttribute) {
		this.getFrmQualityRequirementManagement()
				.getCmbTypeEnvironment()
				.setModel(
						getManager().getComboModelEnvironmentType(
								qualityAttribute));
	}

	public void setModelArtifactTypes(QualityAttribute qualityAttribute) {
		this.getFrmQualityRequirementManagement()
				.getCmbTypeArtifact()
				.setModel(
						getManager()
								.getComboModelArtifactType(qualityAttribute));
	}

	public void setModelResponseTypes(QualityAttribute qualityAttribute) {
		this.getFrmQualityRequirementManagement()
				.getCmbTypeResponse()
				.setModel(
						getManager()
								.getComboModelResponseType(qualityAttribute));
	}

	public void setModelResponseMeasureTypes(QualityAttribute qualityAttribute) {
		this.getFrmQualityRequirementManagement()
				.getCmbTypeResponseMeasure()
				.setModel(
						getManager().getComboModelResponseMeasureType(
								qualityAttribute));
	}

	public void setModelMetric(ResponseMeasureType type) {
		this.getFrmQualityRequirementManagement().getCmbMetric()
				.setModel(getManager().getComboModelMetric(type));
	}

	public void setModelUnit(Metric type) {
		this.getFrmQualityRequirementManagement().getCmbUnit()
				.setModel(getManager().getComboModelUnit(type));
	}

	public int saveView() {
		int err = 0;
		if (this.getOpcABM() == 0) {
			err = this.newQualityRequirement();
		} else {
			err = this.setQualityRequirement();
		}
		if (err == 0) {
			this.getManager().saveQualityRequirement(this.getOpcABM());
			this.getFrmQualityRequirementManagement().clearView();
			this.setOpcABM(3);
			this.getFrmQualityRequirementManagement().prepareView(
					this.getOpcABM());
		}
		return err;
	}

	/**
	 * Create a new quality requirement and its create the specific scenario
	 */
	public int newQualityRequirement() {
		if (this.isValidData()) {
			StimulusSource stimulusSource = new StimulusSource(this
					.getFrmQualityRequirementManagement()
					.getTxtDescriptionStimulusSource().getText(), this
					.getFrmQualityRequirementManagement()
					.getTxtValueStimulusSource().getText(),
					(StimulusSourceType) this
							.getFrmQualityRequirementManagement()
							.getCmbTypeStimulusSource().getSelectedItem());
			Stimulus stimulus = new Stimulus(this
					.getFrmQualityRequirementManagement()
					.getTxtDescriptionStimulus().getText(), this
					.getFrmQualityRequirementManagement().getTxtValueStimulus()
					.getText(), (StimulusType) this
					.getFrmQualityRequirementManagement().getCmbTypeStimulus()
					.getSelectedItem());
			Environment environment = new Environment(this
					.getFrmQualityRequirementManagement()
					.getTxtDescriptionEnvironment().getText(), this
					.getFrmQualityRequirementManagement()
					.getTxtValueEnvironment().getText(), (EnvironmentType) this
					.getFrmQualityRequirementManagement()
					.getCmbTypeEnvironment().getSelectedItem());
			Artifact artifact = new Artifact(this
					.getFrmQualityRequirementManagement()
					.getTxtDescriptionArtifact().getText(), (ArtifactType) this
					.getFrmQualityRequirementManagement().getCmbTypeArtifact()
					.getSelectedItem());
			Response response = new Response(this
					.getFrmQualityRequirementManagement()
					.getTxtDescriptionResponse().getText(), this
					.getFrmQualityRequirementManagement().getTxtValueResponse()
					.getText(), (ResponseType) this
					.getFrmQualityRequirementManagement().getCmbTypeResponse()
					.getSelectedItem());
			ResponseMeasure responseMeasure = new ResponseMeasure(this
					.getFrmQualityRequirementManagement()
					.getTxtDescriptionResponseMeasure().getText(),
					Double.parseDouble(this
							.getFrmQualityRequirementManagement()
							.getTxtValueResponseMeasure().getText()),
					(ResponseMeasureType) this
							.getFrmQualityRequirementManagement()
							.getCmbTypeResponseMeasure().getSelectedItem(),
					(Metric) this.getFrmQualityRequirementManagement()
							.getCmbMetric().getSelectedItem(), (Unit) this
							.getFrmQualityRequirementManagement().getCmbUnit()
							.getSelectedItem());
			this.getManager().newQualityRequirement(
					(software.DomainModel.AnalysisEntity.System) this
							.getFrmQualityRequirementManagement()
							.getCmbSystem().getSelectedItem(),
					this.getFrmQualityRequirementManagement()
							.getTxtDescription().getText(),
					true,
					(QualityAttribute) this
							.getFrmQualityRequirementManagement()
							.getCmbQualityAttribute().getSelectedItem(),
					stimulusSource,
					stimulus,
					artifact,
					environment,
					response,
					responseMeasure,
					(Condition) this.getFrmQualityRequirementManagement()
							.getCmbCondition().getSelectedItem());
			return 0;
		} else {
			return 1;
		}
	}

	/**
	 * Update a quality requirement and its update the specific scenario
	 */
	public int setQualityRequirement() {
		if (this.isValidData()) {
			this.getManager()
					.getQualityRequirement()
					.getQualityScenario()
					.setDescription(
							this.getFrmQualityRequirementManagement()
									.getTxtDescription().getText());
			this.getManager().setStimulusSource(
					this.getFrmQualityRequirementManagement()
							.getTxtDescriptionStimulusSource().getText(),
					this.getFrmQualityRequirementManagement()
							.getTxtValueStimulusSource().getText(),
					(StimulusSourceType) this
							.getFrmQualityRequirementManagement()
							.getCmbTypeStimulusSource().getSelectedItem());
			this.getManager().setStimulus(
					this.getFrmQualityRequirementManagement()
							.getTxtDescriptionStimulus().getText(),
					this.getFrmQualityRequirementManagement()
							.getTxtValueStimulus().getText(),
					(StimulusType) this.getFrmQualityRequirementManagement()
							.getCmbTypeStimulus().getSelectedItem());
			this.getManager().setEnvironment(
					this.getFrmQualityRequirementManagement()
							.getTxtDescriptionEnvironment().getText(),
					this.getFrmQualityRequirementManagement()
							.getTxtValueEnvironment().getText(),
					(EnvironmentType) this.getFrmQualityRequirementManagement()
							.getCmbTypeEnvironment().getSelectedItem());
			this.getManager().setArtifact(
					this.getFrmQualityRequirementManagement()
							.getTxtDescriptionArtifact().getText(),
					(ArtifactType) this.getFrmQualityRequirementManagement()
							.getCmbTypeArtifact().getSelectedItem());
			this.getManager().setResponse(
					this.getFrmQualityRequirementManagement()
							.getTxtDescriptionResponse().getText(),
					this.getFrmQualityRequirementManagement()
							.getTxtValueResponse().getText(),
					(ResponseType) this.getFrmQualityRequirementManagement()
							.getCmbTypeResponse().getSelectedItem());
			this.getManager().setResponseMeasure(
					this.getFrmQualityRequirementManagement()
							.getTxtDescriptionResponseMeasure().getText(),
					Double.parseDouble(this
							.getFrmQualityRequirementManagement()
							.getTxtValueResponseMeasure().getText()),
					(ResponseMeasureType) this
							.getFrmQualityRequirementManagement()
							.getCmbTypeResponseMeasure().getSelectedItem(),
					(Metric) this.getFrmQualityRequirementManagement()
							.getCmbMetric().getSelectedItem(),
					(Unit) this.getFrmQualityRequirementManagement()
							.getCmbUnit().getSelectedItem());
			return 0;
		} else {
			return 1;
		}
	}

	/**
	 * return true if they have completed the required fields
	 */
	public boolean isValidData() {
		if (this.isEmpty(this.getFrmQualityRequirementManagement()
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
			this.getFrmQualityRequirementManagement().getCmbSystem()
					.grabFocus();
			return false;
		}
		if (this.isEmpty(this.getFrmQualityRequirementManagement()
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
			this.getFrmQualityRequirementManagement().getTxtDescription()
					.grabFocus();
			return false;
		}
		if (this.isEmpty(this.getFrmQualityRequirementManagement()
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
			this.getFrmQualityRequirementManagement().getCmbQualityAttribute()
					.grabFocus();
			return false;
		}
		if (this.isEmpty(this.getFrmQualityRequirementManagement()
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
			this.getFrmQualityRequirementManagement().getCmbCondition()
					.grabFocus();
			return false;
		} else if (this.isEmpty(this.getFrmQualityRequirementManagement()
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
			this.getFrmQualityRequirementManagement()
					.getTxtDescriptionStimulusSource().grabFocus();
			return false;
		} else if (this.isEmpty(this.getFrmQualityRequirementManagement()
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
			this.getFrmQualityRequirementManagement()
					.getTxtDescriptionStimulus().grabFocus();
			return false;
		} else if (this.isEmpty(this.getFrmQualityRequirementManagement()
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
			this.getFrmQualityRequirementManagement()
					.getTxtDescriptionEnvironment().grabFocus();
			return false;
		} else if (this.isEmpty(this.getFrmQualityRequirementManagement()
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
			this.getFrmQualityRequirementManagement()
					.getTxtDescriptionArtifact().grabFocus();
			return false;
		} else if (this.isEmpty(this.getFrmQualityRequirementManagement()
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
			this.getFrmQualityRequirementManagement()
					.getTxtDescriptionResponse().grabFocus();
			return false;
		} else if (this.isEmpty(this.getFrmQualityRequirementManagement()
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
			this.getFrmQualityRequirementManagement()
					.getTxtDescriptionResponseMeasure().grabFocus();
			return false;
		} else if (this.isEmpty(this.getFrmQualityRequirementManagement()
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
			this.getFrmQualityRequirementManagement()
					.getTxtValueStimulusSource().grabFocus();
			return false;
		} else if (this.isEmpty(this.getFrmQualityRequirementManagement()
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
			this.getFrmQualityRequirementManagement().getTxtValueStimulus()
					.grabFocus();
			return false;
		} else if (this.isEmpty(this.getFrmQualityRequirementManagement()
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
			this.getFrmQualityRequirementManagement().getTxtValueEnvironment()
					.grabFocus();
			return false;
		} else if (this.isEmpty(this.getFrmQualityRequirementManagement()
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
			this.getFrmQualityRequirementManagement().getTxtValueEnvironment()
					.grabFocus();
			this.getFrmQualityRequirementManagement().getTxtValueResponse()
					.grabFocus();
			return false;
		} else if (this.isEmpty(this.getFrmQualityRequirementManagement()
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
			this.getFrmQualityRequirementManagement()
					.getCmbTypeStimulusSource().grabFocus();
			return false;
		} else if (this.isEmpty(this.getFrmQualityRequirementManagement()
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
			this.getFrmQualityRequirementManagement().getCmbTypeStimulus()
					.grabFocus();
			return false;
		} else if (this.isEmpty(this.getFrmQualityRequirementManagement()
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
			this.getFrmQualityRequirementManagement().getCmbTypeEnvironment()
					.grabFocus();
			return false;
		} else if (this.isEmpty(this.getFrmQualityRequirementManagement()
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
			this.getFrmQualityRequirementManagement().getCmbTypeArtifact()
					.grabFocus();
			return false;
		} else if (this.isEmpty(this.getFrmQualityRequirementManagement()
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
			this.getFrmQualityRequirementManagement().getCmbTypeResponse()
					.grabFocus();
			return false;
		} else if (this.isEmpty(this.getFrmQualityRequirementManagement()
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
			this.getFrmQualityRequirementManagement()
					.getCmbTypeResponseMeasure().grabFocus();
			return false;
		} else if (this.isEmpty(this.getFrmQualityRequirementManagement()
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
			this.getFrmQualityRequirementManagement().getCmbMetric()
					.grabFocus();
			return false;
		} else if (this.isEmpty(this.getFrmQualityRequirementManagement()
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
			this.getFrmQualityRequirementManagement()
					.getTxtValueResponseMeasure().grabFocus();
			return false;
		} else if (Double.parseDouble(this.getFrmQualityRequirementManagement()
				.getTxtValueResponseMeasure().getText()) <= 0) {
			JOptionPane.showOptionDialog(
					null,
					"Invalid response measure value",
					"Warning",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.ERROR_MESSAGE,
					new ImageIcon(FrmSystemManagement.class
							.getResource("/Icons/error.png")),
					new Object[] { "OK" }, "OK");
			this.getFrmQualityRequirementManagement()
					.getTxtValueResponseMeasure().grabFocus();
			return false;
		} else if (this.isEmpty(this.getFrmQualityRequirementManagement()
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
			this.getFrmQualityRequirementManagement().getCmbUnit().grabFocus();
			return false;
		}

		return true;
	}

	/**
	 * Validate whether a JTextField is empty
	 * @param ptxt
	 */
	public boolean isEmpty(JTextField ptxt) {
		return (ptxt.getText().equals(""));
	}

	/**
	 * Validate whether a JTextArea is empty
	 * @param ptxt
	 */
	public boolean isEmpty(JTextArea ptxt) {
		return (ptxt.getText().equals(""));
	}

	/**
	 * Validate whether a JComboBox is empty
	 * @param pcmb
	 */
	public boolean isEmpty(JComboBox pcmb) {
		return pcmb.getSelectedItem() == "";
	}

	/**
	 * Sets the model table of the quality requirements of a specific system
	 * @param ptype
	 */
	public void setModelQualityRequirement(
			software.DomainModel.AnalysisEntity.System ptype) {
		this.getManager().setSystem(ptype);
		while (this.getFrmQualityRequirementSearch().getModel().getRowCount() > 0) {
			this.getFrmQualityRequirementSearch().getModel().removeRow(0);
		}
		for (QualityRequirement dp : this.getManager().getQualityRequirements()) {
			if (dp.isState()) {
				this.getFrmQualityRequirementSearch()
						.getModel()
						.addRow(new Object[] { dp,
								dp.getQualityScenario().getQualityAttribute(),
								dp.getQualityScenario().getDescription(),
								dp.getQualityScenario().getCondition() });
			}
		}
	}

	public void setModel(QualityRequirement pmodel) {
		this.getManager().setQualityRequirement(pmodel);
	}

	public void getView() {
		this.getFrmQualityRequirementManagement().loadCombo();
		this.getFrmQualityRequirementManagement().loadComboMetric(
				this.getManager().getTypeResponseMeasure());
		this.getFrmQualityRequirementManagement().loadComboUnit(
				this.getManager().getMetric());

		this.getFrmQualityRequirementManagement().getCmbSystem()
				.setSelectedItem(this.getManager().getSystem());
		this.getFrmQualityRequirementManagement().getTxtDescription()
				.setText(this.getManager().getDescriptionScenario());
		this.getFrmQualityRequirementManagement().getCmbQualityAttribute()
				.setSelectedItem(this.getManager().getQualityAttribute());
		this.getFrmQualityRequirementManagement().getCmbCondition()
				.setSelectedItem(this.getManager().getConditionScenario());

		this.getFrmQualityRequirementManagement()
				.getTxtDescriptionStimulusSource()
				.setText(this.getManager().getDescriptionStimulusSource());
		this.getFrmQualityRequirementManagement().getCmbTypeStimulusSource()
				.setSelectedItem(this.getManager().getTypeStimulusSource());
		this.getFrmQualityRequirementManagement().getTxtValueStimulusSource()
				.setText(this.getManager().getValueStimulusSource());

		this.getFrmQualityRequirementManagement().getTxtDescriptionStimulus()
				.setText(this.getManager().getDescriptionStimulus());
		this.getFrmQualityRequirementManagement().getCmbTypeStimulus()
				.setSelectedItem(this.getManager().getTypeStimulus());
		this.getFrmQualityRequirementManagement().getTxtValueStimulus()
				.setText(this.getManager().getValueStimulus());

		this.getFrmQualityRequirementManagement()
				.getTxtDescriptionEnvironment()
				.setText(this.getManager().getDescriptionEnvironment());
		this.getFrmQualityRequirementManagement().getCmbTypeEnvironment()
				.setSelectedItem(this.getManager().getTypeEnvironment());
		this.getFrmQualityRequirementManagement().getTxtValueEnvironment()
				.setText(this.getManager().getValueEnvironment());

		this.getFrmQualityRequirementManagement().getTxtDescriptionArtifact()
				.setText(this.getManager().getDescriptionArtifact());
		this.getFrmQualityRequirementManagement().getCmbTypeArtifact()
				.setSelectedItem(this.getManager().getTypeArtifact());

		this.getFrmQualityRequirementManagement().getTxtDescriptionResponse()
				.setText(this.getManager().getDescriptionResponse());
		this.getFrmQualityRequirementManagement().getCmbTypeResponse()
				.setSelectedItem(this.getManager().getTypeResponse());
		this.getFrmQualityRequirementManagement().getTxtValueResponse()
				.setText(this.getManager().getValueResponse());

		this.getFrmQualityRequirementManagement()
				.getTxtDescriptionResponseMeasure()
				.setText(this.getManager().getDescriptionResponseMeasure());
		this.getFrmQualityRequirementManagement().getCmbTypeResponseMeasure()
				.setSelectedItem(this.getManager().getTypeResponseMeasure());
		this.getFrmQualityRequirementManagement().getCmbMetric()
				.setSelectedItem(this.getManager().getMetric());
		this.getFrmQualityRequirementManagement().getTxtValueResponseMeasure()
				.setText(this.getManager().getValueResponseMeasure());
		this.getFrmQualityRequirementManagement().getCmbUnit()
				.setSelectedItem(this.getManager().getUnit());
	}

	public void removeView() {
		this.getManager().removeQualityRequirement();
		this.getFrmQualityRequirementManagement().clearView();
		this.setOpcABM(3);
		this.getFrmQualityRequirementManagement().prepareView(this.getOpcABM());
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
