package project.preferences;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import software.DomainModel.AnalysisEntity.QualityAttribute;
import software.DomainModel.AnalysisEntity.QualityRequirement;


/**
 * This class establish the relationship between parent and child object
 * 
 * @author: MICA
 * @version: 07/09/2016
 */
public class MyTreeContentProvider implements ITreeContentProvider {
	
	software.DomainModel.AnalysisEntity.System system;
	
	public software.DomainModel.AnalysisEntity.System getSystem() {
		return system;
	}

	public void setSystem(software.DomainModel.AnalysisEntity.System system) {
		this.system = system;
	}

	public Object[] getChildren(Object parentElement){
		QualityAttribute qualityAttribute = (QualityAttribute) parentElement;
		Set<Object> childrens = new HashSet<Object>();
		int i = 0;
		for (QualityRequirement dp: system.getQualityRequirements()){
			if (dp.getQualityScenario().getQualityAttribute()==qualityAttribute){
				childrens.add(dp);
				i++;
			}
		}
		return childrens.toArray();
     }

     public boolean hasChildren(Object parentElement){
    	QualityAttribute qualityAttribute = (QualityAttribute) parentElement;
 		Set<Object> childrens = new HashSet<Object>();
 		int i = 0;
 		for (QualityRequirement dp: system.getQualityRequirements()){
 			if (dp.getQualityScenario().getQualityAttribute()==qualityAttribute){
 				childrens.add(dp);
 				i++;
 			}
 		}
 		if (childrens.isEmpty()){
 			return false;
 		}else {
 			return true;
 		}
     }

     public Object[] getElements(Object pobject){
        return getChildren(pobject);
     }

     public void dispose(){
     }

     public void inputChanged(Viewer viewer, Object oldInput, Object newInput){
     }

	@Override
	public Object getParent(Object childrenElement) {
		QualityRequirement qualityRequirement = (QualityRequirement) childrenElement;
		return qualityRequirement.getQualityScenario().getQualityAttribute();
	}

}
