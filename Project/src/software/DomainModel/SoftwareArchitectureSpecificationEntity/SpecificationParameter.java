package software.DomainModel.SoftwareArchitectureSpecificationEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This class defines parameters for a specific responsibility
 *  
 * @author: FEM
 * @version: 06/09/2016
 */

@Entity
@Table(name = "SEPECIFICATIONPARAMETER")
public class SpecificationParameter {
	@Id
	@GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
	private int id;
	
	private double meanExecutionTime;
	
	private double meanRecoveryTime;
	
	private double meanDownTime;
	
	private double meanTimeBFail;
	
	private double meanTimeBRequest;

	
	public SpecificationParameter() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getMeanExecutionTime() {
		return meanExecutionTime;
	}

	public void setMeanExecutionTime(double meanExecutionTime) {
		this.meanExecutionTime = meanExecutionTime;
	}

	public double getMeanRecoveryTime() {
		return meanRecoveryTime;
	}

	public void setMeanRecoveryTime(double meanRecoveryTime) {
		this.meanRecoveryTime = meanRecoveryTime;
	}

	public double getMeanDownTime() {
		return meanDownTime;
	}

	public void setMeanDownTime(double meanDownTime) {
		this.meanDownTime = meanDownTime;
	}

	public double getMeanTimeBFail() {
		return meanTimeBFail;
	}

	public void setMeanTimeBFail(double meanTimeBFail) {
		this.meanTimeBFail = meanTimeBFail;
	}

	public double getMeanTimeBRequest() {
		return meanTimeBRequest;
	}

	public void setMeanTimeBRequest(double meanTimeBRequest) {
		this.meanTimeBRequest = meanTimeBRequest;
	}
	
}
