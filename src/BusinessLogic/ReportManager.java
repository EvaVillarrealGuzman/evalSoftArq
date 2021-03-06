package BusinessLogic;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import DataManager.HibernateManager;
import DomainModel.AnalysisEntity.QualityAttribute;
import DomainModel.AnalysisEntity.QualityRequirement;
import DomainModel.AnalysisEntity.Tactic;
import DomainModel.AnalysisEntity.Unit;
import DomainModel.ReportsEntity.ResponsibilityAvailability;
import DomainModel.ReportsEntity.ResponsibilityPerformance;
import DomainModel.ReportsEntity.ResponsibilityReliability;
import DomainModel.ReportsEntity.SystemAvailability;
import DomainModel.ReportsEntity.SystemPerformance;
import DomainModel.ReportsEntity.SystemPerformanceThroughput;
import DomainModel.ReportsEntity.SystemPerformanceTurnaroundTime;
import DomainModel.ReportsEntity.SystemReliability;
import DomainModel.ReportsEntity.ireport;
import DomainModel.SoftwareArchitectureEvaluationEntity.Indicator;
import DomainModel.SoftwareArchitectureEvaluationEntity.ResponsabilityIndicator;
import DomainModel.SoftwareArchitectureEvaluationEntity.Run;
import DomainModel.SoftwareArchitectureEvaluationEntity.SystemIndicator;
import DomainModel.SoftwareArchitectureSpecificationEntity.Architecture;

