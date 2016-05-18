package software.DomainModel.SoftwareArchitectureSpecificationEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "RESPONSABILITY")
public class Responsability implements Comparable{

	//Attributes
		@Id
	    @GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
		private int id;
		
		private String name;
		private String description;
		
		//CompareTo
		@Override
	    public int compareTo(Object p) {
	        Responsability t = (Responsability) p;
	        return this.toString().compareTo(t.toString());
	    }
}

