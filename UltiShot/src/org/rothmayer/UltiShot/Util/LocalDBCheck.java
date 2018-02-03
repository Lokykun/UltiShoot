package org.rothmayer.UltiShot.Util;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;

import org.avaje.datasource.DataSourceConfig;
import org.rothmayer.UltiShot.Check.StartupCheck;
import org.rothmayer.UltiShot.DB.Local.Zuweisung;
import org.rothmayer.UltiShot.DB.Local.ZuweisungPK;
import org.rothmayer.UltiShot.GUI.UltiShot;
import com.avaje.ebean.Ebean;
import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.EbeanServerFactory;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.config.ServerConfig;
import com.mysql.jdbc.Statement;
 
 
public class LocalDBCheck implements StartupCheck
{
    private static EbeanServer server;
    private Exception exp;
    private ServerConfig config;
    
    public LocalDBCheck()
    {
    	
    	config = new ServerConfig();
    	/*
		List<Class<?>> tables = new ArrayList<>();
		config.setClasses(tables);
		*/
		config.setName("local");
		//config.loadFromProperties();

		// Define DataSource parameters  
		DataSourceConfig local = new DataSourceConfig();  
		local.setDriver("org.sqlite.JDBC");  
		//TODO URL User Passwortd dynamisch laden
		local.setUsername("");  
		local.setPassword("");
		local.setUrl("jdbc:sqlite:" + System.getProperty( "user.home" ) + "\\local.db");  
		local.setIsolationLevel(Connection.TRANSACTION_SERIALIZABLE);
		//smdb.setHeartbeatSql("select 1");  
		
    	
		config.setDataSourceConfig(local);
		config.addClass(Zuweisung.class);
		config.addClass(ZuweisungPK.class);    
		// specify a JNDI DataSource   
		// config.setDataSourceJndiName("someJndiDataSourceName");  
		  
		// set DDL options...  
		  
		config.setDefaultServer(false);  
		config.setRegister(false);  
    }

	@Override
	public boolean performCheck() {
		try {
			File dbFile = new File(System.getProperty( "user.home" ) + "\\local.db");
	    	if(!dbFile.exists()){
	    		dbFile.createNewFile();
	    		config.setDdlRun(true);
	    		config.setDdlGenerate(true);
	    	    server = EbeanServerFactory.create(config);
	    	}
	    	
	    	try {
	    	    server = EbeanServerFactory.create(config);
	    	} catch(Exception e) {
	    		config.setDdlRun(true);
	    		config.setDdlGenerate(true);
	    	    server = EbeanServerFactory.create(config);
	    	}
			return true;
		} catch (Exception e) {
			exp = e;
			UltiShot.logger.error("", e);
			return false;
		}
	}

	@Override
	public String getMessage() {
		return "Connect to database Local!";
	}

	@Override
	public String getErrorMessage() {
		return "Can't connect to database Local!";
	}

	public static EbeanServer getServer() {
		return server;
	}

	public Exception getExp() {
		return exp;
	}
}
