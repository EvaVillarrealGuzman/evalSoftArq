package software.Presentation.GUIAnalysis;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import software.Presentation.ControllerAnalysis.SoftwareArchitectureSpecificationManagementController;
import software.Presentation.ControllerAnalysis.SystemManagementController;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import com.toedter.calendar.JDateChooser;
import javax.swing.JComboBox;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.ImageIcon;


/**
 * Form SystemManagement
 * 
 * @author: FEM
 * @version: 06/11/2015
 */
public class FrmSoftwareArchitectureSpecification extends JFrame {

	/**
	 * Attributes
	 */
	private static FrmSoftwareArchitectureSpecification frmSoftwareArchitectureSpecificationManagement;
	private JPanel contentPane;
	private SystemManagementController viewController;
	private JTextField txtSystemName;
	private JComboBox cmbSystemName;
	private JButton btnSearch;
	private int YES_NO_OPTION;

	/**
	 * Builder
	 */
	private FrmSoftwareArchitectureSpecification(SoftwareArchitectureSpecificationManagementController pthis) {
		try {
			initComponent();
		} catch (Exception pe) {
			JOptionPane.showOptionDialog(
					null,
					"The form does not initialize properly",
					"Warning",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.ERROR_MESSAGE,
					new ImageIcon(FrmSoftwareArchitectureSpecification.class
							.getResource("software/Icons/error.png")),
					new Object[] { "OK" }, "OK");
		}
	}

	/**
	 * Get FrmSystemManagement. Applied Singleton pattern
	 */
	public static FrmSoftwareArchitectureSpecification getFrmSoftwareArchitectureSpecificationManagement(
			SoftwareArchitectureSpecificationManagementController pthis) {
		if (frmSoftwareArchitectureSpecificationManagement == null) {
			synchronized (FrmSoftwareArchitectureSpecification.class) {
				frmSoftwareArchitectureSpecificationManagement = new FrmSoftwareArchitectureSpecification(pthis);
			}
		}
		return frmSoftwareArchitectureSpecificationManagement;
	}

	/**
	 * Initialized the components
	 */
	public void initComponent() {
		setFont(new Font("Cambria", Font.PLAIN, 12));
		setResizable(false);
		setTitle("Software Architecture Specification Management");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 723, 273);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblSystemName = new JLabel("System Name");
		lblSystemName.setFont(new Font("Cambria", Font.PLAIN, 12));
		lblSystemName.setBounds(20, 32, 123, 14);
		contentPane.add(lblSystemName);

