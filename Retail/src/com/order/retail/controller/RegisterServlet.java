package com.order.retail.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.order.retail.adapter.Retailer;
import com.order.retail.adapter.UserManager;
import com.order.retail.model.User;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Retailer.fileUrl = new File(getServletContext().getRealPath("/")+"orders.ser");
		Retailer.UserFile = new File(getServletContext().getRealPath("/")+"user.ser");
		RequestDispatcher rd = request.getRequestDispatcher("/createAccount.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Retailer.UserFile = new File(getServletContext().getRealPath("/")+"user.ser");
		User user = new User();
		user.setAddress(request.getParameter("address"));
		user.setCity(request.getParameter("city"));
		user.setFirstName(request.getParameter("firstName"));
		user.setLastName(request.getParameter("lastName"));
		user.setState(request.getParameter("state"));
		user.setZipCode(Integer.parseInt(request.getParameter("zipcode")));
		user.setUserName(request.getParameter("userName"));
		user.setPassword(request.getParameter("password"));
		boolean isValid = UserManager.addUser(user, Retailer.UserFile);
		if(!isValid){
			request.setAttribute("error", "Registration failed !!\n Try Different User Name");
			RequestDispatcher rd = request.getRequestDispatcher("/createAccount.jsp");
			rd.forward(request, response);
		}
		else{
			request.setAttribute("success", "Registeration Successful");
			RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
			rd.forward(request, response);
		}
	}

}
