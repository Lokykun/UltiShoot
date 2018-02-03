package org.rothmayer.UltiShot.GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Toolkit;

public class LoggerWindow extends JFrame{


	/**
	 * 
	 */
	private static final long serialVersionUID = -9214906259231094192L;

	public LoggerWindow() {
		
		setTitle("UltiShot Log");
		setIconImage(Toolkit.getDefaultToolkit().getImage(LoggerWindow.class.getResource("/images/logo250.png")));
		setBounds(100, 100, 450, 300);
		
		JTextArea textLogger = new JTextArea();
		getContentPane().add(textLogger, BorderLayout.CENTER);
	}

}
