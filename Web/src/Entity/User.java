package Entity;

public class User {
	String username;
	String password;	
	String customerName;
	String address;

	
	public User(String username, String password, String customerName, String address) {
		this.username = username;
		this.password = password;
		this.customerName = customerName;
		this.address = address;
	}

	public String toString() {
		return username + "; " + password + "; " + customerName + "; " + address + '\n';
	}
}
