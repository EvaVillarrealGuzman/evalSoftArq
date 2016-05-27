package software.DomainModel.AnalysisEntity;

import java.util.HashSet;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;

import org.eclipse.swt.widgets.DateTime;
import org.hibernate.annotations.Type;

import software.DomainModel.SoftwareArchitectureSpecificationEntity.Architecture;

/**
 * This class defines the system of a project
 * @author: FEM
 * @version: 06/11/2015
 */

@Entity
@Table(name = "SYSTEM")
public class System implements Comparable{

	//Attributes
	@Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
	private int id;
	
	private String systemName;
	
	private String projectName;
	
	@Type(type="org.joda.time.contrib.hibernate.PersistentDateTimeTZ")
	private DateTime  projectFinishDate;
	
	@Type(type="org.joda.time.contrib.hibernate.PersistentDateTimeTZ")
	private DateTime  projectStartDate;
	
    @OneToMany(targetEntity = QualityRequirement.class, cascade = CascadeType.ALL)
	private Set<QualityRequirement> qualityRequirements = new HashSet<QualityRequirement>();
    
    //VER QUE PUEDE SER CERO
    @OneToMany(targetEntity = Architecture.class, cascade = CascadeType.ALL)
   	private Set<Architecture> architectures = new HashSet<Architecture>();
    
	private boolean state;
	
	//Builders
	public System (){
		
	}
	
	public System(String psystemName, String pprojectName,
			DateTime  pprojectStartDate, DateTime  pprojectFinishDate, boolean pstate) {
		super();
		this.projectFinishDate = pprojectFinishDate;
		this.projectStartDate = pprojectStartDate;
		this.projectName = pprojectName;
		this.systemName = psystemName;
		this.state = pstate;
	}
	
	//Getters and Setters
	public int getId() {
		return id;
	}
	
	public void setId(int pid) {
		this.id = pid;
	}
	
	public DateTime  getProjectFinishDate() {
		return projectFinishDate;
	}
	
	public void setProjectFinishDate(DateTime  pprojectFinishDate) {
		this.projectFinishDate = pprojectFinishDate;
	}
	
	public DateTime  getProjectStartDate() {
		return projectStartDate;
	}
	
	public void setProjectStartDate(DateTime  pprojectStartDate) {
		this.projectStartDate = pprojectStartDate;
	}
	
	public String getProjectName() {
		return projectName;
	}
	
	public void setProjectName(String pprojectName) {
		this.projectName = pprojectName;
	}
	
	public String getSystemName() {
		return systemName;
	}
	
	public void setSystemName(String psystemName) {
		this.systemName = psystemName;
	}
	
	public Set<QualityRequirement> getQualityRequirements() {
		return qualityRequirements;
	}
	
	public void setQualityRequirements(Set<QualityRequirement> pqualityRequirements) {
		this.qualityRequirements = pqualityRequirements;
	}
	
	public boolean isState() {
		return state;
	}
	
	public void setState(boolean pstate) {
		this.state = pstate;
	}
	
	@Override
	public String toString() {
		return this.getSystemName();
	}

	// compareTo
	public int compareTo(Object p) {
		System t = (System) p;
		return this.toString().compareTo(t.toString());
	}
	
	/**
     * Method removes a system logically, by setting their status to false
     * @param doesn't apply
     * @return void
     */
	public void remove() {
        this.setState(false);
    }
	
}
