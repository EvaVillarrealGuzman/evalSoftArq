package software.DomainModel.SoftwareArchitectureSpecificationEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;

@Entity
@Inheritance(strategy = javax.persistence.InheritanceType.TABLE_PER_CLASS)
public class ArchitectureElement {

	@Id
	@GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
	protected int id;
}
