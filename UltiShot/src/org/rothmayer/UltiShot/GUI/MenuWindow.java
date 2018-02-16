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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MenuWindow extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = -636219048544578528L;
	
	public TargetAssignmentWindow taWindow;
	public TeamAssignmentWindow teWindow;
	public LoggerWindow logWindow;
	public JLabel lblStatus;

	public MenuWindow(LoggerWindow logWindow) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				taWindow.dispatchEvent(new WindowEvent(taWindow, WindowEvent.WINDOW_CLOSING));
				teWindow.dispatchEvent(new WindowEvent(teWindow, WindowEvent.WINDOW_CLOSING));
				logWindow.dispatchEvent(new WindowEvent(logWindow, WindowEvent.WINDOW_CLOSING));

			}
		});
		taWindow = new TargetAssignmentWindow(this);
		teWindow = new TeamAssignmentWindow();
		this.logWindow = logWindow;
		setResizable(false);
		setTitle("UltiShot Men\u00FC");
		setIconImage(Toolkit.getDefaultToolkit().getImage(MenuWindow.class.getResource("/images/logo250.png")));
		setBounds(100, 100, 250, 540);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel northPanel = new JPanel();
		getContentPane().add(northPanel, BorderLayout.NORTH);
		
		Box verticalBox = Box.createVerticalBox();
		verticalBox.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		
		//JImage lblLogo = new JImage(Toolkit.getDefaultToolkit().getImage(MenuWindow.class.getResource("/images/logo250.png")));
		ImageIcon II = new ImageIcon(Toolkit.getDefaultToolkit().getImage(MenuWindow.class.getResource("/images/logo250.png")));
		JLabel lblImage = new JLabel(II);
		lblImage.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblImage.setHorizontalTextPosition(SwingConstants.CENTER);
		verticalBox.add(lblImage);
		
		JLabel lblUltishorMen = new JLabel("UltiShot Men\u00FC");
		lblUltishorMen.setPreferredSize(new Dimension(230, 50));
		lblUltishorMen.setMinimumSize(new Dimension(230, 50));
		lblUltishorMen.setMaximumSize(new Dimension(230, 50));
		lblUltishorMen.setHorizontalTextPosition(SwingConstants.CENTER);
		lblUltishorMen.setHorizontalAlignment(SwingConstants.CENTER);
		lblUltishorMen.setFont(new Font("Segoe UI", Font.PLAIN, 30));
		lblUltishorMen.setAlignmentX(Component.CENTER_ALIGNMENT);
		verticalBox.add(lblUltishorMen);
		GroupLayout gl_northPanel = new GroupLayout(northPanel);
		gl_northPanel.setHorizontalGroup(
			gl_northPanel.createParallelGroup(Alignment.LEADING)
				.addComponent(verticalBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		);
		gl_northPanel.setVerticalGroup(
			gl_northPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_northPanel.createSequentialGroup()
					.addGap(5)
					.addComponent(verticalBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		);
		northPanel.setLayout(gl_northPanel);
		
		JPanel centerPanel = new JPanel();
		getContentPane().add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		Box verticalBox_1 = Box.createVerticalBox();
		verticalBox_1.setMinimumSize(new Dimension(250, 0));
		centerPanel.add(verticalBox_1);
		
		JButton btnMannschaftsZuordnung = new JButton("Mannschaftszuordnung");
		btnMannschaftsZuordnung.setPreferredSize(new Dimension(230, 29));
		btnMannschaftsZuordnung.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				teWindow.setVisible(true);
			}
		});
		btnMannschaftsZuordnung.setMaximumSize(new Dimension(230, 29));
		btnMannschaftsZuordnung.setMinimumSize(new Dimension(230, 29));
		verticalBox_1.add(btnMannschaftsZuordnung);
		btnMannschaftsZuordnung.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		verticalStrut_1.setMaximumSize(new Dimension(32767, 5));
		verticalStrut_1.setMinimumSize(new Dimension(0, 5));
		verticalBox_1.add(verticalStrut_1);
		
		JButton btnAuswerter = new JButton("Auswerter");
		btnAuswerter.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				taWindow.setVisible(true);
			}
		});
		btnAuswerter.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnAuswerter.setMaximumSize(new Dimension(250, 29));
		btnAuswerter.setMinimumSize(new Dimension(250, 29));
		verticalBox_1.add(btnAuswerter);
		
		Component verticalStrut_2 = Box.createVerticalStrut(20);
		verticalStrut_2.setMinimumSize(new Dimension(0, 5));
		verticalStrut_2.setMaximumSize(new Dimension(32767, 5));
		verticalBox_1.add(verticalStrut_2);
		
		Component verticalStrut_3 = Box.createVerticalStrut(20);
		verticalStrut_3.setMinimumSize(new Dimension(0, 5));
		verticalStrut_3.setMaximumSize(new Dimension(32767, 5));
		verticalBox_1.add(verticalStrut_3);
		
		JButton btnLog = new JButton("Log");
		btnLog.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				logWindow.setVisible(true);
			}
		});
		btnLog.setMinimumSize(new Dimension(250, 29));
		btnLog.setMaximumSize(new Dimension(250, 29));
		btnLog.setAlignmentX(0.5f);
		verticalBox_1.add(btnLog);
		
		JPanel trailPanel = new JPanel();
		trailPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		trailPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		getContentPane().add(trailPanel, BorderLayout.SOUTH);
		trailPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JLabel lblStatusText = new JLabel("Status Auswerter: ");
		lblStatusText.setHorizontalAlignment(SwingConstants.LEFT);
		lblStatusText.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		trailPanel.add(lblStatusText);
		
		lblStatus = new JLabel("gestoppt");
		trailPanel.add(lblStatus);
		
	}
}
