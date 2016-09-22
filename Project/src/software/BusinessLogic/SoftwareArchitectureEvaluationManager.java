package software.BusinessLogic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import software.DataManager.HibernateManager;
import software.DomainModel.AnalysisEntity.Unit;
import software.DomainModel.SoftwareArchitectureSpecificationEntity.Architecture;

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
	 * @return ComboBoxModel with system names whose state==true and
	 *         architectures!=empty
	 * 
	 */
	public software.DomainModel.AnalysisEntity.System[] getComboModelSystemWithArchitecture() { 
		ArrayList<software.DomainModel.AnalysisEntity.System> systems = new ArrayList<software.DomainModel.AnalysisEntity.System>();
		for (software.DomainModel.AnalysisEntity.System auxTipo : this.listSystem()) {
			if (auxTipo.getArchitectures().isEmpty() == false) {
				systems.add(auxTipo);
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
		for (software.DomainModel.AnalysisEntity.System auxTipo : this.listSystem()) {
			if (auxTipo.getArchitectures().isEmpty() == false) {
				return true;
			}
		}
		return false;
	}
}
