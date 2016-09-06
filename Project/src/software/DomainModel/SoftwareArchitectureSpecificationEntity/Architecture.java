package software.DomainModel.SoftwareArchitectureSpecificationEntity;

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

@Entity
@Table(name = "ARCHITECTURE")
public class Architecture {

	@Id
	@GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
	protected int id;

	@OneToMany(targetEntity = Path.class, cascade = CascadeType.ALL)
	private Set<Path> paths = new HashSet<Path>();
	
	@OneToMany(targetEntity = ArchitectureElement.class, cascade = CascadeType.ALL)
	private Set<ArchitectureElement> architectureElements = new HashSet<ArchitectureElement>();
	
	@OneToOne(targetEntity = Simulator.class, cascade = CascadeType.ALL)
	private Simulator simulator;

}
