package edu.cmu.bikeretailer.warehouse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.Properties;

import edu.cmu.bikeretailer.api.WarehouseInterface;

/**
 * 
 */

/**
 * @author pravi1206
 *
 */
public class WarehouseMain {

	private static Registry registry;

    public static void startRegistry(int port) throws RemoteException {
        // create in server registry
        registry = java.rmi.registry.LocateRegistry.createRegistry(port);
    }

    public static void registerObject(String name, Remote remoteObj)
        throws RemoteException, AlreadyBoundException {
        registry.bind(name, remoteObj);
    }
    
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
    	} catch(IOException ioe) {
    		System.out.println("Exception occured when reading properties file: " + ioe.getMessage());
    		throw ioe;
    	}
    	int port = Integer.parseInt(prop.getProperty(Constants.RMI_PORT_KEY));
        startRegistry(port);
        registerObject(WarehouseInterface.class.getSimpleName(), new WarehouseImpl(new DBHelper(prop)));
        System.out.println("Started warehouse..");
    }

}
