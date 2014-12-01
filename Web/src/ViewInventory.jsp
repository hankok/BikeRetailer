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
      View Inventory
    </title>
    Logon User：<%= session.getAttribute("username") %>
  </head>
  <body>
    <center>
      <h1>
        View Inventory
      </h1>
      <form id="form" name="form" method="get" action="viewInventory">
      <table>
      <tr>
      <td> <select name="orderby" onchange="" style="width:180px; margin-left:-20px; ">
                   <option value="1">OrderBy Price</option>
                   <option value="2">OrderBy BikeName</option>
	    </select>
	    </td>
	    <td><input name="View Inventory" type="submit" value=Submit/>
	    </td>
      </tr>
      </table>
      </form>
      <br>
      <br>
      <h1>
      <a href = "PlaceOrder.jsp">Place Order</a>
      <br>
      <br>
      <a href = "ViewOrder.jsp">View Order</a>
      <br>
      <br>
      <a href = "Signup.jsp">Create a new user</a>
      <br>
      <br>
      <a href = "Login.jsp">Login</a>
      </h1>
    </center>
  </body>
</html>
