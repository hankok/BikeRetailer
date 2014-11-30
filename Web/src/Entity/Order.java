package Entity;

public class Order {
	String customerName;
	String address;
	String item;
	int quantity;
	String status;

	public Order(String customerName, String address, String item, int quantity, String stauts) {
		this.customerName = customerName;
		this.address = address;
		this.item = item;
		this.quantity = quantity;
		this.status = stauts;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}

	public String toString() {
		return status + "; " + customerName + "; " + address + "; " + item + "; " + quantity + '\n';
	}
}
