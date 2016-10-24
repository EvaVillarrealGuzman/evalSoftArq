package DomainModel.SoftwareArchitectureEvaluationEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "SYSTEMINDICATOR")
public class SystemIndicator extends IndicatorType {

	public SystemIndicator() {
		super();
	}

	public SystemIndicator(String pname) {
		super(pname);
	}

}
