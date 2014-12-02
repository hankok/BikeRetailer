package edu.cmu.bikeretailer.warehouse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class WarehouseMain {
    
    public static Properties readConfig(String propFile) throws IOException {
    	Properties p = new Properties();
    	InputStream inputStream = new FileInputStream(propFile);
    	p.load(inputStream);
    	return p;
    }

    public static void main(String[] args) throws Exception {
    	if (args.length != 1) {
    		System.out.println("Invalid arguments. Usage: java WarehouseMain <config file>");
    		return;
    	}
    	Properties prop = null;
    	try {
    		prop = readConfig(args[0]);
    		DBHelper help = new DBHelper(prop);
    		OrderQueueManager manager = new OrderQueueManager(help);
    	} catch(IOException ioe) {
    		System.out.println("Exception occured when reading properties file: " + ioe.getMessage());
    		throw ioe;
    	}
    }

}
