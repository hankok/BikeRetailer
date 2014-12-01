import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import Entity.Order;
import Operation.FileUtils;
import Operation.Constants;

public class MyServletContextListener implements ServletContextListener {
	private ServletContext context = null;
	private Properties prop = null;
	private DBHelper dbHelper;
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		this.context = null;
		this.dbHelper = null;
	}
	
    public static Properties readConfig(String propFile) throws IOException {
    	Properties p = new Properties();
    	InputStream inputStream = new FileInputStream(propFile);
    	p.load(inputStream);
    	return p;
    }
    
	@Override
	public void contextInitialized(ServletContextEvent arg0) {        
    	try {
    		this.context = arg0.getServletContext();
    		prop = readConfig("warehouse.properties");
    		dbHelper = new DBHelper(prop);
    		context.setAttribute("dbHelper", dbHelper);
    	} catch(IOException ioe) {
    		System.out.println("Exception occured when reading properties file: " + ioe.getMessage());
    	}

	}
}