package DomainModel.SoftwareArchitectureEvaluationEntity;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "RUN")
public class Run implements Comparable { // NOPMD by Usuario-Pc on 10/06/16
											// 21:53

	// Attributes
	@Id
	@GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
	private int id;

	private Date date;
	private Double simulationHorizon;

	// VER QUE TAMBIÉN PUEDE SER CERO
	@OneToMany(targetEntity = Indicator.class, cascade = CascadeType.ALL)
	private Set<Indicator> indicators = new HashSet<Indicator>();

	// CompareTo
	@Override
	public int compareTo(Object p) {
		Indicator t = (Indicator) p;
		return this.toString().compareTo(t.toString());
	}

	public Set<Indicator> getIndicators() {
		return indicators;
	}

	public void setIndicators(Set<Indicator> indicators) {
		this.indicators = indicators;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(java.util.Date pdate) {
		this.date = new java.sql.Date(pdate.getTime());
	}

	public Double getSimulationHorizon() {
		return simulationHorizon;
	}

	public void setSimulationHorizon(Double psimulationHorizon) {
		this.simulationHorizon = psimulationHorizon;
	}

}
