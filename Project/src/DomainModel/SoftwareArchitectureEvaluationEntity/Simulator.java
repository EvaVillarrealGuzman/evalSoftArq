package DomainModel.SoftwareArchitectureEvaluationEntity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import DomainModel.AnalysisEntity.QualityRequirement;

@Entity
@Table(name = "SIMULATOR")
public class Simulator implements Comparable{

	//Attributes
	@Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
	private int id;
	
	@OneToMany(targetEntity = Run.class, cascade = CascadeType.ALL)
	private Set<Run> runs = new HashSet<Run>();
	
	@ManyToMany(targetEntity = QualityRequirement.class, cascade = CascadeType.ALL)
	private Set<QualityRequirement> requirements = new HashSet<QualityRequirement>();
	
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

	public Set<Run> getRuns() {
		return runs;
	}

	public void setRuns(Set<Run> runs) {
		this.runs = runs;
	}

	public Set<QualityRequirement> getRequirements() {
		return requirements;
	}

	public void setRequirements(Set<QualityRequirement> requirements) {
		this.requirements = requirements;
	}

	public Double calculateAverageIndicator(){
		Double averageIndicator=0.2;
		return averageIndicator;
	}
}
