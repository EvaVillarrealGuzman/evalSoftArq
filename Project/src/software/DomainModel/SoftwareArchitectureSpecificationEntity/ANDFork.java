package software.DomainModel.SoftwareArchitectureSpecificationEntity;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "ANDFORK")
@PrimaryKeyJoinColumn(name = "id")
public class ANDFork extends Fork {

}
