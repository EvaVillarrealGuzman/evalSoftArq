package project.preferences.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Text;

/**
 * Parent controller with the reusable methods
 */
public class Controller {

	/**
	 * Validate whether a StringFieldEditor is empty
	 * @param ptxt
	 */
	public boolean isEmpty(StringFieldEditor ptxt) {
		return ptxt.getStringValue().equals("");
	}

	/**
	 * Validate whether a Text is empty 
	 * @param ptxt
	 */
	public boolean isEmpty(Text ptxt) {
		return ptxt.getText().equals("");
	}

	/**
	 * Validate whether a JComboBox is empty
	 * @param pcmb
	 */
	public boolean isEmpty(ComboViewer pcmb) {
		return ((IStructuredSelection) pcmb.getSelection()).getFirstElement() == null;
	}

	/**
	 * Convert a DateTime to Date
	 * @param dt
	 * @return
	 */
	public Date convertDateTimeToDate(DateTime pdt) {
		Calendar cal = GregorianCalendar.getInstance();
		cal.set(Calendar.YEAR, pdt.getYear());
		cal.set(Calendar.MONTH, pdt.getMonth());
		cal.set(Calendar.DAY_OF_MONTH, pdt.getDay());

		return cal.getTime();

	}

	/**
	 * Methods to get day, month and year of date
	 * @param pdate
	 * @return
	 */
	public int getDay(Date pdate) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		String dateFormat = sdf.format(pdate); // NOPMD by Usuario-Pc on
												// 10/06/16 21:36
		dateFormat = dateFormat.substring(0, 2);
		return Integer.parseInt(dateFormat);
	}

	public int getMonth(Date pdate) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		String dateFormat = sdf.format(pdate); // NOPMD by Usuario-Pc on
												// 10/06/16 21:36
		dateFormat = dateFormat.substring(3, 5);
		return Integer.parseInt(dateFormat);
	}

	public int getYear(Date pdate) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		String dateFormat = sdf.format(pdate); // NOPMD by Usuario-Pc on
												// 10/06/16 21:36
		dateFormat = dateFormat.substring(6, 10);
		return Integer.parseInt(dateFormat);
	}

	/**
	 * Valid if a date is before that other
	 * @param pcs
	 * @param pcf
	 * @return boolean (indicates if finish date is before start date)
	 */
	public boolean isAfter(DateTime pcs, DateTime pcf) {
		Date dcs = convertDateTimeToDate(pcs);
		Date dcf = convertDateTimeToDate(pcf);
		return dcs.after(dcf);
	}

	/**
	 * Created a dialog for error
	 * @param error
	 */
	public void createErrorDialog(String error) {
		MessageDialog.openError(null, "Error", error);
	}

	/**
	 * Created a dialog for system's delete
	 * @return boolean
	 */
	public boolean createDeleteDialog() {
		return MessageDialog.openQuestion(null, "Question", "Do you want to delete the system?");
	}
	
	/**
	 * Created a dialog for quality requirement's delete
	 * @return boolean
	 */
	public boolean createDeleteRequirementDialog() {
		return MessageDialog.openQuestion(null, "Question", "Do you want to delete the quality requirement?");
	}

	/**
	 * Convert a File to IFile
	 * @param file
	 * @return IFile
	 */
	public IFile convert(File file) {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IPath location = Path.fromOSString(file.getAbsolutePath());
		IFile ifile = workspace.getRoot().getFileForLocation(location);
		return ifile;

	}

}
