package software.DataManager;

import java.util.HashSet;
import java.util.Set;

import software.BusinessLogic.AnalysisManager;
import software.DomainModel.AnalysisEntity.ArtifactType;
import software.DomainModel.AnalysisEntity.Condition;
import software.DomainModel.AnalysisEntity.EnvironmentType;
import software.DomainModel.AnalysisEntity.GenericScenario;
import software.DomainModel.AnalysisEntity.Metric;
import software.DomainModel.AnalysisEntity.QualityAttribute;
import software.DomainModel.AnalysisEntity.ResponseMeasureType;
import software.DomainModel.AnalysisEntity.ResponseType;
import software.DomainModel.AnalysisEntity.StimulusSourceType;
import software.DomainModel.AnalysisEntity.StimulusType;
import software.DomainModel.AnalysisEntity.Unit;

/**
 * This class initialize the conditions, quality attributes and its responsible
 * for creating specific scenarios (Creator Pattern).
 * 
 * @author: FEM
 * @version: 06/11/2015
 */
public class Data { // NOPMD by Usuario-Pc on 10/06/16 21:53

	public static void initialize() {

		AnalysisManager manager = AnalysisManager.getManager();

		Unit unit1 = new Unit("Seconds");
		manager.saveObject(unit1);
		Unit unit2 = new Unit("Minutes");
		manager.saveObject(unit2);

		createConditions(manager);
		createQualityAttributeAvailability(manager, unit1, unit2);
		createQualityAttributeConfiability(manager, unit1, unit2);
		createQualityAttributePerformance(manager, unit1, unit2);

	}

	private static void createConditions(AnalysisManager pmanager) {
		Condition c1 = new Condition("Normal");
		Condition c2 = new Condition("Overload");
		pmanager.saveObject(c1);
		pmanager.saveObject(c2);
	}

	private static void createQualityAttributeAvailability(HibernateManager pmanager, Unit punit1, Unit punit2) {
		StimulusSourceType ss1 = new StimulusSourceType("Internal to the system");
		StimulusSourceType ss2 = new StimulusSourceType("External to the system");
		Set<StimulusSourceType> stimulusSourceTypes = new HashSet<StimulusSourceType>();
		stimulusSourceTypes.add(ss1);
		stimulusSourceTypes.add(ss2);

		StimulusType s1 = new StimulusType("Omission");
		StimulusType s2 = new StimulusType("Crash");
		StimulusType s3 = new StimulusType("Timing");
		StimulusType s4 = new StimulusType("Response");
		Set<StimulusType> stimulusTypes = new HashSet<StimulusType>();
		stimulusTypes.add(s1);
		stimulusTypes.add(s2);
		stimulusTypes.add(s3);
		stimulusTypes.add(s4);

		EnvironmentType e1 = new EnvironmentType("Normal operation");
		EnvironmentType e2 = new EnvironmentType("Degraded mode");
		Set<EnvironmentType> environmentTypes = new HashSet<EnvironmentType>();
		environmentTypes.add(e1);
		environmentTypes.add(e2);

		ArtifactType a1 = new ArtifactType("System's processors");
		ArtifactType a2 = new ArtifactType("Communication channels");
		ArtifactType a3 = new ArtifactType("Persistent storage");
		ArtifactType a4 = new ArtifactType("Processes");
		Set<ArtifactType> artifactTypes = new HashSet<ArtifactType>();
		artifactTypes.add(a1);
		artifactTypes.add(a2);
		artifactTypes.add(a3);
		artifactTypes.add(a4);

		ResponseType r1 = new ResponseType("Record it");
		ResponseType r2 = new ResponseType("Notify");
		ResponseType r3 = new ResponseType("Disable sources of events");
		ResponseType r4 = new ResponseType("Be unavailable for a prespecified interval");
		ResponseType r5 = new ResponseType("Continue to operate in normal or degraded mode");
		Set<ResponseType> responseTypes = new HashSet<ResponseType>();
		responseTypes.add(r1);
		responseTypes.add(r2);
		responseTypes.add(r3);
		responseTypes.add(r4);
		responseTypes.add(r5);

		Set<Unit> units = new HashSet<Unit>();
		units.add(punit1);
		units.add(punit2);

		Metric metric1 = new Metric("Availability average time", units);
		Metric metric2 = new Metric("Availability half time", units);
		pmanager.saveObject(metric1);
		pmanager.saveObject(metric2);
		Set<Metric> metrics = new HashSet<Metric>();
		metrics.add(metric1);
		metrics.add(metric2);

		ResponseMeasureType type1 = new ResponseMeasureType("Time interval when the system must be available", metrics);
		ResponseMeasureType type2 = new ResponseMeasureType("Availability Time", metrics);
		ResponseMeasureType type3 = new ResponseMeasureType("Time interval in which system can be in degraded mode",
				metrics);
		ResponseMeasureType type4 = new ResponseMeasureType("Repair time", metrics);
		Set<ResponseMeasureType> responseMeasureTypes = new HashSet<ResponseMeasureType>();
		responseMeasureTypes.add(type1);
		responseMeasureTypes.add(type2);
		responseMeasureTypes.add(type3);
		responseMeasureTypes.add(type4);

		QualityAttribute attribute;
		attribute = new QualityAttribute("Availability", new GenericScenario(stimulusSourceTypes, stimulusTypes,
				environmentTypes, artifactTypes, responseTypes, responseMeasureTypes));
		attribute.setAttributeGenericScenario();
		pmanager.saveObject(attribute);

	}

