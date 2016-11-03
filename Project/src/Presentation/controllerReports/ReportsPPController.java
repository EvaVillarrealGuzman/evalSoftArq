package Presentation.controllerReports;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TableItem;

import BusinessLogic.ReportManager;
import DomainModel.AnalysisEntity.QualityRequirement;
import DomainModel.SoftwareArchitectureEvaluationEntity.Simulator;
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
	public static final String PATHREPORT = Platform.getInstallLocation().getURL().getPath()
			+ "plugins/UCM2DEVS/Report/";

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
		this.getForm().getCmbSystem().setInput(getManager().getComboModelSystemWithSimulations());
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
	/**
	 * Validate the necessary data for save the UCM path
	 * 
	 * @return boolean (is true if they have completed the required fields)
	 */
	public boolean isValidData() {
		if (this.isEmpty(this.getForm().getCmbSystem())) {
			this.createErrorDialog(Messages.getString("UCM2DEVS_SelectSystem_ErrorDialog"));
			this.getForm().getCmbSystem().getCombo().setFocus();
			return false;
		} else if (this.getForm().getTableSimulation().getSelectionIndex() == -1) {
			this.createErrorDialog(Messages.getString("UCM2DEVS_SelectArchitecture_ErrorDialog"));
			return false;
		} else {
			return true;
		}
	}

	/*
	 * Methods to print reports
	 */
	public Boolean printReportPerResponsibilityPerformance() {
		return this.getManager().createReport(PATHREPORT + "reportResponsibilityPerformance.jasper",
				"Report per Responsibility - Attribute: Performance", this.getManager().listResponsibilityPerformance(),
				false);
	}

	public Boolean printReportPerResponsibilityReliability() {
		return this.getManager().createReport(PATHREPORT + "reportResponsibilityReliability.jasper",
				"Report per Responsibility - Attribute: Reliability", this.getManager().listResponsibilityReliability(),
				false);
	}

	public Boolean printReportPerResponsibilityAvailability() {
		return this.getManager().createReport(PATHREPORT + "reportResponsibilityAvailability.jasper",
				"Report per Responsibility - Attribute: Availability",
				this.getManager().listResponsibilityAvailability(), true);
	}

	public Boolean printReportPerSystemAvailability() {
		return this.getManager().createReport(PATHREPORT + "reportSystemAvailability.jasper",
				"Report of System - Attribute: Availability", this.getManager().listSystemAvailability(), true);
	}

	public Boolean printReportPerSystemReliability() {
		return this.getManager().createReport(PATHREPORT + "reportSystemReliability.jasper",
				"Report of System - Attribute: Reliability", this.getManager().listSystemReliability(), false);
	}

	public Boolean printReportPerSystemPerformance() {
		return this.getManager().createReport(PATHREPORT + "reportSystemPerformance.jasper",
				"Report of System - Attribute: Performance", this.getManager().listSystemPerformance(), true);
	}

	/**
	 * Sets the model table of the software architecture of a specific system
	 * 
	 * @param ptype
	 */
	public void setModelSimulation(DomainModel.AnalysisEntity.System ptype) {
		this.getManager().setSystem(ptype);
		while (this.getForm().getTableSimulation().getItems().length > 0) {
			this.getForm().getTableSimulation().remove(0);
		}
		if (!this.getManager().getArchitectures().isEmpty()) {
			for (Architecture arc : this.getManager().getArchitectures()) {
				if (arc.getSimulator() != null) {
					addToTable(arc);
				}
			}
		}
	}

	public void addToTable(Architecture arc) {
		TableItem item = new TableItem(this.getForm().getTableSimulation(), SWT.NONE);
		item.setData(arc);

		item.setText(new String[] { arc.toString(), arc.getPathUCM().substring(arc.getPathUCM().lastIndexOf("\\") + 1),
				arc.getPathUCM().substring(0, arc.getPathUCM().lastIndexOf("\\")), });
	}

	public void setModelQualityRequirement() {
		TableItem item = this.getForm().getTableSimulation()
				.getItem(this.getForm().getTableSimulation().getSelectionIndex());
		Simulator simulator = this.getManager().SimulatorBySystem(item.getText(0));
		setModelQualityRequirement(simulator);
	}

	public void setModelQualityRequirementFirst() {
		TableItem item = this.getForm().getTableSimulation().getItem(0);

		Simulator simulator = this.getManager().SimulatorBySystem(item.getText(0));
		setModelQualityRequirement(simulator);
	}

	/**
	 * Sets the model table of the quality requirements of a specific system
	 * 
	 * @param ptype
	 */
	public void setModelQualityRequirement(Simulator simulator) {
		while (this.getForm().getTableQualityRequirement().getItems().length > 0) {
			this.getForm().getTableQualityRequirement().remove(0);
		}
		for (QualityRequirement dp : simulator.getRequirements()) {

			if (dp.isState()) {
				TableItem item = new TableItem(this.getForm().getTableQualityRequirement(), SWT.NONE);
				item.setData(dp);
				item.setText(new String[] { dp.toString(), dp.getQualityScenario().getQualityAttribute().toString(),
						dp.getQualityScenario().getDescription().toString() });
			}
		}
	}

	public void setQualityAttribute(QualityRequirement qr) {
		manager.setQualityAttribute(qr.getQualityScenario().getQualityAttribute());
	}

	public void setQualityRequirement(QualityRequirement qr) {
		manager.setQualityRequirement(qr);
	}

	public void setModelReport() {
		TableItem item = this.getForm().getTableQualityRequirement()
				.getItem(this.getForm().getTableQualityRequirement().getSelectionIndex());
		this.setModelReport(this.getManager().getQualityRequirementBySystem(item.getText(0)));
	}

	public void setModelReport(QualityRequirement qualityRequirement) {
		for (int i = 0; i < this.getForm().getTableReport().getItemCount(); i++) {
			TableItem item = this.getForm().getTableReport().getItem(i);
			item.setText(0, qualityRequirement.getQualityScenario().getQualityAttribute().getName());
		}
	}

	public void printReportResponsability() {
		String qualityAttribute = this.getManager().getQualityAttribute().getName();
		switch (qualityAttribute) {
		case "Performance":
			printReportPerResponsibilityPerformance();
			break;
		case "Availability":
			printReportPerResponsibilityAvailability();
			break;
		case "Reliability":
			printReportPerResponsibilityReliability();
			break;
		}
	}

	public void printReportSystem() {
		String qualityAttribute = this.getManager().getQualityAttribute().getName();
		switch (qualityAttribute) {
		case "Performance":
			printReportPerSystemPerformance();
			break;
		case "Availability":
			printReportPerSystemAvailability();
			break;
		case "Reliability":
			printReportPerSystemReliability();
			break;
		}

	}
	
	public Boolean isConnection() {
		return this.getManager().isConnection();
	}

}
