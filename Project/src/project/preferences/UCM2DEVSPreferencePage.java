package project.preferences;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class UCM2DEVSPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public UCM2DEVSPreferencePage() {
		super(GRID);
		noDefaultAndApplyButton();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}

	protected Control createContents(Composite parent) {

		Label message = new Label(parent, SWT.NONE);
		message.setText("Expand the tree to edit preferences for a specific feature.");
		
		return new Composite(parent, SWT.NULL);

	}

	@Override
	protected void createFieldEditors() {
	}

}
