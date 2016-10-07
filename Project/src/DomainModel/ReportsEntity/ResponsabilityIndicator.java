package DomainModel.ReportsEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import DomainModel.ReportsEntity.IndicatorType;
import DomainModel.SoftwareArchitectureSpecificationEntity.Responsibility;

@Entity
@Table(name = "RESPONSABILIDADINDICATOR")
public class ResponsabilityIndicator extends IndicatorType {

	public ResponsabilityIndicator(String pname) {
		super(pname);
	}

	@ManyToOne(targetEntity = Responsibility.class, cascade = CascadeType.ALL)
	private Responsibility responsibility;

	public Responsibility getResponsibility() {
		return responsibility;
	}

	public void setResponsibility(Responsibility responsibility) {
		this.responsibility = responsibility;
	}

	
}
