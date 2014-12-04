package com.order.retail.adapter;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import com.order.retail.model.User;
import com.order.retail.util.AppendingObjectOutputStream;

public class UserManager {
	static ArrayList<User> userList = new ArrayList<User>();
	public static boolean addUser(User user, File file){
		ObjectOutputStream objectOutputStream = null;
		FileOutputStream fileOutputStream = null;
		AppendingObjectOutputStream objectOutputStream2 =  null;
		if(userNameExists(file.getPath(),user.getUserName()))
			return false;
		try {
			if(!file.exists()){
				fileOutputStream = new FileOutputStream(file.getAbsolutePath(), true);
				objectOutputStream = new ObjectOutputStream(fileOutputStream);
				objectOutputStream.writeObject(user);
				objectOutputStream.close();
			}else{
				fileOutputStream = new FileOutputStream(file.getAbsolutePath(), true);
				objectOutputStream2 = new AppendingObjectOutputStream(fileOutputStream);
				objectOutputStream2.writeObject(user);
				objectOutputStream2.close();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	public static void getUser(String file){
		ObjectInputStream objectInputStream;
		FileInputStream fileInputStream;
		userList = new ArrayList<User>();
		if(!new File(file).exists())
			return;
		try {
			fileInputStream = new FileInputStream(file);
			objectInputStream = new ObjectInputStream(fileInputStream);
			User user = null;
			while((user = (User)objectInputStream.readObject())!=null){
				userList.add(user);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (EOFException e){
			
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static User checkUser(String userName, String password){
		for(int i =0; i< userList.size(); i++){
			if(userList.get(i).getUserName().equals(userName) && userList.get(i).getPassword().equals(password))
				return userList.get(i);
		}
		return null;
	}
	
	private static boolean userNameExists(String file,String username){
		getUser(file);
		for(int i =0; i< userList.size(); i++){
			if(userList.get(i).getUserName().equals(username))
				return true;
		}
		return false;
	}

}
