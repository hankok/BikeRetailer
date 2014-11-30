import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.List;

import Operation.Supplies;
import Entity.Bike;

public class Inventory {
	public static ArrayList<Bike> allList;

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

	public static void getAllInventory() {
		Supplies.readSupplier1();
		Supplies.readSupplier2();
		allList = new ArrayList<Bike>();
		allList.addAll(Supplies.lst1);
		allList.addAll(Supplies.lst2);
	}

	public static void getInventory1() {
		Supplies.readSupplier1();
		allList = new ArrayList<Bike>();
		allList.addAll(Supplies.lst1);
	}
	
	public static void getInventory2() {
		Supplies.readSupplier2();
		allList = new ArrayList<Bike>();
		allList.addAll(Supplies.lst2);
	}
	
	public static String getStrInventory() {
		String ret = "";
		for (int i = 0; i < allList.size(); i++) {
			Bike bike = (Bike) allList.get(i);
			ret += bike.toString() + '\n';
			System.out.println(bike.toString()+'\n');
		}
		return ret;
	}	
	

	public static String sortByPrice() {
		ComparatorPrice comparator = new ComparatorPrice();
		Collections.sort(allList, comparator);

		String ret = "";
		for (int i = 0; i < allList.size(); i++) {
			Bike bike = (Bike) allList.get(i);
			ret += bike.toString() + '\n';
			System.out.println(bike.toString()+'\n');
		}
		return ret;
	}

	public static int findBike(List<Bike> lst, String itemNo) {		
		ComparatorItemNo comparator = new ComparatorItemNo();
		Collections.sort(lst, comparator);
		int index = Collections.binarySearch(lst, new Bike(itemNo, "",0,(float)0,"",""), comparator);
		return index;
	}
	public static void main(String[] args) {
	}
}
