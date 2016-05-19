package project.preferences;

import javax.swing.JTextField;

import org.apache.log4j.chainsaw.Main;
import org.eclipse.jface.preference.*;
import org.eclipse.swt.widgets.Button;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;

/**
 * This class represents a preference page that is contributed to the
 * Preferences dialog. By subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows us to create a page
 * that is small and knows how to save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They are stored in the
 * preference store that belongs to the main plug-in class. That way,
 * preferences can be accessed directly via the preference store.
 */

public class SystemPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	private Composite composite;

	private DateTime calendarStartDate;
	private DateTime calendarFinishDate;
	private GridData gd;
	private GridData gridData;
	private Label lblSystem;
	private Combo cboSystem;
	private Group groupProject;
	private Label lblProject;
	private Label lblCalendarStarDate;
	private Label lblCalendarFinishDate;
	private Button btnSave;
	private Button btnRemove;
	private Text txtProjectName;

	public SystemPreferencePage() {
		super(GRID);
		// setPreferenceStore(Activator.getDefault().getPreferenceStore());
		// setDescription("");
	}

	protected Control createContents(Composite parent) {

		gd = new GridData(SWT.FILL, SWT.FILL, true, true);

		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.widthHint = 100;

		lblSystem = new Label(parent, SWT.LEFT | SWT.WRAP);
		lblSystem.setText("System Name: ");
		lblSystem.setLayoutData(gridData);

		cboSystem = new Combo(parent, SWT.READ_ONLY);
		cboSystem.setLayoutData(gridData);

		groupProject = new Group(parent, SWT.NONE);
		groupProject.setText(" Project ");
		gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		gridData.horizontalSpan = 2;
		groupProject.setLayoutData(gridData);
		groupProject.setLayout(new RowLayout(SWT.VERTICAL));

		lblProject = new Label(groupProject, SWT.NONE);
		lblProject.setText("Project Name: ");

		txtProjectName = new Text(groupProject, SWT.WRAP | SWT.BORDER);
		
		lblCalendarStarDate = new Label(groupProject, SWT.NONE);
		lblCalendarStarDate.setText("Start Date");

		calendarStartDate = new DateTime(groupProject, SWT.DATE | SWT.DROP_DOWN);

		lblCalendarFinishDate = new Label(groupProject, SWT.NONE);
		lblCalendarFinishDate.setText("Finish Date");

		calendarFinishDate = new DateTime(groupProject, SWT.DATE | SWT.DROP_DOWN);

		// TODO implementar

		/*Display display = new Display();
		Image image = new Image(display, System.getProperty("user.dir") + "/icons/remove.png");*/

		btnSave = new Button(parent, SWT.PUSH);
		btnSave.setImage(getImage("/Icons/Save.png"));
		btnSave.setText("Save");

		btnRemove = new Button(parent, SWT.PUSH);
		btnRemove.setImage(getImage("/Icons/remove.png"));
		btnRemove.setText("Remove");

		composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		composite.setLayout(new GridLayout(2, false));

		return composite;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}

	@Override
	protected void createFieldEditors() {
		// TODO Auto-generated method stub

	}

	//TODO ver de sacar afuera
	private static Image getImage(String imageName) {
		ImageData source = new ImageData(Main.class.getResourceAsStream(imageName));
		ImageData mask = source.getTransparencyMask();
		Image image = new Image(null, source, mask);
		return image;
	}

}