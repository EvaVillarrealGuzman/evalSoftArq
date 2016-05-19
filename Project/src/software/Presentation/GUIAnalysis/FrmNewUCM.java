package software.Presentation.GUIAnalysis;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import software.Presentation.ControllerAnalysis.NewUCMController;
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
public class FrmNewUCM extends JFrame {

	/**
	 * Attributes
	 */
	private static FrmNewUCM frmNewUCM;
	private JPanel contentPane;
	private NewUCMController viewController;
	private JTextField txtProjectName;
	private JButton btnBrowse;
	private int YES_NO_OPTION;
	private JTextField textFileName;

	/**
	 * Builder
	 */
	private FrmNewUCM(NewUCMController pthis) {
		try {
			initComponent();
		} catch (Exception pe) {
			JOptionPane.showOptionDialog(
					null,
					"The form does not initialize properly",
					"Warning",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.ERROR_MESSAGE,
					new ImageIcon(FrmNewUCM.class
							.getResource("software/Icons/error.png")),
					new Object[] { "OK" }, "OK");
		}
		this.setViewController(pthis);
		this.getViewController().setFrmNewUCM(this);
	}

	/**
	 * Get FrmSystemManagement. Applied Singleton pattern
	 */
	public static FrmNewUCM getFrmNewUCM(
			NewUCMController pthis) {
		if (frmNewUCM == null) {
			synchronized (FrmNewUCM.class) {
				frmNewUCM = new FrmNewUCM(pthis);
			}
		}
		return frmNewUCM;
	}

	/**
	 * Initialized the components
	 */
	public void initComponent() {
		setFont(new Font("Cambria", Font.PLAIN, 12));
		setResizable(false);
		setTitle("New UCM");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 723, 273);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setForeground(SystemColor.desktop);
		panel.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "Properties",
				TitledBorder.LEADING, TitledBorder.TOP, null,
				SystemColor.inactiveCaption));
		panel.setBounds(10, 11, 697, 175);
		contentPane.add(panel);
		panel.setLayout(null);
		
				JLabel lblProjectName = new JLabel("Project or folder: ");
				lblProjectName.setBounds(46, 41, 123, 14);
				panel.add(lblProjectName);
				lblProjectName.setFont(new Font("Cambria", Font.PLAIN, 12));

		txtProjectName = new JTextField();
		txtProjectName.setEnabled(false);
		txtProjectName.setBounds(155, 39, 332, 20);
		panel.add(txtProjectName);
		txtProjectName.setToolTipText("Choose folder");
		txtProjectName.setColumns(10);
		
				btnBrowse = new JButton("Browse...");
				btnBrowse.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						getViewController().getPath();
					}
				});
				btnBrowse.setBounds(505, 37, 103, 23);
				panel.add(btnBrowse);
				btnBrowse.setIcon(new ImageIcon(FrmNewUCM.class
						.getResource("/Icons/search.png")));
				btnBrowse.setFont(new Font("Cambria", Font.PLAIN, 12));
				btnBrowse.setToolTipText("Browse... ");
				
				JLabel label = new JLabel("file name:  ");
				label.setFont(new Font("Cambria", Font.PLAIN, 12));
				label.setBounds(46, 98, 123, 14);
				panel.add(label);
				
				textFileName= new JTextField();
				textFileName.setToolTipText("Insert file name");
				textFileName.setColumns(10);
				textFileName.setBounds(155, 96, 332, 20);
				panel.add(textFileName);
	

		JButton btnExit = new JButton("Exit");
		btnExit.setIcon(new ImageIcon(FrmNewUCM.class
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
		btnCancel.setIcon(new ImageIcon(FrmNewUCM.class
				.getResource("/Icons/cancel.png")));
		btnCancel.setFont(new Font("Cambria", Font.PLAIN, 12));
		btnCancel.setToolTipText("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearView();
			/*	getViewController().setOpcABM(3);
				prepareView(getViewController().getOpcABM());*/
			}
		});
		btnCancel.setBounds(491, 211, 103, 23);
		contentPane.add(btnCancel);
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

	public NewUCMController getViewController() {
		return viewController;
	}

	public void setViewController(NewUCMController pviewController) {
		this.viewController = pviewController;
	}

	public JTextField gettxtProjectName() {
		return txtProjectName;
	}

	public void settxtProjectName(JTextField ptxtProjectName) {
		this.txtProjectName = ptxtProjectName;
	}

	/**
	 * Prepare the view according to option (Manage,New, Search, Remove, Edit)
	 * 
	 * @param pabm
	 */
	public void prepareView(int pabm) {
		switch (pabm) {
		case 0:// New
			btnBrowse.setEnabled(false);
			txtProjectName.setVisible(true);
			txtProjectName.setEnabled(false);
			txtProjectName.grabFocus();
			break;
		case 1:// Search
			btnBrowse.setEnabled(false);
			break;
		case 2:// Remove-Edit
			txtProjectName.setVisible(true);
			txtProjectName.setEnabled(false);
			break;
		case 3: // Manage
			btnBrowse.setEnabled(true);
			txtProjectName.setVisible(false);
			break;
		}

	}

	/**
	 * Clear the view (clear: txt, cmb and calendar)
	 */
	public void clearView() {
		txtProjectName.setText("");
	}


}
