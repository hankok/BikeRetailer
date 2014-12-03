
import java.util.Vector;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		response.setCharacterEncoding("text/html;chaset=gbk");		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		

		if(password.equals("a1b2c3"))
		{// set the order Customer name's complete
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