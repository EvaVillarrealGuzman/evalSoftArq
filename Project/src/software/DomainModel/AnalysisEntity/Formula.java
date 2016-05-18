package software.DomainModel.AnalysisEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This class defines the formula of a metric
 * @author: FEM
 * @version: 06/11/2015
 */

@Entity
@Table(name = "FORMULA")
public class Formula implements Comparable{

	//Attributes
	@Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
	private int id;
	
	private String formula; 

	//Builders
	public Formula(){
		
	}
	
	public Formula(String pformula) {
		super();
		this.formula = pformula;
	}
	
	//Getters and Setters
	public int getId() {
		return id;
	}

	public void setId(int pid) {
		this.id = pid;
	}
	
	public String getFormula() {
		return formula;
	}

	public void setFormula(String pformula) {
		this.formula = pformula;
	}

	//CompareTo
	public int compareTo(Object p) {
        Formula t = (Formula) p;
        return this.toString().compareTo(t.toString());
    }
	
}
