package Presentation.controllerSoftwareArchitectureSpecification;

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

import BusinessLogic.SoftwareArchitectureSpecificationManager;
import DomainModel.AnalysisEntity.Unit;
import DomainModel.SoftwareArchitectureSpecificationEntity.Architecture;
import Presentation.Controller;
import Presentation.preferenceSoftwareArchitectureSpecification.SoftwareArchitectureSpecificationManagementPreferencePage;
import Presentation.preferences.Messages;

/**
 * Controller for SoftwareArchitectureEspecificationPreferencePage
 * 
 * @author FEM
 *
 */
public class SoftwareArchitectureSpecificationPPController extends Controller {

	/**
	 * Attributes
	 */
	private static SoftwareArchitectureSpecificationPPController controller;
	private SoftwareArchitectureSpecificationManager manager;
	private SoftwareArchitectureSpecificationManagementPreferencePage form;

	/**
	 * Getters and Setters
	 */
	public static SoftwareArchitectureSpecificationPPController getController() {
		return controller;
	}

	public static void setController(SoftwareArchitectureSpecificationPPController controller) {
		SoftwareArchitectureSpecificationPPController.controller = controller;
	}

	public SoftwareArchitectureSpecificationManager getManager() {
		if (manager == null) {
			manager = new SoftwareArchitectureSpecificationManager();
		}
		return manager;
	}

	public void setManager(SoftwareArchitectureSpecificationManager manager) {
		this.manager = manager;
	}

	public SoftwareArchitectureSpecificationManagementPreferencePage getForm() {
		return form;
	}

	public void setForm(SoftwareArchitectureSpecificationManagementPreferencePage form) {
		this.form = form;
	}

	/**
	 * Sets the model of system combo
	 */
	public void setModel() {
		this.getForm().getCboSystem().setInput(getManager().getComboModelSystem());
	}

	public void setModel(ComboViewer pcmb) {
		this.setModel(
				(DomainModel.AnalysisEntity.System) ((IStructuredSelection) pcmb.getSelection()).getFirstElement());
	}

	private void setModel(DomainModel.AnalysisEntity.System pmodel) {
		this.getManager().setSystem(pmodel);
	}

	/**
	 * Sets the model of unit combo
	 */
	public void setModelUnit() {
		this.getForm().getCmbUnit().setInput(getManager().getComboModelUnit());
	}
	
	public void setModelUnit(ComboViewer pcmb) {
		this.setModelUnit(
				(Unit) ((IStructuredSelection) pcmb.getSelection()).getFirstElement());
	}
	
	private void setModelUnit(Unit pmodel) {
		this.getManager().setUnit(pmodel);
	}
	
	/**
	 * Update the system with the UCM path and prepare the view
	 */
	public Boolean save() {
		int err;
		err = this.setSystem();
		if (err == 0) {
			this.getForm().clearView();
			this.getForm().prepareView(0);
			return this.getManager().updateSystem();
		}
		return null;
	}

	/**
	 * Update the system with the UCM path
	 * 
	 * @return int (indicates if the UCM path was saved successfully)
	 */
	public int setSystem() {
		if (this.isValidData()) {
			this.getManager().setPathUCMs(getArrayListNamePath());
			return 0;
		} else {
			return 1;
		}
	}

	private ArrayList<String> getArrayListNamePath() {
		ArrayList<String> pathUCMs = new ArrayList<String>();

		for (int i = 0; i < this.getForm().getTable().getItemCount(); i++) {
			TableItem item = this.getForm().getTable().getItem(i);
			pathUCMs.add(item.getText(2) + "\\" + item.getText(1));
		}
		return pathUCMs;
	}

	/**
	 * Validate the necessary data for save the UCM path
	 * 
	 * @return boolean (is true if they have completed the required fields)
	 */
	public boolean isValidData() {
		if (this.isEmpty(this.getForm().getCboSystem())) {
			this.createErrorDialog(Messages.getString("UCM2DEVS_EmptySystemName_ErrorDialog"));
			this.getForm().getCboSystem().getCombo().setFocus();
			return false;
		}
		return true;
	}

	/**
	 * Sets the model table of the quality requirements of a specific system
	 * 
	 * @param ptype
	 */
	public void setModelPaths(DomainModel.AnalysisEntity.System ptype) {
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

	public void deleteToTable() {
		this.getForm().getTable().remove(this.getForm().getTable().getSelectionIndices());
	}

	public boolean isUCMDuplicate(String newPath) {
		for (int i = 0; i < this.getForm().getTable().getItemCount(); i++) {
			TableItem item = this.getForm().getTable().getItem(i);
			if (newPath.equals(item.getText(2) + "\\" + item.getText(1))) {
				return true;
			}
		}
		return false;
	}

	public void openJUCMNavEditor(Composite parent, String pathUCM) {
		File file = new File(pathUCM);
		if (file.exists()) {
			IFile ifile = convert(file);
			IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			try {
				IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry()
						.getDefaultEditor(ifile.getName());
				parent.getShell().close();
				page.openEditor(new FileEditorInput(ifile), desc.getId());
			} catch (Exception e1) {
				createErrorDialog(Messages.getString("UCM2DEVS_ProjectOpenEclipse_ErrorDialog"));
			}
		} else {
			createErrorDialog(Messages.getString("UCM2DEVS_UCMNotExists_ErrorDialog"));
		}
	}

	public Boolean isConnection() {
		return this.getManager().isConnection();
	}
}