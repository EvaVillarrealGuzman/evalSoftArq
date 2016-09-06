package software.DomainModel.SoftwareArchitectureSpecificationEntity;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "ORFORK")
@PrimaryKeyJoinColumn(name = "id")
public class ORFork extends Fork {

}
