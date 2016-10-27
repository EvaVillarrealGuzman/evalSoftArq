package DomainModel.ReportsEntity;

public class SystemReliability {

	private double failures;
	private String system;
	
	public double getFailures() {
		return failures;
	}
	public void setFailures(double failures) {
		this.failures = failures;
	}
	public String getSystem() {
		return system;
	}
	public void setSystem(String system) {
		this.system = system;
	}	
}
