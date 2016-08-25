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

import project.preferences.SoftwareArchitectureSpecificationManagementPreferencePage;
import software.BusinessLogic.SoftwareArchitectureSpecificationManager;

/**
 * Controller for SoftwareArchitectureEspecificationPreferencePage
 * 
 * @author Eva
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
		this.setModel((software.DomainModel.AnalysisEntity.System) ((IStructuredSelection) pcmb.getSelection())
				.getFirstElement());
	}

	private void setModel(software.DomainModel.AnalysisEntity.System pmodel) {
		this.getManager().setSystem(pmodel);
	}

	/**
	 * Update the system with the UCM path and prepare the view
	 */
	public void save() {
		int err;
		err = this.setSystem();
		if (err == 0) {
			this.getManager().updateSystem();
		}
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
			this.createErrorDialog("System name empty");
			this.getForm().getCboSystem().getCombo().setFocus();
			return false;
		}
		return true;
	}

	/**
	 * Configure the form when a Combo´s item is selected
	 */
	/*
	 * public void getView() { this.setModelPaths(); String pathUCM =
	 * this.getManager().getPathUCMs(); if (pathUCM != null) { File file = new
	 * File(pathUCM); if (!file.exists()) { createErrorDialog(
	 * "The UCM file does not exist"); }
	 * this.getForm().getTxtSelectUCM().setText(pathUCM); } else {
	 * this.getForm().getTxtSelectUCM().setText(""); } }
	 */

	/**
	 * Sets the model table of the quality requirements of a specific system
	 * 
	 * @param ptype
	 */
	public void setModelPaths(software.DomainModel.AnalysisEntity.System ptype) { // NOPMD
																					// by
																					// Usuario-Pc
																					// on
																					// 10/06/16
																					// 21:46
		this.getManager().setSystem(ptype);// TODO
		// Borra lo que tenía antes la table
		while (this.getForm().getTable().getItems().length > 0) {
			this.getForm().getTable().remove(0);
		}
		if (this.getManager().getPathUCMs() != null) {
			for (String namePath : this.getManager().getPathUCMs()) {
				addToTable(namePath);
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
				createErrorDialog("There must be a project in Eclipse to open a file");
			}
		} else {
			createErrorDialog("The UCM file does not exist");
		}

	}

}
