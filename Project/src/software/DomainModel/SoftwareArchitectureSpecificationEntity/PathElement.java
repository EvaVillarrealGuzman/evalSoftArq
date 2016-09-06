package software.DomainModel.SoftwareArchitectureSpecificationEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;

/**
 * This class defines the pats elements
 * 
 * @author: FEM
 * @version: 06/09/2016
 */

@Entity
@Inheritance(strategy = javax.persistence.InheritanceType.TABLE_PER_CLASS)
public class PathElement {
	
	@Id
	@GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
	protected int id;
	
	private String name;
}