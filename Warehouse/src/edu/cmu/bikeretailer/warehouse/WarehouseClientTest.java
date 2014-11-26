package edu.cmu.bikeretailer.warehouse;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import edu.cmu.bikeretailer.api.WarehouseInterface;

public class WarehouseClientTest {

	/**
	 * @param args
	 * @throws RemoteException 
	 * @throws NotBoundException 
	 */
	public static void main(String[] args) throws RemoteException, NotBoundException {
		// TODO Auto-generated method stub
		Registry registry = LocateRegistry.getRegistry("localhost", 1099);
		WarehouseInterface warehouseApi = (WarehouseInterface) registry.lookup(WarehouseInterface.class.getSimpleName());
		System.out.println(warehouseApi.getBikeInventory());
		System.out.println(warehouseApi.updateInventory("1", 2));
		System.out.println(warehouseApi.getBikeInventory());
		System.out.println(warehouseApi.updateInventory("1", 3));
	}

}
