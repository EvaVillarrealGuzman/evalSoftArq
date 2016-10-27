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
	public static final String PATHREPORT = Platform.getInstallLocation().getURL().getPath() + "plugins/UCM2DEVS/Report/";

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

	public Boolean printReportPerResponsibilityPerformance() {
		try {
			this.openReport(this.PATHREPORT + "reportResponsibilityPerformance.jasper");
			this.addParameterToReport("title", "Report per Responsibility - Attribute: Performance");
			// Agrega los datos al reporte
			this.getManager().setDataCollection(this.getManager().listResponsibilityPerformance());
			// imprime el reporte
			this.getManager().print();
			this.getManager().visibleReport();
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}
	
	public Boolean printReportPerResponsibilityReliability() {
		try {
			this.openReport(this.PATHREPORT + "reportResponsibilityReliability.jasper");
			this.addParameterToReport("title", "Report per Responsibility - Attribute: Reliability");
			// Agrega los datos al reporte
			this.getManager().setDataCollection(this.getManager().listResponsibilityReliability());
			// imprime el reporte
			this.getManager().print();
			this.getManager().visibleReport();
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	public Boolean printReportPerResponsibilityAvailability() {
		try {
			this.openReport(this.PATHREPORT + "reportResponsibilityAvailability.jasper");
			this.addParameterToReport("title", "Report per Responsibility - Attribute: Availability");
			// Agrega los datos al reporte
			this.getManager().setDataCollection(this.getManager().listResponsibilityAvailability());
			// imprime el reporte
			this.getManager().print();
			this.getManager().visibleReport();
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
