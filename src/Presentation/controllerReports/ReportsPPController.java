package Presentation.controllerReports;

import java.util.Iterator;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TableItem;

import BusinessLogic.ReportManager;
import DomainModel.AnalysisEntity.QualityRequirement;
import DomainModel.SoftwareArchitectureEvaluationEntity.Indicator;
import DomainModel.SoftwareArchitectureEvaluationEntity.Run;
import DomainModel.SoftwareArchitectureEvaluationEntity.Simulator;
import DomainModel.SoftwareArchitectureSpecificationEntity.Architecture;
import Presentation.Controller;
import Presentation.preferenceReports.ReportsPreferencePage;

/**
 * Controller for ReportPreferencePage
 * 
 * @author: Florencia Rossini. E-mail: flori.rossini@gmail.com
 */
public class ReportsPPController extends Controller {

	/**
	 * Attributes
	 */
	private static ReportsPPController controller;
	private ReportManager manager;
	private ReportsPreferencePage form;
	public static final String PATHREPORT = Platform.getInstallLocation().getURL().getPath()
			+ "plugins/SAE/reports/";

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

	/*
	 * Methods to print reports
	 */
	private Boolean printReportPerResponsibilityPerformance() {
		return this.getManager().createReport(PATHREPORT + "reportResponsibilityPerformance.jasper",
				"Report per Responsibility - Attribute: Performance", this.getManager().listResponsibilityPerformance(),
				true, false);
	}

	private Boolean printReportPerResponsibilityReliability() {
		return this.getManager().createReport(PATHREPORT + "reportResponsibilityReliability.jasper",
				"Report per Responsibility - Attribute: Reliability", this.getManager().listResponsibilityReliability(),
				false, false);
	}

	private Boolean printReportPerResponsibilityAvailability() {
		return this.getManager().createReport(PATHREPORT + "reportResponsibilityAvailability.jasper",
				"Report per Responsibility - Attribute: Availability",
				this.getManager().listResponsibilityAvailability(), true, false);
	}

	private Boolean printReportPerSystemAvailability() {
		return this.getManager().createReport(PATHREPORT + "reportSystemAvailability.jasper",
				"Report of System - Attribute: Availability", this.getManager().listSystemAvailability(), true,
				this.isCumplimentRequirement());
	}

	private Boolean printReportPerSystemReliability() {
		return this.getManager().createReport(PATHREPORT + "reportSystemReliability.jasper",
				"Report of System - Attribute: Reliability", this.getManager().listSystemReliability(), false,
				this.isCumplimentRequirement());
	}

	public Boolean printReportPerSystemPerformance() {
//		return this.getManager().createReport(PATHREPORT + "reportSystemPerformance.jasper",
//				"Report of System - Attribute: Performance", this.getManager().listSystemPerformance(), true,
//				this.isCumplimentRequirement());
		if (this.getManager().getQualityRequirement().getQualityScenario().getResponseMeasure().getMetric().getName().equals("System Turnaround Time")){
			return this.getManager().createReport(PATHREPORT + "reportSystemPerformanceTurnaroundTime.jasper",
				"Report of System - Attribute: Performance", this.getManager().listSystemPerformanceTurnaroundTime(), true,
				this.isCumplimentRequirement());
		}else {
			return this.getManager().createReport(PATHREPORT + "reportSystemPerformanceThroughput.jasper",
					"Report of System - Attribute: Performance", this.getManager().listSystemPerformanceThroughput(), true,
					this.isCumplimentRequirement());
		}
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

	private void addToTable(Architecture arc) {
		TableItem item = new TableItem(this.getForm().getTableSimulation(), SWT.NONE);
		item.setData(arc);

		item.setText(new String[] { arc.toString(), arc.getPathUCM().substring(arc.getPathUCM().lastIndexOf("\\") + 1),
				arc.getPathUCM().substring(0, arc.getPathUCM().lastIndexOf("\\")), });
	}

	public void setModelQualityRequirement() {
		setModelQualityRequirement(this.getManager().getArchitecture().getSimulator());
	}

	/**
	 * Sets the model table of the quality requirements of a specific system
	 * 
	 * @param ptype
	 */
	private void setModelQualityRequirement(Simulator simulator) {
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
		this.getManager().setQualityAttribute(qr.getQualityScenario().getQualityAttribute());
	}

	public void setQualityRequirement(QualityRequirement qr) {
		this.getManager().setQualityRequirement(qr);
	}

	public void setModelReport() {
		for (int i = 0; i < this.getForm().getTableReport().getItemCount(); i++) {
			TableItem item = this.getForm().getTableReport().getItem(i);
			item.setText(0, this.getManager().getQualityAttribute().getName());
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

	private boolean isCumplimentRequirement() {
		boolean cumpliment = true;
		if (this.getManager().getQualityAttribute().getName().equals("Reliability")) {
			for (Iterator<Run> its = this.getManager().getArchitecture().getSimulator().getRuns().iterator(); its.hasNext();) {
				Run r = its.next();
				for (Iterator<Indicator> iti = r.getIndicators().iterator(); iti.hasNext();) {
					Indicator ind = iti.next();
					if (ind.getMetric().getName().equals("System Failures")) {
						if (this.getManager().getQualityRequirement().getQualityScenario().getResponseMeasure().getValue() < ind
								.getValue()) {
							cumpliment = false;
						}
					}
				}
			}
		} else if (this.getManager().getQualityAttribute().getName().equals("Availability")) {
			for (Iterator<Run> its = this.getManager().getArchitecture().getSimulator().getRuns().iterator(); its.hasNext();) {
				Run r = its.next();
				for (Iterator<Indicator> iti = r.getIndicators().iterator(); iti.hasNext();) {
					Indicator ind = iti.next();
					if (ind.getMetric().getName().equals("System Availability Time")) {
						if (this.getManager().getAvailabilityTimeRequirement() > this.getManager()
								.convertValueAcordingToUnitRequirement(ind.getValue(), ind.getUnit()))
							cumpliment = false;
					}
					if (ind.getMetric().getName().equals("System No-Availability Time")) {
						if (this.getManager().getNoAvailabilityTimeRequirement() < this.getManager()
								.convertValueAcordingToUnitRequirement(ind.getValue(), ind.getUnit())) {
							cumpliment = false;
						}
					}
				}
			}
		} else if (this.getManager().getQualityAttribute().getName().equals("Performance")) {
			for (Iterator<Run> its = this.getManager().getArchitecture().getSimulator().getRuns().iterator(); its.hasNext();) {
				Run r = its.next();
				for (Iterator<Indicator> iti = r.getIndicators().iterator(); iti.hasNext();) {
					Indicator ind = iti.next();
					if (ind.getMetric().getName().equals("System Throughput")) {
						if (this.getManager().getThroughputRequirement() < this.getManager()
								.convertValueAcordingToUnitRequirement(ind.getValue(), ind.getUnit())) {
							cumpliment = false;
						}
					}
					if (ind.getMetric().getName().equals("System Turnaround Time")) {
						if (this.getManager().getTurnaroundTimeRequirement() < this.getManager()
								.convertValueAcordingToUnitRequirement(ind.getValue(), ind.getUnit())) {
							cumpliment = false;
						}
					}
				}
			}
		}
		return cumpliment;
	}

	public boolean existSystemTrueWithQualityRequirementTrue() {
		return this.getManager().existSystemTrueWithQualityRequirementTrue();
	}

	public boolean existSystemTrueWithArchitecture() {
		return this.getManager().existSystemTrueWithArchitecture();
	}

}
