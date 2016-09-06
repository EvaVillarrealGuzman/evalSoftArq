package software.DomainModel.SoftwareArchitectureSpecificationEntity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * This class defines the paths for an specific architecture view
 *  
 * @author: FEM
 * @version: 06/09/2016
 */

@Entity
@Table(name = "PATH")
public class Path {
	@Id
	@GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
	private int id;

	@OneToMany(targetEntity = PathElement.class, cascade = CascadeType.ALL)
	private Set<PathElement> pathElements = new HashSet<PathElement>();
}
