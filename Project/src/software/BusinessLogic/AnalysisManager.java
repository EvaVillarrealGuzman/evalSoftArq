package software.BusinessLogic;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.DefaultComboBoxModel;
import software.DataManager.HibernateManager;
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
import software.DomainModel.AnalysisEntity.SpecificScenario;
import software.DomainModel.AnalysisEntity.Stimulus;
import software.DomainModel.AnalysisEntity.StimulusSource;
import software.DomainModel.AnalysisEntity.StimulusSourceType;
import software.DomainModel.AnalysisEntity.StimulusType;
import software.DomainModel.AnalysisEntity.Unit;

/**
 * This class is responsible for the management package: Analysis
 * 
 * @author: FEM
 * @version: 06/11/2015
 */

public class AnalysisManager extends HibernateManager {

	/**
	 * Attributes
	 */
	private software.DomainModel.AnalysisEntity.System system;
	private QualityRequirement qualityRequirement;
	private static AnalysisManager manager;

	/**
	 * Builder
	 */
	public AnalysisManager() {
		super();
	}

	/**
	 * Get AnalysisManager. Applied Singleton pattern
	 */
	public static AnalysisManager getManager() {
		if (manager == null) {
			synchronized (AnalysisManager.class) {
				manager = new AnalysisManager();
			}
		}
		return manager;
	}

	/**
	 * Getters and Setters
	 */
	public void setSystem(software.DomainModel.AnalysisEntity.System psystem) {
		this.system = psystem;
	}

	public software.DomainModel.AnalysisEntity.System getSystem() {
		return this.system;
	}

	public void setQualityRequirement(QualityRequirement pqualityRequirement) {
		this.qualityRequirement = pqualityRequirement;
	}

	public QualityRequirement getQualityRequirement() {
		return qualityRequirement;
	}

	/**
	 * 
	 * @return ComboBoxModel with quality attributes
	 */
	public DefaultComboBoxModel getComboModelQualityAttribute() {
		DefaultComboBoxModel auxModel = new DefaultComboBoxModel();
		auxModel.addElement("");
		for (QualityAttribute auxTipo : this.listQualityAttribute()) {
			auxModel.addElement(auxTipo);
		}
		return auxModel;
	}

	/**
	 * 
	 * @return List<QualityAttribute> with the names of the quality attributes
	 */
	public List<QualityAttribute> listQualityAttribute() {
		return this.listClass(QualityAttribute.class, "name");
	}

	/**
	 * 
	 * @return ComboBoxModel with system names whose state==true
	 */
	public software.DomainModel.AnalysisEntity.System[]  getComboModelSystem() {
		ArrayList<software.DomainModel.AnalysisEntity.System> systems = new ArrayList<software.DomainModel.AnalysisEntity.System>();
		for (software.DomainModel.AnalysisEntity.System auxTipo : this.listSystem()) {
			systems.add(auxTipo);
		}
		software.DomainModel.AnalysisEntity.System[] arraySystem = new software.DomainModel.AnalysisEntity.System[systems.size()];
		systems.toArray(arraySystem);
		return arraySystem;
	}

	/**
	 * 
	 * @return List<System> with the system names whose state==true
	 */
	public List<software.DomainModel.AnalysisEntity.System> listSystem() {
		return this.listClass(software.DomainModel.AnalysisEntity.System.class, "systemName", true);
	}

	/**
	 * 
	 * @return ComboBoxModel with system names whose state==true,
	 *         qualityRequirement!=empty and qualityRequirement.state==true
	 * 
	 */
	public DefaultComboBoxModel getComboModelSystemWithRequirements() {
		DefaultComboBoxModel auxModel = new DefaultComboBoxModel();
		auxModel.addElement("");
		for (software.DomainModel.AnalysisEntity.System auxTipo : this.listSystem()) {
			if (auxTipo.getQualityRequirements().isEmpty() == false) {
				Iterator it = auxTipo.getQualityRequirements().iterator();
				boolean i = true;
				while (it.hasNext() && i) {
					QualityRequirement q = (QualityRequirement) it.next();
					if (q.isState()) {
						auxModel.addElement(auxTipo);
						i = false;
					}
				}
			}
		}
		return auxModel;
	}

