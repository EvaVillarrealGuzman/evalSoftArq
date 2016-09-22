package project.preferences.controller;

import java.io.File;
import java.util.ArrayList;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

import project.preferences.PreferenceConstants;
import project.preferences.SoftwareArchitectureEvaluationPreferencePage;
import software.BusinessLogic.SoftwareArchitectureEvaluationManager;
import software.DomainModel.SoftwareArchitectureSpecificationEntity.Architecture;

/**
 * Controller for SoftwareArchitectureEspecificationPreferencePage
 * 
 * @author FEM
 *
 */
public class SoftwareArchitectureEvaluationPPController extends Controller {

	/**
	 * Attributes
	 */
	private static SoftwareArchitectureEvaluationPPController controller;
	private SoftwareArchitectureEvaluationManager manager;
	private SoftwareArchitectureEvaluationPreferencePage form;

	/**
	 * Getters and Setters
	 */
	public static SoftwareArchitectureEvaluationPPController getController() {
		return controller;
	}

	public static void setController(SoftwareArchitectureEvaluationPPController controller) {
		SoftwareArchitectureEvaluationPPController.controller = controller;
	}

	public SoftwareArchitectureEvaluationManager getManager() {
		if (manager == null) {
			manager = new SoftwareArchitectureEvaluationManager();
		}
		return manager;
	}

	public void setManager(SoftwareArchitectureEvaluationManager manager) {
		this.manager = manager;
	}

	public SoftwareArchitectureEvaluationPreferencePage getForm() {
		return form;
	}

	public void setForm(SoftwareArchitectureEvaluationPreferencePage form) {
		this.form = form;
	}

	/**
	 * Sets the model of system combo
	 */
	public void setModel() {
		this.getForm().getCboSystem().setInput(getManager().getComboModelSystemWithArchitecture());
	}

	/**
	 * Sets the model of unit combo
	 */
	public void setModelUnit() {
		this.getForm().getCmbUnit().setInput(getManager().getComboModelUnit());
	}
	
	public void setModel(ComboViewer pcmb) {
		this.setModel((software.DomainModel.AnalysisEntity.System) ((IStructuredSelection) pcmb.getSelection())
				.getFirstElement());
	}

	private void setModel(software.DomainModel.AnalysisEntity.System pmodel) {
		this.getManager().setSystem(pmodel);
	}

	/**
	 * Update the system with the UCM path and prepare the view
	 */
	public Boolean evaluate() {
		//TODO implementar
		return null;
	}

	/**
	 * Sets the model table of the quality requirements of a specific system
	 * 
	 * @param ptype
	 */
	public void setModelPaths(software.DomainModel.AnalysisEntity.System ptype) {
		this.getManager().setSystem(ptype);
		while (this.getForm().getTable().getItems().length > 0) {
			this.getForm().getTable().remove(0);
		}
		if (!this.getManager().getArchitectures().isEmpty()) {
			for (Architecture dp : this.getManager().getArchitectures()) {
				addToTable(dp.getPathUCMs().get(0));
			}
		}
	}

	public void addToTable(String namePath) {
		TableItem item = new TableItem(this.getForm().getTable(), SWT.NONE);
		item.setData(namePath);

		item.setText(new String[] { namePath, namePath.substring(namePath.lastIndexOf("\\") + 1),
				namePath.substring(0, namePath.lastIndexOf("\\")), });
	}

}
