

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.Timer;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;


@WebServlet("/PlaceOrder")
public class PlaceOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
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
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setCharacterEncoding("text/html;chaset=gbk");
		
		String itemNosByComma = request.getParameter("itemNo");
		String sQuantitysByComma = request.getParameter("sQuantity");
		
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

			String itemNo = "";
			int quantity = 0;
			
			int nTypes = itemNos.length;
			String values = "";
			
			try {
				DBHelper dbhelper =  (DBHelper) getServletContext().getAttribute("dbHelper");
				for(int i = 0; i<nTypes; i++)
				{
					String sQuantity = sQuantitys[i];
					itemNo = itemNos[i];
	
					if(!isNumeric(sQuantity))
					{
						System.out.print("Quantity is not a number, will purchase 1 for you.\n");
						sQuantity = "1";
					}
					else
					{
						quantity = Integer.parseInt(sQuantity);
						if(quantity<1)
						{
							System.out.print("Quantity is less than 1, will purchase 1 for you.\n");
							sQuantity = "1";
						}
					}
					
					Vector<String> namePriceQty = dbhelper.getNamePriceQty(itemNo);

					String bikeName;
					double price = 0.0;
					int currentQty = 0;
					if(namePriceQty.size() == 3)
					{
					    bikeName = (String)namePriceQty.get(0);
						price = Double.parseDouble(namePriceQty.get(1).trim());
						currentQty = Integer.parseInt(namePriceQty.get(2).trim());
						if(currentQty < Integer.parseInt(sQuantity))
						{
							out.print("<html><body><h1>" + "ERROR" 
									+ "</h1><li>"
									+ "Bike with ItemNo" + itemNo + "is not enough, quantity:" + currentQty + "</li>");
							out.print("</body></html>");
							return;
						}
					}
					else
					{
						out.print("<html><body><h1>" + "ERROR" 
								+ "</h1><li>"
								+ "You gave a wrong itemNo." + "</li>");
						out.print("</body></html>");
						return;
					}
					
					values += "(" + username + "," + itemNo + "," + sQuantity + "," + price + ",'" + bikeName + "'),";				
				}
				values = values.substring(0, values.length()-1); // trim the last comma

				for(int i = 0; i < itemNos.length; i++)
				{
					boolean ret = dbhelper.updateInventory(itemNos[i],  Integer.parseInt(sQuantitys[i])); 
					if(!ret)
					{
						out.print("<html><body><h1>" + "ERROR" 
								+ "</h1><li>"
								+ "Order Not placed, update qty error." + "</li>");
						out.print("</body></html>");
						return;
					}
				}
				boolean ret = dbhelper.insertOrder(values);
				if(ret)
				{
					// TODO - Send message to queue
					
					// In the OrderQueueManager, if queue is down, store the msgs in a vector or list
					// Initial status of an order is 0, 
					// if shipped(receive the resp msg), then change the status to 1, 
					// and send a mail to user (I used browser function to pop window in Login.java:40)
					/* This is to trigger the popup, put it where you set the order complete
					String urlStr = "http://localhost:8080/myapp/Login?username="+ URLEncoder.encode(customerName, "UTF-8") +"&password=a1b2c3";  
					URL url1 = new URL(urlStr);  
					URLConnection URLconnection = url1.openConnection();  
					HttpURLConnection httpConnection = (HttpURLConnection)URLconnection;
					*/
					
					out.print("<html><body><h1>" + "INFO" 
							+ "</h1><li>"
							+ "Order Placed and in Process." + "</li>");
					out.print("</body></html>");
				}
				else
				{
					out.print("<html><body><h1>" + "ERROR" 
							+ "</h1><li>"
							+ "Order Not placed." + "</li>");
					out.print("</body></html>");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RemoteException("Exception occured when accessing the database", e);
			}
		}
		
		return;
		
	}
}