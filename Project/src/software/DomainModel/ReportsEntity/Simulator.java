package software.DomainModel.ReportsEntity;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import software.DomainModel.AnalysisEntity.QualityRequirement;

@Entity
@Table(name = "SIMULATOR")
public class Simulator implements Comparable{

	//Attributes
	@Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
	private int id;
	
	@OneToOne(targetEntity = SimulationParameter.class)
	private SimulationParameter simulationParameter;
	
	//VER QUE TAMBIEN PUEDE SER NULO
	@OneToMany(targetEntity = Indicator.class, cascade = CascadeType.ALL)
	private Set<Indicator> indicators = new HashSet<Indicator>();
	
	
	@OneToMany(targetEntity = Run.class, cascade = CascadeType.ALL)
	private Set<Run> runs = new HashSet<Run>();
	
	//CompareTo
	@Override
    public int compareTo(Object p) {
        Simulator t = (Simulator) p;
        return this.toString().compareTo(t.toString());
    }
	
	public Double calculateAverageIndicator(){
		Double averageIndicator=0.2;
		return averageIndicator;
	}
}
