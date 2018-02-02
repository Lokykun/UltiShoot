package org.rothmayer.UltiShot.Util;

import org.avaje.datasource.DataSourceConfig;
import org.rothmayer.UltiShot.Check.StartupCheck;
import org.rothmayer.UltiShot.DB.SSMBD2.Scheiben;
import org.rothmayer.UltiShot.DB.SSMBD2.Serien;
import org.rothmayer.UltiShot.DB.SSMBD2.SerienPK;
import org.rothmayer.UltiShot.DB.SSMBD2.Treffer;
import org.rothmayer.UltiShot.DB.SSMBD2.TrefferPK;
import org.rothmayer.UltiShot.GUI.UltiShotAuswerung;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.EbeanServerFactory;
import com.avaje.ebean.config.ServerConfig;
 
 
public class SSMDB2Check implements StartupCheck
{
    private static EbeanServer server;
    private Exception exp;
    private ServerConfig config;
    
    public SSMDB2Check()
    {
    	config = new ServerConfig();
    	/*
		List<Class<?>> tables = new ArrayList<>();
		config.setClasses(tables);
		*/
		config.setName("ssmdb2");
		//config.loadFromProperties();

		// Define DataSource parameters  
		DataSourceConfig ssmbd2 = new DataSourceConfig();  
		ssmbd2.setDriver("com.mysql.jdbc.Driver");  
		//TODO URL User Passwortd dynamisch laden
		ssmbd2.setUsername("meyton");  
		ssmbd2.setPassword("mc4hct");
		ssmbd2.setUrl("jdbc:mysql://192.168.10.200:3306/SSMDB2");  
		ssmbd2.setHeartbeatSql("select 1");  
		
    	
		config.setDataSourceConfig(ssmbd2);
		config.addClass(Scheiben.class);  
		config.addClass(Treffer.class);
		config.addClass(TrefferPK.class);
		config.addClass(Serien.class); 
		config.addClass(SerienPK.class);
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
			UltiShotAuswerung.logger.error("", e);
			return false;
		}
	}

	@Override
	public String getMessage() {
		return "Connect to database SSMDB2!";
	}

	@Override
	public String getErrorMessage() {
		return "Can't connect to database SSMDB2!";
	}

	public static EbeanServer getServer() {
		return server;
	}

	public Exception getExp() {
		return exp;
	}
}
