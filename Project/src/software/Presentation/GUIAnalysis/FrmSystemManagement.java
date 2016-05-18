package software.Presentation.GUIAnalysis;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
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
public class FrmSystemManagement extends JFrame {

	/**
	 * Attributes
	 */
	private static FrmSystemManagement frmSystemManagement;
	private JPanel contentPane;
	private SystemManagementController viewController;
	private JTextField txtSystemName;
	private JTextField txtProjectName;
	private JDateChooser calendarStartDate;
	private JDateChooser calendarFinishDate;
	private JButton btnSave;
	private JButton btnEdit;
	private JButton btnRemove;
	private JComboBox cmbSystemName;
	private JButton btnNew;
	private JButton btnSearch;
	private int YES_NO_OPTION;

	/**
	 * Builder
	 */
	private FrmSystemManagement(SystemManagementController pthis) {
		try {
			initComponent();
		} catch (Exception pe) {
			JOptionPane.showOptionDialog(
					null,
					"The form does not initialize properly",
					"Warning",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.ERROR_MESSAGE,
					new ImageIcon(FrmSystemManagement.class
							.getResource("software/Icons/error.png")),
					new Object[] { "OK" }, "OK");
		}
		this.setViewController(pthis);
		this.getViewController().setFrmSystemManagement(this);
		prepareView(getViewController().getOpcABM());
	}

	/**
	 * Get FrmSystemManagement. Applied Singleton pattern
	 */
	public static FrmSystemManagement getFrmSystemManagement(
			SystemManagementController pthis) {
		if (frmSystemManagement == null) {
			synchronized (FrmSystemManagement.class) {
				frmSystemManagement = new FrmSystemManagement(pthis);
			}
		}
		return frmSystemManagement;
	}

	/**
	 * Set FrmSystemManagement
	 */
	public static void setFrmSystemManagement(
			FrmSystemManagement pfrmSystemManagement) {
		FrmSystemManagement.frmSystemManagement = pfrmSystemManagement;
	}

