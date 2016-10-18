package DomainModel.SoftwareArchitectureEvaluationEntity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "SIMULATOR")
public class Simulator implements Comparable{

	//Attributes
	@Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
	private int id;
	
	@OneToOne(targetEntity = SimulationParameter.class)
	private SimulationParameter simulationParameter;
	
	@OneToMany(targetEntity = Run.class, cascade = CascadeType.ALL)
	private Set<Run> runs = new HashSet<Run>();
	
	//CompareTo
	@Override
    public int compareTo(Object p) {
        Simulator t = (Simulator) p;
        return this.toString().compareTo(t.toString());
    }
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public SimulationParameter getSimulationParameter() {
		return simulationParameter;
	}

	public void setSimulationParameter(SimulationParameter simulationParameter) {
		this.simulationParameter = simulationParameter;
	}

	public Set<Run> getRuns() {
		return runs;
	}

	public void setRuns(Set<Run> runs) {
		this.runs = runs;
	}

	public Double calculateAverageIndicator(){
		Double averageIndicator=0.2;
		return averageIndicator;
	}
}