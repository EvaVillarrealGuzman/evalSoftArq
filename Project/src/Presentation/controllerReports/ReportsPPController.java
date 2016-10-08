package Presentation.controllerReports;

import java.util.List;

import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredSelection;

import BusinessLogic.ReportManager;
import DomainModel.SoftwareArchitectureSpecificationEntity.Architecture;
import Presentation.Controller;
import Presentation.preferenceReports.ReportsPreferencePage;
import Presentation.preferences.Messages;

/**
 * Controller for ReportPreferencePage
 * 
 * @author FEM
 *
 */
public class ReportsPPController extends Controller {

	/**
	 * Attributes
	 */
	private static ReportsPPController controller;
	private ReportManager manager;
	private ReportsPreferencePage form;
	public static final String pathReport = "C:\\PP\\Report\\";

	/**
	 * Getters and Setters
	 */
	public static ReportsPPController getController() {
		return controller;
	}

	public static void setController(ReportsPPController controller) {
		ReportsPPController.controller = controller;
	}

	public ReportManager getManager() {
		if (manager == null) {
			manager = new ReportManager();
		}
		return manager;
	}

	public void setManager(ReportManager manager) {
		this.manager = manager;
	}

	public ReportsPreferencePage getForm() {
		return form;
	}

	public void setForm(ReportsPreferencePage form) {
		this.form = form;
	}

	/**
	 * Sets the model of system combo
	 */
	public void setModel() {
		this.getForm().getCboSystem().setInput(getManager().getComboModelSystemWithSimulations());
	}

	public void setModel(ComboViewer pcmb) {
		this.setModel(
				(DomainModel.AnalysisEntity.System) ((IStructuredSelection) pcmb.getSelection()).getFirstElement());
	}

	private void setModel(DomainModel.AnalysisEntity.System pmodel) {
		this.getManager().setSystem(pmodel);
	}

	/**
	 * Setes the model of software architecture specification table
	 * 
	 */
	public void setModel(Architecture pmodel) {
		this.getManager().setArchitecture(pmodel);
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

	public void printFailPerResponsibility() {
		this.openReport(this.pathReport + "FailsPerResponsibility.jasper");
		this.addParameterToReport("Fails per Responsability", "Project");
		// this.addDataToReport(this.getGestorClientesPorLocalidad().listar());
		this.printReport();
	}

	public void addParameterToReport(String pname, Object pobject) {
		manager.addParameter(pname, pobject);
	}

	public void openReport(String archive) {
		try {
			this.getManager().setArchive(archive);
			manager.addParameter("tituloMembrete", "Reportes");
			manager.addParameter("tituloMembrete2", "ttt");
			manager.addParameter("frase", "");
			manager.addParameter("pieMembrete", "");
		} catch (Exception e) {
			this.createErrorDialog(e.getLocalizedMessage());
		}
	}

	public void addDataToReport(List plistData) {
		this.getManager().setColeccionDeDatos(plistData);
	}

	public void printReport() {
		try {
			this.getManager().print();
		} catch (Exception e) {
			this.createErrorDialog(e.getLocalizedMessage());
		}
	}

	public Boolean isConnection() {
		return this.getManager().isConnection();
	}

}
