package project.preferences.controller;

import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredSelection;

public class Controller {

	/**
	 * Validate whether a StringFieldEditor is empty
	 * @param ptxt
	 */
	public boolean isEmpty(StringFieldEditor ptxt) {
		return (ptxt.getStringValue().equals(""));
	}

	/**
	 * Validate whether a JComboBox is empty
	 * @param pcmb
	 */
	public boolean isEmpty(ComboViewer pcmb) {
		return ((IStructuredSelection) pcmb.getSelection()).getFirstElement().toString() == "";
	}
	
	
	
}
