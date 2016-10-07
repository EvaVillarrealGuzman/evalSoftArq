package BusinessLogic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.text.Document;
import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TableItem;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.lucene.DocumentBuilder;

import DataManager.HibernateManager;
import DomainModel.AnalysisEntity.Metric;
import DomainModel.AnalysisEntity.QualityAttribute;
import DomainModel.AnalysisEntity.QualityRequirement;
import DomainModel.AnalysisEntity.StimulusSourceType;
import DomainModel.AnalysisEntity.Unit;
import DomainModel.ReportsEntity.Indicator;
import DomainModel.ReportsEntity.ResponsabilityIndicator;
import DomainModel.ReportsEntity.Run;
import DomainModel.ReportsEntity.Simulator;
import DomainModel.ReportsEntity.SystemIndicator;
import DomainModel.SoftwareArchitectureSpecificationEntity.Architecture;
import DomainModel.SoftwareArchitectureSpecificationEntity.Responsibility;

/**
 * This class is responsible for the management package: Software Architecture
 * Specification
 * 
 * @author: FEM
 * @version: 23/08/2016
 */
public class SoftwareArchitectureEvaluationManager extends HibernateManager {

	/**
	 * Attributes
	 */
	private DomainModel.AnalysisEntity.System system;
	public static final String SEPARATOR = ";";
	public static final String QUOTE = "\"";
	private Simulator simulator;

	/**
	 * Getters and Setters
	 */
	public void setSystem(DomainModel.AnalysisEntity.System psystem) {
		this.system = psystem;
	}

	public DomainModel.AnalysisEntity.System getSystem() {
		return this.system;
	}

