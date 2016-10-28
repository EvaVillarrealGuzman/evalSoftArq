package Presentation.controllerReports;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TableItem;

import BusinessLogic.ReportManager;
import DomainModel.AnalysisEntity.QualityAttribute;
import DomainModel.AnalysisEntity.QualityRequirement;
import DomainModel.AnalysisEntity.Tactic;
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
	/**
	 * Validate the necessary data for save the UCM path
	 * 
	 * @return boolean (is true if they have completed the required fields)
	 */
	public boolean isValidData() {
		if (this.isEmpty(this.getForm().getCboSystem())) {
			this.createErrorDialog(Messages.getString("UCM2DEVS_SelectSystem_ErrorDialog"));
			this.getForm().getCboSystem().getCombo().setFocus();
			return false;
		} else if (this.getForm().getTableSimulation().getSelectionIndex() == -1) {
			this.createErrorDialog(Messages.getString("UCM2DEVS_SelectArchitecture_ErrorDialog"));
			return false;
		} else {
			return true;
		}
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

	public Boolean printReportPerSystemAvailability() {
		try {
			this.openReport(this.PATHREPORT + "reportSystemAvailability.jasper");
			this.addParameterToReport("title", "Report of System - Attribute: Availability");
			this.addParameterToReport("tactics", this.getTacticsOfAvailability());
			// Agrega los datos al reporte
			this.getManager().setDataCollection(this.getManager().listSystemAvailability());
			// imprime el reporte
			this.getManager().print();
			this.getManager().visibleReport();
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	public Boolean printReportPerSystemReliability() {
		try {
			this.openReport(this.PATHREPORT + "reportSystemReliability.jasper");
			this.addParameterToReport("title", "Report of System - Attribute: Reliability");
			this.addParameterToReport("tactics", this.getTacticsOfReliability());
			// Agrega los datos al reporte
			this.getManager().setDataCollection(this.getManager().listSystemReliability());
			// imprime el reporte
			this.getManager().print();
			this.getManager().visibleReport();
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	public Boolean printReportPerSystemPerformance() {
		try {
			this.openReport(this.PATHREPORT + "reportSystemPerformance.jasper");
			this.addParameterToReport("title", "Report of System - Attribute: Performance");
			this.addParameterToReport("tactics", this.getTacticsOfPerformance());

			// Agrega los datos al reporte
			this.getManager().setDataCollection(this.getManager().listSystemPerformance());
			// imprime el reporte
			this.getManager().print();
			this.getManager().visibleReport();
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	public QualityAttribute getQualityAttribute(int qa) {
		QualityAttribute[] qas = this.manager.getQualityAttributes();
		if (qas[0].getName().equals("Performance")) {
			if (qa == 2) {
				return qas[0];
			}
		} else if (qas[0].getName().equals("Availability")) {
			if (qa == 1) {
				return qas[0];
			}
		} else {
			if (qa == 0) {
				return qas[0];
			}
		}
		if (qas[1].getName().equals("Performance")) {
			if (qa == 2) {
				return qas[1];
			}
		} else if (qas[1].getName().equals("Availability")) {
			if (qa == 1) {
				return qas[1];
			}
		} else {
			if (qa == 0) {
				return qas[1];
			}
		}
		if (qas[3].getName().equals("Performance")) {
			if (qa == 2) {
				return qas[3];
			}
		} else if (qas[3].getName().equals("Availability")) {
			if (qa == 1) {
				return qas[3];
			}
		} else {
			if (qa == 0) {
				return qas[3];
			}
		}
		return null;
	}

	public List<String> getTacticsOfPerformance() {
		QualityAttribute q = this.getQualityAttribute(2);
		ArrayList<String> tactics = new ArrayList<String>();
		Iterator t = q.getTactics().iterator();
		while (t.hasNext()) {
			Tactic tc = (Tactic) t.next();
			tactics.add(tc.getName());
		}
		return tactics;
	}

	public List<String> getTacticsOfAvailability() {
		QualityAttribute q = this.getQualityAttribute(0);
		ArrayList<String> tactics = new ArrayList<String>();
		Iterator t = q.getTactics().iterator();
		while (t.hasNext()) {
			Tactic tc = (Tactic) t.next();
			tactics.add(tc.getName());
		}
		return tactics;
	}

	public List<String> getTacticsOfReliability() {
		QualityAttribute q = this.getQualityAttribute(1);
		ArrayList<String> tactics = new ArrayList<String>();
		Iterator t = q.getTactics().iterator();
		while (t.hasNext()) {
			Tactic tc = (Tactic) t.next();
			tactics.add(tc.getName());
		}
		return tactics;
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
					addToTable(arc.getPathUCM(), arc.toString());
				}
			}
		}
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

	public void addToTable(String namePath, String toString) {
		TableItem item = new TableItem(this.getForm().getTableSimulation(), SWT.NONE);
		item.setData(namePath);

		item.setText(new String[] { toString, namePath.substring(namePath.lastIndexOf("\\") + 1),
				namePath.substring(0, namePath.lastIndexOf("\\")), });
	}

	public Boolean isConnection() {
		return this.getManager().isConnection();
	}

	public void printReportResponsability() {
		TableItem item = this.getForm().getTableQualityRequirement().getItem(0);
		String qualityAttribute = item.getText(0);
		switch (qualityAttribute) {
		case "Performance":// No system with architecture or quality requirement
			printReportPerResponsibilityPerformance();
			break;
		case "Availability": // There are systems with architecture or quality requirement
			printReportPerResponsibilityAvailability();
			break;
		case "Reliability": // With system selected
			printReportPerResponsibilityReliability();
			break;
		}
	}

	public void printReportSystem() {
		TableItem item = this.getForm().getTableQualityRequirement().getItem(1);
		String qualityAttribute = item.getText(1);
		switch (qualityAttribute) {
		case "Performance":// No system with architecture or quality requirement
			printReportPerSystemPerformance();
			break;
		case "Availability": // There are systems with architecture or quality requirement
			printReportPerSystemAvailability();
			break;
		case "Reliability": // With system selected
			printReportPerSystemReliability();
			break;
		}
		
	}

}
