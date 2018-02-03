package org.rothmayer.UltiShot.GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Toolkit;

public class LoggerWindow {

	private JFrame frmUltishotLog;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoggerWindow window = new LoggerWindow();
					window.frmUltishotLog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoggerWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmUltishotLog = new JFrame();
		frmUltishotLog.setTitle("UltiShot Log");
		frmUltishotLog.setIconImage(Toolkit.getDefaultToolkit().getImage(LoggerWindow.class.getResource("/images/logo250.png")));
		frmUltishotLog.setBounds(100, 100, 450, 300);
		frmUltishotLog.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTextArea textLogger = new JTextArea();
		frmUltishotLog.getContentPane().add(textLogger, BorderLayout.CENTER);
	}

}
