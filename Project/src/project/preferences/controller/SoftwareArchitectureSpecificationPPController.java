package project.preferences.controller;


import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredSelection;

import project.preferences.SoftwareArchitectureSpecificationPreferencePage;
import software.BusinessLogic.AnalysisManager;

public class SoftwareArchitectureSpecificationPPController extends Controller {

	/**
	 * Attributes
	 */
	private static SoftwareArchitectureSpecificationPPController controller;
	private AnalysisManager manager;
	private SoftwareArchitectureSpecificationPreferencePage form;

	/**
	 * Getters and Setters
	 */
	public static SoftwareArchitectureSpecificationPPController getController() {
		return controller;
	}

	public static void setController(SoftwareArchitectureSpecificationPPController controller) {
		SoftwareArchitectureSpecificationPPController.controller = controller;
	}

	public AnalysisManager getManager() {
		if (manager == null) {
			manager = new AnalysisManager();
		}
		return manager;
	}

	public void setManager(AnalysisManager manager) {
		this.manager = manager;
	}

	public SoftwareArchitectureSpecificationPreferencePage getForm() {
		return form;
	}

	public void setForm(SoftwareArchitectureSpecificationPreferencePage form) {
		this.form = form;
	}

	/**
	 * Open the form
	 * 
	 * @param pabm
	 */
	public void open() {
		// TODO
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

	public void save() {
		int err;
		err = this.setSystem();
		if (err == 0) {
			this.getManager().updateSystem();
		}
	}
	
	public int setSystem() {
		if (this.isValidData()) {
			this.getManager().setPathUCM(this.getForm().getTxtSelectUCM().getText());
			return 0;
		} else {
			return 1;
		}
	}
	
	/**
	 * return true if they have completed the required fields
	 */
	public boolean isValidData() {
		if (this.isEmpty(this.getForm().getCboSystem())) {
			this.createErrorDialog("System name empty");
			this.getForm().getCboSystem().getCombo().setFocus();
			return false;
		}
		if (this.isEmpty(this.getForm().getTxtSelectUCM())) {
			this.createErrorDialog("File name empty");
			this.getForm().getTxtSelectUCM().setFocus();
			return false;
		} 

		return true;
	}
	
	/**
	 * Configure the form when a Combo´s item is selected
	 */
	public void getView() {
		String pathUCM = this.getManager().getPathUCM();
		if (!pathUCM.equals("")){
			this.getForm().getTxtSelectUCM().setText(pathUCM);
		}
	}
	
}
