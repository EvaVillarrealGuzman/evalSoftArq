package Presentation.controllerSoftwareArchitectureEvaluation;

import java.io.IOException;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import BusinessLogic.SoftwareArchitectureEvaluationManager;
import DomainModel.AnalysisEntity.QualityRequirement;
import DomainModel.AnalysisEntity.Unit;
import DomainModel.SoftwareArchitectureSpecificationEntity.Architecture;
import Main.TransformerSimulator;
import Presentation.Controller;
import Presentation.preferenceSoftwareArchitectureEvaluation.SoftwareArchitectureEvaluationPreferencePage;
import Presentation.preferences.Messages;

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
	public int evaluate() throws IOException {
		if (isValidData()) {
			try {
				this.getManager().setArchitecture((Architecture) this.getForm().getTableSoftArc()
						.getItem(this.getForm().getTableSoftArc().getSelectionIndex()).getData());
				
				TransformerSimulator pluginTS = new TransformerSimulator();

				TableItem item = this.getForm().getTableSoftArc()
						.getItem(this.getForm().getTableSoftArc().getSelectionIndex());
				String UCMpath = item.getText(2) + "\\" + item.getText(1);

				String chequerUCMResult = this.getManager().chequerUCM(UCMpath);

				if (chequerUCMResult.equals("")) {
					if (this.getManager().transformer(UCMpath)) {

						if (this.getManager().simulator(this.getForm().getSimulationTime().getDoubleValue(),
								(Unit) ((IStructuredSelection) this.getForm().getCmbUnit().getSelection())
										.getFirstElement())) {
							this.getManager().setSystem((DomainModel.AnalysisEntity.System) ((IStructuredSelection) this
									.getForm().getCboSystem().getSelection()).getFirstElement());
							
							this.getManager().createSimulator();
							for (int i = 1; i <= 10; i++) {
								String num = Integer.toString(i);
								this.getManager().createRun(this.getForm().getSimulationTime().getStringValue(),
										this.getForm().getTable());
								this.getManager().convertCSVToTable(Platform.getInstallLocation().getURL().getPath()
										+ "plugins/UCM2DEVS/Run/Run" + num + "/performance.csv");
								this.getManager().convertCSVToTable(Platform.getInstallLocation().getURL().getPath()
										+ "plugins/UCM2DEVS/Run/Run" + num + "/availability.csv");
								this.getManager().convertCSVToTable(Platform.getInstallLocation().getURL().getPath()
										+ "plugins/UCM2DEVS/Run/Run" + num + "/reliability.csv");
							}
							this.createSuccessDialog("The simulation is successful");
							return 0;
						} else {
							this.createErrorDialog("The simulator is not successful");
							return 1;
						}
					} else {
						this.createErrorDialog("The transformer is not successful");
						return 1;
					}
				} else {
					this.createErrorDialog(chequerUCMResult);
					return 1;
				}
			} catch (IOException e) {
				return 1;
			}
		}
		return 2;
	}

	public boolean isValidData() {
		if (this.isEmpty(this.getForm().getCboSystem())) {
			this.createErrorDialog(Messages.getString("UCM2DEVS_SelectSystem_ErrorDialog"));
			this.getForm().getCboSystem().getCombo().setFocus();
			return false;
		} else if (this.getForm().getTableSoftArc().getSelectionIndex() == -1) {
			this.createErrorDialog(Messages.getString("UCM2DEVS_SelectArchitecture_ErrorDialog"));
			return false;
		} else if (this.isNotChecked(this.getForm().getTable())) {
			this.createErrorDialog(Messages.getString("UCM2DEVS_SelectRequirement_ErrorDialog"));
			return false;
		} else if (this.isEmpty(this.getForm().getSimulationTime())) {
			this.createErrorDialog(Messages.getString("UCM2DEVS_InputSimulationTime_ErrorDialog"));
			return false;
		} else if (!this.getForm().getSimulationTime().isValid()) {
			this.createErrorDialog(Messages.getString("UCM2DEVS_InvalidSimulationTime_ErrorDialog"));
			return false;
		} else if (this.isEmpty(this.getForm().getCmbUnit())) {
			this.createErrorDialog(Messages.getString("UCM2DEVS_SelectUnit_ErrorDialog"));
			this.getForm().getCmbUnit().getCombo().setFocus();
			return false;
		} else {
			return true;
		}
	}

	public boolean isNotChecked(Table ptable) {
		TableItem[] items = ptable.getItems();
		for (int i = 0; i < items.length; i++) {
			TableItem item = items[i];
			if (item.getChecked()) {
				return false;
			}
		}
		return true;
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
				this.addToTable(dp);
			}
		}
	}

	/**
	 * Sets the model table of the quality requirements of a specific system
	 * 
	 * @param ptype
	 */
	public void setModelQualityRequirement(DomainModel.AnalysisEntity.System ptype) {
		this.getManager().setSystem(ptype);
		while (this.getForm().getTable().getItems().length > 0) {
			this.getForm().getTable().remove(0);
		}
		for (QualityRequirement dp : this.getManager().getQualityRequirements()) {
			if (dp.isState()) {
				TableItem item = new TableItem(this.getForm().getTable(), SWT.NONE);
				item.setData(dp);
				item.setText(new String[] { "", dp.toString(), dp.getQualityScenario().getQualityAttribute().toString(),
						dp.getQualityScenario().getDescription().toString() });
			}
		}
	}

	public void addToTable(Architecture arch) {
		TableItem item = new TableItem(this.getForm().getTableSoftArc(), SWT.NONE);
		item.setData(arch);

		item.setText(
				new String[] { arch.toString(), arch.getPathUCM().substring(arch.getPathUCM().lastIndexOf("\\") + 1),
						arch.getPathUCM().substring(0, arch.getPathUCM().lastIndexOf("\\")), });
	}

	public Boolean isConnection() {
		return this.getManager().isConnection();
	}

}