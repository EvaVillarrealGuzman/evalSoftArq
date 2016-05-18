package software.Presentation.GUIAnalysis;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import software.DomainModel.AnalysisEntity.Metric;
import software.DomainModel.AnalysisEntity.QualityAttribute;
import software.DomainModel.AnalysisEntity.ResponseMeasureType;
import software.Presentation.ControllerAnalysis.QualityRequirementController;

/**
 * Form QualityRequirementManagement
 * 
 * @author: FEM
 * @version: 06/11/2015
 */
public class FrmQualityRequirementManagement extends JFrame {

	/**
	 * Attributes
	 */
	private static FrmQualityRequirementManagement frmQualityRequirementManagement;
	private int YES_NO_OPTION;
	private JPanel contentPane;
	private QualityRequirementController viewController;
	private JComboBox cmbQualityAttribute;
	private JComboBox cmbSystem;
	private JTextArea txtDescription;
	private JTextField txtDescriptionStimulusSource;
	private JTextField txtDescriptionStimulus;
	private JTextField txtDescriptionEnvironment;
	private JTextField txtDescriptionArtifact;
	private JTextField txtDescriptionResponse;
	private JTextField txtDescriptionResponseMeasure;
	private JTextField txtValueStimulusSource;
	private JTextField txtValueStimulus;
	private JTextField txtValueEnvironment;
	private JTextField txtValueResponse;
	private JTextField txtValueResponseMeasure;
	private JButton btnRemove;
	private JButton btnEdit;
	private JButton btnSave;
	private JButton btnNew;
	private JComboBox cmbTypeStimulusSource;
	private JComboBox cmbTypeStimulus;
	private JComboBox cmbTypeEnvironment;
	private JComboBox cmbTypeArtifact;
	private JComboBox cmbTypeResponse;
	private JComboBox cmbTypeResponseMeasure;
	private JComboBox cmbMetric;
	private JComboBox cmbUnit;
	private JLabel lblCondition;
	private JComboBox cmbCondition;
	private boolean firstPoint = true;
	boolean firstTime = true;

	/**
	 * Builder
	 */
	private FrmQualityRequirementManagement(QualityRequirementController pthis) {
		try {
			initComponent();
		} catch (Exception pe) {
			JOptionPane.showConfirmDialog(null,
					"The form does not initialize properly", "Warning",
					JOptionPane.ERROR_MESSAGE);
		}
		this.setViewController(pthis);
		this.getViewController().setFrmQualityRequirementManagement(this);
		prepareView(getViewController().getOpcABM());
	}

	/**
	 * Get FrmQualityRequirementManagement. Applied Singleton pattern
	 */
	public static FrmQualityRequirementManagement getFrmQualityRequirementManagement(
			QualityRequirementController pthis) {
		if (frmQualityRequirementManagement == null) {
			synchronized (FrmQualityRequirementManagement.class) {
				frmQualityRequirementManagement = new FrmQualityRequirementManagement(
						pthis);
			}
		}
		return frmQualityRequirementManagement;
	}

	/**
	 * Set FrmSystemManagement
	 */
	public static void setFrmQualityRequirementManagement(
			FrmQualityRequirementManagement pfrmQualityRequirementManagement) {
		FrmQualityRequirementManagement.frmQualityRequirementManagement = pfrmQualityRequirementManagement;
	}

