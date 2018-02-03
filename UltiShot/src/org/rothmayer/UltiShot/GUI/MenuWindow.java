package org.rothmayer.UltiShot.GUI;

import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.JPanel;

import org.rothmayer.UltiShot.GUI.elements.JImage;
import java.awt.Component;
import javax.swing.Box;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.Dimension;
import java.awt.ComponentOrientation;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.SwingConstants;

public class MenuWindow {

	private JFrame frmUltishotMen;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuWindow window = new MenuWindow();
					window.frmUltishotMen.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MenuWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmUltishotMen = new JFrame();
		frmUltishotMen.setResizable(false);
		frmUltishotMen.setTitle("UltiShot Men\u00FC");
		frmUltishotMen.setIconImage(Toolkit.getDefaultToolkit().getImage(MenuWindow.class.getResource("/images/logo250.png")));
		frmUltishotMen.setBounds(100, 100, 250, 540);
		frmUltishotMen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel northPanel = new JPanel();
		frmUltishotMen.getContentPane().add(northPanel, BorderLayout.NORTH);
		northPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		Box verticalBox = Box.createVerticalBox();
		northPanel.add(verticalBox);
		
		JImage lblLogo = new JImage(Toolkit.getDefaultToolkit().getImage(MenuWindow.class.getResource("/images/logo250.png")));
		verticalBox.add(lblLogo);
		
		JLabel lblUltishorMen = new JLabel("UltiShot Men\u00FC");
		lblUltishorMen.setFont(new Font("Tahoma", Font.PLAIN, 35));
		lblUltishorMen.setAlignmentX(Component.CENTER_ALIGNMENT);
		verticalBox.add(lblUltishorMen);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		verticalBox.add(verticalStrut);
		
		JPanel centerPanel = new JPanel();
		frmUltishotMen.getContentPane().add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		Box verticalBox_1 = Box.createVerticalBox();
		verticalBox_1.setMinimumSize(new Dimension(250, 0));
		centerPanel.add(verticalBox_1);
		
		JButton btnMannschaftsZuordnung = new JButton("Mannschafts Zuordnung");
		btnMannschaftsZuordnung.setMaximumSize(new Dimension(250, 29));
		btnMannschaftsZuordnung.setMinimumSize(new Dimension(250, 29));
		verticalBox_1.add(btnMannschaftsZuordnung);
		btnMannschaftsZuordnung.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		verticalStrut_1.setMaximumSize(new Dimension(32767, 5));
		verticalStrut_1.setMinimumSize(new Dimension(0, 5));
		verticalBox_1.add(verticalStrut_1);
		
		JButton btnAuswerter = new JButton("Auswerter");
		btnAuswerter.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnAuswerter.setMaximumSize(new Dimension(250, 29));
		btnAuswerter.setMinimumSize(new Dimension(250, 29));
		verticalBox_1.add(btnAuswerter);
		
		Component verticalStrut_2 = Box.createVerticalStrut(20);
		verticalStrut_2.setMinimumSize(new Dimension(0, 5));
		verticalStrut_2.setMaximumSize(new Dimension(32767, 5));
		verticalBox_1.add(verticalStrut_2);
		
		JButton btnConfig = new JButton("Configuration");
		btnConfig.setMinimumSize(new Dimension(250, 29));
		btnConfig.setMaximumSize(new Dimension(250, 29));
		btnConfig.setAlignmentX(0.5f);
		verticalBox_1.add(btnConfig);
		
		Component verticalStrut_3 = Box.createVerticalStrut(20);
		verticalStrut_3.setMinimumSize(new Dimension(0, 5));
		verticalStrut_3.setMaximumSize(new Dimension(32767, 5));
		verticalBox_1.add(verticalStrut_3);
		
		JButton btnLog = new JButton("Log");
		btnLog.setMinimumSize(new Dimension(250, 29));
		btnLog.setMaximumSize(new Dimension(250, 29));
		btnLog.setAlignmentX(0.5f);
		verticalBox_1.add(btnLog);
		
		JPanel trailPanel = new JPanel();
		trailPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		trailPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		frmUltishotMen.getContentPane().add(trailPanel, BorderLayout.SOUTH);
		trailPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JLabel lblStatusText = new JLabel("Status Auswerter: ");
		lblStatusText.setHorizontalAlignment(SwingConstants.LEFT);
		lblStatusText.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		trailPanel.add(lblStatusText);
		
		JLabel lblStatus = new JLabel("gestoppt");
		trailPanel.add(lblStatus);
		
	}
}
