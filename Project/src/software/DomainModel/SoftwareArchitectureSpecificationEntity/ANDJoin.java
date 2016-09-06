package software.DomainModel.SoftwareArchitectureSpecificationEntity;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "ANDJOIN")
@PrimaryKeyJoinColumn(name = "id")
public class ANDJoin extends Join {

}
