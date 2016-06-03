package project.preferences;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import project.preferences.controller.QualityRequirementPPController;
import software.DomainModel.AnalysisEntity.Metric;
import software.DomainModel.AnalysisEntity.QualityAttribute;
import software.DomainModel.AnalysisEntity.ResponseMeasureType;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

public class SearchQualityRequirementPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {
	/**
	* Attributes
	*/	
	private Group groupPropierties;
	private Composite cPropierties;
	private ComboViewer cmbSystem;
	private Group groupQualityRequirement;
	private Composite cQualityRequirement;
	private TableViewer tblViewerQualityRequirement;
	TableViewerColumn colObject;
	TableViewerColumn colCondition;
	TableViewerColumn colQualityAttribute;
	TableViewerColumn colDescriptionScenario;
	private Button btnConsult;
	private static SearchQualityRequirementPreferencePage qualityRequirementPP;
	private QualityRequirementPPController viewController;
	
	public SearchQualityRequirementPreferencePage() {
		super(GRID);
		noDefaultAndApplyButton();
		viewController = new QualityRequirementPPController();
		this.setViewController(viewController);
	}
		
	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}

	@Override
	protected void createFieldEditors() {
		this.getViewController().setFormSearch(this);
		
		groupPropierties = new Group(getFieldEditorParent(), SWT.SHADOW_ETCHED_IN);
		groupPropierties.setText("Propierties");

		GridLayout layoutPropierties = new GridLayout();
		layoutPropierties.numColumns = 1;
		groupPropierties.setLayout(layoutPropierties);

		GridData dataPropierties = new GridData();
		groupPropierties.setLayoutData(dataPropierties);

		cPropierties = new Composite(groupPropierties, SWT.NONE);
		dataPropierties = new GridData();
		dataPropierties.grabExcessHorizontalSpace = true;
		dataPropierties.horizontalIndent = 40;
		cPropierties.setLayoutData(dataPropierties);
		

		addField( new StringFieldEditor(PreferenceConstants.P_STRING, "Project Name: ", cPropierties));

		Label labelS = new Label(cPropierties, SWT.NONE);
		labelS.setText("System: ");
		
		cmbSystem = new ComboViewer(cPropierties, SWT.READ_ONLY);

		cmbSystem.setContentProvider(ArrayContentProvider.getInstance());

		cmbSystem.getCombo().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (((IStructuredSelection) cmbSystem.getSelection()).getFirstElement()!=""){
					cmbSystemItemStateChanged();
				} else {
					clearScenario();
					prepareView(0);
				}
			}
		});
		
		groupQualityRequirement = new Group(getFieldEditorParent(), SWT.SHADOW_ETCHED_IN);
		groupQualityRequirement.setText("Quality Requirements");

		GridLayout layoutQualityRequirement = new GridLayout();
		layoutQualityRequirement.numColumns = 1;
		groupQualityRequirement.setLayout(layoutQualityRequirement);

		GridData dataQualityRequirement = new GridData();
		groupQualityRequirement.setLayoutData(dataQualityRequirement);

		cQualityRequirement = new Composite(groupQualityRequirement, SWT.NONE);
		dataQualityRequirement = new GridData();
		dataQualityRequirement.grabExcessHorizontalSpace = true;
		dataQualityRequirement.horizontalIndent = 40;
		cQualityRequirement.setLayoutData(dataQualityRequirement);
		

		addField( new StringFieldEditor(PreferenceConstants.P_STRING, "Project Name: ", cQualityRequirement));
		
		//definite the tableViewer
		tblViewerQualityRequirement = new TableViewer(cQualityRequirement, SWT.MULTI|SWT.V_SCROLL|SWT.BORDER);
		
		// make lines and header visible
	    Table table = tblViewerQualityRequirement.getTable();
	    table.setLayoutData(new GridData(GridData.FILL_BOTH));
	    table.setHeaderVisible(true);
	    table.setLinesVisible(true); 
	    
	    // set the content provider
	    tblViewerQualityRequirement.setContentProvider(ArrayContentProvider.getInstance());

	 // create columns
	    colObject = new TableViewerColumn(tblViewerQualityRequirement, SWT.NONE);
	    colObject.getColumn().setWidth(0);
	    colObject.getColumn().setText("Object");
	    //colObject.setLabelProvider(new ColumnLabelProvider() {
	      //@Override
	      //public String getText(Object element) {
	        //Person p = (Person) element;
	        //return p.getFirstName();
	      //}
	    //});

	    colCondition = new TableViewerColumn(tblViewerQualityRequirement, SWT.NONE);
	    colCondition.getColumn().setWidth(200);
	    colCondition.getColumn().setText("Condition");
	    
	    colQualityAttribute = new TableViewerColumn(tblViewerQualityRequirement, SWT.NONE);
	    colQualityAttribute.getColumn().setWidth(200);
	    colQualityAttribute.getColumn().setText("Quality Attribute");
	    
	    colDescriptionScenario = new TableViewerColumn(tblViewerQualityRequirement, SWT.NONE);
	    colDescriptionScenario.getColumn().setWidth(200);
	    colDescriptionScenario.getColumn().setText("Description Scenario");
		
		btnConsult = new Button(getFieldEditorParent(), SWT.PUSH);
		btnConsult.setText(" Consult ");
		btnConsult.setToolTipText("Consult");
		btnConsult.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				viewController.save();
				clearView();
				prepareView(0);
			}
		});

		this.prepareView(0);

	}

	public static SearchQualityRequirementPreferencePage getQualityRequirementPP() {
		return qualityRequirementPP;
	}

	public static void setQualityRequirementPP(SearchQualityRequirementPreferencePage qualityRequirementPP) {
		SearchQualityRequirementPreferencePage.qualityRequirementPP = qualityRequirementPP;
	}

	public QualityRequirementPPController getViewController() {
		return viewController;
	}

	public void setViewController(QualityRequirementPPController viewController) {
		this.viewController = viewController;
	}
	
	
	public Group getGroupPropierties() {
		return groupPropierties;
	}

	public void setGroupPropierties(Group groupPropierties) {
		this.groupPropierties = groupPropierties;
	}

	public Composite getcPropierties() {
		return cPropierties;
	}

	public void setcPropierties(Composite cPropierties) {
		this.cPropierties = cPropierties;
	}

	public ComboViewer getCmbSystem() {
		return cmbSystem;
	}

	public void setCmbSystem(ComboViewer cmbSystem) {
		this.cmbSystem = cmbSystem;
	}

	public Group getGroupQualityRequirement() {
		return groupQualityRequirement;
	}

	public void setGroupQualityRequirement(Group groupQualityRequirement) {
		this.groupQualityRequirement = groupQualityRequirement;
	}

	public Composite getcQualityRequirement() {
		return cQualityRequirement;
	}

	public void setcQualityRequirement(Composite cQualityRequirement) {
		this.cQualityRequirement = cQualityRequirement;
	}
	
	public TableViewer getTblViewerQualityRequirement() {
		return tblViewerQualityRequirement;
	}

	public void setTblViewerQualityRequirement(TableViewer tblViewerQualityRequirement) {
		this.tblViewerQualityRequirement = tblViewerQualityRequirement;
	}

	public TableViewerColumn getColObject() {
		return colObject;
	}

	public void setColObject(TableViewerColumn colObject) {
		this.colObject = colObject;
	}

	public TableViewerColumn getColCondition() {
		return colCondition;
	}

	public void setColCondition(TableViewerColumn colCondition) {
		this.colCondition = colCondition;
	}

	public TableViewerColumn getColQaulityAttribute() {
		return colQualityAttribute;
	}

	public void setColQaulityAttribute(TableViewerColumn colQaulityAttribute) {
		this.colQualityAttribute = colQaulityAttribute;
	}

	public TableViewerColumn getColDescriptionScenario() {
		return colDescriptionScenario;
	}

	public void setColDescriptionScenario(TableViewerColumn colDescriptionScenario) {
		this.colDescriptionScenario = colDescriptionScenario;
	}

	public Button getBtnConsult() {
		return btnConsult;
	}

	public void setBtnConsult(Button btnConsult) {
		this.btnConsult = btnConsult;
	}

	public void loadCmbSystem() {
		this.getViewController().setModelSystemSearch();
	}
	
	private void cmbSystemItemStateChanged() {// GEN-FIRST:event_cmbNombreItemStateChanged
		this.fillTable();
		this.prepareView(1);
	}
	
	public void fillTable(){
		this.getViewController().setModelQualityRequirement(
				(software.DomainModel.AnalysisEntity.System) ((IStructuredSelection) this.getCmbSystem().getSelection()).getFirstElement());
	}
	
	public void clearParts(){
		//TODO
	}
	
	public void clearScenario(){
		//TODO
		this.clearParts();
	}
	
	public void clearView(){
		cmbSystem.getCombo().clearSelection();
		this.clearScenario();
	}

	public void prepareView(int pabm) {
		this.getCmbSystem().getCombo().setFocus();
		if (!getViewController().getManager(). existSystemTrueWithQualityRequirementTrue()) {
			this.getViewController().createErrorDialog("No saved systems with quality requirements");
			pabm = 0;
		}
		switch (pabm) {
		case 0:// Search quality requirement
			this.getCmbSystem().getCombo().setEnabled(true);
			loadCmbSystem();
			
			this.getTblViewerQualityRequirement().getTable().setEnabled(false);
			
			this.getBtnConsult().setEnabled(true);
			
			break;
		case 1:// With system selected
			//this.getTxtDescription().setEnabled(true);
			//this.getCmbQualityAttribute().getCombo().setEnabled(true);
			//this.getCmbCondition().getCombo().setEnabled(true);
			
			break;		
		}
		
	}
	
}
