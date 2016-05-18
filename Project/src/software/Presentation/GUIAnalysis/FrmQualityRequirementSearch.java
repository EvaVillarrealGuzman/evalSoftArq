package software.Presentation.GUIAnalysis;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import software.DomainModel.AnalysisEntity.QualityRequirement;
import software.Presentation.ControllerAnalysis.QualityRequirementController;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

import javax.swing.JButton;
import javax.swing.ImageIcon;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;

/**
 * Form QualityRequirementSearch
 * 
 * @author: FEM
 * @version: 06/11/2015
 */

public class FrmQualityRequirementSearch extends JFrame {
	
	/**
	 * Attributes
	 */
	private static FrmQualityRequirementSearch frmQualityRequirementSearch;
	private JPanel contentPane;
	private QualityRequirementController viewController;
	private JComboBox cmbSystem;
	private DefaultTableModel model; 
	private JButton btnNewButton;
	private JButton btnConsult;
	private JLabel lblQualityRequirements;
	private JTable tblQualityRequirements;

	/**
	 * Builder
	 */
	private FrmQualityRequirementSearch(QualityRequirementController pthis) {
		try{
			initComponent();
		} catch (Exception pe){
			JOptionPane.showConfirmDialog(null, "The form does not initialize properly", "Warning", JOptionPane.ERROR_MESSAGE);
		}
		this.setViewController(pthis);
		this.getViewController().setFrmQualityRequirementSearch(this);
		model = (DefaultTableModel) tblQualityRequirements.getModel();
	}

	/**
	 * Get FrmSystemManagement. Applied Singleton pattern
	 */
	public static FrmQualityRequirementSearch getFrmQualityRequirementSearch(QualityRequirementController pthis) {
		if (frmQualityRequirementSearch==null){
			synchronized (FrmQualityRequirementSearch .class){
				frmQualityRequirementSearch = new FrmQualityRequirementSearch(pthis);
			}
		}
		return frmQualityRequirementSearch;
	}

	/**
	 * Set FrmSystemManagement
	 */
	public static void setFrmQualityRequirementSearch(
			FrmQualityRequirementSearch pfrmQualityRequirementSearch) {
		FrmQualityRequirementSearch.frmQualityRequirementSearch = pfrmQualityRequirementSearch;
	}

	/**
	 * Initialized the components
	 */
	public void initComponent() {
		setTitle("Search Quality Requirements ");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 428, 360);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblSystem = new JLabel("System");
		lblSystem.setFont(new Font("Cambria", Font.PLAIN, 12));
		lblSystem.setBounds(10, 15, 46, 14);
		contentPane.add(lblSystem);

		cmbSystem = new JComboBox();
		cmbSystem.setToolTipText("Select system");
		cmbSystem.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				fillTable();
			}
		});
		cmbSystem.setFont(new Font("Cambria", Font.PLAIN, 12));
		cmbSystem.setBounds(66, 12, 114, 20);
		contentPane.add(cmbSystem);
		
		btnNewButton = new JButton("Cancel");
		btnNewButton.setToolTipText("Cancel");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnNewButton.setIcon(new ImageIcon(FrmQualityRequirementSearch.class.getResource("/Icons/cancel.png")));
		btnNewButton.setFont(new Font("Cambria", Font.PLAIN, 12));
		btnNewButton.setBounds(303, 298, 107, 23);
		contentPane.add(btnNewButton);
		
		btnConsult = new JButton("Consult");
		btnConsult.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getViewController().setModel((QualityRequirement)tblQualityRequirements.getValueAt(tblQualityRequirements.getSelectedRow(), 0));
				getViewController().getFrmQualityRequirementManagement()
						.setView();
				getViewController().setOpcABM(2);
				getViewController().getFrmQualityRequirementManagement()
						.prepareView(getViewController().getOpcABM());
				dispose();
			}
		});
		btnConsult.setToolTipText("Consult");
		btnConsult.setIcon(new ImageIcon(FrmQualityRequirementSearch.class.getResource("/Icons/consult.png")));
		btnConsult.setFont(new Font("Cambria", Font.PLAIN, 12));
		btnConsult.setBounds(186, 298, 107, 23);
		contentPane.add(btnConsult);
		
		lblQualityRequirements = new JLabel("Quality Requirements");
		lblQualityRequirements.setFont(new Font("Cambria", Font.PLAIN, 12));
		lblQualityRequirements.setBounds(10, 64, 156, 14);
		contentPane.add(lblQualityRequirements);
		
		tblQualityRequirements = new JTable();
		tblQualityRequirements.setFillsViewportHeight(true);
		tblQualityRequirements.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblQualityRequirements.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				prepareView(1);
			}
		});
		tblQualityRequirements.setToolTipText("Select requirement to consult");
		tblQualityRequirements.setBorder(new LineBorder(
				SystemColor.inactiveCaption));
		tblQualityRequirements.setModel(new DefaultTableModel(
				new Object[][] {}, new String[] { "QR", "Quality Attribute",
						"Condition", "Description Scenario" }) {
			boolean[] columnEditables = new boolean[] { false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tblQualityRequirements.setFont(new Font("Cambria", Font.PLAIN, 12));
		tblQualityRequirements.setBounds(30, 70, 522, 304);
		tblQualityRequirements.getColumnModel().getColumn(0).setMaxWidth(0);
		tblQualityRequirements.getColumnModel().getColumn(0).setMinWidth(0);
		tblQualityRequirements.getColumnModel().getColumn(0).setPreferredWidth(0);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 89, 402, 183);
		contentPane.add(scrollPane);
		scrollPane.setViewportView(tblQualityRequirements);
		contentPane.add(scrollPane);

	}

	/**
	 * Getters and Setters
	 */
	public JTable getTblQualityRequirements() {
		return tblQualityRequirements;
	}

	public void setTblQualityRequirements(JTable ptblQualityRequirements) {
		this.tblQualityRequirements = ptblQualityRequirements;
	}

	public JComboBox getCmbSystem() {
		return cmbSystem;
	}

	public void setCmbSystem(JComboBox pcmbSystem) {
		this.cmbSystem = pcmbSystem;
	}

	public DefaultTableModel getModel() {
		return model;
	}

	public void setModel(DefaultTableModel pmodel) {
		this.model = pmodel;
	}

	private void fillTable() {
		this.getViewController().setModelQualityRequirement(
				(software.DomainModel.AnalysisEntity.System) this.getCmbSystem()
						.getSelectedItem());

	}

	public QualityRequirementController getViewController() {
		return viewController;
	}

	public void setViewController(QualityRequirementController pviewController) {
		this.viewController = pviewController;
	}

	/**
	 * Prepare the view according to option (search, consult)
	 * 
	 * @param pabm
	 */
	public void prepareView(int pabm) {
		switch (pabm) {
		case 0:
			loadSystem();
			btnConsult.setEnabled(false);
			break;
		case 1:
			btnConsult.setEnabled(true);
			break;
		}
	}

	/**
	 * load combo with system names
	 */
	public void loadSystem() {
		this.getViewController().setModelSystemSearch();
	}
}
