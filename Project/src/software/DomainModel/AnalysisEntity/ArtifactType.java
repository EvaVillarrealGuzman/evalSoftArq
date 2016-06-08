package software.DomainModel.AnalysisEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This class defines the generic artifact of a generic scenario of quality
 * 
 * @author: FEM
 * @version: 06/11/2015
 */

@Entity
@Table(name = "ARTIFACTTYPE")
public class ArtifactType implements Comparable {

	// Attributes
	@Id
	@GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
	private int id;

	private String name;

	// Builder
	public ArtifactType() {

	}

	public ArtifactType(String pname) {
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

	// ComparaTo
	public int compareTo(Object p) {
		ArtifactType t = (ArtifactType) p;
		return this.toString().compareTo(t.toString());
	}

}
