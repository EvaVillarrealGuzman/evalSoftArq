package BusinessLogic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.Platform;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import Configuration.DatabaseConnection;
import DataManager.HibernateManager;
import DataManager.HibernateUtil;
import DomainModel.AnalysisEntity.Metric;
import DomainModel.AnalysisEntity.QualityRequirement;
import DomainModel.AnalysisEntity.Unit;
import DomainModel.SoftwareArchitectureEvaluationEntity.Indicator;
import DomainModel.SoftwareArchitectureEvaluationEntity.ResponsabilityIndicator;
import DomainModel.SoftwareArchitectureEvaluationEntity.Run;
import DomainModel.SoftwareArchitectureEvaluationEntity.Simulator;
import DomainModel.SoftwareArchitectureEvaluationEntity.SystemIndicator;
import DomainModel.SoftwareArchitectureSpecificationEntity.Architecture;
import DomainModel.SoftwareArchitectureSpecificationEntity.Responsibility;
import DomainModel.SoftwareArchitectureSpecificationEntity.SpecificationParameter;
import Main.TransformerSimulator;

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
	private DatabaseConnection db;
	private TransformerSimulator pluginTS;

	/**
	 * Getters and Setters
	 */
	public DatabaseConnection getDb() {
		if (db == null) {
			synchronized (DatabaseConnection.class) {
				db = new DatabaseConnection();
			}
		}
		return db;
	}

	public void setDb(DatabaseConnection db) {
		this.db = db;
	}

	public void setSystem(DomainModel.AnalysisEntity.System psystem) {
		this.system = psystem;
	}

	public DomainModel.AnalysisEntity.System getSystem() {
		return this.system;
	}

	public TransformerSimulator getPluginTS() {
		if (pluginTS == null) {
			pluginTS = new TransformerSimulator();
		}
		return pluginTS;
	}

	public void setPluginTS(TransformerSimulator pluginTS) {
		this.pluginTS = pluginTS;
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

	public Set<QualityRequirement> getQualityRequirements() {
		return this.getSystem().getQualityRequirements();
	}

	public boolean existSystemTrueWithArchitecture() {
		for (DomainModel.AnalysisEntity.System auxTipo : this.listSystem()) {
			if (auxTipo.getArchitectures().isEmpty() == false) {
				return true;
			}
		}
		return false;
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
			if (element.isResponsibility(element)) {
				Responsibility auxTipo = (Responsibility) element;
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
		ind.setUnit(this.getUnitIndicator());
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

	public Unit getUnitIndicator() {
		Responsibility[] r = this.getResponsibilities();
		Iterator it = r[0].getSpecificationParameter().iterator();
		if (it.hasNext()) {
			SpecificationParameter sp = (SpecificationParameter) it.next();
			return sp.getUnit();
		}
		return null;
	}

	public void loadResponsabilityIndicator(String[] pfields) {
		Indicator ind = new Indicator();
		ResponsabilityIndicator type = new ResponsabilityIndicator(pfields[0]);
		type.setResponsibility(this.getResponsability(pfields[0]));
		this.saveObject(type);
		ind.setType(type);
		ind.setValue(Double.parseDouble(pfields[3]));
		ind.setUnit(this.getUnitIndicator());
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
	
	public double convertValueAcordingToUnit(double pvalue, Unit punit){
		double pvalueConvert = 0;
		if (punit == this.getUnitIndicator()){
			pvalueConvert = pvalue;
		} else {
			if (this.getUnitIndicator().getName().equals("Minutes")){
				if (punit.getName().equals("Seconds")){
					pvalueConvert = pvalue/60;
				} else if (punit.getName().equals("Hours")){
					pvalueConvert = pvalue*60;
				}
			}else if (this.getUnitIndicator().getName().equals("Seconds")){
				if (punit.getName().equals("Minutes")){
					pvalueConvert = pvalue*60;
				} else if (punit.getName().equals("Hours")){
					pvalueConvert = pvalue*3600;
				}
			}else if (this.getUnitIndicator().getName().equals("Hours")){
				if (punit.getName().equals("Minutes")){
					pvalueConvert = pvalue/60;
				} else if (punit.getName().equals("Seconds")){
					pvalueConvert = pvalue/3600;
				}
			}
		}
		return pvalueConvert;
	}

	public Responsibility getResponsability(String pname) {
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

	public String chequerUCM(String path) {
		return this.getPluginTS().callChequerUCM(path);
	}

	public Boolean transformer(String inputPath) {
		String outputPath = Platform.getInstallLocation().getURL().getPath() + "plugins/UCM2DEVS";
		return this.getPluginTS().callTransformer(inputPath, outputPath.substring(1, outputPath.length()));
	}

	public Boolean simulator() {
		return this.getPluginTS().callSimulator();
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

}
