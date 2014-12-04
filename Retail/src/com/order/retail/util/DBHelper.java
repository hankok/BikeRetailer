package com.order.retail.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mysql.jdbc.MysqlDataTruncation;
import com.order.retail.model.Bike;
import com.order.retail.model.Order;
import com.order.retail.model.OrderStatus;
import com.order.retail.model.User;

/**
 * 
 * @author Satish
 * 
 */
public class DBHelper {

	private Connection connection = null;
	
	private String connectUrl = null;
	
	public DBHelper() {
		connectUrl = String.format("jdbc:mysql://%s:%s/%s?user=%s&password=%s", "retail.cfgspoawxqsv.us-west-2.rds.amazonaws.com", "3306", "retail", "retail", "bikeretail");
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
		ResultSet rs = stmt.executeQuery("select * from inventory order by price limit 100");
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
	
	public Double[] getItemDetails(int modelNo) throws SQLException {
		getConnection();
		Double[] retVal = new Double[2];
		retVal[0] = -1.0;
		retVal[1] = -1.0;
		PreparedStatement stmt = connection.prepareStatement("SELECT price, quantity FROM inventory where model_no = ?;");
		stmt.setInt(1, modelNo);
		ResultSet result = stmt.executeQuery();
		if(result.next()){
			retVal[0] = result.getDouble(2);
			retVal[1] = result.getDouble(1);
		}
		return retVal;
	}
	
	public List<Order> getOrders(int customerId) throws SQLException{
		List<Order> orderList = new ArrayList<>();
		getConnection();
		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM order where customer = ?;");
		stmt.setInt(1, customerId);
		ResultSet rs = stmt.executeQuery();
		while(rs.next()) {
			Order order = new Order();
			order.setOrderId(rs.getInt("order_id"));
			order.setItemNumber(rs.getString("model_no"));
			order.setItemName(rs.getString("bike_name"));
			order.setOrderDate(rs.getDate("date"));
			order.setPrice(rs.getDouble("price"));
			order.setQuantity(rs.getInt("quantity"));
			order.setStatus(OrderStatus.values()[rs.getInt("status")]);
			orderList.add(order);
		}
		return orderList;		
	}
	
	public void updateOrderStatus(int orderId, OrderStatus status) throws SQLException {
		getConnection();
		PreparedStatement stmt = connection.prepareStatement("UPDATE order SET status = ? where order_id = ?");
		stmt.setInt(1, status.ordinal());
		stmt.setInt(2, orderId);
		stmt.executeUpdate();
	}
	
	public boolean insertOrder(Order order) throws SQLException {
		getConnection();
		Double[] itemVal = getItemDetails(Integer.parseInt(order.getItemNumber()));
		if (itemVal[0] == -1.0)
			return false;
		if (itemVal[1] >= order.getQuantity()) { 
			PreparedStatement stmt = connection.prepareStatement("insert into order values (?, ?, ?, ?, ?, ?, ?)");
			stmt.setString(1, order.getUserName());
			stmt.setInt(2, Integer.parseInt(order.getItemNumber()));
			stmt.setInt(3, order.getQuantity());
			stmt.setDouble(4, itemVal[0] * order.getQuantity());
			stmt.setDate(5, new java.sql.Date(new Date().getTime()));
			stmt.setString(6, order.getItemName());
			stmt.setInt(7, 0);
			int res = stmt.executeUpdate();
			return res != 0;
		} else {
			return false;
		}
	}
	
	private User getUser(String username) throws SQLException {
		User user = null;
		getConnection();
		PreparedStatement stmt = connection.prepareStatement("select * from customer where username = ?");
		stmt.setString(1, username);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			user = new User();
			user.setUserName(rs.getString("username"));
			user.setPassword(rs.getString("password"));
			user.setAddress(rs.getString("address"));
			user.setFirstName(rs.getString("first_name"));
			user.setLastName(rs.getString("last_name"));
			user.setZipCode(rs.getString("zip"));
			user.setState(rs.getString("state"));
			user.setCity(rs.getString("city"));
		}
		return user;
	}
	
	public User validateUser(String username, String password) throws SQLException {
		User user = getUser(username);
		if (user != null) {
			if (user.getPassword().equals(password)) {
				return user;
			}
		}
		return null;
	}
	
	public boolean addUser(User user) throws SQLException {
		if (getUser(user.getUserName()) != null) return false;
		getConnection();
		PreparedStatement stmt = connection.prepareStatement("insert into customer values (?, ?, ?, ?, ?, ?, ?, ?)");
		stmt.setString(1, user.getUserName());
		stmt.setString(2, user.getPassword());
		stmt.setString(3, user.getFirstName());
		stmt.setString(4, user.getFirstName());
		stmt.setString(5, user.getAddress());
		stmt.setString(6, user.getCity());
		stmt.setString(7, user.getState());
		stmt.setString(8, user.getZipCode());
		int res = stmt.executeUpdate();
		return res != 0;
	}
}
