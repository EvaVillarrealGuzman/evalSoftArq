package DomainModel.AnalysisEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This class defines the tactic types
 * 
 * @author: Mica
 * @version: 19/09/2016
 */

@Entity
@Table(name = "TACTICTYPE")
public class TacticType implements Comparable{
	// Attributes
	@Id
	@GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
	private int id;

	private String name;

	public TacticType(String pname) {
		this.name = pname;
	}

	// ComparaTo
	public int compareTo(Object p) {
		TacticType t = (TacticType) p;
		return this.toString().compareTo(t.toString());
	}
}
