package Presentation.preferenceReports;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.TreeColumnLayout;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.hibernate.exception.JDBCConnectionException;

import Presentation.controllerReports.ReportsPPController;
import Presentation.preferences.PreferenceConstants;

/**
 * To view a report for a specific architecture and a specific quality
 * requirement
 * 
 * @author: Flor
 */
public class ReportsPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	/**
	 * Attributes
	 */
	private Button btnViewReport;
	private ComboViewer cboSystem;
	private ReportsPPController viewController;
	private Composite cSystemName;
	private GridData gridData;

	/**
	 * Contructor
	 */
	public ReportsPreferencePage() {
		super(GRID);
		noDefaultAndApplyButton();
		viewController = new ReportsPPController();
		this.setViewController(viewController); // NOPMD by Usuario-Pc on
												// 10/06/16 21:48
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.preference.FieldEditorPreferencePage#createContents(org
	 * .eclipse.swt.widgets.Composite)
	 */
	protected Control createContents(Composite parent) {
		try {
			this.getViewController().setForm(this);

			GridLayout layout = new GridLayout();
			layout.numColumns = 4;
			parent.setLayout(layout);

			cSystemName = new Composite(parent, SWT.NULL);
			cSystemName.setLayout(layout);
			gridData = new GridData();
			gridData.horizontalSpan = 4;
			gridData.horizontalAlignment = GridData.FILL;
			cSystemName.setLayoutData(gridData);

			Label labelSn = new Label(cSystemName, SWT.NONE);
			labelSn.setText(PreferenceConstants.SystemName_Label + ":");

			gridData = new GridData();
			gridData.horizontalAlignment = GridData.FILL;
			gridData.grabExcessHorizontalSpace = true;

			cboSystem = new ComboViewer(cSystemName, SWT.READ_ONLY);
			cboSystem.setContentProvider(ArrayContentProvider.getInstance());
			cboSystem.getCombo().setLayoutData(gridData);
			cboSystem.getCombo().addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					if (((IStructuredSelection) cboSystem.getSelection()).getFirstElement() != "") {
						cmbSystemItemStateChanged();
					}
				}
			});

			new Label(parent, SWT.LEFT);

			gridData = new GridData();
			gridData.horizontalSpan = 1;
			gridData.widthHint = 100;
			gridData.horizontalAlignment = GridData.END;
			gridData.verticalAlignment = SWT.BOTTOM;
			gridData.grabExcessHorizontalSpace = true;

			btnViewReport = new Button(parent, SWT.PUSH);
			// TODO internacionalizar
			btnViewReport.setText("View Report");
			btnViewReport.setLayoutData(gridData);
			btnViewReport.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					viewController.setModel(cboSystem);
					// TODO implementar
				}
			});

			this.prepareView(0);
		} catch (JDBCConnectionException e) {
			viewController.createErrorDialog(PreferenceConstants.Postgres_ErrorDialog);
		}

		return new Composite(parent, SWT.NULL);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.preference.FieldEditorPreferencePage#createFieldEditors
	 * ()
	 */
	@Override
	protected void createFieldEditors() {
	}

	/**
	 * Getters and Setters
	 */

	public ComboViewer getCboSystem() {
		return cboSystem;
	}

	public void setCboSystem(ComboViewer cboSystem) {
		this.cboSystem = cboSystem;
	}

	public ReportsPPController getViewController() {
		return viewController;
	}

	public void setViewController(ReportsPPController viewController) {
		this.viewController = viewController;
	}

	public Button getBtnViewReport() {
		return btnViewReport;
	}

	public void setBtnViewReport(Button btnViewReport) {
		this.btnViewReport = btnViewReport;
	}

	/**
	 * load combo with system with state=true
	 */
	public void loadCombo() {
		this.getViewController().setModel();
	}

	/**
	 * prepare the view for the different actions that are possible
	 * 
	 * @param pabm
	 */
	public void prepareView(int pabm) {
		if (!getViewController().getManager().existSystemTrue()) {
			this.getViewController().createErrorDialog(PreferenceConstants.NoSavedSystemWithSimulations_ErrorDialog);
			pabm=2;
		}
		switch (pabm) {
		case 0:// System with simulations and yet no system selected
			this.getCboSystem().getCombo().setEnabled(true);
			loadCombo();
			this.getBtnViewReport().setEnabled(false);
			break;
		case 1:// With system selected
			this.getBtnViewReport().setEnabled(true);
			break;
		case 2:// No system with simulations
			this.getCboSystem().getCombo().setEnabled(false);
			this.getBtnViewReport().setEnabled(false);
			break;
		}
	}

	/**
	 * When a system is selected, fill table with its quality requirements and
	 * prepare the view
	 */
	private void cmbSystemItemStateChanged() {// GEN-FIRST:event_cmbNombreItemStateChanged
		this.prepareView(1);
	}

}