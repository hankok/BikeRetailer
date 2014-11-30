package Operation;

import Entity.Bike;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;    
import java.util.HashMap;

public class Supplies {
	public static List<Bike> lst1;
	public static List<Bike> lst2;

	public static void getSupplier1RecordByMark(String supplyData, String mark, String inventData,List<Bike> lst)
	{
		String invent[] = inventData.split("\n");
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		
		for(int i=0; i<invent.length; i++)
		{
			String inventItem[] = invent[i].split(" ");
            int inventory = Integer.parseInt(inventItem[1]);
            map.put(inventItem[0], inventory);  // itemNo and inventory(quantity)			
		}
		
		String items[] = supplyData.split(mark);

		for(int i=0; i<items.length; i++){
    		String item = items[i];
            item = mark + item.replaceAll("((\r\n)|\n)[\\s\t ]*(\\1)+", "$1");

            String line[] = item.split("\n");
            if(line.length<=4)
            	continue;
            
        	String itemNo = line[0].split("=")[1];
        	String category = line[1].split("=")[1].trim();
            float price = Float.parseFloat(line[2].split("=")[1].replace(",", "").replace("$", ""));
            String name = line[3].split("=")[1];
            String descr = "";
            if(line.length>=6)
            {
            	for(int j=5; j<line.length; j++)
            	{
            		descr += line[j];
            	}
            }
            
            if(map.get(itemNo) == null)
            {// error of the file
            	return;
            }
            
            int inventory = map.get(itemNo);
        	Bike b = new Bike(itemNo, category, inventory, price, name, descr);
        	lst.add(b);
    	}
	}
	
	public static void getSupplier2RecordByMark(String supplyData, String mark, String inventData, List<Bike> lst)
	{
		String invent[] = inventData.split("\n");
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		
		for(int i=0; i<invent.length; i++)
		{
			String inventItem[] = invent[i].split(" ");
            int inventory = Integer.parseInt(inventItem[1]);
            map.put(inventItem[0], inventory);  // itemNo and inventory(quantity)			
		}
		
		String items[] = supplyData.split(mark);

    	for(int i=0; i<items.length; i++){
    		String item = items[i];

            item = mark + item.replaceAll("((\r\n)|\n)[\\s\t ]*(\\1)+", "$1");
            String line[] = item.split("\n");
            if(line.length<=3)
            	continue;
            String itemNo = line[0];
        	String category = line[1].trim();
            float price = Float.parseFloat(line[2].replace(",", "").replace("$", ""));
            String name = line[3];
            String descr = "";

            if(line.length>=5)
            {
            	for(int j=4; j<line.length; j++)
            	{
            		descr += line[j];
            	}
            }
            
            if(map.get(itemNo) == null)
            {// error of the file
            	return;
            }
            
            int inventory = map.get(itemNo);
            
        	Bike b = new Bike(itemNo, category, inventory, price, name, descr);
        	lst.add(b);
    	}
	}
	
	public static void readSupplier1()
	{
		try {
        	String strSupplier1 = FileUtils.readFile("data/Supplier1.txt");
        	String strInventory1 = FileUtils.readFile("data/Inventory1.txt");

        	String mark = "item number=";
        	lst1 = new ArrayList<Bike>();
        	getSupplier1RecordByMark(strSupplier1, mark, strInventory1, lst1);
		}
        catch (Exception e) 
        {
            System.out.println("Exception1:" + e.getCause() + ": " + e.getMessage());
        }
	}

	public static void readSupplier2()
	{
		try {
        	String strSupplier2 = FileUtils.readFile("data/Supplier2.txt");
        	String strInventory2 = FileUtils.readFile("data/Inventory2.txt");

        	String mark = "#";
        	lst2 = new ArrayList<Bike>();
        	getSupplier2RecordByMark(strSupplier2, mark, strInventory2, lst2);
		}
        catch (Exception e) 
        {
            System.out.println("Exception2:" + e.getCause() + ": " + e.getMessage());
        }
	}
}
