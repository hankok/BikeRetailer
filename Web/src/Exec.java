import java.io.*;

import Entity.Order;
import Operation.Supplies;
import Operation.FileUtils;

public class Exec {
	
	public static void placeOrder(String itemNo, int quantity, String customerName, String addr) throws IOException {
		int index1 = Inventory.findBike(Supplies.lst1, itemNo);
		int index2 = Inventory.findBike(Supplies.lst2, itemNo);
		
		if(index1<0 && index2<0)
		{
			System.out.println("ItemNo not found, order not placed.");
			return;
		}
		
		if(index1>=0)
		{// item found from supplier1
			int inventory = Supplies.lst1.get(index1).inventory;

			if(inventory < quantity)
			{
				System.out.println("Not Enough Invertory to purchase.");
			}
			else
			{
				Supplies.lst1.get(index1).setInventory(inventory-quantity);	

				String content = "";
				for(int i = 0; i < Supplies.lst1.size(); i++)
				{
					content += 
							"item number="+Supplies.lst1.get(i).itemNo + '\n' +  
							"category="+Supplies.lst1.get(i).category + '\n' + 
							"inventory="+Integer.toString(Supplies.lst1.get(i).inventory) + '\n' + 
							"bike price="+Float.toString(Supplies.lst1.get(i).price) + '\n' + 
							"bike name="+Supplies.lst1.get(i).name + '\n' + 
							"bike description=\n"+Supplies.lst1.get(i).descr +'\n'+'\n';
							
				}

				FileUtils.writeFile(content, "data/Supplier1.txt", false);
				Order order = new Order(customerName, addr, itemNo+", "+Supplies.lst1.get(index1).name, quantity, "In process");
				FileUtils.writeFile(order.toString(), "data/Order.txt", true);
			}
		}  
		
		if(index2>=0)
		{// item found from supplier2
			int inventory = Supplies.lst2.get(index2).inventory;
			if(inventory < quantity)
			{
				System.out.println("Not Enough Invertory to purchase.");
			}
			else
			{
				Supplies.lst2.get(index2).setInventory(inventory-quantity);				
				String content = "";
				for(int i = 0; i < Supplies.lst2.size(); i++)
				{
					content += 
							Supplies.lst2.get(i).itemNo + '\n' + 
							Supplies.lst2.get(i).category + '\n' +
							"inventory="+Integer.toString(Supplies.lst2.get(i).inventory) + '\n' +
							Float.toString(Supplies.lst2.get(i).price) + '\n' +
							Supplies.lst2.get(i).name + '\n' +
							Supplies.lst2.get(i).descr+ '\n'+'\n';
							
				}

				FileUtils.writeFile(content, "data/Supplier2.txt", false);	
				
				Order order = new Order(customerName, addr, itemNo+", "+Supplies.lst2.get(index2).name, quantity, "In process");
				FileUtils.writeFile(order.toString(), "data/Order.txt", true);
			}
		}
		
	}
	
	public static String[] loadOrder() throws IOException {
    	String order = FileUtils.readFile("data/Order.txt");
    	return order.split("\n");
	}
	
	public static void setOrder(int index, String status) throws IOException {
		String orders[] = loadOrder();
		if(index < orders.length)
		{
			int i = orders[index].indexOf(";");
			orders[index] = status + orders[index].substring(i,orders[index].length());
		}
		else
		{
			System.out.println("Order index out of range.");
			return;
		}
		
		String order = "";
		for(int i=0; i<orders.length; i++)
		{
			order += orders[i]+'\n';
		}
		FileUtils.writeFile(order, "data/Order.txt", false);
	}
	
	public static void getOrders(String customerName) throws IOException {
		String orders[] = loadOrder();
		
		if(orders.length == 1 && orders[0].trim().equals(""))
		{
			return;
		}

		for(int i=0; i<orders.length; i++)
		{
			int pos0 = orders[i].indexOf(";");
			int pos1 = orders[i].indexOf(";", pos0 + 1);

			if(customerName.equals(orders[i].substring(pos0 + 1, pos1).trim()))
			{
				System.out.println(orders[i]);
			}
		}
	}

	public static boolean isNumeric(String str) {
		if(str.isEmpty())
			return false;
		for (int i = str.length(); --i >= 0;) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		// Load all Inventory from 2 suppliers
		Inventory.getAllInventory();

		String input="";
		InputStreamReader stdin = new InputStreamReader(System.in);
		BufferedReader bufin = new BufferedReader(stdin);
		try {
			while(!input.equals("A") && !input.equals("B"))
			{
				System.out.print("-------------------------------------------------------------------------------\n");
				System.out.print("Enter A to browse inventory, B to place order, C to search orders by Customer name, D to set order status, E to Exit:");
				input = bufin.readLine();
				
				if(input.equals("A"))
				{
					Inventory.sortByPrice();
				}
				else if(input.equals("B"))
				{
					System.out.print("Enter ItemNo to purchase:");
					String itemNo = bufin.readLine();
					
					System.out.print("Enter Quantity you want:");
					String sQuantity = bufin.readLine();
					int quantity = 1;
					if(!isNumeric(sQuantity))
					{
						System.out.print("Quantity is not a number, will purchase 1 for you.\n");
					}
					else
					{
						quantity = Integer.parseInt(sQuantity);
						if(quantity<1)
						{
							System.out.print("Quantity is less than 1, will purchase 1 for you.\n");
							quantity = 1;
						}
					}
					
					System.out.print("Enter Your Name - Fisrt/Last:");
					String customerName = bufin.readLine();
					
					System.out.print("Enter Shipping address - Address:");
					String Address = bufin.readLine();

					System.out.print("Enter Shipping address - City:");
					String City = bufin.readLine();
					System.out.print("Enter Shipping address - State:");
					String State = bufin.readLine();
					System.out.print("Enter Shipping address - Zipcode:");
					String Zipcode = bufin.readLine();
					String addr = Address + ". " + City + ". " + State + ". " + Zipcode;
					placeOrder(itemNo, quantity, customerName, addr);
				}
				else if(input.equals("C"))
				{
					System.out.print("Enter Customer Name - Fisrt/Last:");
					String customerName = bufin.readLine();
					getOrders(customerName);
				}
				else if(input.equals("D"))
				{
					String orders[] = loadOrder();
					if(orders.length == 1 && orders[0].trim().equals(""))
					{
						continue;
					}
					for(int i=0; i<orders.length; i++)
					{
						System.out.println("(" + Integer.toString(i) + ")" + orders[i]);
					}
					
					System.out.print("Select which one to set - index number in the parentheses:");
					String sindex = bufin.readLine();
					if(!isNumeric(sindex)||sindex.isEmpty())
					{
						System.out.print("Index enterned is not a number, will set the 0th order.\n");
						sindex = "0";
					}
					
					System.out.print("Enter a new status:");
					String status = bufin.readLine();
					if(!status.equals("In process") && !status.equals("Complete"))
					{
						System.out.print("Status enterned is invalid, will set it as 'Complete'.\n");
						status = "Complete";
					}
					setOrder(Integer.parseInt(sindex), status);
				}
				else if(input.equals("E"))
				{
					System.out.println("Quit..");
					System.exit(0);
				}
				input = "";
				continue;
				
			}

		} catch (IOException e) {
			System.out.println("Exception in Exec:" + e.getCause() + ": " + e.getMessage());		
		}
	}

}
