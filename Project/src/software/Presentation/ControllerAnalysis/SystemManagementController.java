package software.Presentation.ControllerAnalysis;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.eclipse.jface.viewers.IStructuredSelection;

import software.BusinessLogic.AnalysisManager;
import software.Presentation.GUIAnalysis.FrmSystemManagement;

/**
 * This class is responsible for the management of form:SystemManagement
 * 
 * @author: FEM
 * @version: 06/11/2015
 */
public class SystemManagementController {

	/**
	 * Attributes
	 */
	private static SystemManagementController controller;
	private AnalysisManager manager;
	private FrmSystemManagement frmSystemManagement;
	public int opcABM;

	/**
	 * Builder
	 */
	private SystemManagementController() {
		super();
	}

	/**
	 * Get SystemManagementController. Applied Singleton pattern
	 */
	public static SystemManagementController getController() {
		if (controller == null) {
			synchronized (SystemManagementController .class){
				controller = new SystemManagementController();
			}
		}
		return controller;
	}

	/**
	 * Set SystemManagementController
	 */
	public static void setController(SystemManagementController pcontroller) {
		SystemManagementController.controller = pcontroller;
	}

	/**
	 * Getters and Setters
	 */
	public AnalysisManager getManager() {
		manager = AnalysisManager.getManager();
		return manager;
	}

	public void setManager(AnalysisManager pmanager) {
		this.manager = pmanager;
	}

	public FrmSystemManagement getFrmSystemManagement() {
		frmSystemManagement = FrmSystemManagement.getFrmSystemManagement(this);
		return frmSystemManagement;
	}

	public void setFrmSystemManagement(FrmSystemManagement pfrmSystemManagement) {
		this.frmSystemManagement = pfrmSystemManagement;
	}

	public int getOpcABM() {
		return opcABM;
	}

	public void setOpcABM(int popcABM) {
		this.opcABM = popcABM;
	}

	/**
	 * Open the form FrmSystemManagement
	 * 
	 * @param pabm
	 */
	public void openFrmSystemManagement(int pabm) {
		this.setOpcABM(pabm);
		this.getFrmSystemManagement().setVisible(true);
	}

	public int saveView() {
		int err;
		if (this.getOpcABM() == 0) {
			err = this.newSystem();
		} else {
			err = this.setSystem();
		}
		if (err == 0) {
			this.getManager().saveSystem(this.getOpcABM());
			this.getFrmSystemManagement().clearView();
			this.setOpcABM(3);
			this.getFrmSystemManagement().prepareView(this.getOpcABM());
		}
		return err;
	}

	/**
	 * Create a new system
	 */
	public int newSystem() {
		return opcABM;
		/*if (this.isValidData()) {
			/*this.getManager()
					.newSystem(
							this.getFrmSystemManagement().getTxtSystemName()
									.getText(),
							this.getFrmSystemManagement().getTxtProjectName()
									.getText(),
							this.getFrmSystemManagement()
									.getCalendarStartDate().getDate(),
							this.getFrmSystemManagement()
									.getCalendarFinishDate().getDate(), true);
			return 0;
		} else {
			return 1;
		}*/
		
	}

	/**
	 * Update a system
	 */
	public int setSystem() {
		/*if (this.isValidData()) {
			this.getManager().setSystemName(((IStructuredSelection) this.getFrmSystemManagement().getCboSystem().getSelection()).getFirstElement().toString() 
					.getTxtSystemName().getText());
			this.getManager()
					.setProjectName(
							this.getFrmSystemManagement().getTxtProjectName()
									.getText());
			this.getManager().setStartDate(
					this.getFrmSystemManagement().getCalendarStartDate()
							.getDate());
			this.getManager().setFinishDate(
					this.getFrmSystemManagement().getCalendarFinishDate()
							.getDate());
			return 0;
		} else {*/
			return 1;
	/*	}*/
	}

