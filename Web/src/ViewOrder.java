

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Operation.FileUtils;

@WebServlet("/ViewOrder")
public class ViewOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public static String[] loadOrder() throws IOException {
    	String order = FileUtils.readFile("data/Order.txt");
    	return order.split("\n");
	}
	
	public String getOrders(String customerName) throws IOException {
		String orders[] = loadOrder();
		String ret = "";

		if(orders.length == 1 && orders[0].trim().equals(""))
		{
			return ret;
		}
		
		for(int i=0; i<orders.length; i++)
		{
			int pos0 = orders[i].indexOf(";");
			int pos1 = orders[i].indexOf(";", pos0 + 1);

			if(customerName.equals(orders[i].substring(pos0 + 1, pos1).trim()))
			{
				ret += orders[i] + "<br><br>";
				System.out.println(orders[i]);
			}
		}
		return ret;
	}
	
	public String getOrders() throws IOException {
		String orders[] = loadOrder();
		String ret = "";

		if(orders.length == 1 && orders[0].trim().equals(""))
		{
			return ret;
		}
		
		for(int i=0; i<orders.length; i++)
		{
			ret += "(" + Integer.toString(i) + ")" + orders[i] + "<br><br>";
		}
		return ret;
	}
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setCharacterEncoding("text/html;chaset=gbk");

		String title = "View Order";
		//String customerName = request.getParameter("customerName");
		
		String customerName = "";
		try{
			HttpSession session = request.getSession(false);
			customerName = session.getAttribute("customerName").toString();
		}
		catch(Exception e)
		{
			out.print("<html><body><h1>" + "INFO" 
					+ "</h1><li>"
					+ "To view order, You MUST login." + "</li>");
			out.print("</body></html>");
		}
		
/*		if(customerName.isEmpty())
		{// user didn't input, show all orders
			out.print("<html><body><h1>" + title
					+ "</h1><p>"
					+ getOrders() + "</p>");
			out.print("</body></html>");
			
		}*/
		
		if(!customerName.isEmpty())
		{
			// show the order with input name
			out.print("<html><body><h1>" + title
					+ "</h1><p>"
					+ getOrders(customerName) + "</p>");
			out.print("</body></html>");
		}

	}
}