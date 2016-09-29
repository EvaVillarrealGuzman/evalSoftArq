package BusinessLogic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import DataManager.HibernateManager;
import DomainModel.AnalysisEntity.Unit;
import DomainModel.SoftwareArchitectureSpecificationEntity.Architecture;

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
		DomainModel.AnalysisEntity.System[] arraySystem = new DomainModel.AnalysisEntity.System[systems
				.size()];
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
}
