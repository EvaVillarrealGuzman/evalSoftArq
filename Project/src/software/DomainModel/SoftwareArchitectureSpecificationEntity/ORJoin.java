package software.DomainModel.SoftwareArchitectureSpecificationEntity;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "ORJOIN")
@PrimaryKeyJoinColumn(name = "id")
public class ORJoin extends Join {

}
