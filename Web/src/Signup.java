import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/Signup")
public class Signup extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
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
		
		DBHelper dbhelper =  (DBHelper) getServletContext().getAttribute("dbHelper");
		String values = "(" + username+ ",'"+ password + "','"+ customerName + "','"+ address + "')";
		System.out.println("values:" + values);
		try {
			boolean ret = dbhelper.insertUser(values);
			if(!ret)
			{
				out.print("<html><body><h1>" + "ERROR" 
						+ "</h1><li>"
						+ "user register failed, please use a different username" + "</li>");
				out.print("</body></html>");
				return;
			}
			response.sendRedirect("/myapp/Login.jsp");
		} catch (SQLException e1) {
			out.print("<html><body><h1>" + "ERROR" 
					+ "</h1><li>"
					+ "user register failed, please use a different username" + "</li>");
			out.print("</body></html>");
			System.out.println("User registered exception on the retailer.");
			response.sendRedirect("/myapp/Signup.jsp");
		}
		return;
	}
}