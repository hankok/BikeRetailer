package edu.cmu.bikeretailer.warehouse;

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

import com.mysql.jdbc.MysqlDataTruncation;

import edu.cmu.bikeretailer.api.Bike;

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

}
