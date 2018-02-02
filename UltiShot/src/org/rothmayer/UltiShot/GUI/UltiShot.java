package org.rothmayer.UltiShot.GUI;

import java.awt.EventQueue;
import java.awt.Toolkit;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import java.awt.BorderLayout;
import java.awt.Dimension;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.rothmayer.UltiShot.Check.CheckWritePermission;
import org.rothmayer.UltiShot.Check.StartupCheck;
import org.rothmayer.UltiShot.DB.SMBD.Schuetze;
import org.rothmayer.UltiShot.GUI.elements.USProgressbar;
import org.rothmayer.UltiShot.Util.LocalDBCheck;
import org.rothmayer.UltiShot.Util.SMDBCheck;
import org.rothmayer.UltiShot.Util.SSMDB2Check;

import com.avaje.ebean.EbeanServer;
import com.sun.xml.internal.fastinfoset.util.StringArray;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class UltiShot {
	
	public static Logger logger = LogManager.getLogger(UltiShot.class.getName());
	public static String path = new File("").getAbsolutePath();
	public static EbeanServer smdb;
	public static EbeanServer ssmdb2;
	public static EbeanServer localDB;
	private USProgressbar progressBar;
	private List<StartupCheck> checkList = new ArrayList<>();

	private JFrame splashFrame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) { 
			try {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							UIManager.setLookAndFeel("com.jgoodies.looks.windows.WindowsLookAndFeel");
							UltiShot window = new UltiShot();
							window.splashFrame.setVisible(true);
							
						} catch (Exception e) {
							logger.error("", e);
						}
					}
				});
			} catch (Exception e) {
				logger.error("", e);
			}
			
	}

	/**
	 * Create the application.
	 */
	public UltiShot() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		//StartupCheck
		//-Write
		checkList.add(new CheckWritePermission());
		//-Configload
		//TODO load Config
		//-SQL
		System.out.println("-----------------");
		checkList.add(new LocalDBCheck());
		checkList.add(new SMDBCheck());
		checkList.add(new SSMDB2Check());
		
		splashFrame = new JFrame();
		splashFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				compLoader();
				splashFrame.setVisible(false);
				TeamAssignmentWindow frame = new TeamAssignmentWindow();
				frame.setVisible(true);
			}
		});
		splashFrame.getContentPane().setBackground(Color.DARK_GRAY);
		splashFrame.setUndecorated(true);
		splashFrame.setBounds(100, 100, 450, 300);
		splashFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Screen Center
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		splashFrame.setLocation(dim.width/2-splashFrame.getSize().width/2, dim.height/2-splashFrame.getSize().height/2);
		
		//Initialize Progressbar
		progressBar = new USProgressbar(checkList.size(), checkList.get(0).getMessage());
		splashFrame.getContentPane().add(progressBar, BorderLayout.SOUTH);
		
	}
	
	public void compLoader(){
		for(int i = 0 ; i < checkList.size(); i++){
			progressBar.setMessage(checkList.get(i).getMessage());
			if(!checkList.get(i).performCheck()){
				UltiShot.logger.fatal(checkList.get(i).getErrorMessage());
				String error = "<html><br>          " + checkList.get(i).getErrorMessage() + "         <br></html>";
				JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.WARNING_MESSAGE);
				System.exit(0);
			}

			progressBar.addTick();
		}
		
		smdb = SMDBCheck.getServer();
		ssmdb2 = SSMDB2Check.getServer();
		localDB = LocalDBCheck.getServer();
		//test();
	}
	
	void test(){
		
		List<Schuetze> list = smdb.find(Schuetze.class).findList();
				//List<Scheiben> list = (List<Scheiben>)session.createQuery("from Scheiben", Scheiben.class).list();
		for(Schuetze s : list){
			System.out.println("Nachname: " + s.getNachname() + " Vorname: " + s.getVorname());
		}
	}

	
	public static void setLevel(Level level) {
	    LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
	    Configuration config = ctx.getConfiguration();

	    LoggerConfig loggerConfig = new LoggerConfig();
	    loggerConfig.setLevel(level);

	    config.addLogger(logger.getName(), loggerConfig);

	    ctx.updateLoggers(config);
	}

}
