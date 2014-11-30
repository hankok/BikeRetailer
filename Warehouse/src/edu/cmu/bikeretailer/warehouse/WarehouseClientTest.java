package edu.cmu.bikeretailer.warehouse;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Date;

import edu.cmu.bikeretailer.api.WarehouseInterface;

public class WarehouseClientTest {

	/**
	 * @param args
	 * @throws RemoteException 
	 * @throws NotBoundException 
	 */
	public static void main(String[] args) throws RemoteException, NotBoundException {
		// TODO Auto-generated method stub
		Registry registry = LocateRegistry.getRegistry("ec2-54-69-71-197.us-west-2.compute.amazonaws.com", 1099);
		WarehouseInterface warehouseApi = (WarehouseInterface) registry.lookup(WarehouseInterface.class.getSimpleName());
		long t1 = new Date().getTime();
		warehouseApi.getBikeInventory();
		long t2 = new Date().getTime();
		System.out.println(t2-t1);
		//System.out.println(warehouseApi.getBikeInventory());
		//System.out.println(warehouseApi.updateInventory("1", 2));
		//System.out.println(warehouseApi.getBikeInventory());
		//System.out.println(warehouseApi.updateInventory("1", 3));
	}

}
