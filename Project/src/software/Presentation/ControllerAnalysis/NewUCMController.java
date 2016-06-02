package software.Presentation.ControllerAnalysis;

import java.io.File;
import java.io.IOException;
import org.eclipse.jface.window.Window;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;
import org.eclipse.ui.part.FileEditorInput;

import project.handlers.SoftwareArchitectureSpecificationHandler;
import seg.jUCMNav.editors.UCMNavMultiPageEditor;
import software.BusinessLogic.AnalysisManager;
import software.Presentation.GUIAnalysis.FrmNewUCM;
import software.Presentation.GUIAnalysis.FrmSoftwareArchitectureSpecification;
import software.Presentation.GUIAnalysis.FrmSystemManagement;

/**
 * This class is responsible for the management of form:SystemManagement
 * 
 * @author: FEM
 * @version: 19/05/2016
 */
public class NewUCMController {

	/**
	 * Attributes
	 */
	private static NewUCMController controller;
	private FrmNewUCM frmNewUCM;
	private SoftwareArchitectureSpecificationHandler handler;


	/**
	 * Builder
	 */
	private NewUCMController(SoftwareArchitectureSpecificationHandler phandler) {
		this.setHandler(phandler);
		frmNewUCM = new FrmNewUCM(this);
	}

	/**
	 * Get SystemManagementController. Applied Singleton pattern
	 */
	public static NewUCMController getController(SoftwareArchitectureSpecificationHandler phandler) {
		if (controller == null) {
			synchronized (NewUCMController.class) {
				controller = new NewUCMController(phandler);
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
		return frmNewUCM;
	}

	public void setFrmNewUCM(FrmNewUCM pfrmNewUCM) {
		this.frmNewUCM = pfrmNewUCM;
	}
	

	public SoftwareArchitectureSpecificationHandler getHandler() {
		return handler;
	}

	public void setHandler(SoftwareArchitectureSpecificationHandler handler) {
		this.handler = handler;
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
		FileNameExtensionFilter xmlfilter = new FileNameExtensionFilter(
			     "jucm files (*.jucm)", "jucm");
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle("choosertitle");
		//chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.setFileFilter(xmlfilter);
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			this.getFrmNewUCM().getTxtProjectName().setText(chooser.getSelectedFile().toString());

		}
	}
	
	public void openJUCMNav() throws Exception{
	//	this.getHandler().openJUCMNav();
		this.getFrmNewUCM().dispose();
	}
	

	/*public void openJUCMNav() throws CoreException {
		IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		IProject testproject = workspaceRoot.getProject("jUCMNav-tests"); //$NON-NLS-1$
		if (!testproject.exists())
			testproject.create(null);

		if (!testproject.isOpen())
			testproject.open(null);

		IFile testfile = testproject.getFile("jUCMNav-test.jucm"); //$NON-NLS-1$
		 
		testfile = testproject.getFile("jUCMNav-test.jucm");
		IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(testfile.getName());
		//IWorkbenchPage page = this.getFrmNewUCM().getPage();
		//finaliza la ejecución del programa
		 this.getFrmNewUCM().dispose();
		 
		//abre el editor de jUCMNav
		UCMNavMultiPageEditor editor = (UCMNavMultiPageEditor) page.openEditor(new FileEditorInput(testfile), desc.getId());
	}*/
	
	

}
