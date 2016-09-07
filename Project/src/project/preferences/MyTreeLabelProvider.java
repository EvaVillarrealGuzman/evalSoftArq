package project.preferences;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import software.DomainModel.AnalysisEntity.QualityRequirement;

/**
 * This class help to identify the images and texts to be placed in table tree cells
 * 
 * @author: MICA
 * @version: 07/09/2016
 */
public class MyTreeLabelProvider extends LabelProvider implements ITableLabelProvider{

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	} 
	 
     public String getColumnText(Object element, int columnIndex){
        switch (columnIndex){
           case 0:
              if (element instanceof QualityRequirement)
                 return ((QualityRequirement)element).getQualityScenario().getDescription();
           case 1: 
              if (element instanceof QualityRequirement)
                  return ((QualityRequirement)element).getQualityScenario().getCondition().toString();
           case 2: 
               if (element instanceof QualityRequirement)
                   return "View Report";
        }
        return null;
     }

     public void addListener(ILabelProviderListener listener){
     }

     public void dispose(){
     }

     public boolean isLabelProperty(Object element, String property){
        return false;
     }

     public void removeListener(ILabelProviderListener listener){
     }

}
