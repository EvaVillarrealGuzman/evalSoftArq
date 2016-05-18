package software.DomainModel.SoftwareArchitectureSpecificationEntity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "SINGLECOMPONENT")
public class SingleComponent implements Comparable {

	// Attributes
	@Id
	@GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
	private int id;
	
	private String name;
	
	@OneToMany(targetEntity = Responsability.class, cascade = CascadeType.ALL)
	private Set<Responsability> responsabilities = new HashSet<Responsability>();

	// CompareTo
	@Override
	public int compareTo(Object p) {
		Architecture t = (Architecture) p;
		return this.toString().compareTo(t.toString());
	}
}
