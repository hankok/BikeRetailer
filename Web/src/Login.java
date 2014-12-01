import java.util.Properties;
import java.util.Timer;  
import java.util.TimerTask;  
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Entity.Order;
import Entity.User;
import Operation.FileUtils;


@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static String[] loadUSer() throws IOException {
    	String order = FileUtils.readFile("data/User.txt");
    	return order.split("\n");
	}
	
	public boolean getUser(String username, String password, HttpServletRequest request) throws IOException {
		String orders[] = loadUSer();
		boolean ret = false;

		if(orders.length == 1 && orders[0].trim().equals(""))
		{
			return ret;
		}
		
		for(int i=0; i<orders.length; i++)
		{	
			// username + "; " + password + "; " + customerName + "; " + address + '\n'
			int pos = 0;
			int pos0 = orders[i].indexOf(";");
			int pos1 = orders[i].indexOf(";", pos0 + 1);
			int pos2 = orders[i].indexOf(";", pos1 + 1);
			int pos3 = orders[i].length();

			if(username.equals(orders[i].substring(0, pos0).trim()) && password.equals(orders[i].substring(pos0+1, pos1).trim()))
			{
				String customerName = orders[i].substring(pos1 + 1, pos2).trim();
				String address = orders[i].substring(pos2+1, pos3).trim();  
				HttpSession session = request.getSession(false);
				session.setAttribute("username", username);
				session.setAttribute("customerName", customerName);
				session.setAttribute("address", address);
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
		
		if(password.equals("a1b2c3"))
		{// set the order Customer name's complete
			FileUtils.writeFile(username + "'s order is completed.", "data/GotOrderMessage.txt", true);
			String urlStr = "http://localhost:8080/myapp/gotmessage.jsp";  
			java.awt.Desktop.getDesktop().browse(java.net.URI.create(urlStr));
/*			
			final String username1 = "chenhan2014@gmail.com";
			final String password1 = "123456han";
	 
			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "587");
	 
			Session session = Session.getInstance(props,
			  new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username1, password1);
				}
			  });
	 
			try {
	 
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress("chenhan2014@gmail.com"));
				message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse("chenhan2014@gmail.com"));
				message.setSubject("Order Complete for "+ username);
				message.setText("Dear Customer,"
					+ "\n\n Your order is completed!");
	 
				Transport.send(message);
	 
				System.out.println("Done");
	 
			} catch (MessagingException e) {
				throw new RuntimeException(e);
			}
			*/
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
		catch (IOException e) {
			e.printStackTrace();
		};
		
		return;
	}
}