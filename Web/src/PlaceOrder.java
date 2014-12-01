

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

import Entity.Order;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.Date;

import edu.cmu.bikeretailer.warehouse.DBHelper;
import edu.cmu.bikeretailer.api.WarehouseInterface;

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
	
	public boolean insertOrder(String values) {
		
		
		return true;
	}

	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setCharacterEncoding("text/html;chaset=gbk");
		String title = "Place Order";
		
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
			//boolean isConnLost = !connectRMI();

			String itemNo = "";
			int quantity = 0;
			
			int nTypes = itemNos.length;
			// TODO - when name added
			//String bikeNames[] = new String[nTypes];
			String values = "";
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
				values += "(" + sQuantity + "," + itemNo + "),";				
			}
			values = values.substring(0, values.length()-1); // trim the last comma
			
			/* TODO - order table
			try {
				DBHelper dbhelper =  (DBHelper) getServletContext().getAttribute("dbHelper");
				bikes = dbhelper.insertOrder(values);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RemoteException("Exception occured when accessing the database", e);
			}
			*/
		}
		
		return;
		
	}
}