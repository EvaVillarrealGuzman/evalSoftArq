package software.BusinessLogic;

import java.util.ArrayList;
import java.util.List;

import software.DataManager.HibernateManager;

/**
 * This class is responsible for the management package: Software Architecture Specification
 * 
 * @author: FEM
 * @version: 23/08/2016
 */
public class SoftwareArchitectureSpecificationManager extends HibernateManager{

	/**
	 * Attributes
	 */
	private software.DomainModel.AnalysisEntity.System system;
	
	/**
	 * Getters and Setters
	 */
	public void setSystem(software.DomainModel.AnalysisEntity.System psystem) {
		this.system = psystem;
	}

	public software.DomainModel.AnalysisEntity.System getSystem() {
		return this.system;
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
	 * @return ComboBoxModel with system names whose state==true
	 */
	public software.DomainModel.AnalysisEntity.System[] getComboModelSystem() { // NOPMD
																				// by
																				// Usuario-Pc
																				// on
																				// 10/06/16
																				// 21:42
		ArrayList<software.DomainModel.AnalysisEntity.System> systems = new ArrayList<software.DomainModel.AnalysisEntity.System>();
		for (software.DomainModel.AnalysisEntity.System auxTipo : this.listSystem()) {
			systems.add(auxTipo);
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
		return this.getSystem().getPathUCMs();
	}
	
	public void setPathUCMs(ArrayList<String> pathUCMs) {
		this.getSystem().setPathUCMs(pathUCMs);
	}
}
