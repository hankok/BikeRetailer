
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/ViewInventory")
public class ViewInventory extends HttpServlet {
	private static final long serialVersionUID = 1L;
		
	public static class ComparatorPrice implements Comparator<Object> {
		public int compare(Object arg0, Object arg1) {
			Bike bike0 = (Bike) arg0;
			Bike bike1 = (Bike) arg1;

			if (bike0.getPrice() == bike1.getPrice()) {
				return 0;
			} else if (bike0.getPrice() > bike1.getPrice()) {
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
			return bike0.getModelNumber().compareTo(bike1.getModelNumber());
		}
	}

	/* TODO - add bike name in DB
	public static class ComparatorBikeName implements Comparator<Object> {
		public int compare(Object arg0, Object arg1) {
			Bike bike0 = (Bike) arg0;
			Bike bike1 = (Bike) arg1;
			return bike0.name.compareTo(bike1.name);
		}
	}
	*/
	
	public static String sortByPrice(List<Bike> bikelist) {
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
	
	/*TODO - add bike name in DB
	public static String sortByName(List<Bike> bikelist) {
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
	*/

	void showInventory(String orderby, List<Bike> bikes, PrintWriter out)
	{
		String allbike = "";
		
		if(orderby.equals("2") )
		{
			//allbike = sortByName(allList); 
		}
		else
		{
			allbike = sortByPrice(bikes);
		}
		
		String title = "View Inventory";
		out.print("<html><body><h1>" + title 
				+ "</h1><p>"
				+ allbike + "</p>");
		out.print("</body></html>");
	}
	
	public List<Bike> getBikeInfoFromRetailDB() throws RemoteException
	{
		List<Bike> bikes;
		
		try {
			DBHelper dbHelper =  (DBHelper) getServletContext().getAttribute("dbHelper");
			bikes = dbHelper.getAllBikes();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RemoteException("Exception occured when accessing the database", e);
		}
		return bikes;
	}

	/* may be usefull for mem sort
	public static int findBike(List<Bike> lst, String itemNo) {		
		ComparatorItemNo comparator = new ComparatorItemNo();
		Collections.sort(lst, comparator);
		int index = Collections.binarySearch(lst, new Bike(itemNo, "",0,(float)0,"",""), comparator);
		return index;
	}
	*/
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setCharacterEncoding("text/html;chaset=gbk");
		String orderby = request.getParameter("orderby");

		List<Bike> bikes = getBikeInfoFromRetailDB();
		showInventory(orderby, bikes, out);
	}
}