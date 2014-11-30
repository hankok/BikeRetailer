
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Entity.Bike;

@WebServlet("/ViewInventory")
public class ViewInventory extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static ArrayList<Bike> allList = new ArrayList<Bike>();
	static ArrayList<Bike> allList1 = new ArrayList<Bike>();
	static ArrayList<Bike> allList2 = new ArrayList<Bike>();
	public static class ComparatorPrice implements Comparator<Object> {
		public int compare(Object arg0, Object arg1) {
			Bike bike0 = (Bike) arg0;
			Bike bike1 = (Bike) arg1;

			if (bike0.price == bike1.price) {
				return 0;
			} else if (bike0.price > bike1.price) {
				return 1;
			} else {
				return -1;
			}
		}
	}

	public static class ComparatorItemNo implements Comparator<Object> {
		public int compare(Object arg0, Object arg1) {
			Bike bike0 = (Bike) arg0;
			Bike bike1 = (Bike) arg1;
			return bike0.itemNo.compareTo(bike1.itemNo);
		}
	}
	
	public static class ComparatorBikeName implements Comparator<Object> {
		public int compare(Object arg0, Object arg1) {
			Bike bike0 = (Bike) arg0;
			Bike bike1 = (Bike) arg1;
			return bike0.name.compareTo(bike1.name);
		}
	}
	
	public static String sortByPrice(ArrayList<Bike> bikelist) {
		ComparatorPrice comparator = new ComparatorPrice();
		Collections.sort(bikelist, comparator);

		String ret = "";
		for (int i = 0; i < bikelist.size(); i++) {
			Bike bike = (Bike) bikelist.get(i);
			ret += bike.toString() + "<br><br>";
			System.out.println(bike.toString()+'\n');
		}
		return ret;
	}
	
	public static String sortByName(ArrayList<Bike> bikelist) {
		ComparatorBikeName comparator = new ComparatorBikeName();
		Collections.sort(bikelist, comparator);

		String ret = "";
		for (int i = 0; i < bikelist.size(); i++) {
			Bike bike = (Bike) bikelist.get(i);
			ret += bike.toString() + "<br><br>";
			System.out.println(bike.toString()+'\n');
		}
		return ret;
	}

	void showInventory(String orderby, ArrayList<Bike> allList, PrintWriter out)
	{
		String allbike = "";
		
		if(orderby.equals("2") )
		{
			allbike = sortByName(allList);
		}
		else
		{
			allbike = sortByPrice(allList);
		}
		
		String title = "View Inventory";
		out.print("<html><body><h1>" + title 
				+ "</h1><p>"
				+ allbike + "</p>");
		out.print("</body></html>");
	}
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setCharacterEncoding("text/html;chaset=gbk");
		String orderby = request.getParameter("orderby");

		String url="rmi://127.0.0.1/Hello1"; 
		SupplyServer ss = null;
		try {
			ss = (SupplyServer)Naming.lookup(url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			if(!allList.isEmpty())
			{
				showInventory(orderby, allList, out);
			}
			else
			{
				out.print("<html><body><h1>" + "ERROR" 
						+ "</h1><li>"
						+ "Data is not cached, need to view Inventory success at least once.sup1" + "</li>");
				out.print("</body></html>");			
			}
			return;
		}
		if(ss == null)
		{
			if(!allList.isEmpty())
			{
				showInventory(orderby, allList, out);
			}
			else
			{
				out.print("<html><body><h1>" + "ERROR" 
						+ "</h1><li>"
						+ "Data is not cached, need to view Inventory success at least once.sup1" + "</li>");
				out.print("</body></html>");
			}
			return;
		}
		allList1 = ss.getInventorySup();
		
		// should fetch from another rmi server 
		url="rmi://127.0.0.1/Hello2";
		ss = null;
		try {
			ss = (SupplyServer)Naming.lookup(url);
		} catch (Exception e) {
			//e.printStackTrace();

			if(!allList.isEmpty())
			{
				showInventory(orderby, allList, out);
			}
			else
			{
				out.print("<html><body><h1>" + "ERROR" 
						+ "</h1><li>"
						+ "Data is not cached, need to view Inventory success at least once.sup2" + "</li>");
				out.print("</body></html>");
			}
			return;
		}
		if(ss == null)
		{
			if(!allList.isEmpty())
			{
				showInventory(orderby, allList, out);
			}
			else
			{
				out.print("<html><body><h1>" + "ERROR" 
						+ "</h1><li>"
						+ "Data is not cached, need to view Inventory success at least once.sup2" + "</li>");
				out.print("</body></html>");
			}
			return;
		}		
		allList2 = ss.getInventorySup();
		allList.clear();
		allList.addAll(allList1);
		allList.addAll(allList2);
		showInventory(orderby, allList, out);
	}
}