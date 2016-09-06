package software.DomainModel.SoftwareArchitectureSpecificationEntity;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "STARTPOINT")
@PrimaryKeyJoinColumn(name = "id")
public class StartPoint extends PathElement {

}
