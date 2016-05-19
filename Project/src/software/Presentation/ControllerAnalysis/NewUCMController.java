package software.Presentation.ControllerAnalysis;

import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import software.BusinessLogic.AnalysisManager;
import software.Presentation.GUIAnalysis.FrmNewUCM;
import software.Presentation.GUIAnalysis.FrmSoftwareArchitectureSpecification;
import software.Presentation.GUIAnalysis.FrmSystemManagement;

/**
 * This class is responsible for the management of form:SystemManagement
 * 
 * @author: FEM
 * @version: 06/11/2015
 */
public class NewUCMController {

	/**
	 * Attributes
	 */
	private static NewUCMController controller;
	private FrmNewUCM frmNewUCM;

	/**
	 * Builder
	 */
	private NewUCMController() {
		super();
	}

	/**
	 * Get SystemManagementController. Applied Singleton pattern
	 */
	public static NewUCMController getController() {
		if (controller == null) {
			synchronized (NewUCMController.class) {
				controller = new NewUCMController();
			}
		}
		return controller;
	}

	/**
	 * Set SystemManagementController
	 */
	public static void setController(NewUCMController pcontroller) {
		NewUCMController.controller = pcontroller;
	}

	/**
	 * Getters and Setters
	 */
	public FrmNewUCM getFrmNewUCM() {
		frmNewUCM = FrmNewUCM.getFrmNewUCM(this);
		return frmNewUCM;
	}

	public void setFrmNewUCM(FrmNewUCM pfrmNewUCM) {
		this.frmNewUCM = pfrmNewUCM;
	}

	/**
	 * Open the form frmSoftwareArchitectureSpecification
	 * 
	 * @param pabm
	 */
	public void openfrmNewUCM() {
		this.getFrmNewUCM().setVisible(true);
	}

	// TODO comentar
	public void getPath() {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle("choosertitle");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);

		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
			System.out.println("getSelectedFile() : " + chooser.getSelectedFile());
		} else {
			System.out.println("No Selection ");
		}
	}
	
}
