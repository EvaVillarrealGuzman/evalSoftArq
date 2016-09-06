package software.DomainModel.SoftwareArchitectureSpecificationEntity;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "ENDPOINT")
@PrimaryKeyJoinColumn(name = "id")
public class EndPoint extends PathElement {

}
