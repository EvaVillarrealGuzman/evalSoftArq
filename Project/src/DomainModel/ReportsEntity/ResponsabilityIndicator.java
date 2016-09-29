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

	@ManyToOne(targetEntity = Responsibility.class, cascade = CascadeType.ALL)
	private Responsibility responsability;

}
