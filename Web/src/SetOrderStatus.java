import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    
	public boolean setOrder(int index, String status) throws IOException {

		return true;
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
		
		boolean ret = setOrder(Integer.parseInt(sindex), status);
		out.print("<html><body><h1>" + title 
				+ "</h1><li>"
				+ ret + "</li>");
		out.print("</body></html>");
		return;
	}
}