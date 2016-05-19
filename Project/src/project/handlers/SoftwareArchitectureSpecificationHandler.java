package project.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import software.Presentation.ControllerAnalysis.NewUCMController;
import software.Presentation.ControllerAnalysis.SoftwareArchitectureSpecificationManagementController;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class SoftwareArchitectureSpecificationHandler extends AbstractHandler {
	private SoftwareArchitectureSpecificationManagementController softwareArchitectureSpecificationManagementController;
	private NewUCMController newUCMController;
	
	/**
	 * The constructor.
	 */
	public SoftwareArchitectureSpecificationHandler() {

	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		//this.getSoftwareArchitectureSpecificationManagementController().openfrmSoftwareArchitectureSpecification();
		this.getNewUCMControllerr().openfrmNewUCM();
		return null;
	}
	
	public SoftwareArchitectureSpecificationManagementController getSoftwareArchitectureSpecificationManagementController() {
		softwareArchitectureSpecificationManagementController = SoftwareArchitectureSpecificationManagementController.getController();
		return softwareArchitectureSpecificationManagementController;
	}
	
	
	public NewUCMController getNewUCMControllerr() {
		newUCMController = NewUCMController.getController();
		return newUCMController;
	}


}
