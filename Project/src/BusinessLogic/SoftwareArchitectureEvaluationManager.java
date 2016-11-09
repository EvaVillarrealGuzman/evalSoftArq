package BusinessLogic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

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
	private static final String PATHEVALUATION = Platform.getInstallLocation().getURL().getPath() + "plugins/UCM2DEVS";
	private TransformerSimulator pluginTS;
	private Simulator simulator;
	private Architecture architecture;
	private Run run;
	private SystemIndicator typeIndicator;

	/**
	 * Getters and Setters
	 */
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

	public Simulator getSimulator() {
		return simulator;
	}

	public void setSimulator(Simulator simulator) {
		this.simulator = simulator;
	}

	public Architecture getArchitecture() {
		return architecture;
	}

	public void setArchitecture(Architecture architecture) {
		this.architecture = architecture;
	}

	public Run getRun() {
		return run;
	}

	public void setRun(Run run) {
		this.run = run;
	}

	public SystemIndicator getTypeIndicator() {
		return typeIndicator;
	}

	public void setTypeIndicator(SystemIndicator typeIndicator) {
		this.typeIndicator = typeIndicator;
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

	public String getPathUCMs() {
		Iterator it = this.getSystem().getArchitectures().iterator();
		if (it.hasNext()) {
			Architecture a = (Architecture) it.next();
			return a.getPathUCM();
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

	public void createSimulator() {
		simulator = new Simulator();
		typeIndicator = new SystemIndicator(this.getSystem().getSystemName());
		this.saveObject(typeIndicator);
	}

	public void createRun(String psimulationTime, Table ptable) {
		run = new Run(GregorianCalendar.getInstance().getTime(), Double.parseDouble(psimulationTime));
		this.getSimulator().getRuns().add(run);
		this.saveObject(this.getSimulator());

		TableItem[] items = ptable.getItems();
		for (int i = 0; i < items.length; i++) {
			TableItem item = items[i];
			if (item.getChecked()) {
				this.getSimulator().getRequirements().add((QualityRequirement) item.getData());
			}
		}

		this.getArchitecture().setSimulator(this.getSimulator());
		this.updateObject(this.getSystem());
	}

	public void convertCSVToTable(String ppath) throws IOException {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(ppath));
			String line = br.readLine();
			while (null != line) {
				String[] fields = line.split(SEPARATOR);
				fields = removeTrailingQuotes(fields);
				loadDataInBd(fields, this.getTypeIndicator());
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
		System.out.println("architecture is 0: " + this.getArchitecture());
		Iterator it2 = this.getArchitecture().getPaths().iterator();
		DomainModel.SoftwareArchitectureSpecificationEntity.Path path = (DomainModel.SoftwareArchitectureSpecificationEntity.Path) it2
				.next();
		Iterator it3 = path.getPathElements().iterator();
		while (it3.hasNext()) {
			DomainModel.SoftwareArchitectureSpecificationEntity.PathElement element = (DomainModel.SoftwareArchitectureSpecificationEntity.PathElement) it3
					.next();
			if (element.isResponsibility()) {
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
		switch (pfields[2]) {
		case "SA":
			ind.setUnit(this.getUnitIndicator());
			ind.setMetric((Metric) this.listMetric("System Availability Time").get(0));
			break;
		case "SNA":
			ind.setUnit(this.getUnitIndicator());
			ind.setMetric((Metric) this.listMetric("System No-Availability Time").get(0));
			break;
		case "ST":
			ind.setUnit(this.getUnitIndicator());
			ind.setMetric((Metric) this.listMetric("System Throughput").get(0));
			break;
		case "STT":
			ind.setUnit(this.getUnitIndicator());
			ind.setMetric((Metric) this.listMetric("System Turnaround Time").get(0));
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
		type.setName(pfields[0]);
		type.setResponsibility(this.getResponsability(pfields[0]));
		this.saveObject(type);
		ind.setType(type);
		ind.setValue(Double.parseDouble(pfields[3]));

		switch (pfields[2]) {
		case "RDT":
			ind.setUnit(this.getUnitIndicator());
			ind.setMetric((Metric) this.listMetric("Responsibility Downtime").get(0));
			break;
		case "RRT":
			ind.setUnit(this.getUnitIndicator());
			ind.setMetric((Metric) this.listMetric("Responsibility Recovery Time").get(0));
			break;
		case "RTT":
			ind.setUnit(this.getUnitIndicator());
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

	public double convertValueAcordingToUnit(double pvalue, Unit punit) {
		double pvalueConvert = 0;
		if (punit == this.getUnitIndicator()) {
			pvalueConvert = pvalue;
		} else {
			if (this.getUnitIndicator().getName().equals("Minutes")) {
				if (punit.getName().equals("Seconds")) {
					pvalueConvert = pvalue / 60;
				} else if (punit.getName().equals("Hours")) {
					pvalueConvert = pvalue * 60;
				} else if (punit.getName().equals("Milliseconds")) {
					pvalueConvert = (pvalue / 60) / 1000;
				}
			} else if (this.getUnitIndicator().getName().equals("Seconds")) {
				if (punit.getName().equals("Minutes")) {
					pvalueConvert = pvalue * 60;
				} else if (punit.getName().equals("Hours")) {
					pvalueConvert = pvalue * 3600;
				} else if (punit.getName().equals("Milliseconds")) {
					pvalueConvert = pvalue / 1000;
				}
			} else if (this.getUnitIndicator().getName().equals("Hours")) {
				if (punit.getName().equals("Minutes")) {
					pvalueConvert = pvalue / 60;
				} else if (punit.getName().equals("Seconds")) {
					pvalueConvert = pvalue / 3600;
				} else if (punit.getName().equals("Milliseconds")) {
					pvalueConvert = (pvalue / 3600) / 1000;
				}
			} else if (this.getUnitIndicator().getName().equals("Milliseconds")) {
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

	public Responsibility getResponsability(String pname) {
		for (Responsibility dp : this.getResponsibilities()) {
			if (dp.getName().equals(pname)) {
				return dp;
			}
		}
		return null;
	}

	public String chequerUCM(String path) {
		return this.getPluginTS().callChequerUCM(path);
	}

	public Boolean transformer(String inputPath) {
		return this.getPluginTS().callTransformer(inputPath, PATHEVALUATION.substring(1, PATHEVALUATION.length()));
	}

	public Boolean simulator(double observe_t, Unit punit) {
		//TODO modificar
		double psimulationTime = this.convertValueAcordingToUnit(observe_t, punit);
		if (this.getPluginTS().callSimulator(PATHEVALUATION, psimulationTime)) {
			return true;
		} else {
			return false;
		}

	}

	// Delete files generate by plugin TS
	public void deleteFiles() {
		// Delete jar
		File jarFile = new File(PATHEVALUATION + "/simulator.jar");
		jarFile.delete();

		// Delete csv
		for (int i = 1; i < 11; i++) {
			File RunDirectory = new File(PATHEVALUATION + "/Run/Run" + i);

			String[] fileList;
			fileList = RunDirectory.list();
			for (int j = 0; j < fileList.length; j++) {
				File csvFile = new File(RunDirectory, fileList[j]);
				csvFile.delete();
			}
		}

		// Delete SystemTemp
		File Directory = new File(PATHEVALUATION + "/Simulator/src/SimEnvironment/SAModel/SystemTemp");

		String[] fileList;
		fileList = Directory.list();
		for (int i = 0; i < fileList.length; i++) {
			File file = new File(Directory, fileList[i]);
			file.delete();
		}
		
		//Delete test.class and simenvironment.class
		File testFile = new File(PATHEVALUATION + "/Simulator/src/Test.class");
		testFile.delete();
		
		File simEnvironmentFile = new File(PATHEVALUATION + "/Simulator/src/SimEnvironment/SimEnvironment.class");
		simEnvironmentFile.delete();
		
		
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
