package project.preferences.controller;


import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import project.preferences.SoftwareArchitectureSpecificationPage;
import software.BusinessLogic.AnalysisManager;

public class SoftwareArchitectureSpecificationPPController extends Controller {

	/**
	 * Attributes
	 */
	private static SoftwareArchitectureSpecificationPPController controller;
	private AnalysisManager manager;
	private SoftwareArchitectureSpecificationPage form;

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

	public SoftwareArchitectureSpecificationPage getForm() {
		return form;
	}

	public void setForm(SoftwareArchitectureSpecificationPage form) {
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

}
