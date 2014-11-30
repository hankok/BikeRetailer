import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import Entity.Bike;

public interface SupplyServer extends Remote {
    public int AddNumbers(int firstnumber,int secondnumber) throws RemoteException;
    public ArrayList<Bike> getInventorySup() throws RemoteException;
	public boolean placeOrder(String itemNo, int quantity, String customerName) throws RemoteException, IOException;
//	public String getOrders(String customerName) throws RemoteException, IOException;
//	public String getOrders() throws RemoteException, IOException;
//	public String setOrder(int index, String status) throws RemoteException, IOException;
}