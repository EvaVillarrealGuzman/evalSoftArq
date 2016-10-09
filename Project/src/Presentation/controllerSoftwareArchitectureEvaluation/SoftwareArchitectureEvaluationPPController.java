package Presentation.controllerSoftwareArchitectureEvaluation;

import java.io.IOException;

import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TableItem;

import BusinessLogic.SoftwareArchitectureEvaluationManager;
import DomainModel.AnalysisEntity.QualityRequirement;
import DomainModel.SoftwareArchitectureSpecificationEntity.Architecture;
import Main.TransformerSimulator;
import Presentation.Controller;
import Presentation.preferenceSoftwareArchitectureEvaluation.SoftwareArchitectureEvaluationPreferencePage;

/**
 * Controller for SoftwareArchitectureEspecificationPreferencePage
 * 
 * @author FEM
 *
 */
public class SoftwareArchitectureEvaluationPPController extends Controller {

	/**
	 * Attributes
	 */
	private static SoftwareArchitectureEvaluationPPController controller;
	private SoftwareArchitectureEvaluationManager manager;
	private SoftwareArchitectureEvaluationPreferencePage form;

	/**
	 * Getters and Setters
	 */
	public static SoftwareArchitectureEvaluationPPController getController() {
		return controller;
	}

	public static void setController(SoftwareArchitectureEvaluationPPController controller) {
		SoftwareArchitectureEvaluationPPController.controller = controller;
	}

	public SoftwareArchitectureEvaluationManager getManager() {
		if (manager == null) {
			manager = new SoftwareArchitectureEvaluationManager();
		}
		return manager;
	}

	public void setManager(SoftwareArchitectureEvaluationManager manager) {
		this.manager = manager;
	}

	public SoftwareArchitectureEvaluationPreferencePage getForm() {
		return form;
	}

	public void setForm(SoftwareArchitectureEvaluationPreferencePage form) {
		this.form = form;
	}

	/**
	 * Sets the model of system combo
	 */
	public void setModel() {
		this.getForm().getCboSystem().setInput(getManager().getComboModelSystemWithArchitecture());
	}

	/**
	 * Sets the model of unit combo
	 */
	public void setModelUnit() {
		this.getForm().getCmbUnit().setInput(getManager().getComboModelUnit());
	}

	public void setModel(ComboViewer pcmb) {
		this.setModel(
				(DomainModel.AnalysisEntity.System) ((IStructuredSelection) pcmb.getSelection()).getFirstElement());
	}

	private void setModel(DomainModel.AnalysisEntity.System pmodel) {
		this.getManager().setSystem(pmodel);
	}

	/**
	 * Evaluate a architecture
	 * 
	 * @throws IOException
	 */
	public void evaluate() {
		try {
			TransformerSimulator pluginTS = new TransformerSimulator();

			TableItem item = this.getForm().getTableSoftArc()
					.getItem(this.getForm().getTableSoftArc().getSelectionIndex());
			String UCMpath = item.getText(2) + "\\" + item.getText(1);

			String chequerUCMResult = this.getManager().chequerUCM(UCMpath);

			if (chequerUCMResult.equals("")) {
				if (this.getManager().transformer(UCMpath)) {
					if (this.getManager().simulator()) {
						this.createSuccessDialog("The simulation is successful");
						this.getManager().setSystem((DomainModel.AnalysisEntity.System) ((IStructuredSelection) this
								.getForm().getCboSystem().getSelection()).getFirstElement());
						this.getManager().createSimulator(this.getForm().getSimulationTime().getStringValue());
						this.getManager().convertCSVToTable(
								"C:/Users/Micaela/Dropbox/PROYECTO FINAL/EJ. SALIDAS DE SIMULACIÓN/availability.csv");
					} else {
						this.createErrorDialog("The simulator is not successful");
					}
				} else {
					this.createErrorDialog("The transformer is not successful");
				}
			} else {
				this.createErrorDialog(chequerUCMResult);
			}
		} catch (IOException e) {
		}
	}

	/**
	 * Sets the model table of the software architecture of a specific system
	 * 
	 * @param ptype
	 */
	public void setModelPaths(DomainModel.AnalysisEntity.System ptype) {
		this.getManager().setSystem(ptype);
		while (this.getForm().getTableSoftArc().getItems().length > 0) {
			this.getForm().getTableSoftArc().remove(0);
		}
		if (!this.getManager().getArchitectures().isEmpty()) {
			for (Architecture dp : this.getManager().getArchitectures()) {
				addToTable(dp.getPathUCMs().get(0));
			}
		}
	}

	/**
	 * Sets the model table of the quality requirements of a specific system
	 * 
	 * @param ptype
	 */
	public void setModelQualityRequirement(DomainModel.AnalysisEntity.System ptype) { // NOPMD
																						// by
																						// Usuario-Pc
																						// on
																						// 10/06/16
																						// 21:46
		this.getManager().setSystem(ptype);
		while (this.getForm().getTableQR().getItems().length > 0) {
			this.getForm().getTableQR().remove(0);
		}
		for (QualityRequirement dp : this.getManager().getQualityRequirements()) {
			if (dp.isState()) {
				TableItem item = new TableItem(this.getForm().getTableQR(), SWT.NONE);
				item.setData(dp);
				item.setText(new String[] { null, dp.getQualityScenario().getDescription().toString(),
						dp.getQualityScenario().getQualityAttribute().toString() });
			}
		}
	}

	public void addToTable(String namePath) {
		TableItem item = new TableItem(this.getForm().getTableSoftArc(), SWT.NONE);
		item.setData(namePath);

		item.setText(new String[] { "", namePath, namePath.substring(namePath.lastIndexOf("\\") + 1),
				namePath.substring(0, namePath.lastIndexOf("\\")), });
	}

	public Boolean isConnection() {
		return this.getManager().isConnection();
	}

}