	/**
	 * 
	 * @return True if there are systems whose state==true, else return false
	 * 
	 */
	public boolean existSystemTrue() {
		if (listSystem().isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 
	 * @return True if there are systems whose state==true,
	 *         qualityRequirement!=empty and qualityRequirement.state==true,
	 *         else return false
	 * 
	 */
	public boolean existSystemTrueWithQualityRequirementTrue() {
		for (software.DomainModel.AnalysisEntity.System auxTipo : this.listSystem()) {
			if (auxTipo.getQualityRequirements().isEmpty() == false) {
				Iterator it = auxTipo.getQualityRequirements().iterator();
				while (it.hasNext()) {
					QualityRequirement q = (QualityRequirement) it.next();
					if (q.isState()) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * 
	 * @return ComboBoxModel with condition names
	 * 
	 */
	public DefaultComboBoxModel getComboModelCondition() {
		DefaultComboBoxModel auxModel = new DefaultComboBoxModel();
		auxModel.addElement("");
		for (Condition auxTipo : this.listCondition()) {
			auxModel.addElement(auxTipo);
		}
		return auxModel;
	}

	/**
	 * 
	 * @return List<Condition> with the condition names
	 */
	public List<Condition> listCondition() {
		return this.listClass(Condition.class, "name");
	}

	/**
	 * @param QualityAttribute
	 * @return ComboBoxModel with stimulusSourceType names for an specific
	 *         attribute of quality
	 * 
	 */
	public DefaultComboBoxModel getComboModelStimulusSourceType(QualityAttribute qualityAttribute) {
		DefaultComboBoxModel auxModel = new DefaultComboBoxModel();
		auxModel.addElement("");
		Iterator it = qualityAttribute.getGenericScenario().getStimulusSourceTypes().iterator();
		while (it.hasNext()) {
			StimulusSourceType aux = (StimulusSourceType) it.next();
			auxModel.addElement(aux);
		}
		return auxModel;
	}

	/**
	 * 
	 * @return List<StimulusSourceType> with the stimulusSourceType names
	 */
	public List<StimulusSourceType> listStimulusSourceType() {
		return this.listClass(StimulusSourceType.class, "name");
	}

	/**
	 * @param QualityAttribute
	 * @return ComboBoxModel with stimulusType names for an specific attribute
	 *         of quality
	 * 
	 */
	public DefaultComboBoxModel getComboModelStimulusType(QualityAttribute qualityAttribute) {
		DefaultComboBoxModel auxModel = new DefaultComboBoxModel();
		auxModel.addElement("");
		Iterator it = qualityAttribute.getGenericScenario().getStimulusTypes().iterator();
		while (it.hasNext()) {
			StimulusType aux = (StimulusType) it.next();
			auxModel.addElement(aux);
		}
		return auxModel;
	}

	/**
	 * 
	 * @return List<StimulusType> with the stimulusType names
	 */
	public List<StimulusType> listStimulusType() {
		return this.listClass(StimulusType.class, "name");
	}

	/**
	 * @param QualityAttribute
	 * @return ComboBoxModel with environmentType names for an specific
	 *         attribute of quality
	 * 
	 */
	public DefaultComboBoxModel getComboModelEnvironmentType(QualityAttribute qualityAttribute) {
		DefaultComboBoxModel auxModel = new DefaultComboBoxModel();
		auxModel.addElement("");
		Iterator it = qualityAttribute.getGenericScenario().getEnvironmentTypes().iterator();
		while (it.hasNext()) {
			EnvironmentType aux = (EnvironmentType) it.next();
			auxModel.addElement(aux);
		}
		return auxModel;
	}

	/**
	 * 
	 * @return List<EnvironmentType> with the environmentType names
	 */
	public List<EnvironmentType> listEnvironmentType() {
		return this.listClass(EnvironmentType.class, "name");
	}

	/**
	 * @param QualityAttribute
	 * @return ComboBoxModel with artifactType names for an specific attribute
	 *         of quality
	 * 
	 */
	public DefaultComboBoxModel getComboModelArtifactType(QualityAttribute qualityAttribute) {
		DefaultComboBoxModel auxModel = new DefaultComboBoxModel();
		auxModel.addElement("");
		Iterator it = qualityAttribute.getGenericScenario().getArtifactTypes().iterator();
		while (it.hasNext()) {
			ArtifactType aux = (ArtifactType) it.next();
			auxModel.addElement(aux);
		}
		return auxModel;
	}

	/**
	 * 
	 * @return List<ArtifactType> with the artifactType names
	 */
	public List<ArtifactType> listArtifactType() {
		return this.listClass(ArtifactType.class, "name");
	}

	/**
	 * @param QualityAttribute
	 * @return ComboBoxModel with responseType names for an specific attribute
	 *         of quality
	 * 
	 */
	public DefaultComboBoxModel getComboModelResponseType(QualityAttribute qualityAttribute) {
		DefaultComboBoxModel auxModel = new DefaultComboBoxModel();
		auxModel.addElement("");
		Iterator it = qualityAttribute.getGenericScenario().getResponseTypes().iterator();
		while (it.hasNext()) {
			ResponseType aux = (ResponseType) it.next();
			auxModel.addElement(aux);
		}
		return auxModel;
	}

	/**
	 * 
	 * @return List<ResponseType> with the responseType names
	 */
	public List<ResponseType> listResponseType() {
		return this.listClass(ResponseType.class, "name");
	}

	/**
	 * @param QualityAttribute
	 * @return ComboBoxModel with responseMeasureType names for an specific
	 *         attribute of quality
	 * 
	 */
	public DefaultComboBoxModel getComboModelResponseMeasureType(QualityAttribute qualityAttribute) {
		DefaultComboBoxModel auxModel = new DefaultComboBoxModel();
		auxModel.addElement("");
		Iterator it = qualityAttribute.getGenericScenario().getResponseMeasureTypes().iterator();
		while (it.hasNext()) {
			ResponseMeasureType aux = (ResponseMeasureType) it.next();
			auxModel.addElement(aux);
		}
		return auxModel;
	}

	/**
	 * 
	 * @return List<ResponseMeasureType> with the responseMeasureType names
	 */
	public List<ResponseMeasureType> listResponseMeasureType() {
		return this.listClass(ResponseMeasureType.class, "name");
	}

	/**
	 * @param ResponseMeasureType
	 * @return ComboBoxModel with metric names for an specific response measure
	 *         type
	 * 
	 */
	public DefaultComboBoxModel getComboModelMetric(ResponseMeasureType type) {
		DefaultComboBoxModel auxModel = new DefaultComboBoxModel();
		auxModel.addElement("");
		Iterator it = type.getMetrics().iterator();
		while (it.hasNext()) {
			Metric aux = (Metric) it.next();
			auxModel.addElement(aux);
		}
		return auxModel;
	}

	/**
	 * 
	 * @return List<Metric> with the metric names
	 */
	public List<Metric> listMetric() {
		return this.listClass(Metric.class, "name");
	}

	/**
	 * @param Metric
	 * @return ComboBoxModel with unit names for an specific metric
	 * 
	 */
	public DefaultComboBoxModel getComboModelUnit(Metric type) {
		DefaultComboBoxModel auxModel = new DefaultComboBoxModel();
		auxModel.addElement("");
		Iterator it = type.getUnits().iterator();
		while (it.hasNext()) {
			Unit aux = (Unit) it.next();
			auxModel.addElement(aux);
		}
		return auxModel;
	}

	/**
	 * 
	 * @return List<Unit> with the unit names
	 */
	public List<Unit> listUnit() {
		return this.listClass(Unit.class, "name");
	}

	/**
	 * Sets the parameters of its attribute system
	 * 
	 * @param systemname
	 *            , projectName, projectStartDate, projectFinishDate, state
	 * 
	 */
	public void newSystem(String psystemname, String pprojectName, Date pprojectStartDate, Date pprojectFinishDate,
			Boolean pstate) {
		this.setSystem(new software.DomainModel.AnalysisEntity.System(psystemname, pprojectName, pprojectStartDate,
				pprojectFinishDate, pstate));
	}

	/**
	 * Creates the quality requirement and this is responsible for creating the
	 * specific scenario (Creator Pattern). The quality requirement is added to
	 * the system.
	 * 
	 * @param psystem
	 *            , pdescription, pqualityAttribute, pstimulusSource, pstimulus,
	 *            partifact, penvironment, presponse, presponseMeasure,
	 *            pcondition
	 * 
	 */
	public void newQualityRequirement(software.DomainModel.AnalysisEntity.System psystem, String pdescription,
			boolean pstate, QualityAttribute pqualityAttribute, StimulusSource pstimulusSource, Stimulus pstimulus,
			Artifact partifact, Environment penvironment, Response presponse, ResponseMeasure presponseMeasure,
			Condition pcondition) {
		psystem.getQualityRequirements()
				.add(new QualityRequirement(pstate, new SpecificScenario(pdescription, pqualityAttribute,
						pstimulusSource, pstimulus, partifact, penvironment, presponse, presponseMeasure, pcondition)));
		this.setSystem(psystem);
	}

	// Setters of system atribbutes (name, projectName, startDate, finishDate)
	public void setSystemName(String psystemName) {
		this.getSystem().setSystemName(psystemName);
	}

	public void setProjectName(String pprojectName) {
		this.getSystem().setProjectName(pprojectName);
	}

	public void setStartDate(Date pstartDate) {
		this.getSystem().setProjectStartDate(pstartDate);
	}

	public void setFinishDate(Date pfinishDate) {
		this.getSystem().setProjectFinishDate(pfinishDate);
	}

	// Getters of system atribbutes (name, projectName, startDate, finishDate)
	public String getSystemName() {
		return this.getSystem().getSystemName();
	}

	public String getProjectName() {
		return this.getSystem().getProjectName();
	}

	public Date getStartDate() {
		return this.getSystem().getProjectStartDate();
	}

	public Date getFinishDate() {
		return this.getSystem().getProjectFinishDate();
	}

	public void saveSystem(int popcABM) {
		switch (popcABM) {
		case 0:
			this.saveSystem();
			break;
		case 1:
			this.updateSystem();
			break;
		case 2:
			this.saveSystem();
			break;

		}
	}

	public void saveQualityRequirement(int popcABM) {
		switch (popcABM) {
		case 0:
			this.saveQualityRequirement();
			break;
		case 1:
			this.updateQualityRequirement();
			break;
		case 2:
			this.saveQualityRequirement();
			break;
		}
	}

	public void saveSystem() {
		System.out.println(getSystem().toString());
		this.saveObject(this.getSystem());

	}

	public void updateSystem() {
		this.updateObject(this.getSystem());
	}

	public void saveQualityRequirement() {
		this.updateSystem();
	}

	public void updateQualityRequirement() {
		this.updateSystem();
	}

	public void removeSystem() {
		this.getSystem().remove();
		this.updateSystem();
	}

	public void removeQualityRequirement() {
		this.getQualityRequirement().remove();
		this.updateQualityRequirement();
	}

	public Set<QualityRequirement> getQualityRequirements() {
		return this.getSystem().getQualityRequirements();
	}

	// Getters of specificScenario atribbutes
	public String getDescriptionScenario() {
		return this.getQualityRequirement().getQualityScenario().getDescription();
	}

	public QualityAttribute getQualityAttribute() {
		return this.getQualityRequirement().getQualityScenario().getQualityAttribute();
	}

	public Condition getConditionScenario() {
		return this.getQualityRequirement().getQualityScenario().getCondition();
	}

	public String getDescriptionStimulusSource() {
		return this.getQualityRequirement().getQualityScenario().getStimulusSource().getDescription();
	}

	public StimulusSourceType getTypeStimulusSource() {
		return this.getQualityRequirement().getQualityScenario().getStimulusSource().getType();
	}

	public String getValueStimulusSource() {
		return this.getQualityRequirement().getQualityScenario().getStimulusSource().getValue();
	}

	public String getDescriptionStimulus() {
		return this.getQualityRequirement().getQualityScenario().getStimulus().getDescription();
	}

	public StimulusType getTypeStimulus() {
		return this.getQualityRequirement().getQualityScenario().getStimulus().getType();
	}

	public String getValueStimulus() {
		return this.getQualityRequirement().getQualityScenario().getStimulus().getValue();
	}

	public String getDescriptionEnvironment() {
		return this.getQualityRequirement().getQualityScenario().getEnvironment().getDescription();
	}

	public EnvironmentType getTypeEnvironment() {
		return this.getQualityRequirement().getQualityScenario().getEnvironment().getType();
	}

	public String getValueEnvironment() {
		return this.getQualityRequirement().getQualityScenario().getEnvironment().getValue();
	}

	public String getDescriptionArtifact() {
		return this.getQualityRequirement().getQualityScenario().getArtifact().getDescription();
	}

	public ArtifactType getTypeArtifact() {
		return this.getQualityRequirement().getQualityScenario().getArtifact().getType();
	}

	public String getDescriptionResponse() {
		return this.getQualityRequirement().getQualityScenario().getResponse().getDescription();
	}

	public ResponseType getTypeResponse() {
		return this.getQualityRequirement().getQualityScenario().getResponse().getType();
	}

	public String getValueResponse() {
		return this.getQualityRequirement().getQualityScenario().getResponse().getValue();
	}

	public String getDescriptionResponseMeasure() {
		return this.getQualityRequirement().getQualityScenario().getResponseMeasure().getDescription();
	}

	public ResponseMeasureType getTypeResponseMeasure() {
		return this.getQualityRequirement().getQualityScenario().getResponseMeasure().getType();
	}

	public Metric getMetric() {
		return this.getQualityRequirement().getQualityScenario().getResponseMeasure().getMetric();
	}

	public String getValueResponseMeasure() {
		return Double.toString(this.getQualityRequirement().getQualityScenario().getResponseMeasure().getValue());
	}

	public Unit getUnit() {
		return this.getQualityRequirement().getQualityScenario().getResponseMeasure().getUnit();
	}

	// Setters of specificScenario atribbutes (stimulusSource, stimulus,
	// Enviroment, Artifact, Response, ResponseMeasure)
	public void setStimulusSource(String pdescription, String pvalue, StimulusSourceType ptype) {
		this.getQualityRequirement().getQualityScenario().getStimulusSource().setDescription(pdescription);
		this.getQualityRequirement().getQualityScenario().getStimulusSource().setValue(pvalue);
		this.getQualityRequirement().getQualityScenario().getStimulusSource().setType(ptype);
	}

	public void setStimulus(String pdescription, String pvalue, StimulusType ptype) {
		this.getQualityRequirement().getQualityScenario().getStimulus().setDescription(pdescription);
		this.getQualityRequirement().getQualityScenario().getStimulus().setValue(pvalue);
		this.getQualityRequirement().getQualityScenario().getStimulus().setType(ptype);
	}

	public void setEnvironment(String pdescription, String pvalue, EnvironmentType ptype) {
		this.getQualityRequirement().getQualityScenario().getEnvironment().setDescription(pdescription);
		this.getQualityRequirement().getQualityScenario().getEnvironment().setValue(pvalue);
		this.getQualityRequirement().getQualityScenario().getEnvironment().setType(ptype);
	}

	public void setArtifact(String pdescription, ArtifactType ptype) {
		this.getQualityRequirement().getQualityScenario().getArtifact().setDescription(pdescription);
		this.getQualityRequirement().getQualityScenario().getArtifact().setType(ptype);
	}

	public void setResponse(String pdescription, String pvalue, ResponseType ptype) {
		this.getQualityRequirement().getQualityScenario().getResponse().setDescription(pdescription);
		this.getQualityRequirement().getQualityScenario().getResponse().setValue(pvalue);
		this.getQualityRequirement().getQualityScenario().getResponse().setType(ptype);
	}

	public void setResponseMeasure(String pdescription, double pvalue, ResponseMeasureType ptype, Metric pmetric,
			Unit punit) {
		this.getQualityRequirement().getQualityScenario().getResponseMeasure().setDescription(pdescription);
		this.getQualityRequirement().getQualityScenario().getResponseMeasure().setValue(pvalue);
		this.getQualityRequirement().getQualityScenario().getResponseMeasure().setType(ptype);

	}

}