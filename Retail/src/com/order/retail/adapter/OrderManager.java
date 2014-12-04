package com.order.retail.adapter;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.order.retail.model.Order;
import com.order.retail.model.OrderStatus;
import com.order.retail.util.AppendingObjectOutputStream;
import com.order.retail.util.DBHelper;

public class OrderManager {
	DBHelper dbHelper;
	
	public OrderManager(DBHelper dbHelper) {
		this.dbHelper = dbHelper;
	}
	
	public void addOrder(Order order){
		dbHelper.insertOrder(order);
	}
	
	public List<Order> getOrder(int customerId){
		try {
			return dbHelper.getOrders(customerId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void updateOrder(int orderId, OrderStatus status){
		dbHelper.updateOrderStatus(orderId, status);
	}
	
	public static void main(String[] args){
//		Order order = new Order();
//		order.setAddress("202");
//		order.setCategory("test");
//		order.setCity("Pittsburgh");
//		order.setItemName("bike");
//		order.setItemNumber("#gdg");
//		order.setName("eddd");
//		order.setOrderId(5);
//		order.setPrice(200.0);
//		order.setQuantity(2);
//		order.setState("ddddd sds");
//		order.setZipCode(15208);
//		orderManager.addOrder(order);
//		orderManager.updateOrder(orderManager.getOrder());
//		System.out.println(orderManager.getOrder());
	}
}
