import java.util.Timer;  
import java.util.TimerTask;  
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Entity.Bike;
import Entity.Order;
import Entity.User;
import Operation.FileUtils;

@WebServlet("/Signup")
public class Signup extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static String[] loadUSer() throws IOException {
    	String order = FileUtils.readFile("data/User.txt");
    	return order.split("\n");
	}
	
	public boolean getUser(String username) throws IOException {
		String orders[] = loadUSer();

		if(orders.length == 1 && orders[0].trim().equals(""))
		{
			return false;
		}
		
		for(int i=0; i<orders.length; i++)
		{	
			// username + "; " + password + "; " + customerName + "; " + address + '\n'
			int pos = 0;
			int pos0 = orders[i].indexOf(";");

			if(username.equals(orders[i].substring(0, pos0).trim()))
			{
				return true;
			}
		}
		return false;
	}
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setCharacterEncoding("text/html;chaset=gbk");		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String customerName = request.getParameter("customerName");

		String Address = request.getParameter("Address");
		String City = request.getParameter("City");
		String State = request.getParameter("State");
		String Zipcode = request.getParameter("Zipcode");
		String address = Address + ". " + City + ". " + State + ". " + Zipcode;		
		
		try {
			if(getUser(username)){
				out.print("<html><body><h1>" + "ERROR" 
						+ "</h1><li>"
						+ "username exists!!" + "</li>");
				out.print("</body></html>");
				return;
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		};
		User user = new User(username, password,customerName,address);
		FileUtils.writeFile(user.toString(), "data/User.txt", true);
		try {
			response.sendRedirect("/myapp/Login.jsp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};  

		return;
	}
}