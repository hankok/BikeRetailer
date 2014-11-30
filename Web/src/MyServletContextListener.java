import java.io.IOException;
import java.rmi.Naming;
import java.rmi.RemoteException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import Entity.Order;
import Operation.FileUtils;


public class MyServletContextListener implements ServletContextListener {
    private MyThreadClass myThread = null;
	static SupplyServer ss1 = null;
	static SupplyServer ss2 = null;
	
	public class MyThreadClass extends Thread {

	    public void run() {
	    	while(true)
	    	{
	    		try {
					checkAndPlaceOrders();
					Thread.sleep(5000);

				} catch (Exception e1) {
					// TODO Auto-generated catch block
	    			FileUtils.writeFile("Exception1..\n"+e1.getMessage(), "data/GotOrderMessage.txt", true);
					e1.printStackTrace();
				}

	    	}
	    }
	}
	
	boolean connectRMI() throws RemoteException
	{
		String url="rmi://127.0.0.1/Hello1"; // find from supplier1

		try {
				ss1 = (SupplyServer)Naming.lookup(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(ss1 == null)
		{

			return false;
		}
	
		url="rmi://127.0.0.1/Hello2"; // find from supplier2
		try {
				ss2 = (SupplyServer)Naming.lookup(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(ss2 == null)
		{

			return false;
		}
		return true;
	}

	public static String[] loadOrder() throws IOException {
    	String order = FileUtils.readFile("data/OrderPending.txt");
    	return order.split("\n");
	}
	
	public void checkAndPlaceOrders() throws IOException, InterruptedException {
		String orders[] = loadOrder();
		String sOrder = "";

		if(orders.length == 0 || (orders.length == 1 && orders[0].trim().equals("")))
		{
			FileUtils.writeFile("No order to place\n", "data/GotOrderMessage.txt", true);
			return;
		}
		FileUtils.writeFile(String.valueOf(orders.length) + " order pending", "data/GotOrderMessage.txt", true);

		
		for(int i=0; i<orders.length; i++)
		{
			int pos0 = orders[i].indexOf(";");
			int pos1 = orders[i].indexOf(";", pos0 + 1);
			int pos2 = orders[i].indexOf(";", pos1 + 1);
			int pos3 = orders[i].indexOf(";", pos2 + 1);

			//String status = orders[i].substring(0, pos0).trim();
			String customer = orders[i].substring(pos0+1, pos1).trim();
			String address = orders[i].substring(pos1+1, pos2).trim();
			String item = orders[i].substring(pos2+1, pos3).trim();
			String quantity = orders[i].substring(pos3+1, orders[i].length()).trim();
			
			String items[] = item.split("%");
			String itemNos[] = items[0].split(",");
			String sQuantitys[] = items[1].split(",");

			if(items.length != 3)
			{
				continue;
			}
			else
			{
				if(connectRMI())
				{
					boolean succ = true;
					for(int j = 0; j<itemNos.length; j++)
					{
						Thread.sleep(2000);
						FileUtils.writeFile("itemNo&Quan: " +itemNos[j].trim() + " "+ sQuantitys[j].trim() + " " + customer + "\n", "data/GotOrderMessage.txt", true);
						if(!ss1.placeOrder(itemNos[j].trim(), Integer.parseInt(sQuantitys[j].trim()), customer.trim()) && 
						   !ss2.placeOrder(itemNos[j].trim(), Integer.parseInt(sQuantitys[j].trim()), customer.trim()))
						{
							succ = false;
						}
					}
					if(succ)
					{
						Order order = new Order(customer, address, item, -1, "In process"); 
						FileUtils.writeFile(order.toString(), "data/Order.txt", true);
					}
					continue;
				}				
			}
			sOrder += orders[i]+'\n';

		}
		//FileUtils.writeFile("Unplaced orders are " + sOrder + "\n", "data/GotOrderMessage.txt", true);

		FileUtils.writeFile(sOrder, "data/OrderPending.txt", false);
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
        try {
            myThread.interrupt();
        } catch (Exception ex) {
        }
	}
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
        if ((myThread == null) || (!myThread.isAlive())) {
            myThread = new MyThreadClass();
            myThread.start();
        }
	}
}