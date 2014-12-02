import java.util.List;
import java.util.Properties;
import java.util.Timer;  
import java.util.TimerTask;  
import java.util.Vector;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.javafx.collections.MappingChange.Map;

import Entity.Order;
import Entity.User;
import Operation.FileUtils;


@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public boolean getUser(String username, String password, HttpServletRequest request) throws IOException, SQLException {
		DBHelper dbhelper =  (DBHelper) getServletContext().getAttribute("dbHelper");
		Vector<String> nameAddr = dbhelper.getNameAddr(username, password);
		if(nameAddr.size() == 2)
		{
			HttpSession session = request.getSession(false);
			String customerName = nameAddr.get(0).trim();
			String address = nameAddr.get(1).trim();
			session.setAttribute("username", username);
			session.setAttribute("customerName", customerName);
			session.setAttribute("address", address);
			return true;
		}				
		return false;
	}
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setCharacterEncoding("text/html;chaset=gbk");		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		if(password.equals("a1b2c3"))
		{// set the order Customer name's complete
			FileUtils.writeFile(username + "'s order is completed.", "data/GotOrderMessage.txt", true);
			String urlStr = "http://localhost:8080/myapp/gotmessage.jsp";  
			java.awt.Desktop.getDesktop().browse(java.net.URI.create(urlStr));

		}

    	response.setContentType("text/html; charset=gbk");
		try {
				if(getUser(username, password, request)){
					response.sendRedirect("/myapp/ViewInventory.jsp");
				}
				else{// login failed
					response.sendRedirect("/myapp/Login.jsp");
				}		
		} 
		catch (IOException | SQLException e) {
			e.printStackTrace();
		};
		
		return;
	}
}