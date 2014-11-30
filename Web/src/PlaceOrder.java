

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Entity.Bike;
import Entity.Order;
import Operation.FileUtils;
import Operation.Supplies;

@WebServlet("/PlaceOrder")
public class PlaceOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
	SupplyServer ss1 = null;
	SupplyServer ss2 = null;
	Timer timer;
	
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
	
	boolean connectRMI() throws RemoteException
	{
		Boolean isConnLost = true;
		String url="rmi://127.0.0.1/Hello1"; // find from supplier1

		try {
			ss1 = null;
			ss1 = (SupplyServer)Naming.lookup(url);
		} catch (Exception e) {
			e.printStackTrace();
			isConnLost = false;
		}
		if(ss1 == null)
		{

			isConnLost = false;
		}
	
		url="rmi://127.0.0.1/Hello2"; // find from supplier2
		try {
			ss2 = null;
			ss2 = (SupplyServer)Naming.lookup(url);
		} catch (Exception e) {
			e.printStackTrace();
			isConnLost = false;
		}
		if(ss2 == null)
		{

			isConnLost = false;
		}
		return isConnLost;
	}
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setCharacterEncoding("text/html;chaset=gbk");
		String title = "Place Order";
		
		String itemNosByComma = request.getParameter("itemNo");
		String sQuantitysByComma = request.getParameter("sQuantity");
		

		/*
		String customerName = request.getParameter("customerName");
		String Address = request.getParameter("Address");
		String City = request.getParameter("City");
		String State = request.getParameter("State");
		String Zipcode = request.getParameter("Zipcode");
		
		String addr = Address + ". " + City + ". " + State + ". " + Zipcode;
		*/
		String customerName = "";
		String addr = "";
		String username = "";
		
		try{
			HttpSession session = request.getSession(false);
		    customerName = session.getAttribute("customerName").toString();
		    addr = session.getAttribute("address").toString();
		    username = session.getAttribute("username").toString();
		}
		catch(Exception e)
		{
			out.print("<html><body><h1>" + "INFO" 
					+ "</h1><li>"
					+ "To place order, You MUST login." + "</li>");
			out.print("</body></html>");
			return;
		}
		
		String[] itemNos = itemNosByComma.split(",");
		String[] sQuantitys = sQuantitysByComma.split(",");

		if(itemNos.length == sQuantitys.length)
		{
			boolean isConnLost = !connectRMI();

			ArrayList<Bike> allList1 = new ArrayList<Bike>();
			
			ArrayList<Bike> allList2 = new ArrayList<Bike>();
			
			if(isConnLost)
			{
				allList1 = ViewInventory.allList1;
				allList2 = ViewInventory.allList2;
				if(allList1.isEmpty() || allList2.isEmpty())
				{
					out.print("<html><body><h1>" + "INFO - Remote Connection lost and No Local Cache" 
							+ "</h1><li>"
							+ "You must at least successfully viewing Inventory once to have them cached." + "</li>");
					out.print("</body></html>");
					return;
				}
			}
			else
			{
				allList1 = ss1.getInventorySup();
				allList2 = ss2.getInventorySup();
			}

			
			String sQuantity = "";
			String itemNo = "";
			int quantity = 1;
			int nTypes = itemNos.length;
			String bikeNames[] = new String[nTypes];
			int quantitys[] = new int[nTypes];
			int indexs1[] = new int[nTypes];
			int indexs2[] = new int[nTypes];
			for(int i = 0; i<nTypes; i++)
			{
			    sQuantity = sQuantitys[i];
				itemNo = itemNos[i];

				
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
				quantitys[i] = quantity;

				indexs1[i] = Inventory.findBike(allList1, itemNo);
				
				if(indexs1[i]<0)
				{
					indexs2[i] = Inventory.findBike(allList2, itemNo);
					
					if(indexs2[i]<0)
					{
						out.print("<html><body><h1>" + "INFO" 
								+ "</h1><li>"
								+ "No such bicycle available." + "</li>");
						out.print("</body></html>");
						return;
					}
					bikeNames[i] = allList2.get(indexs2[i]).name;
					if(quantity>allList2.get(indexs2[i]).inventory)
					{
						out.print("<html><body><h1>" + "INFO" 
								+ "</h1><li>"
								+ "Out of Stock." + "</li>");
						out.print("</body></html>");
						return;
					}
				}
				else
				{
					bikeNames[i] = allList1.get(indexs1[i]).name;
					if(quantity>allList1.get(indexs1[i]).inventory)
					{
						out.print("<html><body><h1>" + "INFO" 
								+ "</h1><li>"
								+ "Out of Stock." + "</li>");
						out.print("</body></html>");
						return;
					}
				}
			}
			 
			if(!isConnLost)
			{
				for(int i = 0; i<nTypes; i++)
				{
					if(indexs1[i]>=0)
					{
						if(!ss1.placeOrder(itemNos[i], quantitys[i], customerName))
						{
							out.print("<html><body><h1>" + "INFO" 
									+ "</h1><li>"
									+ "Out of stock-sup1-ERROR-." +itemNos[i] + "-"+ quantitys[i] +"-"+customerName+ "</li>");
							out.print("</body></html>");
							return;
						}
					}
					else
					{
						if(!ss2.placeOrder(itemNos[i], quantitys[i], customerName))
						{
							out.print("<html><body><h1>" + "INFO" 
									+ "</h1><li>"
									+ "Out of stock-sup2-ERROR-." +itemNos[i] + "-"+ quantitys[i] +"-"+customerName+ "</li>");
							out.print("</body></html>");
							return;
						}
					}
				}
			}
			
			String sbikeNames = "";
			for(int i=0; i<nTypes; i++)
			{
				sbikeNames += bikeNames[i];
			}
				
			if(isConnLost)
			{
				Order order = new Order(customerName, addr, itemNosByComma+"%"+ sQuantitysByComma +"%"+sbikeNames, quantity, "Pending"); 
				FileUtils.writeFile(order.toString(), "data/OrderPending.txt", true);			
			}
			else
			{
				Order order = new Order(customerName, addr, itemNosByComma+"% "+ sQuantitysByComma +"% "+sbikeNames, quantity, "In process"); 
				FileUtils.writeFile(order.toString(), "data/Order.txt", true);
			}
			
			out.print("<html><body><h1>" + "INFO" 
					+ "</h1><li>"
					+ "Order sent to Supplier. " + customerName + "  "+ addr +"</li>");
			out.print("</body></html>");

		}
		
		return;
		
	}
}