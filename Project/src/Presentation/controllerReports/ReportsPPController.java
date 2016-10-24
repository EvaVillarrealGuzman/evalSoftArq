package Presentation.controllerReports;

import org.eclipse.core.runtime.Platform;
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
	public static final String pathReport = Platform.getInstallLocation().getURL().getPath() + "plugins/UCM2DEVS/Report/";

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

	public Boolean printTurnaroundTimePerResponsibility() {
		try {
			this.openReport(this.pathReport + "ResponsibilityTurnaroundTime.jasper");
			this.addParameterToReport("title", "Responsibility Turnaround Time");
			// Agrega los datos al reporte
			this.getManager().setDataCollection(this.getManager().listTurnaroundTime());
			// imprime el reporte
			this.getManager().print();
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	// TODO borrar
	public Boolean printPrueba() {
		try {
			this.openReport(this.pathReport + "ListadoClientesPorLocalidad.jasper");
			this.addParameterToReport("titulo", "TDH Viajes");
			// Agrega los datos al reporte
			this.getManager().setDataCollection(this.getManager().listClientesPorLocalidad());
			// imprime el reporte
			this.getManager().print();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public Boolean printReport() {
		try {
			this.openReport(this.pathReport + "ReporteProyectoFinal.jasper");
			this.addParameterToReport("titulo", "Reporte Resultados de la Simulación");
			// Agrega los datos al reporte
			this.getManager().setDataCollection(this.getManager().listResultSimulation());
			// imprime el reporte
			this.getManager().print();
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	private void addParameterToReport(String pname, Object pobject) {
		this.getManager().addParameter(pname, pobject);
	}
	

	private void openReport(String archive) {
		try {
			this.getManager().setArchive(archive);
			this.getManager().addParameter("tituloMembrete", "Reportes");
			this.getManager().addParameter("tituloMembrete2", "ttt");
			this.getManager().addParameter("frase", "");
			this.getManager().addParameter("pieMembrete", "");
		} catch (Exception e) {
			this.createErrorDialog(e.getLocalizedMessage());
		}
	}

	public Boolean isConnection() {
		return this.getManager().isConnection();
	}

}