	private static void createQualityAttributeConfiability(HibernateManager pmanager, Unit punit1, Unit punit2) {
		StimulusSourceType ss1 = new StimulusSourceType("Internal to the system");
		StimulusSourceType ss2 = new StimulusSourceType("External to the system");
		Set<StimulusSourceType> stimulusSourceTypes = new HashSet<StimulusSourceType>();
		stimulusSourceTypes.add(ss1);
		stimulusSourceTypes.add(ss2);

		StimulusType s1 = new StimulusType("Omission");
		StimulusType s2 = new StimulusType("Crash");
		StimulusType s3 = new StimulusType("Timing");
		StimulusType s4 = new StimulusType("Response");
		Set<StimulusType> stimulusTypes = new HashSet<StimulusType>();
		stimulusTypes.add(s1);
		stimulusTypes.add(s2);
		stimulusTypes.add(s3);
		stimulusTypes.add(s4);

		EnvironmentType e1 = new EnvironmentType("Normal operation");
		EnvironmentType e2 = new EnvironmentType("Degraded mode");
		Set<EnvironmentType> environmentTypes = new HashSet<EnvironmentType>();
		environmentTypes.add(e1);
		environmentTypes.add(e2);

		ArtifactType a1 = new ArtifactType("System's processors");
		ArtifactType a2 = new ArtifactType("Communication channels");
		ArtifactType a3 = new ArtifactType("Persistent storage");
		ArtifactType a4 = new ArtifactType("Processes");
		Set<ArtifactType> artifactTypes = new HashSet<ArtifactType>();
		artifactTypes.add(a1);
		artifactTypes.add(a2);
		artifactTypes.add(a3);
		artifactTypes.add(a4);

		ResponseType r1 = new ResponseType("Record it");
		ResponseType r2 = new ResponseType("Notify");
		ResponseType r3 = new ResponseType("Disable sources of events");
		ResponseType r4 = new ResponseType("Be unavailable for a prespecified interval");
		ResponseType r5 = new ResponseType("Continue to operate in normal or degraded mode");
		Set<ResponseType> responseTypes = new HashSet<ResponseType>();
		responseTypes.add(r1);
		responseTypes.add(r2);
		responseTypes.add(r3);
		responseTypes.add(r4);
		responseTypes.add(r5);

		Set<Unit> units = new HashSet<Unit>();
		units.add(punit1);
		units.add(punit2);

		Metric metric1 = new Metric("Confiability average time", units);
		Metric metric2 = new Metric("Confiability half time", units);
		pmanager.saveObject(metric1);
		pmanager.saveObject(metric2);
		Set<Metric> metrics = new HashSet<Metric>();
		metrics.add(metric1);
		metrics.add(metric2);

		ResponseMeasureType type1 = new ResponseMeasureType("Time interval when the system must be available", metrics);
		ResponseMeasureType type2 = new ResponseMeasureType("Confiability Time", metrics);
		ResponseMeasureType type3 = new ResponseMeasureType("Time interval in which system can be in degraded mode",
				metrics);
		ResponseMeasureType type4 = new ResponseMeasureType("Repair time", metrics);
		Set<ResponseMeasureType> responseMeasureTypes = new HashSet<ResponseMeasureType>();
		responseMeasureTypes.add(type1);
		responseMeasureTypes.add(type2);
		responseMeasureTypes.add(type3);
		responseMeasureTypes.add(type4);

		QualityAttribute attribute;
		attribute = new QualityAttribute("Confiability", new GenericScenario(stimulusSourceTypes, stimulusTypes,
				environmentTypes, artifactTypes, responseTypes, responseMeasureTypes));
		attribute.setAttributeGenericScenario();
		pmanager.saveObject(attribute);

	}

