package BusinessLogic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import DataManager.HibernateManager;
import DataManager.HibernateUtil;
import DomainModel.AnalysisEntity.QualityAttribute;
import DomainModel.AnalysisEntity.QualityRequirement;
import DomainModel.AnalysisEntity.Tactic;
import DomainModel.AnalysisEntity.Unit;
import DomainModel.ReportsEntity.ResponsibilityAvailability;
import DomainModel.ReportsEntity.ResponsibilityPerformance;
import DomainModel.ReportsEntity.ResponsibilityReliability;
import DomainModel.ReportsEntity.SystemAvailability;
import DomainModel.ReportsEntity.SystemPerformance;
import DomainModel.ReportsEntity.SystemReliability;
import DomainModel.ReportsEntity.ireport;
import DomainModel.SoftwareArchitectureEvaluationEntity.Indicator;
import DomainModel.SoftwareArchitectureEvaluationEntity.Run;
import DomainModel.SoftwareArchitectureEvaluationEntity.SystemIndicator;
import DomainModel.SoftwareArchitectureSpecificationEntity.Architecture;

/**
 * This class is responsible for the management package: Report
 * 
 * @author: MICA
 * @version: 07/09/2016
 */
public class ReportManager extends HibernateManager {
	/**
	 * Attributes
	 */
	private DomainModel.AnalysisEntity.System system;
	private Architecture architecture;
	private QualityRequirement qualityRequirement;
	private QualityAttribute qualityAttribute;
	

	/**
	 * Getters and Setters
	 */
	
	public void setSystem(DomainModel.AnalysisEntity.System psystem) {
		this.system = psystem;
	}

	public DomainModel.AnalysisEntity.System getSystem() {
		return this.system;
	}

	public Architecture getArchitecture() {
		return architecture;
	}

	public void setArchitecture(Architecture architecture) {
		this.architecture = architecture;
	}

	public QualityRequirement getQualityRequirement() {
		return qualityRequirement;
	}

	public void setQualityRequirement(QualityRequirement qualityRequirement) {
		this.qualityRequirement = qualityRequirement;
	}

	public QualityAttribute getQualityAttribute() {
		return qualityAttribute;
	}

	public void setQualityAttribute(QualityAttribute qualityAttribute) {
		this.qualityAttribute = qualityAttribute;
	}

