

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;

import Entity.User;

import com.mysql.jdbc.MysqlDataTruncation;


/**
 * 
 * @author Satish
 * 
 */
public class DBHelper {

	private Connection connection = null;
	
	private String connectUrl = null;
	
	public DBHelper(Properties prop) {
		String dbHost = prop.getProperty(Constants.DB_HOSTNAME_KEY);
		String dbPort = prop.getProperty(Constants.DB_PORT_KEY);
		String dbName = prop.getProperty(Constants.DB_NAME_KEY);
		String dbUsername = prop.getProperty(Constants.DB_USERNAME_KEY);
		String dbPassword = prop.getProperty(Constants.DB_PASSWORD_KEY);
		connectUrl = String.format("jdbc:mysql://%s:%s/%s?user=%s&password=%s", dbHost, dbPort, dbName, dbUsername, dbPassword);
	}

	private void getConnection() throws SQLException {
		if (connection == null) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			connection = DriverManager.getConnection(connectUrl);
		}
	}
	
	public List<Bike> getAllBikes() throws SQLException {
		List<Bike> bikeList = new ArrayList<>();
		getConnection();
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery("select * from inventory");
		while(rs.next()) {
			Bike bike = new Bike();
			bike.setModelNumber(rs.getString(Constants.COLUMN_MODEL_NO));
			bike.setDescription(rs.getString(Constants.COLUMN_DESC));
			bike.setPrice(rs.getDouble(Constants.COLUMN_PRICE));
			bike.setQuantity(rs.getInt(Constants.COLUMN_QTY));
			bikeList.add(bike);
		}
		return bikeList;
	}
	
	public Map<String, Integer> getInventory() throws SQLException {
		Map<String, Integer> resultMap = new HashMap<>();
		getConnection();
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery("select model_no, quantity from inventory");
		while(rs.next()) {
			resultMap.put(rs.getString(Constants.COLUMN_MODEL_NO), rs.getInt(Constants.COLUMN_QTY));
		}
		return resultMap;
	}
	
	public boolean updateInventory(String model, int qty) throws SQLException {
		getConnection();
		PreparedStatement stmt = connection.prepareStatement("update inventory set quantity = quantity - ? where model_no = ?");
		stmt.setInt(1, qty);
		stmt.setString(2, model);
		try {
			int cnt = stmt.executeUpdate();
			if (cnt != 1) {
				throw new SQLException("Model number <" + model + "> is invalid");
			}
		} catch (MysqlDataTruncation ex) {
			System.out.println("Not enough quantity to update model " + model + " by " + qty);
			return false;
		}
		return true;
	}
	
	public boolean insertOrder(String values) throws SQLException {
		getConnection();
		PreparedStatement stmt = connection.prepareStatement(
				"insert into `order`(`customer`, `model_no`, `quantity`, `price`,`bike_name`) values "+values);
		try {
			stmt.executeUpdate();
		} catch (MysqlDataTruncation ex) {
			System.out.println("Order not placed on the retailer.");
			return false;
		}
		return true;
	}

	public boolean insertUser(String values) throws SQLException {
		getConnection();
		PreparedStatement stmt = connection.prepareStatement(
				"insert into `customer`(`username`,`password`,`name`,`address`) values "+values);
		try {
			stmt.executeUpdate();
		} catch (MysqlDataTruncation ex) {
			System.out.println("User not registered on the retailer.");
			return false;
		}
		return true;
	}
	
	public Vector<String> getNameAddr(String username, String password) throws SQLException {
		// if name and addr can be retrieved, then this user exists
		Vector<String> result = new Vector<String>();
		getConnection();
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery
				("select name,address from `customer` where `username` = " + username + " and `password`= '" + password +"'");
		while(rs.next()) {
			result.add(rs.getString("name"));
			result.add(rs.getString("address")); 
		}
		return result;
	}
	
	public ArrayList<User> getAllUsers() throws SQLException {
		// if name and addr can be retrieved, then this user exists
		ArrayList<User> result = new ArrayList<User>();
		getConnection();
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery
				("select `username`,`password`,`name`,`address` from `customer`");
		while(rs.next()) {
			result.add(new User(rs.getString("name"), rs.getString("password"), rs.getString("name"), rs.getString("address")));
		}
		return result;
	}
	
	public Vector<String> getOrders(String username) throws SQLException {
		Vector<String> result = new Vector<String>();
		getConnection();
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery
				("select * from `order` where `customer` = " + username);
	    System.out.println("getOrders:" + "select * from `order` where `customer` = " + username);
		while(rs.next()) {
			result.add(rs.getString("order_id") + "," + rs.getString("customer")
					+ "," + rs.getString("model_no") + "," + rs.getString("quantity")
					+ "," + rs.getString("price") + "," + rs.getString("date")
					+ "," + rs.getString("bike_name") + "," + rs.getString("status")
					);
		}
		return result;
	}
	
	public Vector<String> getNamePriceQty(String model_no) throws SQLException {
		Vector<String> result = new Vector<String>();
		getConnection();
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery("select description, price, quantity from inventory where model_no = " + model_no);
		while(rs.next()) {
			result.add(rs.getString(Constants.COLUMN_DESC));
			result.add(rs.getString(Constants.COLUMN_PRICE)); // should be converted to double
			result.add(rs.getString(Constants.COLUMN_QTY));   // should be converted to int
		}
		return result;
	}
	
	public int getQuantity(int modelNo) throws SQLException {
		int quantity = 0;
		getConnection();
		PreparedStatement stmt = connection.prepareStatement("SELECT quantity FROM warehouse.inventory where model_no = ?;");
		stmt.setInt(1, modelNo);
		ResultSet result = stmt.executeQuery();
		if(result.next()){
			quantity = result.getInt(0);
		}
		return quantity;
	}
	
	public boolean updateInventoryBatch(Map<String, Integer> inventoryMap) throws SQLException {
		getConnection();
		Statement stmt = connection.createStatement();
		StringBuilder sqlUpdate = new StringBuilder();
		Set<String> keys = inventoryMap.keySet();
		for(String key : keys){
			sqlUpdate.append("update inventory set quantity = quantity - "+inventoryMap.get(key)+" where model_no = '"+key+"'; ");
		}
		System.out.println(sqlUpdate.toString());
		try {
			stmt.addBatch(sqlUpdate.toString());
			stmt.executeBatch();
		} catch (MysqlDataTruncation ex) {
			System.out.println("Not enough quantity to update model ");
			return false;
		}
		return true;
	}
}
