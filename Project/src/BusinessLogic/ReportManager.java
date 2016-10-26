package BusinessLogic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jface.viewers.IStructuredSelection;

import Configuration.DatabaseConnection;
import DataManager.HibernateManager;
import DataManager.HibernateUtil;
import DomainModel.AnalysisEntity.QualityAttribute;
import DomainModel.AnalysisEntity.QualityRequirement;
import DomainModel.ReportsEntity.ClientesPorLocalidad;
import DomainModel.ReportsEntity.ResponsibilityDowntime;
import DomainModel.ReportsEntity.ResponsibilityFailures;
import DomainModel.ReportsEntity.ResponsibilityRecoveryTime;
import DomainModel.ReportsEntity.ResponsibilityTurnaroundTime;
import DomainModel.SoftwareArchitectureEvaluationEntity.Indicator;
import DomainModel.SoftwareArchitectureEvaluationEntity.Run;
import DomainModel.SoftwareArchitectureSpecificationEntity.Architecture;
import Presentation.controllerReports.DataSourceCollection;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

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
	private DataSourceCollection dataSource;
	private JasperReport masterReport;
	private JasperPrint jasperPrint;
	private Map parameters;
	private String archive;
	private DatabaseConnection db;

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

	public Architecture getArchitecture() {
		return architecture;
	}

	public void setArchitecture(Architecture architecture) {
		this.architecture = architecture;
	}

	public Map getParameters() {
		if (parameters == null) {
			parameters = new HashMap();
		}
		return parameters;
	}

	public void setParameters(Map parameters) {
		this.parameters = parameters;
	}

	public String getArchive() {
		return archive;
	}

	public void setArchive(String archive) {
		this.archive = archive;
	}

	public JasperReport getMasterReport() {
		return masterReport;
	}

	public void setMasterReport(JasperReport masterReport) {
		this.masterReport = masterReport;
	}

	public JasperPrint getJasperPrint() {
		return jasperPrint;
	}

	public void setJasperPrint(JasperPrint jasperPrint) {
		this.jasperPrint = jasperPrint;
	}

	public DataSourceCollection getDataSource() {
		if (dataSource == null) {
			dataSource = new DataSourceCollection();
		}
		return dataSource;
	}

	public void setDataSource(DataSourceCollection dataSource) {
		this.dataSource = dataSource;
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
		for (DomainModel.AnalysisEntity.System auxTipo : this.listSystem()) {
			Iterator it = auxTipo.getArchitectures().iterator();
			if (it.hasNext()) {
				Architecture q = (Architecture) it.next();
				if (q.getSimulator() != null) {
					systems.add(auxTipo);
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

	public ArrayList<String> getPathUCMs() {
		Iterator it = this.getSystem().getArchitectures().iterator();
		if (it.hasNext()) {
			Architecture a = (Architecture) it.next();
			return a.getPathUCMs();
		} else {
			return null;
		}
	}

	public void setPathUCMs(ArrayList<String> pathUCMs) {
		Iterator it = this.getSystem().getArchitectures().iterator();
		if (it.hasNext()) {
			Architecture a = (Architecture) it.next();
			a.setPathUCMs(pathUCMs);
		} else {
			Architecture pa = new Architecture(pathUCMs);
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

	/**
	 * Agrega un parametro al reporte, este parametro debe coincidir con los
	 * parametros previamente creados en el reporte
	 * 
	 * @param nombreParametro
	 *            nombre del parametro definido en el reporte
	 * @param valorParametro
	 *            Object con el valor del parametro
	 */
	public void addParameter(String pparamenterName, Object pparameterValue) {
		getParameters().put(pparamenterName, pparameterValue);
	}

	/**
	 * Setea la coleccion de datos del reporte, esta coleccion debe contener
	 * javaBeans que coincidan sus atributos, encapsulados por sus accesores,
	 * con los fields usados en el reporte
	 * 
	 * @param datos
	 */
	public void setDataCollection(Collection pdata) {
		DataSourceCollection.setColeccionDeDatos(pdata);
	}

	/**
	 * Lanza un hilo con el metodo run() donde arma el reporte con los
	 * parametros, el archivo jasper seleccionado en el constructor y la fuente
	 * de datos mencionada anteriormente. Una vez listo el reporte instancia un
	 * visor y lo muestra.
	 */
	public void print() {
		try {
			JRDataSource jrd = null;
			if (this.getJasperPrint() == null) {
				setMasterReport((JasperReport) JRLoader.loadObject(this.archive));
				try {
					jrd = this.getDataSource().createBeanCollectionDatasource();
				} catch (Exception ek) {
					System.out.println("error 1");
					ek.printStackTrace();
				}
				System.out.println("parametros=" + this.getParameters().toString());
				System.out.println("fuenteDeDatos: cantidad=" + DataSourceCollection.getColeccionDeDatos().size() + " "
						+ DataSourceCollection.getColeccionDeDatos().toString());
				jasperPrint = JasperFillManager.fillReport(this.getMasterReport(), this.getParameters(), jrd);
			}
			JasperViewer jviewer = new JasperViewer(this.getJasperPrint(), false);
			jviewer.setTitle("Report");
			jviewer.setVisible(true);
		} catch (Exception e) {
			System.out.println(" " + e);
		}

	}

	public List<ClientesPorLocalidad> listClientesPorLocalidad() {
		List<ClientesPorLocalidad> lista = new ArrayList<ClientesPorLocalidad>();

		for (int i = 0; i < 10; i++) {
			ClientesPorLocalidad item = new ClientesPorLocalidad();
			item.setLocalidad("hola");
			item.setCantidadClientes(5);
			lista.add(item);
		}

		return lista;
	}

	public List<ResponsibilityTurnaroundTime> listResponsibilityTurnaroundTime() {

		List<ResponsibilityTurnaroundTime> list = new ArrayList<ResponsibilityTurnaroundTime>();

		for (Iterator<Architecture> it = this.getSystem().getArchitectures().iterator(); it.hasNext();) {
			Architecture f = it.next();
			for (Iterator<Run> its = f.getSimulator().getRuns().iterator(); its.hasNext();) {
				Run r = its.next();
				for (Iterator<Indicator> iti = r.getIndicators().iterator(); iti.hasNext();) {
					Indicator ind = iti.next();
					if (ind.getMetric().getName().equals("Responsibility Turnaround Time")) {
						ResponsibilityTurnaroundTime item = new ResponsibilityTurnaroundTime();
						item.setResponsibility(ind.getType().getName());
						item.setTurnaroundTime(ind.getValue());
						list.add(item);
					}
				}
			}
		}
		return list;
	}

	public List<ResponsibilityFailures> listResponsibilityFailures() {

		List<ResponsibilityFailures> list = new ArrayList<ResponsibilityFailures>();

		for (Iterator<Architecture> it = this.getSystem().getArchitectures().iterator(); it.hasNext();) {
			Architecture f = it.next();
			for (Iterator<Run> its = f.getSimulator().getRuns().iterator(); its.hasNext();) {
				Run r = its.next();
				for (Iterator<Indicator> iti = r.getIndicators().iterator(); iti.hasNext();) {
					Indicator ind = iti.next();
					if (ind.getMetric().getName().equals("Responsibility Failures")) {
						ResponsibilityFailures item = new ResponsibilityFailures();
						item.setResponsibility(ind.getType().getName());
						item.setFails(ind.getValue());
						list.add(item);
					}
				}
			}
		}
		return list;	
	}
	
	public List<ResponsibilityDowntime> listResponsibilityDowntime() {

		List<ResponsibilityDowntime> list = new ArrayList<ResponsibilityDowntime>();

		for (Iterator<Architecture> it = this.getSystem().getArchitectures().iterator(); it.hasNext();) {
			Architecture f = it.next();
			for (Iterator<Run> its = f.getSimulator().getRuns().iterator(); its.hasNext();) {
				Run r = its.next();
				for (Iterator<Indicator> iti = r.getIndicators().iterator(); iti.hasNext();) {
					Indicator ind = iti.next();
					if (ind.getMetric().getName().equals("Responsibility Downtime")) {
						ResponsibilityDowntime item = new ResponsibilityDowntime();
						item.setResponsibility(ind.getType().getName());
						item.setDowntime(ind.getValue());
						list.add(item);
					}
				}
			}
		}
		return list;	
	}
	
	public List<ResponsibilityRecoveryTime> listResponsibilityRecoveryTime() {

		List<ResponsibilityRecoveryTime> list = new ArrayList<ResponsibilityRecoveryTime>();

		for (Iterator<Architecture> it = this.getSystem().getArchitectures().iterator(); it.hasNext();) {
			Architecture f = it.next();
			for (Iterator<Run> its = f.getSimulator().getRuns().iterator(); its.hasNext();) {
				Run r = its.next();
				for (Iterator<Indicator> iti = r.getIndicators().iterator(); iti.hasNext();) {
					Indicator ind = iti.next();
					if (ind.getMetric().getName().equals("Responsibility Recovery Time")) {
						ResponsibilityRecoveryTime item = new ResponsibilityRecoveryTime();
						item.setResponsibility(ind.getType().getName());
						item.setRecoveryTime(ind.getValue());
						list.add(item);
					}
				}
			}
		}
		return list;	
	}

	public List<Double> listResultSimulation() {
		List<Double> lista = new ArrayList<Double>();

		for (int i = 0; i < 10; i++) {
			Double item = new Double(9.8);
			// item.setLocalidad("hola");
			// item.setCantidadClientes(5);
			lista.add(item);
		}

		return lista;
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

}
