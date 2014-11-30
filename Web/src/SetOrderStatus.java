

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

import Operation.FileUtils;

@WebServlet("/SetOrderStatus")
public class SetOrderStatus extends HttpServlet {
	private static final long serialVersionUID = 1L;

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
	
	public static String[] loadOrder() throws IOException {
    	String order = FileUtils.readFile("data/Order.txt");
    	return order.split("\n");
	}
    
	public String setOrder(int index, String status) throws IOException {
		String orders[] = loadOrder();
		String msg = "Order Status successfully set.";
		
		if(index < orders.length)
		{
			int i = orders[index].indexOf(";");
			orders[index] = status + orders[index].substring(i,orders[index].length());
		}
		else
		{
			msg = "Order index out of range.";
			return msg;
		}
		
		String order = "";
		for(int i=0; i<orders.length; i++)
		{
			order += orders[i]+'\n';
		}
		FileUtils.writeFile(order, "data/Order.txt", false);
		return msg;
	}
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setCharacterEncoding("text/html;chaset=gbk");
		String title = "Set Order Status";
		
		String sindex = request.getParameter("sindex");
		if(!isNumeric(sindex)||sindex.isEmpty())
		{			
			out.print("<html><body><h1>" + "ERROR" 
					+ "</h1><li>"
					+ "Index enterned is not a number." + "</li>");
			out.print("</body></html>");
			return;
		}
		
		String status = request.getParameter("status");
		if(!status.equals("In process") && !status.equals("Complete"))
		{
			out.print("<html><body><h1>" + "ERROR" 
					+ "</h1><li>"
					+ "Status enterned is invalid, should be 'In process' or 'Complete'." + "</li>");
			out.print("</body></html>");
			return;
		}

		String ret = setOrder(Integer.parseInt(sindex), status);
		out.print("<html><body><h1>" + title 
				+ "</h1><li>"
				+ ret + "</li>");
		out.print("</body></html>");
		return;
	}
}