package project.preferences;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;


/**
 * This class establish the relationship between parent and child object
 * 
 * @author: MICA
 * @version: 07/09/2016
 */
public class MyTreeContentProvider implements ITreeContentProvider {

	public Object[] getChildren(Object parentElement){
		//TODO implementar
		return new Object[0];
     }

     public boolean hasChildren(Object element){
        //TODO implementar
          return false;
     }

     public Object[] getElements(Object pobject){
        return getChildren(pobject);
     }

     public void dispose(){
     }

     public void inputChanged(Viewer viewer, Object oldInput, Object newInput){
     }

	@Override
	public Object getParent(Object arg0) {
		// TODO implementar
		return null;
	}

}
