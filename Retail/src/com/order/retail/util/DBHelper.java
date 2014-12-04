package com.order.retail.util;

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

import com.mysql.jdbc.MysqlDataTruncation;
import com.order.retail.model.Bike;
import com.order.retail.model.Order;
import com.order.retail.model.OrderStatus;

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
	
	public int getQuantity(int modelNo) throws SQLException {
		int quantity = 0;
		getConnection();
		PreparedStatement stmt = connection.prepareStatement("SELECT quantity FROM inventory where model_no = ?;");
		stmt.setInt(1, modelNo);
		ResultSet result = stmt.executeQuery();
		if(result.next()){
			quantity = result.getInt(1);
		}
		return quantity;
	}
	
	public List<Order> getOrders(int customerId) throws SQLException{
		List<Order> bikeList = new ArrayList<>();
		getConnection();
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery("");
		while(rs.next()) {
			Order order = new Order();
//			bike.setModelNumber(rs.getString(Constants.COLUMN_MODEL_NO));
//			bike.setDescription(rs.getString(Constants.COLUMN_DESC));
//			bike.setPrice(rs.getDouble(Constants.COLUMN_PRICE));
//			bike.setQuantity(rs.getInt(Constants.COLUMN_QTY));
//			bikeList.add(bike);
		}
		return bikeList;		
	}
	
	public void updateOrderStatus(int orderId, OrderStatus status){
		
	}
	
	public boolean insertOrder(Order order){
		return false;
	}
}
