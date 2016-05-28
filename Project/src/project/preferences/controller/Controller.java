package project.preferences.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Text;

public class Controller {

	/**
	 * Validate whether a StringFieldEditor is empty
	 * @param ptxt
	 */
	public boolean isEmpty(StringFieldEditor ptxt) {
		return (ptxt.getStringValue().equals(""));
	}

	/**
	 * Validate whether a Text is empty
	 * @param ptxt
	 */
	public boolean isEmpty(Text ptxt) {
		return (ptxt.getText().equals(""));
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
	public Date convertDateTimeToDate(DateTime pdt){
		Calendar cal = GregorianCalendar.getInstance();
		System.out.println(pdt.getYear());
		System.out.println(pdt.getMonth());
		System.out.println(pdt.getDay());
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
	public int getDay(Date pdate){
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy"); 
		String dateFormat = sdf.format(pdate);
		dateFormat= dateFormat.substring(0, 2);
		return  Integer.parseInt(dateFormat);
	}
	
	public int getMonth(Date pdate){
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy"); 
		String dateFormat = sdf.format(pdate);
		dateFormat= dateFormat.substring(3, 5);
		return  Integer.parseInt(dateFormat);
	}
	
	public int getYear(Date pdate){
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy"); 
		String dateFormat = sdf.format(pdate);
		dateFormat= dateFormat.substring(6, 10);
		return  Integer.parseInt(dateFormat);
	}
	
	/**
	 * Valid if a date is before that other
	 * @param pcs
	 * @param pcf
	 * @return
	 */
	public boolean isAfter(DateTime pcs, DateTime pcf){
		Date dcs = convertDateTimeToDate(pcs);
		Date dcf = convertDateTimeToDate(pcf);
		System.out.println(dcs.before(dcf));
		return dcs.after(dcf);
	}
	
}
