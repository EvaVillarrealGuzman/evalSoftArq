package software.DomainModel.ReportsEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import software.DomainModel.SoftwareArchitectureSpecificationEntity.Responsability;

@Entity
@Table(name = "SPECIFICATIONPARAMETER")
public class SpecificationParameter implements Comparable{
	//Attributes
			@Id
		    @GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
			private int id;
			
			private Double fallTime;
			private Double executionTime;
			private Double recuperationTime;
			private Double FaultAverageTime;

			@ManyToOne(targetEntity = Responsability.class)
			private Responsability responsability;
		
			//CompareTo
			@Override
		    public int compareTo(Object p) {
		        SpecificationParameter t = (SpecificationParameter) p;
		        return this.toString().compareTo(t.toString());
		    }

}
