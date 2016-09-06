package software.DomainModel.SoftwareArchitectureSpecificationEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SEPECIFICATIONPARAMETER")
public class SpecificationParameter {
	@Id
	@GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
	private int id;

}