/**
 * This class is responsible for the management package: Report
 * 
 * @author: Florencia Rossini. E-mail: flori.rossini@gmail.com
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
	private List<DomainModel.AnalysisEntity.System> listSystem() {
		return this.listClass(DomainModel.AnalysisEntity.System.class, "systemName", true);
	}

	public Set<Architecture> getArchitectures() {
		return this.getSystem().getArchitectures();
	}

	/**
	 * Return if connection with database is success
	 * 
	 * @return
	 */

	public List<ResponsibilityPerformance> listResponsibilityPerformance() {
		List<ResponsibilityPerformance> list = new ArrayList<ResponsibilityPerformance>();

		Architecture f = this.getArchitecture();
		int i = 0;
		for (Iterator<Run> its = f.getSimulator().getRuns().iterator(); its.hasNext();) {
			Run r = its.next();
			for (Iterator<Indicator> iti = r.getIndicators().iterator(); iti.hasNext();) {
				String name;
				Indicator ind = iti.next();
				if (ind.getType() instanceof ResponsabilityIndicator) {
					ResponsabilityIndicator respIndicator = (ResponsabilityIndicator) ind.getType();
					name = respIndicator.getResponsibility().getName();
				} else {
					name = ind.getType().getName();
				}
				if (ind.getMetric().getName().equals("Responsibility Turnaround Time")) {
					if (i == 0) {
						ResponsibilityPerformance item = new ResponsibilityPerformance();
						item.setResponsibilityTT(name);
						item.setTurnaroundTime(ind.getValue() / 10);
						list.add(item);
					} else {
						Iterator ite = list.iterator();
						while (ite.hasNext()) {
							ResponsibilityPerformance q = (ResponsibilityPerformance) ite.next();
							if (q.getResponsibilityTT().equals(name)) {
								q.setTurnaroundTime(q.getTurnaroundTime() + (ind.getValue() / 10));
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

		List<ResponsibilityReliability> listResponsibility = new ArrayList<ResponsibilityReliability>();
		List<Integer> runsByResponsibility = new ArrayList<Integer>();

		Architecture f = this.getArchitecture();

		for (Iterator<Run> its = f.getSimulator().getRuns().iterator(); its.hasNext();) {
			Run r = its.next();
			for (Iterator<Indicator> iti = r.getIndicators().iterator(); iti.hasNext();) {
				String name;
				Indicator ind = iti.next();
				if (ind.getType() instanceof ResponsabilityIndicator) {
					ResponsabilityIndicator respIndicator = (ResponsabilityIndicator) ind.getType();
					name = respIndicator.getResponsibility().getName();
				} else {
					name = ind.getType().getName();
				}
				if (ind.getMetric().getName().equals("Responsibility Failures")) {
					int index = 0;
					Boolean exist = false;
					Iterator ite = listResponsibility.iterator();
					while (ite.hasNext()) {
						ResponsibilityReliability q = (ResponsibilityReliability) ite.next();
						if (q.getResponsibilityF().equals(name)) {
							exist = true;
							q.setFails(q.getFails() + (ind.getValue()));
							runsByResponsibility.set(index, runsByResponsibility.get(index) + 1);
						}
						index++;
					}
					if (!exist) {
						ResponsibilityReliability item = new ResponsibilityReliability();
						item.setResponsibilityF(name);
						item.setFails(ind.getValue());
						runsByResponsibility.add(1);
						listResponsibility.add(item);
					}
				}
			}
		}
		// Divide el n?mero de fallas por la cantidad de corridas
		int index = 0;
		Iterator ite = listResponsibility.iterator();
		while (ite.hasNext()) {
			ResponsibilityReliability q = (ResponsibilityReliability) ite.next();
			q.setFails(q.getFails() / runsByResponsibility.get(index));
			index++;
		}

		return listResponsibility;
	}

	public List<ResponsibilityAvailability> listResponsibilityAvailability() {
		// TODO preguntar a vero si mismo que downtime es igual a la cantidad de
		// corridas para el otro
		List<ResponsibilityAvailability> listResponsibility = new ArrayList<ResponsibilityAvailability>();
		List<Integer> runsByResponsibility = new ArrayList<Integer>();

		Architecture f = this.getArchitecture();
		int i = 0;
		for (Iterator<Run> its = f.getSimulator().getRuns().iterator(); its.hasNext();) {
			Run r = its.next();
			for (Iterator<Indicator> iti = r.getIndicators().iterator(); iti.hasNext();) {
				String name;
				Indicator ind = iti.next();
				if (ind.getType() instanceof ResponsabilityIndicator) {
					ResponsabilityIndicator respIndicator = (ResponsabilityIndicator) ind.getType();
					name = respIndicator.getResponsibility().getName();
				} else {
					name = ind.getType().getName();
				}
				if (ind.getMetric().getName().equals("Responsibility Downtime")) {
					int index = 0;
					Boolean exist = false;
					Iterator ite = listResponsibility.iterator();
					while (ite.hasNext()) {
						ResponsibilityAvailability q = (ResponsibilityAvailability) ite.next();
						if (q.getResponsibility().equals(name)) {
							exist = true;
							q.setDowntime(q.getDowntime()
									+ this.convertValueAcordingToUnitRequirement(ind.getValue(), ind.getUnit()));
							runsByResponsibility.set(index, runsByResponsibility.get(index) + 1);
						}
						index++;
					}
					if (!exist) {
						ResponsibilityAvailability item = new ResponsibilityAvailability();
						item.setResponsibility(name);
						item.setDowntime(this.convertValueAcordingToUnitRequirement(ind.getValue(), ind.getUnit()));
						listResponsibility.add(item);
						runsByResponsibility.add(1);
						exist = false;
					}
				}
				if (ind.getMetric().getName().equals("Responsibility Recovery Time")) {
					int index = 0;
					Iterator ite = listResponsibility.iterator();
					Boolean exist = false;
					while (ite.hasNext()) {
						ResponsibilityAvailability q = (ResponsibilityAvailability) ite.next();
						if (q.getResponsibility().equals(name)) {
							exist = true;
							q.setRecoveryTime(q.getRecoveryTime()
									+ this.convertValueAcordingToUnitRequirement(ind.getValue(), ind.getUnit()));
						//	runsByResponsibility.set(index, runsByResponsibility.get(index) + 1);
						}
						index++;
					}
					if (!exist) {
						ResponsibilityAvailability item = new ResponsibilityAvailability();
						item.setResponsibility(name);
						item.setRecoveryTime(this.convertValueAcordingToUnitRequirement(ind.getValue(), ind.getUnit()));
						runsByResponsibility.add(1);
					}

				}
			}

		}
		
		// Calcula promedio
		int index = 0;
		Iterator ite = listResponsibility.iterator();
		while (ite.hasNext()) {
			ResponsibilityAvailability q = (ResponsibilityAvailability) ite.next();
			q.setDowntime(q.getDowntime() / runsByResponsibility.get(index));
			q.setRecoveryTime(q.getRecoveryTime() / runsByResponsibility.get(index));
			index++;
		}
		
		return listResponsibility;
	}

	public List<SystemAvailability> listSystemAvailability() {

		List<SystemAvailability> list = new ArrayList<SystemAvailability>();

		Architecture f = this.getArchitecture();
		int runNum = 1;
		for (Iterator<Run> its = f.getSimulator().getRuns().iterator(); its.hasNext();) {
			boolean isIndicator = false;
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
					isIndicator = true;
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

			if (isIndicator) {
				runNum++;
			}
		}
		return list;
	}

	public double getAvailabilityTimeRequirement() {
		if (this.getQualityRequirement().getQualityScenario().getResponseMeasure().getMetric().getName()
				.equals("System Availability Time")) {
			return this.getQualityRequirement().getQualityScenario().getResponseMeasure().getValue();
		} else {
			return this.getSimulationTime()
					- this.getQualityRequirement().getQualityScenario().getResponseMeasure().getValue();
		}
	}

	public double getNoAvailabilityTimeRequirement() {
		return 1.67;
		//TODO ver
		/*if (this.getQualityRequirement().getQualityScenario().getResponseMeasure().getMetric().getName()
				.equals("System No-Availability Time")) {
			return this.getQualityRequirement().getQualityScenario().getResponseMeasure().getValue();
		} else {
			return this.getSimulationTime()
					- this.getQualityRequirement().getQualityScenario().getResponseMeasure().getValue();
		}*/
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
				} else if (punit.getName().equals("Days")) {
					pvalueConvert = pvalue * 1440;
				} else if (punit.getName().equals("Weeks")) {
					pvalueConvert = pvalue * 10080;
				} else if (punit.getName().equals("Months")) {
					pvalueConvert = pvalue * 40320;
				}
			} else if (this.getUnitRequirement().getName().equals("Seconds")) {
				if (punit.getName().equals("Minutes")) {
					pvalueConvert = pvalue * 60;
				} else if (punit.getName().equals("Hours")) {
					pvalueConvert = pvalue * 3600;
				} else if (punit.getName().equals("Milliseconds")) {
					pvalueConvert = pvalue / 1000;
				} else if (punit.getName().equals("Days")) {
					pvalueConvert = pvalue * 86400;
				} else if (punit.getName().equals("Weeks")) {
					pvalueConvert = pvalue * 604800;
				} else if (punit.getName().equals("Months")) {
					pvalueConvert = pvalue * 40320 * 60;
				}
			} else if (this.getUnitRequirement().getName().equals("Hours")) {
				if (punit.getName().equals("Minutes")) {
					pvalueConvert = pvalue / 60;
				} else if (punit.getName().equals("Seconds")) {
					pvalueConvert = pvalue / 3600;
				} else if (punit.getName().equals("Milliseconds")) {
					pvalueConvert = (pvalue / 3600) / 1000;
				} else if (punit.getName().equals("Days")) {
					pvalueConvert = pvalue * 24;
				} else if (punit.getName().equals("Weeks")) {
					pvalueConvert = pvalue * 168;
				} else if (punit.getName().equals("Months")) {
					pvalueConvert = pvalue * 672;
				}
			} else if (this.getUnitRequirement().getName().equals("Milliseconds")) {
				if (punit.getName().equals("Minutes")) {
					pvalueConvert = pvalue * 60000;
				} else if (punit.getName().equals("Seconds")) {
					pvalueConvert = pvalue * 1000;
				} else if (punit.getName().equals("Hours")) {
					pvalueConvert = pvalue * 3600000;
				} else if (punit.getName().equals("Days")) {
					pvalueConvert = pvalue * 86400000;
				} else if (punit.getName().equals("Weeks")) {
					pvalueConvert = pvalue * 604800000;
				} else if (punit.getName().equals("Months")) {
					BigDecimal mil1 = new BigDecimal(pvalue);
					BigDecimal mil2 = new BigDecimal("2419200000");
					pvalueConvert = (mil1.multiply(mil2)).doubleValue();
				}
			} else if (this.getUnitRequirement().getName().equals("Days")) {
				if (punit.getName().equals("Seconds")) {
					pvalueConvert = pvalue / 86400;
				} else if (punit.getName().equals("Hours")) {
					pvalueConvert = pvalue / 24;
				} else if (punit.getName().equals("Milliseconds")) {
					pvalueConvert = pvalue / 86400000;
				} else if (punit.getName().equals("Minutes")) {
					pvalueConvert = pvalue / 1440;
				} else if (punit.getName().equals("Weeks")) {
					pvalueConvert = pvalue * 7;
				} else if (punit.getName().equals("Months")) {
					pvalueConvert = pvalue * 28;
				}
			} else if (this.getUnitRequirement().getName().equals("Weeks")) {
				if (punit.getName().equals("Seconds")) {
					pvalueConvert = pvalue / 604800;
				} else if (punit.getName().equals("Hours")) {
					pvalueConvert = pvalue / 168;
				} else if (punit.getName().equals("Milliseconds")) {
					pvalueConvert = pvalue / 604800000;
				} else if (punit.getName().equals("Days")) {
					pvalueConvert = pvalue / 7;
				} else if (punit.getName().equals("Minutes")) {
					pvalueConvert = pvalue / 10080;
				} else if (punit.getName().equals("Months")) {
					pvalueConvert = pvalue * 4;
				}
			} else if (this.getUnitRequirement().getName().equals("Months")) {
				if (punit.getName().equals("Seconds")) {
					pvalueConvert = pvalue / 2419200;
				} else if (punit.getName().equals("Hours")) {
					pvalueConvert = pvalue * 672;
				} else if (punit.getName().equals("Milliseconds")) {
					BigDecimal num1 = new BigDecimal(pvalue);
					BigDecimal num2 = new BigDecimal("2419200000");
					pvalueConvert = (num1.divide(num2)).doubleValue();
				} else if (punit.getName().equals("Days")) {
					pvalueConvert = pvalue / 28;
				} else if (punit.getName().equals("Weeks")) {
					pvalueConvert = pvalue / 4;
				} else if (punit.getName().equals("Minutes")) {
					pvalueConvert = pvalue * 40320;
				}
			} else if (this.getUnitRequirement().getName().equals("Request/Hour")) {
				if (punit.getName().equals("Request/Day")) {
					pvalueConvert = pvalue / 24;
				} else if (punit.getName().equals("Request/Week")) {
					pvalueConvert = pvalue / 168;
				} else if (punit.getName().equals("Request/Month")) {
					pvalueConvert = pvalue / 672;
				}
			} else if (this.getUnitRequirement().getName().equals("Request/Day")) {
				if (punit.getName().equals("Request/Hour")) {
					pvalueConvert = pvalue * 24;
				} else if (punit.getName().equals("Request/Week")) {
					pvalueConvert = pvalue / 7;
				} else if (punit.getName().equals("Request/Month")) {
					pvalueConvert = pvalue / 28;
				}
			} else if (this.getUnitRequirement().getName().equals("Request/Week")) {
				if (punit.getName().equals("Request/Hour")) {
					pvalueConvert = pvalue * 168;
				} else if (punit.getName().equals("Request/Day")) {
					pvalueConvert = pvalue * 7;
				} else if (punit.getName().equals("Request/Month")) {
					pvalueConvert = pvalue / 4;
				}
			} else if (this.getUnitRequirement().getName().equals("Request/Month")) {
				if (punit.getName().equals("Request/Hour")) {
					pvalueConvert = pvalue * 672;
				} else if (punit.getName().equals("Request/Day")) {
					pvalueConvert = pvalue * 28;
				} else if (punit.getName().equals("Request/Week")) {
					pvalueConvert = pvalue * 4;
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
			boolean isIndicator = false;
			for (Iterator<Indicator> iti = r.getIndicators().iterator(); iti.hasNext();) {
				Indicator ind = iti.next();
				if (ind.getMetric().getName().equals("System Failures")) {
					SystemReliability item = new SystemReliability();
					item.setFailsE(this.getQualityRequirement().getQualityScenario().getResponseMeasure().getValue());
					item.setFails(ind.getValue());
					item.setSystem(ind.getType().getName());
					item.setRun(Integer.toString(runNum));
					isIndicator = true;
					list.add(item);
				}
			}
			if (isIndicator) {
				runNum++;
			}
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
			boolean isIndicator = false;
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
					isIndicator = true;
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
			if (isIndicator) {
				runNum++;
			}
		}
		return list;
	}

	public List<SystemPerformanceTurnaroundTime> listSystemPerformanceTurnaroundTime() {

		List<SystemPerformanceTurnaroundTime> list = new ArrayList<SystemPerformanceTurnaroundTime>();

		Architecture f = this.getArchitecture();
		int runNum = 1;
		for (Iterator<Run> its = f.getSimulator().getRuns().iterator(); its.hasNext();) {
			boolean isIndicator = false;
			Run r = its.next();
			boolean band = true;

			for (Iterator<Indicator> iti = r.getIndicators().iterator(); iti.hasNext();) {
				Indicator ind = iti.next();
				if (ind.getType() instanceof SystemIndicator && band) {
					SystemPerformanceTurnaroundTime item = new SystemPerformanceTurnaroundTime();
					item.setTurnaroundTimeE(this.getTurnaroundTimeRequirement());
					item.setSystem(ind.getType().getName());
					item.setRun(Integer.toString(runNum));
					isIndicator = true;
					list.add(item);
					band = false;
				}
				if (ind.getMetric().getName().equals("System Turnaround Time")) {
					SystemPerformanceTurnaroundTime q = list.get(runNum - 1);
					q.setTurnaroundTime(this.convertValueAcordingToUnitRequirement(ind.getValue(), ind.getUnit()));
				}
			}
			if (isIndicator) {
				runNum++;
			}
		}
		return list;
	}

	public List<SystemPerformanceThroughput> listSystemPerformanceThroughput() {

		List<SystemPerformanceThroughput> list = new ArrayList<SystemPerformanceThroughput>();

		Architecture f = this.getArchitecture();
		int runNum = 1;
		for (Iterator<Run> its = f.getSimulator().getRuns().iterator(); its.hasNext();) {
			boolean isIndicator = false;
			Run r = its.next();
			boolean band = true;

			for (Iterator<Indicator> iti = r.getIndicators().iterator(); iti.hasNext();) {
				Indicator ind = iti.next();
				if (ind.getType() instanceof SystemIndicator && band) {
					SystemPerformanceThroughput item = new SystemPerformanceThroughput();
					item.setThroughputE(this.getThroughputRequirement());
					item.setSystem(ind.getType().getName());
					item.setRun(Integer.toString(runNum));
					isIndicator = true;
					list.add(item);
					band = false;
				}
				if (ind.getMetric().getName().equals("System Throughput")) {
					SystemPerformanceThroughput q = list.get(runNum - 1);
					q.setThroughput(this.convertValueAcordingToUnitRequirement(ind.getValue(), ind.getUnit()));
				}
			}
			if (isIndicator) {
				runNum++;
			}
		}
		return list;
	}

	private double getSimulationTime() {
		Iterator<Run> its = this.getArchitecture().getSimulator().getRuns().iterator();
		Run r = its.next();
		return r.getSimulationHorizon();
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

	public Boolean createReport(String path, String title, Collection pdata, Boolean isUnit,
			boolean isCumplimentRequirement) {
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
			if (!isCumplimentRequirement) {
				report.addParameter("tactics",
						"The requirement hasn't been completed. You can apply the next tactics:\n" + this.getTactics());
			} else {
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

	private String getTactics() {
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
