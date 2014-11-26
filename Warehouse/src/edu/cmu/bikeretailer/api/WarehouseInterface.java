package edu.cmu.bikeretailer.api;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

/**
 * 
 */

/**
 * @author Satish
 *
 */
public interface WarehouseInterface extends Remote {
	
	public List<Bike> getBikes() throws RemoteException;
	
	public Map<String, Integer> getBikeInventory() throws RemoteException;
	
	public boolean updateInventory(String model, int quantity) throws RemoteException;

}
