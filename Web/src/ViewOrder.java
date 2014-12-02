

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Vector;

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

	
	public String getOrders(String username) throws IOException, SQLException {
		DBHelper dbhelper =  (DBHelper) getServletContext().getAttribute("dbHelper");
		Vector<String> vOrder = dbhelper.getOrders(username);
		String orders = "";
		for(int i=0; i< vOrder.size(); i++)
		{
			orders += vOrder.get(i) + "<br>";
		}
		return orders;
	}
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setCharacterEncoding("text/html;chaset=gbk");

		String title = "View Order";
		//String customerName = request.getParameter("customerName");
		
		String username = "";
		try{
			HttpSession session = request.getSession(false);
			username = session.getAttribute("username").toString();
		}
		catch(Exception e)
		{
			out.print("<html><body><h1>" + "INFO" 
					+ "</h1><li>"
					+ "To view order, You MUST login." + "</li>");
			out.print("</body></html>");
		}
		
		if(!username.isEmpty())
		{
			// show the order with input name
			try {
				out.print("<html><body><h1>" + title
						+ "</h1><p>"
						+ getOrders(username) + "</p>");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			out.print("</body></html>");
		}

	}
}