package software.DomainModel.AnalysisEntity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * This class defines metrics
 *
 * @author: FEM
 * @version: 04/05/2016
 */

@Entity
@Table(name = "INDIRECTMETRICS")
@PrimaryKeyJoinColumn(name = "id")
public class IndirectMetric extends Metric {

	@OneToMany(targetEntity = Metric.class, cascade = CascadeType.ALL)
	private Set<Metric> metrics = new HashSet<Metric>();

	// Composite pattern

	public void add(Metric pmetric) {
		metrics.add(pmetric);
	}

	public void delete(Metric pmetric) {
		metrics.remove(pmetric);
	}

	// Getters and Setters

	public Set<Metric> getMetrics() {
		return metrics;
	}

	public void setMetrics(Set<Metric> metrics) {
		this.metrics = metrics;
	}
}
