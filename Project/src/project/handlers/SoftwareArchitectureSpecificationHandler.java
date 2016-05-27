package project.handlers;

import java.io.ByteArrayInputStream;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.internal.resources.File;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

import seg.jUCMNav.editors.UCMNavMultiPageEditor;
import seg.jUCMNav.views.wizards.importexport.jUCMNavLoader;
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
	
	 private UCMNavMultiPageEditor editor;
	 private IFile testfile;
	 private IWorkbenchPage page;
	 private boolean jUCMNav = false;
	
    public void openJUCMNav() throws Exception {
    	
        IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
        IProject testproject = workspaceRoot.getProject("jUCMNav-tests"); //$NON-NLS-1$
        if (!testproject.exists())
            testproject.create(null);

        if (!testproject.isOpen())
            testproject.open(null);

        IFile testfile = testproject.getFile("jUCMNav-GRL-test.jucm"); //$NON-NLS-1$

        // start with clean file
        if (testfile.exists())
            testfile.delete(true, false, null);

        testfile.create(new ByteArrayInputStream("".getBytes()), false, null); //$NON-NLS-1$
        
        //IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
        IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(testfile.getName());
        editor = (UCMNavMultiPageEditor) page.openEditor(new FileEditorInput(testfile), desc.getId());
        System.out.print("asdfasfdsaf");

    }
    
    public void newJUCMNav() throws Exception {
    	
    	   String sFileName = "cs-broker.jucm";
          String sContainer = "C:/Users/Usuario-Pc/Desktop/runtime-EclipseApplication/jUCMNav-tests";
          jUCMNavLoader loader = new jUCMNavLoader(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage(), null);
          IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
          IResource resource = root.findMember(new Path(loader.getTargetFilename(sFileName, sContainer, true)));
          if (resource != null) {

          }

    }
	
	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			this.newJUCMNav();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		this.getNewUCMControllerr().openfrmNewUCM();
		if(jUCMNav){
			try {
				this.openJUCMNav();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
		 /*Shell activeShell = HandlerUtil.getActiveShell(event);

		  IWizard wizard = new seg.jUCMNav.views.wizards.NewUcmFileWizard();

		  WizardDialog dialog = new WizardDialog(activeShell, wizard);

		  dialog.open();

		  return null;*/
	}
	
	public SoftwareArchitectureSpecificationManagementController getSoftwareArchitectureSpecificationManagementController() {
		softwareArchitectureSpecificationManagementController = SoftwareArchitectureSpecificationManagementController.getController();
		return softwareArchitectureSpecificationManagementController;
	}
	
	
	public NewUCMController getNewUCMControllerr() {
		newUCMController = NewUCMController.getController(this);
		return newUCMController;
	}

	public boolean isjUCMNav() {
		return jUCMNav;
	}

	public void setjUCMNav(boolean jUCMNav) {
		this.jUCMNav = jUCMNav;
	}

}
