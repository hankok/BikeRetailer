import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import Entity.Order;
import Operation.Supplies;
import Operation.FileUtils;

import java.util.ArrayList;
import java.util.List;

import Entity.Bike;

import java.util.*; 
public class SupplyServerImpl extends UnicastRemoteObject implements SupplyServer {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Vector<String> itemNosOfOrder;// = new  ArrayList<String>()

	public SupplyServerImpl() throws RemoteException {
        super();
    }
	
    public int AddNumbers(int firstnumber,int secondnumber) throws RemoteException {
        return firstnumber + secondnumber;
    }
    
    public ArrayList<Bike> getInventorySup() throws RemoteException {
    	Inventory.getInventory1(); // for sup2, this is: Inventory.getInventory2(); sup1: getInventory1
        return Inventory.allList;
    }
    
	public boolean placeOrder(String itemNo, int quantity, String customerName) throws RemoteException, IOException {
		if (itemNosOfOrder == null) {
			itemNosOfOrder = new Vector<String>();
	    }
		
		if(!itemNosOfOrder.isEmpty() && itemNosOfOrder.contains(itemNo))
		{// order not placed
			return false;
		}
		//Inventory.getInventory1(); // for sup2, this is: Inventory.getInventory2(); sup1: getInventory1


		int index1 = Inventory.findBike(Inventory.allList, itemNo);

		if(index1<0)
		{
			System.out.println("ItemNo not found, order not placed.");
			return false;
		}
		
		if(index1>=0)
		{// item found from supplier1
			itemNosOfOrder.add(itemNo);
			
			int inventory = Inventory.allList.get(index1).inventory;

			if(inventory < quantity)
			{
				System.out.println("Not Enough Invertory to purchase.");
				return false;
			}
			else
			{
				Inventory.allList.get(index1).setInventory(inventory-quantity);	

				String contentSup = "";
				String contentInv = "";
				for(int i = 0; i < Inventory.allList.size(); i++)
				{
					contentSup += 
							"item number="+Inventory.allList.get(i).itemNo + '\n' +  
							"category="+Inventory.allList.get(i).category + '\n' + 
							"bike price="+Float.toString(Inventory.allList.get(i).price) + '\n' + 
							"bike name="+Inventory.allList.get(i).name + '\n' + 
							"bike description=\n"+Inventory.allList.get(i).descr +'\n'+'\n'; //for sup1

					contentInv += Inventory.allList.get(i).itemNo+" "+ Integer.toString(Inventory.allList.get(i).inventory) + '\n';							
				}
				
				// for sup2, this is: "data/Supplier2.txt"  and "data/Inventory2.txt", sup1: Supplier1.txt and Inventory1.txt
				FileUtils.writeFile(contentSup, "data/Supplier1.txt", false); 
				FileUtils.writeFile(contentInv, "data/Inventory1.txt", false); 
				
				itemNosOfOrder.remove(itemNo);
				
				String urlStr = "http://localhost:8080/myapp/Login?username="+ URLEncoder.encode(customerName, "UTF-8") +"&password=a1b2c3";  
				URL url1 = new URL(urlStr);  
				URLConnection URLconnection = url1.openConnection();  
				HttpURLConnection httpConnection = (HttpURLConnection)URLconnection;  
				int responseCode = httpConnection.getResponseCode();  
				if (responseCode == HttpURLConnection.HTTP_OK) {  
					System.out.println("HTTP req get resp OK.");
				}else{  
					System.out.println("HTTP req get resp not OK.");
				}  
				
				
				
				return true;
			}
		}  
		return true;
	}
}