	/**
	 * Initialized the components
	 */
	public void initComponent() {
		setFont(new Font("Cambria", Font.PLAIN, 12));
		setResizable(false);
		setTitle("System Management");
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
				.getBorder("TitledBorder.border"), "Project",
				TitledBorder.LEADING, TitledBorder.TOP, null,
				SystemColor.inactiveCaption));
		panel.setBounds(10, 72, 697, 114);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblProjectName = new JLabel("Project Name");
		lblProjectName.setFont(new Font("Cambria", Font.PLAIN, 12));
		lblProjectName.setBounds(10, 28, 84, 14);
		panel.add(lblProjectName);

		txtProjectName = new JTextField();
		txtProjectName.setToolTipText("Insert proyect name");
		txtProjectName.setBounds(102, 25, 184, 20);
		panel.add(txtProjectName);
		txtProjectName.setColumns(10);

		JLabel lblStartDate = new JLabel("Start Date");
		lblStartDate.setFont(new Font("Cambria", Font.PLAIN, 12));
		lblStartDate.setBounds(10, 56, 84, 14);
		panel.add(lblStartDate);

		JLabel lblFinishDate = new JLabel("Finish Date");
		lblFinishDate.setFont(new Font("Cambria", Font.PLAIN, 12));
		lblFinishDate.setBounds(391, 56, 64, 14);
		panel.add(lblFinishDate);

		calendarStartDate = new JDateChooser();
		calendarStartDate.setToolTipText("Select project start date");
		calendarStartDate.setBounds(102, 56, 184, 20);
		panel.add(calendarStartDate);

		calendarFinishDate = new JDateChooser();
		calendarFinishDate.setToolTipText("Select project finish date");
		calendarFinishDate.setBounds(490, 56, 184, 20);
		panel.add(calendarFinishDate);

		txtSystemName = new JTextField();
		txtSystemName.setToolTipText("Insert system name");
		txtSystemName.setBounds(111, 32, 182, 20);
		contentPane.add(txtSystemName);
		txtSystemName.setColumns(10);

		JButton btnExit = new JButton("Exit");
		btnExit.setIcon(new ImageIcon(FrmSystemManagement.class
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
		btnCancel.setIcon(new ImageIcon(FrmSystemManagement.class
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

		btnRemove = new JButton("Remove");
		btnRemove.setIcon(new ImageIcon(FrmSystemManagement.class
				.getResource("/Icons/remove.png")));
		btnRemove.setFont(new Font("Cambria", Font.PLAIN, 12));
		btnRemove.setToolTipText("Remove");
		btnRemove.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				if (JOptionPane.showOptionDialog(
						null,
						"Do you want to delete the system?",
						"Warning",
						JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.ERROR_MESSAGE,
						new ImageIcon(FrmSystemManagement.class
								.getResource("/Icons/error.png")),
						new Object[] { "Yes", "No" }, "Yes") == 0) {
					removeView();
				}
			}
		});
		btnRemove.setBounds(349, 211, 103, 23);
		contentPane.add(btnRemove);

		btnEdit = new JButton("Edit");
		btnEdit.setIcon(new ImageIcon(FrmSystemManagement.class
				.getResource("/Icons/edit.png")));
		btnEdit.setFont(new Font("Cambria", Font.PLAIN, 12));
		btnEdit.setToolTipText("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				prepareView(0);
			}
		});
		btnEdit.setBounds(236, 211, 103, 23);
		contentPane.add(btnEdit);

		btnSave = new JButton("Save");
		btnSave.setIcon(new ImageIcon(FrmSystemManagement.class
				.getResource("/Icons/Save.png")));
		btnSave.setFont(new Font("Cambria", Font.PLAIN, 12));
		btnSave.setToolTipText("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				saveView();
			}
		});
		btnSave.setBounds(123, 211, 103, 23);
		contentPane.add(btnSave);

		cmbSystemName = new JComboBox();
		cmbSystemName.setToolTipText("Select system name");
		cmbSystemName.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				cmbSystemNameItemStateChanged(arg0);
			};
		});
		cmbSystemName.setBounds(111, 32, 182, 20);
		contentPane.add(cmbSystemName);

		btnNew = new JButton("New");
		btnNew.setIcon(new ImageIcon(FrmSystemManagement.class
				.getResource("/Icons/new.png")));
		btnNew.setFont(new Font("Cambria", Font.PLAIN, 12));
		btnNew.setToolTipText("New");
		btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getViewController().setOpcABM(0);
				prepareView(getViewController().getOpcABM());
			}
		});
		btnNew.setBounds(10, 211, 103, 23);
		contentPane.add(btnNew);

		btnSearch = new JButton("Search");
		btnSearch.setIcon(new ImageIcon(FrmSystemManagement.class
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
							new ImageIcon(FrmSystemManagement.class
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

	public JTextField getTxtProjectName() {
		return txtProjectName;
	}

	public void setTxtProjectName(JTextField ptxtProjectName) {
		this.txtProjectName = ptxtProjectName;
	}

	public JDateChooser getCalendarStartDate() {
		return calendarStartDate;
	}

	public void setCalendarStartDate(JDateChooser pcalendarStartDate) {
		this.calendarStartDate = pcalendarStartDate;
	}

	public JDateChooser getCalendarFinishDate() {
		return calendarFinishDate;
	}

	public void setCalendarFinishDate(JDateChooser pcalendarFinishDate) {
		this.calendarFinishDate = pcalendarFinishDate;
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
			btnNew.setEnabled(false);
			btnSave.setEnabled(true);
			btnEdit.setEnabled(false);
			btnRemove.setEnabled(false);
			cmbSystemName.setVisible(false);
			txtSystemName.setVisible(true);
			txtSystemName.setEnabled(true);
			txtSystemName.grabFocus();
			txtProjectName.setEnabled(true);
			calendarStartDate.setEnabled(true);
			calendarFinishDate.setEnabled(true);
			break;
		case 1:// Search
			btnSearch.setEnabled(false);
			btnNew.setEnabled(false);
			btnSave.setEnabled(false);
			btnEdit.setEnabled(false);
			btnRemove.setEnabled(false);
			txtSystemName.setVisible(false);
			cmbSystemName.setVisible(true);
			cmbSystemName.setEnabled(true);
			cmbSystemName.grabFocus();
			txtProjectName.setEnabled(false);
			calendarStartDate.setEnabled(false);
			calendarFinishDate.setEnabled(false);
			this.loadCombo();
			break;
		case 2:// Remove-Edit
			btnNew.setEnabled(false);
			btnSave.setEnabled(false);
			btnEdit.setEnabled(true);
			btnRemove.setEnabled(true);
			cmbSystemName.setVisible(false);
			txtSystemName.setVisible(true);
			txtSystemName.setEnabled(false);
			txtProjectName.setEnabled(false);
			calendarStartDate.setEnabled(false);
			calendarFinishDate.setEnabled(false);
			btnRemove.grabFocus();
			break;
		case 3: // Manage
			btnSearch.setEnabled(true);
			btnNew.setEnabled(true);
			btnSave.setEnabled(false);
			btnEdit.setEnabled(false);
			btnRemove.setEnabled(false);
			txtSystemName.setVisible(false);
			cmbSystemName.setVisible(true);
			cmbSystemName.setEnabled(false);
			cmbSystemName.grabFocus();
			txtProjectName.setEnabled(false);
			calendarStartDate.setEnabled(false);
			calendarFinishDate.setEnabled(false);
			break;
		}

	}

	public void saveView() {
		this.getViewController().saveView();
	}

	/**
	 * Clear the view (clear: txt, cmb and calendar)
	 */
	public void clearView() {
		cmbSystemName.setSelectedItem("");
		txtSystemName.setText("");
		txtProjectName.setText("");
		calendarStartDate.setDate(null);
		calendarFinishDate.setDate(null);
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
