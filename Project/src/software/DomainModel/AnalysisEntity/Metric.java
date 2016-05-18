package software.DomainModel.AnalysisEntity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * This class defines metrics 
 * @author: FEM
 * @version: 06/11/2015
 */

public class Metric implements Comparable{

	//Attributes
	@Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
	private int id;
	
	private String name;
	
	@OneToOne(targetEntity = Formula.class)
	private Formula formula;
	
	@ManyToMany(targetEntity = Unit.class)
	private Set<Unit> units = new HashSet<Unit>();
	
	//Builders
	public Metric() {
		
	}
	public Metric(String pname, Set<Unit> punits) {
		super();
		this.name = pname;
		this.units = punits;
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
	public Formula getFormula() {
		return formula;
	}
	public void setFormula(Formula pformula) {
		this.formula = pformula;
	}
	public Set<Unit> getUnits() {
		return units;
	}
	public void setUnits(Set<Unit> punits) {
		this.units = punits;
	}
		
	//toString
	@Override
	public String toString() {
		return this.getName();
	}

	//CompareTo
	public int compareTo(Object p) {
        Metric t = (Metric) p;
        return this.toString().compareTo(t.toString());
    }
}
