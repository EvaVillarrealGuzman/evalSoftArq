package software.BusinessLogic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import software.DataManager.HibernateManager;
import software.DomainModel.AnalysisEntity.QualityAttribute;
import software.DomainModel.AnalysisEntity.QualityRequirement;
import software.DomainModel.SoftwareArchitectureSpecificationEntity.Architecture;

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
	private software.DomainModel.AnalysisEntity.System system;
	private Architecture architecture;
	
	/**
	 * Getters and Setters
	 */
	public void setSystem(software.DomainModel.AnalysisEntity.System psystem) {
		this.system = psystem;
	}

	public software.DomainModel.AnalysisEntity.System getSystem() {
		return this.system;
	}
	
	public Architecture getArchitecture() {
		return architecture;
	}

	public void setArchitecture(Architecture architecture) {
		this.architecture = architecture;
	}

	/**
	 * 
	 * @return True if there are systems whose state==true, else return false
	 * 
	 */
	public boolean existSystemTrue() {
		for (software.DomainModel.AnalysisEntity.System auxTipo : this.listSystem()) {
			Iterator it = auxTipo.getArchitectures().iterator();
			if(it.hasNext()){
				Architecture q = (Architecture) it.next();
				if (q.getSimulator()!=null){
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
	public software.DomainModel.AnalysisEntity.System[] getComboModelSystemWithSimulations() { 
		ArrayList<software.DomainModel.AnalysisEntity.System> systems = new ArrayList<software.DomainModel.AnalysisEntity.System>();
		for (software.DomainModel.AnalysisEntity.System auxTipo : this.listSystem()) {
			Iterator it = auxTipo.getArchitectures().iterator();
			if(it.hasNext()){
				Architecture q = (Architecture) it.next();
				if (q.getSimulator()!=null){
					systems.add(auxTipo);
				}
			}
		}
		software.DomainModel.AnalysisEntity.System[] arraySystem = new software.DomainModel.AnalysisEntity.System[systems
				.size()];
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
	
	public void updateSystem() {
		this.updateObject(this.getSystem());
	}
	
	public ArrayList<String> getPathUCMs() {
		Iterator it = this.getSystem().getArchitectures().iterator();
		if (it.hasNext()){
			Architecture a = (Architecture) it.next();
			return a.getPathUCMs();
		} else {
			return null;
		}
	}
	
	public void setPathUCMs(ArrayList<String> pathUCMs) {
		Iterator it = this.getSystem().getArchitectures().iterator();
		if (it.hasNext()){
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

}
