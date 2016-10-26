package Presentation.controllerSoftwareArchitectureSpecification;

import java.io.File;
import java.util.Iterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
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
import DomainModel.SoftwareArchitectureSpecificationEntity.Path;
import DomainModel.SoftwareArchitectureSpecificationEntity.PathElement;
import DomainModel.SoftwareArchitectureSpecificationEntity.Responsibility;
import DomainModel.SoftwareArchitectureSpecificationEntity.SpecificationParameter;
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
		this.setModelUnit((Unit) ((IStructuredSelection) pcmb.getSelection()).getFirstElement());
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
			getArchitecturesToSystem();
			return 0;
		} else {
			return 1;
		}
	}

	private void getArchitecturesToSystem() {

		deleteArchitecture();

		for (int i = 0; i < this.getForm().getTable().getItemCount(); i++) {
			TableItem item = this.getForm().getTable().getItem(i);
			if (!item.getText(2).equals("")) {
				boolean isNotAdd = false;
				for (Architecture arc : this.getManager().getArchitectures()) {
					if (arc.getPathUCM().equals(item.getText(2) + "\\" + item.getText(1))) {
						isNotAdd = true;
					}
				}
				if (!isNotAdd) {
					Architecture architecture = new Architecture(item.getText(2) + "\\" + item.getText(1));
					this.getManager().createArchitecture(architecture);
					this.getManager().getArchitectures().add(architecture);
				}
			}
		}

		this.getManager().updateSystem();
	}

	/**
	 * Deleto to current system, architectures
	 */
	private void deleteArchitecture() {
		for (Architecture arc : this.getManager().getArchitectures()) {
			boolean isNotDelete = false;

			for (int i = 0; i < this.getForm().getTable().getItemCount(); i++) {
				TableItem item = this.getForm().getTable().getItem(i);
				if (arc.getPathUCM().equals(item.getText(2) + "\\" + item.getText(1))) {
					isNotDelete = true;
				}
			}

			if (!isNotDelete) {
				this.getManager().getArchitectures().remove(arc);
			}

		}
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
		Boolean first = true;
		this.getManager().setSystem(ptype);
		while (this.getForm().getTable().getItems().length > 0) {
			this.getForm().getTable().remove(0);
		}
		if (!this.getManager().getArchitectures().isEmpty()) {
			for (Architecture arc : this.getManager().getArchitectures()) {
				if (first) {
					// Solo lo hace la primera vez
					this.setUnit(arc);
					first = false;
				}
				addToTable(arc.getPathUCM());
			}
		}
	}

	/**
	 * coloca la unidad en el combo
	 * 
	 * @param architecture
	 */
	private void setUnit(Architecture architecture) {
		Iterator it = architecture.getPaths().iterator();
		if (it.hasNext()) {
			Path a = (Path) it.next();
			Iterator itPathElements = a.getPathElements().iterator();
			if (itPathElements.hasNext()) {
				PathElement pathElement = (PathElement) itPathElements.next();
				if (pathElement instanceof Responsibility) {
					Responsibility responsability = (Responsibility) pathElement;
					Iterator itSpePar = responsability.getSpecificationParameter().iterator();
					if (itSpePar.hasNext()) {
						SpecificationParameter spePar = (SpecificationParameter) itSpePar.next();
						System.out.println(spePar.getUnit());
						this.getForm().getCmbUnit().setSelection(new StructuredSelection(spePar.getUnit()));

					}
				}
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
