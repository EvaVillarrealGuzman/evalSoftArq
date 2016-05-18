package software.Presentation;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

import javax.swing.ImageIcon;

/**
 * Form MainMenu
 * 
 * @author: FEM
 * @version: 06/11/2015
 */

public class FrmMainMenu extends JFrame {

	/**
	 * Attributes
	 * 
	 */
	private JPanel contentPane;
	private static FrmMainMenu frmMainMenu;
	public MainMenuController viewController;

	/**
	 * Builder
	 */
	private FrmMainMenu(MainMenuController pthis) {
		try {
			initComponent();
		} catch (Exception pe) {
			JOptionPane.showConfirmDialog(null,
					"The form does not initialize properly", "Warning",
					JOptionPane.ERROR_MESSAGE);
		}
		this.setViewController(pthis);
	}

	/**
	 * Get FrmMainMenu. Applied Singleton pattern
	 * 
	 */
	public static FrmMainMenu getFrmMainMenu(MainMenuController pthis) {
		if (frmMainMenu == null) {
			synchronized (FrmMainMenu.class) {
				frmMainMenu = new FrmMainMenu(pthis);
			}
		}
		return frmMainMenu;
	}

	/**
	 * Get and Set of viewController
	 * 
	 */
	public MainMenuController getViewController() {
		return viewController;
	}

	public void setViewController(MainMenuController pviewController) {
		this.viewController = pviewController;
	}

	/**
	 * Initialized the components
	 * 
	 */
	public void initComponent() {
		setResizable(false);
		setTitle("Main Menu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 302, 322);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JButton btnQualityRequirementManager = new JButton(
				"Quality Requirement ");
		btnQualityRequirementManager
				.setFont(new Font("Cambria", Font.PLAIN, 12));
		btnQualityRequirementManager.setBounds(5, 45, 269, 23);
		btnQualityRequirementManager.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				viewController.openFrmQualityRequirementManagement();
			}
		});
		contentPane.setLayout(null);
		contentPane.add(btnQualityRequirementManager);

		JButton btnExit = new JButton("Exit");
		btnExit.setIcon(new ImageIcon(
				"C:\\Users\\Micaela\\workspace\\Proyecto Final\\iconos\\exit.png"));
		btnExit.setFont(new Font("Cambria", Font.PLAIN, 12));
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				java.lang.System.exit(0);
			}
		});
		btnExit.setBounds(177, 250, 97, 23);
		contentPane.add(btnExit);

		JButton btnSystemManage = new JButton("System");
		btnSystemManage.setFont(new Font("Cambria", Font.PLAIN, 12));
		btnSystemManage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewController.openFrmSystemManagement();
			}
		});
		btnSystemManage.setBounds(5, 11, 269, 23);
		contentPane.add(btnSystemManage);

		JButton btnSoftwareArchitectureSpecification = new JButton(
				"Software Architecture Specification ");
		btnSoftwareArchitectureSpecification.setFont(new Font("Cambria",
				Font.PLAIN, 12));
		btnSoftwareArchitectureSpecification.setBounds(5, 79, 269, 23);
		contentPane.add(btnSoftwareArchitectureSpecification);

		JButton btnNewButton = new JButton("Software Architecture Evaluation ");
		btnNewButton.setFont(new Font("Cambria", Font.PLAIN, 12));
		btnNewButton.setBounds(5, 113, 269, 23);
		contentPane.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Parameters Specification ");
		btnNewButton_1.setFont(new Font("Cambria", Font.PLAIN, 12));
		btnNewButton_1.setBounds(5, 147, 269, 23);
		contentPane.add(btnNewButton_1);

		JButton btnReports = new JButton("Reports");
		btnReports.setFont(new Font("Cambria", Font.PLAIN, 12));
		btnReports.setBounds(5, 181, 269, 23);
		contentPane.add(btnReports);
	}

}
