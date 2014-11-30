package Entity;
import java.io.Serializable;

public class Bike implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String itemNo;
	public String category;
	public int inventory;
	public float price;
	public String name;
	public String descr;

	public Bike(String itemNo, String category, int inventory, float price,
			String name, String descr) {
		this.itemNo = itemNo;
		this.category = category;
		this.inventory = inventory;
		this.price = price;
		this.name = name;
		this.descr = descr;
	}

	public void setInventory(int inventory) {
		this.inventory = inventory;
	}
	
	public String toString() {
		return itemNo + "," + category + "," + inventory + "," + price
				+ "," + name + "," + descr;
	}
}
