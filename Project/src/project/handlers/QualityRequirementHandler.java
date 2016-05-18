package project.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import software.Presentation.ControllerAnalysis.SystemManagementController;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class QualityRequirementHandler extends AbstractHandler {
	private SystemManagementController systemManagementController;
	/**
	 * The constructor.
	 */
	public QualityRequirementHandler() {

	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		this.getSystemManagementController().openFrmSystemManagement(3);
		return null;
	}
	
	public SystemManagementController getSystemManagementController() {
		systemManagementController = SystemManagementController.getController();
		return systemManagementController;
	}
	
}
