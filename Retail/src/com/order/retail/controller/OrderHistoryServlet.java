package com.order.retail.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.order.retail.adapter.Retailer;
import com.order.retail.model.User;

/**
 * Servlet implementation class OrderHistoryServlet
 */
@WebServlet("/orderhistory")
public class OrderHistoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderHistoryServlet() {
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
			RequestDispatcher rd = request.getRequestDispatcher("/orderhistory.jsp");
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
		int status = Integer.parseInt(request.getParameter("status"));
		int id = Integer.parseInt(request.getParameter("orderid"));
		boolean isUpdated = Retailer.manageOrder(id, status);
		if(isUpdated)
			request.setAttribute("msg", "Order Updated Successfully");
		else
			request.setAttribute("error", "Order update failed");
		RequestDispatcher rd = request.getRequestDispatcher("/orderhistory.jsp");
		rd.forward(request, response);
	}

}
