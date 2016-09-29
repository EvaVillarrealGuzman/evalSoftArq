package DomainModel.SoftwareArchitectureSpecificationEntity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * This class defines the architecture element: simple component
 * 
 * @author: FEM
 * @version: 06/09/2016
 */

@Entity
@Table(name = "SIMPLECOMPONENT")
@PrimaryKeyJoinColumn(name = "id")
public class SimpleComponent extends ArchitectureElement {

	@OneToMany(targetEntity = Responsibility.class, cascade = CascadeType.ALL)
	private Set<Responsibility> responsabilities = new HashSet<Responsibility>();

	public SimpleComponent(String name) {
		super(name);
	}

	public Set<Responsibility> getResponsabilities() {
		return responsabilities;
	}

	public void setResponsabilities(Set<Responsibility> responsabilities) {
		this.responsabilities = responsabilities;
	}
	
	
}
