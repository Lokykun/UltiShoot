package org.rothmayer.UltiShot.GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.rothmayer.UltiShot.Util.CustomOutputStream;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;
import java.io.PrintStream;

public class LoggerWindow extends JFrame{


	/**
	 * 
	 */
	private static final long serialVersionUID = -9214906259231094192L;

	public LoggerWindow() {
		
		setTitle("UltiShot Log");
		setIconImage(Toolkit.getDefaultToolkit().getImage(LoggerWindow.class.getResource("/images/logo250.png")));
		setBounds(100, 100, 1200, 600);
		
		JTextArea textLogger = new JTextArea();
		textLogger.setEditable(false);
		textLogger.setBackground(Color.WHITE);

		JScrollPane scroll = new JScrollPane (textLogger);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		
		
		//PrintStream printStream = new PrintStream(new CustomOutputStream(textLogger));
		//System.setOut(printStream);
		//System.setErr(printStream);
		getContentPane().add(scroll, BorderLayout.CENTER);
		
	}

}
