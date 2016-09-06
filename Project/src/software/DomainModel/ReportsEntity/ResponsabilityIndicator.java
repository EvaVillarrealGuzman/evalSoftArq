package software.DomainModel.ReportsEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import software.DomainModel.SoftwareArchitectureSpecificationEntity.Responsability;

@Entity
@Table(name = "RESPONSABILIDADINDICATOR")
public class ResponsabilityIndicator extends IndicatorType {

	@ManyToOne(targetEntity = Responsability.class, cascade = CascadeType.ALL)
	private Responsability responsability;

}
