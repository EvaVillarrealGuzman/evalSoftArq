package software.DomainModel.SoftwareArchitectureSpecificationEntity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "SIMPLECOMPONENT")
@PrimaryKeyJoinColumn(name = "id")
public class SimpleComponent extends ArchitectureElement {

	@OneToMany(targetEntity = Responsability.class, cascade = CascadeType.ALL)
	private Set<Responsability> responsabilities = new HashSet<Responsability>();
}
