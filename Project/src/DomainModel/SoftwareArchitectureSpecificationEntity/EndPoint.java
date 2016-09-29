package DomainModel.SoftwareArchitectureSpecificationEntity;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * This class defines the path element: EndPoint
 * 
 * @author: FEM
 * @version: 06/09/2016
 */

@Entity
@Table(name = "ENDPOINT")
@PrimaryKeyJoinColumn(name = "id")
public class EndPoint extends PathElement {

	public EndPoint(String name) {
		super(name);
	}
}