	/**
	 * 
	 * @return True if there are systems whose state==true, else return false
	 * 
	 */
	public boolean existSystemTrue() {
		for (DomainModel.AnalysisEntity.System auxTipo : this.listSystem()) {
			Iterator it = auxTipo.getArchitectures().iterator();
			if (it.hasNext()) {
				Architecture q = (Architecture) it.next();
				if (q.getSimulator() != null) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 
	 * @return ComboBoxModel with system names whose state==true
	 */
	public DomainModel.AnalysisEntity.System[] getComboModelSystemWithSimulations() {
		ArrayList<DomainModel.AnalysisEntity.System> systems = new ArrayList<DomainModel.AnalysisEntity.System>();
		for (DomainModel.AnalysisEntity.System auxSystem : this.listSystem()) {
			Iterator it = auxSystem.getArchitectures().iterator();
			if (it.hasNext()) {
				Architecture q = (Architecture) it.next();
				if (q.getSimulator() != null) {
					systems.add(auxSystem);
				}
			}
		}
		DomainModel.AnalysisEntity.System[] arraySystem = new DomainModel.AnalysisEntity.System[systems.size()];
		systems.toArray(arraySystem);
		return arraySystem;
	}

	/**
	 * 
	 * @return List<System> with the system names whose state==true
	 */
	public List<DomainModel.AnalysisEntity.System> listSystem() {
		return this.listClass(DomainModel.AnalysisEntity.System.class, "systemName", true);
	}

	public void updateSystem() {
		this.updateObject(this.getSystem());
	}

	public String getPathUCMs() {
		Iterator it = this.getSystem().getArchitectures().iterator();
		if (it.hasNext()) {
			Architecture a = (Architecture) it.next();
			return a.getPathUCM();
		} else {
			return null;
		}
	}

	public void setPathUCMs(String pathUCM) {
		Iterator it = this.getSystem().getArchitectures().iterator();
		if (it.hasNext()) {
			Architecture a = (Architecture) it.next();
			a.setPathUCM(pathUCM);
		} else {
			Architecture pa = new Architecture(pathUCM);
			this.getSystem().getArchitectures().add(pa);
		}
	}

	public List<QualityAttribute> listQualityAttribute() {
		return this.listClass(QualityAttribute.class, "name");
	}

	public Set<Architecture> getArchitectures() {
		return this.getSystem().getArchitectures();
	}

	public QualityAttribute[] getQualityAttributes() {
		ArrayList<QualityAttribute> qualityAttributes = new ArrayList<QualityAttribute>();
		for (QualityAttribute auxTipo : this.listQualityAttribute()) {
			qualityAttributes.add(auxTipo);
		}
		QualityAttribute[] arrayQualityAttribute = new QualityAttribute[qualityAttributes.size()];
		qualityAttributes.toArray(arrayQualityAttribute);
		return arrayQualityAttribute;
	}

	/**
	 * Return if connection with database is success
	 * 
	 * @return
	 */
	public Boolean isConnection() {
		if (HibernateUtil.getSession().isOpen()) {
			HibernateUtil.getSession().close();
		}
		HibernateUtil.initialize(this.getDb());
		HibernateUtil hu = new HibernateUtil();
		return hu.isConnection();
	}

	public List<ResponsibilityPerformance> listResponsibilityPerformance() {

		List<ResponsibilityPerformance> list = new ArrayList<ResponsibilityPerformance>();

		Architecture f = this.getArchitecture();
		int i = 0;
		for (Iterator<Run> its = f.getSimulator().getRuns().iterator(); its.hasNext();) {
			Run r = its.next();
			for (Iterator<Indicator> iti = r.getIndicators().iterator(); iti.hasNext();) {
				Indicator ind = iti.next();
				if (ind.getMetric().getName().equals("Responsibility Turnaround Time")) {
					if (i == 0) {
						ResponsibilityPerformance item = new ResponsibilityPerformance();
						item.setResponsibilityTT(ind.getType().getName());
						item.setTurnaroundTime(
								this.convertValueAcordingToUnitRequirement(ind.getValue(), ind.getUnit()) / 10);
						list.add(item);
					} else {
						Iterator ite = list.iterator();
						while (ite.hasNext()) {
							ResponsibilityPerformance q = (ResponsibilityPerformance) ite.next();
							if (q.getResponsibilityTT().equals(ind.getType().getName())) {
								q.setTurnaroundTime(q.getTurnaroundTime()
										+ this.convertValueAcordingToUnitRequirement(ind.getValue(), ind.getUnit())
												/ 10);
							}
						}
					}
				}
			}
			i++;
		}
		return list;
	}

	public List<ResponsibilityReliability> listResponsibilityReliability() {

		List<ResponsibilityReliability> list = new ArrayList<ResponsibilityReliability>();

		Architecture f = this.getArchitecture();
		int i = 0;
		for (Iterator<Run> its = f.getSimulator().getRuns().iterator(); its.hasNext();) {
			Run r = its.next();
			for (Iterator<Indicator> iti = r.getIndicators().iterator(); iti.hasNext();) {
				Indicator ind = iti.next();
				if (ind.getMetric().getName().equals("Responsibility Failures")) {
					if (i == 0) {
						ResponsibilityReliability item = new ResponsibilityReliability();
						item.setResponsibilityF(ind.getType().getName());
						item.setFails(ind.getValue() / 10);
						list.add(item);
					} else {
						Iterator ite = list.iterator();
						while (ite.hasNext()) {
							ResponsibilityReliability q = (ResponsibilityReliability) ite.next();
							if (q.getResponsibilityF().equals(ind.getType().getName())) {
								q.setFails(q.getFails() + ind.getValue() / 10);
							}
						}
					}
				}
			}
			i++;
		}
		return list;
	}

	public List<ResponsibilityAvailability> listResponsibilityAvailability() {

		List<ResponsibilityAvailability> list = new ArrayList<ResponsibilityAvailability>();

		Architecture f = this.getArchitecture();
		int i = 0;
		for (Iterator<Run> its = f.getSimulator().getRuns().iterator(); its.hasNext();) {
			Run r = its.next();
			for (Iterator<Indicator> iti = r.getIndicators().iterator(); iti.hasNext();) {
				Indicator ind = iti.next();
				if (ind.getMetric().getName().equals("Responsibility Downtime")) {
					if (i == 0) {
						if (list.isEmpty()) {
							ResponsibilityAvailability item = new ResponsibilityAvailability();
							item.setResponsibility(ind.getType().getName());
							item.setDowntime(
									this.convertValueAcordingToUnitRequirement(ind.getValue(), ind.getUnit()) / 10);
							list.add(item);
						} else {
							Iterator ite = list.iterator();
							boolean band = true;
							while (ite.hasNext()) {
								ResponsibilityAvailability q = (ResponsibilityAvailability) ite.next();
								if (q.getResponsibility().equals(ind.getType().getName())) {
									band = false;
									q.setDowntime(
											this.convertValueAcordingToUnitRequirement(ind.getValue(), ind.getUnit())
													/ 10);
								}
							}
							if (band) {
								ResponsibilityAvailability item = new ResponsibilityAvailability();
								item.setResponsibility(ind.getType().getName());
								item.setDowntime(
										this.convertValueAcordingToUnitRequirement(ind.getValue(), ind.getUnit()) / 10);
								list.add(item);
							}
						}
					} else {
						Iterator ite = list.iterator();
						while (ite.hasNext()) {
							ResponsibilityAvailability q = (ResponsibilityAvailability) ite.next();
							if (q.getResponsibility().equals(ind.getType().getName())) {
								q.setDowntime(q.getDowntime()
										+ this.convertValueAcordingToUnitRequirement(ind.getValue(), ind.getUnit())
												/ 10);
							}
						}
					}
				}
				if (ind.getMetric().getName().equals("Responsibility Recovery Time")) {
					if (i == 0) {
						if (list.isEmpty()) {
							ResponsibilityAvailability item = new ResponsibilityAvailability();
							item.setResponsibility(ind.getType().getName());
							item.setRecoveryTime(
									this.convertValueAcordingToUnitRequirement(ind.getValue(), ind.getUnit()));
							list.add(item);
						} else {
							Iterator ite = list.iterator();
							boolean band = true;
							while (ite.hasNext()) {
								ResponsibilityAvailability q = (ResponsibilityAvailability) ite.next();
								if (q.getResponsibility().equals(ind.getType().getName())) {
									band = false;
									q.setRecoveryTime(
											this.convertValueAcordingToUnitRequirement(ind.getValue(), ind.getUnit()));
								}
							}
							if (band) {
								ResponsibilityAvailability item = new ResponsibilityAvailability();
								item.setResponsibility(ind.getType().getName());
								item.setRecoveryTime(
										this.convertValueAcordingToUnitRequirement(ind.getValue(), ind.getUnit()));
								list.add(item);
							}
						}
					} else {
						Iterator ite = list.iterator();
						while (ite.hasNext()) {
							ResponsibilityAvailability q = (ResponsibilityAvailability) ite.next();
							if (q.getResponsibility().equals(ind.getType().getName())) {
								q.setRecoveryTime(q.getRecoveryTime()
										+ this.convertValueAcordingToUnitRequirement(ind.getValue(), ind.getUnit())
												/ 10);
							}
						}
					}
				}
			}
			i++;
		}
		return list;
	}

	public List<SystemAvailability> listSystemAvailability() {

		List<SystemAvailability> list = new ArrayList<SystemAvailability>();

		Architecture f = this.getArchitecture();
		int runNum = 1;
		for (Iterator<Run> its = f.getSimulator().getRuns().iterator(); its.hasNext();) {
			Run r = its.next();
			boolean band = true;
			for (Iterator<Indicator> iti = r.getIndicators().iterator(); iti.hasNext();) {
				Indicator ind = iti.next();
				if (ind.getType() instanceof SystemIndicator && band) {
					SystemAvailability item = new SystemAvailability();
					item.setAvailabilityE(this.getAvailabilityTimeRequirement());
					item.setNoAvailabilityE(this.getNoAvailabilityTimeRequirement());
					item.setSystem(ind.getType().getName());
					item.setRun(Integer.toString(runNum));
					list.add(item);
					band = false;
				}
				if (ind.getMetric().getName().equals("System Availability Time")) {
					SystemAvailability q = list.get(runNum - 1);
					q.setAvailability(this.convertValueAcordingToUnitRequirement(ind.getValue(), ind.getUnit()));
				}
				if (ind.getMetric().getName().equals("System No-Availability Time")) {
					SystemAvailability q = list.get(runNum - 1);
					q.setNoAvailability(this.convertValueAcordingToUnitRequirement(ind.getValue(), ind.getUnit()));
				}
			}

			runNum++;
		}
		return list;
	}

	public double getAvailabilityTimeRequirement() {
		if (this.getQualityRequirement().getQualityScenario().getResponseMeasure().getMetric().getName()
				.equals("System Availability Time")) {
			return this.getQualityRequirement().getQualityScenario().getResponseMeasure().getValue();
		} else {
			return this.getSimulationTime()-this.getQualityRequirement().getQualityScenario().getResponseMeasure().getValue();
		}
	}

	public double getNoAvailabilityTimeRequirement() {
		if (this.getQualityRequirement().getQualityScenario().getResponseMeasure().getMetric().getName()
				.equals("System No-Availability Time")) {
			return this.getQualityRequirement().getQualityScenario().getResponseMeasure().getValue();
		} else {
			return this.getSimulationTime()-this.getQualityRequirement().getQualityScenario().getResponseMeasure().getValue();
		}
	}

	public double getThroughputRequirement() {
		if (this.getQualityRequirement().getQualityScenario().getResponseMeasure().getMetric().getName()
				.equals("System Throughput")) {
			return this.getQualityRequirement().getQualityScenario().getResponseMeasure().getValue();
		} else {
			// TODO corregir
			return 0.01;
		}
	}

	public double getTurnaroundTimeRequirement() {
		if (this.getQualityRequirement().getQualityScenario().getResponseMeasure().getMetric().getName()
				.equals("System Turnaround Time")) {
			return this.getQualityRequirement().getQualityScenario().getResponseMeasure().getValue();
		} else {
			// TODO corregir
			return 0.01;
		}
	}

	public double convertValueAcordingToUnitRequirement(double pvalue, Unit punit) {
		double pvalueConvert = 0;
		if (punit == this.getUnitRequirement()) {
			pvalueConvert = pvalue;
		} else {
			if (this.getUnitRequirement().getName().equals("Minutes")) {
				if (punit.getName().equals("Seconds")) {
					pvalueConvert = pvalue / 60;
				} else if (punit.getName().equals("Hours")) {
					pvalueConvert = pvalue * 60;
				} else if (punit.getName().equals("Milliseconds")) {
					pvalueConvert = (pvalue / 60) / 1000;
				}
			} else if (this.getUnitRequirement().getName().equals("Seconds")) {
				if (punit.getName().equals("Minutes")) {
					pvalueConvert = pvalue * 60;
				} else if (punit.getName().equals("Hours")) {
					pvalueConvert = pvalue * 3600;
				} else if (punit.getName().equals("Milliseconds")) {
					pvalueConvert = pvalue / 1000;
				}
			} else if (this.getUnitRequirement().getName().equals("Hours")) {
				if (punit.getName().equals("Minutes")) {
					pvalueConvert = pvalue / 60;
				} else if (punit.getName().equals("Seconds")) {
					pvalueConvert = pvalue / 3600;
				} else if (punit.getName().equals("Milliseconds")) {
					pvalueConvert = (pvalue / 3600) / 1000;
				}
			} else if (this.getUnitRequirement().getName().equals("Milliseconds")) {
				if (punit.getName().equals("Minutes")) {
					pvalueConvert = pvalue * 60000;
				} else if (punit.getName().equals("Seconds")) {
					pvalueConvert = pvalue * 1000;
				} else if (punit.getName().equals("Hours")) {
					pvalueConvert = pvalue * 3600000;
				}
			}
		}
		return pvalueConvert;
	}

	public Unit getUnitRequirement() {
		return this.getQualityRequirement().getQualityScenario().getResponseMeasure().getUnit();
	}

	public List<SystemReliability> listSystemReliability() {

		List<SystemReliability> list = new ArrayList<SystemReliability>();

		Architecture f = this.getArchitecture();
		int runNum = 1;
		for (Iterator<Run> its = f.getSimulator().getRuns().iterator(); its.hasNext();) {
			Run r = its.next();
			for (Iterator<Indicator> iti = r.getIndicators().iterator(); iti.hasNext();) {
				Indicator ind = iti.next();
				if (ind.getMetric().getName().equals("System Failures")) {
					SystemReliability item = new SystemReliability();
					item.setFailsE(this.getQualityRequirement().getQualityScenario().getResponseMeasure().getValue());
					item.setFails(ind.getValue());
					item.setSystem(ind.getType().getName());
					item.setRun(Integer.toString(runNum));
					list.add(item);
				}
			}
			runNum++;
		}
		return list;
	}

	public List<Double> listResultSimulation() {
		List<Double> lista = new ArrayList<Double>();

		for (int i = 0; i < 10; i++) {
			Double item = new Double(9.8);
			lista.add(item);
		}

		return lista;
	}

	public List<SystemPerformance> listSystemPerformance() {

		List<SystemPerformance> list = new ArrayList<SystemPerformance>();

		Architecture f = this.getArchitecture();
		int runNum = 1;
		for (Iterator<Run> its = f.getSimulator().getRuns().iterator(); its.hasNext();) {
			Run r = its.next();
			boolean band = true;
			for (Iterator<Indicator> iti = r.getIndicators().iterator(); iti.hasNext();) {
				Indicator ind = iti.next();
				if (ind.getType() instanceof SystemIndicator && band) {
					SystemPerformance item = new SystemPerformance();
					item.setThroughputE(this.getThroughputRequirement());
					item.setTurnaroundTimeE(this.getTurnaroundTimeRequirement());
					item.setSystem(ind.getType().getName());
					item.setRun(Integer.toString(runNum));
					list.add(item);
					band = false;
				}
				if (ind.getMetric().getName().equals("System Throughput")) {
					SystemPerformance q = list.get(runNum - 1);
					q.setThroughput(this.convertValueAcordingToUnitRequirement(ind.getValue(), ind.getUnit()));
				}
				if (ind.getMetric().getName().equals("System Turnaround Time")) {
					SystemPerformance q = list.get(runNum - 1);
					q.setTurnaroundTime(this.convertValueAcordingToUnitRequirement(ind.getValue(), ind.getUnit()));
				}
			}

			runNum++;
		}
		return list;
	}

	public double getSimulationTime(){
		Iterator<Run> its = this.getArchitecture().getSimulator().getRuns().iterator(); 
		Run r = its.next();
		return r.getSimulationHorizon();
	}

	public Set<QualityRequirement> getQualityRequirements() {
		return this.getSystem().getQualityRequirements();
	}

	/**
	 * 
	 * @return ComboBoxModel with qualityAttribute names
	 */
	public QualityAttribute[] getComboModelIndicator() {
		ArrayList<QualityAttribute> qualityAttributes = new ArrayList<QualityAttribute>();
		for (QualityAttribute auxTipo : this.listIndicators()) {
			System.out.println(auxTipo);
			qualityAttributes.add(auxTipo);
		}
		QualityAttribute[] arrayQualityAttribute = new QualityAttribute[qualityAttributes.size()];
		qualityAttributes.toArray(arrayQualityAttribute);
		return arrayQualityAttribute;
	}

	/**
	 * 
	 * @return List<QualityAttribute> with the names of the quality attributes
	 */
	public List<QualityAttribute> listIndicators() {
		return this.listClass(QualityAttribute.class, "name");
	}

	public DomainModel.AnalysisEntity.System[] getComboModelSystemWithRequirements() { // NOPMD
		// by
		// Usuario-Pc
		// on
		// 10/06/16
		// 21:41
		ArrayList<DomainModel.AnalysisEntity.System> systems = new ArrayList<DomainModel.AnalysisEntity.System>();
		for (DomainModel.AnalysisEntity.System auxTipo : this.listSystem()) {
			if (auxTipo.getQualityRequirements().isEmpty() == false) {
				Iterator it = auxTipo.getQualityRequirements().iterator();
				boolean i = true;
				while (it.hasNext() && i) {
					QualityRequirement q = (QualityRequirement) it.next();
					if (q.isState()) {
						systems.add(auxTipo);
						i = false;
					}
				}
			}
		}
		DomainModel.AnalysisEntity.System[] arraySystem = new DomainModel.AnalysisEntity.System[systems.size()];
		systems.toArray(arraySystem);
		return arraySystem;
	}

	/**
	 * 
	 * @return True if there are systems whose state==true,
	 *         qualityRequirement!=empty and qualityRequirement.state==true,
	 *         else return false
	 * 
	 */
	public boolean existSystemTrueWithQualityRequirementTrue() {
		for (DomainModel.AnalysisEntity.System auxTipo : this.listSystem()) {
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

	public boolean existSystemTrueWithArchitecture() {
		for (DomainModel.AnalysisEntity.System auxTipo : this.listSystem()) {
			if (auxTipo.getArchitectures().isEmpty() == false) {
				return true;
			}
		}
		return false;
	}

	public Boolean createReport(String path, String title, Collection pdata, Boolean isUnit, boolean isCumplimentRequirement) {
		try {
			ireport report = new ireport();

			report.setArchive(path);

			report.addParameter("tituloMembrete", "Reportes");
			report.addParameter("tituloMembrete2", "ttt");
			report.addParameter("frase", "");
			report.addParameter("pieMembrete", "");
			report.addParameter("title", title);
			report.addParameter("title", title);
			if (isUnit) {
				report.addParameter("unit",
						this.getQualityRequirement().getQualityScenario().getResponseMeasure().getUnit().getName());
			}
			if (!isCumplimentRequirement){
				report.addParameter("tactics", "The requirement hasn't been completed. You can apply the next tactics:\n"+this.getTactics());
			}else {
				report.addParameter("tactics", "The requirement has been completed.");
			}
			report.setDataCollection(pdata);

			report.print();
			report.visibleReport();
			return true;
		} catch (Exception e) {
			System.err.println(e);
			return false;
		}

	}

	// TODO ver si es mejor ubicarlo en el manager
	public String getTactics() {
		QualityAttribute q = this.getQualityAttribute();
		String tactics = "";
		Iterator t = q.getTactics().iterator();
		while (t.hasNext()) {
			Tactic tc = (Tactic) t.next();
			tactics = tactics + "- " + tc.getName() + "\n";
		}
		return tactics;
	}

}
