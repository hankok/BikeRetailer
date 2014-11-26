package edu.cmu.bikeretailer.warehouse;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import edu.cmu.bikeretailer.api.Bike;
import edu.cmu.bikeretailer.api.WarehouseInterface;

/**
 * 
 * @author Satish
 * 
 */
public class WarehouseImpl extends UnicastRemoteObject implements WarehouseInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6576674296521705710L;
	private DBHelper dbHelper = null;

	public WarehouseImpl(DBHelper dbHelper) throws RemoteException {
		super();
		this.dbHelper = dbHelper;
	}

	@Override
	public List<Bike> getBikes() throws RemoteException {
		try {
			return dbHelper.getAllBikes();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RemoteException("Exception occured when accessing the database", e);
		}
	}

	@Override
	public Map<String, Integer> getBikeInventory() throws RemoteException {
		try {
			return dbHelper.getInventory();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RemoteException("Exception occured when accessing the database", e);
		}
	}

	@Override
	public boolean updateInventory(String model, int quantity) throws RemoteException {
		try {
			return dbHelper.updateInventory(model, quantity);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RemoteException("Exception occured when accessing the database", e);
		}
	}

}
