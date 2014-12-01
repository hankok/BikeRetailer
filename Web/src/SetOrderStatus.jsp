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
      Set Order Status
    </title>
  </head>
  <body>
    <center>
      <h1>
      Set Order Status
      </h1>
      <form id="form" name="form" method="get" action="setOrderStatus">
      <table>
      <tr>
      <td>Order Index:</td>
      <td> <input name="sindex" type="text" /></td>
      </tr>
      <tr>
      <td>Order status:</td>
      <td><input name="status" type="text" /></td>
      </tr>
      <tr>
      <td></td>
      <td><input name="submit" type="submit" value=Submit/></td>
      </tr>
      </table>
      </form>
              
      <br>
      <br>
      <a href = "ViewInventory.jsp">Home Page</a>
    </center>
  </body>
</html>
