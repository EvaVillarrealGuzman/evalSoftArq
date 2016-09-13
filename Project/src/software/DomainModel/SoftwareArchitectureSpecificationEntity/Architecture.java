package software.DomainModel.SoftwareArchitectureSpecificationEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import software.DomainModel.ReportsEntity.Simulator;

import javax.persistence.OneToMany;

/**
 * This class defines an architecture view for a specific system
 *  
 * @author: FEM
 * @version: 06/09/2016
 */

@Entity
@Table(name = "ARCHITECTURE")
public class Architecture {

	@Id
	@GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
	protected int id;

	@OneToMany(targetEntity = Path.class)
	private Set<Path> paths = new HashSet<Path>();
	
	@OneToMany(targetEntity = ArchitectureElement.class)
	private Set<ArchitectureElement> architectureElements = new HashSet<ArchitectureElement>();
	
	@OneToOne(targetEntity = Simulator.class)
	private Simulator simulator;

	private ArrayList<String> pathUCMs;

	public Architecture() {
		
	}
	
	public Architecture(ArrayList<String> ppathUCMs) {
		this.pathUCMs = ppathUCMs;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Set<Path> getPaths() {
		return paths;
	}

	public void setPaths(Set<Path> paths) {
		this.paths = paths;
	}

	public Set<ArchitectureElement> getArchitectureElements() {
		return architectureElements;
	}

	public void setArchitectureElements(Set<ArchitectureElement> architectureElements) {
		this.architectureElements = architectureElements;
	}

	public Simulator getSimulator() {
		return simulator;
	}

	public void setSimulator(Simulator simulator) {
		this.simulator = simulator;
	}

	public ArrayList<String> getPathUCMs() {
		return pathUCMs;
	}

	public void setPathUCMs(ArrayList<String> pathUCMs) {
		this.pathUCMs = pathUCMs;
	}
	
}
