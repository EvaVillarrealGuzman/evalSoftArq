package software.DomainModel.ReportsEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "RESPONSABILIDADINDICATOR")
public class ResponsabilityIndicator extends IndicatorType {

	private String responsability;

}
