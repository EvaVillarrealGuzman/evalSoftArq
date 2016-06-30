package software.DomainModel.ReportsEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "INDICATORTYPE")
public class IndicatorType implements Comparable{
		
		//Attributes
		@Id
	    @GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
		private int id;
		
		private String name;
		
		//CompareTo
		@Override
	    public int compareTo(Object p) {
	        IndicatorType t = (IndicatorType) p;
	        return this.toString().compareTo(t.toString());
	    }
}
