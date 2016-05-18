package software.Presentation;

import software.Presentation.ControllerAnalysis.QualityRequirementController;
import software.Presentation.ControllerAnalysis.SystemManagementController;

/**
 * This class is responsible for the management the form: MainMenu
 * 
 * @author: FEM
 * @version: 06/11/2015
 */

public class MainMenuController {

	/**
	 * Attributes
	 * 
	 */
	private FrmMainMenu frmMainMenu;
	private QualityRequirementController qualityRequirementController;
	private SystemManagementController systemManagementController;

	/**
	 * Getters and setters
	 * 
	 */
	public FrmMainMenu getFrmMainMenu() {
		frmMainMenu = FrmMainMenu.getFrmMainMenu(this);
		return frmMainMenu;
	}

	public void setFrmMainMenu(FrmMainMenu pfrmMainMenu) {
		this.frmMainMenu = pfrmMainMenu;
	}

	public QualityRequirementController getQualityRequirementcontroller() {
		qualityRequirementController = QualityRequirementController
				.getController();
		return qualityRequirementController;
	}

	public void setQualityRequirementcontroller(
			QualityRequirementController qualityRequirementController) {
		this.qualityRequirementController = qualityRequirementController;
	}

	public SystemManagementController getSystemManagementController() {
		systemManagementController = SystemManagementController.getController();
		return systemManagementController;
	}

	public void setSystemManagementcontroller(
			SystemManagementController systemManagementController) {
		this.systemManagementController = systemManagementController;
	}

	/**
	 * open the form MainMenu
	 */
	public void openFrmMainMenu() {
		this.getFrmMainMenu().setVisible(true);
	}

	/**
	 * open the form QualityRequirementManagement
	 */
	public void openFrmQualityRequirementManagement() {
		this.getQualityRequirementcontroller()
				.openFrmQualityRequirementManagement(3);
	}

	/**
	 * open the form SystemManagement
	 */
	public void openFrmSystemManagement() {
		this.getSystemManagementController().openFrmSystemManagement(3);
	}
}
