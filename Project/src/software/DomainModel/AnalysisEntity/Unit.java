package software.DomainModel.AnalysisEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This class defines the unity of a or more metric
 * @author: FEM
 * @version: 06/11/2015
 */

@Entity
@Table(name = "UNIT")
public class Unit implements Comparable{

	//Attributes
	@Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
	private int id;
	
	private String name;
	
	//Builders
	public Unit() {
		
	}
	public Unit(String pname) {
		super();
		this.name = pname;
	}
	
	//Getters and Setters
	public int getId() {
		return id;
	}
	
	public void setId(int pid) {
		this.id = pid;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String pname) {
		this.name = pname;
	}
	
	// toString
	@Override
	public String toString() {
		return this.getName();
	}

	// compareTo
	public int compareTo(Object p) {
		Unit t = (Unit) p;
		return this.toString().compareTo(t.toString());
	}
	
}
