<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE 
    html
    PUBLIC
    "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>
      Place Order
    </title>
    Logon User：<%= session.getAttribute("username") %>
  </head>
  <body>
    <center>
      <h1>
        Place Order
      </h1>
      <form id="form" name="form" method="get" action="placeOrder">
        <label>
          itemNo:
        </label>
        <input name="itemNo" type="text" />
        
        <label>
          Quantity:
        </label>
        <input name="sQuantity" type="text" />
      
        <label>
          Customer Name - Fisrt/Last:
        </label>
        <%= session.getAttribute("customerName") %>
					
        <label>
          Address:
        </label>
        <%= session.getAttribute("address") %>


        <input name="submit" type="submit"/>
       
      </form>
      <br>
      Buy 2 or more types of bikes? Input the itemNo and Quantity seperated by comma as follows: 
      <br><br>
      <center>#31-1673,#31-2593,#31-2595</center> 
      <center>1,2,1</center>
      <br>
      <br>
      <a href = "ViewInventory.jsp">Home Page</a>
    </center>
  </body>
</html>