		JPanel panel = new JPanel();
		panel.setForeground(SystemColor.desktop);
		panel.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "jUCMNav",
				TitledBorder.LEADING, TitledBorder.TOP, null,
				SystemColor.inactiveCaption));
		panel.setBounds(10, 72, 697, 114);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton btnNew = new JButton("New ");
		btnNew.setBounds(186, 49, 89, 23);
		panel.add(btnNew);
		
		JButton btnOpen = new JButton("Open");
		btnOpen.setBounds(379, 49, 89, 23);
		panel.add(btnOpen);

		txtSystemName = new JTextField();
		txtSystemName.setToolTipText("Insert system name");
		txtSystemName.setBounds(111, 32, 182, 20);
		contentPane.add(txtSystemName);
		txtSystemName.setColumns(10);

		JButton btnExit = new JButton("Exit");
		btnExit.setIcon(new ImageIcon(FrmSoftwareArchitectureSpecification.class
				.getResource("/Icons/exit.png")));
		btnExit.setFont(new Font("Cambria", Font.PLAIN, 12));
		btnExit.setToolTipText("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnExit.setBounds(604, 211, 103, 23);
		contentPane.add(btnExit);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.setIcon(new ImageIcon(FrmSoftwareArchitectureSpecification.class
				.getResource("/Icons/cancel.png")));
		btnCancel.setFont(new Font("Cambria", Font.PLAIN, 12));
		btnCancel.setToolTipText("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearView();
				getViewController().setOpcABM(3);
				prepareView(getViewController().getOpcABM());
			}
		});
		btnCancel.setBounds(491, 211, 103, 23);
		contentPane.add(btnCancel);

		cmbSystemName = new JComboBox();
		cmbSystemName.setToolTipText("Select system name");
		cmbSystemName.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				cmbSystemNameItemStateChanged(arg0);
			};
		});
		cmbSystemName.setBounds(111, 32, 182, 20);
		contentPane.add(cmbSystemName);

		btnSearch = new JButton("Search");
		btnSearch.setIcon(new ImageIcon(FrmSoftwareArchitectureSpecification.class
				.getResource("/Icons/search.png")));
		btnSearch.setFont(new Font("Cambria", Font.PLAIN, 12));
		btnSearch.setToolTipText("Search ");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (getViewController().getManager().existSystemTrue()) {
					getViewController().setOpcABM(1);
					prepareView(getViewController().getOpcABM());
				} else {
					JOptionPane.showOptionDialog(
							null,
							"No saved systems",
							"Warning",
							JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.ERROR_MESSAGE,
							new ImageIcon(FrmSoftwareArchitectureSpecification.class
									.getResource("/Icons/error.png")),
							new Object[] { "OK" }, "OK");
				}
			}
		});
		btnSearch.setBounds(303, 32, 103, 23);
		contentPane.add(btnSearch);
	}

	/**
	 * Getters and Setters
	 */
	public int getYES_NO_OPTION() {
		return YES_NO_OPTION;
	}

	public void setYES_NO_OPTION(int pYES_NO_OPTION) {
		YES_NO_OPTION = pYES_NO_OPTION;
	}

	public SystemManagementController getViewController() {
		return viewController;
	}

	public void setViewController(SystemManagementController pviewController) {
		this.viewController = pviewController;
	}

	public JTextField getTxtSystemName() {
		return txtSystemName;
	}

	public void setTxtSystemName(JTextField ptxtSystemName) {
		this.txtSystemName = ptxtSystemName;
	}

	public JComboBox getCmbSystemName() {
		return cmbSystemName;
	}

	public void setCmbSystemName(JComboBox pcmbSystemName) {
		this.cmbSystemName = pcmbSystemName;
	}

	/**
	 * Prepare the view according to option (Manage,New, Search, Remove, Edit)
	 * 
	 * @param pabm
	 */
	public void prepareView(int pabm) {
		switch (pabm) {
		case 0:// New
			btnSearch.setEnabled(false);
			cmbSystemName.setVisible(false);
			txtSystemName.setVisible(true);
			txtSystemName.setEnabled(true);
			txtSystemName.grabFocus();
			break;
		case 1:// Search
			btnSearch.setEnabled(false);
			txtSystemName.setVisible(false);
			cmbSystemName.setVisible(true);
			cmbSystemName.setEnabled(true);
			cmbSystemName.grabFocus();
			this.loadCombo();
			break;
		case 2:// Remove-Edit
			cmbSystemName.setVisible(false);
			txtSystemName.setVisible(true);
			txtSystemName.setEnabled(false);
			break;
		case 3: // Manage
			btnSearch.setEnabled(true);
			txtSystemName.setVisible(false);
			cmbSystemName.setVisible(true);
			cmbSystemName.setEnabled(false);
			cmbSystemName.grabFocus();
			break;
		}

	}

	/**
	 * Clear the view (clear: txt, cmb and calendar)
	 */
	public void clearView() {
		cmbSystemName.setSelectedItem("");
		txtSystemName.setText("");
	}

	/**
	 * load combo with system names
	 */
	public void loadCombo() {
		this.getViewController().setModelSystem();
	}

	/**
	 * It detects when an combo's item is selected and prepare the view.
	 */
	private void cmbSystemNameItemStateChanged(java.awt.event.ItemEvent evt) {// GEN-FIRST:event_cmbNombreItemStateChanged
		if (this.getViewController().isEmpty(cmbSystemName)) {
			this.prepareView(1);
		} else {
			this.getViewController().setModel(cmbSystemName);
			this.setView();
			this.getViewController().setOpcABM(2);
			this.prepareView(this.getViewController().getOpcABM());
		}
	}

	public void setView() {
		this.getViewController().getView();
	}

	public void removeView() {
		this.getViewController().removeView();
	}
}