	private static void createQualityAttributePerformance(HibernateManager pmanager, Unit punit1, Unit punit2) {
		StimulusSourceType ss1 = new StimulusSourceType("Stimulus from internal source");
		StimulusSourceType ss2 = new StimulusSourceType("Stimulus from external source");
		Set<StimulusSourceType> stimulusSourceTypes = new HashSet<StimulusSourceType>();
		stimulusSourceTypes.add(ss1);
		stimulusSourceTypes.add(ss2);

		StimulusType s1 = new StimulusType("Periodic events arrive");
		StimulusType s2 = new StimulusType("Sporadic events arrive");
		StimulusType s3 = new StimulusType("Stochastic events arrive");
		Set<StimulusType> stimulusTypes = new HashSet<StimulusType>();
		stimulusTypes.add(s1);
		stimulusTypes.add(s2);
		stimulusTypes.add(s3);

		EnvironmentType e1 = new EnvironmentType("Normal mode");
		EnvironmentType e2 = new EnvironmentType("Overload mode");
		Set<EnvironmentType> environmentTypes = new HashSet<EnvironmentType>();
		environmentTypes.add(e1);
		environmentTypes.add(e2);

		ArtifactType a1 = new ArtifactType("System");
		Set<ArtifactType> artifactTypes = new HashSet<ArtifactType>();
		artifactTypes.add(a1);

		ResponseType r1 = new ResponseType("Processes stimuli");
		ResponseType r2 = new ResponseType("Changes level of service");
		ResponseType r3 = new ResponseType("Disable sources of events");
		Set<ResponseType> responseTypes = new HashSet<ResponseType>();
		responseTypes.add(r1);
		responseTypes.add(r2);
		responseTypes.add(r3);

		Set<Unit> units = new HashSet<Unit>();
		units.add(punit1);
		units.add(punit2);

		Metric metric1 = new Metric("Performance average time", units);
		Metric metric2 = new Metric("Performance half time", units);
		pmanager.saveObject(metric1);
		pmanager.saveObject(metric2);
		Set<Metric> metrics = new HashSet<Metric>();
		metrics.add(metric1);
		metrics.add(metric2);

		ResponseMeasureType type1 = new ResponseMeasureType("Latency", metrics);
		ResponseMeasureType type2 = new ResponseMeasureType("Deadline", metrics);
		ResponseMeasureType type3 = new ResponseMeasureType("Throughput", metrics);
		ResponseMeasureType type4 = new ResponseMeasureType("Jitter", metrics);
		ResponseMeasureType type5 = new ResponseMeasureType("Miss rate", metrics);
		ResponseMeasureType type6 = new ResponseMeasureType("Data loss", metrics);
		Set<ResponseMeasureType> responseMeasureTypes = new HashSet<ResponseMeasureType>();
		responseMeasureTypes.add(type1);
		responseMeasureTypes.add(type2);
		responseMeasureTypes.add(type3);
		responseMeasureTypes.add(type4);
		responseMeasureTypes.add(type5);
		responseMeasureTypes.add(type6);

		QualityAttribute attribute;
		attribute = new QualityAttribute("Performance", new GenericScenario(stimulusSourceTypes, stimulusTypes,
				environmentTypes, artifactTypes, responseTypes, responseMeasureTypes));
		attribute.setAttributeGenericScenario();
		pmanager.saveObject(attribute);

	}

}
