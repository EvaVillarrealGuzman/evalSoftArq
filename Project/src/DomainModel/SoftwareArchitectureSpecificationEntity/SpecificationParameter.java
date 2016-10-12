package DomainModel.SoftwareArchitectureSpecificationEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import DomainModel.AnalysisEntity.Metric;
import DomainModel.AnalysisEntity.Unit;

/**
 * This class defines parameters for a specific responsibility
 *  
 * @author: FEM
 * @version: 06/09/2016
 */

@Entity
@Table(name = "SPECIFICATIONPARAMETER")
public class SpecificationParameter {
	@Id
	@GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
	private int id;
	
	//private double meanExecutionTime;
	
	//private double meanRecoveryTime;
	
	//private double meanDownTime;
	
	//private double meanTimeBFail;
	
	@ManyToOne(targetEntity = Unit.class)
	private Unit unit;
	
	@ManyToOne(targetEntity = Metric.class)
	private Metric metric;
	
	private double value;
	
	public SpecificationParameter() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

//	public double getMeanExecutionTime() {
//		return meanExecutionTime;
//	}
//
//	public void setMeanExecutionTime(double meanExecutionTime) {
//		this.meanExecutionTime = meanExecutionTime;
//	}
//
//	public double getMeanRecoveryTime() {
//		return meanRecoveryTime;
//	}
//
//	public void setMeanRecoveryTime(double meanRecoveryTime) {
//		this.meanRecoveryTime = meanRecoveryTime;
//	}
//
//	public double getMeanDownTime() {
//		return meanDownTime;
//	}
//
//	public void setMeanDownTime(double meanDownTime) {
//		this.meanDownTime = meanDownTime;
//	}
//
//	public double getMeanTimeBFail() {
//		return meanTimeBFail;
//	}
//
//	public void setMeanTimeBFail(double meanTimeBFail) {
//		this.meanTimeBFail = meanTimeBFail;
//	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public Metric getMetric() {
		return metric;
	}

	public void setMetric(Metric metric) {
		this.metric = metric;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
	
}
