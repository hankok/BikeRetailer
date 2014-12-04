package com.order.retail.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.order.retail.adapter.Retailer;
import com.order.retail.model.Order;
import com.order.retail.model.User;

/**
 * Servlet implementation class OrderServlet
 */
@WebServlet("/order")
public class OrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		User user = null;
		if(session!=null)
			user = (User)session.getAttribute("user");
		if(user!=null){
			RequestDispatcher rd = request.getRequestDispatcher("/order.jsp");
			rd.forward(request, response);
		}
		else{
			RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
			rd.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		User user = null;
		if(session!=null)
			user = (User)session.getAttribute("user");
		if(user==null){
			RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
			rd.forward(request, response);
		}
		String[] items  = request.getParameter("itemNumber").split(",");
		String[] quantities = request.getParameter("quantity").split(",");
		for(int i = 0; i< items.length; i++){
			
		}
		Order order = new Order();
		order.setAddress(user.getAddress());
		order.setCity(user.getCity());
		order.setFirstName(user.getFirstName());
		order.setItemNumber(request.getParameter("itemNumber"));
		order.setLastName(user.getLastName());
		order.setQuantity(Integer.parseInt(request.getParameter("quantity")));
		order.setState(user.getState());
		order.setZipCode(user.getZipCode());
		order.setOrderDate(new Date());
		order.setUserName(user.getUserName());
		String result;
		try {
			result = Retailer.createOrder(order);
			if(result.equals("Order Placed Successfully"))
				request.setAttribute("success", result);
			else
				request.setAttribute("error", result);
			RequestDispatcher rd = request.getRequestDispatcher("/order.jsp");
			rd.forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