	/**
	 * 
	 * @return ComboBoxModel with system names whose state==true and
	 *         architectures!=empty
	 * 
	 */
	public DomainModel.AnalysisEntity.System[] getComboModelSystemWithArchitecture() {
		ArrayList<DomainModel.AnalysisEntity.System> systems = new ArrayList<DomainModel.AnalysisEntity.System>();
		for (DomainModel.AnalysisEntity.System auxTipo : this.listSystem()) {
			if (auxTipo.getArchitectures().isEmpty() == false) {
				systems.add(auxTipo);
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

	/**
	 * 
	 * @return ComboBoxModel with units
	 * 
	 */
	public Unit[] getComboModelUnit() {
		ArrayList<Unit> units = new ArrayList<Unit>();
		for (Unit auxTipo : this.listUnit()) {
			units.add(auxTipo);
		}
		Unit[] arrayUnit = new Unit[units.size()];
		units.toArray(arrayUnit);
		return arrayUnit;
	}

	/**
	 * 
	 * @return List<Unit> with the unit names
	 */
	public List<Unit> listUnit() {
		return this.listClass(Unit.class, "name");
	}

	public Set<Architecture> getArchitectures() {
		return this.getSystem().getArchitectures();
	}

	public ArrayList<String> getPathUCMs() {
		Iterator it = this.getSystem().getArchitectures().iterator();
		if (it.hasNext()) {
			Architecture a = (Architecture) it.next();
			return a.getPathUCMs();
		} else {
			return null;
		}
	}

	public boolean existSystemTrueWithArchitecture() { // NOPMD by
		// Usuario-Pc
		// on
		// 10/06/16
		// 21:44
		for (DomainModel.AnalysisEntity.System auxTipo : this.listSystem()) {
			if (auxTipo.getArchitectures().isEmpty() == false) {
				return true;
			}
		}
		return false;
	}

	public Architecture getArchitecture() {
		Iterator it = this.getSystem().getArchitectures().iterator();
		return (Architecture) it.next();

	}

	public void createSimulator(String psimulationTime) {
		Simulator sim = new Simulator();
		Run run = new Run(GregorianCalendar.getInstance().getTime(), Double.parseDouble(psimulationTime));
		sim.getRuns().add(run);
		this.saveObject(sim);
		this.getArchitecture().setSimulator(sim);
		this.updateObject(this.getSystem());
	}

	public void convertCSVToTable(String ppath) throws IOException {
		SystemIndicator type = new SystemIndicator(this.getSystem().getSystemName());
		this.saveObject(type);
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(ppath));
			String line = br.readLine();
			while (null != line) {
				String[] fields = line.split(SEPARATOR);
				fields = removeTrailingQuotes(fields);
				loadDataInBd(fields, type);
				line = br.readLine();
			}

		} catch (Exception e) {
			System.err.println("Error! " + e.getMessage());
		} finally {
			if (null != br) {
				try {
					br.close();
				} catch (IOException e) {
					System.err.println("Error closing file !! " + e.getMessage());
				}
			}
		}
	}

	private static String[] removeTrailingQuotes(String[] fields) {
		String result[] = new String[fields.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = fields[i].replaceAll("^" + QUOTE, "").replaceAll(QUOTE + "$", "");
		}
		return result;
	}

	public void loadDataInBd(String[] pfields, SystemIndicator type) {
		if (pfields[1].equals("system")) {
			loadSystemIndicator(pfields, type);
		} else {
			loadResponsabilityIndicator(pfields);
		}
	}

	public Responsibility[] getResponsibilities() {
		ArrayList<Responsibility> responsibilities = new ArrayList<Responsibility>();
		Iterator it2 = this.getArchitecture().getPaths().iterator();
		DomainModel.SoftwareArchitectureSpecificationEntity.Path path = (DomainModel.SoftwareArchitectureSpecificationEntity.Path) it2
				.next();
		Iterator it3 = path.getPathElements().iterator();
		while (it3.hasNext()) {
			DomainModel.SoftwareArchitectureSpecificationEntity.PathElement element = (DomainModel.SoftwareArchitectureSpecificationEntity.PathElement) it3
					.next();

			if (element instanceof Responsibility) {
				Responsibility auxTipo = (Responsibility) it3.next();
				responsibilities.add(auxTipo);
			}
		}
		Responsibility[] arrayResponsibility = new Responsibility[responsibilities.size()];
		responsibilities.toArray(arrayResponsibility);
		return arrayResponsibility;
	}

	public void loadSystemIndicator(String[] pfields, SystemIndicator ptype) {
		Indicator ind = new Indicator();
		ind.setType(ptype);
		ind.setValue(Double.parseDouble(pfields[3]));
		switch (pfields[2]) {
		case "SA":
			ind.setMetric((Metric) this.listMetric("System Availability").get(0));
			break;
		case "SNA":
			ind.setMetric((Metric) this.listMetric("System No-Availability").get(0));
			break;
		case "ST":
			ind.setMetric((Metric) this.listMetric("System Throughput").get(0));
			break;
		case "STT":
			ind.setMetric((Metric) this.listMetric("System Turnaraound Time").get(0));
			break;
		case "SF":
			ind.setMetric((Metric) this.listMetric("System Failures").get(0));
			break;
		}
		this.getRun().getIndicators().add(ind);
		this.updateObject(this.getSystem());
	}
	
	public void loadResponsabilityIndicator(String[] pfields) {
		Indicator ind = new Indicator();
		ResponsabilityIndicator type = new ResponsabilityIndicator(pfields[0]);
		type.setResponsibility(this.getResponsability(pfields[0]));
		ind.setType(type);
		ind.setValue(Double.parseDouble(pfields[3]));
		switch (pfields[2]) {
		case "RDT":
			ind.setMetric((Metric) this.listMetric("Responsibility Downtime").get(0));
			break;
		case "RRT":
			ind.setMetric((Metric) this.listMetric("Responsibility Recovery Time").get(0));
			break;
		case "RTT":
			ind.setMetric((Metric) this.listMetric("Responsibility Turnaround Time").get(0));
			break;
		case "RF":
			ind.setMetric((Metric) this.listMetric("Responsibility Failures").get(0));
			break;
		}
		this.getRun().getIndicators().add(ind);
		this.updateObject(this.getSystem());
	}

	public List listMetric(String pmetric) {
		Criteria crit = getSession().createCriteria(Metric.class).add(Restrictions.eq("name", pmetric));
		return crit.list();
	}
	
	public Responsibility getResponsability(String pname){
		for (Responsibility dp : this.getResponsibilities()) {
			if (dp.getName().equals(pname)) {
				return dp;
			}
		}
		return null;
	}

	public Run getRun() {
		Iterator it = this.getArchitecture().getSimulator().getRuns().iterator();
		return (Run) it.next();
	}
}
