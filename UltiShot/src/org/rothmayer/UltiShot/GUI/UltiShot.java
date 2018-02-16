package org.rothmayer.UltiShot.GUI;

import java.awt.EventQueue;
import java.awt.Toolkit;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import java.awt.BorderLayout;
import java.awt.Dimension;

//import org.apache.log4j.BasicConfigurator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.DefaultConfiguration;
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


		Configurator.initialize(new DefaultConfiguration());


		//BasicConfigurator.configure();
			try {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							String osName = System.getProperty("os.name");
							if((osName != null) && (osName.indexOf("Windows") != -1)){
								//UIManager.setLookAndFeel("com.jgoodies.looks.windows.WindowsLookAndFeel");
								UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
							}else{
								UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
							}
							

							LoggerWindow logWindow = new LoggerWindow();
							UltiShot window = new UltiShot(logWindow);
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
	public UltiShot(LoggerWindow logWindow) {
		initialize(logWindow);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(LoggerWindow logWindow) {
		//Create TEMP
		/**File tmp = new File(System.getProperty( "user.home" ) + File.separator + "UltiShot"+ File.separator + "tmp" + File.separator);
		if(!tmp.exists()){
			tmp.mkdirs();
		}
		System.setProperty("java.io.tmpdir", tmp.getAbsolutePath());**/
		//StartupCheck
		//-Write
		checkList.add(new CheckWritePermission());
		//-Configload
		//TODO load Config
		//-SQL
		checkList.add(new LocalDBCheck());
		checkList.add(new SMDBCheck());
		checkList.add(new SSMDB2Check());
		
		splashFrame = new JFrame();
		splashFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(UltiShot.class.getResource("/images/logo250.png")));
		splashFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				compLoader();
				splashFrame.setVisible(false);
				MenuWindow frame = new MenuWindow(logWindow);
				frame.setVisible(true);
			}
		});
		splashFrame.getContentPane().setBackground(Color.DARK_GRAY);
		splashFrame.setUndecorated(true);
		splashFrame.setBounds(100, 100, 400, 430);
		splashFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Screen Center
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		splashFrame.setLocation(dim.width/2-splashFrame.getSize().width/2, dim.height/2-splashFrame.getSize().height/2);
		
		ImageIcon II = new ImageIcon(Toolkit.getDefaultToolkit().getImage(MenuWindow.class.getResource("/images/logo400.png")));
		JLabel lblImage = new JLabel(II);
		splashFrame.getContentPane().add(lblImage, BorderLayout.CENTER);
		
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