	/**
	 * return true if they have completed the required fields
	 */
	public boolean isValidData() {
		if (this.isEmpty(this.getFrmSystemManagement().getTxtSystemName())) {
			JOptionPane.showOptionDialog(
					null,
					"System name empty",
					"Warning",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.ERROR_MESSAGE,
					new ImageIcon(FrmSystemManagement.class
							.getResource("/Icons/error.png")),
					new Object[] { "OK" }, "OK");
			this.getFrmSystemManagement().getTxtSystemName().grabFocus();
			return false;
		}
		if (this.isEmpty(this.getFrmSystemManagement().getTxtProjectName())) {
			JOptionPane.showOptionDialog(
					null,
					"Empty project name",
					"Warning",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.ERROR_MESSAGE,
					new ImageIcon(FrmSystemManagement.class
							.getResource("/Icons/error.png")),
					new Object[] { "OK" }, "OK");
			this.getFrmSystemManagement().getTxtProjectName().grabFocus();
			return false;
		}
		if (this.getFrmSystemManagement().getCalendarStartDate().getDate() == null) {
			JOptionPane.showOptionDialog(
					null,
					"Start date invalid",
					"Warning",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.ERROR_MESSAGE,
					new ImageIcon(FrmSystemManagement.class
							.getResource("/Icons/error.png")),
					new Object[] { "OK" }, "OK");
			this.getFrmSystemManagement().getCalendarStartDate().grabFocus();
			return false;
		}
		if (this.getFrmSystemManagement().getCalendarFinishDate().getDate() == null) {
			JOptionPane.showOptionDialog(
					null,
					"Finish date invalid",
					"Warning",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.ERROR_MESSAGE,
					new ImageIcon(FrmSystemManagement.class
							.getResource("/Icons/error.png")),
					new Object[] { "OK" }, "OK");
			this.getFrmSystemManagement().getCalendarFinishDate().grabFocus();
			return false;
		} else if (this
				.getFrmSystemManagement()
				.getCalendarFinishDate()
				.getDate()
				.before(this.getFrmSystemManagement().getCalendarStartDate()
						.getDate())) {
			JOptionPane.showOptionDialog(
					null,
					"The finish date is less than the start date",
					"Warning",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.ERROR_MESSAGE,
					new ImageIcon(FrmSystemManagement.class
							.getResource("/Icons/error.png")),
					new Object[] { "OK" }, "OK");
			this.getFrmSystemManagement().getCalendarStartDate().grabFocus();
			return false;
		}
		return true;
	}

	/**
	 * Validate whether a JTextField is empty
	 * @param ptxt
	 */
	public boolean isEmpty(JTextField ptxt) {
		return (ptxt.getText().equals(""));
	}

	/**
	 * Validate whether a JComboBox is empty
	 * @param pcmb
	 */
	public boolean isEmpty(JComboBox pcmb) {
		return pcmb.getSelectedItem() == "";
	}

	/**
	 * Sets the model of system combo
	 */
	public void setModelSystem() {
		this.getFrmSystemManagement().getCmbSystemName()
				.setModel(getManager().getComboModelSystem());
	}

	public void setModel(JComboBox pcmb) {
		this.setModel((software.DomainModel.AnalysisEntity.System) pcmb
				.getSelectedItem());
	}

	private void setModel(software.DomainModel.AnalysisEntity.System pmodel) {
		this.getManager().setSystem(pmodel);
	}

	public void getView() {
		this.getFrmSystemManagement().getTxtSystemName()
				.setText(this.getManager().getSystemName());
		this.getFrmSystemManagement().getTxtProjectName()
				.setText(this.getManager().getProjectName());
		/*this.getFrmSystemManagement().getCalendarStartDate()
				.setDate(this.getManager().getStartDate());
		this.getFrmSystemManagement().getCalendarFinishDate()
				.setDate(this.getManager().getFinishDate());*/
	}

	public void removeView() {
		this.getManager().removeSystem();
		this.getFrmSystemManagement().clearView();
		this.setOpcABM(3);
		this.getFrmSystemManagement().prepareView(this.getOpcABM());
	}
}