	/**
	 * Initialized the components
	 */
	public void initComponent() {
		setResizable(false);
		setTitle("Quality Requirement Management");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 876, 558);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		panel.setBounds(5, 5, 855, 59);
		panel.setForeground(Color.LIGHT_GRAY);
		panel.setToolTipText("");
		panel.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "Propierties",
				TitledBorder.LEADING, TitledBorder.TOP, null,
				SystemColor.inactiveCaption));

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(5, 65, 855, 401);
		panel_1.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "Scenarios",
				TitledBorder.LEADING, TitledBorder.TOP, null,
				SystemColor.inactiveCaption));
		panel_1.setLayout(null);

		Box horizontalBox = Box.createHorizontalBox();
		horizontalBox.setBounds(185, 13, 0, 0);
		panel_1.add(horizontalBox);

		JLabel lblDescription = new JLabel("Description");
		lblDescription.setFont(new Font("Cambria", Font.PLAIN, 12));
		lblDescription.setBounds(10, 31, 116, 14);
		panel_1.add(lblDescription);

		txtDescription = new JTextArea();
		txtDescription.setToolTipText("Insert scenario description");
		txtDescription.setFont(new Font("Cambria", Font.PLAIN, 12));
		txtDescription.setBounds(136, 26, 709, 48);
		panel_1.add(txtDescription);

		JLabel lblQualityAttribute = new JLabel("Quality Attribute");
		lblQualityAttribute.setFont(new Font("Cambria", Font.PLAIN, 12));
		lblQualityAttribute.setBounds(10, 107, 116, 14);
		panel_1.add(lblQualityAttribute);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "Parts",
				TitledBorder.LEADING, TitledBorder.TOP, null,
				SystemColor.inactiveCaption));
		panel_2.setBounds(10, 152, 835, 231);
		panel_1.add(panel_2);
		panel_2.setLayout(null);

		JLabel lblStimulus = new JLabel("Stimulus");
		lblStimulus.setFont(new Font("Cambria", Font.PLAIN, 12));
		lblStimulus.setBounds(10, 71, 87, 14);
		panel_2.add(lblStimulus);

		JLabel lblSourceOfStimulus = new JLabel("Source of Stimulus");
		lblSourceOfStimulus.setFont(new Font("Cambria", Font.PLAIN, 12));
		lblSourceOfStimulus.setBounds(10, 46, 111, 14);
		panel_2.add(lblSourceOfStimulus);

		JLabel lblEnvironment = new JLabel("Environment");
		lblEnvironment.setFont(new Font("Cambria", Font.PLAIN, 12));
		lblEnvironment.setBounds(10, 99, 87, 14);
		panel_2.add(lblEnvironment);

		JLabel lblArtifact = new JLabel("Artifact");
		lblArtifact.setFont(new Font("Cambria", Font.PLAIN, 12));
		lblArtifact.setBounds(10, 130, 46, 14);
		panel_2.add(lblArtifact);

		JLabel lblResponse = new JLabel("Response");
		lblResponse.setFont(new Font("Cambria", Font.PLAIN, 12));
		lblResponse.setBounds(10, 161, 70, 14);
		panel_2.add(lblResponse);

		JLabel lblResponse_1 = new JLabel("Response Measure");
		lblResponse_1.setFont(new Font("Cambria", Font.PLAIN, 12));
		lblResponse_1.setBounds(10, 192, 99, 14);
		panel_2.add(lblResponse_1);

		JLabel lblDescription_1 = new JLabel("Description");
		lblDescription_1.setFont(new Font("Cambria", Font.PLAIN, 12));
		lblDescription_1.setBounds(119, 11, 70, 14);
		panel_2.add(lblDescription_1);

		txtDescriptionStimulusSource = new JTextField();
		txtDescriptionStimulusSource.setToolTipText("Insert description");
		txtDescriptionStimulusSource
				.setFont(new Font("Cambria", Font.PLAIN, 12));
		txtDescriptionStimulusSource.setBounds(119, 43, 111, 20);
		panel_2.add(txtDescriptionStimulusSource);
		txtDescriptionStimulusSource.setColumns(10);

		txtDescriptionStimulus = new JTextField();
		txtDescriptionStimulus.setToolTipText("Insert description");
		txtDescriptionStimulus.setFont(new Font("Cambria", Font.PLAIN, 12));
		txtDescriptionStimulus.setBounds(119, 68, 111, 20);
		panel_2.add(txtDescriptionStimulus);
		txtDescriptionStimulus.setColumns(10);

		txtDescriptionEnvironment = new JTextField();
		txtDescriptionEnvironment.setToolTipText("Insert description");
		txtDescriptionEnvironment.setFont(new Font("Cambria", Font.PLAIN, 12));
		txtDescriptionEnvironment.setBounds(119, 96, 111, 20);
		panel_2.add(txtDescriptionEnvironment);
		txtDescriptionEnvironment.setColumns(10);

		txtDescriptionArtifact = new JTextField();
		txtDescriptionArtifact.setToolTipText("Insert description");
		txtDescriptionArtifact.setFont(new Font("Cambria", Font.PLAIN, 12));
		txtDescriptionArtifact.setBounds(119, 127, 111, 20);
		panel_2.add(txtDescriptionArtifact);
		txtDescriptionArtifact.setColumns(10);

		txtDescriptionResponse = new JTextField();
		txtDescriptionResponse.setToolTipText("Insert description");
		txtDescriptionResponse.setFont(new Font("Cambria", Font.PLAIN, 12));
		txtDescriptionResponse.setBounds(119, 158, 111, 20);
		panel_2.add(txtDescriptionResponse);
		txtDescriptionResponse.setColumns(10);

		txtDescriptionResponseMeasure = new JTextField();
		txtDescriptionResponseMeasure.setToolTipText("Insert description");
		txtDescriptionResponseMeasure.setFont(new Font("Cambria", Font.PLAIN,
				12));
		txtDescriptionResponseMeasure.setBounds(119, 189, 111, 20);
		panel_2.add(txtDescriptionResponseMeasure);
		txtDescriptionResponseMeasure.setColumns(10);

		JLabel lblType = new JLabel("Type");
		lblType.setFont(new Font("Cambria", Font.PLAIN, 12));
		lblType.setBounds(282, 11, 46, 14);
		panel_2.add(lblType);

		cmbTypeStimulusSource = new JComboBox();
		cmbTypeStimulusSource.setToolTipText("Select type");
		cmbTypeStimulusSource.setFont(new Font("Cambria", Font.PLAIN, 12));
		cmbTypeStimulusSource.setBounds(240, 43, 147, 20);
		panel_2.add(cmbTypeStimulusSource);

		cmbTypeStimulus = new JComboBox();
		cmbTypeStimulus.setToolTipText("Select type");
		cmbTypeStimulus.setFont(new Font("Cambria", Font.PLAIN, 12));
		cmbTypeStimulus.setBounds(240, 68, 147, 20);
		panel_2.add(cmbTypeStimulus);

		cmbTypeEnvironment = new JComboBox();
		cmbTypeEnvironment.setToolTipText("Select type");
		cmbTypeEnvironment.setFont(new Font("Cambria", Font.PLAIN, 12));
		cmbTypeEnvironment.setBounds(240, 96, 147, 20);
		panel_2.add(cmbTypeEnvironment);

		cmbTypeArtifact = new JComboBox();
		cmbTypeArtifact.setToolTipText("Select type");
		cmbTypeArtifact.setFont(new Font("Cambria", Font.PLAIN, 12));
		cmbTypeArtifact.setBounds(240, 127, 147, 20);
		panel_2.add(cmbTypeArtifact);

		cmbTypeResponse = new JComboBox();
		cmbTypeResponse.setToolTipText("Select type");
		cmbTypeResponse.setFont(new Font("Cambria", Font.PLAIN, 12));
		cmbTypeResponse.setBounds(240, 158, 147, 20);
		panel_2.add(cmbTypeResponse);

		cmbTypeResponseMeasure = new JComboBox();
		cmbTypeResponseMeasure.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (cmbTypeResponseMeasure.getSelectedItem() != "") {
					cmbTypeResponseMeasureItemStateChanged((ResponseMeasureType) cmbTypeResponseMeasure
							.getSelectedItem());
				} else {
					cmbMetric.setSelectedItem("");
					cmbMetric.setEnabled(false);
					cmbUnit.setSelectedItem("");
					cmbUnit.setEnabled(false);
				}
			}
		});
		cmbTypeResponseMeasure.setToolTipText("Select type");
		cmbTypeResponseMeasure.setFont(new Font("Cambria", Font.PLAIN, 12));
		cmbTypeResponseMeasure.setBounds(240, 189, 147, 20);
		panel_2.add(cmbTypeResponseMeasure);

		JLabel lblValue = new JLabel("Value");
		lblValue.setFont(new Font("Cambria", Font.PLAIN, 12));
		lblValue.setBounds(554, 11, 46, 14);
		panel_2.add(lblValue);

		txtValueStimulusSource = new JTextField();
		txtValueStimulusSource.setToolTipText("Insert value");
		txtValueStimulusSource.setFont(new Font("Cambria", Font.PLAIN, 12));
		txtValueStimulusSource.setBounds(554, 43, 111, 20);
		panel_2.add(txtValueStimulusSource);
		txtValueStimulusSource.setColumns(10);

		txtValueStimulus = new JTextField();
		txtValueStimulus.setToolTipText("Insert value");
		txtValueStimulus.setFont(new Font("Cambria", Font.PLAIN, 12));
		txtValueStimulus.setBounds(554, 68, 111, 20);
		panel_2.add(txtValueStimulus);
		txtValueStimulus.setColumns(10);

		txtValueEnvironment = new JTextField();
		txtValueEnvironment.setToolTipText("Insert value");
		txtValueEnvironment.setFont(new Font("Cambria", Font.PLAIN, 12));
		txtValueEnvironment.setBounds(554, 96, 111, 20);
		panel_2.add(txtValueEnvironment);
		txtValueEnvironment.setColumns(10);

		txtValueResponse = new JTextField();
		txtValueResponse.setToolTipText("Insert value");
		txtValueResponse.setFont(new Font("Cambria", Font.PLAIN, 12));
		txtValueResponse.setBounds(554, 158, 111, 20);
		panel_2.add(txtValueResponse);
		txtValueResponse.setColumns(10);

		txtValueResponseMeasure = new JTextField();
		txtValueResponseMeasure.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				valueResponseMeasureKeyTyped(arg0);
			}
		});
		txtValueResponseMeasure.setToolTipText("Insert value");
		txtValueResponseMeasure.setFont(new Font("Cambria", Font.PLAIN, 12));
		txtValueResponseMeasure.setBounds(554, 189, 111, 20);
		panel_2.add(txtValueResponseMeasure);
		txtValueResponseMeasure.setColumns(10);

		JLabel lblUnit = new JLabel("Unit");
		lblUnit.setFont(new Font("Cambria", Font.PLAIN, 12));
		lblUnit.setBounds(675, 11, 46, 14);
		panel_2.add(lblUnit);

		JLabel lblMetric = new JLabel("Metric");
		lblMetric.setFont(new Font("Cambria", Font.PLAIN, 12));
		lblMetric.setBounds(397, 11, 46, 14);
		panel_2.add(lblMetric);

		cmbMetric = new JComboBox();
		cmbMetric.setFont(new Font("Cambria", Font.PLAIN, 12));
		cmbMetric.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (cmbMetric.getSelectedItem() != "") {
					cmbMetricItemStateChanged((Metric) cmbMetric
							.getSelectedItem());
				} else {
					cmbUnit.setSelectedItem("");
					cmbUnit.setEnabled(false);
				}
			}
		});

		cmbMetric.setBounds(397, 189, 147, 20);

		panel_2.add(cmbMetric);

		cmbUnit = new JComboBox();
		cmbUnit.setFont(new Font("Cambria", Font.PLAIN, 12));
		cmbUnit.setBounds(675, 189, 150, 20);
		panel_2.add(cmbUnit);
		contentPane.setLayout(null);
		panel.setLayout(null);

		JLabel lblSystem = new JLabel("System");
		lblSystem.setFont(new Font("Cambria", Font.PLAIN, 12));
		lblSystem.setBounds(10, 23, 117, 14);
		panel.add(lblSystem);
		contentPane.add(panel);

		cmbSystem = new JComboBox();
		cmbSystem.setBounds(137, 20, 147, 20);
		panel.add(cmbSystem);
		cmbSystem.setToolTipText("Select system");
		cmbSystem.setFont(new Font("Cambria", Font.PLAIN, 12));

		JButton btnSearch = new JButton("Search");
		btnSearch.setBounds(294, 19, 115, 23);
		panel.add(btnSearch);
		btnSearch.setToolTipText("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (getViewController().getManager()
						.existSystemTrueWithQualityRequirementTrue()) {
					getViewController().openFrmQualityRequirementSearch(0);
				} else {
					JOptionPane.showMessageDialog(
							null,
							"No saved quality requirements",
							"Warning",
							JOptionPane.ERROR_MESSAGE,
							new ImageIcon(FrmSystemManagement.class
									.getResource("/Icons/error.png")));
				}
			}
		});
		btnSearch.setIcon(new ImageIcon(FrmQualityRequirementManagement.class
				.getResource("/Icons/search.png")));
		btnSearch.setFont(new Font("Cambria", Font.PLAIN, 12));
		contentPane.add(panel_1);

		lblCondition = new JLabel("Condition");
		lblCondition.setFont(new Font("Cambria", Font.PLAIN, 12));
		lblCondition.setBounds(490, 107, 63, 14);
		panel_1.add(lblCondition);

		cmbCondition = new JComboBox();
		cmbCondition.setFont(new Font("Cambria", Font.PLAIN, 12));
		cmbCondition.setBounds(563, 104, 147, 20);
		panel_1.add(cmbCondition);

		cmbQualityAttribute = new JComboBox();
		cmbQualityAttribute.setBounds(136, 104, 147, 20);
		panel_1.add(cmbQualityAttribute);
		cmbQualityAttribute.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (cmbQualityAttribute.getSelectedItem() != "") {
					cmbQualityAttributeItemStateChanged();
				} else {
					cmbTypeStimulusSource.setSelectedItem("");
					cmbTypeStimulusSource.setEnabled(false);
					cmbTypeStimulus.setSelectedItem("");
					cmbTypeStimulus.setEnabled(false);
					cmbTypeEnvironment.setSelectedItem("");
					cmbTypeEnvironment.setEnabled(false);
					cmbTypeArtifact.setSelectedItem("");
					cmbTypeArtifact.setEnabled(false);
					cmbTypeResponse.setSelectedItem("");
					cmbTypeResponse.setEnabled(false);
					cmbTypeResponseMeasure.setSelectedItem("");
					cmbTypeResponseMeasure.setEnabled(false);
					cmbMetric.setSelectedItem("");
					cmbMetric.setEnabled(false);
					cmbUnit.setSelectedItem("");
					cmbUnit.setEnabled(false);

				}
			}
		});
		cmbQualityAttribute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		cmbQualityAttribute.setToolTipText("Select quality attribute");
		cmbQualityAttribute.setFont(new Font("Cambria", Font.PLAIN, 12));

		JButton btnExit = new JButton("Exit");
		btnExit.setToolTipText("Exit");
		btnExit.setIcon(new ImageIcon(FrmQualityRequirementManagement.class
				.getResource("/Icons/exit.png")));
		btnExit.setFont(new Font("Cambria", Font.PLAIN, 12));
		btnExit.setBounds(745, 492, 115, 23);
		contentPane.add(btnExit);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.setIcon(new ImageIcon(FrmQualityRequirementManagement.class
				.getResource("/Icons/cancel.png")));
		btnCancel.setToolTipText("Cancel");
		btnCancel.setFont(new Font("Cambria", Font.PLAIN, 12));
		btnCancel.setBounds(620, 492, 115, 23);
		contentPane.add(btnCancel);

		btnRemove = new JButton("Remove");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (JOptionPane.showOptionDialog(
						null,
						"Do you want to delete the quality requirement?",
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
		btnRemove.setIcon(new ImageIcon(FrmQualityRequirementManagement.class
				.getResource("/Icons/remove.png")));
		btnRemove.setToolTipText("Remove");
		btnRemove.setFont(new Font("Cambria", Font.PLAIN, 12));
		btnRemove.setBounds(438, 492, 115, 23);
		contentPane.add(btnRemove);

		btnEdit = new JButton("Edit");
		btnEdit.setIcon(new ImageIcon(FrmQualityRequirementManagement.class
				.getResource("/Icons/edit.png")));
		btnEdit.setToolTipText("Edit");
		btnEdit.setFont(new Font("Cambria", Font.PLAIN, 12));
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				prepareView(7);
			}
		});
		btnEdit.setBounds(313, 492, 115, 23);
		contentPane.add(btnEdit);

		btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				saveView();
			}
		});
		btnSave.setIcon(new ImageIcon(FrmQualityRequirementManagement.class
				.getResource("/Icons/Save.png")));
		btnSave.setToolTipText("Save");
		btnSave.setFont(new Font("Cambria", Font.PLAIN, 12));
		btnSave.setBounds(188, 492, 115, 23);
		contentPane.add(btnSave);

		btnNew = new JButton("New");
		btnNew.setIcon(new ImageIcon(FrmQualityRequirementManagement.class
				.getResource("/Icons/new.png")));
		btnNew.setToolTipText("New");
		btnNew.setFont(new Font("Cambria", Font.PLAIN, 12));
		btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getViewController().setOpcABM(0);
				prepareView(getViewController().getOpcABM());
			}
		});
		btnNew.setBounds(63, 492, 115, 23);
		contentPane.add(btnNew);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearView();
				getViewController().setOpcABM(3);
				prepareView(getViewController().getOpcABM());
			}
		});
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
	}

	/**
	 * Validates that a decimal number is written
	 */
	private void valueResponseMeasureKeyTyped(KeyEvent parg0) {
		if (parg0.getKeyChar() == 46) {
			if (viewController.firstPoint(txtValueResponseMeasure.getText())) {
				if (firstPoint) {
					txtValueResponseMeasure.setText("0");
					firstTime = false;
				} else {
					txtValueResponseMeasure.setText("0.");
				}

			} else {
				if (parg0.getKeyChar() == 46 && !firstPoint) {
					if (!viewController.checkPoint(txtValueResponseMeasure
							.getText())) {
						firstPoint = true;
					}
				}
			}
		}
		if (Character.isDigit(parg0.getKeyChar())) {

		} else if (parg0.getKeyChar() == 46 && firstPoint) {
			firstPoint = false;
		} else {
			parg0.setKeyCode(Character.MIN_VALUE);
			parg0.consume();
		}
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

	public QualityRequirementController getViewController() {
		return viewController;
	}

	public void setViewController(QualityRequirementController pviewController) {
		this.viewController = pviewController;
	}

	public JComboBox getCmbQualityAttribute() {
		return cmbQualityAttribute;
	}

	public void setCmbQualityAttribute(JComboBox pcmbQualityAttribute) {
		this.cmbQualityAttribute = pcmbQualityAttribute;
	}

	public JComboBox getCmbSystem() {
		return cmbSystem;
	}

	public void setCmbSystem(JComboBox pcmbSystem) {
		this.cmbSystem = pcmbSystem;
	}

	public JComboBox getCmbTypeStimulusSource() {
		return cmbTypeStimulusSource;
	}

	public void setCmbTypeStimulusSource(JComboBox pcmbTypeStimulusSource) {
		this.cmbTypeStimulusSource = pcmbTypeStimulusSource;
	}

	public JComboBox getCmbTypeStimulus() {
		return cmbTypeStimulus;
	}

	public void setCmbTypeStimulus(JComboBox pcmbTypeStimulus) {
		this.cmbTypeStimulus = pcmbTypeStimulus;
	}

	public JComboBox getCmbTypeEnvironment() {
		return cmbTypeEnvironment;
	}

	public void setCmbTypeEnvironment(JComboBox pcmbTypeEnvironment) {
		this.cmbTypeEnvironment = pcmbTypeEnvironment;
	}

	public JComboBox getCmbTypeArtifact() {
		return cmbTypeArtifact;
	}

	public void setCmbTypeArtifact(JComboBox pcmbTypeArtifact) {
		this.cmbTypeArtifact = pcmbTypeArtifact;
	}

	public JComboBox getCmbTypeResponse() {
		return cmbTypeResponse;
	}

	public void setCmbTypeResponse(JComboBox pcmbTypeResponse) {
		this.cmbTypeResponse = pcmbTypeResponse;
	}

	public JComboBox getCmbTypeResponseMeasure() {
		return cmbTypeResponseMeasure;
	}

	public void setCmbTypeResponseMeasure(JComboBox pcmbTypeResponseMeasure) {
		this.cmbTypeResponseMeasure = pcmbTypeResponseMeasure;
	}

	public JComboBox getCmbMetric() {
		return cmbMetric;
	}

	public void setCmbMetric(JComboBox pcmbMetric) {
		this.cmbMetric = pcmbMetric;
	}

	public JComboBox getCmbUnit() {
		return cmbUnit;
	}

	public void setCmbUnit(JComboBox pcmbUnit) {
		this.cmbUnit = pcmbUnit;
	}

	public JTextArea getTxtDescription() {
		return txtDescription;
	}

	public void setTxtDescription(JTextArea ptxtDescription) {
		this.txtDescription = ptxtDescription;
	}

	public JTextField getTxtDescriptionStimulusSource() {
		return txtDescriptionStimulusSource;
	}

	public void setTxtDescriptionStimulusSource(
			JTextField ptxtDescriptionStimulusSource) {
		this.txtDescriptionStimulusSource = ptxtDescriptionStimulusSource;
	}

	public JTextField getTxtDescriptionStimulus() {
		return txtDescriptionStimulus;
	}

	public void setTxtDescriptionStimulus(JTextField ptxtDescriptionStimulus) {
		this.txtDescriptionStimulus = ptxtDescriptionStimulus;
	}

	public JTextField getTxtDescriptionEnvironment() {
		return txtDescriptionEnvironment;
	}

	public void setTxtDescriptionEnvironment(
			JTextField ptxtDescriptionEnvironment) {
		this.txtDescriptionEnvironment = ptxtDescriptionEnvironment;
	}

	public JTextField getTxtDescriptionArtifact() {
		return txtDescriptionArtifact;
	}

	public void setTxtDescriptionArtifact(JTextField ptxtDescriptionArtefact) {
		this.txtDescriptionArtifact = ptxtDescriptionArtefact;
	}

	public JTextField getTxtDescriptionResponse() {
		return txtDescriptionResponse;
	}

	public void setTxtDescriptionResponse(JTextField ptxtDescriptionResponse) {
		this.txtDescriptionResponse = ptxtDescriptionResponse;
	}

	public JTextField getTxtDescriptionResponseMeasure() {
		return txtDescriptionResponseMeasure;
	}

	public void setTxtDescriptionResponseMeasure(
			JTextField ptxtDescriptionResponseMeasure) {
		this.txtDescriptionResponseMeasure = ptxtDescriptionResponseMeasure;
	}

	public JTextField getTxtValueStimulusSource() {
		return txtValueStimulusSource;
	}

	public void setTxtValueStimulusSource(JTextField ptxtValueStimulusSource) {
		this.txtValueStimulusSource = ptxtValueStimulusSource;
	}

	public JTextField getTxtValueStimulus() {
		return txtValueStimulus;
	}

	public void setTxtValueStimulus(JTextField ptxtValueStimulus) {
		this.txtValueStimulus = ptxtValueStimulus;
	}

	public JTextField getTxtValueEnvironment() {
		return txtValueEnvironment;
	}

	public void setTxtValueEnvironment(JTextField ptxtValueEnvironment) {
		this.txtValueEnvironment = ptxtValueEnvironment;
	}

	public JTextField getTxtValueResponse() {
		return txtValueResponse;
	}

	public void setTxtValueResponse(JTextField ptxtValueResponse) {
		this.txtValueResponse = ptxtValueResponse;
	}

	public JTextField getTxtValueResponseMeasure() {
		return txtValueResponseMeasure;
	}

	public void setTxtValueResponseMeasure(JTextField ptxtValueResponseMeasure) {
		this.txtValueResponseMeasure = ptxtValueResponseMeasure;
	}

	public JComboBox getCmbCondition() {
		return cmbCondition;
	}

	public void setCmbCondition(JComboBox pcmbCondition) {
		this.cmbCondition = pcmbCondition;
	}

	/**
	 * load the combos: system, quality attribute and condition
	 */
	public void loadCombo() {
		this.getViewController().setModelSystem();
		this.getViewController().setModelQualityAttribute();
		this.getViewController().setModelCondition();
	}

	/**
	 * load the type combos (stimulusSource, stimulus, environment, artifact,
	 * response and response measure) for a specific quality attribute
	 * 
	 * @param qualityAttribute
	 */
	public void loadCombosTypes(QualityAttribute qualityAttribute) {
		this.getViewController().setModelStimulusSourceTypes(qualityAttribute);
		this.getViewController().setModelStimulusTypes(qualityAttribute);
		this.getViewController().setModelEnvironmentTypes(qualityAttribute);
		this.getViewController().setModelArtifactTypes(qualityAttribute);
		this.getViewController().setModelResponseTypes(qualityAttribute);
		this.getViewController().setModelResponseMeasureTypes(qualityAttribute);
	}

	/**
	 * load the metric combo for a specific response measure type
	 * @param ptype
	 */
	public void loadComboMetric(ResponseMeasureType ptype) {
		this.getViewController().setModelMetric(ptype);
	}

	/**
	 * load the unit combo for a specific metric
	 * @param ptype
	 */
	public void loadComboUnit(Metric ptype) {
		this.getViewController().setModelUnit(ptype);
	}

	/**
	 * It detects when the quality attribute is changed and combos types are updated
	 */
	private void cmbQualityAttributeItemStateChanged() {// GEN-FIRST:event_cmbNombreItemStateChanged
		this.prepareView(4);
		this.loadCombosTypes((QualityAttribute) this.getCmbQualityAttribute()
				.getSelectedItem());
	}

	/**
	 * It detects when the measure type is changed and combo metric is updated
	 */
	private void cmbTypeResponseMeasureItemStateChanged(
			ResponseMeasureType ptype) {// GEN-FIRST:event_cmbNombreItemStateChanged
		this.prepareView(5);
		this.loadComboMetric(ptype);
	}

	/**
	 * It detects when the metric is changed and combo unit is updated
	 */
	private void cmbMetricItemStateChanged(Metric ptype) {
		this.prepareView(6);
		this.loadComboUnit(ptype);
	}

	/**
	 * Prepare the view according to option (Manage, New, Search, Remove, Edit, etc)
	 * 
	 * @param pabm
	 */
	public void prepareView(int pabm) {
		switch (pabm) {
		case 0:// New
			btnNew.setEnabled(false);
			btnSave.setEnabled(true);
			btnEdit.setEnabled(false);
			btnRemove.setEnabled(false);
			cmbSystem.setEnabled(true);
			txtDescription.setEnabled(true);
			cmbQualityAttribute.setEnabled(true);
			cmbCondition.setEnabled(true);
			loadCombo();
			break;
		case 1:// Search
			btnNew.setEnabled(true);
			btnSave.setEnabled(false);
			btnEdit.setEnabled(false);
			btnRemove.setEnabled(false);
			cmbSystem.setEnabled(true);
			cmbSystem.grabFocus();
			txtDescription.setEnabled(false);
			cmbQualityAttribute.setEnabled(true);
			cmbCondition.setEnabled(true);
			txtDescriptionStimulusSource.setEnabled(false);
			txtDescriptionStimulus.setEnabled(false);
			txtDescriptionEnvironment.setEnabled(false);
			txtDescriptionArtifact.setEnabled(false);
			txtDescriptionResponse.setEnabled(false);
			txtDescriptionResponseMeasure.setEnabled(false);
			cmbTypeStimulusSource.setEnabled(false);
			cmbTypeStimulus.setEnabled(false);
			cmbTypeEnvironment.setEnabled(false);
			cmbTypeArtifact.setEnabled(false);
			cmbTypeResponse.setEnabled(false);
			cmbTypeResponseMeasure.setEnabled(false);
			cmbMetric.setEnabled(false);
			cmbUnit.setEnabled(false);
			txtValueStimulusSource.setEnabled(false);
			txtValueStimulus.setEnabled(false);
			txtValueEnvironment.setEnabled(false);
			txtValueResponse.setEnabled(false);
			txtValueResponseMeasure.setEnabled(false);
			break;
		case 2:// Remove-Edit
			btnNew.setEnabled(false);
			btnSave.setEnabled(false);
			btnEdit.setEnabled(true);
			btnRemove.setEnabled(true);
			cmbSystem.setVisible(true);
			cmbSystem.setEnabled(false);
			txtDescription.setEnabled(false);
			cmbQualityAttribute.setEnabled(false);
			cmbCondition.setEnabled(false);
			txtDescriptionStimulusSource.setEnabled(false);
			txtDescriptionStimulus.setEnabled(false);
			txtDescriptionEnvironment.setEnabled(false);
			txtDescriptionArtifact.setEnabled(false);
			txtDescriptionResponse.setEnabled(false);
			txtDescriptionResponseMeasure.setEnabled(false);
			cmbTypeStimulusSource.setEnabled(false);
			cmbTypeStimulus.setEnabled(false);
			cmbTypeEnvironment.setEnabled(false);
			cmbTypeArtifact.setEnabled(false);
			cmbTypeResponse.setEnabled(false);
			cmbTypeResponseMeasure.setEnabled(false);
			cmbMetric.setEnabled(false);
			cmbUnit.setEnabled(false);
			txtValueStimulusSource.setEnabled(false);
			txtValueStimulus.setEnabled(false);
			txtValueEnvironment.setEnabled(false);
			txtValueResponse.setEnabled(false);
			txtValueResponseMeasure.setEnabled(false);
			btnRemove.grabFocus();
			break;
		case 3: // Manage
			btnNew.setEnabled(true);
			btnSave.setEnabled(false);
			btnEdit.setEnabled(false);
			btnRemove.setEnabled(false);
			cmbSystem.setEnabled(false);
			txtDescription.setEnabled(false);
			cmbQualityAttribute.setEnabled(false);
			cmbCondition.setEnabled(false);
			txtDescriptionStimulusSource.setEnabled(false);
			txtDescriptionStimulus.setEnabled(false);
			txtDescriptionEnvironment.setEnabled(false);
			txtDescriptionArtifact.setEnabled(false);
			txtDescriptionResponse.setEnabled(false);
			txtDescriptionResponseMeasure.setEnabled(false);
			cmbTypeStimulusSource.setEnabled(false);
			cmbTypeStimulus.setEnabled(false);
			cmbTypeEnvironment.setEnabled(false);
			cmbTypeArtifact.setEnabled(false);
			cmbTypeResponse.setEnabled(false);
			cmbTypeResponseMeasure.setEnabled(false);
			cmbMetric.setEnabled(false);
			cmbUnit.setEnabled(false);
			txtValueStimulusSource.setEnabled(false);
			txtValueStimulus.setEnabled(false);
			txtValueEnvironment.setEnabled(false);
			txtValueResponse.setEnabled(false);
			txtValueResponseMeasure.setEnabled(false);
			break;
		case 4: // Quality Attribute Selected
			cmbQualityAttribute.setEnabled(true);
			cmbCondition.setEnabled(true);
			txtDescriptionStimulusSource.setEnabled(true);
			txtDescriptionStimulus.setEnabled(true);
			txtDescriptionEnvironment.setEnabled(true);
			txtDescriptionArtifact.setEnabled(true);
			txtDescriptionResponse.setEnabled(true);
			txtDescriptionResponseMeasure.setEnabled(true);
			cmbTypeStimulusSource.setEnabled(true);
			cmbTypeStimulus.setEnabled(true);
			cmbTypeEnvironment.setEnabled(true);
			cmbTypeArtifact.setEnabled(true);
			cmbTypeResponse.setEnabled(true);
			cmbTypeResponseMeasure.setEnabled(true);
			txtValueStimulusSource.setEnabled(true);
			txtValueStimulus.setEnabled(true);
			txtValueEnvironment.setEnabled(true);
			txtValueResponse.setEnabled(true);
			txtValueResponseMeasure.setEnabled(true);
			break;
		case 5: // ResponseMeasureType selected
			cmbMetric.setEnabled(true);
			break;
		case 6: // Metric selected
			cmbUnit.setEnabled(true);
			break;
		case 7: // Edit
			btnSave.setEnabled(true);
			btnEdit.setEnabled(false);
			btnRemove.setEnabled(false);
			txtDescription.setEnabled(true);
			txtDescriptionStimulusSource.setEnabled(true);
			txtDescriptionStimulus.setEnabled(true);
			txtDescriptionEnvironment.setEnabled(true);
			txtDescriptionArtifact.setEnabled(true);
			txtDescriptionResponse.setEnabled(true);
			txtDescriptionResponseMeasure.setEnabled(true);
			cmbTypeStimulusSource.setEnabled(true);
			cmbTypeStimulus.setEnabled(true);
			cmbTypeEnvironment.setEnabled(true);
			cmbTypeArtifact.setEnabled(true);
			cmbTypeResponse.setEnabled(true);
			cmbTypeResponseMeasure.setEnabled(true);
			cmbMetric.setEnabled(true);
			cmbUnit.setEnabled(true);
			txtValueStimulusSource.setEnabled(true);
			txtValueStimulus.setEnabled(true);
			txtValueEnvironment.setEnabled(true);
			txtValueResponse.setEnabled(true);
			txtValueResponseMeasure.setEnabled(true);
		}

	}

	public void saveView() {
		this.getViewController().saveView();
	}

	public void setView() {
		this.getViewController().getView();
	}

	/**
	 * Clear the view (clear: txt and cmb)
	 */
	public void clearView() {
		cmbSystem.setSelectedItem("");
		txtDescription.setText("");
		cmbQualityAttribute.setSelectedItem("");
		cmbCondition.setSelectedItem("");
		txtDescriptionStimulusSource.setText("");
		txtDescriptionStimulus.setText("");
		txtDescriptionEnvironment.setText("");
		txtDescriptionArtifact.setText("");
		txtDescriptionResponse.setText("");
		txtDescriptionResponseMeasure.setText("");
		cmbTypeStimulusSource.setSelectedItem("");
		cmbTypeStimulus.setSelectedItem("");
		cmbTypeEnvironment.setSelectedItem("");
		cmbTypeArtifact.setSelectedItem("");
		cmbTypeResponse.setSelectedItem("");
		cmbTypeResponseMeasure.setSelectedItem("");
		cmbMetric.setSelectedItem("");
		cmbUnit.setSelectedItem("");
		txtValueStimulusSource.setText("");
		txtValueStimulus.setText("");
		txtValueEnvironment.setText("");
		txtValueResponse.setText("");
		txtValueResponseMeasure.setText("");
	}

	public void removeView() {
		this.getViewController().removeView();
	}
}
