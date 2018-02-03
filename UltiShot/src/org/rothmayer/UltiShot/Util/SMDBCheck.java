package org.rothmayer.UltiShot.Util;

import org.avaje.datasource.DataSourceConfig;
import org.rothmayer.UltiShot.Check.StartupCheck;
import org.rothmayer.UltiShot.DB.SMBD.Klasse;
import org.rothmayer.UltiShot.DB.SMBD.Mannschaft;
import org.rothmayer.UltiShot.DB.SMBD.Schuetze;
import org.rothmayer.UltiShot.DB.SMBD.Starterliste;
import org.rothmayer.UltiShot.DB.SMBD.Starterlisten;
import org.rothmayer.UltiShot.DB.SMBD.StarterlistePK;
import org.rothmayer.UltiShot.GUI.UltiShot;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.EbeanServerFactory;
import com.avaje.ebean.config.ServerConfig;
 
 
public class SMDBCheck implements StartupCheck
{
    private static EbeanServer server;
    private Exception exp;
    private ServerConfig config;
    
    public SMDBCheck()
    {
    	config = new ServerConfig();
    	/*
		List<Class<?>> tables = new ArrayList<>();
		config.setClasses(tables);
		*/
		config.setName("smdb");
		//config.loadFromProperties();

		// Define DataSource parameters  
		DataSourceConfig smdb = new DataSourceConfig();  
		smdb.setDriver("com.mysql.jdbc.Driver");  
		//TODO URL User Passwortd dynamisch laden
		smdb.setUsername("meyton");  
		smdb.setPassword("mc4hct");

		smdb.setUrl("jdbc:mysql://192.168.10.200:3306/SMDB?zeroDateTimeBehavior=convertToNull");  
		//smdb.setUrl("jdbc:mysql://192.168.10.200:3306/SMDB?zeroDateTimeBehavior=convertToNull");  
		smdb.setHeartbeatSql("select 1");  
		
    	
		config.setDataSourceConfig(smdb);
		config.addClass(Mannschaft.class);  
		config.addClass(Schuetze.class);
		config.addClass(Starterlisten.class);
		config.addClass(Starterliste.class);
		config.addClass(StarterlistePK.class);
		config.addClass(Klasse.class);
		// specify a JNDI DataSource   
		// config.setDataSourceJndiName("someJndiDataSourceName");  
		  
		// set DDL options...  
		//config.setDdlGenerate(true);  
		//config.setDdlRun(true);  
		  
		config.setDefaultServer(false);  
		config.setRegister(false);  
    }

	@Override
	public boolean performCheck() {
		try {
			server = EbeanServerFactory.create(config);
			return true;
		} catch (Exception e) {
			exp = e;
			UltiShot.logger.error("", e);
			return false;
		}
	}

	@Override
	public String getMessage() {
		return "Connect to database SMDB!";
	}

	@Override
	public String getErrorMessage() {
		return "Can't connect to database SMDB!";
	}

	public static EbeanServer getServer() {
		return server;
	}

	public Exception getExp() {
		return exp;
	}
}
