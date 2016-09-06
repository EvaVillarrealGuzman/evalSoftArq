package software.DomainModel.SoftwareArchitectureSpecificationEntity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "COMPOSITECOMPONENT")
@PrimaryKeyJoinColumn(name = "id")
public class CompositeComponent extends ArchitectureElement {
	
	@OneToMany(targetEntity = ArchitectureElement.class, cascade = CascadeType.ALL)
	private Set<ArchitectureElement> internalComponent = new HashSet<ArchitectureElement>();
	
}
