package DomainModel.SoftwareArchitectureSpecificationEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * This class defines the path element: Responsibility
 * 
 * @author: FEM
 * @version: 06/09/2016
 */

@Entity
@Table(name = "RESPONSABILITY")
//@PrimaryKeyJoinColumn(name = "id")
public class Responsibility extends PathElement {
	
	@OneToOne
	//@PrimaryKeyJoinColumn
	private SpecificationParameter specificationParameter;
	
	public Responsibility(String name) {
		super(name);
	}
	
	public SpecificationParameter getSpecificationParameter() {
		return specificationParameter;
	}

	public void setSpecificationParameter(SpecificationParameter specificationParameter) {
		this.specificationParameter = specificationParameter;
	}
	
	
}
