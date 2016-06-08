package software.DomainModel.AnalysisEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This class defines the condition under which the quality scenario is
 * specified
 * 
 * @author: FEM
 * @version: 06/11/2015
 */

@Entity
@Table(name = "CONDITION")
public class Condition implements Comparable {

	// Attributes
	@Id
	@GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
	private int id;

	private String name;

	// Builder
	public Condition() {

	}

	public Condition(String pname) {
		super();
		this.name = pname;
	}

	// Getters and Setters
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

	// CompareTo
	public int compareTo(Object p) {
		Condition t = (Condition) p;
		return this.toString().compareTo(t.toString());
	}
}
