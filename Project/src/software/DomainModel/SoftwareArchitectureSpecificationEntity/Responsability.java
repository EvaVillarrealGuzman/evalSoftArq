package software.DomainModel.SoftwareArchitectureSpecificationEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "RESPONSABILITY")
@PrimaryKeyJoinColumn(name = "id")
public class Responsability extends PathElement {
	
	@OneToOne(targetEntity = Architecture.class, cascade = CascadeType.ALL)
	private SpecificationParameter specificationParameter;
}
