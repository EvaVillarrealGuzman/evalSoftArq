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
@Table(name = "ARCHITECTURE")
public class Architecture implements Comparable {

	// Attributes
	@Id
	@GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
	private int id;

	private String name;
	private String description;
	private Double version;

	// VER QUE TAMBIÉN PUEDE SER CERO
	@OneToMany(targetEntity = SingleComponent.class, cascade = CascadeType.ALL)
	private Set<SingleComponent> singleComponents = new HashSet<SingleComponent>();

	// VER QUE TAMBIÉN PUEDE SER CERO
	@OneToMany(targetEntity = CompoundComponent.class, cascade = CascadeType.ALL)
	private Set<CompoundComponent> compoundComponents = new HashSet<CompoundComponent>();

	// CompareTo
	@Override
	public int compareTo(Object p) {
		Architecture t = (Architecture) p;
		return this.toString().compareTo(t.toString());
	}

}
