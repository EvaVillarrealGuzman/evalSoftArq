package software.Presentation.ControllerAnalysis;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import software.BusinessLogic.AnalysisManager;
import software.Presentation.GUIAnalysis.FrmSoftwareArchitectureSpecification;
import software.Presentation.GUIAnalysis.FrmSystemManagement;

/**
 * This class is responsible for the management of form:SystemManagement
 * 
 * @author: FEM
 * @version: 06/11/2015
 */
public class SoftwareArchitectureSpecificationManagementController {
	
	/**
	 * Attributes
	 */
	private static SoftwareArchitectureSpecificationManagementController controller;
	private FrmSoftwareArchitectureSpecification frmSoftwareArchitectureSpecification;
	
	/**
	 * Builder
	 */
	private SoftwareArchitectureSpecificationManagementController() {
		super();
	}
	
	/**
	 * Get SystemManagementController. Applied Singleton pattern
	 */
	public static SoftwareArchitectureSpecificationManagementController getController() {
		if (controller == null) {
			synchronized (SoftwareArchitectureSpecificationManagementController .class){
				controller = new SoftwareArchitectureSpecificationManagementController();
			}
		}
		return controller;
	}
	

	/**
	 * Set SystemManagementController
	 */
	public static void setController(SoftwareArchitectureSpecificationManagementController pcontroller) {
		SoftwareArchitectureSpecificationManagementController.controller = pcontroller;
	}

	
	/**
	 * Getters and Setters
	 */
	public FrmSoftwareArchitectureSpecification getFrmSoftwareArchitectureSpecification() {
		frmSoftwareArchitectureSpecification = FrmSoftwareArchitectureSpecification.getFrmSoftwareArchitectureSpecificationManagement(this);
		return frmSoftwareArchitectureSpecification;
	}

	public void setFrmSoftwareArchitectureSpecification(
			FrmSoftwareArchitectureSpecification frmSoftwareArchitectureSpecification) {
		this.frmSoftwareArchitectureSpecification = frmSoftwareArchitectureSpecification;
	}
	
	/**
	 * Open the form frmSoftwareArchitectureSpecification
	 * 
	 * @param pabm
	 */
	public void openfrmSoftwareArchitectureSpecification( ) {
		this.getFrmSoftwareArchitectureSpecification().setVisible(true);
	}


	
}
