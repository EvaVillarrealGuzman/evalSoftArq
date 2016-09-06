package software.DomainModel.SoftwareArchitectureSpecificationEntity;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * This class defines the path element: ANDFork
 * 
 * @author: FEM
 * @version: 06/09/2016
 */

@Entity
@Table(name = "ANDFORK")
@PrimaryKeyJoinColumn(name = "id")
public class ANDFork extends Fork {

}
