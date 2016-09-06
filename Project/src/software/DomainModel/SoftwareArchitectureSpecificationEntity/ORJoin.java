package software.DomainModel.SoftwareArchitectureSpecificationEntity;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * This class defines the path element: ORJoin
 * 
 * @author: FEM
 * @version: 06/09/2016
 */
@Entity
@Table(name = "ORJOIN")
@PrimaryKeyJoinColumn(name = "id")
public class ORJoin extends Join {

}
