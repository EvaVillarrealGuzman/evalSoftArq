package project.preferences.controller;

import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.PreferencesUtil;

import project.preferences.SystemPreferencePage;
import software.BusinessLogic.AnalysisManager;
import software.Presentation.ControllerAnalysis.SystemManagementController;
import software.Presentation.GUIAnalysis.FrmSystemManagement;

public class SystemPPController extends Controller {

	/**
	 * Attributes
	 */
	private static SystemPPController controller;
	private AnalysisManager manager;
	private SystemPreferencePage form;

	/**
	 * Getters and Setters
	 */
	public static SystemPPController getController() {
		return controller;
	}

	public static void setController(SystemPPController controller) {
		SystemPPController.controller = controller;
	}

	public AnalysisManager getManager() {
		return manager;
	}

	public void setManager(AnalysisManager manager) {
		this.manager = manager;
	}

	public SystemPreferencePage getForm() {
		return form;
	}

	public void setForm(SystemPreferencePage form) {
		this.form = form;
	}

	public void save() {
		int err;
		err = this.setSystem();
		if (err == 0) {
		//TODO descomentar	this.getManager().saveSystem();
		}
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
	 * Update a system
	 */
	public int setSystem() {
		if (this.isValidData()) {
			this.getManager().setSystemName(
					((IStructuredSelection) this.getForm().getCboSystem().getSelection()).getFirstElement().toString());
			this.getManager().setProjectName(this.getForm().getProjectName().getStringValue());
			this.getManager().setStartDate(this.getForm().getCalendarStartDate());
			this.getManager().setStartDate(this.getForm().getCalendarStartDate());
			this.getManager().setFinishDate(this.getForm().getCalendarFinishDate());
			return 0;
		} else {
			return 1;
		}
	}

	// TODO ver los showoptioDialog
	/**
	 * return true if they have completed the required fields
	 */
	public boolean isValidData() {
		/*if (this.isEmpty(this.getForm().getCboSystem())) {
			JOptionPane.showOptionDialog(null, "System name empty", "Warning", JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.ERROR_MESSAGE,
					new ImageIcon(SystemPreferencePage.class.getResource("/Icons/error.png")), new Object[] { "OK" },
					"OK");
			// this.getForm().getCboSystem().grabFocus();
			return false;
		}*/
		if (this.isEmpty(this.getForm().getProjectName())) {
			JOptionPane.showOptionDialog(null, "Empty project name", "Warning", JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.ERROR_MESSAGE,
					new ImageIcon(SystemPreferencePage.class.getResource("/Icons/error.png")), new Object[] { "OK" },
					"OK");
			// this.getForm().getProjectName().grabFocus();
			return false;
		}
		/*
		 * if (this.getForm().getCalendarStartDate().get == null) {
		 * JOptionPane.showOptionDialog( null, "Start date invalid", "Warning",
		 * JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE, new
		 * ImageIcon(SystemPreferencePage.class
		 * .getResource("/Icons/error.png")), new Object[] { "OK" }, "OK");
		 * this.getFrmSystemManagement().getCalendarStartDate().grabFocus();
		 * return false; } if (this.getForm().getCalendarFinishDate().getDate()
		 * == null) { JOptionPane.showOptionDialog( null, "Finish date invalid",
		 * "Warning", JOptionPane.YES_NO_CANCEL_OPTION,
		 * JOptionPane.ERROR_MESSAGE, new ImageIcon(SystemPreferencePage.class
		 * .getResource("/Icons/error.png")), new Object[] { "OK" }, "OK");
		 * this.getForm().getCalendarFinishDate().grabFocus(); return false; }
		 * else if (this .getForm() .getCalendarFinishDate() .getDate()
		 * .before(this.getForm().getCalendarStartDate() .getDate())) {
		 * JOptionPane.showOptionDialog( null,
		 * "The finish date is less than the start date", "Warning",
		 * JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE, new
		 * ImageIcon(SystemPreferencePage.class
		 * .getResource("/Icons/error.png")), new Object[] { "OK" }, "OK");
		 * this.getFrmSystemManagement().getCalendarStartDate().grabFocus();
		 * return false; }
		 */
		return true;
	}

	/**
	 * Sets the model of system combo
	 */
	public void setModel(ComboViewer pcmb) {
		this.setModel((software.DomainModel.AnalysisEntity.System) ((IStructuredSelection) pcmb.getSelection())
				.getFirstElement());
	}

	private void setModel(software.DomainModel.AnalysisEntity.System pmodel) {
		this.getManager().setSystem(pmodel);
	}

}
