package DomainModel.SoftwareArchitectureEvaluationEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * borrar
 * @author Usuario-Pc
 *
 */
@Entity
@Table(name = "SIMULATIONPARAMETER")
public class SimulationParameter implements Comparable{

	//Attributes
		@Id
	    @GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
		private int id;
		
		private Double simulationTime;
		private Double requestAverageTime;
		
	
		//CompareTo
		@Override
	    public int compareTo(Object p) {
	        SimulationParameter t = (SimulationParameter) p;
	        return this.toString().compareTo(t.toString());
	    }
}
